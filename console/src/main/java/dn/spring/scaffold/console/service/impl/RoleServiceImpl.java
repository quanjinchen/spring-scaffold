package dn.spring.scaffold.console.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.converter.RoleConverter;
import dn.spring.scaffold.console.pojo.req.CreateRoleReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteRoleReqParam;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.req.ListRoleReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateRoleReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleDTO;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfo;
import dn.spring.scaffold.console.service.RoleService;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.entity.SysRoleMenu;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.manager.SysRoleMenuManager;
import dn.spring.scaffold.system.manager.SysUserRoleManager;
import dn.spring.scaffold.system.pojo.query.ListRoleQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private SysRoleManager sysRoleManager;
    @Resource
    private SysRoleMenuManager sysRoleMenuManager;
    @Resource
    private SysUserRoleManager sysUserRoleManager;

    @Override
    public RespInfo<PageData<RoleDTO>> listRole(ListRoleReqParam reqParam) {
        Integer pageNum = reqParam.getPageNum();
        Integer pageSize = reqParam.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        ListRoleQuery query = RoleConverter.INSTANCE.convert(reqParam);
        List<SysRole> roleList = sysRoleManager.listRoles(query);
        List<RoleDTO> roleDTOList = RoleConverter.INSTANCE.convert(roleList);

        PageData<RoleDTO> pageData = new PageData<RoleDTO>();
        pageData.setTotal(new PageInfo<SysRole>(roleList).getTotal());
        pageData.setRecords(roleDTOList);
        pageData.setPageNum(pageNum);
        pageData.setPageSize(pageSize);
        return RespInfo.success(pageData);
    }

    @Override
    public RespInfo<RoleDTO> getRoleById(Long roleId) {
        return RespInfo.success(RoleConverter.INSTANCE.convert(loadRoleById(roleId)));
    }

    @Override
    public RespInfo<RoleDTO> saveRole(CreateRoleReqParam reqParam) {
        SysRole role = RoleConverter.INSTANCE.convert(reqParam);
        if (role.getStatus() == null) {
            role.setStatus(1);
        }

        validateBeforeSave(role, null);
        SysRole savedRole = sysRoleManager.save(role);
        return RespInfo.created(RoleConverter.INSTANCE.convert(savedRole));
    }

    @Override
    public RespInfo<RoleDTO> updateRole(UpdateRoleReqParam reqParam) {
        SysRole existedRole = loadRoleById(reqParam.getId());
        SysRole updateRole = RoleConverter.INSTANCE.convert(reqParam);
        updateRole.setDataScope(reqParam.getDataScope() != null ? reqParam.getDataScope() : existedRole.getDataScope());
        updateRole.setStatus(reqParam.getStatus() != null ? reqParam.getStatus() : existedRole.getStatus());
        updateRole.setRemark(reqParam.getRemark() != null ? reqParam.getRemark() : existedRole.getRemark());

        validateBeforeSave(updateRole, existedRole.getId());
        SysRole savedRole = sysRoleManager.save(updateRole);
        return RespInfo.success(RoleConverter.INSTANCE.convert(savedRole));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespInfo<Void> deleteRole(DeleteRoleReqParam reqParam) {
        Long roleId = reqParam.getRoleId();
        loadRoleById(roleId);
        ResultCode.ROLE_IN_USE.assertIsFalse(sysUserRoleManager.existsByRoleId(roleId));
        sysRoleMenuManager.deleteByRoleId(roleId);
        sysRoleManager.deleteById(roleId);
        return RespInfo.success();
    }

    @Override
    public RoleGrantInfo getGrantInfo(Long roleId) {
        SysRole role = loadRoleById(roleId);

        RoleGrantInfo grantInfo = new RoleGrantInfo();
        grantInfo.setRoleId(role.getId());
        grantInfo.setRoleCode(role.getCode());
        grantInfo.setRoleName(role.getName());
        grantInfo.setMenuIds(listRoleMenuIds(roleId));
        return grantInfo;
    }

    @Override
    public RespInfo<RoleGrantInfo> getRoleGrantInfo(Long roleId) {
        return RespInfo.success(getGrantInfo(roleId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespInfo<RoleGrantInfo> grantRoleMenus(GrantRoleMenusReqParam reqParam) {
        loadRoleById(reqParam.getRoleId());
        sysRoleMenuManager.replaceRoleMenus(reqParam.getRoleId(), reqParam.getMenuIds());
        return RespInfo.success(getGrantInfo(reqParam.getRoleId()));
    }

    private SysRole loadRoleById(Long roleId) {
        SysRole role = sysRoleManager.getById(roleId);
        ResultCode.ROLE_NOT_FOUND.assertNotNull(role);
        return role;
    }

    private void validateBeforeSave(SysRole role, Long excludeRoleId) {
        ResultCode.BAD_REQUEST.assertIsTrue(StringUtils.hasText(role.getCode()), "角色编码不能为空");
        ResultCode.BAD_REQUEST.assertIsTrue(StringUtils.hasText(role.getName()), "角色名称不能为空");

        SysRole sameCodeRole = sysRoleManager.getByCode(role.getCode());
        ResultCode.BAD_REQUEST.assertIsFalse(existsOtherRole(sameCodeRole, excludeRoleId), "角色编码已存在");

        SysRole sameNameRole = sysRoleManager.getByName(role.getName());
        ResultCode.BAD_REQUEST.assertIsFalse(existsOtherRole(sameNameRole, excludeRoleId), "角色名称已存在");
    }

    private boolean existsOtherRole(SysRole sameRole, Long excludeRoleId) {
        if (sameRole == null) {
            return false;
        }
        if (excludeRoleId == null) {
            return true;
        }
        return !Objects.equals(sameRole.getId(), excludeRoleId);
    }

    private List<Long> listRoleMenuIds(Long roleId) {
        List<SysRoleMenu> roleMenus = sysRoleMenuManager.listByRoleId(roleId);
        if (roleMenus.isEmpty()) {
            return Collections.emptyList();
        }
        return roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
    }
}
