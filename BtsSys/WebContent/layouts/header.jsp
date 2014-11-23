<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>顶部</title>
    <link href="${ctx}/layouts/css/header.css" rel="stylesheet"
          type="text/css"/>
    <link href="${ctx}/layouts/css/style.css" rel="stylesheet"
          type="text/css"/>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.6.min.js"></script>
</head>
<script type="text/javascript">
    function jumpPage(id, url) {
        parent.document.getElementById("warp").cols = "170,10,*";
        window.top.menu.location = "${ctx}/admin/menu.action?pid=" + id;
       // parent.main.location.href=' {ctx}/pages/main/main.html';
    }
    function junmpInbox() {
        parent.document.getElementById("warp").cols = "0,0,*";
        parent.main.location.href = '${ctx}/layouts/main.jsp';
    }

</script>
<body>
<div id="header">
    <div class="header_top">
        <div id="logo"> 
           <img class="png_bg" src="${ctx}/layouts/image/banner_left_2.gif" alt=""/>
        </div>
        <div id="logo_right">   
           <img class="png_bg" src="${ctx}/layouts/image/banner_right_2.gif" alt=""/>
        </div>
        <!-- 
        <div id="nav">
            <div class="clear"></div>
            <div>
                <div class="clear"></div>
            </div>
        </div>
         -->
        </div>
     </div>
</body>
</html>
