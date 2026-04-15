package dn.spring.scaffold.system.pojo.query;

import lombok.Data;

@Data
public class ListUserQuery {

    private String keyword;

    private Long orgId;

    private Integer status;
}
