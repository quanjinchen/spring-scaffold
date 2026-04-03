package dn.spring.scaffold.common.page;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class PageReqParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 1, message = "pageNum must be greater than 0")
    private Integer pageNum = 1;

    @Min(value = 1, message = "pageSize must be greater than 0")
    @Max(value = 500, message = "pageSize must be less than or equal to 500")
    private Integer pageSize = 10;
}
