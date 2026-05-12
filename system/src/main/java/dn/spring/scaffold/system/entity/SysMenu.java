package dn.spring.scaffold.system.entity;

import dn.spring.scaffold.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

    private Long parentId;

    private String menuName;

    private String path;

    private String icon;

    private String menuType;

    private String menuCode;

    private Integer orderNum;

    private Boolean visible;
}
