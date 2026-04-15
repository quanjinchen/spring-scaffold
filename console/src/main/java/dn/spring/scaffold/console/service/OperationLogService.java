package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.system.entity.OperationLog;

public interface OperationLogService {

    PageData<OperationLog> page(PageReqParam reqParam);

    RespInfo<PageData<OperationLog>> pageResp(PageReqParam reqParam);

    void save(OperationLog operationLog);
}
