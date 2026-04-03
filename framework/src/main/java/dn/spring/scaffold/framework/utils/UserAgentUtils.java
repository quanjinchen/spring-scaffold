package dn.spring.scaffold.framework.utils;

import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

public final class UserAgentUtils {

    private UserAgentUtils() {
    }

    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = parse(request);
        return userAgent == null || userAgent.getBrowser() == null ? "" : userAgent.getBrowser().getName();
    }

    public static String getOperatingSystem(HttpServletRequest request) {
        UserAgent userAgent = parse(request);
        return userAgent == null || userAgent.getOperatingSystem() == null ? "" : userAgent.getOperatingSystem().getName();
    }

    private static UserAgent parse(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }
}
