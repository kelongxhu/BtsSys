<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>基础数据割接找回</title>
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
            { text: '室外覆盖小区', id: '4' },
            { text: '室内分布小区', id: '3' },
            { text: '隧道覆盖小区', id: '6' }
        ],
        width : 120,
        selectBoxWidth: 150,
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
                {text: '找回', click: mateData, icon: 'add', type: 1}
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
            {display:'手工标识',name:'manualFlag',width : 60,align:'center',
                render: function (row) {
                    if (row.manualFlag == 0) {
                        return "<span>未录入</span>";
                    } else {
                        return "<span>已录入</span>";
                    }
                }},
            {display:'状态',name:'deleteFlag',width : 60,align:'center',
                render: function (row) {
                    if (row.deleteFlag == 0) {
                        return "<span>在用</span>";
                    } else {
                        return "<span>已废弃</span>";
                    }
                }},
            {display:'废弃时间',name:'deleteTimeStr',width : 80,align:'center'}

        ],
        toolbar: {
            items: [
                {text: '找回', click: mateData, icon: 'add', type: 1}
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
                }},
            {display:'废弃时间',name:'deleteTimeStr',width : 80,align:'center'}
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
    toolBar1();
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'室内站点名称',name:'name',width : 100,align:'center'},
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
            {display:'手工标识',name:'manualFlag',width : 60,align:'center',
                render: function (row) {
                    if (row.manualFlag == 0) {
                        return "<span>未录入</span>";
                    } else {
                        return "<span>已录入</span>";
                    }
                }},
            {display:'状态',name:'deleteFlag',width : 60,align:'center',
                render: function (row) {
                    if (row.deleteFlag == 0) {
                        return "<span>在用</span>";
                    } else {
                        return "<span>已废弃</span>";
                    }
                }},
            {display:'废弃时间',name:'deleteTimeStr',width : 80,align:'center'}
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
        height:'99%',
        onDblClickRow :indoorInfo
    });
    $("#pageloading").hide();
}


function cellGrid(url) {
    toolBar1();
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'小区名称',name:'name',width : 200,align:'center'},
            {display:'本地网',name:'cityName',width : 80,align:'center',
                render:function(row){
                    return row.city.cityName;
                }
            },
            {display:'区县',name:'country.cityName',width : 80,align:'center',
                render: function (row) {
                    return row.country.cityName;
                }},
            {display:'室分',name:'isIndoor',width : 60,align:'center'},
            {display:'扇区号',name:'cellId',width : 60,align:'center'},
            {display:'所属BSC名称',name:'bscName',width : 120,align:'center'},
            {display:'网管编号',name:'btsId',width : 80,align:'center'},
            {display:'CI',name:'ci',width : 60,align:'center'},
            {display:'PN',name:'pn',width : 60,align:'center'},
            {display:'DO小区',name:'doCell',width : 60,align:'center',
                render: function (row) {
                    if (row.doCell == 0) {
                        return "1X";
                    } else if (row.doCell == 2) {
                        return "DO";
                    } else if (row.doCell == 3) {
                        return "1X+DO"
                    }
                }},
            {display:'是否室分',name:'isIndoor',width : 60,align:'center'},
            {display:'是否拉远',name:'isRru',width : 60,align:'center'},
            {display:'是否功分',name:'isGf',width : 60,align:'center',
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
                }},
            {display:'废弃时间',name:'deleteTimeStr',width : 80,align:'center'}
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




function tunelGrid(url,params){
    toolBar1();
    gridObj = $("#maingrid").ligerGrid({
        rownumbers: true,
        url: url,
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
                }},
            {display:'废弃时间',name:'deleteTimeStr',width : 80,align:'center'}
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
    var startTime=$("#startTime").val();
    var endTime=$("#endTime").val();
    var url;

    if (typeId == 1) {
        url = encodeURI("${ctx}/businessjson/btsDataDel.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId+"&startTime="+startTime+"&endTime="+endTime);
        btsGrid(url);
    } else if (typeId == 2) {
        url = encodeURI("${ctx}/businessjson/bbuDataDel.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId+"&startTime="+startTime+"&endTime="+endTime);
        bbuGrid(url);
    } else if (typeId == 3) {
        //室内分布小区
        url = encodeURI("${ctx}/businessjson/cellDataDel.action?isIndoor=是&countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&ci=" + ci + "&pn=" + pn+"&startTime="+startTime+"&endTime="+endTime);
        cellGrid(url);
    } else if (typeId == 4) {
        //室外覆盖小区
        url = encodeURI("${ctx}/businessjson/cellDataDel.action?isIndoor=否&countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId + "&ci=" + ci + "&pn=" + pn+"&startTime="+startTime+"&endTime="+endTime);
        cellGrid(url);
    } else if(typeId==6){
        //隧道覆盖小区
        url = encodeURI("${ctx}/businessjson/cellDataDel.action?isIndoor=隧&countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&ci=" + ci + "&pn=" + pn+"&startTime="+startTime+"&endTime="+endTime);
        cellGrid(url);
    } else{
        //默认查询物理站点
        url = encodeURI("${ctx}/businessjson/btsDataDel.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId+"&startTime="+startTime+"&endTime="+endTime);
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

function mateData() {
    var typeId = $("#typeId").val();
    var rows = gridObj.getCheckedRows();
    var j = rows.length;
    if (j == 0) {
        $.ligerDialog.alert('请选择要找回的数据！');
        return;
    }
    var intIds="";
    $(rows).each(function() {
        intIds+=this.intId+",";
    });
   var url="";
   if(typeId==4||typeId==3||typeId==6){
     if(j>1){
         $.ligerDialog.alert('请选择一条废弃小区数据找回！');
         return;
     }
     url= "${ctx}/business/getCellMate.action?typeId=" + typeId+"&intIds="+intIds;
   }else{
      url= "${ctx}/business/getMate.action?typeId=" + typeId+"&intIds="+intIds;
   }
    $.ligerDialog.open({
        url : url,
        name:"findDialog",
        height : 500,
        width : 1050,
        showMax:true,
        showToggle: true,
//        showMin: true,
        isResize : true,
        isDrag:true,
        title : '数据找回'
    });
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
                <td width="120px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
                </td>
                <td width="60px">
                    废弃时间：
                </td>
                <td width="300px">
                    <input type="text" id="startTime" class="Wdate" onFocus="WdatePicker({dateFmt: 'yyyy-MM-dd'})"/>
                    ~<input type="text" id="endTime" class="Wdate" onFocus="WdatePicker({dateFmt: 'yyyy-MM-dd'})"/>
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