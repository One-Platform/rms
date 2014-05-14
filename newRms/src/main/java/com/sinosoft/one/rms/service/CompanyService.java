package com.sinosoft.one.rms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sinosoft.one.data.jade.parsers.sqljep.function.In;
import com.sinosoft.one.rms.model.Company;
import com.sinosoft.one.rms.model.UserPower;
import com.sinosoft.one.rms.repositories.CompanyDao;
import com.sinosoft.one.rms.repositories.GeRmsUserPowerRepository;
import com.sinosoft.one.uiutil.NodeEntity;
import com.sinosoft.one.uiutil.Treeable;

@Component
public class CompanyService{
	
	@Resource(name="companyDao")
	private CompanyDao companyDao;
	@Resource(name="geRmsUserPowerRepository")
	private GeRmsUserPowerRepository geRmsUserPowerRepository;

	public List<Company> findCompanyByUpperComCode(String uppercomcode) {
		
		List<Company> company = new ArrayList<Company>();
		
		if(uppercomcode == null){
			company = (List<Company>) companyDao.findAll();
		}else{
			List<String> childComCode = companyDao.findComCodeByUppercomcode(uppercomcode);
			if(!childComCode.isEmpty()){
				company = (List<Company>) companyDao.findAll(childComCode);
			}
			
		}
		return company;
	}
	
	public Company findCompanyByComCode(String comCode) {
		return companyDao.findOne(comCode);
	}

	/**
	 * 构建功能树 topCompany父节点 filter所有节点
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  Treeable<NodeEntity> creatCompanyTreeAble(List<Company> topCompany,Map<String,Company> filter){
		List<NodeEntity> nodeEntitys=new ArrayList<NodeEntity>();
		nodeEntitys=creatSubNode(topCompany, filter);
		Treeable<NodeEntity> treeable =new Treeable.Builder(nodeEntitys,"id", "title", "children", "state").classField("classField").urlField("urlField").builder();
		return treeable;
	}
	
	List<NodeEntity> creatSubNode(List<Company> topCompany,Map<String,Company> filter){
		ArrayList<NodeEntity> nodeEntitys=new ArrayList<NodeEntity>();
		for (Company company : topCompany) {
			if(!filter.containsKey(company.getComCode()))
                continue;
			NodeEntity nodeEntity = new NodeEntity();
			nodeEntity.setId(company.getComCode());
			nodeEntity.setTitle(company.getComCName());
			List<Company> childCompany = findCompanyByUpperComCode(company.getComCode());
			if(!childCompany.isEmpty()){
				nodeEntity.setChildren(creatSubNode(childCompany,filter));
				
			}
				nodeEntitys.add(nodeEntity);
			}
		return nodeEntitys;
	}

	public List<Company> findAll() {
		return (List<Company>) companyDao.findAll();
	}

	/**
	 * 获取所有下级子机构
	 * @param uppercomcode
	 * @return
	 */
	public List<Company> findAllNextComBySupper(String uppercomcode) {
		List<String>resultComCodes=new ArrayList<String>();
		List<String>uppercomcodes=new ArrayList<String>();
		uppercomcodes.add(uppercomcode);
		iteratorGetComCode(resultComCodes, uppercomcodes);
		return (List<Company>) companyDao.findAll(resultComCodes);
	}
	
	/**
	 * 获取所有下一级子机构代码
	 * @param uppercomcode
	 * @return
	 */
	public List<String> findNextComCodeBySupper(List<String>uppercomCodes) {
		List<String>subComCodes=new ArrayList<String>();
		if(uppercomCodes.size()>1000){
			int i;
			for ( i = 0; i < uppercomCodes.size()/1000; i++) {
				subComCodes.addAll(companyDao.findComCodeByUppercomcode(uppercomCodes.subList(i*1000, 1000*(i+1))));
			}
			subComCodes.addAll(companyDao.findComCodeByUppercomcode(uppercomCodes.subList(1000*(i+1), uppercomCodes.size())));
		}
		return subComCodes;
	}
//	/**
//	 * 获取所有下级子机构机构代码
//	 * @param uppercomcode
//	 * @return
//	 */
//	
//	public List<String>findAllNextComCodeBySupper(List<String>uppercomCodes) {
//		List<String>resultComCodes=new ArrayList<String>();
//		iteratorGetComCode(resultComCodes, uppercomCodes);
//		return resultComCodes;
//	}
	
	
	void iteratorGetComCode(List<String>resultComCodes,List<String> uppercomCodes){
		List<String>subComCodes=new ArrayList<String>();
		if(uppercomCodes.size()>1000){
			int i;
			for ( i = 0; i < uppercomCodes.size()/1000; i++) {
				subComCodes.addAll(companyDao.findComCodeByUppercomcode(uppercomCodes.subList(i*1000, 1000*(i+1))));
			}
			subComCodes.addAll(companyDao.findComCodeByUppercomcode(uppercomCodes.subList(1000*(i+1), uppercomCodes.size())));
		}else{
				subComCodes.addAll(companyDao.findComCodeByUppercomcode(uppercomCodes));
		}
		if(subComCodes.size()>0){
			resultComCodes.addAll(subComCodes);
			iteratorGetComCode(resultComCodes, subComCodes);
		}
	}

	
	
	
	//根据userCode查询出用户已被引入的机构
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Treeable<NodeEntity> findCompanyByUserCode(String userCode) {
		List<String> userPowerIds = geRmsUserPowerRepository.findUserPowerIdByUserCode(userCode);
		List<NodeEntity> nodeEntitys=new ArrayList<NodeEntity>();
		Treeable<NodeEntity> treeable =new Treeable.Builder(nodeEntitys,"id", "title", "children", "state").builder();
		
		if(!userPowerIds.isEmpty()){
			List<UserPower> userPowers = (List<UserPower>) geRmsUserPowerRepository.findAll(userPowerIds);
			
			List<String> comCodes = new ArrayList<String>();
			for(UserPower userPower : userPowers){
				comCodes.add(userPower.getComCode());
			}
			List<Company> companies = (List<Company>) companyDao.findAll(comCodes);
			
			for(Company company : companies){
				NodeEntity nodeEntity = new NodeEntity();
				nodeEntity.setId(company.getComCode());
				nodeEntity.setTitle(company.getComCName());
				nodeEntitys.add(nodeEntity);
			}
			
			
		}
		return treeable;
	}

	

}
