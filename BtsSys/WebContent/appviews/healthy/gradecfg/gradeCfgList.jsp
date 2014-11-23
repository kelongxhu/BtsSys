<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>规则配置</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj;
        $(function () {
            $("#toptoolbar").ligerToolBar({
                items: [
                    { text: '增加', click: add2, icon: 'add'},
                    { text: '编辑', click: edit, icon: 'modify'},
                    { text: '删除', click: del, icon: 'delete'}
                ]
            });

            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display: '规则分数', name: 'grade', width: 80, align: 'center'},
                    {display: '规则类型', name: 'gradetype', width: 80, align: 'center',
                        render: function (row) {
                            if (row.gradetype == 1) {
                                return "基础信息";
                            } else if (row.gradetype == 2) {
                                return "巡检信息";
                            } else if (row.gradetype == 3) {
                                return "告警信息"
                            } else if (row.gradetype == 4) {
                                return "无线指标"
                            }
                        }},
                    {display: 'A级基站权值', name: 'aweight', width: 80, align: 'center'},
                    {display: 'B级基站权值', name: 'bweight', width: 80, align: 'center'},
                    {display: 'C级基站权值', name: 'cweight', width: 80, align: 'center'},
                    {display: 'D级基站权值', name: 'dweight', width: 80, align: 'center'},
                    {display: '规则描述', name: 'ruledesc', width: 200, align: 'center'},
                    {display: '规则建议', name: 'suggest', width: 200, align: 'center'},
                    {display: '规则范围', name: 'status', width: 100, align: 'center',
                        render: function (row) {
                            if (row.status == 1) {
                                return "评分";
                            } else if (row.status == 2) {
                                return "整改";
                            } else if (row.status == 3) {
                                return "评分/整改"
                            }
                        }}
                ],
              //  rownumbers: true,
                showTitle: false,
                pageSize: 50,
                pageSizeOptions: [50, 100],
                url: '${ctx}/healthyjson/ruleCfgList.action',
               checkbox: true,
                width: '100%',
                height: '100%',
                onDblClickRow:cfgInfo
                <%--frozenDetail: true,--%>
                <%--detail:{height:250,onShowDetail:function(row, detailPanel) {--%>
                                   <%--var g = document.createElement('div');--%>
                                   <%--alert( $(detailPanel));--%>
                                   <%--$(detailPanel).append(g);--%>

                                   <%--$(detailPanel).css('overflow', "hidden");--%>
                                   <%--$(g).css('margin', 10).ligerGrid({--%>
                                       <%--columns:[--%>
                                           <%--{display: '评分项', name: 'subjectName', width: 200, align: 'center',--%>
                                               <%--render: function (data) {--%>
                                                   <%--return data.wySubcfg.subjectName;--%>
                                               <%--}--%>
                                           <%--},--%>
                                           <%--{display : '计算类型',name : 'dataTypeStr',width :100,align : 'center'},--%>
                                           <%--{display : '计算符合',name : 'symbolStr',width : 100,align : 'center'},--%>
                                           <%--{display : '阀值A',name : 'value1',width : 100,align : 'center'},--%>
                                           <%--{display : '阀值B',name : 'value2',width : 100,align : 'center'}--%>
                                       <%--],--%>
                                       <%--isScroll:true,--%>
                                       <%--width:'95%',--%>
                                       <%--height:240,--%>
                                       <%--usePager: false,--%>
                                       <%--url :  "${ctx}/healthyjson/ruleItems.action",--%>
                                       <%--parms:{--%>
                                           <%--ruleCfgId: row.id--%>
                                       <%--}--%>
                                   <%--})--%>
                                 <%--$(detailPanel).show();--%>
                               <%--}}--%>
            });


            //规则类型
            $("#gradeTypeVal").ligerComboBox({
                data: [
                    { text: '基础', id: '1' },
                    { text: '巡检', id: '2' },
                    { text: '告警', id: '3' },
                    { text: '无线', id: '4' }
                ],
                width: 200,
                selectBoxWidth: 200,
                valueFieldID: 'gradeType'
            });
        });
        function add() {
            window.top.abilityCfgWnd = window.top.$.ligerDialog({
                title: '配置评分规则',
                width: 700,
                height: 450,
                showMax: true,
                showToggle: false,
                showMin: true,
                isResize: true,
                url: '${ctx}/healthy/addCfgPg.action'
            });
            window.top.abilityCfgWnd.max();
        }

        function cfgInfo(data){
            window.location.href="${ctx}/healthy/ruleCfgInfo.action?id="+data.id ;
        }

        function add2(){
            window.location.href = "${ctx}/healthy/addCfgPg.action";
        }

        function edit() {
            var rows = gridObj.getCheckedRows();
            var j = rows.length;
            if (j == 0) {
                $.ligerDialog.alert('请选择要编辑的数据！');
                return;
            } else if (j > 1) {
                $.ligerDialog.alert('请选择一条编辑！');
                return;
            }
            var id;
            $(rows).each(function() {
                id = this.id;
            });
            window.location.href = "${ctx}/healthy/addCfgPg.action?id=" + id;
        }
       //删除操作
        function del() {
            var rows = gridObj.getCheckedRows();
            var str = "";
            $(rows).each(function () {
                str += this.id + ",";
            });
            if (str.length == 0) {
                $.ligerDialog.alert('请选择要删除的数据！');
                return;
            } else {
                str = str.substring(0, str.length - 1);
            }

            $.ligerDialog.confirm('确认删除', function (yes) {
                var params = {
                    ids: str
                };
                $.getJSON('${ctx}/healthyjson/del.action', params, function (json) {
                    if (json.result == 1) {
                        alert('删除成功!');
                    } else {
                        alert('删除失败！');
                    }
                    gridObj.loadData();
                });

            });
        }

        //查询
        function toSearch() {
            var gradeType = $("#gradeType").val();
            gridObj.setOptions({
                newPage: 1
            });
            gridObj.setOptions({
                url: encodeURI("${ctx}/healthyjson/ruleCfgList.action?subType="+gradeType)
            });
            gridObj.loadData(); //加载数据
        }


    </script>
</head>
<body>
<div id="main">
    <!-- 标题
	  <div class="main_title_2">
	    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">物理站点录入</p>
	  </div>
	  -->
    <!-- 标题 end-->
    <div class="content">
        <table cellpadding="0" cellspacing="0" width="100%">
            <tr class="tr_inquires">
                <td width="60px">
                    规则类型：
                </td>
                <td width="150px">
                    <input type="text" id="gradeTypeVal"/>
                    <input type="hidden" id="gradeType"/>
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