<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>权限分配</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        $(function() {
            //toptoolbar
            $("#toptoolbar").ligerToolBar({
                items: [
                    { text: '分配权限', click: applyMenu , icon:'add'}
                ]
            });


            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display:'角色名称',name:'name',width : 300,align:'center'},
                    {display:'角色描述',name:'description',width : 600,align:'left'}
                ],
                rownumbers:true,
                showTitle :false,
                pageSize :50,
                pageSizeOptions :[50,100],
                url:'${ctx}/adminjson/roleData.action',
                checkbox :true,
                width:'100%',
                height :  '100%'
            });
            $("#pageloading").hide();

            //初始化控件
            $("#name").ligerTextBox({width : 200 });
        });

        //模板定制
        function applyMenu() {
            var rows = gridObj.getCheckedRows();
            var j = rows.length;
            if (j == 0) {
                $.ligerDialog.alert('请选择要分配角色!');
                return;
            } else if (j > 1) {
                $.ligerDialog.alert('请选择一条分配!');
                return;
            }
            var id;
            $(rows).each(function() {
                id = this.intId;
            });
            window.location.href = "${ctx}/admin/roleMenu.action?roleId="+id;
        }
        //查询
        function toSearch() {
            //处理地区
            var name = $("#name").val();

            gridObj.setOptions({
                newPage : 1
            });
            gridObj.setOptions({
                url : encodeURI("${ctx}/adminjson/roleData.action?name=" + name)
            });
            gridObj.loadData(); //加载数据
        }
    </script>
</head>
<body>
<div id="main">
    <!-- 标题
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">角色列表</p>
    </div>   -->
    <!-- 标题 end-->
    <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="tr_inquires">
                <td width="60px">
                    名称：
                </td>
                <td width="150px">
                    <input type="text" id="name"/>
                </td>
                <td align="left">
                    <input class="btn btn-info btn-small" type="button" onclick="toSearch()" value="查询"/>
                </td>
                <td>
                    &nbsp;
                </td>

            </tr>
        </table>
        <div id="toptoolbar"></div>
        <div id="maingrid"></div>
    </div>
</div>
</body>
</html>