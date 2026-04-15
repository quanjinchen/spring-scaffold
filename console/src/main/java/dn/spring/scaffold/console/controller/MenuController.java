package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.service.MenuService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import dn.spring.scaffold.system.entity.SysMenu;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "查询菜单树")
    @GetMapping("/tree")
    @SaCheckPermission("system:menu:query")
    public RespInfo<?> tree() {
        return menuService.treeResp();
    }

    @Operation(summary = "分页查询菜单")
    @GetMapping("/page")
    @SaCheckPermission("system:menu:query")
    public RespInfo<?> page(PageQuery pageQuery) {
        return menuService.pageResp(pageQuery);
    }

    @Operation(summary = "根据 ID 查询菜单详情")
    @GetMapping("/{id}")
    @SaCheckPermission("system:menu:query")
    public RespInfo<?> detail(@Parameter(description = "菜单 ID") @PathVariable Long id) {
        return menuService.detailResp(id);
    }

    @Operation(summary = "保存菜单")
    @PostMapping("/save")
    @OperateLog(module = "menu", action = "save")
    @SaCheckPermission("system:menu:update")
    public RespInfo<?> save(@RequestBody SysMenu menu) {
        return menuService.saveResp(menu);
    }

    @Operation(summary = "编辑菜单")
    @PostMapping("/update")
    @OperateLog(module = "menu", action = "update")
    @SaCheckPermission("system:menu:update")
    public RespInfo<?> update(@RequestBody SysMenu menu) {
        return menuService.updateResp(menu);
    }

    @Operation(summary = "删除菜单")
    @PostMapping("/delete")
    @OperateLog(module = "menu", action = "delete")
    @SaCheckPermission("system:menu:delete")
    public RespInfo<?> delete(@Parameter(description = "菜单 ID") @RequestParam Long menuId) {
        return menuService.deleteResp(menuId);
    }
}
