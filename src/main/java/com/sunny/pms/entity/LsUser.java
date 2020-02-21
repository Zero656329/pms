package com.sunny.pms.entity;



import com.sunny.pms.base.Baseentity;

import javax.persistence.Column;
import java.util.Date;

public class LsUser extends Baseentity {
    @Column(name = "c_num")
    private String cnum;
    @Column(name = "c_name")
    private String cname;
    @Column(name = "c_email")
    private String cemail;
    @Column(name = "c_phone")
    private String cphone;
    @Column(name = "n_sex")
    private Integer nsex;
    @Column(name = "c_password")
    private String cpassword;
    @Column(name = "n_status")
    private Integer nstatus;
    @Column(name = "c_reated")
    private String created;
    @Column(name = "c_reatedname")
    private String createdname;
    @Column(name = "d_createtime")
    private Date dcreatetime;
    @Column(name = "c_updated")
    private String cupdated;
    @Column(name = "c_updatedname")
    private String cupdatedname;
    @Column(name = "d_updatetime")
    private Date dupdatetime;
    @Column(name = "c_remark")
    private String cremark;

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public Integer getNsex() {
        return nsex;
    }

    public void setNsex(Integer nsex) {
        this.nsex = nsex;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public Integer getNstatus() {
        return nstatus;
    }

    public void setNstatus(Integer nstatus) {
        this.nstatus = nstatus;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedname() {
        return createdname;
    }

    public void setCreatedname(String createdname) {
        this.createdname = createdname;
    }

    public Date getDcreatetime() {
        return dcreatetime;
    }

    public void setDcreatetime(Date dcreatetime) {
        this.dcreatetime = dcreatetime;
    }

    public String getCupdated() {
        return cupdated;
    }

    public void setCupdated(String cupdated) {
        this.cupdated = cupdated;
    }

    public String getCupdatedname() {
        return cupdatedname;
    }

    public void setCupdatedname(String cupdatedname) {
        this.cupdatedname = cupdatedname;
    }

    public Date getDupdatetime() {
        return dupdatetime;
    }

    public void setDupdatetime(Date dupdatetime) {
        this.dupdatetime = dupdatetime;
    }

    public String getCremark() {
        return cremark;
    }

    public void setCremark(String cremark) {
        this.cremark = cremark;
    }
}
