package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "删除菜单请求")
public class DeleteMenuReqParam {

    @Schema(description = "菜单 ID", required = true, example = "1")
    @NotNull(message = "菜单 ID 不能为空")
    private Long menuId;
}
