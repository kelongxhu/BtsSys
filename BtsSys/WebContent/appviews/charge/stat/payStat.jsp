<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>缴费统计</title>
<%@ include file="/appviews/common/tag.jsp" %>
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
            { text: '全部', id: '' },
            { text: '室内覆盖站点', id: '1' },
            { text: '纯BBU站点', id: '2' },
            { text: '室外分布站点', id: '3' },
            { text: '隧道覆盖站点', id: '6' }
        ],
        width : 120,
        selectBoxWidth: 150,
        valueFieldID: 'typeId'
    });
    comBox1.selectValue('');


    //缴费类型
    var comBox2 = $("#costTypeVal").ligerComboBox({
        data: [
            { text: '全部', id: ''},
            { text: '房租', id: '1' },
            { text: '物业', id: '2' },
            { text: '电费', id: '3' }
        ],
        width : 120,
        selectBoxWidth: 150,
        valueFieldID: 'costType'
    });
    comBox2.selectValue('');
    toSearch();

});


function btsGrid(url) {
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'本地网',name:'cityName',width : 60,align:'center'},
            {display:'区县',name:'countryName',width :60,align:'center'},
            {display:'费用类型',name:'costTypeStr',width :120,align:'center'} ,
            {display:'金额',name:'money',width : 120,align:'center'}
        ],
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '100%',
        height:'99%'
    });
    $("#pageloading").hide();
}
//查询
function toSearch() {
    var btsType = $("#typeId").val();//基站類型
    var costType= $("#costType").val();//费用类型
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var startTime=$("#startTime").val();
    var endTime=$("#endTime").val();
    var url= encodeURI("${ctx}/chargejson/statPay.action?countryIds=" +cityIds+"&btsType="+btsType+"&costType="+costType+"&startTime="+startTime+"&endTime="+endTime);
    btsGrid(url);
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

function payExport(){
    var btsType = $("#typeId").val();//基站類型
    var costType= $("#costType").val();//费用类型
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var btsName = $("#btsName").val();
    var bscName = $("#bscName").val();
    var btsId = $("#btsId").val();
    window.location.href="${ctx}/charge/payExport.action?btsType="+btsType+"&costType="+costType+"&countryIds="+cityIds+"&btsName="+btsName+"&bscName="+bscName+"&btsId="+btsId;
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
                    <input type="text" id="cityId"/>
                    <input type="hidden" id="cityIdVal"/>
                </td>
                <td width="60px">
                    数据类型：
                </td>
                <td width="120px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
                </td>
                <td width="60px">
                    缴费类型：
                </td>
                <td width="120px">
                    <input type="text" id="costTypeVal"/>
                    <input type="hidden" id="costType"/>
                </td>
                <td width="60px">
                    缴费时间：
                </td>
                <td width="300px">
                    <input type="text" id="startTime" class="Wdate" onFocus="WdatePicker({dateFmt: 'yyyy-MM-dd'})"/>
                    ~<input type="text" id="endTime" class="Wdate" onFocus="WdatePicker({dateFmt: 'yyyy-MM-dd'})"/>
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