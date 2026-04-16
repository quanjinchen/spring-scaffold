package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.ListOperationLogReqParam;
import dn.spring.scaffold.console.pojo.resp.OperationLogDTO;

public interface OperationLogService {

    RespInfo<PageData<OperationLogDTO>> listOperationLog(ListOperationLogReqParam reqParam);
}
