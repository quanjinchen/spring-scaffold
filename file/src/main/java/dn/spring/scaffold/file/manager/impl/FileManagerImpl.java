package dn.spring.scaffold.file.manager.impl;

import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.exception.BizException;
import dn.spring.scaffold.file.config.FileStorageProperties;
import dn.spring.scaffold.file.constant.FileCategoryConstants;
import dn.spring.scaffold.file.entity.FileRecord;
import dn.spring.scaffold.file.manager.FileManager;
import dn.spring.scaffold.file.mapper.FileRecordMapper;
import dn.spring.scaffold.file.util.DataUrlUtils;
import dn.spring.scaffold.file.util.FileUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class FileManagerImpl implements FileManager {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Resource
    private FileRecordMapper fileRecordMapper;

    @Resource
    private S3Client ossClient;

    @Resource
    private FileStorageProperties fileStorageProperties;

    @Override
    public FileRecord upload(String fileName, String dataUrl) {
        return upload(fileName, dataUrl, FileCategoryConstants.COMMON);
    }

    @Override
    public FileRecord upload(String fileName, String dataUrl, String fileCategory) {
        DataUrlUtils.DataUrlInfo dataUrlInfo = DataUrlUtils.parseDataUrl(dataUrl);
        String suffix = dataUrlInfo.getMediaType().getSubtype();
        String fileId = newFileId();
        String objectName = buildObjectName(fileId, suffix);
        byte[] contentBytes = dataUrlInfo.getData();
        String contentType = dataUrlInfo.getMediaType().toString();

        putObject(objectName, contentType, contentBytes);

        FileRecord fileRecord = new FileRecord();
        fileRecord.setFileId(fileId);
        fileRecord.setFileSize((long) contentBytes.length);
        fileRecord.setContentType(contentType);
        fileRecord.setObjectName(objectName);
        fileRecord.setFileCategory(resolveFileCategory(fileCategory));
        fileRecord.setFileSuffix(suffix);
        fileRecord.setFileName(resolveFileName(fileName, buildFallbackFileName(fileId, suffix)));
        saveFile(fileRecord);
        return fileRecord;
    }

    @Override
    public FileRecord upload(MultipartFile multipartFile) {
        return upload(multipartFile, FileCategoryConstants.COMMON);
    }

    @Override
    public FileRecord upload(MultipartFile multipartFile, String fileCategory) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new BizException(ResultCode.FILE_NOT_EMPTY);
        }
        try {
            return upload(multipartFile.getOriginalFilename(), multipartFile.getBytes(), fileCategory);
        } catch (IOException exception) {
            throw new BizException("file upload failed: " + exception.getMessage());
        }
    }

    @Override
    public FileRecord upload(String fileName, byte[] fileBytes) {
        return upload(fileName, fileBytes, FileCategoryConstants.COMMON);
    }

    @Override
    public FileRecord upload(String fileName, byte[] fileBytes, String fileCategory) {
        if (fileBytes == null || fileBytes.length == 0) {
            throw new BizException(ResultCode.FILE_NOT_EMPTY);
        }

        String suffix = FileUtils.getFileSuffix(fileName);
        String fileId = newFileId();
        String objectName = buildObjectName(fileId, suffix);
        String contentType = FileUtils.getMimeType(new ByteArrayInputStream(fileBytes), fileName);

        putObject(objectName, contentType, fileBytes);

        FileRecord fileRecord = new FileRecord();
        fileRecord.setFileId(fileId);
        fileRecord.setFileSize((long) fileBytes.length);
        fileRecord.setContentType(contentType);
        fileRecord.setObjectName(objectName);
        fileRecord.setFileCategory(resolveFileCategory(fileCategory));
        fileRecord.setFileSuffix(suffix);
        fileRecord.setFileName(resolveFileName(fileName, buildFallbackFileName(fileId, suffix)));
        saveFile(fileRecord);
        return fileRecord;
    }

    @Override
    public byte[] download(String objectName) {
        try {
            ResponseBytes<GetObjectResponse> response = ossClient.getObjectAsBytes(GetObjectRequest.builder()
                    .bucket(fileStorageProperties.getBucketName())
                    .key(objectName)
                    .build());
            return response.asByteArray();
        } catch (NoSuchKeyException | NoSuchBucketException exception) {
            throw new BizException(ResultCode.FILE_NOT_FOUND);
        } catch (AwsServiceException exception) {
            throw new BizException("file download failed: " + exception.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String fileId) {
        FileRecord fileRecord = getFileByFileId(fileId);
        if (fileRecord == null) {
            throw new BizException(ResultCode.FILE_NOT_FOUND);
        }
        fileRecordMapper.deleteById(fileRecord.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFile(FileRecord fileRecord) {
        fileRecordMapper.insert(fileRecord);
    }

    @Override
    public FileRecord getFileByFileId(String fileId) {
        return fileRecordMapper.selectOne(new LambdaQueryWrapper<FileRecord>()
                .eq(FileRecord::getFileId, fileId)
                .last("limit 1"));
    }

    private void putObject(String objectName, String contentType, byte[] bytes) {
        try {
            ossClient.putObject(PutObjectRequest.builder()
                            .bucket(fileStorageProperties.getBucketName())
                            .key(objectName)
                            .contentType(contentType)
                            .contentLength((long) bytes.length)
                            .build(),
                    RequestBody.fromBytes(bytes));
        } catch (AwsServiceException exception) {
            throw new BizException("file upload failed: " + exception.getMessage());
        }
    }

    private String buildObjectName(String fileId, String suffix) {
        String datePath = LocalDate.now().format(DATE_FORMATTER);
        if (suffix == null || suffix.isEmpty()) {
            return datePath + "/" + fileId;
        }
        return datePath + "/" + fileId + "." + suffix;
    }

    private String resolveFileName(String fileName, String fallbackFileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return fallbackFileName;
        }
        return fileName;
    }

    private String resolveFileCategory(String fileCategory) {
        if (fileCategory == null || fileCategory.trim().isEmpty()) {
            return FileCategoryConstants.COMMON;
        }
        return fileCategory.trim();
    }

    private String buildFallbackFileName(String fileId, String suffix) {
        if (suffix == null || suffix.isEmpty()) {
            return fileId;
        }
        return fileId + "." + suffix;
    }

    private String newFileId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
