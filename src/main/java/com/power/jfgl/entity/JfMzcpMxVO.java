package com.power.jfgl.entity;

import java.util.Date;

import com.power.common.entity.JfMzcpMx;


public class JfMzcpMxVO extends JfMzcpMx {
    private Date kssj;
    private Date jzsj;
    private String cpzt;
    private String bz;
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
		this.cpzt = cpzt;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
}
