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

    <title>基础数据管理</title>
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
            </div>
            <div id="88" class="box well" style="position: relative; left: 0px; top: 0px;">
                <h4 class="box-title">
                    <i class="icon chevron icon-chevron-up"></i>
                    <i class="icon-star" style=" margin-right: 7px;"></i>
                    本地网各类型基站数据统计
                </h4>

                <div class="box-content " style="display: block;">
                    <table id="sortTableExample" class="table table-striped table table-bordered">
                        <thead>
                        <tr>
                            <th>本地网</th>
                            <th>室外覆盖站点</th>
                            <th>纯BBU站点</th>
                            <th>室内覆盖站点</th>
                            <th>隧道覆盖站点</th>
                            <th>室外小区</th>
                            <th>室内小区</th>
                            <th>隧道小区</th>
                        </tr>
                        </thead>
                        <tbody id="busiDataBody">
                        </tbody>
                    </table>
                </div>
            </div>

            <div id="99" class="box well" style="position: relative; left: 0px; top: 0px;">
                <h4 class="box-title">
                    <i class="icon chevron icon-chevron-up"></i>
                    <i class="icon-star" style=" margin-right: 7px;"></i>
                    本地网各类型基站数据未录入统计
                </h4>

                <div class="box-content " style="display: block;">
                    <table id="sortTableExample2" class="table table-striped table table-bordered">
                        <thead>
                        <tr>
                            <th>本地网</th>
                            <th>室外覆盖站点</th>
                            <th>纯BBU站点</th>
                            <th>室外覆盖小区</th>
                            <th>室内分布小区</th>
                            <th>隧道覆盖小区</th>
                            <th>错误命名</th>
                        </tr>
                        </thead>
                        <tbody id="noManualDataBody">
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
    $.post("${ctx}/businessjson/statByCity", {}, function (data) {
        var result = data['dataCitys'];
        if (result && result.length > 0) {
            var body = $("#busiDataBody");
            for (var i in result) {
                var data = result[i];
                var trObj = $("<tr></tr>");
                var tdObj = $("<td></td>");
                trObj.append('<td>' + data['cityName'] + '</td>');
                trObj.append('<td>' + data['outBtsCount'] + '</td>');
                trObj.append('<td>' + data['bbuCount'] + '</td>');
                trObj.append('<td>' + data['indoorBtsCount'] + '</td>');
                trObj.append('<td>' + data['tunelCount'] + '</td>');
                trObj.append('<td>' + data['outCellCount'] + '</td>');
                trObj.append('<td>' + data['indoorCellCount'] + '</td>');
                trObj.append('<td>' + data['tunelCellCount'] + '</td>');
                body.append(trObj);
            }
        }
    });


    $.post("${ctx}/businessjson/statNoManualByCity", {}, function (data) {
        var result = data['noManualCitys'];
        if (result && result.length > 0) {
            var body = $("#noManualDataBody");
            for (var i in result) {
                var data = result[i];
                var trObj = $("<tr></tr>");
                var tdObj = $("<td></td>");
                trObj.append('<td>' + data['cityName'] + '</td>');
                trObj.append('<td>' + data['outBtsCount'] + '</td>');
                trObj.append('<td>' + data['bbuCount'] + '</td>');
                trObj.append('<td>' + data['outCellCount'] + '</td>');
                trObj.append('<td>' + data['indoorCellCount'] + '</td>');
                trObj.append('<td>' + data['tunelCellCount'] + '</td>');
                trObj.append('<td>' + data['wrongNameCount'] + '</td>');
                body.append(trObj);
            }
        }
    });
</script>
</body>
</html>
