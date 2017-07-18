package com.power.jfgl.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.power.base.sys.entity.KeyValue;
import com.power.common.entity.JfMzcp;
import com.power.common.entity.JfMzcpJgmx;
import com.power.common.entity.JfMzcpMx;
import com.power.common.entity.JfMzcpRymx;
import com.power.common.entity.SysUser;
import com.power.common.mybatis.DaoHelper;
import com.power.common.mybatis.page.JqGrid;
import com.power.index.service.IndexService;
import com.power.jfgl.entity.JfMzcpJgMxVO;
import com.power.jfgl.entity.JfMzcpRyMxVO;
/**
 * 民主测评
 * @author Administrator
 *
 */
@Service("mzcpService")
public class MzcpService {
	private Logger log = LoggerFactory.getLogger(getClass()); 
	@Autowired
	private DaoHelper daoHelper;
	@Autowired
	private IndexService indexService;
	/**
	 * 积分列表
	 * @param map
	 * @param grid
	 */
	public void getMzcpList(Map<String, Object> map, JqGrid grid) {
		daoHelper.findRows("mapper.JfMzcpMapper.getMzcpList", map, grid);
	}
	/**
	 * 民主测评，
	 * 部门全部人员，都参加测评
	 * @param mzcp
	 * @param dws
	 */
	public void newMzcp(JfMzcp mzcp, String dws) {
		List<String> dwList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(dws);
		List<String> dwmcList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(mzcp.getCpdw());
		mzcp.setCpdwgs(dwList.size());
		if(mzcp.getXh()==null){
			daoHelper.insert(mzcp);
			List<SysUser> userList = indexService.getUser("IndexUser");
			//添加单位明细
			for (int i=0;i<dwList.size();i++) {
				JfMzcpMx cpmx = new JfMzcpMx();
				cpmx.setCpxh(mzcp.getXh());
				cpmx.setDwxh(dwList.get(i));
				cpmx.setDwmc(dwmcList.get(i));
				cpmx.setZt(0);//初始化
				daoHelper.insert(cpmx);
				//添加测评单位人员
				int cprs = 0;
				StringBuffer cprmc = new StringBuffer();
				for (SysUser user : userList) {
					if(user.getDepartmentid().equals(dwList.get(i))){
						//
						cprs++;
						cprmc.append(",").append(user.getRealname());
						//
						JfMzcpRymx rymx = new JfMzcpRymx();
						rymx.setCpxh(mzcp.getXh());
						rymx.setCpmxxh(cpmx.getXh());
						rymx.setXh(daoHelper.findTableKeyUUID());//key
						rymx.setRyxh(user.getUserid());
						rymx.setRymc(user.getRealname());
						rymx.setLrsj(mzcp.getLrsj());
						rymx.setCpzt(0);//测评初始化
						daoHelper.insert(rymx);
					}
				}
				//修改mx 信息
				JfMzcpMx mx = new JfMzcpMx();
				mx.setXh(cpmx.getXh());
				mx.setMzcprs(cprs);
				if(cprmc.length()>1){//有测评人员
					mx.setQzcpmc(cprmc.deleteCharAt(0).toString());
				}
				daoHelper.updateSelective(mx);
			}
		}else{//修改
			//TODO 删除原始信息
			daoHelper.updateSelective(mzcp);
		}
		
	}
	/**
	 * 删除 修改状态
	 * @param key
	 */
	public void delMzcp(Integer key) {
		JfMzcp mzcp = new JfMzcp();
		mzcp.setXh(key);
		mzcp.setZt(0);//已删除
		daoHelper.updateSelective(mzcp);
	}
	public JfMzcp getMzcpForm(Integer key) {
		return daoHelper.findObject(JfMzcp.class, key);
	}
	/**
	 *  获取测评单位信息列表
	 * @param map
	 * @param grid
	 */
	public void getMzcpDwList(Map<String, Object> map, JqGrid grid) {
		daoHelper.findRows("mapper.JfMzcpMxMapper.getMzcpDwList", map, grid);
	}
	/**
	 * 测评人员
	 * @param map
	 */
	public List<JfMzcpRyMxVO> getMzcpDwRyList(Map<String, Object> map) {
		return daoHelper.findAll("mapper.JfMzcpRymxMapper.getMzcpDwRyList", map);
	}
	/**
	 * 民主测评人员明细
	 * @param userIds
	 * @param userNames 
	 * @param cpxh
	 * @param cpmxxh
	 */
	public void newMzcpRymx(String userIds, String userNames, Integer cpxh, Integer cpmxxh) {
		//删除原始信息，
		daoHelper.update("mapper.JfMzcpRymxMapper.delRymxByCpmxxh", cpmxxh);
		//添加新纪录
		List<String> userList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(userIds);
		//测评人员姓名列表
		List<String> nameList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(userNames);
		Date d = new Date();
		for (int i=0;i<userList.size();i++) {
			JfMzcpRymx rymx = new JfMzcpRymx();
			rymx.setCpxh(cpxh);
			rymx.setCpmxxh(cpmxxh);
			rymx.setXh(daoHelper.findTableKeyUUID());//key
			rymx.setRyxh(userList.get(i));
			rymx.setRymc(nameList.get(i));
			rymx.setLrsj(d);
			rymx.setCpzt(0);//测评初始化
			daoHelper.insert(rymx);
		}
		//更新明细表
		JfMzcpMx mx = new JfMzcpMx();
		mx.setXh(cpmxxh);
		mx.setMzcprs(userList.size());
		mx.setQzcpmc(userNames);
		daoHelper.updateSelective(mx);
	}
	public List<KeyValue> getRyList(Map<String, Object> param) {
		return daoHelper.findAll("mapper.JfMzcpRymxMapper.getRyList",param);
	}
	/**
	 * 群众测评
	 * 领导测评
	 * @param userIds
	 * @param userNames
	 * @param cpxh
	 * @param cpmxxh
	 * @param rys 选择的人员
	 * @param f 1群众2领导
	 * @param qzs 权重列表
	 */
	public void newMzcpQzmx(String userIds, String userNames, Integer cpxh,Integer cpmxxh, String rys, Integer f, String qzs) {
		//删除原始信息，
		Map<String,Object> param =Maps.newHashMap();
		param.put("pjrIds", "'"+rys.replaceAll(",", "','")+"'");
		param.put("cpmxxh", cpmxxh);
		param.put("rybz", f);//群众
		daoHelper.update("mapper.JfMzcpJgmxMapper.delQzcpByCpmxxh", param);
		//选择人员
		List<String> userList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(userIds);
		List<String> nameList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(userNames);
		//测评人员姓名列表
		List<String> cpryList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(rys);
		List<String> qzList = Lists.newLinkedList();
		if(f==2){
			qzList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(qzs);
		}
//		Date d = new Date();
		for (int j = 0; j < cpryList.size(); j++) {
			//
			for (int i=0;i<userList.size();i++) {
				JfMzcpJgmx jgmx = new JfMzcpJgmx();
				jgmx.setCpxh(cpxh);
				jgmx.setCpmxxh(cpmxxh);
				jgmx.setXh(daoHelper.findTableKeyUUID());//key
				jgmx.setBpjrxh(cpryList.get(j));
				jgmx.setPjrxh(userList.get(i));
				jgmx.setPjrmc(nameList.get(i));
				jgmx.setRybz(f);//群众
				if(f==2){
					jgmx.setPjrqz(new BigDecimal(qzList.get(i)));//
				}
				daoHelper.insert(jgmx);
			}
			//更新明细表
			JfMzcpRymx mx = new JfMzcpRymx();
			mx.setXh(cpryList.get(j));
			if(f==1){
				mx.setQzcprs(userList.size());
			}else{
				mx.setLdcprs(userList.size());
			}
			daoHelper.updateSelective(mx);
		}
		//TODO 更新 jf_mzcp_mx 状态信息，是否全部设置群众测评/领导测评
	}
	/**
	 * 测评人员/结果明细
	 * @param map
	 * @return
	 */
	public List<JfMzcpJgMxVO> getMzcpJgmxList(Map<String, Object> map) {
		return daoHelper.findAll("mapper.JfMzcpJgmxMapper.getMzcpJgmxList", map);
	}
	/**
	 * 测评任务信息列表
	 * @param map
	 * @param grid
	 */
	public void getCpRwList(Map<String, Object> map, JqGrid grid) {
		daoHelper.findRows("mapper.JfMzcpJgmxMapper.getCpRwList", map,grid);
		
	}
	/**
	 * 群众/领导评价列表
	 * @param map
	 * @return
	 */
	public List<JfMzcpJgMxVO> getCpJgMx(Map<String, Object> map) {
		return daoHelper.findAll("mapper.JfMzcpJgmxMapper.getCpJgMx", map);
	}
	/**
	 * 保存测评结果
	 * @param jgx
	 */
	public void editCpjgSubmit(List<JfMzcpJgmx> jgx) {
		Date d = new Date();
		for (JfMzcpJgmx jg : jgx) {
			jg.setPjsj(d);
			daoHelper.updateSelective(jg);
		}
		//调用过程计算分数
		if(jgx.size()>0){
			editJg(jgx.get(0).getCpmxxh());
		}
	}
	public void editJg(Integer cpmxxh){
		log.debug("{}-计算测评积分",cpmxxh);
		daoHelper.update("mapper.JfMzcpJgmxMapper.sp_tjmzcpdf", cpmxxh);
	}
	/**
	 * 民主测评待处理
	 * @param uid
	 * @return
	 */
	public Integer getMzcpRw(String uid) {
		return (Integer) daoHelper.findObject("mapper.JfMzcpJgmxMapper.getMzcpRw", uid);
	}
	/**
	 * 个人民主测评得分
	 * @param uid
	 * @param kssj
	 * @param jssj
	 * @return
	 */
	public BigDecimal getMzcpDf(String uid, String kssj,String jssj) {
		Map<String,Object> map = new  HashMap<String,Object>();
		
		if(StringUtils.isBlank(uid)){
			return null;
		}
		
		map.put("userid", uid);
		
		if(!StringUtils.isBlank(kssj)){
			map.put("kssj", kssj);
		}
		
		if(!StringUtils.isBlank(jssj)){
			map.put("jssj", jssj);
		}
		return (BigDecimal)daoHelper.findObject("mapper.JfMzcpRymxMapper.getMzcpDf", map);
	}
	public void getCprwWczt(Integer cpmxxh, JqGrid grid) {
		daoHelper.findRows("mapper.JfMzcpJgmxMapper.getCprwWczt", cpmxxh,grid);
	}
	
}
