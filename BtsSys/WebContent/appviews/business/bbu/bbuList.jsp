<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>BBU站点</title>
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
				width : 200,
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
	             { text: '录入/编辑', click: add , icon:'add'},
	             { text: '导出模板', click: exportInputData , icon:'logout'},
	             { text: '导入数据', click: importInput , icon:'save'}
	           ]
	});


    //是否录入
    var manaFlagData = [{text: '未录', id: '0'}, {text: '已录', id: '1'}];
    $("#manualFlag").ligerComboBox({
        width: 100,
        selectBoxWidth: 120,
        data: manaFlagData,
        valueFieldID: 'manualFlagVal'
    });
	
    gridObj=$("#maingrid").ligerGrid({
			columns: [{display:'纯BBU站点名称',name:'name',width : 150,align:'center'},
			          {display:'本地网',name:'cityName',width : 55,align:'center'},
			          {display:'区县',name:'country.cityName',width : 55,align:'center',
			        	  render: function (row)
			      	        {
                                if(row.country){
                                    return row.country.cityName;
                                }else{
                                    return "";
                                }
			      	    	}},
			      	  {display:'编号',name:'bbuNo',width : 30,align:'center'},
                      {display:'施主基站名称',name:'btsName',width : 120,align:'center'},
			       	  {display:'所属BSC名称',name:'bscName',width : 120,align:'center'},
			       	  {display:'网管编号',name:'btsId',width : 60,align:'center'},
			          {display:'机房产权',name:'circuitroomOwnership',width : 60,align:'center'},
			          {display:'传输产权',name:'transOwnership',width : 60,align:'center'},
			          {display:'是否共享',name:'isShare',width : 60,align:'center',
			        	  render: function (row){
			        		if(row.isShare==1){
			        			return "是";
			        		}else{
			        			return "否";
			        		}	  
			           }},
			          {display:'设备类型',name:'vendorBtstype',width : 60,align:'center'},
                      {display:'更新时间',name:'updatetimeStr',width : 80,align:'center'},
			          {display:'手工标识',name:'manualFlag',width : 60,align:'center',
			        	  render: function (row)
			      	        {
		      				if(row.manualFlag==0){
		      					return "<span class='label label-important'>未录入</span>";
		      				}else{
		      					return "<span class='label'>已录入</span>";
				      		}
			      	    }}
			],
			rownumbers:true,
			showTitle : false,
			pageSize : 50,
			pageSizeOptions:[50,100],
			url:'${ctx}/businessjson/bbuData.action',
			checkbox : true,
			width: '99.8%',
			height:'99%',
			onDblClickRow :bbuInfo,
            onCheckAllRow: f_onCheckAllRow
		}); 
 	$("#pageloading").hide();

    //控件
              $("#name").ligerTextBox({width : 200 });
              $("#bscName").ligerTextBox({width : 200 });
              $("#btsId").ligerTextBox({width : 200 });
 });
 
 	
 	//BBU信息显示界面
 	function bbuInfo(data){
 		window.location.href="${ctx}/business/bbuInfo.action?intId="+data.intId;
 	}
 
 
 	
 	//添加页面
 	function add(){
 		var rows = gridObj.getCheckedRows();
		var j=rows.length;
		if(j==0){
			$.ligerDialog.alert('请选择要录入的数据！');
			return;
		}else if (j > 1) {
			 $.ligerDialog.alert('请选择一条录入！');
			return;
		}
		var id;
		$(rows).each(function() {
			intId=this.intId;
		});
 	    window.location.href="${ctx}/business/bbuInput.action?intId="+intId;
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
		window.location.href="${ctx}/school/secneryEditPage.action?id="+id;
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
		$.getJSON('${ctx}/schooljson/delSecneryLib.action', params, function(json) {
			 if (json.result == 1) {
				 alert('删除成功!');
			}else{
				 alert('删除失败！');
			} 
			 gridObj.loadData();
		 });
			
		});
 	}

var checkAllFlag = 0;
function f_onCheckAllRow(checked) {
    if (checked) {
        checkAllFlag = 1;
    } else {
        checkAllFlag = 0;
    }
}
 	
 	
 	
 	//导出录入模板
 	
 	function exportInputData(){
        //勾选导出
        var rows = gridObj.getCheckedRows();
        var str = "";
        $(rows).each(function () {
            str += this.intId + ",";
        });
        if (str.length>= 0) {
            str = str.substring(0, str.length - 1);
        }
 		var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
        var name = $("#name").val();
        var bscName = $("#bscName").val();
        var btsId = $("#btsId").val();
 		var url=encodeURI("${ctx}/business/exportBbuInputData.action?countryIds="+cityIds+"&name="+name+"&bscName="+bscName+"&btsId="+btsId+"&ids="+str+"&checkAllFlag="+checkAllFlag);
		window.location.href=url;
 	}
 	
 	//导入数据
 	function importInput(){
 		window.location.href="${ctx}/appviews/business/bbu/bbuImport.jsp";
 	}


	//查询
	function toSearch() {
		//处理地区
		var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
        var name = $("#name").val();
        var bscName = $("#bscName").val();
        var btsId = $("#btsId").val();
        var manualFlag=$("#manualFlagVal").val();
		
		gridObj.setOptions({
			newPage : 1
		});
		gridObj.setOptions({
			url : encodeURI("${ctx}/businessjson/bbuData.action?countryIds="
					+ cityIds+"&name="+name+"&bscName="+bscName+"&btsId="+btsId+"&manualFlag="+manualFlag)
		});
		gridObj.loadData(); //加载数据
	}

//高级检索
function toggle(targetid) {
    if (document.getElementById) {
        target = document.getElementById(targetid);
        var image = document.getElementById("arrow_icon_" + targetid);
        if (target.style.display == "block") {
            target.style.display = "none";
            image.src = "${ctx}/layouts/images/btn_searchest.jpg" + "?timestamp=" + Date();
        }
        else {
            target.style.display = "block";
            image.src = "${ctx}/layouts/images/btn_searchest_on.jpg" + "?timestamp=" + Date();
        }
    }
}

</script>
</head>
<body>
<div id="main">
<!-- 标题
	  <div class="main_title_2">
	    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">纯BBU站点录入</p>
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
            是否录入：
        </td>
        <td width="120px">
            <input type="text" id="manualFlag"/>
            <input type="hidden" id="manualFlagVal"/>
        </td>
		<td align="left">
			 <input class="btn btn-info btn-small" type="button" onclick="toSearch()" value="查询"/>
               <a onclick="toggle('ydzdgcs')"><img id="arrow_icon_ydzdgcs"
                                                        src="${ctx}/layouts/images/btn_searchest.jpg"/></a>
		</td>
			<td>
				&nbsp;
			</td>

	</tr>
</table>
    <table id="ydzdgcs" class="tab_send" cellpadding="0" cellspacing="0" style="display:none;" border="0">
            <tr>
                <td width="50px" align="left">名称:</td>
                <td>
                    <input type="text" id="name"/>
                </td>
                  <td width="50px" align="left">BSC名称:</td>
                <td>
                    <input type="text" id="bscName"/>
                </td>
                <td width="50px" align="left">btsid:</td>
                <td>
                    <input type="text" id="btsId"/>
                </td>
            </tr>
        </table>
<div id="toptoolbar"></div>
<div id="maingrid"></div>
</div>
</div>
</body>
</html>