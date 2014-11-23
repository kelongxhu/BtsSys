<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>缴费信息</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/ligerUI/1.1.9/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        var gridObj=null;
        $(function(){
            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display:'缴费金额',name:'money',width : 140,align:'center'},
                    {display:'缴费人员',name:'payUser',width : 80,align:'center'},
                    {display:'缴费时间',name:'payTimeStr',width : 80,align:'center'},
                    {display:'是否超时',name:'isTimeoutStr',width :120,align:'center'} ,
                    {display:'缴费凭证',name:'proofFileName',width :140,align:'center'}
                ],
                usePager:false,
                rownumbers:true,
                showTitle : false,
                url:'${ctx}/chargejson/payDetailList.action?intId=${charge.intId}&costType=${charge.costType}',
                checkbox : true,
                width: '800',
                height:'300'
            });
        })
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
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>基站缴费信息</p>
    </div>
    <div class="content">
        <table cellpadding="0" cellspacing="0" class="tab_1">
            <tr>
                <td colspan="4"><span class="label label-success">站点基本信息</span></td>
            </tr>
            <tr>
                <td width="150px">站点名称：</td>
                <td width="300px">${charge.btsName}
                </td>
                <td width="150px">所属BSC:</td>
                <td width="300px">${charge.bscName}</td>
            </tr>
            <tr>
                <td width="150px">本地网：</td>
                <td width="300px">${charge.cityName}</td>
                <td width="150px">区县:</td>
                <td width="300px">${charge.countryName}</td>
            </tr>
            <tr>
                <td>BTSID:</td>
                <td>${charge.btsId}</td>
                <td>费用类型:</td>
                <td>${charge.costTypeStr}</td>
            </tr>
        </table>
        <div id="maingrid"></div>
        <%--<table class="table table-condensed">--%>
            <%--<tr>--%>
                <%--<th width="20%">缴费金额:</th>--%>
                <%--<th width="20%">缴费人员:</th>--%>
                <%--<th width="20%">缴费时间:</th>--%>
                <%--<th width="10%">是否超时:</th>--%>
                <%--<th width="30%">缴费凭证:</th>--%>
            <%--</tr>--%>
            <%--<c:forEach items="${chargeList}" var="charge">--%>
            <%--<tr>--%>
                    <%--<td>${charge.money}</td>--%>
                    <%--<td>${charge.payUser}</td>--%>
                    <%--<td>${charge.payTimeStr}</td>--%>
                    <%--<td>${charge.isTimeoutStr}</td>--%>
                    <%--<td>${charge.proofFileName}</td>--%>
            <%--</tr>--%>
            <%--</c:forEach>--%>
        <%--</table>--%>
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