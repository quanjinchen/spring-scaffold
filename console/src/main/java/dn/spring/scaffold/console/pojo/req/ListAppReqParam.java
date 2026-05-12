package dn.spring.scaffold.console.pojo.req;

import dn.spring.scaffold.common.page.PageReqParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "分页查询应用列表请求")
public class ListAppReqParam extends PageReqParam {

    @Schema(description = "关键字，支持按应用名称、应用编码或客户端 ID 搜索", example = "admin")
    private String keyword;
}
