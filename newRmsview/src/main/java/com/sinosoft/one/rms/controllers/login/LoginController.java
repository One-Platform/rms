/**
 * 
 */
package com.sinosoft.one.rms.controllers.login;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.client.ShiroLoginUser;
import com.sinosoft.one.rms.model.Company;
import com.sinosoft.one.rms.model.Employe;
import com.sinosoft.one.rms.service.CompanyService;
import com.sinosoft.one.rms.service.EmployeeService;
import com.sinosoft.one.rms.service.UserPowerService;

@Path
public class LoginController {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private UserPowerService userPowerService;
	
	@Get
	public String login(Invocation inv){
		//获得shiro已验证的登陆对象 可将其放入SESSION
		User user=ShiroLoginUser.getLoginUser();
		inv.getRequest().getSession().setAttribute("user", user);
		return "index"; 
	} 	
	
	@Post("getMenu")
	public Reply getMenu(Invocation inv){
		User user=(User) inv.getRequest().getSession().getAttribute("user");
		return Replys.with(user.getMenuList()).as(Json.class);
	}
	
	@Get("logout")
	public String logout(Invocation inv){
		HttpSession session = inv.getRequest().getSession();
		session.removeAttribute("user");
		session.removeAttribute(session.getId());
		session.setMaxInactiveInterval(0);
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "r:login"; 
	}
	
	@Post("checkUserCode/{userCode}")
	public Reply checkUserCode(@Param("userCode")String userCode, Invocation inv) throws Exception {

		Employe user = employeeService.findEmployeByUserCode(userCode);
		String result = "yes";
		if(user == null){
			result = "no";
		}
		System.out.println(result);
		return Replys.with(result);
	}
	
	@Post("company/{userCode}")
	public Reply company(@Param("userCode")String userCode, Invocation inv) throws Exception {

		List<Company> companies = userPowerService.findPowerCompanyByuserCode(userCode);
		return Replys.with(companies).as(Json.class);
	}


}
