package com.power.qingjia.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.service.DeptService;
import com.power.base.sys.service.UserService;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.GrQingjia;
import com.power.common.entity.GrQingjiaSp;
import com.power.common.entity.JfShenbaoSp;
import com.power.common.entity.SysDepartment;
import com.power.common.entity.SysUser;
import com.power.common.mybatis.page.JqGrid;
import com.power.common.springmvc.ExtReturn;
import com.power.jfgl.service.GxpzService;
import com.power.jfsb.service.JfsbService;
import com.power.qingjia.entity.QingJiaSpVO;
import com.power.qingjia.service.QingJiaService;

/**
 * 请休假管理
 * @author lb
 *
 */
@Controller
@RequestMapping("gr/qingjia")
public class QingJiaController {
	private static final Logger log = LoggerFactory.getLogger(QingJiaController.class);
	
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
	private JfsbService jfsbService;
	
	@Autowired
	private GxpzService gxpzService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private QingJiaService qingJiaService;
	
	private String PREFIX="gr/qingjia/";
	
	/**
	 * 请休假主页面
	 * @return
	 */
	@RequestMapping("index")
	public String index(){
		return PREFIX+"index";
	}
	
	/**
	 * 列表展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("showQingjia")
	@ResponseBody
		public Object showPzs( @RequestParam(value = "condition",required = false)String condition,
							   @RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("qjrid", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
		
		qingJiaService.showQingJia(map,grid);
		return grid;
	}
	
	/**
	 * 请休假添加
	 * @return
	 */
	@RequestMapping("new")
	public String newPz(Model model){
		AuthUserVO user = UserUtils.getAuthUser();
		model.addAttribute("uname",user.getRealName());
		model.addAttribute("deptname",user.getDeptName());
		return PREFIX+"new";
	}
	

	/**
	 * 新增/编辑保存
	 * @param news
	 * @return
	 */
	@RequestMapping(value="saveForm",method = RequestMethod.POST)
	@ResponseBody
	public Object saveForm(GrQingjia grQingjia){
		
		try{
			if(grQingjia.getId()!=null){
				qingJiaService.editGrQingJia(grQingjia);
			}else{
				AuthUserVO user = UserUtils.getAuthUser();
				grQingjia.setTjsj(new Date());
				grQingjia.setStatus("1");
				grQingjia.setQjrid(user.getUid());
				qingJiaService.newGrQingJiaAndSp(grQingjia);
				
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
		GrQingjia grQingjia = qingJiaService.getQingJia(id);
		SysUser user = userService.getUser(grQingjia.getQjrid());
		SysDepartment dept = deptService.getDept(user.getDepartmentid());
		grQingjia.setUname(user.getRealname());
		grQingjia.setDeptname(dept.getFullname());
		return new ExtReturn(true,grQingjia);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delQingJia",method = RequestMethod.POST)
	@ResponseBody	
	public Object delQingJia(@RequestParam("keyValue")Integer id){
		try {	
			qingJiaService.delQingJia(id);
		} catch (Exception e) {
		log.error("delQingJia error:{}"+e);
			return new ExtReturn(false,"操作失败".getClass());
		}
		return new ExtReturn(true,"操作成功");
	}
	
	
	//========== 
	// 请假审批
	//==========
	
	/**
	 * 请假审批主页面
	 * @return
	 */
	@RequestMapping("spindex")
	public String spindex(){
		return PREFIX+"spindex";
	}
	
	/**
	 * 审批列表展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("showQingJiaSp")
	@ResponseBody
		public Object showQingJiaSp( @RequestParam(value = "condition",required = false)String condition,
							   @RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("sprid", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
		qingJiaService.showQingJiaSp(map,grid);
		return grid;
	}
	
	/**
	 * 请假审批
	 * @return
	 */
	@RequestMapping("sp")
	public String sp(Model model){
		return PREFIX+"sp";
	}
	/**
	 * 请假审批提交
	 * @param news
	 * @return
	 */
	@RequestMapping(value="saveSpForm",method = RequestMethod.POST)
	@ResponseBody
	public Object saveSpForm(GrQingjiaSp grQingjiaSp){
		AuthUserVO user = UserUtils.getAuthUser();
		grQingjiaSp.setStatus("1");
		grQingjiaSp.setSprid(user.getUid());
		
		try{
			grQingjiaSp.setSpsj(new Date());
			qingJiaService.editQingJiaspSubmit(grQingjiaSp);
			
			return new ExtReturn(true,"操作成功");
		}catch(Exception e){
			return new ExtReturn(false,"操作失败");
		}
	
		
	}
	
	//==============
	// 请假审批流程展示
	//==============
	/**
	 * 请假审批流程主页面
	 * @return
	 */
	@RequestMapping("splcindex")
	public String splcindex(){
		return PREFIX+"splcindex";
	}
	
	/**
	 * 审批流程列表展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("showQingJiaSpLc")
	@ResponseBody
		public Object showQingJiaSpLc(@RequestParam(value = "qjid")Integer qjid,
							   JqGrid grid)throws IOException{
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("qjid", qjid);
		
		qingJiaService.showQingJiaSpLc(map,grid);
		return grid;
	}
	
}
