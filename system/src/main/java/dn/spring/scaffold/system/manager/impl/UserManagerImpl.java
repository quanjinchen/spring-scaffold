package dn.spring.scaffold.system.manager.impl;

import dn.spring.scaffold.common.entity.EncryptField;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.UserManager;
import dn.spring.scaffold.system.mapper.UserMapper;
import dn.spring.scaffold.system.pojo.query.ListUserQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class UserManagerImpl implements UserManager {

    @Resource
    private UserMapper userMapper;

    @Override
    public Page<User> page(PageReqParam reqParam) {
        return userMapper.selectPage(
                new Page<User>(reqParam.getPageNum(), reqParam.getPageSize()),
                new LambdaQueryWrapper<User>().orderByAsc(User::getId)
        );
    }

    @Override
    public User getById(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User getByAccount(String account) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .and(wrapper -> wrapper
                        .eq(User::getUsername, account)
                        .or()
                        .eq(User::getPhone, new EncryptField(account)))
                .last("limit 1"));
    }

    @Override
    public User getByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .last("limit 1"));
    }

    @Override
    public User getByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email)
                .last("limit 1"));
    }

    @Override
    public User getByPhone(String phone) {
        if (!StringUtils.hasText(phone)) {
            return null;
        }
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, new EncryptField(phone))
                .last("limit 1"));
    }

    @Override
    public List<User> listByIds(Collection<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(userIds);
    }

    @Override
    public List<User> listUsers(ListUserQuery query) {
        return userMapper.selectList(buildListUserQueryWrapper(query));
    }

    @Override
    public Page<User> page(PageReqParam reqParam, ListUserQuery query) {
        return userMapper.selectPage(
                new Page<User>(reqParam.getPageNum(), reqParam.getPageSize()),
                buildListUserQueryWrapper(query)
        );
    }

    @Override
    public boolean existsByOrgId(Long orgId) {
        if (orgId == null) {
            return false;
        }
        Long count = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getOrgId, orgId));
        return count != null && count > 0;
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            userMapper.insert(user);
            return user;
        }
        userMapper.updateById(user);
        return userMapper.selectById(user.getId());
    }

    @Override
    public void deleteById(Long userId) {
        userMapper.deleteById(userId);
    }

    private LambdaQueryWrapper<User> buildListUserQueryWrapper(ListUserQuery query) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .orderByAsc(User::getId);
        if (query == null) {
            return queryWrapper;
        }
        if (StringUtils.hasText(query.getUsername())) {
            queryWrapper.like(User::getUsername, query.getUsername());
        }
        if (StringUtils.hasText(query.getFullName())) {
            queryWrapper.like(User::getFullName, query.getFullName());
        }
        if (StringUtils.hasText(query.getPhone())) {
            queryWrapper.eq(User::getPhone, new EncryptField(query.getPhone()));
        }
        if (StringUtils.hasText(query.getEmail())) {
            queryWrapper.like(User::getEmail, query.getEmail());
        }
        if (query.getOrgId() != null) {
            queryWrapper.eq(User::getOrgId, query.getOrgId());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(User::getStatus, query.getStatus());
        }
        return queryWrapper;
    }
}
