package dn.spring.scaffold.common.validation.validator;

import cn.hutool.core.util.StrUtil;
import dn.spring.scaffold.common.annotation.LdapUrl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LdapUrlValidator implements ConstraintValidator<LdapUrl, String> {

    private boolean allowBlank;

    @Override
    public void initialize(LdapUrl constraintAnnotation) {
        this.allowBlank = constraintAnnotation.allowBlank();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return allowBlank;
        }
        String lowerValue = value.toLowerCase();
        return lowerValue.startsWith("ldap://") || lowerValue.startsWith("ldaps://");
    }
}
