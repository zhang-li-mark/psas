package com.power.common.entity;

import java.util.Date;

public class GrXuexiBm {
    private Integer id;

    private Integer xxid;

    private String bmbh;

    private Integer dxxrs;

    private Integer wcrs;

    private Date tjsj;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getXxid() {
        return xxid;
    }

    public void setXxid(Integer xxid) {
        this.xxid = xxid;
    }

    public String getBmbh() {
        return bmbh;
    }

    public void setBmbh(String bmbh) {
        this.bmbh = bmbh == null ? null : bmbh.trim();
    }

    public Integer getDxxrs() {
        return dxxrs;
    }

    public void setDxxrs(Integer dxxrs) {
        this.dxxrs = dxxrs;
    }

    public Integer getWcrs() {
        return wcrs;
    }

    public void setWcrs(Integer wcrs) {
        this.wcrs = wcrs;
    }

    public Date getTjsj() {
        return tjsj;
    }

    public void setTjsj(Date tjsj) {
        this.tjsj = tjsj;
    }
}