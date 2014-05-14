<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>æéç®¡ç-åè½èåç®¡ç</title>
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.base.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.tree2.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/sinosoft.grid.css" />
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.tree.js"></script>
<script language="javascript" src="${ctx}/js/sinosoft.grid.js"></script>
<script type="text/javascript" src="${ctx}/js/sinosoft.mouseoutclick.js"></script>
<script type="text/javascript">
$(function(){
	
	$("#addGroupGrid").Grid({
		type : "get",
		url : "${ctx}/group/findRole",
		dataType: "json",
		height: 220,
		colums:[
			{id:'1',text:'角色名称',name:"appellation",index:'1',align:'',color:''},
			{id:'2',text:'角色编号',name:"Status",index:'1',align:'',color:''},
			{id:'3',text:'创建日期',name:"Version",index:'1',align:'',color:''},
			{id:'4',text:'修改日期',name:"degrees",index:'1',align:'',color:''}
		],
		rowNum:5,
		sorts:false,
		pager : true,
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
        <td><input type="text" style="width:160px;" id="addGroupName"/></td>
       <!--  <td>类型：</td>
        <td>
         <select name="select2"  id="addGroupType">
          <option>默认类型</option>
          <option>所有可见类型</option>
          </select>  
          </td> -->
      </tr>
      <tr>
        <td align="right">用户组描述：</td>
        <td colspan="3"><textarea name="textarea" cols="30" rows="1" style="width:90%;" id="addGroupDes"></textarea></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="3" valign="top">
    	<div id="addGroupGrid"></div>
    </td>
  </tr>
</table>
</body>
</html>
