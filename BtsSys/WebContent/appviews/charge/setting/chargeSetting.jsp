<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/appviews/common/tag.jsp"%>
<html>
	<head>
		<title>费用设置</title>
		<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			var gridObj = null;
			$(function() {
			
			    var treeCombox;
			
			    //初始化地市
			    //初始化树控件
			    $.post("${ctx}/schooljson/initCountryTree.action", function(ajaxData, status) {
			        var treeData = ajaxData.cityJson;
			        treeData = treeData.replace(/"children":\[\],/g, '');
			        treeData = eval('(' + treeData + ')');
			        treeCombox = $("#cityId").ligerComboBox({
			            width : 150,
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
			
			    //数据类型
			    var comBox1 = $("#typeIdVal").ligerComboBox({
			        data: [
			            { text: '室内覆盖站点', id: '1' },
			            { text: '纯BBU站点', id: '2' },
			            { text: '室外分布站点', id: '3' },
			            { text: '隧道覆盖站点', id: '6' }
			        ],
			        width : 120,
			        selectBoxWidth: 150,
			        valueFieldID: 'typeId'
			    });
			    comBox1.selectValue('1');
			    
			    //缴费类型
			    var comBox2 = $("#costTypeVal").ligerComboBox({
			        data: [
			            { text: '房租', id: '1' },
			            { text: '物业', id: '2' },
			            { text: '电费', id: '3' }
			        ],
			        width : 120,
			        selectBoxWidth: 150,
			        valueFieldID: 'costType'
			    });
			    comBox2.selectValue('1');
			
	            $("#toptoolbar").ligerToolBar({
	                items: [
	                    { text: '设置/编辑', click: settingOrEdit , icon:'add'},
	                    { text: '导出模板', click: exportInputData , icon:'logout'},
		                { text: '导入数据', click: importInput , icon:'save'}
	                ]
	            });
			
			    //控件
			    $("#btsName").ligerTextBox({width : 150 });
			    $("#bscName").ligerTextBox({width : 150 });
			    $("#btsId").ligerTextBox({width : 150 });
			
			    toSearch();
			});
			
			function settingOrEdit(){
			    var rows = gridObj.getCheckedRows();
	            var count = rows.length;
	            if (count == 0) {
	                $.ligerDialog.alert('请选择要录入的数据！');
	                return;
	            } else if (count > 1) {
	                $.ligerDialog.alert('请选择一条录入！');
	                return;
	            }
	            var id, btsType;
	            $(rows).each(function() {
	                id = this.intId;
	                btsType = this.btsType;
	            });
	            window.location.href = "${ctx}/charge/setting.action?intId="+id+"&btsType="+btsType;
			}
			
			function exportInputData(){}
			function importInput(){}

			//查询
			function toSearch() {
			    var typeId = $("#typeId").val();
			    var costType = $("#costType").val();
			    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
			    var btsName = $("#btsName").val();
			    var bscName = $("#bscName").val();
			    var btsId = $("#btsId").val();
			    var url = encodeURI("${ctx}/chargejson/queryBtsData.action?countryIds=" 
			    		+ cityIds + "&btsType=" + typeId + "&costType=" + costType
			    		+ "&btsName=" + btsName + "&bscName=" + bscName + "&btsId=" + btsId);
				initGrid(url);
			}
			
			function initGrid(url) {
			    gridObj = $("#maingrid").ligerGrid({
			        columns: [
			            {display:'名称',name:'btsName',width : 140,align:'center'},
			            {display:'本地网',name:'cityName',width : 80,align:'center'},
			            {display:'区县',name:'countryName',width : 80,align:'center'},
			            {display:'所属BSC',name:'bscName',width :120,align:'center'} ,
			            {display:'BTSID',name:'btsId',width :60,align:'center',isSort:true},
			            {display:'缴费周期',name:'payCycle',width : 60,align:'center'},
			            {display:'缴费日期',name:'payDay',width : 60,align:'center'},
			            {display:'提醒人员',name:'remindUser',width : 80,align:'center'},
			            {display:'提醒号码',name:'remindTel',width : 80,align:'center'}
			        ],
			        rownumbers:true,
			        showTitle : false,
			        pageSize : 50,
			        pageSizeOptions:[50,100],
			        url:url,
			        checkbox : true,
			        width: '100%',
			        height:'99%',
			        onDblClickRow :btsInfo
			    });
			    $("#pageloading").hide();
			}
			
			//基站显示信息
			function btsInfo(data) {
			    window.location.href = "${ctx}/business/btsInfo.action?intId=" + data.intId;
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
							数据类型：
						</td>
						<td width="120px">
							<input type="text" id="typeIdVal" />
							<input type="hidden" id="typeId" />
						</td>
                		<td width="60px">
                   			缴费类型：
		                </td>
		                <td width="120px">
		                    <input type="text" id="costTypeVal"/>
		                    <input type="hidden" id="costType"/>
		                </td>						
						<td align="left">
							<input class="btn btn-info btn-small" type="button"
								onclick="toSearch()" value="查询" />
							<a onclick="toggle('ydzdgcs');">
								<img id="arrow_icon_ydzdgcs" src="${ctx}/layouts/images/btn_searchest.jpg" />
							</a>
						</td>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table id="ydzdgcs" class="tab_send" cellpadding="0" cellspacing="0"
					style="display: none;" border="0">
					<tr>
						<td width="60px" align="left">
							基站名称:
						</td>
						<td>
							<input type="text" id="btsName" />
						</td>
						<td width="60px" align="left">
							BSC名称:
						</td>
						<td>
							<input type="text" id="bscName" />
						</td>
						<td width="60px" align="left">
							BTSID:
						</td>
						<td>
							<input type="text" id="btsId" />
						</td>
					</tr>
				</table>
				<div id="toptoolbar"></div>
				<div id="maingrid"></div>
			</div>
		</div>
	</body>
</html>