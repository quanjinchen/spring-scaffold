package dn.spring.scaffold.console.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "重置用户密码请求")
public class ResetUserPasswordReqParam {

    @Schema(description = "用户 ID", required = true, example = "1")
    @NotNull(message = "用户 ID 不能为空")
    private Long userId;
}
