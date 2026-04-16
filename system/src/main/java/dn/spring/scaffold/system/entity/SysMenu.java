package dn.spring.scaffold.system.entity;

import dn.spring.scaffold.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

    @TableField("parent_id")
    private Long parentId;

    private String name;

    private String path;

    @TableField("menu_type")
    private String menuType;

    @TableField("permission_code")
    private String permissionCode;

    @TableField("sort_order")
    private Integer sortOrder;

    private Boolean visible;
}
