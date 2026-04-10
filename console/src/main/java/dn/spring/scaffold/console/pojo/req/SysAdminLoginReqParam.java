package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "后台账号密码登录请求")
public class SysAdminLoginReqParam {

    @Schema(description = "登录账号", required = true, example = "admin")
    @NotBlank(message = "账号不能为空")
    private String account;

    @Schema(description = "登录密码", required = true, example = "Admin@123")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "验证码唯一标识", required = true)
    @NotBlank(message = "验证码标识不能为空")
    private String uuid;

    @Schema(description = "验证码", required = true, example = "1234")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
