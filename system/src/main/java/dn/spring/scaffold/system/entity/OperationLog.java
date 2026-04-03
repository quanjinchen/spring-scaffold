package dn.spring.scaffold.system.entity;

import dn.spring.scaffold.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_operation_log")
public class OperationLog extends BaseEntity {

    @TableField("module_name")
    private String moduleName;

    @TableField("action_name")
    private String actionName;

    @TableField("operator_name")
    private String operatorName;

    @TableField("request_path")
    private String requestPath;

    @TableField("success_flag")
    private Boolean successFlag;

    @TableField("request_time")
    private LocalDateTime requestTime;
}
