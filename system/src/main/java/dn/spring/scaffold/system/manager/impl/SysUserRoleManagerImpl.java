package dn.spring.scaffold.system.manager.impl;

import dn.spring.scaffold.system.entity.SysUserRole;
import dn.spring.scaffold.system.manager.SysUserRoleManager;
import dn.spring.scaffold.system.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Component
public class SysUserRoleManagerImpl implements SysUserRoleManager {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserRole> listByUserId(Long userId) {
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId)
        );
        return userRoles == null ? Collections.emptyList() : userRoles;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replaceUserRoles(Long userId, List<Long> roleIds) {
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }
        for (Long roleId : roleIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            sysUserRoleMapper.insert(userRole);
        }
    }
}
