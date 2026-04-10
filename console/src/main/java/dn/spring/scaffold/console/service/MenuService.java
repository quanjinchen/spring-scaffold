package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.pojo.resp.MenuTreeNode;
import dn.spring.scaffold.system.entity.SysMenu;

import java.util.List;

public interface MenuService {

    List<SysMenu> listAll();

    List<MenuTreeNode> tree();

    PageResult<SysMenu> page(PageQuery pageQuery);

    SysMenu detail(Long id);

    SysMenu save(SysMenu menu);

    List<Long> listRoleMenuIds(Long roleId);
}
