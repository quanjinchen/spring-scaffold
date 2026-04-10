package dn.spring.scaffold.framework.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project.web.request-security")
public class RequestSecurityProperties {

    private boolean enabled = true;

    private boolean checkTimestamp = true;

    private boolean checkRequestIdRepeat = true;

    private long timestampToleranceMillis = 300000L;

    private long requestIdExpireSeconds = 600L;
}
