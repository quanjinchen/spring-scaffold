package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "菜单树节点")
public class MenuTreeNode {

    @Schema(description = "菜单 ID")
    private Long id;

    @Schema(description = "父菜单 ID")
    private Long parentId;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "菜单类型，CATALOG/MENU/BUTTON")
    private String menuType;

    @Schema(description = "权限编码")
    private String permissionCode;

    @Schema(description = "排序值")
    private Integer sortOrder;

    @Schema(description = "是否可见")
    private Boolean visible;

    @Schema(description = "子菜单列表")
    private List<MenuTreeNode> children = new ArrayList<MenuTreeNode>();
}
