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
                width : 120,
                selectBoxWidth: 180,
                valueFieldID: 'typeId'
            });

            var comBox2 = $("#stateVal").ligerComboBox({
                data: [
                    { text: '新建未验收站', id: '1' },
                    { text: '调测站', id: '2' },
                    { text: '升级站', id: '3' },
                ],
                width : 120,
                selectBoxWidth: 180,
                valueFieldID: 'state'
            });

            //数据类型
            var comBox3 = $("#netTypeVal").ligerComboBox({
                data: [
                    { text: 'CDMA', id: '1' },
                    { text: 'LTE', id: '2' }
                ],
                width : 120,
                selectBoxWidth: 180,
                valueFieldID: 'netType'
            });
        });

        //查询
        function toSearch() {
            //处理地区
            var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
            var typeId = $("#typeId").val();
            var state=$("#state").val();
            var netType=$("#netType").val();

            gridObj.setOptions({
                newPage : 1
            });
            gridObj.setOptions({
                url : encodeURI("${ctx}/businessjson/specialeData.action?cityIds="+cityIds+"&type=" + typeId+"&state="+state+"&netType="+netType)
            });
            gridObj.loadData(); //加载数据
        }

        function exportSpecial() {
            var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
            var typeId = $("#typeId").val();
            var state=$("#state").val();
            var netType=$("#netType").val();
            var url = "${ctx}/business/specialBtsExport.action?cityIds="+cityIds+"&type=" + typeId+"&state="+state+"&netType="+netType;
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
                <td width="60px">
                    网络类型：
                </td>
                <td width="150px">
                    <input type="text" id="netTypeVal"/>
                    <input type="hidden" id="netType"/>
                </td>
                <td width="60px">
                    类型：
                </td>
                <td width="150px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
                </td>
                <td width="60px">
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