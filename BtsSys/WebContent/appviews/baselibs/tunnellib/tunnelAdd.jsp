<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加隧道库</title>
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
	});
		
		
		
	var treeCombox2;
 	//初始化道路树控件
     $.post("${ctx}/schooljson/roadTree.action", function(
			ajaxData, status) {
		var treeData=ajaxData.roadJson;
		treeData = treeData.replace(/"children":\[\],/g, '');
  		treeData=eval('('+treeData+')'); 
		treeCombox2=$("#roadIdVal").ligerComboBox( {
			width : 200,
			selectBoxWidth : 200,
			selectBoxHeight : 200,
			textField : 'text',
			valueField : 'id',
			valueFieldID : 'roadId',
			treeLeafOnly : true,
			tree : {
				data : treeData,
				checkbox:false
			}
		});
});	
		

	//初始化控件
	$("#name").ligerTextBox({width : 200 });
	$("#tunnellength").ligerTextBox({width : 200 });
	$("#direction").ligerTextBox({width : 200 });
	$("#longitude").ligerTextBox({width : 200 });
	$("#latitude").ligerTextBox({width : 200 });
	$("#height").ligerTextBox({width : 200 });
	$("#covgtype").ligerTextBox({width : 200 });
	$("#installadress").ligerTextBox({width : 200 });
	$("#togbs").ligerTextBox({width : 200 });
	$("#togname").ligerTextBox({width : 200 });
	$("#rights").ligerTextBox({width : 200 });
	$("#address").ligerTextBox({width : 400 });
	$("#remark").ligerTextBox({width : 400,height:50 });
	
	$("#opentime").ligerDateEditor({ label: '', format: "yyyy-MM-dd", labelAlign: 'right',width : 200});
});

	
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
									 window.location.href="${ctx}/school/tunnelLib.action";
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
              <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"/>增加隧道库信息</p>
	</div>
	<div class="content">
	<form method="post" name="form1" id="form1" action="${ctx}/schooljson/addTunnelLib.action">
		<table cellpadding="0" cellspacing="0" class="tab_1" >
            <tr>
                <th width="150px"><span style="color: red;">*</span>地区:</th>
                <td width="300px">
					<input name="countryIdVal" id="countryIdVal" type="text" class="required"/>
					<input name="tunnelLib.countryId" id="countryId" type="hidden"/>
				</td>
           		<th width="150px"><span style="color: red;">*</span>所属道路：</th>
				<td>
					<input name="roadIdVal" id="roadIdVal" type="text" class="required"/>
					<input name="tunnelLib.roadId" id="roadId" type="hidden"/>
				</td>
            </tr>
             <tr>
                <th><span style="color: red;">*</span>隧道名称：</th>
				<td>
					<input name="tunnelLib.name" id="name" type="text" class="required"/>
				</td>
				 <th><span style="color: red;">*</span>隧道长度：</th>
				<td>
					<input name="tunnelLib.tunnellength" id="tunnellength" type="text" class="required"/>
				</td>
			</tr>
            <tr>
                <th><span style="color: red;">*</span>方向：</th>
				<td>
					<input name="tunnelLib.direction" id="direction" type="text" class="required"/>
				</td>
				 <th><span style="color: red;">*</span>经度：</th>
				<td>
					<input name="tunnelLib.longitude" id="longitude" type="text" class="required number"/>
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>纬度:</th>
                <td>
					<input name="tunnelLib.latitude" type="text" id="latitude" class="required number" />
				</td>
				<th><span style="color: red;">*</span>海拔高度:</th>
                <td>
					<input name="tunnelLib.height" type="text" id="height" class="required number" />
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>覆盖设备类型:</th>
                <td>
					<input name="tunnelLib.covgtype" type="text" id="covgtype" class="required" />
				</td>
				<th><span style="color: red;">*</span>设备安装位置:</th>
                <td>
					<input name="tunnelLib.installadress" type="text" id="installadress" class="required" />
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>产权:</th>
                <td>
					<input name="tunnelLib.rights" type="text" id="rights" class="required" />
				</td>
				<th><span style="color: red;">*</span>是否共建共享:</th>
                <td>
					<input name="tunnelLib.togbs" type="text" id="togbs" class="required" />
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>共享方名称:</th>
                <td>
					<input name="tunnelLib.togname" type="text" id="togname" class="required" />
				</td>
				<th><span style="color: red;">*</span>设备开通年月:</th>
                <td>
					<input name="tunnelLib.opentime" type="text" id="opentime" class="required" />
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>详细地址:</th>
                <td colspan="3">
					<input name="tunnelLib.address" type="text" id="address" class="required" />
				</td>
			</tr>
			 <tr>
				<th>备注：</th>
				<td>
					<textarea name="tunnelLib.remark"  id="remark"></textarea>
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