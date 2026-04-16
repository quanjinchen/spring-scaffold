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
import dn.spring.scaffold.framework.captcha.Captcha;
import dn.spring.scaffold.framework.captcha.CaptchaManager;
import dn.spring.scaffold.framework.satoken.LoginUserContext;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.entity.SysRoleMenu;
import dn.spring.scaffold.system.entity.SysRoleUser;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.SysRoleManager;
import dn.spring.scaffold.system.manager.SysRoleMenuManager;
import dn.spring.scaffold.system.manager.SysRoleUserManager;
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
    private MenuService menuService;
    @Resource
    private UserManager userManager;
    @Resource
    private SysRoleUserManager sysRoleUserManager;
    @Resource
    private SysRoleManager sysRoleManager;
    @Resource
    private SysRoleMenuManager sysRoleMenuManager;
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

        User admin = userManager.getByAccount(reqParam.getAccount());
        ResultCode.ACCOUNT_OR_PASSWORD_INVALID.assertNotNull(admin);
        boolean accountMatched = admin.getUsername().equals(reqParam.getAccount());
        ResultCode.ACCOUNT_OR_PASSWORD_INVALID.assertIsTrue(accountMatched);

        boolean passwordMatched = PASSWORD_ENCODER.matches(reqParam.getPassword(), admin.getPassword());
        ResultCode.ACCOUNT_OR_PASSWORD_INVALID.assertIsTrue(passwordMatched);

        StpUtil.login(admin.getId());
        StpUtil.getSession().set(LoginUserContext.LOGIN_USERNAME_SESSION_KEY, admin.getUsername());

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
        User admin = userManager.getById(loginUserId);
        ResultCode.NOT_LOGGED_IN.assertNotNull(admin);
        LoginData loginData = new LoginData();
        loginData.setAdminId(admin.getId());
        loginData.setUsername(admin.getUsername());
        loginData.setNickname(admin.getNickname());

        java.util.List<SysRoleUser> roleUsers = sysRoleUserManager.listByUserId(loginUserId);
        java.util.List<RoleGrantInfoDTO> roleGrantInfoDTOList = new java.util.ArrayList<RoleGrantInfoDTO>(roleUsers.size());
        java.util.List<String> roleCodes = new java.util.ArrayList<String>(roleUsers.size());
        for (SysRoleUser roleUser : roleUsers) {
            SysRole role = sysRoleManager.getById(roleUser.getRoleId());
            if (role == null) {
                continue;
            }

            RoleGrantInfoDTO roleGrantInfoDTO = new RoleGrantInfoDTO();
            roleGrantInfoDTO.setRoleId(role.getId());
            roleGrantInfoDTO.setRoleCode(role.getCode());
            roleGrantInfoDTO.setRoleName(role.getName());

            java.util.List<SysRoleMenu> roleMenus = sysRoleMenuManager.listByRoleId(role.getId());
            java.util.List<Long> menuIds = new java.util.ArrayList<Long>(roleMenus.size());
            for (SysRoleMenu roleMenu : roleMenus) {
                menuIds.add(roleMenu.getMenuId());
            }
            roleGrantInfoDTO.setMenuIds(menuIds);
            roleGrantInfoDTOList.add(roleGrantInfoDTO);
            roleCodes.add(role.getCode());
        }

        loginData.setRoleCodes(roleCodes);
        loginData.setRoles(roleGrantInfoDTOList);
        loginData.setMenus(menuService.listMenuTreeByUserId(loginUserId));
        return RespInfo.success(loginData);
    }

    @Override
    public RespInfo<Void> logout() {
        StpUtil.logout();
        return RespInfo.success();
    }
}
