package dn.spring.scaffold.console.service;

import dn.spring.scaffold.console.pojo.req.SysAdminLoginReqParam;
import dn.spring.scaffold.console.pojo.resp.CaptchaData;
import dn.spring.scaffold.console.pojo.resp.LoginData;
import dn.spring.scaffold.console.pojo.resp.SysAdminLoginData;

public interface AdminAuthService {

    CaptchaData getCaptcha();

    SysAdminLoginData login(SysAdminLoginReqParam reqParam);

    LoginData getLoginInfo();

    void logout();
}
