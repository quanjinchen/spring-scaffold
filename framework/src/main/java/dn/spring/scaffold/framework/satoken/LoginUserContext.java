package dn.spring.scaffold.framework.satoken;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

@Component
public class LoginUserContext {

    public static final String LOGIN_USERNAME_SESSION_KEY = "loginUsername";

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
        Object username = StpUtil.getSession().get(LOGIN_USERNAME_SESSION_KEY);
        if (username == null) {
            return String.valueOf(loginUserId);
        }
        return String.valueOf(username);
    }
}
