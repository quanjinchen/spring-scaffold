package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateMenuReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteMenuReqParam;
import dn.spring.scaffold.console.pojo.req.GetMenuByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListMenuReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateMenuReqParam;
import dn.spring.scaffold.console.pojo.resp.MenuDTO;
import dn.spring.scaffold.console.pojo.resp.MenuTreeNode;
import dn.spring.scaffold.console.service.MenuService;
import dn.spring.scaffold.system.entity.SysMenu;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.entity.SysRoleMenu;
import dn.spring.scaffold.system.entity.SysUserRole;
import dn.spring.scaffold.system.manager.SysMenuManager;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.manager.SysRoleMenuManager;
import dn.spring.scaffold.system.manager.SysUserRoleManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    private SysUserRoleManager sysUserRoleManager;
    @Resource
    private SysRoleManager sysRoleManager;

    @Override
    public RespInfo<List<MenuTreeNode>> listAllMenuTree() {
        return RespInfo.success(buildTree(sysMenuManager.listAll()));
    }

    @Override
    public List<MenuTreeNode> listMenuTreeByUserId(Long userId) {
        List<SysUserRole> userRoles = sysUserRoleManager.listByUserId(userId);
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }

        for (SysUserRole userRole : userRoles) {
            SysRole role = sysRoleManager.getById(userRole.getRoleId());
            if (role != null && SYSTEM_ADMIN_ROLE_CODE.equals(role.getCode())) {
                return buildTree(sysMenuManager.listAll());
            }
        }

        Set<Long> menuIds = new LinkedHashSet<Long>();
        for (SysUserRole userRole : userRoles) {
            List<SysRoleMenu> roleMenus = sysRoleMenuManager.listByRoleId(userRole.getRoleId());
            for (SysRoleMenu roleMenu : roleMenus) {
                menuIds.add(roleMenu.getMenuId());
            }
        }
        if (CollectionUtils.isEmpty(menuIds)) {
            return Collections.emptyList();
        }

        List<SysMenu> allMenus = sysMenuManager.listAll();
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
    public RespInfo<PageData<MenuDTO>> listMenu(ListMenuReqParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        List<SysMenu> menuList = sysMenuManager.listAll();
        List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>(menuList.size());
        for (SysMenu menu : menuList) {
            menuDTOList.add(toMenuDTO(menu));
        }

        PageData<MenuDTO> pageData = new PageData<MenuDTO>();
        pageData.setTotal(new PageInfo<SysMenu>(menuList).getTotal());
        pageData.setRecords(menuDTOList);
        pageData.setPageNum(reqParam.getPageNum());
        pageData.setPageSize(reqParam.getPageSize());
        return RespInfo.success(pageData);
    }

    @Override
    public RespInfo<MenuDTO> getMenuById(GetMenuByIdReqParam reqParam) {
        SysMenu menu = sysMenuManager.getById(reqParam.getMenuId());
        ResultCode.MENU_NOT_FOUND.assertNotNull(menu);
        return RespInfo.success(toMenuDTO(menu));
    }

    @Override
    public RespInfo<MenuDTO> createMenu(CreateMenuReqParam reqParam) {
        SysMenu menu = new SysMenu();
        menu.setParentId(reqParam.getParentId());
        menu.setName(reqParam.getName());
        menu.setPath(reqParam.getPath());
        menu.setMenuType(reqParam.getMenuType());
        menu.setPermissionCode(reqParam.getPermissionCode());
        menu.setSortOrder(reqParam.getSortOrder() == null ? 0 : reqParam.getSortOrder());
        menu.setVisible(reqParam.getVisible() == null ? Boolean.TRUE : reqParam.getVisible());
        return RespInfo.created(toMenuDTO(sysMenuManager.save(menu)));
    }

    @Override
    public RespInfo<MenuDTO> updateMenu(UpdateMenuReqParam reqParam) {
        SysMenu existedMenu = sysMenuManager.getById(reqParam.getId());
        ResultCode.MENU_NOT_FOUND.assertNotNull(existedMenu);

        existedMenu.setParentId(reqParam.getParentId());
        existedMenu.setName(reqParam.getName());
        existedMenu.setPath(reqParam.getPath());
        existedMenu.setMenuType(reqParam.getMenuType());
        existedMenu.setPermissionCode(reqParam.getPermissionCode());
        existedMenu.setSortOrder(reqParam.getSortOrder() == null ? 0 : reqParam.getSortOrder());
        existedMenu.setVisible(reqParam.getVisible() == null ? Boolean.TRUE : reqParam.getVisible());
        return RespInfo.success(toMenuDTO(sysMenuManager.save(existedMenu)));
    }

    @Override
    public RespInfo<Void> deleteMenu(DeleteMenuReqParam reqParam) {
        Long menuId = reqParam.getMenuId();
        SysMenu menu = sysMenuManager.getById(menuId);
        ResultCode.MENU_NOT_FOUND.assertNotNull(menu);
        ResultCode.CAN_NOT_DELETE_MENU_BECAUSE_HAS_CHILDREN.assertIsFalse(sysMenuManager.existsChildren(menuId));
        ResultCode.MENU_IN_USE.assertIsFalse(sysRoleMenuManager.existsByMenuId(menuId));
        sysMenuManager.deleteById(menuId);
        return RespInfo.success();
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

    private MenuDTO toMenuDTO(SysMenu menu) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menu.getId());
        menuDTO.setParentId(menu.getParentId());
        menuDTO.setName(menu.getName());
        menuDTO.setPath(menu.getPath());
        menuDTO.setMenuType(menu.getMenuType());
        menuDTO.setPermissionCode(menu.getPermissionCode());
        menuDTO.setSortOrder(menu.getSortOrder());
        menuDTO.setVisible(menu.getVisible());
        menuDTO.setCreateTime(menu.getCreateTime());
        menuDTO.setUpdateTime(menu.getUpdateTime());
        return menuDTO;
    }
}
