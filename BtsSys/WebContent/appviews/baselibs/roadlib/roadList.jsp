<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>道路库管理</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        var treeCombox = null;
        $(function () {

            //选择地市
            $.post("${ctx}/schooljson/initCityTree.action", function (ajaxData, status) {
                var treeData = ajaxData.cityJson;
                treeData = treeData.replace(/"children":\[\],/g, '');
                treeData = eval('(' + treeData + ')');
                treeCombox = $("#cityIdVal").ligerComboBox({
                    width: 150,
                    selectBoxWidth: 200,
                    selectBoxHeight: 200,
                    textField: 'text',
                    valueField: 'id',
                    valueFieldID: 'cityId',
                    treeLeafOnly: false,
                    tree: {
                        data: treeData,
                        checkbox: false
                    }
                });
            });

            //toptoolbar
            $("#toptoolbar").ligerToolBar({
                items: [
                    { text: '增加', click: add, icon: 'add'},
                    { text: '编辑', click: edit, icon: 'modify'},
                    { text: '删除', click: del, icon: 'delete'},
                    { text: '导出模板', click: exportRoadLibTemplate, icon: 'logout'} ,
                    { text: '导入', click: importRoadPage, icon: 'save'},
                ]
            });

            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display: '本地网', name: 'cityName', width: 100, align: 'center'},
                    {display: '道路编号', name: 'roadNo', width: 100, align: 'center'},
                    {display: '道路类别', name: 'roadPropName', width: 100, align: 'center'},
                    {display: '道路名称', name: 'name', width: 200, align: 'center'},
                    {display: '境内起点', name: 'domesiicStart', width: 100, align: 'center'},
                    {display: '境内终点', name: 'domesiicEnd', width: 120, align: 'center'},
                    {display: '里程', name: 'mileage', width: 120, align: 'center'},
                    {display: '开通状态', name: 'openStatus', width: 140, align: 'center'}
                ],
                rownumbers: true,
                showTitle: false,
                pageSize: 50,
                pageSizeOptions: [50, 100],
                url: '${ctx}/schooljson/roadLibData.action',
                checkbox: true,
                width: '99.9%',
                height: '99.9%',
                onDblClickRow: roadInfo
            });
            $("#pageloading").hide();
        });


        function roadInfo(data) {
            window.location.href = "${ctx}/school/roadInfo.action?id=" + data.id;
        }


        //初始化天线厂家
        var typeURL1 = "${ctx}/schooljson/cons.action?groupCode=ROAD_TYPE";
        var sl1;
        $.getJSON(typeURL1,
                function (data) {
                    sl1 = $("#roadProp").ligerComboBox({
                        isShowCheckBox: true,
                        isMultiSelect: true,
                        data: data.Rows,
                        width: 150,
                        selectBoxWidth: 200,
                        textField: 'name',
                        valueField: 'code',
                        valueFieldID: 'roadPropVal'
                    });
                }
        );


        //添加页面
        function add() {
            window.location.href = "${ctx}/appviews/baselibs/roadlib/roadAdd.jsp";
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
            $(rows).each(function () {
                id = this.id;
            });

            window.location.href = "${ctx}/school/addRoadLibPage.action?id=" + id;
        }
        //删除操作
        function del() {
            var rows = gridObj.getCheckedRows();
            var str = "";
            $(rows).each(function () {
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
                    ids: str
                };
                $.getJSON('${ctx}/schooljson/delRoadLib.action', params, function (json) {
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
            var cityId = $("#cityId").val();
            var roadProps = $("#roadPropVal").val().replace(/;/g, ',');

            gridObj.setOptions({
                newPage: 1
            });
            gridObj.setOptions({
                url: encodeURI("${ctx}/schooljson/roadLibData.action?roadProps="
                        + roadProps + "&cityId=" + cityId)
            });
            gridObj.loadData(); //加载数据
        }

        function exportRoadLibTemplate() {
            window.location.href = "${ctx}/school/exportRoadLibTemplate.action";
        }
        //导入数据
        function importRoadPage() {
            window.location.href = "${ctx}/school/importRoadPage.action";
        }
    </script>
</head>
<body>
<div id="main">
    <!-- 标题
	  <div class="main_title_2">
	    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">道路库管理</p>
	  </div>   -->
    <!-- 标题 end-->
    <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="tr_inquires">
                <td width="60px">
                    地区：
                </td>
                <td width="150px">
                    <input type="text" id="cityIdVal"/>
                    <input type="hidden" id="cityId"/>
                </td>
                <td width="60px">
                    道路类别：
                </td>
                <td width="150px">
                    <input type="text" id="roadProp"/>
                    <input type="hidden" id="roadPropVal"/>
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