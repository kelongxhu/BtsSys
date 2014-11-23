<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>异常页面</title>
	    <style type="text/css">
<!--
.STYLE5 {
	font-size: 12px;
	color: #6666FF;
	margin-top:20px; 
}
.STYLE6 {
	color: #FF6600;
	font-size: 12;
}
.STYLE11 {color: #6666FF;margin-top:20px; font-size: 12px;}
.STYLE15 {font-size: large; font-weight: bold; color: #A80000; margin-top: 20px; }
-->
        </style>
</head>
	
	<body>
	<div class="mainTable" style="background-color:#E4EAEB">
			<div class="mainTitle">系统提示</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  <!--DWLayoutTable-->
			  <tr>
				<td width="30%" rowspan="4" align="center" valign="top" bgcolor="f6f6f6" style="padding:20px;font-size:14px; border-right:1px solid #ABD0E7;">&nbsp;
				<img src='${ctx}/images/error.jpg' /></td>
				<td width="70%" height="50" align="left" valign="middle" bgcolor="f6f6f6">&nbsp;<span class="STYLE15">抱歉您访问的功能出错了！</span></td>
			  </tr>
			  <tr>
				<td height="10%" align="left" valign="top" bgcolor="f6f6f6">&nbsp;<span class="STYLE5">您好！您访问的页面出现问题，请联系系统管理员解决问题。</span></td>
			  </tr>
			  <tr>
				<td height="10%" align="left" valign="top" bgcolor="f6f6f6">&nbsp;<span class="STYLE11">如果您是系统管理员，以下信息可能您对有所帮助：</span></td>
			  </tr>
			  <tr>
				<td height="10%" align="left" valign="top" bgcolor="f6f6f6">&nbsp;<span class="STYLE6">
			    【<s:property value="exception.message"/>】</span>				</td>
			  </tr>
	  </table>			 
	  <table width="100%" border="0" cellpadding="5" cellspacing="0" class="borderTable" >
			  <tr bgcolor="fff" class="tableBaseBg" style="border-top:1px solid #ABD0E7;">
				<td colspan="2" align="center" >&nbsp;
			       
				</td>
			  </tr>
			  <tr bgcolor="fff" class="tableBaseBg">
				<td colspan="2" align="center" >
			        <input type="button" name="Submit" onClick="JavaScript:history.back();" value="返回上一级" class="bottomStyle"  />
				</td>
			  </tr>
	  </table>

			      
			</div>
	</body>
</html>