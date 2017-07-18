package com.power.xuexi.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.GrXuexi;
import com.power.common.mybatis.page.JqGrid;
import com.power.common.springmvc.ExtReturn;
import com.power.xuexi.entity.XxRyVO;
import com.power.xuexi.service.XueXiService;

@Controller
@RequestMapping("gr/xuexi")
public class XueXiController {
	
	private static final Logger log = LoggerFactory.getLogger(XueXiController.class);
	
	/**
	 * 时间格式规范
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder){
		CustomDateEditor editor=new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"),true);
		binder.registerCustomEditor(Date.class,editor);
	}
	
	@Autowired
	private XueXiService xueXiService;
	
	private String PREFIX = "gr/xuexi/";
	
	/**
	 * 学习任务下发主页面
	 * @return
	 */
	@RequestMapping("index")
	public String index(){
		return PREFIX+"rwxf/index";
	}
	
	/**
	 * 学习任务展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("showXueXi")
	@ResponseBody
		public Object showXueXi( @RequestParam(value = "condition",required = false)String condition,
							   @RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("sbr", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
		
		xueXiService.showXueXi(map,grid);
		return grid;
	}
	
	/**
	 * 添加学习任务
	 * @return
	 */
	@RequestMapping("new")
	public String newXueXi(Model model){
		return PREFIX+"rwxf/new";
	}
	
	/**
	 * 获取要被修改的值传到修改页面上
	 * @param id
	 * @return
	 */
	@RequestMapping("getFormJson")
	@ResponseBody
	public Object getFormJson(@RequestParam("keyValue")Integer id){
		GrXuexi grXuexi = xueXiService.getXueXi(id);
		return new ExtReturn(true,grXuexi);
	}
	
	/**
	 * 新增/编辑保存
	 * @param news
	 * @return
	 */
	@RequestMapping(value="saveForm",method = RequestMethod.POST)
	@ResponseBody
	public Object saveForm(GrXuexi grXuexi){
		AuthUserVO user = UserUtils.getAuthUser();
		grXuexi.setFbrid(user.getUid());
		
		try{
			if(grXuexi.getId()!=null){
				xueXiService.editXueXi(grXuexi);
			}else{
				grXuexi.setFbsj(new Date());
				String bmIds = grXuexi.getDws();
				if(StringUtils.isBlank(bmIds)){
					return new ExtReturn(false,"请选择指派单位");
				}
				xueXiService.newXueXi(grXuexi);
				
			}
			return new ExtReturn(true,"操作成功");
		}catch(Exception e){
			return new ExtReturn(false,"操作失败");
		}
	
	}
	
	/**
	 * 删除学习任务
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delxx",method = RequestMethod.POST)
	@ResponseBody	
	public Object delxx(@RequestParam("keyValue")Integer id){
		try {	
			xueXiService.delXx(id);
		} catch (Exception e) {
		log.error("delxx error:{}"+e);
			return new ExtReturn(false,"操作失败".getClass());
		}
		return new ExtReturn(true,"操作成功");
	}
	
	/**
	 * 学习任务选择单位
	 * @return
	 * */
	@RequestMapping("setXxdw")
	public String setCpdw(){
		return PREFIX+"rwxf/setXxdw";
	}
	
	
	//==========
	// 学习任务管理
	//==========
	
	/**
	 * 学习任务管理主页面
	 * @return
	 */
	@RequestMapping("glindex")
	public String glindex(){
		return PREFIX+"rwgl/index";
	}
	
	/**
	 * 个人学习任务展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("showXxByUID")
	@ResponseBody
		public Object showXx( @RequestParam(value = "condition",required = false)String condition,
							   @RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("xxrid", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
		
		xueXiService.showXxByUID(map,grid);
		return grid;
	}
	
	/**
	 * 修改学习状态
	 * @param id
	 * @return
	 */
	@RequestMapping(value="xxwb")
	@ResponseBody	
	public Object xxwb(@RequestParam("id")Integer id){
		try {	
			xueXiService.editxxwb(id);
		} catch (Exception e) {
		log.error("delxx error:{}"+e);
			return new ExtReturn(false,"操作失败".getClass());
		}
		return new ExtReturn(true,"操作成功");
	}
}
