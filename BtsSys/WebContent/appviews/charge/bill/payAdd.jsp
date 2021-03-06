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
    <link href="${ctx}/resources/ligerUI/1.1.9/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>

<script type="text/javascript">
	var select = false;
	var upload = false;
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

        if ("${chargeBill.payType==null?"":chargeBill.payType}" != "") {
            var pp = "${chargeBill.payType}";
            $("#payType option[value='" + pp + "']").attr("selected", "selected");
        }
    }

    var gridObj;
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
                   error.appendTo(element.parent());
                }
               // success : function(lable) {
                 //   lable.ligerHideTip();
                 //   lable.remove();
                //}
            });



    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'缴费金额',name:'money',width : 140,align:'center'},
            {display:'缴费人员',name:'payUser',width : 80,align:'center'},
            {display:'缴费时间',name:'payTimeStr',width : 80,align:'center'},
            {display:'是否超时',name:'isTimeoutStr',width :80,align:'center'},
            {display:'底度',name:'baseDegree',width :80,align:'center'},
            {display:'当月度数',name:'monthDegree',width :80,align:'center'} ,
            {display:'缴费方式',name:'payTypeStr',width :80,align:'center'} ,
            {display:'缴费凭证',name:'proofFileName',width :300,align:'center'}
        ],
        toolbar: {
            items: [
                {text: '编辑',click: payEdit, icon: 'modify'},
                {line: true},
                {text: '删除',click: payDel,icon: 'delete'}
            ]
        },
        usePager:false,
        rownumbers:true,
        showTitle : false,
        url:'${ctx}/chargejson/payDetailList.action?intId=${charge.intId}&costType=${charge.costType}',
        checkbox : true,
        width: '98%',
        height:'250'
    });

    var costType= "${charge.costType}";//费用类型
    if(costType=='3'){
        gridObj.toggleCol('baseDegree', true);
        gridObj.toggleCol('monthDegree', true);
        gridObj.toggleCol('payTypeStr', true);
    }else{
        gridObj.toggleCol('baseDegree', false);
        gridObj.toggleCol('monthDegree', false);
        gridObj.toggleCol('payTypeStr', false);
    }
});


function add() {
	if(select == true && upload == false){
		alert("请上传文件");
	}else{
	    $("#form1").submit();
	}
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
            select = true;
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
                upload = true;
            }else{
                $("#msg").html("上传失败!");
            }
        }
    });
});

 function payDel(){
     var rows = gridObj.getCheckedRows();
     var str="";
     $(rows).each(function() {
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
             ids : str
         };
         $.getJSON('${ctx}/chargejson/payDel.action', params, function(json) {
             if (json.result == 1) {
                 alert('删除成功!');
             }else{
                 alert('删除失败！');
             }
             gridObj.loadData();
         });

     });
 }

    function payEdit(){
        var rows = gridObj.getCheckedRows();
        var j=rows.length;
        if(j==0){
            $.ligerDialog.alert('请选择要编辑的数据！');
            return;
        }else if (j > 1) {
            $.ligerDialog.alert('请选择一条编辑！');
            return;
        }
        var id;
        $(rows).each(function() {
            id=this.id;
        });
        window.location.href="${ctx}/charge/payAddPage.action?id="+id;
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
                <input name="chargeBill.payDay" type="hidden" value="${charge.payDay}">
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
               <input name="chargeBill.id" type="hidden" value="${chargeBill.id}"/>
               <input name="chargeBill.money" type="text" class="input150 required number"  <c:if test="${!empty chargeBill }">value="${chargeBill.money }"</c:if>
                      <c:if test="${empty chargeBill }">value="${charge.money}"</c:if>/>
            </td>
            <td><span style="color: red;">*</span>缴费时间:</td>
            <td>
                <input type="text" name="chargeBill.payTime" class="Wdate input150 required" onFocus="WdatePicker({dateFmt: 'yyyy-MM-dd'})" value="${chargeBill.payTimeStr}"/>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>缴费人员：</td>
            <td><input name="chargeBill.payUser" type="text" class="input150 required"  <c:if test="${!empty chargeBill }">value="${chargeBill.payUser}"</c:if>
                       <c:if test="${empty chargeBill }">value="${charge.remindUser}"</c:if>/>
            </td>
            <td id="PayTypeLable" class="noneCss"><span style="color: red;">*</span>缴费方式：</td>
            <td id="payTypeTd" class="noneCss">
                <select id="payType" class="input150" name="chargeBill.payType">
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
            <input name="chargeBill.proofFile" id="proofFile"  type="text" readonly="readonly" class="input150" value="${chargeBill.proofFile}"/>
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
    <div id="maingrid"></div>

</div>
</div>

</body>
</html>