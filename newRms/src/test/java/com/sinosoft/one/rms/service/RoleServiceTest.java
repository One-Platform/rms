package com.sinosoft.one.rms.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sinosoft.one.rms.model.Role;
import com.sinosoft.one.rms.model.Task;

@DirtiesContext
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml"})
public class RoleServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private RoleService roleService;
	
	@Test
	public void testFindRoleById(){
		Role role = roleService.findRoleById("role100011");
		if(role!=null){
			Assert.assertEquals("role100011", role.getRoleID());
		}
		
	}
	
	/**
	 * 根据角色ID查询角色关联的功能
	 */
	@Test
	public void testFindTaskByRole(){
		List<Task> tasks = roleService.findTaskByRole("role100012");
		if(tasks!=null){
			for(Task task : tasks){
				System.out.println(task.getTaskID());
			}
		}
	}
	
	/**
	 * 根据机构查询所有可用的功能
	 */
	@Test
	public void testFindTaskByComCode(){
		List<Task> tasks = roleService.findTaskByComCode("00");
		for(Task task : tasks){
			System.out.println(task.getTaskID());
		}
	}
	
	/**
	 * 查询机构下所有可见的角色
	 */
	@Test
	public void testFindRole(){
		Pageable pageable = new PageRequest(0, 10);
		Page<Role> page = roleService.findRole("00", null, pageable);
		List<Role> roles = page.getContent();
		for(Role role : roles){
			System.out.println(role.getRoleID());
		}
	}
	
//	/**
//	 * 根据父机构ID查询角色
//	 */
//	@Test
//	public void testFindRoleDesignate(){
//		Pageable pageable = new PageRequest(0, 10);
//		Page<RoleDesignateInfo> page = roleService.findRoleDesignate("00", pageable);
//		List<RoleDesignateInfo> RoleDesignateInfos = page.getContent();
//		for(RoleDesignateInfo rdi : RoleDesignateInfos){
//			System.out.println(rdi.getRoleId());
//		}
//	}
	
	/**
	 * 根据用户组ID查询相应的角色
	 */
	@Test
	public void testFindRoleByGroupId(){
		List<Role> roles = roleService.findRoleByGroupId("group10010", "00");
		if(roles!=null){
			for(Role role : roles){
				System.out.println(role.getRoleID());
			}
		}
	}
	
	/**
	 * 更新角色
	 */
	@Test
	public void testUpdateRole(){
		List<String> taskIds = new ArrayList<String>();
		taskIds.add("RMS001");
		roleService.updateRole("role100001", "07", "admin", "role00000", "测试000", "all", taskIds);
	}
	
	/**
	 * 新增角色
	 */
	@Test
	public void testAddRole(){
		List<String> taskIds = new ArrayList<String>();
		taskIds.add("RMS001");
		roleService.addRole("00", "admin", "Transactional2", "Transactional2", "all", taskIds);
	}
	
	/**
	 * 删除角色指派
	 */
	@Test
	public void testDeleteRole(){
		roleService.deleteRole("402892173c8e58b5013c8e58baee0000", "00");
		
	}
	
	/**
	 * 根据机构Id查询角色ID
	 */
	@Test
	public void testFindRoleIdByComCode(){
		List<String> roleIds = roleService.findRoleIdByComCode("00");
		if(!roleIds.isEmpty()){
			for(String id : roleIds){
				System.out.println(id);
			}
		}
	}
	
	/**
	 * 保存机构的角色
	 */
	@Test
	public void testSaveRoleDesignate(){
//		roleService.saveRoleDesignate("02", "role100012");
	}

}
