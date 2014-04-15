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
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.tree.css" />
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
	loadGroup();
});
function loadGroup(){
	$("#grid").Grid({
		type:"post",
		url : "${ctx}/group/grouplist",
		dataType: "json",
		height: 240,
		colums:[
			{id:'1',text:'用户组名称',name:"appellation",index:'1',align:'',color:''},
			{id:'2',text:'描述',name:"Status",index:'1',align:'',color:''},
			{id:'3',text:'创建日期',name:"Version",width:'120',index:'1',align:'',color:''},
			{id:'4',text:'修改日期',name:"degrees",width:'120',index:'1',align:'',color:''},
			{id:'5',text:'操作',name:"degrees",index:'1',align:'',color:''}
		],
		rowNum:10,
		sorts:false,
		pager : true,
		number:true,
		multiselect: false,	
	});	
	//$("#grid > table .dis").tips({type:"toolTip",tipPostion:"left"});
	var windowBox;
}

function search(){
	$("#grid").text("");
	var name = "";
		name=	$(".seach_text").attr("value");
	$("#grid").Grid({
		type:"post",
		url : "${ctx}/group/grouplist/"+name,
		dataType: "json",
		height: 240,
		colums:[
			{id:'1',text:'用户组名称',name:"appellation",index:'1',align:'',color:''},
			{id:'2',text:'描述',name:"Status",index:'1',align:'',color:''},
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
function openUpdateWindow(obj){
	var id=$(obj).parent().parent().attr("id").split("_")[1];
	alert(id);
	$("body").window({
		"id":"window1", 
		"url":"${ctx}/group/findGroupById/"+id,
		"title":"用户组管理", 
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
		 					var groupid=$obj.find("table").eq(1).find("#updataGroupId").val();
		 					var name=$obj.find("table").eq(1).find("#updataGroupName").val();
		 					var des=$obj.find("table").eq(1).find("#updataGroupDes").val();
		 					var groupType=$obj.find("table").eq(1).find("#updateGroupType").val();
		 					var roleid="";
		 					$obj.find("#updataGrid").find("[name='g_check']:checked").each(function(){
		 						roleid=roleid+$(this).parents("tr").attr("id").split("_")[1]+",";
		 					});
		 					$.ajax({
		 						url : "${ctx}/group/update/"+groupid+"/"+name+"/"+groupType+"/"+roleid+"/"+des,
		 						type : "post",
		 						success : function(data){
		 							msgSuccess("", "保存成功！",function(){
		 								window.parent.frames[0].location.reload();
		 							});
		 							$("#window1").remove();
		 							$(".all_shadow").remove();
		 						},
		 						error : function(){
		 							alert("新增失败！！");
		 						}
		 					});
		 					$("#window1").remove();
		 					$(".all_shadow").remove();
		 					}	
		 				},{
			"id": "btTwo",
			"btClass": "def_btn",
			"value": "取 消",
			"btFun": function() {
				$("#window1").remove();
				$(".all_shadow").remove();
				}
			},{
			"id": "btTre",
			"btClass": "def_btn",
			"value": "管理组成员",
			"btFun": function() {
					$("body").window({
						"id":"myTest", 
						"url":"${ctx}/user_group/usergroup/groupId/"+groupId,
						"title":"管理组成员", 
						"content":"",
						"hasIFrame":true,
						"width":580,
						"height":470, 
						"resizing":false
					});
				}	
			}
		]
	});
};
function openAddWindow(){
	$("body").window({
		"id":"window1", 
		"url":"addGroup.jsp",
		"title":"用户组管理", 
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
				var name=$obj.find("table").eq(1).find("#addGroupName").val();
				var des=$obj.find("table").eq(1).find("#addGroupDes").val();
				var groupType=$obj.find("table").eq(1).find("#addGroupType").val();
				var roleid="";
				$obj.find("#addGroupGrid").find("[name='g_check']:checked").each(function(){
					roleid=roleid+$(this).parents("tr").attr("id").split("_")[1]+",";
				});
				$.ajax({
					url : "${ctx}/group/add/"+name+"/"+groupType+"/"+roleid+"/"+des,
					type : "post",
					success : function(data){
						msgSuccess("", "保存成功！");
						$("#window1").remove();
						$(".all_shadow").remove();
						loadGroup();
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
			},{
				"id": "btOne",
				"btClass": "def_btn",
				"value": "管理组成员",
				"btFun": function() {
						$("body").window({
							"id":"myTest", 
							"url":"${ctx}/user_group/usergroup/groupId/"+groupId,
							"title":"管理组成员", 
							"content":"",
							"hasIFrame":true,
							"width":580,
							"height":470, 
							"resizing":false
						});
					}	
				}
		]
	});
}

	function delRow(obj){
		var id = $(obj).parents('tr').attr('id');
		var groupId = id.substr(4);
		$.ajax({
	        type:"post",
	        url:"${ctx}/group/del/"+groupId,
	        dataType:"html",
	        success:function (data) {
	        	alert(data);
	            if (data == "success") {  
					var row = $(obj).parents('tr');
					row.remove();
	            	msgSuccess("", "删除成功！"); 
	            }else{
	            	alert("删除失败！"); 
	            }
	        },
	        error:function () {  
	            alert("删除失败！！！");  
	        }  
	    });
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right">用户组名称：</td>
    <td width="180"><input type="text" value="" class="seach_text" id="name"/></td>
    <td width="120"><input type="button" value=" " class="seach_btn" onclick="search();"/></td>
    <td>
    	<input type="button" class="new_btn" value="增 加" onclick="openAddWindow();" />
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
