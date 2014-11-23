<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>

    <title>评分详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${ctx}/layouts/css/main.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/layouts/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/ligerUI/1.1.9/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/ligerUI/1.1.9/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.8.2.min.js"></script>


    <script src="${ctx}/resources/ligerUI/1.1.9/js/core/base.js" type="text/javascript"></script>
    <script src="${ctx}/resources/ligerUI/1.1.9/js/ligerui.min.js" type="text/javascript"></script>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/resources/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/resources/bootstrap/css/xadmin.main.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/resources/bootstrap/css/bootstrap-xadmin.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript">
        var rules = [
            {
                color: "green",
                minGrade: 80,
                maxGrade: 100
            },
            {
                color: "yellow",
                minGrade: 60,
                maxGrade: 79
            },
            {
                color: "red",
                minGrade: 0,
                maxGrade: 59
            }
        ];
        $(function(){

            $("#totalGrade").html(getGrade('${wyGrade.totalGrade}'));
            $("#infoGrade").html(getGrade('${wyGrade.infoGrade}'));
            $("#inspGrade").html(getGrade('${wyGrade.inspGrade}'));
            $("#alarmGrade").html(getGrade('${wyGrade.alarmGrade}'));
            $("#wirelessGrade").html(getGrade('${wyGrade.wirelessGrade}'));
            initGradeDetail();
            showIndicateGradeTypeDetail('${gradeType}');
            $("#grade div[gradeType]").click(function(){
                showIndicateGradeTypeDetail($(this).attr("gradeType"));
            });
        });
        function getGrade(grade){
            if(!grade) grade = 0;
            else grade = parseFloat(grade);
            var gradeObj = $("<span></span>");
            var inRule = false;
            for(var i in rules){
                var rule = rules[i];
                if(grade < rule['minGrade'] || grade > rule['maxGrade']) continue;
                else{
                    gradeObj.css("color", rule['color']);
                    inRule = true;
                    break;
                }
            }
            if(!inRule){
                if(grade < 0) gradeObj.css("color", "red");
                else if(grade > 100) gradeObj.css("color", "green");
            }
            gradeObj.append(grade + '分');
            return gradeObj;
        }
        function initGradeDetail(){
            var datas = [
                            {
                                name: '基础数据得分',
                                gradeType: '1'
                            },
                            {
                                name: '巡检数据得分',
                                gradeType: '2'
                            },
                            {
                                name: '告警数据得分',
                                gradeType: '3'
                            },
                            {
                                name: '无线指标得分',
                                gradeType: '4'
                            }
            ];
            var wyRulecfgList = ${wyGrade.wyRulecfgList};
            for(var i in datas){
                var data = datas[i];
                var div = $('<div class="box well" style="position: relative; left: 0; top: 0;"></div>');
                div.attr("gradeType", data["gradeType"]);
                var content = $('<div class="box-content " style="display: block;padding: 0 15px 0 15px;"></div>');
                var ul = $('<ul></ul>');
                content.append(ul);
                var rulecfgCount = 0;
                var totalRulecfgGrade = 0;
                for(var j in wyRulecfgList){
                    var gradeType = wyRulecfgList[j]['gradetype'];
                    if(gradeType != data['gradeType']) continue;
                    var li = $(
                            '<li style="padding-top:5px;">' +
                                '<i class="icon-key" style="color: green;"></i>' +
                                '<i class="icon icon-ok-circle"></i>' +
                                wyRulecfgList[j]['ruledesc'] + '<span style="float:right;margin-right: 20px;">' + wyRulecfgList[j]['grade'] + '分</span>' +
                            '</li>'
                    );
                    ul.append(li);
                    rulecfgCount++;
                    totalRulecfgGrade += wyRulecfgList[j]['grade'];
                }
                var title = $(
                    '<h4 class="box-title">' +
                        '<i class="icon chevron icon-chevron-up" style="color: green;"></i>' +
                        '<div style="float: right;margin-right: 10px;">共<span style="color: blue;margin: 0 4px 0 4px;">' + rulecfgCount + '</span>项,<span style="color: blue;margin: 0 4px 0 4px;">' + (100-totalRulecfgGrade) + '</span>分</div>' +
                        '<i class="icon-star" style=" margin-right: 7px;color: green;"></i>' +
                        data['name'] +
                    '</h4>'
                );
                div.append(title);
                div.append(content);
                $("#gradeDetail").append(div);
                $("#gradeDetail").css("height", $("body").height() - $("#head").height());
            }
        }
        function showIndicateGradeTypeDetail(gradeType){
            $("#gradeDetail > div").each(function(i){
                if(gradeType == '0') $(this).show();
                else if($(this).attr("gradeType") != gradeType) $(this).hide();
                else $(this).show();
            });
            $("#grade div[gradeType]").each(function(i){
                var color = $(this).attr("gradeType") == gradeType ? 'red' : 'green';
                var type = $(this).attr("gradeType") == '0' ? 'leida' : 'qq';
                $(this).css({background: "url('${ctx}/layouts/image/" + type + "-" + color + ".png')"});
            });
        }
        function back(obj){
            window.location.href = '${ctx}/healthy/showGradeList';
            $(obj).attr("disabled", true);
        }
    </script>
</head>
<body>
    <div class="container-fluid" id="body-content" style="padding:0">
    <div class="row-fluid" id="head">
        <div class="span12" style="padding: 20px;  background-color: #F8F8F8;
  background-image: -moz-linear-gradient(top, #FDFDFD, #F6F6F6);
  background-image: -ms-linear-gradient(top, #FDFDFD, #F6F6F6);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#FDFDFD), to(#F6F6F6));
  background-image: -webkit-linear-gradient(top, #FDFDFD, #F6F6F6);
  background-image: -o-linear-gradient(top, #FDFDFD, #F6F6F6);
  background-image: linear-gradient(top, #FDFDFD, #F6F6F6);
   border-bottom: 1px solid #D6D6D6;">
            <div style="position: absolute;height: 15px;top: 72px;right:100px;z-index: 0;left:150px;background-color: #F6F2F2;
  background-image: -moz-linear-gradient(top, #FDFDFD, #F6F2F2);
  background-image: -ms-linear-gradient(top, #FDFDFD, #F6F2F2);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#FDFDFD), to(#F6F2F2));
  background-image: -webkit-linear-gradient(top, #FDFDFD, #F6F2F2);
  background-image: -o-linear-gradient(top, #FDFDFD, #F6F2F2);
  background-image: linear-gradient(top, #FDFDFD, #F6F2F2);
  border-radius: 15px;
   border: 1px solid #D6D6D6;"></div>
            <div style="position: absolute;height: 15px;top: 22px;right:10px;z-index: 1000;left:150px;">
                ${wyGrade.bts.name}
                <span style="margin-right: 15px;color: blue;">评分情况</span>
                <span style="float:right;"><a class="btn" href="#" onclick="back(this);"><i class="icon-share-alt"></i>返回</a></span>
            </div>
            <div id="grade" class="row-fluid">
                <div class="span2">
                    <div gradeType="0" style="background: url('${ctx}/layouts/image/leida-green.png') no-repeat;width: 114px;height: 114px;">
                        <div id="totalGrade" style="margin-left: auto;margin-right: auto;padding-top: 45px;color: #fff;font-weight: bold;font-size: 32px;text-align: center;"></div>
                    </div>
                </div>
                <div class="span10" style="position: relative;">
                    <div class="row-fluid">
                        <div class="span3">
                            <div gradeType="1" style="background: url('${ctx}/layouts/image/qq-green.png') no-repeat;width: 57px;height: 57px;margin-top: 28px;">
                                <div id="infoGrade" style="margin-left: auto;margin-right: auto;padding-top: 20px;color: #fff;font-weight: bold;font-size: 12px;text-align: center;"></div>
                            </div>
                            <div style="font-size: 12px;margin-left:5px;">基础数据</div>
                        </div>
                        <div class="span3">
                            <div gradeType="2" style="background: url('${ctx}/layouts/image/qq-green.png') no-repeat;width: 57px;height: 57px;margin-top: 28px;">
                                <div id="inspGrade" style="margin-left: auto;margin-right: auto;padding-top: 20px;color: #fff;font-weight: bold;font-size: 12px;text-align: center;"></div>
                            </div>
                            <div style="font-size: 12px;margin-left:5px;">巡检数据</div>
                        </div>
                        <div class="span3">
                            <div gradeType="3" style="background: url('${ctx}/layouts/image/qq-green.png') no-repeat;width: 57px;height: 57px;margin-top: 28px;">
                                <div id="alarmGrade" style="margin-left: auto;margin-right: auto;padding-top: 20px;color: #fff;font-weight: bold;font-size: 12px;text-align: center;"></div>
                            </div>
                            <div style="font-size: 12px;margin-left:5px;">告警数据</div>
                        </div>
                        <div class="span3">
                            <div gradeType="4" style="background: url('${ctx}/layouts/image/qq-green.png') no-repeat;width: 57px;height: 57px;margin-top: 28px;">
                                <div id="wirelessGrade" style="margin-left: auto;margin-right: auto;padding-top: 20px;color: #fff;font-weight: bold;font-size: 12px;text-align: center;"></div>
                            </div>
                            <div style="font-size: 12px;margin-left:5px;">无线指标</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row-fluid">
        <div id="gradeDetail" class="span12 boxtree" style="padding:20px;overflow: auto;">
            <%--<div id="1" class="box well" style="position: relative; left: 0px; top: 0px;">
                <h4 class="box-title">
                    <i class="icon chevron icon-chevron-up" style="color: green;"></i>
                    <div style="float: right;margin-right: 10px;">共<span style="color: blue;margin: 0 4px 0 4px;">5</span>项</div>
                    <i class="icon-star" style=" margin-right: 7px;color: green;"></i>
                    基础数据评分
                </h4>
                <div class="box-content " style="display: block;padding: 0px 15px 0px 15px;">
                     <ul>
                         <li style="padding-top:5px;">
                             <i class="icon-key" style="color: green;"></i>
                             <i class="icon icon-ok-circle"></i>
                             网页安全防护已开启
                         </li>
                         <li>
                             <i class="icon-key" style="color: green;"></i>
                             <i class="icon icon-ok-circle"></i>
                             聊天安全防护已开启
                         </li>
                         <li>
                             <i class="icon-key" style="color: green;"></i>
                             <i class="icon icon-ok-circle"></i>
                             U盘安全防护已开启
                         </li>
                         <li>
                             <i class="icon-key" style="color: red;"></i>
                             <i class="icon icon-remove-circle" style="color: red;"></i>
                             浏览器防护已开启
                         </li>
                         <li>
                             <i class="icon-key" style="color: green;"></i>
                             <i class="icon icon-ok-circle"></i>
                             系统自我保护已开启
                         </li>
                     </ul>
                </div>

            </div>
            <div id="2" class="box well" style="position: relative; left: 0px; top: 0px;">
                <h4 class="box-title">
                    <i class="icon chevron icon-chevron-up" style="color: green;"></i>
                    <div style="float: right;margin-right: 10px;">共<span style="color: blue;margin: 0 4px 0 4px;">5</span>项</div>
                    <i class="icon-star" style=" margin-right: 7px;color: green;"></i>
                    巡检数据评分
                </h4>
                <div class="box-content " style="display: block;padding: 0px 15px 0px 15px;">
                    <ul>
                        <li style="padding-top:5px;">
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            网页安全防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            聊天安全防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            U盘安全防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: red;"></i>
                            <i class="icon icon-remove-circle" style="color: red;"></i>
                            浏览器防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            系统自我保护已开启
                        </li>
                    </ul>
                </div>

            </div>
            <div id="3" class="box well" style="position: relative; left: 0px; top: 0px;">
                <h4 class="box-title">
                    <i class="icon chevron icon-chevron-up" style="color: green;"></i>
                    <div style="float: right;margin-right: 10px;">共<span style="color: blue;margin: 0 4px 0 4px;">5</span>项</div>
                    <i class="icon-star" style=" margin-right: 7px;color: green;"></i>
                    告警数据评分
                </h4>
                <div class="box-content " style="display: block;padding: 0px 15px 0px 15px;">
                    <ul>
                        <li style="padding-top:5px;">
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            网页安全防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            聊天安全防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            U盘安全防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: red;"></i>
                            <i class="icon icon-remove-circle" style="color: red;"></i>
                            浏览器防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            系统自我保护已开启
                        </li>
                    </ul>
                </div>

            </div>
            <div id="4" class="box well" style="position: relative; left: 0px; top: 0px;">
                <h4 class="box-title">
                    <i class="icon chevron icon-chevron-up" style="color: green;"></i>
                    <div style="float: right;margin-right: 10px;">共<span style="color: blue;margin: 0 4px 0 4px;">5</span>项</div>
                    <i class="icon-star" style=" margin-right: 7px;color: green;"></i>
                    无线指标评分
                </h4>
                <div class="box-content " style="display: block;padding: 0px 15px 0px 15px;">
                    <ul>
                        <li style="padding-top:5px;">
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            网页安全防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            聊天安全防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            U盘安全防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: red;"></i>
                            <i class="icon icon-remove-circle" style="color: red;"></i>
                            浏览器防护已开启
                        </li>
                        <li>
                            <i class="icon-key" style="color: green;"></i>
                            <i class="icon icon-ok-circle"></i>
                            系统自我保护已开启
                        </li>
                    </ul>
                </div>

            </div>--%>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/resources/bootstrap/js/xadmin.plugin.portal.js"></script>
    <script type="text/javascript" src="${ctx}/resources/highcharts/js/highcharts.src.js"></script>
</body>
</html>