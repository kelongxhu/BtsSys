<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>小区信息库查询</title>
<%@ include file="/appviews/common/tag.jsp" %>
<script type="text/javascript">
var gridObj = null;
var treeCombox = null;
$(function() {
    //地市树
    treeCombox = $("#cityId").ligerComboBox({
        width : 200,
        selectBoxWidth : 200,
        selectBoxHeight : 200,
        textField : 'text',
        valueField : 'id',
        valueFieldID : 'cityIdVal',
        treeLeafOnly : false,
        tree : {
            data : null
        }
    });

    $("#toptoolbar").ligerToolBar({
        items: [
            { text: '导出', click: exportLib , icon:'add'}
        ]
    });

//数据类型
    var comBox1 = $("#typeIdVal").ligerComboBox({
        data: [
            { text: '校园库', id: '1' },
            { text: '风景库', id: '5' },
            { text: '农村库', id: '2' },
            { text: '道路库', id: '3' },
            { text: '隧道库', id: '6' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'typeId',
        onSelected:function(value, text) {
            if (value == 1 || value == 3) {
                initCity();
            } else {
                initCountry();
            }
        }
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


function initCity() {
    $.post("${ctx}/schooljson/initAllCityTree.action", function(ajaxData, status) {
        var treeData = ajaxData.cityJson;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData = eval('(' + treeData + ')');
        var treeManager = treeCombox.tree.ligerGetTreeManager();
        treeManager.clear();
        treeManager.setData(treeData);
    });
}

function initCountry() {
    $.post("${ctx}/schooljson/initAllCountryTree.action", function(ajaxData, status) {
        var treeData = ajaxData.cityJson;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData = eval('(' + treeData + ')');
        var treeManager = treeCombox.tree.ligerGetTreeManager();
        treeManager.clear();
        treeManager.setData(treeData);
    });
}


function schoolGrid(url) {
    gridObj = null;
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'本地网',name:'CITYNAME',width : 80,align:'center'},
            {display:'校园名称',name:'LIBNAME',width :200,align:'center',
                render:function(row) {
                    if (null != row.LIBNAME) {
                        return "<a style='color: blue;' href='#' onclick=\"schoolInfo('" + row.ID + "')\">" + row.LIBNAME + "</a>";
                    }
                }},
            {display:'小区名称',name:'CELLNAME',width :250,align:'center'} ,
            {display:'所属BSC名称',name:'BSC_NAME',width :120,align:'center'} ,
            {display:'BTSID',name:'BTSID',width :60,align:'center'},
            {display:'CI',name:'CI',width : 60,align:'center'},
            {display:'PN',name:'PN',width : 60,align:'center'}
        ],
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '100%',
        height:'100%'
    });
    $("#pageloading").hide();
}

function secneryGrid(url) {
    gridObj = null;
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'本地网',name:'CITYNAME',width : 80,align:'center'},
            {display:'区县',name:'COUNTRYNAME',width : 80,align:'center'},
            {display:'风景名称',name:'LIBNAME',width :200,align:'center',
                render:function(row) {
                    if (null != row.LIBNAME) {
                        return "<a style='color: blue;' href='#' onclick=\"secneryInfo('" + row.ID + "')\">" + row.LIBNAME + "</a>";
                    }
                }},
            {display:'小区名称',name:'CELLNAME',width :250,align:'center'} ,
            {display:'所属BSC名称',name:'BSC_NAME',width :120,align:'center'} ,
            {display:'BTSID',name:'BTSID',width :60,align:'center'},
            {display:'CI',name:'CI',width : 60,align:'center'},
            {display:'PN',name:'PN',width : 60,align:'center'}
        ],
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '100%',
        height:'100%'
    });
    $("#pageloading").hide();
}

//农村库
function vitoGrid(url) {
    gridObj = null;
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'本地网',name:'CITYNAME',width : 80,align:'center'},
            {display:'区县',name:'COUNTRYNAME',width : 80,align:'center'},
            {display:'农村、乡镇名称',name:'LIBNAME',width :200,align:'center',
                render:function(row) {
                    if (null != row.LIBNAME) {
                        return "<a style='color: blue;' href='#' onclick=\"vitoInfo('" + row.ID + "')\">" + row.LIBNAME + "</a>";
                    }
                }},
            {display:'小区名称',name:'CELLNAME',width :250,align:'center'} ,
            {display:'所属BSC名称',name:'BSC_NAME',width :120,align:'center'} ,
            {display:'BTSID',name:'BTSID',width :60,align:'center'},
            {display:'CI',name:'CI',width : 60,align:'center'},
            {display:'PN',name:'PN',width : 60,align:'center'}
        ],
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '100%',
        height:'100%'
    });
    $("#pageloading").hide();
}



//道路库
function roadGrid(url) {
    gridObj = null;
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'本地网',name:'CITYNAME',width : 80,align:'center'},
            {display:'道路名称',name:'LIBNAME',width :200,align:'center',
                render:function(row) {
                    if (null != row.LIBNAME) {
                        return "<a style='color: blue;' href='#' onclick=\"roadInfo('" + row.ID + "')\">" + row.LIBNAME + "</a>";
                    }
                }},
            {display:'小区名称',name:'CELLNAME',width :250,align:'center'} ,
            {display:'所属BSC名称',name:'BSC_NAME',width :120,align:'center'} ,
            {display:'BTSID',name:'BTSID',width :60,align:'center'},
            {display:'CI',name:'CI',width : 60,align:'center'},
            {display:'PN',name:'PN',width : 60,align:'center'}
        ],
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '100%',
        height:'100%'
    });
    $("#pageloading").hide();
}


function tunnelGrid(url) {
     gridObj = null;
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'本地网',name:'CITYNAME',width : 80,align:'center'},
            {display:'区县',name:'COUNTRYNAME',width : 80,align:'center'},
            {display:'隧道名称',name:'LIBNAME',width :200,align:'center',
                render:function(row) {
                    if (null != row.LIBNAME) {
                        return "<a style='color: blue;' href='#' onclick=\"tunnelInfo('" + row.ID + "')\">" + row.LIBNAME + "</a>";
                    }
                }},
            {display:'小区名称',name:'CELLNAME',width :250,align:'center'} ,
            {display:'所属BSC名称',name:'BSC_NAME',width :120,align:'center'} ,
            {display:'BTSID',name:'BTSID',width :60,align:'center'},
            {display:'CI',name:'CI',width : 60,align:'center'},
            {display:'PN',name:'PN',width : 60,align:'center'}
        ],
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '100%',
        height:'100%'
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
        url = encodeURI("${ctx}/businessjson/schoolQuery.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId + "&ci=" + ci + "&pn=" + pn);
        schoolGrid(url);
    } else if (typeId == 2) {
        url = encodeURI("${ctx}/businessjson/vitoQuery.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId + "&ci=" + ci + "&pn=" + pn);
        vitoGrid(url);
    } else if (typeId == 3) {
        url = encodeURI("${ctx}/businessjson/roadQuery.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&ci=" + ci + "&pn=" + pn);
        roadGrid(url);
    } else if (typeId == 5) {
        url = encodeURI("${ctx}/businessjson/secneryQuery.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId + "&ci=" + ci + "&pn=" + pn);
        secneryGrid(url);
    } else if(typeId==6){
         url = encodeURI("${ctx}/businessjson/tunnelQuery.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId + "&ci=" + ci + "&pn=" + pn);
        tunnelGrid(url);
    } else{
        //默认查询校园库
       url = encodeURI("${ctx}/businessjson/schoolQuery.action?countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId + "&ci=" + ci + "&pn=" + pn);
       schoolGrid(url);
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


function exportLib() {
    var typeId = $("#typeId").val();
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var name = $("#name").val();
    var bscName = $("#bscName").val();
    var btsId = $("#btsId").val();
    var ci = $("#ci").val();
    var pn = $("#pn").val();
    var url = "${ctx}/business/libExport.action?type=" + typeId + "&countryIds=" + cityIds + "&name=" + name + "&bscName=" + bscName + "&btsId=" + btsId + "&ci=" + ci + "&pn=" + pn;
    window.location.href = url;
}
  function schoolInfo(id){
         window.location.href="${ctx}/school/schoolInfo.action?id="+id;
  }
  function secneryInfo(id) {
         window.location.href = "${ctx}/school/secneryInfo.action?id=" + id;
   }
  function vitoInfo(id){
        window.location.href="${ctx}/school/vitoInfo.action?id="+id;
    }
function roadInfo(id) {
    window.location.href = "${ctx}/school/roadInfo.action?id=" +id;
}
 function tunnelInfo(id){
        window.location.href="${ctx}/school/tunnelInfo.action?id="+id;
   }
</script>
</head>
<body>
<div id="main">
    <!-- 标题
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">小区信息库查询</p>
    </div>   -->
    <!-- 标题 end-->
    <div class="content">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr class="tr_inquires">
                <td width="60px">
                    覆盖类型：
                </td>
                <td width="150px">
                    <input type="text" id="typeIdVal"/>
                    <input type="hidden" id="typeId"/>
                </td>
                <td width="60px">
                    地区：
                </td>
                <td width="150px">
                    <input type="text" id="cityId"/>
                    <input type="hidden" id="cityIdVal"/>
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