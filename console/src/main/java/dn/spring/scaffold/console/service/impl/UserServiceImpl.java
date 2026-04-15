package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.common.page.PageUtils;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.converter.UserConverter;
import dn.spring.scaffold.console.pojo.resp.UserDetailResp;
import dn.spring.scaffold.console.service.UserService;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.OrgUserManager;
import dn.spring.scaffold.system.manager.SysUserRoleManager;
import dn.spring.scaffold.system.manager.UserManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserManager userManager;
    @Resource
    private SysUserRoleManager sysUserRoleManager;
    @Resource
    private OrgUserManager orgUserManager;

    @Override
    public PageData<UserDetailResp> page(PageReqParam reqParam) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<User> page = userManager.page(reqParam);
        return PageUtils.of(page.getCurrent(), page.getSize(), page.getTotal(), UserConverter.INSTANCE.convert(page.getRecords()));
    }

    @Override
    public RespInfo<PageData<UserDetailResp>> pageResp(PageReqParam reqParam) {
        return RespInfo.success(page(reqParam));
    }

    @Override
    public UserDetailResp detail(Long id) {
        User user = userManager.getById(id);
        return user == null ? new UserDetailResp() : UserConverter.INSTANCE.convert(user);
    }

    @Override
    public RespInfo<UserDetailResp> detailResp(Long id) {
        return RespInfo.success(detail(id));
    }

    @Override
    public UserDetailResp save(UserDetailResp userDetailResp) {
        User user = UserConverter.INSTANCE.convert(userDetailResp);
        if (!StringUtils.hasText(user.getPassword())) {
            user.setPassword(null);
        }
        User latestUser = userManager.save(user);
        return latestUser == null ? new UserDetailResp() : UserConverter.INSTANCE.convert(latestUser);
    }

    @Override
    public RespInfo<UserDetailResp> saveResp(UserDetailResp userDetailResp) {
        return RespInfo.created(save(userDetailResp));
    }

    @Override
    public UserDetailResp update(UserDetailResp userDetailResp) {
        return save(userDetailResp);
    }

    @Override
    public RespInfo<UserDetailResp> updateResp(UserDetailResp userDetailResp) {
        return RespInfo.success(update(userDetailResp));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId) {
        User user = userManager.getById(userId);
        ResultCode.USER_NOT_FOUND.assertNotNull(user);
        sysUserRoleManager.deleteByUserId(userId);
        orgUserManager.deleteByUserId(userId);
        userManager.deleteById(userId);
    }

    @Override
    public RespInfo<Void> deleteResp(Long userId) {
        delete(userId);
        return RespInfo.success();
    }

    @Override
    public String resetPassword(Long userId) {
        return "TEMP-RESET-PASSWORD-" + userId;
    }

    @Override
    public RespInfo<String> resetPasswordResp(Long userId) {
        return RespInfo.success(resetPassword(userId));
    }
}
