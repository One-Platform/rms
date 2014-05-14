<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE jsp PUBLIC "-//W3C//DTD Xjsp 1.0 Transitional//EN" "http://www.w3.org/TR/xjsp1/DTD/xjsp1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xjsp">
<head>

  <meta charset="utf-8">

  <title>accordion demo</title>

<link href="${ctx}/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
<script language="javascript" src="${ctx}/js/jquery-ui.custom.js"></script>

</head>

<body>

 

<div id="accordion">

  <h3>Section 1</h3>

  <div>

    <p>Mauris mauris ante, blandit et, ultrices a, suscipit eget.

    Integer ut neque. Vivamus nisi metus, molestie vel, gravida in,

    condimentum sit amet, nunc. Nam a nibh. Donec suscipit eros.

    Nam mi. Proin viverra leo ut odio.</p>

  </div>

  <h3>Section 2</h3>

  <div>

    <p>Sed non urna. Phasellus eu ligula. Vestibulum sit amet purus.

    Vivamus hendrerit, dolor aliquet laoreet, mauris turpis velit,

    faucibus interdum tellus libero ac justo.</p>

  </div>

  <h3>Section 3</h3>

  <div>

    <p>Nam enim risus, molestie et, porta ac, aliquam ac, risus.

    Quisque lobortis.Phasellus pellentesque purus in massa.</p>

    <ul>

      <li>List item one</li>

      <li>List item two</li>

      <li>List item three</li>

    </ul>

  </div>

</div>

 

<script>

$( "#accordion" ).accordion();

</script>

 

</body>

</html>

