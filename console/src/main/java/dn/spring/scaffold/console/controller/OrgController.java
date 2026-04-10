package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.console.service.OrgService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import dn.spring.scaffold.system.entity.Org;
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

@Tag(name = "组织机构管理")
@RestController
@RequestMapping("/org")
public class OrgController {

    private final OrgService orgService;

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    @Operation(summary = "查询组织机构树")
    @GetMapping("/tree")
    @SaCheckPermission("system:org:query")
    public RespInfo<?> tree() {
        return RespInfo.success(orgService.tree());
    }

    @Operation(summary = "分页查询组织机构")
    @GetMapping("/page")
    @SaCheckPermission("system:org:query")
    public RespInfo<?> page(PageQuery pageQuery) {
        return RespInfo.success(orgService.page(pageQuery));
    }

    @Operation(summary = "根据 ID 查询组织机构详情")
    @GetMapping("/{id}")
    @SaCheckPermission("system:org:query")
    public RespInfo<?> detail(@Parameter(description = "组织机构 ID") @PathVariable Long id) {
        return RespInfo.success(orgService.detail(id));
    }

    @Operation(summary = "保存组织机构")
    @PostMapping("/save")
    @OperateLog(module = "org", action = "save")
    @SaCheckPermission("system:org:update")
    public RespInfo<?> save(@RequestBody Org org) {
        return RespInfo.created(orgService.save(org));
    }

    @Operation(summary = "查询组织机构下的用户")
    @GetMapping("/users")
    @SaCheckPermission("system:org:query")
    public RespInfo<?> listOrgUsers(@Parameter(description = "组织机构 ID") @RequestParam Long orgId) {
        return RespInfo.success(orgService.listOrgUsers(orgId));
    }

    @Operation(summary = "分配组织机构用户")
    @PostMapping("/grant-users")
    @OperateLog(module = "org", action = "grant-users")
    @SaCheckPermission("system:org:update")
    public RespInfo<?> grantOrgUsers(@RequestBody GrantOrgUsersReqParam reqParam) {
        return RespInfo.success(orgService.grantOrgUsers(reqParam));
    }
}
