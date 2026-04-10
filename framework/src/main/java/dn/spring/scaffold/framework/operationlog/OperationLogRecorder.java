package dn.spring.scaffold.framework.operationlog;

import dn.spring.scaffold.system.entity.OperationLog;
import dn.spring.scaffold.system.manager.OperationLogManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OperationLogRecorder {

    @Resource
    private OperationLogManager operationLogManager;

    public void record(OperationLog operationLog) {
        operationLogManager.save(operationLog);
    }
}
