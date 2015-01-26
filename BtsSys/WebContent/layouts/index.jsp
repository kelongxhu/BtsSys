<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>贵州基站健康档案管理系统</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/layouts/css/header.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/layouts/css/left.css" rel="stylesheet" type="text/css"/>


    <script type="text/javascript">

        var tab = null;
        var accordion = null;
        var tree = null;
        $(function ()
        {
            //布局
            $("#layout1").ligerLayout({
                leftWidth: 200,
                height: '100%',
                allowLeftResize: false,
                heightDiff:-34,
                space:4,
                onHeightChanged: f_heightChanged
            });

            var height = $(".l-layout-center").height();

            //Tab
            $("#framecenter").ligerTab({
                height:height,
                onAfterSelectTabItem: function(tabId){
                    if(tabId=="home")
                        tab.reload(tabId);
                },
                onBeforeRemoveTabItem: function(tabId){
//                    if(tabId == "topManagementTab")
                    <%--$.post('${ctx}/lifeCycleMg/removeNetwork');--%>
                }
            });

            //设置默认展开菜单
//            $("#accordion1 div").each(function(i){
//                if($(this).hasClass("l-scroll")){
//                    if('生命周期管理' == this.title){
//                        $(this).attr("lselected", true);
//                    }
//                }
//            });

            //面板
            $("#accordion1").ligerAccordion({ height: height - 24, speed: null });

            $(".l-link").hover(function ()
            {
                $(this).addClass("l-link-over");
            }, function ()
            {
                $(this).removeClass("l-link-over");
            });

            //分别加载每个模块下的子菜单
            var uls = $("#accordion1 ul");
            uls.each(function(i){
                var num =  Math.random();

                var treeCombox;
                //初始化地市
                //初始化树控件
                $.post("${ctx}/adminjson/loadMenuTree.action?pid=" + this.id+"&randomNum="+num, function(
                        ajaxData, status) {
                    var treeData=ajaxData.menuJson;
                    var ulId=ajaxData.pid;
//                    treeData = treeData.replace(/"children":\[\],/g, '');
                    treeData=eval('('+treeData+')');
                    $("#"+ulId).ligerTree({
                        data:treeData,
                        checkbox: false,
                        slide: false,
                        nodeWidth: 120,
                        textFieldName: 'name',
//                    attribute: ['text', 'url'],
                        onSelect: function (node)
                        {
                            if (!node.data.url || node.data.url == "/" || node.data.url == "#") return;
                            var tabid = $(node.target).attr("tabid");
                            if (!tabid)
                            {
                                tabid = new Date().getTime();
                                $(node.target).attr("tabid", tabid);
                            }
                            f_addTab(tabid, node.data.name, node.data.url);
                        }
                    });
                });
                tab = $("#framecenter").ligerGetTabManager();
                accordion = $("#accordion1").ligerGetAccordionManager();
                tree = $("#tree1").ligerGetTreeManager();
            });


        });
        function f_heightChanged(options)
        {
            if (tab)
                tab.addHeight(options.diff);
            if (accordion && options.middleHeight - 24 > 0)
                accordion.setHeight(options.middleHeight - 24);
        }
        function f_addTab(tabid,text, url)
        {
            var flag=tab.isTabItemExist(tabid);
            if(flag){
//                tab.reload(tabid);
                tab.removeTabItem(tabid);
            }
            tab.addTabItem({ tabid : tabid,text: text, url: '${ctx}/' + url});

        }
        function showHomeTab(){
            var tabId = 'home';
            if(tab.isTabItemExist(tabId))tab.selectTabItem(tabId);
            else f_addTab(tabId, '首页', '${ctx}/layouts/dashbord.jsp');
        }
    </script>

    <style type="text/css">
        body,html{height:100%;}
        body{ padding:0px; margin:0;overflow: hidden;}
        #nav{ float:right; z-index:3; margin-top: 30px;}
        .suv li{ background:url(${ctx}/images/top_sub_bg.jpg) repeat-x; font-weight:bold; float:right; width:90px; text-align:center; }
        .suv li a{ color:#1585c0; text-decoration:none;  height:23px; display:block; padding-top:10px; background:url(${ctx}/images/sub_right_line.jpg) right bottom no-repeat;}
        .suv li a:hover{ color:#006; }
        #sub_left{ background:url(${ctx}/images/sub_left_pic.png) no-repeat; height:33px; width:37px; }
    </style>
</head>
<body>
<div style="height:69px;background:#B3DFDA;padding:0px">
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
            </div>
        </div>
    </div>
</div>
<div id="layout1">
    <div position="left" title="系统功能菜单" id="accordion1">
        <c:forEach var="item" items="${authorityList}">
            <c:if test="${item.flag==0}">
                <div id="menu_${item.id}" title="${item.name}" class="l-scroll">
                    <ul id="${item.id}" style="margin-top:3px;"/>
                </div>
            </c:if>
        </c:forEach>
    </div>
    <div position="center" id="framecenter">
        <div tabid="home" title="首页" style="">
            <iframe frameborder="0" style="width:100%;height:100%;" src="${ctx}/layouts/dashbord.jsp"/>
        </div>
    </div>
</div>
<div id="Foot">贵州基站健康档案管理系统 版权所有</div>
</body>
</html>
