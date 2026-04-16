package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.DeleteFileReqParam;
import dn.spring.scaffold.console.pojo.resp.FileUploadRespData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    RespInfo<FileUploadRespData> upload(MultipartFile multipartFile, String publicUrlPrefix);

    RespInfo<FileUploadRespData> uploadBytes(String fileName, byte[] fileBytes, String publicUrlPrefix);

    RespInfo<FileUploadRespData> uploadDataUrl(String fileName, String dataUrl, String publicUrlPrefix);

    void download(String fileId, HttpServletResponse response);

    RespInfo<Void> deleteFile(DeleteFileReqParam reqParam);
}
