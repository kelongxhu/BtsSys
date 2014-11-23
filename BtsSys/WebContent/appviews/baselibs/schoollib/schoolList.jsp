<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>校园库管理</title>
<%@ include file="/appviews/common/tag.jsp"%>
<script type="text/javascript">
var gridObj = null;
$(function(){
	         var treeCombox;
				//初始化地市
			 	//初始化树控件
			$.post("${ctx}/schooljson/initCityTree.action", function(
						ajaxData, status) {
					var treeData=ajaxData.cityJson;
					treeData = treeData.replace(/"children":\[\],/g, '');
	                treeData=eval('('+treeData+')'); 
					treeCombox=$("#cityId").ligerComboBox( {
						width : 180,
						selectBoxWidth : 200,
						selectBoxHeight : 200,
						textField : 'text',
						valueField : 'id',
						valueFieldID : 'cityIdVal',
						treeLeafOnly : false,
						tree : {
							data : treeData
						}
					});
					//treeCombox.initComboBox(treeData,'localNetsVal');
			});
    //toptoolbar
    $("#toptoolbar").ligerToolBar({ 
		items: [
	             { text: '增加', click: add2 , icon:'add'},
	             { text: '编辑', click: edit , icon:'modify'},
	             { text: '删除', click: del , icon:'delete'}
	           ]
	});
	
    gridObj=$("#maingrid").ligerGrid({
			columns: [{display:'地市',name:'cityName',width : 60,align:'center'},
			          {display:'名称',name:'name',width : 200,align:'center'},
			          {display:'地址',name:'address',width : 120,align:'center'},
			          {display:'等级',name:'schoolLevelName',width : 60,align:'center'},
			          {display:'类型',name:'schoolTypeName',width : 100,align:'center'},
			          {display:'覆盖方式',name:'coverageType',width : 100,align:'center'},              		          
			          {display:'结构特点',name:'structuralTypeName',width :100,align:'center'},              		          
			          {display:'WiFi覆盖',name:'wifiTypeName',width : 100,align:'center'},
			          //{display:'C网用户总数量',name:'cusers',width : 60,align:'center'},              		          
			          //{display:'DO用户总数量',name:'doUsers',width : 60,align:'center'},              		          
			          //{display:'计划发展C网用户总数量',name:'cusersPlan',width : 60,align:'center'},              		          
			          //{display:'计划发展DO用户总数量',name:'doUsersPlan',width : 60,align:'center'} 
			          {display:'套餐类型',name:'mealType',width : 80,align:'center'}, 
			          {display:'业务类型',name:'businessType',width : 90,align:'center'} 
			],
			rownumbers:true,
			showTitle : false,
			pageSize : 50,
			pageSizeOptions:[50,100],
			url:'${ctx}/schooljson/schoolLibData.action',
			checkbox : true,
			width: '100%',
			height:'100%',
            onDblClickRow :schoolInfo
		}); 
 	$("#pageloading").hide();
 });
 
 //增加页面
function add()
{
	var url='${ctx}/appviews/baselibs/schoollib/schoolAdd.jsp';
	$.ligerDialog.open( {
			height : 450,
			url : url,
			width : 1000,
			name : 'school',
			title : '增加校园库',
			showMax:true,
			showToggle: true, 
			showMin: true,
			isResize: true,
			slide: false,
			buttons : [ {
				text : '增加',
				onclick : function(item, dialog) {
					document.getElementById('school').contentWindow.editData(dialog,gridObj);
				}
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
} 
 	
 	//添加页面
 	function add2(){
 	     window.location.href="${ctx}/school/addPage.action";
 	}

    function schoolInfo(data){
         window.location.href="${ctx}/school/schoolInfo.action?id="+data.id;
    }
 	
 	//编辑页面
 	function edit(){
		var rows = gridObj.getCheckedRows();
		var j=rows.length;
		if(j==0){
			$.ligerDialog.alert('请选择要编辑的数据！');
			return;
		}else if (j > 1) {
			 $.ligerDialog.alert('请选择一条编辑！');
			return;
		}
		var id;
		$(rows).each(function() {
			id=this.id;
		});
		window.location.href="${ctx}/school/addPage.action?id="+id;
	}
 	//删除操作
 	function del(){
 		var rows = gridObj.getCheckedRows();
		var str="";
		$(rows).each(function() {
			str += this.id + ",";
		});
		if (str.length == 0) {
			 $.ligerDialog.alert('请选择要删除的数据！');
			return;
		} else {
			str = str.substring(0, str.length - 1);
		}
		
		$.ligerDialog.confirm('确认删除', function (yes) { 
			var params = {
					ids : str
				};
		$.getJSON('${ctx}/schooljson/delSchoolLib.action', params, function(json) {
			 if (json.result == 1) {
				 alert('删除成功!');
			}else{
				 alert('删除失败！');
			} 
			 gridObj.loadData();
		 });
			
		});
 	}


	//查询
	function toSearch() {
		//处理地区
		var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
		
		gridObj.setOptions({
			newPage : 1
		});
		gridObj.setOptions({
			url : encodeURI("${ctx}/schooljson/schoolLibData.action?cityIds="
					+ cityIds)
		});
		gridObj.loadData(); //加载数据
	}
</script>
</head>
<body>
<div id="main">
<!-- 标题
	  <div class="main_title_2">
	  <!-- 
	    <img class="imgFleft" src="${ctx }/layouts/images/main_title_left.gif" />
	    <img class="imgFright" src="${ctx }/layouts/images/main_title_right.gif" />

	    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">校园库管理</p>
	  </div>
-->
	<!-- 标题 end-->
	<div class="content"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr class="tr_inquires">
		<td width="40px">
				地区：
		</td>
		<td width="150px">
		<input type="text" id="cityId" /> 
		<input type="hidden" id="cityIdVal" />
		</td>
		<td align="left">
			 <input class="btn btn-info btn-small" type="button" onclick="toSearch()" value="查询"/>
		</td>
			<td>
				&nbsp;
			</td>

	</tr>
</table>
<div id="toptoolbar"></div>
<div id="maingrid"></div>
</div>
</div>
</body>
</html>