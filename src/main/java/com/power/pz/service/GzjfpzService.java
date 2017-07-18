package com.power.pz.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.power.common.entity.BaseNews;
import com.power.common.entity.JfShenbaoXm;
import com.power.common.entity.SysRole;
import com.power.common.mybatis.DaoHelper;
import com.power.common.mybatis.page.JqGrid;

@Service("GzjfpzService")
public class GzjfpzService {

	@Autowired
	private DaoHelper daoHelper;

	/**
	 * 获取岗位列表
	 * @param map
	 * @return
	 */
	public List<SysRole> getGw(Map<String, Object> map){
		return  daoHelper.findAll("mapper.SysRoleMapper.showRoles",map);
	}
	
	/**
	 * 列表展示
	 * @param map
	 * @return
	 */
	public List<JfShenbaoXm> showPzs(Map<String, Object> map,JqGrid grid){
		return  daoHelper.findRows("mapper.JfShenbaoXmMapper.showPzs",map,grid);
	}
	
	/**
	 * 获取被修改的值
	 * @param id 被修改的id
	 * @return
	 */
	public JfShenbaoXm getPz(int id) {
		
		return (JfShenbaoXm)daoHelper.findObject(JfShenbaoXm.class,id);
	}
	
	/**
	 * 新增
	 * @param JfShenbaoXm
	 * @return
	 */
	public Object newPz(JfShenbaoXm jfShenbaoXm){
		return daoHelper.insertSelective(jfShenbaoXm);
	}

	/**
	 * 修改
	 * @param JfShenbaoXm
	 * @return
	 */
	public Object editPz(JfShenbaoXm jfShenbaoXm) {
		
		return daoHelper.updateSelective(jfShenbaoXm);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	 public void delPz(Integer id) {
		daoHelper.delete(JfShenbaoXm.class, id);
	 }
}
