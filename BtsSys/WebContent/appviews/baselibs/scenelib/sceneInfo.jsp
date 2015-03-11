<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>场景库信息</title>
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
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>场景库信息</p>
    </div>
    <div class="content">
        <table class="table table-condensed">
            <tr>
                <th width="15%">本地网:</th>
                <td align="left">${wyLibScene.city.cityName}</td>
            </tr>
            <tr>
                <th>区县:</th>
                <td>${wyLibScene.country.cityName}</td>
            </tr>
            <tr>
                <th>场景类型:</th>
                <td>${wyLibScene.sceneTypeName}</td>
            </tr>
            <tr>
                <th>场景级别:</th>
                <td>${wyLibScene.sceneLevelName}</td>
            </tr>
            <tr>
                <th>场景名称:</th>
                <td>${wyLibScene.name}</td>
            </tr>
            <tr>
                <th>经度:</th>
                <td>${wyLibScene.longitude}</td>
            </tr>
            <tr>
                <th>纬度:</th>
                <td>${wyLibScene.latitude}</td>
            </tr>
            <tr>
                <th>备注:</th>
                <td colspan="3">${wyLibScene.remark}</td>
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