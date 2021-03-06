<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>

    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${ctx}/layouts/css/login.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.7.js"></script>
    <style type="text/css">
    </style>


    <script type="text/JavaScript">
        function remove() {
            document.getElementById("password").value = "";
            document.getElementById("account").value = "";
            document.getElementById("validateCode").value = "";
        }
        $(function () {

            $("#btn").click(
                    function () {
                        var account = $("#account").val();
                        var password = $("#password").val();
                        var validateCode = $("#validateCode").val();
                        var loginForm = document.getElementById("loginForm");
                        if (account == "") {
                            alert("请输入用户名!");
                            return;
                        }
                        if (password == "") {
                            alert("请输入密码!!");
                            return;
                        }
                        if(validateCode==""){
                            alert("请输入验证码!");
                            return;
                        }
                        check(password, account, validateCode, loginForm);
                    });
        });

        function check(password, account, validateCode, frm) {
            var paramObj = {
                password: password,
                validateCode: validateCode,
                account: account
            };
            jQuery.getJSON('${ctx}/admin/checkPassword.action', paramObj, function (json) {
                if (json.isCheckPwd == 4) {
                    frm.action = "${ctx}/admin/login2.action";
                    frm.submit();
                } else if (json.isCheckPwd == 1) {
                    alert('密码不正确，请重新输入');
                    $("#password").val("")
                } else if (json.isCheckPwd == 5) {
                    alert('该用户不存在，请联系管理员');
                    $("#account").val("")
                    $("#password").val("")
                } else if (json.isCheckPwd == 6) {
                    alert('验证码不正确，请点击重新获取验证码输入');
                    var validateImg = document.getElementById("validateImg");
                    changeValidateCode(validateImg);
                    $("#validateCode").val("");
                } else if (json.isCheckPwd == 0) {
                    alert('登陆异常!');
                }
            });

        }
        function changeValidateCode(obj) {
            var timenow = new Date().getTime();
            obj.src = "admin/validateCode.action?d=" + timenow;
        }
        function MM_swapImgRestore() { //v3.0
            var i, x, a = document.MM_sr;
            for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
                x.src = x.oSrc;
        }

        function MM_preloadImages() { //v3.0
            var d = document;
            if (d.images) {
                if (!d.MM_p)
                    d.MM_p = new Array();
                var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
                for (i = 0; i < a.length; i++)
                    if (a[i].indexOf("#") != 0) {
                        d.MM_p[j] = new Image;
                        d.MM_p[j++].src = a[i];
                    }
            }
        }

        function MM_findObj(n, d) { //v4.01
            var p, i, x;
            if (!d)
                d = document;
            if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
                d = parent.frames[n.substring(p + 1)].document;
                n = n.substring(0, p);
            }
            if (!(x = d[n]) && d.all)
                x = d.all[n];
            for (i = 0; !x && i < d.forms.length; i++)
                x = d.forms[i][n];
            for (i = 0; !x && d.layers && i < d.layers.length; i++)
                x = MM_findObj(n, d.layers[i].document);
            if (!x && d.getElementById)
                x = d.getElementById(n);
            return x;
        }

        function MM_swapImage() { //v3.0
            var i, j = 0, x, a = MM_swapImage.arguments;
            document.MM_sr = new Array;
            for (i = 0; i < (a.length - 2); i += 3)
                if ((x = MM_findObj(a[i])) != null) {
                    document.MM_sr[j++] = x;
                    if (!x.oSrc)
                        x.oSrc = x.src;
                    x.src = a[i + 2];
                }
        }
        //-->

        function enterLogin() {
            if (event.keyCode == 13) {
                $("#btn").trigger("click");
            }
        }


        $(function () {
            var windowWidth = document.documentElement.clientWidth;
            var windowHeight = document.documentElement.clientHeight;
            var popupHeight = $("#login_wrap").height();
            var popupWidth = $("#login_wrap").width();
            //centering
            $("#login_wrap").css({
                "position": "absolute",
                "top": windowHeight / 2 - popupHeight / 2,
                "left": windowWidth / 2 - popupWidth / 2 + 200
            });
            $("#login_wrap").fadeIn("slow");
        });
    </script>
</head>
<body id="login_body" onkeydown="enterLogin()">
<div style="background: url(${ctx}/layouts/image/login_kuang2.png) no-repeat;width: 465px;height: 180px;margin: 300px auto 0 auto;position: relative">
    <div style="width: 740px;height: 65px; background: url(${ctx}/layouts/image/login_text2.png) no-repeat;position:absolute;top:-70px;left: 0">
    </div>
    <form id="loginForm" name="loginForm" method="post">
        <div style="position: absolute;left: 35px;top:45px;font-size:12px;color: #fff;width: 500px;">
            <div style="width:100%;margin-top:10px;">
                <label for="account" class="control-label" style="float: left;width:70px;line-height: 20px;font-weight: bold;">
                    用户名
                </label>

                <div>
                    <input type="text" name="account" style="width: 190px;"
                           id="account" value=""/>
                </div>
            </div>
            <div style="width:100%;margin-top:10px;">
                <label for="password" class="control-label" style="float: left;width:70px;line-height: 20px;font-weight: bold;">
                    密码
                </label>

                <div>
                    <input type="password" name="password" style="width: 190px;"
                           id="password" value=""/>
                </div>
            </div>
            <div style="width:100%;margin-top:10px;">
                <label for="validateCode" class="control-label" style="float: left;width:70px;line-height: 20px;font-weight: bold;">
                    验证码
                </label>

                <div>
                    <input name="validateCode" type="text" id="validateCode" size="6" style="width: 120px;"
                           maxlength="6"/>
                    <img src="${ctx}/admin/validateCode.action"
                         onclick="changeValidateCode(this)" id="validateImg"
                         style="cursor: pointer; height: 23px;width: 64px;" title="点击重新获取验证码"/>
                </div>
            </div>

        </div>
        <div style="position: absolute;right: 20px;top:60px;">
            <a href="#" id="btn"><img style="margin-right:30px;" src="${ctx}/layouts/image/login_btn2.png" alt=""/></a>
        </div>
    </form>
</div>
<%--<div id="login_wrap">
    <div id="login_top" class="png_bg">
        <!--<img class="png_bg" src="${ctx}/layouts/images/login_logo.png" alt="" />     -->
       <h2></h2>
    </div>
    <div id="login_mid_center" class="png_bg">
      <form id="loginForm" name="loginForm" method="post">
        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                    <td colspan="2"><span style="color:red;">${errMsg}</span></td>
                </tr>
            <tr>
                <th>
                    用户:
                </th>
                <td>
                    <input class="login_input_user" type="text" name="account"
                        id="account" value="" />
                </td>
            </tr>
            <tr>
                <th>
                    密码:
                </th>
                <td>
                    <input class="login_input_pswd" type="password" name="password"
                        id="password" value="" />
                </td>
            </tr>
            <tr>
                <th>
                    验证码:
                </th>
                <td>
                    <input name="validateCode" type="text" id="validateCode" size="6"
                        maxlength="6" />
                    <img src="${ctx}/admin/validateCode.action"
                        onclick="changeValidateCode(this)" id="validateImg"
                        style="cursor: pointer;" title="点击重新获取验证码" />
                </td>
            </tr>
            <tr id="login_table_tr">
                <td colspan="2"><a href="#" id="btn"><img style="margin-right:30px;" src="${ctx}/layouts/images/login_btn_dl.jpg" alt="" /></a>
                <a href="#" onclick="remove()"><img src="${ctx}/layouts/images/login_btn_qc.jpg" alt="" /></a></td>
            </tr>
        </table>
      </form>
    </div>
    <!--<div id="login_btm" class="png_bg"></div>-->
</div>--%>
</body>
</html>
