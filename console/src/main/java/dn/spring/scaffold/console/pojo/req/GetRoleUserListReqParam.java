package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "查询角色关联用户列表请求")
public class GetRoleUserListReqParam {

    @Schema(description = "角色 ID", required = true, example = "1")
    @NotNull(message = "角色 ID 不能为空")
    private Long roleId;
}
