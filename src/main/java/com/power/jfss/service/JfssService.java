package com.power.jfss.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.power.common.entity.BaseNews;
import com.power.common.entity.JfShenSu;
import com.power.common.entity.JfShenbao;
import com.power.common.mybatis.DaoHelper;
import com.power.common.mybatis.page.JqGrid;
import com.power.jfsb.entity.JfsbStatusEnum;
import com.power.jfss.entity.JfTotal;
import com.power.jfss.entity.JfssVO;

@Service("JfssService")
public class JfssService {

	@Autowired
	private DaoHelper daoHelper;

	/**
	 * 获取积分申报列表
	 * 
	 * @param map
	 * @return
	 */
	public List<JfShenbao> getJfsbByUID(Map<String, Object> map) {
		return daoHelper.findAll("mapper.JfShenbaoMapper.getJfsbByUid", map);
	}

	/**
	 * 获取被修改的值
	 * 
	 * @param id
	 *            被修改的id
	 * @return
	 */
	public JfShenSu getJfss(int id) {

		return (JfShenSu) daoHelper.findObject(JfShenSu.class, id);
	}

	/**
	 * 列表展示
	 * 
	 * @param map
	 * @return
	 */
	public List<JfssVO> showJfss(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.JfShenSuMapper.showJfss", map,grid);
	}

	/**
	 * 新增
	 * 
	 * @param JfShenSu
	 * @return
	 */
	public Object newJfss(JfShenSu jfShenSu) {
		return daoHelper.insertSelective(jfShenSu);
	}

	/**
	 * 修改
	 * 
	 * @param JfShenSu
	 * @return
	 */
	public Object editJfss(JfShenSu jfShenSu) {
		Integer row = daoHelper.updateSelective(jfShenSu);
		//计算积分
		if(jfShenSu.getStatus().equals(JfsbStatusEnum.CHENGGONG.getIndex()+"")){//积分申诉通过
			//调用过程计算汇总积分
			Map<String, Object> param=Maps.newHashMap();
			param.put("sbid",0);//积分申诉无需
			param.put("sblx", 0);//积分申诉无需
			param.put("ssid", jfShenSu.getId());
			daoHelper.update("mapper.JfTjJcxxMapper.sp_tj_jfsb",param);
			
			JfShenSu _JfShenSu = daoHelper.findObject(JfShenSu.class, jfShenSu.getId());
			
			// 插入公告公示积分申诉成功提示
			BaseNews baseNews = new BaseNews();
			baseNews.setNewsid(daoHelper.findTableKeyUUID());
			baseNews.setCategory("101004");
			baseNews.setTypeid(2);
			baseNews.setDeletemark(0);
			baseNews.setEnabledmark(1);
			baseNews.setCreatedate(new Date());
			baseNews.setReleasetime(new Date());
			baseNews.setParentid(_JfShenSu.getDeptid());
			baseNews.setFullhead(_JfShenSu.getDeptname()+_JfShenSu.getUname()+"的申诉已通过审核");
			daoHelper.insertSelective(baseNews);
		}
		return row;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delSs(Integer id) {
		daoHelper.delete(JfShenSu.class, id);
	}

	/**
	 * 获取申报事件信息
	 * userid 关联人的ID
	 * @param map
	 * @return
	 */
	public List<JfssVO> getSbSjByUserID(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.JfShenbaoRyMapper.getSbSjByUserID", map,grid);
	}
	
	/**
	 * 获取申诉审批信息
	 * 
	 * @param map
	 * @return
	 */
	public List<JfssVO> showJfSsSp(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.JfShenSuMapper.getSsSpByUID", map,grid);
	}
	
	
	/**
	 * 查询用户申诉所得总积分数
	 * @param userid 用户编号(必选)
	 * @param kssj   开始时间 (可选) 格式 "YYYY-MM-dd HH:ss"
	 * @param jssj   结束时间(可选)
	 * @return
	 */
	public JfTotal getUserJfSsTotal(String userid,String kssj,String jssj){
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
		return (JfTotal)daoHelper.findObject("mapper.JfShenSuMapper.getUserJfSsTotal", map);
	}
	
	/**
	 * 获取申诉待审批数量
	 * @param uid
	 * @return
	 */
	public Integer getSsSpSl(String uid){
		return (Integer)daoHelper.findObject("mapper.JfShenSuMapper.getSuSpSl", uid);
	}
}
