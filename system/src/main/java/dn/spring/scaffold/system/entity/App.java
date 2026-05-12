package dn.spring.scaffold.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import dn.spring.scaffold.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class App extends BaseEntity {

    private String appName;

    private String appCode;

    private String clientId;

    private String clientSecret;

    private String remark;
}
