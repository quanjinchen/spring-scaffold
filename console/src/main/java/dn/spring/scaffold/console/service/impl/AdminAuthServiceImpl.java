package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.SysAdminLoginReqParam;
import dn.spring.scaffold.console.pojo.resp.CaptchaData;
import dn.spring.scaffold.console.pojo.resp.LoginData;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfoDTO;
import dn.spring.scaffold.console.pojo.resp.SysAdminLoginData;
import dn.spring.scaffold.console.service.AdminAuthService;
import dn.spring.scaffold.console.service.MenuService;
import dn.spring.scaffold.console.service.RoleService;
import dn.spring.scaffold.console.service.UserRoleService;
import dn.spring.scaffold.framework.captcha.Captcha;
import dn.spring.scaffold.framework.captcha.CaptchaManager;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.UserManager;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserManager userManager;
    @Resource
    private CaptchaManager captchaManager;

    @Override
    public RespInfo<CaptchaData> getCaptcha() {
        Captcha captcha = captchaManager.generate();

        CaptchaData captchaData = new CaptchaData();
        captchaData.setUuid(captcha.getUuid());
        captchaData.setCode(captcha.getCode());
        captchaData.setImg(captcha.getImg());
        captchaData.setExpireSeconds(captcha.getExpireSeconds());
        captchaData.setTip("Use the image captcha returned by the service.");
        return RespInfo.success(captchaData);
    }

    @Override
    public RespInfo<SysAdminLoginData> login(SysAdminLoginReqParam reqParam) {
        ResultCode.CAPTCHA_EXPIRED.assertIsTrue(captchaManager.exists(reqParam.getUuid()));
        boolean verified = captchaManager.verify(reqParam.getUuid(), reqParam.getCode(), true);
        ResultCode.CAPTCHA_INVALID.assertIsTrue(verified);

        User admin = loadUserByAccount(reqParam.getAccount());
        boolean accountMatched = admin.getUsername().equals(reqParam.getAccount());
        ResultCode.ACCOUNT_OR_PASSWORD_INVALID.assertIsTrue(accountMatched);

        boolean passwordMatched = PASSWORD_ENCODER.matches(reqParam.getPassword(), admin.getPassword());
        ResultCode.ACCOUNT_OR_PASSWORD_INVALID.assertIsTrue(passwordMatched);

        StpUtil.login(admin.getId());

        SysAdminLoginData loginData = new SysAdminLoginData();
        loginData.setUserId(admin.getId());
        loginData.setUsername(admin.getUsername());
        loginData.setToken(StpUtil.getTokenValue());
        loginData.setSystemAdmin(true);
        return RespInfo.success(loginData);
    }

    @Override
    public RespInfo<LoginData> getLoginInfo() {
        ResultCode.NOT_LOGGED_IN.assertIsTrue(StpUtil.isLogin());

        Long loginUserId = StpUtil.getLoginIdAsLong();
        User admin = loadUserById(loginUserId);
        LoginData loginData = new LoginData();
        loginData.setAdminId(admin.getId());
        loginData.setUsername(admin.getUsername());
        loginData.setNickname(admin.getNickname());
        loginData.setRoleCodes(userRoleCodes(loginUserId));
        loginData.setRoles(roleGrantInfos(loginUserId));
        loginData.setMenus(menuService.treeByUserId(loginUserId));
        return RespInfo.success(loginData);
    }

    @Override
    public RespInfo<Void> logout() {
        StpUtil.logout();
        return RespInfo.success();
    }

    private User loadUserByAccount(String account) {
        User user = userManager.getByAccount(account);
        ResultCode.ACCOUNT_OR_PASSWORD_INVALID.assertNotNull(user);
        return user;
    }

    private User loadUserById(Long userId) {
        User user = userManager.getById(userId);
        ResultCode.NOT_LOGGED_IN.assertNotNull(user);
        return user;
    }

    private java.util.List<String> userRoleCodes(Long userId) {
        return roleGrantInfos(userId).stream().map(RoleGrantInfoDTO::getRoleCode).collect(Collectors.toList());
    }

    private java.util.List<RoleGrantInfoDTO> roleGrantInfos(Long userId) {
        java.util.List<dn.spring.scaffold.console.pojo.resp.UserRoleInfo> userRoles = userRoleService.listUserRoles(userId);
        if (userRoles.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        java.util.List<RoleGrantInfoDTO> result = new java.util.ArrayList<RoleGrantInfoDTO>();
        for (dn.spring.scaffold.console.pojo.resp.UserRoleInfo userRole : userRoles) {
            result.add(roleService.getRoleGrantInfoByRoleId(userRole.getRoleId()).getData());
        }
        return result;
    }
}
