package com.power.jfgl.entity;

import java.util.Date;

/**
 * 测评任务信息列表
 * 项目名称：psas <br>
 * 类名称：JfCprwVO <br>
 * @version 1.0
 */
public class JfCprwVO {
	private String cpzt;
	private Date kssj;
	private Date jzsj;
	private String dwmc;
	private String pjrxh;
	private Integer mzcprs;
	private Integer zt;//任务状态
	private Integer cpxh;
	private Integer cpmxxh;
	private Integer rybz;
	private Integer cprs;//待测评人数
	private Integer ywc;//已完成人数
	public String getCpzt() {
		return cpzt;
	}
	public void setCpzt(String cpzt) {
		this.cpzt = cpzt;
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
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getPjrxh() {
		return pjrxh;
	}
	public void setPjrxh(String pjrxh) {
		this.pjrxh = pjrxh;
	}
	public Integer getMzcprs() {
		return mzcprs;
	}
	public void setMzcprs(Integer mzcprs) {
		this.mzcprs = mzcprs;
	}
	public Integer getZt() {
		return zt;
	}
	public void setZt(Integer zt) {
		this.zt = zt;
	}
	public Integer getCpxh() {
		return cpxh;
	}
	public void setCpxh(Integer cpxh) {
		this.cpxh = cpxh;
	}
	public Integer getCpmxxh() {
		return cpmxxh;
	}
	public void setCpmxxh(Integer cpmxxh) {
		this.cpmxxh = cpmxxh;
	}
	public Integer getRybz() {
		return rybz;
	}
	public void setRybz(Integer rybz) {
		this.rybz = rybz;
	}
	public Integer getCprs() {
		return cprs;
	}
	public void setCprs(Integer cprs) {
		this.cprs = cprs;
	}
	public Integer getYwc() {
		return ywc;
	}
	public void setYwc(Integer ywc) {
		this.ywc = ywc;
	}
}
