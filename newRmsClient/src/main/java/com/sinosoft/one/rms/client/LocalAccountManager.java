package com.sinosoft.one.rms.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.service.GetUserService;

@Component
public class LocalAccountManager implements AccountManager{
	

	@Autowired
	private GetUserService getUserService;

	public User login(String userCode, String comCode,String sysFlag) {
		return getUserService.getUserByUserCodeComCode(userCode, comCode, sysFlag);
	}
	
}
