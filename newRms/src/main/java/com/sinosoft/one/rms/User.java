package com.sinosoft.one.rms;

import java.util.Collections;
import java.util.List;
import org.springframework.util.Assert;


/** 
 * webService服务端传输对象
 * @author Administrator
 */
public class User {
	//用户信息
	private String userCode;

	private String userName;
	
	private String passWord;
	
	private String plaintextPassWord;
	//权限生效的机构代码
	private String loginComCode;
	
	private String loginComName;
	
	private List<String> roleIdList;

	//功能权限集合
	private List<String> taskIdList;
	
	private List<Menu> menuList;
	
	private List<DataPower> dataPowers;
	
	private List<String>urls;
	public User(){
	}

	public User(final String userCode,final String passWord,final String userName,final String loginComCode,final String loginComName,
		final List<String> roleIdList,final List<String> taskIdList,final List<Menu> menuList, final List<DataPower> dataPowers,final List<String> urls){
		Assert.hasText(userCode);
		Assert.hasText(userName);
		Assert.hasText(loginComCode);
		Assert.hasText(loginComName);
		Assert.notNull(taskIdList);
		Assert.notNull(urls);
		this.userCode=userCode;
		this.passWord=passWord;
		this.userName=userName;
		this.loginComCode=loginComCode;
		this.loginComName=loginComName;
		this.roleIdList = roleIdList;
		this.taskIdList=taskIdList;
		this.menuList=menuList;
		this.dataPowers=dataPowers;
		this.urls=urls;
	}

	public String getLoginComCode() {
		return loginComCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public String getUserName() {
		return userName;
	}

	public String getLoginComName() {
		return loginComName;
	}

	public List<String> getTaskIdList() {
		return Collections.unmodifiableList(taskIdList);
	}
	
	public String getPassWord() {
		return passWord;
	}

	public List<String> getRoleIdList() {
		return Collections.unmodifiableList(roleIdList);
	}
	
	public List<Menu> getMenuList() {
		return Collections.unmodifiableList(menuList);
	}

	public List<DataPower> getDataPowers() {
		return dataPowers;
	}

	public void setUserCode(String userCode) {
		if (this.userCode==null) {
			this.userCode = userCode;
		}
	}

	public void setUserName(String userName) {
		if(this.userName==null){
			this.userName = userName;
		}
	}

	public void setPassWord(String passWord) {
		if(this.passWord ==null){
			this.passWord = passWord;
		}
	}

	public void setLoginComCode(String loginComCode) {
		if(this.loginComCode==null){
			this.loginComCode = loginComCode;
		}
		
	}

	public void setLoginComName(String loginComName) {
		if(this.loginComName==null){
			this.loginComName = loginComName;
		}
		
	}

	public void setRoleIdList(List<String> roleIdList) {
		if(	this.roleIdList==null){
			this.roleIdList = roleIdList;
		}
	}

	public void setTaskIdList(List<String> taskIdList) {
		if(	this.taskIdList==null){
			this.taskIdList = taskIdList;
		}
	}

	public void setMenuList(List<Menu> menuList) {
		if(this.menuList ==null){
			this.menuList = menuList;
		}
	}

	public void setDataPowers(List<DataPower> dataPowers) {
		if(this.dataPowers ==null){
			this.dataPowers = dataPowers;
		}
	}

	public String getPlaintextPassWord() {
		return plaintextPassWord;
	}

	public void setPlaintextPassWord(String plaintextPassWord) {
		this.plaintextPassWord = plaintextPassWord;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	
	
	
}
