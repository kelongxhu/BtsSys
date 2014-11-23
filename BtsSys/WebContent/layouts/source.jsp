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

    <title>中国电信</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/resources/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/resources/bootstrap/css/xadmin.main.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/resources/bootstrap/css/bootstrap-xadmin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%--
 关于图标如何改变其颜色

上面的分数(雷达)背景图标 提供3种颜色背景图片,实际操作做应该根据分数自行选择
1,这里有2中类型的图标,一种是使用自定义图票
如 :  background: url('${ctx}/layouts/image/leida-green.png'
background: url('${ctx}/layouts/image/leida-red.png'
background: url('${ctx}/layouts/image/leida-yellow.png'
同理的还有图标
qq-green.png
qq-red.png
qq-yellow.png

2,对于使用bootstrap图标
<i class="icon-key" style="color: green;"></i>形式的
可自行变更这个,图标会按照你设置颜色变化,如:
style="color: green;"
style="color: red;"
style="color: yellow;"--%>


<div class="container-fluid" id="body-content" style="padding:0">
    <div class="row-fluid">
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
            <div style="position: absolute;height: 15px;top: 22px;right:100px;z-index: 0;left:150px;">
                遵义-遵义--遵义县养龙司
                <span style="margin-right: 15px;color: blue;">评分情况</span>
            </div>
            <div class="row-fluid">
                <div class="span2">
                    <div style="background: url('${ctx}/layouts/image/leida-green.png') no-repeat;width: 114px;height: 114px;">
                        <div style="margin-left: auto;margin-right: auto;padding-top: 45px;color: #fff;font-weight: bold;font-size: 32px;text-align: center;">100分</div>
                    </div>
                </div>
                <div class="span10" style="position: relative;">
                    <div class="row-fluid">
                        <div class="span3">
                            <div style="background: url('${ctx}/layouts/image/qq-green.png') no-repeat;width: 57px;height: 57px;margin-top: 28px;">
                                <div style="margin-left: auto;margin-right: auto;padding-top: 20px;color: #fff;font-weight: bold;font-size: 12px;text-align: center;">100分</div>
                            </div>
                            <div style="font-size: 12px;margin-left:5px;">基础数据</div>
                        </div>
                        <div class="span3">
                            <div style="background: url('${ctx}/layouts/image/qq-green.png') no-repeat;width: 57px;height: 57px;margin-top: 28px;">
                                <div style="margin-left: auto;margin-right: auto;padding-top: 20px;color: #fff;font-weight: bold;font-size: 12px;text-align: center;">100分</div>
                            </div>
                            <div style="font-size: 12px;margin-left:5px;">巡检数据</div>
                        </div>
                        <div class="span3">
                            <div style="background: url('${ctx}/layouts/image/qq-green.png') no-repeat;width: 57px;height: 57px;margin-top: 28px;">
                                <div style="margin-left: auto;margin-right: auto;padding-top: 20px;color: #fff;font-weight: bold;font-size: 12px;text-align: center;">100分</div>
                            </div>
                            <div style="font-size: 12px;margin-left:5px;">告警数据</div>
                        </div>
                        <div class="span3">
                            <div style="background: url('${ctx}/layouts/image/qq-green.png') no-repeat;width: 57px;height: 57px;margin-top: 28px;">
                                <div style="margin-left: auto;margin-right: auto;padding-top: 20px;color: #fff;font-weight: bold;font-size: 12px;text-align: center;">100分</div>
                            </div>
                            <div style="font-size: 12px;margin-left:5px;">无线指标</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span12 boxtree" style="padding:20px;">
            <div id="1" class="box well" style="position: relative; left: 0px; top: 0px;">
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

            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/resources/bootstrap/js/xadmin.plugin.portal.js"></script>
    <script type="text/javascript" src="${ctx}/resources/highcharts/js/highcharts.src.js"></script>
</body>
</html>
