package com.power.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GrQingjia {
    private Integer id;

    private String type;

    private BigDecimal tian;

    private Date kssj;

    private Date jssj;

    private String qjyy;

    private String qjrid;

    private String status;

    private Date tjsj;

    private String pz;
    
    private Integer dqjd;
    
    private Integer dqjdid;
    
    private Date  dqjdsj;
    
    private String uname;
    
    private String deptname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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

    public String getQjyy() {
        return qjyy;
    }

    public void setQjyy(String qjyy) {
        this.qjyy = qjyy == null ? null : qjyy.trim();
    }

    public String getQjrid() {
        return qjrid;
    }

    public void setQjrid(String qjrid) {
        this.qjrid = qjrid == null ? null : qjrid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getTjsj() {
        return tjsj;
    }

    public void setTjsj(Date tjsj) {
        this.tjsj = tjsj;
    }

    public String getPz() {
        return pz;
    }

    public void setPz(String pz) {
        this.pz = pz == null ? null : pz.trim();
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

	public Integer getDqjd() {
		return dqjd;
	}

	public void setDqjd(Integer dqjd) {
		this.dqjd = dqjd;
	}

	public Integer getDqjdid() {
		return dqjdid;
	}

	public void setDqjdid(Integer dqjdid) {
		this.dqjdid = dqjdid;
	}

	public Date getDqjdsj() {
		return dqjdsj;
	}

	public void setDqjdsj(Date dqjdsj) {
		this.dqjdsj = dqjdsj;
	}
}