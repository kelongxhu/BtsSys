<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>天线库信息</title>
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
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>天线库信息</p>
    </div>
    <div class="content">
        <table class="table table-condensed">
            <tr>
                <th width="20%">天线厂家:</th>
                <td width="30%" align="left">${airFacCons.name}</td>
                <th width="20%">天线型号:</th>
                <td width="30%" align="left">${airLib.airModel}</td>
            </tr>
            <tr>
                <th>是否美化天线:</th>
                <td>
                    <c:if test="${airLib.prettifyFlag==1}">
                        是
                    </c:if>
                     <c:if test="${airLib.prettifyFlag==0}">
                        否
                    </c:if>
                </td>
                <th>是否可变电调:</th>
               <td>
                    <c:if test="${airLib.ivaryFlag==1}">
                        是
                    </c:if>
                     <c:if test="${airLib.ivaryFlag==0}">
                        否
                    </c:if>
                </td>
            </tr>
            <tr>
                <th>内置电下倾度数(度):</th>
                <td>${airLib.degree}</td>
                <th>频率范围(MHz)：</th>
                <td>${airLib.freq}</td>
            </tr>
            <tr>
                <th>频带宽度（单位MHz）:</th>
                <td>${airLib.trapWidth}</td>
                <th>天线增益（单位dBi）:</th>
                <td>${airLib.airPlus}</td>
            </tr>
            <tr>
                <th>天线类型:</th>
                <td>${airTypeCons.name}</td>
                <th>极化方式:</th>
                <td>${jhTypeCons.name}</td>
            </tr>
            <tr>
                <th> 水平波瓣（单位度）:</th>
                <td>${airLib.flatLobe}</td>
                <th>垂直波瓣（单位度）:</th>
                <td>${airLib.uplobe}</td>
            </tr>
            <tr>
                <th>输入阻抗(欧姆):</th>
                <td>${airLib.inputDrag}</td>
                <th>天线前后比（单位dB）:</th>
                <td>${airLib.wireScale}</td>
            </tr>
            <tr>
                <th> 驻波系数:</th>
                <td>${airLib.standingWave}</td>
                <th> 俯仰上旁瓣抑制（单位dB）:</th>
                <td>${airLib.pitchUp}</td>
            </tr>
             <tr>
                <th> 俯仰下旁瓣抑制（单位dB）:</th>
                <td>${airLib.standingWave}</td>
                <th> 备注:</th>
                <td>${airLib.remark}</td>
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