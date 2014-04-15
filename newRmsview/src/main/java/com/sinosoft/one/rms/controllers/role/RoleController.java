package com.sinosoft.one.rms.controllers.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.model.Company;
import com.sinosoft.one.rms.model.Employe;
import com.sinosoft.one.rms.model.Role;
import com.sinosoft.one.rms.model.RoleDesignateInfo;
import com.sinosoft.one.rms.model.Task;
import com.sinosoft.one.rms.service.CompanyService;
import com.sinosoft.one.rms.service.RoleService;
import com.sinosoft.one.uiutil.GridRender;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.NodeEntity;
import com.sinosoft.one.uiutil.Render;
import com.sinosoft.one.uiutil.TreeRender;
import com.sinosoft.one.uiutil.Treeable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;

@Path
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CompanyService companyService;
	// 角色列表
	@Post({ "rolelist/{name}", "rolelist" })
	public Reply list(@Param("name") String name, @Param("pageNo") int pageNo,
			@Param("rowNum") int rowNum, Invocation inv) throws Exception {
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getLoginComCode();
		Pageable pageable = new PageRequest(pageNo - 1, rowNum);
		Gridable<Role> ga = new Gridable<Role>(null);
		Page<Role> page = null;
		//查询机构下所有可见的角色
		List<String> roleAttribute = new ArrayList<String>();
		page = roleService.findRole(comCode,name,pageable);
		String button = "<a href='#' class='set' onclick='openUpdateWindow(this);'>修 改</a><a href='#' class='set' onclick='delRow(this);'>删 除</a>";
		List<Role> geRmsRoles = page.getContent();
		for (Role geRmsRole : geRmsRoles) {
			geRmsRole.setFlag(button);
		} 
		ga.setPage(page);
		ga.setIdField("roleID");
		roleAttribute.add("name");
		roleAttribute.add("des");
		roleAttribute.add("createTime");
		roleAttribute.add("operateTime");
		roleAttribute.add("flag");
		ga.setCellListStringField(roleAttribute);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (GridRender) UIUtil.with(ga).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}

	// 根据角色查询角色下的功能树 【树的长度为根据机构查询的功能】【选中的节点为角色下已经关联的功能节点】
	@Post("findTaskByRole/{roleId}")
	public Reply findTaskByRole(@Param("roleId") String roleId, Invocation inv)
			throws Exception {
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getLoginComCode();
		// 根据角色ID查询角色关联功能
		List<Task> rolesTasks = roleService.findTaskByRole(roleId);
		// 根据机构代码查询可见功能
		List<Task> comsTasks = roleService.findTaskByComCode(comCode);
		// 构建树对象
		roleService.deleteRole(roleId, comCode);
		Map<String, Task> filter = new HashMap<String, Task>();
		List<Task> topList = new ArrayList<Task>();
		for (Task task : comsTasks) {
			filter.put(task.getTaskID(), task);
			if (task.getParent() == null) {
				topList.add(task);
			}
		}
		Treeable<NodeEntity> treeable = creatTaskTreeAble(topList,filter,rolesTasks);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		TreeRender render = (TreeRender) UIUtil.with(treeable).as(UIType.Json);
		System.out.println(render.getResultForTest());
		render.render(inv.getResponse());
		return null;
	}

	// 增加角色页面的查询功能树 
	@Post("findTask")
	public Reply findTask(Invocation inv)
			throws Exception {
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getLoginComCode();
		// 根据角色ID查询角色关联功能
		List<Task> rolesTasks =new ArrayList<Task>();
		// 根据机构代码查询可见功能
		List<Task> comsTasks = roleService.findTaskByComCode(comCode);
		// 构建树对象
		
		Map<String, Task> filter = new HashMap<String, Task>();
		List<Task> topList = new ArrayList<Task>();
		for (Task task : comsTasks) {
			filter.put(task.getTaskID(), task);
			if (task.getParent() == null) {
				topList.add(task);
			}
		}
		Treeable<NodeEntity> treeable = creatTaskTreeAble(topList,filter,rolesTasks);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		TreeRender render = (TreeRender) UIUtil.with(treeable).as(UIType.Json);
		System.out.println(render.getResultForTest());
		render.render(inv.getResponse());
		return null;
	}
	
	
	//查询角色页面 修改/查看页面
	@Get("findRoleById/{roleId}")
	public String findRoleById(@Param("roleId") String roleId, Invocation inv) {
		Role role = roleService.findRoleById(roleId);
		inv.addModel("name", role.getName());
		inv.addModel("des", role.getDes());
		inv.addModel("roleId", role.getRoleID());
		inv.addModel("flag", role.getFlag());
		return "loadRoleInfo";
	}
	
	//准备增加角色 打开增加页面
	@Get("prepareAddRole")
	public String prepareAddRole(){
		return "addRole";
	}
	
	
	@Post("add/{name}/{des}/{roleType}/{taskId}")
	public Reply addRole(@Param("name")String name,@Param("des") String des,@Param("roleType") String roleType,@Param("taskId") String taskid, Invocation inv){
		Employe user = (Employe) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getCompany().getComCode();
		taskid=taskid.substring(0, taskid.length()-1);
		roleService.addRole(comCode, user.getUserCode(), name, des, roleType, Arrays.asList(taskid.split(",")));
		return null;
	}
	
	@Post("update/{roleId}/{name}/{des}/{roleType}/{taskId}")
	public Reply updateRole(@Param("roleId") String roleId,@Param("name")String name,@Param("des") String des,@Param("roleType") String roleType,@Param("taskId") String taskid, Invocation inv){
		Employe user = (Employe) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getCompany().getComCode();
		taskid=taskid.substring(0, taskid.length()-1);
		roleService.updateRole(roleId, comCode,user.getUserCode(), name, des, roleType, Arrays.asList(taskid.split(",")));
		return null;
	}

	@Post("findDesigNateComTree")
	public Reply findComTree(Invocation inv) throws Exception{
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String supercomCode=user.getLoginComCode();
		List<Company> showCompany=companyService.findAllNextComBySupper(supercomCode);
		Map<String, Company> filter = new HashMap<String, Company>();
		List<Company> topList = new ArrayList<Company>();
		for (Company company : showCompany) {
			if(company.getUpperComCode().toString().equals(supercomCode))
				topList.add(company);
			filter.put(company.getComCode(), company);
		}
		Treeable<NodeEntity> treeable=companyService.creatCompanyTreeAble(topList, filter);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (TreeRender) UIUtil.with(treeable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	@Post("findDesignateRole/{comCode}")
	public Reply findDesignateRole(@Param("comCode") String comCode, @Param("pageNo") int pageNo,
			@Param("rowNum") int rowNum,Invocation inv) throws Exception{
//		User user=ShiroLoginUser.getLoginUser();
		Company company=companyService.findCompanyByComCode(comCode);
		Pageable pageable = new PageRequest(pageNo - 1, rowNum);
		Gridable<Role> ga = new Gridable<Role>(null);
		//查询机构下所有可见的角色
		List<String> roleAttribute = new ArrayList<String>();
		Page<RoleDesignateInfo> superComRolePage = roleService.findRoleDesignate(company.getUpperComCode(), comCode, pageable);
//		Page<Role> subComRolePage = roleService.findRole(comCode, null, pageable);
//		List<Role> supersRoles = superComRolePage.getContent();
//		List<Role> subsRoles = subComRolePage.getContent();
//		for (Role supersRole : supersRoles) {
//			for (Role subsRole : subsRoles) {
//				if(supersRole.getRoleID().toString().equals(subsRole.getRoleID().toString())){
//					supersRole.setChecked("true");
//					supersRole.setFlag(subsRole.getr)
//					break;
//				}
//			}
//		}
		ga.setPage(superComRolePage);
		ga.setIdField("roleId");
		roleAttribute.add("roleName");
		roleAttribute.add("operateTime");
		roleAttribute.add("operateUser");
		roleAttribute.add("type");
		ga.setCellListStringField(roleAttribute);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (GridRender) UIUtil.with(ga).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	
	//-----------------------------------------------------------//
	/**
	 * 构建功能树 topTasks父节点 filter所有节点
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  Treeable<NodeEntity> creatTaskTreeAble(List<Task> topTasks,Map<String,Task> filter,List<Task>checkedTask){
		List<NodeEntity> nodeEntitys=new ArrayList<NodeEntity>();
		nodeEntitys=creatSubNode(topTasks, filter,checkedTask);
		Treeable<NodeEntity> treeable =new Treeable.Builder(nodeEntitys,"id", "title", "children", "state").classField("classField").urlField("urlField").builder();
		return treeable;
	}
	
	List<NodeEntity> creatSubNode(List<Task> topTasks,Map<String,Task> filter,List<Task>checkedTask){
		ArrayList<NodeEntity> nodeEntitys=new ArrayList<NodeEntity>();
		for (Task geRmsTask : topTasks) {
			if(!filter.containsKey(geRmsTask.getTaskID()))
                continue;
				NodeEntity nodeEntity = new NodeEntity();
				nodeEntity.setId(geRmsTask.getTaskID());
				nodeEntity.setTitle(geRmsTask.getName());
				if(checkedTask != null)
					for (Task checkTask : checkedTask) {
						if(geRmsTask.getTaskID().toString().equals(checkTask.getTaskID().toString())){
							nodeEntity.setClassField("jstree-checked");
						}
					}
				if(!geRmsTask.getChildren().isEmpty()){
					nodeEntity.setChildren(creatSubNode(geRmsTask.getChildren(),filter,checkedTask));
				}
				nodeEntitys.add(nodeEntity);
			}
		return nodeEntitys;
	}
}
