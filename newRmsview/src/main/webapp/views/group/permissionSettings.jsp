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
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.tree.css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tree.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.mouseoutclick.js"></script>
<script type="text/javascript">
var temTreeId = "";
var temFlag = true;//该变量用来最下边的初始化的。
$(function(){
	$(".setup_box").hide();
	$(".clear").hide(); 
	$(".set_info").hide();
	$("#treeOne").jstree({ 
		"themes" : {
			"dots" : false,
			"icons" : false
		},
		"json_data":{
			"ajax":{
				"url":"${ctx}/views/common/tree.json"
			}
		},
		"plugins":["themes","json_data","ui"]
	}).bind("open_node.jstree",function(e,data) {
		var theId = $(this).find(".jstree-open");
		var thisId = data.rslt.obj.attr("id");
		temTreeId = thisId;
		theId.each(function(){
			var okId = $(this).attr("id");
			if(okId != thisId){
				$("#treeOne").jstree("close_node","#" + okId);
			};
		});
	}).bind("close_node.jstree", function(e, data) {
		var theId = $(this).find(".jstree-open");
		var thisId = data.rslt.obj.attr("id");
		if(thisId == temTreeId) {
			$(".setup_box").hide();
			$(".clear").hide(); 
			$(".set_info").hide();
		}
	}).bind("select_node.jstree", function(e, data){
		var temValOne = "<li onclick='addSelect(this); ajaxMethodOne(this);'><a href='javascript:;'><span></span>权限管理</a></li>"	
		var temValTwo = "<li onclick='addSelect(this); ajaxMethodOne(this);'><a href='javascript:;'><span></span>客服专员组</a></li>"
        var temValThree = "<li onclick='addSelect(this); ajaxMethodOne(this);'><a href='javascript:;'><span></span>保单配送</a></li>"
		$(".setup_box").eq(0).children("ul").html(temValOne + temValTwo + temValThree);
		$(".setup_box").eq(0).show();
		$(".setup_box").eq(1).children("ul").children().remove();
		$(".setup_box").eq(1).hide();
		$(".setup_box").eq(2).children("ul").children().remove();
		$(".setup_box").eq(2).hide();
		$(".clear").hide(); 
		$(".set_info").hide();
	});
	
	fitHeight();
	$("#treeTow").jstree({ 
		"themes" : {
			"dots" : false,
			"icons" : false
		},
		"json_data":{
			"ajax":{
				"url":"${ctx}/views/common/tree.json"
			}
		},
		"plugins":["themes","json_data","ui"]
	});
	$(".tolge_show").bind("click", toggleDiv);
	
});
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
function addSelect(thisLi) {
	if($(thisLi).hasClass("select") || $(thisLi).find("span").hasClass("select")) {
		$(thisLi).removeClass("select");
		$(thisLi).find("span").removeClass("select");
		$(".set_info").find(".set_box").children().remove();
	} else {	
		$(thisLi).addClass("select");
		$(thisLi).find("span").addClass("select");
		$(".set_info").find(".set_box").children().remove();
		$(thisLi).siblings().each(function(){
			if($(this).hasClass("select")) {
				$(this).removeClass("select");
			}
		})
	}
	if(!$(thisLi).hasClass("select") && !$(thisLi).find("span").hasClass("select")) {
		if($(thisLi).parent().siblings().hasClass("set2")) {
			$(".setup_box").eq(2).children("ul").children().each(function(){
				$(this).find("span").removeClass("select");	
			});
			var booFlag = true;
			$(thisLi).siblings().each(function(){
				if($(this).find("span").hasClass("select")) {
					booFlag = false;
				}
			});
			if(booFlag == true) {
				$(".setup_box").eq(2).children("ul").children().remove();
				$(".setup_box").eq(2).hide();
				$(".clear").hide(); 
				$(".set_info").find(".set_box").children().remove();
				$(".set_info").hide();
			}
		} else {
			var booFlag = true;
			$(thisLi).siblings().each(function(){
				if($(this).find("span").hasClass("select")) {
					booFlag = false;
				}
			});
			if(booFlag == true) {
				$(".setup_box").eq(1).children("ul").children().remove();
				$(".setup_box").eq(1).hide();
				$(".setup_box").eq(2).children("ul").children().remove();
				$(".setup_box").eq(2).hide();
				$(".clear").hide(); 
				$(".set_info").find(".set_box").children().remove();
				$(".set_info").hide();
			}
		}
	}
	
}
function addThreeSelect(thisLi) {
	if($(thisLi).find("span").hasClass("select")) {
		$(thisLi).find("span").removeClass("select");
	} else {
		$(thisLi).find("span").addClass("select");
	}
}
function ajaxMethodOne(thisLi) {
	if($(thisLi).hasClass("select")) {
		var temValOne = "<li onclick='addSelect(this); ajaxMethodTwo(this);'><a href='javascript:;'><span></span>权限管理</a></li>"
		var temValTwo = "<li onclick='addSelect(this); ajaxMethodTwo(this);'><a href='javascript:;'><span></span>客服专员组</a></li>"
        var temValThree = "<li onclick='addSelect(this); ajaxMethodTwo(this);'><a href='javascript:;'><span></span>保单配送</a></li>"
		$(".setup_box").eq(1).children("ul").html(temValOne + temValTwo + temValThree);
		$(".setup_box").eq(1).show();
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
function ajaxMethodTwo(thisLi) {
	if($(thisLi).hasClass("select")) {
		var temValOne = "<li id='id_11' onclick='addThreeSelect(this); ajaxMethodThree(this);'><a href='javascript:;'><span></span>保单配送</a></li>";
        var temValTwo = "<li id='id_22' onclick='addThreeSelect(this); ajaxMethodThree(this);'><a href='javascript:;'><span></span>人员授权</a></li>";
        var temValThree = "<li id='id_33' onclick='addThreeSelect(this); ajaxMethodThree(this);'><a href='javascript:;'><span></span>用户组管理</a></li>";
        var temValFour = "<li id='id_44' onclick='addThreeSelect(this); ajaxMethodThree(this);'><a href='javascript:;'><span></span>角色管理</a></li>";
        var temValFive = "<li id='id_55' onclick='addThreeSelect(this); ajaxMethodThree(this);'><a href='javascript:;'><span></span>客服专员配置</a></li>";
        var temValSix = "<li id='id_66' onclick='addThreeSelect(this); ajaxMethodThree(this);'><a href='javascript:;'><span></span>客服专员发布</a></li>";
		$(".setup_box").eq(2).children("ul").html(temValOne + temValTwo + temValThree + temValFour + temValFive + temValSix);
		$(".setup_box").eq(2).show();
		$(".clear").show(); 
		$(".set_info").show();	
	} else {
		var tem = true;
		$(thisLi).siblings().each(function(){
			if($(this).hasClass("select")) {
				
			} else {
				
			}
		});
	}
}

function ajaxMethodThree(thisLi) {
	if($(thisLi).find("span").hasClass("select")) {
		var thisId = $(thisLi).attr("id");
		var tipName = $(thisLi).find("a").text();
		$fLeft = $("<div class='f_left'></div>");
		$fLeft.append("<label class='set_name'><input name='' type='checkbox' value='' />" + tipName + "</label>")
			.append("<div id='"+ thisId +"_f_left'></div>");
		$(".set_info").find(".set_box").append($fLeft);
			$("#" + thisId + "_f_left").jstree({ 
				"themes" : {
					"dots" : false,
					"icons" : false
				},
				"json_data":{
					"ajax":{
						"url":"tree.json"
					}
				},
				"plugins":["themes","json_data","ui"]
			});
	} else {
		var thisId = $(thisLi).attr("id");
		$("#"+thisId +"_f_left").parent().remove();
	}
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="279" valign="top">
        <div class="left_content f_left" style="width:249px;">
          <div class="title2"><b>姓名：张山  编号：10009999000</b></div>
          <h4>未引入机构</h4>
          <div id="treeOne" class="tree_view">
		  </div>
        </div>
    </td>
	<td valign="top">
        <div class="setup_box f_left">
            <div class="set_title">
            	设置用户组
            </div>
            <ul>
			</ul>
        </div>
        <div class="setup_box f_left">
            <div class="set_title set2">
            	设置角色
            </div>
            <ul>
			</ul>
        </div>
        <div class="setup_box f_left r_b">
            <div class="set_title set3">
            	设置权限
            </div>
            <ul>
            	<li onclick="addSelect(this);"><a href="javascript:;"><span></span>保单配送</a></li>
                <li onclick="addSelect(this);"><a href="javascript:;"><span></span>人员授权</a></li>
                <li onclick="addSelect(this);"><a href="javascript:;"><span></span>用户组管理</a></li>
                <li class="select" onclick="addSelect(this);"><a href="javascript:;"><span></span>角色管理</a></li>
                <li onclick="addSelect(this);"><a href="javascript:;"><span></span>客服专员配置</a></li>
                <li onclick="addSelect(this);"><a href="javascript:;"><span></span>客服专员发布</a></li>
            </ul>
        </div>
        <div class="clear setw"><div class="tolge_show up"></div></div>
        <div class="set_info setw">
        	<div class="title2"><b>该员工在<span>河北</span>分公司权限设置预览</b></div>
            <div class="set_box" >
            </div>
        </div>
    </td>
	</tr>
</table>
</body>
</html>
