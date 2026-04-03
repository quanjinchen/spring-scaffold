package dn.spring.scaffold.console.controller;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.console.service.OrgService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import dn.spring.scaffold.system.entity.Org;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/org")
public class OrgController {

    private final OrgService orgService;

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    @GetMapping("/tree")
    @SaCheckPermission("system:org:query")
    public RespInfo<?> tree() {
        return RespInfo.success(orgService.tree());
    }

    @GetMapping("/page")
    @SaCheckPermission("system:org:query")
    public RespInfo<?> page(PageQuery pageQuery) {
        return RespInfo.success(orgService.page(pageQuery));
    }

    @GetMapping("/{id}")
    @SaCheckPermission("system:org:query")
    public RespInfo<?> detail(@PathVariable Long id) {
        return RespInfo.success(orgService.detail(id));
    }

    @PostMapping("/save")
    @OperateLog(module = "org", action = "save")
    @SaCheckPermission("system:org:update")
    public RespInfo<?> save(@RequestBody Org org) {
        return RespInfo.created(orgService.save(org));
    }

    @GetMapping("/users")
    @SaCheckPermission("system:org:query")
    public RespInfo<?> listOrgUsers(@RequestParam Long orgId) {
        return RespInfo.success(orgService.listOrgUsers(orgId));
    }

    @PostMapping("/grant-users")
    @OperateLog(module = "org", action = "grant-users")
    @SaCheckPermission("system:org:update")
    public RespInfo<?> grantOrgUsers(@RequestBody GrantOrgUsersReqParam reqParam) {
        return RespInfo.success(orgService.grantOrgUsers(reqParam));
    }
}
