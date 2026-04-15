package dn.spring.scaffold.system.manager.impl;

import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.mapper.SysRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class SysRoleManagerImpl implements SysRoleManager {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public Page<SysRole> page(PageReqParam reqParam) {
        return sysRoleMapper.selectPage(
                new Page<SysRole>(reqParam.getPageNum(), reqParam.getPageSize()),
                new LambdaQueryWrapper<SysRole>().orderByAsc(SysRole::getId)
        );
    }

    @Override
    public SysRole getById(Long roleId) {
        return sysRoleMapper.selectById(roleId);
    }

    @Override
    public List<SysRole> listByIds(Collection<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return sysRoleMapper.selectBatchIds(roleIds);
    }

    @Override
    public SysRole save(SysRole role) {
        if (role.getId() == null) {
            sysRoleMapper.insert(role);
            return role;
        }
        sysRoleMapper.updateById(role);
        return sysRoleMapper.selectById(role.getId());
    }

    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }
}
