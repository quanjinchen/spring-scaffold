package dn.spring.scaffold.framework.operationlog;

import dn.spring.scaffold.system.entity.OperationLog;
import dn.spring.scaffold.system.mapper.OperationLogMapper;
import org.springframework.stereotype.Component;

@Component
public class OperationLogRecorder {

    private final OperationLogMapper operationLogMapper;

    public OperationLogRecorder(OperationLogMapper operationLogMapper) {
        this.operationLogMapper = operationLogMapper;
    }

    public void record(OperationLog operationLog) {
        operationLogMapper.insert(operationLog);
    }
}
