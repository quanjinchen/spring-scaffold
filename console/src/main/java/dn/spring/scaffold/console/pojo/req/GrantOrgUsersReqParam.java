package dn.spring.scaffold.console.pojo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GrantOrgUsersReqParam {

    @NotNull(message = "?? ID ????")
    private Long orgId;

    private List<Long> userIds;
}
