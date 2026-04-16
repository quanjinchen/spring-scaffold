package dn.spring.scaffold.console.pojo.req;

import dn.spring.scaffold.common.annotation.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "更新菜单请求")
public class UpdateMenuReqParam {

    @Schema(description = "菜单 ID", required = true, example = "1")
    @NotNull(message = "菜单 ID 不能为空")
    private Long id;

    @Schema(description = "父菜单 ID", required = true, example = "0")
    @NotNull(message = "父菜单 ID 不能为空")
    private Long parentId;

    @Schema(description = "菜单名称", required = true, example = "用户管理")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @Schema(description = "菜单路径", example = "/system/user")
    private String path;

    @Schema(description = "菜单类型，M 目录，C 菜单，B 按钮", required = true, example = "C")
    @NotBlank(message = "菜单类型不能为空")
    @MenuType(allowBlank = false)
    private String menuType;

    @Schema(description = "权限编码", example = "system:user:query")
    private String permissionCode;

    @Schema(description = "排序值", example = "10")
    private Integer sortOrder;

    @Schema(description = "是否可见", example = "true")
    private Boolean visible;
}
