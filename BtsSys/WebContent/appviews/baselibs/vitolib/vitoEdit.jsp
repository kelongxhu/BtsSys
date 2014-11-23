<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加乡镇库</title>
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
				valueFieldID : 'countryId',
				treeLeafOnly : true,
				tree : {
					data : treeData,
					checkbox:false
				},
				onSelected:function(data){
				    initCombox(data,0);
				}
			});
			//treeCombox.selectValue('${vitoLib.countryId}');
		});
	       //初始化乡镇库
	
			treeCombox2=$("#vitoIdVal").ligerComboBox( {
				width : 200,
				selectBoxWidth : 200,
				selectBoxHeight : 200,
				textField : 'text',
				valueField : 'id',
				valueFieldID : 'vitoId',
				treeLeafOnly : true,
				tree : {
					data : null,
					checkbox:false
				}
			});
	
	  initCombox('${vitoLib.countryId}','${vitoLib.parentId}');	
		
		
	//初始化控件
	$("#name").ligerTextBox({width : 200 });
	$("#gpsLong").ligerTextBox({width : 200 });
	$("#gpsLat").ligerTextBox({width : 200 });
	$("#cmda1xCovgrate").ligerTextBox({width : 200});
	$("#mobgsmCovgrate").ligerTextBox({width : 200});
	$("#covgDifCovgrate").ligerTextBox({width : 200});
	$("#unicgsmCovgrate").ligerTextBox({width : 200});
	$("#evdoCovgrate").ligerTextBox({width : 200});
	
	$("#cdma1xMos").ligerTextBox({width : 200});
	$("#mobgsmMos").ligerTextBox({width : 200});
	$("#unicgsmMos").ligerTextBox({width : 200});

	$("#testuser").ligerTextBox({width : 200});
	$("#remark").ligerTextBox({width : 600,height:50});
	
	 $("#testtime").ligerDateEditor({ label: '', format: "yyyy-MM-dd", labelAlign: 'right',width : 200,initValue: '${vitoLib.testtime}'});
	
});

    //初始化乡镇库
    function initCombox(countryId,value){
    	if(countryId==null||countryId==0){
    		return;
    	}
    	 var url="${ctx}/schooljson/townTree.action?countryId="+countryId;
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
									 window.location.href="${ctx}/school/vitoLib.action";
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
              <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>编辑乡镇库信息</p>
	</div>
	<div class="content">
	<form method="post" name="form1" id="form1" action="${ctx}/schooljson/editVitoLib.action">
		<table cellpadding="0" cellspacing="0" class="tab_1" >
            <tr>
                <th width="200px"><span style="color: red;">*</span>地区:</th>
                <td colspan="3">
					<input name="countryIdVal" id="countryIdVal" type="text" class="required" value="${city.cityName}"/>
					<input name="vitoLib.countryId" id="countryId" type="hidden" value="${city.id}"/>
					<input name="vitoLib.id" id="id" type="hidden" value="${vitoLib.id}"/>
				</td> 		
            </tr>
            
            <tr>
            <th><span style="color: red;">*</span>所属乡镇：</th>
				<td colspan="3">
				   <div style="float:left;">
					<input name="vitoIdVal" id="vitoIdVal" type="text" class="required"/>
					<input name="vitoLib.parentId" id="vitoId" type="hidden"/>
					</div>
					<div style="float:left;padding: 5px;">
					<span style="color: red;font-size:10px;">
					(注：选乡镇库(ROOT)则为乡镇库，选所属乡镇则为农村库)
					</span>
					</div>
				</td>
            </tr>
            
            <tr>
                <th><span style="color: red;">*</span>乡镇(村)名称：</th>
				<td>	
					<input name="vitoLib.name" id="name" type="text" class="{required:true}" value="${vitoLib.name}"/>
				</td>
			</tr>
			<tr>
				<th width="200px"><span style="color: red;">*</span>中心GPS经度:</th>
                <td>
					<input name="vitoLib.gpsLong" type="text" id="gpsLong" class="{number:true}" value="${vitoLib.gpsLong}"/> 
				</td>
				<th><span style="color: red;">*</span>中心GPS纬度:</th>
                <td>
					<input name="vitoLib.gpsLat" type="text" id="gpsLat" class="{number:true}" value="${vitoLib.gpsLat}"/> 
				</td>           
			</tr>
			 <tr>
				<th><span style="color: red;">*</span>电信CDMA1X2000网络覆盖率(%)：</th>
				<td>
					<input name="vitoLib.cmda1xCovgrate" id="cmda1xCovgrate" type="text" class="{required:true}" value="${vitoLib.cmda1xCovgrate}"/>
				</td>
				<th><span style="color: red;">*</span>移动GSM网络覆盖率(%)：</th>
				<td>
					<input name="vitoLib.mobgsmCovgrate" id="mobgsmCovgrate" type="text" class="{required:true}" value="${vitoLib.mobgsmCovgrate}"/>
				</td>
            </tr>
             <tr>
				<th><span style="color: red;">*</span>电信与移动覆盖差值(%)：</th>
				<td>
					<input name="vitoLib.covgDifCovgrate" id="covgDifCovgrate" type="text" class="{required:true}" value="${vitoLib.covgDifCovgrate}"/>
				</td>
				<th><span style="color: red;">*</span>联通GSM网络覆盖率(%)：</th>
				<td>
					<input name="vitoLib.unicgsmCovgrate" id="unicgsmCovgrate" type="text" class="{required:true}" value="${vitoLib.unicgsmCovgrate}"/>
				</td>
            </tr>
             <tr>
				<th><span style="color: red;">*</span>电信EVDO网络覆盖率(%)：</th>
				<td>
					<input name="vitoLib.evdoCovgrate" id="evdoCovgrate" type="text" class="{required:true}" value="${vitoLib.evdoCovgrate}"/>
				</td>
				 <th><span style="color: red;">*</span>电信CDMA1X2000语音MOS值：</th>
				<td>	
					<input name="vitoLib.cdma1xMos" id="cdma1xMos" type="text" class="{required:true}" value="${vitoLib.cdma1xMos}"/>
				</td>
            </tr>       
            <tr>
             
				<th><span style="color: red;">*</span>移动GSM语音MOS值：</th>
				<td>	
					<input name="vitoLib.mobgsmMos" id="mobgsmMos" type="text" class="{required:true}" value="${vitoLib.mobgsmMos}"/>
				</td>
             	 <th><span style="color: red;">*</span>联通GSM语音MOS值：</th>
				<td>	
					<input name="vitoLib.unicgsmMos" id="unicgsmMos" type="text" class="{required:true}" value="${vitoLib.unicgsmMos}"/>
				</td>
			</tr>
			<tr>
				<th><span style="color: red;">*</span>测试时间：</th>
				<td>	
					<input name="vitoLib.testtime" id="testtime" type="text" class="{required:true}"/>
				</td>
             	 <th><span style="color: red;">*</span>测试人员：</th>
				<td>	
					<input name="vitoLib.testuser" id="testuser" type="text" class="{required:true}" value="${vitoLib.testuser}"/>
				</td>
			</tr>
			<tr>
			<th>备注：</th>
			<td colspan="3">
					<textarea name="vitoLib.remark"  id="remark">${vitoLib.remark}</textarea>
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