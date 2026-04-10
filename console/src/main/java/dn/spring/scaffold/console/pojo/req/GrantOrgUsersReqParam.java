package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "分配组织机构用户请求")
public class GrantOrgUsersReqParam {

    @Schema(description = "组织机构 ID", required = true, example = "1")
    @NotNull(message = "组织机构 ID 不能为空")
    private Long orgId;

    @Schema(description = "用户 ID 列表")
    private List<Long> userIds;
}
