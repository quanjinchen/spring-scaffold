package dn.spring.scaffold.framework.captcha;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project.captcha")
public class CaptchaProperties {

    private int width = 160;

    private int height = 60;

    private int codeCount = 4;

    private int circleCount = 20;

    private int expireSeconds = 300;
}
