package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantUserRolesReqParam;
import dn.spring.scaffold.console.pojo.resp.UserDetailResp;
import dn.spring.scaffold.console.service.UserService;
import dn.spring.scaffold.console.service.UserRoleService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final UserRoleService userRoleService;

    public UserController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Operation(summary = "分页查询用户")
    @GetMapping("/page")
    @SaCheckPermission("system:user:query")
    public RespInfo<?> page(@Valid PageReqParam reqParam) {
        return userService.pageResp(reqParam);
    }

    @Operation(summary = "根据 ID 查询用户详情")
    @GetMapping("/{id}")
    @SaCheckPermission("system:user:query")
    public RespInfo<?> detail(@Parameter(description = "用户 ID") @PathVariable Long id) {
        return userService.detailResp(id);
    }

    @Operation(summary = "保存用户")
    @PostMapping("/save")
    @OperateLog(module = "user", action = "save")
    @SaCheckPermission("system:user:update")
    public RespInfo<?> save(@RequestBody UserDetailResp user) {
        return userService.saveResp(user);
    }

    @Operation(summary = "编辑用户")
    @PostMapping("/update")
    @OperateLog(module = "user", action = "update")
    @SaCheckPermission("system:user:update")
    public RespInfo<?> update(@RequestBody UserDetailResp user) {
        return userService.updateResp(user);
    }

    @Operation(summary = "删除用户")
    @PostMapping("/delete")
    @OperateLog(module = "user", action = "delete")
    @SaCheckPermission("system:user:delete")
    public RespInfo<?> delete(@Parameter(description = "用户 ID") @RequestParam Long userId) {
        return userService.deleteResp(userId);
    }

    @Operation(summary = "重置用户密码")
    @PostMapping("/reset-password")
    @OperateLog(module = "user", action = "reset-password")
    @SaCheckPermission("system:user:resetPassword")
    public RespInfo<?> resetPassword(@Parameter(description = "用户 ID") @RequestParam Long userId) {
        return userService.resetPasswordResp(userId);
    }

    @Operation(summary = "查询用户角色列表")
    @GetMapping("/{id}/roles")
    @SaCheckPermission("system:user:query")
    public RespInfo<?> listRoles(@Parameter(description = "用户 ID") @PathVariable Long id) {
        return userRoleService.listUserRolesResp(id);
    }

    @Operation(summary = "分配用户角色")
    @PostMapping("/grant-roles")
    @OperateLog(module = "user", action = "grant-roles")
    @SaCheckPermission("system:user:update")
    public RespInfo<?> grantRoles(@Valid @RequestBody GrantUserRolesReqParam reqParam) {
        return userRoleService.grantUserRolesResp(reqParam);
    }
}
