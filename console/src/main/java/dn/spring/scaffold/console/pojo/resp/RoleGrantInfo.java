package dn.spring.scaffold.console.pojo.resp;

import lombok.Data;

import java.util.List;

@Data
public class RoleGrantInfo {

    private Long roleId;

    private String roleCode;

    private String roleName;

    private List<Long> menuIds;
}
