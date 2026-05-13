package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "命中用户信息")
public class OpenFaceSearchUserDTO {

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "手机号")
    private String phoneNum;

    @Schema(description = "姓名")
    private String fullName;

    @Schema(description = "用户名")
    private String userName;
}
