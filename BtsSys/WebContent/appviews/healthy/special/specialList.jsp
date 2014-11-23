<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>专项查询</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        $(function() {
            //toptoolbar
            $("#toptoolbar").ligerToolBar({
                items: [
                    { text: '配置', click: add , icon:'add'},
                    { text: '编辑', click: edit , icon:'archives'},
                    { text: '删除', click: del , icon:'archives'}
                ]
            });

            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display:'专项名称',name:'name',width : 420,align:'center'},
                    {display:'描述',name:'specdesc',width : 420,align:'center'}
                ],
                rownumbers:true,
                showTitle :false,
                pageSize :50,
                pageSizeOptions :[50,100],
                url:'${ctx}/healthyjson/specialCfgList.action',
                checkbox :true,
                width:'100%',
                height :  '100%',
                onDblClickRow:specialInfo
            });
            $("#pageloading").hide();

            //控件
            $("#name").ligerTextBox({width : 200 });
        });

        //模板定制
        function add() {
            window.location.href = "${ctx}/healthy/addPage.action";
        }

        //编辑页面
        function edit() {
            var rows = gridObj.getCheckedRows();
            var j = rows.length;
            if (j == 0) {
                $.ligerDialog.alert('请选择要编辑的数据！');
                return;
            } else if (j > 1) {
                $.ligerDialog.alert('请选择一条编辑！');
                return;
            }
            var id;
            $(rows).each(function() {
                id = this.id;
            });
            window.location.href = "${ctx}/healthy/addPage.action?id=" + id;
        }
        //删除操作
        function del() {
            var rows = gridObj.getCheckedRows();
            var str = "";
            $(rows).each(function() {
                str += this.id + ",";
            });
            if (str.length == 0) {
                $.ligerDialog.alert('请选择要删除的数据！');
                return;
            } else {
                str = str.substring(0, str.length - 1);
            }

            $.ligerDialog.confirm('确认删除', function (yes) {
                var params = {
                    ids : str
                };
                $.getJSON('${ctx}/healthyjson/delSpecial.action', params, function(json) {
                    if (json.result == 1) {
                        alert('删除成功!');
                    } else {
                        alert('删除失败！');
                    }
                    gridObj.loadData();
                });

            });
        }

        function specialInfo(data){
            window.location.href = "${ctx}/healthy/specialInfo.action?id=" + data.id;
        }


        //查询
        function toSearch() {
            //处理地区
            var name = $("#name").val();

            gridObj.setOptions({
                newPage : 1
            });
            gridObj.setOptions({
                url : encodeURI("${ctx}/healthyjson/specialCfgList.action?name=" + name)
            });
            gridObj.loadData(); //加载数据
        }
    </script>
</head>
<body>
<div id="main">
    <!-- 标题
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">导出模板定制</p>
    </div>   -->
    <!-- 标题 end-->
    <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="tr_inquires">
                <td width="60px">
                    专项名称：
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