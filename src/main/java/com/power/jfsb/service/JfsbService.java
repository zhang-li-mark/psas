package com.power.jfsb.service;

import java.math.BigDecimal;
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
import com.power.common.entity.JfShenbao;
import com.power.common.entity.JfShenbaoRy;
import com.power.common.entity.JfShenbaoSp;
import com.power.common.entity.JfShenbaoXm;
import com.power.common.mybatis.DaoHelper;
import com.power.common.mybatis.page.JqGrid;
import com.power.jfgl.entity.SpjdVO;
import com.power.jfgl.service.GxpzService;
import com.power.jfsb.entity.JFSbTotalList;
import com.power.jfsb.entity.JbNewsVO;
import com.power.jfsb.entity.JfSbListVO;
import com.power.jfsb.entity.JfSbSpVO;
import com.power.jfsb.entity.JfsbStatusEnum;
import com.power.jfsb.entity.SbRyVO;
import com.power.jfsb.entity.UserInfoVO;
import com.power.jfss.entity.JfTotal;
import com.power.pz.entity.JfPzVO;

@Service("JfsbService")
public class JfsbService {

	@Autowired
	private DaoHelper daoHelper;

	@Autowired
	private GxpzService gxpzService;
	
	/**
	 * 获取积分项列表
	 * 
	 * @param map
	 * @return
	 */
	public List<JfPzVO> getJfx(Map<String, Object> map) {
		return daoHelper.findAll("mapper.JfShenbaoXmMapper.showPzs", map);
	}

	/**
	 * 获取被修改的值
	 * 
	 * @param id
	 *            被修改的id
	 * @return
	 */
	public JfShenbao getJfsb(int id) {

		return (JfShenbao) daoHelper.findObject(JfShenbao.class, id);
	}

	/**
	 * 列表展示
	 * 
	 * @param map
	 * @return
	 */
	public List<JfShenbao> showJfsb(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.JfShenbaoMapper.showJfsb", map,grid);
	}

	/**
	 * 申报人员列表展示
	 * 
	 * @param map
	 * @return
	 */
	public List<SbRyVO> showJfsbry(Map<String, Object> map) {
		return daoHelper.findAll("mapper.JfShenbaoRyMapper.getSbRyBySbID", map);
	}

	/**
	 * 新增
	 * 
	 * @param JfShenbao
	 * @return
	 */
	public Object newJfsb(JfShenbao jfShenbao) {
		
		
		return daoHelper.insertSelective(jfShenbao);
	}
	
	/**
	 * 添加积分申报记录和积分审批
	 * 
	 * @param JfShenbao
	 * @param rys 申报人员
	 * @param fzs 申报人员对应的分值
	 * @return
	 */
	public Object newJfsbAndSp(JfShenbao jfShenbao,String rys,String fzs) {
		AuthUserVO user = UserUtils.getAuthUser();
		String[] ryids = rys.split(",");
		String[] fz = fzs.split(",");
		
		// 插入申报记录表
		daoHelper.insertSelective(jfShenbao);
		// 添加申报审批记录
		SpjdVO dpjdVO = gxpzService.getNextNode(user.getUid(), "", 2);
		
		if(dpjdVO != null && !StringUtils.isBlank(dpjdVO.getNextUserId())){
			JfShenbaoSp _JfShenbaoSp = new JfShenbaoSp();
			_JfShenbaoSp.setSbid(jfShenbao.getId());
			_JfShenbaoSp.setSprid(dpjdVO.getNextUserId());
			_JfShenbaoSp.setSzjd(dpjdVO.getCurrentNodeIndex()+1);
			_JfShenbaoSp.setStatus("0");
			newJfsbsp(_JfShenbaoSp);
			jfShenbao.setSpjlid(_JfShenbaoSp.getId());
			jfShenbao.setDqjd(dpjdVO.getCurrentNodeIndex()+1);
			jfShenbao.setDqjdsj(new Date());
			editJfsb(jfShenbao); 
		}
		
		// 插入申报人员记录
		if(ryids != null && ryids.length>0){
			for(int i=0;i<ryids.length;i++){
				JfShenbaoRy _JfShenbaoRy = new JfShenbaoRy();
				_JfShenbaoRy.setUserid(ryids[i]);
				_JfShenbaoRy.setFz(new BigDecimal(fz[i]));
				_JfShenbaoRy.setSbid(jfShenbao.getId());
				_JfShenbaoRy.setTjsj(new Date());
				newJfsbry(_JfShenbaoRy);
			}
		}
		return null;
	}
	
	/**
	 * 新增申报审批
	 * 
	 * @param JfShenbao
	 * @return
	 */
	public Object newJfsbsp(JfShenbaoSp jfShenbaoSp) {
		return daoHelper.insertSelective(jfShenbaoSp);
	}

	/**
	 * 修改
	 * 
	 * @param JfShenbao
	 * @return
	 */
	public Object editJfsb(JfShenbao jfShenbao) {
		Integer row =  daoHelper.updateSelective(jfShenbao);
		//计算积分
		if(jfShenbao.getStatus().equals(JfsbStatusEnum.CHENGGONG.getIndex()+"")){//积分申报通过
			//调用过程计算汇总积分
			Map<String, Object> param=Maps.newHashMap();
			param.put("sbid", jfShenbao.getId());
			param.put("sblx", jfShenbao.getJflxid());
			param.put("ssid", 0);//区别积分申报
			daoHelper.update("mapper.JfTjJcxxMapper.sp_tj_jfsb",param);
			
			
			List<JbNewsVO> news = daoHelper.findAll("mapper.JfShenbaoRyMapper.getSbNewsInfo", jfShenbao.getId());
			StringBuffer sbinfo = new StringBuffer();
			for(JbNewsVO JbNewsVO:news){
				sbinfo.append(JbNewsVO.getRyname()+"对应分值"+JbNewsVO.getFz()+"分");
			}
			// 插入公告公示积分申报成功提示
			BaseNews baseNews = new BaseNews();
			baseNews.setNewsid(daoHelper.findTableKeyUUID());
			baseNews.setCategory("101003");
			baseNews.setTypeid(2);
			baseNews.setDeletemark(0);
			baseNews.setEnabledmark(1);
			baseNews.setCreatedate(new Date());
			baseNews.setReleasetime(new Date());
			baseNews.setParentid(news.get(0).getDeptid());
			baseNews.setFullhead(news.get(0).getDeptname()+news.get(0).getSbrname()+"申报的积分"+news.get(0).getZongfen()+"分"+"已通过审核"+sbinfo.toString());
			daoHelper.insertSelective(baseNews);
			
		}
		return row;
	}
	
	/**
	 * 修改申报记录
	 * 
	 * @param JfShenbao
	 * @return
	 */
	public Object editJfsbAndRy(JfShenbao jfShenbao,String rys,String fzs) {
		String[] ryids = rys.split(",");
		String[] fz = fzs.split(",");
		// 修改申报记录
		daoHelper.updateSelective(jfShenbao);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("sbid", jfShenbao.getId());
		List<SbRyVO> ycry = showJfsbry(map);
		
		if(ycry!=null && ycry.size()>0){
			for(SbRyVO sbRyVO:ycry){
				delSbRy(sbRyVO.getId());
			}
			
		}
		
		if(ryids!=null&& ryids.length>0){
			for(int i=0;i<ryids.length;i++){
				JfShenbaoRy _JfShenbaoRy = new JfShenbaoRy();
				_JfShenbaoRy.setUserid(ryids[i]);
				_JfShenbaoRy.setFz(new BigDecimal(fz[i]));
				_JfShenbaoRy.setSbid(jfShenbao.getId());
				_JfShenbaoRy.setTjsj(new Date());
				newJfsbry(_JfShenbaoRy);
			}
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delSb(Integer id) {
		daoHelper.delete(JfShenbao.class, id);
		//TODO 删除积分申报人员对应表
		delSbRyBySbid(id);
		//TODO 删除积分申报审批表
		delSbSpBySbid(id);
	}
	
	/**
	 * 通过积分记录ID删除申报人员记录
	 * 
	 * @param id
	 */
	public void delSbRyBySbid(Integer id) {
		daoHelper.delete("mapper.JfShenbaoRyMapper.deleteBySbid", id);
	}
	
	/**
	 * 通过积分记录ID删除申报审批记录
	 * 
	 * @param id
	 */
	public void delSbSpBySbid(Integer id) {
		daoHelper.delete("mapper.JfShenbaoSpMapper.deleteBySbid", id);
	}
	

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delSbRy(Integer id) {
		daoHelper.delete(JfShenbaoRy.class, id);
	}
	
	/**
	 * 新增积分申报人员
	 * 
	 * @param jfShenbaoRy
	 * @return
	 */
	public Object newJfsbry(JfShenbaoRy jfShenbaoRy) {
		return daoHelper.insertSelective(jfShenbaoRy);
	}
	
	/**
	 * 获取积分申报审批列表
	 * 
	 * @param map
	 * @return
	 */
	public List<JfSbSpVO> showJfsbsp(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.JfShenbaoSpMapper.getJfSbSp", map,grid);
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
	public Object editJfsbspSubmit(JfShenbaoSp jfShenbaoSp) {
		
		AuthUserVO user = UserUtils.getAuthUser();
		//修改申报记录信息
		daoHelper.updateSelective(jfShenbaoSp);
		
		// 修改积分审批
		JfShenbao jfShenbao = getJfsb(jfShenbaoSp.getSbid());
		SpjdVO dpjdVO = gxpzService.getNextNode(jfShenbao.getSbrid(), user.getUid(), 2);
		
		if(dpjdVO != null && (!StringUtils.isBlank(dpjdVO.getNextUserId()) && !dpjdVO.getNextUserId().equals("0")) && jfShenbaoSp.getSpjg().equals("1")){
			JfShenbaoSp _JfShenbaoSp = new JfShenbaoSp();
			_JfShenbaoSp.setSbid(jfShenbao.getId());
			_JfShenbaoSp.setSprid(dpjdVO.getNextUserId());
			_JfShenbaoSp.setSzjd(dpjdVO.getCurrentNodeIndex()+1);
			_JfShenbaoSp.setStatus("0");
			newJfsbsp(_JfShenbaoSp);
			jfShenbao.setStatus("2");
			jfShenbao.setSpjlid(_JfShenbaoSp.getId());
			jfShenbao.setDqjdsj(new Date());
			jfShenbao.setDqjd(dpjdVO.getCurrentNodeIndex()+1);
			editJfsb(jfShenbao);
		}else if(jfShenbaoSp.getSpjg().equals("1")){
			jfShenbao.setStatus("3");
			editJfsb(jfShenbao);
			
		}else if(jfShenbaoSp.getSpjg().equals("0")){
			jfShenbao.setStatus("4");
			editJfsb(jfShenbao);
		}
		return null;
	}

	/**
	 * 获取积分项
	 * 
	 * @param id
	 *            积分项的id
	 * @return
	 */
	public JfShenbaoXm getJfx(int id) {

		return (JfShenbaoXm) daoHelper.findObject(JfShenbaoXm.class, id);
	}
	

	/**
	 * 审批流程列表展示
	 * 
	 * @param map
	 * @return
	 */
	public List<JfShenbaoSp> showJfsbLc(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.JfShenbaoSpMapper.getSbSpLc", map,grid);
	}
	
	/**
	 * 查询用户申报所得总积分数
	 * @param userid 用户编号(必选)
	 * @param kssj   开始时间 (可选) 格式 "YYYY-MM-dd HH:ss"
	 * @param jssj   结束时间(可选)
	 * @return
	 */
	public JfTotal getUserJfSbTotal(String userid,String kssj,String jssj){
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
		return (JfTotal)daoHelper.findObject("mapper.JfShenbaoRyMapper.getUserJfSbTotal", map);
	}
	
	/**
	 * 查询用户申报所得积分明细
	 * @param userid 用户编号(必选)
	 * @param kssj   开始时间 (可选) 格式 "YYYY-MM-dd HH:ss"
	 * @param jssj   结束时间(可选)
	 * @param jflx   积分类型 (可选)1.业务积分 2.管理积分
	 * @return
	 */
	public List<JFSbTotalList> getUserJfSbTotalList(String userid,String kssj,String jssj,String jflx){
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
		if(!StringUtils.isBlank(jflx)){
			if(jflx.equals("1")){
				map.put("jflx", "业务积分");
			}else if(jflx.equals("2")){
				map.put("jflx", "管理积分");
			}
			
		}
		return daoHelper.findAll("mapper.JfShenbaoRyMapper.getUserJfSbList", map);
	}
	
	/**
	 * 获取申报待审批数量
	 * @param uid
	 * @return
	 */
	public Integer getSbSpSl(String uid){
		return (Integer)daoHelper.findObject("mapper.JfShenbaoSpMapper.getSbSpSl", uid);
	}
	

	/**
	 * 获取用户积分项
	 * 
	 * @param map
	 * @return
	 */
	public List<JfShenbaoXm> getUserGwJfx(Map<String, Object> map) {
		return daoHelper.findAll("mapper.JfShenbaoXmMapper.getUserGwJfx", map);
	}
	
	
	/**
	 * 通过用户ID获取用户信息
	 * @param uid
	 * @return
	 */
	public UserInfoVO getUserInfoByUID(String uid){
		return (UserInfoVO)daoHelper.findObject("mapper.JfShenbaoMapper.getUserInfoByUID", uid);
	}
	
	/**
	 * 列表展示
	 * 
	 * @param map
	 * @return
	 */
	public List<JfSbListVO> showJfsbList(Map<String, Object> map,JqGrid grid) {
		return daoHelper.findRows("mapper.JfShenbaoRyMapper.getSbList", map,grid);
	}

}
