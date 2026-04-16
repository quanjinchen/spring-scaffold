package dn.spring.scaffold.system.entity;

import dn.spring.scaffold.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrgUser extends BaseEntity {

    @TableField("org_id")
    private Long orgId;

    @TableField("user_id")
    private Long userId;
}
