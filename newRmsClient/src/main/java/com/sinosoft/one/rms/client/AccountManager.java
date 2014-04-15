package com.sinosoft.one.rms.client;

import com.sinosoft.one.rms.User;

public interface AccountManager {

	public User login(String userCode, String comCode,String sysFlag);
	 
}
