package dn.spring.scaffold.common.pojo;

import dn.spring.scaffold.common.constant.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "统一响应体")
public class RespInfo<T> {

    @Schema(description = "响应码")
    private Integer code;

    @Schema(description = "响应消息")
    private String msg;

    @Schema(description = "响应数据")
    private T data;

    public RespInfo(Integer code, String message, T data) {
        this.code = code;
        setMessage(message);
        this.data = data;
    }

    public static <T> RespInfo<T> success() {
        return success(null);
    }

    public static <T> RespInfo<T> success(T data) {
        return new RespInfo<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> RespInfo<T> created() {
        return created(null);
    }

    public static <T> RespInfo<T> created(T data) {
        return success(data);
    }

    public static <T> RespInfo<T> fail(String message) {
        return new RespInfo<>(ResultCode.INTERNAL_SERVER_ERROR.getCode(), message, null);
    }

    public static <T> RespInfo<T> fail(ResultCode resultCode) {
        return new RespInfo<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> RespInfo<T> fail(ResultCode resultCode, String message) {
        return new RespInfo<>(resultCode.getCode(), message, null);
    }

    public static <T> RespInfo<T> fail(int code, String message) {
        return new RespInfo<>(code, message, null);
    }

    public static <T> RespInfo<T> fail(ResultCode resultCode, T data) {
        return new RespInfo<>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static <T> RespInfo<T> failed(String message) {
        return fail(message);
    }

    public static <T> RespInfo<T> failed(ResultCode resultCode) {
        return fail(resultCode);
    }

    public static <T> RespInfo<T> failed(ResultCode resultCode, String message) {
        return fail(resultCode, message);
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
