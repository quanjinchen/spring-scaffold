package dn.spring.scaffold.console.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateUserReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteUserReqParam;
import dn.spring.scaffold.console.pojo.req.GetUserByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListUserReqParam;
import dn.spring.scaffold.console.pojo.req.ResetUserPasswordReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateUserReqParam;
import dn.spring.scaffold.console.pojo.resp.UserDTO;
import dn.spring.scaffold.console.service.UserService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Operation(summary = "创建用户", description = "权限：system:user:add")
    @OperateLog(module = "user", action = "创建用户")
    @PostMapping("/create-user")
    @SaCheckPermission("system:user:add")
    public RespInfo<Void> createUser(@Valid @RequestBody CreateUserReqParam createUserReqParam) {
        return userService.createUser(createUserReqParam);
    }

    @Operation(summary = "更新用户", description = "权限：system:user:update")
    @OperateLog(module = "user", action = "更新用户")
    @PostMapping("/update-user")
    @SaCheckPermission("system:user:update")
    public RespInfo<Void> updateUser(@Valid @RequestBody UpdateUserReqParam updateUserReqParam) {
        return userService.updateUser(updateUserReqParam);
    }

    @Operation(summary = "删除用户", description = "权限：system:user:delete")
    @OperateLog(module = "user", action = "删除用户")
    @PostMapping("/delete-user")
    @SaCheckPermission("system:user:delete")
    public RespInfo<Void> deleteUser(@Valid @RequestBody DeleteUserReqParam deleteUserReqParam) {
        return userService.deleteUser(deleteUserReqParam);
    }

    @Operation(summary = "根据 ID 查询用户", description = "权限：system:user:query")
    @GetMapping("/get-user-by-id/{id}")
    @SaCheckPermission("system:user:query")
    public RespInfo<UserDTO> getUserById(@Parameter(description = "用户 ID") @PathVariable Long id) {
        GetUserByIdReqParam reqParam = new GetUserByIdReqParam();
        reqParam.setUserId(id);
        return userService.getUserById(reqParam);
    }

    @Operation(summary = "重置用户密码", description = "权限：system:user:resetPassword")
    @OperateLog(module = "user", action = "重置用户密码")
    @PostMapping("/reset-user-password")
    @SaCheckPermission("system:user:resetPassword")
    public RespInfo<Void> resetUserPassword(@Valid @RequestBody ResetUserPasswordReqParam resetUserPasswordReqParam) {
        return userService.resetUserPassword(resetUserPasswordReqParam);
    }

    @Operation(summary = "分页查询用户", description = "权限：system:user:query")
    @PostMapping("/list-user")
    @SaCheckPermission("system:user:query")
    public RespInfo<PageData<UserDTO>> listUser(@Valid @RequestBody ListUserReqParam reqParam) {
        return userService.listUser(reqParam);
    }
}
