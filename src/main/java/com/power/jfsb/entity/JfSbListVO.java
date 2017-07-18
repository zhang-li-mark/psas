package com.power.jfsb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class JfSbListVO {

	private Integer id;
	private Integer sbid;
	private BigDecimal fz;
	private Date  tjsj;
	private String sbrid;
	private String sjmc;
	private BigDecimal zf;
	private String jfxmc;
	private String uname;
	private String sbname;
	private String jflx;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSbid() {
		return sbid;
	}
	public void setSbid(Integer sbid) {
		this.sbid = sbid;
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
	public String getSbrid() {
		return sbrid;
	}
	public void setSbrid(String sbrid) {
		this.sbrid = sbrid;
	}
	public String getSjmc() {
		return sjmc;
	}
	public void setSjmc(String sjmc) {
		this.sjmc = sjmc;
	}
	public BigDecimal getZf() {
		return zf;
	}
	public void setZf(BigDecimal zf) {
		this.zf = zf;
	}
	public String getJfxmc() {
		return jfxmc;
	}
	public void setJfxmc(String jfxmc) {
		this.jfxmc = jfxmc;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getSbname() {
		return sbname;
	}
	public void setSbname(String sbname) {
		this.sbname = sbname;
	}
	public String getJflx() {
		return jflx;
	}
	public void setJflx(String jflx) {
		this.jflx = jflx;
	}
}
