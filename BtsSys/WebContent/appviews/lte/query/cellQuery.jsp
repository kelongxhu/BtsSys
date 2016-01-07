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
            { text: '室外覆盖小区', id: '1' },
            { text: '室内分布小区', id: '2' },
            { text: '隧道覆盖小区', id: '3' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'typeId'
    });
    comBox1.selectValue('1');


    //控件
    $("#name").ligerTextBox({width : 150 });

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
//    if(gridObj!=null){
//        toolBar1();
//    }
    gridObj = $("#maingrid").ligerGrid({
        columns: [
            {display:'小区名称',name:'name',width : 250,align:'center'},
            {display:'本地网',name:'cityName',width : 55,align:'center'},
            {display:'区县',name:'countryName',width : 55,align:'center'},
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
            {display:'高铁覆盖',name:'highTrainFlag',width : 55,align:'center'},
            {display:'红线内外',name:'redLineFlagStr',width : 55,align:'center'},
            {display:'更新时间',name:'updateTimeStr',width : 80,align:'center'}
        ],
        rownumbers:true,
        showTitle : false,
        pageSize : 50,
        pageSizeOptions:[50,100],
        url:url,
        checkbox : true,
        width: '99.9%',
        height:'99%'
    });
    $("#pageloading").hide();
}
//查询
function toSearch() {
    var typeId = $("#typeId").val();
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var name = $("#name").val();
    var url= encodeURI("${ctx}/ltejson/cellData.action?countryIds=" + cityIds + "&name=" + name + "&type=" + typeId);
    cellGrid(url);
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
            </tr>
        </table>
        <div id="toptoolbar"></div>
        <div id="maingrid"></div>
    </div>
</div>
</body>
</html>