package dn.spring.scaffold.console.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GetOrgUserListReqParam;
import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.console.pojo.resp.OrgUserInfo;
import dn.spring.scaffold.console.service.OrgUserService;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "组织用户管理")
@RestController
@RequestMapping("/org-user")
public class OrgUserController {

    @Resource
    private OrgUserService orgUserService;

    @Operation(summary = "查询组织用户列表", description = "权限：system:org:query")
    @PostMapping("/list-org-user")
    @SaCheckPermission("system:org:query")
    public RespInfo<List<OrgUserInfo>> listOrgUser(@Valid @RequestBody GetOrgUserListReqParam reqParam) {
        return orgUserService.listOrgUser(reqParam);
    }

    @Operation(summary = "分配组织用户", description = "权限：system:org:update")
    @PostMapping("/grant-org-users")
    @OperateLog(module = "orgUser", action = "分配组织用户")
    @SaCheckPermission("system:org:update")
    public RespInfo<List<OrgUserInfo>> grantOrgUsers(@Valid @RequestBody GrantOrgUsersReqParam reqParam) {
        return orgUserService.grantOrgUsers(reqParam);
    }
}
