package com.bcht.rminf.modules.terminal.model;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERROR = 1;

    private int status;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(int status, String msg, T data) {
        this.setStatus(status);
        this.setMsg(msg);
        this.setData(data);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // ============================  构建  ==================================

    // 构建成功
    public static <T> Result<T> ok() {
        return new Result<>(CODE_SUCCESS, "操作成功", null);
    }

    public static <T> Result<T> ok(String msg) {
        return new Result<>(CODE_SUCCESS, msg, null);
    }

    public static <T> Result<T> code(int code) {
        return new Result<>(code, null, null);
    }

    public static <T> Result<T> data(T data) {
        return new Result<>(CODE_SUCCESS, "操作成功", data);
    }

    // 构建失败
    public static <T> Result<T> error() {
        return new Result<>(CODE_ERROR, "服务器异常", null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(CODE_ERROR, msg, null);
    }

    // 构建指定状态码
    public static <T> Result<T> get(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    /*
     * toString()
     */
    @Override
    public String toString() {
        return "{"
                + "\"status\": " + this.getStatus()
                + ", \"msg\": \"" + this.getMsg() + "\""
                + ", \"data\": \"" + this.getData() + "\""
                + "}";
    }

}
