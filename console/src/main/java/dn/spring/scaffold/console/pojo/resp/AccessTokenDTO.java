package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "应用访问令牌")
public class AccessTokenDTO {

    @Schema(description = "接口调用令牌")
    private String accessToken;

    @Schema(description = "有效时长，单位秒")
    private Integer expiresIn;
}
