package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "人脸比对结果")
public class FaceCompareDTO {

    @Schema(description = "相似度分数")
    private Float similarity;
}
