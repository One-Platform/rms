package com.sinosoft.one.rms.client.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class LoginToken implements AuthenticationToken{

	private static final long serialVersionUID = 1L;
	
	private String userCode;
	
	private String passWord;
	
	private String comCode;
	
	private String sysFlag;
	
	public LoginToken(){
	}  
	  
    public LoginToken(String userCode,String passWord,String comCode,String sysFlag ){  
        this.userCode=userCode; 
        this.passWord=passWord; 
        this.comCode=comCode; 
        this.sysFlag=sysFlag;
    }  
	
	public Object getPrincipal() {
		return getUserCode();
	}

	public Object getCredentials() {
		return getPassWord();
	}
	
	public void clear() {
		this.userCode = null;
		this.passWord = null;
		this.comCode= null;
		this.sysFlag= null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName());
		sb.append(" - ");
		sb.append(userCode);
		sb.append(" - ");
		sb.append(passWord);
		sb.append(" - ");
		sb.append(comCode);
		sb.append(" - ");
		sb.append(sysFlag);
		return sb.toString();
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}
	
}
