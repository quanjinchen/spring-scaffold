package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "应用信息")
public class AppDTO {

    @Schema(description = "应用 ID")
    private Long id;

    @Schema(description = "应用名称")
    private String appName;

    @Schema(description = "应用编码")
    private String appCode;

    @Schema(description = "客户端 ID")
    private String clientId;

    @Schema(description = "客户端密钥")
    private String clientSecret;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
