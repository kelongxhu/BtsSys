<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        $(function () {
            //控件
            $("#name").ligerTextBox({width: 200 });

            var comBox1 = $("#typeVal").ligerComboBox({
                data: [
                    { text: '物理站点', id: '1' },
                    { text: '纯BBU', id: '3' }
                ],
                width: 200,
                selectBoxWidth: 200,
                valueFieldID: 'type'
            });
            comBox1.selectValue("1");
            toSearch();
        });

        function btsData(url) {
            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display: '物理站点名称', name: 'name', width: 400, align: 'center'},
                    {display: '本地网', name: 'cityName', width: 80, align: 'center'},
                    {display: '区县', name: 'country.cityName', width: 80, align: 'center',
                        render: function (row) {
                            return row.country.cityName;
                        }}
                ],
                rownumbers: true,
                showTitle: false,
                pageSize: 50,
                pageSizeOptions: [50, 100],
                url: url,
                checkbox: true,
                width: '98%',
                height: 260
            });
            $("#pageloading").hide();
        }

        function bbuData(url) {
                   gridObj = $("#maingrid").ligerGrid({
                       columns: [
                           {display: '名称', name: 'name', width: 400, align: 'center'},
                           {display: '本地网', name: 'cityName', width: 80, align: 'center'},
                           {display: '区县', name: 'country.cityName', width: 80, align: 'center',
                               render: function (row) {
                                   return row.country.cityName;
                               }}
                       ],
                       rownumbers: true,
                       showTitle: false,
                       pageSize: 50,
                       pageSizeOptions: [50, 100],
                       url: url,
                       checkbox: true,
                       width: '98%',
                       height: 260
                   });
                   $("#pageloading").hide();
               }


        //查询
        function toSearch() {
            //处理地区
            var name = $("#name").val();
            var type = $("#type").val();

            var url;
            if (type == 3) {
                url = encodeURI("${ctx}/businessjson/bbuData.action?&name=" + name);
                bbuData(url);
            } else {
                url = encodeURI("${ctx}/businessjson/btsData.action?name=" + name);
                btsData(url);
            }
        }

        function back() {
            parent.$.ligerDialog.close();
            parent.$(".l-dialog,.l-window-mask").remove();
        }

        function select() {
            var type = $("#type").val();
            var rows = gridObj.getCheckedRows();
            var str = "";
            var name = "";
            $(rows).each(function () {
                if (type == 1) {
                    str = 1 + "_" + this.name;
                }else if (type == 3) {
                    str = 3 + "_" + this.name;
                }
                name += this.name;
            });
            if (rows.length == 0) {
                $.ligerDialog.alert('请选择一个传输下游拓扑站点!');
                return;
            } else if (rows.length > 1) {
                $.ligerDialog.alert('只能选择一个传输下游拓扑站点!');
                return;
            }
            var tranDownsitenameVal = parent.window.document.getElementById("tranDownsitenameVal");
            var tranDownsitename = parent.window.document.getElementById("tranDownsitename");
            tranDownsitenameVal.value = name;
            tranDownsitename.value = str;
            parent.$.ligerDialog.close();
            parent.$(".l-dialog,.l-window-mask").remove();
        }
    </script>
</head>
<body>
<div id="main">
    <!-- 标题
	  <div class="main_title_2">
	    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">物理站点录入</p>
	  </div>
	  -->
    <!-- 标题 end-->
    <div class="content">
        <table cellpadding="0" cellspacing="0" width="100%">
            <tr class="tr_inquires">
                <td width="60px">
                    类型：
                </td>
                <td width="150px">
                    <input id="typeVal" name="typeVal" type="text">
                    <input id="type" name="type" type="hidden">
                </td>
                <td width="60px">
                    名称：
                </td>
                <td width="150px">
                    <input id="name" name="name" type="text">
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
        <div class="form-actions_2">
            <button class="btn btn-info" type="button" onclick="select();"><i class="icon-ok icon-white"></i>确定
            </button>
            <button class="btn" type="reset" onclick="back();"><i class="icon-repeat"></i>关闭</button>
        </div>
    </div>
</div>
</body>
</html>