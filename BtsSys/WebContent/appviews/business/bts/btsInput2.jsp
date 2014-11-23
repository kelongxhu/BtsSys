<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>物理站点录入</title>
<%@ include file="/appviews/common/tag.jsp"%>
<script type="text/javascript">

//注册表单验证
$(function(){
	var comBox1=$("#sharFlagVal").ligerComboBox({  
         data: [
             { text: '是', id: '是' },
             { text: '否', id: '否' }
         ], 
         width : 170,
		 selectBoxWidth: 170,
         valueFieldID: 'sharFlag'
     });  
	 
	comBox1.selectValue('${btsManual.sharFlag}');
	 
	 //共享方名称，可能多选
	 
	 var comBox2=$("#sharNameVal").ligerComboBox({  
		 isShowCheckBox: true,
		 isMultiSelect: true,
         data: [
             { text: '移动', id: '移动' },
             { text: '联通',id:  '联通' },
             { text: '其它', id: '其它' }
         ], 
         width : 170,
		 selectBoxWidth: 170,
         valueFieldID: 'sharName'
     });  
	 comBox2.selectValue('${btsManual.sharName}');
	 
});


//初始化主设备安装位置
var typeURL1="${ctx}/schooljson/cons.action?groupCode=BTSINSTALL";
var sl1;
$.getJSON(typeURL1, 
	function(data){
		sl1 = $("#installPosVal").ligerComboBox({
			data : data.Rows,
			width : 170,
			selectBoxWidth: 170,
			textField : 'name',
			valueField : 'code',
			valueFieldID:'installPos'
		});
		sl1.selectValue('${btsManual.installPos}');
	}
);


//初始化塔桅类型
var typeURL2="${ctx}/schooljson/cons.action?groupCode=TOWERTYPE";
var sl2;
$.getJSON(typeURL2, 
	function(data){
		sl2 = $("#towerTypeVal").ligerComboBox({
			data : data.Rows,
			width : 170,
			selectBoxWidth: 170,
			textField : 'name',
			valueField : 'code',
			valueFieldID:'towerType'
		});
		sl2.selectValue('${btsManual.towerType}');
	}
);

//初始化机房结构
var typeURL3="${ctx}/schooljson/cons.action?groupCode=MRSTRUT";
var sl3;
$.getJSON(typeURL3, 
	function(data){
		sl3 = $("#mrStrutVal").ligerComboBox({
			data : data.Rows,
			width : 170,
			selectBoxWidth: 170,
			textField : 'name',
			valueField : 'code',
			valueFieldID:'mrStrut'
		});
		sl3.selectValue('${btsManual.mrStrut}');
	}
);




$(function() {
	var v = $("#form1")
			.validate(
					{
						submitHandler : function(form) {
							//form.submit();
							 jQuery(form).ajaxSubmit(function(json){
								 if (json.result == 1) {
									 alert('操作成功!');
								}else{
									 alert('操作失败!');
								} 
								 window.location.href="${ctx}/business/bts.action";
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

function add(){
	$("#form1").submit();
}

//返回
function back(){
	javascript: history.go(-1);
}


</script>


</head>
<body>
<!-- 标题 -->
	<div id="main">
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
	<div class="content_2"> 
	<form method="post" name="form1" id="form1" action="${ctx}/businessjson/addBtsManual.action">
			<table cellpadding="0" cellspacing="0" class="tab_1" >
				<tr>
					<th colspan="6">站点基本信息</th>
				</tr>
				<tr>
					<td width="100px">站点名称：</td>
					<td width="200px">${bts.name}<input name="btsManual.name" type="hidden" value="${bts.name}"/></td>
					<td width="100px">所属BSC:</td>
					<td width="200px">${bts.bscName}<input name="btsManual.bscName" type="hidden" value="${bts.bscName}"/></td>
					<td width="100px">站网管编号:</td>
					<td width="200px">${bts.btsId}<input name="btsManual.btsId" type="hidden" value="${bts.btsId}"/>
					<input name="btsManual.intId" type="hidden" value="${bts.intId}"/>
					<input name="editFlag" type="hidden" value="${editFlag}">
					</td>
				</tr>
				<tr>
					<td><span style="color: red;">*</span>主设备安装位置:</td>
					<td><input name="installPosVal" id="installPosVal" type="text" class="required"/>
					<input name="btsManual.installPos" id="installPos" type="hidden"/></td>
					<td><span style="color: red;">*</span>是否共建共享:</td>
					<td>
						<input name="sharFlagVal" id="sharFlagVal" type="text" class="required"/>
						<input name="btsManual.sharFlag" id="sharFlag" type="hidden"/>
					</td>
					<td><span style="color: red;">*</span>共享方名称:</td>
					<td>
					<input name="sharNameVal" id="sharNameVal" type="text" class="required"/>
					<input name="btsManual.sharName" id="sharName" type="hidden"/>
					</td>				
				</tr>
				<tr>
					<td><span style="color: red;">*</span>详细地址:</td>
					<td colspan="3"><input name="btsManual.address"  type="text" class="input400 required" value="${btsManual.address}"/></td>
					<td><span style="color: red;">*</span>基站开通年月:</td>
					<td><input name="btsManual.openTime" id="openTime" type="text" class="input150 required" value="${btsManual.openTime}"/></td>
				</tr>
				<tr>
				<th colspan="6">传输配套信息</th>
				</tr>
				<tr>
					<td>规格型号：</td>
					<td><input name="btsManual.tranModel" type="text" class="input150" value="${btsManual.tranModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="btsManual.tranFac" type="text" class="input150" value="${btsManual.tranFac}"/></td>
					<td>传输拓扑上游站点名称：</td>
					<td><input name="btsManual.tranUpsitename" type="text" class="input150" value="${btsManual.tranUpsitename}"/></td>
				</tr>
				<tr>
					<td>传输拓扑下游站点名称:</td>
					<td><input name="btsManual.tranDownsitename" type="text" class="input150" value="${btsManual.tranDownsitename}"/></td>
					<td>是否在传输环网保护：</td>
					<td><input name="btsManual.tranNetprotect" type="text" class="input150" value="${btsManual.tranNetprotect}"/></td>
					<td>是否节点站:</td>
					<td><input name="btsManual.tranIsnode" type="text" class="input150" value="${btsManual.tranIsnode}"/></td>
				</tr>
				<tr>
					<td>节点站下挂基站数量：</td>
					<td><input name="btsManual.tranSitenum" type="text" class="input150 number" value="${btsManual.tranSitenum}"/></td>
				</tr>
				<tr>
				<th colspan="6">开关电源</th>
				</tr>
				<tr>
					<td>规格型号：</td>
					<td><input name="btsManual.sourModel" type="text" class="input150" value="${btsManual.sourModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="btsManual.sourFac" type="text" class="input150" value="${btsManual.sourFac}"/></td>
					<td>整流模块型号：</td>
					<td><input name="btsManual.sourModuleModel"  type="text" class="input150" value="${btsManual.sourModuleModel}"/></td>
				</tr>
				<tr>
					<td>整流模块数量:</td>
					<td><input name="btsManual.sourModuleNum"  type="text" class="input150" value="${btsManual.sourModuleNum}"/></td>
					<td>满架容量（A）：</td>
					<td><input name="btsManual.sourCapa" type="text" class="input150" value="${btsManual.sourCapa}"/></td>
				</tr>
				<tr>
				<th colspan="6">交流配电防雷</th>
				</tr>
				<tr>
					<td>防雷箱规格型号：</td>
					<td><input name="btsManual.boxModel" type="text" class="input150" value="${btsManual.boxModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="btsManual.boxFac" type="text" class="input150" value="${btsManual.boxFac}"/></td>
				</tr>
				<tr>
				<th colspan="6">蓄电池组或UPS</th>
				</tr>
				<tr>
					<td>型号：</td>
					<td><input name="btsManual.cellModel" type="text" class="input150" value="${btsManual.cellModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="btsManual.cellFac" type="text" class="input150" value="${btsManual.cellFac}"/></td>
					<td>容量(AH)：</td>
					<td><input name="btsManual.cellCapa" type="text" class="input150" value="${btsManual.cellCapa}"/></td>
				</tr>
				<tr>
					<td>数量(组):</td>
					<td><input name="btsManual.cellNum"  type="text" class="input150" value="${btsManual.cellNum}"/></td>
					<td>机房设备总耗电（A）：</td>
					<td><input name="btsManual.cellPower" type="text" class="input150" value="${btsManual.cellPower}"/></td>
					<td>支撑基站设备运行时长（小时）:</td>
					<td><input name="btsManual.cellDuar" type="text" class="input150" value="${btsManual.cellDuar}"/></td>
				</tr>
				<tr>
					<td>启用时间（年月）：</td>
					<td><input name="btsManual.cellUstime" type="text" class="input150" value="${btsManual.cellUstime}"/></td>
				</tr>
				<tr>
				<th colspan="6">塔桅类型</th>
				</tr>
				<tr>
					<td>塔桅类型：</td>
					<td>
					<input name="towerTypeVal" id="towerTypeVal" type="text"/>
					<input name="btsManual.towerType" id="towerType" type="hidden"/>					
					</td>
					<td>塔桅高度(m):</td>
					<td><input name="btsManual.towerHigh"  type="text" class="input150" value="${btsManual.towerHigh}"/></td>
					<td>塔桅照片：</td>
					<td><input name="btsManual.towerPic"  type="text" class="input150" value="${btsManual.towerPic}"/></td>
				</tr>
				<tr>
				<th colspan="6">空调系统</th>
				</tr>
				<tr>
					<td>规格型号：</td>
					<td><input name="btsManual.acModel"  type="text" class="input150" value="${btsManual.acModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="btsManual.acFac"  type="text" class="input150" value="${btsManual.acFac}"/></td>
					<td>数量：</td>
					<td><input name="btsManual.acNum"  type="text" class="input150" value="${btsManual.acNum}"/></td>
				</tr>
				<tr>
					<th colspan="6">动环监控系统</th>
				</tr>
				<tr>
					<td>规格型号:</td>
					<td><input name="btsManual.dhModel"  type="text" class="input150" value="${btsManual.dhModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="btsManual.dhFac"  type="text" class="input150" value="${btsManual.dhFac}"/></td>
				</tr>
				<tr>
					<th colspan="6">新风系统</th>
				</tr>
				<tr>
					<td>规格型号:</td>
					<td><input name="btsManual.xfModel"  type="text" class="input150" value="${btsManual.xfModel}"/></td>
					<td>生产厂家:</td>
					<td><input name="btsManual.xfFac"  type="text" class="input150" value="${btsManual.xfFac}"/></td>
				</tr>
				<tr>
					<th colspan="6">机房</th>
				</tr>
				<tr>
					<td>机房结构：</td>
					<td>
					<input name="mrStrutVal" id="mrStrutVal" type="text"/>
					<input name="btsManual.mrStrut"  id="mrStrut" type="hidden"/>
					
					</td>
					<td>机房长度(米):</td>
					<td><input name="btsManual.mrLength"  type="text" class="input150 number" value="${btsManual.mrLength}"/></td>
					<td>机房宽度(米)：</td>
					<td><input name="btsManual.mrWidth"  type="text" class="input150 number" value="${btsManual.mrWidth}"/></td>
				</tr>
				
				<tr>
					<td>机房高度（米）：</td>
					<td><input name="btsManual.mrHigh"  type="text" class="input150 number" value="${btsManual.mrHigh}"/></td>
					<td>机房所属业主或单位:</td>
					<td><input name="btsManual.mrOwner"  type="text" class="input150" value="${btsManual.mrOwner}"/></td>
					<td>机房照片：</td>
					<td><input name="btsManual.mrPic"  type="text" class="input150" value="${btsManual.mrPic}"/></td>
				</tr>
				<tr>
					<th colspan="6">外电</th>
				</tr>
				<tr>
					<td>市电引入方式：</td>
					<td><input name="btsManual.wdType"  type="text" class="input150 number" value="${btsManual.wdType}"/></td>
					<td>变压器型号:</td>
					<td><input name="btsManual.wdModel"  type="text" class="input150" value="${btsManual.wdModel}"/></td>
					<td>变压器厂家：</td>
					<td><input name="btsManual.wdFac"  type="text" class="input150" value="${btsManual.wdFac}"/></td>
				</tr>
				<tr>
					<td>所属供电局所:</td>
					<td><input name="btsManual.wdGds"  type="text" class="input150" value="${btsManual.wdGds}"/></td>
				</tr>
				<tr>
					<th colspan="6">油机配置</th>
				</tr>
				<tr>
					<td>油机类型：</td>
					<td><input name="btsManual.oeType"  type="text" class="input150" value="${btsManual.oeType}"/></td>
					<td>油机型号:</td>
					<td><input name="btsManual.oeModel"  type="text" class="input150" value="${btsManual.oeModel}"/></td>
					<td>油机功率：</td>
					<td><input name="btsManual.oePower"  type="text" class="input150" value="${btsManual.oePower}"/></td>
				</tr>
				<tr>
					<td>厂家：</td>
					<td><input name="btsManual.oeFac"  type="text" class="input150" value="${btsManual.oeFac}"/></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="5"><textarea name="btsManual.remark"  id="remark" cols="55" rows="3">${btsManual.remark}</textarea></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="5"></td>
				</tr>
			</table>
          <fieldset class="form-actions">
          <input class="btn btn-primary" type="button" onclick="add()" value="保存"/>
		  <input class="btn btn-primary" type="button" onclick="back()" value="返回"/>
          </fieldset>	
        </form>
	</div>
	</div>
</body>
</html>