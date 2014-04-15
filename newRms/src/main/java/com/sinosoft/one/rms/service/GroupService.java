package com.sinosoft.one.rms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.sinosoft.one.rms.model.Group;
import com.sinosoft.one.rms.model.GroupRole;
import com.sinosoft.one.rms.model.Role;
import com.sinosoft.one.rms.model.UserGroup;
import com.sinosoft.one.rms.repositories.GeRmsGroupRepository;
import com.sinosoft.one.rms.repositories.GeRmsGroupRoleRepositoriy;
import com.sinosoft.one.rms.repositories.GeRmsRoleRepository;
import com.sinosoft.one.rms.repositories.GeRmsUserGroupRepository;
import com.sinosoft.one.uiutil.Gridable;
@Component
public class GroupService {
	
	@Resource(name="geRmsGroupRepository")
	private GeRmsGroupRepository geRmsGroupRepository;
	@Resource(name="geRmsRoleRepository")
	private GeRmsRoleRepository geRmsRoleRepository;
	@Resource(name="geRmsGroupRoleRepositoriy")
	private GeRmsGroupRoleRepositoriy geRmsGroupRoleRepositoriy;
	@Resource(name="geRmsUserGroupRepository")
	private GeRmsUserGroupRepository geRmsUserGroupRepository;
	
	
	
	//根据机构ID查询本机构的用户组
	public List<Group> findGroupByComCode(String comCode) {
		List<Group> groupList = geRmsGroupRepository.findGroupByComCode(comCode);
		return groupList;
	}
	
	public Gridable<Group> getGroupGridable(Gridable<Group> gridable,
			String comCode, String name, Pageable pageable) {
		//查询机构下所有可见的角色
		Page<Group> page = null;
		page = findGroup(comCode,name,pageable);
		String button = "<a href='#' class='set' onclick='openUpdateWindow(this);'>修 改</a><a href='#' class='set' onclick='delRow(this);'>删 除</a>";
		List<Group> groups = page.getContent();
		for (Group group : groups) {
			group.setFlag(button);
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
		return gridable;
	}
	
	Page<Group> findGroup(String comCode,String name,Pageable pageable){
		Page<Group> page =null;
		if(name!=null&&!"".equals(name))
			page = geRmsGroupRepository.findGroupByName(comCode, "%"+name+"%", pageable);
		else
			page = geRmsGroupRepository.findGroup(comCode, pageable);
		return page;
	}

	public Group findGroupById(String groupId) {
		return geRmsGroupRepository.findOne(groupId);	
	}

	public Gridable<Role> getRoleGridableByGroupId(Gridable<Role> gridable,
			String groupId,String comCode, Pageable pageable) {
		Page<Role> page = geRmsRoleRepository.findRole(comCode, pageable);
	
		Group group =geRmsGroupRepository.findOne(groupId);
		List<String> roleids=new ArrayList<String>();
		for (GroupRole groupRole : 	group.getGroupRoles()) {
			roleids.add(groupRole.getRole().getRoleID());
		}
		for (Role role: page.getContent()) {
			for (String string : roleids) {
			
				if(role.getRoleID().toString().equals(string)){
					role.setChecked("true");
					break;
				} 
			}
		}
		gridable.setPage(page);
		gridable.setIdField("roleID");
		List<String> roleAttribute = new ArrayList<String>();
		roleAttribute.add("name");
		roleAttribute.add("des");
		roleAttribute.add("createTime");
		roleAttribute.add("operateTime");
		roleAttribute.add("checked");
		gridable.setCellListStringField(roleAttribute);
		return gridable;
	}

	public void updateGroup(String groupId, String name, String groupType,
			List<String> roleIds, String des, String comCode, String userCode) {
		Group group=geRmsGroupRepository.findOne(groupId);
		if (group!=null) {
			geRmsGroupRoleRepositoriy.delete(group.getGroupRoles());
			group.setName(name);
			group.setOperateUser(userCode);
			if (des!=null) {
				group.setDes(des);
			}
			if(groupType.equals("all")){
				group.setComCode("*");
			}else {
				group.setComCode(comCode);
			}
			List<Role>roles=(List<Role>) geRmsRoleRepository.findAll(roleIds);
			List<GroupRole>groupRoles=new ArrayList<GroupRole>();
			for (Role role : roles) {
				GroupRole groupRole=new GroupRole();
				groupRole.setGroup(group);
				groupRole.setRole(role);
				groupRole.setIsValidate("1");
				groupRoles.add(groupRole);
			}
			group.setGroupRoles(groupRoles);
			geRmsGroupRepository.save(group);
		}
		
	}

	public void addGroup(String name, String groupType, List<String> roleIds,
			String des, String comCode, String userCode) {
		Group group=new Group();
		group.setName(name);
		if (des!=null) {
			group.setDes(des);
		}
		group.setCreateUser(userCode);
		group.setOperateTime(new Date());
		group.setIsValidate("1");
		if(groupType.equals("all")){
			group.setComCode("*");
		}else {
			group.setComCode(comCode);
		}
		List<GroupRole> groupRoles=new ArrayList<GroupRole>();
		for (String string : roleIds) {
			GroupRole groupRole=new GroupRole();
			groupRole.setGroup(group);
			groupRole.setIsValidate("1");
			groupRole.setRole(geRmsRoleRepository.findOne(string));
			groupRoles.add(groupRole);
		}
		group.setGroupRoles(groupRoles);
		geRmsGroupRepository.save(group);
	}

	//根据机构Id，查询机构的用户组,并对已引入用户的组进行标记
	public List<Group> findGroupByComCode(String comCode, String userCode) {
		
		List<Group> groups = geRmsGroupRepository.findGroupByComCode(comCode);
		List<String> userGroupIds = geRmsUserGroupRepository.findUserGroupIdByUserCode(userCode);
		if(!userGroupIds.isEmpty()){
			List<UserGroup> userGroups = (List<UserGroup>) geRmsUserGroupRepository.findAll(userGroupIds);
			List<String> checkGroupIds = new ArrayList<String>();
			for(UserGroup userGroup : userGroups){
				checkGroupIds.add(userGroup.getGroup().getGroupID());
			}

			for(Group group : groups){
				if(checkGroupIds.contains(group.getGroupID().toString())){
					group.setFlag("1");
				}else{
					group.setFlag("0");
				}
			}
		}
		return groups;
	}

	public List<Group> findGroupById(List<String> groupIds) {
		System.out.println(groupIds);
		List<Group> groups = new ArrayList<Group>();
		if(!groupIds.isEmpty()){
			
			groups = (List<Group>) geRmsGroupRepository.findAll(groupIds);
		}
		return groups;
	}
	
	
	
	
//	//将数据库中的用户组记录保存在Gridable中，并返回
//	public Gridable<GeRmsGroup> getGridable(Gridable<GeRmsGroup> gridable, Pageable pageable,List<String> groupAttribute) {
//		Page<GeRmsGroup> page = geRmsGroupRepository.findAll(pageable);
//		List<GeRmsGroup> geRmsGroups = page.getContent();
//		String button = "<a href='#' class='set' onclick='openWindow(this);'>修 改</a><a href='#' class='set' onclick='delRow(this);'>删 除</a>";
//		for (GeRmsGroup group : geRmsGroups) {
//			if (group.getCreateTime() != null)
//				group.setCreateTimeStr(group.getCreateTime().toString());
//			if (group.getOperateTime() != null)
//				group.setOperateTimeStr(group.getOperateTime().toString());
//			group.setButton(button);
//		}
//		gridable.setPage(page);
//		
//		gridable.setIdField("groupID");
//		groupAttribute.add("name");
//		groupAttribute.add("des");
//		groupAttribute.add("createTimeStr");
//		groupAttribute.add("operateTimeStr");
//		groupAttribute.add("button");
//		gridable.setCellListStringField(groupAttribute);
//		
//		return gridable;
//	}
//	
//	//根据组名，将数据库中的用户组记录保存在Gridable中，并返回
//	public Gridable<GeRmsGroup> searchGroup(Gridable<GeRmsGroup> gridable,
//			String name, Pageable pageable, List<String> groupAttribute) {
//		
//		Page<GeRmsGroup> page = null;
//		if (!name.equals("null")) {
//			page = geRmsGroupRepository.findGroupByName("%"+name+"%", pageable);
//		} else {
//			page = geRmsGroupRepository.findAll(pageable);
//		}
//		String button = "<a href='#' class='set' onclick='openWindow(this);'>修 改</a><a href='#' class='set' onclick='delRow(this);'>删 除</a>";
//		List<GeRmsGroup> groupList = page.getContent();
//		for (GeRmsGroup group : groupList) {
//			if (group.getCreateTime() != null)
//				group.setCreateTimeStr(group.getCreateTime().toString());
//			if (group.getOperateTime() != null)
//				group.setOperateTimeStr(group.getOperateTime().toString());
//			group.setButton(button);
//		}
//		gridable.setPage(page);
//		gridable.setIdField("groupID");
//		groupAttribute.add("name");
//		groupAttribute.add("des");
//		groupAttribute.add("createTimeStr");
//		groupAttribute.add("operateTimeStr");
//		groupAttribute.add("button");
//		gridable.setCellListStringField(groupAttribute);
//		return gridable;
//	}
//	
//	//保存用户组和用户组的角色
//	public void saveGroupAndGroupRole(String name, String des,String roleIdStr,GeRmsGroup group,GeRmsGroupRole groupRole) {
//		User user = (User) inv.getRequest().getSession().getAttribute("user");
//
//		group.setName(name);
//		group.setGroupID(name);
//		group.setDes(des);
//		group.setCreateTime(new Date());
//		group.setComCode(user.getComCode());
//		group.setCreateUser(user.getUserName());
//		group.setIsValidate("1");
//		geRmsGroupRepository.save(group);
//
//		String[] roleId = roleIdStr.split(",");
//		for (int i = 0; i < roleId.length; i++) {
//			
//			groupRole.setGroupRoleID(group.getGroupID()
//					+ roleId[i].substring(3, 10));
//			groupRole.setGroupID(group.getGroupID());
//			groupRole.setRoleID(roleId[i]);
//			groupRole.setIsValidate("1");
//			geRmsGroupRoleRepositoriy.save(groupRole);
//		}
//		
//		
//	}
//	
//	//根据用户组Id修改Group和GroupRole表中数据的isvalidate值
//	public void del(String groupId) {
//		System.out.println("check----1");
//		geRmsGroupRepository.updateIsvalidateByGroupId(groupId);
//		System.out.println("check----2");
//		geRmsGroupRoleRepositoriy.updateIsvalidateByGroupId(groupId);
//		System.out.println("check----3");
//	}
//
//	//根据用户组ID，查出符合条件的用户，存入Gridable对象，并返回
//	public Gridable<User> getGridable(Gridable<User> gridable, String groupId,Pageable pageable,
//			List<String> userAttribute) {
//		
//		Page<User> page = geRmsUserGroupRepository.findUserByGroupId(groupId, pageable);
//		List<User> userList = page.getContent();
//		String button = "<a href='#' class='set' onclick='delRow(this);'>删 除</a>";
//		for (User user : userList) {
//			user.setButton(button);
//		}
//
//		gridable.setPage(page);
//		gridable.setIdField("userCode");
//		userAttribute.add("userCode");
//		userAttribute.add("userName");
//		userAttribute.add("button");
//		gridable.setCellListStringField(userAttribute);
//		
//		return gridable;
//	}
//	
//	//根据用户名和用户ID，查出符合条件的用户，存入Gridable对象，并返回
//	public Gridable<User> getGridable(Gridable<User> gridable, String userName,
//			String userCode,String groupId, Pageable pageable, List<String> userAttribute) {
//		
//		Page<User> page = null;
//		if (userCode.equals("null") && userName.equals("null")) {
//			page = geRmsUserGroupRepository.findUserByGroupId(groupId, pageable);
//		} else if (!userCode.equals("null") && userName.equals("null")) {
//			page = geRmsUserGroupRepository.findUserByUserCode("%"+userCode+"%",groupId, pageable);
//		} else if (userCode.equals("null") && !userName.equals("null")) {
//			page = userDao.searchUserByUserName("%"+userName+"%",groupId, pageable);
//		} else {
//			page = userDao.searchUserByNameCode("%"+userName+"%", "%"+userCode+"%",groupId, pageable);
//		}
//
//		List<User> userList = page.getContent();
//		String button = "<a href='#' class='set' onclick='delRow(this);'>删 除</a>";
//		for (User user : userList) {
//			user.setButton(button);
//		}
//		
//		gridable.setPage(page);
//		gridable.setIdField("userCode");
//		userAttribute.add("userCode");
//		userAttribute.add("userName");
//		userAttribute.add("button");
//		gridable.setCellListStringField(userAttribute);
//		
//		return gridable;
//	}
//	
//	//根据用户Id和用户组Id修改UserGroup表中数据的isvalidate值
//	public void del(String userCode, String groupId) {
//		
//		geRmsUserGroupRepository.updateIsvalidateByUserCodeGroupId(userCode, groupId);
//		
//	}

}
