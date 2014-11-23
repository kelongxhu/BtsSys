<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>贵州基站健康档案管理系统</title>
    <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <link href="${ctx}/layouts/css/header.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/layouts/css/left.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/layouts/css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/easyUI/themes/default/easyui.css">

    <link href="${ctx}/resources/ligerUI/1.1.9/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/ligerUI/1.1.9/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <%----%>
    <script type="text/javascript" src="${ctx}/resources/easyUI/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
    <script src="${ctx}/resources/ligerUI/1.1.9/js/core/base.js" type="text/javascript"></script>
    <script src="${ctx}/resources/ligerUI/1.1.9/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="${ctx}/resources/ligerUI/1.1.9/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function() {
            var p = $('body').layout('panel', 'west').panel({
                onCollapse:function() {
                }
            });

            $(".menu li a").click(function () {
                $('ul li a:not(this)').removeClass("visit_menu");
                $(this).addClass("visit_menu");
            })

            //addTab("首页2","${ctx}/layouts/first.jsp");
        });

        function addTab(title, href) {
            var tt = $('#main-center');
            if (tt.tabs('exists', title)) {
//                var tab =tt.tabs('select', title);
                tt.tabs('close',title);
            }
                if (href) {
                    var content = '<iframe frameborder="0" scrolling="no"  src="' + href + '" style="width:100%;height:100%;"></iframe>';
                } else {
                    var content = '未实现';
                }
                tt.tabs('add', {
                    title:title,
                    closable:true,
                    content:content
                });
        }

        function modalWindow(url, width, height) {
            var sURL = url;
            var sFeatures = "dialogWidth:" + width + "px; dialogHeight:" + height + "px; "
                    + "help:no; scroll:yes; center:yes; status:no;resizable:yes";
            window.showModalDialog(sURL, window, sFeatures);
        }


        function openModalWindow(url, width, height) {
            var sFeatures = "toolbar=no, menubar=no, scrollbars=no, resizable=no, " + "location=no, status=no, titlebar=no, width=" + width + ", " + "height=" + height + ", top=100, left=100";
            window.open(url, "main1", sFeatures);

        }


    </script>
</head>
<body class="easyui-layout">
<div region="north" border="false" style="height:69px;background:#B3DFDA;padding:0px">
    <div id="header">
        <div class="header_top">
            <div id="logo">
                <!--<img class="png_bg" src="${ctx}/layouts/image/banner_left_2.gif" alt=""/>  -->
            </div>
            <div style="position: absolute;top:15px;right:5px;height:33px;">
                <ul>
                    <li style="color: #6D6D69;margin-left:10px;float: left;padding:3px 3px 3px 15px;;background-image: url('${ctx}/layouts/image/staff.png');background-repeat: no-repeat;">
                        ${user.name}&nbsp;[${user.departMent.name}]
                    </li>
                </ul>
                <ul style="margin-top: 30px;">

                    <li style="margin-left:10px;float: left;height:33px;padding:3px 3px 3px 15px;;background-image: url('${ctx}/layouts/image/nav_lock.png');background-repeat: no-repeat;">
                        <a href="#" id="updateUserPassword" style="text-decoration: none; color: #000000;outline: medium none;">首页</a>
                    </li>
                    <li style="margin-left:10px;float: left;height:33px;padding:3px 3px 3px 19px;;background-image: url('${ctx}/layouts/image/nav_exit.png');background-repeat: no-repeat;">
                        <a href="${ctx}/admin/loginOut.action" id="logout" style="text-decoration: none;color: #000000;outline: medium none;">注销</a>
                    </li>
                </ul>


            </div>
            <div style="position: absolute;top:5px;right:165px;">

                <%--<ul>--%>
                    <%--<li style="margin-left:42px;float: left;">--%>
                        <%--<a onclick="#" style="display: block;text-decoration: none;color: #000000;outline: medium none;" href="#">--%>
                        <%--<span style="display: block;text-align:center;padding:44px 0 0 0px;width:48px;background-image: url('${ctx}/layouts/image/home.png');background-repeat: no-repeat;">--%>
                            <%--首页--%>
                        <%--</span>--%>
                        <%--</a>--%>
                    <%--</li>--%>

                    <%--<li style="margin-left:42px;float: left;">--%>
                        <%--<a style="display: block;text-decoration: none;color: #000000;outline: medium none;" href="#">--%>
                        <%--<span style="display: block;text-align:center;padding:44px 0 0 0px;width:48px;background-image: url('${ctx}/layouts/image/support.png');background-repeat: no-repeat;">--%>
                            <%--资源中心--%>
                        <%--</span>--%>
                        <%--</a>--%>
                    <%--</li>--%>

                    <%--<li style="margin-left:42px;float: left;">--%>
                        <%--<a style="display: block;text-decoration: none;color: #000000;outline: medium none;" href="#">--%>
                        <%--<span style="display: block;text-align:center;padding:44px 0 0 0px;width:48px;background-image: url('${ctx}/layouts/image/chart_up.png');background-repeat: no-repeat;">--%>
                            <%--我的任务--%>
                        <%--</span>--%>
                        <%--</a>--%>
                    <%--</li>--%>

                    <%--<li style="margin-left:42px;float: left;">--%>
                        <%--<a style="display: block;text-decoration: none;color: #000000;outline: medium none;" href="#">--%>
                        <%--<span style="display: block;text-align:center;padding:44px 0 0 0px;width:48px;background-image: url('${ctx}/layouts/image/database.png');background-repeat: no-repeat;">--%>
                            <%--知识库--%>
                        <%--</span>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            </div>
            <%--<div id="logo_right">
                <!--    <img class="png_bg" src="${ctx}/layouts/image/banner_right_2.gif" alt=""/> -->
                <div id="nav">
                    <ul class="Shortcut">
                        <li><span style="color:blue;">欢迎您!${user.name}[${user.departMent.name}]</span></li>
                    </ul>
                    <div>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>--%>


        </div>
    </div>
</div>
<div region="west" split="true" title="导航菜单" style="width:190px;padding:1px;overflow:hidden;">
    <div class="easyui-accordion" fit="true" border="false">
        <c:forEach var="item" items="${authorityList}">
            <c:if test="${item.flag==0}">
                <div title="${item.name}" style="padding:0px;overflow:auto;">
                    <div class="menu">
                        <ul>
                            <c:forEach var="node" items="${item.children}">
                                <c:choose>
                                    <c:when test="${node.flag==0}">
                                        <li><a onclick="addTab('${node.name}','${ctx}/${node.url}')" href="#"><img
                                                src='${ctx}/layouts/image/tabs_rightarrow.png'/>${node.name}
                                        </a></li>
                                    </c:when>
                                    <%--<c:otherwise>--%>
                                    <%--<li><a onclick="modalWindow('${node.url}costAction_toIndex_index?u=${user.intId}','800','400');" href="#"><img--%>
                                    <%--src='${ctx}/layouts/image/tabs_rightarrow.png'/>${node.name}--%>
                                    <%--</a></li>--%>
                                    <%--</c:otherwise>--%>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
</div>
<div region="south" border="false" style="height:25px;background:#55B6F4;padding:5px;text-align:center;color: #fff;">
    贵州基站健康档案管理系统
</div>
<div region="center">
    <div id="main-center" class="easyui-tabs" fit="true" border="false">
        <div title="首页" style="">
            <iframe frameborder="0" style="width:100%;height:100%;" src="${ctx}/layouts/first.jsp">
        </div>
    </div>
</div>
</body>
</html>
