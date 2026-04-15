package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "创建角色请求")
public class CreateRoleReqParam {

    @Schema(description = "角色编码", required = true, example = "systemAdmin")
    @NotBlank(message = "角色编码不能为空")
    private String code;

    @Schema(description = "角色名称", required = true, example = "系统管理员")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @Schema(description = "数据权限范围", example = "1")
    private String dataScope;

    @Schema(description = "状态，1 启用，0 禁用", example = "1")
    private Integer status;

    @Schema(description = "备注", example = "系统内置角色")
    private String remark;
}
