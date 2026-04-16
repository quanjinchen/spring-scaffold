package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "删除文件请求")
public class DeleteFileReqParam {

    @Schema(description = "文件 ID", required = true, example = "abc123")
    @NotBlank(message = "文件 ID 不能为空")
    private String fileId;
}
