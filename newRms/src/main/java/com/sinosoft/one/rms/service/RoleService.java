package com.sinosoft.one.rms.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.sinosoft.one.rms.model.Group;
import com.sinosoft.one.rms.model.GroupRole;
import com.sinosoft.one.rms.model.Role;
import com.sinosoft.one.rms.model.RoleDesignate;
import com.sinosoft.one.rms.model.RoleDesignateId;
import com.sinosoft.one.rms.model.RoleDesignateInfo;
import com.sinosoft.one.rms.model.RoleTask;
import com.sinosoft.one.rms.model.Task;
import com.sinosoft.one.rms.model.TaskAuth;
import com.sinosoft.one.rms.repositories.GeRmsGroupRepository;
import com.sinosoft.one.rms.repositories.GeRmsGroupRoleRepositoriy;
import com.sinosoft.one.rms.repositories.GeRmsRoleDesignateRepository;
import com.sinosoft.one.rms.repositories.GeRmsRoleRepository;
import com.sinosoft.one.rms.repositories.GeRmsTaskAuthRepository;
import com.sinosoft.one.rms.repositories.GeRmsTaskRepository;

@Component
public class RoleService {
	
	
	@Resource(name="geRmsRoleRepository")
	private GeRmsRoleRepository geRmsRoleRepository;
	
	@Resource(name="geRmsGroupRepository")
	private GeRmsGroupRepository geRmsGroupRepository; 
	
	@Resource
	private GeRmsGroupRoleRepositoriy geRmsGroupRoleRepository;
	@Resource(name="geRmsTaskRepository")
	private GeRmsTaskRepository geRmsTaskRepository;
	@Resource(name="geRmsTaskAuthRepository")
	private GeRmsTaskAuthRepository geRmsTaskAuthRepository;
	@Resource(name="geRmsRoleDesignateRepository")
	private GeRmsRoleDesignateRepository geRmsRoleDesignateRepository;
	
	
	
	//查询角色信息
	public Role findRoleById(String roleId){
		//查询角色对象
		Role role = geRmsRoleRepository.findOne(roleId);
		//查询角色类型  default/all
		List<String> rolesComCodes=geRmsRoleRepository.findRoleTypById(roleId);
		if(rolesComCodes.size()>0&&rolesComCodes!=null){
			role.setFlag("default");
			for (String comcode : rolesComCodes) {
				if(comcode.toString().equals("*"))
					role.setFlag("all");
					break;
			}
		}
		return role;
	}
	
	public Page<Role> findRole(String comCode,String name,Pageable pageable){
		Page<Role> page =null;
		if(name!=null&&!"".equals(name))
			page = geRmsRoleRepository.findRoleByName(comCode, name, pageable);
		else
			page = geRmsRoleRepository.findRole(comCode, pageable);
		return page;
	}
	
	//根据角色ID查询角色关联的功能
	public List<Task> findTaskByRole(String roleId){
		//先查询角色关联的授权
		List<String> roleids=new ArrayList<String>() ;
		roleids.add(roleId);
		List<String> taskIds=geRmsTaskAuthRepository.findTaskAuthByRole(roleids);
		//根据授权获得的功能ID获取功能集合
//		List<Task> geRmsTasks =geRmsTaskRepository.findTaskByTaskIds(taskIds);
		List<Task> geRmsTasks = null;
		if(!taskIds.isEmpty()){
			
			geRmsTasks = (List<Task>) geRmsTaskRepository.findAll(taskIds);
		}
		return geRmsTasks;
	}
	
	//根据机构查询所有可用的功能
	public List<Task> findTaskByComCode(String comCode){
		List<String> taskIds=geRmsTaskAuthRepository.findAllTaskIdByComCode(comCode);
		List<Task> geRmsTasks=(List<Task>) geRmsTaskRepository.findAll(taskIds);
		return geRmsTasks;
	}


	public void updateRole(String roleId,String  comCode, String userCode,String name, String des,
			String roleTpe, List<String> taskids) {
		Role role=geRmsRoleRepository.findOne(roleId);
		//删除角色关联的功能
		geRmsRoleRepository.deleteRoleTask(roleId, comCode);
		List<TaskAuth> geRmsTaskAuths=geRmsTaskAuthRepository.findTaskAuthByComCode(comCode, taskids);
		List<RoleTask> roleTasks = new ArrayList<RoleTask>();
		for (TaskAuth geRmsTaskAuth : geRmsTaskAuths) {
			RoleTask geRmsRoleTask=new RoleTask();
			geRmsRoleTask.setRole(role);
			geRmsRoleTask.setTaskAuth(geRmsTaskAuth);
			geRmsRoleTask.setIsValidate("1");
			roleTasks.add(geRmsRoleTask);
		}
		role.setRoleTasks(roleTasks);
		role.setName(name);
		role.setDes(des);
		Date date = new Date();
		role.setOperateTime(date);
		role.setOperateUser(userCode);
		geRmsRoleRepository.save(role);
		if(roleTpe.toString().equals("default")){
			//修改类型
			geRmsRoleRepository.updateRoleToDefault(comCode, roleId);
			// comCodes为选中的机构 不 delete comCodes的机构指派
			geRmsRoleRepository.deleteRoleDesignate(comCode, roleId);
			//删除不指派的用户组角色数据
			geRmsGroupRoleRepository.deleteGroupRoleByComCode(comCode);
			editDefaultGroup(comCode, userCode, role);
		}
		if(roleTpe.toString().equals("all")){
			geRmsRoleRepository.updateRoleToAll(comCode, roleId);
		}
	}
	
	public void addRole(String comCode, String userCode, String name,
			String des, String roleTpe, List<String> taskids) {
		Role role = new Role();
		role.setCreateUser(userCode);
		role.setOperateUser(userCode);
		role.setName(name);
		role.setDes(des);
		role.setComCode(comCode);
		List<TaskAuth> taskAuths=geRmsTaskAuthRepository.findTaskAuthByComCode(comCode, taskids);
		List<RoleTask> roleTasks = new ArrayList<RoleTask>();
		for (TaskAuth taskAuth : taskAuths) {
			RoleTask roleTask = new RoleTask();
			roleTask.setRole(role);
			roleTask.setIsValidate("1");
			roleTask.setTaskAuth(taskAuth);
			roleTasks.add(roleTask);
		}
		role.setRoleTasks(roleTasks);
		role.setIsValidate("1");
		Date date = new Date();
		role.setCreateTime(date);
		role.setOperateTime(date);
		if(roleTpe.toString().equals("default".toString()))
			role.setFlag("");
		if(roleTpe.toString().equals("all".toString()))
			role.setFlag("*");
		geRmsRoleRepository.save(role);
		RoleDesignate roleDesignate = new RoleDesignate();
		RoleDesignateId roleDesignateId = new RoleDesignateId();
		roleDesignateId.setRoleID(role.getRoleID());
		if(roleTpe.toString().equals("default".toString()))
			roleDesignateId.setComCode(comCode);
		if(roleTpe.toString().equals("all".toString()))
			roleDesignateId.setComCode("*");
		roleDesignate.setRole(role);
		roleDesignate.setId(roleDesignateId);
		roleDesignate.setCreateUser(userCode);
		roleDesignate.setOperateUser(userCode);
		roleDesignate.setCreateTime(date);
		roleDesignate.setOperateTime(date);
		geRmsRoleDesignateRepository.save(roleDesignate);
		//操作默认用户组 默认类型的才操作
		if(roleTpe.toString().equals("default".toString()))
			editDefaultGroup(comCode, userCode, role);
	}
	
	public void deleteRole(String roleId, String comCode){
		RoleDesignateId roleDesignateId=new RoleDesignateId();
		roleDesignateId.setComCode(comCode);
		roleDesignateId.setRoleID(roleId);
//		 geRmsRoleDesignateRepository.delete(roleDesignateId);
	}
	
	//操作默认用户组
	void editDefaultGroup(String comCode,String userCode,Role role){
		String groupid=  geRmsGroupRepository.findGroupIdbyName("默认用户组", comCode);
		Group group= geRmsGroupRepository.findOne(groupid);
		if(group==null){
			//如果该机构没有用户组则先建立默认用户组
			group=new Group();
			group.setComCode(comCode);
			group.setName("默认用户组");
			group.setIsValidate("1");
			Date date = new Date();
			group.setCreateTime(date);
			group.setOperateTime(date);
			group.setCreateUser(userCode);
			group.setOperateUser(userCode);
			geRmsGroupRepository.save(group);
		}
		//获取关联表是否为空
		if (role != null) {
			List<GroupRole> groupRoles=group.getGroupRoles();
			// 如果没有组-角色的关联表则建立关联表数据
			if (groupRoles.size() == 0) {
				// 为空则建立关联
				List<GroupRole> groupRols = new ArrayList<GroupRole>();
				GroupRole groupRole = new GroupRole();
				groupRole.setGroup(group);
				groupRole.setRole(role);
				groupRole.setIsValidate("1");
				groupRols.add(groupRole);
				group.setGroupRoles(groupRols);
				geRmsGroupRepository.save(group);
			} else {
				// 如果不为空 则循环判断是否有相等的 则不再新建
				int i = 0;
				for (GroupRole groupRole : groupRoles) {
					if (!groupRole.getRole().getRoleID().toString()
							.equals(role.getRoleID())) {
						i++;
					}
				}
				if (i == groupRoles.size()) {
					GroupRole groupRole = new GroupRole();
					groupRole.setGroup(group);
					groupRole.setRole(role);
					groupRole.setIsValidate("1");
					groupRoles.add(groupRole);
					group.setGroupRoles(groupRoles);
					geRmsGroupRepository.save(group);
				}
			}
		}
	}

	//根据用户组ID查询相应的角色
	public List<Role> findRoleByGroupId(String groupId, String comCode) {
		List<String> roleIds = new ArrayList<String>();
		Group group =geRmsGroupRepository.findOne(groupId);
		List<GroupRole>groupRoles =group.getGroupRoles();
		
		for (GroupRole groupRole : groupRoles) {
			roleIds.add(groupRole.getRole().getRoleID());
		}
		List<String> results = new ArrayList<String>();
		List<String> roleDegNatIds =geRmsRoleDesignateRepository.findRoleIdByComCode(comCode);
		for (String roleDegNatId : roleDegNatIds) {
			for (String roleId : roleIds) {
				if(roleDegNatId.toString().equals(roleId)){
					results.add(roleId);
				}
			}
		}
		List<Role> roles = null;
		if(!results.isEmpty()){
			roles =(List<Role>) geRmsRoleRepository.findAll(results);
		}
		return roles;
	}


	public Page<RoleDesignateInfo> findRoleDesignate(String superComCode ,String comCode,
			Pageable pageable) {
		List<RoleDesignate> supers= geRmsRoleDesignateRepository.findRoleDesignateByComCodeQuery(superComCode);
		final Page<RoleDesignate> superRoledPage=geRmsRoleDesignateRepository.findRoleDesignateByComCode(superComCode,pageable);
//		List<RoleDesignate> subs=geRmsRoleDesignateRepository.findRoleDesignateByComCodeQuery(comCode);
		
		for (RoleDesignate roleDesignate : supers) {
			System.out.println(roleDesignate.getId().getComCode());
			System.out.println(roleDesignate.getId().getRoleID());
		}
		List<RoleDesignateInfo> roleDesignateInfos=new ArrayList<RoleDesignateInfo>();
		for (RoleDesignate supDesignate : supers) {
			RoleDesignateInfo roleDesignateInfo=new RoleDesignateInfo();
			roleDesignateInfo.setCreateTime(supDesignate.getCreateTime());
			roleDesignateInfo.setCreateUser(supDesignate.getCreateUser());
			roleDesignateInfo.setRoleId(supDesignate.getRole().getRoleID());
			roleDesignateInfo.setRoleName(supDesignate.getRole().getName());
			roleDesignateInfo.setOperateTime(supDesignate.getOperateTime());
			roleDesignateInfo.setOperateUser(supDesignate.getOperateUser());
			roleDesignateInfo.setComCode(supDesignate.getId().getComCode());
//			for (RoleDesignate subDesignate : subs) {
//				if(supDesignate.getId().getRoleID().toString().equals(subDesignate.getId().getRoleID().toString())){
//					roleDesignateInfo.setType("true");
//					break;
//				}
//				
//			}
			roleDesignateInfos.add(roleDesignateInfo);
		}
		
		final Iterator<RoleDesignateInfo> iterator= roleDesignateInfos.iterator();
		final List<RoleDesignateInfo> result=roleDesignateInfos;
		
		Page<RoleDesignateInfo> page=new Page<RoleDesignateInfo>() {
			public Iterator<RoleDesignateInfo> iterator() {
				return iterator;
			}
			
			public boolean isLastPage() {
				return superRoledPage.isLastPage();
			}
			
			public boolean isFirstPage() {
				return superRoledPage.isFirstPage();
			}
			
			public boolean hasPreviousPage() {
				return superRoledPage.hasPreviousPage();
			}
			
			public boolean hasNextPage() {
				return superRoledPage.hasNextPage();
			}
			
			public boolean hasContent() {
				return superRoledPage.hasContent();
			}
			
			public int getTotalPages() {
				return superRoledPage.getTotalPages();
			}
			
			public long getTotalElements() {
				return superRoledPage.getTotalElements();
			}
			
			public Sort getSort() {
				return superRoledPage.getSort();
			}
			
			public int getSize() {
				return superRoledPage.getSize();
			}
			
			public int getNumberOfElements() {
				return superRoledPage.getNumberOfElements();
			}
			
			public int getNumber() {
				return superRoledPage.getNumber();
			}
			
			public List<RoleDesignateInfo> getContent() {
				return result;
			}
		};
		return page;
	}

	public void designateRole(List<String> roleIds, String comCode) {
		
	}

	public void designateRole(String roleId, String comCode) {
		// TODO Auto-generated method stub
		
	}

	//根据机构Id查询角色ID
	public List<String> findRoleIdByComCode(String comCode) {
		List<String> roleId = geRmsRoleDesignateRepository.findRoleIdByComCode(comCode);
		return roleId;
	}

	
}
