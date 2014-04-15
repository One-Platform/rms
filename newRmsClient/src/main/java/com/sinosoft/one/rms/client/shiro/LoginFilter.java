package com.sinosoft.one.rms.client.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.one.rms.User;



public class LoginFilter extends AuthenticatingFilter {

	public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "登陆失败";
	
	private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);
	public static final String DEFAULT_USERNAME_PARAM = "userCode";
	public static final String DEFAULT_PASSWORD_PARAM = "passWord";
	public static final String DEFAULT_COMCODE_PARAM = "comCode";
	public static final String DEFAULT_SYSFLAG_PARAM = "sysFlag";

	private String failureKeyAttribute = DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;
	private String userCodeParam = DEFAULT_USERNAME_PARAM;
	private String passWordParam = DEFAULT_PASSWORD_PARAM;
	private String comCodeParam = DEFAULT_COMCODE_PARAM;
	private String sysFlagParam = DEFAULT_SYSFLAG_PARAM;
	public LoginFilter() {
		 setLoginUrl(DEFAULT_LOGIN_URL);//父类定义的默认登陆链接'loging.jsp'
	}
	
	public void setLoginUrl(String loginUrl) {
		String previous = getLoginUrl();
		if (previous != null) {
			this.appliedPaths.remove(previous);
		}
		super.setLoginUrl(loginUrl);
		this.appliedPaths.put(getLoginUrl(), null);
	}

	
	//必须重写的方法
	public AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) throws Exception {		
//		System.out.println("userCode="+userCode);
		String userCode = getUserCode(request);
		String passWord = getPassWord(request);
		String comCode = getComCode(request);
		String sysFlag = getSysFlag(request);
		System.out.println("userCode="+userCode);
		return new LoginToken(userCode, passWord, comCode,sysFlag);//返回自定义的TOKEN实例
	}

	//必须重写的方法 对所拦截链接的处理
	public boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception  {
			
			if (isLoginRequest(request, response)) {//是否登录的请求
				System.out.println("请求");
				if (isLoginSubmission(request, response)) {//是否登陆请求的提交
					System.out.println("提交");
					return executeLogin(request, response);//拦截的是登陆链接则返回executeLogin
				} else {
				return true;
				}
			} else {
				saveRequestAndRedirectToLogin(request, response);
				return false;
			}
		
	}
	
	  protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
	        AuthenticationToken token = createToken(request, response);
	        if (token == null) {
	            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
	                    "must be created in order to execute a login attempt.";
	            throw new IllegalStateException(msg);
	        }
	        try {
	            Subject subject = getSubject(request, response);
	            subject.login(token);
	            return onLoginSuccess(token, subject, request, response);
	        } catch (AuthenticationException e) {
	            return onLoginFailure(token, e, request, response);
	        }
	    }
	
	public boolean isLoginSubmission(ServletRequest request,
			ServletResponse response) {
		return (request instanceof HttpServletRequest)&& WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
	}
	
	//登陆失败的处理
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		setFailureAttribute(request, e);
		// login failed, let request continue back to the login page:
		return true;
	}
	
	 protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
	        String className = ae.getClass().getName();
	        request.setAttribute(getFailureKeyAttribute(), className);
	}

	//登陆成功的处理
	public boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		issueSuccessRedirect(request, response, subject);
		// we handled the success redirect directly, prevent the chain from
		// continuing:
		return false ;
	}
	
	public void issueSuccessRedirect(ServletRequest request, ServletResponse response,Subject subject)throws Exception{
//		if(pathsMatch(getLoginUrl(), request)){//判断是请求是否是定义的登陆链接 即是否是从登陆页面登陆
//			WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
//		}else{
			String successUrl = null;
			boolean contextRelative = true;
			SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
			if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
				successUrl = savedRequest.getRequestUrl();
				contextRelative = false;//该变量判断是否是登陆失效后再登陆 false：表示不是首次登陆（之前登陆失效）保存了之前的请求savedRequest
			}							//								 true：表示首次登陆 没有保存的请求savedRequest
		        
			if (successUrl == null) {
				successUrl = getSuccessUrl();
			}

			if (successUrl == null) {
				throw new IllegalStateException("Success URL not available via saved request or via the " +
						"successUrlFallback method parameter. One of these must be non-null for " +
						"issueSuccessRedirect() to work.");
			}
			if(!contextRelative){//如果有保存了之前的的保存请求  则一律跳转到配置文件中的成功后链接getSuccessUrl()
				WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);//设置contextRelative为true使其当做第一次登陆 一直返回主框架页面
			}else{
				WebUtils.issueRedirect(request, response,successUrl, null, contextRelative);
			}
//		}
		
	}
	
	

//	
//	protected boolean doisAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
//		Subject subject = getSubject(request, response);
//		if(subject.isAuthenticated()){
//			if (isLoginRequest(request, response)) {
//				if (isLoginSubmission(request, response)) {
//					subject.logout();
//					return subject.isAuthenticated();
//				}
//			}
//		}
//		return subject.isAuthenticated();
//	}
	
	public String getUserCode(ServletRequest request) {
		return WebUtils.getCleanParam(request, getUserCodeParam());
	}

	public String getPassWord(ServletRequest request) {
		return WebUtils.getCleanParam(request, getPassWordParam());
	}

	public String getComCode(ServletRequest request) {
		return WebUtils.getCleanParam(request, getComCodeParam());
	}
	public String getSysFlag(ServletRequest request) {
		return WebUtils.getCleanParam(request, getSysFlagParam());
	}

	public String getUserCodeParam() {
		return userCodeParam;
	}

	public void setUserCodeParam(String userCodeParam) {
		this.userCodeParam = userCodeParam;
	}

	public String getPassWordParam() {
		return passWordParam;
	}

	public void setPassWordParam(String passWordParam) {
		this.passWordParam = passWordParam;
	}

	public String getComCodeParam() {
		return comCodeParam;
	}

	public void setComCodeParam(String comCodeParam) {
		this.comCodeParam = comCodeParam;
	}

	public String getSysFlagParam() {
		return sysFlagParam;
	}

	public void setSysFlagParam(String sysFlagParam) {
		this.sysFlagParam = sysFlagParam;
	}

	public String getFailureKeyAttribute() {
		return failureKeyAttribute;
	}

	public void setFailureKeyAttribute(String failureKeyAttribute) {
		this.failureKeyAttribute = failureKeyAttribute;
	}
	
}
