package com.czk.springboot_mybatis.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<T> {
    private Integer code;
    private String msg;
    private T data;
    // 常用状态码常量
    public static final int SUCCESS = 200; // 成功
    public static final int ERROR = -1;    // 通用错误，可以根据具体场景细化
    public static final int UNAUTHORIZED = 401; // 未授权
    public static final int BAD_REQUEST = 400;  // 客户端请求错误
    public static final int NOT_FOUND = 404;    // 资源未找到

    public static <T> JsonResult<T> success() {
        return new JsonResult<>(SUCCESS, "操作成功", null);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(SUCCESS, "操作成功", data);
    }

    public static <T> JsonResult<T> success(String msg, T data) {
        return new JsonResult<>(SUCCESS, msg, data);
    }

    public static <T> JsonResult<T> success(String msg) {
        return new JsonResult<>(SUCCESS, msg, null);
    }

    public static <T> JsonResult<T> error(Integer code, String msg) {
        return new JsonResult<>(code, msg, null);
    }

    public static <T> JsonResult<T> error(String msg) {
        return new JsonResult<>(ERROR, msg, null);
    }

    public static <T> JsonResult<T> error(Integer code, String msg, T data) {
        return new JsonResult<>(code, msg, data);
    }

}