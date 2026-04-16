package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "操作日志信息")
public class OperationLogDTO {

    @Schema(description = "日志 ID")
    private Long id;

    @Schema(description = "模块名称")
    private String moduleName;

    @Schema(description = "动作名称")
    private String actionName;

    @Schema(description = "操作人")
    private String operatorName;

    @Schema(description = "请求路径")
    private String requestPath;

    @Schema(description = "是否成功")
    private Boolean successFlag;

    @Schema(description = "请求时间")
    private LocalDateTime requestTime;
}
