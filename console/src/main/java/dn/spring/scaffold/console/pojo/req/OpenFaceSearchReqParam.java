package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "开放 1 比 N 人脸检索请求")
public class OpenFaceSearchReqParam {

    @Schema(description = "接口访问令牌", required = true, example = "atk_1_xxx")
    @NotBlank(message = "accessToken 不能为空")
    private String accessToken;

    @Schema(description = "人脸图片 Base64，支持 data url 或纯 base64", required = true, example = "/9j/4AAQSkZJRgABAQAAAQABAAD...")
    @NotBlank(message = "faceImageBase64 不能为空")
    private String faceImageBase64;
}
