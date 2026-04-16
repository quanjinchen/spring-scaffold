package dn.spring.scaffold.system.entity;

import dn.spring.scaffold.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Org extends BaseEntity {

    @TableField("parent_id")
    private Long parentId;

    @TableField("org_code")
    private String orgCode;

    private String name;

    @TableField("leader_name")
    private String leaderName;

    @TableField("sort_order")
    private Integer sortOrder;

    private Integer status;
}
