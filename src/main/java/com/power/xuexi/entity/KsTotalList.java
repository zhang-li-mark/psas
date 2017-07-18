package com.power.xuexi.entity;

import java.math.BigDecimal;
import java.util.Date;

public class KsTotalList {

	private String uname; // 学习人姓名
	private String sjmc;  
	private Integer ksdf;
	private Integer qzf;
	private BigDecimal zf;
	private BigDecimal zzjf;
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getSjmc() {
		return sjmc;
	}
	public void setSjmc(String sjmc) {
		this.sjmc = sjmc;
	}
	public Integer getKsdf() {
		return ksdf;
	}
	public void setKsdf(Integer ksdf) {
		this.ksdf = ksdf;
	}
	public Integer getQzf() {
		return qzf;
	}
	public void setQzf(Integer qzf) {
		this.qzf = qzf;
	}
	public BigDecimal getZf() {
		return zf;
	}
	public void setZf(BigDecimal zf) {
		this.zf = zf;
	}
	public BigDecimal getZzjf() {
		return zzjf;
	}
	public void setZzjf(BigDecimal zzjf) {
		this.zzjf = zzjf;
	}
	
}
