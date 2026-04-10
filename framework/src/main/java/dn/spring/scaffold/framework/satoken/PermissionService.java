package dn.spring.scaffold.framework.satoken;

import dn.spring.scaffold.system.entity.SysMenu;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.entity.SysRoleMenu;
import dn.spring.scaffold.system.entity.SysUserRole;
import dn.spring.scaffold.system.manager.SysMenuManager;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.manager.SysRoleMenuManager;
import dn.spring.scaffold.system.manager.SysUserRoleManager;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionService {

    @Resource
    private SysUserRoleManager sysUserRoleManager;
    @Resource
    private SysRoleManager sysRoleManager;
    @Resource
    private SysRoleMenuManager sysRoleMenuManager;
    @Resource
    private SysMenuManager sysMenuManager;

    public List<String> getPermissionList(Long userId) {
        Set<String> permissions = new LinkedHashSet<String>();
        List<Long> roleIds = listUserRoleIds(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<String>();
        }

        List<SysRoleMenu> roleMenus = sysRoleMenuManager.listByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(roleMenus)) {
            return new ArrayList<String>();
        }

        Set<Long> menuIds = new LinkedHashSet<Long>();
        for (SysRoleMenu roleMenu : roleMenus) {
            menuIds.add(roleMenu.getMenuId());
        }

        List<SysMenu> menus = sysMenuManager.listByIds(menuIds);
        for (SysMenu menu : menus) {
            if (StringUtils.hasText(menu.getPermissionCode())) {
                permissions.add(menu.getPermissionCode());
            }
        }
        return new ArrayList<String>(permissions);
    }

    public List<String> getRoleCodeList(Long userId) {
        List<Long> roleIds = listUserRoleIds(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<String>();
        }
        List<SysRole> roles = sysRoleManager.listByIds(roleIds);
        List<String> result = new ArrayList<String>(roles.size());
        for (SysRole role : roles) {
            if (StringUtils.hasText(role.getCode())) {
                result.add(role.getCode());
            }
        }
        return result;
    }

    private List<Long> listUserRoleIds(Long userId) {
        List<SysUserRole> userRoles = sysUserRoleManager.listByUserId(userId);
        List<Long> roleIds = new ArrayList<Long>(userRoles.size());
        for (SysUserRole userRole : userRoles) {
            roleIds.add(userRole.getRoleId());
        }
        return roleIds;
    }
}
