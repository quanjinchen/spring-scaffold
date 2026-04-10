package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "分配角色菜单请求")
public class GrantRoleMenusReqParam {

    @Schema(description = "角色 ID", required = true, example = "1")
    @NotNull(message = "角色 ID 不能为空")
    private Long roleId;

    @Schema(description = "菜单 ID 列表")
    private List<Long> menuIds;
}
