<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>乡镇库管理</title>
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
			
			//$("#tree1").ligerTree({ data:treeData,checkbox:false });
			
	});
		
	//乡镇农村
	
	//是否美化天线
	 $("#vitoType").ligerComboBox({  
         data: [
             { text: '乡镇', id: '0' },
             { text: '农村', id: '1' }
         ], 
         width : 200,
		 selectBoxWidth: 200,
         valueFieldID: 'vitoTypeVal'
     });
		
		
		
    //toptoolbar
    $("#toptoolbar").ligerToolBar({ 
		items: [
	             { text: '增加', click: add , icon:'add'},
	             { text: '编辑', click: edit , icon:'modify'},
	             { text: '删除', click: del , icon:'delete'},
                 { text: '导出乡镇模板', click:exporttownTemplate , icon:'logout'} ,
                 { text: '导出农村模板', click:exportCountryTemplate , icon:'logout'} ,
                 { text: '导入', click: importInput , icon:'save'}
	           ]
	});
    

	
    gridObj=$("#maingrid").ligerGrid({
			columns: [{display:'地市',name:'cityName',width : 60,align:'center'},
			          {display:'区县',name:'countryName',width : 60,align:'center'},
			          {display:'名称',name:'name',width : 150,align:'center'},
			          {display:'所属乡镇',name:'parentName',width : 150,align:'center',
			        	  render: function (row)
			      	        {
		      				if(row.parentName==null){
		      					return "<span style='color: red;'><乡镇库></span>";
		      				}else{
		      					return "<span style='color: red;'><农村库></span>"+row.parentName;
				      		}
			      	    }},
			          {display:'中心GPS经度',name:'gpsLong',width : 120,align:'center'},              		          
			          {display:'中心GPS纬度',name:'gpsLat',width :120,align:'center'}, 
			          {display:'网络覆盖率',columns:
				      		[
				                   {display:'电信CDMA1X 2000',name:'cmda1xCovgrate',width : 120,align:'center'},
			                       {display:'移动GSM',name:'mobgsmCovgrate',width : 120,align:'center'},
			                       {display:'电信与移动差值',name:'covgDifCovgrate',width : 120,align:'center'},
			                       {display:'联通GSM',name:'unicgsmCovgrate',width : 120,align:'center'},
			                       {display:'电信EVDO',name:'evdoCovgrate',width : 120,align:'center'}
					    	 ]
				      },
				      {display:'语音MOS值',columns:
				      		[
				                   {display:'电信CDMA1X 2000',name:'cdma1xMos',width : 120,align:'center'},
			                       {display:'移动GSM',name:'mobgsmMos',width : 120,align:'center'},
			                       {display:'联通GSM',name:'unicgsmMos',width : 120,align:'center'}
					        ]
				      }
			],
			rownumbers:true,
			showTitle : false,
			pageSize : 50,
			pageSizeOptions:[50,100],
			url:'${ctx}/schooljson/vitoLibData.action',
			checkbox : true,
			width: '100%',
			height:'100%',
            onDblClickRow :vitoInfo
		}); 
 	$("#pageloading").hide();
 });

    function vitoInfo(data){
        window.location.href="${ctx}/school/vitoInfo.action?id="+data.id;
    }
 
 	
 	//添加页面
 	function add(){
 	     window.location.href="${ctx}/appviews/baselibs/vitolib/vitoAdd.jsp";
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
		window.location.href="${ctx}/school/vitoLibEditPage.action?id="+id;
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
		$.getJSON('${ctx}/schooljson/delVitoLib.action', params, function(json) {
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
		
		var vitoType=$("#vitoTypeVal").val();
		
		gridObj.setOptions({
			newPage : 1
		});
		gridObj.setOptions({
			url : encodeURI("${ctx}/schooljson/vitoLibData.action?countryIds="
					+ countryIds+"&vitoType="+vitoType)
		});
		gridObj.loadData(); //加载数据
	}

     //导入数据
        function importInput() {
            window.location.href = "${ctx}/appviews/baselibs/vitolib/vitoImport.jsp";
        }

        //导出模板
        function exporttownTemplate(){
             window.location.href = "${ctx}/school/exportTownTemplate.action";
        }

        function exportCountryTemplate(){
             window.location.href = "${ctx}/school/exportCountryTemplate.action";
        }
</script>
</head>
<body>
<div id="main">
<!-- 标题
	  <div class="main_title_2">
	    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">乡镇库管理</p>
	  </div>
	  -->
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
				类型：
		</td>
		<td width="150px">
		<input type="text" id="vitoType" /> 
		<input type="hidden" id="vitoTypeVal" />
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