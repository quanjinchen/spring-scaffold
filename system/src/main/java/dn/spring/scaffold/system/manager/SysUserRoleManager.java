package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.system.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleManager {

    List<SysUserRole> listByUserId(Long userId);

    void replaceUserRoles(Long userId, List<Long> roleIds);
}
