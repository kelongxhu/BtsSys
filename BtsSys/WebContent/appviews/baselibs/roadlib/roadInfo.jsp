<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>道路库信息</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        //返回
        function back() {
            javascript: history.go(-1);
        }
    </script>
</head>
<body>
<!-- 标题 -->
<div id="main_2">
    <!-- 标题 end-->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>道路库信息</p>
    </div>
    <div class="content">
        <table class="table table-condensed">
            <tr>
                <th width="15%">地市:</th>
                <td width="35%" align="left">${city.cityName}</td>
                <th width="15%">线路属性:</th>
                <td width="35%" align="left">${roadPropCons.name}</td>
            </tr>
            <tr>
                <th>线路名称:</th>
                <td>${roadLib.name}</td>
                <th>设计时速:</th>
                <td>${roadLib.speed}</td>
            </tr>
            <tr>
                <th>辖区内线路里程(km):</th>
                <td>${roadLib.lineMile}</td>
                <th>辖区内隧道里程(km):</th>
                <td>${roadLib.tunnelMile}</td>
            </tr>
            <tr>
                <th>辖区内平原段里程(km):</th>
                <td>${roadLib.plainMile}</td>
                <th>辖区内山区段里程(km):</th>
                <td>${roadLib.mountainMile}</td>
            </tr>
            <tr>
                <th>辖区内车站数量:</th>
                <td>${roadLib.stationNum}</td>
                <th>沿线CDMA网络覆盖方式:</th>
                <td>${coverTypeCons.name}</td>
            </tr>
            <tr>
                <th>辖区内1X覆盖里程(km):</th>
                <td>${roadLib.coverMile1x}</td>
                <th>辖区内1X里程覆盖率（%）：</th>
                <td>${roadLib.coverRate1x}</td>
            </tr>
            <tr>
                <th>里程掉话比（km）：</th>
                <td>${roadLib.dropcallMile}</td>
                <th>辖区内DO覆盖里程（km）：</th>
                <td>${roadLib.coverMileDo}</td>
            </tr>
            <tr>
                <th>辖区内DO里程覆盖率（%）：</th>
                <td>${roadLib.coverRateDo}</td>
                <th>覆盖区域的下行吞吐率（%）：</th>
                <td>${roadLib.downTpRate}</td>
            </tr>
            <tr>
                <th>覆盖区域下行吞吐率优良比：</th>
                <td>${roadLib.downTpgoodRate}</td>
                <th>辖区内已覆盖车站：</th>
                <td>${roadLib.coverStationNum}</td>
            </tr>
            <tr>
                <th>辖区内已覆盖车站(收费站或服务区)覆盖比例：</th>
                <td>${roadLib.coverStationRate}</td>
                <th>路测时间：：</th>
                <td>${roadLib.testtime}</td>
            </tr>
            <tr>
                <th>路测人员：</th>
                <td>${roadLib.testuser}</td>
                <th>备注：：</th>
                <td>${roadLib.remark}</td>
            </tr>


        </table>
        <div class="form-actions_2">
            <button class="btn" type="reset" onclick="back();">
                <i class="icon-repeat"></i>
                返回
            </button>
        </div>
    </div>
</div>

</body>
</html>