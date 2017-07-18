package com.power.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class KsKaoShi {
    private Long id;

    private String fbrid;

    private String kszt;

    private Date kssj;

    private Date jssj;

    private Date tjsj;

    private Integer zpbms;

    private String zpbm;
    
    private String dws;

    private BigDecimal zf;

    private Integer jf;
    
    private String kstm;
    
    private String kstmid;
    private Integer sjlx;
    public Integer getSjlx() {
		return sjlx;
	}

	public void setSjlx(Integer sjlx) {
		this.sjlx = sjlx;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFbrid() {
        return fbrid;
    }

    public void setFbrid(String fbrid) {
        this.fbrid = fbrid == null ? null : fbrid.trim();
    }

    public String getKszt() {
        return kszt;
    }

    public void setKszt(String kszt) {
        this.kszt = kszt == null ? null : kszt.trim();
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

    public Integer getZpbms() {
        return zpbms;
    }

    public void setZpbms(Integer zpbms) {
        this.zpbms = zpbms;
    }

    public String getZpbm() {
        return zpbm;
    }

    public void setZpbm(String zpbm) {
        this.zpbm = zpbm == null ? null : zpbm.trim();
    }

    public BigDecimal getZf() {
        return zf;
    }

    public void setZf(BigDecimal zf) {
        this.zf = zf;
    }

	public Integer getJf() {
		return jf;
	}

	public void setJf(Integer jf) {
		this.jf = jf;
	}

	public String getKstm() {
		return kstm;
	}

	public void setKstm(String kstm) {
		this.kstm = kstm;
	}

	public String getKstmid() {
		return kstmid;
	}

	public void setKstmid(String kstmid) {
		this.kstmid = kstmid;
	}

	public String getDws() {
		return dws;
	}

	public void setDws(String dws) {
		this.dws = dws;
	}
}