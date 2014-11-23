<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>盲点库信息</title>
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
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>盲点库信息</p>
    </div>
    <div class="content">
        <table class="table table-condensed">
            <tr>
                <th width="15%">本地网:</th>
                <td width="35%" align="left">${city.cityName}</td>
                <th width="15%">区县:</th>
                <td width="35%" align="left">${country.cityName}</td>
            </tr>
            <tr>
                <th>农村和乡镇:</th>
                <td>${vitoLib.name}</td>
                <th>盲点名称:</th>
                <td>${wyBlind.name}</td>
            </tr>
			<tr>
                <th>经度:</th>
                <td>${wyBlind.longitude}</td>
                <th>纬度:</th>
                <td>${wyBlind.latitude}</td>
            </tr>            
            <tr>
                <th>地址:</th>
                <td>${wyBlind.address}</td>
                <th>网格类型:</th>
                <td>
	               	<c:choose>
	               		<c:when test="${wyBlind.gridType == '0'}">市区</c:when>
	               		<c:when test="${wyBlind.gridType == '1'}">城郊</c:when>
	               		<c:when test="${wyBlind.gridType == '2'}">县城</c:when>
	               		<c:when test="${wyBlind.gridType == '3'}">乡镇</c:when>
	               		<c:when test="${wyBlind.gridType == '4'}">农村</c:when>
	               		<c:otherwise/>
	               	</c:choose>
                </td>
            </tr>
            <tr>
                <th>区域类型1:</th>
                <td>
                	<c:choose>
                		<c:when test="${wyBlind.blindType1 == '1'}">室内</c:when>
                		<c:when test="${wyBlind.blindType1 == '0'}">室外</c:when>
						<c:otherwise/>
                	</c:choose>
                </td>
                <th>区域类型2:</th>
                <td>
                	<c:choose>
                		<c:when test="${wyBlind.blindType2 == '0'}">高铁</c:when>
                		<c:when test="${wyBlind.blindType2 == '1'}">高速公路</c:when>
                		<c:when test="${wyBlind.blindType2 == '2'}">机场</c:when>
                		<c:when test="${wyBlind.blindType2 == '3'}">车站港口码头</c:when>
                		<c:when test="${wyBlind.blindType2 == '4'}">住宅小区</c:when>
                		<c:when test="${wyBlind.blindType2 == '5'}">风景区</c:when>
                		<c:when test="${wyBlind.blindType2 == '6'}">宾馆</c:when>
                		<c:when test="${wyBlind.blindType2 == '7'}">写字楼</c:when>
                		<c:when test="${wyBlind.blindType2 == '8'}">商场</c:when>
                		<c:when test="${wyBlind.blindType2 == '9'}">体育场</c:when>
                		<c:when test="${wyBlind.blindType2 == '10'}">地铁</c:when>
                		<c:when test="${wyBlind.blindType2 == '11'}">海域</c:when>
                		<c:when test="${wyBlind.blindType2 == '12'}">校园</c:when>
                		<c:when test="${wyBlind.blindType2 == '13'}">隧道</c:when>
                		<c:when test="${wyBlind.blindType2 == '14'}">医院</c:when>
                		<c:when test="${wyBlind.blindType2 == '15'}">政府机关</c:when>
                		<c:when test="${wyBlind.blindType2 == '16'}">工业园区</c:when>
                		<c:when test="${wyBlind.blindType2 == '17'}">农村</c:when>
                		<c:when test="${wyBlind.blindType2 == '18'}">水运</c:when>
                		<c:when test="${wyBlind.blindType2 == '19'}">聚类市场</c:when>
                		<c:when test="${wyBlind.blindType2 == '20'}">休闲娱乐场所</c:when>
						<c:otherwise/>
                	</c:choose>
                </td>
            </tr>
            <tr>
                <th>是否1X:</th>
                <td>
                	<c:choose>
                		<c:when test="${wyBlind.is1x == '1'}">是</c:when>
                		<c:when test="${wyBlind.is1x == '0'}">否</c:when>
                		<c:otherwise/>
                	</c:choose>
                </td>
                <th>是否DO:</th>
                <td>
                	<c:choose>
                		<c:when test="${wyBlind.isDo == '1'}">是</c:when>
                		<c:when test="${wyBlind.isDo == '0'}">否</c:when>
                		<c:otherwise/>
                	</c:choose>
                </td>
            </tr>
            <tr>
                <th>是否LTE:</th>
                <td>
                	<c:choose>
                		<c:when test="${wyBlind.isLte == '1'}">是</c:when>
                		<c:when test="${wyBlind.isLte == '0'}">否</c:when>
                		<c:otherwise/>
                	</c:choose>
                </td>
                <th>解决方式:</th>
                <td>
					<c:choose>
                		<c:when test="${wyBlind.solType == '0'}">C网优化</c:when>
                		<c:when test="${wyBlind.solType == '1'}">C网工程：指对基站、直放站、室分、RRU进行调整</c:when>
                		<c:when test="${wyBlind.solType == '2'}">C网辅助手段</c:when>
                		<c:when test="${wyBlind.solType == '3'}">WiFi解决</c:when>
                		<c:otherwise/>
                	</c:choose>                	
                </td>
            </tr>
            <tr>
                <th>辅助手段:</th>
                <td>
                	<c:choose>
                		<c:when test="${wyBlind.assistType == '0'}">BPA</c:when>
                		<c:when test="${wyBlind.assistType == '1'}">塔放</c:when>
                		<c:when test="${wyBlind.assistType == '2'}">壁虎</c:when>
                		<c:when test="${wyBlind.assistType == '3'}">手机伴侣</c:when>
                		<c:when test="${wyBlind.assistType == '4'}">五类线</c:when>
                		<c:when test="${wyBlind.assistType == '5'}">其它</c:when>
                		<c:when test="${wyBlind.assistType == '6'}">无</c:when>
                		<c:otherwise/>
                	</c:choose> 
               	</td>
                <th>填报日期:</th>
                <td>${wyBlind.inTimeString}</td>
            </tr>
            <tr>
            	<th>状态:</th>
                <td>
                	<c:choose>
                		<c:when test="${wyBlind.status == '0'}">未解决</c:when>
                		<c:when test="${wyBlind.status == '1'}">已解决</c:when>
                		<c:otherwise/>
                	</c:choose> 
               	</td>
               	<th>是否规划:</th>
                <td>
                	<c:choose>
                		<c:when test="${wyBlind.isPlan == '0'}">无</c:when>
                		<c:when test="${wyBlind.isPlan == '1'}">有</c:when>
                		<c:otherwise/>
                	</c:choose> 
               	</td>
            </tr>
            <tr>
            	<th>形成原因:</th>
                <td>${wyBlind.causesBy}</td>
                <th>标志建筑:</th>
                <td>${wyBlind.landMark}</td>
            </tr>
             <tr>
                <th>计划解决时间:</th>
                <td>${wyBlind.planSolutionTmStr}</td>
                 <th>实际解决时间:</th>
                 <td>${wyBlind.realSolutionTmStr}</td>
            </tr>
             <tr>
                <th>备注:</th>
                <td colspan="3">${wyBlind.remarks}</td>
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