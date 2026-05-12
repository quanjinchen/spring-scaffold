package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户信息")
public class UserDTO {

    @Schema(description = "用户 ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "姓名")
    private String fullName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "人脸文件 ID")
    private String faceFileId;

    @Schema(description = "组织机构 ID")
    private Long orgId;

    @Schema(description = "状态，1 启用，0 禁用")
    private Integer status;
}
