package com.xingjin.common;

import lombok.Data;

/**
 * 通用结果返回类，用于封装API接口的返回数据
 * @param <T> 返回数据的类型
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 创建成功的Result对象
     * @param data 返回的数据
     * @param <T> 数据类型
     * @return 包含成功状态和数据的Result对象
     */
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    /**
     * 创建成功的Result对象（无数据）
     * @param <T> 数据类型
     * @return 包含成功状态的Result对象
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 创建错误的Result对象
     * @param msg 错误信息
     * @param <T> 数据类型
     * @return 包含错误状态和信息的Result对象
     */
    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }
}

