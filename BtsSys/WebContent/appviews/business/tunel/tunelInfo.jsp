<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>隧道覆盖站点显示信息</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
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
        <img src="${ctx}/layouts/image/ico_arrow.gif"/> 隧道覆盖站点显示信息
    </span>
</div>
<div class="content">
    <table class="table table-condensed">
        <tr>
            <th width="10%">室外覆盖站点名称:</th>
            <td align="left" width="20%">${wyTunel.name}</td>
            <th>是否拉远:</th>
            <td align="left">${wyTunel.isRru}</td>
        </tr>
        <tr>
            <th width="10%">本地网:</th>
            <td align="left" width="20%">${wyTunel.city.cityName}</td>
            <th width="10%">区县:</th>
            <td align="left" width="20%">${wyTunel.country.cityName}</td>
        </tr>
        <tr>
            <th width="10%">乡镇:</th>
            <td align="left" width="20%">${tunelManual.town}</td>
            <th width="10%">农村:</th>
            <td align="left" width="20%">${tunelManual.village}</td>
        </tr>
        <tr>
            <th>所属BSC:</th>
            <td align="left">${wyTunel.bscName}</td>
            <th>网管编号:</th>
            <td align="left">${wyTunel.btsId}</td>
       </tr>
        <tr>
            <th>PN:</th>
            <td align="left">${wyTunel.pn}</td>
            <th>CI:</th>
            <td align="left">${wyTunel.ci}</td>
        <tr>
            <th>隧道性质:</th>
            <td align="left">${tunelManual.propCons.name}</td>
            <th>所属道路名称:</th>
            <td align="left">${tunelManual.roadLib.name}</td>
        </tr>
        <tr>
            <th>隧道长度 :</th>
            <td align="left">${tunelManual.tunelLength}</td>
            <th>天馈类型:</th>
            <td align="left">${tunelManual.antennatype}</td>
        </tr>
        <tr>
            <th>天线型号 :</th>
            <td align="left">${tunelManual.linetype}</td>
            <th>覆盖类型:</th>
            <td align="left">${tunelManual.covertype}</td>
        </tr>
        <tr>
            <th>覆盖范围描述 :</th>
            <td align="left">${tunelManual.coverrangedesc}</td>
            <th>详细地址:</th>
            <td align="left">${tunelManual.address}</td>
        </tr>
        <tr>
            <th>海拔高度 :</th>
            <td align="left">${tunelManual.tunelHigh}</td>
            <th>信源设备数量:</th>
            <td align="left">${tunelManual.rrunum}</td>
        </tr>
        <tr>
            <th>信源设备安装位置 :</th>
            <td align="left">${tunelManual.rruaddress}</td>
            <th>下挂直放站数量::</th>
            <td align="left">${tunelManual.repeaternum}</td>
        </tr>
        <tr>
            <th>直放站安装位置 :</th>
            <td align="left">${tunelManual.repeateraddress}</td>
            <th>下挂干放数量:</th>
            <td align="left">${tunelManual.drynum}</td>
        </tr>
        <tr>
            <th>干放安装位置 :</th>
            <td align="left">${tunelManual.dryaddress}</td>
            <th>共建共享情况:</th>
            <td align="left">${tunelManual.shareCons.name}</td>
        </tr>
        <tr>
            <th>有无机房 :</th>
            <td align="left">${tunelManual.mchroomflag}</td>
            <th>机房面积:</th>
            <td align="left">${tunelManual.mchroonarea}</td>
        </tr>
        <tr>
            <th>对应信源设备的电表安装位置 :</th>
            <td align="left">${tunelManual.meteradress}</td>
            <th>建设时间（年月）:</th>
            <td align="left">${tunelManual.builddateStr}</td>
        </tr>
        <tr>
        <th>业主联系人 :</th>
        <td align="left">${tunelManual.owner}</td>
        <th>业主联系电话:</th>
        <td align="left">${tunelManual.ownertel}</td>
    </tr>
        <tr>
            <th>备注 :</th>
            <td align="left" colspan="3">${tunelManual.remark}</td>
        </tr>
    </table>
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