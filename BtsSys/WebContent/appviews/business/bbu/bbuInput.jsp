<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>BBU站点录入</title>
<%@ include file="/appviews/common/tag.jsp"%>
<script src="${ctx}/resources/uploadify/jquery.uploadify.js" type="text/javascript"></script>  
 <link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css">
<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
    
    <style type="text/css">
	 #msg {
		padding-left: 16px;
		padding-bottom: 2px;
		font-weight: bold;
		display:inline;
		color: #EA5200;
	 }

     #mytab {
         margin-bottom: 20px;
     }

     #mytab li {
         width: 180px;
     }

     #mytab li a {
         padding-bottom: 2px;
         padding-top: 2px;
     }

     #mytab li.active a {
         background: url("${ctx}/layouts/images/menu_current2.jpg") repeat-x scroll 0 0 transparent;
         color: #FFF;
     }
	</style>

<script type="text/javascript">
var countryCombox=null;
//注册表单验证
$(function(){
	var comBox1=$("#sharFlagVal").ligerComboBox({  
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
	 
	comBox1.selectValue('${bbuManual.sharFlag}');
	 
	 //共享方名称，可能多选
	 
	 var comBox2=$("#sharNameVal").ligerComboBox({  
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
	 comBox2.selectValue('${bbuManual.sharName}');
	 
	 
	 //市点引入方式
	 
	 var comBox3=$("#wdTypeVal").ligerComboBox({  
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
	 comBox3.selectValue('${bbuManual.wdType}');
	 
	 //是否在传输环网保护
	 
	 var comBox4=$("#tranNetprotectVal").ligerComboBox({  
         data: [
             { text: '是', id: '是' },
             { text: '否', id: '否' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'tranNetprotect'
     });  
	 
	comBox4.selectValue('${bbuManual.tranNetprotect}');
	
	//是否节点站
	 
	 var comBox4=$("#tranIsnodeVal").ligerComboBox({  
        data: [
            { text: '是', id: '是' },
            { text: '否', id: '否' }
        ], 
        width : 200,
		selectBoxWidth: 200,
        valueFieldID: 'tranIsnode'
    });  
	 
	comBox4.selectValue('${bbuManual.tranIsnode}');

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

var townURL = "${ctx}/schooljson/getTownList.action?countryId=${bbu.countyId}";
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
            if('${bbuManual.town}'!=''){
                townCombox.selectValue('${bbuManual.town}');
            }
            var village='${bbuManual.village}';
            if(village!=''){
                countryCombox.selectValue(village);
            }
        }
);


//初始化乡镇库

function initCombox(town){
    var url="${ctx}/schooljson/getVillageLib.action?countryId=${bbu.countyId}&town="+town;
    url=encodeURI(url);
    //初始化树控件
    $.post(url, function(
            data, status) {
        liger.get("villageVal").setData(data.Rows);
    });
}



//初始化主设备安装位置
var typeURL1="${ctx}/schooljson/cons.action?groupCode=BTSINSTALL";
var sl1;
$.getJSON(typeURL1, 
	function(data){
		sl1 = $("#installPosVal").ligerComboBox({
			data : data.Rows,
			width : 200,
			selectBoxWidth: 200,
			textField : 'name',
			valueField : 'code',
			valueFieldID:'installPos',
            readonly:true
		});
		sl1.selectValue('1');
        sl1.setDisabled(true);
	}
);


//初始化机房结构
var typeURL3="${ctx}/schooljson/cons.action?groupCode=MRSTRUT";
var sl3;
$.getJSON(typeURL3, 
	function(data){
		sl3 = $("#mrStrutVal").ligerComboBox({
			data : data.Rows,
			width : 200,
			selectBoxWidth: 200,
			textField : 'name',
			valueField : 'code',
			valueFieldID:'mrStrut'
		});
		sl3.selectValue('${bbuManual.mrStrut}');
	}
);




$(function() {
	var v = $("#form1").validate(
					{
                        ignore:"",
						submitHandler : function(form) {
							//form.submit();
							 jQuery(form).ajaxSubmit(function(json){
								 if (json.result == 1) {
									 alert('操作成功!');
								}else{
									 alert('操作失败!');
								} 
								 window.location.href="${ctx}/business/bbu.action";
							 });
						},
                        errorPlacement : function(error, element) {
                            var arr=["townVal","installPosVal","sharFlagVal","sharNameVal","tranNetprotectVal","tranIsnodeVal"];
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
//						success : function(lable) {
//						//	lable.ligerHideTip();
//						//	lable.remove();
//						}
					});
});


function add(){
	$("#form1").submit();
}

//返回
function back(){
	javascript: history.go(-1);
}


	$(function() {  
    
    //机房照片
    $("#jf_uploadify").uploadify({  
        method    : 'post',  
        swf           : '${ctx}/resources/uploadify/uploadify.swf',  
        uploader      : '${ctx}/businessjson/importFile.action',  
        cancelImg	  : '${ctx}/resources/uploadify/uploadify-cancel.png',
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
            data=eval('('+data+')'); 
			if(data.result==1){  
				$("#mrPic").val(data.address);
            	$("#jf_msg").html("上传成功!");
			}else{
				$("#jf_msg").html("上传失败!");
			}
        }
    });  
    
	});  


</script>


</head>
<body>
<!-- 标题 -->
	<div id="main_2">
	<!-- 标题 end-->
	<div class="main_title_2">
              <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>
                <c:if test="${editFlag==1}">
                	编辑纯BBU站点手工信息
                </c:if>         
                <c:if test="${editFlag==0}">
             	    录入纯BBU站点手工信息
                </c:if>
             </p>
	</div>
	<div class="content"> 	
    <form method="post" name="form1" id="form1" action="${ctx}/businessjson/addBbuManual.action">
	
	<div class="tabbable tabs-left"> 
    <ul class="nav nav-tabs nav-stacked" id="mytab">
    <li class="active"><a href="#tab1" data-toggle="tab">站点、传输、开关信息</a></li>
    <li><a href="#tab2" data-toggle="tab">交流、蓄电池、空调、动环</a></li>
    <li><a href="#tab3" data-toggle="tab">新风、机房、外电、油机</a></li>
    </ul>
    <div class="tab-content">
    <div class="tab-pane active" id="tab1">
    	<table cellpadding="0" cellspacing="0" class="tab_1" >
    			<tr>
    			<th colspan="4"><span class="label label-success">站点基本信息</span></th>
    			</tr>
				<tr>
					<td width="150px">站点名称：</td>
					<td width="300px">${bbu.name}
					<input name="bbuManual.name" type="hidden" value="${bbu.name}"/>
					<input name="bbuManual.intId" type="hidden" value="${bbu.intId}"/>
					<input name="editFlag" type="hidden" value="${editFlag}">
					</td>
					<td width="150px">所属BSC:</td>
					<td width="300px">${bbu.bscName}<input name="bbuManual.bscName" type="hidden" value="${bbu.bscName}"/></td>
				</tr>
				<tr>
					<td>站网管编号:</td>
					<td>${bbu.btsId}<input name="bbuManual.btsId" type="hidden" value="${bbu.btsId}"/></td>
                    <td width="150px">本地网:</td>
                    <td width="300px">${bbu.city.cityName}</td>
				</tr>
            <tr>
                <td>区县:</td>
                <td>${bbu.country.cityName}</td>
                <td><span style="color: red;">*</span>乡镇:</td>
                <td>
                    <div style="float: left">
                        <input id="townVal" type="text" class="required"/>
                        <input name="bbuManual.town" id="town" type="hidden"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>农村:</td>
                <td>
                    <input id="villageVal" type="text"/>
                    <input name="bbuManual.village" id="village" type="hidden"/>
                </td>
                <td>主设备安装位置:</td>
                <td>
                    <div style="float: left">
                    <input name="installPosVal" id="installPosVal" type="text"/>
                    <input name="bbuManual.installPosCode" id="installPos" type="hidden"/>
                   </div>
                </td>
            </tr>
            <tr>
                <td><span style="color: red;">*</span>经度:</td>
                <td>
                    <input name="bbuManual.longitude"  type="text" class="input150 required number" value="${bbuManual.longitude}"/>
                </td>
                <td><span style="color: red;">*</span>纬度:</td>
                <td>
                    <input name="bbuManual.latitude" type="text" class="input150 required number" value="${bbuManual.latitude}"/>
                </td>
            </tr>
				<tr>
					<td><span style="color: red;">*</span>是否共建共享:</td>
					<td>
                        <div style="float: left">
						<input name="sharFlagVal" id="sharFlagVal" type="text" class="required"/>
						<input name="bbuManual.sharFlag" id="sharFlag" type="hidden"/>
                        </div>
					</td>
					<td><span style="color: red;">*</span>共享方名称:</td>
					<td>
                        <div style="float: left">
					<input name="sharNameVal" id="sharNameVal" type="text" class="required"/>
					<input name="bbuManual.sharName" id="sharName" type="hidden"/>
                       </div>
					</td>				
				</tr>
				<tr>
					<td><span style="color: red;">*</span>详细地址:</td>
					<td><input name="bbuManual.address"  type="text" class="input150 required" value="${bbuManual.address}"/></td>
					<td><span style="color: red;">*</span>基站开通年月:</td>
					<td>
                        <input type="text" name="bbuManual.openTime" class="Wdate input150 required" onFocus="WdatePicker({dateFmt: 'yyyy.MM'})" value="${bbuManual.openTime}"/>
					</td>
				</tr>
				<tr>
				<th colspan="4"><span class="label label-success">传输配套信息</span></th>
				</tr>
				<tr>
					<td><span style="color: red;">*</span>规格型号：</td>
					<td><input name="bbuManual.tranModel" type="text" class="input150 required" value="${bbuManual.tranModel}"/></td>
					<td><span style="color: red;">*</span>生产厂家:</td>
					<td><input name="bbuManual.tranFac" type="text" class="input150 required" value="${bbuManual.tranFac}"/></td>
				</tr>
				<tr>
					<td><span style="color: red;">*</span>传输拓扑上游站点名称：</td>
					<td>
                        <input name="bbuManual.tranUpsitename" type="text" class="input150 required" value="${bbuManual.tranUpsitename}"/></td>
					<td><span style="color: red;">*</span>传输拓扑下游站点名称:</td>
					<td><input name="bbuManual.tranDownsitename" type="text" class="input150 required" value="${bbuManual.tranDownsitename}"/></td>
				</tr>
				<tr>
					<td><span style="color: red;">*</span>是否在传输环网保护：</td>
					<td>
                        <div style="float: left">
					<input name="tranNetprotectVal" id="tranNetprotectVal" type="text" class="required"/>
					<input name="bbuManual.tranNetprotect" id="tranNetprotect" type="hidden"/>
                       </div>
					</td>
					
					
					
					<td><span style="color: red;">*</span>是否节点站:</td>
					<td>
                        <div style="float: left">
						<input name="tranIsnodeVal" id="tranIsnodeVal" type="text" class="required"/>
						<input name="bbuManual.tranIsnode" id="tranIsnode" type="hidden"/>
                        </div>
					</td>
				</tr>
				<tr>
					<td><span style="color: red;">*</span>节点站下挂基站数量：</td>
					<td>
					<input name="bbuManual.tranSitenum" type="text" class="input150 required number" value="${bbuManual.tranSitenum}"/>
					</td>
				</tr>
				<tr>
				<th colspan="4"><span class="label label-success">开关电源</span></th>
				</tr>
				<tr>
					<td width="150px">规格型号：</td>
					<td width="300px"><input name="bbuManual.sourModel" type="text" class="input150" value="${bbuManual.sourModel}"/></td>
					<td width="150px">生产厂家:</td>
					<td width="300px"><input name="bbuManual.sourFac" type="text" class="input150" value="${bbuManual.sourFac}"/></td>
				</tr>
				<tr>
					<td>整流模块型号：</td>
					<td><input name="bbuManual.sourModuleModel"  type="text" class="input150" value="${bbuManual.sourModuleModel}"/></td>					
					<td>整流模块数量:</td>
					<td>
					<input name="bbuManual.sourModuleNum"  type="text" class="input150 number" value="${bbuManual.sourModuleNum}"/>
					<span style="color: red;font-size: 10px;">(注：填整数)</span>
					</td>			
				</tr>
				<tr>
					<td>满架容量（A）：</td>
					<td><input name="bbuManual.sourCapa" type="text" class="input150 number" value="${bbuManual.sourCapa}"/>
                        <span style="color:red;font-size: 10px;">(注：填整数)</span></td>
				</tr>
		   </table>
    </div>
    <div class="tab-pane" id="tab2">
    	<table cellpadding="0" cellspacing="0" class="tab_1" >
				<tr>
				<th colspan="4"><span class="label label-success">交流配电防雷</span></th>
				</tr>
				<tr>
					<td>防雷箱规格型号：</td>
					<td><input name="bbuManual.boxModel" type="text" class="input150" value="${bbuManual.boxModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="bbuManual.boxFac" type="text" class="input150" value="${bbuManual.boxFac}"/></td>
				</tr>
				<tr>
				<th colspan="4"><span class="label label-success">蓄电池组或UPS</span></th>
				</tr>
				<tr>
					<td>型号：</td>
					<td><input name="bbuManual.cellModel" type="text" class="input150" value="${bbuManual.cellModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="bbuManual.cellFac" type="text" class="input150" value="${bbuManual.cellFac}"/></td>
				</tr>
				<tr>
					<td>容量(AH)：</td>
					<td><input name="bbuManual.cellCapa" type="text" class="input150 number" value="${bbuManual.cellCapa}"/><span style="color:red;font-size: 10px;">(注：填整数)</span></td>
					<td>数量(组):</td>
					<td><input name="bbuManual.cellNum"  type="text" class="input150 number" value="${bbuManual.cellNum}"/><span style="color:red;font-size: 10px;">(注：填整数)</span></td>
				</tr>
				<tr>
					<td>机房设备总耗电(A)：</td>
					<td><input name="bbuManual.cellPower" type="text" class="input150 number" value="${bbuManual.cellPower}"/></td>
					<td>基站设备运行时长(小时):</td>
					<td><input name="bbuManual.cellDuar" type="text" class="input150 number" value="${bbuManual.cellDuar}"/></td>
				</tr>
				<tr>
					<td>启用时间（年月）：</td>
					<td>
                        <input type="text" name="bbuManual.cellUstime" class="Wdate input150" onFocus="WdatePicker({dateFmt: 'yyyy.MM'})" value="${bbuManual.cellUstime}"/>
                    </td>
				</tr>
				<tr>
				<th colspan="4"><span class="label label-success">空调系统</span></th>
				</tr>
				<tr>
					<td>规格型号：</td>
					<td><input name="bbuManual.acModel"  type="text" class="input150" value="${bbuManual.acModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="bbuManual.acFac"  type="text" class="input150" value="${bbuManual.acFac}"/></td>
				</tr>
				<tr>
					<td>数量：</td>
					<td>
					<input name="bbuManual.acNum"  type="text" class="input150 number" value="${bbuManual.acNum}"/><span style="color:red;font-size: 10px;">(注：填整数)</span>
					</td>				
				</tr>
				<tr>
					<th colspan="4"><span class="label label-success">动环监控系统</span></th>
				</tr>
				<tr>
					<td>规格型号:</td>
					<td><input name="bbuManual.dhModel"  type="text" class="input150" value="${bbuManual.dhModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="bbuManual.dhFac"  type="text" class="input150" value="${bbuManual.dhFac}"/></td>
				</tr>
				</table>
    </div>
    <div class="tab-pane" id="tab3">
			<table cellpadding="0" cellspacing="0" class="tab_1" >
				<tr>
					<th colspan="4"><span class="label label-success">新风系统</span></th>
				</tr>
				<tr>
					<td>规格型号:</td>
					<td><input name="bbuManual.xfModel"  type="text" class="input150" value="${bbuManual.xfModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="bbuManual.xfFac"  type="text" class="input150" value="${bbuManual.xfFac}"/></td>
				</tr>
				<tr>
					<th colspan="4"><span class="label label-success">机房</span></th>
				</tr>
				<tr>
					<td width="150px">机房结构：</td>
					<td width="300px">
					<input name="mrStrutVal" id="mrStrutVal" type="text"/>
					<input name="bbuManual.mrStrut"  id="mrStrut" type="hidden"/>			
					</td>
					<td width="150px">机房长度(米):</td>
					<td width="300px"><input name="bbuManual.mrLength"  type="text" class="input150 number" value="${bbuManual.mrLength}"/>
                    </td>
				</tr>
				<tr>
					<td>机房宽度(米)：</td>
					<td><input name="bbuManual.mrWidth"  type="text" class="input150 number" value="${bbuManual.mrWidth}"/>
                    </td>
					<td>机房高度（米）：</td>
					<td><input name="bbuManual.mrHigh"  type="text" class="input150 number" value="${bbuManual.mrHigh}"/>
                    </td>
				</tr>
				<tr>
					<td>机房所属业主或单位:</td>
					<td colspan="3"><input name="bbuManual.mrOwner"  type="text" class="input150" value="${bbuManual.mrOwner}"/></td>
				</tr>
				<tr>
					<td>机房照片：</td>
					<td colspan="3">
					<div style="float:left">
						<input name="bbuManual.mrPic" id="mrPic"  type="text" class="input150" value="${bbuManual.mrPic}"/>
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
					<input name="wdTypeVal"  id="wdTypeVal" type="text"/>
					<input name="bbuManual.wdType" id="wdType" type="hidden"/>
						
					</td>
					<td>变压器型号:</td>
					<td><input name="bbuManual.wdModel"  type="text" class="input150" value="${bbuManual.wdModel}"/></td>
				</tr>
				<tr>
					<td>变压器厂家：</td>
					<td><input name="bbuManual.wdFac"  type="text" class="input150" value="${bbuManual.wdFac}"/></td>
					<td>所属供电局所:</td>
					<td><input name="bbuManual.wdGds"  type="text" class="input150" value="${bbuManual.wdGds}"/></td>
				</tr>
				<tr>
					<th colspan="4"><span class="label label-success">油机配置</span></th>
				</tr>
				<tr>
					<td>油机类型：</td>
					<td><input name="bbuManual.oeType"  type="text" class="input150" value="${bbuManual.oeType}"/></td>
					<td>油机型号:</td>
					<td><input name="bbuManual.oeModel"  type="text" class="input150" value="${bbuManual.oeModel}"/></td>
				</tr>
				<tr>
					<td>油机功率：</td>
					<td><input name="bbuManual.oePower"  type="text" class="input150" value="${bbuManual.oePower}"/></td>
					<td>厂家：</td>
					<td><input name="bbuManual.oeFac"  type="text" class="input150" value="${bbuManual.oeFac}"/></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="3"><textarea name="bbuManual.remark"  id="remark" cols="55" rows="2">${bbuManual.remark}</textarea></td>
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