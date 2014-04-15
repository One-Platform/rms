package com.sinosoft.one.rms.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sinosoft.one.rms.model.BusPower;
import com.sinosoft.one.rms.model.DataRule;
import com.sinosoft.one.rms.model.UserPower;

@DirtiesContext
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml"})
public class StaffingServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private StaffingService staffingService;
	
	/**
	 * 查询出没有赋参数的数据规则
	 */
	@Test
	public void testGetRules(){
		List<DataRule> dataRules = staffingService.getRules("00", "admin");
		for(DataRule dr : dataRules){
			System.out.println(dr.getDataRuleID());
		}
	}
	
	/**
	 * 查询出有参数的数据规则
	 */
	@Test
	public void testGetRuleParam(){
		List<DataRule> dataRules = staffingService.getRuleParam("00", "admin");
		for(DataRule dr : dataRules){
			System.out.println(dr.getDataRuleID());
		}
	}
	
	/**
	 * 查询数据规则的参数
	 */
	@Test
	public void testGetParams(){
		List<BusPower> busPower = staffingService.getParams("00", "admin", "queryRuleAccordCompany");
		if(busPower.size()>0){
			Assert.assertEquals("queryRuleAccordCompany", busPower.get(0).getDataRule().getDataRuleID());
		}
		}
	
	/**
	 * 检查用户权限的id是否存在，存在返回yes，否则返回no
	 */
	@Test
	public void testCheckIdByUserCodeComCode(){
		String result = staffingService.checkIdByUserCodeComCode("admin", "00");
		Assert.assertEquals("yes", result);
		
		String result2 = staffingService.checkIdByUserCodeComCode("admin", "08");
		Assert.assertEquals("no", result2);
	}
	
	/**
	 * 保存用户的权限除外表、用户权限表和用户与组关系表
	 */
	@Test
	public void testSavePower(){
		staffingService.savePower("07", "user10", "group10110", "RMS001");
	}
	
	/**
	 * 保存数据设置
	 */
	@Test
	public void testSaveBusPower(){
		staffingService.saveBusPower("07", "user10", "rule100001", "{comCode:'07'}");
	}
	
	/**
	 * 查询用户权限
	 */
	@Test
	public void testFindUserPowerByUserCode(){
		List<UserPower> userPosers = staffingService.findUserPowerByUserCode("admin");
		for(UserPower up : userPosers){
			System.out.println(up.getUserPowerID()+","+up.getComCode());
		}
	}
	
//	/**
//	 * 查询用户权限
//	 */
//	@Test
//	public void testFindDataRuleByDataRuleId(){
//		DataRule dr = staffingService.findDataRuleByDataRuleId("queryRuleAccordCompany");
//		Assert.assertEquals("queryRuleAccordCompany", dr.getDataRuleID());
//	}

}
