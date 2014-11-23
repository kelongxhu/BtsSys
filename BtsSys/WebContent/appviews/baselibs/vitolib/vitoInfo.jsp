<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>农村库信息</title>
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
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>乡镇库信息</p>
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
                <th>所属乡镇:</th>
                <td>
                    <c:choose>
                        <c:when test="${vitoLib.parentId==0}">
                            <span style='color: red;'> <乡镇库> </span>
                        </c:when>
                        <c:otherwise>
                            <span style='color: red;'>  <农村库> </span>${vitoLib.parent.name}
                        </c:otherwise>
                    </c:choose>


                </td>
                <th>名称:</th>
                <td>${vitoLib.name}</td>
            </tr>
            <tr>
                <th>中心GPS经度:</th>
                <td>${vitoLib.gpsLong}</td>
                <th>中心GPS纬度:</th>
                <td>${vitoLib.gpsLat}</td>
            </tr>
            <tr>
                <td colspan="4"><span class="label label-success">网络覆盖率</span></td>
            </tr>
            <tr>
                <th>电信CDMA1X 2000:</th>
                <td>${vitoLib.cmda1xCovgrate}</td>
                <th>移动GSM:</th>
                <td>${vitoLib.mobgsmCovgrate}</td>
            </tr>
            <tr>
                <th>电信与移动差值:</th>
                <td>${vitoLib.covgDifCovgrate}</td>
                <th>联通GSM :</th>
                <td>${vitoLib.unicgsmCovgrate}</td>
            </tr>
            <tr>
                <th>电信EVDO:</th>
                <td colspan="3">${vitoLib.evdoCovgrate}</td>

            </tr>
            <tr>
                <td colspan="4"><span class="label label-success">语音MOS值</span></td>
            </tr>
            <tr>
                <th>电信CDMA1X 2000 :</th>
                <td>${vitoLib.cdma1xMos}</td>
                <th>移动GSM:</th>
                <td>${vitoLib.mobgsmMos}</td>

            </tr>
            <tr>
               <th>联通GSM :</th>
                <td colspan="3">${vitoLib.unicgsmMos}</td>
            </tr>
            <tr>
                <th>备注:</th>
                <td colspan="3">${vitoLib.remark}</td>
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