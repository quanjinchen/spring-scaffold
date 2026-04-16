package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "更新组织请求")
public class UpdateOrgReqParam {

    @Schema(description = "组织 ID", required = true, example = "1")
    @NotNull(message = "组织 ID 不能为空")
    private Long id;

    @Schema(description = "父组织 ID", required = true, example = "0")
    @NotNull(message = "父组织 ID 不能为空")
    private Long parentId;

    @Schema(description = "组织编码", required = true, example = "TECH")
    @NotBlank(message = "组织编码不能为空")
    private String orgCode;

    @Schema(description = "组织名称", required = true, example = "技术中心")
    @NotBlank(message = "组织名称不能为空")
    private String name;

    @Schema(description = "负责人", example = "张三")
    private String leaderName;

    @Schema(description = "排序值", example = "10")
    private Integer sortOrder;

    @Schema(description = "状态，1 启用，0 禁用", example = "1")
    private Integer status;
}
