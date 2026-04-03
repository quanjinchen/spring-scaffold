package dn.spring.scaffold.common.validation.validator;

import cn.hutool.core.util.StrUtil;
import dn.spring.scaffold.common.annotation.HttpUrl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URI;

public class HttpUrlValidator implements ConstraintValidator<HttpUrl, String> {

    private boolean allowBlank;

    @Override
    public void initialize(HttpUrl constraintAnnotation) {
        this.allowBlank = constraintAnnotation.allowBlank();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return allowBlank;
        }
        try {
            URI uri = URI.create(value);
            String scheme = uri.getScheme();
            if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                return false;
            }
            return StrUtil.isNotBlank(uri.getHost());
        } catch (Exception exception) {
            return false;
        }
    }
}
