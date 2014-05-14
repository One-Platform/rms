<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限管理-功能菜单管理</title>
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.base.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.tree2.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.grid.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.window.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.message.css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tree.js"></script>
<script language="javascript" src="${ctx}/js/sinosoft.grid.js"></script>
<script language="javascript" src="${ctx}/js/sinosoft.tips.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.mouseoutclick.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.window.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.message.js"></script>
<script type="text/javascript">
$(function(){
	$("#grid").Grid({
		url : "${ctx}/staffing/userAll",
		dataType: "json",
		height: 'auto',
		colums:[
			{id:'1',text:'员工编号',name:"appellation",width:'120',index:'1',align:'',color:''},
			{id:'2',text:'员工姓名',name:"Status",width:'90',index:'1',align:'',color:'green'},
			{id:'3',text:'归属机构',name:"Version",index:'1',align:'',color:''},
			{id:'4',text:'已引入机构',name:"degrees",index:'1',align:'',color:''},
			{id:'5',text:'操作',name:"degrees",index:'1',align:'',color:''}
		],
		rowNum:10,
		sorts:false,
		pager : true,
		number:false,
		multiselect: false
	});	

});
function openWindow(obj){
	var userCode = $(obj).parents("tr").children().eq(0).text();
	var userName = $(obj).parents("tr").children().eq(1).text();
	$("body").window({
		"id":"window1", 
		"url":"${ctx}/staffing/updatePower/"+userName+"/"+userCode,
		"title":"姓名："+ userName+"  编号："+userCode +"的权限信息", 
		"hasIFrame":true,
		"content":"",
		"width":1080,
		"height":450, 
		"resizing":false,
		"diyButton":[{
			"id": "btOne",
			"btClass": "def_btn",
			"value": "保 存",
			"btFun": function() {
				$obj = $(document.getElementById('window1_iframe').contentWindow.document);
				$company = $obj.find(".set_info");
				comCode = $company.attr("id");
				alert(comCode);
				var groupIdStr = "";
				$obj.find(".set_box").children().each(function(){
					var id = $(this).attr("id");
					groupIdStr = groupIdStr + id.substr(5) + ",";
					
				});
				var taskIdStr = "";
				$obj.find(".jstree-unchecked").each(function(){
					var id = $(this).attr("id");
					taskIdStr = taskIdStr + id +",";
				});
				if(taskIdStr == ""){
					taskIdStr = "null";
				}
			
				$.ajax({
					url : "${ctx}/staffing/savePower/"+comCode +"/"+userCode+"/"+groupIdStr+"/"+taskIdStr,
					success : function(data){
						alert("保存成功！");
						$("#window1").remove();
						$(".all_shadow").remove();	
					},
					error : function(){
						alert("操作失败！");
					}
				});

			} 
			}, {
				"id": "btTwo",
				"btClass": "def_btn",
				"value": "取 消",
				"btFun": function() {
					
					$("#window1").remove();
					$(".all_shadow").remove();
					}
				}
		]
	});
}
function openQX(obj) {
	var name = $(obj).parents("tr").children().eq(0).text();
	var userCode = $(obj).parents("tr").children().eq(1).text();
	$("body").window({
		"id":"window2",
		"url":"${ctx}/staffing/power/"+name+"/"+userCode,
		"title":"权限设置", 
		"hasIFrame":true,
		"content":"",
		"width":1080, 
		"height":480,
		"diyButton":[{
			"id": "btOne",
			"btClass": "def_btn",
			"value": "保 存",
			"btFun": function() {
				$obj = $(document.getElementById('window2_iframe').contentWindow.document);
				$company = $obj.find(".set_info");
				comCode = $company.attr("id");
				var groupIdStr = "";
				$company.find(".set_box").children().each(function(){
					var id = $(this).attr("id");
					groupIdStr = groupIdStr + id.substr(5) + ",";
					
				});
				var taskIdStr = "";
				$company.find(".jstree-unchecked").each(function(){
					var id = $(this).attr("id");
					taskIdStr = taskIdStr + id +",";
				});
				
				if(taskIdStr == ""){
					taskIdStr = "null";
				}

				$.ajax({
					url : "${ctx}/staffing/savePower/"+comCode +"/"+userCode+"/"+groupIdStr+"/"+taskIdStr,
					success : function(data){
						msgSuccess("", "保存成功！",function(){
							window.parent.frames[0].location.reload();
						});
						
					},
					error : function(){
						alert(231);
						alert("操作失败！");
					}
				});
				
			} 
			}, {
			"id": "btTwo",
			"btClass": "def_btn",
			"value": "取 消",
			"btFun": function() {
				
				$("#window2").remove();
				$(".all_shadow").remove();
				}
			}
		]
	});
}

function openSJ(obj) {
	var name = $(obj).parents("tr").find("td").eq(0).text();
	var userCode = $(obj).parents("tr").find("td").eq(1).text();
	
	$("body").window({
		"id":"window3",
		"url":"${ctx}/staffing/userinfo/"+name+"/"+userCode,
		"title":"数据设置",
		"hasIFrame":true,
		"content":"",
		"width":840,
		"height":450,
		"diyButton":[{
			"id": "btOne",
			"btClass": "def_btn",
			"value": "保 存",
			"btFun": function() {
				$obj = $(document.getElementById('window3_iframe').contentWindow.document);
				var comCode = $obj.find("#comCode").val();
				var ruleIdStr = "";
				var paramStr = "";
				var tablename="";
				var column = "";
				var tdata="";
				/* $busPower = $obj.find(".code_box").find("input");
				$busPower.each(function(){
					var id = $(this).attr("id");
					alert(id);
					ruleIdStr = id.substr(3)+ "," + ruleIdStr;
					var param = $(this).val();
					paramStr = param + "," + paramStr;
				}); */
				$busPower = $obj.find(".code_box").find("table");
				$busPower.each(function(){
					var id = $(this).attr("id");
					 id=id.split("_")[1];
					 alert(id);
					 paramStr=  $(this).find("#p_"+id).val();
					 tablename=  $(this).find("#t_"+id).val();
					 column=  $(this).find("#c_"+id).val();
					 tdata=tdata+id+":"+paramStr+"。"+tablename+"。"+column+"|"
				});
				alert(tdata);
					$.ajax({
						url : "${ctx}/staffing/saveBusPower/"+comCode+"/"+userCode+"/"+tdata,
						type : "post",
						dataType : "html",
						success : function(data){
							if(data == "success"){
								msgSuccess("", "保存成功！");	
								$("#window3").remove();
								$(".all_shadow").remove();
							}else{
								alert("保存失败！");
							}
						},
						error : function(){
							alert("保存失败！！");
						}
					});
			}
			}, {
			"id": "btTwo",
			"btClass": "def_btn",
			"value": "取 消",
			"btFun": function() {
				
				$("#window3").remove();
				$(".all_shadow").remove();
				}	
			}, {
			"id": "btTwo",
			"btClass": "def_btn",
			"value": "关 闭",
			"btFun": function() {
				
				$("#window3").remove();
				$(".all_shadow").remove();
				}	
			}
		]
	});	
}

$(function(){
	$("#userCode").focusin(function() {
		var value=$("#userCode").val();
        if(value=="请输入员工编号"){
			$("#userCode").val("");
		}
    });
	$("#userCode").focusout(function() {
		var value=$("#userCode").val();
        if(value == ""){
			$("#userCode").val("请输入员工编号");
		}
    });

	$("#comCode").focusin(function() {
		var value=$("#comCode").val();
        if(value=="请输入机构代码"){
			$("#comCode").val("");
		}
    });
	$("#comCode").focusout(function() {
		var value=$("#comCode").val();
        if(value == ""){
			$("#comCode").val("请输入机构代码");
		}
    });
	
	$(".seach_btn").click(function(){
		var userCode = $("#userCode").val();
		var comCode = $("#comCode").val();
		if(userCode == "请输入员工编号"){
			userCode = "null";
		}
		if(comCode == "请输入机构代码"){
			comCode = "null";
		}
		$("#grid").html("");
		$("#grid").Grid({
			url : "${ctx}/staffing/search/"+userCode+"/"+comCode,
			dataType: "json",
			height: 'auto',
			colums:[
				{id:'1',text:'员工编号',name:"appellation",width:'120',index:'1',align:'',color:''},
				{id:'2',text:'员工姓名',name:"Status",width:'90',index:'1',align:'',color:'green'},
				{id:'3',text:'归属机构',name:"Version",index:'1',align:'',color:''},
				{id:'4',text:'已引入机构',name:"degrees",index:'1',align:'',color:''},
				{id:'5',text:'操作',name:"degrees",index:'1',align:'',color:''}
			],
			rowNum:10,
			sorts:false,
			pager : true,
			number:false,
			multiselect: false
		});
		
	});
});
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="40%" align="right"><input type="text" value="请输入员工编号" class="seach_text"  id="userCode" /></td>
    <td>&nbsp;</td>
    <td><input type="text" value="请输入机构代码" class="seach_text"  id="comCode"/></td>
    <td>&nbsp;</td>
    <td><input type="button" value="" class="seach_btn"/></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td>&nbsp;</td>
    <td width="50">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<div id="grid"></div>
</body>
</html>
