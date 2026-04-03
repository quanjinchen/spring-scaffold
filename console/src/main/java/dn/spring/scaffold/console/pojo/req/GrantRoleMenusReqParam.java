package dn.spring.scaffold.console.pojo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GrantRoleMenusReqParam {

    @NotNull(message = "?? ID ????")
    private Long roleId;

    private List<Long> menuIds;
}
