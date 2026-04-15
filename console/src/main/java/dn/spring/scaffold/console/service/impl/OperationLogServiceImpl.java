package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.common.page.PageUtils;
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
    public PageData<OperationLog> page(PageReqParam reqParam) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<OperationLog> page = operationLogManager.page(reqParam);
        return PageUtils.of(page);
    }

    @Override
    public RespInfo<PageData<OperationLog>> pageResp(PageReqParam reqParam) {
        return RespInfo.success(page(reqParam));
    }

    @Override
    public void save(OperationLog operationLog) {
        operationLogManager.save(operationLog);
    }
}
