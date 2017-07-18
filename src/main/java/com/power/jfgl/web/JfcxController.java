package com.power.jfgl.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.JfTjHz;
import com.power.common.mybatis.page.JqGrid;
import com.power.common.utils.ExcelUtil;
import com.power.index.service.IndexService;
import com.power.jfgl.entity.JfJcxxMxVO;
import com.power.jfgl.service.JfcxService;
import com.power.jfgl.service.MzcpService;
import com.power.jfsb.entity.JFSbTotalList;
import com.power.jfsb.service.JfsbService;
import com.power.jfss.service.ExportService;
import com.power.qingjia.entity.QaTotalList;
import com.power.qingjia.service.QingJiaService;
import com.power.xuexi.entity.KsTotalList;
import com.power.xuexi.entity.XxTotalList;
import com.power.xuexi.service.KaoShiService;
import com.power.xuexi.service.XueXiService;

/**
 * 积分信息查询
 * 项目名称：psas <br>
 * 类名称：GxpzController <br>
 * @version 1.0
 */
@Controller
@RequestMapping("jfcx")
public class JfcxController {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private IndexService indexService;
	@Autowired
	private JfcxService jfcxService;
	@Autowired
	private JfsbService jfsbService;
	@Autowired
	private MzcpService mzcpService;
	@Autowired
	private XueXiService xueXiService;
	@Autowired
	private QingJiaService qingJiaService;
	@Autowired
	private KaoShiService kaoShiService;
	@Autowired
	private ExportService exportService;
	
	private String JFCX="jf/jfcx/";//积分查询
	private String WDJF="jf/jfcx/wdjf/";//我的积分
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		CustomDateEditor editor=new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true);
		binder.registerCustomEditor(Date.class,editor);
	}
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("jfcxglIndex")
	public String jfcxglIndex(Model model){
		AuthUserVO user = UserUtils.getAuthUser();
		model.addAttribute("userid", user.getUid());
		return JFCX+"index";
	}
	
	/**
	 * 获取积分统计
	 * @param uname
	 * @param st
	 * @param et
	 * @param orgId
	 * @param deptId
	 * @param grid
	 * @return
	 */
	@RequestMapping("getJfTj")
	@ResponseBody
	public Object getJfTj(@RequestParam(value="uname",required=false)String uname,
			@RequestParam(value="st",required=false)String st,
			@RequestParam(value="et",required=false)String et,
			@RequestParam(value="orgId",required=false)String orgId,
			@RequestParam(value="deptId",required=false)String deptId,
			JqGrid grid){
		Map<String,Object> map = Maps.newHashMap();
		if(StringUtils.isNoneBlank(st)){
			map.put("st",st.replaceAll("年", "").replaceAll("月", ""));
		}
		if(StringUtils.isNoneBlank(et)){
			map.put("et",et.replaceAll("年", "").replaceAll("月", ""));
		}
		if(StringUtils.isNoneBlank(uname)){
			map.put("uname",uname);
		}
		//层级关系处理
		indexService.setOrgDeptParam(orgId,deptId,map);
		jfcxService.getJfTj(map, grid);
		return grid;
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
//		indexService.setOrgDeptParam(orgId, deptId, map);
//		gxpzService.getGxpzList(map,grid);
		return grid;
	}
//	/////////////////////////////////// 我的积分
	/**
	 * 我的积分
	 * @return
	 * */
	@RequestMapping("wdjfIndex")
	public String wdjfIndex(Model model,@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="st",required=false)String st
			,@RequestParam(value="et",required=false)String et){
		AuthUserVO user = UserUtils.getAuthUser();
		//基础信息积分
		String uid ="";
		if(StringUtils.isBlank(userid)){
			uid=user.getUid();
		}else{
			uid= userid;
			model.addAttribute("userid", userid);
		}
		JfJcxxMxVO jcxx = jfcxService.getJfJcxxMx(uid);
		model.addAttribute("jcxx", jcxx);
		if(StringUtils.isNoneBlank(st)){
			model.addAttribute("st", st.replaceAll("年", "-").replaceAll("月", ""));
		}
		if(StringUtils.isNoneBlank(et)){
			model.addAttribute("et", et.replaceAll("年", "-").replaceAll("月", ""));
		}
		//民主测评得分
//		BigDecimal mzcpdf = mzcpService.getMzcpDf(user.getUid(),null,null);
//		model.addAttribute("mzcpdf", mzcpdf);
		
		Map<String,Object> map = Maps.newHashMap();
		map.put("nd", DateTime.now().getYear());
		map.put("userid", uid);
		if(StringUtils.isNoneBlank(st)){
			map.put("st",st.replaceAll("年", "").replaceAll("月", ""));
		}
		if(StringUtils.isNoneBlank(et)){
			map.put("et",et.replaceAll("年", "").replaceAll("月", ""));
		}
		JfTjHz hz = jfcxService.getMyJfHz(map);
		model.addAttribute("hz", hz);
		return WDJF+"index";
	}
	
	/**
	 * 获取定量积分明细信息
	 * @param jflx
	 * @param grid
	 * @return
	 */
	@RequestMapping("getDljfList")
	@ResponseBody
	public Object getDljfList(@RequestParam("jflx")String jflx,JqGrid grid,@RequestParam(value="userid",required=false)String userid,
			 @RequestParam(value="st",required=false)String st
			,@RequestParam(value="et",required=false)String et){
		AuthUserVO user = UserUtils.getAuthUser();
		//FIXME 起至时间待定
		String uid ="";
		if(StringUtils.isBlank(userid)){
			uid=user.getUid();
		}else{
			uid= userid;
		}
		List<JFSbTotalList> rows = jfsbService.getUserJfSbTotalList(uid, st, et, jflx);
		grid.setRows(rows);
		return grid;
	}
	
	/**
	 * 获取学习明细信息
	 * @param jflx
	 * @param grid
	 * @return
	 */
	@RequestMapping("getxxjfList")
	@ResponseBody
	public Object getxxjfList(JqGrid grid,@RequestParam(value="userid",required=false)String userid,
				@RequestParam(value="st",required=false)String st
			,@RequestParam(value="et",required=false)String et){
		AuthUserVO user = UserUtils.getAuthUser();
		//FIXME 起至时间待定
		String uid ="";
		if(StringUtils.isBlank(userid)){
			uid=user.getUid();
		}else{
			uid= userid;
		}
		List<XxTotalList> rows = xueXiService.getUserXxTotalList(uid, st, et);
		grid.setRows(rows);
		return grid;
	}
	
	/**
	 * 获取请休假明细信息
	 * @param jflx
	 * @param grid
	 * @return
	 */
	@RequestMapping("getQaList")
	@ResponseBody
	public Object getQaList(JqGrid grid,@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="st",required=false)String st
			,@RequestParam(value="et",required=false)String et){
		AuthUserVO user = UserUtils.getAuthUser();
		//FIXME 起至时间待定
		String uid ="";
		if(StringUtils.isBlank(userid)){
			uid=user.getUid();
		}else{
			uid= userid;
		}
		List<QaTotalList> rows = qingJiaService.getUserQaTotalList(uid, st, et);
		grid.setRows(rows);
		return grid;
	}
	
	/**
	 * 获取考试明细信息
	 * @param jflx
	 * @param grid
	 * @return
	 */
	@RequestMapping("getKsList")
	@ResponseBody
	public Object getKsList(JqGrid grid,@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="st",required=false)String st
			,@RequestParam(value="et",required=false)String et){
		AuthUserVO user = UserUtils.getAuthUser();
		//FIXME 起至时间待定
		String uid ="";
		if(StringUtils.isBlank(userid)){
			uid=user.getUid();
		}else{
			uid= userid;
		}
		List<KsTotalList> rows = kaoShiService.getUserXxTotalList(uid, st, et);
		grid.setRows(rows);
		return grid;
	}
	
	//导出配件列表
    @RequestMapping(value = "jfExport")
    @ResponseBody
    public void partExport(HttpServletResponse response,@RequestParam(value="uname",required=false)String uname,
			@RequestParam(value="st",required=false)String st,
			@RequestParam(value="et",required=false)String et,
			@RequestParam(value="orgId",required=false)String orgId,
			@RequestParam(value="deptId",required=false)String deptId){
    	HashMap<String,Object> map = Maps.newHashMap();
		if(StringUtils.isNoneBlank(st)){
			map.put("st",st.replaceAll("年", "").replaceAll("月", ""));
		}
		if(StringUtils.isNoneBlank(et)){
			map.put("et",et.replaceAll("年", "").replaceAll("月", ""));
		}
		if(StringUtils.isNoneBlank(uname)){
			map.put("uname",uname);
		}
		//层级关系处理
		indexService.setOrgDeptParam(orgId,deptId,map);
        List<Object> list = exportService.selectJfExport(map);//获取业务数据集
        Map<String,String> headMap = exportService.getPartJfHeadMap();//获取属性-列头
        String title = "积分管理表";
        ExcelUtil.downloadExcelFile(title,headMap,list,response);
    }
	
}