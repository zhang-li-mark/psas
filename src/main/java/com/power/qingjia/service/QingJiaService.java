package com.power.qingjia.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.BaseNews;
import com.power.common.entity.GrQingjia;
import com.power.common.entity.GrQingjiaSp;
import com.power.common.entity.JfShenbaoSp;
import com.power.common.mybatis.DaoHelper;
import com.power.common.mybatis.page.JqGrid;
import com.power.jfgl.entity.SpjdVO;
import com.power.jfgl.service.GxpzService;
import com.power.jfsb.entity.JfsbStatusEnum;
import com.power.qingjia.entity.QaTotalList;
import com.power.qingjia.entity.QingJiaSpVO;
import com.power.qingjia.entity.QjNewsVO;
import com.power.xuexi.entity.XxTotalList;

@Service("QingJiaService")
public class QingJiaService {

	@Autowired
	private DaoHelper daoHelper;

	@Autowired
	private GxpzService gxpzService;
	

	/**
	 * 获取被修改的值
	 * 
	 * @param id
	 *            被修改的id
	 * @return
	 */
	public GrQingjia getQingJia(int id) {

		return (GrQingjia) daoHelper.findObject(GrQingjia.class, id);
	}

	

	/**
	 * 列表展示
	 * 
	 * @param map
	 * @return
	 */
	public List<GrQingjia> showQingJia(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.GrQingjiaMapper.getQingJia", map,grid);
	}


	/**
	 * 新增
	 * 
	 * @param GrQingjia
	 * @return
	 */
	public Object newGrQingJia(GrQingjia grQingjia) {
		
		return daoHelper.insertSelective(grQingjia);
	}
	
	/**
	 * 新增
	 * 
	 * @param GrQingjia
	 * @return
	 */
	public Object newGrQingJiaAndSp(GrQingjia grQingjia) {
		
		AuthUserVO user = UserUtils.getAuthUser();
		daoHelper.insertSelective(grQingjia);
		
		// 添加请假审批记录
		SpjdVO dpjdVO = gxpzService.getNextNode(user.getUid(), "", 1);
		
		if(dpjdVO != null && !StringUtils.isBlank(dpjdVO.getNextUserId())){
			GrQingjiaSp _GrQingjiaSp= new GrQingjiaSp();
			_GrQingjiaSp.setQjid(grQingjia.getId());
			_GrQingjiaSp.setSprid(dpjdVO.getNextUserId());
			_GrQingjiaSp.setSzjd(dpjdVO.getCurrentNodeIndex()+1);
			_GrQingjiaSp.setStatus("0");
			newQingJiaSp(_GrQingjiaSp);
			grQingjia.setDqjdid(_GrQingjiaSp.getId());
			grQingjia.setDqjd(dpjdVO.getCurrentNodeIndex()+1);
			grQingjia.setDqjdsj(new Date());
			editGrQingJia(grQingjia); 
		}
		return null;
	}
	

	/**
	 * 修改
	 * 
	 * @param GrQingjia
	 * @return
	 */
	public Object editGrQingJia(GrQingjia grQingjia) {
		//TODO 计算本月 请假天数
		Integer row = daoHelper.updateSelective(grQingjia);
		if(grQingjia.getStatus().equals(JfsbStatusEnum.CHENGGONG.getIndex()+"")){//审批通过
			//调用过程计算汇总积分
			Map<String, Object> param=Maps.newHashMap();
			param.put("id",grQingjia.getId());//
			param.put("jflx",1);//1请假，2学习，3考试
			daoHelper.update("mapper.JfTjJcxxMapper.sp_tj_qjxxks",param);
			
			QjNewsVO _QjNewsVO = (QjNewsVO)daoHelper.findObject("mapper.GrQingjiaMapper.getQingJiaNews", grQingjia.getId());
			
			// 插入公告公示请假成功提示
			BaseNews baseNews = new BaseNews();
			baseNews.setNewsid(daoHelper.findTableKeyUUID());
			baseNews.setCategory("101005");
			baseNews.setTypeid(2);
			baseNews.setDeletemark(0);
			baseNews.setEnabledmark(1);
			baseNews.setCreatedate(new Date());
			baseNews.setReleasetime(new Date());
			baseNews.setParentid(_QjNewsVO.getDeptid());
			baseNews.setFullhead(_QjNewsVO.getDeptname()+_QjNewsVO.getUname()+"的"+_QjNewsVO.getTian()+"天"+_QjNewsVO.getTypeStr()+"申请已通过审核");
			daoHelper.insertSelective(baseNews);
		}
		return row;
	}
	

	/**
	 * 新增请假审批
	 * 
	 * @param JfShenbao
	 * @return
	 */
	public Object newQingJiaSp(GrQingjiaSp grQingjiaSp) {
		return daoHelper.insertSelective(grQingjiaSp);
	}


	
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delQingJia(Integer id) {
		daoHelper.delete(GrQingjia.class, id);
		// 删除请假审批表相关数据
		delQjSpBySbid(id);
	}
	

	
	/**
	 * 通过请假记录ID删除请假审批记录
	 * 
	 * @param id
	 */
	public void delQjSpBySbid(Integer id) {
		daoHelper.delete("mapper.GrQingjiaSpMapper.deleteByQjid", id);
	}
	/**
	 * 获取请假审批列表
	 * 
	 * @param map
	 * @return
	 */
	public List<QingJiaSpVO> showQingJiaSp(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.GrQingjiaSpMapper.getQingJiaSp", map,grid);
	}
	
	
	/**
	 * 审批
	 * 
	 * @param JfShenbao
	 * @return
	 */
	public Object editJfsbsp(JfShenbaoSp jfShenbaoSp) {

		return daoHelper.updateSelective(jfShenbaoSp);
	}
	
	/**
	 * 审批提交
	 * 
	 * @param JfShenbao
	 * @return
	 */
	public Object editQingJiaspSubmit(GrQingjiaSp grQingjiaSp) {
		
		AuthUserVO user = UserUtils.getAuthUser();
		//修改审批记录信息
		daoHelper.updateSelective(grQingjiaSp);
		
		GrQingjia  grQingjia = getQingJia(grQingjiaSp.getQjid());
		
		// 判断是否继续流转
		if(grQingjiaSp.getSflz()!=null &&  grQingjiaSp.getSflz().equals("0")){
			// 结束流程
			if(grQingjiaSp.getSpjg().equals("1")){
				grQingjia.setStatus("3");
				editGrQingJia(grQingjia);
			}else if(grQingjiaSp.getSpjg().equals("0")){
				grQingjia.setStatus("4");
				editGrQingJia(grQingjia);
			}
			
			return null;
		}
		
		// 继续流转
		
		SpjdVO dpjdVO = gxpzService.getNextNode(grQingjia.getQjrid(), user.getUid(), 1);
		
		if(dpjdVO != null && (!StringUtils.isBlank(dpjdVO.getNextUserId()) && !dpjdVO.getNextUserId().equals("0")) && grQingjiaSp.getSpjg().equals("1")){
			GrQingjiaSp _GrQingjiaSp = new GrQingjiaSp();
			_GrQingjiaSp.setQjid(grQingjia.getId());
			_GrQingjiaSp.setSprid(dpjdVO.getNextUserId());
			_GrQingjiaSp.setSzjd(dpjdVO.getCurrentNodeIndex()+1);
			_GrQingjiaSp.setStatus("0");
			newQingJiaSp(_GrQingjiaSp);
			grQingjia.setStatus("2");
			grQingjia.setDqjdid(_GrQingjiaSp.getId());
			grQingjia.setDqjdsj(new Date());
			grQingjia.setDqjd(dpjdVO.getCurrentNodeIndex()+1);
			editGrQingJia(grQingjia);
		}else if(grQingjiaSp.getSpjg().equals("1")){
			grQingjia.setStatus("3");
			editGrQingJia(grQingjia);
			
		}else if(grQingjiaSp.getSpjg().equals("0")){
			grQingjia.setStatus("4");
			editGrQingJia(grQingjia);
		}
		return null;
	}
	
	/**
	 * 获取请假审批流程列表
	 * 
	 * @param map
	 * @return
	 */
	public List<GrQingjiaSp> showQingJiaSpLc(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.GrQingjiaSpMapper.getQingJiaSpLc", map,grid);
	}
	
	/**
	 * 获取请假待审批数量
	 * @param uid
	 * @return
	 */
	public Integer getQjSpcl(String uid){
		return (Integer)daoHelper.findObject("mapper.GrQingjiaSpMapper.getQaSpsl", uid);
	}

	/**
	 * 查询审批过的请假列表
	 * @param userid 用户编号(必选)
	 * @param kssj   开始时间 (可选) 格式 "YYYY-MM-dd HH:ss"
	 * @param jssj   结束时间(可选)
	 * @return
	 */
	public List<QaTotalList> getUserQaTotalList(String userid,String kssj,String jssj){
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
		return daoHelper.findAll("mapper.GrQingjiaMapper.getUserQaTotalList", map);
	}
}
