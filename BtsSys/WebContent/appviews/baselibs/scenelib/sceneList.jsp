<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>场景库管理</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        $(function() {


            var treeCombox;
            //初始化地市
            //初始化树控件
            $.post("${ctx}/schooljson/initCityTree.action", function(ajaxData, status) {
                var treeData = ajaxData.cityJson;
                treeData = treeData.replace(/"children":\[\],/g, '');
                treeData = eval('(' + treeData + ')');
                treeCombox = $("#cityId").ligerComboBox({
                    width : 180,
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
                //treeCombox.initComboBox(treeData,'localNetsVal');
            });
            //toptoolbar
            $("#toptoolbar").ligerToolBar({
                items: [
                    { text: '增加', click: add , icon:'add'},
                    { text: '编辑', click: edit , icon:'modify'},
                    { text: '删除', click: del , icon:'delete'},
                    { text: '导出模板', click: exportSceneLibTemplate , icon:'logout'} ,
                    { text: '导入', click: importScenePage , icon:'save'},
                    { text: '导出', click: importData , icon:'save'}
                ]
            });

            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display:'本地网',name:'city.cityName',width : 60,align:'center',
                        render: function (row) {
                            if(row.city!=null){
                                return row.city.cityName;
                            }
                        }},
                    {display:'区县',name:'country.cityName',width : 60,align:'center',
                        render: function (row) {
                            if(row.country!=null){
                                return row.country.cityName;
                            }
                        }},
                    {display:'场景类型',name:'sceneTypeName',width : 120,align:'center'},
                    {display:'场景级别',name:'sceneLevelName',width : 120,align:'center'},
                    {display:'场景名称',name:'name',width : 300,align:'center'},
                    {display:'经度',name:'longitude',width :100,align:'center'} ,
                    {display:'纬度',name:'latitude',width : 100,align:'center'}
                ],
                rownumbers:true,
                showTitle : false,
                pageSize : 50,
                pageSizeOptions:[50,100],
                url:'${ctx}/schooljson/sceneLibData.action',
                checkbox : true,
                width: '99.9%',
                height:'99%',
                onDblClickRow :secneryInfo
            });
            $("#pageloading").hide();
        });

        function secneryInfo(data) {
            window.location.href = "${ctx}/school/secneryInfo.action?id=" + data.id;
        }


        //添加页面
        function add() {
            window.location.href = "${ctx}/school/pageSceneLibAdd.action";
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
            window.location.href = "${ctx}/school/pageSceneLibAdd.action?id=" + id;
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
                $.getJSON('${ctx}/schooljson/delSceneLibs.action', params, function(json) {
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

            gridObj.setOptions({
                newPage : 1
            });
            gridObj.setOptions({
                url : encodeURI("${ctx}/schooljson/sceneLibData.action?cityIds="
                        + cityIds)
            });
            gridObj.loadData(); //加载数据
        }

        function exportSceneLibTemplate(){
            window.location.href = "${ctx}/school/exportSceneLibTemplate.action";
        }
        //导入数据
        function importScenePage() {
            window.location.href = "${ctx}/school/importScenePage.action";
        }

        function importData(){

        }
    </script>
</head>
<body>
<div id="main">
    <!-- 标题
	  <div class="main_title_2">
	    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">景区库管理</p>
	  </div>  -->
    <!-- 标题 end-->
    <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="tr_inquires">
                <td width="60px">
                    本地网：
                </td>
                <td width="150px">
                    <input type="text" id="cityId"/>
                    <input type="hidden" id="cityIdVal"/>
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