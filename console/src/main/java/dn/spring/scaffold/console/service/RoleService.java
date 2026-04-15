package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfo;
import dn.spring.scaffold.system.entity.SysRole;

import java.util.List;

public interface RoleService {

    PageResult<SysRole> page(PageQuery pageQuery);

    RespInfo<PageResult<SysRole>> pageResp(PageQuery pageQuery);

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
