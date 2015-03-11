<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加道路库</title>
<%@ include file="/appviews/common/tag.jsp"%>

<script type="text/javascript">
    var treeCombox=null;
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
        if('${roadLib.cityId}'!=""){
            treeCombox.selectValue('${roadLib.cityId}');
        }
     });

    //开通状态

    var comBox1 = $("#openStatusVal").ligerComboBox({
        data: [
            { text: '已开通', id: '1' },
            { text: '部分开通', id: '2' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'openStatus'
    });

    if('${roadLib.openStatus}'!=""){
        comBox1.selectValue('${roadLib.openStatus}');
    }

	//初始化控件
	$("#name").ligerTextBox({width : 200 });
	$("#roadNo").ligerTextBox({width : 200 });
	$("#domesiicStart").ligerTextBox({width : 200 });
	$("#domesiicEnd").ligerTextBox({width : 200 });
	$("#mileage").ligerTextBox({width : 200 });
	$("#remark").ligerTextBox({width : 600 ,height:50 });
});


   //道路属性
	var typeURL1="${ctx}/schooljson/cons.action?groupCode=ROAD_TYPE";
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
            if('${roadLib.roadProp}'!=''){
                sl1.selectValue('${roadLib.roadProp}');
            }
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
										 alert('操作成功!');
									}else{
										 alert('操作失败!');
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
              <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>
                  <c:if test="${empty roadLib.id}">
                      增加道路库信息
                  </c:if>
                  <c:if test="${!empty roadLib.id}">
                      编辑道路库信息
                  </c:if>
              </p>
	</div>
	<div class="content">
	<form method="post" name="form1" id="form1" action="${ctx}/schooljson/addRoadLib.action">
		<table cellpadding="0" cellspacing="0" class="tab_1" >
            <tr>
                <th width="170px"><span style="color: red;">*</span>本地网:</th>
                <td width="300px">
                    <input name="roadLib.id"  type="hidden" value="${roadLib.id}"/>
					<input name="cityIdVal" id="cityIdVal" type="text" class="required"/>
					<input name="roadLib.cityId" id="cityId" type="hidden"/>
				</td>
				<th width="170px"><span style="color: red;">*</span>道路类型:</th>
                <td width="300px">
					<input name="roadPropVal" type="text" id="roadPropVal" class="{required:true}"/> 
					<input name="roadLib.roadProp" type="hidden" id="roadProp" />
				</td>
            </tr>
            <tr>
                <th><span style="color: red;">*</span>道路编号：</th>
                <td>
                    <input name="roadLib.roadNo" type="text" id="roadNo" class="{required:true}" value="${roadLib.roadNo}"/>
                </td>
           		<th><span style="color: red;">*</span>道路名称：</th>
				<td>
					<input name="roadLib.name" type="text" id="name" class="{required:true}" value="${roadLib.name}"/>
				</td>

            </tr>
             <tr>
                <th><span style="color: red;">*</span>境内起点：</th>
				<td>
					<input name="roadLib.domesiicStart" id="domesiicStart" type="text" class="{required:true}" value="${roadLib.domesiicStart}"/>
				</td>
				<th><span style="color: red;">*</span>境内终点:</th>
                <td>
					<input name="roadLib.domesiicEnd" type="text" id="domesiicEnd" class="{required:true}" value="${roadLib.domesiicEnd}"/>
				</td>           
			</tr>
			<tr>
				<th><span style="color: red;">*</span>里程(km):</th>
                <td>
					<input name="roadLib.mileage" type="text" id="mileage" class="{required:true,number:true}" value="${roadLib.mileage}" />
				</td>
				<th><span style="color: red;">*</span>开通状态：</th>
				<td>
					<input type="text" id="openStatusVal" class="{required:true}"/>
                    <input name="roadLib.openStatus" type="hidden" id="openStatus"/>
				</td>
			</tr>
            <tr>
				<th>备注：</th>
				<td colspan="3">
					<textarea name="roadLib.remark"  id="remark">${roadLib.remark}</textarea>
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