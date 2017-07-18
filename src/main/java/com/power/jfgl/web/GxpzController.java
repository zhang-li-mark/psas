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
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.service.DeptService;
import com.power.base.sys.service.DictService;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.JfPzRygx;
import com.power.common.entity.JfTjBzjfmx;
import com.power.common.entity.SysDict;
import com.power.common.mybatis.page.JqGrid;
import com.power.common.springmvc.ExtReturn;
import com.power.index.service.IndexService;
import com.power.jfgl.service.GxpzService;

/**
 * 请假/积分申报 关系配置
 * 人员表彰信息管理
 * 项目名称：psas <br>
 * 类名称：GxpzController <br>
 * @version 1.0
 */
@Controller
@RequestMapping("gxpz")
public class GxpzController {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private GxpzService gxpzService;
	@Autowired
	private IndexService indexService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private DictService dictService;
	
	private String GXPZ="jf/gxpz/";//关系配置
	private String JCXXJF="jf/gxpz/jcxxjf/";
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		CustomDateEditor editor=new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true);
		binder.registerCustomEditor(Date.class,editor);
	}
	/**
	 * 人员关系首页
	 * @return
	 */
	@RequestMapping("gxpzIndex")
	public String gxpzIndex(){
//		SpjdVO sp1 = gxpzService.getNextNode("86bc5d1db04a40bdb822094970224a08", null, 2);
//		SpjdVO sp2 = gxpzService.getNextNode("86bc5d1db04a40bdb822094970224a08", "ff223fface9d4ca6b3571ad8cb5b326c", 2);
//		SpjdVO sp3 = gxpzService.getNextNode("86bc5d1db04a40bdb822094970224a08", "f5aa870a1cc04bbfb64c80b069b7d854", 2);
//		SpjdVO sp4 = gxpzService.getNextNode("86bc5d1db04a40bdb822094970224a08", "ss", 2);
//		SpjdVO sp5 = gxpzService.getNextNode("sss", "ss", 2);
//		SpjdVO sp6 = gxpzService.getNextNode("86bc5d1db04a40bdb822094970224a08", "f5aa870a1cc04bbfb64c80b069b7d854", 1);
		return GXPZ+"index";
	}
	/**
	 * 关系 配置列表
	 * @param colname
	 * @param value
	 * @param orgId
	 * @param deptId
	 * @param grid
	 * @return
	 */
	@RequestMapping("getGxpzList")
	@ResponseBody
	public Object getGxpzList(@RequestParam(value="condition",required=false)String colname,
			@RequestParam(value="keyword",required=false)String value,
			@RequestParam(value="orgId",required=false)String orgId,
			@RequestParam(value="deptId",required=false)String deptId,
			JqGrid grid){
		Map<String,Object> map = Maps.newHashMap();
		if(StringUtils.isNoneBlank(colname)){
			map.put(colname,value);
		}
		indexService.setOrgDeptParam(orgId, deptId, map);
		gxpzService.getGxpzList(map,grid);
		return grid;
	}
	/**
	 * 请假审批设置页面
	 * @return
	 * */
	@RequestMapping("setQjsplc")
	public String setQjsplc(){
		return GXPZ+"setQjsplc";
	}
	/**
	 * 选择人员
	 * @return
	 */
	@RequestMapping("setRy")
	public String setRy(){
		return GXPZ+"setRy";
	}
	/**
	 * 关系明细
	 * @return
	 */
	@RequestMapping("gxpzMx")
	public String gxpzMx(){
		return GXPZ+"gxpzMx";
	}
	
	/**
	 * 设置关系保存
	 * @param jsonArr
	 * @param rys
	 * @param xhs 被设置人员
	 * @param rygx
	 * @return
	 */
	@RequestMapping("setQjlcSubmit")
	@ResponseBody
	public Object setQjlcSubmit(
			@RequestParam("f")Integer f,
			@RequestParam("json")String jsonArr,
			@RequestParam("rys")String rys,
			@RequestParam("xhs")String xhs,JfPzRygx rygx){
		AuthUserVO user = UserUtils.getAuthUser();
		rygx.setLssj(new Date());
		rygx.setLryxh(user.getUid());
		rygx.setLrymc(user.getRealName());
		if(f==1){
			rygx.setQjqbryxh(rys);
			rygx.setQjsjgs(rys.split(",").length);
			rygx.setQjcjgx(jsonArr);
		}else if(f==2){
			rygx.setQbryxh(rys);
			rygx.setSjgs(rys.split(",").length);
			rygx.setCjgx(jsonArr);
		}
		try {
			gxpzService.newQjlcSubmit(rygx,xhs,f);//
			return new ExtReturn(true,"处理成功");
		} catch (Exception e) {
			log.error("setQjlcSubmit error:{}"+e);
			return new ExtReturn(false,"操作失败");
		}
	}
	///////////////////////////////////基础信息积分设置
	/**
	 * 民主测评选择单位
	 * @return
	 * */
	@RequestMapping("jcxxjfszIndex")
	public String jcxxjfszIndex(Model model){
		model.addAttribute("zwlj", dictService.getDict("-104012"));
		return JCXXJF+"index";
	}
	/**
	 * 编辑提交
	 * @param mzcp
	 * @param dws
	 * @return
	 */
	@RequestMapping(value="editJfszSubmit",method = RequestMethod.POST)
	@ResponseBody
	public Object editJfszSubmit(@RequestParam("dictArr")String dictArr){
		List<SysDict> dictList = JSON.parseArray(dictArr, SysDict.class);
		try {
			for (SysDict sysDict : dictList) {
				dictService.editDict(sysDict);//
			}
			return new ExtReturn(true,"处理成功");
		} catch (Exception e) {
			log.error("editJfszSubmit error:{}"+e);
			return new ExtReturn(false,"操作失败");
		}
	}
	/**
	 * 选取资格证书
	 * @param model
	 * @return
	 */
	@RequestMapping("selZgzs")
	public String selZgzs(Model model){
		return JCXXJF+"selZgzs";
	}
	
	//////////////////// 人员表彰信息列表
	private String RYBZ = "sys/user/";
	/**
	 * 人员表彰信息列表
	 * @param colname
	 * @param value
	 * @param grid
	 * @return
	 */
	@RequestMapping("getBzxxList")
	@ResponseBody
	public Object getBzxxList(JqGrid grid,@RequestParam("userid")String userid){
		Map<String, Object> param = Maps.newHashMap();
		param.put("userid", userid);
		gxpzService.getBzxxList(param,grid);
		return grid;
	}
	/**
	 * 新增
	 * @return
	 * */
	@RequestMapping("newBzxx")
	public String newBzxx(){
		return RYBZ+"newBzxx";
	}
	/**
	 * 获取数据
	 * @param key
	 * @return
	 * */
	@RequestMapping("getBzxxForm")
	@ResponseBody
	public Object getBzxxForm(@RequestParam(value="keyValue")Long key){
		JfTjBzjfmx bzxx=gxpzService.getBzxxForm(key);
		return new ExtReturn(true, bzxx);
	}
	/**
	 * 新增/编辑提交
	 * @param mzcp
	 * @param dws
	 * @return
	 */
	@RequestMapping(value="newBzxxSubmit",method = RequestMethod.POST)
	@ResponseBody
	public Object newBzxxSubmit(JfTjBzjfmx bzxx,@RequestParam("ryxh")String ryxh,@RequestParam("rymc")String rymc){
		AuthUserVO user = UserUtils.getAuthUser();
		bzxx.setLrsj(new Date());
		bzxx.setLryxh(user.getUid());
		bzxx.setLrymc(user.getRealName());
		try {
			gxpzService.newBzxx(bzxx);//
			return new ExtReturn(true,"处理成功");
		} catch (Exception e) {
			log.error("newBzxxSubmit error:{}"+e);
			return new ExtReturn(false,"操作失败");
		}
	}
	/**
	 * 新增/编辑提交
	 * @param ryxh
	 * @param rymc
	 * @param listJson
	 * @param delArr 删除
	 * @return
	 */
	@RequestMapping(value="newBzxxListSubmit",method = RequestMethod.POST)
	@ResponseBody
	public Object newBzxxListSubmit(@RequestParam("ryxh")String ryxh,@RequestParam("rymc")String rymc,
			@RequestParam("listJson")String listJson,
			@RequestParam("delBz")String delArr){
		List<JfTjBzjfmx> bzxx = JSON.parseArray(listJson, JfTjBzjfmx.class);
		try {
			gxpzService.newBzxxList(ryxh,rymc,bzxx,delArr);//
			return new ExtReturn(true,"处理成功");
		} catch (Exception e) {
			log.error("newBzxxSubmit error:{}"+e);
			return new ExtReturn(false,"操作失败");
		}
	}
	/**
	 *删除
	 *@param key
	 *@return
	 * */
	@RequestMapping(value="delBzxx",method = RequestMethod.POST)
	@ResponseBody
	public Object delMzcp(@RequestParam("keyValue")Long key){
		try {	
			gxpzService.delBzxx(key);
		} catch (Exception e) {
			log.error("delBzxx error:{}"+e);
			return new ExtReturn(false,"操作失败");
		}
		return new ExtReturn(true,"操作成功");
	}
	
}