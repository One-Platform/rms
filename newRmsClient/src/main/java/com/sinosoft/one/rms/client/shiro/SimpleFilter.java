package com.sinosoft.one.rms.client.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import com.sinosoft.one.rms.Menu;
import com.sinosoft.one.rms.User;

public class SimpleFilter extends AuthenticatingFilter {

	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) throws Exception {
		String username = getUsername(request);
		String password = getPassword(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return createToken(username, password, rememberMe, host);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		try {

			if (isLoginRequest(request, response)) {// 是否登录的请求
				System.out.println("请求");
				if (isLoginSubmission(request, response)) {// 是否登陆请求的提交
					System.out.println("提交");
					return executeLogin(request, response);// 拦截的是登陆链接则返回executeLogin
				} else {
					return true;
				}
			} else {
				saveRequestAndRedirectToLogin(request, response);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		issueSuccessRedirect(request, response);
		// we handled the success redirect directly, prevent the chain from
		// continuing:
		return false;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		Subject subject = getSubject(request, response);
		if (subject.isAuthenticated()
				|| (!isLoginRequest(request, response) && isPermissive(mappedValue))) {
			User user = (User) subject.getPrincipals().getPrimaryPrincipal();
			for (String url : user.getUrls()) {
				String requesturi = WebUtils
						.getRequestUri((HttpServletRequest) request);
				if (requesturi.toString().equals(url)) {
					return true;
				}
			}

		}
		return false;
	}

	public boolean isLoginSubmission(ServletRequest request,
			ServletResponse response) {
		return (request instanceof HttpServletRequest)
				&& WebUtils.toHttp(request).getMethod()
						.equalsIgnoreCase(POST_METHOD);
	}

	protected String getUsername(ServletRequest request) {
		return WebUtils.getCleanParam(request, "userCode");
	}

	protected String getPassword(ServletRequest request) {
		return WebUtils.getCleanParam(request, "passWord");
	}

}
