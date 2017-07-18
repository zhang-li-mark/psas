package com.power.xuexi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.power.common.entity.GrXuexi;
import com.power.common.entity.GrXuexiBm;
import com.power.common.entity.GrXuexiRy;
import com.power.common.mybatis.DaoHelper;
import com.power.common.mybatis.page.JqGrid;
import com.power.jfss.entity.JfTotal;
import com.power.xuexi.entity.UserVO;
import com.power.xuexi.entity.XxRyVO;
import com.power.xuexi.entity.XxTotalList;

@Service("XueXiService")
public class XueXiService {
	
	@Autowired
	private DaoHelper daoHelper;
	
	/**
	 * 列表展示
	 * @param map
	 * @return
	 */
	public List<GrXuexi> showXueXi(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.GrXuexiMapper.getXueXi", map,grid);
	}
	
	/**
	 * 获取学习任务信息
	 * @param id
	 * @return
	 */
	public GrXuexi getXueXi(Integer id){
		return daoHelper.findObject(GrXuexi.class, id);
	}
	
	/**
	 * 新增学习任务
	 * 
	 * @param GrXuexi
	 * @return
	 */
	public Object newXueXi(GrXuexi grXuexi) {
		String[] bmIds = grXuexi.getDws().split(",");
		grXuexi.setZpbms(bmIds.length);
		
		//插入学习任务主表
		daoHelper.insertSelective(grXuexi);
		
		//插入学习任务部门关系表
		for(int i=0;i<bmIds.length;i++){
			String bmID = bmIds[i];
			GrXuexiBm _GrXuexiBm = new GrXuexiBm();
			_GrXuexiBm.setXxid(grXuexi.getId());
			_GrXuexiBm.setBmbh(bmID);
			_GrXuexiBm.setWcrs(0);
			_GrXuexiBm.setTjsj(new Date());
			
			// 查询部门人员信息
			List<UserVO> users = daoHelper.findAll("mapper.GrXuexiBmMapper.getUsersByDeptID", bmID);
			_GrXuexiBm.setDxxrs(users.size());
			daoHelper.insertSelective(_GrXuexiBm);
			
			//插入学习人员对应表
			for(int j=0;users!=null&&j<users.size();j++){
				GrXuexiRy _GrXuexiRy = new GrXuexiRy();
				_GrXuexiRy.setFz(grXuexi.getFz());
				_GrXuexiRy.setStatus("0");
				_GrXuexiRy.setTjsj(new Date());
				_GrXuexiRy.setXxbmid(_GrXuexiBm.getId());
				_GrXuexiRy.setXxid(grXuexi.getId());
				_GrXuexiRy.setXxrid(users.get(j).getUid());
				daoHelper.insertSelective(_GrXuexiRy);
			}
			
		}
		
		return null;
	}
	
	/**
	 * 修改学习任务
	 * 
	 * @param GrXuexi
	 * @return
	 */
	public Object editXueXi(GrXuexi grXuexi) {
		//判断是否修改指派部门
		String IDS = grXuexi.getDws();
		if(StringUtils.isBlank(IDS)){
			daoHelper.updateSelective(grXuexi);
			//更新人员记录表分值
			GrXuexiRy _GrXuexiRy = new GrXuexiRy();
			_GrXuexiRy.setFz(grXuexi.getFz());
			_GrXuexiRy.setXxid(grXuexi.getId());
			daoHelper.update("mapper.GrXuexiRyMapper.updateFzByXXID", _GrXuexiRy);
		}else{
			daoHelper.updateSelective(grXuexi);
			//已修改部门
			//删除学习任务部门关系表记录
			daoHelper.delete("mapper.GrXuexiBmMapper.deleteXxBmByXXID", grXuexi.getId());
			//删除学习人员对应表记录
			daoHelper.delete("mapper.GrXuexiBmMapper.deleteXxRyByXXID", grXuexi.getId());
			
			//插入学习任务部门关系表
			String[] bmIds = grXuexi.getDws().split(",");
			for(int i=0;i<bmIds.length;i++){
				String bmID = bmIds[i];
				GrXuexiBm _GrXuexiBm = new GrXuexiBm();
				_GrXuexiBm.setXxid(grXuexi.getId());
				_GrXuexiBm.setBmbh(bmID);
				_GrXuexiBm.setWcrs(0);
				_GrXuexiBm.setTjsj(new Date());
				
				// 查询部门人员信息
				List<UserVO> users = daoHelper.findAll("mapper.GrXuexiBmMapper.getUsersByDeptID", bmID);
				_GrXuexiBm.setDxxrs(users.size());
				daoHelper.insertSelective(_GrXuexiBm);
				
				//插入学习人员对应表
				for(int j=0;users!=null&&j<users.size();j++){
					GrXuexiRy _GrXuexiRy = new GrXuexiRy();
					_GrXuexiRy.setFz(grXuexi.getFz());
					_GrXuexiRy.setStatus("0");
					_GrXuexiRy.setTjsj(new Date());
					_GrXuexiRy.setXxbmid(_GrXuexiBm.getId());
					_GrXuexiRy.setXxid(grXuexi.getId());
					_GrXuexiRy.setXxrid(users.get(j).getUid());
					daoHelper.insertSelective(_GrXuexiRy);
				}
				
			}
			
		}
		return null;
	}
	
	/**
	 * 删除学习任务
	 * 
	 * @param id
	 */
	public void delXx(Integer id) {
		daoHelper.delete(GrXuexi.class, id);
		//删除学习任务部门关系表记录
		daoHelper.delete("mapper.GrXuexiBmMapper.deleteXxBmByXXID", id);
		//删除学习人员对应表记录
		daoHelper.delete("mapper.GrXuexiBmMapper.deleteXxRyByXXID", id);
	}
	
	/**
	 * 获取某个人对应的学习任务列表
	 * @param map
	 * @return
	 */
	public List<XxRyVO> showXxByUID(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.GrXuexiRyMapper.getXxInfoByUID", map,grid);
	}
	
	/**
	 * 学习完毕
	 * 
	 * @param id
	 */
	public void editxxwb(Integer id) {
		GrXuexiRy _GrXuexiRy = daoHelper.findObject(GrXuexiRy.class, id);
		_GrXuexiRy.setId(id);
		_GrXuexiRy.setStatus("1");
		_GrXuexiRy.setGxsj(new Date());
		daoHelper.updateSelective(_GrXuexiRy);
		
		GrXuexiBm _GrXuexiBm = daoHelper.findObject(GrXuexiBm.class, _GrXuexiRy.getXxbmid());;
		_GrXuexiBm.setWcrs(_GrXuexiBm.getWcrs()+1);
		
		daoHelper.updateSelective(_GrXuexiBm);
		//更新考试已完成人数，计算考试积分
		Map<String, Object> param=Maps.newHashMap();
		param.put("id",id);//
		param.put("jflx",2);//1请假，2学习，3考试
		daoHelper.update("mapper.JfTjJcxxMapper.sp_tj_qjxxks",param);
	}
	
	
	/**
	 * 查询用户学习所得总积分数
	 * @param userid 用户编号(必选)
	 * @param kssj   开始时间 (可选) 格式 "YYYY-MM-dd HH:ss"
	 * @param jssj   结束时间(可选)
	 * @return
	 */
	public JfTotal getUserXxTotal(String userid,String kssj,String jssj){
		Map<String,Object> map = new  HashMap<String,Object>();
		
		if(StringUtils.isBlank(userid)){
			return null;
		}
		
		map.put("userid", userid);
		
		if(!StringUtils.isBlank(kssj)){
			map.put("kssj", kssj);
		}
		
		if(!StringUtils.isBlank(jssj)){
			map.put("jssj", jssj);
		}
		return (JfTotal)daoHelper.findObject("mapper.GrXuexiRyMapper.getUserXxTotal", map);
	}
	
	/**
	 * 查询用户学习所得积分明细
	 * @param userid 用户编号(必选)
	 * @param kssj   开始时间 (可选) 格式 "YYYY-MM-dd HH:ss"
	 * @param jssj   结束时间(可选)
	 * @return
	 */
	public List<XxTotalList> getUserXxTotalList(String userid,String kssj,String jssj){
		Map<String,Object> map = new  HashMap<String,Object>();
		
		if(StringUtils.isBlank(userid)){
			return null;
		}
		
		map.put("userid", userid);
		
		if(!StringUtils.isBlank(kssj)){
			map.put("kssj", kssj);
		}
		
		if(!StringUtils.isBlank(jssj)){
			map.put("jssj", jssj);
		}
		return daoHelper.findAll("mapper.GrXuexiRyMapper.getUserXxTotalList", map);
	}
	
	/**
	 * 获取待学习数量
	 * @param uid
	 * @return
	 */
	public Integer getXxDcl(String uid){
		return (Integer)daoHelper.findObject("mapper.GrXuexiRyMapper.getXxDcl", uid);
	}
}
