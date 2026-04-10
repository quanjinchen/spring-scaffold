package dn.spring.scaffold.common.pojo;

import dn.spring.scaffold.common.constant.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "统一响应体")
public class RespInfo<T> {

    @Schema(description = "响应码")
    private Integer code;

    @Schema(description = "响应消息")
    private String message;

    @Schema(description = "响应数据")
    private T data;

    public static <T> RespInfo<T> success(T data) {
        return new RespInfo<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> RespInfo<T> created(T data) {
        return new RespInfo<>(ResultCode.CREATED.getCode(), ResultCode.CREATED.getMessage(), data);
    }

    public static <T> RespInfo<T> failed(String message) {
        return new RespInfo<>(ResultCode.INTERNAL_SERVER_ERROR.getCode(), message, null);
    }

    public static <T> RespInfo<T> failed(ResultCode resultCode) {
        return new RespInfo<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> RespInfo<T> failed(ResultCode resultCode, String message) {
        return new RespInfo<>(resultCode.getCode(), message, null);
    }
}
