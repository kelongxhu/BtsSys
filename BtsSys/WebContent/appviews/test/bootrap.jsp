<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="${ctx}/resources/bootstrap/2.1.1/css/bootstrap.css" rel="stylesheet" type="text/css">
<script src="${ctx}/resources/jquery/jquery-1.7.js" type="text/javascript"></script>
<script src="${ctx}/resources/bootstrap/2.1.1/js/bootstrap.js" type="text/javascript"></script>
</head>
<body>

   	<div class="btn-group">
          <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">动作 <span class="caret"></span></button>
          <ul class="dropdown-menu">
            <li><a href="#">动作</a></li>
            <li><a href="#">其他动作</a></li>
            <li><a href="#">其他</a></li>
            <li class="divider"></li>
            <li><a href="#">被间隔的链接</a></li>
          </ul>
	</div>
	
	<input type="text">

</body>
</html>