package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.exception.BizException;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.resp.FileUploadRespData;
import dn.spring.scaffold.file.entity.FileRecord;
import dn.spring.scaffold.file.manager.FileManager;
import dn.spring.scaffold.file.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Service
public class FileService {

    private final FileManager fileManager;

    public FileService(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public RespInfo<FileUploadRespData> upload(MultipartFile multipartFile, String publicUrlPrefix) {
        FileRecord fileRecord = fileManager.upload(multipartFile);
        return RespInfo.success(buildUploadResp(fileRecord.getFileId(), publicUrlPrefix));
    }

    public RespInfo<FileUploadRespData> uploadBytes(String fileName, byte[] fileBytes, String publicUrlPrefix) {
        FileRecord fileRecord = fileManager.upload(fileName, fileBytes);
        return RespInfo.success(buildUploadResp(fileRecord.getFileId(), publicUrlPrefix));
    }

    public RespInfo<FileUploadRespData> uploadDataUrl(String fileName, String dataUrl, String publicUrlPrefix) {
        FileRecord fileRecord = fileManager.upload(fileName, dataUrl);
        return RespInfo.success(buildUploadResp(fileRecord.getFileId(), publicUrlPrefix));
    }

    public void download(String fileId, HttpServletResponse response) {
        FileRecord fileRecord = fileManager.getFileByFileId(fileId);
        if (fileRecord == null) {
            throw new BizException(ResultCode.FILE_NOT_FOUND);
        }

        byte[] content = fileManager.download(fileRecord.getObjectName());
        try {
            response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileRecord.getFileName(), "UTF-8"));
        } catch (Exception exception) {
            throw new BizException("file download failed: " + exception.getMessage());
        }
        response.setContentType(fileRecord.getContentType());
        try {
            response.getOutputStream().write(content);
            response.getOutputStream().flush();
        } catch (IOException exception) {
            throw new BizException("file download failed: " + exception.getMessage());
        }
    }

    public void delete(String fileId) {
        fileManager.delete(fileId);
    }

    private FileUploadRespData buildUploadResp(String fileId, String publicUrlPrefix) {
        FileUploadRespData respData = new FileUploadRespData();
        respData.setFileId(fileId);
        respData.setFileUrl(FileUtils.buildFileUrl(publicUrlPrefix, fileId));
        return respData;
    }
}
