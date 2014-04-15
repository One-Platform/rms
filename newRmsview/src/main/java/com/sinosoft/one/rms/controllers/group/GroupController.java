package com.sinosoft.one.rms.controllers.group;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.sinosoft.one.rms.model.Group;
import com.sinosoft.one.rms.model.Role;
import com.sinosoft.one.rms.service.GroupService;
import com.sinosoft.one.rms.service.RoleService;
import com.sinosoft.one.uiutil.GridRender;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.Render;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;

@Path
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private RoleService roleService;
	
	@Post({ "grouplist/{name}", "grouplist" })
	public Reply list(@Param("name") String name, @Param("pageNo") int pageNo,
			@Param("rowNum") int rowNum, Invocation inv) throws Exception {
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getLoginComCode();
		Pageable pageable = new PageRequest(pageNo - 1, rowNum);

		Gridable<Group> ga = new Gridable<Group>(null);
		Gridable<Group> gridable = groupService.getGroupGridable(ga,comCode, name,
				pageable);

		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (GridRender) UIUtil.with(gridable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	//查询用户组页面 修改/查看页面
	@Get("findGroupById/{groupId}")
	public String findGroupById(@Param("groupId") String groupId, Invocation inv) {
		Group group = groupService.findGroupById(groupId);
		inv.addModel("name", group.getName());
		inv.addModel("des", group.getDes());
		inv.addModel("groupId", group.getGroupID());
		if(group.getComCode().toString().equals("*")){
			inv.addModel("flag", "all");
		}else {
			inv.addModel("flag", "default");
		}
		
		return "loadGroupInfo";
	}
	
	//查询用户组页面 修改/查看页面
	@Post("findRoleByGroupId/{groupId}")
	public Reply findGroupByGroupId(@Param("groupId") String groupId,@Param("pageNo") int pageNo,
			@Param("rowNum") int rowNum, Invocation inv) throws Exception {
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getLoginComCode();
		Pageable pageable = new PageRequest(pageNo - 1, rowNum);
		Gridable<Role> ga = new Gridable<Role>(null);
		Gridable<Role> gridable = groupService.getRoleGridableByGroupId(ga, groupId,comCode, pageable);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (GridRender) UIUtil.with(gridable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	@Get("prepareAddGroup")
	public String prepareAddGroup(){
		return "addGroup";
	}
	
	@Get("findRole")
	public Reply findRole( @Param("pageNo") int pageNo,@Param("rowNum") int rowNum,Invocation inv)throws Exception{
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getLoginComCode();
		Pageable pageable = new PageRequest(pageNo - 1, rowNum);
		Gridable<Role> ga = new Gridable<Role>(null);
		Page<Role> page = null;
		List<String> roleAttribute = new ArrayList<String>();
		page = roleService.findRole(comCode,null,pageable);
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
	
	@Post({"update/{groupId}/{name}/{groupType}/{roleId}/{des}","update/{groupId}/{name}/{groupType}/{roleId}"}) 
	public Reply updataRole(@Param("groupId") String groupid,@Param("name")String name,@Param("des") String des,@Param("groupType") String groupType,@Param("roleId") String roleId, Invocation inv){
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getLoginComCode();
		groupService.updateGroup(groupid, name, groupType, Arrays.asList(roleId.substring(0,roleId.length()-1).split(",")), des, comCode, user.getUserCode());
		return null;
	}
	
	@Post({"add/{name}/{groupType}/{roleId}/{des}","add/{name}/{groupType}/{roleId}"}) 
	public Reply addRole(@Param("name")String name,@Param("des") String des,@Param("groupType") String groupType,@Param("roleId") String roleId, Invocation inv){
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getLoginComCode();
		groupService.addGroup(name, groupType, Arrays.asList(roleId.substring(0,roleId.length()-1).split(",")), des, comCode, user.getUserCode());
		return null;
	}
}
