package com.sinosoft.one.rms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.sinosoft.one.rms.model.Company;
import com.sinosoft.one.rms.model.Employe;
import com.sinosoft.one.rms.repositories.CompanyDao;
import com.sinosoft.one.rms.repositories.GeRmsUserPowerRepository;
import com.sinosoft.one.rms.repositories.UserDao;
import com.sinosoft.one.uiutil.Gridable;

@Component
public class EmployeeService {

	@Resource(name="userDao")
	private UserDao userDao;
	@Resource(name="geRmsUserPowerRepository")
	private GeRmsUserPowerRepository geRmsUserPowerRepository;
	@Resource(name="companyDao")
	private CompanyDao companyDao;
	
	public Employe findEmployeByUserCode(String userCode) {	
		Employe employe = userDao.findUserById(userCode);		
		return employe;
	}

	//将数据库中的用户记录查出，并保存在Gridable对象中返回
	public Gridable<Employe> getGridable(Gridable<Employe> gridable,
			Pageable pageable, List<String> userAttribute) {
		Page<Employe> page = userDao.findAll(pageable);
		List<Employe> userList = page.getContent();
		
		gridable = getGridable(userList, gridable, userAttribute, page);
		
		return gridable;
	}

	//根据userCode和comCode，将数据库中的用户记录查出，并保存在Gridable对象中返回
	public Gridable<Employe> getGridable(Gridable<Employe> gridable,
			String userCode, String comCode, Pageable pageable,
			List<String> userAttribute) {
			Page<Employe> page =null;

			if(userCode.equals("null") && comCode.equals("null")){
				
				//userCode和comCode都为空时，查询全部
				page = userDao.findAll(pageable);
			}else if(!userCode.equals("null") && comCode.equals("null")){
				
				//userCode不为空，comCode为空时，根据userCode，进行查询
				page = userDao.findUserByUserCode("%"+userCode+"%",pageable);
				
			}else if(userCode.equals("null") && !comCode.equals("null")){
				
				//userCode为空，comCode不为空时，根据comCode，进行查询
				page = userDao.findUserByComCode("%"+comCode+"%",pageable);

			}else {
				
				//userCode不为空，comCode不为空时，根据userCode和comCode，进行查询
				page = userDao.findUserByComCodeUserCode("%"+comCode+"%", "%"+userCode+"%",pageable);

			}
			List<Employe> userList = page.getContent();
			
			gridable = getGridable(userList, gridable, userAttribute, page);
			return gridable;
	}
	
	public Gridable<Employe> getGridable(List<Employe> userList,Gridable<Employe> gridable ,List<String> userAttribute,Page<Employe> page ){
		String button = "<a href='javascript:;' class='set' onclick='openQX(this);'>权限设置</a><a href='#' class='set' onclick='openSJ(this);'>数据设置</a>";
		String button_ = "<a href='javascript:;' class='set' onclick='openQX(this);'>权限设置</a><a href='#' title='该人员未配置权限，无法操作' class='set dis'>数据设置</a>";
		String button2 = "<a href='#' class='agency' onclick='openWindow(this)'></a>";
		for(Employe user: userList){
			List<String> userPowerId = geRmsUserPowerRepository.findUserPowerIdByUserCode(user.getUserCode());
			System.out.println(userPowerId);
			if(!userPowerId.isEmpty()){
				user.setNewUserCode(button);
			}else{
				user.setNewUserCode(button_);
			}
			user.setArticleCode(button2);
			String comCodeUser = userDao.findComCodeByUserCode(user.getUserCode());
			Company com = companyDao.findOne(comCodeUser);
			user.setFlag(com.getComCName());
		}
		
		gridable.setPage(page);
		gridable.setIdField("userCode");
		userAttribute.add("userCode");
		userAttribute.add("userName");
		userAttribute.add("flag");
		userAttribute.add("articleCode");
		userAttribute.add("newUserCode");
		gridable.setCellListStringField(userAttribute);
		return gridable;
	}

	public Company findComByUserCode(String userCode) {
		String comCode = userDao.findComCodeByUserCode(userCode);
		Company com = new Company();
		if(comCode != null){
			com = companyDao.findOne(comCode);
		}else{
			com.setComCode("");
		}
		return com;
	}

}
