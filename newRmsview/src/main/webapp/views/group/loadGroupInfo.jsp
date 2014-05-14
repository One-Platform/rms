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
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tree.js"></script>
<script language="javascript" src="${ctx}/js/sinosoft.grid.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.mouseoutclick.js"></script>
<script type="text/javascript">
	$(function(){
		fitHeight();
		$("#updataGrid").Grid({
			type:"post",
			url : "${ctx}/group/findRoleByGroupId/"+"${groupId}",
			dataType: "json",
			height: 220,
			colums:[
				{id:'1',text:'角色名称',name:"appellation",width:'130',index:'1',align:'',color:''},
				{id:'2',text:'描述',name:"Status",width:'170',index:'1',align:'',color:''},
				{id:'3',text:'创建日期',name:"Version",width:'125',index:'1',align:'',color:''},
				{id:'4',text:'修改日期',name:"degrees",width:'125',index:'1',align:'',color:''}
			],
			rowNum:500,
			sorts:false,
			pager : false,
			number:false,
			multiselect: true
		});	 
	});
	function fitHeight(){
		var pageHeight = $(document).height() - 102;
		$("#treeOne").height(pageHeight);
		$("#treeTow").height(pageHeight);
	};
</script>
</head>

<body>
<table border="0" cellspacing="0" cellpadding="0" class="authorize">
  <tr>
    <td width="568" colspan="3" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="info_form add_user">
      <tr>
        <td align="right">用户组名称：</td>
        <td><input type="text" style="width:160px;" id="updataGroupName" value="${name}"/>
        	<input type="hidden" style="width:160px;" id="updataGroupId" value="${groupId}"/>
        </td>
        <%-- <td>类型：</td>
        <td>
        	<select name="select" id="updateGroupType">
           	 		<c:if test="${flag eq 'all'}" >
							 	<option value="default" >默认</option>
	                   			<option value="all" selected="selected">所有可见</option>
	          		</c:if>          		
               		<c:if test="${flag eq 'default'}">
               		 	<option value="default" selected="selected">默认</option>
               			<option value="all" >所有可见</option>
               		</c:if>     
                </select>
                </td> --%>
      </tr>
      <tr>
        <td align="right">用户组描述：</td>
        <td colspan="3"><textarea name="textarea" id="updataGroupDes" cols="30" rows="1" style="width:90%;">${des}</textarea></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="3" valign="top">
    	
    </td>
  </tr>
</table>
<div id="updataGrid"></div>
</body>
</html>
