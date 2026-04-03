package dn.spring.scaffold.console.pojo.resp;

import lombok.Data;

@Data
public class UserDetailResp {

    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private Long orgId;

    private Integer status;
}
