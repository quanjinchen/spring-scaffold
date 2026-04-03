package dn.spring.scaffold.framework.web.config;

import dn.spring.scaffold.framework.web.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestInterceptor requestInterceptor;

    private final RequestInterceptorConfig requestInterceptorConfig;

    public WebConfig(RequestInterceptor requestInterceptor, RequestInterceptorConfig requestInterceptorConfig) {
        this.requestInterceptor = requestInterceptor;
        this.requestInterceptorConfig = requestInterceptorConfig;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**").excludePathPatterns(requestInterceptorConfig.getExcludePaths());
    }
}
