package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "更新角色请求")
public class UpdateRoleReqParam {

    @Schema(description = "角色 ID", required = true, example = "1")
    @NotNull(message = "角色 ID 不能为空")
    private Long id;

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
