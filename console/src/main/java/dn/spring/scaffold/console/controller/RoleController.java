package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.service.RoleService;
import dn.spring.scaffold.console.service.MenuService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import dn.spring.scaffold.system.entity.SysRole;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    private final MenuService menuService;

    public RoleController(RoleService roleService, MenuService menuService) {
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @Operation(summary = "分页查询角色")
    @GetMapping("/page")
    @SaCheckPermission("system:role:query")
    public RespInfo<?> page(PageQuery pageQuery) {
        return RespInfo.success(roleService.page(pageQuery));
    }

    @Operation(summary = "根据 ID 查询角色详情")
    @GetMapping("/{id}")
    @SaCheckPermission("system:role:query")
    public RespInfo<?> detail(@Parameter(description = "角色 ID") @PathVariable Long id) {
        return RespInfo.success(roleService.detail(id));
    }

    @Operation(summary = "保存角色")
    @PostMapping("/save")
    @OperateLog(module = "role", action = "save")
    @SaCheckPermission("system:role:update")
    public RespInfo<?> save(@RequestBody SysRole role) {
        return RespInfo.created(roleService.save(role));
    }

    @Operation(summary = "查询角色授权信息")
    @GetMapping("/{id}/grants")
    @SaCheckPermission("system:role:query")
    public RespInfo<?> grants(@Parameter(description = "角色 ID") @PathVariable Long id) {
        return RespInfo.success(roleService.getGrantInfo(
                id,
                menuService.listRoleMenuIds(id)
        ));
    }

    @Operation(summary = "分配角色菜单")
    @PostMapping("/grant-menus")
    @OperateLog(module = "role", action = "grant-menus")
    @SaCheckPermission("system:role:update")
    public RespInfo<?> grantMenus(@Valid @RequestBody GrantRoleMenusReqParam reqParam) {
        return RespInfo.success(roleService.grantMenus(reqParam));
    }
}
