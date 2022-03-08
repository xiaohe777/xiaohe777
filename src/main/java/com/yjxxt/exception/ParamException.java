package com.yjxxt.exception;

public class ParamException extends RuntimeException{

    private Integer code;
    private String msg;

    public ParamException() {
        super("参数异常");
    }

    public ParamException(Integer code) {
        super();
        this.code = code;
    }

    public ParamException(String msg) {
        super();
        this.msg = msg;
    }

    public ParamException(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ParamException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
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
