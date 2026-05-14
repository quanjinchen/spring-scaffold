package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateRoleReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteRoleReqParam;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.req.GrantRoleUsersReqParam;
import dn.spring.scaffold.console.pojo.req.GetRoleUserListReqParam;
import dn.spring.scaffold.console.pojo.req.ListRoleAssignableUsersReqParam;
import dn.spring.scaffold.console.pojo.req.ListRoleReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateRoleReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleDTO;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfoDTO;
import dn.spring.scaffold.console.pojo.resp.RoleUserInfo;
import dn.spring.scaffold.console.pojo.resp.UserDTO;

import java.util.List;

public interface RoleService {

    RespInfo<PageData<RoleDTO>> listRole(ListRoleReqParam reqParam);

    RespInfo<RoleDTO> getRoleById(Long roleId);

    RespInfo<RoleDTO> createRole(CreateRoleReqParam reqParam);

    RespInfo<RoleDTO> updateRole(UpdateRoleReqParam reqParam);

    RespInfo<Void> deleteRole(DeleteRoleReqParam reqParam);

    RespInfo<RoleGrantInfoDTO> getRoleGrantInfoByRoleId(Long roleId);

    RespInfo<RoleGrantInfoDTO> grantRoleMenus(GrantRoleMenusReqParam reqParam);

    RespInfo<PageData<UserDTO>> listRoleAssignableUsers(ListRoleAssignableUsersReqParam reqParam);

    RespInfo<List<RoleUserInfo>> listRoleUsers(GetRoleUserListReqParam reqParam);

    RespInfo<List<RoleUserInfo>> grantRoleUsers(GrantRoleUsersReqParam reqParam);
}
