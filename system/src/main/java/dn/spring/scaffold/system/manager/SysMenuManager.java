package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.system.entity.SysMenu;

import java.util.Collection;
import java.util.List;

public interface SysMenuManager {

    List<SysMenu> listAll();

    SysMenu getById(Long menuId);

    List<SysMenu> listByIds(Collection<Long> menuIds);

    SysMenu save(SysMenu menu);
}
