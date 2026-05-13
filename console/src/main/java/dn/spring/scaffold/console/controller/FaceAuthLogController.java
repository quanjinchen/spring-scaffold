package dn.spring.scaffold.console.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.ListFaceAuthLogReqParam;
import dn.spring.scaffold.console.pojo.resp.FaceAuthLogDTO;
import dn.spring.scaffold.console.service.FaceAuthLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name = "人脸认证日志")
@RestController
@RequestMapping("/face-auth-log")
public class FaceAuthLogController {

    @Resource
    private FaceAuthLogService faceAuthLogService;

    @Operation(summary = "分页查询人脸认证日志", description = "权限：system:operationLog:query")
    @PostMapping("/list-face-auth-log")
    @SaCheckPermission("system:operationLog:query")
    public RespInfo<PageData<FaceAuthLogDTO>> listFaceAuthLog(@Valid @RequestBody ListFaceAuthLogReqParam reqParam) {
        return faceAuthLogService.listFaceAuthLog(reqParam);
    }
}

