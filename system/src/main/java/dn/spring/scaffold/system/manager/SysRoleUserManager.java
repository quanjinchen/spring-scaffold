package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.system.entity.SysRoleUser;

import java.util.List;

public interface SysRoleUserManager {

    List<SysRoleUser> listByUserId(Long userId);

    boolean existsByRoleId(Long roleId);

    void replaceUserRoles(Long userId, List<Long> roleIds);

    void deleteByUserId(Long userId);

    void deleteByRoleId(Long roleId);
}
