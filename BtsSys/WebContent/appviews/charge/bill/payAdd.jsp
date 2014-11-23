<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>基站缴费</title>
<%@ include file="/appviews/common/tag.jsp" %>
<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
 <script src="${ctx}/resources/uploadify/jquery.uploadify.js" type="text/javascript"></script>
 <link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css">
<style type="text/css">
</style>

<script type="text/javascript">

    function init(){
        var costType="${charge.costType}";
        $("#costType option[value='" + costType + "']").attr("selected", true);
        $("#costType").attr("disabled", "disabled");
        if(costType==3){
            $("#powerProperties").removeClass("noneCss").addClass("displayCss");
            $("#payTypeTd").removeClass("noneCss").addClass("displayCss");
            $("#PayTypeLable").removeClass("noneCss").addClass("displayCss");
            $("#baseDegree").addClass("required").addClass("number");
            $("#monthDegree").addClass("required").addClass("number");
            $("#payTypeTd").addClass("required");
        }
    }
$(function() {

    init();

    var v = $("#form1").validate(
            {
                ignore:"",
                submitHandler : function(form) {
                    //form.submit();
                    jQuery(form).ajaxSubmit(function(json) {
                        if (json.result == 1) {
                            alert('操作成功!');
                        } else {
                            alert('操作失败!');
                        }
                        window.location.href = "${ctx}/charge/pay.action";
                    });
                },
               errorPlacement : function(error, element) {
                   var arr=["propVal","covertypeVal","shareflagVal","mchroomflagVal","builddate"];
                   var id=element.attr("id");
                   var index=arr.indexOf(id);
                   if(index>-1){
                       var parent = element.parent().parent().parent();
                       var div = $("<div style='float: left;'></div>");
                       div.append(error);
                       div.insertAfter(parent);
                   }else{
                       error.appendTo(element.parent());
                   }
                }
               // success : function(lable) {
                 //   lable.ligerHideTip();
                 //   lable.remove();
                //}
            });
});


function add() {
    $("#form1").submit();
}

//返回
function back() {
    javascript: history.go(-1);
}


$(function() {
    $("#pz_uploadify").uploadify({
        method    : 'post',
        swf           : '${ctx}/resources/uploadify/uploadify.swf',
        uploader      : '${ctx}/chargejson/uploadFile.action;jsessionid=${pageContext.session.id}',
        cancelImg :  '${ctx}/resources/uploadify-cancel.png',
        fileObjName     : 'file',
        successTimeout:600,
        multi  : false,
        removeCompleted : false,
        fileSizeLimit : '10MB',
        buttonText      : '选择文件',
        height        : 25,
        width         : 70 ,
        auto :false,
        fileTypeDesc: 'All Files',//上传文件类型说明
        fileTypeExts: '*.*', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
        onInit   : function() {
            $("#pz_uploadify-queue").hide();
        },
        onSelect : function(file) {
            $("#proofFile").val(file.name);
        },
        onUploadProgress: function(file,bytesUploaded,bytesTotal,totalBytesUploaded,totalBytesTotal){
            $('#msg').html('已上传:'+(totalBytesTotal/bytesTotal)*100);
        },
        onUploadSuccess : function(file, data, response) {
            data = eval('(' + data + ')');
            var status=data["fileDTO"].status;
            if(status==1){
                $("#proofFile").val(data["fileDTO"].uuid);
                $("#msg").html("上传成功!");
            }else{
                $("#msg").html("上传失败!");
            }
        }
    });
});

//上传
function uploadifyUpload() {
    $('#pz_uploadify').uploadify('upload', '*');
//    $("#msg").html("请稍等,正在上传。。");
//    $("#msg").addClass("tipMsg");
    return false;
}
</script>


</head>
<body>
<!-- 标题 -->
<div id="main_2">
<!-- 标题 end-->
<div class="main_title_2">
    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>
            录入缴费信息
    </p>
</div>
<div class="content">
<form method="post" name="form1" id="form1" action="${ctx}/chargejson/payAdd.action">
    <table cellpadding="0" cellspacing="0" class="tab_1">
        <tr>
            <td colspan="4"><span class="label label-success">站点基本信息</span></td>
        </tr>
        <tr>
            <td width="150px">站点名称：</td>
            <td width="300px">${charge.btsName}
                <input name="chargeBill.intId" type="hidden" value="${charge.intId}"/>
                <input name="chargeBill.btsType" type="hidden" value="${charge.btsType}"/>
                <input name="chargeBill.costType" type="hidden" value="${charge.costType}"/>
                <input name="chargeBill.payCycle" type="hidden" value="${charge.payCycle}">
            </td>
            <td width="150px">所属BSC:</td>
            <td width="300px">${charge.bscName}</td>
        </tr>
        <tr>
            <td width="150px">本地网：</td>
            <td width="300px">${charge.cityName}</td>
            <td width="150px">区县:</td>
            <td width="300px">${charge.countryName}</td>
        </tr>
        <tr>
            <td>BTSID:</td>
            <td>${charge.btsId}</td>
            <td>费用类型:</td>
            <td>
                <select id="costType" class="input150">
                    <option value="">请选择</option>
                    <option value="1">房租</option>
                    <option value="2">物业</option>
                    <option value="3">电费</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>金额:</td>
            <td>
               <input name="chargeBill.money" type="text" class="input150 required number" value="${charge.money}"/>
            </td>
            <td><span style="color: red;">*</span>缴费时间:</td>
            <td>
                <input type="text" name="chargeBill.payTime" class="Wdate input150 required" onFocus="WdatePicker({dateFmt: 'yyyy-MM-dd'})"/>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>缴费人员：</td>
            <td><input name="chargeBill.payUser" type="text" class="input150 required" value="${charge.remindUser}"/>
            </td>
            <td id="PayTypeLable" class="noneCss"><span style="color: red;">*</span>缴费方式：</td>
            <td id="payTypeTd" class="noneCss">
                <select id="payType" class="input150">
                    <option value="">请选择</option>
                    <option value="1" selected>人工</option>
                    <option value="2">代扣</option>
                </select>
            </td>
        </tr>
        <tr id="powerProperties" class="noneCss">
            <td><span style="color: red;">*</span>底度：</td>
            <td><input name="chargeBill.baseDegree" id="baseDegree" type="text" class="input150"/>
            </td>
            <td><span style="color: red;">*</span>当月度数:</td>
            <td><input name="chargeBill.monthDegree" id="monthDegree" type="text" class="input150"/>
        </tr>
        <td><label>缴费凭证上传:</label></td>
        <td>
        <div style="float:left">
            <input name="chargeBill.proofFile" id="proofFile"  type="text" readonly="readonly" class="input150"/>
        </div>
        <div style="float:left">
            <input id="pz_uploadify" name="pz_uploadify" type="file" multiple="true">
        </div>
        </td>
        <td colspan="2">
            <button class="btn btn-success" onclick="$('#pz_uploadify').uploadify('upload', '*'); return false;" title="Upload File"><i class="icon-ok icon-white"></i>上传</button>
            <span id="msg" class="tipMsg"></span>
        </td>
        </tr>
        <tr>
            <td>备注:</td>
            <td colspan="3">
                <textarea name="chargeBill.remark" class="area150">${chargeBill.remark}</textarea>
            </td>
        </tr>
    </table>
               <div class="form-actions_2">
                <button class="btn btn-info" type="button"  onclick="add();">
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

</body>
</html>