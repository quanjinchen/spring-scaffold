package dn.spring.scaffold.common.constant;

public enum ResultCode {

    SUCCESS(200, "success"),
    CREATED(201, "created"),
    BAD_REQUEST(400, "bad request"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    NOT_FOUND(404, "not found"),
    INTERNAL_SERVER_ERROR(500, "internal server error"),
    CAPTCHA_INVALID(10001, "captcha invalid"),
    CAPTCHA_EXPIRED(10002, "captcha expired"),
    ACCOUNT_OR_PASSWORD_INVALID(10003, "account or password invalid"),
    NOT_LOGGED_IN(10004, "not logged in"),
    FILE_NOT_FOUND(61000, "file not found"),
    FILE_NOT_EMPTY(61002, "file content must not be empty");

    private final int code;

    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
