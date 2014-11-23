<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
    </style>
    <script type="text/javascript">

        var manager;
        var gridObj = null;

        $(function () {
            initRuleCfg();
            var mm = "${wyRulecfg.gradetype}";
            intRuleCfgsGrid();
            if(mm!=null){
                flushGrid(mm);
            }
            $("#gradetype").change(function () {
                var gradeType = $(this).children('option:selected').val();
                getSumGrade(gradeType);
                flushGrid(gradeType);
            })
        });


        function getSumGrade(gradeType){
            var params = {
                subType: gradeType
            };
            $.getJSON("${ctx}/healthyjson/getSum.action",params,function(json){
                $("#sumGrade").html(json["sumGrade"]);
            });
        }

        function initRuleCfg() {
            //初始化页面值
            var mm = "${wyRulecfg.gradetype}";
            $("#gradetype" + " option[value='" + mm + "']").attr("selected", "selected");
            getSumGrade(mm);
            <c:forEach var="ruleCfg" varStatus="status" items="${wyRulecfg.wyRuletermList}">
            addParm();
            var i = '${status.count-1}';
            var comBoxManger = $("#sub" + i).ligerGetComboBoxManager();
            if (comBoxManger != null) {
                comBoxManger.setValue('${ruleCfg.subcfgid}');
            }
            $("#v" + i).val('${ruleCfg.value1}');
            $("#vv" + i).val('${ruleCfg.value2}');
            </c:forEach>
        }


        function intRuleCfgsGrid(url) {
            $("#toptoolbar").ligerToolBar({
                          items: [
                              { text: '增加', click: add2, icon: 'add'},
                              { text: '编辑', click: edit, icon: 'modify'},
                              { text: '删除', click: del, icon: 'delete'}
                          ]
                      });
            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display: '规则分数', name: 'grade', width: 80, align: 'center', totalSummary: {
                        render: function (suminf, column, cell) {
                            return '<div>总分：:' + suminf.sum + '</div>';
                        },
                        align: 'left'

                    }
                    },
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
                    {display: 'A级基站权值', name: 'aweight', width: 80, align: 'center'},
                    {display: 'B级基站权值', name: 'bweight', width: 80, align: 'center'},
                    {display: 'C级基站权值', name: 'cweight', width: 80, align: 'center'},
                    {display: 'D级基站权值', name: 'dweight', width: 80, align: 'center'},
                    {display: '规则描述', name: 'ruledesc', width: 180, align: 'center'},
                    {display: '规则建议', name: 'suggest', width: 180, align: 'center'},
                    {display: '规则范围', name: 'status', width: 100, align: 'center',
                        render: function (row) {
                            if (row.status == 1) {
                                return "评分";
                            } else if (row.status == 2) {
                                return "整改";
                            } else if (row.status == 3) {
                                return "评分/整改"
                            }
                        }}
                ],
                rownumbers: true,
                usePager: false,
                showTitle: false,
                pageSize: 50,
                pageSizeOptions: [50, 100],
                url: url,
                checkbox: true,
                width: '99.9%',
                height: 300
            });
        }

        //级联刷新grid
        function flushGrid(gradeType) {
            gridObj.setOptions({
                newPage: 1
            });
            gridObj.setOptions({
                url: encodeURI("${ctx}/healthyjson/ruleCfgListByType.action?subType=" + gradeType)
            });
            gridObj.loadData(); //加载数据
        }


        function fullCombox(id) {
            var subType = $("#gradetype").val();
            var typeURL1 = "${ctx}/healthyjson/subCfg.action?subType=" + subType;
            var sl1=null;
            $.getJSON(typeURL1,
                    function (data) {
                        sl1 = $("#sub" + id).ligerComboBox({
                            data: data.Rows,
                            width: 120,
                            selectBoxWidth: 200,
                            textField: 'subjectName',
                            valueField: 'id',
                            valueFieldID: 'subV' + id
                        });
                        <c:forEach var="ruleCfg" varStatus="status" items="${wyRulecfg.wyRuletermList}">
                        var i = '${status.count-1}';
                        if (id == i&&sl1!=null) {
                            sl1.setValue(${ruleCfg.subcfgid});
                        }
                        </c:forEach>
                    }
            );
        }

        function fullDataTypeCombox(id) {
            var combox1 = $("#dataType" + id).ligerComboBox({
                data: [
                    { text: '字符串', id: '1' },
                    { text: '数字', id: '2' },
                    { text: '日期', id: '3' }
                ],
                width: 120,
                selectBoxWidth: 200,
                valueFieldID: 'dataTypeV' + id
            });
            <c:forEach var="ruleCfg" varStatus="status" items="${wyRulecfg.wyRuletermList}">
            var i = '${status.count-1}';
            if (id == i&&combox1!=null) {
                combox1.setValue(${ruleCfg.dataType});
            }
            </c:forEach>
        }

        function fullSymbolCombox(id) {
            var typeURL1 = "${ctx}/schooljson/cons2.action?groupCode=SYMBOL";
            var sl2=null;
            $.getJSON(typeURL1,
                    function (data) {
                        sl2 = $("#symbol" + id).ligerComboBox({
                            data: data.Rows,
                            width: 120,
                            selectBoxWidth: 200,
                            textField: 'name',
                            valueField: 'code',
                            valueFieldID: 'symbolV' + id
                        });
                        <c:forEach var="ruleCfg" varStatus="status" items="${wyRulecfg.wyRuletermList}">
                        var i = '${status.count-1}';
                        if (id == i&&sl2!=null) {
                            sl2.setValue(${ruleCfg.symbol});
                        }
                        </c:forEach>
                    }
            );
        }

        function jy(index){
           var subVal=$("#sub"+index).val();
           var dataTypeVal=$("#dataType"+index).val();
           var symbolVal=$("#symbol"+index).val();
           var vVal=$("#v"+index).val();
            if (subVal == '') {
                $.ligerDialog.alert('请填写规则' + index + "的评分项！");
                return false;
            }
            if (dataTypeVal == '') {
                $.ligerDialog.alert('请填写规则' + index + "的计算类型！");
                return false;
            }
            if (symbolVal == '') {
                $.ligerDialog.alert('请填写规则' + index + "的符号类型！");
                return false;
            }
            if(vVal==''){
                $.ligerDialog.alert('请填写规则' + index + "的阀值A类型！");
                return false;
            }
           return true;
        }

        function addParm() {
            var subType = $("#gradetype").val();
            if (subType == '') {
                alert('请选择规则类型!');
                return;
            }
            var index = $("#cmdParmUI li").length;
            if(index>0){
                if(!jy(index-1));{
                    return;
                }
            }
            $("#cmdParmUI").append($("#cmdParmBak").html().replace(new RegExp("#parmindex#", 'g'), index));
            fullCombox(index);
            fullDataTypeCombox(index);
            fullSymbolCombox(index);
        }
        function cmdParmDel(element) {
            $(element).closest("li").find("input").each(function(i){
                window.liger.remove($(this).attr("id"));
            });
            $(element).closest("li").remove();

        }


        //提交


        $(function () {
            var v = $("#form1").validate(
                    {
                        ignore: "",
                        submitHandler: function (form) {
                            //form.submit();
                            jQuery(form).ajaxSubmit(function (json) {
                                if (json.result == 1) {
                                    alert('操作成功!');
                                } else {
                                    alert('操作失败!');
                                }
                                // parent.window.toSearch();
                                //window.location.href = "${ctx}/healthy/ruleCfg.action";
                                //   back();
                                var gradeType = $("#gradetype").val();
                                flushGrid(gradeType);
                            });
                        }
                    });
        });


        function add() {
            $("#form1").submit();
        }

        //返回
        function back(){
        	javascript: history.go(-1);
        }

        function back2() {
            parent.$.ligerDialog.close();
            parent.$(".l-dialog,.l-window-mask").remove();
        }

       //增加，清空控件
       function add2(){
           window.location.href = "${ctx}/healthy/addCfgPg.action";
       }
       function edit(){
        var rows = gridObj.getCheckedRows();
        var j = rows.length;
        if (j == 0) {
            $.ligerDialog.alert('请选择要编辑的数据！');
            return;
        } else if (j > 1) {
            $.ligerDialog.alert('请选择一条编辑！');
            return;
        }
        var id;
        $(rows).each(function () {
            id = this.id;
//            $("#ruleCfgId").val(this.id);
//            $("#editFlag").val(1);
//            $("#gradetype" + " option[value='" + this.gradetype + "']").attr("selected", "selected");
//            $("#grade").val(this.grade);
//            $("#aweight").val(this.aweight);
//            $("#bweight").val(this.bweight);
//            $("#cweight").val(this.cweight);
//            $("#dweight").val(this.dweight);
//            $("#gradeflag" + " option[value='" + this.gradeflag + "']").attr("selected", "selected");
//            $("#status" + " option[value='" + this.status + "']").attr("selected", "selected");
//            $("#ruledesc").val(this.ruledesc);
//            $("#suggest").val(this.suggest);
        });
           window.location.href = "${ctx}/healthy/addCfgPg.action?id=" + id;
       }
       function del(){
           var rows = gridObj.getCheckedRows();
                var str = "";
                $(rows).each(function () {
                    str += this.id + ",";
                });
                if (str.length == 0) {
                    $.ligerDialog.alert('请选择要删除的数据！');
                    return;
                } else {
                    str = str.substring(0, str.length - 1);
                }
                $.ligerDialog.confirm('确认删除', function (yes) {
                    var params = {
                        ids:str
                    };
                    $.getJSON('${ctx}/healthyjson/del.action', params, function (json) {
                        if (json.result==1) {
                            $.ligerDialog.alert('删除成功!');
                        } else {
                            $.ligerDialog.alert('删除失败！');
                        }
                        gridObj.loadData();
                    });
                });
       }

    </script>
</head>
<body>
<div id="main_2">
    <div style="margin-top: 20px">
     <%--<div style="height: 30px"></div>--%>
    <form id="form1" class="form-horizontal" action="${ctx}/healthyjson/addRuleCfg.action" method="POST">
        <fieldset class="inputs">
            <div
                    class="string control-group optional stringish">
                <label class="control-label" style="width: 80px;">
                    *规则类型
                </label>

                <div class="controls" style="margin-left: 90px;">
                    <div class="input-append">
                        <select name="wyRulecfg.gradetype" style="width:100px;"
                                class="required" id="gradetype">
                            <option value=""></option>
                            <option value="1">基础信息</option>
                            <option value="2">巡检信息</option>
                            <option value="3">告警信息</option>
                            <option value="4">无线指标</option>
                        </select>
                    </div>

                    <div gradeType="1" class="input-append"
                         style="float:right;background: url('${ctx}/layouts/image/qq-green.png') no-repeat;width: 57px;height: 57px;margin-top: -10px;margin-bottom:-10px;margin-right:10px;">
                        <div id="sumGrade"
                             style="margin-left: auto;margin-right: auto;padding-top: 20px;color: #fff;font-weight: bold;font-size: 16px;text-align: center;">
                        </div>
                    </div>
                    <input type="hidden" name="editFlag" value="${editFlag}" id="editFlag">
                    <input type="hidden" name="wyRulecfg.id" value="${wyRulecfg.id}" id="ruleCfgId">
                </div>
            </div>


            <div id="cmd_parm_input"
                 class="string control-group optional stringish">
                <label class="control-label" for="cmd_parm_input" style="width: 80px;">
                    *<a href="#" onclick="addParm();return false;">规则条件</a>
                </label>

                <div class="controls" style="margin-left: 90px;">
                    <div class="well" style="margin-bottom: 5px;padding: 5px;margin-top: 10px;">
                        <ul id="cmdParmUI" class="unstyled" style="margin-bottom: 0">

                        </ul>
                    </div>
                </div>
            </div>

            <div
                    class="string control-group optional stringish">
                <label class="control-label" style="width: 80px;">
                    *分数
                </label>

                <div class="controls" style="margin-left: 90px;">
                    <input type="text" style="width:100px;" id="grade"
                           name="wyRulecfg.grade" class="required number" value="${wyRulecfg.grade}">
                </div>
            </div>

            <div class="string control-group optional stringish">
                <label class="control-label" style="width: 80px;">
                    *基站权值
                </label>

                <div class="controls" style="margin-left: 90px;">
                    <div class="input-append">
                        <span class="add-on">A</span><input type="text" style="width:50px;" name="wyRulecfg.aweight"
                                                            class="required number" value="${wyRulecfg.aweight}" id="aweight">
                    </div>
                    <div class="input-append">
                        <span class="add-on">B</span><input type="text" style="width:50px;" name="wyRulecfg.bweight"
                                                            class="required number" value="${wyRulecfg.bweight}" id="bweight">
                    </div>
                    <div class="input-append">
                        <span class="add-on">C</span><input type="text" style="width:50px;" name="wyRulecfg.cweight"
                                                            class="required number" value="${wyRulecfg.cweight}" id="cweight">
                    </div>
                    <div class="input-append">
                        <span class="add-on">D</span><input type="text" style="width:50px;" name="wyRulecfg.dweight"
                                                            class="required number" value="${wyRulecfg.dweight}" id="dweight">
                    </div>
                </div>
            </div>


            <div
                    class="string control-group optional stringish">
                <label class="control-label" style="width: 80px;">
                    *得分规则
                </label>

                <div class="controls" style="margin-left: 90px;">
                    <select name="wyRulecfg.gradeflag" style="width:100px;"
                            class="required" id="gradeflag">
                        <option value="1">分数*权值</option>
                    </select>
                </div>
            </div>

            <div
                    class="string control-group optional stringish">
                <label class="control-label" style="width: 80px;">
                    *标识
                </label>

                <div class="controls" style="margin-left: 90px;">
                    <select name="wyRulecfg.status" style="width:100px;"
                            class="required" id="status">
                        <option value="1">评分</option>
                        <option value="2">整改</option>
                        <option value="3">评分/整改</option>
                    </select>
                </div>
            </div>


            <div
                    class="string control-group optional stringish">
                <label class="control-label" style="width: 80px;">
                    *规则描述
                </label>

                <div class="controls" style="margin-left: 90px;">
                    <textarea style="width: 500px;height: 40px"
                              name="wyRulecfg.ruledesc" id="ruledesc">${wyRulecfg.ruledesc}</textarea>
                </div>
            </div>

            <div
                    class="string control-group optional stringish">
                <label class="control-label" style="width: 80px;">
                    *规则建议
                </label>

                <div class="controls" style="margin-left: 90px;">
                    <textarea style="width: 500px;height: 40px" name="wyRulecfg.suggest" id="suggest">${wyRulecfg.suggest}</textarea>
                </div>
            </div>


        </fieldset>
        <fieldset class="form-actions_2" style="background-color: #EFF7FA;">
            <button class="btn btn-info" type="button" onclick="add();"><i class="icon-ok icon-white"></i>保存
                          </button>
            <button class="btn btn-danger" type="button" onclick="back();"><i class="icon-repeat"></i>返回</button>
        </fieldset>
    </form>

    <div id="toptoolbar"></div>
    <div id="maingrid"></div>
    </div>
</div>

<ul id="cmdParmBak" style="display: none;">
    <li style="margin-bottom: 3px;">
        <div class="form-inline">
            <div style="float:left;">
                评分项：
            </div>
            <div style="float:left;">
                <%--<select name="wyRulecfg.wyRuletermList[#parmindex#].subcfgid" style="width:100px;"--%>
                <%--class="required" id="sub#parmindex#">--%>
                <%--<option value=""></option>--%>
                <%--</select>--%>
                <input type="text" style="height:11px;background-color:#FFFFFF;"
                       id="sub#parmindex#">
                <input type="hidden" name="wyRulecfg.wyRuletermList[#parmindex#].subcfgid" id="subV#parmindex#">
            </div>
            <div style="float:left; margin-left: 15px">
                计算类型：
            </div>
            <div style="float:left;">
                <input type="text" style="height:11px;background-color:#FFFFFF;"
                       id="dataType#parmindex#">
                <input type="hidden" name="wyRulecfg.wyRuletermList[#parmindex#].dataType" id="dataTypeV#parmindex#">
            </div>
            &nbsp;&nbsp;&nbsp;
            <div style="float:left; margin-left: 15px">
                计算符号：
            </div>
            <div style="float: left">
                <input type="text" style="height:11px;background-color:#FFFFFF;"
                       id="symbol#parmindex#">
                <input type="hidden" name="wyRulecfg.wyRuletermList[#parmindex#].symbol" id="symbolV#parmindex#">
            </div>
            &nbsp;
            <span class="add-on">阀值A</span>：<input type="text" class="input-medium"
                                                   style="width:50px;height:12px;"
                                                   name="wyRulecfg.wyRuletermList[#parmindex#].value1"
                                                   id="v#parmindex#">
            &nbsp;
            <span class="add-on">阀值B</span>：<input type="text" class="input-medium"
                                                   style="width:50px;height:12px;"
                                                   name="wyRulecfg.wyRuletermList[#parmindex#].value2"
                                                   id="vv#parmindex#">
            &nbsp;

            <div class="btn-group" style="float:right;margin-top:5px;">
                <button class="btn-mini cmddel" type="button" style="padding: 0 3px;line-height: 10px;"
                        onclick="return cmdParmDel(this)">X
                </button>
            </div>
            <div style="clear:both;"></div>
        </div>
    </li>
</ul>
</body>
</html>