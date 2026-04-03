package dn.spring.scaffold.common.validation.validator;

import cn.hutool.core.util.StrUtil;
import dn.spring.scaffold.common.annotation.IP;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.InetAddress;

public class IPAddressValidator implements ConstraintValidator<IP, String> {

    private boolean allowBlank;

    @Override
    public void initialize(IP constraintAnnotation) {
        this.allowBlank = constraintAnnotation.allowBlank();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return allowBlank;
        }
        try {
            InetAddress.getByName(value);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
