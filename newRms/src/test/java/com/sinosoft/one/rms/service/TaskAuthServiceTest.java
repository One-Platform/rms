package com.sinosoft.one.rms.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@DirtiesContext
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml"})
public class TaskAuthServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private TaskAuthService taskAuthService;
	
	/**
	 * 根据机构ID，查询机构的功能，保存在一个Treeable对象，并返回
	 */
//	@Test
//	public void testTreeAble(){
//		Treeable<NodeEntity> treeable = taskAuthService.treeAble("00");
//		List<NodeEntity> nes = treeable.getContent();
//		for(NodeEntity ne : nes){
//			System.out.println(ne.getId());
//		}
//	}
//	
	/**
	 * 保存当前机构的功能
	 */
	@Test
	public void testSave(){
//		TaskAuth taskAuth = new TaskAuth();
//		taskAuthService.save("RMS001", "00", taskAuth);
	}
}
