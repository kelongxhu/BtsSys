<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加盲点库</title>
<%@ include file="/appviews/common/tag.jsp"%>

<script type="text/javascript">
var treeCombox2;

$(function(){
 var treeCombox;
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
				valueFieldID : 'countyId',
				treeLeafOnly : true,
				tree : {
					data : treeData,
					checkbox:false
				},
				onSelected:function(data){
				    initCombox(data,0);
				}
			});
			//treeCombox.selectValue('${wyBlind.countyId}');
		});
		
	//初始化乡镇库
	treeCombox2=$("#vitoIdVal").ligerComboBox( {
		width : 200,
		selectBoxWidth : 200,
		selectBoxHeight : 200,
		textField : 'text',
		valueField : 'id',
		valueFieldID : 'vitoId',
		treeLeafOnly : false,
		tree : {
			data : null,
			checkbox:false
		}
	});		
	
	initCombox('${wyBlind.countyId}','${wyBlind.vitoId}');	
		
	//初始化乡镇库
    function initCombox(countryId,value){
    	if(countryId==null||countryId==0){
    		return;
    	}
    	 var url="${ctx}/schooljson/vitoTree.action?countryId="+countryId;
 	 	//初始化树控件
 			$.post(url, function(
 				ajaxData, status) {
 			var treeData=ajaxData.townJson;
 			treeData = treeData.replace(/"children":\[\],/g, '');
      	    treeData=eval('('+treeData+')'); 
      	    var treeManager=treeCombox2.tree.ligerGetTreeManager();
      	    treeManager.clear();
      	    treeManager.setData(treeData);
      	    treeCombox2.setValue(value);
 		});
    }
		
	//初始化控件
	$("#name").ligerTextBox({width : 200 });
	$("#longitude").ligerTextBox({width : 200 });
	$("#latitude").ligerTextBox({width : 200 });	
	$("#solution").ligerTextBox({width : 200 });
	$("#causesBy").ligerTextBox({width : 200 });
	$("#landMark").ligerTextBox({width : 200 });
	$("#address").ligerTextBox({width : 200 });
	$("#remarks").ligerTextBox({width : 400,height:50 });
	
	 //网格类型
	 $("#gridTypeVal").ligerComboBox({  
         data: [
             { text: '市区', id: '0' },
             { text: '城郊', id: '1' },
             { text: '县城', id: '2' },
             { text: '乡镇', id: '3' },
             { text: '农村', id: '4' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'gridType'
     });
	
	//是否1X
	 $("#is1xVal").ligerComboBox({  
         data: [
             { text: '是', id: '1' },
             { text: '否', id: '0' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'is1x'
     });
     
     //是否DO
	 $("#isDoVal").ligerComboBox({  
         data: [
             { text: '是', id: '1' },
             { text: '否', id: '0' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'isDo'
     });
	
	 //是否LTE
	 $("#isLteVal").ligerComboBox({  
         data: [
             { text: '是', id: '1' },
             { text: '否', id: '0' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'isLte'
     });
     
     //区域类型1
	 $("#blindType1Val").ligerComboBox({  
         data: [
             { text: '室内', id: '1' },
             { text: '室外', id: '0' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'blindType1'
     });
     
     //区域类型2
	 $("#blindType2Val").ligerComboBox({  
         data: [
             { text: '高铁', id: '0' },
             { text: '高速公路', id: '1' },
             { text: '机场', id: '2' },
             { text: '车站港口码头', id: '3' },
             { text: '住宅小区', id: '4' },
             { text: '风景区', id: '5' },
             { text: '宾馆', id: '6' },
             { text: '写字楼', id: '7' },
             { text: '商场', id: '8' },
             { text: '体育场', id: '9' },
             { text: '地铁', id: '10' },
             { text: '海域', id: '11' },
             { text: '校园', id: '12' },
             { text: '隧道', id: '13' },
             { text: '医院', id: '14' },
             { text: '政府机关', id: '15' },
             { text: '工业园区', id: '16' },
             { text: '农村', id: '17' },
             { text: '水运', id: '18' },
             { text: '聚类市场', id: '19' },
             { text: '休闲娱乐场所', id: '20' },
             { text: '其它', id: '21' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'blindType2'
     });     
	
	//解决方式
	 $("#solTypeVal").ligerComboBox({  
         data: [
             { text: '网络优化', id: '0' },
             { text: '规划建站', id: '1' },
             { text: '辅助手段', id: '2' },
             { text: 'WiFi解决', id: '3' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'solType'
     });
     
     //辅助手段
	 $("#assistTypeVal").ligerComboBox({  
         data: [
             { text: 'BPA', id: '0' },
             { text: '塔放', id: '1' },
             { text: '壁虎', id: '2' },
             { text: '手机伴侣', id: '3' },
             { text: '五类线', id: '4' },
             { text: '其它', id: '5' },
             { text: '无', id: '6' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'assistType'
     });
     
          	//状态
	 $("#statusVal").ligerComboBox({  
         data: [
             { text: '已解决', id: '1' },
             { text: '未解决', id: '0' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'status'
     });
     
          	 //是否
	 $("#isPlanVal").ligerComboBox({  
         data: [
             { text: '有', id: '1' },
             { text: '无', id: '0' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'isPlan'
     });
     
     $("#planSolutionTime").ligerDateEditor({ label: '', format: "yyyy-MM-dd", labelAlign: 'right',width : 200});
     $("#realSolutionTime").ligerDateEditor({ label: '', format: "yyyy-MM-dd", labelAlign: 'right',width : 200});
	
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
										 alert('编辑成功!');
									}else{
										 alert('编辑失败!');
									} 
									 //跳转
									 window.location.href="${ctx}/school/blind.action";
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
              <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"/>编辑盲点库信息</p>
	</div>
	<div class="content">
	<form method="post" name="form1" id="form1" action="${ctx}/schooljson/editBlind.action">
		<table cellpadding="0" cellspacing="0" class="tab_1" >
             <tr>
                <th><span style="color: red;">*</span>盲点名称：</th>
				<td>
					<input name="wyBlind.name" id="name" type="text" class="required" value="${wyBlind.name }"/>
				</td>
				<th width="150px"><span style="color: red;">*</span>区县:</th>
                <td width="300px">
					<input name="countryIdVal" id="countryIdVal" type="text" class="required" style="width: 200px;" value="${country.cityName }"/>
					<input name="wyBlind.countyId" id="countyId" type="hidden" />
				</td>
			</tr>		
            <tr>
           		<th width="150px">农村和乡镇：</th>
				<td>
					<input name="vitoIdVal" id="vitoIdVal" type="text"/>
					<input name="wyBlind.vitoId" id="vitoId" type="hidden"/>
				</td>
				<th><span style="color: red;">*</span>网格类型：</th>
				<td>
					<input name="gridTypeVal" id="gridTypeVal" type="text" class="required" />
					<input name="wyBlind.gridType" id="gridType" type="hidden" value="${wyBlind.gridType }"/>
				</td>				
            </tr>
             <tr>
                <th><span style="color: red;">*</span>经度：</th>
				<td>
					<input name="wyBlind.longitude" id="longitude" type="text" class="required number" value="${wyBlind.longitude }"/>
				</td>
				<th><span style="color: red;">*</span>纬度:</th>
                <td>
					<input name="wyBlind.latitude" type="text" id="latitude" class="required number" value="${wyBlind.latitude }"/>
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>区域类型1:</th>
                <td>
					<input name="blindType1Val" type="text" id="blindType1Val" class="required" />
					<input name="wyBlind.blindType1" type="hidden" id="blindType1" value="${wyBlind.blindType1 }"/>
				</td>
				<th>区域类型2:</th>
                <td>
					<input name="blindType2Val" type="text" id="blindType2Val" />
					<input name="wyBlind.blindType2" type="hidden" id="blindType2" value="${wyBlind.blindType2 }"/>
				</td>				
			</tr>
			<tr>
				<th><span style="color: red;">*</span>盲点状态:</th>
                <td>
					<input name="statusVal" type="text" id="statusVal" class="required" />
					<input name="wyBlind.status" type="hidden" id="status" value="${wyBlind.status }"/>
				</td>
				<th><span style="color: red;">*</span>是否1X盲点:</th>
                <td>
					<input name="is1xVal" type="text" id="is1xVal" class="required" />
					<input name="wyBlind.is1x" type="hidden" id="is1x" value="${wyBlind.is1x }"/>
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>是否DO盲点:</th>
                <td>
                	<input name="isDoVal" type="text" id="isDoVal" class="required" />
					<input name="wyBlind.isDo" type="hidden" id="isDo" value="${wyBlind.isDo }"/>
				</td>			
				<th><span style="color: red;">*</span>是否LTE盲点:</th>
                <td>
					<input name="isLteVal" type="text" id="isLteVal" class="required" />
					<input name="wyBlind.isLte" type="hidden" id="isLte" value="${wyBlind.isLte }"/>
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>解决方式:</th>
                <td>
					<input name="solTypeVal" type="text" id="solTypeVal" class="required" />
					<input name="wyBlind.solType" type="hidden" id="solType" value="${wyBlind.solType }"/>
				</td>
				<th>辅助手段:</th>
                <td>
					<input name="assistTypeVal" type="text" id="assistTypeVal"/>
					<input name="wyBlind.assistType" type="hidden" id="assistType" value="${wyBlind.assistType }">
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>形成原因：</th>
				<td>
					<input name="wyBlind.causesBy" id="causesBy" type="text" class="required" value="${wyBlind.causesBy }"/>
				</td>
                <th>有无规划站:</th>
                <td>
                    <input name="isPlanVal" type="text" id="isPlanVal" />
                    <input name="wyBlind.isPlan" type="hidden" id="isPlan" value="${wyBlind.isPlan}"/>
                </td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>标志建筑：</th>
				<td>
					<input name="wyBlind.landMark" id="landMark" type="text" class="required" value="${wyBlind.landMark }"/>
				</td>
            	<th><span style="color: red;">*</span>地址：</th>
				<td>
					<input name="wyBlind.address" id="address" type="text" class="required" value="${wyBlind.address }"/>
				</td>
			</tr>
			<tr>
				<th>计划解决时间:</th>
                <td>
					<input name="wyBlind.planSolutionTime" type="text" id="planSolutionTime" value="${wyBlind.planSolutionTmStr }"/>
				</td>
				<th>实际解决时间:</th>
                <td>
					<input name="wyBlind.realSolutionTime" type="text" id="realSolutionTime" value="${wyBlind.realSolutionTmStr }"/>
				</td>
			</tr>

			 <tr>
				<th>备注：</th>
				<td colspan="3">
					<textarea name="wyBlind.remarks"  id="remarks">${wyBlind.remarks }</textarea>
				</td>
            </tr>
            <tr>
			   	<td></td>
				<td></td>
			</tr>

         </table>
         <input type="hidden" name="wyBlind.id" value="${wyBlind.id }">
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