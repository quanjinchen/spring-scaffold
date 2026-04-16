package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "查询组织用户列表请求")
public class GetOrgUserListReqParam {

    @Schema(description = "组织 ID", required = true, example = "1")
    @NotNull(message = "组织 ID 不能为空")
    private Long orgId;
}
