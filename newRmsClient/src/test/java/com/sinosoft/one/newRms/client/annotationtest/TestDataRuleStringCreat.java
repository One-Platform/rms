package com.sinosoft.one.newRms.client.annotationtest;


import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.client.AccountManager;
import com.sinosoft.one.rms.client.EnvContext;
import com.sinosoft.one.rms.model.Employe;
import com.sinosoft.one.rms.repositories.UserDao;


@DirtiesContext
@ContextConfiguration("/spring/applicationContext-test.xml")
public class TestDataRuleStringCreat extends AbstractJUnit4SpringContextTests{

	@Autowired
	private TestService testService;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private UserDao userDao;
	@Test
	public void testFindAll(){
		User user = accountManager.login("admin", "00","RMS");
		EnvContext.setLogin(user);
//		List<Employe> users = (List<Employe>) userDao.findAll();
//		Employe user = userDao.findUserById("admin");
//		System.out.println(user.getUserCode());
		System.out.println(user.getDataPowers().get(0).getRuleId());
		List<Employe> users = testService.testFindAll();
		System.out.println(user.getDataPowers().size());
		System.out.println(user.getDataPowers().get(0).getComCode());
		System.out.println("users.size()==============================="+users.size());
		
	}
	
}
