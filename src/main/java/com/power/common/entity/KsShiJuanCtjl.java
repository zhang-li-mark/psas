package com.power.common.entity;

import java.util.Date;

public class KsShiJuanCtjl {
    private Long xh;

    private String ryxh;

    private Long sjxh;

    private Long tmxh;

    private String zqda;

    private String xzda;

    private Integer kf;

    private Date tjsj;

    public Long getXh() {
        return xh;
    }

    public void setXh(Long xh) {
        this.xh = xh;
    }

    public String getRyxh() {
        return ryxh;
    }

    public void setRyxh(String ryxh) {
        this.ryxh = ryxh == null ? null : ryxh.trim();
    }

    public Long getSjxh() {
        return sjxh;
    }

    public void setSjxh(Long sjxh) {
        this.sjxh = sjxh;
    }

    public Long getTmxh() {
        return tmxh;
    }

    public void setTmxh(Long tmxh) {
        this.tmxh = tmxh;
    }

    public String getZqda() {
        return zqda;
    }

    public void setZqda(String zqda) {
        this.zqda = zqda == null ? null : zqda.trim();
    }

    public String getXzda() {
        return xzda;
    }

    public void setXzda(String xzda) {
        this.xzda = xzda == null ? null : xzda.trim();
    }

    public Integer getKf() {
        return kf;
    }

    public void setKf(Integer kf) {
        this.kf = kf;
    }

    public Date getTjsj() {
        return tjsj;
    }

    public void setTjsj(Date tjsj) {
        this.tjsj = tjsj;
    }
}