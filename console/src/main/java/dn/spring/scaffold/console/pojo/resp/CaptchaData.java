package dn.spring.scaffold.console.pojo.resp;

import lombok.Data;

@Data
public class CaptchaData {

    private String uuid;

    private String code;

    private String img;

    private Integer expireSeconds;

    private String tip;
}
