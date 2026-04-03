package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.SysAdminLoginReqParam;
import dn.spring.scaffold.console.pojo.resp.CaptchaData;
import dn.spring.scaffold.console.pojo.resp.LoginData;
import dn.spring.scaffold.console.pojo.resp.SysAdminLoginData;
import dn.spring.scaffold.console.service.AdminAuthService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminAuthService adminAuthService;

    public AdminController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/get-captcha")
    public RespInfo<CaptchaData> getCaptcha() {
        return RespInfo.success(adminAuthService.getCaptcha());
    }

    @PostMapping("/login")
    @OperateLog(module = "admin", action = "login")
    public RespInfo<SysAdminLoginData> login(@Valid @RequestBody SysAdminLoginReqParam reqParam) {
        return RespInfo.success(adminAuthService.login(reqParam));
    }

    @PostMapping("/get-login-info")
    public RespInfo<LoginData> getLoginInfo() {
        return RespInfo.success(adminAuthService.getLoginInfo());
    }

    @PostMapping("/logout")
    @OperateLog(module = "admin", action = "logout")
    public RespInfo<Void> logout() {
        adminAuthService.logout();
        return RespInfo.success(null);
    }
}
