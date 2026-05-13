package dn.spring.scaffold.framework.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessTokenInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long appId;

    private String appName;

    private Integer expiresIn;
}
