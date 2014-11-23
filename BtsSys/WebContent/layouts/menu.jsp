<%@page import="org.apache.commons.logging.impl.Log4JLogger"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>左侧菜单</title>
<link href="${ctx}/layouts/css/left.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/layouts/css/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.7.min.js"></script>
</head>

<body>
<div id="sidebar" class="sidebar">
	<div class="sid_title"><img src='${ctx}/layouts/image/all.gif' />功能导航</div>
  	<s:iterator id="itemNa" status="status" value="authorityList">
  		<div class="sid_title"><a onclick="showSubList(${id},'${ctx}/${url}')"><img src="${ctx}/layouts/image/all.gif"  alt="" />${name}</a></div>
  		<div id="subMenu${id}" style="display:none;" isRefresh="0" class="subMenu"></div>
	</s:iterator>
</div>
</body>
<script type="text/javascript">
   function showSubList(pid,purl){
   	  // if(purl.indexOf('#')<=0) window.top.main.location=purl;
	   //展开当前div
	   var divObj = document.getElementById("subMenu"+pid);
	   if(null==divObj.style.display || ''==divObj.style.display || 'none'==divObj.style.display){
	       //获取子菜单
	       var url = "${ctx}/adminjson/subMenu.action?pid="+pid;
	       jQuery.post(url,null,callBack);
		   divObj.style.display='block';
	   }else{
		   divObj.style.display='none';
	   }
	   //隐藏其他div
   }
   function callBack(data,data2){
	   try{
        var divObj = document.getElementById("subMenu"+data.pid);
        divObj.innerHTML='';
        var ulObj = document.createElement("ul");
        for(var i = 0;i < data.authorityList.length;i++){
            var authority = data.authorityList[i];
        	var liObj = document.createElement("li");
        	var aObj = document.createElement("a");
        	aObj.target='main';
        	aObj.href="${ctx}/"+authority.url;
        	aObj.innerHTML="<img src='${ctx}/layouts/image/tabs_rightarrow.png' />"+authority.name;
        	liObj.appendChild(aObj);
        	ulObj.appendChild(liObj);
        }
        divObj.appendChild(ulObj);
	   }catch(e){
         alert(e);
	   }
   }
   
   $(function(){
	//   var url = "${ctx}/adminjson/subMenu.action?pid="+0;
      // jQuery.post(url,null,function(data){
    	//   $.each(data.authorityList,function(){
    	//	   showSubList(this.id,'${ctx}/'+this.url)
    	 //  });
       //}); 
   });
</script>
 
</html>
