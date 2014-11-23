<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>室分详情</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>

    <style type="text/css">
        .container-fluid {
            margin-left: 32px;
            min-width: 800px;
        }

        .container-fluid {
            padding-left: 0px;
            padding-right: 0px;
            border-left: #DDDDDD solid 1px;
            border-bottom: #DDDDDD solid 1px;
            border-right: #DDDDDD solid 1px;
        }

        .form-horizontal .controls {
            margin-left: 80px;
        }

        .form-horizontal .control-label {
            width: 60px;
        }

        .nav {
            margin-bottom: 3px;
        }

        .Seach-box {
            background: none repeat scroll left center #EFF7FA;
            border: 1px solid #C0C2C1;
            height: 40px;
            margin: 4px;
        }

        .Seach-box ul {
            height: 10px;
            margin-left: 10px;
        }

        .Seach-box li {
            float: left;
            height: 40px;
            line-height: 40px;
            margin-right: 6px;
        }

        .l-bar-group .pcontrol input {
            height: 18px;
            padding: 0;
            margin: 0;
        }

        .form-horizontal .control-group {
            margin-bottom: 10px;
        }

        .form-actions {
            margin-top: 10px;
            padding: 10px 20px 10px;
        }
    </style>
</head>
<body data-spy="scroll" data-target=".subnav" data-offset="50">

<div class="tabbable" style="height:100%;overflow:auto">
<div class="container-fluid" style="position: relative;">
<ul id="tab" class="nav nav-tabs nav-stacked" style="position: absolute;top:0;left:-32px;">
    <li class="active" style="width: 30px;"><a href="#home" data-toggle="tab">室分录入信息</a></li>
    <li style="width: 30px;"><a href="#profile" data-toggle="tab">直放站干放站信息</a></li>
</ul>
<div id="myTabContent" class="tab-content">
    <div class="tab-pane fade in active" id="home">
        <table class="table table-condensed">
            <tr class="success">
                <td colspan="7">室分录入信息</td>
            </tr>
            <tr>
                <th>室内分布站点名称：</th>
                <td>${bts.name}</td>
                <th>本地网：</th>
                <td>${bts.cityName}</td>
                <th>区县：</th>
                <td>${bts.country.cityName}</td>
            </tr>
            <tr>
                <th>纬度：</th>
                <td>${bts.latitude}</td>
                <th>信源设备类型：</th>
                <td>${bts.vendorBtstype}</td>
                <th>对应基站所属BSC：</th>
                <td>${bts.bscName}</td>
            </tr>
            <tr>
                <th>对应基站名称：</th>
                <td>${bts.btsName}</td>
                <th>小区序号：</th>
                <td>${ccell.adjNum}</td>
                <th>PN：</th>
                <td>${ccell.pn}</td>
            </tr>
            <tr>
                <th>CI：</th>
                <td>${ccell.ci}</td>
                <th>站点性质一：</th>
                <td>${indoorManual.prop1}</td>
                <th>站点性质二：</th>
                <td>${indoorManual.prop2}</td>
            </tr>
            <tr>
                <th>站点分级：</th>
                <td>${indoorManual.grade}</td>
                <th>覆盖范围描述：</th>
                <td>${indoorManual.coverage}</td>
                <th>楼宇数量：</th>
                <td>${indoorManual.buildingnum}</td>
            </tr>
            <tr>
                <th>详细地址：</th>
                <td>${indoorManual.address}</td>
                <th>信源设备数量：</th>
                <td>${indoorManual.devicenum}</td>
                <th>信源有无监控：</th>
                <td>${indoorManual.monitorflag}</td>
            </tr>
            <tr>
                <th>监控号码：</th>
                <td>${indoorManual.monitornumber}</td>
                <th>信源设备安装位置：</th>
                <td>${indoorManual.deviceaddress}</td>
                <th>直放站数量：</th>
                <td>${indoorManual.repeaternum}</td>
            </tr>
            <tr>
                <th>干放设备数量：</th>
                <td>${indoorManual.drynum}</td>
                <th>分布系统共建共享情况：</th>
                <td>${indoorManual.distributedesc}</td>
                <th>分布系统集成厂家：</th>
                <td>${indoorManual.distributefac}</td>
            </tr>
            <tr>
                <th>有无机房：</th>
                <td>${indoorManual.motorflag}</td>
                <th>机房面积：</th>
                <td>${indoorManual.motorarea}</td>
                <th>机房产权：</th>
                <td>${indoorManual.motorright}</td>
            </tr>
            <tr>
                <th>对应信源设备的电表安装位置：</th>
                <td>${indoorManual.meteraddress}</td>
                <th>建设时间（年月）：</th>
                <td>${indoorManual.constructiondate}</td>
                <th>是否有WLAN：</th>
                <td>${indoorManual.wlanflag}</td>
            </tr>
            <tr>
                <th>WLAN与C网是否共享分布系统：</th>
                <td>${indoorManual.wlanshare}</td>
                <th>WLAN覆盖范围描述：</th>
                <td>${indoorManual.wlancoverage}</td>
                <th>业主联系人：</th>
                <td>${indoorManual.ownername}</td>
            </tr>
            <tr>
                <th>业主联系电话：</th>
                <td>${indoorManual.ownerphone}</td>
                <th>室分监控设备数量：</th>
                <td>${indoorManual.monitordevicenum}</td>
                <th>室分监控设备ID：</th>
                <td>${indoorManual.monitordeviceids}</td>
            </tr>
            <tr>
                <th>备注：</th>
                <td colspan="5">${indoorManual.remark}</td>
            </tr>
        </table>
    </div>
    <div class="tab-pane fade" id="profile">
        <table class="table table-condensed">
            <c:forEach var="erectStation" items="${erectStationList}" varStatus="index">
                <tr class="success">
                    <td colspan="7">直放站信息${index.count}</td>
                </tr>
                <tr>
                    <th>室内分布站点名称：</th>
                    <td>${bts.name}</td>
                    <th>本地网：</th>
                    <td>${bts.cityName}</td>
                    <th>所带直放站编号：</th>
                    <td>${erectStation.no}</td>
                </tr>
                <tr>
                    <th>直放站类型：</th>
                    <td>${erectStation.type}</td>
                    <th>直放站型号：</th>
                    <td>${erectStation.model}</td>
                    <th>直放站厂家：</th>
                    <td>${erectStation.fac}</td>
                </tr>
                <tr>
                    <th>直放站安装位置：</th>
                    <td>${erectStation.address}</td>
                    <th>直放站下接室分系统覆盖范围：</th>
                    <td>${erectStation.coverage}</td>
                    <th>有无监控：</th>
                    <td>${erectStation.monitorfalg}</td>
                </tr>
                <tr>
                    <th>监控号码：</th>
                    <td>${erectStation.monitornumber}</td>
                    <th>电表安装位置：</th>
                    <td>${erectStation.meteraddress}</td>
                    <th>备注：</th>
                    <td>${erectStation.remark}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty erectStationList}"><tr class="success"><td colspan="7">无直放站数据</td></tr></c:if>
            <c:if test="${empty dryStationList}"><tr class="success"><td colspan="7">无干放站数据</td></tr></c:if>
            <c:forEach var="dryStation" items="${dryStationList}" varStatus="index">
                <tr class="success">
                    <td colspan="7">干放站信息${index.count}</td>
                </tr>
                <tr>
                    <th>室内分布站点名称：</th>
                    <td>${bts.name}</td>
                    <th>本地网：</th>
                    <td>${bts.cityName}</td>
                    <th>所带干放站编号：</th>
                    <td>${dryStation.no}</td>
                </tr>
                <tr>
                    <th>干放上级设备（归属）：</th>
                    <td>${dryStation.parentdevice}</td>
                    <th>干放站型号：</th>
                    <td>${dryStation.model}</td>
                    <th>干放站厂家：</th>
                    <td>${dryStation.fac}</td>
                </tr>
                <tr>
                    <th>干放站安装位置：</th>
                    <td>${dryStation.address}</td>
                    <th>干放站下接室分系统覆盖范围：</th>
                    <td>${dryStation.coverage}</td>
                    <th>有无监控：</th>
                    <td>${dryStation.monitorflag}</td>
                </tr>
                <tr>
                    <th>监控号码：</th>
                    <td>${dryStation.monitornumber}</td>
                    <th>电表安装位置：</th>
                    <td>${dryStation.meteraddress}</td>
                    <th>备注：</th>
                    <td>${dryStation.remark}</td>
                </tr>
            </c:forEach>
        </table>

    </div>

    <div class="form-actions_2">
        <button class="btn btn-info" type="reset" onclick="javascript:history.back(-1);">返回</button>
    </div>

</div>



</div>
 </div>



</body>
</html>

