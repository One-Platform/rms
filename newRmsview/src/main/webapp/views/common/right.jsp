<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<script type="text/javascript">
	function showBottom(thisCheck){
		if($(thisCheck).attr("checked") != "checked" ) {
			//如果为false，那么代表该checkbox被取消，那么bottom中的tree也得删除
			var temId = $(thisCheck).attr("id");
			$(".right_div_bottom #" + temId + "_tree").parent().replaceWith("");
		} else {	
		}
		if($(".right_div_right input:checkbox:checked").length > 0) {
			$(".right_div_bottom").show();
		} else {
			$(".right_div_bottom").hide();
			return false;	
		}
		var treeId = "";
		$(".right_div_right input:checkbox:checked").each(function(){
			treeId = $(this).attr("id");
			if($(".right_div_bottom #" + treeId + "_tree").length > 0) {
			} else {
				var tem = $(this).parent().text();
				//把ID传给后台，返回一个tree的json数据
				$.ajax({
				   type: "POST",
				   url: "_json_data.json",
				   dataType: "json",
				   async: false,
				   data: treeId,
				   success: function(msg){
						$(".right_div_bottom .show_select")
							.append("<div class='_treeGener'><input type='checkbox' checked='checked' disabled='disabled' />"+ tem +"<br /><div id="+ treeId +"_tree></div></div>");			
					$("#"+treeId+"_tree").jstree({
						"themes" : {
							"theme" : "default",
							"dots" : false,
						}, 
						"json_data" : {
							"data" : msg
						},
						"plugins" : [ "themes", "json_data", "checkbox", "ui" ]
					});
					}
				});
			}
		});
	}
</script>
</head>

<body>
	<span>设置权限</span>
    <label><input type="button" value="关闭" /></label>
    <ul>
        <li>保单配送<input id="test_id_1" type="checkbox" onclick="showBottom(this);" /></li>
        <li>人员权限<input id="test_id_2" type="checkbox" onclick="showBottom(this);" /></li>
        <li>用户组管理<input id="test_id_3" type="checkbox" onclick="showBottom(this);" /></li>
        <li>角色管理<input id="test_id_4" type="checkbox" onclick="showBottom(this);" /></li>
        <li>客服专员配置<input id="test_id_5" type="checkbox" onclick="showBottom(this);" /></li>
        <li>客服专员发布<input id="test_id_6" type="checkbox" onclick="showBottom(this);" /></li>
    </ul>
</body>
</html>
