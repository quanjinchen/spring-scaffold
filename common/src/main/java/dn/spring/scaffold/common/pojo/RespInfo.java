package dn.spring.scaffold.common.pojo;

import dn.spring.scaffold.common.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespInfo<T> {

    private Integer code;

    private String message;

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
