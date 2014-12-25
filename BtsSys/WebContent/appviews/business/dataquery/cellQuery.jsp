<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>小区查询</title>
<%@ include file="/appviews/common/tag.jsp" %>
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

    //数据类型
    var comBox1 = $("#typeIdVal").ligerComboBox({
        data: [
            { text: '室外覆盖小区', id: '否' },
            { text: '室内分布小区', id: '是' },
            { text: '隧道覆盖小区', id: '隧' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'typeId'
    });
    comBox1.selectValue('否');


    //控件
    $("#name").ligerTextBox({width : 150 });
    $("#bscName").ligerTextBox({width : 150 });
    $("#btsId").ligerTextBox({width : 150 });
    $("#ci").ligerTextBox({width : 150 });
    $("#pn").ligerTextBox({width : 150 });

    toSearch();

});

function toolBar1(){
    gridObj.toolbarManager.toolBar.html("");
    gridObj.toolbarManager._setItems(
            [
                {text: '导出', click: columnConfigExport, icon: 'add', type: 1}
            ]
    );
}

function toolBar2(){
       gridObj.toolbarManager.toolBar.html("");
       gridObj.toolbarManager._setItems(
               [
                      {text: '导出', click: columnConfigExport, icon: 'add', type: 1},
                      {line: true},
                      {text: '导出直放站',click: ErectExport, icon: 'modify',type: 2},
                      {line: true},
                      {text: '导出干放站',click: DryExport,icon: 'delete',type: 3}
                  ]
       );
}

function cellGrid(url) {
    if(gridObj!=null){
        toolBar1();
    }
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'小区名称',name:'name',width : 200,align:'center'},
            {display:'本地网',name:'cityName',width : 55,align:'center',
                render: function (row) {
                    if (row.city != null) {
                        return row.city.cityName;
                        }
                }
            },
            {display:'区县',name:'country.cityName',width : 55,align:'center',
                render: function (row) {
                    return row.country.cityName;
                }},
            {display:'扇区号',name:'cellId',width : 55,align:'center'},
            {display:'所属BSC名称',name:'bscName',width : 120,align:'center'},
            {display:'网管编号',name:'btsId',width : 55,align:'center'},
            {display:'CI',name:'ci',width : 60,align:'center'},
            {display:'PN',name:'pn',width : 40,align:'center'},
            {display:'DO小区',name:'doCell',width : 55,align:'center',
                render: function (row) {
                    if (row.doCell == 0) {
                        return "1X";
                    } else if (row.doCell == 2) {
                        return "DO";
                    } else if (row.doCell == 3) {
                        return "1X+DO"
                    }
                }},
            {display:'是否室分',name:'isIndoor',width : 55,align:'center'},
            {display:'是否拉远',name:'isRru',width : 55,align:'center'},
            {display:'是否功分',name:'isGf',width : 55,align:'center',
                render: function (row) {
                    if (row.isGf == 0) {
                        return "否";
                    } else {
                        return "是";
                    }
                }},
            {display:'手工标识',name:'manualFlag',width : 55,align:'center',
                render: function (row) {
                    if (row.manualFlag == 0) {
                        return "<span>未录入</span>";
                    } else {
                        return "<span>已录入</span>";
                    }
                }}
        ],
        toolbar: {
            items: [
                {text: '导出', click: columnConfigExport, icon: 'add', type: 1}
            ]
        },
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '100%',
        height:'99%',
        onDblClickRow :cellInfo
    });
    $("#pageloading").hide();
}

function indoorGrid(url) {
    toolBar2();
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'室内分布名称',name:'name',width : 200,align:'center'},
            {display:'本地网',name:'city.cityName',width : 80,align:'center',
                render: function (row) {
                    if (row.city != null) {
                        return row.city.cityName;
                    }
                }
            },
            {display:'区县',name:'country.cityName',width : 80,align:'center',
                render: function (row) {
                    if (row.country != null) {
                        return row.country.cityName;
                    }
                }},
            {display:'设备类型',name:'vendorBtstype',width : 80,align:'center'},
            {display:'所属BSC名称',name:'bscName',width : 120,align:'center'},
            {display:'施主基站名称',name:'btsName',width : 80,align:'center'},
            {display:'小区序号',name:'cellSeq',width : 80,align:'center'},
            {display:'PN',name:'pn',width : 80,align:'center'},
            {display:'CI',name:'ci',width : 80,align:'center'},
            {display:'站点性质一',name:'prop1',width : 80,align:'center'},
            {display:'站点性质二',name:'prop2',width : 80,align:'center'},
            {display:'站点分级',name:'grade',width : 80,align:'center'},
            {display:'直放设备数量',name:'repeaternum',width : 80,align:'center'},
            {display:'干放设备数量',name:'drynum',width : 80,align:'center'}

        ],
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '100%',
        height:'98%',
        onDblClickRow :indoorInfo
    });
    $("#pageloading").hide();
}
//查询
function toSearch() {
    var typeId = $("#typeId").val();
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var name = $("#name").val();
    var bscName = $("#bscName").val();
    var btsId = $("#btsId").val();
    var ci = $("#ci").val();
    var pn = $("#pn").val();
    var url;

    if (typeId == '否') {
        url = encodeURI("${ctx}/businessjson/cellQueryData.action?isIndoor=否&countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId + "&ci=" + ci + "&pn=" + pn);
        cellGrid(url);
    } else if (typeId == '是') {
        url = encodeURI("${ctx}/businessjson/indoorQueryData.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&ci=" + ci + "&pn=" + pn);
        indoorGrid(url);
    } else if (typeId == '隧') {
        url = encodeURI("${ctx}/businessjson/cellQueryData.action?isIndoor=隧&countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&ci=" + ci + "&pn=" + pn);
        cellGrid(url);
    }else{
        //默认查询室外覆盖小区
        url = encodeURI("${ctx}/businessjson/cellQueryData.action?isIndoor=否&countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId + "&ci=" + ci + "&pn=" + pn);
        cellGrid(url);
    }
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
    window.location.href = "${ctx}/business/btsInfo.action?intId=" + data.intId;
}
//小区信息显示界面
function cellInfo(data) {
    window.location.href = "${ctx}/business/cellInfo.action?intId=" + data.intId;
}
//BBU信息显示界面
function bbuInfo(data) {
    window.location.href = "${ctx}/business/bbuInfo.action?intId=" + data.intId;
}

//indoorinfo显示界面
function indoorInfo(data) {
    window.location.href = "${ctx}/business/business/indoorDetail.action?intId=" + data.intId;
}

//transferinfo显示页面
function transferInfo(data){
    window.location.href="${ctx}/business/showTransferDetail.action?transferId="+data.id;
}

function columnConfig() {
    var typeId = $("#typeId").val();
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var name = $("#name").val();
    var bscName = $("#bscName").val();
    var btsId = $("#btsId").val();
    var ci = $("#ci").val();
    var pn = $("#pn").val();
   var url = "${ctx}/business/columnConfig.action?typeId=" + typeId+"&countryIds="+cityIds+"&name="+name+"&bscName="+bscName+"&btsId="+btsId+"&ci="+ci+"&pn="+pn;
    $.ligerDialog.open({
        url : url,
        height : 450,
        width : 1000,
        showMax:true,
        showToggle: true,
        showMin: true,
        isResize : true,
        isDrag:true,
        name : 'columnConfig',
        title : '选择导出模板'

    });
}

function columnConfigExport() {
    var type = $("#typeId").val();
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var name = $("#name").val();
    var bscName = $("#bscName").val();
    var btsId = $("#btsId").val();
    var ci = $("#ci").val();
    var pn = $("#pn").val();
    var url;
    if(type=='否')
        url=encodeURI("${ctx}/business/columnConfig.action?typeId=4&countryIds="+cityIds+"&name="+name+"&bscName="+bscName+"&btsId="+btsId+"&ci="+ci+"&pn="+pn);
    else if(type=='是')
        url=encodeURI("${ctx}/business/columnConfig.action?typeId=3&countryIds="+cityIds+"&name="+name+"&bscName="+bscName+"&btsId="+btsId+"&ci="+ci+"&pn="+pn);
    else if(type=='隧')
        url=encodeURI('${ctx}/business/tunelExport?countryIds='+cityIds+'&name='+name+"&bscName="+bscName+"&btsId="+btsId+"&ci="+ci+"&pn="+pn);
    window.location.href = url;
}
//直放站导出
function ErectExport() {
    window.location.href ="${ctx}/business/exportErect.action";
}
//干放站导出
function DryExport() {
    window.location.href ="${ctx}/business/exportDry.action";
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
                <td width="150px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
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
            <tr>
                <td>CI:</td>
                <td>
                    <input type="text" id="ci"/>
                </td>
                <td>PN:</td>
                <td>
                    <input type="text" id="pn"/>
                </td>
            </tr>
        </table>
        <div id="toptoolbar"></div>
        <div id="maingrid"></div>
    </div>
</div>
</body>
</html>