package com.sinosoft.one.rms.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sinosoft.one.rms.model.Group;
import com.sinosoft.one.rms.model.Role;
import com.sinosoft.one.uiutil.Gridable;

@DirtiesContext
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml"})
public class GroupServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private GroupService groupService;
	
	/**
	 * 测试根据机构Id，查询机构的用户组
	 */
	@Test
	public void testFindGroupByComCode(){
		List<Group> groups = groupService.findGroupByComCode("00");
		if(!groups.isEmpty()){
			int i = 0;
			for(Group group : groups){
				System.out.println(i+","+group.getGroupID());
				i++;
			}
		}
	}
	
	/**
	 * 测试根据机构Id，查询机构的用户组,并对已引入用户的组进行标记
	 */
	@Test
	public void testFindGroupByComCode2(){
		List<Group> groups = groupService.findGroupByComCode("00","admin");
		if(!groups.isEmpty()){
			int i = 0;
			for(Group group : groups){
				System.out.println(i+","+group.getGroupID());
				i++;
			}
		}
	}
	
	/**
	 * 测试根据用户组ID查询用户组
	 */
	@Test
	public void testFindGroupById(){
		List<String> groupIds = new ArrayList<String>();
		groupIds.add("group10001");
		groupIds.add("group10002");
		List<Group> groups = groupService.findGroupById(groupIds);
		if(!groups.isEmpty()){
			int i = 0;
			for(Group group : groups){
				System.out.println(i+","+group.getGroupID());
				i++;
			}
		}
	}
	
	/**
	 * 测试根据用户组ID查询用户组
	 */
	@Test
	public void testFindGroupById2(){
		
		Group group = groupService.findGroupById("group10001");
		if(group!=null)
		Assert.assertEquals("group10001", group.getGroupID());
	}
	
	/**
	 * 添加用户组
	 */
	@Test
	public void testAddGroup(){
		List<String> roleIds = new ArrayList<String>();
		roleIds.add("role100001");
		roleIds.add("role100002");
		groupService.addGroup("group00000", "all", roleIds, "测试", "00", "admin");
	}
	
	/**
	 * 查询用户组
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetGroupGridable(){
		Gridable<Group> gridable = new Gridable<Group>(null);
		Pageable pageable = new PageRequest(0, 10);
		gridable = groupService.getGroupGridable(gridable, "00", null, pageable);
		List<Group> groups = gridable.getPage().getContent();
		for(Group group : groups){
			System.out.println(group.getGroupID());
		}
	}
	
	/**
	 * 根据用户组ID查询角色
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRoleGridableByGroupId(){
		Gridable<Role> gridable = new Gridable<Role>(null);
		Pageable pageable = new PageRequest(0, 10);
		gridable = groupService.getRoleGridableByGroupId(gridable, "group10010", "00", pageable);
		if(gridable!=null){
			if(gridable.getPage()!=null){
				List<Role> roles = gridable.getPage().getContent();
				for (Role role : roles) {
					System.out.println(role.getRoleID());
				}
			}
		}
	}

}
