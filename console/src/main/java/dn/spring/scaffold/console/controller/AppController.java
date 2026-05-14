package dn.spring.scaffold.console.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateAppReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteAppReqParam;
import dn.spring.scaffold.console.pojo.req.GetAppByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListAppReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateAppReqParam;
import dn.spring.scaffold.console.pojo.resp.AppDTO;
import dn.spring.scaffold.console.service.AppService;
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

@Tag(name = "应用管理")
@RestController
@RequestMapping("/app")
public class AppController {

    @Resource
    private AppService appService;

    @Operation(summary = "分页查询应用", description = "权限：system:app:query")
    @OperateLog(module = "应用管理", action = "分页查询应用")
    @PostMapping("/list-app")
    @SaCheckPermission("system:app:query")
    public RespInfo<PageData<AppDTO>> listApp(@Valid @RequestBody ListAppReqParam reqParam) {
        return appService.listApp(reqParam);
    }

    @Operation(summary = "根据 ID 查询应用详情", description = "权限：system:app:query")
    @OperateLog(module = "应用管理", action = "查询应用详情")
    @GetMapping("/get-app-by-id/{id}")
    @SaCheckPermission("system:app:query")
    public RespInfo<AppDTO> getAppById(@Parameter(description = "应用 ID") @PathVariable Long id) {
        GetAppByIdReqParam reqParam = new GetAppByIdReqParam();
        reqParam.setAppId(id);
        return appService.getAppById(reqParam);
    }

    @Operation(summary = "创建应用", description = "权限：system:app:update")
    @PostMapping("/create-app")
    @OperateLog(module = "应用管理", action = "创建应用")
    @SaCheckPermission("system:app:update")
    public RespInfo<AppDTO> createApp(@Valid @RequestBody CreateAppReqParam reqParam) {
        return appService.createApp(reqParam);
    }

    @Operation(summary = "更新应用", description = "权限：system:app:update")
    @PostMapping("/update-app")
    @OperateLog(module = "应用管理", action = "更新应用")
    @SaCheckPermission("system:app:update")
    public RespInfo<AppDTO> updateApp(@Valid @RequestBody UpdateAppReqParam reqParam) {
        return appService.updateApp(reqParam);
    }

    @Operation(summary = "删除应用", description = "权限：system:app:delete")
    @PostMapping("/delete-app")
    @OperateLog(module = "应用管理", action = "删除应用")
    @SaCheckPermission("system:app:delete")
    public RespInfo<Void> deleteApp(@Valid @RequestBody DeleteAppReqParam reqParam) {
        return appService.deleteApp(reqParam);
    }
}
