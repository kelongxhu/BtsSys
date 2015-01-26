<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>BBU显示信息</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        .container-fluid {
            margin-left: 32px;
            min-width: 800px;
            padding-left: 0px;
            padding-right: 0px;
            border-left: #DDDDDD solid 1px;
            border-bottom: #DDDDDD solid 1px;
            border-right: #DDDDDD solid 1px;
        }
    </style>


     <script type="text/javascript">
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
    <p class="main_title_p">
        <img src="${ctx}/layouts/image/ico_arrow.gif"></img> 纯BBU站点显示信息
    </p>
</div>
<div class="content">
<!--<div class="tabbable tabs-left"> -->
<div class="container-fluid" style="position: relative;">
<ul class="nav nav-tabs nav-stacked" style="position: absolute;top:0;left:-32px;">
    <li class="active" style="width: 35px;"><a href="#tab1" data-toggle="tab">站点bbu信息</a>
    </li>
    <li style="width: 35px;"><a href="#tab2" data-toggle="tab">配套设备信息</a></li>
</ul>
<div class="tab-content">
<div class="tab-pane active" id="tab1">
    <table class="table table-condensed" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="success">
            <td colspan="6">站点基本信息</td>
        </tr>
        <tr>
            <th width="150px">基站名称</th>
            <td width="150px">${bbu.name}</td>
            <th width="150px">本地网</th>
            <td width="150px">${city.cityName}</td>
            <th width="150px">区县</th>
            <td width="150px">${country.cityName}</td>

        </tr>
        <tr>
            <th>乡镇:</th>
            <td align="left">${bbuManual.town}</td>
            <th>农村:</th>
            <td align="left">${bbuManual.village}</td>
        </tr>
        <tr>
            <th>经度</th>
            <td>${bbuManual.longitude}</td>
            <th>纬度</th>
            <td>${bbuManual.latitude}</td>
            <th>共站BBU数量</th>
            <td>${bbuNum}</td>


        </tr>
        <tr>
            <th>基站产权</th>
            <td>${bbu.circuitroomOwnership}</td>
            <th>是否共建共享</th>
            <td>${bbuManual.sharFlag}</td>
            <th>共享方名称</th>
            <td>${bbuManual.sharName }</td>
        </tr>
        <tr>
            <th>传输产权</th>
            <td>${bbu.transOwnership }</td>
            <th>详细地址</th>
            <td colspan="3">${bbuManual.address}</td>
        </tr>
        <tr>
            <th>基站开通年月</th>
            <td>${bbuManual.openTime}</td>
            <th>高铁覆盖</th>
            <td>${bbu.highTrainFlag}</td>
            <th>红线内外</th>
            <td>${bbu.redLineFlagStr}</td>
        </tr>
        <tr>
            <th>备注</th>
            <td colspan="5">${ bbuManual.remark}</td>
        </tr>
    </table>
    <table class="table table-condensed">
        <tr class="success">
            <td colspan="8">共站BBU基本信息</td>
        </tr>
        <tr class="inquires_tr">
            <th>BBU名称</th>
            <th>共站BBU编号</th>
            <th>归属MSC</th>
            <th>所属BSC</th>
            <th>网管编号</th>
            <th>使用状态</th>
            <th>是否DO站</th>
            <th>BTS设备型号</th>
        </tr>
        <c:forEach items="${bbuList}" var="bbu">
            <tr>
                <td>
                        ${bbu.btsName }
                </td>
                <td>
                        ${bbu.bbuNo}
                </td>
                <td>
                        ${bbu.mscName}
                </td>
                <td>
                        ${bbu.bscName}
                </td>
                <td>
                        ${bbu.btsId}
                </td>
                <td>
                    <c:if test="${bbu.useStatus==0}">
                        正式商用
                    </c:if>
                    <c:if test="${bbu.useStatus==1}">
                        开通未商用
                    </c:if>
                    <c:if test="${bbu.useStatus==2}">
                        未开通
                    </c:if>
                </td>
                <td>
                    <c:if test="${bbu.doBts==0}">1X基站</c:if>
                    <c:if test="${bbu.doBts==1}">DO基站</c:if>
                </td>
                <td>${bbu.vendorBtstype }</td>
            </tr>
        </c:forEach>
    </table>

</div>

<div class="tab-pane" id="tab2">
    <table class="table table-condensed">
        <tr class="success">
            <td colspan="6">机房</td>
        </tr>
        <tr>
            <th width="150px">机房结构</th>
            <td width="150px">${bbuManual.mrStrutCons.name}</td>
            <th width="150px">机房长度(米)</th>
            <td width="150px">${bbuManual.mrLength}</td>
            <th width="150px">机房宽度(米)</th>
            <td width="150px">${bbuManual.mrWidth}</td>
        </tr>
        <tr>
            <th>机房高度(米)</th>
            <td>${bbuManual.mrHigh}</td>
            <th>机房所属业主或单位</th>
            <td>${bbuManual.mrOwner}</td>
            <th>照片</th>
            <td>${bbuManual.mrPic}</td>
        </tr>
        <tr class="success">
            <td colspan="6">外电</td>
        </tr>
        <tr>
            <th>市电引入方式</th>
            <td>${bbuManual.wdType}</td>
            <th>变压器型号</th>
            <td>${bbuManual.wdModel}</td>
            <th>变压器厂家</th>
            <td>${bbuManual.wdFac}</td>
        </tr>
        <tr>
            <th>所属供电局所</th>
            <td>${bbuManual.wdGds}</td>
        </tr>
        <tr class="success">
            <td colspan="6">传输配套</td>
        </tr>
        <tr>
            <th>规格型号</th>
            <td>${bbuManual.tranModel}</td>
            <th>生产厂家</th>
            <td>${bbuManual.tranFac}</td>
            <th>传输拓扑上游站点名称</th>
            <td>${bbuManual.tranUpsitename}</td>
        </tr>
        <tr>
            <th>传输拓扑下游站点名称</th>
            <td>${bbuManual.tranDownsitename}</td>
            <th>是否在传输环网保护</th>
            <td>${bbuManual.tranNetprotect}</td>
            <th>是否节点站</th>
            <td>${bbuManual.tranIsnode}</td>
        </tr>
        <tr>
            <th>节点站下挂基站数量</th>
            <td>${bbuManual.tranSitenum}</td>
        </tr>

        <tr class="success">
            <td colspan="6">开关电源</td>
        </tr>
        <tr>
            <th>规格型号</th>
            <td>${bbuManual.sourModel}</td>
            <th>生产厂家</th>
            <td>${bbuManual.sourFac}</td>
            <th>整流模块型号</th>
            <td>${bbuManual.sourModuleModel}</td>
        </tr>
        <tr>
            <th>整流模块数量</th>
            <td>${bbuManual.sourModuleNum}</td>
            <th>满架容量（A）</th>
            <td>${bbuManual.sourCapa}</td>
        </tr>

        <tr class="success">
            <td colspan="6">交流配电防雷</td>
        </tr>
        <tr>
            <th>防雷箱规格型号</th>
            <td>${bbuManual.boxModel}</td>
            <th>生产厂家</th>
            <td>${bbuManual.boxFac}</td>
        </tr>
        <tr class="success">
            <td colspan="6">蓄电池组或UPS</td>
        </tr>
        <tr>
            <th>型号</th>
            <td>${bbuManual.cellModel}</td>
            <th>生产厂家</th>
            <td>${bbuManual.cellFac}</td>
            <th>容量（AH）</th>
            <td>${bbuManual.cellCapa}</td>
        </tr>
        <tr>
            <th>数量（组）</th>
            <td>${bbuManual.cellNum}</td>
            <th>机房设备总耗电（A）</th>
            <td>${bbuManual.cellPower}</td>
            <th>支撑基站设备运行时长（小时）</th>
            <td>${bbuManual.cellDuar}</td>
        </tr>
        <tr>
            <th>启用时间（年月）</th>
            <td>${bbuManual.cellUstime}</td>
        </tr>
        <tr class="success">
            <td colspan="6">空调系统</td>
        </tr>
        <tr>
            <th>规格型号</th>
            <td>${bbuManual.acModel}</td>
            <th>生产厂家</th>
            <td>${bbuManual.acFac}</td>
            <th>数量</th>
            <td>${bbuManual.acNum}</td>
        </tr>
        <tr class="success">
            <td colspan="6">动环监控系统</td>
        </tr>
        <tr>
            <th>规格型号</th>
            <td>${bbuManual.dhModel}</td>
            <th>生产厂家</th>
            <td>${bbuManual.dhFac}</td>
        </tr>
        <tr class="success">
            <td colspan="6">新风系统</td>
        </tr>
        <tr>
            <th>规格型号</th>
            <td>${bbuManual.xfModel}</td>
            <th>生产厂家</th>
            <td>${bbuManual.xfFac}</td>
        </tr>
        <tr class="success">
            <td colspan="6">油机配置</td>
        </tr>
        <tr>
            <th>油机类型</th>
            <td>${bbuManual.oeType}</td>
            <th>油机型号</th>
            <td>${bbuManual.oeModel}</td>
            <th>油机功率</th>
            <td>${bbuManual.oePower}</td>
        </tr>
        <tr>
            <th>厂家</th>
            <td>${bbuManual.oeFac}</td>
        </tr>
    </table>
</div>
</div>


<div class="form-actions_2">
    <button class="btn" type="reset" onclick="back();">
        <i class="icon-repeat"></i>
        返回
    </button>
</div>
</div>
</div>
</div>
</body>
</html>