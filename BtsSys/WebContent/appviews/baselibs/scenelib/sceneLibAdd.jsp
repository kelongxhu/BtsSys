<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加场景库信息</title>
<%@ include file="/appviews/common/tag.jsp"%>

<script type="text/javascript">
    var treeCombox;
    var countryCombox;
    var sceneLevelCombox;
    $(function(){

		//初始化地市
	 	//初始化树控件
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
				treeLeafOnly : true,
				tree : {
					data : treeData,
					checkbox:false
				},
                onSelected:function(data){
                    initCountryCombox(data);
                }
			});
             if('${wyLibScene.cityId}'!=''){
                 treeCombox.selectValue('${wyLibScene.cityId}');
             }
             if('${wyLibScene.countryId}'!=''){
                 countryCombox.selectValue('${wyLibScene.countryId}');
             }
	});
    //初始化country Combox
    //初始化乡镇库
    countryCombox=$("#countryIdVal").ligerComboBox( {
        data : null,
        width : 200,
        selectBoxWidth : 200,
        selectBoxHeight : 200,
        textField : 'cityName',
        valueField : 'id',
        valueFieldID : 'countryId'
    });

    sceneLevelCombox=$("#sceneLevelVal").ligerComboBox( {
        data : null,
        width : 200,
        selectBoxWidth : 200,
        selectBoxHeight : 200,
        textField : 'name',
        valueField : 'code',
        valueFieldID : 'sceneLevel'
    });
    //初始化控件
	$("#name").ligerTextBox({width : 300 });
	$("#longitude").ligerTextBox({width : 200 });
	$("#latitude").ligerTextBox({width : 200 });
	$("#remark").ligerTextBox({width : 400,height:50 });
});


   //初始化场景类型
	var typeURL1="${ctx}/schooljson/cons.action?groupCode=SCENE_TYPE";
	var sl1;
	$.getJSON(typeURL1, 
		function(data){
			sl1 = $("#sceneTypeVal").ligerComboBox({
				data : data.Rows,
				width : 200,
				selectBoxWidth: 200,
				textField : 'name',
				valueField : 'code',
				valueFieldID:'sceneType',
                onSelected:function(data){
                    initSceneLevel(data);
                }
			});
            if('${wyLibScene.sceneType}'!=''){
                sl1.selectValue('${wyLibScene.sceneType}');
            }
            if('${wyLibScene.sceneLevel}'!=''){
                sceneLevelCombox.selectValue('${wyLibScene.sceneLevel}');
            }
		}
	);


   function initCountryCombox(cityId){
       var url="${ctx}/adminjson/getCountrys.action?cityId="+cityId;
       //初始化树控件
       url=encodeURI(url);
       //初始化树控件
       $.post(url, function(
               data, status) {
           liger.get("countryIdVal").setData(data.Rows);
       });
   }

   function initSceneLevel(typeCode){
       var url = "${ctx}/schooljson/schooljson/cons.action?groupCode=SCENE_LEVEL&pid=" + typeCode;
       //初始化树控件
       url=encodeURI(url);
       //初始化树控件
       $.post(url, function(
               data, status) {
           liger.get("sceneLevelVal").setData(data.Rows);
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
										 alert('增加成功!');
									}else{
										 alert('增加失败!');
									} 
									 //跳转
									 window.location.href="${ctx}/school/sceneLib.action";
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
              <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>
                  <c:if test="${empty wyLibScene.id}">
                      增加场景库信息
                  </c:if>
                  <c:if test="${!empty wyLibScene.id}">
                      编辑场景库信息
                  </c:if>
              </p>
	</div>
	<div class="content">
	<form method="post" name="form1" id="form1" action="${ctx}/schooljson/addSceneLib.action">
		<table cellpadding="0" cellspacing="0" class="tab_1" >
            <tr>
                <th width="150px"><span style="color: red;">*</span>本地网:</th>
                <td width="300px">
                    <input name="wyLibScene.id"  type="hidden" value="${wyLibScene.id}"/>
					<input name="cityIdVal" id="cityIdVal" type="text" class="required"/>
					<input name="wyLibScene.cityId" id="cityId" type="hidden"/>
				</td>
                <th width="150px">区县:</th>
                <td width="300px">
                    <input name="countryIdVal" id="countryIdVal" type="text"/>
                    <input name="wyLibScene.countryId" id="countryId" type="hidden"/>
                </td>
            </tr>
            <tr>
                <th><span style="color: red;">*</span>场景类型:</th>
                <td>
                    <input name="sceneTypeVal" id="sceneTypeVal" type="text" class="required"/>
                    <input name="wyLibScene.sceneType" id="sceneType" type="hidden"/>
                </td>
                <th>场景级别:</th>
                <td>
                    <input name="sceneLevelVal" id="sceneLevelVal" type="text" class="required"/>
                    <input name="wyLibScene.sceneLevel" id="sceneLevel" type="hidden"/>
                </td>
            </tr>
            <tr>
           		<th><span style="color: red;">*</span>场景名称：</th>
				<td colspan="3">
					<input name="wyLibScene.name" id="name" type="text" class="required" value="${wyLibScene.name}"/>
				</td>
            </tr>
			<tr>
				<th>经度:</th>
                <td>
					<input name="wyLibScene.longitude" type="text" id="longitude" class="{number:true}" value="${wyLibScene.longitude}"/>
				</td>
                <th>纬度:</th>
                <td>
                    <input name="wyLibScene.latitude" type="text" id="latitude" class="{number:true}" value="${wyLibScene.latitude}"/>
                </td>
			</tr>
			<tr>

			</tr>
			 <tr>
				<th>备注：</th>
				<td colspan="3">
					<textarea name="wyLibScene.remark"  id="remark">${wyLibScene.remark}</textarea>
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