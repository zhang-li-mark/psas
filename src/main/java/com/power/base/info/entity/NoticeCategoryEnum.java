package com.power.base.info.entity;

/**
 * 通知类型
 * 项目名称：psas <br>
 * 类名称：NoticeCategoryEnum <br>
 * 创建时间：2017-5-11 下午9:46:52 <br>
 */
public enum NoticeCategoryEnum {
	SHENBAO("积分申报",101003),SHENSU("积分申诉",101004),QINGJIA("请休假",101005);

	public static String getName(int index) {
        for (NoticeCategoryEnum c : NoticeCategoryEnum.values()) {
            if (c.getIndex() == index) {  
                return c.name;  
            }
        }
        return "";
    }
	
	private String name;
    private int index;
    private NoticeCategoryEnum(String name, int index) {  
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
