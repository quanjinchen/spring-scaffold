package dn.spring.scaffold.console.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateMenuReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteMenuReqParam;
import dn.spring.scaffold.console.pojo.req.GetMenuByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListMenuReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateMenuReqParam;
import dn.spring.scaffold.console.pojo.resp.MenuDTO;
import dn.spring.scaffold.console.pojo.resp.MenuTreeNode;
import dn.spring.scaffold.console.service.MenuService;
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

import javax.validation.Valid;
import java.util.List;

@Tag(name = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "查询菜单树", description = "权限：system:menu:query")
    @PostMapping("/list-all-menu-tree")
    @SaCheckPermission("system:menu:query")
    public RespInfo<List<MenuTreeNode>> listAllMenuTree() {
        return menuService.listAllMenuTree();
    }

    @Operation(summary = "分页查询菜单", description = "权限：system:menu:query")
    @PostMapping("/list-menu")
    @SaCheckPermission("system:menu:query")
    public RespInfo<PageData<MenuDTO>> listMenu(@Valid @RequestBody ListMenuReqParam reqParam) {
        return menuService.listMenu(reqParam);
    }

    @Operation(summary = "根据 ID 查询菜单详情", description = "权限：system:menu:query")
    @GetMapping("/get-menu-by-id/{id}")
    @SaCheckPermission("system:menu:query")
    public RespInfo<MenuDTO> getMenuById(@Parameter(description = "菜单 ID") @PathVariable Long id) {
        GetMenuByIdReqParam reqParam = new GetMenuByIdReqParam();
        reqParam.setMenuId(id);
        return menuService.getMenuById(reqParam);
    }

    @Operation(summary = "创建菜单", description = "权限：system:menu:update")
    @PostMapping("/create-menu")
    @OperateLog(module = "菜单管理", action = "创建菜单")
    @SaCheckPermission("system:menu:update")
    public RespInfo<MenuDTO> createMenu(@Valid @RequestBody CreateMenuReqParam reqParam) {
        return menuService.createMenu(reqParam);
    }

    @Operation(summary = "更新菜单", description = "权限：system:menu:update")
    @PostMapping("/update-menu")
    @OperateLog(module = "菜单管理", action = "更新菜单")
    @SaCheckPermission("system:menu:update")
    public RespInfo<MenuDTO> updateMenu(@Valid @RequestBody UpdateMenuReqParam reqParam) {
        return menuService.updateMenu(reqParam);
    }

    @Operation(summary = "删除菜单", description = "权限：system:menu:delete")
    @PostMapping("/delete-menu")
    @OperateLog(module = "菜单管理", action = "删除菜单")
    @SaCheckPermission("system:menu:delete")
    public RespInfo<Void> deleteMenu(@Valid @RequestBody DeleteMenuReqParam reqParam) {
        return menuService.deleteMenu(reqParam);
    }
}
