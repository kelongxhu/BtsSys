<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Simple jsp page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var SYS_ERROR = '系统错误,请重试!';
        var NO_TOTAL_GRADE_DATA = '暂无得分数据!';
        var TOTAL_GRADE_TYPE = 0;
        var INFO_GRADE_TYPE = 1;
        var INSP_GRADE_TYPE = 2;
        var ALARM_GRADE_TYPE = 3;
        var WIRELESS_GRADE_TYPE = 4;
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
            $("#nameSearch").ligerTextBox({width: 150});
            grid = $("#maingrid").ligerGrid({
                rownumbers: true,
                url: '${ctx}/healthyjson/queryGridList',
                parms: {},
                width: '100%',
                height: '100%',
                pageSize: 50,
                pageSizeOptions: [50,100],
                onDblClickRow: function (data, rowindex, rowobj){
                    if(data['totalGrade']) showGradeDetail(data['intId'], TOTAL_GRADE_TYPE);
                    else alert(NO_TOTAL_GRADE_DATA);
                },
                columns: [
                    {display: '本地网', name: 'cityName', minWidth: 100, align: 'center'},
                    {display: '区县', name: 'countyName', minWidth: 100, align: 'center'},
                    {display: '物理站点名称', minWidth: 200, align: 'center', render: function(rowdata, rowindex){
                        return rowdata['bts']['name'];
                    }},
                    {display: '基础数据得分', name: 'infoGrade', minWidth: 50, align: 'center', render: function(rowdata, rowindex, value){
                        if (!value && value != 0) return '';
                        else return '<a href="javascript: void(0)" onclick="showGradeDetail(' + rowdata['intId'] + ',' + INFO_GRADE_TYPE + ')" title="点击查看详情">' + value + '</a>';
                    }},
                    {display: '巡检数据得分', name: 'inspGrade', minWidth: 50, align: 'center', render: function(rowdata, rowindex, value){
                        if(!value && value!=0) return '';
                        else return '<a href="javascript: void(0)" onclick="showGradeDetail(' + rowdata['intId'] + ',' + INSP_GRADE_TYPE + ')" title="点击查看详情">' + value + '</a>';
                    }},
                    {display: '告警数据得分', name: 'alarmGrade', minWidth: 50, align: 'center', render: function(rowdata, rowindex, value){
                        if(!value && value!=0) return '';
                        else return '<a href="javascript: void(0)" onclick="showGradeDetail(' + rowdata['intId'] + ',' + ALARM_GRADE_TYPE + ')" title="点击查看详情">' + value + '</a>';
                    }},
                    {display: '无线指标得分', name: 'wirelessGrade', minWidth: 50, align: 'center', render: function(rowdata, rowindex, value){
                        if(!value && value!=0) return '';
                        else return '<a href="javascript: void(0)" onclick="showGradeDetail(' + rowdata['intId'] + ',' + WIRELESS_GRADE_TYPE + ')" title="点击查看详情">' + value + '</a>';
                    }},
                    {display: '总得分', name: 'totalGrade', minWidth: 50, align: 'center', render: function(rowdata, rowindex, value){
                        if(!value && value!=0) return '';
                        else return '<a href="javascript: void(0)" onclick="showGradeDetail(' + rowdata['intId'] + ',' + TOTAL_GRADE_TYPE + ')" title="点击查看详情">' + value + '</a>';
                    }}
                ]
            });
        });
        function showGradeDetail(intId, gradeType){
            if(intId) window.location.href = '${ctx}/healthy/showGradeDetail?intId='+intId+'&gradeType='+gradeType;
            else alert(SYS_ERROR);
        }
        function toSearch(){
            var countryIds = $("#cityIdVal").val();
            var nameSearch = $.trim($("#nameSearch").val());
            grid.setOptions({
                url: '${ctx}/healthyjson/queryGridList',
                parms: {countryIds: countryIds, nameSearch: nameSearch}
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
                    <td width="100px">
                        物理站点名称：
                    </td>
                    <td width="150px">
                        <input type="text" id="nameSearch"/>
                    </td>
                    <td align="left">
                        <input class="btn btn-info btn-small" type="button" onclick="toSearch()" value="查询"/>
                        <%--<a onclick="toggle('ydzdgcs')"><img id="arrow_icon_ydzdgcs"
                                                            src="${ctx}/layouts/images/btn_searchest.jpg"/></a>--%>
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