<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>基础数据统计</title>
<%@ include file="/appviews/common/tag.jsp" %>
<script type="text/javascript">
var gridObj = null;
var typeCombox2=null;
$(function() {
    var treeCombox;
    //初始化地市
    //初始化树控件
    $.post("${ctx}/schooljson/initCityTree.action", function(ajaxData, status) {
        var treeData = ajaxData.cityJson;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData = eval('(' + treeData + ')');
        treeCombox = $("#cityId").ligerComboBox({
            width : 150,
            selectBoxWidth : 180,
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
    var typeCombox = $("#typeIdVal").ligerComboBox({
        data: [
            { text: '物理站点', id: '1' },
            { text: '小区', id: '2' }
        ],
        width : 150,
        selectBoxWidth: 180,
        valueFieldID: 'typeId',
        onSelected:function(value, text) {
            initTypeCombox2(value);
        }
    });
    //数据类型
        typeCombox2 = $("#subTypeIdVal").ligerComboBox({
        data:null,
        width : 150,
        selectBoxWidth: 180,
        valueFieldID: 'subTypeId',
        onSelected:function(value, text) {
            $("#cnName").val("");
            $("#enName").val("");
            $("#ids").val("");
        }
    });
    typeCombox.selectValue('1');
    //统计字段

    $("#cnName").ligerComboBox({
        onBeforeOpen: selectColmns,
        width:200
    });
    $("#toptoolbar").ligerToolBar({
          items: [
              { text: '导出', click: exportData , icon:'add'}
          ]
      });

    toSearch();
});
   function initTypeCombox2(data){
       var btsData= [
           { text: '室外覆盖站点', id: '1' },
           { text: 'BBU设备', id: '2' },
           { text: '室内分布站点', id: '7' },
           {text:'纯BBU',id:'5'},
           {text:'隧道覆盖站点',id:'8'}
       ];
       var cellData= [
           { text: '室外覆盖小区', id: '4' },
           { text: '室内分布小区', id: '3' },
           { text: '隧道覆盖小区', id: '6' }
       ];
       if(data==1){
           liger.get("subTypeIdVal").setData(btsData);
           typeCombox2.selectValue('1');
       }else if(data==2) {
           liger.get("subTypeIdVal").setData(cellData);
           typeCombox2.selectValue('7');
       }
   }
   //选择统计字段
   function selectColmns(){
      var typeId=$("#subTypeId").val();
      if(typeId==5){
          typeId=2;
      }
      var typeName=$("#subTypeIdVal").val();
      var title="选择"+typeName+"所需统计字段";
       var m=$.ligerDialog({
			height : 520,
			url : '${ctx}/business/selectColumns.action?typeId='+typeId,
			width : 1060,
			name : 'columns',
			title : title,
			showMax:true,
			showToggle: true,
//			showMin: true,
			isResize : true,
            isHidden:false
		});
   }

   function buildGrid(url){
       gridObj=null;
       var cnName=$("#cnName").val();
       var enName =$("#enName").val();
       var cityIds=$("#cityIdVal").val();
       var typeId=$("#typeId").val();
       if(cnName==''||enName==''){
           cnName="本地网,区县";
           enName="CITYNAME,COUNTRYNAME"
           url="${ctx}/businessjson/countResult.action?ids=2,3&cityIds="+cityIds+"&typeId="+typeId;
           $("#cnName").val(cnName);
           $("#ids").val("2,3");
       }
       var array=enName.split(",");
       var array2=cnName.split(",");
       var colnames="";
       for(var i=0;i<array.length;i++){
           colnames+=",{name:'"+array[i]+"',display:'"+array2[i]+"'}";
       }
       colnames+=",{name:'NUM',display:'数量'}";
       colnames=colnames.substr(1,colnames.length);

       var c=eval("["+colnames+"]")
       gridObj = $("#maingrid").ligerGrid({
              columns: c,
              rownumbers:true,
              showTitle : false,
              pageSize : 50,
              pageSizeOptions:[50,100],
              url:url,
              //checkbox : true,
              sortName: null,
              sortOrder: null,
              width: '99.9%',
              height:'99%'
          });
           gridObj.loadData();
          $("#pageloading").hide();
   }

 //导出
function exportData(){
    var ids=$("#ids").val();
    var cityIds=$("#cityIdVal").val();
    var typeId=$("#typeId").val();
    var url="${ctx}/business/exportCountResult.action?cityIds="+cityIds+"&typeId="+typeId+"&ids="+ids;
    window.location.href = url;
}


function toSearch() {
    var ids=$("#ids").val();
    var cityIds=$("#cityIdVal").val();
    var typeId=$("#subTypeId").val();
    var url="${ctx}/businessjson/countResult.action?ids="+ids+"&cityIds="+cityIds+"&typeId="+typeId;
    buildGrid(url);
}

</script>
</head>

<body>
<div id="main">
    <!-- 标题
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">基础数据查询</p>
    </div>    -->
    <!-- 标题 end-->
    <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="tr_inquires">
                <td width="60px">
                    地区：
                </td>
                <td width="150px">
                    <input type="text" id="cityId"/>
                    <input type="hidden" id="cityIdVal"/>
                </td>
                <td width="60px">
                    统计类型：
                </td>
                <td width="150px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
                </td>
                <td width="60px">
                    数据类型：
                </td>
                <td width="150px">
                    <input type="text" id="subTypeIdVal"/>
                    <input type="hidden" id="subTypeId"/>
                </td>

                <td width="60px">统计字段</td>
                <td width="150px">
                    <input type="text" id="cnName" value="${cnNames}"/>
                    <input type="hidden" id="enName" values="${enNames}"/>
                    <input type="hidden" id="ids" value="${ids}"/>
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