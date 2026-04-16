package dn.spring.scaffold.framework.web.filter;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import dn.spring.scaffold.framework.utils.IpUtils;
import dn.spring.scaffold.framework.web.trace.TraceContext;
import dn.spring.scaffold.framework.web.trace.TraceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class TraceFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        TraceContext traceContext = new TraceContext();
        String traceId = createTraceId(request);
        traceContext.setTraceId(traceId);
        traceContext.setPath(request.getRequestURI());
        traceContext.setMethod(request.getMethod());
        traceContext.setClientIp(IpUtils.getIpAddr(request));
        TraceContextHolder.set(traceContext);
        MDC.put("traceId", traceId);
        response.setHeader("X-Request-Id", request.getHeader("X-REQUEST-ID"));
        response.setHeader("X-Trace-Id", traceId);
        long startTime = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            if (!request.getRequestURI().startsWith("/actuator")) {
                log.info("接口耗时：{}ms, method={}, uri={}, status={}",
                        System.currentTimeMillis() - startTime,
                        request.getMethod(),
                        request.getRequestURI(),
                        response.getStatus());
            }
            PageHelper.clearPage();
            MDC.remove("traceId");
            TraceContextHolder.clear();
        }
    }

    private String createTraceId(HttpServletRequest request) {
        return request.getRequestURI() + " | " + IdUtil.fastSimpleUUID();
    }
}
