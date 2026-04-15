package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.service.OperationLogService;
import dn.spring.scaffold.system.entity.OperationLog;
import dn.spring.scaffold.system.manager.OperationLogManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Resource
    private OperationLogManager operationLogManager;

    @Override
    public PageResult<OperationLog> page(PageQuery pageQuery) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<OperationLog> page = operationLogManager.page(pageQuery);
        return PageResult.of(page.getRecords(), page.getTotal(), pageQuery);
    }

    @Override
    public RespInfo<PageResult<OperationLog>> pageResp(PageQuery pageQuery) {
        return RespInfo.success(page(pageQuery));
    }

    @Override
    public void save(OperationLog operationLog) {
        operationLogManager.save(operationLog);
    }
}
