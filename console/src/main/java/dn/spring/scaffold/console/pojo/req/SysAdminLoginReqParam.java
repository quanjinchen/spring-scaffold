package dn.spring.scaffold.console.pojo.req;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class SysAdminLoginReqParam {

    @NotBlank(message = "??????")
    private String account;

    @NotBlank(message = "??????")
    private String password;

    @NotBlank(message = "???????????")
    private String uuid;

    @NotBlank(message = "???????")
    private String code;
}
