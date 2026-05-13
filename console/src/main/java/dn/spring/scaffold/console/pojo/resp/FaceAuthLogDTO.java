package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "人脸认证日志信息")
public class FaceAuthLogDTO {

    @Schema(description = "日志 ID")
    private Long id;

    @Schema(description = "认证接口类型，1 表示 1:1，2 表示 1:N")
    private Integer authApiType;

    @Schema(description = "请求 IP")
    private String ip;

    @Schema(description = "应用 ID")
    private Long appId;

    @Schema(description = "应用名称")
    private String appName;

    @Schema(description = "认证人姓名")
    private String authFullName;

    @Schema(description = "认证人 ID")
    private Long authUserId;

    @Schema(description = "状态，0 失败 1 成功")
    private Integer status;

    @Schema(description = "失败原因")
    private String errmsg;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
