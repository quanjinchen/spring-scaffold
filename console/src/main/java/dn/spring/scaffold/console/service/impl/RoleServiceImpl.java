package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfo;
import dn.spring.scaffold.console.service.RoleService;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.manager.SysRoleMenuManager;
import dn.spring.scaffold.system.manager.SysUserRoleManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private SysRoleManager sysRoleManager;
    @Resource
    private SysRoleMenuManager sysRoleMenuManager;
    @Resource
    private SysUserRoleManager sysUserRoleManager;

    @Override
    public PageResult<SysRole> page(PageQuery pageQuery) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<SysRole> page = sysRoleManager.page(pageQuery);
        return PageResult.of(page.getRecords(), page.getTotal(), pageQuery);
    }

    @Override
    public RespInfo<PageResult<SysRole>> pageResp(PageQuery pageQuery) {
        return RespInfo.success(page(pageQuery));
    }

    @Override
    public SysRole detail(Long id) {
        SysRole role = sysRoleManager.getById(id);
        return role == null ? new SysRole() : role;
    }

    @Override
    public RespInfo<SysRole> detailResp(Long id) {
        return RespInfo.success(detail(id));
    }

    @Override
    public SysRole save(SysRole role) {
        SysRole latestRole = sysRoleManager.save(role);
        return latestRole == null ? new SysRole() : latestRole;
    }

    @Override
    public RespInfo<SysRole> saveResp(SysRole role) {
        return RespInfo.created(save(role));
    }

    @Override
    public SysRole update(SysRole role) {
        return save(role);
    }

    @Override
    public RespInfo<SysRole> updateResp(SysRole role) {
        return RespInfo.success(update(role));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long roleId) {
        SysRole role = sysRoleManager.getById(roleId);
        ResultCode.ROLE_NOT_FOUND.assertNotNull(role);
        ResultCode.ROLE_IN_USE.assertIsFalse(sysUserRoleManager.existsByRoleId(roleId));
        sysRoleMenuManager.deleteByRoleId(roleId);
        sysRoleManager.deleteById(roleId);
    }

    @Override
    public RespInfo<Void> deleteResp(Long roleId) {
        delete(roleId);
        return RespInfo.success();
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
    public RespInfo<RoleGrantInfo> getGrantInfoResp(Long roleId, List<Long> menuIds) {
        return RespInfo.success(getGrantInfo(roleId, menuIds));
    }

    @Override
    public RoleGrantInfo grantMenus(GrantRoleMenusReqParam reqParam) {
        sysRoleMenuManager.replaceRoleMenus(reqParam.getRoleId(), reqParam.getMenuIds());
        List<Long> menuIds = reqParam.getMenuIds() == null ? Collections.<Long>emptyList() : reqParam.getMenuIds();
        return getGrantInfo(reqParam.getRoleId(), menuIds);
    }

    @Override
    public RespInfo<RoleGrantInfo> grantMenusResp(GrantRoleMenusReqParam reqParam) {
        return RespInfo.success(grantMenus(reqParam));
    }
}
