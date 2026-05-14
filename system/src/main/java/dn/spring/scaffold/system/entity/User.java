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

    private String fullName;

    private String email;

    private EncryptField phone;

    private EncryptField idCard;

    private String faceFileId;

    private String faceFeature;

    private String password;

    private Integer status;
}
