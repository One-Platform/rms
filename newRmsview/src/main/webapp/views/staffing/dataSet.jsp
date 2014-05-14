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
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tree.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.mouseoutclick.js"></script>
<script type="text/javascript">
$(function(){
	$("#sel_2").children().remove();
	$li = $("#treeOne").find("li");
	$("#treeOne").jstree({
		"themes" : {
			"dots" : false,
			"icons" : false
		},
		"json_data":{
			"ajax":{
				"url":"${ctx}/staffing/companise/${userCode}"
			}
		},
		"plugins":["themes","json_data","ui"]
	}).bind("open_node.jstree",function(e,data){
		var theId = $(this).find(".jstree-open");
		var thisId = data.rslt.obj.attr("id");
		theId.each(function(){
			var okId = $(this).attr("id");
			if(okId != thisId){
				$("#treeOne").jstree("close_node","#" + okId);
			};
		});
	}).bind("select_node.jstree",function(e,data){
		$com=data.rslt.obj;
		$a = $com.find("a");
//		$com.addClass("select");
		$a.addClass("select");
//		$a.parent("li").siblings().each(function(){
//			if($(this).hasClass("select")) {
//				$(this).removeClass("select");
//			}
//		});
		$a.parent("li").siblings().find("a").each(function(){
			if($(this).hasClass("select")) {
				$(this).removeClass("select");
			}
		});
		var comCode = $com.attr("id");
		$("#comCode").val($com.attr("id"));
		$("#comCName").text($com.find("a").text());
		$com.addClass("select");
		if($("#sel_2").html() != "" || $("#sel_1").html() != ""){
			$("#sel_1").html("");
			$("#sel_2").html("");
			$(".code_box").html("");
		}	

		//查询出没有赋参数的数据规则
		$.ajax({
			url : "${ctx}/staffing/rules/"+comCode+"/${userCode}",
			type : "post",
			success : function(data){

				var optionSel_2 = "";
				for(var i=0;i<data.length;i++){
					optionSel_2 = optionSel_2 +"<option id='"+data[i].dataRuleID+"'>"+data[i].rule+"</option>";

				};
				$("#sel_2").append($(optionSel_2));
				
				//查询出有参数的数据规则
				dataRuleParam(comCode);
			},
			error : function(){
				alert("操作失败！！");
			}
		});
	});
	$("#sel_2").find("option").live("dblclick", toLeftMove);
	$("#sel_1").find("option").live("dblclick", toRightMove);
	
});

function dataRuleParam(comCode){
	$.ajax({
		url : "${ctx}/staffing/ruleParam/"+comCode+"/${userCode}",
		type : "get",
		success : function(data){
			var optionSel_1 = "";
			var dataRuleIdStr = "";
			if(data != null)
				for(var i=0;i<data.length;i++){
					dataRuleIdStr = dataRuleIdStr + data[i].dataRuleID + ",";
					optionSel_1 = optionSel_1 +"<option id='"+data[i].dataRuleID+"'>"+data[i].rule+"</option>";
				};
			$("#sel_1").append($(optionSel_1));
			
			//显示参数
			if(dataRuleIdStr != ""){
				params(comCode,dataRuleIdStr);
			}
		},
		error : function(){
			alert("操作失败！！");
		}
	});
}

function params(comCode,dataRuleIdStr){
	$.ajax({
		url : "${ctx}/staffing/params/"+comCode+"/${userCode}/"+dataRuleIdStr,
		type : "get",
		success : function(data){
				/*for (var i=0;i<data.length;i++){
					temValP=  "<p id='p_"+data[i].dataRule.dataRuleID+"'>"+"数据规则"+data[i].dataRule.rule+"的设置";
					var param=""
					if(data[i].dataRuleParam!=null){
						param=	data[i].dataRuleParam;
					}
					temValP =temValP+"<label>参数：</label><input id='te_"+data[i].dataRule.dataRuleID+"' type='text' class='code_text' value='"+param+"' />"+"</p>";
					
					$(".code_box").append(temValP);
				};*/
				alert(data.length);
			for (var i=0;i<data.length;i++){
					var table="	<table id='table_"+data[i].dataRule.dataRuleID+"' width='100%'  style='border:#ccc 1px solid;'  >"
					table=	table+"<tr><td  width='250'><span>数据规则"+data[i].dataRule.rule+"的设置</span></td></tr>"	
					var param="";
					var tablename="";
					var columName="";
					if(data[i].dataRuleParam!=null){
						param=	data[i].dataRuleParam;
					}
					table=	table+"<tr><td><span>参数:</span></td><td><input  id='p_"+data[i].dataRule.dataRuleID+"' type='text' class='seach_text' value='"+param+"'/></td></tr>"
					if(data[i].dataRule.busDataInfos.length>0){
						var infos=data[i].dataRule.busDataInfos;
						for (var j=0;j<infos.length;j++){
							tablename=infos[j].busDataTable+","+tablename;
							columName=infos[j].busDataColumn+","+columName;
						}
					}
					tablename=tablename.substring(0,tablename.length - 1);
					columName=columName.substring(0,columName.length - 1);
					table=  table+"<tr><td><span>使用的数据规则表名:</span></td><td><input id='t_"+data[i].dataRule.dataRuleID+"' type='text'class='seach_text' value='"+tablename+"'/></td></tr>";
					table=  table+"<tr><td><span>使用的数据规则列名:</span></td><td><input id='c_"+data[i].dataRule.dataRuleID+"' type='text'class='seach_text' value='"+columName+"'/></td></tr>";
					$(".code_box").append(table);
				}
			
		},
		error : function(){
			alert("操作失败！！");
		}
	});
}
function toLeftMove() {
	var ops = $("#sel_2").children();
	ops.each(function(){
		if($(this).attr("selected")) {
			var thisId = $(this).attr("id");
			var rootText = $(this).text();
			$("#sel_1").append($(this));
			//var temValP = $("<p id='p_"+thisId+"'>"+rootText+"</p>");
			//var temValText = $("<input id='te_"+thisId+"' type='text' class='code_text' value='请输入参数' />"); 
			
			var table="	<table id='table_"+thisId+"' width='100%'  style='border:#ccc 1px solid;'  >"
			table=	table+"<tr><td width='250'><span>数据规则"+rootText+"的设置</span></td><td></td></tr>"	
			table=	table+"<tr><td><span>参数:</span></td><td><input  id='p_"+thisId+"' type='text' class='seach_text' value=''/></td></tr>"
			table=  table+"<tr><td><span>使用的数据规则表名:</span></td><td><input id='t_"+thisId+"' type='text'class='seach_text' value=''/></td></tr>";
			table=  table+"<tr><td><span>使用的数据规则列名:</span></td><td><input id='c_"+thisId+"' type='text'class='seach_text' value=''/></td></tr>";
			//$(".code_box").append(temValP).append(temValText);
			$(".code_box").append(table);
		}
	});

}

function toRightMove() {
	var ops = $("#sel_1").children();
	ops.each(function(){
		if($(this).attr("selected")) {
			var thisId = $(this).attr("id");
			$("#sel_2").append($(this));
			$("#table_"+thisId).remove();
			//$("#p_"+thisId).remove();
			//$("#te_"+thisId).remove();
		}
	});	
};
</script>
</head>

<body>
<input type="hidden" id="comCode" />
<div class="data_list">
    <div class="title2"><b><span>姓名：${name} 编号：${userCode}</span>数据设置</b></div>
    <div id="treeOne" class="tree_view f_left" style="height:700px;width: 288px;"></div>
    <div class="data_right f_left"	>
    	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td >
                <select id="sel_1" class="datas" multiple="multiple">
                </select>
            </td>
            <td class="data_cen">
            	<a href="javascript:;" class="a_left" onclick="toLeftMove();"></a>
                <a href="javascript:;" class="right a_right" onclick="toRightMove();"></a>
            </td>
            <td >
            	<select id="sel_2" multiple="multiple" class="datas">
                </select>
            </td>
          </tr>
        </table>
        <div class="code_box" style="margin-bottom:10px;height:446px">
        	<!--<table width="100%"  style='border:#ccc 1px solid;'  >
        		<tr><td><span>数据规则</span></td><td></td></tr>
        		<tr><td><span>参数:</span></td><td><input type='text' class='seach_text'/></td></tr>
        		<tr><td><span>使用的数据规则表名:</span></td><td><input type='text'class='seach_text'/></td></tr>
        	</table> 
        	-->
        </div>
    </div>
</div>
</body>
</html>
