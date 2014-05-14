<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限管理-功能菜单管理</title>
<link type="text/css" rel="stylesheet" href="../css/sinosoft.base.css" />
<link type="text/css" rel="stylesheet" href="../css/sinosoft.tree2.css" />
<link type="text/css" rel="stylesheet" href="../css/sinosoft.message.css" />
<script type="text/javascript" src="../js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="../js/sinosoft.tree.js"></script>
<script type="text/javascript" src="../js/sinosoft.message.js"></script>
<script type="text/javascript" src="../js/sinosoft.mouseoutclick.js"></script>
<script type="text/javascript">
function evevtCheck(){
		 msgSuccess("", "保存成功！");
};
</script>
</head>

<body>
<div class="fun_info" id="centerInfo" style="display:block">
  <div class="title2"><b>修改密码</b></div>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="info_form">
          <tr>
            <td width="97" align="right">员工编号：</td>
            <td><input type="text" disabled="disabled" /></td>
          </tr>
          <tr>
            <td align="right">原密码：</td>
            <td width="144"><input type="password" /></td>
          </tr>
          <tr>
            <td align="right">设置新密码：</td>
            <td><input type="password" /></td>
          </tr>
          <tr>
            <td align="right">确认新密码：</td>
            <td><input type="password" /></td>
          </tr>
          <tr>
            <td colspan="2"><div  class="form_tool">
              <input type="button" id="saveBtn" value="保存" onclick="evevtCheck()" class="def_btn" />
            </div></td>
          </tr>
        </table>
      </div>
</body>
</html>
