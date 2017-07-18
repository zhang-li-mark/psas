package com.power.jfgl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.JfPzRygx;
import com.power.common.entity.JfTjBzjfmx;
import com.power.common.entity.SysUser;
import com.power.common.mybatis.DaoHelper;
import com.power.common.mybatis.page.JqGrid;
import com.power.jfgl.entity.SpjdVO;

@Service("gxpzService")
public class GxpzService {
	@Autowired
	private DaoHelper daoHelper;
	
	/**
	 * 关系配置信息列表
	 * @param map
	 * @param grid
	 */
	public void getGxpzList(Map<String, Object> map, JqGrid grid) {
		daoHelper.findRows("mapper.JfPzRygxMapper.getGxpzList", map, grid);
	}
	/**
	 * 请假流程关系处理
	 * @param rygx
	 * @param xhs
	 * @param f 
	 */
	public void newQjlcSubmit(JfPzRygx rygx, String xhs, Integer f) {
		List<String> userList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(xhs);
		//添加新纪录
		for (int i=0;i<userList.size();i++) {
			JfPzRygx tmp = daoHelper.findObject(JfPzRygx.class, userList.get(i));
			if(tmp==null){
				rygx.setRyxh(userList.get(i));
				daoHelper.insert(rygx);
			}else{
				tmp.setLssj(new Date());
				tmp.setLryxh(rygx.getLryxh());
				tmp.setLrymc(rygx.getLrymc());
				tmp.setQjqbryxh(rygx.getQjqbryxh());
				tmp.setQjsjgs(rygx.getQjsjgs());
				tmp.setQjcjgx(rygx.getQjcjgx());
				tmp.setQbryxh(rygx.getQbryxh());
				tmp.setSjgs(rygx.getSjgs());
				tmp.setCjgx(rygx.getCjgx());
				//选择行更新
				daoHelper.updateSelective(tmp);
			}
		}
	}
	public SysUser getUserById(String userid){
//		List<SysUser> userList = indexService.getUser("IndexUser");
//		if (StringUtils.isBlank(user.getDeptId())) {//全部部门
//			return userList;
//		}
		return null;
	}
	/**
	 * 获取流程执行的下一级节点
	 * @param owner 申请人 userid
	 * @param currentNode 当前节点userid
	 * @param flag 1请假，2积分申报
	 * @return
	 */
	public SpjdVO getNextNode(String owner,String currentNode,Integer flag){
		//
		JfPzRygx gx = daoHelper.findObject(JfPzRygx.class, owner);
		if(gx==null){
			return null;
		}
		List<String> shr=Lists.newArrayList();
		//
		SpjdVO re = new SpjdVO();
		//
		if(flag==1){
			shr = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(gx.getQjqbryxh());
			re.setAllIndex(gx.getQjsjgs());
		}else if(flag==2){
			shr = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(gx.getQbryxh());
			re.setAllIndex(gx.getSjgs());
		}
		if(StringUtils.isBlank(currentNode)){//第一次
			re.setNextUserId(shr.get(0));
			re.setCurrentNodeIndex(0);
			return re;
		}
		for (int i = 0; i < shr.size(); i++) {
			if(currentNode.equals(shr.get(i))){
				if(i+1<re.getAllIndex()){
					re.setNextUserId(shr.get(i+1));
				}else{
					re.setNextUserId("0");//最终节点
				}
				re.setCurrentNodeIndex(i+1);//当前节点
			}
		}
		//审批节点
		return re;
	}
	////////////////////人员表彰信息列表
	//
	public void getBzxxList(Map<String, Object> param, JqGrid grid) {
		daoHelper.findRows("mapper.JfTjBzjfmxMapper.getBzxxList", param, grid);
		
	}
	public JfTjBzjfmx getBzxxForm(Long key) {
		return daoHelper.findObject(JfTjBzjfmx.class, key);
	}
	public void newBzxx(JfTjBzjfmx jfmx) {
		if(jfmx.getXh()==null){//new
			daoHelper.insert(jfmx);
		}else{
			daoHelper.updateSelective(jfmx);
		}
	}
	public void delBzxx(Long key) {
		daoHelper.delete(JfTjBzjfmx.class, key);
	}
	/**
	 * 添加表彰信息
	 * @param ryxh
	 * @param rymc
	 * @param bzxxList
	 * @param delArr
	 */
	public void newBzxxList(String ryxh, String rymc, List<JfTjBzjfmx> bzxxList, String delArr) {
		// del已有的表彰信息
		List<String> delBzList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(delArr);
		for (String bzxh : delBzList) {
			delBzxx(Long.parseLong(bzxh));
		}
		AuthUserVO user = UserUtils.getAuthUser();
		Date d = new Date();
		for (JfTjBzjfmx bzxx : bzxxList) {
			bzxx.setRyxh(ryxh);
			bzxx.setRymc(rymc);
			bzxx.setLrsj(d);
			bzxx.setLryxh(user.getUid());
			bzxx.setLrymc(user.getRealName());
			if(bzxx.getXh()<0){
				daoHelper.insert(bzxx);
			}else{
				daoHelper.updateSelective(bzxx);
			}
		}
		//更新表彰积分
		daoHelper.update("mapper.JfTjBzjfmxMapper.bzjfTj",ryxh);
	}

}
