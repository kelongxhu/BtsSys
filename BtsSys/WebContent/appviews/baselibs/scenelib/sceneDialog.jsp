<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>选择场景库</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        $(function () {
            //控件
            $("#name").ligerTextBox({width: 200 });

            //场景类型

            var typeURL1="${ctx}/schooljson/cons.action?groupCode=SCENE_TYPE";
            var comBox3;
            $.getJSON(typeURL1,
                    function(data){
                        comBox3 = $("#sceneTypeVal").ligerComboBox({
                            isMultiSelect :true,
                            isShowCheckBox:true,
                            data : data.Rows,
                            width : 200,
                            selectBoxWidth: 200,
                            textField : 'name',
                            valueField : 'code',
                            valueFieldID:'sceneType'
                        });
                    }
            );

            //grid
            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display:'本地网',name:'city.cityName',width : 60,align:'center',
                        render: function (row) {
                            if(row.city!=null){
                                return row.city.cityName;
                            }
                        }},
                    {display:'场景类型',name:'sceneTypeName',width : 120,align:'center'},
                    {display:'场景级别',name:'sceneLevelName',width : 120,align:'center'},
                    {display:'场景名称',name:'name',width : 300,align:'center'}
                ],
                usePager:false,
                showTitle : false,
                url:'${ctx}/schooljson/sceneLibs.action?cityIds=${cityId}',
                checkbox : true,
                width: '96%',
                height:260
            });
        });



        //查询
        function toSearch() {
            //处理地区
            var name = $("#name").val();
            var sceneType = $("#sceneType").val();
            var url = encodeURI("${ctx}/schooljson/sceneLibs.action?name=" + name+"&sceneTypes="+sceneType);
            gridObj.setOptions({
                newPage : 1
            });
            gridObj.setOptions({
                url : url
            });
            gridObj.loadData(); //加载数据
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
                str+=this.id+",";
                name += this.name+",";
            });
            if (rows.length == 0) {
                $.ligerDialog.alert('请选择至少一条数据');
                return;
            }
            if(str.length>0){
                str=str.substring(0,str.length-1);
                name=name.substring(0,name.length-1);
            }
            var sceneVal = parent.window.document.getElementById("sceneVal");
            var scene = parent.window.document.getElementById("scene");
            sceneVal.value = name;
            scene.value = str;
            parent.$.ligerDialog.close();
            parent.$(".l-dialog,.l-window-mask").remove();
        }
    </script>
</head>
<body>
<div id="main">
    <div class="content">
        <table cellpadding="0" cellspacing="0" width="100%">
            <tr class="tr_inquires">
                <td width="60px">
                    场景类型：
                </td>
                <td width="150px">
                    <input id="sceneTypeVal" type="text">
                    <input id="sceneType" type="hidden">
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