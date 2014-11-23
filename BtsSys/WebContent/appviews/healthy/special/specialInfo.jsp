<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>专项信息</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <script type="text/javascript">
        function edit() {
            window.location.href = "${ctx}/healthy/addPage.action?id=" + ${wySpeciallycfg.id};
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
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>专项信息</p>
    </div>
    <div class="content">
        <form class="form-horizontal">
            <fieldset class="inputs">
                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        专项名称:
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        ${wySpeciallycfg.name}
                    </div>
                </div>
                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        描述
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        ${wySpeciallycfg.specdesc}
                    </div>
                </div>

                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        专项规则
                    </label>

                    <div class="controls" style="margin-left: 90px;">

                        <table class="table table-hover">
                            <tr>
                                <td>规则分数</td>
                                <td>规则类型</td>
                                <td>规则描述</td>
                                <td>规则建议</td>
                            </tr>
                            <c:forEach items="${wySpeciallycfg.wyRulecfgList}" var="rule">
                                <tr>
                                    <td>${rule.grade}</td>
                                    <td>${rule.gradeTypeStr}</td>
                                    <td>${rule.ruledesc}</td>
                                    <td>${rule.suggest}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>


            </fieldset>
            <fieldset class="form-actions_2" style="background-color: #EFF7FA;">
                <button class="btn btn-info" type="button" onclick="edit();"><i class="icon-ok icon-white"></i>编辑
                </button>
                <button class="btn btn-info" type="reset" onclick="back();"><i class="icon-repeat"></i>返回</button>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>