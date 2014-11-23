<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<Script  Language="JavaScript">
<!--
   function setFramesWidth(srcObj)
    {
      if (srcObj.src.indexOf("none.gif")>0)
	     {
		   parent.document.getElementById("warp").cols="0,10,*";
	       srcObj.src="${ctx}/layouts/images/block.gif";
		   srcObj.width="7";
		   srcObj.alt="打开菜单";
           srcObj.height="50";
         }
      else
	     {
		   parent.document.getElementById("warp").cols="170,10,*";
	       srcObj.src="${ctx}/layouts/images/none.gif";
	       srcObj.alt="隐藏菜单";
		   srcObj.width="7";
           srcObj.height="50";
      	 }
    }
//-->
// style="width:10px;height:100%; background:url(../images/body_bg_top.gif) repeat-x top #e6f4fa; float:right;  padding-top:15px; 
</Script>
</head>
<body style="background:#fffff; margin:0; padding:0;">
<div style="background:url(${ctx}/layouts/images/body_bg_top.gif) repeat-x top #e6f4fa;height:100%;">
<div style="width:10px;height:2.8%;"></div>
<div>
<img onClick="setFramesWidth(this)" src="${ctx}/layouts/images/none.gif" alt="打开菜单" width="7" height="50" border="0" style="" />
</div>
</div>
</body>
</html>
