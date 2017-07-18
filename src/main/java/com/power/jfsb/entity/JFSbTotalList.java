package com.power.jfsb.entity;

import java.math.BigDecimal;
import java.util.Date;

public class JFSbTotalList {

	private String uname; // 积分所得者姓名
	private String sjmc;  // 事件名称
	private Date   sqsj;  // 申请时间
	private Date   spsj;  // 审批时间
	private BigDecimal fz;// 申请的分值
	private String jfx;   // 积分类型 (管理积分,业务积分)
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
	public Date getSqsj() {
		return sqsj;
	}
	public void setSqsj(Date sqsj) {
		this.sqsj = sqsj;
	}
	public Date getSpsj() {
		return spsj;
	}
	public void setSpsj(Date spsj) {
		this.spsj = spsj;
	}
	public BigDecimal getFz() {
		return fz;
	}
	public void setFz(BigDecimal fz) {
		this.fz = fz;
	}
	public String getJfx() {
		return jfx;
	}
	public void setJfx(String jfx) {
		this.jfx = jfx;
	}
	
}
