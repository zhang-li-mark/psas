package com.power.common.entity;

import java.util.Date;

public class JfShenbaoSp {
    private Integer id;

    private Integer sbid;

    private String sprid;

    private String status;

    private String spjg;

    private Date spsj;

    private String spbz;

    private Integer szjd;
    
    private String uname;
    
    private String deptname;
    
    private String zw;

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
        this.sprid = sprid == null ? null : sprid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSpjg() {
        return spjg;
    }

    public void setSpjg(String spjg) {
        this.spjg = spjg == null ? null : spjg.trim();
    }

    public Date getSpsj() {
        return spsj;
    }

    public void setSpsj(Date spsj) {
        this.spsj = spsj;
    }

    public String getSpbz() {
        return spbz;
    }

    public void setSpbz(String spbz) {
        this.spbz = spbz == null ? null : spbz.trim();
    }

    public Integer getSzjd() {
        return szjd;
    }

    public void setSzjd(Integer szjd) {
        this.szjd = szjd;
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