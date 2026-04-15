package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.entity.EncryptField;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.converter.UserConverter;
import dn.spring.scaffold.console.pojo.req.CreateUserReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteUserReqParam;
import dn.spring.scaffold.console.pojo.req.GetUserByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListUserReqParam;
import dn.spring.scaffold.console.pojo.req.ResetUserPasswordReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateUserReqParam;
import dn.spring.scaffold.console.pojo.resp.UserDTO;
import dn.spring.scaffold.console.service.UserService;
import dn.spring.scaffold.system.entity.OrgUser;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.OrgUserManager;
import dn.spring.scaffold.system.manager.SysUserRoleManager;
import dn.spring.scaffold.system.manager.UserManager;
import dn.spring.scaffold.system.pojo.query.ListUserQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private static final String DEFAULT_RESET_PASSWORD = "123456";

    @Resource
    private UserManager userManager;
    @Resource
    private SysUserRoleManager sysUserRoleManager;
    @Resource
    private OrgUserManager orgUserManager;

    @Override
    public RespInfo<Void> createUser(CreateUserReqParam createUserReqParam) {
        User user = new User();
        user.setUsername(createUserReqParam.getUsername());
        user.setNickname(createUserReqParam.getNickname());
        user.setEmail(createUserReqParam.getEmail());
        user.setPhone(StringUtils.hasText(createUserReqParam.getPhone()) ? new EncryptField(createUserReqParam.getPhone()) : null);
        user.setOrgId(createUserReqParam.getOrgId());
        user.setStatus(createUserReqParam.getStatus() == null ? 1 : createUserReqParam.getStatus());

        validateBeforeSave(user, null);

        String password = createUserReqParam.getPassword();
        if (!StringUtils.hasText(password)) {
            password = DEFAULT_RESET_PASSWORD;
        }
        user.setPassword(PASSWORD_ENCODER.encode(password));
        User savedUser = userManager.save(user);
        bindOrgUser(savedUser.getId(), savedUser.getOrgId());
        return RespInfo.success();
    }

    @Override
    public RespInfo<UserDTO> getUserById(GetUserByIdReqParam getUserByIdReqParam) {
        User user = userManager.getById(getUserByIdReqParam.getUserId());
        ResultCode.USER_NOT_FOUND.assertNotNull(user);
        return RespInfo.success(UserConverter.INSTANCE.convert(user));
    }

    @Override
    public RespInfo<PageData<UserDTO>> listUser(ListUserReqParam listUserReqParam) {
        Integer pageNum = listUserReqParam.getPageNum();
        Integer pageSize = listUserReqParam.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        ListUserQuery query = UserConverter.INSTANCE.convert(listUserReqParam);
        List<User> userList = userManager.listUsers(query);
        List<UserDTO> userDTOList = UserConverter.INSTANCE.convert(userList);

        PageData<UserDTO> pageData = new PageData<>();
        pageData.setTotal(new PageInfo<User>(userList).getTotal());
        pageData.setRecords(userDTOList);
        pageData.setPageNum(pageNum);
        pageData.setPageSize(pageSize);
        return RespInfo.success(pageData);
    }

    @Override
    public RespInfo<Void> updateUser(UpdateUserReqParam updateUserReqParam) {
        User existedUser = userManager.getById(updateUserReqParam.getId());
        ResultCode.USER_NOT_FOUND.assertNotNull(existedUser);

        User updateUser = new User();
        updateUser.setId(existedUser.getId());
        updateUser.setUsername(StringUtils.hasText(updateUserReqParam.getUsername()) ? updateUserReqParam.getUsername() : existedUser.getUsername());
        updateUser.setNickname(StringUtils.hasText(updateUserReqParam.getNickname()) ? updateUserReqParam.getNickname() : existedUser.getNickname());
        updateUser.setEmail(updateUserReqParam.getEmail() != null ? updateUserReqParam.getEmail() : existedUser.getEmail());
        updateUser.setPhone(updateUserReqParam.getPhone() != null ? new EncryptField(updateUserReqParam.getPhone()) : existedUser.getPhone());
        updateUser.setOrgId(updateUserReqParam.getOrgId() != null ? updateUserReqParam.getOrgId() : existedUser.getOrgId());
        updateUser.setStatus(updateUserReqParam.getStatus() != null ? updateUserReqParam.getStatus() : existedUser.getStatus());
        updateUser.setPassword(existedUser.getPassword());

        validateBeforeSave(updateUser, existedUser.getId());
        User savedUser = userManager.save(updateUser);
        bindOrgUser(savedUser.getId(), savedUser.getOrgId());
        return RespInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespInfo<Void> deleteUser(DeleteUserReqParam deleteUserReqParam) {
        Long userId = deleteUserReqParam.getUserId();
        User user = userManager.getById(userId);
        ResultCode.USER_NOT_FOUND.assertNotNull(user);
        sysUserRoleManager.deleteByUserId(userId);
        orgUserManager.deleteByUserId(userId);
        userManager.deleteById(userId);
        return RespInfo.success();
    }

    @Override
    public RespInfo<Void> resetUserPassword(ResetUserPasswordReqParam resetUserPasswordReqParam) {
        User user = userManager.getById(resetUserPasswordReqParam.getUserId());
        ResultCode.USER_NOT_FOUND.assertNotNull(user);
        user.setPassword(PASSWORD_ENCODER.encode(DEFAULT_RESET_PASSWORD));
        userManager.save(user);
        return RespInfo.success();
    }

    private void validateBeforeSave(User user, Long excludeUserId) {
        ResultCode.BAD_REQUEST.assertNotEmpty(user.getUsername(), "用户名不能为空");
        ResultCode.BAD_REQUEST.assertNotEmpty(user.getNickname(), "昵称不能为空");

        User sameUsernameUser = userManager.getByUsername(user.getUsername());
        ResultCode.BAD_REQUEST.assertIsFalse(existsOtherUser(sameUsernameUser, excludeUserId), "用户名已存在");

        if (StringUtils.hasText(user.getEmail())) {
            User sameEmailUser = userManager.getByEmail(user.getEmail());
            ResultCode.BAD_REQUEST.assertIsFalse(existsOtherUser(sameEmailUser, excludeUserId), "邮箱已存在");
        }

        String phone = user.getPhone() == null ? null : user.getPhone().getPlainText();
        if (StringUtils.hasText(phone)) {
            User samePhoneUser = userManager.getByPhone(phone);
            ResultCode.BAD_REQUEST.assertIsFalse(existsOtherUser(samePhoneUser, excludeUserId), "手机号已存在");
        }
    }

    private boolean existsOtherUser(User sameUser, Long excludeUserId) {
        if (sameUser == null) {
            return false;
        }
        if (excludeUserId == null) {
            return true;
        }
        return !Objects.equals(sameUser.getId(), excludeUserId);
    }

    private void bindOrgUser(Long userId, Long orgId) {
        orgUserManager.deleteByUserId(userId);
        if (orgId == null) {
            return;
        }
        OrgUser orgUser = new OrgUser();
        orgUser.setOrgId(orgId);
        orgUser.setUserId(userId);
        orgUserManager.save(orgUser);
    }
}
