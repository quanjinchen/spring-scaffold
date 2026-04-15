package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantUserRolesReqParam;
import dn.spring.scaffold.console.pojo.resp.UserRoleInfo;
import dn.spring.scaffold.console.service.UserRoleService;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.entity.SysUserRole;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.manager.SysUserRoleManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private SysUserRoleManager sysUserRoleManager;
    @Resource
    private SysRoleManager sysRoleManager;

    @Override
    public List<UserRoleInfo> listUserRoles(Long userId) {
        List<SysUserRole> userRoles = sysUserRoleManager.listByUserId(userId);
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }
        List<UserRoleInfo> result = new ArrayList<UserRoleInfo>();
        for (SysUserRole userRole : userRoles) {
            SysRole role = sysRoleManager.getById(userRole.getRoleId());
            if (role == null) {
                continue;
            }
            UserRoleInfo roleInfo = new UserRoleInfo();
            roleInfo.setUserId(userId);
            roleInfo.setRoleId(role.getId());
            roleInfo.setRoleCode(role.getCode());
            roleInfo.setRoleName(role.getName());
            result.add(roleInfo);
        }
        return result;
    }

    @Override
    public RespInfo<List<UserRoleInfo>> listUserRolesResp(Long userId) {
        return RespInfo.success(listUserRoles(userId));
    }

    @Override
    public List<UserRoleInfo> grantUserRoles(GrantUserRolesReqParam reqParam) {
        sysUserRoleManager.replaceUserRoles(reqParam.getUserId(), reqParam.getRoleIds());
        return listUserRoles(reqParam.getUserId());
    }

    @Override
    public RespInfo<List<UserRoleInfo>> grantUserRolesResp(GrantUserRolesReqParam reqParam) {
        return RespInfo.success(grantUserRoles(reqParam));
    }
}
