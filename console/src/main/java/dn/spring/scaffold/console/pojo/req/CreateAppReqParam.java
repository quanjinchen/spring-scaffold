package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "创建应用请求")
public class CreateAppReqParam {

    @Schema(description = "应用名称", required = true, example = "后台管理系统")
    @NotBlank(message = "应用名称不能为空")
    private String appName;

    @Schema(description = "应用编码", required = true, example = "admin-console")
    @NotBlank(message = "应用编码不能为空")
    private String appCode;

    @Schema(description = "备注", example = "后台管理端应用")
    private String remark;
}
