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
<link type="text/css" rel="stylesheet" href="${ctx}/css/query.css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tree.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.mouseoutclick.js"></script>
<link href="${ctx}/css/sinosoft.grid.css" rel="stylesheet" type="text/css" />                
<script language="javascript" src="${ctx}/js/sinosoft.grid.js"></script>    

<script type="text/javascript">
$(function(){
	$("#sel_2").children().remove();
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
		var $temValOne = $("<option id='op_1'>财产保险公司北京分公司</option>");
		var $temValTwo = $("<option id='op_2'>财产保险公司山东分公司</option>");
		var $temValThree = $("<option id='op_3'>财产保险公司上海分公司</option>");
		var $temValFour = $("<option id='op_4'>财产保险公司深圳分公司</option>");
		$("#sel_2").append($temValOne).append($temValTwo).append($temValThree).append($temValFour);
		
	});
	$("#sel_2").find("option").live("dblclick", toLeftMove);
	$("#sel_1").find("option").live("dblclick", toRightMove);
});
function toLeftMove() {
	var ops = $("#sel_2").children();
	ops.each(function(){
		if($(this).attr("selected")) {
			var thisId = $(this).attr("id");
			var rootText = $(this).text();
			$("#sel_1").append($(this));
			var temValP = $("<p id='p_"+thisId+"'>"+rootText+"cc/read.php?tid=53766773424&_fp=4,文字数据.com/categroy/ux3724&_fp=4,文字数据.com/categroy/ux</p>");
			var temValText = $("<input id='te_"+thisId+"' type='text' class='code_text' value='请输入参数' />");
			$(".code_box").append(temValP).append(temValText);
		}
	});

}

function toRightMove() {
	var ops = $("#sel_1").children();
	ops.each(function(){
		if($(this).attr("selected")) {
			var thisId = $(this).attr("id");
			$("#sel_2").append($(this));
			$("#p_"+thisId).remove();
			$("#te_"+thisId).remove();
		}
	});	
};

function openQX() {
	$("body").window({
		"id":"window1", 
		"url":"permissionSettings.jsp",
		"title":"权限设置", 
		"hasIFrame":true,
		"content":"",
		"width":1080, 
		"height":480,
		"diyButton":[{
			"id": "btOne",
			"class": "def_btn",
			"value": "保 存",
			"btFun": function() {
				msgSuccess("", "保存成功！");
				$("#window1").remove();
				$(".all_shadow").remove();
				}	
			}, {
			"id": "btTwo",
			"class": "def_btn",
			"value": "取 消",
			"btFun": function() {
				$("#window1").remove();
				$(".all_shadow").remove();
				}	
			}
		]
	});
}

function openSJ() {
	$("body").window({
		"id":"window1", 
		"url":"dataquery.jsp",
		"title":"数据设置", 
		"hasIFrame":true,
		"content":"",
		"width":820, 
		"height":450,
		"diyButton":[{
			"id": "btOne",
			"class": "def_btn",
			"value": "保 存",
			"btFun": function() {
				msgSuccess("", "保存成功！");
				$("#window1").remove();
				$(".all_shadow").remove();
				}	
			}, {
			"id": "btTwo",
			"class": "def_btn",
			"value": "取 消",
			"btFun": function() {
				$("#window1").remove();
				$(".all_shadow").remove();
				}	
			}, {
			"id": "btTwo",
			"class": "def_btn",
			"value": "关 闭",
			"btFun": function() {
				$("#window1").remove();
				$(".all_shadow").remove();
				}	
			}
		]
	});	
}
</script>
<script type="text/javascript">
$(function(){
$("#grid").Grid({
    url : "${ctx}/group/groupid/${groupId}",  
    dataType: "json",
    height: '125',
    colums:[
        {id:'1',text:'用户代码',name:"appellation",width:'',index:'1',align:'center',color:'#666'},  
        {id:'2',text:'用户名',name:"Status",width:'',index:'1',align:'center',color:'#666'},  
        {id:'3',text:'操作',name:"Version",index:'1',align:'',color:'#666',align:'center'}   
    ],
    rowNum:5,
    rowList:[10,20,30],  
    pager : true,  
    number:true,
    multiselect: false  
});

$(".btn_query").click(function(){
	var userCode = $(".userCode").val();
	var userName = $(".userName").val();
	if(userCode == ""){
		userCode = "null";
	}
	if(userName == ""){
		userName = "null";
	}
	$("#grid").text("");
	$("#grid").Grid({
	    url : "${ctx}/group/searchUser/"+userCode+"/"+userName+"/${groupId}",
	    dataType: "json",  
	    height: '125',
	    colums:[
	        {id:'1',text:'用户代码',name:"appellation",width:'',index:'1',align:'center',color:'#666'},  
	        {id:'2',text:'用户名',name:"Status",width:'',index:'1',align:'center',color:'#666'},  
	        {id:'3',text:'操作',name:"Version",index:'1',align:'',color:'#666',align:'center'}   
	    ],
	    rowNum:3,  
	    rowList:[10,20,30],  
	    pager : true,  
	    number:true, 
	    multiselect: false  
	});
	
});



$("#grid_1").Grid({  
    url : "${ctx}/group/groupid/${groupId}",
    dataType: "json",  
    height: '125',
    colums:[  
        {id:'1',text:'用户代码',name:"appellation",width:'',index:'1',align:'center',color:'#666'},  
        {id:'2',text:'用户名',name:"Status",width:'',index:'1',align:'center',color:'#666'},  
        {id:'3',text:'操作',name:"Version",index:'1',align:'',color:'#666',align:'center'}   
    ],  
    rowNum:3,  
    rowList:[10,20,30],  
    pager : true,  
    number:true, 
    multiselect: false  
});

$(".query_img").bind("click" , function(){
									  if($(".query_box").css("visibility")== "hidden"){
										  $(".query_box").css("visibility","");
										  }
										  else{
											   $(".query_box").css("visibility","hidden");
											  }
									  });
});
function delRow(obj){
	var id = $(obj).parents('tr').attr('id');
	var userCode = id.substr(4);
	alert(userCode);
	alert("${groupId}");
	$.ajax({
        type:"post",
        url:"${ctx}/group/delete/"+userCode+"/"+"${groupId}",
        success:function (data) {
            if (data == "success") {
				var row = $(obj).parents('tr');
				row.remove();
            	msgSuccess("", "删除成功！"); 
            }else{
            	alert("删除失败！！"); 
            }
        },  
        error:function () {  
            alert("删除失败！！");  
        }  
    });

}
</script>
</head>

<body>
<div>
    <div class="query f_left">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
    	<tr>
        	<td width="258" class="query_tab1" valign="top">
            	<table border="0" cellpadding="0" cellspacing="0" width="100%">
                	<tr>
                    	<td class="query_bor">
                        	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="query_tab">
                            	<tr>
                                	<td width="34%" align="right" height="34px">用户代码&nbsp;&nbsp;</td>
                                    <td width="66%" height="34px"><input name="" type="text"  class="userCode"/></td>
                                </tr>
                                <tr>
                                	<td width="34%" align="right" height="34px"  style="letter-spacing:6px;">用户名</td>
                                    <td width="66%" height="34px"><input name="" type="text"  class="userName"/></td>
                                </tr>
                                 <tr>
                                	<td width="34%"></td>
                                    <td width="66%" height="40px"><input name="" type="button" class="btn_query" /></td>
                                </tr>
                            </table>	
                        </td>
                    </tr>
                  	<tr>
                    	<td><div id="grid"></div></td>
                  	</tr>  	
                    <tr>
                    	<td align="center" height="60px"><input name="" type="button" class="btn_return" value="返回" /></td>
                    </tr>
                </table>	
            </td>
            <td valign="top"><div class="query_img"><img src="${ctx}/images/system/qr_04.png" width="34" height="109" /></div></td>
            <td width="258"  valign="top">
            	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="query_box" style=" visibility:hidden;">
                	<tr>
                    	<td class="query_bor">
                        	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="query_tab">
                            	<tr>
                                	<td width="34%" align="right" height="34px">用户代码&nbsp;&nbsp;</td>
                                    <td width="66%" height="34px"><input name="" type="text"  class="txt"/></td>
                                </tr>
                                <tr>
                                	<td width="34%" align="right" height="34px"  style="letter-spacing:6px;">用户名</td>
                                    <td width="66%" height="34px"><input name="" type="text"  class="txt"/></td>
                                </tr>
                                 <tr>
                                	<td width="34%"></td>
                                    <td width="66%" height="40px"><input name="" type="button" class="btn_query" /></td>
                                </tr>
                            </table>	
                        </td>
                    </tr>
                    <tr>
                    	<td><div id="grid_1"></div></td>
                  	</tr>
                    <tr>
                    	<td align="center" height="60px"><input name="" type="button" class="btn_return" value="取消" /></td>
                    </tr>
                </table>	
            </td>
        </tr>
    </table> 
</div>
</div>
</body>
</html>
