package com.power.index.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.service.AuthService;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.SysDict;
import com.power.common.entity.SysModule;
import com.power.common.entity.SysModuleButton;
import com.power.common.entity.SysUser;
import com.power.common.springmvc.ExtReturn;
import com.power.index.entity.GlobalData;
import com.power.index.service.IndexService;
import com.power.jfgl.service.MzcpService;
import com.power.jfsb.service.JfsbService;
import com.power.jfss.service.JfssService;
import com.power.qingjia.service.QingJiaService;
import com.power.xuexi.service.KaoShiService;
import com.power.xuexi.service.XueXiService;

/**
 * 首页
 * 项目名称：power2016 <br>
 * 类名称：IndexController <br>
 * 创建时间：2016-9-5 上午9:48:09 <br>
 * @author LRF <br>
 * @version 1.0
 */
@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private AuthService authService;
    @Autowired
    private MzcpService mzcpService;
    @Autowired
    private XueXiService xueXiService;
    @Autowired
    private JfsbService jfsbService;
    @Autowired
    private JfssService jfssService;
    @Autowired
    private QingJiaService qingJiaService;
    @Autowired
    private KaoShiService kaoShiService;
    
    /**
     * home
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/home")
    public String home(HttpServletRequest req,Model model) {
    	AuthUserVO user = UserUtils.getAuthUser();
    	String title=user.getOrgName();
		if(user.getDeptId() == null && user.getEnabledmark()==102001){//中心
			model.addAttribute("orgId", user.getOrgId());
		}else if(user.getDeptId()!=null){//单位
			model.addAttribute("deptId", user.getDeptId());
			title = user.getDeptName();
		}
		model.addAttribute("title", title);
        return "home";
    }
    
    /**
     * 待办事件数量 拉取
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/poll")
    @ResponseBody
    public Object poll(Model model) {
    	Map<String,Object> reMap= Maps.newConcurrentMap();
    	AuthUserVO user = UserUtils.getAuthUser();
    	Integer mzcp = mzcpService.getMzcpRw(user.getUid());//民主测评待处理
    	Integer xuexi = xueXiService.getXxDcl(user.getUid());//学习待处理
    	Integer sbsp = jfsbService.getSbSpSl(user.getUid());//申报审批待处理
    	Integer sssp = jfssService.getSsSpSl(user.getUid());//申诉审批待处理
    	Integer qjsp = qingJiaService.getQjSpcl(user.getUid());//请假审批待处理
    	Integer ks = kaoShiService.getKsDcl(user.getUid());//学习待处理
    	
    	reMap.put("mzcp", mzcp);
    	reMap.put("xuexi", xuexi);
    	reMap.put("sbsp", sbsp);
    	reMap.put("sssp", sssp);
    	reMap.put("qjsp", qjsp);
    	reMap.put("ks", ks);
    	return new ExtReturn(true,reMap);
    }
    /**
     * 获取全局参数
     * @return
     */
    @RequestMapping("/getGlobalData")
    @ResponseBody
    public Object getGlobalData() {
    	Map<String,Object> map = new HashMap<String, Object>();
    	//字典
    	List<SysDict> dictList = indexService.getDict("IndexDict");
    	List<SysDict> dictClassList = Lists.newArrayList(Collections2.filter(dictList, new Predicate<SysDict>() {
            public boolean apply(SysDict dict) {
                return dict.getDictid().length()==3;
            }
        }));
    	//
    	Map<String,Object> dictClassMap = new HashMap<String, Object>();
    	for (SysDict dictCls : dictClassList) {
    		String classKey = dictCls.getDictid();
    		Map<String,String> dictMap = new HashMap<String, String>();
			for(SysDict dict:dictList){
				if(classKey.equals(StringUtils.left(dict.getDictid(), classKey.length()))){//子类
					dictMap.put(dict.getDictid(), dict.getFullname());
				}
			}
			dictClassMap.put(classKey, dictMap);
		}
    	map.put("dict", dictClassMap);
    	
    	//组织机构列表
    	List<GlobalData> orgList = indexService.getOrg();
    	Map<String,GlobalData> orgMap = new HashMap<String,GlobalData>();
    	for (GlobalData kv : orgList) {
    		orgMap.put(kv.getId(), kv);
		}
    	//
    	map.put("org", orgMap);
    	//部门信息列表
    	List<GlobalData> deptList = indexService.getDept();
    	Map<String,GlobalData> deptMap = new HashMap<String,GlobalData>();
    	for (GlobalData kv : deptList) {
    		deptMap.put(kv.getId(), kv);
		}
    	//
    	map.put("dept", deptMap);
    	//用户
    	List<SysUser> userList = indexService.getUser("IndexUser");
    	Map<String,SysUser> userMap = new HashMap<String,SysUser>();
    	for (SysUser user : userList) {
    		userMap.put(user.getUserid(), user);
		}
    	map.put("user", userMap);
    	//AuthMenu
    	AuthUserVO user = UserUtils.getAuthUser();
    	List<SysModule> authMenu = authService.getModuleAuthByUid(user.getUid());
    	map.put("authMenu", authMenu);
    	//AuthBtn按钮
    	List<SysModuleButton> btnList = authService.getModuleBtnAuthByUid(user.getUid());
    	Map<String,List<SysModuleButton>> btnMap = Maps.newHashMap();
    	for (SysModule module : authMenu) {
    		final String moduleid = module.getModuleid();
    		List<SysModuleButton> tempBtn = Lists.newArrayList(Collections2.filter(btnList, new Predicate<SysModuleButton>() {
                public boolean apply(SysModuleButton btn) {
                    return btn.getModuleid().equals(moduleid);
                }
            }));
    		if(tempBtn.size()>0){
    			btnMap.put(moduleid, tempBtn);
    			btnList.removeAll(tempBtn);//移除使用过多元素
    		}
		}
    	map.put("authBtn", btnMap);
    	
//	    clientpostData = data.post;
//	    clientroleData = data.role;
//	    clientuserGroup = data.userGroup;
//	    authorizeColumnData = data.authorizeColumn;
    	return map;
    }
    
    
    
//    @RequestMapping("/401")
//	public String authorizationException(HttpServletRequest request,HttpServletResponse response) {
//		if (Servlets.isAjaxRequest(request)) {// ajax 请求
//			ResponseUtils.renderJson(response, "{\"success\":false,\"msg\":\"无操作权限！\"}");
//			return null;
//		} else {
//			return "redirect:/res/error/403.html";
//		}
//	}
    
    @RequestMapping("/personcenter/index")
    public String pcIndex(HttpServletRequest req,Model model){
    	model.addAttribute("uid",UserUtils.getAuthUser().getUid());
    	return "personcenter";
    }
}
