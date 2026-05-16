package com.blog.common;

/**
 * 业务异常类
 * 用于抛出业务逻辑相关的异常，由全局异常处理器统一捕获处理
 */
public class BusinessException extends RuntimeException {
    /** 异常状态码 */
    private final int code;

    /**
     * 构造方法
     * @param code    业务状态码
     * @param message 异常消息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造方法（默认状态码500）
     * @param message 异常消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    /** 获取异常状态码 */
    public int getCode() { return code; }
}
