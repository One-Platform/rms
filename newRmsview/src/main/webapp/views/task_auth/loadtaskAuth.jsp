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
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.message.css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tree.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.mouseoutclick.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.message.js"></script>
<script type="text/javascript">
var comCode = "";
$(function(){
	$("#treeOne").jstree({
		"themes" : {
			"theme" : "default",
			"dots" : false,
		},
		"json_data" : {
		"ajax" : {
				"type":"post",
				"url" : "${ctx}/taskAuth/companyAll",
				"data" : function (n) { 
					return { id : n.attr ? n.attr("id") : 0 }; 
				}
			}
		},
		"plugins" : [ "themes", "json_data", "ui" ]
	}).bind("select_node.jstree", function(event,data){
		comCode=data.rslt.obj.attr("id");			
			$("#treeTow").jstree({ 
				"themes" : {
					"theme" : "default",
					"dots" : false,
				},
				"json_data" : {
					"ajax" : {
							"type":"post",
							"url" : "${ctx}/taskAuth/tasklist/"+comCode,
					},
				},
				"plugins" : [ "themes", "json_data", "checkbox", "ui" ]
			}).bind("loaded.jstree",function(){
				checkRms();
			});
	});
	fitHeight();
});

function checkRms(){
	$("#treeTow").find(".jstree-rms").each(function(){
		$("#treeTow").jstree("check_node", this);    
	});
};

function saveFunction(){
	var strId = "";
	
	$("#treeTow").find(".jstree-checked").each(function(){
		strId = strId + this.id +",";
	});
	
	$("#treeTow").find(".jstree-undetermined").each(function(){
		strId = strId + this.id +",";
	});

	$.ajax({  
        type:"post",  
        url:"${ctx}/taskAuth/taskId/"+strId+"/"+comCode,
        dataType:"html",
        success:function (data) {  
            if (data == "success") {  
            	msgSuccess("", "保存成功！",function(){
            		$("#treeTow").jstree({ 
        				"themes" : {
        					"theme" : "default",
        					"dots" : false,
        				},
        				"json_data" : {
        					"ajax" : {
        							"type":"post",
        							"url" : "${ctx}/taskAuth/tasklist/"+comCode,
        					},
        				},
        				"plugins" : [ "themes", "json_data", "checkbox", "ui" ]
        			}).bind("loaded.jstree",function(){
        				checkRms();
        			});
            	}); 
            	
            }
        },  
        error:function () {  
            alert("保存失败！！");  
        }
    });
};
function fitHeight(){
	var pageHeight = $(document).height() - 62;
	$("#treeOne").height(pageHeight);
	$("#treeTow").height(pageHeight);
};

</script>
</head>
	<body >
		<table border="0" cellspacing="0" cellpadding="0" class="authorize">
		  <tr>
		    <td width="269" valign="top">
		      <div class="title2"><b>机构列表</b></div>
		      <div id="treeOne" class="tree_view"></div>
		    </td>
		    <td width="30" valign="top">&nbsp;</td>
		    <td width="269" valign="top">
		        <div class="title2"><b>功能列表</b></div>
		        <div id="treeTow" class="tree_view"></div>
		    </td>
		    <td width="30" valign="top">&nbsp;</td>
		    <td valign="top">
		    	<input type="button" class="def_btn" value="保  存"  onclick="saveFunction();" />
		    </td>
		  </tr>
		</table>
	</body>
</html>
