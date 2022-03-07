package com.yjxxt.exceptions;

/**
 * 自定义参数异常
 */
public class NoAuthenticationException extends RuntimeException {
    private Integer code=300;
    private String msg="无权访问!";


    public NoAuthenticationException() {
        super("无权访问!");
    }

    public NoAuthenticationException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public NoAuthenticationException(Integer code) {
        super("无权访问!");
        this.code = code;
    }

    public NoAuthenticationException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
