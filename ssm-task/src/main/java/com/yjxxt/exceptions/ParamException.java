package com.yjxxt.exceptions;

public class ParamException extends RuntimeException{

    private Integer code = 200;
    private String msg = "操作成功";

    public ParamException() {
        super("数据错误");
    }

    public ParamException(Integer code) {
        super("数据错误");
        this.code = code;
    }

    public ParamException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ParamException(Integer code , String msg) {
        super(msg);
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
