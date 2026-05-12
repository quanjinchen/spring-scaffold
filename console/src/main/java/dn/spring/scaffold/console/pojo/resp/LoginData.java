package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "当前登录用户信息")
public class LoginData {

    @Schema(description = "管理员 ID")
    private Long adminId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "姓名")
    private String fullName;

    @Schema(description = "角色编码列表")
    private List<String> roleCodes;

    @Schema(description = "角色授权信息列表")
    private List<RoleGrantInfoDTO> roles;

    @Schema(description = "菜单树")
    private List<MenuTreeNode> menus;
}
