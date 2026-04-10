package dn.spring.scaffold.common.constant;

public enum ResultCode {

    SUCCESS(200, "成功"),
    CREATED(201, "创建成功"),

    // ========== 客户端错误段 ==========
    BAD_REQUEST(400, "请求参数不正确"),
    UNAUTHORIZED(401, "账号未登录"),
    FORBIDDEN(403, "缺少访问权限"),
    NOT_FOUND(404, "无效请求路径"),
    INVALID_REQUEST_TIMESTAMP(400, "请求时间戳超过允许范围"),
    INVALID_REQUEST_ID(400, "请求标识不合法"),
    DUPLICATE_REQUEST_ID(400, "请求标识重复"),

    // ========== 服务端错误段 ==========
    INTERNAL_SERVER_ERROR(500, "系统异常，请稍后重试"),

    // ========== 管理后台认证错误段 [10000, 11000) ==========
    CAPTCHA_INVALID(10001, "验证码错误"),
    CAPTCHA_EXPIRED(10002, "验证码不存在或已经过期"),
    ACCOUNT_OR_PASSWORD_INVALID(10003, "账号或密码错误"),
    NOT_LOGGED_IN(10004, "账号未登录"),

    // ========== 文件错误段 [61000, 62000) ==========
    FILE_NOT_FOUND(61000, "文件不存在"),
    FILE_NOT_EMPTY(61002, "文件内容不能为空");

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
