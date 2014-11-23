<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>废弃查询</title>
<%@ include file="/appviews/common/tag.jsp" %>
<script type="text/javascript">
$("#tab1").hide();
$("#tab2").hide();
$("#tab3").hide();
var current = "tab1";
var rowData;
//var menu;
var gridObj = null;
var gridObj1 = null;
var gridObj2 = null;

var cookie = document.cookie;
var cur = cookie.split(";");

$(document).ready(function () {
    //$("select option:nth-child(1)").attr("selected", "selected");
})
function tt1() {
    if (gridObj1 == null) {
        $(function () {
            $("#toptoolbar1").ligerToolBar({
                items:[
                    { text:'编辑废弃信息', click:edit, icon:'modify'}
                ]
            });
            gridObj1 = $("#maingrid1").ligerGrid({
                columns:[
                    {display:'物理站点名称', name:'name', width:100, align:'center'},
                    {display:'本地网', name:'cityName', width:60, align:'center'},
                    {display:'区县', name:'country', width:60, align:'center',
                        render:function (row) {
                            if (row.country != null) {
                                return row.country.cityName;
                            }
                        }
                    },
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
                    {display:'基站名称', name:'btsName', width:120, align:'center'},
                    {display:'BSC名称', name:'bscName', width:100, align:'center'},
                    {display:'机房产权', name:'circuitroomOwnership', width:60, align:'center'},
                    {display:'传输产权', name:'transOwnership', width:60, align:'center'},
                    {display:'设备类型', name:'vendorBtstype', width:60, align:'center'},
                    {display:'手工标识', name:'deleteResoncode', width:60, align:'center',
                        render:function (row) {
                            if (row.deleteResoncode == 0 || row.deleteResoncode == '') {
                                return "<span class='label label-important'>未填写</span>";
                            } else {
                                return "<span class='label'>已填写</span>";
                            }
                        }
                    },
                    {display:'废弃原因', name:'cons', width:120, align:'center',
                        render:function (row) {
                            if (row.cons) {
                                return row.cons.name;
                            } else {
                                return "";
                            }
                        }
                    },
                    {display:'废弃文本解释', name:'deleteText', width:120, align:'center'}
                ],
                rownumbers:true,
                showTitle:false,
                autoLoad:false,
                pageSize:50,
                pageSizeOptions:[50, 100],
                url:'${ctx}/businessjson/abandonBtsData.action?query=' + $("#query").val(),
                checkbox:true,
                width:'99.8%',
                height:'100%'
//                onContextmenu:function (parm, e) {
//                    rowData = parm.data;
//                    if (rowData) {
//                        menu.show({ top:e.pageY, left:e.pageX });
//                    }
//                    return false;
//                }
            });
        })
    }
}

function tt2() {
    if (gridObj2 == null) {
        $(function () {
            $("#toptoolbar2").ligerToolBar({
                items:[
                    { text:'编辑废弃信息', click:edit, icon:'modify'}
                ]
            });
            gridObj2 = $("#maingrid2").ligerGrid({
                columns:[
                    {display:'小区名称', name:'name', width:250, align:'center'},
                    {display:'本地网', name:'cityName', width:100, align:'center'},
                    {display:'区县', name:'country', width:80, align:'center',
                        render:function (row) {
                            if (row.country != null) {
                                return row.country.cityName;
                            }
                        }
                    },
                    {display:'是否室内', name:'isIndoor', width:80, align:'center'} ,
                    {display:'是否拉远', name:'isRru', width:80, align:'center'},
                    {display:'填写标识', name:'deleteResoncode', width:80, align:'center',
                        render:function (row) {
                            if (row.deleteResoncode == 0 || row.deleteResoncode == '') {
                                return "<span class='label label-important'>未填写</span>";
                            } else {
                                return "<span class='label'>已填写</span>";
                            }
                        }},
                    {display:'废弃原因', name:'cons', width:200, align:'center',
                        render:function (row) {
                            if (row.cons) {
                                return row.cons.name;
                            } else {
                                return "";
                            }
                        }
                    },
                    {display:'废弃文本解释', name:'deleteText', width:200, align:'center'}
                ],
                rownumbers:true,
                showTitle:false,
                autoLoad:false,
                pageSize:50,
                pageSizeOptions:[50, 100],
                url:'${ctx}/businessjson/abandonBtsData.action?query=' + $("#query").val(),
                checkbox:true,
                width:'99.8%',
                height:'100%'
//                onContextmenu:function (parm, e) {
//                    rowData = parm.data;
//                    if (rowData) {
//                        menu.show({ top:e.pageY, left:e.pageX });
//                    }
//                    return false;
//                }
            });
        })
    }
}

$(function () {
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
        items:[
            { text:'编辑废弃信息', click:edit, icon:'modify'}
        ]
    });

    gridObj = $("#maingrid").ligerGrid({
        columns:[
            {display:'物理站点名称', name:'name', width:80, align:'center'},
            {display:'本地网', name:'cityName', width:40, align:'center'},
            {display:'区县', name:'country', width:40, align:'center',
                render:function (row) {
                    if (row.country != null) {
                        return row.country.cityName;
                    }
                }
            },
            {display:'是否室内', name:'isIndoor', width:50, align:'center'} ,
            {display:'是否拉远', name:'isRru', width:50, align:'center'},
            {display:'基站名称', name:'btsName', width:100, align:'center'},
            {display:'BSC名称', name:'bscName', width:100, align:'center'},
            {display:'机房产权', name:'circuitroomOwnership', width:60, align:'center'},
            {display:'传输产权', name:'transOwnership', width:60, align:'center'},
            {display:'维护等级', name:'serviceLevel', width:60, align:'center'},
            {display:'设备类型', name:'vendorBtstype', width:60, align:'center'},
            {display:'填写标识', name:'deleteText', width:60, align:'center',
                render:function (row) {
                    if (row.deleteResoncode == 0 || row.deleteResoncode == '') {
                        return "<span class='label label-important'>未填写</span>";
                    } else {
                        return "<span class='label'>已填写</span>";
                    }
                }} ,
            {display:'废弃原因', name:'deleteResoncode', width:120, align:'center',
                render:function (row) {
                    if (row.cons) {
                        return row.cons.name;
                    } else {
                        return "";
                    }
                }
            },
            {display:'废弃文本解释', name:'deleteText', width:120, align:'center'}
        ],
        rownumbers:true,
        showTitle:false,
        pageSize:50,
        pageSizeOptions:[50, 100],
        url:'${ctx}/businessjson/abandonBtsData.action?query=' + $("#query").val(),
        checkbox:true,
        width:'99.8%',
        height:'100%'
//        onContextmenu:function (parm, e) {
//            rowData = parm.data;
//            if (rowData) {
//                menu.show({ top:e.pageY, left:e.pageX });
//            }
//            return false;
//        }
    });
    $("#pageloading").hide();
    $("#tab1").show();

   // if (cur[0] == "tab1") {
//        toSearch(true);
   // } else if (cur[0] == "tab2") {
    //    $("#query option[value='2']").attr("selected", "selected");
//        toSearch(true);
   // } else {
  //      $("#query option[value='3']").attr("selected", "selected");
   // }

    //控件
    var comBox1 = $("#txFlagVal").ligerComboBox({
        data: [
            { text: '未填写', id: '0' },
            { text: '已填写', id: '1' }
        ],
        width : 170,
        selectBoxWidth: 170,
        valueFieldID: 'txFlag'
    });

});

$(document).ready(function () {
    $("#query").change(function () {
        if ($("#query").val() == "3") {
            document.cookie = null;
            document.cookie = "tab3";
        } else if ($("#query").val() == "2") {
            document.cookie = null;
            document.cookie = "tab2";
        } else {
            document.cookie = null;
            document.cookie = "tab1";
        }
        toSearch(false);
    });
});


//编辑页面
function edit() {
    var rows
    if (current == "tab1") {
        rows = gridObj.getCheckedRows();
    } else if (current == "tab2") {
        rows = gridObj1.getCheckedRows();
    } else {
        rows = gridObj2.getCheckedRows();
    }
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
    var url = "${ctx}/business/business/editPage.action?id=" + id + "&current=" + current;
    if (deleteText)
        url += "&deleteText=" + deleteText;
    if (deleteResoncode)
        url += "&deleteResoncode=" + deleteResoncode;
    window.location.href = url;
}
//删除操作
function editDeleteText() {
    var rows
    if (current == "tab1") {
        rows = gridObj.getCheckedRows();
    } else {
        if (current == "tab2") {
            rows = gridObj1.getCheckedRows();
        } else {
            rows = gridObj2.getCheckedRows();
        }
    }
    var str = "";
    $(rows).each(function () {
        str += this.intId + ",";
    });
    if (str.length == 0) {
        $.ligerDialog.alert('请选择要删除的数据！');
        return;
    } else {
        str = str.substring(0, str.length - 1);
    }
    $.ligerDialog.prompt("编辑废弃文本解释", "", true, function (yes, value) {
        if (yes) {
            $.get("${ctx}/businessjson/edit.action?ids=" + str + "&deleteText=" + value + "&current=" + current, function (result) {
                var msg = result["result"];
                if (msg)
                    $.ligerDialog.alert("操作成功！");
                else
                    $.ligerDialog.alert("操作失败！");
                if (msg) {
                    $(".l-dialog-btn-inner").click();
                    toSearch(true);
                }
            })
        }
    });
}

function del() {
    var rows
    if (current == "tab1") {
        rows = gridObj.getCheckedRows();
    } else {
        if (current == "tab2") {
            rows = gridObj1.getCheckedRows();
        } else {
            rows = gridObj2.getCheckedRows();
        }
    }
    var str = "";
    $(rows).each(function () {
        str += this.intId + ",";
    });
    if (str.length == 0) {
        $.ligerDialog.alert('请选择要删除的数据！');
        return;
    } else {
        str = str.substring(0, str.length - 1);
    }
    $.ligerDialog.confirm('确认删除', function (yes) {
        $.get("${ctx}/businessjson/delete.action?ids=" + str + "&current=" + current, function (result) {
            var msg = result["result"];
            if (msg)
                $.ligerDialog.alert("操作成功！");
            else
                $.ligerDialog.alert("操作失败！");
            if (msg) {
                $(".l-dialog-btn-inner").click();
                toSearch(true);
            }
        })

    });
}

//查询
function toSearch(s) {
    //处理地区
    var cityIds = $("#cityIdVal").val().replace(/;/g, ',');
    var query = $("#query").val();
    var txFlag=$("#txFlag").val();
    if (query == "1") {
        $("#tab1").hide();
        $("#tab2").hide();
        $("#tab3").hide();
        $("#tab1").show();
        current = "tab1";
        gridObj.setOptions({
            newPage:1
        });
        if (s) {
            gridObj.setOptions({
                url:encodeURI("${ctx}/businessjson/abandonBtsData.action?query=" + query + "&countryIds=" + cityIds+"&txFlag="+txFlag)
            });
        }
    } else {
        if (query == "2") {
            $("#tab1").hide();
            $("#tab2").hide();
            $("#tab3").hide();
            tt1();
            current = "tab2";
            $("#tab2").show();
            gridObj1.setOptions({
                newPage:1
            });
            if (s) {
                gridObj1.setOptions({
                    url:encodeURI("${ctx}/businessjson/abandonBtsData.action?query=" + query + "&countryIds=" + cityIds+"&txFlag="+txFlag)
                });
            }
        } else {
            $("#tab1").hide();
            $("#tab2").hide();
            $("#tab3").hide();
            tt2();
            current = "tab3";
            $("#tab3").show();
            gridObj2.setOptions({
                newPage:1
            });
            if (s) {
                gridObj2.setOptions({
                    url:encodeURI("${ctx}/businessjson/abandonBtsData.action?query=" + query + "&countryIds=" + cityIds+"&txFlag="+txFlag)
                });
            }
        }
    }
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
                <td width="60px;">
                    数据类型：
                </td>
                <td width="100px">
                    <select id="query">
                        <option value="1" selected="selected">废弃物理站点</option>
                        <option value="2">废弃BBU</option>
                        <option value="3">废弃小区</option>
                    </select>
                </td>
                <td width="40px">
                    区县：
                </td>
                <td width="150px">
                    <input type="text" id="cityId"/>
                    <input type="hidden" id="cityIdVal"/>
                </td>
                 <td width="60px">
                    是否填写：
                </td>
                <td width="150px">
                    <input type="text" id="txFlagVal"/>
                    <input type="hidden" id="txFlag"/>
                </td>
                <td align="left" style="margin-left:10px;">
                    <input class="btn btn-info btn-small" type="button" onclick="toSearch(true)" value="查询"/>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
        </table>
        <div style="" id="tab1">
            <div id="toptoolbar"></div>
            <div id="maingrid"></div>
        </div>

        <div style="" id="tab2">
            <div id="toptoolbar1"></div>
            <div id="maingrid1"></div>
        </div>
        <div style="" id="tab3">
            <div id="toptoolbar2"></div>
            <div id="maingrid2"></div>
        </div>
    </div>
</div>
</body>
</html>