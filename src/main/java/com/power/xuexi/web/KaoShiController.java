package com.power.xuexi.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
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

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.KsKaoShi;
import com.power.common.entity.KsShiJuan;
import com.power.common.entity.KsTiMu;
import com.power.common.mybatis.page.JqGrid;
import com.power.common.springmvc.ExtReturn;
import com.power.xuexi.service.KaoShiService;

@Controller
@RequestMapping("gr/kaoshi")
public class KaoShiController {
	
	private static final Logger log = LoggerFactory.getLogger(KaoShiController.class);
	
	/**
	 * 时间格式规范
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder){
		CustomDateEditor editor=new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true);
		binder.registerCustomEditor(Date.class,editor);
	}
	
	@Autowired
	private KaoShiService kaoShiService;
	
	private String PREFIX = "gr/kaoshi/";
	
	/**
	 * 考试任务下发主页面
	 * @return
	 */
	@RequestMapping("rwxf")
	public String rwxf(){
		return PREFIX+"rwxf/index";
	}
	
	/**
	 * 添加考试任务
	 * @return
	 */
	@RequestMapping("newRwxf")
	public String newRwxf(Model model){
		return PREFIX+"rwxf/new";
	}
	
	/**
	 * 考试题目展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("getKsRws")
	@ResponseBody
		public Object getKsRws( @RequestParam(value = "condition",required = false)String condition,
							   @RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("sbr", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
	    kaoShiService.getKsRws(map,grid);
		return grid;
	}
	
	/**
	 * 获取要被修改的值传到修改页面上
	 * @param id
	 * @return
	 */
	@RequestMapping("getRwFormJson")
	@ResponseBody
	public Object getRwFormJson(@RequestParam("keyValue")Long id){
		KsKaoShi ksKaoShi = kaoShiService.getKsRw(id);
		return new ExtReturn(true,ksKaoShi);
	}
	
	/**
	 * 新增/编辑保存
	 * @param news
	 * @return
	 */
	@RequestMapping(value="saveRwForm",method = RequestMethod.POST)
	@ResponseBody
	public Object saveRwForm(KsKaoShi ksKaoShi){
		AuthUserVO user = UserUtils.getAuthUser();
		ksKaoShi.setFbrid(user.getUid());
		
		try{
			if(ksKaoShi.getId()!=null){
				kaoShiService.editKsRw(ksKaoShi);
			}else{
				ksKaoShi.setTjsj(new Date());
				String bmIds = ksKaoShi.getDws();
				if(StringUtils.isBlank(bmIds)){
					return new ExtReturn(false,"请选择指派单位");
				}
				String tmIds = ksKaoShi.getKstmid();
				if(StringUtils.isBlank(tmIds)){
					return new ExtReturn(false,"请选择考试题目");
				}
				kaoShiService.newKsRw(ksKaoShi);
				
			}
			return new ExtReturn(true,"操作成功");
		}catch(Exception e){
			return new ExtReturn(false,"操作失败");
		}
	
	}
	
	/**
	 * 删除考试任务
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delrw",method = RequestMethod.POST)
	@ResponseBody	
	public Object delrw(@RequestParam("keyValue")Long id){
		try {	
			kaoShiService.delKsrw(id);
		} catch (Exception e) {
		log.error("delrw error:{}"+e);
			return new ExtReturn(false,"操作失败".getClass());
		}
		return new ExtReturn(true,"操作成功");
	}
	
	//==========
	// 考试题目
	//==========
	
	/**
	 * 选择题目
	 * @return
	 */
	@RequestMapping("tmindex")
	public String tmindex(Model model){
		return PREFIX+"rwxf/tmindex";
	}
	
	/**
	 * 添加题目
	 * @return
	 */
	@RequestMapping("newtm")
	public String newtm(Model model){
		return PREFIX+"rwxf/newtm";
	}
	
	/**
	 * 考试题目展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("getKsTms")
	@ResponseBody
		public Object getKsTms( @RequestParam(value = "condition",required = false)String condition,
							   @RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("sbr", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
	    kaoShiService.getKsTms(map,grid);
		return grid;
	}
	
	/**
	 * 获取考试题目
	 * @param id
	 * @return
	 */
	@RequestMapping("getkstmByID")
	@ResponseBody
	public Object getFormJson(@RequestParam("keyValue")Long id){
		KsTiMu ksTiMu = kaoShiService.getKsTm(id);
		return new ExtReturn(true,ksTiMu);
	}
	
	/**
	 * 新增/编辑保存
	 * @param news
	 * @return
	 */
	@RequestMapping(value="saveTmForm",method = RequestMethod.POST)
	@ResponseBody
	public Object saveTmForm(KsTiMu ksTiMu){
		AuthUserVO user = UserUtils.getAuthUser();
		
		try{
			if(ksTiMu.getXh()!=null){
				kaoShiService.editKsTm(ksTiMu);
			}else{
				ksTiMu.setLrrxh(user.getUid());
				ksTiMu.setLrrmc(user.getRealName());
				ksTiMu.setLrsj(new Date());
				kaoShiService.newKsTm(ksTiMu);
				
			}
			return new ExtReturn(true,"操作成功");
		}catch(Exception e){
			return new ExtReturn(false,"操作失败");
		}
	
	}
	
	/**
	 * 删除题目
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deltm",method = RequestMethod.POST)
	@ResponseBody	
	public Object deltm(@RequestParam("keyValue")Long id){
		try {	
			kaoShiService.delKsTm(id);
		} catch (Exception e) {
		log.error("deltm error:{}"+e);
			return new ExtReturn(false,"操作失败".getClass());
		}
		return new ExtReturn(true,"操作成功");
	}
	
	//==========
	// 待考试
	//==========
	/**
	 * 待考试任务主页面
	 * @return
	 */
	@RequestMapping("rwgl")
	public String rwgl(){
		return PREFIX+"rwgl/index";
	}
	
	/**
	 * 待考试列表展示
	 * @param grid 分页
	 * @return
	 * @throws IOException
	 */
	 
	@RequestMapping("getDKs")
	@ResponseBody
		public Object getDKs( @RequestParam(value = "condition",required = false)String condition,
							   @RequestParam(value = "keyword",required = false)String keyword,
							   JqGrid grid)throws IOException{
		AuthUserVO user = UserUtils.getAuthUser();
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("userid", user.getUid());
		if(condition!=null){
			map.put(condition, keyword);
		}
		kaoShiService.getDKs(map,grid);
		return grid;
	}
	////////////////
	///考试
	/**
	 * 开始考试
	 * @param xh
	 * @return
	 */
	@RequestMapping("ksksForm")
	public String ksksForm(@RequestParam("xh")Long xh,Model model){
		AuthUserVO user = UserUtils.getAuthUser();
		//获取试卷信息
		KsShiJuan sj = kaoShiService.getKsShiJuanByXh(xh);
		//判断试卷时间是否截至
		if(Seconds.secondsBetween(DateTime.now(),new DateTime(sj.getJssj())).getSeconds()<0){
			model.addAttribute("err", "考试时间已截止，无法继续学习");
			return PREFIX+"rwgl/ksksForm";
		}
		model.addAttribute("user", user);
		//获取试题信息
		List<KsTiMu> tms = kaoShiService.getTiMuByKsxh(sj.getXh());
		model.addAttribute("sj", sj);
		List<KsTiMu> dxTm = Lists.newLinkedList(Collections2.filter(tms, new Predicate<KsTiMu>() {
            public boolean apply(KsTiMu tm) {
                return tm.getTmlx() == 1;
            }
        }));
		List<KsTiMu> duoxuanTm = Lists.newLinkedList(Collections2.filter(tms, new Predicate<KsTiMu>() {
			public boolean apply(KsTiMu tm) {
				return tm.getTmlx() == 2;
			}
		}));
		List<KsTiMu> panduanTm = Lists.newLinkedList(Collections2.filter(tms, new Predicate<KsTiMu>() {
			public boolean apply(KsTiMu tm) {
				return tm.getTmlx() == 3;
			}
		}));
//		List<KsTiMu> tiankongTm = Lists.newLinkedList(Collections2.filter(tms, new Predicate<KsTiMu>() {
//			public boolean apply(KsTiMu tm) {
//				return tm.getTmlx() == 4;
//			}
//		}));
		model.addAttribute("dxTm", dxTm);
		model.addAttribute("duoxuanTm", duoxuanTm);
		model.addAttribute("panduanTm", panduanTm);
//		model.addAttribute("tiankongTm", tiankongTm);
		if(sj.getSjzt()>0){//已经考试过
			//返回到考试明细页面
			//将题目编号，答案，得分放入map
			Map<String, String> das = Splitter.on("&").withKeyValueSeparator("=").split(sj.getDa());
			Map<String, String> dfs = Splitter.on("#").withKeyValueSeparator("=").split(sj.getDfmx());
			model.addAttribute("das", das);
			model.addAttribute("sfs", dfs);
			return PREFIX+"rwgl/ksDetail";
		}
		return PREFIX+"rwgl/ksksForm";
	}
	/**
	 * 考试提交
	 * @return
	 */
	@RequestMapping(value="ksSubmit",method = RequestMethod.POST)
	@ResponseBody
	public Object ksSubmit(@RequestParam("xh")Long sjxh,@RequestParam("da")String da){
		try{
			kaoShiService.newKsSubmit(sjxh,da);
			return new ExtReturn(true,"操作成功");
		}catch(Exception e){
			return new ExtReturn(false,"操作失败");
		}
	
	}
}
