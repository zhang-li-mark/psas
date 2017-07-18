package com.power.pz.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.power.base.sys.entity.WdTreeVO;
import com.power.common.entity.BaseNews;
import com.power.common.entity.JfShenbaoXm;
import com.power.common.entity.SysRole;
import com.power.common.mybatis.page.JqGrid;
import com.power.common.springmvc.ExtReturn;
import com.power.pz.service.GzjfpzService;

/**
 * 工作积分配置
 * @author lb
 *
 */
@Controller
@RequestMapping("pz/gzjfpz")
public class GzjfpzController {
	private static final Logger log = LoggerFactory.getLogger(GzjfpzController.class);
	
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
	private GzjfpzService gzjfpzService;
	
	private String PREFIX="pz/gzjf/";
	
	/**
	 * 工作积分配置主页面
	 * @return
	 */
	@RequestMapping("index")
	public String index(){
		return PREFIX+"index";
	}
	
	/**
	 * 列表展示
	 * @param category 岗位编号
	 * @param fullhead 标题
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("showPzs")
	@ResponseBody
		public Object showPzs(@RequestParam(value = "category",required=false)String category,
							   @RequestParam(value = "fullhead",required = false)String fullhead,
							   @RequestParam(value = "type",required=false)String type,
							   JqGrid grid)throws IOException{
		Map<String, Object> map = new HashMap<String, Object>(3);
		if(category!=null){
			map.put("gwid", category);
		}
		if(fullhead!=null){
			map.put("fullhead", fullhead);
		}
		if(type!=null&&type.equals("1")){
			map.put("jflx", "业务积分");
		}else if(type!=null&&type.equals("2")){
			map.put("jflx", "管理积分");
		}
		gzjfpzService.showPzs(map,grid);
		 return grid;
	}
	
	/**
	 * 工作积分配置主页面
	 * @return
	 */
	@RequestMapping("newPz")
	public String newPz(Model model){
		model.addAttribute("nowtime",new Date());
		model.addAttribute("newColor", new Object());
		return PREFIX+"new";
	}
	
	
	/**
	 * 左侧树形列表
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping("gwTree")
	@ResponseBody
	public Object newTree(){	
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("category", 3);
		List<SysRole> list = gzjfpzService.getGw(map);		
		List<WdTreeVO> treeList = new ArrayList<WdTreeVO>();
		getList(list,treeList);
		return treeList ;
		
	}


	private void getList(List<SysRole> list, List<WdTreeVO> treeList) {
		for (SysRole item : list) {
		    WdTreeVO tree = new WdTreeVO();
		    tree.setId(item.getRoleid());
    		tree.setValue(item.getRoleid());
    		tree.setText(item.getFullname());
    		tree.setComplete(true);
    		tree.setIsexpand(true);
	    		treeList.add(tree);
		}		
	}
	
	/**
	 * 新增/编辑保存
	 * @param news
	 * @return
	 */
	@RequestMapping(value="saveForm",method = RequestMethod.POST)
	@ResponseBody
	public Object saveForm(JfShenbaoXm jfShenbaoXm){
		try{
			if(jfShenbaoXm.getId()!=null){
				jfShenbaoXm.setXgsj(new Date());
				gzjfpzService.editPz(jfShenbaoXm);
			}else{
				jfShenbaoXm.setTjsj(new Date());
				jfShenbaoXm.setXgsj(new Date());
				gzjfpzService.newPz(jfShenbaoXm);
			}
			return new ExtReturn(true,"操作成功");
		}catch(Exception e){
			return new ExtReturn(false,"操作失败");
		}
	
		
	}
	
	/**
	 *获取要被修改的值传到修改页面上
	 * @param id
	 * @return
	 */
	@RequestMapping("getFormJson")
	@ResponseBody
	public Object getFormJson(@RequestParam("keyValue")Integer id){
		JfShenbaoXm jfShenbaoXm = gzjfpzService.getPz(id);
		return new ExtReturn(true,jfShenbaoXm);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delPz",method = RequestMethod.POST)
	@ResponseBody	
	public Object delPz(@RequestParam("keyValue")Integer id){
		try {	
			gzjfpzService.delPz(id);
			
		} catch (Exception e) {
		log.error("delPz error:{}"+e);
			return new ExtReturn(false,"操作失败".getClass());
		}
		return new ExtReturn(true,"操作成功");
	}
}
