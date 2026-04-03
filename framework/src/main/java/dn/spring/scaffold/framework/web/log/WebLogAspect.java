package dn.spring.scaffold.framework.web.log;

import dn.spring.scaffold.common.utils.JsonUtils;
import dn.spring.scaffold.framework.utils.WebFrameworkUtils;
import dn.spring.scaffold.framework.web.trace.TraceContext;
import dn.spring.scaffold.framework.web.trace.TraceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class WebLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Around("execution(* dn.spring.scaffold..controller..*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = WebFrameworkUtils.getRequest();
        TraceContext traceContext = TraceContextHolder.get();
        long start = System.currentTimeMillis();
        if (request != null) {
            LOGGER.info("request start traceId={}, method={}, uri={}, args={}",
                    traceContext == null ? "" : traceContext.getTraceId(),
                    request.getMethod(),
                    request.getRequestURI(),
                    JsonUtils.toJson(joinPoint.getArgs()));
        }
        Object result = joinPoint.proceed();
        LOGGER.info("request end traceId={}, cost={}ms, result={}",
                traceContext == null ? "" : traceContext.getTraceId(),
                System.currentTimeMillis() - start,
                JsonUtils.toJson(result));
        return result;
    }
}
