package com.sinosoft.one.rms.controllers.staffing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.rms.model.BusPower;
import com.sinosoft.one.rms.model.Company;
import com.sinosoft.one.rms.model.DataRule;
import com.sinosoft.one.rms.model.Employe;
import com.sinosoft.one.rms.model.Group;
import com.sinosoft.one.rms.model.Role;
import com.sinosoft.one.rms.model.Task;
import com.sinosoft.one.rms.model.UserGroup;
import com.sinosoft.one.rms.model.UserPower;
import com.sinosoft.one.rms.service.CompanyService;
import com.sinosoft.one.rms.service.EmployeeService;
import com.sinosoft.one.rms.service.GroupService;
import com.sinosoft.one.rms.service.RoleService;
import com.sinosoft.one.rms.service.TaskService;
import com.sinosoft.one.rms.service.UserPowerService;
import com.sinosoft.one.rms.utils.ProceesTaskTreebble;
import com.sinosoft.one.uiutil.GridRender;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.NodeEntity;
import com.sinosoft.one.uiutil.Render;
import com.sinosoft.one.uiutil.TreeRender;
import com.sinosoft.one.uiutil.Treeable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;

@Path
public class StaffingController {
	
	@Autowired
	private UserPowerService staffingService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private GroupService groupService;
	
	private List<String> userAttribute = new ArrayList<String>();
	
	//查询出所有的用户，返回页面
	@Post("userAll")
	@Get("userAll")
	public Reply list(@Param("pageNo") int pageNo, @Param("rowNum")int rowNum,Invocation inv) throws Exception{
		Pageable pageable = new PageRequest(pageNo-1, rowNum);
		
		Gridable<Employe> gridable = new Gridable<Employe>(null);
		gridable = employeeService.getGridable(gridable,pageable,userAttribute);
		
		inv.getResponse().setContentType("text/html;charset=UTF-8");
	    Render render = (GridRender) UIUtil.with(gridable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	//查询用户，并返回相应页面
	@Post("search/{userCode}/{comCode}")
	@Get("search/{userCode}/{comCode}")
	public Reply search(@Param("pageNo") int pageNo, @Param("rowNum")int rowNum,@Param("userCode")String userCode,@Param("comCode")String comCode,Invocation inv) throws Exception{
		Pageable pageable = new PageRequest(pageNo-1, rowNum);
		
		Gridable<Employe> gridable = new Gridable<Employe>(null);
		gridable = employeeService.getGridable(gridable,userCode,comCode, pageable, userAttribute);
		
		inv.getResponse().setContentType("text/html;charset=UTF-8");
	    Render render = (GridRender) UIUtil.with(gridable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	//将用户名和用户ID传到permissionSettings.jsp页面
	@Get("power/{name}/{userCode}")
	public String power(@Param("name")String name,@Param("userCode")String userCode, Invocation inv){
		
		inv.addModel("name", name);
		inv.addModel("userCode", userCode);
		return "permissionSettings";
		
	}
	
	//查询全部机构
	@Get("companyTree")
	public Reply companyList(Invocation inv) throws Exception{

		//查询出全部机构，并出入treeable中
//		Treeable<NodeEntity> treeable = companyService.getTreeable();
		
		List<Company> topCompany = new ArrayList<Company>();
		Map<String,Company> filter = new HashMap<String,Company>();
		List<Company> showCompany = (List<Company>) companyService.findAll();
		for(Company company : showCompany){
			if(company.getUpperComCode() == null){
				topCompany.add(company);
			}
			filter.put(company.getComCode(), company);
			
		}
		
		Treeable<NodeEntity> treeable =companyService.creatCompanyTreeAble(topCompany, filter);
		
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (TreeRender) UIUtil.with(treeable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	//检查机构是否被引入
	@Get("checkCom/{comCode}/{userCode}")
	public Reply checkCom(@Param("comCode")String comCode,@Param("userCode")String userCode, Invocation inv){
		String result = staffingService.checkIdByUserCodeComCode(userCode, comCode);
		return Replys.with(result);
	}
	
	//查询当前机构的用户组，并返回页面
	@Get("group/{comCode}")
	public Reply groupList(@Param("comCode")String comCode,Invocation inv){
		List<Group> groupList = groupService.findGroups(comCode);
		return Replys.with(groupList).as(Json.class);
	}
	
	//查询当前用户组的角色，并返回页面
	@Get("roleList/{groupId}/{comCode}")
	public Reply role(@Param("groupId")String groupId,@Param("comCode")String comCode,Invocation  inv){
		List<Role> groupRoleList = roleService.findRoleByGroupId(groupId, comCode);
		return Replys.with(groupRoleList).as(Json.class);
	}
	
	//查询当前机构，当前用户组，当前角色的根权限
	@Get({"taskList/{comCode}/{roleIdStr}","taskList/{comCode}"})
	public Reply taskList(@Param("comCode")String comCode, @Param("roleIdStr")String roleIdStr,Invocation  inv){
		List<String> roleIDs = new ArrayList<String>();
		if (roleIdStr != null && !roleIdStr.equals("")) {
			String[] roleIds = roleIdStr.split(",");

			for (String roleId : roleIds) {
				roleIDs.add(roleId);
			}
		}
		List<Task> taskList = taskService.findTaskByRoleIds(roleIDs, comCode);
		
		return Replys.with(taskList).as(Json.class);
	}
	
	//查询当前机构，当前用户组,根权限的后代权限
	@Get("taskChildren/{comCode}/{roleIdStr}/{taskId}/{userCode}")
	public Reply taskChildren(@Param("comCode")String comCode,@Param("roleIdStr")String roleIdStr,@Param("taskId")String taskId,@Param("userCode")String userCode,Invocation  inv) throws Exception{
		
		Treeable<NodeEntity> treeable = null;
//		if(userCode.toString().equals("null")){
//			
//			treeable = taskService.getTreeable(roleIdStr, comCode, taskId);
//		}else{
			
			//检查子权限在权限除外表中是否存在
//			treeable = taskService.getTreeable(roleIdStr, comCode, userCode, taskId);
			//获取这个机构下的角色具有的功能的树长度（所有节点）
			List<Task> temtasks=taskService.getTaskChildren(roleIdStr, comCode, taskId);
			List<String> tasks=new ArrayList<String>();
			for (Task task : temtasks) {
				tasks.add(task.getTaskID());
			}
			List<Task> topTasks = new ArrayList<Task>();
			//除去除外权限，即是具有的权限
			List<Task>excTasks=taskService.getexcpowerTask(userCode, comCode);
			//和除外权限过滤
			List<String> taskIdList=new ArrayList<String>();
			
			for (String taskid : tasks) {
				int i=0;
				for (Task excTask : excTasks) {
					if(excTask.getTaskID().toString().equals(taskid)){
						i++;
						break;
					}
				}
				if(i==0){
					taskIdList.add(taskid);
				}
			}
			Task topTask= taskService.findTaskByTaskId(taskId);
			topTask.setFlag("0");
			topTasks.add(topTask);
			
			treeable =ProceesTaskTreebble.creatTaskTreeAble(topTasks, taskIdList, tasks);
//		}
		
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (TreeRender) UIUtil.with(treeable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	//保存用户的权限除外表、用户权限表和用户与组关系表
	@Get("savePower/{comCode}/{userCode}/{groupIdStr}/{taskIdStr}")
	public Reply savePower(@Param("comCode")String comCode,@Param("userCode")String userCode,@Param("groupIdStr")String groupIdStr,@Param("taskIdStr")String taskIdStr,Invocation inv){
		staffingService.savePower(comCode, userCode, groupIdStr,taskIdStr);
		return Replys.with("success");
	}
	
	//将用户名和用户ID传到updatePower.jsp页面
	@Get("updatePower/{userName}/{userCode}")
	public String updatePower(@Param("userName")String userName,@Param("userCode")String userCode, Invocation inv){
		
		inv.addModel("userName", userName);
		inv.addModel("userCode", userCode);
		return "updatePower";
		
	}
	
	//查询用户已引入机构
	@Get("companise/{userCode}")
	public Reply companies(Invocation inv,@Param("userCode")String userCode) throws Exception{
		
		Treeable<NodeEntity> treeable =companyService.findCompanyByUserCode(userCode);
		
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (TreeRender) UIUtil.with(treeable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	//查询用户在当前引入机构的用户组，并返回页面
	@Get("groupList/{comCode}/{userCode}")
	public Reply group(@Param("comCode")String comCode,@Param("userCode")String userCode,Invocation inv){
		List<Group> groupList = groupService.findGroups( comCode);
		UserPower userPower=staffingService.findUserPowerByUserCode(userCode, comCode);
		
		if(!userPower.getUserGroups().isEmpty()){
			List<UserGroup> userGroups = userPower.getUserGroups();
			List<String> checkGroupIds = new ArrayList<String>();
			for(UserGroup userGroup : userGroups){
				checkGroupIds.add(userGroup.getGroup().getGroupID());
			}

			for(Group group : groupList){
				if(checkGroupIds.contains(group.getGroupID().toString())){
					group.setFlag("1");
				}else{
					group.setFlag("0");
				}
			}
		}
		return Replys.with(groupList).as(Json.class);
	}
	
	//查询当前机构，当前用户组的根权限，并标记权限是否了赋给用户
	@Get("taskShow/{comCode}/{roleIdStr}/{userCode}")
	public Reply taskShow(@Param("comCode")String comCode, @Param("roleIdStr")String roleIdStr, @Param("userCode")String userCode,Invocation  inv){
		String[] roleIds = roleIdStr.split(",");
		List<String> roleIDs = new ArrayList<String>();
		for(String roleId : roleIds){
			roleIDs.add(roleId);
		}
		
		List<Task> taskList = taskService.findTaskByRoleIds(roleIDs, comCode,userCode);
		
		return Replys.with(taskList).as(Json.class);
	}
	
	//将用户名和用户ID传到dataSet.jsp页面
	@Get("userinfo/{name}/{usreCode}")
	public String chuandi(@Param("name")String name,@Param("usreCode")String usreCode,Invocation inv){
		inv.addModel("name", name);
		inv.addModel("userCode", usreCode);
		return "dataSet";
	}
	
	//查询出没有赋参数的数据规则
	@Post("rules/{comCode}/{userCode}")
	public Reply rules(@Param("comCode")String comCode,@Param("userCode")String userCode,Invocation inv) throws Exception {
		List<DataRule> delList = new ArrayList<DataRule>();
		List<DataRule> alldataRules = staffingService.findAllDataRule();
		List<DataRule> userdataRules =staffingService.getUserDataRule(comCode, userCode);
		for (DataRule alldataRule : alldataRules) {
			for (DataRule userdataRule : userdataRules) {
				if(alldataRule.getDataRuleID().toString().equals(userdataRule.getDataRuleID().toString())){
					delList.add(alldataRule);
					break;
				}
			}
		}
		alldataRules.removeAll(delList);
		return Replys.with(alldataRules).as(Json.class);
	}
	
	//查询出有参数的数据规则
	@Get("ruleParam/{comCode}/{userCode}")
	public Reply ruleParam(@Param("comCode")String comCode,@Param("userCode")String userCode,Invocation inv) throws Exception {
		
		List<DataRule> delList = new ArrayList<DataRule>();
		List<DataRule> alldataRules = staffingService.findAllDataRule();
		List<DataRule> userdataRules =staffingService.getUserDataRule(comCode, userCode);
		for (DataRule alldataRule : alldataRules) {
			for (DataRule userdataRule : userdataRules) {
				if(alldataRule.getDataRuleID().toString().equals(userdataRule.getDataRuleID().toString())){
					delList.add(alldataRule);
					break;
				}
			}
		}
		return Replys.with(delList).as(Json.class);
	}
	
	//查询数据规则的参数
	@Get("params/{comCode}/{userCode}/{dataRuleIdStr}")
	public Reply params(@Param("comCode")String comCode,@Param("userCode")String userCode,@Param("dataRuleIdStr")String dataRuleIdStr,Invocation inv) throws Exception {
		
		List<BusPower> busPowers = staffingService.getParams(comCode, userCode,dataRuleIdStr);
		
		return Replys.with(busPowers).as(Json.class);
	}

	//保存数据设置
	@Post("saveBusPower/{comCode}/{userCode}/{tdata}")
	public Reply save(@Param("comCode")String comCode,@Param("userCode")String userCode,@Param("tdata")String tdata,Invocation inv){
		System.out.println(tdata);
//		String result = staffingService.saveBusPower(comCode, userCode, paramStr);
		
		return Replys.with("success");
	}

}
