package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.SysAdminLoginReqParam;
import dn.spring.scaffold.console.pojo.resp.CaptchaData;
import dn.spring.scaffold.console.pojo.resp.LoginData;
import dn.spring.scaffold.console.pojo.resp.SysAdminLoginData;
import dn.spring.scaffold.console.service.AdminAuthService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "后台认证")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminAuthService adminAuthService;

    public AdminController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @Operation(summary = "获取图形验证码")
    @PostMapping("/get-captcha")
    public RespInfo<CaptchaData> getCaptcha() {
        return adminAuthService.getCaptcha();
    }

    @Operation(summary = "账号密码登录")
    @PostMapping("/login")
    @OperateLog(module = "admin", action = "账号密码登录")
    public RespInfo<SysAdminLoginData> login(@Valid @RequestBody SysAdminLoginReqParam reqParam) {
        return adminAuthService.login(reqParam);
    }

    @Operation(summary = "获取当前登录信息")
    @PostMapping("/get-login-info")
    public RespInfo<LoginData> getLoginInfo() {
        return adminAuthService.getLoginInfo();
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    @OperateLog(module = "admin", action = "退出登录")
    public RespInfo<Void> logout() {
        return adminAuthService.logout();
    }
}
