package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "人脸比对请求")
public class CompareFaceFeatureReqParam {

    @Schema(description = "源人脸图片 base64，支持 data url 或纯 base64", required = true, example = "data:image/png;base64,xxxx")
    @NotBlank(message = "源人脸图片 base64 不能为空")
    private String sourceImageBase64;

    @Schema(description = "目标人脸图片 base64，支持 data url 或纯 base64", required = true, example = "data:image/png;base64,yyyy")
    @NotBlank(message = "目标人脸图片 base64 不能为空")
    private String targetImageBase64;
}
