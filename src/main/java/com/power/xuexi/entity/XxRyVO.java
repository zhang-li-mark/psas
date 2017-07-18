package com.power.xuexi.entity;

import java.math.BigDecimal;
import java.util.Date;

public class XxRyVO {
	private Integer id;

    private Integer xxid;

    private String xxrid;

    private Integer xxbmid;

    private String status;

    private Date tjsj;

    private Date gxsj;

    private BigDecimal fz;
    
    private String content;
    
    private String xxsm;
    
    private Date   fbsj;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getXxid() {
		return xxid;
	}

	public void setXxid(Integer xxid) {
		this.xxid = xxid;
	}

	public String getXxrid() {
		return xxrid;
	}

	public void setXxrid(String xxrid) {
		this.xxrid = xxrid;
	}

	public Integer getXxbmid() {
		return xxbmid;
	}

	public void setXxbmid(Integer xxbmid) {
		this.xxbmid = xxbmid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTjsj() {
		return tjsj;
	}

	public void setTjsj(Date tjsj) {
		this.tjsj = tjsj;
	}

	public Date getGxsj() {
		return gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

	public BigDecimal getFz() {
		return fz;
	}

	public void setFz(BigDecimal fz) {
		this.fz = fz;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getXxsm() {
		return xxsm;
	}

	public void setXxsm(String xxsm) {
		this.xxsm = xxsm;
	}

	public Date getFbsj() {
		return fbsj;
	}

	public void setFbsj(Date fbsj) {
		this.fbsj = fbsj;
	}
}
