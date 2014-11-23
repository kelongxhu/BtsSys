<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link rel="stylesheet" href="${ctx}/resources/ztree/css/demo.css" type="text/css">
    <link rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.excheck-3.5.js"></script>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">

        var treeObj = null;
        $(function () {
            var setting = {
                check: {
                    enable: true,
                    chkStyle: "checkbox",
                    chkboxType: { "Y": "ps", "N": "ps" }
                },
                <%--async: {--%>
                    <%--enable: true,--%>
                    <%--type: "get",--%>
                    <%--url: "${ctx}/adminjson/roleMenuTreeData.action",--%>
                    <%--autoParam: ["id", "treeLevel"]--%>
                <%--},--%>
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        var parms = {};
                    },
                    beforeClick: beforeClick,
                    onCheck: onCheck

//                    onAsyncSuccess: function (event, treeId, treeNode, msg) {
//                        // alert(msg);
//                    },
//                    onAsyncError: function (event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
//                        alert("异步加载失败!");
//                    }
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "parentId"
                    },
                    key: {
                        name: "text",
                        pId:"pid"
                    }
                }
            };

            //ztree初始化
            $.get("${ctx}/adminjson/roleMenuTreeData.action?roleId=${role.intId}", function (data) {
                var treeData=data.json;
                treeData = treeData.replace(/"children":\[\],/g, '');
                treeData=eval('('+treeData+')');
                treeObj = $.fn.zTree.init($("#treeDemo"), setting,treeData);
                treeObj.expandNode(treeObj.getNodeByParam("id", 0), true);
            })

//            treeObj = $.fn.zTree.init($("#tree1"), setting);
        });


        function beforeClick(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }

        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getCheckedNodes(true),
            v = "";
            var menuId=""
            for (var i = 0, l = nodes.length; i < l; i++) {
                v += nodes[i].text + ";";
                menuId+=nodes[i].id+";";
            }
            if (v.length > 0) v = v.substring(0, v.length - 1);
            if (menuId.length > 0) menuId = menuId.substring(0, menuId.length - 1);
            var cityObj = $("#citySel");
            cityObj.attr("value", v);
            var menuIdObj=$("#menuIds");
            menuIdObj.attr("value",menuId);
        }

        function showMenu() {
            var cityObj = $("#citySel");
            var cityOffset = $("#citySel").offset();
            $("#menuContent").css({left: cityOffset.left + "px", top: cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        }

        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }
        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                hideMenu();
            }
        }


        $(function () {
            var v = $("#form1")
                    .validate(
                    {
                        submitHandler: function (form) {
                            //form.submit();
                            jQuery(form).ajaxSubmit(function (json) {
                                if (json.result == 1) {
                                    alert('操作成功!');
                                } else {
                                    alert('操作失败!');
                                }
                                window.location.href = "${ctx}/admin/role.action";
                            });
                        }
                    });
        });


        function add() {
            $("#form1").submit();
        }

        //返回
        function back() {
            javascript: history.go(-1);
        }
    </script>
<body>


<div id="main_2">
    <!-- 标题 -->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">权限分配</p>
    </div>
    <!-- 标题 end-->
    <div class="content">

        <form class="form-horizontal" method="post" name="form1" id="form1" action="${ctx}/adminjson/menuApply.action">
            <div class="control-group" style="margin-top: 50px">
                <label class="control-label">角色名称:</label>

                <div class="controls">
                    ${role.name}
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">选择菜单:</label>

                <div class="controls">
                    <input id="citySel" type="text" readonly value="${menuText}" style="width:500px;" onclick="showMenu();" class="required"/>
                    <input id="menuIds" name="menuIds" type="hidden" value="${menuIds}">
                    <input id="roleId" name="roleId" type="hidden" value="${role.intId}">
                </div>
            </div>

        </form>
        <div class="form-actions_2" style="margin-top: 100px">
            <button id="demo-upload" class="btn btn-info" onclick="add();">
                <i class="icon-ok icon-white"></i>
                保存
            </button>
            <button id="a-save" class="btn btn-danger" type="reset" onclick="back();">
                <i class="icon-repeat"></i>
                返回
            </button>
        </div>


    </div>
</div>





<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:500px; height: 300px;"></ul>
</div>


</body>
</html>