<%@page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
  	<head>
	    <title>登录超时</title>
		<meta name="currentPosition" content="信息提示">
		<script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.7.js"></script>
		<script type="text/javascript">
		  
		  var  time1=10;   
		  
		  cdown();
		  
		  function   cdown(){    
			  time1-=1;   
			  if(time1>0){
			  	$("#msg").html(time1+"秒后，自动跳转到登陆页……") ;        
			  }else{   
				  reLogin(); 
				  clearTimeout(tmid);     
			  }         
			  var   tmid=setTimeout('cdown()',1000);          
		  } 
		  
		  function reLogin() {
			self.top.location  = "${ctx}/" ;
		  }
		</script>
	</head>
	<body>
		<div class="mainTable">
			<div class="mainTitle">系统提示</div>
				<table width="100%" border="0" cellpadding="5" cellspacing="0" class="borderTable">
					<tr bgcolor="f6f6f6">
						<td colspan="6" align="center" class="tableBaseBg">
							<div id="msg"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center" style="padding:20px;font-size:14px;" class="borderBottom">
						<img src='${ctx}/layouts/images/stop.gif' /> 
						 用户登录会话过期，请 <a href="javascript:onclick=reLogin();" >重新登录!</a>
						</td>
					</tr>
				</table>
		</div>
	</body>
</html>