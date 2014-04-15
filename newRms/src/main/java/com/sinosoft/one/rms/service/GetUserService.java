package com.sinosoft.one.rms.service;

import ins.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.sinosoft.one.rms.DataPower;
import com.sinosoft.one.rms.Menu;
import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.model.BusPower;
import com.sinosoft.one.rms.model.Company;
import com.sinosoft.one.rms.model.Employe;
import com.sinosoft.one.rms.model.ExcPower;
import com.sinosoft.one.rms.model.Group;
import com.sinosoft.one.rms.model.GroupRole;
import com.sinosoft.one.rms.model.Role;
import com.sinosoft.one.rms.model.Task;
import com.sinosoft.one.rms.model.UserGroup;
import com.sinosoft.one.rms.model.UserPower;
@Component
public class GetUserService {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private EmployeeService employeService;
	@Autowired
	private StaffingService staffingService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private TaskService taskServer;

	/**
	 * 创建USER对象
	 */
	public User getUserByUserCodeComCode(String userCode, String comCode,String sysFlag) {
		try{

			//获取用户
			Employe employe = findUserByCode(userCode);
			Assert.notNull(employe);
			//获取用户权限
			UserPower userPower = findUserPowerByComUser(userCode, comCode);
			Assert.notNull(userPower);
			
			//获取用户归属机构
			Company company = findCompanyByComCode(comCode);
			Assert.notNull(company);
			
			//获取用户已引入的机构
			List<Group> grouplist = findGroupByUser(userCode);
			List<String> groupIdList = new ArrayList<String>();
			for (Group group : grouplist) {
				groupIdList.add(group.getGroupID());
			}
			
			//获取用户已取得的角色
			Set<Role> roles = findRoleByGroup(groupIdList, comCode);
			List<String> roleIdList = new ArrayList<String>();
			for (Role role : roles) {
				roleIdList.add(role.getRoleID());
			}
			
			//获取用户已取得的功能
			List<Task> tasklist = findTaskByUserCode(userCode, comCode, sysFlag);
			List<String> taskIdList = new ArrayList<String>();

			for (Task task : tasklist) {
				taskIdList.add(task.getTaskID());
		
			}
			List<String>urls= new ArrayList<String>();
			List<Menu> menulist = new ArrayList<Menu>();
			List<Task> topList = new ArrayList<Task>();
//			Map<String, Task> filter = new HashMap<String, Task>();
			try {
				for (Task task : tasklist) {
					if(task.getMenuURL()!=null&&!task.getMenuURL().toString().equals("")){
						urls.add(task.getMenuURL());
					}
					if (task.getIsAsMenu()!=null&& task.getIsAsMenu().toString().equals("1")) {
						if (task.getParent() == null) {
							topList.add(task);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//创建功能树
			createList(topList, menulist, tasklist);
			
			//保存用户的数据规则
			List<DataPower> dataPowers = new ArrayList<DataPower>();
			creatDataPowerList(dataPowers,userPower.getBusPowers(),userCode,comCode);
//			String passWord="";
//			if(employe.getPassword()!=null&&!employe.getPassword().equals("")){
//				passWord=employe.getPassword();
//			}
//			System.out.println("用户的密码："+employe.getPassword()+"        用户代码："+employe.getUserCode());
			return new User(employe.getUserCode(),employe.getPassword(),
					employe.getUserName(), company.getComCode(),
					company.getComCName(), roleIdList, taskIdList,menulist,dataPowers,urls);
		 } catch (Exception e) {
			  e.printStackTrace();
		}
		 return null;
		}
//----------------------------------------------------------------------------------------------------//

	Employe findUserByCode(String userCode) {
		Assert.hasText(userCode);
		return employeService.findEmployeByUserCode(userCode);
	}

	/**
	 * 根据用户代码机构代码查询权限
	 * 
	 * @param userCode
	 * @param comCode
	 * @return
	 */
	UserPower findUserPowerByComUser(String userCode, String comCode) {
		UserPower userPower = staffingService.findUserPowerByUserCode(userCode, comCode);
		return userPower;
	}

	Company findCompanyByComCode(String comCode) {
		Assert.hasText(comCode);
		return companyService.findCompanyByComCode(comCode);
	}

	List<Group> findGroupByUser(String userCode) {
		
		List<UserPower> userPowers = new ArrayList<UserPower>();
		userPowers = staffingService.findUserPowerByUserCode(userCode);
		List<Group> groups = new ArrayList<Group>();
		if (userPowers != null) {
			for (UserPower userPower : userPowers) {
				List<UserGroup> userGroups = userPower.getUserGroups();
				if (userGroups != null) {
					for (UserGroup userGroup : userGroups) {
						groups.add(userGroup.getGroup());
					}
				}
			}
		}
		return groups;
	}

	/**
	 * 根据用户组查询关联的角色
	 */
	Set<Role> findRoleByGroup(List<String> groupIDs, String comCode) {
		
		List<Group> groups = groupService.findGroupById(groupIDs);
		if (groups == null) {
			// 异常
		}
		Set<GroupRole> groupRoles = new HashSet<GroupRole>();
		for (Group group : groups) {
			groupRoles.addAll(group.getGroupRoles());
		}
		// 根据机构获得指派的信息 取得roleID 过滤用户组关联的角色
		List<String> roleids = roleService.findRoleIdByComCode(comCode);
		
		Set<Role> roles = new HashSet<Role>();
//		// 根据指派获得的ID过滤用户组角色 获得角色集合
		for (GroupRole groupRole : groupRoles) {
			for (String roleid : roleids) {
				if (groupRole.getRole().getRoleID().toString()
						.equals(roleid.toString())) {
					roles.add(groupRole.getRole());
				}
			}
		}
		return roles;
	}

	/**
	 * 获得员工在机构下有效权限集合
	 */
	 List<Task> findTaskByUserCode(String userCode, String comCode,String sysFlag) {
		List<Task> userTasksResult = new ArrayList<Task>();
		UserPower userPower = new UserPower();
		
		userPower = staffingService.findUserPowerByUserCode(userCode, comCode);
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		if (userPower != null) {
			userGroups = userPower.getUserGroups();
			List<Group> groups = new ArrayList<Group>();
			for (UserGroup usergroup : userGroups) {
				// 用户组的过滤
				if (usergroup.getIsValidate().toString().equals("1".toString())) {
					groups.add(usergroup.getGroup());
				}
			}
			List<GroupRole> groupRoles = new ArrayList<GroupRole>();
			for (Group group : groups) {
				groupRoles.addAll(group.getGroupRoles());
			}
			// 根据机构获得指派的信息 取得roleID 过滤用户组关联的角色
			List<String> roleIds = roleService.findRoleIdByComCode(comCode);
			
			// 根据获得的指派角色信息获得角色ID
			List<String> roleids = new ArrayList<String>();
			for (GroupRole groupRole : groupRoles) {
				if (roleIds.contains(groupRole.getRole().getRoleID().toString())) {
					roleids.add(groupRole.getRole().getRoleID().toString());
				}
			}
			
			List<Task> sourceTasks =taskServer.findTaskByRoleIds(roleids, comCode);
		
			//从根获取开始获得功能对象
			List<Task> resultTasks = new ArrayList<Task>();
			
			iterateGetSubTask(resultTasks, sourceTasks);
			
			
			
			// 获得除外权限
			List<Task> excTasks = new ArrayList<Task>();
			if (userPower != null) {
				for (ExcPower excpower : userPower.getExcPowers()) {
					excTasks.add(excpower.getTask());
				}
			}
//			Set<Task> result = new HashSet<Task>();
			List<Task> result = new ArrayList<Task>();
			// 除外权限和获得的权限过滤
			for (Task task : resultTasks) {
				int i = 0;
				for (Task etask : excTasks) {
					if (task.getTaskID().toString()
							.equals(etask.getTaskID().toString()))
						i = i + 1;
				}
				if (i == 0) {
					result.add(task);
				}
			}
//			Set<Task> ts = new HashSet<Task>();
//			iterateTask(ts, result);
//			userTasksResult = taskArrange(ts);
			userTasksResult = result;
			return userTasksResult;
		} else {
			return userTasksResult;
		}
	}

	/**
	 * 循环获得子节点功能
	 */
	void iterateGetSubTask(List<Task> result, List<Task> sourceTasks) {
		for (Task task : sourceTasks) {
			result.add(task);
			if(task.getChildren().size()>0){
				iterateGetSubTask(result, task.getChildren());
			}
		 
		}
	}

 

//	// 对功能集合排序
//	List<Task> taskArrange(Set<Task> ts) {
//		List<String> ids = new ArrayList<String>();
//		for (Task task : ts) {
//			ids.add(task.getTaskID());
//		}
//		List<Task> tasks = new ArrayList<Task>();
//		if (ids.size() > 0) {
//			QueryRule queryRule = QueryRule.getInstance();
//			queryRule.addIn("taskID", ids);
//			queryRule.addAscOrder("taskID");
//			tasks = super.find(Task.class, queryRule);
//		}
//		return tasks;
//	}

	/**
	 * 根据用户代码 获取机构列表(引入机构,登陆时已引入机构)
	 */
//	List<Company> findComByUserCode(String userCode) {
//		QueryRule queryRule = QueryRule.getInstance();
//		queryRule.addEqual("userCode", userCode);
//		List<UserPower> userPower = super.find(UserPower.class, queryRule);
//		if (userPower == null || userPower.isEmpty()) {
//			// 异常
//		}
//		List<String> comCodes = new ArrayList<String>();
//		for (Iterator<UserPower> iter = userPower.iterator(); iter.hasNext();) {
//			comCodes.add(iter.next().getComCode());
//		}
//		List<Company> companies = new ArrayList<Company>();
//		if (comCodes.size() > 0) {
//			QueryRule queryRuleComcode = QueryRule.getInstance();
//			queryRuleComcode.addIn("comCode", comCodes);
//			companies = super.find(Company.class, queryRuleComcode);
//		}
//		// QueryRule queryComCode = QueryRule.getInstance();
//		// queryComCode.addIn("comCode", comCodes);
//		return companies;
//	}

	
	/**
	 * 组织树状数据
	 * @param list
	 * @param dest
	 * @param filter
	 */
	 void createList(List<Task> list, List<Menu> dest,
			List<Task> filter) {
		for (Iterator<Task> iter = list.iterator(); iter.hasNext();) {
			Task task = iter.next();
			if (!filter.contains(task))
				continue;
			Menu menu = new Menu(task.getTaskID(), task.getMenuURL(),task.getName());
			if (!task.getChildren().isEmpty()) {
				List<Task> newTopTask=new ArrayList<Task>();
				for (Task filterTask : filter) {
					for (Task childrenTask : task.getChildren()){
						if(filterTask.getTaskID().toString().equals(childrenTask.getTaskID().toString())&&"1".toString().equals(childrenTask.getIsAsMenu()))
						newTopTask.add(filterTask);
					}
				}
				List<Menu> ls = new ArrayList<Menu>();
				menu.setChildren(ls);
				createList(newTopTask, ls, filter);
			}
			dest.add(menu);
		}

	}
	
	 void creatDataPowerList(List<DataPower> dataPowers,List<BusPower>busPowers,String userCode,String comCode){
		for (BusPower busPower : busPowers) {
			if(StringUtils.isNotBlank(busPower.getDataRuleParam())&&busPower.getDataRule()!=null){
				DataPower dataPower=new DataPower(userCode,comCode,busPower.getTask().getTaskID(), busPower.getDataRule().getDataRuleID(), busPower.getDataRuleParam(), busPower.getDataRule().getRule(),busPower.getDataRule().getBusDataInfos());
				dataPowers.add(dataPower);
			}else if(busPower.getDataRule()!=null){
				DataPower dataPower=new DataPower(userCode,comCode,busPower.getTask().getTaskID(),busPower.getDataRule().getDataRuleID(), null, busPower.getDataRule().getRule(),busPower.getDataRule().getBusDataInfos());
				dataPowers.add(dataPower);
			}
		}
	}




}
