package dn.spring.scaffold.system.manager.impl;

import dn.spring.scaffold.common.entity.EncryptField;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.UserManager;
import dn.spring.scaffold.system.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

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
    public List<User> listByIds(Collection<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(userIds);
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
}
