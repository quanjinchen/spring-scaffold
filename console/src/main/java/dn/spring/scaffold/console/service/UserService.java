package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.entity.EncryptField;
import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.converter.UserConverter;
import dn.spring.scaffold.console.pojo.resp.UserDetailResp;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PageResult<UserDetailResp> page(PageQuery pageQuery) {
        Page<User> page = userMapper.selectPage(new Page<User>(pageQuery.getPageNum(), pageQuery.getPageSize()),
                new LambdaQueryWrapper<User>().orderByAsc(User::getId));
        return PageResult.of(UserConverter.INSTANCE.convert(page.getRecords()), page.getTotal(), pageQuery);
    }

    public UserDetailResp detail(Long id) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, id)
                .last("limit 1"));
        return user == null ? new UserDetailResp() : UserConverter.INSTANCE.convert(user);
    }

    public UserDetailResp save(UserDetailResp userDetailResp) {
        User user = UserConverter.INSTANCE.convert(userDetailResp);
        if (!StringUtils.hasText(user.getPassword())) {
            user.setPassword(null);
        }
        if (user.getId() == null) {
            userMapper.insert(user);
            return UserConverter.INSTANCE.convert(user);
        }
        userMapper.updateById(user);
        User latestUser = userMapper.selectById(user.getId());
        return latestUser == null ? new UserDetailResp() : UserConverter.INSTANCE.convert(latestUser);
    }

    public String resetPassword(Long userId) {
        return "TEMP-RESET-PASSWORD-" + userId;
    }
}
