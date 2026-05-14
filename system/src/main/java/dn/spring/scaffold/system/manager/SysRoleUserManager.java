package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.system.entity.SysRoleUser;

import java.util.List;

public interface SysRoleUserManager {

    List<SysRoleUser> listByUserId(Long userId);

    List<SysRoleUser> listByRoleId(Long roleId);

    boolean existsByRoleId(Long roleId);

    void replaceUserRoles(Long userId, List<Long> roleIds);

    void replaceRoleUsers(Long roleId, List<Long> userIds);

    void deleteByUserId(Long userId);

    void deleteByRoleId(Long roleId);
}
