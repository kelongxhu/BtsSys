<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>接口修改数据审核</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        $(function () {
            var treeCombox;
            //初始化地市
            //初始化树控件
            $.post("${ctx}/schooljson/initCityTree.action", function(ajaxData, status) {
                var treeData = ajaxData.cityJson;
                treeData = treeData.replace(/"children":\[\],/g, '');
                treeData = eval('(' + treeData + ')');
                treeCombox = $("#cityId").ligerComboBox({
                    width : 150,
                    selectBoxWidth : 180,
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

            $("#toptoolbar").ligerToolBar({
                items: [
                    { text: '审核通过', click: applyAgree, icon: 'add'},
                    { text: '审核不通过', click: applyOpposed , icon: 'busy'}
                ]
            });
            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display: '名称', name: 'name', width: 120, align: 'center'},
                    {display: '本地网', name: 'cityname', width: 120, align: 'center'},
                    {display: '修改字段', name: 'cncolumnname', width: 120, align: 'center'},
                    {display: '修改前的值', name: 'columnValueOld', width: 180, align: 'center'},
                    {display: '修改后的值', name: 'columnvalue', width: 180, align: 'center'},
                    {display: '数据类型', name: 'typeStr', width: 120, align: 'center'},
                    {display: '状态', name: 'statusStr', width: 80, align: 'center'},
                    {display: '修改人', name: 'userid', width: 80, align: 'center',
                        render: function (row) {
                            if (row.user != null) {
                                return row.user.name;
                            }
                        }
                    },
                    {display: '提交时间', name: 'intimeStr', width: 120, align: 'center'},
                    {display: '审核人', name: 'confirmuserid', width: 80, align: 'center',
                        render:function(row){
                            if(row.confirmUser!=null){
                                return row.confirmUser.name;
                            }
                        }
                    },
                    {display: '审核时间', name: 'updatetimeStr', width: 120, align: 'center'}
                ],
                rownumbers: true,
                showTitle: false,
                pageSize: 50,
                pageSizeOptions: [50, 100],
                url: '${ctx}/businessjson/columneditInterfaceList.action',
                checkbox: true,
                width: '100%',
                height: '100%'
            });
            $("#pageloading").hide();

            //数据类型
            var comBox1 = $("#typeIdVal").ligerComboBox({
                data: [
                    { text: '物理站点', id: '1' },
                    { text: 'BBU', id: '2' },
                    { text: '室分', id: '3' },
                    { text: '小区', id: '4' }
                ],
                width: 150,
                selectBoxWidth: 200,
                valueFieldID: 'typeId'
            });

            var comBox2 = $("#statusVal").ligerComboBox({
                          data: [
                              { text: '未审核', id: '0' },
                              { text: '审核通过', id: '1' },
                              { text: '审核不通过', id: '99' }
                          ],
                          width: 150,
                          selectBoxWidth: 200,
                          valueFieldID: 'status'
                      });
        });


        //编辑页面
        function applyAgree() {
            var rows = gridObj.getCheckedRows();
            var j = rows.length;
            if (j == 0) {
                $.ligerDialog.alert('请选择要审核数据!');
                return;
            }
            var ids="";
            var flag = false;
            var jwFlag=false;
            var columnArr=["LONGITUDE","LATITUDE"];
            $(rows).each(function () {
                ids += this.id + ",";
                if (this.status != 0) {
                    flag = true;
                }
                var type=this.type;
                var column=this.encolumnname;
                if(type==1||type==3||type==4){
                    if(columnArr.indexOf(column)!=-1)
                       jwFlag=true;
                }
            });
            if (flag) {
                $.ligerDialog.alert('选择数据已经审核,请核查!');
                return;
            }
            if(jwFlag){
                $.ligerDialog.alert('经纬度修改请在网管系统修改!');
                return;
            }
            var params = {
                ids: ids,
                status :1
            }
        $.getJSON('${ctx}/businessjson/editApply.action', params, function (json) {
            alert(json["result"]);
            gridObj.loadData();
        });
        }

        function applyOpposed() {
                   var rows = gridObj.getCheckedRows();
                   var j = rows.length;
                   if (j == 0) {
                       $.ligerDialog.alert('请选择要审核数据！');
                       return;
                   }
                   var ids="";
                   var flag=false;
                   $(rows).each(function () {
                       ids += this.id+",";
                       if (this.status != 0) {
                          flag=true;
                       }
                   });

                  if(flag){
                      $.ligerDialog.alert('选择数据已经审核,请核查!');
                      return;
                  }

                   var params = {
                       ids: ids,
                       status :99
                    }
               $.getJSON('${ctx}/businessjson/editApply.action', params, function (json) {
                   alert(json["result"]);
                   gridObj.loadData();
               });
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
                $.getJSON('${ctx}/schooljson/delSecneryLib.action', params, function (json) {
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
            var status=$("#status").val();
            var cityIds=$("#cityIdVal").val();

            gridObj.setOptions({
                newPage: 1
            });
            gridObj.setOptions({
                url: encodeURI("${ctx}/businessjson/columneditInterfaceList.action?cityIds="+cityIds+"&type=" + typeId+"&status="+status)
            });
            gridObj.loadData(); //加载数据
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
                    数据类型：
                </td>
                <td width="150px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
                </td>
                <td width="60px">
                    审核状态：
                </td>
                <td width="150px">
                    <input type="text" id="statusVal"/>
                    <input type="hidden" id="status"/>
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