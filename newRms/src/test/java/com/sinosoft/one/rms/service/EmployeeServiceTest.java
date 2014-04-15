package com.sinosoft.one.rms.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sinosoft.one.rms.model.Employe;

@DirtiesContext
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml"})
public class EmployeeServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 测试根据userCode和password查询用户
	 */
	@Test
	public void testFindEmployeByUserCodePassword(){
		Employe user = employeeService.findEmployeByUserCode("admin");
		Assert.assertEquals("admin", user.getUserCode());
	}
	
//	/**
//	 * 测试将数据库中的用户记录查出，并保存在Gridable对象中返回
//	 */
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testGetGridable(){
//		Gridable<EmployeInfo> gridable = new Gridable<EmployeInfo>(null);
//		Pageable pageable = new PageRequest(0, 5);
//		List<String> userAttribute = new ArrayList<String>();
//		
//		gridable = employeeService.getGridable(gridable, pageable, userAttribute);
//		List<EmployeInfo> users = gridable.getPage().getContent();
//		
//		if(!users.isEmpty()){
//			for(EmployeInfo user : users){
//				System.out.println(user.getUserCode());
//			}
//		}
//	}
	
//	/**
//	 * 测试根据userCode和comCode，将数据库中的用户记录查出，并保存在Gridable对象中返回
//	 */
//	@Test
//	public void testGetGridable2(){
//		Gridable<EmployeInfo> gridable = new Gridable<EmployeInfo>(null);
//		Pageable pageable = new PageRequest(0, 5);
//		List<String> userAttribute = new ArrayList<String>();
//		String userCode = "admin";
//		String comCode = "00";
//		gridable = employeeService.getGridable(gridable, userCode, comCode, pageable, userAttribute);
////		EmployeInfo user = (EmployeInfo) gridable.getPage().getContent().get(0);
////		Assert.assertEquals("admin", user.getUserCode());
//		System.out.println(gridable.getPage().getContent().size());
//		
//	}
}
