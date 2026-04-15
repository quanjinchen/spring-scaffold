package dn.spring.scaffold.console.pojo.req;

import dn.spring.scaffold.common.page.PageReqParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询角色列表请求")
public class ListRoleReqParam extends PageReqParam {

    @Schema(description = "关键字，支持按角色编码或角色名称搜索", example = "admin")
    private String keyword;

    @Schema(description = "状态，1 启用，0 禁用", example = "1")
    private Integer status;
}
