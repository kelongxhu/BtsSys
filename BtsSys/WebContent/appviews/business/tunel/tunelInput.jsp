<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>隧道站点录入</title>
<%@ include file="/appviews/common/tag.jsp" %>
<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
    #msg {
        padding-left: 16px;
        padding-bottom: 2px;
        font-weight: bold;
        display: inline;
        color: #EA5200;
    }

    #mytab {
        margin-bottom: 20px;
    }

    #mytab li{
        width:180px;
    }

    #mytab li a {
        padding-bottom: 2px;
        padding-top: 2px;
    }

    #mytab li.active a{
        background: url("${ctx}/layouts/images/menu_current2.jpg") repeat-x scroll 0 0 transparent;
        color:#FFF;
    }

</style>

<script type="text/javascript">
    var countryCombox;
    var townCombox;
//注册表单验证
$(function() {
    //有无机房
    var comBox1 = $("#mchroomflagVal").ligerComboBox({
        data: [
            { text: '有', id: '有' },
            { text: '无', id: '无' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'mchroomflag'
    });
    comBox1.selectValue('${tunelManual.mchroomflag}');

    //隧道类型
    var comBox2 = $("#covertypeVal").ligerComboBox({
        data: [
            { text: '单向覆盖', id: '单向覆盖' },
            { text: '双向覆盖', id: '双向覆盖' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'covertype'
    });
    comBox2.selectValue('${tunelManual.covertype}');


    var treeCombox3;
    //初始化道路树控件
    $.post("${ctx}/schooljson/roadTree.action", function(
            ajaxData, status) {
        var treeData=ajaxData.roadJson;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData=eval('('+treeData+')');
        treeCombox3=$("#roadIdVal").ligerComboBox( {
            width : 200,
            selectBoxWidth : 200,
            selectBoxHeight : 200,
            textField : 'text',
            valueField : 'id',
            valueFieldID : 'roadId',
            treeLeafOnly : true,
            tree : {
                data : treeData,
                checkbox:false
            }
        });
        treeCombox3.selectValue('${tunelManual.roadId}');
    });


    //乡镇行政区域
    var treeCombox;
    //初始化树控件
    $.post("${ctx}/schooljson/initCountryTree.action", function(
            ajaxData, status) {
        var treeData=ajaxData.cityJson;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData=eval('('+treeData+')');
        treeCombox=$("#townCountryIdVal").ligerComboBox( {
            width : 200,
            selectBoxWidth : 200,
            selectBoxHeight : 200,
            textField : 'text',
            valueField : 'id',
            valueFieldID : 'townCountryId',
            treeLeafOnly : true,
            tree : {
                data : treeData,
                checkbox:false
            },
            onSelected:function(data){
                if(data!=''){
                    initTownCombox(data);
                }
            }
        });
        var townCountryId='${tunelManual.wyLibVillage.countryId}';
        if(townCountryId!=''){
            treeCombox.selectValue(townCountryId);
        }
    });



    townCombox=$("#townVal").ligerComboBox( {
        data : null,
        width : 200,
        selectBoxWidth : 200,
        selectBoxHeight : 200,
        textField : 'TOWN',
        valueField : 'TOWN',
        valueFieldID : 'town',
        onSelected:function(data){
            if(data!=''){
                var countryId=$("#townCountryId").val();
                initCombox(countryId,data);
            }
        }
    });


    countryCombox=$("#villageVal").ligerComboBox( {
        data : null,
        width : 200,
        selectBoxWidth : 200,
        selectBoxHeight : 200,
        textField : 'village',
        valueField : 'village',
        valueFieldID : 'village'
    });

    //初始化
    if('${tunelManual.town}'!=''){
        townCombox.selectValue('${tunelManual.town}');
    }
    var village='${tunelManual.village}';
    if(village!=''){
        countryCombox.selectValue(village);
    }



    if (!Array.prototype.indexOf)
    {
        Array.prototype.indexOf = function(elt /*, from*/)
        {
            var len = this.length >>> 0;
            var from = Number(arguments[1]) || 0;
            from = (from < 0)
                    ? Math.ceil(from)
                    : Math.floor(from);
            if (from < 0)
                from += len;
            for (; from < len; from++)
            {
                if (from in this &&
                        this[from] === elt)
                    return from;
            }
            return -1;
        };
    }



});


    //初始化乡镇库
    function initTownCombox(data){
        //初始化乡镇下拉框
        var townURL = "${ctx}/schooljson/getTownList.action?countryId="+data;
        townURL=encodeURI(townURL);
        //初始化树控件
        $.post(townURL, function(
                data, status) {
            liger.get("townVal").setData(data.Rows);
        });
    }



    //初始化乡镇库
    function initCombox(countryId,town){
        var url="${ctx}/schooljson/getVillageLib.action?countryId="+countryId+"&town="+town;
        url=encodeURI(url);
        //初始化树控件
        $.post(url, function(
                data, status) {
            liger.get("villageVal").setData(data.Rows);
        });
    }


//隧道属性
var typeURL1 = "${ctx}/schooljson/cons.action?groupCode=TUNELPROP";
var sl1;
$.getJSON(typeURL1,
        function(data) {
            sl1 = $("#propVal").ligerComboBox({
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'name',
                valueField : 'code',
                valueFieldID:'prop'
            });
            sl1.selectValue('${tunelManual.prop}');
        }
);

$("#propVal").blur();

//共建共享
var typeURL2 = "${ctx}/schooljson/cons.action?groupCode=TUNEL_SHARE";
var sl2;
$.getJSON(typeURL2,
        function(data) {
            sl2 = $("#shareflagVal").ligerComboBox({
                data : data.Rows,
                width : 200,
                selectBoxWidth: 200,
                textField : 'name',
                valueField : 'code',
                valueFieldID:'shareflag'
            });
            sl2.selectValue('${tunelManual.shareflag}');
        }
);


$(function() {
    var v = $("#form1").validate(
            {
                ignore:"",
                submitHandler : function(form) {
                    //form.submit();
                    jQuery(form).ajaxSubmit(function(json) {
                        if (json.result == 1) {
                            alert('操作成功!');
                        } else {
                            alert('操作失败!');
                        }
                        window.location.href = "${ctx}/business/tunel.action";
                    });
                },
               errorPlacement : function(error, element) {
                   var arr=["townVal","villageVal","propVal","covertypeVal","shareflagVal","mchroomflagVal","builddate"];
                   var id=element.attr("id");
                   var index=arr.indexOf(id);
                   if(index>-1){
                       var parent = element.parent().parent().parent();
                       var div = $("<div style='float: left;'></div>");
                       div.append(error);
                       div.insertAfter(parent);
                   }else{
                       error.appendTo(element.parent());
                   }
                }
               // success : function(lable) {
                 //   lable.ligerHideTip();
                 //   lable.remove();
                //}
            });
});


function add() {
    $("#form1").submit();
}

//返回
function back() {
    javascript: history.go(-1);
}
$(function() {
});


</script>


</head>
<body>
<!-- 标题 -->
<div id="main_2">
<!-- 标题 end-->
<div class="main_title_2">
    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>
        <c:if test="${editFlag==1}">
            编辑隧道站点手工信息
        </c:if>
        <c:if test="${editFlag==0}">
            录入隧道站点手工信息
        </c:if>
    </p>
</div>
<div class="content">
<form method="post" name="form1" id="form1" action="${ctx}/businessjson/addTunelManual.action">
    <table cellpadding="0" cellspacing="0" class="tab_1">
        <tr>
            <td colspan="4"><span class="label label-success">隧道小区基本信息</span></td>
        </tr>
        <tr>
            <td width="150px">站点名称：</td>
            <td width="300px">${wyTunel.name}
                <input name="tunelManual.intId" type="hidden" value="${wyTunel.intId}"/>
                <input name="editFlag" type="hidden" value="${editFlag}">
            </td>
            <td width="150px">所属BSC:</td>
            <td width="300px">${wyTunel.bscName}</td>
        </tr>
        <tr>
            <td width="150px">本地网：</td>
            <td width="300px">${wyTunel.city.cityName}</td>
            <td width="150px">区县:</td>
            <td width="300px">${wyTunel.country.cityName}</td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>乡镇行政区域:</td>
            <td colspan="3">
                <div style="float: left">
                    <input id="townCountryIdVal" type="text" class="required"/>
                    <input id="townCountryId" type="hidden"/>
                </div>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>所属乡镇:</td>
            <td>
                <div style="float: left">
                    <input id="townVal" type="text" class="required"/>
                    <input name="tunelManual.town" id="town" type="hidden"/>
                </div>
            </td>
            <td>覆盖农村:</td>
            <td>
                <input id="villageVal" type="text"/>
                <input name="tunelManual.village" id="village" type="hidden"/>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>隧道性质:</td>
            <td>
                <div style="float: left">
                     <input type="text" id="propVal" class="required"/>
                     <input name="tunelManual.prop" id="prop" type="hidden" value="${tunelManual.prop}"/>
                </div>
            </td>
            <td><span style="color: red;">*</span>所属道路名称:</td>
            <td><input id="roadIdVal"  type="text" class="required"/>
                <input name="tunelManual.roadId" id="roadId" type="hidden" value="${tunelManual.roadId}"/>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>隧道长度（米)：</td>
            <td><input name="tunelManual.tunelLength" type="text" class="input150 required number" value="${tunelManual.tunelLength}"/>
            </td>
            <td><span style="color: red;">*</span>天馈类型:</td>
            <td><input name="tunelManual.antennatype" type="text" class="input150 required" value="${tunelManual.antennatype}"/>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>天线型号：</td>
            <td><input name="tunelManual.linetype" type="text" class="input150 required"
                       value="${tunelManual.linetype}"/>
            </td>
            <td><span style="color: red;">*</span>覆盖类型:</td>
            <td>
                <div style="float: left">
                   <input type="text" id="covertypeVal" class="required"/>
                    <input name="tunelManual.covertype" id="covertype" type="hidden" value="${tunelManual.covertype}"/>
                </div>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>覆盖范围描述：</td>
            <td>
                <input name="tunelManual.coverrangedesc"  type="text" class="input200 required" value="${tunelManual.coverrangedesc}"/>
            </td>


            <td><span style="color: red;">*</span>详细地址:</td>
            <td>
                <input name="tunelManual.address" type="text" class="input200 required" value="${tunelManual.address}"/>
            </td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>海拔高度：</td>
            <td>
                <input name="tunelManual.tunelHigh" type="text" class="input150 required number"
                       value="${tunelManual.tunelHigh}"/>
            </td>
            <td><span style="color: red;">*</span>信源设备数量：</td>
            <td><input name="tunelManual.rrunum" type="text" class="input150 required number"
                       value="${tunelManual.rrunum}"/></td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>信源设备安装位置:</td>
            <td><input name="tunelManual.rruaddress" type="text" class="input150 required"
                                     value="${tunelManual.rruaddress}"/></td>
            <td><span style="color: red;">*</span>下挂直放站数量：</td>
            <td><input name="tunelManual.repeaternum" type="text" class="input150 required number"
                       value="${tunelManual.repeaternum}"/></td>
        </tr>
        <tr>

            <td><span style="color: red;">*</span>直放站安装位置:</td>
            <td>
                <input name="tunelManual.repeateraddress" type="text" class="input150 required"
                       value="${tunelManual.repeateraddress}"/>
            </td>
            <td><span style="color: red;">*</span>下挂干放数量（A）：</td>
            <td><input name="tunelManual.drynum" type="text" class="input150 required number"
                       value="${tunelManual.drynum}"/></td>
        </tr>
        <tr>

            <td><span style="color: red;">*</span>干放安装位置：</td>
            <td><input name="tunelManual.dryaddress" type="text" class="input150 required"
                       value="${tunelManual.dryaddress}"/></td>
            <td><span style="color: red;">*</span>共建共享情况：</td>
            <td>
                <div style="float: left">
                <input type="text" id="shareflagVal" class="required"/>
                <input name="tunelManual.shareflag" id="shareflag" type="hidden" value="${tunelManual.shareflag}"/>
                </div>
            </td>
        </tr>
        <tr>

            <td><span style="color: red;">*</span>有无机房:</td>
            <td>
                <div style="float: left">
                <input type="text" id="mchroomflagVal"  class="required">
                <input type="hidden" id="mchroomflag" name="tunelManual.mchroomflag" value="${tunelManual.mchroomflag}">
                </div>
            </td>
            <td><span style="color: red;">*</span>机房面积：</td>
            <td><input name="tunelManual.mchroonarea" type="text" class="input150 required number" value="${tunelManual.mchroonarea}"/></td>
        </tr>
        <tr>
            <td><span style="color: red;">*</span>对应信源设备的电表安装位置:</td>
            <td><input name="tunelManual.meteradress" type="text" class="input150 required" value="${tunelManual.meteradress}"/></td>
            <td><span style="color: red;">*</span>建设时间（年月）：</td>
            <td>
                <div style="float: left">
                <input type="text" name="tunelManual.builddate" class="Wdate input150 required" onFocus="WdatePicker({dateFmt: 'yyyy-MM'})" value="${tunelManual.builddateStr}"/>
                <%--<input name="tunelManual.builddate" id="builddate" type="text" class="input150" value="${tunelManual.builddate}"/>--%>
                </div>
            </td>
        </tr>
        <tr>

            <td>业主联系人:</td>
            <td><input name="tunelManual.owner" type="text" class="input150" value="${tunelManual.owner}"/></td>
            <td>业主联系电话：</td>
            <td><input name="tunelManual.ownertel" type="text" class="input150" value="${tunelManual.ownertel}"/></td>
        </tr>
        <tr>
            <td>备注:</td>
            <td colspan="3">
                <textarea name="tunelManual.remark" cols="55"
                          rows="2">${tunelManual.remark}</textarea>
            </td>
        </tr>
    </table>
               <div class="form-actions_2">
                <button class="btn btn-info" type="button" onclick="add();">
                    <i class="icon-ok icon-white"></i>
                    保存
                </button>
                <button class="btn" type="reset" onclick="back();">
                    <i class="icon-repeat"></i>
                    返回
                </button>
 </div>
</form>

</div>
</div>

</body>
</html>