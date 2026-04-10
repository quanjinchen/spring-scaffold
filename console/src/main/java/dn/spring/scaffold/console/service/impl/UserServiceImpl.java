package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.converter.UserConverter;
import dn.spring.scaffold.console.pojo.resp.UserDetailResp;
import dn.spring.scaffold.console.service.UserService;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.UserManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserManager userManager;

    @Override
    public PageResult<UserDetailResp> page(PageQuery pageQuery) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<User> page = userManager.page(pageQuery);
        return PageResult.of(UserConverter.INSTANCE.convert(page.getRecords()), page.getTotal(), pageQuery);
    }

    @Override
    public UserDetailResp detail(Long id) {
        User user = userManager.getById(id);
        return user == null ? new UserDetailResp() : UserConverter.INSTANCE.convert(user);
    }

    @Override
    public UserDetailResp save(UserDetailResp userDetailResp) {
        User user = UserConverter.INSTANCE.convert(userDetailResp);
        if (!StringUtils.hasText(user.getPassword())) {
            user.setPassword(null);
        }
        User latestUser = userManager.save(user);
        return latestUser == null ? new UserDetailResp() : UserConverter.INSTANCE.convert(latestUser);
    }

    @Override
    public String resetPassword(Long userId) {
        return "TEMP-RESET-PASSWORD-" + userId;
    }
}
