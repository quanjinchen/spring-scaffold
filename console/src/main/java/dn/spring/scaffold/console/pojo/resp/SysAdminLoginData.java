package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "后台登录响应")
public class SysAdminLoginData {

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "访问令牌")
    private String token;

    @Schema(description = "二次认证令牌")
    private String mfaToken;

    @Schema(description = "是否系统管理员")
    private boolean systemAdmin;
}
