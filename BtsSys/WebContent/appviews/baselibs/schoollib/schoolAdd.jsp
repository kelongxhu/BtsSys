<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加校园库</title>
<%@ include file="/appviews/common/tag.jsp" %>
<script type="text/javascript" src="${ctx}/resources/js/ajaxfileupload.js"></script>

<script type="text/javascript">
$(function() {
    //选择地市
    $.post("${ctx}/schooljson/initCityTree.action", function(ajaxData, status) {
        var treeData = ajaxData.cityJson;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData = eval('(' + treeData + ')');
        treeCombox = $("#cityIdVal").ligerComboBox({
            width : 200,
            selectBoxWidth : 200,
            selectBoxHeight : 200,
            textField : 'text',
            valueField : 'id',
            valueFieldID : 'cityId',
            treeLeafOnly : false,
            tree : {
                data : treeData,
                checkbox:false
            }
        });
        if('${schoolLib.cityId}'!=''){
            treeCombox.selectValue('${schoolLib.cityId}');
        }
    });
    //初始化控件
    $("#name").ligerTextBox({width : 300 });
    $("#address").ligerTextBox({width : 300 });
    $("#cUsers").ligerTextBox({width : 150 });
    $("#doUsers").ligerTextBox({width : 150 });
    $("#cUsersPlan").ligerTextBox({width : 150 });
    $("#doUsersPlan").ligerTextBox({width : 150 });
    $("#mealType").ligerTextBox({width : 300 });
    $("#businessType").ligerTextBox({width : 300 });
    $("#schoolImage").ligerTextBox({width : 300 });
    $("#remark").ligerTextBox({width : 600,height:50 });
});


//初始化重要等级
var typeURL1 = "${ctx}/schooljson/cons.action?groupCode=SCHOOL_LEVEL";
var sl1;
$.getJSON(typeURL1,
        function(data) {
            sl1 = $("#schoolLevelVal").ligerComboBox({
                //isShowCheckBox: true,
                //isMultiSelect: true,
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'name',
                valueField : 'code',
                valueFieldID:'schoolLevel'
            });
            if('${schoolLib.schoolLevel}'!=''){
                sl1.selectValue('${schoolLib.schoolLevel}');
            }
        }
);
//初始化校园类型
var typeURL2 = "${ctx}/schooljson/cons.action?groupCode=SCHOOL_TYPE";
var sl2;
$.getJSON(typeURL2,
        function(data) {
            sl2 = $("#schoolTypeVal").ligerComboBox({
                //isShowCheckBox: true,
                //isMultiSelect: true,
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'name',
                valueField : 'code',
                valueFieldID:'schoolType'
            });
            if('${schoolLib.schoolType}'!=''){
                sl2.selectValue('${schoolLib.schoolType}');
            }
        }
);

//初始化校园整体覆盖方式
var typeURL3 = "${ctx}/schooljson/cons.action?groupCode=COVERAGE_TYPE";
var sl3;
$.getJSON(typeURL3,
        function(data) {
            sl3 = $("#coverageTypeVal").ligerComboBox({
                //isShowCheckBox: true,
                //isMultiSelect: true,
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'name',
                valueField : 'code',
                valueFieldID:'coverageType'
            });

        }
);
//初始化校园结构
var typeURL4 = "${ctx}/schooljson/cons.action?groupCode=STRUCTURAL_TYPE";
var sl4;
$.getJSON(typeURL4,
        function(data) {
            sl4 = $("#structuralTypeVal").ligerComboBox({
                //isShowCheckBox: true,
                //isMultiSelect: true,
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'name',
                valueField : 'code',
                valueFieldID:'structuralType'
            });
            if('${schoolLib.structuralType}'!=''){
                sl4.selectValue('${schoolLib.structuralType}');
            }
        }
);
//初始化WIFI覆盖
var typeURL5 = "${ctx}/schooljson/cons.action?groupCode=WIFI_TYPE";
var sl5;
$.getJSON(typeURL5,
        function(data) {
            sl5 = $("#wifiTypeVal").ligerComboBox({
                //isShowCheckBox: true,
                //isMultiSelect: true,
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'name',
                valueField : 'code',
                valueFieldID:'wifiType'
            });
            if('${schoolLib.wifiType}'!=''){
                sl5.selectValue('${schoolLib.wifiType}');
            }
        }
);


function add() {
    $("#form1").submit();
}

//注册表单验证
$(function() {
    var v = $("#form1")
            .validate(
            {
                submitHandler : function(form) {
                    jQuery(form).ajaxSubmit(function(json) {
                        if (json.result == 1) {
                            alert('操作成功!');
                        } else {
                            alert('操作失败!');
                        }
                        //跳转
                        window.location.href = "${ctx}/school/schoolLib.action";
                    });
                },
                errorPlacement : function(lable, element) {
                    element.parent().ligerTip({ content: lable.html(), target: element[0] });
                },
                success : function(lable) {
                    lable.ligerHideTip();
                    lable.remove();
                }
            });
});


var upDialog;

function up() {
    upDialog = $.ligerDialog.open({ height : 150,width : 300,target: $("#forUploader"),title : '导入楼宇分布图' });
}


function upImage() {
    var fileName = document.getElementById("file").value;
    if (fileName.length < 1) {
        alert("上传图片不能为空!");
        return;
    }
    var type = fileName.substring(fileName.lastIndexOf(".") + 1);
    if (!(type == "jpg")) {
        alert("请上传jpg类型图片!");
        return;
    }

    $.ajaxFileUpload({
        url:'${ctx}/schooljson/upImage.action',
        secureuri:false,
        fileElementId:'file',
        dataType: 'text',
        type: "post",
        cache: false,
        timeout: 5000,
        success: function (data, status) {
            if (data.indexOf("error") >= 0) {
                alert("文件上传失败");
            } else {
                var mmsDo = eval("(" + data + ")");
                var sc = mmsDo.result;
                if (sc == "1") {
                    address = mmsDo.address;
                    alert("上传成功");
                    $("#schoolImage").val(address);
                    upDialog.close();
                } else {
                    alert("上传失败");
                }
            }
        },
        error: function (data, status, e) {
            alert(e);
        }
    });
}

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
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"/>
            <c:if test="${editFlag==0}">
            增加校园库信息
            </c:if>
            <c:if test="${editFlag==1}">
            编辑校园库信息
            </c:if>
        </p>
    </div>
    <div class="content">
        <form method="post" name="form1" id="form1" action="${ctx}/schooljson/addSchoolLib.action">
            <table cellpadding="0" cellspacing="0" class="tab_1">
                <tr>
                    <th width="150px"><span style="color: red;">*</span>地市:</th>
                    <td>
                        <input type="text" id="cityIdVal" class="required"/>
                        <input name="schoolLib.cityId" type="hidden" id="cityId"/>
                        <input name="schoolLib.id" type="hidden" value="${schoolLib.id}">
                        <input name="editFlag" type="hidden" value="${editFlag}">
                    </td>
                    <th><span style="color: red;">*</span>名称：</th>
                    <td colspan="3">
                        <input name="schoolLib.name" id="name" type="text" class="required" value="${schoolLib.name}"/>
                    </td>
                </tr>
                <tr>
                    <th><span style="color: red;">*</span>地址：</th>
                    <td colspan="3">
                        <input name="schoolLib.address" id="address" type="text" class="{required:true}" value="${schoolLib.address}"/>
                    </td>

                </tr>
                <tr>
                    <th><span style="color: red;">*</span>重要等级:</th>
                    <td colspan="3">
                        <div style="float:left;">
                            <input name="schoolLevelVal" type="text" id="schoolLevelVal" class="{required:true}"/>
                            <input name="schoolLib.schoolLevel" type="hidden" id="schoolLevel"/>
                        </div>
                        <div style="float:left;padding: 5px;">
					<span style="color: red;font-size:10px;">
					(注：A:1500用户以上或开通“翼机通”业务; B:用户500~1500; C:其他类)
					</span>
                        </div>
                    </td>
                </tr>


                <tr>
                    <th><span style="color: red;">*</span>校园类型：</th>
                    <td>
                        <input name="schoolTypeVal" type="text" id="schoolTypeVal" class="{required:true}"/>
                        <input name="schoolLib.schoolType" type="hidden" id="schoolType"/>
                    </td>
                    <th><span style="color: red;">*</span>校园整体覆盖方式：</th>
                    <td>
                        <input name="schoolLib.coverageType" type="text" id="coverageTypeVal" class="{required:true}" value="${schoolLib.coverageType}"/>
                        <input type="hidden" id="coverageType"/>
                    </td>
                </tr>

                <tr>
                    <th><span style="color: red;">*</span>校园结构:</th>
                    <td>
                        <input name="structuralTypeVal" type="text" id="structuralTypeVal" class="{required:true}"/>
                        <input name="schoolLib.structuralType" type="hidden" id="structuralType"/>
                    </td>
                    <th><span style="color: red;">*</span>WIFI覆盖：</th>
                    <td>
                        <input name="wifiTypeVal" type="text" id="wifiTypeVal" class="{required:true}"/>
                        <input name="schoolLib.wifiType" type="hidden" id="wifiType"/>
                    </td>
                </tr>
                <tr>
                    <th>C网用户数量：</th>
                    <td>
                        <input name="schoolLib.cusers" type="text" id="cUsers" class="{digits:true}" value="${schoolLib.cusers}"/>
                    </td>
                    <th>DO用户数量：</th>
                    <td>
                        <input name="schoolLib.doUsers" type="text" id="doUsers" class="{digits:true}" value="${schoolLib.doUsers}"/>
                    </td>
                </tr>
                <tr>
                    <th>计划发展C网用户数量：</th>
                    <td>
                        <input name="schoolLib.cusersPlan" type="text" id="cUsersPlan" class="{digits:true}" value="${schoolLib.cusersPlan}"/>
                    </td>
                    <th>计划发展DO用户数量：</th>
                    <td>
                        <input name="schoolLib.doUsersPlan" type="text" id="doUsersPlan"  class="{digits:true}" value="${schoolLib.doUsersPlan}"/>
                    </td>
                </tr>
                <tr>
                    <th><span style="color: red;">*</span>套餐类型：</th>
                    <td colspan="3">
                        <div style="float:left;">
                            <input name="schoolLib.mealType" type="text" id="mealType" class="{required:true}" value="${schoolLib.mealType}"/>
                        </div>
                        <div style="float:left;padding: 5px;">
					<span style="color: red;font-size:10px;">
					(注：如有多个套餐按用户数量从多到少用逗号分隔填写)
					</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th><span style="color: red;">*</span>业务特点：</th>
                    <td colspan="3" valign="middle">
                        <div style="float:left;">
                            <input name="schoolLib.businessType" type="text" id="businessType" class="{required:true}" value="${schoolLib.businessType}"/>
                        </div>
                        <div style="float:left;padding: 5px,5px; width:350px">
                            <span style="color:red;font-size:10px;">(注：1、电脑使用受校方限制2、校园无宽带入户3、全部为1x用户 4、1x用户为主，Do用户为辅5、1x和Do用户并重 6、其他特点描述)</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>楼宇分布图：</th>
                    <td colspan="3">
                        <div style="float:left;">
                            <input type='text' name='schoolLib.schoolImage' id="schoolImage" readonly="readonly" value="${schoolLib.schoolImage}"/>
                        </div>
                        <div style="float:left;padding: 5px,5px; width:350px">
                            <input class="btn btn-mini btn-success" type="button" onclick="up()" value="上传"/>
                            <span style="color:red;font-size:10px;">(注：统一为JPG格式图片,名称格式如：南京_东南大学_浦口校区.jpg)</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>备注：</th>
                    <td colspan="3">
                        <textarea name="schoolLib.remark" id="remark">${schoolLib.remark}</textarea>
                    </td>
                </tr>
            </table>
             <div class="form-actions_2">
                <button class="btn btn-info" type="button" onclick="add();">
                    <i class="icon-ok icon-white"></i>
                    保存
                </button>
                <button class="btn" type="reset" onclick="back();">
                    <i class="icon-repeat"></i>
                    返回
                </button>
           </div>
        </form>
    </div>
</div>


<!-- 选择文件DIV -->
<div id="forUploader" style="display:none">
    <table width="100%" border="0" align="center" cellpadding="4" cellspacing="4" bordercolor="#666666">
        <tr>
            <th>选择文件:</th>
            <td align="left">
                <input type='file' style="width: 200px" name='file' id='file'/>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input class="btn btn-small btn-success" type="button" onclick="upImage()" value="上传"/>
            </td>
        </tr>
    </table>
</div>


</body>
</html>