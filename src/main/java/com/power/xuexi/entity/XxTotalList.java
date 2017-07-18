package com.power.xuexi.entity;

import java.math.BigDecimal;
import java.util.Date;

public class XxTotalList {

	private String uname; // 学习人姓名
	private String xxzt;  // 学习主题
	private String content; // 学习内容
	private Date   fbsj;  // 学习发布时间
	private Date   wcsj;  // 学习完成时间
	private BigDecimal fz;// 学习对应的分值
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getXxzt() {
		return xxzt;
	}
	public void setXxzt(String xxzt) {
		this.xxzt = xxzt;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getFbsj() {
		return fbsj;
	}
	public void setFbsj(Date fbsj) {
		this.fbsj = fbsj;
	}
	public Date getWcsj() {
		return wcsj;
	}
	public void setWcsj(Date wcsj) {
		this.wcsj = wcsj;
	}
	public BigDecimal getFz() {
		return fz;
	}
	public void setFz(BigDecimal fz) {
		this.fz = fz;
	}
}
