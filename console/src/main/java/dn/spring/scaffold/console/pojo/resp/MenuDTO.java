package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "菜单信息")
public class MenuDTO {

    @Schema(description = "菜单 ID")
    private Long id;

    @Schema(description = "父菜单 ID")
    private Long parentId;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "菜单类型")
    private String menuType;

    @Schema(description = "权限编码")
    private String menuCode;

    @Schema(description = "排序值")
    private Integer orderNum;

    @Schema(description = "是否可见")
    private Boolean visible;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
