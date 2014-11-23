<%@ page language="java"  pageEncoding="gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>页面出错</title>
<style type="text/css">
<!--
body,html {
	margin: 0px;
	background: url(${ctx}/images/itsmbodybg.jpg) repeat-x top left;
	height: 100%;
	overflow: hidden;
}

img {
	border: none;
}

.bg {
	width: 800px;
	height: 600px;
	background-image: url(${ctx}/images/itsmwelcomebg.jpg);
	padding: 2px 4px 2px 4px;
	text-align: center;
	
}
-->
</style>
</head>

<body>
<div  align="center">
	<div class='bg' align="center"><h1>页面出错</h1></div>
	<s:property value="exception.message"/>
	<s:property value="exceptionStack"/>
</div>
</body>
</html>