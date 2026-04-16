package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GetUserRoleListReqParam;
import dn.spring.scaffold.console.pojo.req.GrantUserRolesReqParam;
import dn.spring.scaffold.console.pojo.resp.UserRoleInfo;
import dn.spring.scaffold.console.service.UserRoleService;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.entity.SysRoleUser;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.manager.SysRoleUserManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private SysRoleUserManager sysRoleUserManager;
    @Resource
    private SysRoleManager sysRoleManager;

    @Override
    public RespInfo<List<UserRoleInfo>> listUserRole(GetUserRoleListReqParam reqParam) {
        return RespInfo.success(buildUserRoleInfoList(reqParam.getUserId()));
    }

    @Override
    public RespInfo<List<UserRoleInfo>> grantUserRoles(GrantUserRolesReqParam reqParam) {
        sysRoleUserManager.replaceUserRoles(reqParam.getUserId(), reqParam.getRoleIds());
        return RespInfo.success(buildUserRoleInfoList(reqParam.getUserId()));
    }

    private List<UserRoleInfo> buildUserRoleInfoList(Long userId) {
        List<SysRoleUser> roleUsers = sysRoleUserManager.listByUserId(userId);
        if (roleUsers.isEmpty()) {
            return Collections.emptyList();
        }
        List<UserRoleInfo> result = new ArrayList<UserRoleInfo>();
        for (SysRoleUser roleUser : roleUsers) {
            SysRole role = sysRoleManager.getById(roleUser.getRoleId());
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
}
