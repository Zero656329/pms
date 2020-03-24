package com.sunny.pms.result;

public class ResponseData extends Response {
    private Object data;
    public ResponseData(Object data){
        this.data=data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
