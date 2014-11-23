<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>基础数据查询</title>
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
            { text: '室外覆盖站点', id: '1' },
            { text: '纯BBU站点', id: '2' },
            { text: '室内分布站点', id: '3' },
            { text: '小区', id: '4' },
            { text: '传输节点', id: '5' },
            { text: '隧道覆盖站点', id: '6' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'typeId'
    });
    comBox1.selectValue('1');


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



function btsGrid(url) {
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'室外覆盖站点名称',name:'name',width : 100,align:'center'},
            {display:'本地网',name:'cityName',width : 80,align:'center'},
            {display:'区县',name:'country.cityName',width : 80,align:'center',
                render: function (row) {
                    return row.country.cityName;
                }},
            {display:'所属BSC',name:'bscName',width :120,align:'center'} ,
            {display:'网管编号',name:'btsId',width :60,align:'center',isSort:true} ,
            {display:'维护等级',name:'serviceLevel',width : 60,align:'center'},
            {display:'机房产权',name:'circuitroomOwnership',width : 60,align:'center'},
            {display:'传输产权',name:'transOwnership',width : 60,align:'center'},
            {display:'是否拉远',name:'isRru',width : 60,align:'center'},
            {display:'施主基站名称',name:'btsName',width : 120,align:'center'},
            {display:'设备类型',name:'vendorBtstype',width : 60,align:'center'},
            {display:'手工标识',name:'manualFlag',width : 60,align:'center',
                render: function (row) {
                    if (row.manualFlag == 0) {
                        return "<span>未录入</span>";
                    } else {
                        return "<span>已录入</span>";
                    }
                }},
            {display:'状态',name:'manualFlag',width : 60,align:'center',
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
        onDblClickRow :btsInfo
    });
    $("#pageloading").hide();
}

function bbuGrid(url) {
    toolBar1();
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'纯BBU站点名称',name:'name',width : 150,align:'center'},
            {display:'本地网',name:'cityName',width : 80,align:'center'},
            {display:'区县',name:'country.cityName',width : 80,align:'center',
                render: function (row) {
                    return row.country.cityName;
                }},
            {display:'所属BSC',name:'bscName',width :120,align:'center'} ,
            {display:'网管编号',name:'btsId',width :60,align:'center'} ,
            {display:'共站BBU数量',name:'shareBbuCount',width : 100,align:'center'},
            {display:'机房产权',name:'circuitroomOwnership',width : 60,align:'center'},
            {display:'传输产权',name:'transOwnership',width : 60,align:'center'},
            {display:'手工标识',name:'manualFlag',width : 60,align:'center',
                render: function (row) {
                    if (row.manualFlag == 0) {
                        return "<span>未录入</span>";
                    } else {
                        return "<span>已录入</span>";
                    }
                }}
        ],
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '100%',
        height:'99%',
        onDblClickRow :bbuInfo
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
//        toolbar: {
//            items: [
//                {text: '导出', click: columnConfigExport, icon: 'add', type: 1},
//                {line: true},
//                {text: '导出直放站',click: ErectExport, icon: 'modify',type: 2},
//                {line: true},
//                {text: '导出干放站',click: DryExport,icon: 'delete',type: 3}
//            ]
//        },
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


function cellGrid(url) {
    toolBar1();
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'小区名称',name:'name',width : 200,align:'center'},
            {display:'本地网',name:'cityName',width : 80,align:'center'},
            {display:'区县',name:'country.cityName',width : 80,align:'center',
                render: function (row) {
                    return row.country.cityName;
                }},
            {display:'扇区号',name:'cellId',width : 80,align:'center'},
            {display:'所属BSC名称',name:'bscName',width : 120,align:'center'},
            {display:'网管编号',name:'btsId',width : 80,align:'center'},
            {display:'CI',name:'ci',width : 80,align:'center'},
            {display:'PN',name:'pn',width : 80,align:'center'},
            {display:'DO小区',name:'doCell',width : 80,align:'center',
                render: function (row) {
                    if (row.doCell == 0) {
                        return "1X";
                    } else if (row.doCell == 2) {
                        return "DO";
                    } else if (row.doCell == 3) {
                        return "1X+DO"
                    }
                }},
            {display:'是否室分',name:'isIndoor',width : 80,align:'center'},
            {display:'是否拉远',name:'isRru',width : 80,align:'center'},
            {display:'是否功分',name:'isGf',width : 80,align:'center',
                render: function (row) {
                    if (row.isGf == 0) {
                        return "否";
                    } else {
                        return "是";
                    }
                }},
            {display:'手工标识',name:'manualFlag',width : 60,align:'center',
                render: function (row) {
                    if (row.manualFlag == 0) {
                        return "<span>未录入</span>";
                    } else {
                        return "<span>已录入</span>";
                    }
                }}
        ],
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

function transferGrid(params){
    toolBar1();
    gridObj = $("#maingrid").ligerGrid({
        rownumbers: true,
        url: '${ctx}/businessjson/queryTransferList',
        parms: params,
        showTitle : false,
        checkbox: true,
        width: '100%',
        height: '99%',
        pageSize: 50,
        pageSizeOptions: [50,100],
        onDblClickRow: transferInfo,
        columns: [
                    {display: '本地网', name: 'cityName', minWidth: 100, align: 'center'},
                    {display: '区县', name: 'countryName', minWidth: 100, align: 'center'},
                    {display: '站名', name: 'name', minWidth: 200, align: 'center'},
                    {display: '所属类型', name: 'typeName', minWidth: 50, align: 'center'},
                    {display: '经度', name: 'longitude', minWidth: 50, align: 'center'},
                    {display: '纬度', name: 'latitude', minWidth: 50, align: 'center'},
                    {display: '备注', name: 'remark', minWidth: 180, align: 'center'},
                    {display: '下挂基站', name: 'btsList', minWidth: 180, align: 'center', render: function(data, index, value){
                        var result = '';
                        for(var i in value){
                            var bts = value[i];
                            var name = bts['name'];
                            result += name + ',';
                        }
                        if(result) result = result.substring(0, result.length - 1);
                        return result;
                    }}
        ]
    });
    $("#pageloading").hide();
}


function tunelGrid(params){
    toolBar1();
    gridObj = $("#maingrid").ligerGrid({
        rownumbers: true,
        url: '${ctx}/businessjson/tunelData.action',
        parms: params,
        showTitle : false,
        checkbox: true,
        width: '100%',
        height: '99%',
        pageSize: 50,
        pageSizeOptions: [50,100],
        onDblClickRow: transferInfo,
        columns: [
            {display:'隧道覆蓋站点名称',name:'name',width : 140,align:'center'},
            {display:'本地网',name:'cityName',width : 80,align:'center',
                render:function(row){
                    if(row.city){
                        return row.city.cityName;
                    }
                }},
            {display:'区县',name:'countryName',width : 80,align:'center',
                render:function(row){
                    if(row.country){
                        return row.country.cityName;
                    }
                }},
            {display:'是否拉远',name:'isRru',width : 60,align:'center'},
            {display:'施主基站名称',name:'btsName',width : 120,align:'center'},
            {display:'所属BSC名称',name:'bscName',width : 120,align:'center'},
            {display:'网管编号',name:'btsId',width : 80,align:'center'},
            {display:'机房产权',name:'circuitroomOwnership',width : 60,align:'center'},
            {display:'传输产权',name:'transOwnership',width : 60,align:'center'},
            {display:'维护等级',name:'serviceLevel',width : 60,align:'center'},
            {display:'手工标识',name:'manualFlag',width : 60,align:'center',
                render: function (row) {
                    if (row.manualFlag == 0) {
                        return "未录入";
                    } else {
                        return "已录入";
                    }
                }}
        ]
    });
    $("#pageloading").hide();
}


function add() {

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

    if (typeId == 1) {
        url = encodeURI("${ctx}/businessjson/btsData.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId);
        btsGrid(url);
    } else if (typeId == 2) {
        url = encodeURI("${ctx}/businessjson/bbuData.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId);
        bbuGrid(url);
    } else if (typeId == 3) {
        url = encodeURI("${ctx}/businessjson/indoorQueryData.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&ci=" + ci + "&pn=" + pn);
        indoorGrid(url);
    } else if (typeId == 4) {
        url = encodeURI("${ctx}/businessjson/cellData.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId + "&ci=" + ci + "&pn=" + pn);
        cellGrid(url);
    }else if (typeId == 5) {
        var params = {countryIds: cityIds, name: name};
        transferGrid(params);
    } else if(typeId==6){
        var params = {countryIds: cityIds, name: name,bscName:bscName,btsId:btsId};
        tunelGrid(params);
    } else{
        //默认查询物理站点
        url = encodeURI("${ctx}/businessjson/btsData.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId);
        btsGrid(url);
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
    var typeId = $("#typeId").val();
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var name = $("#name").val();
    var bscName = $("#bscName").val();
    var btsId = $("#btsId").val();
    var ci = $("#ci").val();
    var pn = $("#pn").val();
    var url;
    if(typeId == '5') url = encodeURI('${ctx}/business/transferExport?countryIds='+cityIds+'&name='+name)
    else if(typeId=='6')
        url=encodeURI('${ctx}/business/tunelExport?countryIds='+cityIds+'&name='+name);
    else url = encodeURI("${ctx}/business/columnConfig.action?typeId=" + typeId+"&countryIds="+cityIds+"&name="+name+"&bscName="+bscName+"&btsId="+btsId+"&ci="+ci+"&pn="+pn);
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