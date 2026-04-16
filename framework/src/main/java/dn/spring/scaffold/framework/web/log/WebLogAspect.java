package dn.spring.scaffold.framework.web.log;

import dn.spring.scaffold.common.utils.JsonUtils;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Aspect
@Component
@Order(1)
@Slf4j
public class WebLogAspect {

    private static final String IGNORE_ARG_PLACEHOLDER = "...";

    @Pointcut("execution(public * dn.spring.scaffold..controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("请求参数：{}", JsonUtils.toJson(getPrintableArgs(joinPoint.getArgs())));
    }

    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Object result) {
        log.info("响应信息：{}", JsonUtils.toJson(result));
    }

    private List<Object> getPrintableArgs(Object[] args) {
        if (ArrayUtil.isEmpty(args)) {
            return Collections.emptyList();
        }

        List<Object> printableArgs = new ArrayList<Object>(args.length);
        for (Object arg : args) {
            if (isPrintableArg(arg)) {
                printableArgs.add(arg);
                continue;
            }
            printableArgs.add(IGNORE_ARG_PLACEHOLDER);
        }
        return printableArgs;
    }

    private boolean isPrintableArg(Object arg) {
        if (arg == null) {
            return true;
        }
        if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
            return false;
        }
        if (arg instanceof ServletRequest || arg instanceof ServletResponse) {
            return false;
        }
        if (arg instanceof HttpSession) {
            return false;
        }
        if (arg instanceof MultipartFile || arg instanceof MultipartFile[]) {
            return false;
        }
        return true;
    }
}
