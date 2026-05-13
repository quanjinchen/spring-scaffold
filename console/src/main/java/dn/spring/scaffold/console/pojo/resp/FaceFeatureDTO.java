package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "人脸特征信息")
public class FaceFeatureDTO {

    @Schema(description = "Base64 编码的人脸特征值")
    private String feature;
}
