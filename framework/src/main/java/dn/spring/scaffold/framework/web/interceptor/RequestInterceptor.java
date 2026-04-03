package dn.spring.scaffold.framework.web.interceptor;

import dn.spring.scaffold.framework.utils.IpUtils;
import dn.spring.scaffold.framework.utils.UserAgentUtils;
import dn.spring.scaffold.framework.web.trace.TraceContext;
import dn.spring.scaffold.framework.web.trace.TraceContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        TraceContext traceContext = TraceContextHolder.get();
        if (traceContext != null) {
            traceContext.setClientIp(IpUtils.getIpAddr(request));
            traceContext.setBrowser(UserAgentUtils.getBrowser(request));
            traceContext.setOperatingSystem(UserAgentUtils.getOperatingSystem(request));
        }
        return true;
    }
}
