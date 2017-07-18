package com.power.jfss.entity;

import java.math.BigDecimal;
import java.util.Date;

public class JfSelectSjVO {

	private Integer sbid; // 申报ID
	private String  sjmc; // 关联事件名称
	private String  sjms; // 事件描述
	private String  sbrid; // 申报人ID
	private String  uname; // 申报人姓名
	private String  deptname; // 申报人部门
	private String  zw;   // 申请人职位
	private String  status; // 审核失败
	private BigDecimal fz;  // 分值
	private BigDecimal zongfen;// 总分
	private Date    sbsj; //申报时间
	public Integer getSbid() {
		return sbid;
	}
	public void setSbid(Integer sbid) {
		this.sbid = sbid;
	}
	public String getSjmc() {
		return sjmc;
	}
	public void setSjmc(String sjmc) {
		this.sjmc = sjmc;
	}
	public String getSbrid() {
		return sbrid;
	}
	public void setSbrid(String sbrid) {
		this.sbrid = sbrid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getZw() {
		return zw;
	}
	public void setZw(String zw) {
		this.zw = zw;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getFz() {
		return fz;
	}
	public void setFz(BigDecimal fz) {
		this.fz = fz;
	}
	public BigDecimal getZongfen() {
		return zongfen;
	}
	public void setZongfen(BigDecimal zongfen) {
		this.zongfen = zongfen;
	}
	public String getSjms() {
		return sjms;
	}
	public void setSjms(String sjms) {
		this.sjms = sjms;
	}
	public Date getSbsj() {
		return sbsj;
	}
	public void setSbsj(Date sbsj) {
		this.sbsj = sbsj;
	}
	
}
