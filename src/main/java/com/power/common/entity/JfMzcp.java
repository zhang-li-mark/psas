package com.power.common.entity;

import java.util.Date;

public class JfMzcp {
    private Integer xh;

    private Date lrsj;

    private String lryxh;

    private String lrymc;

    private Integer zt;

    private Date kssj;

    private Date jzsj;

    private String cpzt;

    private String bz;

    private Integer cpdwgs;

    private String cpdw;

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Date getLrsj() {
        return lrsj;
    }

    public void setLrsj(Date lrsj) {
        this.lrsj = lrsj;
    }

    public String getLryxh() {
        return lryxh;
    }

    public void setLryxh(String lryxh) {
        this.lryxh = lryxh == null ? null : lryxh.trim();
    }

    public String getLrymc() {
        return lrymc;
    }

    public void setLrymc(String lrymc) {
        this.lrymc = lrymc == null ? null : lrymc.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    public Date getJzsj() {
        return jzsj;
    }

    public void setJzsj(Date jzsj) {
        this.jzsj = jzsj;
    }

    public String getCpzt() {
        return cpzt;
    }

    public void setCpzt(String cpzt) {
        this.cpzt = cpzt == null ? null : cpzt.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Integer getCpdwgs() {
        return cpdwgs;
    }

    public void setCpdwgs(Integer cpdwgs) {
        this.cpdwgs = cpdwgs;
    }

    public String getCpdw() {
        return cpdw;
    }

    public void setCpdw(String cpdw) {
        this.cpdw = cpdw == null ? null : cpdw.trim();
    }
}