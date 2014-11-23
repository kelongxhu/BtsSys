<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>专项定制</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">

    <style type="text/css">
    </style>
    <script type="text/javascript">
        var gridObj = null;
        var comBox4 = null;
        $(function () {
            //选择字段

            gridObj = {
                columns: [
                    {display: '规则分数', name: 'grade', width: 80, align: 'center'},
                    {display: '规则类型', name: 'gradetype', width: 80, align: 'center',
                        render: function (row) {
                            if (row.gradetype == 1) {
                                return "基础信息";
                            } else if (row.gradetype == 2) {
                                return "巡检信息";
                            } else if (row.gradetype == 3) {
                                return "告警信息"
                            } else if (row.gradetype == 4) {
                                return "无线指标"
                            }
                        }},
                    {display: '规则描述', name: 'ruledesc', width: 400, align: 'center'}
                ],
                rownumbers: true,
                showTitle: false,
                url: '${ctx}/healthyjson/specialRules.action',
                checkbox: true,
                isChecked: f_isChecked
            };

            var comBox3 = $("#ruleCfgsVal").ligerComboBox({
                width: 350,
                slide: false,
                selectBoxWidth: 600,
                selectBoxHeight: 180,
                valueFieldID: 'ruleCfgs',
                textField: 'id',
                valueField: 'id',
                grid: gridObj
            });


            $("#name").ligerTextBox({width: 350 });


        });


        function init(typeURL) {
            $.getJSON(typeURL,
                    function (data) {
                        comBox4.setData(data.Rows);
                    }
            );
        }


        function getGridOptions(url) {
            var gridObj2 = {
                columns: [
                    {display: '名称', name: 'cnName', width: 200, align: 'center'},
                    {display: '字段命名', name: 'enName', width: 200, align: 'center'}
                ],
                rownumbers: true,
                showTitle: false,
                url: url,
                checkbox: true,
                usePager: false,
                isChecked: f_isChecked
            };
            return gridObj2;
        }

        function f_isChecked(data) {
            var str = '${wySpeciallycfg.ruleCfgIds}';
            var ch = new Array;
            ch = str.split(",");
            var flag = false;
            for (var i = 0; i < ch.length; i++) {
                if (data.id == ch[i]) {
                    flag = true;
                    break;
                }
            }
            return flag;
        }


        $(function () {
            var v = $("#form1")
                    .validate(
                    {
                        submitHandler: function (form) {
                            //form.submit();
                            jQuery(form).ajaxSubmit(function (json) {
                                if (json.result == 1) {
                                    alert('操作成功!');
                                } else {
                                    alert('操作失败!');
                                }
                                window.location.href = "${ctx}/healthy/specialCfg.action";
                            });
                        },
                        errorPlacement: function (lable, element) {
                           // element.parent().ligerTip({ content: lable.html(), target: element[0] });
                            //lable.appendTo(element.parent());
                        },
                        success: function (lable) {
                            lable.ligerHideTip();
                            lable.remove();
                        }
                    });
        });


        function add() {
            $("#form1").submit();
        }

        function back() {
            javascript: history.go(-1);
        }

    </script>
</head>
<body>
<div id="main">
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">
            <c:if test="${editFlag==0}">
                专项定制
            </c:if>
            <c:if test="${editFlag==1}">
                专项编辑
            </c:if>
        </p>
    </div>
    <div class="content">

        <div style="height: 20px;"></div>
        <form method="post" name="form1" id="form1" class="form-horizontal"
              action="${ctx}/healthyjson/addSpecial.action">


            <fieldset class="inputs">
                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        <span style="color: red;">*</span>专项名称
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        <input type="text" name="wySpeciallycfg.name" value="${wySpeciallycfg.name}" class="required">
                        <input type="hidden" name="wySpeciallycfg.id" value="${wySpeciallycfg.id}">
                        <input type="hidden" name="editFlag" value="${editFlag}">
                    </div>
                </div>

                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        <span style="color: red;">*</span>专项规则
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        <input type="text" id="ruleCfgsVal" class="required" value="${wySpeciallycfg.ruleCfgIds}" style="height:11px;background-color:#FFFFFF;"/>
                        <input type="hidden" id="ruleCfgs" name="wySpeciallycfg.ruleCfgIds"
                               value="${wySpeciallycfg.ruleCfgIds}"/>

                    </div>
                </div>

                <div
                        class="string control-group optional stringish">
                    <label class="control-label" style="width: 80px;">
                        <span style="color: red;">*</span>描述
                    </label>

                    <div class="controls" style="margin-left: 90px;">
                        <textarea type="text" name="wySpeciallycfg.specdesc" style="width:500px;height:40px;" class="required">${wySpeciallycfg.specdesc}</textarea>
                    </div>
                </div>


            </fieldset>
            <fieldset class="form-actions_2" style="background-color: #EFF7FA;">
                <button class="btn btn-info" type="button" onclick="add();"><i class="icon-ok icon-white"></i>定制
                </button>
                <button class="btn btn-danger" type="reset" onclick="back();"><i class="icon-repeat"></i>返回</button>
            </fieldset>
        </form>
    </div>
</div>

</body>
</html>