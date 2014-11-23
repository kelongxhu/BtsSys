<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>专项整改</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        $(function() {
            //toptoolbar
            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display:'专项名称',name:'name',width : 420,align:'center'},
                    {display:'描述',name:'specdesc',width : 420,align:'center'},
                    {display:'更新时间',name:'updatetime',width : 140,align:'center'}
                ],
                //rownumbers:true,
                showTitle :false,
                pageSize :50,
                pageSizeOptions :[50,100],
                url:'${ctx}/healthyjson/specialListByCorrection.action',
                //checkbox :true,
                width:'100%',
                height :  '100%',
                //onDblClickRow:specialInfo,
                detail:{height:340,onShowDetail:function(row, detailPanel) {
                    var g = document.createElement('div');
                    $(detailPanel).append(g);
                    $(detailPanel).css('overflow', "hidden");
                    $(g).css('margin', 10).ligerGrid({
                        columns:[
                            {display : '基站名字',name : 'name',width :200,align : 'center',
                                render:function (row) {
                                    return row.bts.name;
                                }
                            },
                            {display : '基站等级',name : 'serviceLevel',width : 100,align : 'center',
                                render:function(row){
                                    return row.bts.serviceLevel;
                                }
                            },
                            {display : '检测时间',name : 'updateTimeStr',width :100,align : 'center'}
                        ],
                        isScroll:true,
                        width:'95%',
                        height:320,
                        pageSize :50,
                        pageSizeOptions :[50,100],
                        url :  "${ctx}/healthyjson/specialBtsListByCorrection.action",
                        parms:{
                            specialId: row.id
                        }
                    })
                }}
            });
            $("#pageloading").hide();

            //控件
            $("#specialName").ligerTextBox({width : 200 });
            $("#btsName").ligerTextBox({width : 200 });
        });

        function specialInfo(data){
            window.location.href = "${ctx}/healthy/specialInfo.action?id=" + data.id;
        }


        //查询
        function toSearch() {
            var specialName = $("#specialName").val();
            var btsName = $("#btsName").val();

            gridObj.setOptions({
                newPage : 1
            });
            gridObj.setOptions({
                url : encodeURI("${ctx}/healthyjson/specialListByCorrection.action?specialName="+specialName+"&btsName="+btsName)
            });
            gridObj.loadData(); //加载数据
        }
    </script>
</head>
<body>
<div id="main">
    <!-- 标题
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">导出模板定制</p>
    </div>   -->
    <!-- 标题 end-->
    <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="tr_inquires">
                <td width="60px">
                    专项名称：
                </td>
                <td width="150px">
                    <input type="text" id="specialName"/>
                </td>
                <td width="60px">
                    基站名称：
                </td>
                <td width="150px">
                    <input type="text" id="btsName"/>
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