<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>小区录入</title>
<%@ include file="/appviews/common/tag.jsp"%>
    
    <style type="text/css">
	 #msg {
		padding-left: 16px;
		padding-bottom: 2px;
		font-weight: bold;
		display:inline;
		color: #EA5200;
	 }
	</style>

<script type="text/javascript">
var treeCombox4;
//注册表单验证
$(function(){

	 var treeCombox;

		//初始化地市
	 	//初始化树控件
    $.post("${ctx}/schooljson/airTree.action", function(ajaxData, status) {
        var treeData = ajaxData.airTreeJsonString;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData = eval('(' + treeData + ')');
        for (var i = 0; i <='${isGf-1}'; i++) {
            treeCombox = $("#airModelVal" + i).ligerComboBox({
                width : 240,
                selectBoxWidth : 240,
                textField : 'text',
                valueField : 'id',
                valueFieldID : 'airModel' + i,
                treeLeafOnly : true,
                tree : {
                    data : treeData,
                    checkbox:false,
                    nodeWidth:240
                }
            });

            var airId;

            if (i == 0) {
                airId = '${cellManual.airLibs[0].airId}';
            } else if (i == 1) {
                airId = '${cellManual.airLibs[1].airId}';
            } else if (i == 2) {
                airId = '${cellManual.airLibs[2].airId}';
            }
            if (airId != '') {
                treeCombox.selectValue(airId);
            }
        }
      //over
	});
		
	//覆盖区域
	var comBox1=$("#coverareaVal").ligerComboBox({  
        data: [
            { text: '市区', id: '市区' },
            { text: '城郊', id: '城郊' },
            { text: '县城', id: '县城' },
            { text: '乡镇', id: '乡镇' },
            { text: '农村', id: '农村' }
        ], 
        width : 200,
		selectBoxWidth: 200,
        valueFieldID: 'coverarea'
    });
	 comBox1.selectValue('${cellManual.coverarea}');
	//扇区道路覆盖类型
	
	var comBox2=$("#coverroadVal").ligerComboBox({  
        data: [
            { text: '市区', id: '市区' },
            { text: '县城', id: '县城' },
            { text: '高速', id: '高速' },
            { text: '高铁', id: '高铁' },
            { text: '铁路', id: '铁路' },
            { text: '国道', id: '国道' },
            { text: '省道', id: '省道' },
            { text: '县道', id: '县道' },
            { text: '乡道', id: '乡道' },
            { text: '隧道', id: '隧道' },
            { text: '无', id: '无' }
        ], 
        isMultiSelect :true,
        isShowCheckBox:true,
        width : 200,
		selectBoxWidth: 200,
        valueFieldID: 'coverroad'
    });  

	comBox2.selectValue('${cellManual.coverroad}');

	//初始化扇区热点覆盖类型
	var typeURL1="${ctx}/schooljson/cons.action?groupCode=SCENE_TYPE";
	var comBox3;
	$.getJSON(typeURL1, 
		function(data){
		comBox3 = $("#coverhotVal").ligerComboBox({
				isMultiSelect :true,
			    isShowCheckBox:true,
				data : data.Rows,
				width : 200,
				selectBoxWidth: 200,
				textField : 'name',
				valueField : 'code',
				valueFieldID:'coverhot' ,
                onSelected:function(data){
                    initHotTreeCombox(data);
                }
			});
            if('${cellManual.coverhot}'!=''){
                comBox3.selectValue('${cellManual.coverhot}');
            }
		}
	);
	//是否属于边界扇区
	
	var comBox4=$("#boundarycellflagVal").ligerComboBox({  
        data: [
            { text: '省内', id: '省内' },
            { text: '省际', id: '省际' },
            { text: '省际且省内', id: '省际且省内' },
            { text: '否', id: '否' }
        ], 
        width : 200,
		selectBoxWidth: 200,
        valueFieldID: 'boundarycellflag'
    });  

	comBox4.selectValue('${cellManual.boundarycellflag}');


    var comBox5=$("#borderprovinceVal").ligerComboBox({
                data: [
                    { text: '云南', id: '云南' },
                    { text: '四川', id: '四川' },
                    { text: '重庆', id: '重庆' },
                    { text: '广西', id: '广西' },
                    { text: '湖南', id: '湖南' }
                ],
                width : 200,
        		selectBoxWidth: 200,
                valueFieldID: 'borderprovince'
            });

    comBox5.selectValue('${cellManual.borderprovince}');

    /*
	//初始化农村、乡镇库的树结构
	var treeCombox2;
	
	 var url="${ctx}/schooljson/vitoTree.action?countryId=${cell.countryId}";
	 	//初始化树控件
			$.post(url, function(
				ajaxData, status) {
			var treeData=ajaxData.townJson;
			treeData = treeData.replace(/"children":\[\],/g, '');
   	    	treeData=eval('('+treeData+')'); 
   	    	
   	    	treeCombox2=$("#countryLibVal").ligerComboBox( {
				width : 200,
				selectBoxWidth : 220,
				selectBoxHeight : 200,
				textField : 'text',
				valueField : 'id',
				valueFieldID : 'countryLib',
				treeLeafOnly : false,
				tree : {
					data : treeData,
					checkbox:true,
					single:false,
					autoCheckboxEven:false,
					nodeWidth:200
				}
			});
   	    
   	    	var countryLib='${cellManual.countryLib}';
   	    	if(countryLib!=''){
   	    		treeCombox2.selectValue('${cellManual.countryLib}');
   	    	}
		});
	 	
	 	*/
	 	
			//初始化道路、隧道的树结构
			var treeCombox3;
			
			 var url="${ctx}/schooljson/roadTunnelTree.action";
			 	//初始化树控件
					$.post(url, function(
						ajaxData, status) {
					var treeData=ajaxData.roadJson;
					treeData = treeData.replace(/"children":\[\],/g, '');
		   	    	treeData=eval('('+treeData+')'); 
		   	    	
		   	    	treeCombox3=$("#roadLibVal").ligerComboBox( {
						width : 200,
						selectBoxWidth : 220,
						selectBoxHeight : 200,
						textField : 'text',
						valueField : 'id',
						valueFieldID : 'roadLib',
						treeLeafOnly : false,
						tree : {
							data : treeData,
							checkbox:true,
							single:false,
							autoCheckboxEven:false,
							nodeWidth:200
						}
					});
		   	    	var roadLib='${cellManual.roadLib}';
		   	    	if(roadLib!=''){		   	    		
		   	    		treeCombox3.selectValue('${cellManual.roadLib}');
		   	    	}
				});

			//初始校园库、风景库树结构
            treeCombox4 = $("#hotLibVal").ligerComboBox({
                width: 200,
                selectBoxWidth: 220,
                selectBoxHeight: 200,
                textField: 'text',
                valueField: 'id',
                valueFieldID: 'hotLib',
                treeLeafOnly: true,
                tree: {
                    data: null,
                    checkbox: true,
                    nodeWidth: 200
                }
            });
});


//默认没数据
function initHotTreeCombox(sceneType){
    if(sceneType==''){
        var treeManager=treeCombox4.tree.ligerGetTreeManager();
        treeManager.clear();
        return;
    }
    var url="${ctx}/schooljson/hotTree.action";
    var parm={
        cityIds:'${cell.cityId}',
        sceneTypes:sceneType
    }
    //初始化树控件
    $.post(url,parm, function(
            ajaxData, status) {
        var treeData=ajaxData.jsonData;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData=eval('('+treeData+')');
        var treeManager=treeCombox4.tree.ligerGetTreeManager();
        treeManager.clear();
        treeManager.setData(treeData);
        var hotLib='${cellManual.hotLib}';
        if(hotLib!=''){
            treeCombox4.selectValue('${cellManual.hotLib}');
        }
    });
}


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
								 window.location.href="${ctx}/business/cell.action";
							 });
						},
						errorPlacement : function(lable, element) {
							element.parent().ligerTip({ content: lable.html(), target: element[0] });
							//lable.appendTo(element.parent());
						},
						success : function(lable) {
							lable.ligerHideTip();
							lable.remove();
						}
					});
});


function add(){
	$("#form1").submit();
}

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
                <c:if test="${editFlag==1}">
                	室外覆盖小区手工信息编辑
                </c:if>         
                <c:if test="${editFlag==0}">
             	    室外覆盖小区手工信息录入
                </c:if>
             </p>
	</div>
	<div class="content_2">
    <form method="post" name="form1" id="form1" action="${ctx}/businessjson/addCellManual.action">
	     <table cellpadding="0" cellspacing="0" class="tab_1" >
	     	<tr>
	     		<th width="150px">小区名称</th>
	     		<td width="250px">
	     		${cell.name}
	     		<input name="cellManual.intId" type="hidden" value="${cell.intId}"/>
				<input name="editFlag" type="hidden" value="${editFlag}">
	     		</td>
	     		<th width="150px">归属BSC</th>
	     		<td width="250px">${ccell.bscName}</td>
	     	</tr>
	     		<tr>
	     		<th>CI</th>
	     		<td>${ccell.ci}</td>
	     		<th>是否功分</th>
	     		<td>
	     			<c:choose>
	     				<c:when test="${cell.isGf==0}">
	     					否
	     				</c:when>
	     				<c:otherwise>
	     					是
	     				</c:otherwise>
	     			</c:choose>
	     		</td>
	     	</tr>
	     	<tr>
	     		<th>功分编号</th>
	     		<td colspan="3">${cell.isGf}</td>

	     	</tr>
	     	<tr>
                <th><span style="color: red;">*</span>室内分布数量</th>
                <td><input name="cellManual.indoornum"  type="text" class="input150 required" value="${cellManual.indoornum}"/></td>
	     		<th><span style="color: red;">*</span>多载频边界Border扇区</th>
	     		<td>
	     			<input name="cellManual.borderflag" type="text" class="input150 required" value="${cellManual.borderflag}"/>
	     		</td>
	     	</tr>
	     	<tr>
                <th><span style="color: red;">*</span>馈线长度</th>
                <td>
                    <input name="cellManual.feederlength" id="feederlength3333" type="text" class="input150 required" value="${cellManual.feederlength}"/>
                </td>
	     		<th><span style="color: red;">*</span>扇区覆盖区域类型</th>
	     		<td>
	     			<input name="coverareaVal" id="coverareaVal" type="text" class="required">
	     			<input name="cellManual.coverarea" id="coverarea" type="hidden"/>
	     		</td>
	     		<%--<td>农村、乡镇</td>--%>
	     		<%--<td>--%>
	     			<%--<input name="countryLibVal" id="countryLibVal" type="text">--%>
	     			<%--<input name="cellManual.countryLib" id="countryLib" type="hidden"/>--%>
	     		<%--</td>--%>
	     	</tr>
	     	
	     	<tr>
	     		<th><span style="color: red;">*</span>扇区覆盖道路类型</th>
	     		<td>
	     			<input name="coverroadVal" id="coverroadVal" type="text" class="required">
	     			<input name="cellManual.coverroad" id="coverroad" type="hidden"/>
	     		</td>
	     		<td>道路、隧道</td>
	     		<td>
	     			<input name="roadLibVal" id="roadLibVal" type="text">
	     			<input name="cellManual.roadLib" id="roadLib" type="hidden"/>
	     		</td>
	     	</tr>
	     	
	     	<tr>
	     		<th>扇区覆盖场景类型</th>
	     		<td>
	     			<input name="coverhotVal" id="coverhotVal" type="text">
	     			<input name="cellManual.coverhot" id="coverhot" type="hidden"/>
	     		</td>
	     		<td>场景名称</td>
	     		<td>
	     			<input name="hotLibVal" id="hotLibVal" type="text">
	     			<input name="cellManual.hotLib" id="hotLib" type="hidden"/>
	     		</td>
	      </tr>
	     	
	     		<tr>
	     		<th>是否属边界扇区</th>
	     		<td>
	     			<input name="boundarycellflagVal" id="boundarycellflagVal" type="text">
	     			<input name="cellManual.boundarycellflag" id="boundarycellflag" type="hidden"/>
	     		</td>
	     		<th>接壤省份名称</th>
	     		<td>
	     			<input name="borderprovinceVal" type="text" id="borderprovinceVal"/>
                    <input name="cellManual.borderprovince" type="hidden" id="borderprovince"/>
	     		</td>
	     		</tr>
	     	
	     		<tr>
	     		<th>接壤城市名称</th>
	     		<td>
	     			<input name="cellManual.bordercity" type="text" class="input150" value="${cellManual.bordercity}"/>
	     		</td>
	     		</tr>
	     	
	     </table>
	     
	     <table cellpadding="0" cellspacing="0" class="tab_inquires_75" >
	     	<tr>
	     		<th id="inquires_top" colspan="6">
	     			小区天线信息
	     		</th>
	     	</tr>
	     	<tr>
	     		<th width="30px">编号</th>
	     		<th width="250px">天线型号</th>
	     		<th width="100px">方位角</th>
	     		<th width="100px">挂高</th>
	     		<th width="100px">电子倾角</th>
	     		<th width="100px">机械倾角</th>
	     	</tr>
	 
	  	<c:forEach begin="0" end="${isGf-1}" var="i" step="1">
	     	<tr>
	     		<td>${i+1}</td>
	     		<td>
	     			<input id="airModelVal${i}" type="text"/>
	     			<input name="cellManual.airLibs[${i}].airId" id="airModel${i}" type="hidden"/>
	     		</td>
	     		<td><input name="cellManual.airLibs[${i}].azimuth" type="text" class="input100" value="${cellManual.airLibs[i].azimuth }"/></td>
	     		<td><input name="cellManual.airLibs[${i}].hanghigh" type="text" class="input100" value="${cellManual.airLibs[i].hanghigh}"/></td>
	     		<td><input name="cellManual.airLibs[${i}].electrondip" type="text" class="input100" value="${cellManual.airLibs[i].electrondip}"/></td>
	     		<td><input name="cellManual.airLibs[${i}].enginedip" type="text" class="input100" value="${cellManual.airLibs[i].enginedip}"/></td>	     		
	     	</tr>
	   </c:forEach>
	   	
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