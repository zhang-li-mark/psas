package com.power.jfgl.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.power.common.entity.JfShenbao;
import com.power.common.entity.JfTjHz;
import com.power.common.entity.JfTjHzKey;
import com.power.common.mybatis.DaoHelper;
import com.power.common.mybatis.page.JqGrid;
import com.power.jfgl.entity.JfJcxxMxVO;
import com.power.jfgl.entity.JfTjVO;

/**
 * 积分信息查询
 * 项目名称：psas <br>
 * 类名称：JfcxService <br>
 */
@Service("jfcxService")
public class JfcxService {
	@Autowired
	private DaoHelper daoHelper;
	/**
	 * 积分明细信息
	 * @param uid
	 * @return
	 */
	public JfJcxxMxVO getJfJcxxMx(String uid) {
		return (JfJcxxMxVO) daoHelper.findObject("mapper.JfTjJcxxMapper.getJfJcxxMx", uid);
	}
	/**
	 * 积分汇总信息
	 * @param key
	 * @return
	 */
	public JfTjHz getJfHz(JfTjHzKey key) {
		return daoHelper.findObject(JfTjHz.class, key);
	}
	
	/**
	 * 我的积分积分汇总信息
	 * @param key
	 * @return
	 */
	public JfTjHz getMyJfHz(Map<String, Object> map) {
		return (JfTjHz)daoHelper.findObject("mapper.JfTjHzMapper.getMyJfTJ", map);
	}
	
	/**
	 * 列表展示
	 * 
	 * @param map
	 * @return
	 */
	public List<JfTjVO> getJfTj(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.JfTjHzMapper.getJfTJ", map,grid);
	}
	
}
