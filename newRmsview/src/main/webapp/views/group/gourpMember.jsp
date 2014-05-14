<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限管理-功能菜单管理</title>
<link type="text/css" rel="stylesheet"
	href="${ctx}/css/sinosoft.base.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/css/sinosoft.tree2.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/css/sinosoft.grid.css" />
	<link type="text/css" rel="stylesheet"
	href="${ctx}/css/sinosoft.message.css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tree.js"></script>
<script language="javascript" src="${ctx}/js/sinosoft.grid.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.mouseoutclick.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.message.js"></script>
<script type="text/javascript">
	$(function() {
		memberGrid();
	});

	function memberGrid(){
		var groupId="${groupId}";
		$("#menberGrid").Grid({
			url : "${ctx}/group/findGourpMember/"+groupId,
			dataType : "json",
			height: 250,
			colums : [ {
				id : '1',
				text : '员工编号',
				name : "appellation",
					index : '1',
					width:'150',
				align : '',
				color : 'green'
			}, {
				id : '2',
				text : '员工姓名',
				name : "Status",
				index : '1',
				width:'150',
				align : '',
				color : ''
			},  {
				id : '2',
				text : '所属机构',
				name : "Status",
				index : '1',
				width:'100',
				align : '',
				color : ''
			},{
				id : '3',
				text : '',
				name : "Status",
				index : '1',
				width:'175',
				align : '',
				color : ''
			} ],
			rowNum : 500,
			sorts : false,
			pager : false,
			number : false,
			multiselect : false
		});
		
	}
	function delRow(obj){
		var row = $(obj).parents('tr');
		var userid=row.attr("id").split("_")[1];
		var groupId="${groupId}";
	  	$.ajax({
			url : "${ctx}/group/deleteGourpMember/"+groupId+"/"+userid,
			type : "post",
			success : function(isdelete){
				if(isdelete=="true"){
					row.remove();
					msgSuccess("", "删除成功！");
				}else{
					msgSuccess("", "无法删除该用户");
				}
			},
			error : function(){
				alert("操作失败！！");
			}
		});
	};
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
		
		$(".seach_btn").click(function(){
			var groupId="${groupId}";
			var userCode = $("#userCode").val();
			if(userCode == "请输入员工编号"){
				userCode = "";
			}
			$("#menberGrid").html("");
			var s=$("#btOne").val();
			if(s=="添加组成员"){
				$("#menberGrid").Grid({
					url : "${ctx}/group/findGourpMember/"+groupId+"/"+userCode,
					dataType: "json",
					height: 250,
					colums:[
						{id:'1',text:'员工编号',name:"appellation",width:'150',index:'1',align:'',color:''},
						{id:'2',text:'员工姓名',name:"Status",width:'150',index:'1',align:'',color:'green'},
						{id:'3',text:'所属机构',name:"degrees",index:'100',align:'',color:''},
						{id:'5',text:'操作',name:"degrees",width:'175',index:'1',align:'',color:''}
					],
					rowNum:10,
					sorts:false,
					pager : false,
					number:false,
					multiselect: false
				});
			}
			if(s=="确定"){
				$("#menberGrid").Grid({
					url : "${ctx}/group/finduser/"+groupId+"/"+userCode,
					dataType: "json",
					height: 250,
					colums:[
						{id:'1',text:'员工编号',name:"appellation",width:'190',index:'1',align:'',color:''},
						{id:'2',text:'员工姓名',name:"Status",width:'190',index:'1',align:'',color:'green'},
						{id:'5',text:'所属机构',name:"degrees",index:'173',align:'',color:''}
					],
					rowNum : 500,
					sorts : false,
					pager : false,
					number : false,
					multiselect : true
				});
			}	
			
			
		});
	
	});

	function buttonfun(){
		var s=$("#btOne").val();
		if(s=="添加组成员"){
			addGroupMember();
			$("#btOne").val("确定");
		}
		if(s=="确定"){
			saveGroupMember();
			$("#btOne").val("添加组成员");
		}	
	}
	
	function addGroupMember(){
		var groupId="${groupId}";
		$("#menberGrid").html("");
		$("#menberGrid").Grid({
			url : "${ctx}/group/finduser/"+groupId,
			dataType : "json",
			height: 250,
			colums : [ {
				id : '1',
				text : '员工编号',
				name : "appellation",
					index : '1',
					width:'190',
				align : '',
				color : 'green'
			}, {
				id : '2',
				text : '员工姓名',
				name : "Status",
				index : '1',
				width:'190',
				align : '',
				color : ''
			}, {
				id : '3',
				text : '所属机构',
				name : "Status",
				index : '1',
				width:'173',
				align : '',
				color : ''
			} ],
			rowNum : 500,
			sorts : false,
			pager : false,
			number : false,
			multiselect : true
		});
	}

	function saveGroupMember(){
		var useIds="";
		$("#menberGrid").find("[name='g_check']:checked").each(function(){
			useIds=useIds+$(this).parents("tr").attr("id").split("_")[1]+",";
		}); 
		$.ajax({
			url : "${ctx}/group/addGroupMember/"+"${groupId}"+"/"+useIds,
			type : "post",
			success : function(data){
				msgSuccess("", "保存成功！");
				$("#menberGrid").html("");
				memberGrid();
			},
			error : function(){
				alert("新增失败！！");
			}
		});
	}
 
	
</script>
</head>

<body>
	<table border="0" cellspacing="0" cellpadding="0" class="authorize">
		<tr>
			<td width="568" colspan="3" valign="top"><table width="100%"
					border="0" cellspacing="0" cellpadding="0"
					class="info_form add_user">
					<tr>
						<tr>
							<td width="40%" align="right"><input type="text"
								value="请输入员工编号" class="seach_text" id="userCode" />
								<input type="hidden" style="width:160px;" id="groupId" value="${groupId}"/>
							</td>
							<td>&nbsp;</td>
							<td><input type="button" value="" class="seach_btn" /></td>
						</tr>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td colspan="3" valign="top">
				
			</td>
		</tr>
	</table>
	<div id="menberGrid"></div>
	 <div class="form_tool add_peo">
		<input id="btOne" class="def_btn" type="button" onclick="buttonfun();" value="添加组成员">
	</div>
</body>
</html>
