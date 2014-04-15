package com.sinosoft.one.rms.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sinosoft.one.rms.model.Task;

@DirtiesContext
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml"})
public class TaskServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private TaskService taskService;
	
	/**
	 * 根据TaskId查询出Task对象
	 */
	@Test
	public void testFindTaskByTaskId(){
//		LoginToken loginToken = new LoginToken("admin", "000000", "00", "RMS");
//		Subject currentUser = SecurityUtils.getSubject();
//		currentUser.login(loginToken);
//		Task task = taskService.findTaskByTaskId("RMS001");
//		Assert.assertEquals("RMS001", task.getTaskID());
	}
	
	/**
	 * 新建或修改功能，保存
	 */
	@Test
	public void testSave(){
//		TaskAuth taskAuth = new TaskAuth();
//		Task task = new Task();
//		task.setTaskID("RMS0089");
//		task.setName("test89");
//		task.setMenuName("");
//		task.setMenuURL("");
//		task.setDes("");
//		task.setIsValidate("1");
//		task.setIsAsMenu("");
//		task.setFlag("*");
//		taskService.save(task, "RMS004", taskAuth);
	}
	
	/**
	 * 查询出全部功能
	 */
	@Test
	public void testFindAllTasks(){
		List<Task> taskAll = taskService.findAllTasks();
		Assert.assertEquals(Task.class, taskAll.get(0).getClass());
	}
	
	/**
	 * 根据功能ID集合查询出功能
	 */
	@Test
	public void testFindTaskByTaskId2(){
		List<String> taskIds = new ArrayList<String>();
		taskIds.add("RMS001");
//		taskIds.add("RMS002");
//		taskIds.add("RMS003");
		List<Task> tasks = taskService.findTaskByTaskId(taskIds, "RMS");
		Assert.assertEquals(Task.class, tasks.get(0).getClass());
	}
	
	/**
	 * 查询当前机构，当前用户组的根权限，并标记权限是否赋了给用户
	 */
	@Test
	public void testFindTaskByRoleIds(){
		List<String> roleids = new ArrayList<String>();
		roleids.add("role");
		taskService.findTaskByRoleIds(roleids, "00", "admin");
	}
//	
//	/**
//	 * 根据子功能ID查询父功能ID
//	 */
//	@Test
//	public void testFindParentIdBytaskId(){
//		String parentId = taskService.findParentIdBytaskId("RMS002");
//		Assert.assertEquals("RMS001", parentId);
//	}
	
	/**
	 * 根据角色的功能ID和机构ID查询出角色在此机构中的功能
	 */
	@Test
	public void testGetTasks(){
//		List<String> roleTaskIds = new ArrayList<String>();
//		roleTaskIds.add("eTest");
//		roleTaskIds.add("RMS0091");
//		List<Task> tasks = taskService.getTasks(roleTaskIds, "08");
//		List<String> results = new ArrayList<String>();
//		for(Task task : tasks){
//			results.add(task.getTaskID());
//		}
//		Assert.assertFalse(results.contains("RMS0091"));
//		Assert.assertTrue(results.contains("eTest"));
		
	}
	
	/**
	 * 查询当前机构的角色的当前根权限的后代权限，并检查权限在权限除外表中是否存在
	 */
	@Test
	public void testGetTreeable(){
		taskService.getTreeable("role100012", "00", "admin" , "RMS001");
	}
	
	/**
	 * 查询当前机构的角色的根权限的后代权限
	 */
	@Test
	public void testGetTreeable2(){
		taskService.getTreeable("role100012", "00", "RMS001");
	}

}
