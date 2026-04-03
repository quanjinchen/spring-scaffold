package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.system.entity.OperationLog;
import dn.spring.scaffold.system.mapper.OperationLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

@Service
public class OperationLogService {

    private final OperationLogMapper operationLogMapper;

    public OperationLogService(OperationLogMapper operationLogMapper) {
        this.operationLogMapper = operationLogMapper;
    }

    public PageResult<OperationLog> page(PageQuery pageQuery) {
        Page<OperationLog> page = operationLogMapper.selectPage(new Page<OperationLog>(pageQuery.getPageNum(), pageQuery.getPageSize()),
                new LambdaQueryWrapper<OperationLog>()
                        .orderByDesc(OperationLog::getRequestTime)
                        .orderByDesc(OperationLog::getId));
        return PageResult.of(page.getRecords(), page.getTotal(), pageQuery);
    }

    public void save(OperationLog operationLog) {
        operationLogMapper.insert(operationLog);
    }
}
