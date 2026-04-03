package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.service.RoleService;
import dn.spring.scaffold.console.service.MenuService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import dn.spring.scaffold.system.entity.SysRole;
import cn.dev33.satoken.annotation.SaCheckPermission;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    private final MenuService menuService;

    public RoleController(RoleService roleService, MenuService menuService) {
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @GetMapping("/page")
    @SaCheckPermission("system:role:query")
    public RespInfo<?> page(PageQuery pageQuery) {
        return RespInfo.success(roleService.page(pageQuery));
    }

    @GetMapping("/{id}")
    @SaCheckPermission("system:role:query")
    public RespInfo<?> detail(@PathVariable Long id) {
        return RespInfo.success(roleService.detail(id));
    }

    @PostMapping("/save")
    @OperateLog(module = "role", action = "save")
    @SaCheckPermission("system:role:update")
    public RespInfo<?> save(@RequestBody SysRole role) {
        return RespInfo.created(roleService.save(role));
    }

    @GetMapping("/{id}/grants")
    @SaCheckPermission("system:role:query")
    public RespInfo<?> grants(@PathVariable Long id) {
        return RespInfo.success(roleService.getGrantInfo(
                id,
                menuService.listRoleMenuIds(id)
        ));
    }

    @PostMapping("/grant-menus")
    @OperateLog(module = "role", action = "grant-menus")
    @SaCheckPermission("system:role:update")
    public RespInfo<?> grantMenus(@Valid @RequestBody GrantRoleMenusReqParam reqParam) {
        return RespInfo.success(roleService.grantMenus(reqParam));
    }
}
