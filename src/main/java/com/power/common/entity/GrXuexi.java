package com.power.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GrXuexi {
    private Integer id;

    private String fbrid;

    private String content;

    private Date fbsj;

    private String xxsm;

    private Integer zpbms;

    private String zpbm;

    private BigDecimal fz;
    
    private String dws;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFbrid() {
        return fbrid;
    }

    public void setFbrid(String fbrid) {
        this.fbrid = fbrid == null ? null : fbrid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getFbsj() {
        return fbsj;
    }

    public void setFbsj(Date fbsj) {
        this.fbsj = fbsj;
    }

    public String getXxsm() {
        return xxsm;
    }

    public void setXxsm(String xxsm) {
        this.xxsm = xxsm == null ? null : xxsm.trim();
    }

    public Integer getZpbms() {
        return zpbms;
    }

    public void setZpbms(Integer zpbms) {
        this.zpbms = zpbms;
    }

    public String getZpbm() {
        return zpbm;
    }

    public void setZpbm(String zpbm) {
        this.zpbm = zpbm == null ? null : zpbm.trim();
    }

    public BigDecimal getFz() {
        return fz;
    }

    public void setFz(BigDecimal fz) {
        this.fz = fz;
    }

	public String getDws() {
		return dws;
	}

	public void setDws(String dws) {
		this.dws = dws;
	}
}