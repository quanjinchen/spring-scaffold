package dn.spring.scaffold.framework.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "project.web.interceptor")
public class RequestInterceptorConfig {

    private List<String> excludePaths = new ArrayList<String>();
}
