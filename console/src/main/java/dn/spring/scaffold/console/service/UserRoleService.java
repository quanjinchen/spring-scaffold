package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GetUserRoleListReqParam;
import dn.spring.scaffold.console.pojo.req.GrantUserRolesReqParam;
import dn.spring.scaffold.console.pojo.resp.UserRoleInfo;

import java.util.List;

public interface UserRoleService {

    RespInfo<List<UserRoleInfo>> listUserRole(GetUserRoleListReqParam reqParam);

    RespInfo<List<UserRoleInfo>> grantUserRoles(GrantUserRolesReqParam reqParam);
}
