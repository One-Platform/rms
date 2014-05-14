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
<script type="text/javascript" src="${ctx}/js/sinosoft.message.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.window.js"></script>
<script type="text/javascript">
$(function(){
	$("#grid").Grid({
		type:"post",
		url : "${ctx}/role/rolelist",
		dataType: "json",
		height: 240,
		colums:[
			{id:'1',text:'角色名称',name:"appellation",width:'120',index:'1',align:'',color:''},
			{id:'2',text:'角色描述',name:"Status",width:'90',index:'1',align:'',color:''},
			{id:'3',text:'创建日期',name:"Version",index:'1',align:'',color:''},
			{id:'4',text:'修改日期',name:"degrees",index:'1',align:'',color:''},
			{id:'5',text:'操作',name:"degrees",index:'1',align:'',color:''}
		],
		rowNum:10,
		sorts:false,
		pager : true,
		number:true,
		multiselect: false
	});	
	//$("#grid > table .dis").tips({type:"toolTip",tipPostion:"left"});
	var windowBox;
});

function search(){
	$("#grid").text("");
	var name = $("#roleName").val();
	$("#grid").Grid({
		type:"post",
		url : "${ctx}/role/rolelist/"+name,
		dataType: "json",
		height: 240,
		colums:[
			{id:'1',text:'用户组名称',name:"appellation",index:'1',align:'',color:''},
			{id:'2',text:'角色描述',name:"Status",index:'1',align:'',color:''},
			{id:'3',text:'创建日期',name:"Version",width:'120',index:'1',align:'',color:''},
			{id:'4',text:'修改日期',name:"degrees",width:'120',index:'1',align:'',color:''},
			{id:'5',text:'操作',name:"degrees",index:'1',align:'',color:''}
		],
		rowNum:10,
		sorts:false,
		pager : true,
		number:true,
		multiselect: false
	});	
}


function openUpdateWindow(th){
	var id=$(th).parent().parent().attr("id").split("_")[1];
	
	$("body").window({
		"id":"window1", 
		"url":"${ctx}/role/findRoleById/"+id,
		"title":"角色修改", 
		"content":"",
		"hasIFrame":true,
		"width":600, 
		"height":450, 
		"resizing":false,
		"diyButton":[{
			"id": "btOne",
				"btClass": "def_btn",
				"value": "保存",
			"btFun": function() {
				$obj = $(document.getElementById('window1_iframe').contentWindow.document);
				var roleid=$obj.find("table").eq(1).find("#updateRoleId").val();
				var name=$obj.find("table").eq(1).find("#updateRoleName").val();
				var des='';
				des=$obj.find("table").eq(1).find("#updateRoleDes").val();
				var roleType=$obj.find("table").eq(1).find("#updateRoleType").val();
				var taskid="";
				$obj.find("#updateTreeTow").find(".jstree-checked").each(function(){
					taskid=taskid+$(this).attr("id")+",";
				});
				$.ajax({
					url : "${ctx}/role/update/"+taskid,
					type : "post",
					data:{
						roleID:roleid,
						name:name,
						des:des,
						flag:roleType
					},
					success : function(data){
						if(data=="true"){
							msgSuccess("", "保存成功！");
							$("#window1").remove();
							$(".all_shadow").remove();
							$("#grid").text("");
							$("#grid").Grid({
								type:"post",
								url : "${ctx}/role/rolelist",
								dataType: "json",
								height: 240,
								colums:[
									{id:'1',text:'角色名称',name:"appellation",width:'120',index:'1',align:'',color:''},
									{id:'2',text:'角色描述',name:"Status",width:'90',index:'1',align:'',color:''},
									{id:'3',text:'创建日期',name:"Version",index:'1',align:'',color:''},
									{id:'4',text:'修改日期',name:"degrees",index:'1',align:'',color:''},
									{id:'5',text:'操作',name:"degrees",index:'1',align:'',color:''}
								],
								rowNum:10,
								sorts:false,
								pager : true,
								number:true,
								multiselect: false
							});	
						}else{
							msgSuccess("", "非该角色所属机构，无法修改");
						}
					},
					error : function(){
						alert("操作失败！！");
					}
				});
			}	
		}, 
			{
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
};

function openAddWindow(){
	$("body").window({
		"id":"window1", 
		"url":"${ctx}/role/prepareAddRole/",
		"title":"角色添加", 
		"content":"",
		"hasIFrame":true,
		"width":600, 
		"height":450, 
		"resizing":false,
		"diyButton":[{
			"id": "btOne",
			"btClass": "def_btn",
			"value": "保 存",
			"btFun": function() {
				$obj = $(document.getElementById('window1_iframe').contentWindow.document);
				var name=$obj.find("table").eq(1).find("#addRoleName").val();
				var des=$obj.find("table").eq(1).find("#addRoleDes").val();
				var roleType=$obj.find("table").eq(1).find("#addRoleType").val();
				var taskid="";
				$obj.find("#addTreeTow").find(".jstree-checked").each(function(){
					taskid=taskid+$(this).attr("id")+",";
				});
				
				$.ajax({
					url : "${ctx}/role/add/"+taskid,
					type : "post",
					data:{
						taskID:taskid,
						name:name,
						des:des,
						flag:roleType
					},
					success : function(data){
						msgSuccess("", "保存成功！");
						$("#window1").remove();
						$(".all_shadow").remove();
						$("#grid").text("");
						$("#grid").Grid({
							type:"post",
							url : "${ctx}/role/rolelist",
							dataType: "json",
							height: 240,
							colums:[
								{id:'1',text:'角色名称',name:"appellation",width:'120',index:'1',align:'',color:''},
								{id:'2',text:'角色描述',name:"Status",width:'90',index:'1',align:'',color:''},
								{id:'3',text:'创建日期',name:"Version",index:'1',align:'',color:''},
								{id:'4',text:'修改日期',name:"degrees",index:'1',align:'',color:''},
								{id:'5',text:'操作',name:"degrees",index:'1',align:'',color:''}
							],
							rowNum:10,
							sorts:false,
							pager : true,
							number:true,
							multiselect: false
						});	
						
					},
					error : function(){
						alert("新增失败！！");
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
function delRow(e){
	var row = $(e).parents('tr');
	
	$.ajax({
		url : "${ctx}/role/delete/"+row.attr("id").split("_")[1],
		type : "post",
		success : function(isdelete){
			if(isdelete=="true"){
				row.remove();
				msgSuccess("", "删除成功！");
			}else{
				msgSuccess("", "无法删除该角色");
			}
		},
		error : function(){
			alert("操作失败！！");
		}
	});  
	
	
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right">角色名称：</td>
    <td width="180"><input id="roleName" type="text" value="" class="seach_text" /></td>
    <td width="120"><input type="button" value=" " class="seach_btn" onclick="search();"/></td>
    <td>
    	<input type="button" class="new_btn" value="增 加" onclick="openAddWindow()" />
    </td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<div id="grid"></div>
</body>
</html>
