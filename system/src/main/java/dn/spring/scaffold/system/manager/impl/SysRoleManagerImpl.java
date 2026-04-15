package dn.spring.scaffold.system.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.mapper.SysRoleMapper;
import dn.spring.scaffold.system.pojo.query.ListRoleQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
                buildListRoleQueryWrapper(null)
        );
    }

    @Override
    public SysRole getById(Long roleId) {
        return sysRoleMapper.selectById(roleId);
    }

    @Override
    public SysRole getByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return null;
        }
        return sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getCode, code)
                .last("limit 1"));
    }

    @Override
    public SysRole getByName(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getName, name)
                .last("limit 1"));
    }

    @Override
    public List<SysRole> listByIds(Collection<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return sysRoleMapper.selectBatchIds(roleIds);
    }

    @Override
    public List<SysRole> listRoles(ListRoleQuery query) {
        return sysRoleMapper.selectList(buildListRoleQueryWrapper(query));
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

    private LambdaQueryWrapper<SysRole> buildListRoleQueryWrapper(ListRoleQuery query) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>()
                .orderByAsc(SysRole::getId);
        if (query == null) {
            return queryWrapper;
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                    .like(SysRole::getCode, query.getKeyword())
                    .or()
                    .like(SysRole::getName, query.getKeyword()));
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(SysRole::getStatus, query.getStatus());
        }
        return queryWrapper;
    }
}
