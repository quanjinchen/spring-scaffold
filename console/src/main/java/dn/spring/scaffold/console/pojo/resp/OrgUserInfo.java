package dn.spring.scaffold.console.pojo.resp;

import lombok.Data;

@Data
public class OrgUserInfo {

    private Long orgId;

    private Long userId;

    private String username;

    private String nickname;
}
