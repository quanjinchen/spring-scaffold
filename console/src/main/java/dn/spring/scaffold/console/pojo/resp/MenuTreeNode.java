package dn.spring.scaffold.console.pojo.resp;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuTreeNode {

    private Long id;

    private Long parentId;

    private String name;

    private String path;

    private String menuType;

    private String permissionCode;

    private Integer sortOrder;

    private Boolean visible;

    private List<MenuTreeNode> children = new ArrayList<MenuTreeNode>();
}
