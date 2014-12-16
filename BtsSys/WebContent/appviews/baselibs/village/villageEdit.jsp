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
									 window.location.href="${ctx}/school/village.action";
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
              <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"/>编辑乡镇农村库信息</p>
	</div>
	<div class="content">
	<form method="post" name="form1" id="form1" action="${ctx}/schooljson/editVillage.action">
		<table cellpadding="0" cellspacing="0" class="tab_1" >
            <tr>
                <th width="150px">本地网:</th>
                <td width="300px">${wyLibVillage.city.cityName}</td>
                <th width="150px">区县:</th>
                <td width="300px">${wyLibVillage.country.cityName}</td>
            </tr>
            <tr>
                <th>乡镇:</th>
                <td>${wyLibVillage.town}</td>
                <th>农村:</th>
                <td>${wyLibVillage.village}</td>
            </tr>

			<tr>
				<th>经度:</th>
                <td>
                    <input name="wyLibVillage.id" type="hidden" value="${wyLibVillage.id}"/>
					<input name="wyLibVillage.longitude" type="text"  class="number input150" value="${wyLibVillage.longitude}"/>
				</td>
				<th>纬度:</th>
                <td>
					<input name="wyLibVillage.latitude" type="text" class="number input150" value="${wyLibVillage.latitude}"/>
				</td>           
			</tr>
            </table>
			   		
           <div class="form-actions_2">
                <button class="btn btn-primary" type="button" onclick="add();">
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