<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加道路库</title>
<%@ include file="/appviews/common/tag.jsp"%>

<script type="text/javascript">
$(function(){
	
	//选择地市
	$.post("${ctx}/schooljson/initCityTree.action", function(
			ajaxData, status) {
		var treeData=ajaxData.cityJson;
		treeData = treeData.replace(/"children":\[\],/g, '');
        treeData=eval('('+treeData+')'); 
		treeCombox=$("#cityIdVal").ligerComboBox( {
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
     });
	
	//初始化控件
	$("#name").ligerTextBox({width : 200 });
	$("#speed").ligerTextBox({width : 200 });
	$("#lineMile").ligerTextBox({width : 200 });
	$("#tunnelMile").ligerTextBox({width : 200 });
	$("#plainMile").ligerTextBox({width : 200 });
	$("#mountainMile").ligerTextBox({width : 200 });
	$("#stationNum").ligerTextBox({width : 200 });
	$("#coverMile1x").ligerTextBox({width : 200 });
	$("#coverRate1x").ligerTextBox({width : 200 });
	$("#dropcallMile").ligerTextBox({width : 200 });
	$("#coverMileDo").ligerTextBox({width : 200 });
	$("#coverRateDo").ligerTextBox({width : 200 });
	$("#downTpRate").ligerTextBox({width : 200 });
	$("#downTpgoodRate").ligerTextBox({width : 200 });
	$("#coverStationNum").ligerTextBox({width : 200 });
	$("#coverStationRate").ligerTextBox({width : 200 });
	
	
	
	
	$("#testuser").ligerTextBox({width : 200 });
	$("#remark").ligerTextBox({width : 600 ,height:50 });
	
	 $("#testtime").ligerDateEditor({ label: '', format: "yyyy-MM-dd", labelAlign: 'right',width : 200});
});


   //道路属性
	var typeURL1="${ctx}/schooljson/cons.action?groupCode=ROADPROP";
	var sl1;
	$.getJSON(typeURL1, 
		function(data){
			sl1 = $("#roadPropVal").ligerComboBox({
				data : data.Rows,
				width : 200,
				selectBoxWidth: 200,
				textField : 'name',
				valueField : 'code',
				valueFieldID:'roadProp'
			});
		}
	);
	
	//沿线CDMA网络覆盖方式
	var typeURL2="${ctx}/schooljson/cons.action?groupCode=COVERTYPE";
	var sl2;
	$.getJSON(typeURL2, 
		function(data){
			sl2 = $("#covertypeVal").ligerComboBox({
				data : data.Rows,
				width : 200,
				selectBoxWidth: 200,
				textField : 'name',
				valueField : 'code',
				valueFieldID:'covertype'
			});
		}
	);
	
	
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
									 window.location.href="${ctx}/school/roadLib.action";
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
              <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>增加道路库信息</p>
	</div>
	<div class="content">
	<form method="post" name="form1" id="form1" action="${ctx}/schooljson/addRoadLib.action">
		<table cellpadding="0" cellspacing="0" class="tab_1" >
            <tr>
                <th width="170px"><span style="color: red;">*</span>本地网:</th>
                <td width="300px">
					<input name="cityIdVal" id="cityIdVal" type="text" class="required"/>
					<input name="roadLib.cityId" id="cityId" type="hidden"/>
				</td>
				<th width="170px"><span style="color: red;">*</span>线路属性:</th>
                <td width="300px">
					<input name="roadPropVal" type="text" id="roadPropVal" class="{required:true}"/> 
					<input name="roadLib.roadProp" type="hidden" id="roadProp" />
				</td>
            </tr>
            <tr>
           		<th><span style="color: red;">*</span>线路名称：</th>
				<td>
					<input name="roadLib.name" type="text" id="name" class="{required:true}"/> 
				</td>
				<th><span style="color: red;">*</span>设计时速：</th>
				<td>
					<input name="roadLib.speed" type="text" id="speed" class="{required:true}"/> 
				</td>
            </tr>
             <tr>
                <th><span style="color: red;">*</span>辖区内线路里程(km)：</th>
				<td>
					<input name="roadLib.lineMile" id="lineMile" type="text" class="{required:true,number:true}"/>
				</td>
				<th><span style="color: red;">*</span>辖区内隧道里程(km):</th>
                <td>
					<input name="roadLib.tunnelMile" type="text" id="tunnelMile" class="{required:true,number:true}"/> 
				</td>           
			</tr>
			<tr>
				<th><span style="color: red;">*</span>辖区内平原段里程(km):</th>
                <td>
					<input name="roadLib.plainMile" type="text" id="plainMile" class="{required:true,number:true}" /> 
				</td>
				<th><span style="color: red;">*</span>辖区内山区段里程(km)：</th>
				<td>
					<input name="roadLib.mountainMile" type="text" id="mountainMile" class="{required:true,number:true}"/> 
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>辖区内车站（收费站或服务区）数量：</th>
				<td>
			        <input name="roadLib.stationNum" type="text" id="stationNum" class="{required:true,number:true}"/> 
				</td>
				 <th><span style="color: red;">*</span>沿线CDMA网络覆盖方式:</th>
                <td>
					<input name="covertypeVal" type="text" id="covertypeVal" class="{required:true}"/> 
					<input name="roadLib.covertype" type="hidden" id="covertype" />
				</td>
            </tr>
   
            <tr>
				<th>辖区内1X覆盖里程（km）：</th>
				<td>
					<input name="roadLib.coverMile1x" type="text" id="coverMile1x" class="{number:true)"/>
				</td>
				<th> 辖区内1X里程覆盖率（%）：</th>
				<td>
					<input name="roadLib.coverRate1x" type="text" id="coverRate1x" class="{number:true}"/>
				</td>
			</tr>
				<tr>
				
				<th>里程掉话比（km）：</th>
				<td>
					<input name="roadLib.dropcallMile" type="text" id="dropcallMile" class="{number:true}"/>
				</td>
				<th>辖区内DO覆盖里程（km）：</th>
				<td>
					<input name="roadLib.coverMileDo" type="text" id="coverMileDo" class="{number:true}"/>
				</td>
            </tr>
            <tr>		
				<th>辖区内DO里程覆盖率（%）：</th>
				<td>
					<input name="roadLib.coverRateDo" type="text" id="coverRateDo" class="{number:true}"/>
				</td>
				<th>覆盖区域的下行吞吐率（%）：</th>
				<td>
					<input name="roadLib.downTpRate" type="text" id="downTpRate" class="{number:true}"/>
				</td>
            </tr>
             <tr>
				
				<th>覆盖区域下行吞吐率优良比（>=300kbps）：</th>
				<td>
					<input name="roadLib.downTpgoodRate" type="text" id="downTpgoodRate" class="{number:true}"/>
				</td>
				<th>辖区内已覆盖车站（收费站或服务区）数量：</th>
				<td>
					<input name="roadLib.coverStationNum" type="text" id="coverStationNum" class="{number:true}"/>
				</td>
				
				
			
            </tr>
            <tr>
				
				<th> 辖区内已覆盖车站(收费站或服务区)覆盖比例：</th>
				<td>
					<input name="roadLib.coverStationRate" type="text" id="coverStationRate" class="{number:true}"/>
				</td>
				<th>路测时间：</th>
				<td>
					<input name="roadLib.testtime" type="text" id="testtime"/>
				</td>
				
					
            </tr>
            
            <tr>
            	<th> 路测人员：</th>
				<td colspan="3">
					<input name="roadLib.testuser" type="text" id="testuser"/>
				</td>
            
            </tr>
            
           
            <tr>
				<th>备注：</th>
				<td colspan="3">
					<textarea name="roadLib.remark"  id="remark"></textarea>
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