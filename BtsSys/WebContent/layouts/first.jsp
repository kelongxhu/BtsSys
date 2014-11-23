<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>

    <title>中国电信</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/resources/bootstrap/css/xadmin.main.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/resources/bootstrap/css/bootstrap-xadmin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container-fluid" id="body-content" style="padding: 20px;">
    <div class="row-fluid">
        <div class="span12" id="content-block">
            <div class="dashboard row-fluid">
                <div class="span6 column ui-sortable">

                    <div id="6" class="box well" style="position: relative; left: 0px; top: 0px;">
                        <h4 class="box-title">
                            <i class="icon chevron icon-chevron-up"></i>
                            <i class="icon-star" style=" margin-right: 7px;"></i>
                            基站评分分段个数统计
                        </h4>

                        <div class="box-content " style="display: block;">
                            <div id="chart-1"
                                 style="height: 317px; overflow: hidden;"></div>
                        </div>

                    </div>

                </div>

                <div class="span6 column ui-sortable">

                    <div id="9" class="box well" style="position: relative; left: 0px; top: 0px;">
                        <h4 class="box-title">
                            <i class="icon chevron icon-chevron-up"></i>
                            <i class="icon-star" style=" margin-right: 7px;"></i>
                            基站评分分段个数占比统计
                        </h4>

                        <div class="box-content " style="display: block;">
                            <div id="chart-2"
                                 style="height: 317px; overflow: hidden;"></div>


                        </div>

                    </div>


                </div>
            </div>
            <div id="88" class="box well" style="position: relative; left: 0px; top: 0px;">
                <h4 class="box-title">
                    <i class="icon chevron icon-chevron-up"></i>
                    <i class="icon-star" style=" margin-right: 7px;"></i>
                    本地网基站平均分排名
                </h4>

                <div class="box-content " style="display: block;">


                    <table id="sortTableExample" class="table table-striped table table-bordered">
                        <thead>
                        <tr>
                            <th>本地网</th>
                            <th>排名</th>
                            <th>基础评分</th>
                            <th>巡检评分</th>
                            <th>告警评分</th>
                            <th>无线评分</th>
                            <th>总平均分</th>
                        </tr>
                        </thead>
                        <tbody id="busiDataBody">
                        </tbody>
                    </table>


                </div>

            </div>
        </div>
        <!--/span-->
    </div>
    <!--/row-->
</div>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/xadmin.plugin.portal.js"></script>
<script type="text/javascript" src="${ctx}/resources/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="${ctx}/resources/fusionCharts/js/FusionCharts.js"></script>
<script type="text/javascript">
    $.post("${ctx}/healthyjson/countBtsGradeNumForFusionChart", {fusionChartType: 'chart1'}, function(data){
        var chart1 = new FusionCharts('${ctx}/resources/fusionCharts/swf/Column2D.swf', "chart1", $("#chart-1").width(), $("#chart-1").height(), "0", "1", "#FFFFFF");
        chart1.setDataXML(data.xml);
        chart1.render("chart-1");
    });
    $.post("${ctx}/healthyjson/countBtsGradeNumForFusionChart", {fusionChartType: 'chart2'}, function(data){
        var chart2 = new FusionCharts('${ctx}/resources/fusionCharts/swf/Pie3D.swf', "chart2", $("#chart-2").width(), $("#chart-2").height(), "0", "1", "#FFFFFF");
        chart2.setDataXML(data.xml);
        chart2.render("chart-2");
    });
    $.post("${ctx}/healthyjson/getSortedBtsAvgGradeWithCity", {}, function(data){
        var result = data['result'];
        if(result && result.length > 0){
            var body = $("#busiDataBody");
            for(var i in result){
                var data = result[i];
                var trObj = $("<tr></tr>");
                var tdObj = $("<td></td>");
                var name = data['NAME'] ? data['NAME'] : '';
                trObj.append('<td>' + name + '</td>');
                var rn = data['RN'] ? data['RN'] : '';
                trObj.append('<td>' + rn + '</td>');
                var avgInfoGrade = data['AVG_INFO_GRADE'] != undefined ? data['AVG_INFO_GRADE'] : '';
                trObj.append('<td>' + avgInfoGrade + '</td>');
                var avgInspGrade = data['AVG_INSP_GRADE'] != undefined ? data['AVG_INSP_GRADE'] : '';
                trObj.append('<td>' + avgInspGrade + '</td>');
                var avgAlarmGrade = data['AVG_ALARM_GRADE'] != undefined ? data['AVG_ALARM_GRADE'] : '';
                trObj.append('<td>' + avgAlarmGrade + '</td>');
                var avgWirelessGrade = data['AVG_WIRELESS_GRADE'] != undefined ? data['AVG_WIRELESS_GRADE'] : '';
                trObj.append('<td>' + avgWirelessGrade + '</td>');
                var avgTotalGrade = data['AVG_TOTAL_GRADE'] != undefined ? data['AVG_TOTAL_GRADE'] : '';
                trObj.append('<td>' + avgTotalGrade + '</td>');
                body.append(trObj);
            }
        }
    });
    /*$('#chart-1').highcharts({
        colors: ['#EE505C', '#F99B40', '#F0FF5A', '#43D5FA'],
        chart: {
            type: 'spline'
        },
        title: {
            text: ''
        },
        xAxis: {
            categories: ['20130408','20130415', '20130422', '20130429']
        },
        yAxis: {
            title: {
                text: '告警(故障)数'
            },
            labels: {
                formatter: function() {
                    return this.value +'°'
                }
            }
        },
        tooltip: {
            crosshairs: true,
            shared: true
        },
        plotOptions: {
            spline: {
                marker: {
                    radius: 4,
                    lineColor: '#666666',
                    lineWidth: 1
                }
            }
        },
        series: [{
            name: '严重',
            marker: {
                symbol: 'square'
            },
            data: [7, 25, 30,9]

        }, {
            name: '重要',
            marker: {
                symbol: 'diamond'
            },
            data: [2, 35, 22,5]
        },{
            name: '一般',
            marker: {
                symbol: 'diamond'
            },
            data: [15.0, 22, 12,51]
        },{
            name: '通知',
            marker: {
                symbol: 'diamond'
            },
            data: [1, 5, 7,9]
        }]
    });*/

   /* $('#chart-2').highcharts({
        colors: ['#EE505C', '#F99B40', '#F0FF5A', '#43D5FA'],
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: ''
        },
        tooltip: {
            formatter: function() { //鼠标滑向图像提示框的格式化提示信息
                return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.percentage,2) + ' %';
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    formatter: function() {
                        return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 2) +' %';
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
                ['严重',   5],
                ['重要',   1],
                {
                    name: '一般',
                    y: 2,
                    sliced: true,
                    selected: true
                },
                ['通知',  1]
            ]
        }]
    });*/
</script>
</body>
</html>
