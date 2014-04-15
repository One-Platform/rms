<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户组新增</title>
	</head>
	<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.js"></script>
	<script type="text/javascript">
		function addGroup(){
			var id=$('#id').attr('value');
			var name=$('.name').attr('value');
			var des=$('.des').val();
			if(id ==''){
				alert("用户组ID不能为空！！");
				return;
			}
			if(name ==''){
				alert("用户组名称不能为空！！");
				return;
			}
			if(des ==''){
				alert("描述不能为空！！");
				return;
			}

			$.ajax({
				url : "${ctx}/user_group/group/insert",
				type : "post",
				data : {
					groupId : $('#id').val(),
					name : $('.name').val(),
					des : $('.des').val()
				},
				dataType : "json",
				success:function (data) {  
		           alert("新增成功！！");
		        },
		        error:function () {  
		            alert("新增失败！！");  
		        }
			});

			
		}
	
	</script>
	<body>
		<form>
			<table border="1">
				<tr>
					<td>用户组ID：</td>
					<td><input id="id"/> </td>
				</tr>
				<tr>
					<td>用户组名称：</td>
					<td><input class="name"/> </td>
				</tr>
				<tr>
					<td>描述：</td>
					<td><input class="des"/> </td>
				</tr>
			</table>
			<input type="button" value="提交" onclick="addGroup();">
		</form>
	
	
	</body>
</html>