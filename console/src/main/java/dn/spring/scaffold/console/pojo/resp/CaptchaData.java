package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "图形验证码响应")
public class CaptchaData {

    @Schema(description = "验证码唯一标识")
    private String uuid;

    @Schema(description = "验证码内容，仅开发调试时可见")
    private String code;

    @Schema(description = "验证码图片 Base64 数据")
    private String img;

    @Schema(description = "过期时间，单位秒")
    private Integer expireSeconds;

    @Schema(description = "提示信息")
    private String tip;
}
