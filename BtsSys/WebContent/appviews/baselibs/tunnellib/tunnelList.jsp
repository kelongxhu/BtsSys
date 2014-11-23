<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>隧道库管理</title>
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
			//treeCombox.initComboBox(treeData,'localNetsVal');
	});


    //是否录入
    var manaFlagData = [{text: '手动', id: '0'}, {text: '自动', id: '1'}];
    $("#manualFlag").ligerComboBox({
        width: 100,
        selectBoxWidth: 120,
//                isShowCheckBox: true,
//                isMultiSelect: true,
        data: manaFlagData,
        valueFieldID: 'manualFlagVal'
    });
    //toptoolbar
    $("#toptoolbar").ligerToolBar({ 
		items: [
	             { text: '增加', click: add , icon:'add'},
	             { text: '编辑', click: edit , icon:'modify'},
	             { text: '删除', click: del , icon:'delete'}
	           ]
	});
	
    gridObj=$("#maingrid").ligerGrid({
			columns: [{display:'地市',name:'cityName',width : 80,align:'center'},
			          {display:'区县',name:'countryName',width : 80,align:'center'},
			          {display:'隧道名称',name:'name',width : 120,align:'center'},
			          {display:'线路属性',name:'roadPropName',width : 80,align:'center'},
			          {display:'道路名称',name:'roadName',width :120,align:'center'} ,         		          
			          {display:'长度',name:'tunnellength',width : 80,align:'center'},
			          {display:'方向',name:'direction',width : 120,align:'center'},
			          {display:'经度',name:'longitude',width : 80,align:'center'},
			          {display:'纬度',name:'latitude',width : 80,align:'center'},
			          {display:'海拔高度',name:'height',width : 80,align:'center'},
			          {display:'覆盖设备类型',name:'covgtype',width : 120,align:'center'},
			          {display:'设备安装位置',name:'installadress',width : 120,align:'center'},
			          {display:'产权',name:'rights',width : 80,align:'center'},
			          {display:'是否共建共享',name:'togbs',width : 120,align:'center'},
			          {display:'共享方名称',name:'togname',width : 80,align:'center'},
			          {display:'开通年月',name:'opentime',width : 80,align:'center'},
			          {display:'详细地址',name:'address',width : 120,align:'center'},
                      {display:'解析标识',name:'autoFlagStr',width : 60,align:'center'}


			],
			rownumbers:true,
			showTitle : false,
			pageSize : 50,
			pageSizeOptions:[50,100],
			url:'${ctx}/schooljson/tunnelLibData.action',
			checkbox : true,
			width: '100%',
			height:'100%',
            onDblClickRow :tunnelInfo
		}); 
 	$("#pageloading").hide();
 });


   function tunnelInfo(data){
        window.location.href="${ctx}/school/tunnelInfo.action?id="+data.id;
   }
 
 	
 	//添加页面
 	function add(){
 	     window.location.href="${ctx}/appviews/baselibs/tunnellib/tunnelAdd.jsp";
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
		window.location.href="${ctx}/school/tunnelLibEditPage.action?id="+id;
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
		$.getJSON('${ctx}/schooljson/delTunnelLib.action', params, function(json) {
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
        var autoFlag=$("#manualFlagVal").val();

		gridObj.setOptions({
			newPage : 1
		});
		gridObj.setOptions({
			url : encodeURI("${ctx}/schooljson/tunnelLibData.action?countryIds="
					+ countryIds+"&autoFlag="+autoFlag)
		});
		gridObj.loadData(); //加载数据
	}
</script>
</head>
<body>
<div id="main">
<!-- 标题
	  <div class="main_title_2">
	    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">隧道库管理</p>
	  </div>  -->
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
        <td width="60px">
             是否自动：
        </td>
        <td width="120px">
            <input type="text" id="manualFlag"/>
            <input type="hidden" id="manualFlagVal"/>
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