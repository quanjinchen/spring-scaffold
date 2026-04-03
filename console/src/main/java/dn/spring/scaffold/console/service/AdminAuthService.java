package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.exception.BizException;
import dn.spring.scaffold.common.entity.EncryptField;
import dn.spring.scaffold.console.pojo.req.SysAdminLoginReqParam;
import dn.spring.scaffold.console.pojo.resp.CaptchaData;
import dn.spring.scaffold.console.pojo.resp.LoginData;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfo;
import dn.spring.scaffold.console.pojo.resp.SysAdminLoginData;
import dn.spring.scaffold.framework.captcha.Captcha;
import dn.spring.scaffold.framework.captcha.CaptchaManager;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.mapper.UserMapper;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AdminAuthService {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private final RoleService roleService;

    private final MenuService menuService;

    private final UserRoleService userRoleService;

    private final UserMapper userMapper;

    private final CaptchaManager captchaManager;

    public AdminAuthService(RoleService roleService,
                            MenuService menuService,
                            UserRoleService userRoleService,
                            UserMapper userMapper,
                            CaptchaManager captchaManager) {
        this.roleService = roleService;
        this.menuService = menuService;
        this.userRoleService = userRoleService;
        this.userMapper = userMapper;
        this.captchaManager = captchaManager;
    }

    public CaptchaData getCaptcha() {
        Captcha captcha = captchaManager.generate();

        CaptchaData captchaData = new CaptchaData();
        captchaData.setUuid(captcha.getUuid());
        captchaData.setCode(captcha.getCode());
        captchaData.setImg(captcha.getImg());
        captchaData.setExpireSeconds(captcha.getExpireSeconds());
        captchaData.setTip("Use the image captcha returned by the service.");
        return captchaData;
    }

    public SysAdminLoginData login(SysAdminLoginReqParam reqParam) {
        if (!captchaManager.exists(reqParam.getUuid())) {
            throw new BizException(ResultCode.CAPTCHA_EXPIRED);
        }
        boolean verified = captchaManager.verify(reqParam.getUuid(), reqParam.getCode(), true);
        if (!verified) {
            throw new BizException(ResultCode.CAPTCHA_INVALID);
        }

        User admin = loadUserByAccount(reqParam.getAccount());
        boolean accountMatched = admin.getUsername().equals(reqParam.getAccount());
        if (!accountMatched) {
            throw new BizException(ResultCode.ACCOUNT_OR_PASSWORD_INVALID);
        }

        boolean passwordMatched = PASSWORD_ENCODER.matches(reqParam.getPassword(), admin.getPassword());
        if (!passwordMatched) {
            throw new BizException(ResultCode.ACCOUNT_OR_PASSWORD_INVALID);
        }

        StpUtil.login(admin.getId());

        SysAdminLoginData loginData = new SysAdminLoginData();
        loginData.setUserId(admin.getId());
        loginData.setUsername(admin.getUsername());
        loginData.setToken(StpUtil.getTokenValue());
        loginData.setSystemAdmin(true);
        return loginData;
    }

    public LoginData getLoginInfo() {
        if (!StpUtil.isLogin()) {
            throw new BizException(ResultCode.NOT_LOGGED_IN);
        }

        Long loginUserId = StpUtil.getLoginIdAsLong();
        User admin = loadUserById(loginUserId);
        LoginData loginData = new LoginData();
        loginData.setAdminId(admin.getId());
        loginData.setUsername(admin.getUsername());
        loginData.setNickname(admin.getNickname());
        loginData.setRoleCodes(userRoleCodes(loginUserId));
        loginData.setRoles(roleGrantInfos(loginUserId));
        loginData.setMenus(menuService.tree());
        return loginData;
    }

    public void logout() {
        StpUtil.logout();
    }

    private User loadUserByAccount(String account) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .and(wrapper -> wrapper
                        .eq(User::getUsername, account)
                        .or()
                        .eq(User::getPhone, new EncryptField(account)))
                .last("limit 1"));
        if (user == null) {
            throw new BizException(ResultCode.ACCOUNT_OR_PASSWORD_INVALID);
        }
        return user;
    }

    private User loadUserById(Long userId) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, userId)
                .last("limit 1"));
        if (user == null) {
            throw new BizException(ResultCode.NOT_LOGGED_IN);
        }
        return user;
    }

    private java.util.List<String> userRoleCodes(Long userId) {
        return roleGrantInfos(userId).stream().map(RoleGrantInfo::getRoleCode).collect(Collectors.toList());
    }

    private java.util.List<RoleGrantInfo> roleGrantInfos(Long userId) {
        java.util.List<dn.spring.scaffold.console.pojo.resp.UserRoleInfo> userRoles = userRoleService.listUserRoles(userId);
        if (userRoles.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        java.util.List<RoleGrantInfo> result = new java.util.ArrayList<RoleGrantInfo>();
        for (dn.spring.scaffold.console.pojo.resp.UserRoleInfo userRole : userRoles) {
            result.add(roleService.getGrantInfo(
                    userRole.getRoleId(),
                    menuService.listRoleMenuIds(userRole.getRoleId())
            ));
        }
        return result;
    }
}
