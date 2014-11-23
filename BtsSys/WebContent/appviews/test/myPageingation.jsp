<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style type="text/css">
	.note{
	background:#afdcff;
	width:600px;
	height:50px;
	}
</style>
<link href="${ctx}/resources/myPagination/css/page.css" rel="stylesheet"
	type="text/css" />
<script src="${ctx}/resources/jquery/jquery-1.6.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/myPagination/js/jquery.myPagination5.0.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
	    $("#demo1").myPagination({
		  currPage: 1,
	      pageCount: 10,
	      pageSize: 5,
	      ajax: {
	              on: true,                        //开启状态
	              callback: function(data) {
	            	  $("#result").empty();
	            	  $.each(data.Rows, function(){ 
	            		  $("#result").append('<div class="note"><a>'+this.content+'</a></div>'); 
	            	  });
	              },
	              url: "${ctx}/comment/commentData.action",            //访问服务器地址
	              dataType: 'json',               //返回类型
	              param:{on:true,pagesize:10,arg1:'1',arg2:'2'}	//参数列表，其中  on 必须开启，page 参数必须存在，其他的都是自定义参数，如果是多条件查询，可以序列化表单，然后增加 page 参数
	            }
	    });
	});
	//自定义 回调函数
	function ajaxCallBack(data) {
		alert('111111111111');
	}
</script>
</head>
<body>
    <div id="result"></div>
	<div id="demo1"></div>
</body>
</html>