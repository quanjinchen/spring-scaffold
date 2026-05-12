package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "根据 ID 查询应用详情请求")
public class GetAppByIdReqParam {

    @Schema(description = "应用 ID", required = true, example = "1")
    @NotNull(message = "应用 ID 不能为空")
    private Long appId;
}
