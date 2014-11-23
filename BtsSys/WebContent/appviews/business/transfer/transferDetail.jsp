<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>传输节点详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        $(function(){
            var btsList = ${wyTransfernode.btsList};
            var btsStr = '';
            for(var i in btsList){
                var bts = btsList[i];
                var name = bts['name'];
                btsStr += name + ',';
            }
            if(btsStr) btsStr = btsStr.substring(0, btsStr.length - 1);
            $("#bts").html(btsStr);
        });
        function back(){
            history.back();
        }
    </script>
</head>
<body>
<!-- 标题 -->
<div id="main">
    <!-- 标题 end-->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>传输节点详情</p>
    </div>
    <div class="content">
        <table class="table table-condensed">
            <tr>
                <th width="15%">本地网:</th>
                <td width="35%" align="left">${wyTransfernode.cityName}</td>
                <th width="15%">区县:</th>
                <td width="35%" align="left">${wyTransfernode.countryName}</td>
            </tr>
            <tr>
                <th>站名:</th>
                <td>${wyTransfernode.name}</td>
                <th>所属类型:</th>
                <td>${wyTransfernode.typeName}</td>
            </tr>
            <tr>
                <th>经度:</th>
                <td>${wyTransfernode.longitude}</td>
                <th>纬度:</th>
                <td>${wyTransfernode.latitude}</td>
            </tr>
            <tr>
                <th>备注:</th>
                <td colspan="3">${wyTransfernode.remark}</td>
            </tr>
            <tr>
                <th>下挂站点:</th>
                <td colspan="3" id="bts"></td>
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