package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "分页查询角色可分配用户请求")
public class ListRoleAssignableUsersReqParam extends ListUserReqParam {

    @Schema(description = "角色 ID", required = true, example = "1")
    @NotNull(message = "角色 ID 不能为空")
    private Long roleId;
}
