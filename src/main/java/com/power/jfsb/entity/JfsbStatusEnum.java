package com.power.jfsb.entity;

/**
 * 积分申报
 * 项目名称：psas <br>
 * 类名称：JfsbStatusEnum <br>
 * 创建时间：2017-5-11 下午9:46:52 <br>
 */
public enum JfsbStatusEnum {
	CHENGGONG("审批成功",3),SHIBAI("审批失败",4);

	public static String getName(int index) {
        for (JfsbStatusEnum c : JfsbStatusEnum.values()) {
            if (c.getIndex() == index) {  
                return c.name;  
            }
        }
        return "";
    }
	
	private String name;
    private int index;
    private JfsbStatusEnum(String name, int index) {  
        this.name = name;  
        this.index = index;  
    }
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
