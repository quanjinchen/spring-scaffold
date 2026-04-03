package dn.spring.scaffold.console.service;

import dn.spring.scaffold.console.pojo.req.GrantUserRolesReqParam;
import dn.spring.scaffold.console.pojo.resp.UserRoleInfo;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.entity.SysUserRole;
import dn.spring.scaffold.system.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserRoleService {

    private final RoleService roleService;

    private final SysUserRoleMapper sysUserRoleMapper;

    public UserRoleService(RoleService roleService, SysUserRoleMapper sysUserRoleMapper) {
        this.roleService = roleService;
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    public List<UserRoleInfo> listUserRoles(Long userId) {
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId));
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }
        List<UserRoleInfo> result = new ArrayList<UserRoleInfo>();
        for (SysUserRole userRole : userRoles) {
            SysRole role = roleService.detail(userRole.getRoleId());
            UserRoleInfo roleInfo = new UserRoleInfo();
            roleInfo.setUserId(userId);
            roleInfo.setRoleId(role.getId());
            roleInfo.setRoleCode(role.getCode());
            roleInfo.setRoleName(role.getName());
            result.add(roleInfo);
        }
        return result;
    }

    public List<UserRoleInfo> grantUserRoles(GrantUserRolesReqParam reqParam) {
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, reqParam.getUserId()));
        if (reqParam.getRoleIds() != null) {
            for (Long roleId : reqParam.getRoleIds()) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(reqParam.getUserId());
                userRole.setRoleId(roleId);
                sysUserRoleMapper.insert(userRole);
            }
        }
        return listUserRoles(reqParam.getUserId());
    }
}
