package com.power.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class JfShenbaoRy {
    private Integer id;

    private Integer sbid;

    private String userid;

    private BigDecimal fz;

    private String uname;

    private Date tjsj;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSbid() {
        return sbid;
    }

    public void setSbid(Integer sbid) {
        this.sbid = sbid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BigDecimal getFz() {
        return fz;
    }

    public void setFz(BigDecimal fz) {
        this.fz = fz;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public Date getTjsj() {
        return tjsj;
    }

    public void setTjsj(Date tjsj) {
        this.tjsj = tjsj;
    }
}