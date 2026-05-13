package dn.spring.scaffold.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import dn.spring.scaffold.common.entity.BaseEntity;
import dn.spring.scaffold.system.enums.FaceAuthApiTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FaceAuthLog extends BaseEntity {

    @TableField("auth_api_type")
    private FaceAuthApiTypeEnum authApiType;

    private String ip;

    @TableField("app_id")
    private Long appId;

    private String appName;

    private String authFullName;

    @TableField("auth_user_id")
    private Long authUserId;

    private Integer status;

    private String errmsg;
}
