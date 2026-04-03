package dn.spring.scaffold.common.validation.validator;

import cn.hutool.core.util.StrUtil;
import dn.spring.scaffold.common.annotation.RedirectUri;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URI;

public class RedirectUriValidator implements ConstraintValidator<RedirectUri, String> {

    private boolean allowBlank;

    @Override
    public void initialize(RedirectUri constraintAnnotation) {
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
            if (StrUtil.isBlank(scheme)) {
                return false;
            }
            return "http".equalsIgnoreCase(scheme)
                    || "https".equalsIgnoreCase(scheme)
                    || "urn".equalsIgnoreCase(scheme)
                    || "custom".equalsIgnoreCase(scheme);
        } catch (Exception exception) {
            return false;
        }
    }
}
