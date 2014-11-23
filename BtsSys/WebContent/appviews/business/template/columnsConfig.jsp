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
            $("a[data-toggle=tab]").on("shown", function() {
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
                url:'${ctx}/businessjson/columnsList.action?typeId=${typeId}',
                checkbox : true,
                width: '98%',
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
                url:'${ctx}/businessjson/templateList.action?useFlag=0&typeId=${typeId}',
                checkbox : true,
                width: '98%',
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
            window.location.href = url;
        }
        //按模板导出
        function templateExport() {
            var rows = gridObj2.getCheckedRows();
            var j = rows.length;
            if (j == 0) {
                $.ligerDialog.alert('请选择要导出的模板！');
                return;
            } else if (j > 1) {
                $.ligerDialog.alert('请选择一个模板导出！');
                return;
            }
            var str = "";
            $(rows).each(function() {
                str += this.id
            });
          var url = encodeURI("${ctx}/business/templateExport.action?id=" + str +
                    "&typeId=${typeId}&countryIds=${countryIds}&name=${name}&bscName=${bscName}&btsId=${btsId}&ci=${ci}&pn=${pn}");
            window.location.href = url;
        }

        function back(){
            javascript: history.go(-1);
        }
    </script>
</head>
<body>
<div id="main">
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">
            <c:if test="${typeId==1}">
                自定义导出室外覆盖站点字段信息
            </c:if>
            <c:if test="${typeId==2}">
                自定义导出纯BBU字段信息
            </c:if>
            <c:if test="${typeId==3}">
                自定义导出室内分布字段信息
            </c:if>
            <c:if test="${typeId==4}">
                自定义导出小区字段信息
            </c:if>
        </p>
    </div>
    <div class="content">
        <div class="container-fluid" style="position: relative;">
            <ul class="nav nav-tabs nav-stacked" style="position: absolute;top:0;left:-32px;">
                <li class="active" style="width: 30px;"><a href="#tab1" data-toggle="tab">自定义导出</a>
                </li>
                <li style="width: 30px;"><a href="#tab2" data-toggle="tab">按模板导出</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="tab1">
                    <div id="maingrid"></div>
                    <div class="form-actions_2">
                        <button class="btn btn-info" type="button" onclick="defineExport();"> <i class="icon-ok icon-white"></i>导出</button>
                        <button class="btn btn-danger" type="reset" onclick="back();">  <i class="icon-repeat"></i>返回</button>
                    </div>
                </div>
                <div class="tab-pane" id="tab2">
                    <div id="maingrid2"></div>
                    <div class="form-actions_2">
                        <button class="btn btn-info" type="button" onclick="templateExport();"> <i class="icon-ok icon-white"></i>导出</button>
                        <button class="btn btn-danger" type="reset" onclick="back();"> <i class="icon-repeat"></i>返回</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>