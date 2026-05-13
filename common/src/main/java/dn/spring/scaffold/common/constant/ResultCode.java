package dn.spring.scaffold.common.constant;

import dn.spring.scaffold.common.assertion.Assert;
import dn.spring.scaffold.common.exception.BizException;

import java.text.MessageFormat;

public enum ResultCode implements Assert {

    SUCCESS(0, "成功"),

    // ========== 客户端错误段 ==========
    BAD_REQUEST(400, "请求参数不正确"),
    UNAUTHORIZED(401, "账号未登录"),
    FORBIDDEN(403, "缺少访问权限"),
    NOT_FOUND(404, "无效请求路径"),
    METHOD_NOT_ALLOWED(405, "请求方法不正确"),
    DATA_IN_USE(409, "当前数据已被关联，无法删除"),
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

    // ========== 组织机构错误段 [7000, 8000) ==========
    ORG_NOT_FOUND(7001, "组织不存在"),
    DELETE_ORG_FAILED_BECAUSE_HAS_CHILD(7008, "组织下存在子组织，无法删除"),
    DELETE_ORG_FAILED_BECAUSE_HAS_USER(7009, "组织下存在用户，无法删除"),

    // ========== 用户错误段 [8000, 9000) ==========
    USER_NOT_FOUND(8005, "用户不存在"),

    // ========== 角色错误段 [15000, 16000) ==========
    ROLE_CODE_ALREADY_EXISTS(15001, "角色编码已存在"),
    ROLE_NAME_ALREADY_EXISTS(15002, "角色名称已存在"),
    ROLE_NOT_FOUND(15003, "角色不存在"),
    ROLE_IN_USE(15004, "角色已分配给用户，无法删除"),
    ROLE_CODE_REQUIRED(15005, "角色编码不能为空"),
    ROLE_NAME_REQUIRED(15006, "角色名称不能为空"),

    // ========== 应用错误段 [16000, 17000) ==========
    APP_NOT_FOUND(16001, "应用不存在"),
    APP_CODE_ALREADY_EXISTS(16002, "应用编码已存在"),
    APP_CLIENT_ID_ALREADY_EXISTS(16003, "客户端 ID 已存在"),
    APP_NAME_ALREADY_EXISTS(16004, "应用名称已存在"),
    APP_CLIENT_AUTH_FAILED(16005, "应用账号或应用秘钥错误"),

    // ========== 菜单错误段 [18000, 19000) ==========
    MENU_NOT_FOUND(18004, "菜单不存在"),
    CAN_NOT_DELETE_MENU_BECAUSE_HAS_CHILDREN(18005, "菜单下存在子菜单，无法删除"),
    MENU_IN_USE(18006, "菜单已分配给角色，无法删除"),

    // ========== 文件错误段 [61000, 62000) ==========
    FILE_NOT_FOUND(61000, "文件不存在"),
    FILE_NOT_EMPTY(61002, "文件内容不能为空"),

    // ========== 人脸错误段 [62000, 63000) ==========
    FACE_ENGINE_INIT_FAILED(62000, "人脸引擎初始化失败"),
    FACE_MODEL_NOT_FOUND(62001, "人脸模型文件不存在: {0}"),
    FACE_IMAGE_INVALID(62002, "人脸图片格式不正确"),
    FACE_NOT_DETECTED(62003, "未检测到人脸"),
    FACE_FEATURE_EXTRACT_FAILED(62004, "人脸特征提取失败"),
    FACE_FEATURE_NOT_FOUND(62005, "未找到可比对的人脸特征"),
    FACE_ACCESS_TOKEN_INVALID(62006, "accessToken 无效或已过期");

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

    public BizException newException() {
        return new BizException(this);
    }

    public BizException newException(String message) {
        return new BizException(this, message);
    }

    public BizException newException(Object... args) {
        String formattedMessage = MessageFormat.format(this.message, args);
        return new BizException(this.code, formattedMessage);
    }

    public BizException newException(Throwable cause, Object... args) {
        String formattedMessage = MessageFormat.format(this.message, args);
        return new BizException(this.code, formattedMessage, cause);
    }
}
