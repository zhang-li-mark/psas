package com.power.jfss.entity;

import java.math.BigDecimal;

public class JfTotal {

	private String userid;     // 用户的ID
	
	private BigDecimal zongfen; // 总分

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public BigDecimal getZongfen() {
		return zongfen;
	}

	public void setZongfen(BigDecimal zongfen) {
		this.zongfen = zongfen;
	}
}
