package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.common.page.PageUtils;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.resp.MenuTreeNode;
import dn.spring.scaffold.console.pojo.resp.UserRoleInfo;
import dn.spring.scaffold.console.service.MenuService;
import dn.spring.scaffold.console.service.UserRoleService;
import dn.spring.scaffold.system.entity.SysMenu;
import dn.spring.scaffold.system.entity.SysRoleMenu;
import dn.spring.scaffold.system.manager.SysMenuManager;
import dn.spring.scaffold.system.manager.SysRoleMenuManager;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private static final String SYSTEM_ADMIN_ROLE_CODE = "systemAdmin";

    @Resource
    private SysMenuManager sysMenuManager;
    @Resource
    private SysRoleMenuManager sysRoleMenuManager;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public List<SysMenu> listAll() {
        return sysMenuManager.listAll();
    }

    @Override
    public List<MenuTreeNode> tree() {
        List<SysMenu> allMenus = listAll();
        return buildTree(allMenus);
    }

    @Override
    public RespInfo<List<MenuTreeNode>> treeResp() {
        return RespInfo.success(tree());
    }

    @Override
    public List<MenuTreeNode> treeByUserId(Long userId) {
        List<UserRoleInfo> userRoles = userRoleService.listUserRoles(userId);
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }

        for (UserRoleInfo userRole : userRoles) {
            if (SYSTEM_ADMIN_ROLE_CODE.equals(userRole.getRoleCode())) {
                return tree();
            }
        }

        Set<Long> menuIds = new LinkedHashSet<Long>();
        for (UserRoleInfo userRole : userRoles) {
            menuIds.addAll(listRoleMenuIds(userRole.getRoleId()));
        }
        if (CollectionUtils.isEmpty(menuIds)) {
            return Collections.emptyList();
        }

        List<SysMenu> allMenus = listAll();
        Set<Long> selectedMenuIds = new LinkedHashSet<Long>(menuIds);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (SysMenu menu : allMenus) {
                if (selectedMenuIds.contains(menu.getId())) {
                    Long parentId = menu.getParentId();
                    if (parentId != null && parentId > 0 && selectedMenuIds.add(parentId)) {
                        changed = true;
                    }
                }
            }
        }

        List<SysMenu> userMenus = allMenus.stream()
                .filter(menu -> selectedMenuIds.contains(menu.getId()))
                .collect(Collectors.toList());
        return buildTree(userMenus);
    }

    @Override
    public PageData<SysMenu> page(PageReqParam reqParam) {
        List<SysMenu> records = listAll();
        return PageUtils.of(reqParam.getPageNum(), reqParam.getPageSize(), records.size(), records);
    }

    @Override
    public RespInfo<PageData<SysMenu>> pageResp(PageReqParam reqParam) {
        return RespInfo.success(page(reqParam));
    }

    @Override
    public SysMenu detail(Long id) {
        SysMenu sysMenu = sysMenuManager.getById(id);
        return sysMenu == null ? new SysMenu() : sysMenu;
    }

    @Override
    public RespInfo<SysMenu> detailResp(Long id) {
        return RespInfo.success(detail(id));
    }

    @Override
    public SysMenu save(SysMenu menu) {
        SysMenu latestMenu = sysMenuManager.save(menu);
        return latestMenu == null ? new SysMenu() : latestMenu;
    }

    @Override
    public RespInfo<SysMenu> saveResp(SysMenu menu) {
        return RespInfo.created(save(menu));
    }

    @Override
    public SysMenu update(SysMenu menu) {
        return save(menu);
    }

    @Override
    public RespInfo<SysMenu> updateResp(SysMenu menu) {
        return RespInfo.success(update(menu));
    }

    @Override
    public void delete(Long menuId) {
        SysMenu menu = sysMenuManager.getById(menuId);
        ResultCode.MENU_NOT_FOUND.assertNotNull(menu);
        ResultCode.CAN_NOT_DELETE_MENU_BECAUSE_HAS_CHILDREN.assertIsFalse(sysMenuManager.existsChildren(menuId));
        ResultCode.MENU_IN_USE.assertIsFalse(sysRoleMenuManager.existsByMenuId(menuId));
        sysMenuManager.deleteById(menuId);
    }

    @Override
    public RespInfo<Void> deleteResp(Long menuId) {
        delete(menuId);
        return RespInfo.success();
    }

    @Override
    public List<Long> listRoleMenuIds(Long roleId) {
        List<SysRoleMenu> roleMenus = sysRoleMenuManager.listByRoleId(roleId);
        if (roleMenus.isEmpty()) {
            return Collections.emptyList();
        }
        return roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
    }

    private List<MenuTreeNode> buildTree(List<SysMenu> menus) {
        List<MenuTreeNode> roots = new ArrayList<MenuTreeNode>();
        for (SysMenu menu : menus) {
            if (Long.valueOf(0L).equals(menu.getParentId())) {
                roots.add(toTreeNode(menu, menus));
            }
        }
        return roots;
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
