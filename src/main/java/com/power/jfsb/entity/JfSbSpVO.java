package com.power.jfsb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class JfSbSpVO {

	private Integer id; // 审批记录ID
	private Integer sbid; // 申报记录ID
	private String  sprid; // 审批人ID
	private String  status; // 审批状态0.未审批1.已审批
	private String  spjg;  // 审批结果0.未通过1.已通过
	private Date    spsj;  // 审批时间
	private String  sbrid; // 申报人ID
	private String 	sjmc;  // 事件名称
	private String  jfxmc; // 积分项
	private BigDecimal fz; // 申报分值
	private Date    sbsj;  // 申报时间
	private String  uname; // 申报人姓名
	private String  deptname; // 部门
	private String  zw;    // 职位
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
	public String getSprid() {
		return sprid;
	}
	public void setSprid(String sprid) {
		this.sprid = sprid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSpjg() {
		return spjg;
	}
	public void setSpjg(String spjg) {
		this.spjg = spjg;
	}
	public Date getSpsj() {
		return spsj;
	}
	public void setSpsj(Date spsj) {
		this.spsj = spsj;
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
	public String getJfxmc() {
		return jfxmc;
	}
	public void setJfxmc(String jfxmc) {
		this.jfxmc = jfxmc;
	}
	public BigDecimal getFz() {
		return fz;
	}
	public void setFz(BigDecimal fz) {
		this.fz = fz;
	}
	public Date getSbsj() {
		return sbsj;
	}
	public void setSbsj(Date sbsj) {
		this.sbsj = sbsj;
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
}
