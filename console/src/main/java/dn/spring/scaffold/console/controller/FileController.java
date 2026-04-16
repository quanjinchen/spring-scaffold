package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.DeleteFileReqParam;
import dn.spring.scaffold.console.pojo.resp.FileUploadRespData;
import dn.spring.scaffold.console.service.FileService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "文件管理")
@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @Value("${project.file.public-url-prefix:}")
    private String publicUrlPrefix;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "上传文件", description = "权限：system:file:upload")
    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @OperateLog(module = "file", action = "上传文件")
    @SaCheckPermission("system:file:upload")
    public RespInfo<FileUploadRespData> uploadFile(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file, publicUrlPrefix);
    }

    @Operation(summary = "下载文件", description = "权限：system:file:download")
    @GetMapping("/download-file/{fileId}")
    @SaCheckPermission("system:file:download")
    public void downloadFile(@Parameter(description = "文件 ID") @PathVariable String fileId, HttpServletResponse response) {
        fileService.download(fileId, response);
    }

    @Operation(summary = "删除文件", description = "权限：system:file:delete")
    @PostMapping("/delete-file")
    @OperateLog(module = "file", action = "删除文件")
    @SaCheckPermission("system:file:delete")
    public RespInfo<Void> deleteFile(@Valid @RequestBody DeleteFileReqParam reqParam) {
        return fileService.deleteFile(reqParam);
    }
}
