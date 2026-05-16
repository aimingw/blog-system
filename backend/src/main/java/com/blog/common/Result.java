package com.blog.common;

/**
 * 统一响应结果封装类
 * 所有接口返回数据都使用此类包装，前端可统一处理
 *
 * @param <T> 数据泛型
 */
public class Result<T> {
    /** 状态码：200成功，其他为错误 */
    private int code;
    /** 提示消息 */
    private String message;
    /** 返回数据 */
    private T data;

    public Result() {}

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    /**
     * 返回成功结果（带数据）
     */
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.code = 200;
        r.message = "success";
        r.data = data;
        return r;
    }

    /**
     * 返回成功结果（无数据）
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 返回错误结果
     * @param code    错误状态码
     * @param message 错误消息
     */
    public static <T> Result<T> error(int code, String message) {
        Result<T> r = new Result<>();
        r.code = code;
        r.message = message;
        return r;
    }

    /**
     * 返回错误结果（默认状态码500）
     */
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
}
