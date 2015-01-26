<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>物理站点录入</title>
<%@ include file="/appviews/common/tag.jsp" %>
<script src="${ctx}/resources/uploadify/jquery.uploadify.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css">
<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
    #msg {
        padding-left: 16px;
        padding-bottom: 2px;
        font-weight: bold;
        display: inline;
        color: #EA5200;
    }

    #mytab {
        margin-bottom: 20px;
    }

    #mytab li{
        width:180px;
    }

    #mytab li a {
        padding-bottom: 2px;
        padding-top: 2px;
    }

    #mytab li.active a{
        background: url("${ctx}/layouts/images/menu_current2.jpg") repeat-x scroll 0 0 transparent;
        color:#FFF;
    }

</style>

<script type="text/javascript">
var countryCombox=null;
//注册表单验证
$(function() {
    var comBox1 = $("#sharFlagVal").ligerComboBox({
        data: [
            { text: '是', id: '是' },
            { text: '否', id: '否' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'sharFlag',
        onSelected: function (newvalue){
            if(newvalue=='否'){
                if(comBox2!=null){
                    comBox2.selectValue('无');
                }
            }
        }
    });

    comBox1.selectValue('${btsManual.sharFlag}');

    //共享方名称，可能多选

    var comBox2 = $("#sharNameVal").ligerComboBox({
        isShowCheckBox: true,
        isMultiSelect: true,
        data: [
            { text: '移动', id: '移动' },
            { text: '联通',id:  '联通' },
            { text: '其他', id: '其他' },
            { text: '无', id: '无' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'sharName'
    });
    comBox2.selectValue('${btsManual.sharName}');


    //市点引入方式

    var comBox3 = $("#wdTypeVal").ligerComboBox({
        data: [
            { text: '10KV', id: '10KV' },
            { text: '380V',id:  '380V' },
            { text: '220V', id: '220V' },
            { text: '直流供电', id: '直流供电' },
            { text: '直流远供', id: '直流远供' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'wdType'
    });
    comBox3.selectValue('${btsManual.wdType}');

    //是否在传输环网保护

    var comBox4 = $("#tranNetprotectVal").ligerComboBox({
        data: [
            { text: '是', id: '是' },
            { text: '否', id: '否' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'tranNetprotect'
    });

    comBox4.selectValue('${btsManual.tranNetprotect}');

    //是否节点站

    var comBox5 = $("#tranIsnodeVal").ligerComboBox({
        data: [
            { text: '是', id: '是' },
            { text: '否', id: '否' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'tranIsnode'
    });

    comBox5.selectValue('${btsManual.tranIsnode}');


    //传输上游节点，弹出选择框
    $("#tranUpsitenameVal").ligerComboBox({
        onBeforeOpen: showDilog,
        valueFieldID: 'tranUpsitename',
        width: 200
    });


    //传输下游节点，弹出选择框
    $("#tranDownsitenameVal").ligerComboBox({
        onBeforeOpen: showDownDilog,
        valueFieldID: 'tranDownsitename',
        width: 200
    });


      countryCombox=$("#villageVal").ligerComboBox( {
        data : null,
        width : 200,
        selectBoxWidth : 200,
        selectBoxHeight : 200,
        textField : 'village',
        valueField : 'village',
        valueFieldID : 'village'
    });



    if (!Array.prototype.indexOf)
    {
        Array.prototype.indexOf = function(elt /*, from*/)
        {
            var len = this.length >>> 0;
            var from = Number(arguments[1]) || 0;
            from = (from < 0)
                    ? Math.ceil(from)
                    : Math.floor(from);
            if (from < 0)
                from += len;
            for (; from < len; from++)
            {
                if (from in this &&
                        this[from] === elt)
                    return from;
            }
            return -1;
        };
    }



});

//初始化乡镇下拉框

var townURL = "${ctx}/schooljson/getTownList.action?countryId=${bts.countyId}";
var townCombox;
$.getJSON(townURL,
        function(data) {
            townCombox = $("#townVal").ligerComboBox({
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'TOWN',
                valueField : 'TOWN',
                valueFieldID:'town',
                onSelected:function(data){
                    initCombox(data);
                }
            });
            if('${btsManual.town}'!=''){
                townCombox.selectValue('${btsManual.town}');
            }
            var village='${btsManual.village}';
            if(village!=''){
                countryCombox.selectValue(village);
            }
        }
);


//初始化乡镇库



//初始化乡镇库
function initCombox(town){
    var url="${ctx}/schooljson/getVillageLib.action?countryId=${bts.countyId}&town="+town;
    url=encodeURI(url);
    //初始化树控件
    $.post(url, function(
            data, status) {
        liger.get("villageVal").setData(data.Rows);
    });
}


//初始化主设备安装位置
var typeURL1 = "${ctx}/schooljson/cons.action?groupCode=BTSINSTALL";
var sl1;
$.getJSON(typeURL1,
        function(data) {
            sl1 = $("#installPosVal").ligerComboBox({
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'name',
                valueField : 'code',
                valueFieldID:'installPos'
            });
            sl1.selectValue('${btsManual.installPos}');
        }
);


//初始化塔桅类型
var typeURL2 = "${ctx}/schooljson/cons.action?groupCode=TOWERTYPE";
var sl2;
$.getJSON(typeURL2,
        function(data) {
            sl2 = $("#towerTypeVal").ligerComboBox({
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'name',
                valueField : 'code',
                valueFieldID:'towerType'
            });
            sl2.selectValue('${btsManual.towerType}');
        }
);

//初始化机房结构
var typeURL3 = "${ctx}/schooljson/cons.action?groupCode=MRSTRUT";
var sl3;
$.getJSON(typeURL3,
        function(data) {
            sl3 = $("#mrStrutVal").ligerComboBox({
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'name',
                valueField : 'code',
                valueFieldID:'mrStrut'
            });
            sl3.selectValue('${btsManual.mrStrut}');
        }
);


$(function() {
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
                        window.location.href = "${ctx}/business/bts.action";
                    });
                },
               errorPlacement : function(error, element) {
                   var arr=["townVal","installPosVal","sharFlagVal","sharNameVal","tranUpsitenameVal","tranDownsitenameVal","tranNetprotectVal","tranIsnodeVal"];
                   var id=element.prop("id");
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
    //塔跪照片
    $("#uploadify").uploadify({
        method    : 'post',
        swf           : '${ctx}/resources/uploadify/uploadify.swf',
        uploader      : '${ctx}/businessjson/importFile.action',
        cancelImg      : '${ctx}/resources/uploadify/uploadify-cancel.png',
        fileObjName     : 'file',
        fileSizeLimit   : 0,
        multi  : false,
        removeCompleted : false,
        fileSizeLimit : '10MB',
        buttonText      : '选择文件',
        buttonClass : 'some-class',
        height        : 21,
        width         : 75 ,
        auto :true,
        fileTypeDesc :'jpg文件',//上传文件类型说明
        fileTypeExts : '*.jpg', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
        onInit   : function() {
              $("#uploadify-queue").hide();
        },
        onSelect : function(file) {
            //$("#path").val(file.name);
        },
        onUploadSuccess : function(file, data, response) {
            data = eval('(' + data + ')');
            if (data.result == 1) {
                $("#towerPic").val(data.address);
                $("#msg").html("上传成功!");
            } else {
                $("#msg").html("上传失败!");
            }
        }
    });

    //机房照片
    $("#jf_uploadify").uploadify({
        method    : 'post',
        swf           : '${ctx}/resources/uploadify/uploadify.swf',
        uploader      : '${ctx}/businessjson/importFile.action',
        cancelImg      : '${ctx}/resources/uploadify/uploadify-cancel.png',
        fileObjName     : 'file',
        fileSizeLimit   : 0,
        multi  : false,
        removeCompleted : false,
        fileSizeLimit : '10MB',
        buttonText      : '选择文件',
        buttonClass : 'some-class',
        height        : 21,
        width         : 75 ,
        auto :true,
        fileTypeDesc :'jpg文件',//上传文件类型说明
        fileTypeExts : '*.jpg', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
        onInit   : function() {
              $("#jf_uploadify-queue").hide();
        },
        onSelect : function(file) {
            //$("#path").val(file.name);
        },
        onUploadSuccess : function(file, data, response) {
            data = eval('(' + data + ')');
            if (data.result == 1) {
                $("#mrPic").val(data.address);
                $("#jf_msg").html("上传成功!");
            } else {
                $("#jf_msg").html("上传失败!");
            }
        }
    });

});


//选择上游节点对话框

function showDilog(){
    $.ligerDialog.open({
                   height: 512,
                   url: '${ctx}/appviews/business/bts/topTransferNode.jsp',
                   width: 1100,
                   name: 'columns',
                   title: "选择传输拓扑上游站点",
                   showMax: true,
                   showToggle: true,
                   showMin: true,
                   isResize: true,
                   isHidden: false
               });
}


function showDownDilog(){
    $.ligerDialog.open({
                   height: 512,
                   url: '${ctx}/appviews/business/bts/downTransferNode.jsp',
                   width: 1100,
                   name: 'columns',
                   title: "选择传输拓扑下游站点",
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
<!-- 标题 -->
<div id="main_2">
<!-- 标题 end-->
<div class="main_title_2">
    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>
        <c:if test="${editFlag==1}">
            编辑物理站点手工信息
        </c:if>
        <c:if test="${editFlag==0}">
            录入物理站点手工信息
        </c:if>
    </p>
</div>
<div class="content">
<form method="post" name="form1" id="form1" action="${ctx}/businessjson/addBtsManual.action">

<div class="tabbable tabs-left">
<ul class="nav nav-tabs nav-stacked" id="mytab">
    <li class="active"><a href="#tab1" data-toggle="tab">站点、传输、开关、交流</a></li>
    <li><a href="#tab2" data-toggle="tab">蓄电池、塔桅、空调、动环</a></li>
    <li><a href="#tab3" data-toggle="tab">新风、机房、外电、油机</a></li>
</ul>
<div class="tab-content">
<div class="tab-pane active" id="tab1">
    <table cellpadding="0" cellspacing="0" class="tab_1">
        <tr>
            <td colspan="4"><span class="label label-success">站点基本信息</span></td>
        </tr>
        <tr>
            <td width="150px">站点名称：</td>
            <td width="300px">${bts.name}
                <input name="btsManual.name" type="hidden" value="${bts.name}"/>
                <input name="btsManual.intId" type="hidden" value="${bts.intId}"/>
                <input name="editFlag" type="hidden" value="${editFlag}">
            </td>
            <td width="150px">所属BSC:</td>
            <td width="300px">${bts.bscName}<input name="btsManual.bscName" type="hidden" value="${bts.bscName}"/></td>
        </tr>
        <tr>
            <td>站网管编号:</td>
            <td>${bts.btsId}<input name="btsManual.btsId" type="hidden" value="${bts.btsId}"/></td>
            <td width="150px">本地网:</td>
            <td width="300px">${bts.city.cityName}</td>
        </tr>
        <tr>
            <td>区县:</td>
            <td>${bts.country.cityName}</td>
            <td><span style="color: red;">*</span>所属乡镇:</td>
            <td>
                <div style="float: left">
                <input id="townVal" type="text" class="required"/>
                <input name="btsManual.town" id="town" type="hidden"/>
                </div>
            </td>
        </tr>
        <tr>
            <td>覆盖农村:</td>
            <td>
                <input id="villageVal" type="text"/>
                <input name="btsManual.village" id="village" type="hidden"/>
            </td>
            <td><span style="color: red;">*</span>主设备安装位置:</td>
            <td>
                <div style="float: left">
                <input name="installPosVal" id="installPosVal" type="text" class="required"/>
                <input name="btsManual.installPos" id="installPos" type="hidden"/>
                </div>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>是否共建共享:</td>
            <td>
                <div style="float: left">
                <input name="sharFlagVal" id="sharFlagVal" type="text" class="required"/>
                <input name="btsManual.sharFlag" id="sharFlag" type="hidden"/>
                </div>
            </td>
            <td><span style="color: red;">*</span>共享方名称:</td>
            <td>
                <div style="float: left">
                <input name="sharNameVal" id="sharNameVal" type="text" class="required"/>
                <input name="btsManual.sharName" id="sharName" type="hidden"/>
                </div>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>详细地址:</td>
            <td><input name="btsManual.address" type="text" class="input200 required" value="${btsManual.address}"/>
            </td>
            <td><span style="color: red;">*</span>基站开通年月:</td>
            <td>
                <input type="text" name="btsManual.openTime" class="Wdate input150 required" onFocus="WdatePicker({dateFmt: 'yyyy.MM'})" value="${btsManual.openTime}"/>
            </td>
        </tr>
        <tr>
            <th colspan="4"><span class="label label-success">传输配套信息</span></th>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>规格型号：</td>
            <td><input name="btsManual.tranModel" type="text" class="input150 required" value="${btsManual.tranModel}"/>
            </td>
            <td><span style="color: red;">*</span>生产厂家:</td>
            <td><input name="btsManual.tranFac" type="text" class="input150 required" value="${btsManual.tranFac}"/>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>传输拓扑上游站点名称：</td>
            <td>
                <div style="float: left">
                <input type="text" class="required"
                       value="${btsManual.tranUpsitenameStr}" id="tranUpsitenameVal"/>
                <input name="btsManual.tranUpsitename" type="hidden"
                     value="${btsManual.tranUpsitename}" id="tranUpsitename"/>
                </div>
            </td>
            <td><span style="color: red;">*</span>传输拓扑下游站点名称:</td>
            <td>
              <div style="float: left">
            <input type="text" class="required"
                                   value="${btsManual.tranDownsitenameStr}" id="tranDownsitenameVal"/>
            <input name="btsManual.tranDownsitename" type="hidden"
                                 value="${btsManual.tranDownsitename}" id="tranDownsitename"/>
              </div>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>是否在传输环网保护：</td>
            <td>
                <div style="float: left">
                <input name="tranNetprotectVal" id="tranNetprotectVal" type="text" class="required"/>
                <input name="btsManual.tranNetprotect" id="tranNetprotect" type="hidden"/>
                </div>
            </td>


            <td><span style="color: red;">*</span>是否节点站:</td>
            <td>
                <div style="float: left">
                <input name="tranIsnodeVal" id="tranIsnodeVal" type="text" class="required"/>
                <input name="btsManual.tranIsnode" id="tranIsnode" type="hidden"/>
                </div>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>节点站下挂基站数量：</td>
            <td>
                <input name="btsManual.tranSitenum" type="text" class="input150 required number"
                       value="${btsManual.tranSitenum}"/>
                <span style="color: red;font-size: 10px">(注：填整数)</span>
            </td>
        </tr>
        <tr>
            <th colspan="4"><span class="label label-success">开关电源</span></th>
        </tr>
        <tr>
            <td width="150px">规格型号：</td>
            <td width="300px"><input name="btsManual.sourModel" type="text" class="input150"
                                     value="${btsManual.sourModel}"/></td>
            <td width="150px">生产厂家:</td>
            <td width="300px"><input name="btsManual.sourFac" type="text" class="input150"
                                     value="${btsManual.sourFac}"/></td>
        </tr>
        <tr>
            <td>整流模块型号：</td>
            <td><input name="btsManual.sourModuleModel" type="text" class="input150"
                       value="${btsManual.sourModuleModel}"/></td>
            <td>整流模块数量:</td>
            <td>
                <input name="btsManual.sourModuleNum" type="text" class="input150 number"
                       value="${btsManual.sourModuleNum}"/>
                <span style="color: red;font-size: 10px">(注：填整数)</span>
            </td>
        </tr>
        <tr>
            <td>满架容量（A）：</td>
            <td><input name="btsManual.sourCapa" type="text" class="input150 number"
                       value="${btsManual.sourCapa}"/><span style="color:red;font-size: 10px">(注：填整数)</span></td>
        </tr>
        <tr>
            <th colspan="4"><span class="label label-success">交流配电防雷</span></th>
        </tr>
        <tr>
            <td>防雷箱规格型号：</td>
            <td><input name="btsManual.boxModel" type="text" class="input150" value="${btsManual.boxModel}"/></td>
            <td>生产厂家:</td>
            <td><input name="btsManual.boxFac" type="text" class="input150" value="${btsManual.boxFac}"/></td>
        </tr>
    </table>
</div>
<div class="tab-pane" id="tab2">
    <table cellpadding="0" cellspacing="0" class="tab_1">
        <tr>
            <td colspan="4"><span class="label label-success">蓄电池组或UPS</span></td>
        </tr>
        <tr>
            <td>型号：</td>
            <td><input name="btsManual.cellModel" type="text" class="input150" value="${btsManual.cellModel}"/></td>
            <td>生产厂家:</td>
            <td><input name="btsManual.cellFac" type="text" class="input150" value="${btsManual.cellFac}"/></td>
        </tr>
        <tr>
            <td>容量(AH)：</td>
            <td><input name="btsManual.cellCapa" type="text" class="input150 number"
                       value="${btsManual.cellCapa}"/><span style="color:red;font-size: 10px">(注：填整数)</span></td>
            <td>数量(组):</td>
            <td><input name="btsManual.cellNum" type="text" class="input150 number" value="${btsManual.cellNum}"/><span
                    style="color:red;font-size: 10px">(注：填整数)</span></td>
        </tr>
        <tr>
            <td>机房设备总耗电(A)：</td>
            <td><input name="btsManual.cellPower" type="text" class="input150 number"
                       value="${btsManual.cellPower}"/></td>
            <td>基站设备运行时长(小时):</td>
            <td><input name="btsManual.cellDuar" type="text" class="input150 number"
                       value="${btsManual.cellDuar}"/></td>
        </tr>
        <tr>
            <td>启用时间（年月）：</td>
            <td>
                <input type="text" name="btsManual.cellUstime" class="Wdate input150 required" onFocus="WdatePicker({dateFmt: 'yyyy.MM'})" value="${btsManual.cellUstime}"/>
            </td>
        </tr>
        <tr>
            <th colspan="4"><span class="label label-success">塔桅类型</span></th>
        </tr>
        <tr>
            <td width="150px">塔桅类型：</td>
            <td width="300px">
                <input name="towerTypeVal" id="towerTypeVal" type="text"/>
                <input name="btsManual.towerType" id="towerType" type="hidden"/>
            </td>
            <td width="150px">塔桅高度(m):</td>
            <td width="300px">
                <input name="btsManual.towerHigh" type="text" class="input150 number" value="${btsManual.towerHigh}"/>
                <span style="color:red;font-size: 10px">(注：填整数)</span>
            </td>
        </tr>
        <tr>
            <td>塔桅照片：</td>
            <td colspan="3">
                <div style="float:left">
                    <input name="btsManual.towerPic" id="towerPic" type="text" class="input150"
                           value="${btsManual.towerPic}"/>
                </div>
                <div style="float:left">
                    <input id="uploadify" name="uploadify" type="file" multiple="true"><span id="msg"></span>
                </div>
            </td>
        </tr>
        <tr>
            <th colspan="4"><span class="label label-success">空调系统</span></th>
        </tr>
        <tr>
            <td>规格型号：</td>
            <td><input name="btsManual.acModel" type="text" class="input150" value="${btsManual.acModel}"/></td>
            <td>生产厂家:</td>
            <td><input name="btsManual.acFac" type="text" class="input150" value="${btsManual.acFac}"/></td>
        </tr>
        <tr>
            <td>数量：</td>
            <td>
                <input name="btsManual.acNum" type="text" class="input150 number" value="${btsManual.acNum}"/><span
                    style="color:red;font-size: 10px">(注：填整数)</span>
            </td>
        </tr>
        <tr>
            <th colspan="4"><span class="label label-success">动环监控系统</span></th>
        </tr>
        <tr>
            <td>规格型号:</td>
            <td><input name="btsManual.dhModel" type="text" class="input150" value="${btsManual.dhModel}"/></td>
            <td>生产厂家:</td>
            <td><input name="btsManual.dhFac" type="text" class="input150" value="${btsManual.dhFac}"/></td>
        </tr>
    </table>
</div>
<div class="tab-pane" id="tab3">
    <table cellpadding="0" cellspacing="0" class="tab_1">
        <tr>
            <th colspan="4"><span class="label label-success">新风系统</span></th>
        </tr>
        <tr>
            <td>规格型号:</td>
            <td><input name="btsManual.xfModel" type="text" class="input150" value="${btsManual.xfModel}"/></td>
            <td>生产厂家:</td>
            <td><input name="btsManual.xfFac" type="text" class="input150" value="${btsManual.xfFac}"/></td>
        </tr>
        <tr>
            <th colspan="4"><span class="label label-success">机房</span></th>
        </tr>
        <tr>
            <td width="150px">机房结构：</td>
            <td width="300px">
                <input name="mrStrutVal" id="mrStrutVal" type="text"/>
                <input name="btsManual.mrStrut" id="mrStrut" type="hidden"/>
            </td>
            <td width="150px">机房长度(米):</td>
            <td width="300px"><input name="btsManual.mrLength" type="text" class="input150 number"
                                     value="${btsManual.mrLength}"/><span
                    style="color:red;font-size: 10px"></span></td>
        </tr>
        <tr>
            <td>机房宽度(米)：</td>
            <td><input name="btsManual.mrWidth" type="text" class="input150 number" value="${btsManual.mrWidth}"/><span
                    style="color:red;font-size: 10px"></span></td>
            <td>机房高度（米）：</td>
            <td><input name="btsManual.mrHigh" type="text" class="input150 number" value="${btsManual.mrHigh}"/><span
                    style="color:red;font-size: 10px"></span></td>
        </tr>
        <tr>
            <td>机房所属业主或单位:</td>
            <td colspan="3"><input name="btsManual.mrOwner" type="text" class="input150" value="${btsManual.mrOwner}"/>
            </td>
        </tr>
        <tr>
            <td>机房照片：</td>
            <td colspan="3">
                <div style="float:left">
                    <input name="btsManual.mrPic" id="mrPic" type="text" class="input150" value="${btsManual.mrPic}"/>
                </div>
                <div style="float:left">
                    <input id="jf_uploadify" name="jf_uploadify" type="file" multiple="true">
                    <span id="jf_msg" style="color: #EA5200;"></span>
                </div>
            </td>
        </tr>
        <tr>
            <th colspan="4"><span class="label label-success">外电</span></th>
        </tr>
        <tr>
            <td>市电引入方式：</td>
            <td>
                <input name="wdTypeVal" id="wdTypeVal" type="text"/>
                <input name="btsManual.wdType" id="wdType" type="hidden"/>

            </td>
            <td>变压器型号:</td>
            <td><input name="btsManual.wdModel" type="text" class="input150" value="${btsManual.wdModel}"/></td>
        </tr>
        <tr>
            <td>变压器厂家：</td>
            <td><input name="btsManual.wdFac" type="text" class="input150" value="${btsManual.wdFac}"/></td>
            <td>所属供电局所:</td>
            <td><input name="btsManual.wdGds" type="text" class="input150" value="${btsManual.wdGds}"/></td>
        </tr>
        <tr>
            <th colspan="4"><span class="label label-success">油机配置</span></th>
        </tr>
        <tr>
            <td>油机类型：</td>
            <td><input name="btsManual.oeType" type="text" class="input150" value="${btsManual.oeType}"/></td>
            <td>油机型号:</td>
            <td><input name="btsManual.oeModel" type="text" class="input150" value="${btsManual.oeModel}"/></td>
        </tr>
        <tr>
            <td>油机功率：</td>
            <td><input name="btsManual.oePower" type="text" class="input150" value="${btsManual.oePower}"/></td>
            <td>厂家：</td>
            <td><input name="btsManual.oeFac" type="text" class="input150" value="${btsManual.oeFac}"/></td>
        </tr>
        <tr>
            <td>备注：</td>
            <td colspan="3"><textarea name="btsManual.remark" id="remark" cols="55"
                                      rows="2">${btsManual.remark}</textarea></td>
        </tr>
    </table>
</div>
</div>


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
</div>
</form>

</div>
</div>

</body>
</html>