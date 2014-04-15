package com.sinosoft.one.rms.controllers.taskAuth;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.rms.User;
import com.sinosoft.one.rms.model.Company;
import com.sinosoft.one.rms.model.TaskAuth;
import com.sinosoft.one.rms.service.CompanyService;
import com.sinosoft.one.rms.service.TaskAuthService;
import com.sinosoft.one.uiutil.NodeEntity;
import com.sinosoft.one.uiutil.Render;
import com.sinosoft.one.uiutil.TreeRender;
import com.sinosoft.one.uiutil.Treeable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;

@Path
public class TaskAuthController {

	@Autowired
	private TaskAuthService taskAuthService;
	@Autowired
	private CompanyService companyService;
	
	@Post("companyAll")
	public Reply list(Invocation inv) throws Exception {

		User user = (User) inv.getRequest().getSession().getAttribute("user");
		String supercomCode=user.getLoginComCode();
		List<Company> showCompany=companyService.findAllNextComBySupper(supercomCode);
		Map<String, Company> filter = new HashMap<String, Company>();
		List<Company> topList = new ArrayList<Company>();
		for (Company company : showCompany) {
			if(company.getUpperComCode().toString().equals(supercomCode))
				topList.add(company);
			filter.put(company.getComCode(), company);
		}
		Treeable<NodeEntity> treeable=companyService.creatCompanyTreeAble(topList, filter);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (TreeRender) UIUtil.with(treeable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	//返回机构的功能，在页面功能已被授权给此机构，则打钩
	@Post("tasklist/{comCode}")
	public Reply list(@Param("comCode") String comCode, Invocation inv) throws Exception {		
		Treeable<NodeEntity> treeable = taskAuthService.treeAble(comCode);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		Render render = (TreeRender) UIUtil.with(treeable).as(UIType.Json);
		render.render(inv.getResponse());
		return null;
	}
	
	//保存机构的功能
	@Post("taskId/{strId}/{comCode}")
	public Reply result(@Param("strId") String strId,@Param("comCode") String comCode, Invocation inv) {
		
		TaskAuth taskAuth = new TaskAuth();
		//保存当前机构的功能
		taskAuthService.save(strId,comCode,taskAuth);
		inv.addModel("comCode", comCode);
		return Replys.with("success");
	}
	
	

	

}
