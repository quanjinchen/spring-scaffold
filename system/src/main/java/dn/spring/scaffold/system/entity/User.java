package dn.spring.scaffold.system.entity;

import dn.spring.scaffold.common.entity.BaseEntity;
import dn.spring.scaffold.common.entity.EncryptField;
import dn.spring.scaffold.common.mybatisplus.typehandler.EncryptTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user", autoResultMap = true)
public class User extends BaseEntity {

    private String username;

    private String nickname;

    private String email;

    @TableField(typeHandler = EncryptTypeHandler.class)
    private EncryptField phone;

    @TableField("org_id")
    private Long orgId;

    private String password;

    private Integer status;
}
