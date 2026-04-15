package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "创建用户请求")
public class CreateUserReqParam {

    @Schema(description = "用户名", required = true, example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "昵称", required = true, example = "系统管理员")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;

    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @Schema(description = "组织机构 ID", example = "1")
    private Long orgId;

    @Schema(description = "状态，1 启用，0 禁用", example = "1")
    private Integer status;

    @Schema(description = "密码，不传则使用默认重置密码", example = "123456")
    private String password;
}
