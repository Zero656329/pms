package com.sunny.pms.entity;

import lombok.Data;


public class DeadInfo  {
    private Integer id;
    private String msg;
private String addr;
    public DeadInfo() {
    }

    public DeadInfo(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
