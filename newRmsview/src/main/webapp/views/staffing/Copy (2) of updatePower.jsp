<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限管理-权限设置</title>
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.base.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.tree2.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.grid.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.window.css" /> 
 <link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.message.css" /> 
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tree.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.mouseoutclick.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.grid.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tips.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.window.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.message.js"></script>
<script type="text/javascript">
var temTreeId = "";
var temFlag = true;//该变量用来最下边的初始化的。
var comCode = "";
var groupId = "";
var groupIdStr = "";
var groupName = "";
var groupNameStr = "";
var roleId = "";
var roleIdStr = ",";
$(function(){
	$(".setup_box").hide();
	$(".clear").hide(); 
	$(".set_info").hide();
			
	$("#treeOne").jstree({ 
		"themes" : {
			"dots" : false,
		/* 	"theme" : "default", */
			"icons" : false 
		},
		"json_data":{
			"ajax":{
				"url":"${ctx}/staffing/companise/${userCode}"
			}
		},
		"plugins":["themes","json_data","ui"]
	}).bind("close_node.jstree", function(e, data) {
		var theId = $(this).find(".jstree-open");
		var thisId = data.rslt.obj.attr("id");
		if(thisId == temTreeId) {
			$(".setup_box").hide();
			$(".clear").hide(); 
			$(".set_info").hide();
		}
	}).bind("select_node.jstree", function(e, data){
		var n = 0;
		var temVal = "";
		comCode = data.rslt.obj.attr("id");
		comCName = data.rslt.obj.find("a").text();
		$(".set_info").attr("id" , comCode);
		$(".set_info").find("span").text(comCName);
		$.ajax({
			url : "${ctx}/staffing/groupList/"+comCode+"/${userCode}",
			type : "get",
			success : function(data){
				for(var i=0;i<data.length;i++){
					if(data[i].flag == "1"){
						if(n == 0){
							temVal = temVal + "<li onclick='ajaxMethodOne(this);' id='"+data[i].groupID+"' class = 'select'><a href='javascript:;'><span onclick='addSelect(this,event);' class = 'select'></span>"+data[i].name+"</a></li>";
							n++;
						}else{
							groupIdStr = groupIdStr + data[i].groupID +",";
							groupNameStr = groupNameStr + data[i].name +",";
							temVal = temVal + "<li onclick='ajaxMethodOne(this);' id='"+data[i].groupID+"'><a href='javascript:;'><span onclick='addSelect(this,event);' class = 'select'></span>"+data[i].name+"</a></li>";
						}
					}else{
						temVal = temVal + "<li onclick='ajaxMethodOne(this);' id='"+data[i].groupID+"'><a href='javascript:;'><span onclick='addSelect(this,event);'></span>"+data[i].name+"</a></li>";
					}
				};
				
				$First = $(temVal).find(".select").eq(0).parents("li");
				groupId = $First.attr("id");
				groupName = $First.text();
		        $(".setup_box").eq(0).children("ul").html(temVal);
				$(".setup_box").eq(0).show();
				if(groupId != null){
					showRoles(groupId,comCode);	
				}else{
					alert("无已被引入用户组！");
				}
				
			},
			error : function(){
				alert("操作失败！");
			}

		});
	});
	
	fitHeight();
	/* $("#treeTow").jstree({ 
		"themes" : {
			"dots" : false,
			//"icons" : false
			"theme" : "default"
		},
		"json_data":{
			"ajax":{
				"url":"${ctx}/views/common/tree.json"
			}
		},
		"plugins":["themes","json_data","ui"]
	}); */
	$(".tolge_show").bind("click", toggleDiv);
	
});

function showRoles(id,comCode){
	$.ajax({
		url : "${ctx}/staffing/roleList/"+id+"/"+comCode,
		type : "get",
		success : function(data){
			
			var temVal = "";
			if(data != null)
				for(var i=0;i<data.length;i++){
					roleIdStr = roleIdStr + data[i].roleID + ",";
					temVal = temVal + "<li onclick='addSelect(this); ajaxMethodTwo(this);' id='"+data[i].roleID+"'><a href='javascript:;'>"+data[i].name+"</a></li>";
				};
				
			if($("#hidden").find("#"+groupId+"Role").length == 1){
				$(".setup_box").eq(1).children("ul").html($("#"+groupId+"Role").html());
			}else{
				$div = $("<div id='"+groupId+"Role"+"'></div>");
				$div.html(temVal);
				$childObj = $("#hidden").find("div[id='"+groupId+"Role']");
				if($childObj.length == 1)
					$childObj.remove();
				$("#hidden").append($div);
				$(".setup_box").eq(1).children("ul").html(temVal);
			}
			$(".setup_box").eq(1).show();
			if($(".setup_box").eq(2).children("ul").html() != ""){
				$(".setup_box").eq(2).children("ul").html("");
			}
			
			showGroupBox(comCode,roleIdStr);

		},
		error : function(){
			alert("操作失败或用户组无角色！！");
		}

	});
}

function showGroupBox(comCode,roleIdStr){
	
	$.ajax({
		url : "${ctx}/staffing/taskShow/"+comCode+"/"+roleIdStr+"/${userCode}",
		type : "get",
		success : function(data){
			var temVal = "";
			for(var i=0;i<data.length;i++){
				if(data[i].flag == "1"){
					temVal = temVal + "<li id='"+data[i].taskID+"' onclick='addThreeSelect(this); ajaxMethodFour(this);'><a href='javascript:;'><span></span>"+data[i].name+"</a></li>";
				}else{
					temVal = temVal + "<li id='"+data[i].taskID+"' onclick='addThreeSelect(this); ajaxMethodFour(this);'><a href='javascript:;'><span class = 'select'></span>"+data[i].name+"</a></li>";
				}
				
			};
			
			if($("#hidden").find("#"+groupId+"Task").length == 1){
				$(".setup_box").eq(2).children("ul").html($("#hidden").find("#"+groupId+"Task").html());
			}else{
				$div = $("<div id='"+groupId+"Task"+"'></div>");
				$div.html(temVal);
				$childObj = $("#hidden").find("div[id='"+groupId+"Task']");
				if($childObj.length == 1)
					$childObj.remove();
				$("#hidden").append($div);
				
				$(".setup_box").eq(2).children("ul").html(temVal);
			}
			
			if($("#gbox_"+groupId).length != 0){
				$("#gbox_"+groupId).remove();
			}
			
			$(temVal).find(".select").parents("li").each(function(){
				ajaxMethodThree_2(this);
			});	
			
			var groupIds = groupIdStr.split(",");
			var groupNames= groupNameStr.split(",");
			if(groupIds.length > 0)
			for(var i=0;i<groupIds.length ;i++){
				if(groupIds[i].length>0){
					groupId = groupIds[i];
					groupName = groupNames[i];
					otherGroupBox(groupIds[i],comCode);
				}
			}
			$(".setup_box").eq(2).show();
			$(".clear").show(); 
			$(".set_info").show();	
		},
		error : function(){
			alert("操作失败！！");
		}

	});	
	
};

function otherGroupBox(groupId,comCode){
	$.ajax({
		url : "${ctx}/staffing/roleList/"+groupId+"/"+comCode,
		type : "get",
		success : function(data){
			
			var temVal = "";
			if(data != null)
				for(var i=0;i<data.length;i++){
					roleIdStr = roleIdStr + data[i].roleID + ",";
					temVal = temVal + "<li onclick='addSelect(this); ajaxMethodTwo(this);' id='"+data[i].roleID+"'><a href='javascript:;'>"+data[i].name+"</a></li>";
				};

			$.ajax({
				url : "${ctx}/staffing/taskShow/"+comCode+"/"+roleIdStr+"/${userCode}",
				type : "get",
				success : function(data){
					
					var temVal = "";
					for(var i=0;i<data.length;i++){
						if(data[i].flag == "1"){
							temVal = temVal + "<li id='"+data[i].taskID+"' onclick='addThreeSelect(this); ajaxMethodFour(this);'><a href='javascript:;'><span></span>"+data[i].name+"</a></li>";
						}else{
							temVal = temVal + "<li id='"+data[i].taskID+"' onclick='addThreeSelect(this); ajaxMethodFour(this);'><a href='javascript:;'><span class = 'select'></span>"+data[i].name+"</a></li>";
						}
						
					};
					
					if($("#hidden").find("#"+groupId+"Task").length == 1){
						$(".setup_box").eq(2).children("ul").html($("#hidden").find("#"+groupId+"Task").html());
					}else{
						
						$div = $("<div id='"+groupId+"Task"+"'></div>");
						$div.html(temVal);
						$childObj = $("#hidden").find("div[id='"+groupId+"Task']");
						if($childObj.length == 1)
							$childObj.remove();
						$("#hidden").append($div);
						
						$(".setup_box").eq(2).children("ul").html(temVal);
					}
					
					if($("#gbox_"+groupId).length != 0){
						$("#gbox_"+groupId).remove();
					}
					
					$(temVal).find(".select").parents("li").each(function(){
						ajaxMethodThree_2(this);
					});
				},
				error : function(){
					alert("操作失败！！");
				}
			});
		},
		error : function(){
			alert("操作失败！！");
		}

	});
}

function toggleDiv() {
	if($(".tolge_show").hasClass("up")) {
		$(".tolge_show").removeClass("up").addClass("down");
		$(".set_info").hide();
	} else {
		$(".tolge_show").removeClass("down").addClass("up");
		$(".set_info").show();
	}
}
function fitHeight(){
	var pageHeight = $(document).height() - 62;
	$("#treeOne").height(pageHeight);
};
function addSelect(obj,event) {

	if($(obj).hasClass("select")) {
		$(obj).removeClass("select");
		if($(obj).parents("li").hasClass("select")){	
			$(".setup_box").eq(2).find("ul").children().each(function(){
				if($(this).find("span").hasClass("select")){
					$(this).find("span").removeClass("select");
				}
			});
		}
		if($(obj).parents("ul").children().find(".select").length == 0){
			$(".setup_box").eq(1).hide();
			$(".setup_box").eq(2).hide();
		}
		var id = $(obj).parents("li").attr("id");
		$(".set_info").find("#gbox_"+id).remove();
		$(".setup_box").eq(0).find("ul").find("#"+id).removeClass("select");
		$("#"+id+"Task").remove();
		$("#"+id+"Role").remove();
		event.stopPropagation();
	} else {
		event.stopPropagation();
		
		$(obj).addClass("select");
		
		groupId = $(obj).parents("li").attr("id");
		//机构没有蓝条，直接选中，不显示角色和权限，但在下面的class='set_info'标签中显示全部权限
		if(!($(obj).parents("li").hasClass("select"))){
			groupName = $(obj).parents("li").find("a").text();
			var liId = $(obj).parents("li").attr("id");
			
			roleList(liId,comCode);
		}else{

			$(".setup_box").eq(2).find("ul").children().each(function(){
				if(!($(this).find("span").hasClass("select"))){
					$(this).find("span").addClass("select");
					 ajaxMethodFour(this);
				}
			});
		}
		
	}

}

function roleList(liId,comCode){
	
	$.ajax({
		url : "${ctx}/staffing/roleList/"+liId+"/"+comCode,
		type : "get",
		success : function(data){
			var temVal = "";
			if(data != null)
				for(var i=0;i<data.length;i++){
					roleIdStr = roleIdStr + data[i].roleID + ",";
					temVal = temVal + "<li onclick='addSelect(this); ajaxMethodTwo(this);' id='"+data[i].roleID+"'><a href='javascript:;'>"+data[i].name+"</a></li>";
				};
			$div = $("<div id='"+liId+"Role"+"'></div>");
			$div.html(temVal);
			$childObj = $("#hidden").find("div[id='"+liId+"Role']");
			if($childObj.length == 1)
				$childObj.remove();
			$("#hidden").append($div);
			
			$.ajax({
				url : "${ctx}/staffing/taskList/"+comCode+"/"+roleIdStr,
				type : "get",
				success : function(data){			
					var temVal = "";
					for(var i=0;i<data.length;i++){
						temVal = temVal + "<li id='"+data[i].taskID+"' onclick='addThreeSelect(this); ajaxMethodFour(this);'><a href='javascript:;'><span class='select'></span>"+data[i].name+"</a></li>";
					};

					$div = $("<div id='"+liId+"Task"+"'></div>");
					$div.html(temVal);
					
					$childObj = $("#hidden").find("div[id='"+liId+"Task']");
					if($childObj.length == 1){
						$childObj.find("span[class != 'select']").each(function(){
							var liObj = $(this).parents("li");
							$(this).addClass("select");
							ajaxMethodThree(liObj);
						});
						$childObj.remove();
					}else{
						$(temVal).each(function(){
							ajaxMethodThree(this);
						});	
					}

					$("#hidden").append($div);
					$(".clear").show(); 
					$(".set_info").show();	
				},
				error : function(){
					alert("操作失败！！");
				}

			});	
		},
		error : function(){
			alert("操作失败！！");
		}
	});
}

function addThreeSelect(thisLi) {
	if($(thisLi).find("span").hasClass("select")) {
		$(thisLi).find("span").removeClass("select");
	} else {
		$(thisLi).find("span").addClass("select");
	}
}
function ajaxMethodOne(thisLi) {
	groupId = thisLi.id;
	groupName = $(thisLi).find("a").text();
	$(thisLi).addClass("select");
	$(thisLi).siblings().each(function(){
		if($(this).hasClass("select")) {
			$(this).removeClass("select");
		}
	});
	if($(thisLi).hasClass("select")) {
		$.ajax({
			url : "${ctx}/staffing/roleList/"+thisLi.id+"/"+comCode,
			type : "get",
			success : function(data){
				roleIdStr = "";
				var temVal = "";
				if(data != null)
					for(var i=0;i<data.length;i++){
						roleIdStr = roleIdStr + data[i].roleID + ",";
						temVal = temVal + "<li onclick='addSelect(this); ajaxMethodTwo(this);' id='"+data[i].roleID+"'><a href='javascript:;'>"+data[i].name+"</a></li>";
					};
					
				if($("#hidden").find("#"+groupId+"Role").length == 1){
					$(".setup_box").eq(1).children("ul").html($("#"+groupId+"Role").html());
				}else{
					$div = $("<div id='"+groupId+"Role"+"'></div>");
					$div.html(temVal);
					$childObj = $("#hidden").find("div[id='"+groupId+"Role']");
					if($childObj.length == 1)
						$childObj.remove();
					$("#hidden").append($div);
					$(".setup_box").eq(1).children("ul").html(temVal);
				}
				$(".setup_box").eq(1).show();
				if($(".setup_box").eq(2).children("ul").html() != ""){
					$(".setup_box").eq(2).children("ul").html("");
				}
				if(roleIdStr != ""){
					taskList_2(comCode,roleIdStr);
				}else{
					alert("无角色！");
				}
			},
			error : function(){
				alert("操作失败！！");
			}

		});
	} else {
		var tem = 0;
		$(thisLi).siblings().each(function(){
			if($(this).hasClass("select") && $(this).find("span").hasClass("select")) {
				$(".setup_box").eq(1).hide();
				$(".setup_box").eq(2).hide();
				$(".clear").hide(); 
				$(".set_info").hide();
			}
		});
		
	}
}

function taskList_2(comCode,roleIdStr){

	$.ajax({
		url : "${ctx}/staffing/taskList/"+comCode+"/"+roleIdStr,
		type : "get",
		success : function(data){
			var temVal = "";
			for(var i=0;i<data.length;i++){
				temVal = temVal + "<li id='"+data[i].taskID+"' onclick='addThreeSelect(this); ajaxMethodFour(this);'><a href='javascript:;'><span></span>"+data[i].name+"</a></li>";
			};
			
			if($("#hidden").find("#"+groupId+"Task").length == 1){
				$(".setup_box").eq(2).children("ul").html($("#hidden").find("#"+groupId+"Task").html());
			}else{
				$(".setup_box").eq(2).children("ul").html(temVal);
			}

			$(".setup_box").eq(2).show();
			$(".clear").show(); 
			$(".set_info").show();	
		},
		error : function(){
			alert("操作失败！！");
		}

	});	
}

function ajaxMethodThree(thisLi) {
	
	if($(thisLi).find("span").hasClass("select")) {

		var thisId = $(thisLi).attr("id");
		var tipName = $(thisLi).find("a").text();
		
		$fLeft = $("<div class='f_left'></div>");
		$fLeft.append("<label class='set_name'>" + tipName + "</label>").append("<div id='"+thisId+groupId+"_f_left'></div>");

		if($("#gbox_"+groupId).length == 0){
			$groupBox = $("<div class='f_left' id = 'gbox_"+groupId+"' style='width:756px'></div>");
			$groupBox.append("<label class='set_name'>" + groupName + "</label>");
			$(".set_info").find(".set_box").append($groupBox);
			$groupBox.append($fLeft);
		}else{
			$("#gbox_"+groupId).append($fLeft);
		}
		
		$("#" +thisId+groupId+ "_f_left").jstree({ 
			"themes" : {
				"dots" : false,
			/* 	"icons" : false, */
				"theme" : "default"
			},
			"json_data":{
				"ajax":{
					"url":"${ctx}/staffing/taskChildren/"+comCode+"/"+roleIdStr+"/"+thisId+"/null"
				}
			},
			"plugins":["themes","json_data","checkbox","ui"]
		});
	
	} else {
		var thisId = $(thisLi).attr("id");
		$("#"+thisId+groupId +"_f_left").parent().remove();
		if($("#gbox_"+groupId).children().length == 1){
			$("#gbox_"+groupId).remove();
			$(".setup_box").eq(0).find("ul").find("#"+id).removeClass("select");
			$("#"+groupId+"Task").remove();
			$("#"+groupId+"Role").remove();
		}
		
	}
	
	if($(thisLi).parent("ul").length == 1){
		var htmlVal = $(thisLi).parent("ul").html();
		$div = $("<div id='"+groupId+"Task"+"'></div>");
		$div.html(htmlVal);
		$childObj = $("#hidden").find("div[id='"+groupId+"Task']");
		if($childObj.length == 1)
			$childObj.remove();
		$("#hidden").append($div);
	}
	
}

function ajaxMethodThree_2(thisLi){
	
	if($(thisLi).find("span").hasClass("select")) {

		var thisId = $(thisLi).attr("id");
		var tipName = $(thisLi).find("a").text();
		
		$fLeft = $("<div class='f_left'></div>");
		//$fLeft.append("<label class='set_name'><input name='' type='checkbox' value='' />" + tipName + "</label>").append("<div id='"+thisId+groupId+"_f_left'></div>");
		$fLeft.append("<label class='set_name'>" + tipName + "</label>").append("<div id='"+thisId+groupId+"_f_left'></div>");

		if($("#gbox_"+groupId).length == 0){
			$groupBox = $("<div class='f_left' id = 'gbox_"+groupId+"' style='width:756px'></div>");
			$groupBox.append("<label class='set_name'>" + groupName + "</label>");
			$(".set_info").find(".set_box").append($groupBox);
			$groupBox.append($fLeft);
		}else{
			$("#gbox_"+groupId).append($fLeft);
		}
		
		$("#" +thisId+groupId+ "_f_left").jstree({ 
			"themes" : {
				"dots" : false,
				//"icons" : false
				"theme" : "default"
			},
			"json_data":{
				"ajax":{
					"url":"${ctx}/staffing/taskChildren/"+comCode+"/"+roleIdStr+"/"+thisId+"/${userCode}"
				}
			},
			"plugins":["themes","json_data","checkbox","ui"]
		});
		
	}
}

function ajaxMethodFour(obj){

	var n1 = $(obj).parent().find(".select").length;
	var n2 = $(obj).parent().children().length;
	$obj = $(".setup_box").eq(0).children("ul").find("li[class='select']").find("span");
	if(n1 == n2){
		$obj.addClass("select");
	}

	groupId = $(".setup_box").eq(0).find("li[class='select']").attr("id");
	groupName = $(".setup_box").eq(0).find("li[class='select']").find("a").text();
	ajaxMethodThree(obj);
}
</script>
</head>

<body>
<input type="hidden" id="hidden"></input>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="279" valign="top">
        <div class="left_content f_left" style="width:249px;">
          <div class="title2"><b>姓名：${userName}  编号：${userCode}</b></div>
          <h4>已引入机构</h4>
          <div id="treeOne" class="tree_view">
		  </div>
        </div>
    </td>
	<td valign="top">
        <div class="setup_box f_left">
            <div class="set_title">
            	添加到用户组
            </div>
            <ul>
			</ul>
        </div>
        <div class="setup_box f_left">
            <div class="set_title set2">
            	角色
            </div>
            <ul>
			</ul>
        </div>
        <div class="setup_box f_left r_b">
            <div class="set_title set3">
            	设置权限
            </div>
            <ul>
            	
            </ul>
        </div>
        <div class="clear setw"><div class="tolge_show up"></div></div>
        <div class="set_info setw" id = ''>
        	<div class="title2"><b>该员工在<span></span>分公司权限设置预览</b></div>
            <div class="set_box" >
            </div>
        </div>
    </td>
	</tr>
</table>
</body>
</html>
