package com.power.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GrXuexiRy {
    private Integer id;

    private Integer xxid;

    private String xxrid;

    private Integer xxbmid;

    private String status;

    private Date tjsj;

    private Date gxsj;

    private BigDecimal fz;

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

    public String getXxrid() {
        return xxrid;
    }

    public void setXxrid(String xxrid) {
        this.xxrid = xxrid == null ? null : xxrid.trim();
    }

    public Integer getXxbmid() {
        return xxbmid;
    }

    public void setXxbmid(Integer xxbmid) {
        this.xxbmid = xxbmid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getTjsj() {
        return tjsj;
    }

    public void setTjsj(Date tjsj) {
        this.tjsj = tjsj;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public BigDecimal getFz() {
        return fz;
    }

    public void setFz(BigDecimal fz) {
        this.fz = fz;
    }
}