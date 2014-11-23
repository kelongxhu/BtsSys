<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>模板查询</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        $(function() {
            //toptoolbar
            $("#toptoolbar").ligerToolBar({
                items: [
                    { text: '定制', click: define , icon:'add'},
                    { text: '编辑', click: edit , icon:'archives'}
                ]
            });

            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display:'模板名称',name:'name',width : 420,align:'center'},
                    {display:'数据类型',name:'type',width : 100,align:'center',
                        render: function (row) {
                            if (row.type == 1) {
                                return "物理站点";
                            } else if (row.type == 2) {
                                return "纯BBU";
                            } else if (row.type == 3) {
                                return "室分站点";
                            } else if (row.type == 4) {
                                return "小区";
                            }
                        }
                    },
                    {display:'模板类型',name:'useFlagStr',width : 100,align:'center'},
                    {display:'是否共享',name:'shareflag',width : 100,align:'center',
                        render: function (row) {
                            if (row.shareflag == 0) {
                                return "不共享";
                            } else if (row.shareflag == 1) {
                                return "共享";
                            }
                        }},
                    {display:'定制用户',name:'username',width : 100,align:'center',
                        render:function(row) {
                            if (row.user != null) {
                                return row.user.name;
                            }
                        }
                    }
                ],
                rownumbers:true,
                showTitle :false,
                pageSize :50,
                pageSizeOptions :[50,100],
                url:'${ctx}/businessjson/templateData.action',
                checkbox :true,
                width:'100%',
                height :  '100%'
            });
            $("#pageloading").hide();

            //数据类型
            var comBox1 = $("#typeIdVal").ligerComboBox({
                data: [
                    { text: '物理站点', id: '1' },
                    { text: '纯BBU', id: '2' },
                    { text: '室分', id: '3' },
                    { text: '小区', id: '4' }
                ],
                width : 200,
                selectBoxWidth: 200,
                valueFieldID: 'typeId'
            });

            //模板类型
            var comBox2 = $("#useFlagVal").ligerComboBox({
                data: [
                    { text: '导出模板', id: '0' },
                    { text: '统计模板', id: '1' }
                ],
                width: 200,
                selectBoxWidth: 200,
                valueFieldID: 'useFlag'
            });
        });

        //模板定制
        function define() {
            window.location.href = "${ctx}/business/templateDefine.action";
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
            window.location.href = "${ctx}/business/templateDefine.action?id=" + id;
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
                $.getJSON('${ctx}/schooljson/delSecneryLib.action', params, function(json) {
                    if (json.result == 1) {
                        alert('删除成功!');
                    } else {
                        alert('删除失败！');
                    }
                    gridObj.loadData();
                });

            });
        }


        //查询
        function toSearch() {
            //处理地区
            var typeId = $("#typeId").val();
            var useFlag=$("#useFlag").val();

            gridObj.setOptions({
                newPage : 1
            });
            gridObj.setOptions({
                url : encodeURI("${ctx}/businessjson/templateData.action?typeId=" + typeId+"&useFlag="+useFlag)
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
                    数据类型：
                </td>
                <td width="150px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
                </td>
                <td width="60px">
                    模板类型：
                </td>
                <td width="150px">
                    <input type="text" id="useFlagVal"/>
                    <input type="hidden" id="useFlag"/>
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