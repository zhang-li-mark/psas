package com.power.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class JfJiaJian {
    private Integer id;

    private String type;

    private String dyrid;

    private Integer sjid;

    private BigDecimal fz;

    private String yy;

    private String sqrid;

    private Date sqsj;

    private String status;

    private Integer shrid;

    private String shzt;

    private String shsj;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDyrid() {
        return dyrid;
    }

    public void setDyrid(String dyrid) {
        this.dyrid = dyrid == null ? null : dyrid.trim();
    }

    public Integer getSjid() {
        return sjid;
    }

    public void setSjid(Integer sjid) {
        this.sjid = sjid;
    }

    public BigDecimal getFz() {
        return fz;
    }

    public void setFz(BigDecimal fz) {
        this.fz = fz;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy == null ? null : yy.trim();
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid == null ? null : sqrid.trim();
    }

    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getShrid() {
        return shrid;
    }

    public void setShrid(Integer shrid) {
        this.shrid = shrid;
    }

    public String getShzt() {
        return shzt;
    }

    public void setShzt(String shzt) {
        this.shzt = shzt == null ? null : shzt.trim();
    }

    public String getShsj() {
        return shsj;
    }

    public void setShsj(String shsj) {
        this.shsj = shsj == null ? null : shsj.trim();
    }
}