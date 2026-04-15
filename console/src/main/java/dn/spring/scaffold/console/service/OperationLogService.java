package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.system.entity.OperationLog;

public interface OperationLogService {

    PageResult<OperationLog> page(PageQuery pageQuery);

    RespInfo<PageResult<OperationLog>> pageResp(PageQuery pageQuery);

    void save(OperationLog operationLog);
}
