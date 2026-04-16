package dn.spring.scaffold.system.entity;

import dn.spring.scaffold.common.entity.BaseEntity;
import dn.spring.scaffold.common.entity.EncryptField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String username;

    private String nickname;

    private String email;

    private EncryptField phone;

    @TableField("org_id")
    private Long orgId;

    private String password;

    private Integer status;
}
