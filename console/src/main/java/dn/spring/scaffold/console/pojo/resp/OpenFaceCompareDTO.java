package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "开放 1 比 1 人脸比对结果")
public class OpenFaceCompareDTO {

    @Schema(description = "是否命中")
    private Boolean matched;

    @Schema(description = "相似度分数")
    private Float score;
}
