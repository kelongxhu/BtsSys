<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page language="java" pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />

		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <link href="${ctx}/layouts/css/login.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.7.js"></script>
	    <style type="text/css"></style>

		
<script type="text/JavaScript">
	function remove() {
		document.getElementById("password").value = "";
		document.getElementById("password_2").value = "";
	}
	
	function pwdEdit(){
		var password = $("#password").val();
		var password_2 = $("#password_2").val();
		if(password==""){
			alert("请输入密码!");
			return;
		}
		if(password_2==""){
			alert("请输入确认密码!");
			return;
		}
		
		if(password!=password_2){
			alert("两次输入密码不一致!");
			return;
		}
		
		if(password.length<6){
			alert("密码不能小于6位");
			return;
		}  
		var paramObj = {
				password : password,
			};
			jQuery.getJSON('${ctx}/adminjson/pwdEdit.action', paramObj, function(
					json) {
				if (json.result== 1) {
					alert("修改成功!");
					 window.location.href="${ctx}//layouts/index.jsp";
				} else if(json.result== 0){
					alert('修改失败!');
				}
			});
		
	  }

		function enterLogin() {
			 if(event.keyCode==13) {
				 $("#btn").trigger("click");
			 }
		}



	$(function(){
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		var popupHeight = $("#login_wrap").height();
		var popupWidth = $("#login_wrap").width();
		//centering
		$("#login_wrap").css({
			"position": "absolute",
			"top": windowHeight/2-popupHeight/2,
			"left": windowWidth/2-popupWidth/2+200
		});
		$("#login_wrap").fadeIn("slow");
	});
</script>
	</head>
	<body id="login_body" onkeydown="enterLogin()">
		<div id="login_wrap">
			<div id="login_top" class="png_bg">
				<!--<img class="png_bg" src="${ctx}/layouts/images/login_logo.png" alt="" />     -->
               <span style=" margin-left:20px; font-size:16px;font-weight:bold; ">图书管理系统欢迎你,</span><span style="color:red">${user.userName}你好,第一次进入,需更改密码:谢谢!</span>
			</div>
			<div id="login_mid_center" class="png_bg">
			  <form id="loginForm" name="loginForm" method="post">
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th>
							密码:
						</th>
						<td>
							<input class="login_input_user" type="text" name="password"
								id="password" value="" />
						</td>
					</tr>
					<tr>
						<th>
							确认密码:
						</th>
						<td>
							<input class="login_input_pswd" type="password_2" name="password"
								id="password_2" value="" />
						</td>
					</tr>
					<tr id="login_table_tr">
						<td colspan="2">
							 <input name="btn" type="button" onclick="pwdEdit();" class="button"  onMouseOver="this.className='buttonover'"  onMouseOut="this.className='button'" value="确认" />
							 <input name="btn" type="button" onclick="remove();" class="button"  onMouseOver="this.className='buttonover'"  onMouseOut="this.className='button'" value="重置" />
						</td>
					</tr>
				</table>
		      </form>
			</div>
			<!--<div id="login_btm" class="png_bg"></div>-->
		</div>
	</body>
</html>
