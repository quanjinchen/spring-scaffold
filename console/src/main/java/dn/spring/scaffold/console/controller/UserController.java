package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantUserRolesReqParam;
import dn.spring.scaffold.console.pojo.resp.UserDetailResp;
import dn.spring.scaffold.console.service.UserService;
import dn.spring.scaffold.console.service.UserRoleService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import cn.dev33.satoken.annotation.SaCheckPermission;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final UserRoleService userRoleService;

    public UserController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/page")
    @SaCheckPermission("system:user:query")
    public RespInfo<?> page(PageQuery pageQuery) {
        return RespInfo.success(userService.page(pageQuery));
    }

    @GetMapping("/{id}")
    @SaCheckPermission("system:user:query")
    public RespInfo<?> detail(@PathVariable Long id) {
        return RespInfo.success(userService.detail(id));
    }

    @PostMapping("/save")
    @OperateLog(module = "user", action = "save")
    @SaCheckPermission("system:user:update")
    public RespInfo<?> save(@RequestBody UserDetailResp user) {
        return RespInfo.created(userService.save(user));
    }

    @PostMapping("/reset-password")
    @OperateLog(module = "user", action = "reset-password")
    @SaCheckPermission("system:user:resetPassword")
    public RespInfo<?> resetPassword(@RequestParam Long userId) {
        return RespInfo.success(userService.resetPassword(userId));
    }

    @GetMapping("/{id}/roles")
    @SaCheckPermission("system:user:query")
    public RespInfo<?> listRoles(@PathVariable Long id) {
        return RespInfo.success(userRoleService.listUserRoles(id));
    }

    @PostMapping("/grant-roles")
    @OperateLog(module = "user", action = "grant-roles")
    @SaCheckPermission("system:user:update")
    public RespInfo<?> grantRoles(@Valid @RequestBody GrantUserRolesReqParam reqParam) {
        return RespInfo.success(userRoleService.grantUserRoles(reqParam));
    }
}
