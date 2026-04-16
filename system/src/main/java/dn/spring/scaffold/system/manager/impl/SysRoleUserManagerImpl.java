package dn.spring.scaffold.system.manager.impl;

import dn.spring.scaffold.system.entity.SysRoleUser;
import dn.spring.scaffold.system.manager.SysRoleUserManager;
import dn.spring.scaffold.system.mapper.SysRoleUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Component
public class SysRoleUserManagerImpl implements SysRoleUserManager {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public List<SysRoleUser> listByUserId(Long userId) {
        List<SysRoleUser> roleUsers = sysRoleUserMapper.selectList(
                new LambdaQueryWrapper<SysRoleUser>().eq(SysRoleUser::getUserId, userId)
        );
        return roleUsers == null ? Collections.emptyList() : roleUsers;
    }

    @Override
    public boolean existsByRoleId(Long roleId) {
        if (roleId == null) {
            return false;
        }
        Long count = sysRoleUserMapper.selectCount(new LambdaQueryWrapper<SysRoleUser>().eq(SysRoleUser::getRoleId, roleId));
        return count != null && count > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replaceUserRoles(Long userId, List<Long> roleIds) {
        deleteByUserId(userId);
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }
        for (Long roleId : roleIds) {
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setUserId(userId);
            roleUser.setRoleId(roleId);
            sysRoleUserMapper.insert(roleUser);
        }
    }

    @Override
    public void deleteByUserId(Long userId) {
        sysRoleUserMapper.delete(new LambdaQueryWrapper<SysRoleUser>().eq(SysRoleUser::getUserId, userId));
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        sysRoleUserMapper.delete(new LambdaQueryWrapper<SysRoleUser>().eq(SysRoleUser::getRoleId, roleId));
    }
}
