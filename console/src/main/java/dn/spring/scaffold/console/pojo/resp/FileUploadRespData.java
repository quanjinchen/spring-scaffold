package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "文件上传响应")
public class FileUploadRespData {

    @Schema(description = "文件 ID")
    private String fileId;

    @Schema(description = "文件访问地址")
    private String fileUrl;
}
