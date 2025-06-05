package com.zyw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果封装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer status;     // 响应状态码
    private String msg;         // 响应状态描述
    private T data;             // 响应数据

    public static <T> Result<T> success() {
        return new Result<>(200, "ok", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "ok", data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    public static <T> Result<T> error() {
        return new Result<>(500, "服务器错误！", null);
    }

    public static <T> Result<T> error(Object msg) {
        return new Result<>(400, (String) msg, null);
    }
}
