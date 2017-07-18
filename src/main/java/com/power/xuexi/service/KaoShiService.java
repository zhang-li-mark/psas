package com.power.xuexi.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.power.base.sys.entity.AuthUserVO;
import com.power.base.sys.service.UserUtils;
import com.power.common.entity.KsKaoShi;
import com.power.common.entity.KsShiJuan;
import com.power.common.entity.KsShiJuanCtjl;
import com.power.common.entity.KsTiMu;
import com.power.common.mybatis.DaoHelper;
import com.power.common.mybatis.page.JqGrid;
import com.power.xuexi.entity.KsTotalList;
import com.power.xuexi.entity.UserVO;

@Service("KaoShiService")
public class KaoShiService {

	@Autowired
	private DaoHelper daoHelper;
	
	/**
	 * 获取考试题目
	 * @param ksTiMu
	 */
	public KsTiMu getKsTm(Long id){
		return daoHelper.findObject(KsTiMu.class, id);
	}
	
	/**
	 * 保存考试题目
	 * @param ksTiMu
	 */
	public void newKsTm(KsTiMu ksTiMu){
		daoHelper.insertSelective(ksTiMu);
	}
	
	
	/**
	 * 删除题目信息
	 * @param xh
	 */
	public void delKsTm(Long xh){
		daoHelper.delete(KsTiMu.class, xh);
	}
	

	/**
	 * 修改考试题目
	 * @param ksTiMu
	 */
	public void editKsTm(KsTiMu ksTiMu){
		daoHelper.updateSelective(ksTiMu);
	}
	
	/**
	 * 获取考试题目列表
	 * @param map
	 * @return
	 */
	public List<KsTiMu> getKsTms(Map<String,Object> map,JqGrid grid){
		
		return daoHelper.findRows("mapper.KsTiMuMapper.getKsTms", map, grid);
	}
	
	/**
	 * 获取考试任务列表
	 * @param map
	 * @return
	 */
	public List<KsKaoShi> getKsRws(Map<String,Object> map,JqGrid grid){
		
		return daoHelper.findRows("mapper.KsKaoShiMapper.getKsRws", map,grid);
	}
	
	/**
	 * 获取考试任务详情
	 * @param map
	 * @return
	 */
	public KsKaoShi getKsRw(Long id){
		
		return daoHelper.findObject(KsKaoShi.class, id);
	}
	
	/**
	 * 修改考试任务信息
	 * @param ksKaoShi
	 */
	public void editKsRw(KsKaoShi ksKaoShi){
		//更新任务表信息
		daoHelper.updateSelective(ksKaoShi);
		
		String dws = ksKaoShi.getDws();
		String kstmid = ksKaoShi.getKstmid();
		
		if(!StringUtils.isBlank(dws)||!StringUtils.isBlank(kstmid)){
			//删除试卷表信息
			daoHelper.delete("mapper.KsShiJuanMapper.deleteByKsID", ksKaoShi.getId());
			//重新插入考试试卷表
			
			AuthUserVO user = UserUtils.getAuthUser();
			
			String[] bmIds = ksKaoShi.getDws().split(",");
			
			String[] tmIds = ksKaoShi.getKstmid().split(",");
			Integer zongfen = 0;
			StringBuffer tm = new StringBuffer();
			StringBuffer da = new StringBuffer();
			for(int i=0;i<tmIds.length;i++){
				String tmID = tmIds[i];
				KsTiMu ksTiMu = daoHelper.findObject(KsTiMu.class,Long.parseLong(tmID));
				zongfen+=ksTiMu.getTmfs();
				tm.append(ksTiMu.getXh()+"#");
				da.append(ksTiMu.getZqda()+"#");
			}
			// 题目和答案
			String tms = tm.substring(0, tm.length()-1);
			String das = da.substring(0, da.length()-1);
			
			
			for(int i=0;i<bmIds.length;i++){
				String bmID = bmIds[i];
				
				// 查询部门人员信息
				List<UserVO> users = daoHelper.findAll("mapper.GrXuexiBmMapper.getUsersByDeptID", bmID);
				
				//插入试卷表部门
				KsShiJuan ksShiJuan = new KsShiJuan();
				ksShiJuan.setKsid(ksKaoShi.getId());
				ksShiJuan.setSjbz(1);
				ksShiJuan.setSjmc(ksKaoShi.getKszt());
				ksShiJuan.setDwxh(bmID);
				ksShiJuan.setKssj(ksKaoShi.getKssj());
				ksShiJuan.setJssj(ksKaoShi.getJssj());
				ksShiJuan.setTmbh(tms);
//				ksShiJuan.setDa(das);
				ksShiJuan.setTmgs(tmIds.length);
				ksShiJuan.setSjzt(0);
				ksShiJuan.setSjzf(zongfen);
				ksShiJuan.setXksrs(users.size());
				ksShiJuan.setKsywcrs(0);
				ksShiJuan.setLrrxh(user.getUid());
				ksShiJuan.setLrrmc(user.getRealName());
				ksShiJuan.setLrsj(new Date());
				ksShiJuan.setQzf(ksKaoShi.getJf());//
				ksShiJuan.setSjlx(ksKaoShi.getSjlx());//
				daoHelper.insertSelective(ksShiJuan);
				
				//插入人员考试表
				for(int j=0;users!=null&&j<users.size();j++){
					KsShiJuan ksShiJuanRy = new KsShiJuan();
					ksShiJuanRy.setKsid(ksKaoShi.getId());
					ksShiJuanRy.setSjbz(2);
					ksShiJuanRy.setSjmc(ksKaoShi.getKszt());
					ksShiJuanRy.setDwxh(bmID);
					ksShiJuanRy.setKssj(ksKaoShi.getKssj());
					ksShiJuanRy.setJssj(ksKaoShi.getJssj());
					ksShiJuanRy.setTmbh(tms);
//					ksShiJuanRy.setDa(das);
					ksShiJuanRy.setTmgs(tmIds.length);
					ksShiJuanRy.setSjzt(0);
					ksShiJuanRy.setSjzf(zongfen);
					ksShiJuanRy.setLrrxh(user.getUid());
					ksShiJuanRy.setLrrmc(user.getRealName());
					ksShiJuanRy.setLrsj(new Date());
					ksShiJuanRy.setQzf(ksKaoShi.getJf());//
					ksShiJuanRy.setKsrxh(users.get(j).getUid());
					ksShiJuanRy.setSjlx(ksKaoShi.getSjlx());//
					daoHelper.insertSelective(ksShiJuanRy);
				}
				
			}
			
		}else{
			//更新试卷表试卷名称和考试积分
			KsShiJuan ksShiJuan = new KsShiJuan();
			ksShiJuan.setKsid(ksKaoShi.getId());
			ksShiJuan.setSjmc(ksKaoShi.getKszt());
			ksShiJuan.setQzf(ksKaoShi.getJf());//
			ksShiJuan.setSjlx(ksKaoShi.getSjlx());//
			daoHelper.update("mapper.KsShiJuanMapper.updateJFAndTmByKsID", ksShiJuan);
		}
		
	}
	
	/**
	 * 添加考试任务信息
	 * @param ksKaoShi
	 */
	public void newKsRw(KsKaoShi ksKaoShi){
		AuthUserVO user = UserUtils.getAuthUser();
		
		String[] bmIds = ksKaoShi.getDws().split(",");
		ksKaoShi.setZpbms(bmIds.length);
		
		String[] tmIds = ksKaoShi.getKstmid().split(",");
		Integer zongfen = 0;
		StringBuffer tm = new StringBuffer();
		StringBuffer da = new StringBuffer();
		for(int i=0;i<tmIds.length;i++){
			String tmID = tmIds[i];
			KsTiMu ksTiMu = daoHelper.findObject(KsTiMu.class,Long.parseLong(tmID));
			zongfen+=ksTiMu.getTmfs();
			tm.append(ksTiMu.getXh()+",");
			da.append(ksTiMu.getZqda()+"#");
		}
		// 题目和答案
		String tms = tm.substring(0, tm.length()-1);
		String das = da.substring(0, da.length()-1);
		
		
		// 插入考试记录表
		ksKaoShi.setZf(BigDecimal.valueOf(zongfen));
		daoHelper.insertSelective(ksKaoShi);
		
		for(int i=0;i<bmIds.length;i++){
			String bmID = bmIds[i];
			
			// 查询部门人员信息
			List<UserVO> users = daoHelper.findAll("mapper.GrXuexiBmMapper.getUsersByDeptID", bmID);
			
			//插入试卷表部门
			KsShiJuan ksShiJuan = new KsShiJuan();
			ksShiJuan.setKsid(ksKaoShi.getId());
			ksShiJuan.setSjbz(1);
			ksShiJuan.setSjmc(ksKaoShi.getKszt());
			ksShiJuan.setDwxh(bmID);
			ksShiJuan.setKssj(ksKaoShi.getKssj());
			ksShiJuan.setJssj(ksKaoShi.getJssj());
			ksShiJuan.setTmbh(tms);
//			ksShiJuan.setDa(das);//
			ksShiJuan.setTmgs(tmIds.length);
			ksShiJuan.setSjzt(0);
			ksShiJuan.setSjzf(zongfen);
			ksShiJuan.setXksrs(users.size());
			ksShiJuan.setKsywcrs(0);
			ksShiJuan.setLrrxh(user.getUid());
			ksShiJuan.setLrrmc(user.getRealName());
			ksShiJuan.setLrsj(new Date());
			ksShiJuan.setQzf(ksKaoShi.getJf());//
			ksShiJuan.setSjlx(ksKaoShi.getSjlx());//
			daoHelper.insertSelective(ksShiJuan);
			
			//插入人员考试表
			for(int j=0;users!=null&&j<users.size();j++){
				KsShiJuan ksShiJuanRy = new KsShiJuan();
				ksShiJuanRy.setKsid(ksKaoShi.getId());
				ksShiJuanRy.setSjbz(2);
				ksShiJuanRy.setSjmc(ksKaoShi.getKszt());
				ksShiJuanRy.setDwxh(bmID);
				ksShiJuanRy.setKssj(ksKaoShi.getKssj());
				ksShiJuanRy.setJssj(ksKaoShi.getJssj());
				ksShiJuanRy.setTmbh(tms);
				//ksShiJuanRy.setDa(das);
				ksShiJuanRy.setTmgs(tmIds.length);
				ksShiJuanRy.setSjzt(0);
				ksShiJuanRy.setSjzf(zongfen);
				ksShiJuanRy.setLrrxh(user.getUid());
				ksShiJuanRy.setLrrmc(user.getRealName());
				ksShiJuanRy.setLrsj(new Date());
				ksShiJuanRy.setQzf(ksKaoShi.getJf());//
				ksShiJuanRy.setKsrxh(users.get(j).getUid());
				ksShiJuanRy.setSjlx(ksKaoShi.getSjlx());//
				daoHelper.insertSelective(ksShiJuanRy);
			}
			
		}
	}
	
	/**
	 * 删除考试任务
	 * @param xh
	 */
	public void delKsrw(Long xh){
		// 删除考试任务表
		daoHelper.delete(KsKaoShi.class, xh);
		//删除试卷表信息
		daoHelper.delete("mapper.KsShiJuanMapper.deleteByKsID", xh);
	}
	
	/**
	 * 获取待考试试卷列表
	 * @param map
	 * @return
	 */
	public List<KsShiJuan> getDKs(Map<String,Object> map,JqGrid grid){
		
		return daoHelper.findRows("mapper.KsShiJuanMapper.getDKs", map,grid);
	}
	/**
	 * 考试试卷
	 * @param xh
	 * @return
	 */
	public KsShiJuan getKsShiJuanByXh(Long xh){
		return daoHelper.findObject(KsShiJuan.class, xh);
	}
	/**
	 * 考试试卷,题目列表
	 * @param xh
	 * @return
	 */
	public List<KsTiMu> getTiMuByKsxh(Long xh){
		return daoHelper.findAll("mapper.KsTiMuMapper.getTiMuByKsxh", xh);
	}
	/**
	 * 考试结果提交，阅卷
	 * @param sjxh
	 * @param da
	 */
	public void newKsSubmit(Long sjxh, String da) {
		List<KsTiMu> tms = getTiMuByKsxh(sjxh);
		AuthUserVO user = UserUtils.getAuthUser();
		Date d = new Date();
		//开始阅卷，计算积分
		//list to map
		Map<Long, KsTiMu> timuMap = Maps.uniqueIndex(tms, new Function<KsTiMu,Long>() {
			public Long apply(KsTiMu tm) {
				return tm.getXh();
			}
		});
		//未剔除空行
		Map<String, String> das = Splitter.on("&").withKeyValueSeparator("=").split(da);
//		StringBuffer daStr=new StringBuffer();
		StringBuffer dfStr=new StringBuffer();
		Integer ctsl = 0;
		Integer kszf = 0;
		for (Map.Entry<String, String> entry : das.entrySet()) {
//			System.err.println(entry.getKey()+"-----"+entry.getValue());
			//
			KsTiMu timu = timuMap.get(Long.parseLong(entry.getKey()));
//			daStr.append("#").append(timu.getZqda());
			//TODO 区分单选/多选/
			if(timu.getZqda().equals(entry.getValue())){
				dfStr.append("#").append(timu.getXh()).append("=").append(timu.getTmfs());
				kszf = kszf + timu.getTmfs();
			}else{//TODO 统计题目错题次数
				ctsl++;
				dfStr.append("#").append(timu.getXh()).append("=").append(0);
				//错题记录
				KsShiJuanCtjl ctjl = new KsShiJuanCtjl();
				ctjl.setRyxh(user.getUid());
				ctjl.setSjxh(sjxh);
				ctjl.setTjsj(d);
				ctjl.setTmxh(timu.getXh());
				ctjl.setZqda(timu.getZqda());
				ctjl.setXzda(entry.getValue());
				ctjl.setKf(timu.getTmfs());
				daoHelper.insert(ctjl);
			}
		}
		//封装试卷结果信息
		KsShiJuan sj = getKsShiJuanByXh(sjxh);//new KsShiJuan();
		sj.setXh(sjxh);
//		sj.setDa(daStr.deleteCharAt(0).toString());
		sj.setDa(da);
		sj.setDfmx(dfStr.deleteCharAt(0).toString());
		sj.setKsdf(kszf);//考试得分
		sj.setSjzt(1);//1已完成
		sj.setLrrxh(user.getUid());
		sj.setLrrmc(user.getRealName());
		sj.setLrsj(d);
		
		//根据权重计算 ，积分
		if(sj.getSjzf()!=0){
			BigDecimal dfbl = new BigDecimal(sj.getKsdf()).divide(new BigDecimal(sj.getSjzf()), 1, BigDecimal.ROUND_HALF_UP);
			BigDecimal qzf = new BigDecimal(sj.getQzf()==null?0:sj.getQzf());
			sj.setZzjf(dfbl.multiply(qzf).setScale(1,  BigDecimal.ROUND_HALF_UP));//
		}else{
			sj.setZzjf(BigDecimal.ZERO);
		}
		daoHelper.updateSelective(sj);
		//更新考试已完成人数，计算考试积分
		Map<String, Object> param=Maps.newHashMap();
		param.put("id",sjxh);//
		param.put("jflx",3);//1请假，2学习，3考试
		daoHelper.update("mapper.JfTjJcxxMapper.sp_tj_qjxxks",param);
	}
	
	
	
	/**
	 * 查询用户考试所得积分明细
	 * @param userid 用户编号(必选)
	 * @param kssj   开始时间 (可选) 格式 "YYYY-MM-dd HH:ss"
	 * @param jssj   结束时间(可选)
	 * @return
	 */
	public List<KsTotalList> getUserXxTotalList(String userid,String kssj,String jssj){
		Map<String,Object> map = new  HashMap<String,Object>();
		
		if(StringUtils.isBlank(userid)){
			return null;
		}
		
		map.put("userid", userid);
		
		if(!StringUtils.isBlank(kssj)){
			map.put("kssj", kssj);
		}
		
		if(!StringUtils.isBlank(jssj)){
			map.put("jssj", jssj);
		}
		return daoHelper.findAll("mapper.KsShiJuanMapper.getUserKsTotalList", map);
	}
	
	/**
	 * 获取待考试数量
	 * @param uid
	 * @return
	 */
	public Integer getKsDcl(String uid){
		return (Integer)daoHelper.findObject("mapper.KsShiJuanMapper.getKsDcl", uid);
	}
	
}
