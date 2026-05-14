package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.resp.DashboardSummaryDTO;

public interface DashboardService {

    RespInfo<DashboardSummaryDTO> summary();
}

