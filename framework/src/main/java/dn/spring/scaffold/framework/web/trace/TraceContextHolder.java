package dn.spring.scaffold.framework.web.trace;

import java.util.HashMap;
import java.util.Map;

public final class TraceContextHolder {

    private static final ThreadLocal<TraceContext> HOLDER = new ThreadLocal<TraceContext>();

    private TraceContextHolder() {
    }

    public static void set(TraceContext traceContext) {
        HOLDER.set(traceContext);
    }

    public static TraceContext get() {
        return HOLDER.get();
    }

    public static void clear() {
        HOLDER.remove();
    }

    public static Map<String, String> copy() {
        TraceContext traceContext = HOLDER.get();
        Map<String, String> map = new HashMap<String, String>();
        if (traceContext == null) {
            return map;
        }
        map.put("traceId", traceContext.getTraceId());
        map.put("method", traceContext.getMethod());
        map.put("path", traceContext.getPath());
        map.put("clientIp", traceContext.getClientIp());
        map.put("browser", traceContext.getBrowser());
        map.put("operatingSystem", traceContext.getOperatingSystem());
        return map;
    }

    public static void restore(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            clear();
            return;
        }
        TraceContext traceContext = new TraceContext();
        traceContext.setTraceId(map.get("traceId"));
        traceContext.setMethod(map.get("method"));
        traceContext.setPath(map.get("path"));
        traceContext.setClientIp(map.get("clientIp"));
        traceContext.setBrowser(map.get("browser"));
        traceContext.setOperatingSystem(map.get("operatingSystem"));
        set(traceContext);
    }
}
