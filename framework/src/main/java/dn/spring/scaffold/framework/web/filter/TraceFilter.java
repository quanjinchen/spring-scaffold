package dn.spring.scaffold.framework.web.filter;

import cn.hutool.core.util.IdUtil;
import dn.spring.scaffold.framework.utils.IpUtils;
import dn.spring.scaffold.framework.web.trace.TraceContext;
import dn.spring.scaffold.framework.web.trace.TraceContextHolder;
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
public class TraceFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        TraceContext traceContext = new TraceContext();
        traceContext.setTraceId(IdUtil.fastSimpleUUID());
        traceContext.setPath(request.getRequestURI());
        traceContext.setMethod(request.getMethod());
        traceContext.setClientIp(IpUtils.getIpAddr(request));
        TraceContextHolder.set(traceContext);
        MDC.put("traceId", traceContext.getTraceId());
        response.setHeader("X-Request-Id", request.getHeader("X-REQUEST-ID"));
        response.setHeader("X-Trace-Id", traceContext.getTraceId());
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("traceId");
            TraceContextHolder.clear();
        }
    }
}
