package dn.spring.scaffold.system.entity;

import dn.spring.scaffold.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleUser extends BaseEntity {

    @TableField("user_id")
    private Long userId;

    @TableField("role_id")
    private Long roleId;
}
