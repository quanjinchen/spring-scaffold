package dn.spring.scaffold.system.pojo.query;

import lombok.Data;

@Data
public class ListUserQuery {

    private String username;

    private String fullName;

    private String phone;

    private String email;

    private Integer status;
}
