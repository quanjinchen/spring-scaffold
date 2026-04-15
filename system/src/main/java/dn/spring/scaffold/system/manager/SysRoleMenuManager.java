package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.system.entity.SysRoleMenu;

import java.util.Collection;
import java.util.List;

public interface SysRoleMenuManager {

    List<SysRoleMenu> listByRoleId(Long roleId);

    List<SysRoleMenu> listByRoleIds(Collection<Long> roleIds);

    boolean existsByMenuId(Long menuId);

    void replaceRoleMenus(Long roleId, List<Long> menuIds);

    void deleteByRoleId(Long roleId);

    void deleteByMenuId(Long menuId);
}
