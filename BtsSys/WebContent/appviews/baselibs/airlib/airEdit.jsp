<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>编辑天线库</title>
<%@ include file="/appviews/common/tag.jsp"%>

<script type="text/javascript">
$(function(){
	//初始化控件
	$("#airModel").ligerTextBox({width : 200 });
	$("#degree").ligerTextBox({width : 200 });
	$("#freq").ligerTextBox({width : 200 });
	$("#trapWidth").ligerTextBox({width : 200 });
	$("#airPlus").ligerTextBox({width : 200 });
	$("#trapWidth").ligerTextBox({width : 200 });
	$("#trapWidth").ligerTextBox({width : 200 });
	$("#trapWidth").ligerTextBox({width : 200 });
	$("#flatLobe").ligerTextBox({width : 200 });
	$("#uplobe").ligerTextBox({width : 200 });
	$("#inputDrag").ligerTextBox({width : 200 });
	$("#wireScale").ligerTextBox({width : 200 });
	$("#standingWave").ligerTextBox({width : 200 });
	$("#pitchUp").ligerTextBox({width : 200 });
	$("#pitchDown").ligerTextBox({width : 200 });
	$("#remark").ligerTextBox({width : 600 ,height:50 });
	
	//是否美化天线
	var comBox1= $("#prettifyFlagVal").ligerComboBox({  
         data: [
             { text: '是', id: '1' },
             { text: '否', id: '0' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'prettifyFlag'
     });
	comBox1.selectValue('${airLib.prettifyFlag}');
	//是否可变电调
	 var comBox2=$("#ivaryFlagVal").ligerComboBox({  
         data: [
             { text: '是', id: '1' },
             { text: '否', id: '0' }
         ],
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'ivaryFlag'
     });
    comBox2.selectValue('${airLib.ivaryFlag}');
});


   //初始化天线厂家
	var typeURL1="${ctx}/schooljson/cons.action?groupCode=AIRFAC";
	var sl1;
	$.getJSON(typeURL1, 
		function(data){
			sl1 = $("#airFacVal").ligerComboBox({
				data : data.Rows,
				width : 200,
				selectBoxWidth: 200,
				textField : 'name',
				valueField : 'code',
				valueFieldID:'airFac'
			});
		sl1.selectValue('${airLib.airFac}');
		}
	);
	
	//初始化天线类型
	var typeURL2="${ctx}/schooljson/cons.action?groupCode=AIRTYPE";
	var sl2;
	$.getJSON(typeURL2, 
		function(data){
		sl2 = $("#airTypeVal").ligerComboBox({
				data : data.Rows,
				width : 200,
				selectBoxWidth: 200,
				textField : 'name',
				valueField : 'code',
				valueFieldID:'airType'
			});
		sl2.selectValue('${airLib.airType}');
		}
	);
	
	//初始化天线类型
	var typeURL3="${ctx}/schooljson/cons.action?groupCode=JHTYPE";
	var sl3;
	$.getJSON(typeURL3, 
		function(data){
		sl3 = $("#jhTypeVal").ligerComboBox({
				data : data.Rows,
				width : 200,
				selectBoxWidth: 200,
				textField : 'name',
				valueField : 'code',
				valueFieldID:'jhType'
			});
		sl3.selectValue('${airLib.jhType}');
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
										 alert('编辑成功!');
									}else{
										 alert('编辑失败!');
									} 
									 //跳转
									 window.location.href="${ctx}/school/airLib.action";
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
              <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>编辑天线库信息</p>
	</div>
	<div class="content">
	<form method="post" name="form1" id="form1" action="${ctx}/schooljson/editAirLib.action">
		<table cellpadding="0" cellspacing="0" class="tab_1" >
            <tr>
                <th width="150px"><span style="color: red;">*</span>天线型号:</th>
                <td width="300px">
					<input name="airLib.airModel" id="airModel" type="text" class="required" value="${airLib.airModel}" readonly/>
					<input name="airLib.id" type="hidden" id="id" value="${airLib.id}"/>
				</td>
				<th width="150px"><span style="color: red;">*</span>天线厂家:</th>
                <td width="300px">
					<input name="airFacVal" type="text" id="airFacVal" class="{required:true}"/> 
					<input name="airLib.airFac" type="hidden" id="airFac" />
				</td>
            </tr>
            <tr>
           		<th><span style="color: red;">*</span>是否美化天线：</th>
				<td>
					<input name="prettifyFlagVal" type="text" id="prettifyFlagVal" class="{required:true}"/> 
					<input name="airLib.prettifyFlag" type="hidden" id="prettifyFlag" />
				</td>
				<th><span style="color: red;">*</span>是否可变电调：</th>
				<td>
					<input name="ivaryFlagVal" type="text" id="ivaryFlagVal" class="{required:true}"/> 
					<input name="airLib.ivaryFlag" type="hidden" id="ivaryFlag" />
				</td>
            </tr>
             <tr>
                <th><span style="color: red;">*</span>内置电下倾度数(单位度)：</th>
				<td>
					<input name="airLib.degree" id="degree" type="text" class="{required:true,number:true}" value="${airLib.degree}"/>
				</td>
				<th>频率范围(单位MHz):</th>
                <td>
					<input name="airLib.freq" type="text" id="freq" class="{number:true}" value="${airLib.freq}"/> 
				</td>           
			</tr>
			<tr>
				<th>频带宽度(单位MHz):</th>
                <td>
					<input name="airLib.trapWidth" type="text" id="trapWidth" class="{number:true}" value="${airLib.trapWidth}"/> 
				</td>
				<th>天线增益(单位dBi)：</th>
				<td>
					<input name="airLib.airPlus" type="text" id="airPlus" class="{number:true}" value="${airLib.airPlus}"/> 
				</td>
			</tr>
			<tr>
				<th>天线类型：</th>
				<td>
			     	<input name="airTypeVal" type="text" id="airTypeVal"/>
					<input name="airLib.airType" type="hidden" id="airType" />
				</td>
				 <th>极化方式:</th>
                <td>
					<input name="jhTypeVal" type="text" id="jhTypeVal" /> 
					<input name="airLib.jhType" type="hidden" id="jhType" />
				</td>
            </tr>
   
            <tr>
				<th>水平波瓣(单位度)：</th>
				<td>
					<input name="airLib.flatLobe" type="text" id="flatLobe" class="{number:true)" value="${airLib.flatLobe}"/>
				</td>
				<th> 垂直波瓣（单位度）：</th>
				<td>
					<input name="airLib.uplobe" type="text" id="uplobe" class="{number:true}" value="${airLib.uplobe}"/>
				</td>
			</tr>
				<tr>
				
				<th>输入阻抗（单位欧姆）：</th>
				<td>
					<input name="airLib.inputDrag" type="text" id="inputDrag" class="{number:true}" value="${airLib.inputDrag}"/>
				</td>
				<th>天线前后比（单位dB）：</th>
				<td>
					<input name="airLib.wireScale" type="text" id="wireScale" class="{number:true}" value="${airLib.wireScale}"/>
				</td>
            </tr>
            <tr>		
				<th>驻波系数：</th>
				<td>
					<input name="airLib.standingWave" type="text" id="standingWave" class="{number:true}" value="${airLib.standingWave}"/>
				</td>
				<th>俯仰上旁瓣抑制（单位dB）：</th>
				<td>
					<input name="airLib.pitchUp" type="text" id="pitchUp" class="{number:true}" value="${airLib.pitchUp}"/>
				</td>
            </tr>
             <tr>
				
				<th>俯仰下旁瓣抑制（单位dB）：</th>
				<td>
					<input name="airLib.pitchDown" type="text" id="pitchDown" class="{number:true}" value="${airLib.pitchDown}"/>
				</td>
            </tr>
            <tr>
				<th>备注：</th>
				<td colspan="3">
					<textarea name="airLib.remark"  id="remark">${airLib.remark}</textarea>
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