<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="${ctx}/resources/jquery/jquery-1.7.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/raty/js/jquery.raty.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$('#star').raty({
		  path: '${ctx}/resources/raty/img/',
		  precision  : true,
		  size       : 30,
		  starOff    : 'star-off-big.png',
		  starOn     : 'star-on-big.png',
		  mouseover : function(score, evt) {
			    if (score != null) {
				    $('#target').html(score);
			    	$('#score').val(score);
			    }
			  }
	});	
});
</script>
</head>
<body>
<a>ddddddddd</a>
<div id="star"></div> 
<div id="target"></div>
<input type="text" id="score"> 
</body>
</html>