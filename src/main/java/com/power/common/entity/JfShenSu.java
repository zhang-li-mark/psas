package com.power.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class JfShenSu {
    private Integer id;

    private String userid;

    private String uname;

    private String deptid;

    private String deptname;

    private String type;

    private Integer sjid;
    
    private String  sjmc;

    private BigDecimal fz;

    private String ssyy;

    private String status;

    private String sspz;

    private Date sssj;

    private String sprid;

    private String spjg;

    private String spbz;

    private Date spsj;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getSjid() {
        return sjid;
    }

    public void setSjid(Integer sjid) {
        this.sjid = sjid;
    }

    public BigDecimal getFz() {
        return fz;
    }

    public void setFz(BigDecimal fz) {
        this.fz = fz;
    }

    public String getSsyy() {
        return ssyy;
    }

    public void setSsyy(String ssyy) {
        this.ssyy = ssyy == null ? null : ssyy.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSspz() {
        return sspz;
    }

    public void setSspz(String sspz) {
        this.sspz = sspz == null ? null : sspz.trim();
    }

    public Date getSssj() {
        return sssj;
    }

    public void setSssj(Date sssj) {
        this.sssj = sssj;
    }

    public String getSprid() {
        return sprid;
    }

    public void setSprid(String sprid) {
        this.sprid = sprid;
    }

    public String getSpjg() {
        return spjg;
    }

    public void setSpjg(String spjg) {
        this.spjg = spjg == null ? null : spjg.trim();
    }

    public String getSpbz() {
        return spbz;
    }

    public void setSpbz(String spbz) {
        this.spbz = spbz == null ? null : spbz.trim();
    }

    public Date getSpsj() {
        return spsj;
    }

    public void setSpsj(Date spsj) {
        this.spsj = spsj;
    }

	public String getSjmc() {
		return sjmc;
	}

	public void setSjmc(String sjmc) {
		this.sjmc = sjmc;
	}
}