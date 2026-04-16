package dn.spring.scaffold.system.manager.impl;

import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.system.entity.OperationLog;
import dn.spring.scaffold.system.manager.OperationLogManager;
import dn.spring.scaffold.system.mapper.OperationLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OperationLogManagerImpl implements OperationLogManager {

    @Resource
    private OperationLogMapper operationLogMapper;

    @Override
    public Page<OperationLog> page(PageReqParam reqParam) {
        return operationLogMapper.selectPage(
                new Page<OperationLog>(reqParam.getPageNum(), reqParam.getPageSize()),
                new LambdaQueryWrapper<OperationLog>()
                        .orderByDesc(OperationLog::getRequestTime)
                        .orderByDesc(OperationLog::getId)
        );
    }

    @Override
    public List<OperationLog> listOperationLogs() {
        return operationLogMapper.selectList(new LambdaQueryWrapper<OperationLog>()
                .orderByDesc(OperationLog::getRequestTime)
                .orderByDesc(OperationLog::getId));
    }

    @Override
    public void save(OperationLog operationLog) {
        operationLogMapper.insert(operationLog);
    }
}
