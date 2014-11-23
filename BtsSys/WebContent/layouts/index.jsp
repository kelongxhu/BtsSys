<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>贵州网优工作管理平台</title>
</head>

	<frameset cols="*" rows="64, *,28" id="frame_main" border="0" name="frame_warp"> 
		<frame src="${ctx}/layouts/header.jsp" scrolling="no" noresize name="header">
			<frameset cols="170, 5,*" name="warp" id="warp">
				<frame src="${ctx}/admin/menu.action?pid=0" name="menu" /> 
				<frame src="${ctx}/layouts/hide.jsp" name="menu1" style="border-left:solid 0px #164f83;" scrolling="No" noresize="noresize" id="menu1" /> 
				<frame src="${ctx}/appviews/baselibs/schoollib/schoolList.jsp" name="main" /> 
		    </frameset> 
		<frame src="${ctx}/layouts/footer.jsp"/>
	</frameset>
<noframes>
<body>
</body>
</noframes></html>
