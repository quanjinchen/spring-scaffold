package dn.spring.scaffold.framework.satoken;

import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.UserManager;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LoginUserContext {

    @Resource
    private UserManager userManager;

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

        User user = userManager.getById(loginUserId);
        if (user == null) {
            return "anonymous";
        }
        return user.getUsername();
    }
}
