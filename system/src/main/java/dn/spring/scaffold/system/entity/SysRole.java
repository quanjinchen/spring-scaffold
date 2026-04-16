package dn.spring.scaffold.system.entity;

import dn.spring.scaffold.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    private String code;

    private String name;

    private Integer status;

    private String remark;
}
