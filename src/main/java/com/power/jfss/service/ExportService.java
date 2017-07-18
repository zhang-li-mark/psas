package com.power.jfss.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.power.base.sys.entity.KeyValue;
import com.power.common.mybatis.DaoHelper;

@Service("exportService")
public class ExportService {
	
	@Autowired
	private DaoHelper daoHelper;
	
	public List<Object> selectStatExport(HashMap<String, Object> map){
	    List<Object> list = daoHelper.findAll("mapper.JfTjHzMapper.getUserExport", map);
		return list;
	}
	public List<Object> selectJfExport(HashMap<String, Object> map){
		List<Object> list = daoHelper.findAll("mapper.JfTjHzMapper.getJfTJExport", map);
		return list;
	}
	
	public  Map<String,String> getPartStatHeadMap(){
		 Map<String,String> headMap = new LinkedHashMap<String,String>();
		 headMap.put("account", "警号");
		 headMap.put("realname", "姓名");
		 headMap.put("gender", "性别");
		 headMap.put("organizeid", "机构");
		 headMap.put("password", "部门");
		 headMap.put("departmentid", "处室");
		 headMap.put("dutyid", "职位");
		return headMap;
	}
	public  Map<String,String> getPartJfHeadMap(){
		Map<String,String> headMap = new LinkedHashMap<String,String>();
		headMap.put("uname", "姓名");
		headMap.put("bmname", "部门");
		headMap.put("deptid", "处室");
		headMap.put("jcxxjf", "基础信息积分");
		headMap.put("mzcpdf", "民主测评积分");
		headMap.put("gzjf", "业务积分");
		headMap.put("gljf", "管理积分");
		headMap.put("qxjjf", "请休假积分");
		headMap.put("xxjf", "学习积分");
		headMap.put("ksjf", "考试积分");
		headMap.put("zjf", "总积分");
		return headMap;
	}
}
