package com.power.jfgl.entity;

import com.power.common.entity.SysUser;

public class RyGxpzVO extends SysUser {
	private static final long serialVersionUID = 1L;
	private Integer qjsjgs=0;//请假上级
	private String qjcjgx;//
	private Integer sjgs=0;//申报上级
	private String cjgx;//
	public Integer getQjsjgs() {
		return qjsjgs;
	}
	public void setQjsjgs(Integer qjsjgs) {
		this.qjsjgs = qjsjgs;
	}
	public String getQjcjgx() {
		return qjcjgx;
	}
	public void setQjcjgx(String qjcjgx) {
		this.qjcjgx = qjcjgx;
	}
	public Integer getSjgs() {
		return sjgs;
	}
	public void setSjgs(Integer sjgs) {
		this.sjgs = sjgs;
	}
	public String getCjgx() {
		return cjgx;
	}
	public void setCjgx(String cjgx) {
		this.cjgx = cjgx;
	}
}
