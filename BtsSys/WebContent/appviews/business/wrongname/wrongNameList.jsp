<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>错误命名小区查询</title>
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
                    { text: '导出', click: exportWrongName, icon:'add'}
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
                    {display:'错误名称',name:'cellName',width : 330,align:'center'},
                    {display:'BTSID/CI/ENB_ID/ECI',name:'bscName',width : 200,align:'center'},
                    {display:'错误原因',name:'wrongMsg',width : 200,align:'center'},
                    {display:'网络类型',name:'netType',width : 80,align:'center',
                        render: function (row) {
                            if (row.netType == 1) {
                                return "CDMA";
                            } else if (row.netType == 2) {
                                return "LTE";
                            }
                        }
                    },
                    {display:'类型',name:'type',width : 80,align:'center',
                        render: function (row) {
                            if (row.type == 1) {
                                return "错误小区";
                            } else if (row.type == 2) {
                                return "错误BBU";
                            }
                        }
                    }
                ],
                rownumbers:true,
                showTitle :false,
                pageSize :50,
                pageSizeOptions :[50,100],
                url:'${ctx}/businessjson/wrongNameData.action',
                checkbox :true,
                width:'99.8%',
                height:'99%'
            });
            $("#pageloading").hide();

            //数据类型
            var comBox1 = $("#typeIdVal").ligerComboBox({
                data: [
                    { text: '错误小区', id: '1' },
                    { text: '错误BBU', id: '2' }
                ],
                width : 150,
                selectBoxWidth: 180,
                valueFieldID: 'typeId'
            });
            //数据类型
            var comBox2 = $("#netIdVal").ligerComboBox({
                data: [
                    { text: 'CDMA', id: '1' },
                    { text: 'LTE', id: '2' }
                ],
                width : 150,
                selectBoxWidth: 180,
                valueFieldID: 'netId'
            });
        });
        //查询
        function toSearch() {
            //处理地区
            var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
            var typeId = $("#typeId").val();
            var netId=$("#netId").val();

            gridObj.setOptions({
                newPage : 1
            });
            gridObj.setOptions({
                url : encodeURI("${ctx}/businessjson/wrongNameData.action?cityIds="+cityIds+"&type=" + typeId+"&netId="+netId)
            });
            gridObj.loadData(); //加载数据
        }
        //导出错误命名
        function exportWrongName() {
            var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
            var typeId = $("#typeId").val();
            var netId=$("#netId").val();
            var url = "${ctx}/business/wrongNameExport.action?cityIds="+cityIds+"&type=" + typeId+"&netId="+netId;
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
                    网络类型：
                </td>
                <td width="150px">
                    <input type="text" id="netIdVal"/>
                    <input type="hidden" id="netId"/>
                </td>
                <td width="80px">
                    错误类型：
                </td>
                <td width="150px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
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