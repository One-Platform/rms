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
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.client.ShiroLoginUser;
import com.sinosoft.one.rms.model.Employe;
import com.sinosoft.one.rms.model.Group;
import com.sinosoft.one.rms.model.Role;
import com.sinosoft.one.rms.repositories.UserDao;
import com.sinosoft.one.rms.service.GroupService;
import com.sinosoft.one.rms.service.RoleService;
import com.sinosoft.one.uiutil.GridRender;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.Render;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import com.sinosoft.one.uiutil.exception.ConverterException;

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

		Gridable<Group> gridable = new Gridable<Group>(null);
		Page<Group> page = null;
		page = groupService.findGroup(comCode,name,pageable);
		
		List<Group> groups = page.getContent();
		for (Group group : groups) {
			if(!group.getComCode().equals(comCode)){
				group.setFlag("<a  class='set' onclick='openUpdateWindow(this);'>修 改</a><a class='set dis' >删 除</a><a class='set' onclick='gourpMember(this);'>管理组成员</a>");
			}else{
				group.setFlag("<a  class='set' onclick='openUpdateWindow(this);'>修 改</a><a class='set' onclick='delRow(this);'>删 除</a><a class='set' onclick='gourpMember(this);'>管理组成员</a>");
			}
		} 
		gridable.setPage(page);
		gridable.setIdField("groupID");
		List<String> roleAttribute = new ArrayList<String>();
		roleAttribute.add("name");
		roleAttribute.add("des");
		roleAttribute.add("createTime");
		roleAttribute.add("operateTime");
		roleAttribute.add("flag");
		gridable.setCellListStringField(roleAttribute);
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
	public Reply updataGroup(@Param("groupId") String groupid,@Param("name")String name,@Param("des") String des,@Param("groupType") String groupType,@Param("roleId") String roleId, Invocation inv){
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getLoginComCode();
		boolean result =groupService.updateGroup(groupid, name, groupType, Arrays.asList(roleId.substring(0,roleId.length()-1).split(",")), des, comCode, user.getUserCode());
		return Replys.with(result);
	}
	
	@Post({"add/{name}/{groupType}/{roleId}/{des}","add/{name}/{groupType}/{roleId}"}) 
	public Reply addGroup(@Param("name")String name,@Param("des") String des,@Param("groupType") String groupType,@Param("roleId") String roleId, Invocation inv){
		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String comCode = user.getLoginComCode();
		groupService.addGroup(name, groupType, Arrays.asList(roleId.substring(0,roleId.length()-1).split(",")), des, comCode, user.getUserCode());
		return null;
	}
	
	@Post("delete/{groupid}")
	public Reply deleteGroup(@Param("groupid") String groupid){
		User user=ShiroLoginUser.getLoginUser();
		boolean isdelete=groupService.deleteGroupByid(groupid,user.getLoginComCode());
		return Replys.with(isdelete);
	}
	
	@Get({"gourpMember/{groupId}","gourpMember"})
	public String gourpmember(@Param("groupId") String groupId,Invocation inv){
		if(groupId!=null){
			inv.addModel("groupId",groupId);
		}
		return "gourpMember";
	}
	@Get({"findGourpMember/{groupid}/{userCode}","findGourpMember/{groupid}"})
	public Reply findgourpmember(@Param("groupid") String groupid,@Param("userCode") String userCode,@Param("pageNo") int pageNo,
			@Param("rowNum") int rowNum, Invocation inv)  throws Exception{
		Pageable pageable = new PageRequest(pageNo - 1, rowNum);
		Gridable<Employe> gridable = new Gridable<Employe>(null);
		Page<Employe> page =groupService.findGroupMember(userCode, groupid, pageable);
		String button = "<a class='set' onclick='delRow(this);'>删 除</a>";
		List<Employe> employes = page.getContent();
		for (Employe employe : employes) {
			employe.setFlag(button);
			employe.setArticleCode(employe.getCompany().getComCode());
		}
		gridable.setPage(page);
		gridable.setIdField("userCode");
		List<String> roleAttribute = new ArrayList<String>();
		roleAttribute.add("userCode");
		roleAttribute.add("userName");
		roleAttribute.add("articleCode");
		roleAttribute.add("flag");
		gridable.setCellListStringField(roleAttribute);
		inv.addModel("groupId",groupid);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (GridRender) UIUtil.with(gridable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	@Post("deleteGourpMember/{groupid}/{userid}")
	public Reply deleteGourpMember(@Param("groupid") String groupid,@Param("userid") String userid){
		User user=ShiroLoginUser.getLoginUser();
		boolean isdelete=groupService.deleteGourpMember(groupid ,userid, user.getLoginComCode());
		return Replys.with(isdelete);
	}
	

	@Get({"dpAddGourpMember/{groupId}"})
	public String dpAddGourpMember(@Param("groupId") String groupId,Invocation inv){
		if(groupId!=null){
			inv.addModel("groupId",groupId);
		}
		return "addgourpMember";
	}
	
	
	@Get({"finduser/{groupId}/{userCode}","finduser/{groupId}"})
	public Reply finduser(@Param("groupId") String groupId,@Param("userCode") String userCode,@Param("pageNo") int pageNo,
			@Param("rowNum") int rowNum, Invocation inv)  throws Exception{
		Pageable pageable = new PageRequest(pageNo - 1, rowNum);
		Gridable<Employe> gridable = new Gridable<Employe>(null);
		Page<Employe> page =groupService.finduser(userCode, groupId, pageable);
		List<Employe> employes = page.getContent();
		for (Employe employe : employes) {
			employe.setFlag(employe.getCompany().getComCode());
		}
		gridable.setPage(page);
		gridable.setIdField("userCode");
		List<String> roleAttribute = new ArrayList<String>();
		roleAttribute.add("userCode");
		roleAttribute.add("userName");
		roleAttribute.add("flag");
		gridable.setCellListStringField(roleAttribute);
		inv.addModel("groupId",groupId);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (GridRender) UIUtil.with(gridable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}

	@Post({"addGroupMember/{groupId}/{useIds}","addGroupMember/{groupId}"})
	public Reply addGroupMember(@Param("groupId") String groupId,@Param("useIds") String useIds,Invocation inv){
		List<String>useridlist=new ArrayList<String>();
		if(useIds!=null){
			useridlist= Arrays.asList(useIds.substring(0, useIds.length()-1).split(","));
		}
		
		groupService.addGroupMember(groupId, useridlist);
		return null;
	}
}
