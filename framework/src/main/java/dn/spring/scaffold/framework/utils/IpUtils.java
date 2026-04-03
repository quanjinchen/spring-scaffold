package dn.spring.scaffold.framework.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public final class IpUtils {

    private static final String UNKNOWN = "unknown";

    private IpUtils() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!hasRealIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!hasRealIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!hasRealIp(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            return ip.split(",")[0].trim();
        }
        return ip;
    }

    private static boolean hasRealIp(String ip) {
        return StringUtils.hasText(ip) && !UNKNOWN.equalsIgnoreCase(ip);
    }
}
