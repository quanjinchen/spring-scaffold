package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfo;
import dn.spring.scaffold.system.entity.SysRole;

import java.util.List;

public interface RoleService {

    PageData<SysRole> page(PageReqParam reqParam);

    RespInfo<PageData<SysRole>> pageResp(PageReqParam reqParam);

    SysRole detail(Long id);

    RespInfo<SysRole> detailResp(Long id);

    SysRole save(SysRole role);

    RespInfo<SysRole> saveResp(SysRole role);

    SysRole update(SysRole role);

    RespInfo<SysRole> updateResp(SysRole role);

    void delete(Long roleId);

    RespInfo<Void> deleteResp(Long roleId);

    RoleGrantInfo getGrantInfo(Long roleId, List<Long> menuIds);

    RespInfo<RoleGrantInfo> getGrantInfoResp(Long roleId, List<Long> menuIds);

    RoleGrantInfo grantMenus(GrantRoleMenusReqParam reqParam);

    RespInfo<RoleGrantInfo> grantMenusResp(GrantRoleMenusReqParam reqParam);
}
