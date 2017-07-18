package com.power.jfsb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SbRyVO {

	private Integer id;
	
	private String pjrxh;
	
	private BigDecimal fz;
	
	private String pjrmc;
	
	private String deptId;
	
	private String zw;
	
	private Date  tjsj;

	

	public BigDecimal getFz() {
		return fz;
	}

	public void setFz(BigDecimal fz) {
		this.fz = fz;
	}

	public String getPjrmc() {
		return pjrmc;
	}

	public void setPjrmc(String pjrmc) {
		this.pjrmc = pjrmc;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

	public Date getTjsj() {
		return tjsj;
	}

	public void setTjsj(Date tjsj) {
		this.tjsj = tjsj;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPjrxh() {
		return pjrxh;
	}

	public void setPjrxh(String pjrxh) {
		this.pjrxh = pjrxh;
	}


	
}
