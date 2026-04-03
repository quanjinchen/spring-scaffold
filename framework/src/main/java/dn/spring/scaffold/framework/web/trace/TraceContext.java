package dn.spring.scaffold.framework.web.trace;

import lombok.Data;

@Data
public class TraceContext {

    private String traceId;

    private String method;

    private String path;

    private String clientIp;

    private String browser;

    private String operatingSystem;
}
