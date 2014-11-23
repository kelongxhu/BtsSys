<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>室外覆盖站点显示信息</title>
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
    <span class="main_title_p">
        <img src="${ctx}/layouts/image/ico_arrow.gif"/> 室外覆盖站点显示信息
    </span>
</div>
<div class="content">
<!--   <div class="tabbable tabs-left">-->
<div class="container-fluid" style="position: relative;">
<ul class="nav nav-tabs nav-stacked" style="position: absolute;top:0;left:-32px;">
    <li class="active" style="width: 30px;"><a href="#tab1" data-toggle="tab">基本信息</a>
    </li>
    <li style="width: 30px;"><a href="#tab2" data-toggle="tab">配套信息</a></li>
    <li style="width: 30px;"><a href="#tab3" data-toggle="tab">覆盖信息</a></li>
    <li style="width: 30px;"><a href="#tab4" data-toggle="tab">共站bbu信息</a></li>
</ul>
<div class="tab-content">
<div class="tab-pane active" id="tab1">
    <table class="table table-condensed">
        <tr>
            <th rowspan="9" width="5%"><span class="label label-success">站点基本信息</span></th>
            <th width="10%">室外覆盖站点名称:</th>
            <td align="left" width="20%">${bts.name}</td>
            <th width="10%">本地网:</th>
            <td align="left" width="20%">${bts.city.cityName}</td>
            <th width="10%">区县:</th>
            <td align="left" width="20%">${bts.country.cityName}</td>
        </tr>
        <tr>
            <th>归属MSC:</th>
            <td align="left">${cbts.mscName}</td>
            <th>LAC:</th>
            <td align="left"></td>
            <th>所属BSC:</th>
            <td align="left">${bts.bscName}</td>
       </tr>
        <tr>
            <th>网管编号:</th>
            <td align="left">${bts.btsId}</td>
            <th>使用状态:</th>
            <td align="left">
                <c:if test="${cbts.usestatus==0}">
                    正式商用
                </c:if>
                <c:if test="${cbts.usestatus==1}">
                    开通未商用
                </c:if>
                <c:if test="${cbts.usestatus==2}">
                    未开通
                </c:if>
            </td>
           <th>是否DO站:</th>
           <td align="left">
                <c:if test="${cbts.doBts==0}">
                    1X基站
                </c:if>
                <c:if test="${cbts.doBts==1}">
                    DO基站
                </c:if>
            </td>
        </tr>
        <tr>
             <th>经度:</th>
              <td align="left">${bts.longitude}</td>
             <th>纬度:</th>
            <td align="left">${bts.latitude}</td>

        </tr>
        <tr>
             <th>所带扇区:</th>
            <td align="left" colspan="6">
                <c:forEach items="${bts.cells}" var="cell">
                    <a href="${ctx}/business/cellInfo.action?intId=${cell.intId}">${cell.name}</a>||
                </c:forEach>
            </td>
        </tr>
        <tr>
            <th>是否拉远:</th>
            <td align="left">${bts.isRru}</td>
            <th>基站名称:</th>
            <td align="left">${bts.btsName}</td>
            <th>主设备安装位置:</th>
            <td align="left" colspan="3">${btsManual.installPosCons.name}</td>
        </tr>
        <tr>
            <th>维护等级:</th>
            <td align="left">${bts.serviceLevel}</td>
            <th>基站产权:</th>
            <td align="left">${bts.circuitroomOwnership}</td>
            <th>传输产权:</th>
            <td align="left">${bts.transOwnership}</td>
        </tr>
        <tr>
            <th>共建共享:</th>
            <td align="left">${btsManual.sharFlag}</td>
            <th>共享方名称:</th>
            <td align="left">${btsManual.sharName}</td>
            <th>开通年月:</th>
            <td align="left">${btsManual.openTime}</td>
        </tr>
        <tr>
            <th>详细地址:</th>
            <td align="left" colspan="5">${btsManual.address}</td>
        </tr>
    </table>
    <table class="table table-condensed">
        <tr>
            <th width="10%" rowspan="2"><span class="label label-success">基站主设备信息</span></th>
            <th width="10%">BTS设备型号:</th>
            <td align="left" width="20%">${bts.vendorBtstype}</td>
            <th width="10%">厂家:</th>
            <td align="left" width="20%"></td>
            <th width="10%">1X配置:</th>
            <td align="left" width="20%">${btsManual.conf1x}</td>
        </tr>
        <tr>
            <th>DO配置:</th>
            <td align="left" colspan="5">${btsManual.confDo}</td>
        </tr>
        <tr>
            <th><span class="label label-success">塔桅</span></th>
            <th>塔桅类型:</th>
            <td align="left">${btsManual.towerTypeCons.name}</td>
            <th>塔桅高度(m)</th>
            <td align="left" colspan="3">${btsManual.towerHigh}</td>
        </tr>
        <tr>
            <th rowspan="2"><span class="label label-success">机房</span></th>
            <th>机房结构:</th>
            <td align="left">${btsManual.mrStrutCons.name}</td>
            <th>机房长度（米）</th>
            <td align="left">${btsManual.mrLength}</td>
            <th>机房宽度（米）</th>
            <td align="left">${btsManual.mrWidth}</td>
        </tr>
        <tr>
            <th>机房高度（米）</th>
            <td align="left">${btsManual.mrHigh}</td>
            <th>机房所属业主或单位：</th>
            <td align="left" colspan="3">${btsManual.mrOwner}</td>
        </tr>
        <tr>
            <th rowspan="2"><span class="label label-success">外电</span></th>
            <th>市电引入方式:</th>
            <td align="left">${btsManual.wdType}</td>
            <th>变压器型号：</th>
            <td align="left">${btsManual.wdModel}</td>
            <th>变压器厂家：</th>
            <td align="left">${btsManual.wdFac}</td>
        </tr>
        <tr>
            <th>所属供电局所：</th>
            <td align="left" colspan="5">${btsManual.wdGds}</td>
        </tr>
    </table>

</div>
<div class="tab-pane" id="tab2">
    <table class="table table-condensed">
        <tr>
            <th rowspan="3" width="10%"><span class="label label-success">传输配套</span></th>
            <th width="15%">规格型号:</th>
            <td align="left" width="15%">${btsManual.tranModel}</td>
            <th width="15%">生产厂家：</th>
            <td align="left" width="15%">${btsManual.tranFac}</td>
            <th width="15%">拓扑上游站点：</th>
            <td align="left" width="15%">${btsManual.tranUpsitenameStr}</td>
        </tr>
        <tr>
           <th>拓扑下游站点：</th>
            <td align="left">${btsManual.tranDownsitenameStr}</td>
            <th>传输环网保护:</th>
            <td align="left">${btsManual.tranNetprotect}</td>
            <th>是否节点站：</th>
            <td align="left">${btsManual.tranIsnode}</td>
        </tr>
        <tr>
            <th>节点站下挂基站数量：</th>
            <td align="left">${btsManual.tranSitenum}</td>
        </tr>
        <tr>
            <th rowspan="2"><span class="label label-success">开关电源</span></th>
            <th>规格型号:</th>
            <td align="left">${btsManual.sourModel}</td>
            <th>生产厂家：</th>
            <td align="left">${btsManual.sourFac}</td>
            <th>整流模块型号：</th>
            <td align="left">${btsManual.sourModuleModel}</td>
        </tr>
        <tr>
            <th>整流模块数量：</th>
            <td align="left">${btsManual.sourModuleNum}</td>
            <th>满架容量（A）：</th>
            <td align="left">${btsManual.sourCapa}</td>
        </tr>
        <tr>
            <th><span class="label label-success">交流配电防雷</span></th>
            <th>防雷箱规格型号:</th>
            <td align="left">${btsManual.boxModel}</td>
            <th>生产厂家：</th>
            <td align="left" colspan="3">${btsManual.boxFac}</td>
        </tr>
        <tr>
            <th rowspan="3"><span class="label label-success">蓄电池组或UPS</span></th>
            <th>型号:</th>
            <td align="left">${btsManual.cellModel}</td>
            <th>生产厂家：</th>
            <td align="left">${btsManual.cellFac}</td>
            <th>容量（AH）：</th>
            <td align="left">${btsManual.cellCapa}</td>
        </tr>
        <tr>
             <th>数量（组）：</th>
            <td align="left">${btsManual.cellNum}</td>
            <th>机房设备总耗电（A）:</th>
            <td align="left">${btsManual.cellPower}</td>
            <th>支撑基站设备运行时长（小时）：</th>
            <td align="left">${btsManual.cellDuar}</td>
        </tr>
        <tr>
            <th>启用时间（年月）：</th>
            <td align="left">${btsManual.cellUstime}</td>
        </tr>
        <tr>
            <th><span class="label label-success">空调系统</span></th>
            <th>规格型号:</th>
            <td align="left">${btsManual.acModel}</td>
            <th>生产厂家：</th>
            <td align="left">${btsManual.acFac}</td>
            <th>数量：</th>
            <td align="left">${btsManual.acNum}</td>
        </tr>
        <tr>
            <th><span class="label label-success">动环监控系统</span></th>
            <th>规格型号:</th>
            <td align="left">${btsManual.dhModel}</td>
            <th>生产厂家：</th>
            <td align="left">${btsManual.dhFac}</td>
        </tr>
        <tr>
            <th><span class="label label-success">新风系统</span></th>
            <th>规格型号:</th>
            <td align="left">${btsManual.xfModel}</td>
            <th>生产厂家：</th>
            <td align="left">${btsManual.xfFac}</td>
        </tr>
        <tr>
            <th rowspan="2"><span class="label label-success">油机配置</span></th>
            <th>油机类型:</th>
            <td align="left">${btsManual.oeType}</td>
            <th>油机型号：</th>
            <td align="left">${btsManual.oeModel}</td>
            <th>油机功率：</th>
            <td align="left">${btsManual.oePower}</td>

        </tr>
        <tr>
            <th>厂家：</th>
            <td align="left" colspan="5">${btsManual.oeFac}</td>
        </tr>

    </table>

</div>
<div class="tab-pane" id="tab3">
        <table class="table table-condensed">
            <tr>
                <th width="18%">扇区覆盖区域类型：</th>
                <td width="18%" align="left">${cellManual.coverarea}</td>
                <th width="18%">农村、乡镇名称：</th>
                <td align="left">
                    <c:forEach items="${cellManual.vitoLibs}" var="vitoLib">
                        <a href="#">${vitoLib.name}</a>||
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <th>扇区覆盖道路类型：</th>
                <td align="left">${cellManual.coverroad}</td>
                <th>道路名称：</th>
                <td align="left">
                    <span class="label label-info">道路：</span>
                    <c:forEach items="${cellManual.roadLibs}" var="roadLib">
                        <a href="#">${roadLib.name}</a>||
                    </c:forEach>
                    <span class="label label-info">隧道:</span>
                    <c:forEach items="${cellManual.tunnelLibs}" var="tunnelLib">
                        <a href="#">${tunnelLib.name}</a>||
                    </c:forEach>
                </td>
            </tr>

            <tr>
                <th>扇区覆盖热点类型：</th>
                <td align="left">${cellManual.coverhot}</td>
                <th>热点名称：</th>
                <td align="left">
                    <span class="label label-info">校园:</span>
                    <c:forEach items="${cellManual.schoolLibs}" var="schoolLib">
                        <a href="#">${schoolLib.name}</a>||
                    </c:forEach>
                    <span class="label label-info">风景：</span>
                    <c:forEach items="${cellManual.secneryLibs}" var="secneryLib">
                        <a href="#">${secneryLib.sceName}</a>||
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <th>是否属边界扇区：</th>
                <td align="left" colspan="3">${cellManual.boundarycellflag}</td>
            </tr>
            <tr>
                <th>接壤省份名称：</th>
                <td align="left" colspan="3">${cellManual.borderprovince}</td>
            </tr>
            <tr>
                <th>接壤城市名称：</th>
                <td align="left" colspan="3">${cellManual.bordercity}</td>
            </tr>
        </table>

</div>
<div class="tab-pane" id="tab4">
    <table class="table table-condensed">
        <tr class="success">
            <td colspan="9">站点共站BBU信息</td>
        </tr>
        <tr>
            <td>共站BBU名称</td>
            <td>归属MSC</td>
            <td>LAC</td>
            <td>所属BSC</td>
            <td>网管编号</td>
            <td>使用状态</td>
            <td>是否DO站</td>
            <td>bts设备型号</td>
            <td>厂家</td>
        </tr>
        <c:forEach items="${bts.bbus}" var="bbu">
            <tr>
                <td>${bbu.name}</td>
                <td>${bbu.cBts.mscName}</td>
                <td>${bbu.cBts.mscName}</td>
                <td>${bbu.cBts.bscName}</td>
                <td>${bbu.cBts.btsid}</td>
                <td>
                    <c:if test="${bbu.cBts.usestatus==0}">
                        正式商用
                    </c:if>
                    <c:if test="${bbu.cBts.usestatus==1}">
                        开通未商用
                    </c:if>
                    <c:if test="${bbu.cBts.usestatus==2}">
                        未开通
                    </c:if>
                </td>
                <td>
                    <c:if test="${bbu.cBts.doBts==0}">
                        1X基站
                    </c:if>
                    <c:if test="${bbu.cBts.doBts==1}">
                        DO基站
                    </c:if>
                </td>
                <td>${bbu.cBts.vendorBtstype}</td>
                <td></td>
            </tr>
        </c:forEach>
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