package dn.spring.scaffold.console.pojo.req;

import dn.spring.scaffold.common.page.PageReqParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询操作日志列表请求")
public class ListOperationLogReqParam extends PageReqParam {
}
