package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "组织信息")
public class OrgDTO {

    @Schema(description = "组织 ID")
    private Long id;

    @Schema(description = "父组织 ID")
    private Long parentId;

    @Schema(description = "组织编码")
    private String orgCode;

    @Schema(description = "组织名称")
    private String name;

    @Schema(description = "负责人")
    private String leaderName;

    @Schema(description = "排序值")
    private Integer sortOrder;

    @Schema(description = "状态，1 启用，0 禁用")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
