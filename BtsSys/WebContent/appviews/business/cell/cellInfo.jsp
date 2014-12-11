<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>小区显示信息</title>
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
        <img src="${ctx}/layouts/image/ico_arrow.gif"/> 小区显示信息
    </p>
</div>
<div class="content">
   <!-- <div class="tabbable tabs-left"> -->
      <div class="container-fluid" style="position: relative;">
            <ul class="nav nav-tabs nav-stacked" style="position: absolute;top:0;left:-32px;">
            <li class="active" style="width: 35px;"><a href="#tab1" data-toggle="tab">基本信息</a>
            </li>
            <li style="width: 35px;"><a href="#tab2" data-toggle="tab">覆盖信息</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="tab1">
                <table class="table table-condensed">
                    <tr class="success">
                        <td colspan="6">扇区基本信息</td>
                    </tr>
                    <tr>
                        <th width="10%">小区名称</th>
                        <td width="25%">${cell.name}</td>
                        <th width="15%">本地网</th>
                        <td width="15%">${city.cityName}</td>
                        <th width="15%">区县</th>
                        <td width="15%">${country.cityName}</td>
                    </tr>
                    <tr>
                        <th>乡镇</th>
                        <td>${btsManual.town}</td>
                        <th>农村</th>
                        <td>${btsManual.village}</td>
                    </tr>
                    <tr>
                        <th>扇区号</th>
                        <td>${ccell.cellid}</td>
                        <th>所属msc名称</th>
                        <td>${ccell.mscName}</td>
                        <th>所属bsc名称</th>
                        <td>${ccell.bscName}</td>
                    </tr>
                    <tr>
                        <th>BTSID</th>
                        <td>${ccell.btsid}</td>
                        <th>是否拉远</th>
                        <td>${cell.isRru}</td>
                        <th>所属物理站点名称</th>
                        <td>${bts.name}</td>
                    </tr>
                    <tr>
                        <th>所属基站名称</th>
                        <td>${bts.btsName}</td>
                        <th>LAC</th>
                        <td>${ccell.ci}</td>
                        <th>CI</th>
                        <td>${ccell.lac}</td>
                    </tr>
                    <tr>
                        <th>扇区经度</th>
                        <td>${bts.longitude}</td>
                        <th>扇区纬度</th>
                        <td>${bts.latitude}</td>
                        <th>PN(1x)</th>
                        <td>${ccell.pn}</td>
                    </tr>
                    <tr>
                        <th>扇区类型</th>
                        <td>${cellManual.celltype}</td>
                        <th>是否DO小区</th>
                        <td>
                            <c:if test="${ccell.doCell==0}">
                                1X
                            </c:if>
                            <c:if test="${ccell.doCell==1}">
                                DO
                            </c:if>
                            <c:if test="${ccell.doCell==2}">
                                1X+DO
                            </c:if>
                        </td>
                        <th>是否功分</th>
                        <td>${cellManual.gfflag}</td>
                    </tr>
                    <tr>
                        <th>功分数量</th>
                        <td>${cellManual.gfnum}</td>
                        <th>扇区无线容量</th>
                        <td>${ccell.capacity}</td>
                    </tr>
                </table>
                    <table class="table table-condensed">
                        <tr class="success">
                            <td align="left" colspan="5"> 载频信息</td>
                        </tr>
                        <tr>
                            <th>PN(1x)</th>
                            <th>pn_inc</th>
                            <th>1X载波数量</th>
                            <th>DOPN</th>
                            <th>DO载波数量</th>
                        </tr>
                        <tr>
                            <td>${ccell.pn}</td>
                            <td>${ccell.pnInc}</td>
                            <td>${ccell.numfa}</td>
                            <td>${cparCell.pilotpn}</td>
                            <td>${ccell.numfaDo}</td>
                        </tr>
                    </table>
                    <table class="table table-condensed">
                         <tr class="success">
                            <td align="left" colspan="5">设备信息</td>
                        </tr>
                        <tr>
                            <th>设备厂家</th>
                            <th>设备类型</th>
                            <th>塔桅类型</th>
                            <th>塔桅高度(m)</th>
                            <th>馈线长度</th>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>${towerCons.name}</td>
                            <td>${btsManual.towerHigh}</td>
                            <td>${cellManual.feederlength}</td>
                        </tr>
                    </table>
                    <table class="table table-condensed">
                        <tr class="success">
                            <td align="left" colspan="7">天线列表</td>
                        </tr>
                        <tr>
                            <th>功分编号</th>
                            <th>天线型号</th>
                            <th>天线方位角</th>
                            <th>天线挂高</th>
                            <th>天线电子倾角</th>
                            <th>天线机械倾角</th>
                            <th>天线总倾角</th>
                        </tr>
                        <c:forEach items="${cellManual.airLibs}" var="obj" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>${obj.airLib.airModel}</td>
                                <td>${obj.azimuth}</td>
                                <td>${obj.hanghigh}</td>
                                <td>${obj.electrondip}</td>
                                <td>${obj.enginedip}</td>
                                <td>${obj.totaldip}</td>
                            </tr>
                        </c:forEach>
                    </table>
            </div>
            <div class="tab-pane" id="tab2">
                <fieldset>
                    <legend>扇区覆盖区域类型</legend>
                    <table class="table table-condensed">
                        <tr>
                            <th width="18%">扇区覆盖区域类型：</th>
                            <td width="18%" align="left">${cellManual.coverarea}</td>
                            <th width="18%">农村、乡镇名称：</th>
                            <td align="left">
                                <c:forEach items="${cellManual.vitoLibs}" var="vitoLib">
                                     <a href="${ctx}/school/vitoInfo.action?id=${vitoLib.id}">${vitoLib.name}</a>||
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
                                     <a href="${ctx}/school/roadInfo.action?id=${roadLib.id}">${roadLib.name}</a>||
                                </c:forEach>
                                <span class="label label-info">隧道:</span>
                                <c:forEach items="${cellManual.tunnelLibs}" var="tunnelLib">
                                     <a href="${ctx}/school/tunnelInfo.action?id=${tunnelLib.id}">${tunnelLib.name}</a>||
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
                                     <a href="${ctx}/school/schoolInfo.action?id=${schoolLib.id}">${schoolLib.name}</a>||
                                </c:forEach>
                                <span class="label label-info">风景：</span>
                                  <c:forEach items="${cellManual.secneryLibs}" var="secneryLib">
                                     <a href="${ctx}/school/secneryInfo.action?id=${secneryLib.id}">${secneryLib.sceName}</a>||
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

                </fieldset>

            </div>
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
</body>
</html>