<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>数据找回</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        var gridObj = null;
        $(function() {
            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    { display: '废弃', columns:
                            [
                                {display:'名称',name:'del_Name',width : 200,align:'center'},
                                {display:'BSCNAME',name:'del_BscName',width : 100,align:'center'},
                                {display:'BTSID',name:'del_BtsId',width : 60,align:'center'},
                                {display:'CI',name:'del_ci',width : 60,align:'center'},
                                {display:'PN',name:'del_pn',width : 40,align:'center'},
                                {display:'录入',name:'manualFlag',width : 60,align:'center',
                                    render: function (row) {
                                        if (row.del_ManualFlag == 0) {
                                            return "<span class='label label-important'>未录入</span>";
                                        } else {
                                            return "<span class='label'>已录入</span>";
                                        }
                                    }}
                            ]
                    },
                    { display: '在用', columns:
                            [
                                {display:'名称',name:'name',width : 200,align:'center'},
                                {display:'BSCNAME',name:'bscName',width : 100,align:'center'},
                                {display:'BTSID',name:'btsId',width : 60,align:'center'},
                                {display:'CI',name:'ci',width : 60,align:'center'},
                                {display:'PN',name:'pn',width : 60,align:'center'},
                                {display:'录入',name:'manualFlag',width : 60,align:'center',
                                    render: function (row) {
                                        if (row.manualFlag == 0) {
                                            return "<span class='label label-important'>未录入</span>";
                                        } else {
                                            return "<span class='label'>已录入</span>";
                                        }
                                    }}
                            ]
                    }
                ],
                rownumbers:true,
                showTitle : false,
                url:'${ctx}/businessjson/mateData.action?typeId=${typeId}&intIds=${intIds}',
                checkbox : true,
                width: '99%',
                height:300,
                usePager:false
            });

            var type=${typeId};
            if(type==4){
                gridObj.toggleCol('del_ci', true);
                gridObj.toggleCol('del_pn', true);
                gridObj.toggleCol('ci', true);
                gridObj.toggleCol('pn', true);
            }else{
                gridObj.toggleCol('del_ci', false);
                gridObj.toggleCol('del_pn', false);
                gridObj.toggleCol('ci', false);
                gridObj.toggleCol('pn', false);
            }

        });


        function ok(){
            var rows = gridObj.getCheckedRows();
            var j = rows.length;
            if (j == 0) {
                $.ligerDialog.alert('请选择要找回的数据！');
                return;
            }
            var intIds="";
            $(rows).each(function() {
                intIds+=this.del_IntId+","+this.intId+";";
            });
            //找回
            var url="${ctx}/businessjson/backMateData.action";
            var param={
                typeId:${typeId},
                intIds:intIds
            }
            $.getJSON(url,param,function(data){
                var reponse=data["result"];
                if(reponse.result=='Y'){
                    alert("找回成功!数量:"+reponse.sucessNum);
                    parent.$.ligerDialog.close();
                    parent.$(".l-dialog,.l-window-mask").remove();
                    parent.window.toSearch();
                    <%--parent.window.location.href="${ctx}/business/findback.action";--%>
                }else{
                    alert("找回失败!");
                }
            })
        }

        function back(){
            parent.$.ligerDialog.close();
            parent.$(".l-dialog,.l-window-mask").remove();
        }
    </script>
</head>
<body>
<div id="main">
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">
            <c:if test="${typeId==1}">
                室外覆盖站点匹配信息
            </c:if>
            <c:if test="${typeId==2}">
                纯BBU覆盖站点匹配信息
            </c:if>
            <c:if test="${typeId==3}">
               室分站点匹配信息
            </c:if>
            <c:if test="${typeId==4}">
                小区匹配信息
            </c:if>
            <c:if test="${typeId==6}">
                隧道覆盖站点信息
            </c:if>
        </p>
    </div>
    <div class="content">
                    <div id="maingrid"></div>
                    <div class="form-actions_2">
                        <button class="btn btn-info" type="button" onclick="ok();"> <i class="icon-ok icon-white"></i>确定</button>
                        <button class="btn btn-info" type="reset" onclick="back();"> <i class="icon-repeat"></i>关闭</button>
                    </div>
    </div>
</div>
</body>
</html>