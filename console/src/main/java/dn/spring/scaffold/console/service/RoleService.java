package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfo;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.entity.SysRoleMenu;
import dn.spring.scaffold.system.mapper.SysRoleMapper;
import dn.spring.scaffold.system.mapper.SysRoleMenuMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleService {

    private final SysRoleMapper sysRoleMapper;

    private final SysRoleMenuMapper sysRoleMenuMapper;

    public RoleService(SysRoleMapper sysRoleMapper, SysRoleMenuMapper sysRoleMenuMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysRoleMenuMapper = sysRoleMenuMapper;
    }

    public PageResult<SysRole> page(PageQuery pageQuery) {
        Page<SysRole> page = sysRoleMapper.selectPage(new Page<SysRole>(pageQuery.getPageNum(), pageQuery.getPageSize()),
                new LambdaQueryWrapper<SysRole>().orderByAsc(SysRole::getId));
        return PageResult.of(page.getRecords(), page.getTotal(), pageQuery);
    }

    public SysRole detail(Long id) {
        SysRole role = sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getId, id)
                .last("limit 1"));
        return role == null ? new SysRole() : role;
    }

    public SysRole save(SysRole role) {
        if (role.getId() == null) {
            sysRoleMapper.insert(role);
            return role;
        }
        sysRoleMapper.updateById(role);
        return role;
    }

    public RoleGrantInfo getGrantInfo(Long roleId, List<Long> menuIds) {
        SysRole role = detail(roleId);
        RoleGrantInfo grantInfo = new RoleGrantInfo();
        grantInfo.setRoleId(role.getId());
        grantInfo.setRoleCode(role.getCode());
        grantInfo.setRoleName(role.getName());
        grantInfo.setMenuIds(menuIds);
        return grantInfo;
    }

    public RoleGrantInfo grantMenus(GrantRoleMenusReqParam reqParam) {
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, reqParam.getRoleId()));
        if (reqParam.getMenuIds() != null) {
            for (Long menuId : reqParam.getMenuIds()) {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(reqParam.getRoleId());
                roleMenu.setMenuId(menuId);
                sysRoleMenuMapper.insert(roleMenu);
            }
        }
        List<Long> menuIds = reqParam.getMenuIds() == null ? Collections.<Long>emptyList() : reqParam.getMenuIds();
        return getGrantInfo(reqParam.getRoleId(), menuIds);
    }
}
