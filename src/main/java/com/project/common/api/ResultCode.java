package com.project.common.api;

/**
 * 常用API操作码
 *
 * @author Qing2514
 */
public enum ResultCode implements IErrorCode {

    /**
     * 通用操作码
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),

    /**
     * 权限模块
     */
    ACCOUNT_FORBIDDEN(1001, "帐号已被禁用"),
    TOKEN_EXPIRE(1002, "token已过期"),
    PASSWORD_INCORRECT(1003, "密码不正确"),
    USERNAME_OR_PASSWORD_INCORRECT(1004, "用户名或密码错误"),
    OLD_PASSWORD_INCORRECT(1005, "旧密码错误"),
    NEW_PASSWORD_EQUALS_OLD(1005, "新旧密码相同"),

    /**
     * 用户模块
     */
    USER_EXISTS(2001, "用户已存在"),
    USER_NOT_EXISTS(2002, "用户不存在"),
    NOT_CURRENT_USER(2003, "非当前登录用户"),

    /**
     * 文件模块
     */
    FILENAME_NULL(3001, "文件名为空"),
    FILE_EXISTS(3002, "文件已存在"),
    FILE_NOT_EXISTS(3003, "文件不存在"),

    ;

    private final long code;

    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
