package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfo;
import dn.spring.scaffold.system.entity.SysRole;

import java.util.List;

public interface RoleService {

    PageResult<SysRole> page(PageQuery pageQuery);

    SysRole detail(Long id);

    SysRole save(SysRole role);

    RoleGrantInfo getGrantInfo(Long roleId, List<Long> menuIds);

    RoleGrantInfo grantMenus(GrantRoleMenusReqParam reqParam);
}
