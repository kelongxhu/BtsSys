<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>传输节点列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var grid;
        $(function(){
            var treeCombox;
            //初始化地市
            //初始化树控件
            $.post("${ctx}/schooljson/initCountryTree.action", function(ajaxData, status) {
                var treeData = ajaxData.cityJson;
                treeData = treeData.replace(/"children":\[\],/g, '');
                treeData = eval('(' + treeData + ')');
                treeCombox = $("#cityId").ligerComboBox({
                    width : 200,
                    selectBoxWidth : 200,
                    selectBoxHeight : 200,
                    textField : 'text',
                    valueField : 'id',
                    valueFieldID : 'cityIdVal',
                    treeLeafOnly : false,
                    tree : {
                        data : treeData
                    }
                });
            });
            //初始化所属类型
            var typeURL1 = "${ctx}/schooljson/cons.action?groupCode=TRANSFER";
            $.getJSON(typeURL1,
                    function(data) {
                        $("#type").ligerComboBox({
                            data : data.Rows,
                            width : 120,
                            selectBoxWidth: 120,
                            textField : 'name',
                            valueField : 'code',
                            valueFieldID:'typeValue'
                        });
                    }
            );
            grid = $("#maingrid").ligerGrid({
                rownumbers: true,
                url: '${ctx}/businessjson/queryTransferList',
                parms: {},
                checkbox: true,
                width: '100%',
                height: '100%',
                pageSize: 50,
                pageSizeOptions: [50,100],
                toolbar: {
                    items: [
                        {
                            text: '增加',
                            click: itemClick,
                            icon: 'add',
                            type: 1
                        },
                        {line:true},
                        {
                            text: '修改',
                            click: itemClick,
                            icon: 'modify',
                            type: 2
                        },
                        {line:true},
                        {
                            text: '删除',
                            click: itemClick,
                            icon: 'delete',
                            type: 3
                        }
                    ]
                },
                onDblClickRow: showTransferDetail,
                columns: [
                    {display: '本地网', name: 'cityName', minWidth: 100, align: 'center'},
                    {display: '区县', name: 'countryName', minWidth: 100, align: 'center'},
                    {display: '站名', name: 'name', minWidth: 200, align: 'center'},
                    {display: '所属类型', name: 'typeName', minWidth: 50, align: 'center'},
                    {display: '经度', name: 'longitude', minWidth: 50, align: 'center'},
                    {display: '纬度', name: 'latitude', minWidth: 50, align: 'center'},
                    {display: '备注', name: 'remark', minWidth: 180, align: 'center'},
                    {display: '下挂基站', name: 'btsList', minWidth: 180, align: 'center', render: function(data, index, value){
                        var result = '';
                        for(var i in value){
                            var bts = value[i];
                            var name = bts['name'];
                            result += name + ',';
                        }
                        if(result) result = result.substring(0, result.length - 1);
                        return result;
                    }}
                ]
            });
        });
        function itemClick(item){
            switch(item.type){
                case 1: addTransfer(); break;
                case 2: modifyTransfer(); break;
                case 3: deleteTransfer(); break;
                default: alert('系统错误, 请重试...'); break;
            }
        }
        function addTransfer(){
            window.location.href="${ctx}/business/showTransferAddEdit.action";
        }
        function modifyTransfer(){
            var checkedRows = grid.getSelectedRows();
            var length = checkedRows.length;
            if(length == 0){
                alert('请勾选需要修改的数据...');
            }else if(length == 1){
                var id = checkedRows[0].id;
                window.location.href="${ctx}/business/showTransferAddEdit.action?transferId="+id;
            }else{
                alert('只能勾选一条需要修改的数据...');
            }
        }
        function deleteTransfer(){
            var checkedRows = grid.getSelectedRows();
            var length = checkedRows.length;
            if(length == 0){
                alert('请勾选需要删除的数据...');
            }else if(confirm('是否确认要删除已勾选的数据...')){
                var ids = '';
                for(var i in checkedRows){
                    ids += checkedRows[i].id + ',';
                }
                if(ids) {
                    ids = ids.substring(0, ids.length - 1);
                    $.post('${ctx}/businessjson/transferDelete', {transferIds: ids}, function(json){
                        var result = json.result;
                        var msg = json.msg;
                        alert(msg);
                        if(result) toSearch();
                    });
                }
            }
        }
        function showTransferDetail(data, rowindex, rowobj){
            window.location.href="${ctx}/business/showTransferDetail.action?transferId="+data.id;
        }
        function toSearch(){
            var countryIds = $("#cityIdVal").val();
            var type = $.trim($("#typeValue").val());
            grid.set({
                url: '${ctx}/businessjson/queryTransferList',
                parms: {countryIds: countryIds, type: type}
            });
        }
    </script>
</head>
<body>
    <div id="main">
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
                        所属类型：
                    </td>
                    <td width="120px">
                        <input type="text" id="type"/>
                        <input type="hidden" id="typeValue"/>
                    </td>
                    <td align="left">
                        <input class="btn btn-info btn-small" type="button" onclick="toSearch()" value="查询"/>
                    </td>
                    <td>
                        &nbsp;
                    </td>
                </tr>
            </table>
            <div id="maingrid"></div>
        </div>
    </div>
</body>
</html>