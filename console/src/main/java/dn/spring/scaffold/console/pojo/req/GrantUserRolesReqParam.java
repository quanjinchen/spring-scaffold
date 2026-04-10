package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "分配用户角色请求")
public class GrantUserRolesReqParam {

    @Schema(description = "用户 ID", required = true, example = "1")
    @NotNull(message = "用户 ID 不能为空")
    private Long userId;

    @Schema(description = "角色 ID 列表")
    private List<Long> roleIds;
}
