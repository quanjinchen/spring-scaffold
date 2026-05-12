package dn.spring.scaffold.framework.satoken;

import cn.dev33.satoken.exception.NotWebContextException;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

@Component
public class LoginUserContext {

    public static final String LOGIN_USERNAME_SESSION_KEY = "loginUsername";

    public Long getLoginUserId() {
        try {
            if (!StpUtil.isLogin()) {
                return null;
            }
            return StpUtil.getLoginIdAsLong();
        } catch (NotWebContextException exception) {
            return null;
        }
    }

    public String getLoginUsername() {
        Long loginUserId = getLoginUserId();
        if (loginUserId == null) {
            return "anonymous";
        }
        try {
            Object username = StpUtil.getSession().get(LOGIN_USERNAME_SESSION_KEY);
            if (username == null) {
                return String.valueOf(loginUserId);
            }
            return String.valueOf(username);
        } catch (NotWebContextException exception) {
            return String.valueOf(loginUserId);
        }
    }
}
