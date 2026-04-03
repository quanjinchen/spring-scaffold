package dn.spring.scaffold.console.pojo.resp;

import lombok.Data;

@Data
public class UserRoleInfo {

    private Long userId;

    private Long roleId;

    private String roleCode;

    private String roleName;
}
