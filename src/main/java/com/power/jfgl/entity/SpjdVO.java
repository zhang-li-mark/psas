package com.power.jfgl.entity;

public class SpjdVO {

	private String nextUserId;//下一节点用户
	private Integer currentNodeIndex;//当前节点索引
	private Integer allIndex;//总节点数
	public String getNextUserId() {
		return nextUserId;
	}
	public void setNextUserId(String nextUserId) {
		this.nextUserId = nextUserId;
	}
	public Integer getCurrentNodeIndex() {
		return currentNodeIndex;
	}
	public void setCurrentNodeIndex(Integer currentNodeIndex) {
		this.currentNodeIndex = currentNodeIndex;
	}
	public Integer getAllIndex() {
		return allIndex;
	}
	public void setAllIndex(Integer allIndex) {
		this.allIndex = allIndex;
	}
	
}
