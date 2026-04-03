package dn.spring.scaffold.console.pojo.resp;

import lombok.Data;

@Data
public class SysAdminLoginData {

    private Long userId;

    private String username;

    private String token;

    private String mfaToken;

    private boolean systemAdmin;
}
