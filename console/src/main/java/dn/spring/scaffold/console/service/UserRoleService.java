package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantUserRolesReqParam;
import dn.spring.scaffold.console.pojo.resp.UserRoleInfo;

import java.util.List;

public interface UserRoleService {

    List<UserRoleInfo> listUserRoles(Long userId);

    RespInfo<List<UserRoleInfo>> listUserRolesResp(Long userId);

    List<UserRoleInfo> grantUserRoles(GrantUserRolesReqParam reqParam);

    RespInfo<List<UserRoleInfo>> grantUserRolesResp(GrantUserRolesReqParam reqParam);
}
