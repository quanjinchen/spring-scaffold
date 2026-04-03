package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.pojo.resp.MenuTreeNode;
import dn.spring.scaffold.system.entity.SysMenu;
import dn.spring.scaffold.system.entity.SysRoleMenu;
import dn.spring.scaffold.system.mapper.SysMenuMapper;
import dn.spring.scaffold.system.mapper.SysRoleMenuMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final SysMenuMapper sysMenuMapper;

    private final SysRoleMenuMapper sysRoleMenuMapper;

    public MenuService(SysMenuMapper sysMenuMapper, SysRoleMenuMapper sysRoleMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
        this.sysRoleMenuMapper = sysRoleMenuMapper;
    }

    public List<SysMenu> listAll() {
        return sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .orderByAsc(SysMenu::getSortOrder)
                .orderByAsc(SysMenu::getId));
    }

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

    public PageResult<SysMenu> page(PageQuery pageQuery) {
        List<SysMenu> records = listAll();
        return PageResult.of(records, records.size(), pageQuery);
    }

    public SysMenu detail(Long id) {
        SysMenu sysMenu = sysMenuMapper.selectOne(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getId, id)
                .last("limit 1"));
        return sysMenu == null ? new SysMenu() : sysMenu;
    }

    public SysMenu save(SysMenu menu) {
        if (menu.getId() == null) {
            sysMenuMapper.insert(menu);
            return menu;
        }
        sysMenuMapper.updateById(menu);
        return menu;
    }

    public List<Long> listRoleMenuIds(Long roleId) {
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, roleId));
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
