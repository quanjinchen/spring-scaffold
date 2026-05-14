package dn.spring.scaffold.console.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateRoleReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteRoleReqParam;
import dn.spring.scaffold.console.pojo.req.GrantRoleMenusReqParam;
import dn.spring.scaffold.console.pojo.req.GrantRoleUsersReqParam;
import dn.spring.scaffold.console.pojo.req.GetRoleUserListReqParam;
import dn.spring.scaffold.console.pojo.req.ListRoleAssignableUsersReqParam;
import dn.spring.scaffold.console.pojo.req.ListRoleReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateRoleReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleDTO;
import dn.spring.scaffold.console.pojo.resp.RoleGrantInfoDTO;
import dn.spring.scaffold.console.pojo.resp.RoleUserInfo;
import dn.spring.scaffold.console.pojo.resp.UserDTO;
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
import java.util.List;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Operation(summary = "分页查询角色", description = "权限：system:role:query")
    @PostMapping("/list-role")
    @SaCheckPermission("system:role:query")
    public RespInfo<PageData<RoleDTO>> listRole(@Valid @RequestBody ListRoleReqParam reqParam) {
        return roleService.listRole(reqParam);
    }

    @Operation(summary = "根据 ID 查询角色详情", description = "权限：system:role:query")
    @GetMapping("/get-role-by-id/{id}")
    @SaCheckPermission("system:role:query")
    public RespInfo<RoleDTO> getRoleById(@Parameter(description = "角色 ID") @PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @Operation(summary = "创建角色", description = "权限：system:role:update")
    @PostMapping("/create-role")
    @OperateLog(module = "角色管理", action = "创建角色")
    @SaCheckPermission("system:role:update")
    public RespInfo<RoleDTO> createRole(@Valid @RequestBody CreateRoleReqParam reqParam) {
        return roleService.createRole(reqParam);
    }

    @Operation(summary = "更新角色", description = "权限：system:role:update")
    @PostMapping("/update-role")
    @OperateLog(module = "角色管理", action = "更新角色")
    @SaCheckPermission("system:role:update")
    public RespInfo<RoleDTO> updateRole(@Valid @RequestBody UpdateRoleReqParam reqParam) {
        return roleService.updateRole(reqParam);
    }

    @Operation(summary = "删除角色", description = "权限：system:role:delete")
    @PostMapping("/delete-role")
    @OperateLog(module = "角色管理", action = "删除角色")
    @SaCheckPermission("system:role:delete")
    public RespInfo<Void> deleteRole(@Valid @RequestBody DeleteRoleReqParam reqParam) {
        return roleService.deleteRole(reqParam);
    }

    @Operation(summary = "查询角色授权信息", description = "权限：system:role:query")
    @GetMapping("/get-role-grant-info-by-role-id/{id}")
    @SaCheckPermission("system:role:query")
    public RespInfo<RoleGrantInfoDTO> getRoleGrantInfoByRoleId(@Parameter(description = "角色 ID") @PathVariable Long id) {
        return roleService.getRoleGrantInfoByRoleId(id);
    }

    @Operation(summary = "分配角色菜单", description = "权限：system:role:update")
    @PostMapping("/grant-role-menus")
    @OperateLog(module = "角色管理", action = "分配角色菜单")
    @SaCheckPermission("system:role:update")
    public RespInfo<RoleGrantInfoDTO> grantRoleMenus(@Valid @RequestBody GrantRoleMenusReqParam reqParam) {
        return roleService.grantRoleMenus(reqParam);
    }

    @Operation(summary = "查询角色可分配用户列表", description = "权限：system:role:query")
    @PostMapping("/list-role-assignable-users")
    @SaCheckPermission("system:role:query")
    public RespInfo<PageData<UserDTO>> listRoleAssignableUsers(@Valid @RequestBody ListRoleAssignableUsersReqParam reqParam) {
        return roleService.listRoleAssignableUsers(reqParam);
    }

    @Operation(summary = "查询角色关联用户列表", description = "权限：system:role:query")
    @PostMapping("/list-role-users")
    @SaCheckPermission("system:role:query")
    public RespInfo<List<RoleUserInfo>> listRoleUsers(@Valid @RequestBody GetRoleUserListReqParam reqParam) {
        return roleService.listRoleUsers(reqParam);
    }

    @Operation(summary = "分配角色关联用户", description = "权限：system:role:update")
    @PostMapping("/grant-role-users")
    @OperateLog(module = "角色管理", action = "分配关联用户")
    @SaCheckPermission("system:role:update")
    public RespInfo<List<RoleUserInfo>> grantRoleUsers(@Valid @RequestBody GrantRoleUsersReqParam reqParam) {
        return roleService.grantRoleUsers(reqParam);
    }
}
