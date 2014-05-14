package com.sinosoft.one.rms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.sinosoft.one.rms.model.Employe;
import com.sinosoft.one.rms.model.Group;
import com.sinosoft.one.rms.model.GroupRole;
import com.sinosoft.one.rms.model.Role;
import com.sinosoft.one.rms.model.UserGroup;
import com.sinosoft.one.rms.model.UserPower;
import com.sinosoft.one.rms.repositories.GeRmsGroupRepository;
import com.sinosoft.one.rms.repositories.GeRmsGroupRoleRepositoriy;
import com.sinosoft.one.rms.repositories.GeRmsRoleRepository;
import com.sinosoft.one.rms.repositories.GeRmsUserGroupRepository;
import com.sinosoft.one.rms.repositories.GeRmsUserPowerRepository;
import com.sinosoft.one.rms.repositories.UserDao;
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
	@Resource(name="userDao")
	private UserDao userDao;
	@Resource(name="geRmsUserPowerRepository")
	private GeRmsUserPowerRepository geRmsUserPowerRepository;
	
	//根据机构ID查询本机构的用户组
	public List<Group> findGroups(String comCode) {
		List<Group> groupList = geRmsGroupRepository.findGroups();
		return groupList;
	}
	
/*	public Gridable<Group> getGroupGridable(Gridable<Group> gridable,
			String comCode, String name, Pageable pageable) {
		//查询机构下所有可见的角色
		Page<Group> page = null;
		page = findGroup(comCode,name,pageable);
		String button = "<a  class='set' onclick='openUpdateWindow(this);'>修 改</a><a class='set' onclick='delRow(this);'>删 除</a><a class='set' onclick='groupMember(this);'>删 除</a>";
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
	}*/
	
	public Page<Group> findGroup(String comCode,String name,Pageable pageable){
		Page<Group> page =null;
		if(name!=null&&!"".equals(name))
			page = geRmsGroupRepository.findGroupByName("%"+name+"%", pageable);
		else
			page = geRmsGroupRepository.findGroup( pageable);
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

	public boolean updateGroup(String groupId, String name, String groupType,
			List<String> roleIds, String des, String comCode, String userCode) {
		Group group=geRmsGroupRepository.findOne(groupId);
		if(!comCode.equals(group.getComCode())){
			return false;
		}
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
		return true;
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

//	public List<String> findUserGroupIdByUserCode(String userCode){
//		return  geRmsUserGroupRepository.findUserGroupIdByUserCode(userCode);
//	}
//	
	public List<UserGroup>  findAllUserGroup(List<String> userGroupIds){
		return (List<UserGroup>) geRmsUserGroupRepository.findAll(userGroupIds);
	}


	public List<Group> findGroupById(List<String> groupIds) {
		System.out.println(groupIds);
		List<Group> groups = new ArrayList<Group>();
		if(!groupIds.isEmpty()){
			
			groups = (List<Group>) geRmsGroupRepository.findAll(groupIds);
		}
		return groups;
	}
	
	//根据用户Id和用户组Id修改UserGroup表中数据的isvalidate值
	public boolean deleteGroupByid(String groupId,String LoginComCode) {
		Group group=geRmsGroupRepository.findOne(groupId);
		if(group.getComCode().equals(LoginComCode)&&!group.getCreateUser().equals("sys")){
			geRmsGroupRepository.delete(groupId);
			return true;
		}
		return false;
	}
	
	
	public Page<Employe> findGroupMember(String userCode,String groupid,Pageable pageable){
		if(userCode==null||userCode.equals("")){
			userCode="%%";
		}else{
			userCode="%"+userCode+"%";
		}
		Page<Employe> page =userDao.findGroupMember(groupid, userCode, pageable);
	 
		return page;
	}
	
	public Page<Employe> finduser(String userCode,String groupid,Pageable pageable){
		if(userCode==null||userCode.equals("")){
			userCode="%%";
		}else{
			userCode="%"+userCode+"%";
		}
		Page<Employe> page =userDao.finduser(groupid, userCode, pageable);
	 
		return page;
	}
	
	public boolean deleteGourpMember(String groupid,String deleteuserCode,String loginComCode){
		Group group=geRmsGroupRepository.findOne(groupid);
		if(group==null){
			return false;
		}
		//直接删除组下该用户
		geRmsUserGroupRepository.deleteGroupMember(groupid, deleteuserCode, loginComCode);
		//如果组成员关系表中没有记录了 那么需要把权限表也删除 说明他在当前机构已经没有权限了
		List<String >groupuserids=geRmsUserGroupRepository.findUserGroupIdByUserCode(deleteuserCode);
		if(groupuserids.size()==0){
			geRmsUserPowerRepository.deleteUserPower(deleteuserCode, loginComCode);
		}
		return true;
	}

	public void addGroupMember(String groupid,List<String> userids){
		List <UserGroup>userGroups=new ArrayList<UserGroup>();
		Group group=geRmsGroupRepository.findOne(groupid);
		for (String userid : userids) {
			UserGroup userGroup =new UserGroup();
			userGroup.setGroup(group);
			userGroup.setUserCode(userid);
			userGroup.setIsValidate("1");
			UserPower userPower =new UserPower();
			userPower.setComCode(group.getComCode());
			userPower.setIsValidate("1");
			userPower.setUserCode(userid);
			userGroup.setUserPower(	geRmsUserPowerRepository.save(userPower));
			userGroups.add(userGroup);
		}
		geRmsUserGroupRepository.save(userGroups);
	}

}
