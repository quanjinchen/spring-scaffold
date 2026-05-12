package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "更新应用请求")
public class UpdateAppReqParam {

    @Schema(description = "应用 ID", required = true, example = "1")
    @NotNull(message = "应用 ID 不能为空")
    private Long id;

    @Schema(description = "应用名称", required = true, example = "后台管理系统")
    @NotBlank(message = "应用名称不能为空")
    private String appName;

    @Schema(description = "应用编码", required = true, example = "admin-console")
    @NotBlank(message = "应用编码不能为空")
    private String appCode;

    @Schema(description = "备注", example = "后台管理端应用")
    private String remark;
}
