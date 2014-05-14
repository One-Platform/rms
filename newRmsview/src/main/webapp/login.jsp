<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${ctx}/css/sinosoft.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/sinosoft.layout.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script language="javascript" src="${ctx}/js/sinosoft.layout.js"></script>
<script type="text/javascript">
	$(function(){
		<!--layout控件-->
		$("body").layout({
			top:{topHeight:68,topHide:false,topSwitch:false},
			bottom:{bottomHeight:45,bottomHide:false,bottomSwitch:false}
		});
		alignTop();
		$(window).resize(alignTop);
			
	});
	function checkUserCode(){
		var userCode=document.forms[0].userCode.value;
		
		$.ajax({
			url : "${ctx}/login/checkUserCode/"+userCode,
			type : "post",
			success : function(result){
				if(result == "no"){
					var  $li = $("<li id='checkUserCode'>此用户不存在</li>");
					$("table").find("td").eq(0).append($li);
				}else{
					if($("#checkUserCode").length > 0){
						$("#checkUserCode").remove();
					}
				}
			}
			
		});
		option(userCode);
	}
	
	function selectCom(){
		var userCode=document.forms[0].userCode.value;
		var opt;
		option(userCode)
	}
	function getcomCode(){
		var userCode=document.forms[0].userCode.value;
		var opt;
		var obtlength=document.getElementById("comCode").options.length;
		if(obtlength<=1){
			option(userCode);
		}
	}

	function option(userCode){
		
		$.ajax({
			url : "${ctx}/login/company/"+userCode,
			type : "post",
			success : function(data){
				 opt = "<option>请选择机构</option>";
				
			 	$.each(data, function(i, item) {
					opt = opt+"<option value="+item.comCode+">"+item.comCName+"</option>";
				});
			 	$(".inp_selec").html(opt);  
			}
		});
		
	}

	
	function alignTop(){
		var marginTop = $("#layout_center").height()/2 - 180;
		$("#login").css('margin-top',marginTop);
	};
	function login(){
		var name = document.forms[0].userCode.value;
		var password = document.forms[0].passWord.value;
		var company = $(".inp_selec").val();
		if(name==""){
			alert("用户名不能为空！！");
			return ;
		}
		if(password==""){
			alert("密码不能为空！！");
			return ;
		}
		if(company == "" || company == null){
			alert("请选择机构");
			return;
		}
		 
		$("#userLogin").attr("action","${ctx}/login.jsp").submit();
		
	}

</script>
</head>

<body >
<!--head-->
<div id="layout_top">
	<div class="header">
    	<img src="${ctx}/images/system/login.gif" class="logo"  />
    </div>
</div>

<!--内容-->
<div id="layout_center" class="login_cont">
	<div class="login_line"></div>
	<div class="login" id="login">
		<form id="userLogin" method="post">
	    	<ul class="list" >
		    	<table>
		        	<tr>
		        		<td>
			        		<li>用户名 <input type="text" class="inp_text" name="userCode"  onblur="checkUserCode();"/></li>
		        		</td>
		        	</tr>
		        	<tr>
		        		<td>
		            		<li>密　码 <input type="password" class="inp_text" name="passWord" onblur="selectCom();"/></li>
		            	</td>
		        	</tr>
		        	<tr>
		        		<td>
				            <li>机　构 
				            <select name="comCode"  class="inp_selec" id="comCode" onclick="getcomCode();">
				            	<option value="">请选择机构</option>
				            </select>
				            </li>
				    	</td>
		        	</tr>
		    	</table>
	        </ul>
	        <input name="sysFlag" type="hidden"   value="RMS" />
        	<input type="button" class="login_btn"  onclick="login()"/>
		</form>
        <p class="prompt">请输入正确的信息进行登录，如果您还没有账号，请联系管理员。</p>
    </div>
</div>

<!--版权栏-->
<div id="layout_bottom">
	<p class="copyright">
    	<span class="author">技术支持 中科软科技 www.sinofoft.com.cn</span>
    	<span class="help">帮助</span> 
    </p>	
</div>
</body>
</html>
