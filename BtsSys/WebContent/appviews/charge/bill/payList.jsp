<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>缴费</title>
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
            { text: '室外覆盖站点', id: '1' },
            { text: '纯BBU站点', id: '2' },
            { text: '室内分布站点', id: '3' },
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
            { text: '全部', id: ''},
            { text: '房租', id: '1' },
            { text: '物业', id: '2' },
            { text: '电费', id: '3' }
        ],
        width : 120,
        selectBoxWidth: 150,
        valueFieldID: 'costType'
    });
    comBox2.selectValue('1');


    //控件
    $("#btsName").ligerTextBox({width : 100 });
    $("#bscName").ligerTextBox({width : 100 });
    $("#btsId").ligerTextBox({width : 100 });
    $("#money").ligerTextBox({width : 100 });



    toSearch();

});

function toolBar1(){
    gridObj.toolbarManager.toolBar.html("");
    gridObj.toolbarManager._setItems(
            [
                {text: '找回', click: add, icon: 'add', type: 1}
            ]
    );
}

function btsGrid(url) {
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'室外覆盖站点名称',name:'btsName',width : 140,align:'center'},
            {display:'本地网',name:'cityName',width : 60,align:'center'},
            {display:'区县',name:'countryName',width : 60,align:'center'},
            {display:'所属BSC',name:'bscName',width :100,align:'center'} ,
            {display:'BTSID',name:'btsId',width :60,align:'center'} ,
            {display:'费用类型',name:'costTypeStr',width :60,align:'center'} ,
            {display:'缴费周期(月)',name:'payCycle',width :80,align:'center'},
            {display:'缴费金额',name:'money',width : 60,align:'center'},
            {display:'缴费日期',name:'payDay',width : 60,align:'center'},
            {display:'提醒人员',name:'remindUser',width : 80,align:'center'},
            {display:'提醒号码',name:'remindTel',width : 100,align:'center'},
            {display:'下次缴费时间',name:'nextPayTimeStr',width : 100,align:'center'},
            {display:'提醒状态',name:'isRemindStr',width : 100,align:'center'}
        ],
        toolbar: {
            items: [
                {text: '增加', click: add, icon: 'add'},
                {text: '导出模板', click: exportTemplateExcel, icon: 'calendar'},
                {text: '导入', click: importPage, icon: 'photograph'},
                {text: '导出', click: exportChargeSetting, icon: 'print'}
            ]
        },
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '100%',
        height:'99.9%',
        onDblClickRow :btsInfo
    });
    $("#pageloading").hide();
}
    //添加页面
    function add(){
        var rows = gridObj.getCheckedRows();
        var j=rows.length;
        if(j==0){
            $.ligerDialog.alert('请选择要缴费的数据！');
            return;
        }else if (j > 1) {
            $.ligerDialog.alert('请选择一条录入！');
            return;
        }
        var intId;
        var btsType;
        var costType;
        $(rows).each(function() {
            intId=this.intId;
            btsType=this.btsType;
            costType=this.costType;
        });
        window.location.href="${ctx}/charge/payAddPage.action?intId="+intId+"&btsType="+btsType+"&costType="+costType;
    }


//查询
function toSearch() {
    var btsType = $("#typeId").val();//基站類型
    var costType= $("#costType").val();//费用类型
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var btsName = $("#btsName").val();
    var bscName = $("#bscName").val();
    var btsId = $("#btsId").val();
    var contractEndtime=$("#contractEndtime").val();
    var money=$("#money").val();
    var url= encodeURI("${ctx}/chargejson/wyBtsChargeList.action?countryIds=" + cityIds + "&btsName=" + btsName + "&bscName=" + bscName + "&btsId=" + btsId+"&btsType="+btsType+"&costType="+costType+"&contractEndtime="+contractEndtime+"&money="+money);
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

//基站显示信息
function btsInfo(data) {
    window.location.href = "${ctx}/charge/payDetail.action?intId=" + data.intId+"&btsType="+data.btsType+"&costType="+data.costType;
}

function exportTemplateExcel(){
    var btsType = $("#typeId").val();//基站類型
    var costType= $("#costType").val();//费用类型
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var btsName = $("#btsName").val();
    var bscName = $("#bscName").val();
    var btsId = $("#btsId").val();
    var contractEndtime=$("#contractEndtime").val();
    var money=$("#money").val();
    window.location.href="${ctx}/charge/exportTemplateExcel.action?countryIds=" + cityIds + "&btsName=" + btsName + "&bscName=" + bscName + "&btsId=" + btsId+"&btsType="+btsType+"&costType="+costType+"&contractEndtime="+contractEndtime+"&money="+money;
}

function exportChargeSetting(){
    var btsType = $("#typeId").val();//基站類型
    var costType= $("#costType").val();//费用类型
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var btsName = $("#btsName").val();
    var bscName = $("#bscName").val();
    var btsId = $("#btsId").val();
    var contractEndtime=$("#contractEndtime").val();
    var money=$("#money").val();
    window.location.href="${ctx}/charge/exportChargeSetting.action?countryIds=" + cityIds + "&btsName=" + btsName + "&bscName=" + bscName + "&btsId=" + btsId+"&btsType="+btsType+"&costType="+costType+"&contractEndtime="+contractEndtime+"&money="+money;
}

function importPage(){
    var btsType= $("#typeId").val();//费用类型
    var costType=$("#costType").val();//基站类型
    var url="${ctx}/charge/importPage.action?btsType="+btsType+"&costType="+costType;
    window.location.href=url;
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
                <td width="60px" align="left">基站名称:</td>
                <td>
                    <input type="text" id="btsName"/>
                </td>
                <td width="50px" align="left">BSC名称:</td>
                <td>
                    <input type="text" id="bscName"/>
                </td>
                <td width="50px" align="left">btsid:</td>
                <td>
                    <input type="text" id="btsId"/>
                </td>
                <td width="60px" align="left">合同到期:</td>
                <td>
                    <input type="text" id="contractEndtime" class="Wdate input100" onFocus="WdatePicker({dateFmt: 'yyyy-MM'})"/>
                </td>
                <td width="50px" align="left">费用:</td>
                <td>
                    <input type="text" id="money"/>
                </td>
            </tr>
        </table>
        <div id="toptoolbar"></div>
        <div id="maingrid"></div>
    </div>
</div>
</body>
</html>