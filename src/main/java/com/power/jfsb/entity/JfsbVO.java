package com.power.jfsb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class JfsbVO {

	private Integer id;

	private String type;

	private String sjmc;

	private Integer jflxid;

	private BigDecimal fz;

	private String sjms;

	private String fj;

	private Date sbsj;

	private String status;

    private String jdms;
    
    private Integer dqjd;
    
    private Integer spjlid;
    
    private Date dqjdsj;

	private String jfxmc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSjmc() {
		return sjmc;
	}

	public void setSjmc(String sjmc) {
		this.sjmc = sjmc;
	}

	public Integer getJflxid() {
		return jflxid;
	}

	public void setJflxid(Integer jflxid) {
		this.jflxid = jflxid;
	}

	public BigDecimal getFz() {
		return fz;
	}

	public void setFz(BigDecimal fz) {
		this.fz = fz;
	}

	public String getSjms() {
		return sjms;
	}

	public void setSjms(String sjms) {
		this.sjms = sjms;
	}

	public String getFj() {
		return fj;
	}

	public void setFj(String fj) {
		this.fj = fj;
	}

	public Date getSbsj() {
		return sbsj;
	}

	public void setSbsj(Date sbsj) {
		this.sbsj = sbsj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJfxmc() {
		return jfxmc;
	}

	public void setJfxmc(String jfxmc) {
		this.jfxmc = jfxmc;
	}

	public String getJdms() {
		return jdms;
	}

	public void setJdms(String jdms) {
		this.jdms = jdms;
	}

	public Integer getDqjd() {
		return dqjd;
	}

	public void setDqjd(Integer dqjd) {
		this.dqjd = dqjd;
	}

	public Integer getSpjlid() {
		return spjlid;
	}

	public void setSpjlid(Integer spjlid) {
		this.spjlid = spjlid;
	}

	public Date getDqjdsj() {
		return dqjdsj;
	}

	public void setDqjdsj(Date dqjdsj) {
		this.dqjdsj = dqjdsj;
	}
}
