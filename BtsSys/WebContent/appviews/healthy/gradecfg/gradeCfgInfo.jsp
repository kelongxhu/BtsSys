<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>规则信息</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <script type="text/javascript">
        function edit() {
            window.location.href = "${ctx}/healthy/addCfgPg.action?id=" + ${wyRulecfg.id};
        }

        function back() {
            javascript: history.go(-1);
        }
    </script>

</head>
<body>
<div id="main_2">
    <!-- 标题 end-->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>评分规则信息</p>
    </div>
    <div class="content">
        <form class="form-horizontal">
            <fieldset class="inputs">
                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        规则类型:
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        ${wyRulecfg.gradeTypeStr}
                    </div>
                </div>

                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        规则条件:
                    </label>

                    <div class="controls" style="margin-left: 90px;">

                        <table class="table table-hover">
                            <tr>
                                <td>评分项</td>
                                <td>计算类型</td>
                                <td>计算符号</td>
                                <td>阀值A</td>
                                <td>阀值B</td>
                            </tr>
                            <c:forEach items="${wyRulecfg.wyRuletermList}" var="ruleterm">
                                <tr>
                                    <td>${ruleterm.wySubcfg.sub}_${ruleterm.wySubcfg.subchild}</td>
                                    <td>${ruleterm.dataTypeStr}</td>
                                    <td>${ruleterm.symbolStr}</td>
                                    <td>${ruleterm.value1}</td>
                                    <td>${ruleterm.value2}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        分数:
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        ${wyRulecfg.grade}
                    </div>
                </div>

                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        基站权值:
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        <span class="label label-info">A:&nbsp;${wyRulecfg.aweight}</span>
                        <span class="label label-info">B:&nbsp;${wyRulecfg.bweight}</span>
                        <span class="label label-info">C:&nbsp;${wyRulecfg.cweight}</span>
                        <span class="label label-info">D:&nbsp;${wyRulecfg.dweight}</span>
                    </div>
                </div>

                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        得分规则:
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        ${wyRulecfg.gradeflagStr}
                    </div>
                </div>
                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        标识:
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        ${wyRulecfg.statusStr}
                    </div>
                </div>
                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        规则建议:
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        ${wyRulecfg.suggest}
                    </div>
                </div>
                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        规则描述:
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                       ${wyRulecfg.ruledesc}
                    </div>
                </div>


            </fieldset>
            <fieldset class="form-actions_2" style="background-color: #EFF7FA;">
                <button class="btn btn-info" type="button" onclick="edit();"><i class="icon-ok icon-white"></i>编辑
                </button>
                <button class="btn btn-danger" type="reset" onclick="back();"><i class="icon-repeat"></i>返回</button>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>