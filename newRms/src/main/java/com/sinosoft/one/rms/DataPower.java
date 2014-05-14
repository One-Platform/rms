package com.sinosoft.one.rms;


import java.util.List;

import org.springframework.util.Assert;

import com.sinosoft.one.rms.model.BusDataInfo;

public class DataPower  {
	
	private String userCode;
	
	private String comCode;
	
	private String taskId;
	
	private String ruleId;
	
	private String rule;
	
	private String param;
	
	private List<BusDataInfo> busDataInfos;
	
	public DataPower(){
	}
	
	public  DataPower(final String userCode,final String comCode,final String taskId,final String ruleId,final String param,final String rule,final List<BusDataInfo> busDataInfos){
		Assert.hasText(userCode);
		Assert.hasText(comCode);
		Assert.hasText(taskId);
		Assert.hasText(ruleId);
		this.userCode=userCode;
		this.comCode=comCode;
		this.taskId=taskId;
		this.ruleId=ruleId;
		if(param!=null&&!"".equals(param))
			this.param=param;
		else
			this.param=null;
		this.rule=rule;
		this.busDataInfos=busDataInfos;
	}

	public String getUserCode() {
		return userCode;
	}

	public String getComCode() {
		return comCode;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public String getParam() {
		return param;
	}

	public String getRule() {
		return rule;
	}

	public List<BusDataInfo> getBusDataInfos() {
		return busDataInfos;
	}
	
	public void setUserCode(String userCode) {
		if(this.userCode ==null){
			this.userCode = userCode;
		}
	}
	
	public void setComCode(String comCode) {
		if(this.comCode==null){
			this.comCode = comCode;
		}
		
	}
	
	public void setTaskId(String taskId) {
		if(this.taskId ==null){
			this.taskId = taskId;
		}
	}

	public void setRuleId(String ruleId) {
		if(this.ruleId ==null){
			this.ruleId = ruleId;
		}
	}

	public void setRule(String rule) {
		if(this.rule ==null){
			this.rule = rule;
		}
	}

	public void setParam(String param) {
		if(this.param ==null){
			this.param = param;
		}
	}

	public void setBusDataInfos(List<BusDataInfo> busDataInfos) {
		if(	this.busDataInfos ==null){
			this.busDataInfos = busDataInfos;
		}
	}

	
	
}
