package dn.spring.scaffold.common.validation.validator;

import cn.hutool.core.util.StrUtil;
import dn.spring.scaffold.common.annotation.PhoneNum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumValidator implements ConstraintValidator<PhoneNum, String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1\\d{10}$");

    private boolean allowBlank;

    @Override
    public void initialize(PhoneNum constraintAnnotation) {
        this.allowBlank = constraintAnnotation.allowBlank();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return allowBlank;
        }
        return PHONE_PATTERN.matcher(value).matches();
    }
}
