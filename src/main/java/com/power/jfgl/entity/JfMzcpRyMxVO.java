package com.power.jfgl.entity;

import com.power.common.entity.JfMzcpRymx;

/**
 * 人员明细
 * 项目名称：psas <br>
 * 类名称：JfMzcpRyMxVO <br>
 * @version 1.0
 */
public class JfMzcpRyMxVO extends JfMzcpRymx {
    private String rzsj;//入职时间
    private String deptId;
    private String zw;//职务
    private String xb;//性别
	public String getRzsj() {
		return rzsj;
	}
	public void setRzsj(String rzsj) {
		this.rzsj = rzsj;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getZw() {
		return zw;
	}
	public void setZw(String zw) {
		this.zw = zw;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
}
