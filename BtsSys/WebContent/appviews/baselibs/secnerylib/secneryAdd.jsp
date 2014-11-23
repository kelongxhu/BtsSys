<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加天线</title>
<%@ include file="/appviews/common/tag.jsp"%>

<script type="text/javascript">
$(function(){
	
	 var treeCombox;
		//初始化地市
	 	//初始化树控件
	$.post("${ctx}/schooljson/initCountryTree.action", function(
				ajaxData, status) {
			var treeData=ajaxData.cityJson;
			treeData = treeData.replace(/"children":\[\],/g, '');
      treeData=eval('('+treeData+')'); 
			treeCombox=$("#countryIdVal").ligerComboBox( {
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
			//treeCombox.initComboBox(treeData,'localNetsVal');
	});
	//初始化控件
	$("#sceName").ligerTextBox({width : 300 });
	$("#longitude").ligerTextBox({width : 200 });
	$("#latitude").ligerTextBox({width : 200 });
	$("#remark").ligerTextBox({width : 400,height:50 });
});


   //初始化景区级别
	var typeURL1="${ctx}/schooljson/cons.action?groupCode=SECNERYTYPE";
	var sl1;
	$.getJSON(typeURL1, 
		function(data){
			sl1 = $("#sceLevelVal").ligerComboBox({
				data : data.Rows,
				width : 200,
				selectBoxWidth: 200,
				textField : 'name',
				valueField : 'code',
				valueFieldID:'sceLevel'
			});
		}
	);
	
	//保存
	function add(){
		$("#form1").submit();
	}

	//注册表单验证
	$(function() {
		var v = $("#form1")
				.validate(
						{
							submitHandler : function(form) {
								//form.submit();
								 jQuery(form).ajaxSubmit(function(json){
									 if (json.result == 1) {
										 alert('增加成功!');
									}else{
										 alert('增加失败!');
									} 
									 //跳转
									 window.location.href="${ctx}/school/secneryLib.action";
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
	
	
	//返回
	function back(){
		javascript: history.go(-1);
	}

</script>
</head>
<body>
<!-- 标题 -->
	<div id="main_2">
	<!-- 标题 end-->
	<div class="main_title_2">
              <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>增加景区库信息</p>
	</div>
	<div class="content">
	<form method="post" name="form1" id="form1" action="${ctx}/schooljson/addSecneryLib.action">
		<table cellpadding="0" cellspacing="0" class="tab_1" >
            <tr>
                <th width="150px"><span style="color: red;">*</span>地区:</th>
                <td width="300px">
					<input name="countryIdVal" id="countryIdVal" type="text" class="required"/>
					<input name="secneryLib.countryId" id="countryId" type="hidden"/>
				</td>
            </tr>
            <tr>
           		<th><span style="color: red;">*</span>景区名称：</th>
				<td>
					<input name="secneryLib.sceName" id="sceName" type="text" class="required"/>
				</td>
            </tr>
             <tr>
                <th><span style="color: red;">*</span>景区级别：</th>
				<td>	
					<input name="sceLevelVal" id="sceLevelVal" type="text" class="{required:true}"/>
					<input name="secneryLib.sceLevel" id="sceLevel" type="hidden"/>
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>景区中心经度:</th>
                <td>
					<input name="secneryLib.longitude" type="text" id="longitude" class="{number:true}"/> 
				</td>           
			</tr>
			<tr>
				<th><span style="color: red;">*</span>景区中心纬度:</th>
                <td>
					<input name="secneryLib.latitude" type="text" id="latitude" class="{number:true}" /> 
				</td>
			</tr>
			 <tr>
				<th>备注：</th>
				<td>
					<textarea name="secneryLib.remark"  id="remark"></textarea>
				</td>
            </tr>
            <tr>
			   			 <td></td>
						<td>
						
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
	
	
</body>
</html>