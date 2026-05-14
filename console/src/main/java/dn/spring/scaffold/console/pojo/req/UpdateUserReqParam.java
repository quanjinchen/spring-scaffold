package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "更新用户请求")
public class UpdateUserReqParam {

    @Schema(description = "用户 ID", required = true, example = "1")
    @NotNull(message = "用户 ID 不能为空")
    private Long id;

    @Schema(description = "用户名", example = "admin")
    private String username;

    @Schema(description = "姓名", example = "张三")
    private String fullName;

    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;

    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @Schema(description = "身份证号", example = "110101199001011234")
    private String idCard;

    @Schema(description = "人脸图片 base64", example = "data:image/png;base64,xxxx")
    private String faceBase64;

    @Schema(description = "人脸文件 ID", example = "202605120001")
    private String faceFileId;

    @Schema(description = "状态，1 启用，0 禁用", example = "1")
    private Integer status;
}
