package dn.spring.scaffold.console.pojo.resp;

import lombok.Data;

import java.util.List;

@Data
public class LoginData {

    private Long adminId;

    private String username;

    private String nickname;

    private List<String> roleCodes;

    private List<RoleGrantInfo> roles;

    private List<MenuTreeNode> menus;
}
