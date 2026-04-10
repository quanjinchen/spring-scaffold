package dn.spring.scaffold.framework.web.interceptor;

import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.exception.BizException;
import dn.spring.scaffold.framework.web.cache.RequestIdCache;
import dn.spring.scaffold.framework.web.config.RequestSecurityProperties;
import dn.spring.scaffold.framework.utils.IpUtils;
import dn.spring.scaffold.framework.utils.UserAgentUtils;
import dn.spring.scaffold.framework.web.trace.TraceContext;
import dn.spring.scaffold.framework.web.trace.TraceContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    public static final String TIMESTAMP_HEADER_NAME = "X-TIMESTAMP";

    public static final String REQUEST_ID_HEADER_NAME = "X-REQUEST-ID";

    @Resource
    private RequestIdCache requestIdCache;

    @Resource
    private RequestSecurityProperties requestSecurityProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        TraceContext traceContext = TraceContextHolder.get();
        if (traceContext != null) {
            applyRequestSecurity(request, traceContext);
            traceContext.setClientIp(IpUtils.getIpAddr(request));
            traceContext.setBrowser(UserAgentUtils.getBrowser(request));
            traceContext.setOperatingSystem(UserAgentUtils.getOperatingSystem(request));
        }
        return true;
    }

    private void applyRequestSecurity(HttpServletRequest request, TraceContext traceContext) {
        if (!requestSecurityProperties.isEnabled()) {
            return;
        }

        String requestId = request.getHeader(REQUEST_ID_HEADER_NAME);
        if (!StringUtils.hasText(requestId)) {
            throw new BizException(ResultCode.INVALID_REQUEST_ID, "请求头 X-REQUEST-ID 不能为空");
        }
        if (requestId.length() < 8 || requestId.length() > 64) {
            throw new BizException(ResultCode.INVALID_REQUEST_ID, "请求头 X-REQUEST-ID 长度必须在 8 到 64 之间");
        }
        traceContext.setRequestId(requestId);

        if (requestSecurityProperties.isCheckRequestIdRepeat()) {
            boolean saved = requestIdCache.saveRequestId(requestId, requestSecurityProperties.getRequestIdExpireSeconds());
            if (!saved) {
                throw new BizException(ResultCode.DUPLICATE_REQUEST_ID);
            }
        }

        Long timestamp = getTimestamp(request);
        if (timestamp == null) {
            throw new BizException(ResultCode.INVALID_REQUEST_TIMESTAMP, "请求头 X-TIMESTAMP 不能为空");
        }
        traceContext.setClientReqTime(timestamp);

        if (requestSecurityProperties.isCheckTimestamp()) {
            long diff = Math.abs(System.currentTimeMillis() - timestamp.longValue());
            if (diff > requestSecurityProperties.getTimestampToleranceMillis()) {
                throw new BizException(ResultCode.INVALID_REQUEST_TIMESTAMP);
            }
        }
    }

    private Long getTimestamp(HttpServletRequest request) {
        String timestamp = request.getHeader(TIMESTAMP_HEADER_NAME);
        if (!StringUtils.hasText(timestamp)) {
            return null;
        }

        try {
            return Long.valueOf(timestamp);
        } catch (Exception exception) {
            throw new BizException(ResultCode.INVALID_REQUEST_TIMESTAMP, "请求头 X-TIMESTAMP 不合法");
        }
    }
}
