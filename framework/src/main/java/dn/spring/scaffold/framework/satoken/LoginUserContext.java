package dn.spring.scaffold.framework.satoken;

import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.mapper.UserMapper;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;

@Component
public class LoginUserContext {

    private final UserMapper userMapper;

    public LoginUserContext(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Long getLoginUserId() {
        if (!StpUtil.isLogin()) {
            return null;
        }
        return StpUtil.getLoginIdAsLong();
    }

    public String getLoginUsername() {
        Long loginUserId = getLoginUserId();
        if (loginUserId == null) {
            return "anonymous";
        }

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, loginUserId)
                .last("limit 1"));
        if (user == null) {
            return "anonymous";
        }
        return user.getUsername();
    }
}
