<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>模板定制</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <style type="text/css">
        .container-fluid {
            margin-left: 32px;
            min-width: 800px;
            padding-left: 0px;
            padding-right: 0px;
            border-left: #DDDDDD solid 1px;
            border-bottom: #DDDDDD solid 1px;
            border-right: #DDDDDD solid 1px;
        }
    </style>
    <script type="text/javascript">
        var gridObj = null;
        var gridObj2 = null;
        $(function() {
            $("a[data-toggle=tab]").on("shown", function(e) {
                gridObj.loadData();
                gridObj2.loadData();
            })

            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display:'名称',name:'cnName',width : 200,align:'center'},
                    {display:'字段命名',name:'enName',width : 200,align:'center'}
                ],
                rownumbers:true,
                showTitle : false,
                url:'${ctx}/businessjson/columnsList.action?typeId=${typeId}&enAble=0',
                checkbox : true,
                width: 600,
                height:300,
                usePager:false
            });
            gridObj2 = $("#maingrid2").ligerGrid({
                columns: [
                    {display:'模板名称',name:'name',width : 200,align:'center',minWidth: 100 },
                    {display:'是否共享',name:'shareflag',width : 100,align:'center',minWidth: 100,
                        render:function (row) {
                            if (row.shareflag == 0) {
                                return "不共享";
                            } else {
                                return "共享";
                            }
                        }
                    },
                    {display:'定制用户',name:'username',width : 100,align:'center',minWidth: 100 ,
                        render:function (row) {
                            if (row.user != null) {
                                return row.user.name;
                            }
                        }
                    }
                ],
                rownumbers:true,
                showTitle : false,
                url:'${ctx}/businessjson/templateList.action?useFlag=1&typeId=${typeId}',
                checkbox : true,
                width: 600,
                height:300,
                usePager:false
            });
        });

        function defineExport() {
            var rows = gridObj.getCheckedRows();
            var str = "";
            $(rows).each(function() {
                str += this.id + ",";
            });
            if (str.length == 0) {
                $.ligerDialog.alert('请选择要导出的字段！');
                return;
            } else {
                str = str.substring(0, str.length - 1);
            }
            var url = encodeURI(encodeURI("${ctx}/business/defineCoulmnsExport.action?ids=" + str +
                    "&typeId=${typeId}&countryIds=${countryIds}&name=${name}&bscName=${bscName}&btsId=${btsId}&ci=${ci}&pn=${pn}"));
            alert(url);
            window.location.href = url;
        }
        //按模板导出
        function selectTemplate() {
            var rows = gridObj2.getCheckedRows();
            var j = rows.length;
            if (j == 0) {
                $.ligerDialog.alert('请选择要统计的模板！');
                return;
            } else if (j > 1) {
                $.ligerDialog.alert('请选择一个统计模板导出！');
                return;
            }
            var str = "";
            $(rows).each(function() {
                str += this.id
            });
           //模板中查询统计字段

            var ids = "";
            var strName = "";
            var strEnName = "";//英文名称

            var url="${ctx}/businessjson/columnsByTemplate.action";
            var param={
                id:str
            }
            var idss = parent.window.document.getElementById("ids");
            var cnName = parent.window.document.getElementById("cnName");
            var enName = parent.window.document.getElementById("enName");
            $.getJSON(url, param, function (json) {
                ids = json["ids"];
                strName = json["cnName"];
                strEnName = json["enName"];
                cnName.value = strName;
                enName.value = strEnName;
                idss.value = ids;
                parent.$.ligerDialog.close();
                parent.$(".l-dialog,.l-window-mask").remove();
            });

        }

        function back(){
            parent.$.ligerDialog.close();
            parent.$(".l-dialog,.l-window-mask").remove();
        }

        //自定义选择列
        function selectColumns(){
            var rows = gridObj.getCheckedRows();
            var str = "";
            var strName="";
            var strEnName="";//英文名称
            $(rows).each(function() {
                str += this.id + ",";
                strName+=this.cnName+",";
                strEnName+=this.enName+",";
            });
            if (rows.length == 0) {
                $.ligerDialog.alert('请选择要统计的字段！');
                return;
            } else if(rows.length>8) {
                $.ligerDialog.alert('请选择要统计的字段不能超过8个！');
                return;
            }else{
                str = str.substring(0, str.length - 1);
                strName = strName.substring(0, strName.length - 1);
                strEnName = strEnName.substring(0, strEnName.length - 1);
            }
            var ids= parent.window.document.getElementById("ids");
            var cnName=parent.window.document.getElementById("cnName");
            var enName=parent.window.document.getElementById("enName");
            cnName.value=strName;
            enName.value=strEnName;
            ids.value=str;
            parent.$.ligerDialog.close();
            parent.$(".l-dialog,.l-window-mask").remove();
        }
    </script>
</head>
<body>
<div id="main">
    <div class="content">
        <div class="container-fluid" style="position: relative;">
            <ul class="nav nav-tabs nav-stacked" style="position: absolute;top:0;left:-32px;">
                <li class="active" style="width: 30px;"><a href="#tab1" data-toggle="tab">自定义字段</a>
                </li>
                <li style="width: 30px;"><a href="#tab2" data-toggle="tab">按模板字段</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="tab1">
                    <div id="maingrid"></div>
                    <div class="form-actions_2">
                        <button class="btn btn-info" type="button" onclick="selectColumns();"> <i class="icon-ok icon-white"></i>确定</button>
                        <button class="btn btn-danger" type="reset" onclick="back();">  <i class="icon-repeat"></i>关闭</button>
                    </div>
                </div>
                <div class="tab-pane" id="tab2">
                    <div id="maingrid2"></div>
                    <div class="form-actions_2">
                        <button class="btn btn-info" type="button" onclick="selectTemplate();"> <i class="icon-ok icon-white"></i>确定</button>
                        <button class="btn btn-danger" type="reset" onclick="back();">  <i class="icon-repeat"></i>关闭</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>