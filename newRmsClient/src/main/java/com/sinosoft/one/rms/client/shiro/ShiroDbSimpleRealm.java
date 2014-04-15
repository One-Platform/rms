package com.sinosoft.one.rms.client.shiro;


import javax.naming.AuthenticationException;

import ins.framework.common.EncryptUtils;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.client.AccountManager;

@Component
public class ShiroDbSimpleRealm  extends AuthorizingRealm{


	@Autowired
	private AccountManager accountManager;

	public ShiroDbSimpleRealm(){
		super();
		super.setAuthenticationTokenClass(UsernamePasswordToken.class);
	}
	
	/**
	 * 认证、认证信息获取
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) {

		String userCode = (String) token.getPrincipal();
		String passWord = (String) token.getCredentials();
		User user = accountManager.login(userCode, "00", "RMS");

		if (user == null) {
			throw new UnknownAccountException();// 没找到帐号
		}
		if (user.getPassWord() == null
				|| "".equals(user.getPassWord().toString())
				|| !passWord.equals(user.getPassWord())) {
			throw new UnknownAccountException();// 没找到帐号
		}

		System.out.println("AuthenticationInfo");
		return new SimpleAuthenticationInfo(user, user.getPassWord(), getName());
	
	}
	
	 /* 
	 * 授权信息获取
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		User user = (User) principals.fromRealm(getName()).iterator().next();
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addRoles(user.getRoleIdList());
			info.addStringPermissions(user.getTaskIdList());
			return info;
		} else {
			return null;
		}
	}

	
	

}
