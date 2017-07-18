package com.power.jfsb.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.entity.WdTreeVO;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.JfShenbao;
import com.power.common.entity.JfShenbaoSp;
import com.power.common.entity.JfShenbaoXm;
import com.power.common.mybatis.page.JqGrid;
import com.power.common.springmvc.ExtReturn;
import com.power.jfgl.service.GxpzService;
import com.power.jfsb.entity.JfSbSpVO;
import com.power.jfsb.entity.SbRyVO;
import com.power.jfsb.service.JfsbService;
import com.power.pz.entity.JfPzVO;

/**
 * 积分申报
 * @author lb
 *
 */
@Controller
@RequestMapping("jf/jfsb")
public class JfsbController {
	private static final Logger log = LoggerFactory.getLogger(JfsbController.class);
	
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
	
	private String PREFIX="jf/shenbao/";
	
	/**
	 * 积分申报主页面
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
	 
	@RequestMapping("showJfsb")
	@ResponseBody
		public Object showPzs( @RequestParam(value = "condition",required = false)String condition,
							   @RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("sbr", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
		jfsbService.showJfsb(map,grid);
		return grid;
	}
	
	/**
	 * 积分申报添加
	 * @return
	 */
	@RequestMapping("new")
	public String newPz(Model model){
		model.addAttribute("nowtime",new Date());
		model.addAttribute("newColor", new Object());
		AuthUserVO user = UserUtils.getAuthUser();
		model.addAttribute("user",jfsbService.getUserInfoByUID(user.getUid()));
		return PREFIX+"new";
	}
	
	/**
	 * 选择积分项
	 * 
	 * @return
	 */
	@RequestMapping("selectJfx")
	public String selectJfx(Model model) {
		return PREFIX + "selectJfx";
	}

	/**
	 * 获取积分项列表
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping("jfx")
	@ResponseBody
	public Object newTree() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("category", 3);
		List<JfPzVO> list = jfsbService.getJfx(map);
		return list;

	}
	
	/**
	 * 左侧树形列表
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping("jfxTree")
	@ResponseBody
	public Object jfxTree(@RequestParam(value = "type",required = false)String type){	
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userid", user.getUid());
		if(type!=null&&type.equals("1")){
			map.put("jflx", "业务积分");
		}else if(type!=null&&type.equals("2")){
			map.put("jflx", "管理积分");
		}
		List<JfShenbaoXm> gws = jfsbService.getUserGwJfx(map);
		List<WdTreeVO> treeList = new ArrayList<WdTreeVO>();
		getList(gws,treeList);
		return treeList ;
		
	}

	private void getList(List<JfShenbaoXm> list, List<WdTreeVO> treeList) {
		for (JfShenbaoXm item : list) {
		    WdTreeVO tree = new WdTreeVO();
		    tree.setId(item.getId().toString());
    		tree.setValue(item.getJfxms());
    		tree.setText(item.getJfxmc());
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
	public Object saveForm(JfShenbao jfShenbao,@RequestParam(value = "rys")String rys,
			@RequestParam(value = "fzs")String fzs){
		AuthUserVO user = UserUtils.getAuthUser();
		jfShenbao.setStatus("1");
		jfShenbao.setSbrid(user.getUid());
		
		try{
			if(jfShenbao.getId()!=null){
				jfsbService.editJfsbAndRy(jfShenbao, rys, fzs);
			}else{
				jfShenbao.setSbsj(new Date());
				jfsbService.newJfsbAndSp(jfShenbao, rys, fzs);
				
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
		JfShenbao jfShenbao = jfsbService.getJfsb(id);
		JfShenbaoXm _JfShenbaoXm = jfsbService.getJfx(jfShenbao.getJflxid());
		jfShenbao.setJfxmc(_JfShenbaoXm.getJfxmc());
		jfShenbao.setJfxms(_JfShenbaoXm.getJfxms()); 
		jfShenbao.setJflx(_JfShenbaoXm.getJflx());
		return new ExtReturn(true,jfShenbao);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delSb",method = RequestMethod.POST)
	@ResponseBody	
	public Object delSb(@RequestParam("keyValue")Integer id){
		try {	
			jfsbService.delSb(id);
		} catch (Exception e) {
		log.error("delSb error:{}"+e);
			return new ExtReturn(false,"操作失败".getClass());
		}
		return new ExtReturn(true,"操作成功");
	}
	
	/**
	 * 获取积分申报人员
	 * @param id
	 * @return
	 */
	@RequestMapping("getSbRys")
	@ResponseBody
	public Object getSbRys(@RequestParam("keyValue")Integer id){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("sbid", id);
		 List<SbRyVO> sbrys = jfsbService.showJfsbry(map);
		return sbrys;
	}
	
	//========== 
	// 申报审批
	//==========
	
	/**
	 * 申报审批主页面
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
	 
	@RequestMapping("showJfsbSp")
	@ResponseBody
		public Object showJfSbSp( @RequestParam(value = "condition",required = false)String condition,
							   @RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("sprid", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
		
		jfsbService.showJfsbsp(map,grid);
		return grid;
	}
	
	/**
	 * 积分申报审批
	 * @return
	 */
	@RequestMapping("sp")
	public String sp(Model model){
		model.addAttribute("nowtime",new Date());
		model.addAttribute("newColor", new Object());
		return PREFIX+"sp";
	}
	/**
	 * 审批提交
	 * @param news
	 * @return
	 */
	@RequestMapping(value="saveSpForm",method = RequestMethod.POST)
	@ResponseBody
	public Object saveSpForm(JfShenbaoSp jfShenbaoSp){
		AuthUserVO user = UserUtils.getAuthUser();
		jfShenbaoSp.setStatus("1");
		jfShenbaoSp.setSprid(user.getUid());
		
		try{
			jfShenbaoSp.setSpsj(new Date());
			jfsbService.editJfsbspSubmit(jfShenbaoSp);
			
			return new ExtReturn(true,"操作成功");
		}catch(Exception e){
			return new ExtReturn(false,"操作失败");
		}
	
	}
	
	//==============
	// 积分申报审批流程展示
	//==============
	/**
	 * 积分申报审批流程主页面
	 * @return
	 */
	@RequestMapping("splcindex")
	public String splcindex(){
		return PREFIX+"splcindex";
	}
	
	/**
	 * 积分申报审批流程列表展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("showSbSpLc")
	@ResponseBody
		public Object showSbSpLc(@RequestParam(value = "sbid")Integer sbid,
							   JqGrid grid)throws IOException{
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("sbid", sbid);
		
		jfsbService.showJfsbLc(map,grid);
		return grid;
	}
	

	//============
	// 个人申报记录
	//============
	/**
	 * 积分申报主页面
	 * @return
	 */
	@RequestMapping("sbindex")
	public String sbindex(Model model){
		AuthUserVO user = UserUtils.getAuthUser();
		model.addAttribute("userid", user.getUid());
		return PREFIX+"sbindex";
	}
	
	/**
	 * 列表展示
	 * @param category 岗位编号
	 * @param fullhead 标题
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("showJfsbList")
	@ResponseBody
	public Object showsbList( @RequestParam(value = "condition",required = false)String condition,
							   @RequestParam(value = "keyword",required = false)String keyword,
							   @RequestParam(value = "userid")String userid,
							   @RequestParam(value = "jflx")String jflx,
							   JqGrid grid)throws IOException{
		Map<String, Object> map = new HashMap<String, Object>(3);
		if(jflx!=null&&jflx.equals("1")){
			map.put("jflx", "业务积分");
		}else if(jflx!=null&&jflx.equals("2")){
			map.put("jflx", "管理积分");
		}
		map.put("userid", userid);
		if(condition!=null){
			map.put(condition, keyword);
		}
		jfsbService.showJfsbList(map,grid);
		return grid;
	}
}
