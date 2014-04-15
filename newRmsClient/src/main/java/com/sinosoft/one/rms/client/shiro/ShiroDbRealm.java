package com.sinosoft.one.rms.client.shiro;


import ins.framework.common.EncryptUtils;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.client.AccountManager;

@Component
public class ShiroDbRealm  extends AuthorizingRealm{

	@Autowired
	private AccountManager accountManager;

	public ShiroDbRealm(){
		super();
		super.setAuthenticationTokenClass(LoginToken.class);
	}
	
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken)  {
		
		try {
			LoginToken token = (LoginToken) authcToken;
			User user = accountManager.login(token.getUserCode(),token.getComCode(),token.getSysFlag());
			if (user != null) {
				if (user.getPassWord() != null
						&& !"".equals(user.getPassWord().toString())) {
					System.out.println("AuthenticationInfo");
					return new SimpleAuthenticationInfo(user, user.getPassWord(), getName());
				} else {
					return new SimpleAuthenticationInfo(user, EncryptUtils.md5(token.getPassWord()), getName());
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		User user = (User) principals.fromRealm(getName()).iterator().next();
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			System.out.println("授权："+user.getUserName());
			info.addRoles(user.getRoleIdList());
			info.addStringPermissions(user.getTaskIdList());
			return info;
		} else {
			return null;
		}
	}

	
	

}
