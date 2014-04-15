package com.sinosoft.one.rms.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.one.rms.model.BusPower;
import com.sinosoft.one.rms.model.DataRule;
import com.sinosoft.one.rms.model.ExcPower;
import com.sinosoft.one.rms.model.Task;
import com.sinosoft.one.rms.model.UserGroup;
import com.sinosoft.one.rms.model.UserPower;
import com.sinosoft.one.rms.repositories.GeRmsBusPowerRepository;
import com.sinosoft.one.rms.repositories.GeRmsDataRuleRepository;
import com.sinosoft.one.rms.repositories.GeRmsExcPowerRepository;
import com.sinosoft.one.rms.repositories.GeRmsGroupRepository;
import com.sinosoft.one.rms.repositories.GeRmsTaskAuthRepository;
import com.sinosoft.one.rms.repositories.GeRmsTaskRepository;
import com.sinosoft.one.rms.repositories.GeRmsUserGroupRepository;
import com.sinosoft.one.rms.repositories.GeRmsUserPowerRepository;

@Transactional
@Component
public class StaffingService {

	@Resource(name="geRmsGroupRepository")
	private GeRmsGroupRepository geRmsGroupRepository;
	@Resource(name="geRmsUserPowerRepository")
	private GeRmsUserPowerRepository geRmsUserPowerRepository;
	@Resource(name="geRmsTaskRepository")
	private GeRmsTaskRepository geRmsTaskRepository;
	@Resource(name="geRmsTaskAuthRepository")
	private GeRmsTaskAuthRepository geRmsTaskAuthRepository;
	@Resource(name="geRmsDataRuleRepository")
	private GeRmsDataRuleRepository geRmsDataRuleRepository;
	@Resource(name="geRmsExcPowerRepository")
	private GeRmsExcPowerRepository geRmsExcPowerRepository;
	@Resource(name="geRmsUserGroupRepository")
	private GeRmsUserGroupRepository geRmsUserGroupRepository;
	@Resource(name="geRmsBusPowerRepository")
	private GeRmsBusPowerRepository geRmsBusPowerRepository;
	
	//检查用户权限的id是否存在，存在返回yes，否则返回no
	public String checkIdByUserCodeComCode(String userCode, String comCode) {
		System.out.println(userCode);
		System.out.println(comCode);
		String id = geRmsUserPowerRepository.findIdByUserCodeComCode(userCode, comCode);
		String result = "no";
		if(id != null){
			result = "yes";
		}
		return result;
	}
	
	//保存用户的权限除外表、用户权限表和用户与组关系表
	public void savePower(String comCode, String userCode, String groupIdStr, String taskIdStr) {
		
		String userPowerId = geRmsUserPowerRepository.findIdByUserCodeComCode(userCode, comCode);
		if(userPowerId == null){
			UserPower up = new UserPower();
			up.setComCode(comCode);
			up.setUserCode(userCode);
			up.setIsValidate("1");
			geRmsUserPowerRepository.save(up);
			
			String[]groupIds = groupIdStr.split(",");
			if(groupIds.length > 0){
				for(String id : groupIds){
					UserGroup ug = new UserGroup();
					ug.setGroup(geRmsGroupRepository.findOne(id));
					ug.setUserCode(userCode);
					ug.setUserPower(up);
					ug.setIsValidate("1");
					geRmsUserGroupRepository.save(ug);
					
				}
			}
			
			if(!taskIdStr.toString().equals("null")){
				String[] taskId = taskIdStr.split(",");
				if(taskId.length > 0){
					for(String id : taskId){
						ExcPower ep = new ExcPower();
						ep.setTask(geRmsTaskRepository.findOne(id));
						ep.setUserPower(up);
						ep.setIsValidate("1");
						geRmsExcPowerRepository.save(ep);
					}
				}
			}
		}else{
			UserPower userPower = geRmsUserPowerRepository.findOne(userPowerId);
//			geRmsUserPowerRepository.delete(userPower);
//			删除关联用户组记录
			List<UserGroup> userGroups = userPower.getUserGroups();
			if(!userGroups.isEmpty()){
				
				System.out.println(userGroups.size());
				List<String> userGroupIds = new ArrayList<String>();
				for(UserGroup ug : userGroups){
					userGroupIds.add(ug.getUserGropuID());
				}
				geRmsUserGroupRepository.deleteUserPower(userGroupIds);
//			geRmsUserGroupRepository.delete(userGroups);
			}

			//删除关联权限除外表记录
			List<ExcPower> excPowers = userPower.getExcPowers();
			System.out.println(excPowers.size());
			if(!excPowers.isEmpty()){
				
				List<String> excPowerIds = new ArrayList<String>();
				for(ExcPower ep : excPowers){
					excPowerIds.add(ep.getExcPowerID());
				}
				
				geRmsExcPowerRepository.deleteExcPower(excPowerIds);
//				geRmsExcPowerRepository.delete(excPowers);
			}
			
			String[]groupIds = groupIdStr.split(",");
			if(groupIds.length > 0){
				for(String id : groupIds){
					UserGroup ug = new UserGroup();
					ug.setGroup(geRmsGroupRepository.findOne(id));
					ug.setUserCode(userCode);
					ug.setUserPower(userPower);
					ug.setIsValidate("1");
					geRmsUserGroupRepository.save(ug);
					
				}
			}
			
			if(!taskIdStr.toString().equals("null")){
				String[] taskId = taskIdStr.split(",");
				if(taskId.length > 0){
					for(String id : taskId){
						ExcPower ep = new ExcPower();
						ep.setTask(geRmsTaskRepository.findOne(id));
						ep.setUserPower(userPower);
						ep.setIsValidate("1");
						geRmsExcPowerRepository.save(ep);
					}
				}
			}
		}

	}
	
	//查询出没有赋参数的数据规则
	public List<DataRule> getRules(String comCode, String userCode) {
		
		List<DataRule> delList = new ArrayList<DataRule>();
		List<DataRule> dataRules = (List<DataRule>) geRmsDataRuleRepository.findAll();
		
		List<String> dataRuleIds = getDataRuleIds(comCode, userCode);
		for(DataRule dataRule : dataRules){
			if(dataRuleIds.isEmpty())
				break;
			if(dataRuleIds.contains(dataRule.getDataRuleID().toString())){
				delList.add(dataRule);
			}
		}
		dataRules.removeAll(delList);
		return dataRules;
	}

	//查询出有参数的数据规则
	public List<DataRule> getRuleParam(String comCode, String userCode) {
		List<DataRule> delList = new ArrayList<DataRule>();
		List<DataRule> dataRuleParam = (List<DataRule>) geRmsDataRuleRepository.findAll();
		
		List<String> dataRuleIds = getDataRuleIds(comCode, userCode);

		for(DataRule dataRule : dataRuleParam){
			if(dataRuleIds.isEmpty()){
				dataRuleParam.clear();
				break;
			}
			if(!dataRuleIds.contains(dataRule.getDataRuleID().toString())){
				delList.add(dataRule);
			}
		}
		dataRuleParam.removeAll(delList);
		return dataRuleParam;
	}	
	
	//取得在人员数据权限表中相应的数据规则Id
	public List<String> getDataRuleIds(String comCode, String userCode){
		List<String> dataRuleIds = new ArrayList<String>();
		String userPowerId = geRmsUserPowerRepository.findIdByUserCodeComCode(userCode, comCode);
		
		//查询出用户权限
		UserPower userPower = geRmsUserPowerRepository.findOne(userPowerId);
		
		//根据用户权限查询出人员数据权限
		List<BusPower> busPowers = userPower.getBusPowers();

		for(BusPower busPower : busPowers){
			if(!dataRuleIds.contains(busPower.getDataRule().getDataRuleID().toString())){
				dataRuleIds.add(busPower.getDataRule().getDataRuleID().toString());
			}
		}
		
		return dataRuleIds;

	}
	
	//查询数据规则的参数
	public List<BusPower> getParams(String comCode, String userCode,
			String dataRuleIdStr) {
		String[] dataRuleIds = dataRuleIdStr.split(",");
		String userPowerId = geRmsUserPowerRepository.findIdByUserCodeComCode(userCode, comCode);
		List<String> busPowerIds = geRmsBusPowerRepository.findBusPowerIdByUserPowerIdTaskId(userPowerId, dataRuleIds);
		System.out.println(busPowerIds.size());
		
		List<String> resultIds =new ArrayList<String>();
		List<BusPower> resultBusPowers = new ArrayList<BusPower>();

		List<BusPower> busPowers = (List<BusPower>) geRmsBusPowerRepository.findAll(busPowerIds);
		for(BusPower busPower : busPowers){
			if(!resultIds.contains(busPower.getDataRule().getDataRuleID().toString())){
				resultIds.add(busPower.getDataRule().getDataRuleID().toString());

				resultBusPowers.add(busPower);
			}
		}
		
		return resultBusPowers;
	}

	//保存数据设置
	public String saveBusPower(String comCode, String userCode,String ruleIdStr, String paramStr) {
		String userPowerId = geRmsUserPowerRepository.findIdByUserCodeComCode(userCode, comCode);
		UserPower userPower = geRmsUserPowerRepository.findOne(userPowerId);
		String[]dataRuleIds = ruleIdStr.split(",");
		String[] params = paramStr.split(",");
		
		List<String> resultDataRuleIds = new ArrayList<String>();
		for(String id : dataRuleIds){
			resultDataRuleIds.add(id);
		}
		
		//删除原先的人员数据权限记录
		List<String> busPowerIds = geRmsBusPowerRepository.findBusPowerIdByUserPowerId(userPowerId);
		if(!busPowerIds.isEmpty()){
			
//			List<BusPower> busPowers = (List<BusPower>) geRmsBusPowerRepository.findAll(busPowerIds);
			System.out.println("ID数："+busPowerIds.size());
//			System.out.println("记录数："+busPowers.size());
			
			geRmsBusPowerRepository.deleteBusPower(busPowerIds);
		}
		
		//查询需要添加的功能
		//查询功能授权表中的功能ID
		List<String> taskIds1 = geRmsTaskAuthRepository.findAllTaskIdByComCode(comCode);
		//查询人员权限除外表中的功能ID
		List<String> taskIds2 = geRmsExcPowerRepository.findTaskIdByPowerId(userPowerId);
		
		List<String> resultIds = new ArrayList<String>();
		for(String id : taskIds1){
			if(!taskIds2.contains(id.toString())){
				resultIds.add(id);
			}
		}
		
		List<Task> resultTasks = (List<Task>) geRmsTaskRepository.findAll(resultIds);
		List<DataRule> dataRules = (List<DataRule>) geRmsDataRuleRepository.findAll(resultDataRuleIds);
		
		for(int i = 0;i<resultTasks.size();i++){
			for(int j = 0;j<dataRules.size();j++){
				BusPower busPower = new BusPower();
				
				busPower.setDataRule(dataRules.get(j));
				busPower.setDataRuleParam(params[j]);
				busPower.setTask(resultTasks.get(i));
				busPower.setUserPower(userPower);
				busPower.setIsValidate("1");
				
				geRmsBusPowerRepository.save(busPower);
			}
		}
		return "success";
	}

	public List<UserPower> findUserPowerByUserCode(String userCode) {
		
		List<String> userPowerIds = geRmsUserPowerRepository.findUserPowerIdByUserCode(userCode);
		List<UserPower> userPowers = (List<UserPower>) geRmsUserPowerRepository.findAll(userPowerIds);
		
		return userPowers;
	}

	public UserPower findUserPowerByUserCode(String userCode, String comCode) {
		String userPowerId = geRmsUserPowerRepository.findIdByUserCodeComCode(userCode, comCode);
		UserPower userPower = null;
		if(userPowerId != null){
			
			userPower = geRmsUserPowerRepository.findOne(userPowerId);
		}
		return userPower;
	}
		
}
