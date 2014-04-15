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
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.message.css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tree.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.message.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.mouseoutclick.js"></script>
<script type="text/javascript">
$(function taskTree(){
	$("#treeOne").jstree({
		"themes" : {
			"dots" : false,
			"icons" : false
		},
		"json_data":{
			"ajax":{
				"type":"post",
				"url":"${ctx}/taskmenu/taskTree"
			}
		},
		"plugins":["themes","json_data","ui"]
	}).bind("select_node.jstree", function (event, data) {
		$(this).find('.select').removeClass("select");
		var li = data.rslt.obj;
		li.addClass("select");
		taskId = data.rslt.obj.attr("id");
		$.ajax({
			type:"post",
			url:"${ctx}/taskmenu/update/"+taskId,
			dataType:"json",
			success:function(data){
				$(".taskID").val(data.taskID);
				$(".name").val(data.name);
				$(".menuName").val(data.menuName);
				$(".menuURL").val(data.menuURL);
				$(".des").val(data.des);
				$(".isValidate").val(data.isValidate);
				$(".isAsMenu").val(data.isAsMenu);
				$(".flag").val(data.flag);
				if(data.parent != null){
					$(".parentID").val(data.parent.taskID);
					$(".parentName").val(data.parent.name);
				}else{
					$(".parentID").val("");
					$(".parentName").val("");
				}
				
			},
			error:function(){
				alert("失败！");
			}
		});
		showCenter(false);
		$("#saveBtn").val("修改");
		$("#selectParent").hide();
	})
	fitHeight();
	$("#treeTow").jstree({ 
		"themes" : {
			"dots" : false,
			"icons" : false
		},
		"json_data":{
			"ajax":{
				"type":"post",
				"url":"${ctx}/taskmenu/parentTask"
			}
		},
		"plugins":["themes","json_data","ui"]
	}).bind("select_node.jstree", function (event, data) {
		var taskId = data.rslt.obj.attr("id");
		var text = $.trim(data.rslt.obj.children("a").text());
		$("#parentName").val(text);
		$(".parentID").val(taskId);
		$("#rightBox").hide();
	});

/*	.bind("is_select", function(){
		alert($(this).attr("id"))
		$(this).find('.select').removeClass("select");
		var li = $(this).find('.jstree-clicked').parent();
		li.addClass("select");
	});*/
});
function fitHeight(){
	var pageHeight = $(document).height() - 62;
	$("#treeOne").height(pageHeight);
	$("#treeTow").height(pageHeight);
};
function showCenter(dis){
	var center = $("#centerInfo");
	if(center.is(":hidden")){
		$("#centerInfo").slideToggle("fast");
	};
	if(!dis){
		center.find("input:not(#saveBtn),select,textarea").attr("disabled","disabled").val("读取数据");
	}else{
		center.find("input:not(#saveBtn),select,textarea").removeAttr("disabled").val("");
	};
	$("#saveBtn").val("保存");
	$("#selectParent").show();
};
function hideCenter(){
	$("#centerInfo").slideUp("fast");
	$("#rightBox").slideUp("fast");
};
function evevtCheck(){
	var theBtn = $("#saveBtn");
	if(theBtn.val() == "修改"){
		theBtn.val("保存");
		$("#selectParent").show();
		$("#centerInfo").find("input:not(#saveBtn),select,textarea").removeAttr("disabled");
	}else{
		var parentId = $(".parentID").val();
		
		$.ajax({
			type:"post",
			url:"${ctx}/taskmenu/saveTask/"+parentId,
			data:{
				taskID:$(".taskID").val(),
				name:$(".name").val(),
				menuName:$(".menuName").val(),
				menuURL:$(".menuURL").val(),
				des:$(".des").val(),
				isValidate:$(".isValidate").val(),
				isAsMenu:$(".isAsMenu").val(),
				flag:$(".flag").val()
			},
			dataType:"json",
			success:function(d1,d2){
				msgSuccess("", "保存成功！",function(){
					
					window.parent.frames[0].location.reload();
					
				});
			},
			error:function(){
				alert("添加失败！");
			}
		}); 
	}
};
function showRight(){
	var right = $("#rightBox");
	right.slideToggle("fast");
};
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="269" valign="top">
	    <div class="left_content f_left">
	      	<div class="title2"><b>功能列表</b></div>
	      	<div id="treeOne" class="tree_view"></div>
	    </div>
    </td>
    <td width="532" valign="top"><div class="center_content f_left">
      <input class="add_btn" type="button" value="新建功能" onclick="showCenter(true)"/>
      <div class="fun_info" id="centerInfo">
        <div class="title2"><b><a href="javascript:;" onclick="hideCenter()" class="close"></a>功能信息</b></div>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="info_form" >
          <tr>
            <td width="97" align="right">功能ID：</td>
            <td><input type="text" disabled="disabled"  class="taskID"/></td>
            <td width="74" align="right">功能名称：</td>
            <td><input type="text" disabled="disabled"  class="name"/></td>
          </tr>
          <tr>
            <td align="right">菜单名称：</td>
            <td width="144"><input type="text" disabled="disabled"  class="menuName"/></td>
            <td align="right">父功能名称：</td>
            <td><input type="text" disabled="disabled" id="parentName" class="parentName"/></td>
          </tr>
          <tr>
            <td align="right">父功能ID：</td>
            <td><input type="text" disabled="disabled"  class="parentID"/></td>
            <td colspan="2" class="select_parent"><a href="javascript:;" id="selectParent" onclick="showRight()">选择父功能</a></td>
          </tr>
          <tr>
            <td align="right">菜单连接：</td>
            <td colspan="3"><input type="text" disabled="disabled" style="width:297px;" class="menuURL"/></td>
          </tr>
          <tr>
            <td align="right">描述：</td>
            <td colspan="3"><textarea name="textarea" class="des" cols="30" rows="4" disabled="disabled" style="width:297px;"></textarea></td>
          </tr>
          <tr>
            <td align="right">是否为菜单：</td>
            <td><select name="select" class="isAsMenu" disabled="disabled">
              <option value="0">否</option>
              <option value="1">是</option>
            </select></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="right">状态：</td>
            <td><select name="select3" class="isValidate" disabled="disabled">
              <option value="1">使用中</option>
              <option value="0">没有使用</option>
            </select></td>
            <td align="right">功能类型：</td>
            <td><select name="select3" class="flag"  disabled="disabled">
              <option value="">默认类型</option>
              <option value="*">所有可见类型</option>
            </select></td>
          </tr>
          <tr>
            <td colspan="4"><div  class="form_tool">
              <input type="button" id="saveBtn" value="保存" onclick="evevtCheck()" class="def_btn" />
            </div></td>
          </tr>
        </table>
      </div>
    </div></td>
    <td width="269" valign="top">
    <div class="left_content f_left right_content" id="rightBox">
      <div class="title2"><b>选择父级功能节点</b></div>
      <div id="treeTow" class="tree_view"></div>
    </div></td>
  </tr>
</table>
</body>
</html>
