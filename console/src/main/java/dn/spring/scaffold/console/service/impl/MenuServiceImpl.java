package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.pojo.resp.MenuTreeNode;
import dn.spring.scaffold.console.service.MenuService;
import dn.spring.scaffold.system.entity.SysMenu;
import dn.spring.scaffold.system.entity.SysRoleMenu;
import dn.spring.scaffold.system.manager.SysMenuManager;
import dn.spring.scaffold.system.manager.SysRoleMenuManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private SysMenuManager sysMenuManager;
    @Resource
    private SysRoleMenuManager sysRoleMenuManager;

    @Override
    public List<SysMenu> listAll() {
        return sysMenuManager.listAll();
    }

    @Override
    public List<MenuTreeNode> tree() {
        List<SysMenu> allMenus = listAll();
        List<MenuTreeNode> roots = new ArrayList<MenuTreeNode>();
        for (SysMenu menu : allMenus) {
            if (Long.valueOf(0L).equals(menu.getParentId())) {
                roots.add(toTreeNode(menu, allMenus));
            }
        }
        return roots;
    }

    @Override
    public PageResult<SysMenu> page(PageQuery pageQuery) {
        List<SysMenu> records = listAll();
        return PageResult.of(records, records.size(), pageQuery);
    }

    @Override
    public SysMenu detail(Long id) {
        SysMenu sysMenu = sysMenuManager.getById(id);
        return sysMenu == null ? new SysMenu() : sysMenu;
    }

    @Override
    public SysMenu save(SysMenu menu) {
        SysMenu latestMenu = sysMenuManager.save(menu);
        return latestMenu == null ? new SysMenu() : latestMenu;
    }

    @Override
    public List<Long> listRoleMenuIds(Long roleId) {
        List<SysRoleMenu> roleMenus = sysRoleMenuManager.listByRoleId(roleId);
        if (roleMenus.isEmpty()) {
            return Collections.emptyList();
        }
        return roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
    }

    private MenuTreeNode toTreeNode(SysMenu menu, List<SysMenu> allMenus) {
        MenuTreeNode node = new MenuTreeNode();
        node.setId(menu.getId());
        node.setParentId(menu.getParentId());
        node.setName(menu.getName());
        node.setPath(menu.getPath());
        node.setMenuType(menu.getMenuType());
        node.setPermissionCode(menu.getPermissionCode());
        node.setSortOrder(menu.getSortOrder());
        node.setVisible(menu.getVisible());

        List<MenuTreeNode> children = allMenus.stream()
                .filter(item -> menu.getId().equals(item.getParentId()))
                .map(item -> toTreeNode(item, allMenus))
                .collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
