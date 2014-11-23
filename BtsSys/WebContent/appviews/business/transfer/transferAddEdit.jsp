<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>传输节点添加/修改</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var v;
        $(function(){
            $("#name").ligerTextBox({width : 200});
            $("#longitude").ligerTextBox({width : 150});
            $("#latitude").ligerTextBox({width : 150});
            $("#remark").ligerTextBox({width : 400,height:50 });
            //初始化地市
            //初始化树控件
            $.post("${ctx}/schooljson/initCountryTree.action", function(ajaxData, status) {
                var treeData = ajaxData.cityJson;
                treeData = treeData.replace(/"children":\[\],/g, '');
                treeData = eval('(' + treeData + ')');
                var treeCombox = $("#countryIdVal").ligerComboBox({
                    width : 200,
                    selectBoxWidth : 200,
                    selectBoxHeight : 200,
                    textField : 'text',
                    valueField : 'id',
                    valueFieldID : 'countryId',
                    treeLeafOnly : true,
                    tree : {
                        data : treeData,
                        checkbox:false
                    }
                });
                var value = '${wyTransfernode.countryId}';
                if(value) treeCombox.selectValue(value);
            });
            //初始化所属类型
            var typeURL1 = "${ctx}/schooljson/cons.action?groupCode=TRANSFER";
            $.getJSON(typeURL1,
                    function(data) {
                        var l = $("#typeVal").ligerComboBox({
                            data : data.Rows,
                            width : 120,
                            selectBoxWidth: 120,
                            textField : 'name',
                            valueField : 'code',
                            valueFieldID:'type'
                        });
                        //由于没有用url获取数据,而是设置了数据，所以ligerComboBox的onSuccess方法失效，所以直接在线面写初始化值。
                        // 又因为如果用initValue，会导致该下拉获得光标而影响验证,所以没有使用initValue属性。
                        var value = '${wyTransfernode.type}';
                        if(value) {
                            l.selectValue(value);
                            $("#typeVal").blur();
                        }
                    }
            );
            v = $("#form1")
            .validate(
            {
                ignore: "",
                submitHandler : function(form) {
                    $(".form-actions_2 > .btn").attr("disabled", true);
                    jQuery(form).ajaxSubmit(function(json) {
                        alert(json.msg);
                        //跳转
                        if(json.result) window.location.href = '${ctx}/business/showTransferList';
                        else $(".form-actions_2 > .btn").attr("disabled", false);
                    });
                },
                errorPlacement : function(error, element) {
                    var parent = element.parent().parent();
                    var div = $("<div style='float: left;'></div>");
                    div.append(error);
                    div.insertAfter(parent);

                }
            });
        });
        function validateElement(obj){
            v.element($('#'+obj));
        }
        function back(){
            history.back();
        }
    </script>
</head>
<body>
<!-- 标题 -->
<div id="main_2">
    <!-- 标题 end-->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"/>
            <c:if test="${empty wyTransfernode}">
            增加传输节点
            </c:if>
            <c:if test="${!empty wyTransfernode}">
            编辑传输节点
            </c:if>
        </p>
    </div>
    <div class="content">
        <form method="post" name="form1" id="form1" action="${ctx}/businessjson/transferAddEdit.action">
            <table cellpadding="0" cellspacing="0" class="tab_1">
                <tr>
                    <th width="100px"><span style="color: red;">*</span>站名：</th>
                    <td colspan="3">
                        <div style="float: left">
                            <input name="wyTransfernode.name" id="name" type="text" class="required" value="${wyTransfernode.name}"/>
                        </div>
                    </td>
                </tr>
                <tr><td colspan="4">&nbsp;</td></tr>
                <tr>
                    <th><span style="color: red;">*</span>地区:</th>
                    <td width="400px;">
                        <div style="float: left">
                            <input type="text" onblur="validateElement('countryId');" id="countryIdVal" />
                            <input name="wyTransfernode.countryId" class="required" type="hidden" id="countryId"/>
                            <input name="wyTransfernode.id" type="hidden" value="${wyTransfernode.id}">
                        </div>
                    </td>
                    <th width="150px"><span style="color: red;">*</span>所属类型：</th>
                    <td>
                        <div style="float: left">
                            <input type="text" onblur="validateElement('type');" id="typeVal"/>
                            <input name="wyTransfernode.type" type="hidden" class="required" id="type"/>
                        </div>
                    </td>
                </tr>
                <tr><td colspan="4">&nbsp;</td></tr>
                <tr>
                    <th><span style="color: red;">*</span>经度：</th>
                    <td>
                        <div style="float: left">
                            <input name="wyTransfernode.longitude" id="longitude" type="text" class="{required: true, number:true, range: [0, 180]}" class="required" value="${wyTransfernode.longitude}"/>
                        </div>
                    </td>
                    <th><span style="color: red;">*</span>纬度：</th>
                    <td>
                        <div style="float: left">
                            <input name="wyTransfernode.latitude" id="latitude" type="text" class="{required: true, number:true, range: [0, 180]}" class="required" value="${wyTransfernode.latitude}"/>
                        </div>
                    </td>
                </tr>
                <tr><td colspan="4">&nbsp;</td></tr>
                <tr>
                    <th>备注：</th>
                    <td colspan="3">
                        <div style="float: left">
                            <textarea id="remark" name="wyTransfernode.remark">${wyTransfernode.remark}</textarea>
                        </div>
                    </td>
                </tr>
            </table>
             <div class="form-actions_2">
                <button class="btn btn-info" type="submit">
                    <i class="icon-ok icon-white"></i>
                    保存
                </button>
                <button class="btn" type="button" onclick="back();">
                    <i class="icon-repeat"></i>
                    返回
                </button>
           </div>
        </form>
    </div>
</div>
</body>
</html>