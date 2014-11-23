<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>隧道库信息</title>
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
<div id="main">
    <!-- 标题 end-->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>隧道库信息</p>
    </div>
    <div class="content">
        <table class="table table-condensed">
            <tr>
                <th width="15%">地市:</th>
                <td width="35%" align="left">${city.cityName}</td>
                <th width="15%">区县:</th>
                <td width="35%" align="left">${country.cityName}</td>
            </tr>
            <tr>
                <th>所属道路:</th>
                <td>${tunnelLib.roadLib.name}</td>
                <th>隧道名称:</th>
                <td>${tunnelLib.name}</td>
            </tr>
            <tr>
                <th>隧道长度:</th>
                <td>${tunnelLib.tunnellength}</td>
                <th>方向:</th>
                <td>${tunnelLib.direction}</td>
            </tr>
            <tr>
                <th>经度:</th>
                <td>${tunnelLib.longitude}</td>
                <th>纬度:</th>
                <td>${tunnelLib.latitude}</td>
            </tr>
            <tr>
                <th>海拔高度:</th>
                <td>${tunnelLib.height}</td>
                <th>覆盖设备类型:</th>
                <td>${tunnelLib.covgtype}</td>
            </tr>
            <tr>
                <th>设备安装位置:</th>
                <td>${tunnelLib.installadress}</td>
                <th>产权:</th>
                <td>${tunnelLib.rights}</td>
            </tr>
            <tr>
                <th>是否共建共享:</th>
                <td>${tunnelLib.togbs}</td>
                <th>共享方名称:</th>
                <td>${tunnelLib.togname}</td>
            </tr>
            <tr>
                <th>设备开通年月:</th>
                <td>${tunnelLib.opentime}</td>
                <th>详细地址:</th>
                <td>${tunnelLib.address}</td>
            </tr>
            <tr>
                <th>备注:</th>
                <td colspan="3">${tunnelLib.remark}</td>
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