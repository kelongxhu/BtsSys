<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>道路库管理</title>
<%@ include file="/appviews/common/tag.jsp"%>
<script type="text/javascript">
var gridObj = null;
$(function(){

    //toptoolbar
    $("#toptoolbar").ligerToolBar({ 
		items: [
	             { text: '增加', click: add , icon:'add'},
	             { text: '编辑', click: edit , icon:'modify'},
	             { text: '删除', click: del , icon:'delete'}
	           ]
	});
	
    gridObj=$("#maingrid").ligerGrid({
			columns: [{display:'本地网',name:'cityName',width : 100,align:'center'},
			          {display:'线路属性',name:'roadPropName',width : 100,align:'center',frozen: true },
			          {display:'线路名称',name:'name',width : 200,align:'center'},
			          {display:'设计时速',name:'speed',width : 100,align:'center'},
			          {display:'辖区内线路里程(km)',name:'lineMile',width : 120,align:'center'},
			          {display:'辖区内隧道里程(km)',name:'tunnelMile',width : 120,align:'center'},              		          
			          {display:'辖区内平原段里程(km)',name:'plainMile',width :140,align:'center'} ,         		          
			          {display:'辖区内山区段里程(km)',name:'mountainMile',width : 140,align:'center'},
			          {display:'辖区内车站数量',name:'stationNum',width : 140,align:'center'},              		          
			          {display:'沿线CDMA网络覆盖方式',name:'coverTypeName',width : 140,align:'center'}
			],
			rownumbers:true,
			showTitle : false,
			pageSize : 50,
			pageSizeOptions:[50,100],
			url:'${ctx}/schooljson/roadLibData.action',
			checkbox : true,
			width: '100%',
			height:'100%',
            onDblClickRow :roadInfo
		}); 
 	$("#pageloading").hide();
 });


function roadInfo(data){
     window.location.href="${ctx}/school/roadInfo.action?id="+data.id;
}
 
 
//初始化天线厂家
var typeURL1="${ctx}/schooljson/cons.action?groupCode=ROADPROP";
var sl1;
$.getJSON(typeURL1, 
	function(data){
		sl1 = $("#roadProp").ligerComboBox({
			isShowCheckBox: true, 
			isMultiSelect: true,
			data : data.Rows,
			width : 200,
			selectBoxWidth: 200,
			textField : 'name',
			valueField : 'code',
			valueFieldID:'roadPropVal'
		});
	}
);
 
 	
 	//添加页面
 	function add(){
 	     window.location.href="${ctx}/appviews/baselibs/roadlib/roadAdd.jsp";
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
		
		window.location.href="${ctx}/school/editRoadLibPage.action?id="+id;
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
		$.getJSON('${ctx}/schooljson/delRoadLib.action', params, function(json) {
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
		var roadProps = $("#roadPropVal").val().replace(/;/g, ',');
		
		gridObj.setOptions({
			newPage : 1
		});
		gridObj.setOptions({
			url : encodeURI("${ctx}/schooljson/roadLibData.action?roadProps="
					+ roadProps)
		});
		gridObj.loadData(); //加载数据
	}
</script>
</head>
<body>
<div id="main">
<!-- 标题
	  <div class="main_title_2">
	    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">道路库管理</p>
	  </div>   -->
	<!-- 标题 end-->
	<div class="content"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr class="tr_inquires">
		<td width="60px">
				线路属性：
		</td>
		<td width="150px">
		<input type="text" id="roadProp" /> 
		<input type="hidden" id="roadPropVal" />
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