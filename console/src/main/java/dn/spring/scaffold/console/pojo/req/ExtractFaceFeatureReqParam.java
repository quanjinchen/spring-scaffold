package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "提取人脸特征请求")
public class ExtractFaceFeatureReqParam {

    @Schema(description = "人脸图片 base64，支持 data url 或纯 base64", required = true, example = "data:image/png;base64,xxxx")
    @NotBlank(message = "人脸图片 base64 不能为空")
    private String imageBase64;
}
