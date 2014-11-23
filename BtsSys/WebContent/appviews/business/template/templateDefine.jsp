<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>模板定制</title>
    <%@ include file="/appviews/common/tag.jsp" %>

    <style type="text/css">
    </style>
    <script type="text/javascript">
        var gridObj = null;
        var comBox4 =null;
        $(function() {

            //是否共享
            var comBox2 = $("#shareFlagVal").ligerComboBox({
                data: [
                    { text: '共享', id: '1' },
                    { text: '不共享', id: '0' }
                ],
                width : 200,
                selectBoxWidth: 200,
                valueFieldID: 'shareFlag'
            });
            comBox2.selectValue('${template.shareflag}');

            //选择字段

            var comBox3 = $("#columnsConfigsVal").ligerComboBox({
                width : 350,
                slide: false,
                selectBoxWidth: 600,
                selectBoxHeight:180,
                valueFieldID: 'columnsConfigs',
                textField: 'cnName',
                valueField: 'id',
                grid: null
            });

            //模板类型
            var comBox1 = $("#typeIdVal").ligerComboBox({
                data: [
                    { text: '物理站点', id: '1' },
                    { text: '纯BBU', id: '2' },
                    { text: '室分', id: '3' },
                    { text: '小区', id: '4' }
                ],
                width : 200,
                selectBoxWidth: 200,
                valueFieldID: 'typeId',
                onSelected:function(value, text) {
                    var url = encodeURI("${ctx}/businessjson/columnsList.action?typeId=" + value);
                     gridObj = getGridOptions(url);
                     comBox3.setGrid(gridObj);
                }
            });
            if ('${template.type}' != '') {
                comBox1.selectValue('${template.type}');
            }

             //是否共享
            var comBox4 = $("#useFlagVal").ligerComboBox({
                data: [
                    { text: '导出模板', id: '0' },
                    { text: '统计模板', id: '1' }
                ],
                width : 200,
                selectBoxWidth: 200,
                valueFieldID: 'useFlag'
            });
            comBox4.selectValue('${template.useFlag}');

            $("#name").ligerTextBox({width : 350 });


            //test


            /*
            var columns1 = [
                {header: '名称', name: 'cnName', width: 100, align: 'center'},
                {header: '字段命名', name: 'enName', width: 100, align: 'center'}
            ];

           comBox4 = $("#columnsConfigsVal").ligerComboBox({
                           data:null,
                           width : 350,
                           slide: false,
                           selectBoxWidth: 600,
                           selectBoxHeight:180,
                           valueFieldID: 'columnsConfigs',
                           textField: 'cnName',
                           valueField: 'id',
                           columns: columns1,
                           isMultiSelect: true,
                           isShowCheckBox: true
                       });  */


           /*
            $("#columnsConfigsVal").ligerComboBox({
                  onBeforeOpen: selectDilog,
                  width:200
              }); */


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
                    {display:'名称',name:'cnName',width : 200,align:'center'},
                    {display:'字段命名',name:'enName',width : 200,align:'center'}
                ],
                rownumbers:true,
                showTitle : false,
                url:url,
                checkbox : true,
                usePager:false,
                isChecked: f_isChecked
            };
            return gridObj2;
        }

        function f_isChecked(data) {
            var str = '${template.columnsConfigs}';
            var ch = new Array;
            ch = str.split(";");
            var flag = false;
            for (var i = 0; i < ch.length; i++) {
                if (data.id == ch[i]) {
                    flag = true;
                    break;
                }
            }
            return flag;
        }


        $(function() {
            var v = $("#form1")
                    .validate(
                    {
                        submitHandler : function(form) {
                            //form.submit();
                            jQuery(form).ajaxSubmit(function(json) {
                                if (json.result == 1) {
                                    alert('操作成功!');
                                } else {
                                    alert('操作失败!');
                                }
                                window.location.href = "${ctx}/business/template.action";
                            });
                        },
                        errorPlacement : function(lable, element) {
                            element.parent().ligerTip({ content: lable.html(), target: element[0] });
                            //lable.appendTo(element.parent());
                        },
                        success : function(lable) {
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


        function selectDilog() {
            var typeId = $("#typeId").val();
            var typeName = $("#typeIdVal").val();
            var title = "定制" + typeName + "模板字段";
            $.ligerDialog.open({
                height: 500,
                url: '${ctx}/business/columns.action?typeId=' + typeId,
                width: 1000,
                name: 'columns',
                title: title,
                showMax: true,
                showToggle: true,
                showMin: true,
                isResize: true,
                isHidden: false
            });
        }


    </script>
</head>
<body>
<div id="main">
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">
            <c:if test="${editFlag==0}">
            模板定制
            </c:if>
            <c:if test="${editFlag==1}">
            模板编辑
            </c:if>
        </p>
    </div>
    <div class="content_2">

        <div style="height: 20px;"></div>
        <form method="post" name="form1" id="form1" action="${ctx}/businessjson/addTemplate.action">


            <table cellpadding="0" cellspacing="0" class="tab_1">
                <tr>
                    <td width="20%"><span style="color: red;">*</span>数据类型：</td>
                    <td width="70%">
                    <input type="text" id="typeIdVal" class="required"/>
                    <input type="hidden" id="typeId" name="template.type"/>
                    <input type="hidden" name="template.id" value="${template.id}"/>
                    <input type="hidden" name="editFlag" value="${editFlag}"/>
                    </td>
                </tr>
                <tr>
                    <td><span style="color: red;">*</span>模板类型：</td>
                    <td>
                        <input type="text" id="useFlagVal" class="required"/>
                        <input type="hidden" id="useFlag" name="template.useFlag"/>
                    </td>
                </tr>
                <tr>
                    <td><span style="color: red;">*</span>是否共享:</td>
                    <td>
                     <input type="text" id="shareFlagVal" class="required" style="height:12px;"/>
                     <input type="hidden" id="shareFlag" name="template.shareflag"/>
                    </td>
                </tr>
                  <tr>
                    <td><span style="color: red;">*</span>模板名称:</td>
                    <td>
                      <input type="text" id="name" name="template.name" class="required" value="${template.name}" style="width:350px"/>
                    </td>
                </tr>
                 <tr>
                    <td><span style="color: red;">*</span>定制字段:</td>
                    <td>
                      <input type="text" id="columnsConfigsVal" class="required" value="${columnsConfigName}" />
                      <input type="hidden" id="columnsConfigs" name="template.columnsConfigs"
                               value="${template.columnsConfigs}"/>
                        <br />
                        <div style="display: none;"/>

                    </td>
                </tr>
                </table>


        </form>
        <div style="height:50px;clear:both;"></div>
        <div class="form-actions_2">
            <button class="btn btn-info" type="button" onclick="add();"> <i class="icon-ok icon-white"></i>定制</button>
            <button class="btn btn-info" type="reset" onclick="back();"><i class="icon-repeat"></i>返回</button>
        </div>
    </div>
</div>

</body>
</html>