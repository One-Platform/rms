package com.sinosoft.one.rms.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sinosoft.one.rms.User;

@DirtiesContext
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml"})
public class GetUserServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private GetUserService getUserService;
	
	@Test
	public void testGetUserByUserCodeComCode(){
		User user = getUserService.getUserByUserCodeComCode("admin", "00", "RMS");
		Assert.assertEquals("admin", user.getUserCode());
	}

}
