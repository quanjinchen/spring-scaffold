package dn.spring.scaffold.console.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateRoleReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteRoleReqParam;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.req.ListRoleReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateRoleReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleDTO;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfo;
import dn.spring.scaffold.console.service.RoleService;
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

@Tag(name = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Operation(summary = "分页查询角色")
    @PostMapping("/page")
    @SaCheckPermission("system:role:query")
    public RespInfo<PageData<RoleDTO>> page(@Valid @RequestBody ListRoleReqParam reqParam) {
        return roleService.listRole(reqParam);
    }

    @Operation(summary = "根据 ID 查询角色详情")
    @GetMapping("/{id}")
    @SaCheckPermission("system:role:query")
    public RespInfo<RoleDTO> detail(@Parameter(description = "角色 ID") @PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @Operation(summary = "保存角色")
    @PostMapping("/save")
    @OperateLog(module = "role", action = "保存角色")
    @SaCheckPermission("system:role:update")
    public RespInfo<RoleDTO> save(@Valid @RequestBody CreateRoleReqParam reqParam) {
        return roleService.saveRole(reqParam);
    }

    @Operation(summary = "编辑角色")
    @PostMapping("/update")
    @OperateLog(module = "role", action = "编辑角色")
    @SaCheckPermission("system:role:update")
    public RespInfo<RoleDTO> update(@Valid @RequestBody UpdateRoleReqParam reqParam) {
        return roleService.updateRole(reqParam);
    }

    @Operation(summary = "删除角色")
    @PostMapping("/delete")
    @OperateLog(module = "role", action = "删除角色")
    @SaCheckPermission("system:role:delete")
    public RespInfo<Void> delete(@Valid @RequestBody DeleteRoleReqParam reqParam) {
        return roleService.deleteRole(reqParam);
    }

    @Operation(summary = "查询角色授权信息")
    @GetMapping("/{id}/grants")
    @SaCheckPermission("system:role:query")
    public RespInfo<RoleGrantInfo> grants(@Parameter(description = "角色 ID") @PathVariable Long id) {
        return roleService.getRoleGrantInfo(id);
    }

    @Operation(summary = "分配角色菜单")
    @PostMapping("/grant-menus")
    @OperateLog(module = "role", action = "分配角色菜单")
    @SaCheckPermission("system:role:update")
    public RespInfo<RoleGrantInfo> grantMenus(@Valid @RequestBody GrantRoleMenusReqParam reqParam) {
        return roleService.grantRoleMenus(reqParam);
    }
}
