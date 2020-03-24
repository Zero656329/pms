package com.sunny.pms.result;

public enum  ExceptionMsg {
    SUCCESS("200","操作成功"),
    ;


    ExceptionMsg(String code, String msg) {
        this.code=code;
        this.msg=msg;
    }
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
