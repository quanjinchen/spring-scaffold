package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateRoleReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteRoleReqParam;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.req.ListRoleReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateRoleReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleDTO;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfoDTO;

public interface RoleService {

    RespInfo<PageData<RoleDTO>> listRole(ListRoleReqParam reqParam);

    RespInfo<RoleDTO> getRoleById(Long roleId);

    RespInfo<RoleDTO> createRole(CreateRoleReqParam reqParam);

    RespInfo<RoleDTO> updateRole(UpdateRoleReqParam reqParam);

    RespInfo<Void> deleteRole(DeleteRoleReqParam reqParam);

    RespInfo<RoleGrantInfoDTO> getRoleGrantInfoByRoleId(Long roleId);

    RespInfo<RoleGrantInfoDTO> grantRoleMenus(GrantRoleMenusReqParam reqParam);
}
