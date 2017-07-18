package com.power.qingjia.entity;

import java.math.BigDecimal;
import java.util.Date;

public class QaTotalList {

	public static final String SHI_JIA = "1";
	public static final String GX_JIA = "2";
	public static final String TQ_JIA = "3";
	public static final String BING_JIA = "4";
	public static final String CHAN_JIA = "5";
	public static final String HUN_JIA = "6";
	
	private String type;
	
	private String typeStr;
	
	private BigDecimal tian;
	
	private Date kssj;
	
	private Date jssj;
	
	private Date tjsj;
	
	private String uname;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeStr() {
		if(type!=null && type.equals(SHI_JIA)){
			return "事假";
		}else if(type!=null && type.equals(GX_JIA)){
			return "公休假";
		}else if(type!=null && type.equals(TQ_JIA)){
			return "探亲假";
		}else if(type!=null && type.equals(BING_JIA)){
			return "病假";
		}else if(type!=null && type.equals(CHAN_JIA)){
			return "产假";
		}else if(type!=null && type.equals(HUN_JIA)){
			return "婚假";
		}
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public BigDecimal getTian() {
		return tian;
	}

	public void setTian(BigDecimal tian) {
		this.tian = tian;
	}

	public Date getKssj() {
		return kssj;
	}

	public void setKssj(Date kssj) {
		this.kssj = kssj;
	}

	public Date getJssj() {
		return jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	public Date getTjsj() {
		return tjsj;
	}

	public void setTjsj(Date tjsj) {
		this.tjsj = tjsj;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}
}
