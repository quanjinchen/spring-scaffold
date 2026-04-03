package dn.spring.scaffold.console.pojo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GrantUserRolesReqParam {

    @NotNull(message = "?? ID ????")
    private Long userId;

    private List<Long> roleIds;
}
