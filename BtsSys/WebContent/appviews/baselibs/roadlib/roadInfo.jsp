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
                <th width="15%">道路列表:</th>
                <td width="35%" align="left">${roadPropCons.name}</td>
            </tr>
            <tr>
                <th>道路名称:</th>
                <td>${roadLib.name}</td>
                <th>道路编号:</th>
                <td>${roadLib.roadNo}</td>
            </tr>
            <tr>
                <th>境内起点:</th>
                <td>${roadLib.domesiicStart}</td>
                <th>境内终点:</th>
                <td>${roadLib.domesiicEnd}</td>
            </tr>
            <tr>
                <th>里程:</th>
                <td>${roadLib.mileage}</td>
                <th>开通状态:</th>
                <td>${roadLib.openStatus}</td>
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