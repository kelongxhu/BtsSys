<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>校园库管理</title>
<%@ include file="/appviews/common/tag.jsp"%>
<script type="text/javascript">
var gridObj = null;
$(function(){

    //toptoolbar
    $("#toptoolbar").ligerToolBar({ 
		items: [
	             { text: '增加', click: add , icon:'add'},
	             { text: '编辑', click: edit , icon:'modify'}
	            // { text: '删除', click: del , icon:'delete'}
	           ]
	});
	
    gridObj=$("#maingrid").ligerGrid({
			columns: [{display:'天线型号',name:'airModel',width : 100,align:'center'},
			          {display:'天线厂家',name:'airFacName',width : 100,align:'center',frozen: true },
			          {display:'是否美化天线',name:'prettifyFlag',width : 100,align:'center',
			        	  render : function(row) {
								if (row.prettifyFlag == 1) {
									return "是";
								} else {
									return "否";
								}
							}  
			          },
			          {display:'是否可变电调',name:'ivaryFlag',width : 100,align:'center',
			        	  render : function(row) {
								if (row.ivaryFlag == 1) {
									return "是";
								} else {
									return "否";
								}
							}    
			          },
			          {display:'内置电下倾度数(度)',name:'degree',width : 140,align:'center'},
			          {display:'频率范围(MHz)',name:'freq',width : 120,align:'center'},
			          {display:'频带宽度(MHz)',name:'trapWidth',width : 120,align:'center'},              		          
			          {display:'天线增益(dB)',name:'airPlus',width :120,align:'center'} ,         		          
			          {display:'天线类型',name:'airTypeName',width : 120,align:'center'},
			          {display:'极化方式',name:'jhTypeName',width : 120,align:'center'},              		          
			          {display:'水平波瓣(度)',name:'flatLobe',width : 120,align:'center'},              		          
			          {display:'垂直波瓣(度)',name:'uplobe',width : 120,align:'center'},              		          
			          {display:'输入阻抗(欧姆)',name:'inputDrag',width : 120,align:'center'}, 
			          {display:'天线前后比（dB）',name:'wireScale',width : 120,align:'center'}, 
			          {display:'驻波系数',name:'standingWave',width : 120,align:'center'},
			          {display:'俯仰上旁瓣抑制（dB）',name:'pitchUp',width : 200,align:'center'},
			          {display:'俯仰下旁瓣抑制（dB）',name:'pitchDown',width : 200,align:'center'}
			],
			rownumbers:true,
			showTitle : false,
			pageSize : 50,
			pageSizeOptions:[50,100],
			url:'${ctx}/schooljson/airLibData.action',
			checkbox : true,
			width: '99%',
			height:'99%',
            onDblClickRow :airInfo
		}); 
 	$("#pageloading").hide();
 });


function airInfo(data){
      window.location.href="${ctx}/school/airInfo.action?id="+data.id;
}
 
 
//初始化天线厂家
var typeURL1="${ctx}/schooljson/cons.action?groupCode=AIRFAC";
var sl1;
$.getJSON(typeURL1, 
	function(data){
		sl1 = $("#airFac").ligerComboBox({
			isShowCheckBox: true, 
			isMultiSelect: true,
			data : data.Rows,
			width : 200,
			selectBoxWidth: 200,
			textField : 'name',
			valueField : 'code',
			valueFieldID:'airFacVal'
		});
	}
);
 
 	
 	//添加页面
 	function add(){
 	     window.location.href="${ctx}/appviews/baselibs/airlib/airAdd.jsp";
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
		window.location.href="${ctx}/school/airEditPage.action?id="+id;
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
		$.getJSON('${ctx}/schooljson/delAirLib.action', params, function(json) {
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
		var airFacs = $("#airFacVal").val().replace(/;/g, ',');
		
		gridObj.setOptions({
			newPage : 1
		});
		gridObj.setOptions({
			url : encodeURI("${ctx}/schooljson/airLibData.action?airFacs="
					+ airFacs)
		});
		gridObj.loadData(); //加载数据
	}
</script>
</head>
<body>
<div id="main">
<!-- 标题
	  <div class="main_title_2">
	    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">天线库管理</p>
	  </div>   -->
	<!-- 标题 end-->
	<div class="content"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr class="tr_inquires">
		<td width="60px">
				天线厂家：
		</td>
		<td width="150px">
		<input type="text" id="airFac" /> 
		<input type="hidden" id="airFacVal" />
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