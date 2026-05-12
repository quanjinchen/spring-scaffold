package dn.spring.scaffold.framework.operationlog.aspect;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.util.SaTokenConsts;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.exception.BizException;
import dn.spring.scaffold.common.utils.JsonUtils;
import dn.spring.scaffold.framework.async.AsyncManager;
import dn.spring.scaffold.framework.operationlog.OperationLogRecorder;
import dn.spring.scaffold.framework.operationlog.annotation.OperateLog;
import dn.spring.scaffold.framework.satoken.LoginUserContext;
import dn.spring.scaffold.framework.utils.IpUtils;
import dn.spring.scaffold.framework.utils.UserAgentUtils;
import dn.spring.scaffold.framework.web.trace.TraceContext;
import dn.spring.scaffold.framework.web.trace.TraceContextHolder;
import dn.spring.scaffold.system.entity.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component
@Order(SaTokenConsts.ASSEMBLY_ORDER - 1)
@Slf4j
public class OperateLogAspect {

    private static final Logger OPERATION_LOGGER = LoggerFactory.getLogger("OPERATION_LOG");

    private static final ThreadLocal<Long> START_TIME_THREAD_LOCAL = new NamedThreadLocal<Long>("operate-log-start-time");

    private final OperationLogRecorder operationLogRecorder;

    private final AsyncManager asyncManager;

    private final LoginUserContext loginUserContext;

    private final HttpServletRequest httpServletRequest;

    public OperateLogAspect(OperationLogRecorder operationLogRecorder,
                            AsyncManager asyncManager,
                            LoginUserContext loginUserContext,
                            HttpServletRequest httpServletRequest) {
        this.operationLogRecorder = operationLogRecorder;
        this.asyncManager = asyncManager;
        this.loginUserContext = loginUserContext;
        this.httpServletRequest = httpServletRequest;
    }

    @Before("@annotation(operateLog)")
    public void before(JoinPoint joinPoint, OperateLog operateLog) {
        START_TIME_THREAD_LOCAL.set(System.currentTimeMillis());
    }

    @AfterReturning("@annotation(operateLog)")
    public void afterReturning(JoinPoint joinPoint, OperateLog operateLog) {
        handleOperateLog(joinPoint, operateLog, null);
    }

    @AfterThrowing(value = "@annotation(operateLog)", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, OperateLog operateLog, Throwable throwable) {
        handleOperateLog(joinPoint, operateLog, throwable);
    }

    private void handleOperateLog(JoinPoint joinPoint, OperateLog operateLog, Throwable throwable) {
        try {
            Long operatorId = loginUserContext.getLoginUserId();
            String operatorName = loginUserContext.getLoginUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setModuleName(operateLog.module());
            operationLog.setActionName(operateLog.action());
            operationLog.setOperatorName(operatorName);
            operationLog.setRequestPath(httpServletRequest.getRequestURI());
            operationLog.setSuccessFlag(throwable == null);
            operationLog.setRequestTime(LocalDateTime.now());
            operationLog.setCreateBy(operatorId == null ? 0L : operatorId);
            operationLog.setUpdateBy(operatorId == null ? 0L : operatorId);

            long startTime = START_TIME_THREAD_LOCAL.get() == null ? System.currentTimeMillis() : START_TIME_THREAD_LOCAL.get().longValue();
            long cost = System.currentTimeMillis() - startTime;
            Map<String, Object> operationLogMessage = new LinkedHashMap<String, Object>();
            TraceContext traceContext = TraceContextHolder.get();
            operationLogMessage.put("traceId", traceContext == null ? null : traceContext.getTraceId());
            operationLogMessage.put("module", operateLog.module());
            operationLogMessage.put("action", operateLog.action());
            operationLogMessage.put("success", throwable == null);
            operationLogMessage.put("costMillis", cost);
            operationLogMessage.put("method", joinPoint.getSignature().toShortString());
            operationLogMessage.put("requestPath", httpServletRequest.getRequestURI());
            operationLogMessage.put("operatorName", operatorName);
            operationLogMessage.put("clientIp", IpUtils.getIpAddr(httpServletRequest));
            operationLogMessage.put("userAgent", UserAgentUtils.getUserAgent(httpServletRequest));
            operationLogMessage.put("failMsg", parseFailMsg(throwable));

            asyncManager.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        operationLogRecorder.record(operationLog);
                    } catch (Exception exception) {
                        log.error("异步记录操作日志失败，operationLog={}", JsonUtils.toJson(operationLog), exception);
                    }
                }
            });
        } catch (Exception exception) {
            log.error("记录操作日志失败：{}", exception.getMessage(), exception);
        } finally {
            START_TIME_THREAD_LOCAL.remove();
        }
    }

    private String parseFailMsg(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        if (throwable instanceof BizException) {
            return throwable.getMessage();
        }
        if (throwable instanceof SaTokenException) {
            return throwable.getMessage();
        }
        return ResultCode.INTERNAL_SERVER_ERROR.getMessage();
    }
}
