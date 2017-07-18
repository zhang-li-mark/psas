package com.power.jfgl.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.service.DeptService;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.JfMzcp;
import com.power.common.entity.JfMzcpJgmx;
import com.power.common.entity.SysDepartment;
import com.power.common.entity.SysUser;
import com.power.common.mybatis.page.JqGrid;
import com.power.common.springmvc.ExtReturn;
import com.power.index.service.IndexService;
import com.power.jfgl.service.MzcpService;

/**
 * 民主测评管理
 * 项目名称：psas <br>
 * 类名称：MzcpController <br>
 * 创建时间：2017-5-1 上午10:39:55 <br>
 * @version 1.0
 */
@Controller
@RequestMapping("mzcp")
public class MzcpController {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private MzcpService macpService;
	@Autowired
	private IndexService indexService;
	@Autowired
	private DeptService deptService;
	
	private String MZCP="jf/mzcp/";
	private String CPKQ="jf/mzcp/cpkq/";
	private String CPRW="jf/mzcp/cprw/";
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		CustomDateEditor editor=new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true);
		binder.registerCustomEditor(Date.class,editor);
	}
	/**
	 * 民主测评首页
	 * @return
	 */
	@RequestMapping("mzcpIndex")
	public String mzcpIndex(){
		return MZCP+"index";
	}
	/**
	 * 民主测评首页
	 * @param colname
	 * @param value
	 * @param grid
	 * @return
	 */
	@RequestMapping("getMzcpList")
	@ResponseBody
	public Object getMzcpList(@RequestParam(value="condition",required=false)String colname,
			@RequestParam(value="keyword",required=false)String value,JqGrid grid){
		Map<String,Object> map = Maps.newHashMap();
		if(colname!=null){
			map.put(colname,value);
		}
//		AuthUserVO user = UserUtils.getAuthUser();
		macpService.getMzcpList(map,grid);
		return grid;
	}
	/**
	 * 新增
	 * @return
	 * */
	@RequestMapping("newMzcp")
	public String newMzcp(){
		return MZCP+"new";
	}
	/**
	 * 民主测评选择单位
	 * @return
	 * */
	@RequestMapping("setCpdw")
	public String setCpdw(){
		return MZCP+"setCpdw";
	}
	
	/**
	 * 获取数据
	 * @param key
	 * @return
	 * */
	@RequestMapping("getMzcpForm")
	@ResponseBody
	public Object getMzcpForm(@RequestParam(value="keyValue")Integer key){
		JfMzcp mzcp=macpService.getMzcpForm(key);
		return new ExtReturn(true, mzcp);
	}
	/**
	 * 新增/编辑提交
	 * @param mzcp
	 * @param dws
	 * @return
	 */
	@RequestMapping(value="newMzcpSubmit",method = RequestMethod.POST)
	@ResponseBody
	public Object newMzcpSubmit(JfMzcp mzcp,@RequestParam("dws")String dws){
		AuthUserVO user = UserUtils.getAuthUser();
		mzcp.setLrsj(new Date());
		mzcp.setLryxh(user.getUid());
		mzcp.setLrymc(user.getRealName());
		try {
			macpService.newMzcp(mzcp,dws);//
			return new ExtReturn(true,"处理成功");
		} catch (Exception e) {
			log.error("newMzcpSubmit error:{}"+e);
			return new ExtReturn(false,"操作失败");
		}
	}
	/**
	 *删除
	 *@param key
	 *@return
	 * */
	@RequestMapping(value="delMzcp",method = RequestMethod.POST)
	@ResponseBody
	public Object delMzcp(@RequestParam("keyValue")Integer key){
		try {	
			macpService.delMzcp(key);
		} catch (Exception e) {
			log.error("delUser error:{}"+e);
			return new ExtReturn(false,"操作失败");
		}
		return new ExtReturn(true,"操作成功");
	}
	
	////////// 开启测评
	/**
	 * 单位开启测评
	 * @return
	 */
	@RequestMapping("cpkqIndex")
	public String cpkqIndex(){
		return CPKQ+"index";
	}
	/**
	 * 测评单位列表
	 * @param colname
	 * @param value
	 * @param grid
	 * @return
	 */
	@RequestMapping("getMzcpDwList")
	@ResponseBody
	public Object getMzcpDwList(@RequestParam(value="condition",required=false)String colname,
			@RequestParam(value="keyword",required=false)String value,JqGrid grid){
		Map<String,Object> map = Maps.newHashMap();
		if(colname!=null){
			map.put(colname,value);
		}
//		map.put(key, value)//
		AuthUserVO user = UserUtils.getAuthUser();
		indexService.setDeptChildParam(user,map);
		macpService.getMzcpDwList(map,grid);
		return grid;
	}
	/**
	 * 设置测评人员
	 * @return
	 * */
	@RequestMapping("newCpDwRy")
	public String newCpDwRy(){
		return CPKQ+"newCpDwRy";
	}
	/**
	 * 当前部门，下属所有用户
	 * @return
	 */
	@RequestMapping("getAllUser")
	@ResponseBody
	public Object getAllUser(){
		AuthUserVO user = UserUtils.getAuthUser();
		List<SysUser> userList = indexService.getUser("IndexUser");
		if (StringUtils.isBlank(user.getDeptId())) {//全部部门
			return userList;
		}
		List<SysUser> reList = Lists.newLinkedList();
		List<SysDepartment> deptList = deptService.getAllChildren(user.getOrgId(), user.getDeptId());// 子部门
		//只筛选本部门用户
		for (SysDepartment dept : deptList) {
			final String deptId = dept.getDepartmentid();
			List<SysUser> uList = Lists.newLinkedList(Collections2.filter(userList, new Predicate<SysUser>() {
	            public boolean apply(SysUser user) {
	                return user.getDepartmentid().equals(deptId);
	            }
	        }));
			reList.addAll(uList);
		}
		return reList;
	}
	/**
	 * 测评单位 
	 * 人员列表
	 * @param colname
	 * @param value
	 * @param grid
	 * @return
	 */
	@RequestMapping("getMzcpDwRyList")
	@ResponseBody
	public Object getMzcpDwRyList(@RequestParam(value="condition",required=false)String colname,
			@RequestParam(value="keyword",required=false)String value,
			@RequestParam(value="f",required=false)Integer f,
			@RequestParam("cpxh")Integer cpxh,
			@RequestParam("cpmxxh")Integer cpmxxh,
			@RequestParam(value="bpjrxh",required=false)String bpjrxh){
		Map<String,Object> map = Maps.newHashMap();
		if(StringUtils.isNoneBlank(colname)){
			map.put(colname,value);
		}
		map.put("cpmxxh",cpmxxh);
		if(StringUtils.isNoneBlank(bpjrxh)){
			map.put("bpjrxh", "'"+bpjrxh.replaceAll(",", "','")+"'");
		}
//		map.put("cpxh",value);
//		AuthUserVO user = UserUtils.getAuthUser();
//		indexService.setDeptChildParam(user,map);
		if(f!=null){//群众/领导
			map.put("f", f);
			return macpService.getMzcpJgmxList(map);
		}
		return macpService.getMzcpDwRyList(map);
	}
	/**
	 * 选择测评人员
	 * @return
	 * */
	@RequestMapping("setCpry")
	public String setCpry(){
		return CPKQ+"setCpry";
	}
	/**
	 * 领导测评设置列表
	 * @return
	 * */
	@RequestMapping("setLdcp")
	public String setLdcp(){
		return CPKQ+"setLdcp";
	}
	
	/**
	 * 新增/编辑提交
	 * @param userIds 选择的用户
	 * @param userNames
	 * @param cpxh
	 * @param cpmxxh
	 * @param f 
	 * @param rys 被评价人员xh jf_mzcp_rymx
	 * @return
	 */
	@RequestMapping(value="setCprySubmit",method = RequestMethod.POST)
	@ResponseBody
	public Object setCprySubmit(@RequestParam("userIds")String userIds,
			@RequestParam("userNames")String userNames,
			@RequestParam("cpxh")Integer cpxh,
			@RequestParam("cpmxxh")Integer cpmxxh,
			@RequestParam("f")Integer f,
			@RequestParam(value="qzs",required=false)String qzs,
			@RequestParam(value="rys",required=false)String rys){
		try {
			if(f==0){
				macpService.newMzcpRymx(userIds,userNames,cpxh,cpmxxh);//
				return new ExtReturn(true,"处理成功");
			}else{//群众领导
				macpService.newMzcpQzmx(userIds,userNames,cpxh,cpmxxh,rys,f,qzs);//
				return new ExtReturn(true,"处理成功");
			}
		} catch (Exception e) {
			log.error("newMzcpSubmit error:{}"+e);
			return new ExtReturn(false,"操作失败");
		}
	}
	/**
	 * 下属全部部门 
	 * @param deptId
	 * @return 全部部门序号 逗号分隔
	 */
	@RequestMapping("getAllDeptChildren")
	@ResponseBody
	public Object getAllDeptChildren(@RequestParam("deptId")String deptId){
		List<SysDepartment> deptList = deptService.getAllChildren("all", deptId);
		StringBuffer depts = new StringBuffer();
		for (SysDepartment dept : deptList) {
			depts.append(",").append(dept.getDepartmentid()).append("");
		}
		return new ExtReturn(true,depts.deleteCharAt(0).toString());
	}
	/**
	 * 选择的人员
	 * @param f //0人员，1群众，2领导
	 * @return
	 */
	@RequestMapping("getRyList")
	@ResponseBody
	public Object getRyList(@RequestParam("cpxh")Integer cpxh,
			@RequestParam("cpmxxh")Integer cpmxxh,@RequestParam("f")Integer f,
			@RequestParam("xhs")String xhs){
		Map<String, Object> param =Maps.newHashMap();
		param.put("cpxh", cpxh);
		param.put("cpmxxh", cpmxxh);
		param.put("f", f);
		param.put("bpjrxh", "'"+xhs.replaceAll(",", "','")+"'");
		return macpService.getRyList(param);
	}
	////////////////////////////////////
	/**
	 * 测评任务列表
	 * @return
	 */
	@RequestMapping("cprwIndex")
	public String cprwIndex(){
		return CPRW+"index";
	}
	/**
	 * 测评任务
	 * @param colname
	 * @param value
	 * @param grid
	 * @return
	 */
	@RequestMapping("getCpRwList")
	@ResponseBody
	public Object getCpRwList(@RequestParam(value="condition",required=false)String colname,
			@RequestParam(value="keyword",required=false)String value,JqGrid grid){
		Map<String,Object> map = Maps.newHashMap();
		if(colname!=null){
			map.put(colname,value);
		}
		AuthUserVO user = UserUtils.getAuthUser();
//		indexService.setDeptChildParam(user,map);
		map.put("pjrxh", user.getUid());
		macpService.getCpRwList(map,grid);
		return grid;
	}
	/**
	 * 开始测评
	 * @return
	 * */
	@RequestMapping("startCp")
	public String startCp(
			@RequestParam(value="detail",required=false)Integer detail,
			@RequestParam("cpxh")Integer cpxh,
			@RequestParam("cpmxxh")Integer cpmxxh){
		if(detail!=null){
			return CPRW+"cpDetail";
		}
		return CPRW+"startCp";
	}
	/**
	 * 获取测评结果信息
	 * @param colname
	 * @param value
	 * @param grid
	 * @return
	 */
	@RequestMapping("getCpJgMx")
	@ResponseBody
	public Object getCpJgMx(@RequestParam("cpmxxh")Integer cpmxxh,
			@RequestParam("rybz")Integer rybz){
		Map<String,Object> map = Maps.newHashMap();
		map.put("cpmxxh", cpmxxh);
		map.put("f", rybz);
		AuthUserVO user = UserUtils.getAuthUser();
		map.put("pjrxh", user.getUid());
		return macpService.getCpJgMx(map);
	}
	
	/**
	 * 新增/编辑提交
	 * @param mzcp
	 * @param dws
	 * @return
	 */
	@RequestMapping(value="editCpjgSubmit",method = RequestMethod.POST)
	@ResponseBody
	public Object editCpjgSubmit(@RequestParam("jsonArr")String jsonArr){
		try {
			List<JfMzcpJgmx> jgx = JSON.parseArray(jsonArr, JfMzcpJgmx.class);
			macpService.editCpjgSubmit(jgx);//
			return new ExtReturn(true,"处理成功");
		} catch (Exception e) {
			log.error("editCpjgSubmit error:{}"+e);
			return new ExtReturn(false,"操作失败");
		}
	}
	////////测评任务完成状态
	/**
	 * 测评任务列表
	 * @return
	 */
	@RequestMapping("cprwWcztIndex")
	public String cprwWcztIndex(){
		return CPKQ+"wcztIndex";
	}
	/**
	 * 测评任务 完成状态
	 * @param grid
	 * @return
	 */
	@RequestMapping("getCprwWczt")
	@ResponseBody
	public Object getCprwWczt(JqGrid grid,@RequestParam("cpmxxh")Integer cpmxxh){
		macpService.getCprwWczt(cpmxxh,grid);
		return grid;
	}
	
}
