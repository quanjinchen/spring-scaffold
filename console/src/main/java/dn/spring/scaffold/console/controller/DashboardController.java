package dn.spring.scaffold.console.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.resp.DashboardSummaryDTO;
import dn.spring.scaffold.console.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Tag(name = "首页统计")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    @Operation(summary = "首页统计汇总", description = "权限：system:index:baseInfo")
    @PostMapping("/summary")
    @SaCheckPermission(
            value = {
                    "index:baseInfo",
                    "system:index:baseInfo",
                    "system:index:userNum",
                    "system:index:userActive",
                    "system:index:appRank",
                    "system:index:userDevice"
            },
            mode = SaMode.OR
    )
    public RespInfo<DashboardSummaryDTO> summary() {
        return dashboardService.summary();
    }
}
