package com.power.jfsb.entity;

import java.math.BigDecimal;

public class JbNewsVO {

	private String ryname;
	private BigDecimal fz;
	private String sbrname;
	private BigDecimal zongfen;
	private String deptid;
	private String deptname;
	public String getRyname() {
		return ryname;
	}
	public void setRyname(String ryname) {
		this.ryname = ryname;
	}
	public BigDecimal getFz() {
		return fz;
	}
	public void setFz(BigDecimal fz) {
		this.fz = fz;
	}
	public String getSbrname() {
		return sbrname;
	}
	public void setSbrname(String sbrname) {
		this.sbrname = sbrname;
	}
	public BigDecimal getZongfen() {
		return zongfen;
	}
	public void setZongfen(BigDecimal zongfen) {
		this.zongfen = zongfen;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
}
