package com.power.common.entity;

public class JfTjHzKey {
    private Integer nd;

    private String ryxh;

    public Integer getNd() {
        return nd;
    }

    public void setNd(Integer nd) {
        this.nd = nd;
    }

    public String getRyxh() {
        return ryxh;
    }

    public void setRyxh(String ryxh) {
        this.ryxh = ryxh == null ? null : ryxh.trim();
    }
}