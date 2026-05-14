package dn.spring.scaffold.console.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateOrgReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteOrgReqParam;
import dn.spring.scaffold.console.pojo.req.GetOrgByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListOrgReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateOrgReqParam;
import dn.spring.scaffold.console.pojo.resp.OrgDTO;
import dn.spring.scaffold.console.service.OrgService;
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

@Tag(name = "组织机构管理")
@RestController
@RequestMapping("/org")
public class OrgController {

    private final OrgService orgService;

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    @Operation(summary = "查询组织树", description = "权限：system:org:query")
    @PostMapping("/list-all-org-tree")
    @SaCheckPermission("system:org:query")
    public RespInfo<List<OrgDTO>> listAllOrgTree() {
        return orgService.listAllOrgTree();
    }

    @Operation(summary = "分页查询组织", description = "权限：system:org:query")
    @PostMapping("/list-org")
    @SaCheckPermission("system:org:query")
    public RespInfo<PageData<OrgDTO>> listOrg(@Valid @RequestBody ListOrgReqParam reqParam) {
        return orgService.listOrg(reqParam);
    }

    @Operation(summary = "根据 ID 查询组织详情", description = "权限：system:org:query")
    @GetMapping("/get-org-by-id/{id}")
    @SaCheckPermission("system:org:query")
    public RespInfo<OrgDTO> getOrgById(@Parameter(description = "组织 ID") @PathVariable Long id) {
        GetOrgByIdReqParam reqParam = new GetOrgByIdReqParam();
        reqParam.setOrgId(id);
        return orgService.getOrgById(reqParam);
    }

    @Operation(summary = "创建组织", description = "权限：system:org:update")
    @PostMapping("/create-org")
    @OperateLog(module = "组织机构管理", action = "创建组织")
    @SaCheckPermission("system:org:update")
    public RespInfo<OrgDTO> createOrg(@Valid @RequestBody CreateOrgReqParam reqParam) {
        return orgService.createOrg(reqParam);
    }

    @Operation(summary = "更新组织", description = "权限：system:org:update")
    @PostMapping("/update-org")
    @OperateLog(module = "组织机构管理", action = "更新组织")
    @SaCheckPermission("system:org:update")
    public RespInfo<OrgDTO> updateOrg(@Valid @RequestBody UpdateOrgReqParam reqParam) {
        return orgService.updateOrg(reqParam);
    }

    @Operation(summary = "删除组织", description = "权限：system:org:delete")
    @PostMapping("/delete-org")
    @OperateLog(module = "组织机构管理", action = "删除组织")
    @SaCheckPermission("system:org:delete")
    public RespInfo<Void> deleteOrg(@Valid @RequestBody DeleteOrgReqParam reqParam) {
        return orgService.deleteOrg(reqParam);
    }
}
