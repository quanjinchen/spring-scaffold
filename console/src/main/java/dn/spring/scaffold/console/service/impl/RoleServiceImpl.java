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
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfoDTO;
import dn.spring.scaffold.console.service.RoleService;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.entity.SysRoleMenu;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.manager.SysRoleMenuManager;
import dn.spring.scaffold.system.manager.SysRoleUserManager;
import dn.spring.scaffold.system.pojo.query.ListRoleQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private SysRoleManager sysRoleManager;
    @Resource
    private SysRoleMenuManager sysRoleMenuManager;
    @Resource
    private SysRoleUserManager sysRoleUserManager;

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
        SysRole role = sysRoleManager.getById(roleId);
        ResultCode.ROLE_NOT_FOUND.assertNotNull(role);
        return RespInfo.success(RoleConverter.INSTANCE.convert(role));
    }

    @Override
    public RespInfo<RoleDTO> createRole(CreateRoleReqParam reqParam) {
        // 角色编码要求全局唯一，新增时直接按编码查库判重。
        SysRole sameCodeRole = sysRoleManager.getByCode(reqParam.getCode());
        ResultCode.ROLE_CODE_ALREADY_EXISTS.assertIsFalse(sameCodeRole != null);

        // 角色名称同样要求唯一，避免出现多个语义相同但编码不同的角色。
        SysRole sameNameRole = sysRoleManager.getByName(reqParam.getName());
        ResultCode.ROLE_NAME_ALREADY_EXISTS.assertIsFalse(sameNameRole != null);

        SysRole role = new SysRole();
        role.setCode(reqParam.getCode());
        role.setName(reqParam.getName());
        role.setStatus(reqParam.getStatus());
        role.setRemark(reqParam.getRemark());
        if (role.getStatus() == null) {
            // 未显式传状态时，默认按启用处理，和当前后台角色管理默认行为保持一致。
            role.setStatus(1);
        }

        SysRole savedRole = sysRoleManager.save(role);
        return RespInfo.created(RoleConverter.INSTANCE.convert(savedRole));
    }

    @Override
    public RespInfo<RoleDTO> updateRole(UpdateRoleReqParam reqParam) {
        SysRole existedRole = sysRoleManager.getById(reqParam.getId());
        ResultCode.ROLE_NOT_FOUND.assertNotNull(existedRole);

        // 修改角色编码时，需要排除当前正在编辑的角色本身，避免把自己误判成重复数据。
        SysRole sameCodeRole = sysRoleManager.getByCode(reqParam.getCode());
        ResultCode.ROLE_CODE_ALREADY_EXISTS.assertIsFalse(sameCodeRole != null && !sameCodeRole.getId().equals(existedRole.getId()));

        // 修改角色名称时同样要排除自己，只拦截真正的其他重复角色。
        SysRole sameNameRole = sysRoleManager.getByName(reqParam.getName());
        ResultCode.ROLE_NAME_ALREADY_EXISTS.assertIsFalse(sameNameRole != null && !sameNameRole.getId().equals(existedRole.getId()));

        SysRole updateRole = new SysRole();
        updateRole.setId(existedRole.getId());
        updateRole.setCode(reqParam.getCode());
        updateRole.setName(reqParam.getName());
        // 更新接口允许部分字段不传，不传时继续沿用数据库中的原值，避免把已有配置覆盖成空。
        updateRole.setStatus(reqParam.getStatus() != null ? reqParam.getStatus() : existedRole.getStatus());
        updateRole.setRemark(reqParam.getRemark() != null ? reqParam.getRemark() : existedRole.getRemark());

        SysRole savedRole = sysRoleManager.save(updateRole);
        return RespInfo.success(RoleConverter.INSTANCE.convert(savedRole));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespInfo<Void> deleteRole(DeleteRoleReqParam reqParam) {
        Long roleId = reqParam.getRoleId();
        SysRole role = sysRoleManager.getById(roleId);
        ResultCode.ROLE_NOT_FOUND.assertNotNull(role);
        // 已分配给用户的角色不允许直接删除，避免留下用户角色脏数据。
        ResultCode.ROLE_IN_USE.assertIsFalse(sysRoleUserManager.existsByRoleId(roleId));

        // 先删角色菜单关联，再删角色本体，保证关系数据和主数据保持一致。
        sysRoleMenuManager.deleteByRoleId(roleId);
        sysRoleManager.deleteById(roleId);
        return RespInfo.success();
    }

    @Override
    public RespInfo<RoleGrantInfoDTO> getRoleGrantInfoByRoleId(Long roleId) {
        SysRole role = sysRoleManager.getById(roleId);
        ResultCode.ROLE_NOT_FOUND.assertNotNull(role);

        RoleGrantInfoDTO grantInfo = new RoleGrantInfoDTO();
        grantInfo.setRoleId(role.getId());
        grantInfo.setRoleCode(role.getCode());
        grantInfo.setRoleName(role.getName());
        List<SysRoleMenu> roleMenus = sysRoleMenuManager.listByRoleId(roleId);
        if (roleMenus.isEmpty()) {
            grantInfo.setMenuIds(Collections.emptyList());
            return RespInfo.success(grantInfo);
        }
        List<Long> menuIds = new ArrayList<Long>(roleMenus.size());
        for (SysRoleMenu roleMenu : roleMenus) {
            menuIds.add(roleMenu.getMenuId());
        }
        grantInfo.setMenuIds(menuIds);
        return RespInfo.success(grantInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespInfo<RoleGrantInfoDTO> grantRoleMenus(GrantRoleMenusReqParam reqParam) {
        SysRole role = sysRoleManager.getById(reqParam.getRoleId());
        ResultCode.ROLE_NOT_FOUND.assertNotNull(role);

        // 前端未传菜单列表时，按清空授权处理，避免 manager 层出现空指针。
        List<Long> menuIds = reqParam.getMenuIds();
        sysRoleMenuManager.replaceRoleMenus(reqParam.getRoleId(), menuIds == null ? Collections.emptyList() : menuIds);

        RoleGrantInfoDTO grantInfo = new RoleGrantInfoDTO();
        grantInfo.setRoleId(role.getId());
        grantInfo.setRoleCode(role.getCode());
        grantInfo.setRoleName(role.getName());

        List<SysRoleMenu> roleMenus = sysRoleMenuManager.listByRoleId(reqParam.getRoleId());
        if (roleMenus.isEmpty()) {
            grantInfo.setMenuIds(Collections.emptyList());
            return RespInfo.success(grantInfo);
        }
        List<Long> grantedMenuIds = new ArrayList<Long>(roleMenus.size());
        for (SysRoleMenu roleMenu : roleMenus) {
            grantedMenuIds.add(roleMenu.getMenuId());
        }
        grantInfo.setMenuIds(grantedMenuIds);
        return RespInfo.success(grantInfo);
    }
}
