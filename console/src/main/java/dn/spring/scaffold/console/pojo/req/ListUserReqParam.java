package dn.spring.scaffold.console.pojo.req;

import dn.spring.scaffold.common.page.PageReqParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询用户列表请求")
public class ListUserReqParam extends PageReqParam {

    @Schema(description = "关键字，支持用户名、昵称、手机号、邮箱搜索")
    private String keyword;

    @Schema(description = "组织机构 ID", example = "1")
    private Long orgId;

    @Schema(description = "状态，1 启用，0 禁用", example = "1")
    private Integer status;
}
