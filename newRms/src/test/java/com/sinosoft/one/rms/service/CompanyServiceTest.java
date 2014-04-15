package com.sinosoft.one.rms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sinosoft.one.rms.model.Company;
import com.sinosoft.one.uiutil.NodeEntity;
import com.sinosoft.one.uiutil.Treeable;

@DirtiesContext
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml"})
public class CompanyServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 测试查询子机构
	 */
	@Test
	public void testFindCompanyByUpperComCode(){
		System.out.println("============="+companyService);
		List<Company> coms = companyService.findCompanyByUpperComCode("00");
		System.out.println(coms.size());
	}
	
	/**
	 * 测试查询全部机构
	 */
	@Test
	public void testFindAll(){
		List<Company> coms = companyService.findAll();
		if(!coms.isEmpty()){
			int i = 0;
			for(Company com : coms){
				System.out.println(i+","+com.getComCode());
				i++;
			}
		}
	}
	
	/**
	 * 测试构建机构树
	 */
	@Test
	public void testCreatCompanyTreeAble(){
		List<Company> comAll = companyService.findAll();
		
		Map<String,Company> filter = new HashMap<String, Company>();
		List<Company> top = new ArrayList<Company>();
		for(Company com : comAll){
			if(com.getUpperComCode() == null){
				top.add(com);
			}
			filter.put(com.getComCode(), com);
		}
		
		Treeable<NodeEntity> treeable = companyService.creatCompanyTreeAble(top, filter);
		Assert.assertNotNull("not null!", treeable);
	}
	
	/**
	 * 测试根据机构ID查询机构
	 */
	@Test
	public void testFindCompanyByComCode(){
		Company com = companyService.findCompanyByComCode("00");
		Assert.assertEquals("00", com.getComCode());
	}
	
	/**
	 * 测试根据userCode查询出用户已被引入的机构
	 */
	@Test
	public void testFindCompanyByUserCode(){
		Treeable<NodeEntity> treeable = companyService.findCompanyByUserCode("admin");
		List<NodeEntity>children = treeable.getContent();
		if(!children.isEmpty()){
			int i = 0;
			for(NodeEntity ne : children){
				System.out.println(i+","+ne.getId());
				i++;
			}
		}
	}
	
//	/**
//	 * 测试根据userCode查询用户已引入机构
//	 */
//	@Test
//	public void testFindComsByUserCode(){
//		List<Company> coms = companyService.findComsByUserCode("admin");
//		if(!coms.isEmpty()){
//			int i = 0;
//			for(Company com : coms){
//				System.out.println(i+","+com.getComCode());
//				i++;
//			}
//		}
//	}

}
