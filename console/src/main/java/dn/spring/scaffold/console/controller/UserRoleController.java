package dn.spring.scaffold.console.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantUserRolesReqParam;
import dn.spring.scaffold.console.service.UserRoleService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name = "用户角色管理")
@RestController
@RequestMapping("/user-role")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    @Operation(summary = "查询用户角色列表")
    @GetMapping("/roles/{userId}")
    @SaCheckPermission("system:user:query")
    public RespInfo<?> listUserRoles(@PathVariable Long userId) {
        return userRoleService.listUserRolesResp(userId);
    }

    @Operation(summary = "分配用户角色")
    @PostMapping("/grant-user-roles")
    @OperateLog(module = "userRole", action = "分配用户角色")
    @SaCheckPermission("system:user:update")
    public RespInfo<?> grantUserRoles(@Valid @RequestBody GrantUserRolesReqParam reqParam) {
        return userRoleService.grantUserRolesResp(reqParam);
    }
}
