package dn.spring.scaffold.file.manager;

import dn.spring.scaffold.file.entity.FileRecord;
import org.springframework.web.multipart.MultipartFile;

public interface FileManager {

    FileRecord upload(String fileName, String dataUrl);

    FileRecord upload(MultipartFile multipartFile);

    FileRecord upload(String fileName, byte[] fileBytes);

    byte[] download(String objectName);

    void delete(String fileId);

    void saveFile(FileRecord fileRecord);

    FileRecord getFileByFileId(String fileId);
}
