package com.power.jfgl.service;

import java.util.Date;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.power.common.entity.JfTjHz;
import com.power.common.entity.JfTjJcxx;
import com.power.common.entity.SysUser;
import com.power.common.mybatis.DaoHelper;

/**
 * 计算个人基础信息积分
 * 项目名称：psas <br>
 * 类名称：JcxxJfInterceptor <br>
 * @version 1.0
 */
@Aspect
@Component
public class JcxxJfInterceptor {
	//切入点
	@Pointcut("execution (public * com.power.base.sys.service.UserService.newUser(..))")
	private void newUser(){}
	@Pointcut("execution (public * com.power.base.sys.service.UserService.editUser(..))")
	private void editUser(){}
	@Pointcut("execution (public * com.power.jfgl.web.GxpzController.editJfszSubmit(..))")
	private void editCs(){}
	//最终通知
	@After("newUser() &&  args(user)")
	public void newUserAfter(SysUser user){
		//基础信息积分
		JfTjJcxx jcxx = new JfTjJcxx();
		jcxx.setRyxh(user.getUserid());
		jcxx.setGxsj(new Date());
		daoHelper.insert(jcxx);
		//个人汇总信息
//		JfTjHz hz=new JfTjHz();
//		hz.setRyxh(user.getUserid());
//		hz.setNd(DateTime.now().getYear());
//		daoHelper.insert(hz);
		//
		tjJcxxJf(user.getUserid());
	}
	//最终通知
	@After("editUser() &&  args(user)")
	public void editUserAfter(SysUser user){
		tjJcxxJf(user.getUserid());
	}
	//最终通知
	@After("editCs()")
	public void editCsAfter(){
		tjJcxxJf("0");//全部更新
	}
	/**
	 * 统计基础信息积分
	 */
	public void tjJcxxJf(String userid){
		daoHelper.update("mapper.JfTjJcxxMapper.sp_tj_jcxxjf",userid);
	}
	
	@Autowired
	private DaoHelper daoHelper;
	
}
