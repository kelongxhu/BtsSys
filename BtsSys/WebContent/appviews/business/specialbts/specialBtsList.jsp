<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>特殊站点查询</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        $(function() {

            var treeCombox;
            //初始化地市
            //初始化树控件
            $.post("${ctx}/schooljson/initCityTree.action", function (ajaxData, status) {
                var treeData = ajaxData.cityJson;
                treeData = treeData.replace(/"children":\[\],/g, '');
                treeData = eval('(' + treeData + ')');
                treeCombox = $("#cityId").ligerComboBox({
                    width: 150,
                    selectBoxWidth: 180,
                    selectBoxHeight: 200,
                    textField: 'text',
                    valueField: 'id',
                    valueFieldID: 'cityIdVal',
                    treeLeafOnly: false,
                    tree: {
                        data: treeData
                    }
                });
            });

            $("#toptoolbar").ligerToolBar({
                items: [
                    { text: '导出', click: exportSpecial, icon:'add'}
                ]
            });


            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display:'本地网',name:'city',width : 100,align:'center',
                    render:function(row){
                        if(row.city){
                            return row.city.cityName;
                        }
                    }},
                    {display:'名称',name:'name',width : 200,align:'center'},
                    {display:'网络类型',name:'netTypeStr',width : 80,align:'center'},
                    {display:'所属BSC',name:'bscName',width : 120,align:'center'},
                    {display:'特殊状态',name:'stateStr',width : 120,align:'center'},
                    {display:'类型',name:'typeStr',width : 120,align:'center'},
                    {display:'发现时间',name:'intimeStr',width : 120,align:'center'}
                ],
                rownumbers:true,
                showTitle :false,
                pageSize :50,
                pageSizeOptions :[50,100],
                url:'${ctx}/businessjson/specialeData.action',
                checkbox :true,
                width:'99.9%',
                height :  '99%'
            });
            $("#pageloading").hide();

            //数据类型
            var comBox1 = $("#typeIdVal").ligerComboBox({
                data: [
                    { text: '小区', id: '1' },
                    { text: '基站', id: '2' }
                ],
                width : 200,
                selectBoxWidth: 200,
                valueFieldID: 'typeId'
            });

            var comBox2 = $("#stateVal").ligerComboBox({
                data: [
                    { text: '新建未验收站', id: '1' },
                    { text: '调测站', id: '2' },
                    { text: '升级站', id: '3' },
                ],
                width : 200,
                selectBoxWidth: 200,
                valueFieldID: 'state'
            });
        });


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
            var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
            var typeId = $("#typeId").val();
            var state=$("#state").val();

            gridObj.setOptions({
                newPage : 1
            });
            gridObj.setOptions({
                url : encodeURI("${ctx}/businessjson/specialeData.action?cityIds="+cityIds+"&type=" + typeId+"&state="+state)
            });
            gridObj.loadData(); //加载数据
        }


        function exportSpecial() {
            var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
            var typeId = $("#typeId").val();
            var state=$("#state").val();
            var url = "${ctx}/business/specialBtsExport.action?cityIds="+cityIds+"&type=" + typeId+"&state="+state;
            window.location.href = url;
        }
    </script>
</head>
<body>
<div id="main">
    <!-- 标题
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">错误命名查询</p>
    </div>     -->
    <!-- 标题 end-->
    <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="tr_inquires">
                <td width="60px">
                    地区：
                </td>
                <td width="150px">
                    <input type="text" id="cityId"/>
                    <input type="hidden" id="cityIdVal"/>
                </td>
                <td width="80px">
                    类型：
                </td>
                <td width="150px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
                </td>
                <td width="80px">
                    特殊状态：
                </td>
                <td width="150px">
                    <input type="text" id="stateVal"/>
                    <input type="hidden" id="state"/>
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