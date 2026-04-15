package dn.spring.scaffold.common.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class PageReqParam{

    @Schema(description = "页号，从 1 开始，不传默认为 1")
    @Min(value = 1, message = "页号须从 1 开始")
    private Integer pageNum = 1;

    @Schema(description = "页大小，最小为 1，最大不能超过 100，不传默认为 5")
    @Min(value = 1, message = "页大小不能小于 1")
    @Max(value = 100, message = "页大小不能超过 100")
    private Integer pageSize = 5;
}
