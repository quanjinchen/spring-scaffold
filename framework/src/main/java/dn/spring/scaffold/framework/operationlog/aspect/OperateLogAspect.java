package dn.spring.scaffold.framework.operationlog.aspect;

import dn.spring.scaffold.framework.operationlog.OperationLogRecorder;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import dn.spring.scaffold.framework.satoken.LoginUserContext;
import dn.spring.scaffold.system.entity.OperationLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Aspect
@Component
public class OperateLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperateLogAspect.class);

    private final OperationLogRecorder operationLogRecorder;

    private final LoginUserContext loginUserContext;

    private final HttpServletRequest httpServletRequest;

    public OperateLogAspect(OperationLogRecorder operationLogRecorder,
                            LoginUserContext loginUserContext,
                            HttpServletRequest httpServletRequest) {
        this.operationLogRecorder = operationLogRecorder;
        this.loginUserContext = loginUserContext;
        this.httpServletRequest = httpServletRequest;
    }

    @Around("@annotation(operateLog)")
    public Object around(ProceedingJoinPoint joinPoint, OperateLog operateLog) throws Throwable {
        long startTime = System.currentTimeMillis();
        boolean success = true;
        Throwable throwableHolder = null;
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            success = false;
            throwableHolder = throwable;
            throw throwable;
        } finally {
            long cost = System.currentTimeMillis() - startTime;
            saveOperationLog(operateLog, success);
            LOGGER.info("module={}, action={}, success={}, cost={}ms, method={}",
                    operateLog.module(),
                    operateLog.action(),
                    success,
                    cost,
                    joinPoint.getSignature().toShortString());
            if (throwableHolder != null) {
                LOGGER.debug("operate log captured exception", throwableHolder);
            }
        }
    }

    private void saveOperationLog(OperateLog operateLog, boolean success) {
        try {
            OperationLog operationLog = new OperationLog();
            operationLog.setModuleName(operateLog.module());
            operationLog.setActionName(operateLog.action());
            operationLog.setOperatorName(loginUserContext.getLoginUsername());
            operationLog.setRequestPath(httpServletRequest.getRequestURI());
            operationLog.setSuccessFlag(success);
            operationLog.setRequestTime(LocalDateTime.now());
            operationLogRecorder.record(operationLog);
        } catch (Exception exception) {
            LOGGER.warn("save operation log failed: {}", exception.getMessage());
        }
    }
}
