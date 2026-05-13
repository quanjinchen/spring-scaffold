package dn.spring.scaffold.console.pojo.req;

import dn.spring.scaffold.common.page.PageReqParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "分页查询人脸认证日志请求")
public class ListFaceAuthLogReqParam extends PageReqParam {

    @Schema(description = "认证接口类型，1 表示 1:1，2 表示 1:N", example = "1")
    private Integer authApiType;

    @Schema(description = "请求 IP", example = "127.0.0.1")
    private String ip;

    @Schema(description = "状态，0 失败 1 成功", example = "1")
    private Integer status;

    @Schema(description = "应用名称", example = "后台管理系统")
    private String appName;

    @Schema(description = "认证人姓名", example = "张三")
    private String authFullName;
}
