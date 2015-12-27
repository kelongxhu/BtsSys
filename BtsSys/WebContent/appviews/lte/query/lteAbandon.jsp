<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>废弃查询</title>
<%@ include file="/appviews/common/tag.jsp" %>
<script type="text/javascript">

var gridObj = null;
var typeId;

$(function() {
    var treeCombox;
    $.post("${ctx}/schooljson/initAllCountryTree.action", function (ajaxData, status) {
        var treeData = ajaxData.cityJson;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData = eval('(' + treeData + ')');
        treeCombox = $("#cityId").ligerComboBox({
            width:200,
            selectBoxWidth:200,
            selectBoxHeight:200,
            textField:'text',
            valueField:'id',
            valueFieldID:'cityIdVal',
            treeLeafOnly:false,
            tree:{
                data:treeData
            }
        });
    });

    $("#toptoolbar").ligerToolBar({
        items: [
            { text: '编辑废弃原因', click: edit , icon:'modify'},
            { text: '导出', click: exportDeleteData , icon:'add'}
        ]
    });

    //数据类型
    var comBox1 = $("#typeIdVal").ligerComboBox({
        data: [
            { text: '物理站点', id: '1' },
            { text: '纯BBU', id: '2' },
            { text: '小区', id: '3' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'typeId'
    });
    comBox1.selectValue('1');

    var comBox2 = $("#txFlagVal").ligerComboBox({
        data: [
            { text: '未填写', id: '0' },
            { text: '已填写', id: '1' }
        ],
        width : 170,
        selectBoxWidth: 170,
        valueFieldID: 'txFlag'
    });

    toSearch();
});


function btsGrid(url) {
    gridObj = null;
    gridObj = $("#maingrid").ligerGrid({
        columns:[
            {display:'物理站点名称', name:'name', width:150, align:'center'},
            {display:'本地网', name:'cityName', width:40, align:'center'},
            {display:'区县', name:'countryName', width:40, align:'center'},
            {display:'是否室内', name:'isIndoor', width:50, align:'center'} ,
            {display:'是否拉远', name:'isRru', width:50, align:'center'},
            {display:'基站名称', name:'enbName', width:100, align:'center'},
            {display:'机房产权', name:'circuitroomOwnership', width:60, align:'center'},
            {display:'传输产权', name:'transOwnership', width:60, align:'center'},
            {display:'维护等级', name:'serviceLevel', width:60, align:'center'},
            {display:'废弃时间', name:'deleteTimeStr', width:80, align:'center'},
            {display:'废弃原因', name:'deleteResonText', width:120, align:'center'},
            {display:'废弃文本解释', name:'deleteText', width:120, align:'center'}
        ],
        rownumbers:true,
        showTitle:false,
        pageSize:50,
        pageSizeOptions:[50, 100],
        url:url,
        checkbox:true,
        width:'99.8%',
        height:'100%'
    });
    $("#pageloading").hide();
}


function bbuGrid(url) {
    gridObj = null;
    gridObj = $("#maingrid").ligerGrid({
        columns:[
            {display:'物理站点名称', name:'name', width:100, align:'center'},
            {display:'本地网', name:'cityName', width:60, align:'center'},
            {display:'区县', name:'countryName', width:60, align:'center'},
            {display:'BBU类型', name:'bbuType', width:80, align:'center',
                render:function (row) {
                    if (row.bbuType == 0) {
                        return "--";
                    } else if (row.bbuType == 1) {
                        return "纯BBU";
                    } else if (row.bbuType == 2) {
                        return "纯BBU共站BBU";
                    } else {
                        return "基站共站BBUS";
                    }
                }
            },
            {display:'基站名称', name:'enbBtsName', width:120, align:'center'},
            {display:'机房产权', name:'circuitroomOwnership', width:60, align:'center'},
            {display:'传输产权', name:'transOwnership', width:60, align:'center'},
            {display:'废弃时间', name:'deleteTimeStr', width:80, align:'center'},
            {display:'填写标识', name:'deleteResoncode', width:60, align:'center',
                render:function (row) {
                    if (row.deleteResoncode == 0 || row.deleteResoncode == '') {
                        return "<span class='label label-important'>未填写</span>";
                    } else {
                        return "<span class='label'>已填写</span>";
                    }
                }
            },
            {display:'废弃原因', name:'deleteResonText', width:120, align:'center'},
            {display:'废弃文本解释', name:'deleteText', width:120, align:'center'}
        ],
        rownumbers:true,
        showTitle:false,
        autoLoad:false,
        pageSize:50,
        pageSizeOptions:[50, 100],
        url:url,
        checkbox:true,
        width:'99.8%',
        height:'100%'
    });
    $("#pageloading").hide();
}


function cellGrid(url) {
    gridObj = null;
    gridObj = $("#maingrid").ligerGrid({
        columns:[
            {display:'小区名称', name:'name', width:250, align:'center'},
            {display:'本地网', name:'cityName', width:100, align:'center'},
            {display:'区县', name:'countryName', width:80, align:'center'},
            {display:'是否室内', name:'isIndoor', width:80, align:'center'} ,
            {display:'是否拉远', name:'isRru', width:80, align:'center'},
            {display:'废弃时间', name:'deleteTimeStr', width:80, align:'center'},
            {display:'填写标识', name:'deleteResoncode', width:80, align:'center',
                render:function (row) {
                    if (row.deleteResoncode == 0 || row.deleteResoncode == '') {
                        return "<span class='label label-important'>未填写</span>";
                    } else {
                        return "<span class='label'>已填写</span>";
                    }
                }},
            {display:'废弃原因', name:'deleteResonText', width:200, align:'center'},
            {display:'废弃文本解释', name:'deleteText', width:200, align:'center'}
        ],
        rownumbers:true,
        showTitle:false,
        autoLoad:false,
        pageSize:50,
        pageSizeOptions:[50, 100],
        url:url,
        checkbox:true,
        width:'99.8%',
        height:'100%'
    });
    $("#pageloading").hide();
}

//编辑页面
function edit() {
    var rows = gridObj.getCheckedRows();
    var j = rows.length;
    if (j == 0) {
        $.ligerDialog.alert('请选择要编辑的数据！');
        return;
    } else if (j > 1) {
        $.ligerDialog.alert('请选择一条编辑的数据！');
        return;
    }
    var id = "";
    var deleteText = "";
    var deleteResoncode = "";

    $(rows).each(function () {
        id = this.intId;
        deleteText = this.deleteText;
        deleteResoncode = this.deleteResoncode;
    });
    var url = "${ctx}/lte/abandonEdit.action?id=" + id + "&typeId=" + typeId;
    if (deleteText)
        url += "&deleteText=" + deleteText;
    if (deleteResoncode)
        url += "&deleteResoncode=" + deleteResoncode;
    alert(url);
    window.location.href = url;
}
//查询
function toSearch() {
    //处理地区
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    typeId = $("#typeId").val();
    var txFlag = $("#txFlag").val();
    var name = $("#name").val();

    var url = encodeURI("${ctx}/ltejson/abandonData.action?typeId=" + typeId + "&countryIds=" + cityIds + "&txFlag=" + txFlag + "&name=" + name);
    if (typeId == 1) {
        btsGrid(url);
    } else if (typeId == 2) {
        bbuGrid(url);
    } else if (typeId == 3) {
        cellGrid(url);
    } else {
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

function exportDeleteData() {
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    typeId = $("#typeId").val();
    var txFlag = $("#txFlag").val();
    var name = $("#name").val();
    var url="${ctx}/business/exportDeleteData.action?typeId=" + typeId + "&countryIds=" + cityIds + "&txFlag=" + txFlag + "&name=" + name;
     window.location.href =encodeURI(url);

}

</script>
</head>
<body>
<input name="con" id="con" type="hidden"/>

<div id="main">
    <!--
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">物理站点录入</p>
    </div>  -->
    <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="tr_inquires">
                <td width="60px">
                    数据类型：
                </td>
                <td width="150px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
                </td>
                <td width="40px">
                    区县：
                </td>
                <td width="150px">
                    <input type="text" id="cityId"/>
                    <input type="hidden" id="cityIdVal"/>
                </td>
                <td align="left" style="margin-left:10px;">
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
                <td width="60px">
                    是否填写：
                </td>
                <td width="150px">
                    <input type="text" id="txFlagVal"/>
                    <input type="hidden" id="txFlag"/>
                </td>

            </tr>
        </table>
        <div id="toptoolbar"></div>
        <div id="maingrid"></div>
    </div>
</div>
</body>
</html>