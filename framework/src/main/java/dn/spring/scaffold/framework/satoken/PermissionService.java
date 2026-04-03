package dn.spring.scaffold.framework.satoken;

import dn.spring.scaffold.system.entity.SysMenu;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.entity.SysRoleMenu;
import dn.spring.scaffold.system.entity.SysUserRole;
import dn.spring.scaffold.system.mapper.SysMenuMapper;
import dn.spring.scaffold.system.mapper.SysRoleMapper;
import dn.spring.scaffold.system.mapper.SysRoleMenuMapper;
import dn.spring.scaffold.system.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionService {

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysRoleMapper sysRoleMapper;

    private final SysRoleMenuMapper sysRoleMenuMapper;

    private final SysMenuMapper sysMenuMapper;

    public PermissionService(SysUserRoleMapper sysUserRoleMapper,
                             SysRoleMapper sysRoleMapper,
                             SysRoleMenuMapper sysRoleMenuMapper,
                             SysMenuMapper sysMenuMapper) {
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysRoleMenuMapper = sysRoleMenuMapper;
        this.sysMenuMapper = sysMenuMapper;
    }

    public List<String> getPermissionList(Long userId) {
        Set<String> permissions = new LinkedHashSet<String>();
        List<Long> roleIds = listUserRoleIds(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<String>();
        }

        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>()
                .in(SysRoleMenu::getRoleId, roleIds));
        if (CollectionUtils.isEmpty(roleMenus)) {
            return new ArrayList<String>();
        }

        Set<Long> menuIds = new LinkedHashSet<Long>();
        for (SysRoleMenu roleMenu : roleMenus) {
            menuIds.add(roleMenu.getMenuId());
        }

        List<SysMenu> menus = sysMenuMapper.selectBatchIds(menuIds);
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
        List<SysRole> roles = sysRoleMapper.selectBatchIds(roleIds);
        List<String> result = new ArrayList<String>(roles.size());
        for (SysRole role : roles) {
            if (StringUtils.hasText(role.getCode())) {
                result.add(role.getCode());
            }
        }
        return result;
    }

    private List<Long> listUserRoleIds(Long userId) {
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId));
        List<Long> roleIds = new ArrayList<Long>(userRoles.size());
        for (SysUserRole userRole : userRoles) {
            roleIds.add(userRole.getRoleId());
        }
        return roleIds;
    }
}
