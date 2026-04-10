package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfo;
import dn.spring.scaffold.console.service.RoleService;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.manager.SysRoleMenuManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private SysRoleManager sysRoleManager;
    @Resource
    private SysRoleMenuManager sysRoleMenuManager;

    @Override
    public PageResult<SysRole> page(PageQuery pageQuery) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<SysRole> page = sysRoleManager.page(pageQuery);
        return PageResult.of(page.getRecords(), page.getTotal(), pageQuery);
    }

    @Override
    public SysRole detail(Long id) {
        SysRole role = sysRoleManager.getById(id);
        return role == null ? new SysRole() : role;
    }

    @Override
    public SysRole save(SysRole role) {
        SysRole latestRole = sysRoleManager.save(role);
        return latestRole == null ? new SysRole() : latestRole;
    }

    @Override
    public RoleGrantInfo getGrantInfo(Long roleId, List<Long> menuIds) {
        SysRole role = detail(roleId);
        RoleGrantInfo grantInfo = new RoleGrantInfo();
        grantInfo.setRoleId(role.getId());
        grantInfo.setRoleCode(role.getCode());
        grantInfo.setRoleName(role.getName());
        grantInfo.setMenuIds(menuIds);
        return grantInfo;
    }

    @Override
    public RoleGrantInfo grantMenus(GrantRoleMenusReqParam reqParam) {
        sysRoleMenuManager.replaceRoleMenus(reqParam.getRoleId(), reqParam.getMenuIds());
        List<Long> menuIds = reqParam.getMenuIds() == null ? Collections.<Long>emptyList() : reqParam.getMenuIds();
        return getGrantInfo(reqParam.getRoleId(), menuIds);
    }
}
