<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>盲点库管理</title>
<%@ include file="/appviews/common/tag.jsp"%>
<script type="text/javascript">
var gridObj = null;
$(function(){
	var treeCombox;
	//初始化地市
	//初始化树控件
	$.post("${ctx}/schooljson/initCountryTree.action", function(
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
	});
    //toptoolbar
    $("#toptoolbar").ligerToolBar({ 
		items: [
	             { text: '增加', click: add , icon:'add'},
	             { text: '编辑', click: edit, icon:'modify'},
	             { text: '删除', click: del , icon:'delete'},
	             { text: '导出盲点模板', click: exportBlindTemplate , icon:'logout'} ,
	             { text: '导入', click: importInput , icon:'save'},
                 { text: '导出', click: importData , icon:'save'}
	           ]
	});
	
    gridObj=$("#maingrid").ligerGrid({
			columns: [{display:'盲点名称',name:'name',width : 100,align:'center'},
			          {display:'本地网',name:'cityName',width : 60,align:'center'},
			          {display:'区县',name:'countryName',width : 60,align:'center'},
			          {display:'农村和乡镇',name:'vitoName',width : 120,align:'center'},              		          
			          {display:'地址',name:'address',width :120,align:'center'} ,        
					  {display:'经度',name:'longitude',width : 60,align:'center'},
			          {display:'纬度',name:'latitude',width : 60,align:'center'},
			          {display:'网格类型',name:'gridType',width : 120,align:'center',render:gridType},
			          {display:'区域类型1',name:'blindType1',width : 120,align:'center',render:blindType1},
			          {display:'区域类型2',name:'blindType2',width : 120,align:'center',render:blindType2},
			          {display:'是否1X盲点',name:'is1x',width : 120,align:'center',render:is1x},
			          {display:'是否DO盲点',name:'isDo',width : 120,align:'center',render:isDo},
			          {display:'是否LTE盲点',name:'isLte',width : 120,align:'center',render:isLte},
			          {display:'解决方式',name:'solType',width : 120,align:'center',render:solType},
			          {display:'辅助手段',name:'assistType',width : 100,align:'center',render:assistType},
			          {display:'填报日期',name:'inTimeString',width : 100,align:'center'},
			          {display:'填报人',name:'inUserName',width : 100,align:'center'}
			],
			rownumbers:true,
			showTitle : false,
			pageSize : 50,
			pageSizeOptions:[50,100],
			url:'${ctx}/schooljson/blindData.action',
			checkbox : true,
			width: '100%',
			height:'100%',
            onDblClickRow :blindInfo
		}); 
 	$("#pageloading").hide();
 });
	
	var gridType = function(row){
		var res;
		if(row.gridType == '0'){
			res = '市区';
		}else if(row.gridType == '1'){
			res = '城郊';
		}else if(row.gridType == '2'){
			res = '县城';
		}else if(row.gridType == '3'){
			res = '乡镇';
		}else if(row.gridType == '4'){
			res = '农村';
		}
		
		return res;
	}
	
	var blindType1 = function(row){
		var res;
		if(row.blindType1 == '0'){
			res = '室外';
		}else if(row.blindType1 == '1'){
			res = '室内';
		}
		
		return res;
	}
	
	var blindType2 = function(row){
		var res;
		if(row.blindType2 == '0'){
			res = '高铁';
		}else if(row.blindType2 == '1'){
			res = '高速公路';
		}else if(row.blindType2 == '2'){
			res = '机场';
		}else if(row.blindType2 == '3'){
			res = '车站港口码头';
		}else if(row.blindType2 == '4'){
			res = '住宅小区';
		}else if(row.blindType2 == '5'){
			res = '风景区';
		}else if(row.blindType2 == '6'){
			res = '宾馆';
		}else if(row.blindType2 == '7'){
			res = '写字楼';
		}else if(row.blindType2 == '8'){
			res = '商场';
		}else if(row.blindType2 == '9'){
			res = '体育场';
		}else if(row.blindType2 == '10'){
			res = '地铁';
		}else if(row.blindType2 == '11'){
			res = '海域';
		}else if(row.blindType2 == '12'){
			res = '校园';
		}else if(row.blindType2 == '13'){
			res = '隧道';
		}else if(row.blindType2 == '14'){
			res = '医院';
		}else if(row.blindType2 == '15'){
			res = '政府机关';
		}else if(row.blindType2 == '16'){
			res = '工业园区';
		}else if(row.blindType2 == '17'){
			res = '农村';
		}else if(row.blindType2 == '18'){
			res = '水运';
		}else if(row.blindType2 == '19'){
			res = '聚类市场';
		}else if(row.blindType2 == '20'){
			res = '休闲娱乐场所';
		}else if(row.blindType2 == '21'){
            res = '其它';
        }
		
		return res;
	}
	
	var is1x = function(row){
		var res;
		if(row.is1x == '1'){
			res = '是';
		}else if(row.is1x == '0'){
			res = '否';
		}
		
		return res;
	}
	
	var isDo = function(row){
		var res;
		if(row.isDo == '1'){
			res = '是';
		}else if(row.isDo == '0'){
			res = '否';
		}
		
		return res;	
	}

	var isLte = function(row){
		var res;
		if(row.isLte == '1'){
			res = '是';
		}else if(row.isLte == '0'){
			res = '否';
		}
		
		return res;	
	}
	
	var solType = function(row){
		var res;
		if(row.solType == '0'){
			res = '网络优化';
		}else if(row.solType == '1'){
			res = '规划建站';
		}else if(row.solType == '2'){
			res = '辅助手段';
		}else if(row.solType == '3'){
			res = 'WiFi解决';
		}
		
		return res;	
	}
	
	var assistType = function(row){
		var res;
		if(row.assistType == '0'){
			res = 'BPA';
		}else if(row.assistType == '1'){
			res = '塔放';
		}else if(row.assistType == '2'){
			res = '壁虎';
		}else if(row.assistType == '3'){
			res = '手机伴侣';
		}else if(row.assistType == '4'){
			res = '五类线';
		}else if(row.assistType == '5'){
			res = '其它';
		}else if(row.assistType == '6'){
			res = '无';
		}
		
		return res;			
	}

   function blindInfo(data){
        window.location.href="${ctx}/school/blindInfo.action?id="+data.id;
   }
 
 	
 	//添加页面
 	function add(){
 	     window.location.href="${ctx}/appviews/baselibs/blind/blindAdd.jsp";
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
		window.location.href="${ctx}/school/blindEdit.action?id="+id;
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
		$.getJSON('${ctx}/schooljson/deleteBlind.action', params, function(json) {
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
		var countryIds = $("#cityIdVal").val().replace(/;/g, ',');
		
		gridObj.setOptions({
			newPage : 1
		});
		gridObj.setOptions({
			url : encodeURI("${ctx}/schooljson/blindData.action?countryIds="
					+ countryIds)
		});
		gridObj.loadData(); //加载数据
	}
	
    function exportBlindTemplate(){
	    window.location.href = "${ctx}/school/exportBlindTemplate.action";
    }
    function importData(){
    window.location.href = "${ctx}/school/exportBlindData.action";
   }



    
      //导入数据
    function importInput() {
        window.location.href = "${ctx}/school/blindImport.action";
    }
</script>
</head>
<body>
	<div id="main">
		<!-- 标题 end-->
		<div class="content"> 
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr class="tr_inquires">
					<td width="60px">
							地区：
					</td>
					<td width="150px">
						<input type="text" id="cityId" /> 
						<input type="hidden" id="cityIdVal" />
					</td>
					<td align="left">
						 <input class="btn btn-info btn-small" type="button" onclick="toSearch()" value="查询"/>
					</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<div id="toptoolbar"></div>
			<div id="maingrid"></div>
		</div>
	</div>
</body>
</html>