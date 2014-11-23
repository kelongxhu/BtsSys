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
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>校园库信息</p>
    </div>
    <div class="content">
        <table class="table table-condensed">
            <tr>
                <th width="15%">地市:</th>
                <td width="35%" align="left">${city.cityName}</td>
                <th width="15%">名称:</th>
                <td width="35%" align="left">${schoolLib.name}</td>
            </tr>
            <tr>
                <th>地址:</th>
                <td>${schoolLib.address}</td>
                <th>重要等级:</th>
                <td>${schoolLevelCons.name}</td>
            </tr>
            <tr>
                <th>校园类型:</th>
                <td>${schoolTypeCons.name}</td>
                <th>校园整体覆盖方式:</th>
                <td>${schoolLib.coverageType}</td>
            </tr>
            <tr>
                <th>校园结构:</th>
                <td>${structuralTypeCons.name}</td>
                <th>WIFI覆盖:</th>
                <td>${wifiTypeCons.name}</td>
            </tr>
            <tr>
                <th>C网用户数量:</th>
                <td>${schoolLib.cusers}</td>
                <th>DO用户数量 :</th>
                <td>${schoolLib.doUsers}</td>
            </tr>
            <tr>
                <th>计划发展C网用户数量:</th>
                <td>${schoolLib.cusersPlan}</td>
                <th>计划发展DO用户数量 :</th>
                <td>${schoolLib.doUsersPlan}</td>
            </tr>
            <tr>
                <th>套餐类型:</th>
                <td>${schoolLib.mealType}</td>
                <th>业务特点:</th>
                <td>${schoolLib.businessType}</td>
            </tr>
            <tr>
                <th>备注:</th>
                <td colspan="3">${schoolLib.remark}</td>
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