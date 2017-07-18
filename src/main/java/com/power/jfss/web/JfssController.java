package com.power.jfss.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.power.base.sys.service.DeptService;
import com.power.base.sys.service.OrgService;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.JfShenSu;
import com.power.common.entity.JfShenbao;
import com.power.common.entity.SysDepartment;
import com.power.common.entity.SysOrganize;
import com.power.common.mybatis.page.JqGrid;
import com.power.common.springmvc.ExtReturn;
import com.power.common.utils.ExcelUtil;
import com.power.jfsb.service.JfsbService;
import com.power.jfss.service.ExportService;
import com.power.jfss.service.JfssService;

/**
 * 积分申诉
 * @author lb
 *
 */
@Controller
@RequestMapping("jf/jfss")
public class JfssController {
	private static final Logger log = LoggerFactory.getLogger(JfssController.class);
	
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
	private JfssService jfssService;
	
	@Autowired
	private JfsbService jfsbService;
	
	@Autowired
	private ExportService exportService;
	
	@Autowired
	private OrgService orgService;
	@Autowired
	private DeptService deptService;
	
	private String PREFIX="jf/shensu/";
	
	/**
	 * 积分申诉主页面
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
	 
	@RequestMapping("showJfss")
	@ResponseBody
		public Object showJfss( @RequestParam(value = "condition",required = false)String condition,
				   				@RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("ssr", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
		jfssService.showJfss(map,grid);
		 return grid;
	}
	
	/**
	 * 积分申报添加
	 * @return
	 */
	@RequestMapping("new")
	public String newJfss(Model model){
		AuthUserVO user = UserUtils.getAuthUser();
		model.addAttribute("nowtime",new Date());
		model.addAttribute("uname",user.getRealName());
		model.addAttribute("deptname",user.getDeptName());
		model.addAttribute("newColor", new Object());
		return PREFIX+"new";
	}
	
	/**
	 * 获取积分申报列表
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping("jfsb")
	@ResponseBody
	public Object getjfsb(){
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("sbrid",user.getUid());
		List<JfShenbao> list = jfssService.getJfsbByUID(map);		
		return list ;
		
	}
	
	/**
	 * 新增/编辑保存
	 * @param news
	 * @return
	 */
	@RequestMapping(value="saveForm",method = RequestMethod.POST)
	@ResponseBody
	public Object saveForm(JfShenSu jfShenSu){
		jfShenSu.setStatus("1");
		AuthUserVO user = UserUtils.getAuthUser();
		jfShenSu.setUserid(user.getUid());
		jfShenSu.setUname(user.getRealName());
		jfShenSu.setDeptid(user.getDeptId());
		jfShenSu.setDeptname(user.getDeptName());
		jfShenSu.setType("1");
		
		try{
			if(jfShenSu.getId()!=null){
				jfssService.editJfss(jfShenSu);
			}else{
				jfssService.newJfss(jfShenSu);
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
		JfShenSu jfShenSu = jfssService.getJfss(id);
		JfShenbao _JfShenbao = jfsbService.getJfsb(jfShenSu.getSjid());
		jfShenSu.setSjmc(_JfShenbao.getSjmc());
		return new ExtReturn(true,jfShenSu);
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delSs",method = RequestMethod.POST)
	@ResponseBody	
	public Object delSs(@RequestParam("keyValue")Integer id){
		try {	
			jfssService.delSs(id);
			
		} catch (Exception e) {
		log.error("delSs error:{}"+e);
			return new ExtReturn(false,"操作失败".getClass());
		}
		return new ExtReturn(true,"操作成功");
	}
	
	//============
	// 申诉选择关联事件
	//============
	/**
	 * 选择关联事件
	 * @return
	 */
	@RequestMapping("selectSj")
	public String glsj(){
		return PREFIX+"selectsj";
	}
	
	/**
	 * 列表展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("showJfSjByUID")
	@ResponseBody
		public Object show(@RequestParam(value = "condition",required = false)String condition,
   				@RequestParam(value = "keyword",required = false)String keyword,JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("userid", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
		jfssService.getSbSjByUserID(map,grid);
		 return grid;
	}
	
	//=======
	// 申诉审批
	//=======
	/**
	 * 积分申诉主页面
	 * @return
	 */
	@RequestMapping("spindex")
	public String spIndex(){
		return PREFIX+"spindex";
	}
	
	/**
	 * 列表展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("showJfssSp")
	@ResponseBody
		public Object showJfSsSp( @RequestParam(value = "condition",required = false)String condition,
				   				@RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("sprid", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
		jfssService.showJfSsSp(map,grid);
		 return grid;
	}
	
	/**
	 * 积分申报添加
	 * @return
	 */
	@RequestMapping("sp")
	public String sp(Model model){
		return PREFIX+"sp";
	}
	
	/**
	 * 审批
	 * @param news
	 * @return
	 */
	@RequestMapping(value="saveSpForm",method = RequestMethod.POST)
	@ResponseBody
	public Object saveSpForm(JfShenSu jfShenSu){
		jfShenSu.setSpsj(new Date());
		if(jfShenSu.getSpjg()!=null && jfShenSu.getSpjg().equals("0")){
			jfShenSu.setStatus("4");
		}else if(jfShenSu.getSpjg()!=null && jfShenSu.getSpjg().equals("1")){
			jfShenSu.setStatus("3");
		}
		try{
			jfssService.editJfss(jfShenSu);
			return new ExtReturn(true,"操作成功");
		}catch(Exception e){
			return new ExtReturn(false,"操作失败");
		}
	
		
	}
	//导出配件列表
    @RequestMapping(value = "userExport")
    @ResponseBody
    public void partExport(HttpServletResponse response,@RequestParam(value="condition",required=false)String colname,
			@RequestParam(value="keyword",required=false)String value,HashMap<String, Object> map){
		if(colname!=null){
			map.put(colname,value);
		}
		AuthUserVO user = UserUtils.getAuthUser();
		map.put("loginname",user.getLoginName());
		if(StringUtils.isNoneBlank(user.getDeptId())){//判断是否未选择部门
			List<SysDepartment> deptList = deptService.getAllChildren("all",user.getDeptId());
			//封装全部子部门
			StringBuffer depts = new StringBuffer();
			for (SysDepartment dept : deptList) {
				depts.append(",'").append(dept.getDepartmentid()).append("'");
			}
			map.put("depts", depts.deleteCharAt(0).toString());//删除首个逗号
		}else{
			List<SysOrganize> orgList = orgService.getOrgAllChildren(user.getOrgId());
			//封装全部子部门
			StringBuffer orgs = new StringBuffer();
			for (SysOrganize org : orgList) {
				orgs.append(",'").append(org.getOrganizeid()).append("'");
			}
			map.put("orgs", orgs.deleteCharAt(0).toString());//删除首个逗号
		}
        List<Object> list = exportService.selectStatExport(map);//获取业务数据集
        Map<String,String> headMap = exportService.getPartStatHeadMap();//获取属性-列头
        String title = "人事档案表";
        ExcelUtil.downloadExcelFile(title,headMap,list,response);
    }
}
