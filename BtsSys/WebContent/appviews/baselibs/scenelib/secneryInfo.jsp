<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>校园库信息</title>
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
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>景区库信息</p>
    </div>
    <div class="content">
        <table class="table table-condensed">
            <tr>
                <th width="15%">地市:</th>
                <td align="left">${city.cityName}</td>
            </tr>
            <tr>
                <th>区县:</th>
                <td>${country.cityName}</td>
            </tr>
            <tr>
                <th>景区名称:</th>
                <td>${secneryLib.sceName}</td>
            </tr>
            <tr>
                <th>景区级别:</th>
                <td>${secLevelCons.name}</td>
            </tr>
            <tr>
                <th>经度:</th>
                <td>${secneryLib.longitude}</td>
            </tr>
            <tr>
                <th>纬度:</th>
                <td>${secneryLib.latitude}</td>
            </tr>
            <tr>
                <th>备注:</th>
                <td colspan="3">${secneryLib.remark}</td>
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