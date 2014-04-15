package com.sinosoft.one.rms.client;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.model.Employe;

public class ShiroLoginUser {

	/**
	 * 获得shiro登陆用户对象
	 * @return
	 */
	public static User getLoginUser(){
		Subject currentUser = SecurityUtils.getSubject();
		User user=(User) currentUser.getPrincipals().getPrimaryPrincipal();
		return user;
	}
	
}
