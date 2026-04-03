package dn.spring.scaffold.framework.captcha;

import lombok.Data;

@Data
public class Captcha {

    private String uuid;

    private String code;

    private String img;

    private Integer expireSeconds;
}
