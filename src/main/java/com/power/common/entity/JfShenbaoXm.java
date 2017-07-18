package com.power.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class JfShenbaoXm {
    private Integer id;

    private String jfxmc;

    private String jfxms;

    private BigDecimal fz;

    private Date tjsj;

    private Date xgsj;

    private String jflx;
    
    private String jfjb;

    private String gwid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJfxmc() {
        return jfxmc;
    }

    public void setJfxmc(String jfxmc) {
        this.jfxmc = jfxmc == null ? null : jfxmc.trim();
    }

    public String getJfxms() {
        return jfxms;
    }

    public void setJfxms(String jfxms) {
        this.jfxms = jfxms == null ? null : jfxms.trim();
    }

    public BigDecimal getFz() {
        return fz;
    }

    public void setFz(BigDecimal fz) {
        this.fz = fz;
    }

    public Date getTjsj() {
        return tjsj;
    }

    public void setTjsj(Date tjsj) {
        this.tjsj = tjsj;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public String getJflx() {
        return jflx;
    }

    public void setJflx(String jflx) {
        this.jflx = jflx == null ? null : jflx.trim();
    }

    public String getGwid() {
        return gwid;
    }

    public void setGwid(String gwid) {
        this.gwid = gwid == null ? null : gwid.trim();
    }

	public String getJfjb() {
		return jfjb;
	}

	public void setJfjb(String jfjb) {
		this.jfjb = jfjb;
	}
}