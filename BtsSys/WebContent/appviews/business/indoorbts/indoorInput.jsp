<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>室分手工数据</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>

    <style type="text/css">

         .container-fluid {
            margin-left: 32px;
            min-width: 800px;
            padding-left: 0px;
            padding-right: 0px;
            border-left: #DDDDDD solid 1px;
            border-bottom: #DDDDDD solid 1px;
            border-right: #DDDDDD solid 1px;
        }

         #mytab {
             margin-bottom: 20px;
         }

         #mytab li a {
             padding-bottom: 2px;
             padding-top: 2px;
         }

         #mytab li.active a {
             background: url("${ctx}/layouts/images/menu_current2.jpg") repeat-x scroll 0 0 transparent;
             color: #FFF;
         }

    </style>

    <script type="text/javascript">
        var townCombox=null;
        var countryCombox=null;
        $(function () {
            var pid = "";

            var treeCombox;
            $.post("${ctx}/schooljson/schooljson/initCountryTree.action", function (ajaxData, status) {
                var treeData = ajaxData.cityJson;
                treeData = treeData.replace(/"children":\[\],/g, '');
                treeData = eval('(' + treeData + ')');
                treeCombox = $("#countryIdVal").ligerComboBox({
                    width:200,
                    selectBoxWidth:200,
                    selectBoxHeight:200,
                    textField:'text',
                    valueField:'id',
                    valueFieldID:'countryId',
                    treeLeafOnly:true,
                    tree:{
                        data:treeData,
                        checkbox:false
                    }
//                    onSelected:function(data){
//                        if(data!='')initTownCombox(data);
//                    }
                });
                if ('${indoorManual.countryId}' != "") {
                    treeCombox.setValue('${indoorManual.countryId}');
                    if('${addFlag==0}'){
                        treeCombox.setDisabled(true);
                    }
                }
            });


            ///乡镇行政区域
            var treeCombox;
            //初始化地市
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
                var townCountryId='${indoorManual.wyLibVillage.countryId}';
                if(townCountryId!=''){
                    treeCombox.selectValue(townCountryId);
                }
                if('${indoorManual.town}'!=''){
                    townCombox.selectValue('${indoorManual.town}');
                }
                var village='${indoorManual.village}';
                if(village!=''){
                    countryCombox.selectValue(village);
                }
            });

            //初始化主设备安装位置
            var typeURL1 = "${ctx}/schooljson/schooljson/cons.action?groupCode=NATURE1";
            var sl1;
            var sl2 = null;

            $.getJSON(typeURL1,
                    function (data) {
                        sl1 = $("#prop1").ligerComboBox({
                            data:data.Rows,
                            width:200,
                            selectBoxWidth:200,
                            textField:'name',
                            valueField:'code',
                            valueFieldID:'nature11'
                        });
                        if (sl1 && '${prop1}' != "") {
                            sl1.setValue('${prop1}');
                        }
                    }

            );

             sl2 = $("#prop2").ligerComboBox({
                                data:null,
                                width:200,
                                selectBoxWidth:200,
                                textField:'name',
                                valueField:'code',
                                valueFieldID:'nature2'
                            });

            function cons() {
                //初始化主设备安装位置
                var typeURL1 = "${ctx}/schooljson/schooljson/cons.action?groupCode=NATURE2&pid=" + pid;
                $.getJSON(typeURL1,
                        function (data) {
                            sl2.setData(data.Rows);
                            if (sl2 && '${prop2}' != "") {
                                sl2.setValue('${prop2}');
                            }
                        }

                );
            }

            $("#prop1").change(function () {
                if ($("#nature11").val()) {
                    pid = $("#nature11").val();
                }
                if (pid) {
                    cons();
                }
            });


            if ("${indoorManual.monitorflag==null?"":indoorManual.monitorflag}" != "") {
                var pp = "${indoorManual.monitorflag}";
                $("#monitorFlag option[value='" + pp + "']").attr("selected", "selected");
            }

            if ("${indoorManual.motorflag==null?"":indoorManual.motorflag}" != "") {
                var pp = "${indoorManual.motorflag}";
                $("#motorflag option[value='" + pp + "']").attr("selected", "selected");
            }

            if ("${indoorManual.wlanflag==null?"":indoorManual.wlanflag}" != "") {
                var pp = "${indoorManual.wlanflag}";
                $("#wlanflag option[value='" + pp + "']").attr("selected", "selected");
            }

            if ("${indoorManual.wlanshare==null?"":indoorManual.wlanshare}" != "") {
                var pp = "${indoorManual.wlanshare}";
                $("#wlanshare option[value='" + pp + "']").attr("selected", "selected");
            }


            $("#constructionDate").ligerDateEditor({ label: '', format: "yyyy.MM", labelAlign: 'right',initValue: '${indoorManual.constructiondate}',width : 200});


            townCombox=$("#town").ligerComboBox( {
                data : null,
                width : 200,
                selectBoxWidth : 200,
                selectBoxHeight : 200,
                textField : 'TOWN',
                valueField : 'TOWN',
                valueFieldID : 'indoorManual.town',
                onSelected:function(data){
                    var countryId=$("#townCountryId").val();
                    if(countryId!=''){
                        initVillageCombox(countryId,data);
                    }
                }
            });
            //农村
            countryCombox=$("#village").ligerComboBox( {
                data : null,
                width : 200,
                selectBoxWidth : 200,
                selectBoxHeight : 200,
                textField : 'village',
                valueField : 'village',
                valueFieldID : 'indoorManual.village'
            });

            //场景库选择弹出框
            //传输上游节点，弹出选择框
            $("#sceneVal").ligerComboBox({
                onBeforeOpen: showDilog,
                valueFieldID: 'scene',
                width: 200
            });
        })


        function initTownCombox(countryId){
            var url="${ctx}/schooljson/getTownList.action?countryId="+countryId;
            url=encodeURI(url);
            //初始化树控件
            $.post(url, function(
                    data, status) {
                liger.get("town").setData(data.Rows);
            });
        }

        //初始化乡镇库
        function initVillageCombox(countryId,town){
            var url="${ctx}/schooljson/getVillageLib.action?countryId="+countryId+"&town="+town;
            url=encodeURI(url);
            //初始化树控件
            $.post(url, function(
                    data, status) {
                liger.get("village").setData(data.Rows);
            });
        }

        function showDilog(){
            $.ligerDialog.open({
                height: 512,
                url: '${ctx}/school/seeneDialog.action?cityIds=${indoorManual.cityId}',
                width: 1075,
                name: 'columns',
                title: "选择场景库",
                showMax: true,
                showToggle: true,
                showMin: true,
                isResize: true,
                isHidden: false
            });
        }
    </script>
</head>
<body data-spy="scroll" data-target=".subnav" data-offset="50">
<div id="main_2">
<!-- 标题 end-->
<div class="main_title_2">
    <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>
        室分数据录入
    </p>
</div>
<div class="content">
<div class="tabbable tabs-left">
<ul class="nav nav-tabs nav-stacked" id="mytab">
    <li class="active"><a id="tabs1" href="#tab1" data-toggle="tab">室分录入</a></li>
    <li><a id="tabs2" href="#tab2" data-toggle="tab">直放站录入</a></li>
    <li><a id="tabs3" href="#tab3" data-toggle="tab">干放站录入</a></li>
</ul>
<div class="tab-content">
<div class="tab-pane active" id="tab1">
<form id="addIndoorManual" style="padding-bottom: 15px;padding-top: 20px;"
      action="${ctx}/businessjson/addIndoorManual.action"
      method="post">
<input type="hidden" id="intId" name="indoorManual.intId" value="${indoorManual.intId}">
<input type="hidden" name="indoorManual.addFlag" value="${addFlag}">

<table cellpadding="0" cellspacing="0" class="tab_1">
<tr>
    <td width="20%"><span style="color: red;">*</span>室内分布小区名称：</td>
    <td width="30%"><input type="text" id="name" name="indoorManual.name"
                           value="${indoorManual.name}" ${addFlag==0?"readonly":''} class="input150 required"></td>
    <td width="20%"><span style="color: red;">*</span>区县：</td>
    <td width="30%">
        <input type="text" id="countryIdVal" class="required">
        <input type="hidden" id="countryId" name="indoorManual.countryId">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>乡镇行政区域:</td>
    <td>
        <div style="float: left">
            <input id="townCountryIdVal" type="text" class="required"/>
            <input id="townCountryId" type="hidden"/>
        </div>
    </td>
    <td><span style="color: red;">*</span>所属乡镇：</td>
    <td>
        <input type="text" id="town" class="required">
    </td>
</tr>
<tr>
    <td>
        覆盖农村
    </td>
    <td>
        <input type="text" id="village" class="required">
    </td>
    <td>场景名称：</td>
    <td>
        <input type="text" id="sceneVal" value="${indoorManual.sceneLibNames}">
        <input type="hidden" id="scene" name="indoorManual.sceneLibs" value="${indoorManual.sceneLibs}">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>经度：</td>
    <td>
        <input type="text" id="longitude" name="indoorManual.longitude"
               value="${indoorManual.longitude}" ${addFlag==0?"readonly":''} class="input150 required">
    </td>
    <td><span style="color: red;">*</span>纬度：</td>
    <td>
        <input type="text" id="latitude" name="indoorManual.latitude"
               value="${indoorManual.latitude}" ${addFlag==0?"readonly":''} class="input150 required">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>信源设备类型：</td>
    <td>
        <input type="text" id="vendorBtstype" name="indoorManual.vendorBtstype"
               value="${indoorManual.vendorBtstype}" ${addFlag==0?"readonly":''} class="input150 required">
    </td>
    <td><span style="color: red;">*</span>对应基站所属BSC：</td>
    <td>
        <input type="text" id="bscName" name="indoorManual.bscName"
               value="${indoorManual.bscName}" ${addFlag==0?"readonly":''} class="input150 required">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>对应基站名称：</td>
    <td>
        <input type="text" id="btsName" name="indoorManual.btsName"
               value="${indoorManual.btsName}" ${addFlag==0?"readonly":''} class="input150 required">
    </td>
    <td><span style="color: red;">*</span>小区序号：</td>
    <td>
        <input type="text" id="cellSeq" name="indoorManual.cellSeq"
               value="${indoorManual.cellSeq}" ${addFlag==0?"readonly":''} class="input150 required">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>PN：</td>
    <td>
        <input type="text" id="pn" name="indoorManual.pn"
               value="${indoorManual.pn}" ${addFlag==0?"readonly":''} class="input150 required">
    </td>
    <td><span style="color: red;">*</span>CI：</td>
    <td>
        <input type="text" id="ci" name="indoorManual.ci"
               value="${indoorManual.ci}" ${addFlag==0?"readonly":''} class="input150 required"/>
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>站点性质一：</td>
    <td>
        <input type="text" id="prop1" name="indoorManual.prop1" class="required"/>
    </td>
    <td><span style="color: red;">*</span>站点性质二：</td>
    <td>
        <input type="text" id="prop2" name="indoorManual.prop2"
               class="required">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>站点分级：</td>
    <td>
        <input type="text" id="grade" name="indoorManual.grade" value="${indoorManual.grade}" class="input150 required">
    </td>
    <td><span style="color: red;">*</span>覆盖范围描述：</td>
    <td>
        <input type="text" id="coverage" name="indoorManual.coverage" value="${indoorManual.coverage}"
               class="input150 required"/>
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>楼宇数量：</td>
    <td>
        <input type="text" id="buildingNum" name="indoorManual.buildingnum" value="${indoorManual.buildingnum}"
               class="input150 required number">
    </td>
    <td><span style="color: red;">*</span>信源设备数量：</td>
    <td>
        <input type="text" id="deviceNum" name="indoorManual.devicenum" value="${indoorManual.devicenum}"
               class="input150 required number">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>详细地址：</td>
    <td>
        <input type="text" id="address" name="indoorManual.address" value="${indoorManual.address}" class="input150 required">
    </td>
    <td><span style="color: red;">*</span>信源有无监控：</td>
    <td>
        <select id="monitorflag" name="indoorManual.monitorflag" class="input150 required">
            <option value="有">有</option>
            <option value="无" selected>无</option>
        </select>
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>监控号码：</td>
    <td>
        <input type="text" id="monitorNumber" name="indoorManual.monitornumber" value="${indoorManual.monitornumber}"
               class="input150 required">
    </td>
    <td><span style="color: red;">*</span>信源设备安装位置：</td>
    <td>
        <input type="text" id="deviceAddress" name="indoorManual.deviceaddress" value="${indoorManual.deviceaddress}"
               class="input150 required">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>直放站数量：</td>
    <td>
        <input type="text" id="repeaterNum" name="indoorManual.repeaternum" value="${indoorManual.repeaternum}"
               class="input150 required number">
    </td>
    <td><span style="color: red;">*</span>干放设备数量：</td>
    <td>
        <input type="text" id="dryNum" name="indoorManual.drynum" value="${indoorManual.drynum}" class="input150 required number">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>分布系统共建共享情况：</td>
    <td>
        <input type="text" id="distributeDesc" name="indoorManual.distributedesc" value="${indoorManual.distributedesc}"
               class="input150 required">
    </td>
    <td><span style="color: red;">*</span>分布系统集成厂家：</td>
    <td>
        <input type="text" id="distributeFac" name="indoorManual.distributefac" value="${indoorManual.distributefac}"
               class="input150 required">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>有无机房：</td>
    <td>
        <select id="motorflag" name="indoorManual.motorflag" class="input150 required">
                  <option value="有">有</option>
                  <option value="无" selected>无</option>
              </select>
    </td>
    <td><span style="color: red;">*</span>机房面积：</td>
    <td>
        <input type="text" id="motorArea" name="indoorManual.motorarea" value="${indoorManual.motorarea}"
               class="input150 required number">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>机房产权：</td>
    <td>
        <input type="text" id="motorRight" name="indoorManual.motorright" value="${indoorManual.motorright}"
               class="input150 required">
    </td>
    <td><span style="color: red;">*</span>对应信源设备的电表安装位置：</td>
    <td>
        <input type="text" id="meterAddress" name="indoorManual.meteraddress" value="${indoorManual.meteraddress}"
               class="input150 required">
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>建设时间（年月）：</td>
    <td>
        <input type="text" name="indoorManual.constructiondate" class="Wdate input150 required" onFocus="WdatePicker({dateFmt: 'yyyy.MM'})" value="${indoorManual.constructiondate}"/>
    </td>
    <td><span style="color: red;">*</span>是否有WLAN：</td>
    <td>
        <select id="wlanflag" name="indoorManual.wlanflag" class="input150 required">
            <option value="有">有</option>
            <option value="无" selected>无</option>
        </select>
    </td>
</tr>
<tr>
    <td><span style="color: red;">*</span>WLAN与C网是否共享分布系统：</td>
    <td>
        <select id="wlanshare" name="indoorManual.wlanshare" class="input150 required">
                   <option value="是">是</option>
                   <option value="否" selected>否</option>
               </select>
    </td>
    <td><span style="color: red;">*</span>WLAN覆盖范围描述：</td>
    <td>
        <input type="text" id="wlanCoverage" name="indoorManual.wlancoverage" value="${indoorManual.wlancoverage}"
               class="input150 required">
    </td>
</tr>
<tr>

    <td><span style="color: red;">*</span>业主联系人：</td>
    <td>
        <input type="text" id="ownerName" name="indoorManual.ownername" value="${indoorManual.ownername}"
               class="input150 required">
    </td>
    <td><span style="color: red;">*</span>业主联系电话：</td>
    <td>
        <input type="text" id="ownerPhone" name="indoorManual.ownerphone" value="${indoorManual.ownerphone}"
               class="input150 required">
    </td>
</tr>
<tr>

    <td><span style="color: red;">*</span>室分监控设备数量：</td>
    <td>
        <input type="text" id="monitorDeviceNum" name="indoorManual.monitordevicenum"
               value="${indoorManual.monitordevicenum}" class="input150 required number">
    </td>
    <td><span style="color: red;">*</span>室分监控设备ID：</td>
    <td>
        <input type="text" id="monitordeviceids" name="indoorManual.monitordeviceids"
               value="${indoorManual.monitordeviceids}" class="input150 required">
    </td>
</tr>
<tr>
    <td>备注：</td>
    <td>
        <input type="text" id="remark" name="indoorManual.remark" value="${indoorManual.remark}" class="input150">
    </td>
</tr>
</table>

<div class="form-actions_2">
    <button class="btn btn-info" type="submit" id="submit_action"><i class="icon-ok icon-white"></i>确认</button>
    <button class="btn btn-info" type="reset" onclick="javascript:history.back(-1);"><i class="icon-repeat"></i>返回
    </button>
</div>
</form>
</div>
<div class="tab-pane" id="tab2" style="overflow-x:hidden;overflow-y:hidden">
    <form id="addErectstation" style="padding-bottom: 0px;padding-top: 20px;"
          action="${ctx}/businessjson/businessjson/ereAdd.action"
          method="post">
        <input type="hidden" id="id" name="erectStation.id" value="">
        <input type="hidden" name="erectStation.wybtsintid" value="${indoorManual.intId}">


        <table cellpadding="0" cellspacing="0" class="tab_1">
            <tr>
                <td width="20%"><span style="color: red;">*</span>室内分布站点名称：</td>
                <td width="30%">
                     <input type="text" id="wybtsintname"
                                     name="erectStation.wybtsname" readonly="readonly"
                                     class="input150 required" value="${indoorManual.name}">
                </td>
                <td width="20%"><span style="color: red;">*</span>本地网：</td>
                <td width="30%">
                     <input type="text" id="cityName" readonly="readonly">
                </td>
            </tr>
             <tr>
                <td><span style="color: red;">*</span>所带直放站编号：</td>
                <td>
                      <input type="text" id="no" name="erectStation.no" value="0" class="required number input150">
                </td>
                <td><span style="color: red;">*</span>直放站类型：</td>
                <td>
                      <input type="text" id="type" name="erectStation.type" class="required">
                </td>
            </tr>
               <tr>
                <td><span style="color: red;">*</span>直放站型号：</td>
                <td>
                       <input type="text" id="model" name="erectStation.model" value="" class="required input150">
                </td>
                <td><span style="color: red;">*</span>直放站厂家：</td>
                <td>
                      <input type="text" id="fac" name="erectStation.fac"
                                         value="" class="required input150">
                </td>
            </tr>
              <tr>
                <td><span style="color: red;">*</span>直放站安装位置：</td>
                <td>
                         <input type="text" id="ere_address" name="erectStation.address" value="" class="required input150">
                </td>
                <td><span style="color: red;">*</span>直放站下接室分系统覆盖范围：</td>
                <td>
                       <input type="text" id="ere_coverage" name="erectStation.coverage" value="" class="required input150">
                </td>
            </tr>
             <tr>
                <td><span style="color: red;">*</span>有无监控：</td>
                <td>
                       <input type="text" id="monitorFalg" name="erectStation.monitorfalg" value="" class="required input150">
                </td>
                <td><span style="color: red;">*</span>监控号码：</td>
                <td>
                    <input type="text" id="ere_monitorNumber" name="erectStation.monitornumber" value="" class="required input150">
                </td>
            </tr>
              <tr>
                <td><span style="color: red;">*</span>电表安装位置：</td>
                <td>
                   <input type="text" id="ere_meterAddress" name="erectStation.meteraddress" value="" class="required input150">
                </td>
                <td>备注：</td>
                <td>
                     <input type="text" id="ere_remark" name="erectStation.remark" class="input150">
                </td>
            </tr>
        </table>

        <div class="form-actions_2">
            <button class="btn btn-info" type="submit" id="submit"><i class="icon-ok icon-white"></i>保存</button>
            <button class="btn btn-info" type="reset" onclick="javascript:history.back(-1);"><i class="icon-repeat"></i>返回
            </button>
        </div>
    </form>
    <div id="toptoolbar"></div>
    <div id="maingrid" style="min-width: 380px;"></div>
</div>
<div class="tab-pane" id="tab3" style="overflow-x:hidden;overflow-y:hidden ">
    <form id="addDrytation" style="padding-bottom: 0px;padding-top: 20px;"
          action="${ctx}/businessjson/businessjson/dryAdd.action" method="post">
        <input type="hidden" id="dry_id" name="dryStation.id" value="">

        <table cellpadding="0" cellspacing="0" class="tab_1">
            <tr>
                <td width="20%"><span style="color: red;">*</span>室内分布站点名称：</td>
                <td width="30%">
                    <input type="text" id="dry_wybtsintname"
                           name="dryStation.wybtsname" readonly="readonly" class="input150 required"
                           value="${indoorManual.name}">
                    <input type="hidden" id="dry_wybtsintid" name="dryStation.wybtsintid"
                           value="${indoorManual.intId}">
                </td>
                <td width="20%"><span style="color: red;">*</span>本地网：</td>
                <td width="30%">
                    <input type="text" id="dry_cityName" name="cityName" class="required"
                           readonly="readonly">
                </td>
            </tr>
            <tr>
                <td><span style="color: red;">*</span>所带干放站编号：</td>
                <td>
                    <input type="text" id="dry_no" name="dryStation.no" value="0" class="input150 required number">
                </td>
                <td><span style="color: red;">*</span>干放上级设备（归属）：</td>
                <td>
                    <input type="text" id="parentDevice" name="dryStation.parentdevice" class="input150 required">
                </td>
            </tr>
            <tr>
                <td><span style="color: red;">*</span>干放站型号：</td>
                <td>
                    <input type="text" id="dry_model" name="dryStation.model" value="" class="input150 required">
                </td>
                <td><span style="color: red;">*</span>干放站厂家：</td>
                <td>
                    <input type="text" id="dry_fac" name="dryStation.fac"
                           value="" class="input150 required">
                </td>
            </tr>
            <tr>
                <td><span style="color: red;">*</span>干放站安装位置：</td>
                <td>
                    <input type="text" id="dry_address" name="dryStation.address" value="" class="input150 required">
                </td>
                <td><span style="color: red;">*</span>干放站下接室分系统覆盖范围：</td>
                <td>
                    <input type="text" id="dry_coverage" name="dryStation.coverage" value="" class="input150 required">
                </td>
            </tr>

            <tr>
                <td><span style="color: red;">*</span>有无监控：</td>
                <td>
                    <input type="text" id="dry_monitorFlag" name="dryStation.monitorflag" value=""
                           class="input150 required">
                </td>
                <td><span style="color: red;">*</span>监控号码：</td>
                <td>
                    <input type="text" id="dry_monitorNumber" name="dryStation.monitornumber" value=""
                           class="input150 required">
                </td>
            </tr>
            <tr>
                <td><span style="color: red;">*</span>电表安装位置：</td>
                <td>
                    <input type="text" id="dry_meterAddress" name="dryStation.meteraddress" value=""
                           class="input150 required">
                </td>
                <td>备注：</td>
                <td>
                    <input type="text" id="dry_remark" name="dryStation.remark" class="input150">
                </td>
            </tr>
        </table>
        <div class="form-actions_2">
            <button class="btn btn-info" type="submit" id="dry_submit"><i class="icon-ok icon-white"></i>保存</button>
            <button class="btn btn-info" type="reset" onclick="javascript:history.back(-1);"><i class="icon-repeat"></i>返回
            </button>
        </div>
    </form>
    <div id="toptoolbar2"></div>
    <div id="maingrid2" style="min-width: 400px;"></div>
</div>
</div>
</div>
</div>

<script type="text/javascript">

var repeatNum = "${indoorManual.repeaternum==null?"":indoorManual.repeaternum}";
var dryNum = "${indoorManual.drynum==null?"":indoorManual.drynum}";


var cityName;
var gridObj = null;
var zfTypeCombox;
$(function () {


    $("a[data-toggle=tab]").on("shown", function() {
        if (gridObj != null) {
            gridObj.loadData();
        }
        if (gridObj2 != null) {
            gridObj2.loadData();
        }
    })


    $.post("${ctx}/schooljson/schooljson/initCityTree.action", function (ajaxData, status) {
        var treeData = ajaxData.cityJson;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData = eval('(' + treeData + ')');
        cityName = $("#cityName").ligerComboBox({
            width:200,
            selectBoxWidth:200,
            selectBoxHeight:200,
            textField:'text',
            valueField:'id',
            valueFieldID:'erectStation.cityId',
            treeLeafOnly:false,
            tree:{
                data:treeData
            }
        });
    });

    //直放站类型
    zfTypeCombox = $("#type").ligerComboBox({
        data: [
            { text: '光纤', id: '光纤' },
            { text: '无线', id: '无线' },
            { text: '移频', id: '移频' }
        ],
        width : 200,
        selectBoxWidth: 200,
        valueFieldID: 'type11'
    });


    var v = $("#addIndoorManual").validate({
        debug:false,
        submitHandler:function (form) {
            jQuery(form).ajaxSubmit(function(json) {
                var intId = json.intId;
                if (json.result == 1) {
                    alert('操作成功!');
                } else {
                    alert('操作失败!');
                }
                window.location.href = "${ctx}/business/indoorInput.action?intId=" + intId;
            });
        }
    });
    var clicks = false;
    $("#tabs2").click(function () {
        if (repeatNum == "") {
            $("#tabs1").click();
            clicks = true;
            $.ligerDialog.error("请先录入室分录入中直放站数量！");
            setTimeout(function () {
                $(".l-dialog-btn-inner").click();
                $("#tabs1").click();
            }, 1000);
        } else {
            if (gridObj == null) {
                tabsGird();
            }

            if ('${indoorManual.cityId}' != "") {
                cityName.setValue('${indoorManual.cityId}');
                cityName.setDisabled(true);
            }
        }
    })


    var v2 = $("#addErectstation").validate({
        debug:false,
        submitHandler:function (form) {
            jQuery(form).ajaxSubmit({
                success:function (responseText, statusText, xhr, form) {
                    var succe = responseText;
                    if (succe) {
                        $.ligerDialog.alert("操作成功!");
                        gridObj.loadData();
                        $("#id").val("");
                    } else {
                        $.ligerDialog.error("操作失败！");
                    }
                    setTimeout(function () {
                        $(".l-dialog-btn-inner").click();
                    }, 2000);
                }
            });
        }
    });
})
var rowDomElemen1;

function doit() {
    var rows = gridObj.getCheckedRows();
    var j = rows.length;
    var str = "";
    $(rows).each(function () {
        str = this.id;
    });
    if (j == 1) {
        $("#id").val(str);
    } else {
        $.ligerDialog.alert("只能勾选-条");
    }
}

var toptoolbar;
function tabsGird() {
    $(function () {
        //toptoolbar
        if (!toptoolbar) {
            toptoolbar = $("#toptoolbar").ligerToolBar({
                items:[
                    { text:'录入', click:add, icon:'add'},
                    { text:'编辑', click:edit, icon:'modify'},
                    { text:'删除', click:del, icon:'delete'}
                ]
            });
        }
        gridObj = $("#maingrid").ligerGrid({
            columns:[
                {display:'室内分布站点名称', name:'wybtsname', width:120, align:'center'},
                {display:'本地网', name:'country', width:80, align:'center',
                    render:function (row) {
                        if (row.country) {
                            return row.country.cityName;
                        } else {
                            return "";
                        }
                    }
                },
                {display:'所带直放站编号', name:'no', width:110, align:'center'},
                {display:'直放站类型', name:'type', width:80, align:'center'},
                {display:'直放站型号', name:'model', width:70, align:'center'} ,
                {display:'直放站厂家', name:'fac', width:70, align:'center'},
                {display:'直放站安装位置', name:'address', width:120, align:'center'},
                {display:'直放站下接室分系统覆盖范围', name:'coverage', width:180, align:'center'},
                {display:'有无监控', name:'monitorfalg', width:60, align:'center'},
                {display:'监控号码', name:'monitornumber', width:60, align:'center'},
                {display:'电表安装位置', name:'meteraddress', width:90, align:'center'},
                {display:'备注', name:'remark', width:60, align:'center'},
                {display:'更新时间', name:'updatetimeToString', width:130, align:'center'}
            ],
            rownumbers:true,
            showTitle:false,
            pageSize:50,
            pageSizeOptions:[50, 100],
            url:'${ctx}/businessjson/businessjson/ereData.action?intId=${indoorManual.intId}',
            checkbox:true,
            width:'100%',
            height:300,
            onDblClickRow:function (rowdata, rowindex, rowDomElement) {
                rowDomElemen1 = rowDomElement;
                $("#model").val(rowdata.model);
                $("#no").val(rowdata.no);
                $("#fac").val(rowdata.fac);
                $("#id").val(rowdata.id);
                zfTypeCombox.setValue(rowdata.type);
                $("#ere_address").val(rowdata.address);
                $("#ere_coverage").val(rowdata.coverage);
                $("#ere_remark").val(rowdata.remark);
                $("#ere_meterAddress").val(rowdata.meteraddress);
                $("#ere_monitorNumber").val(rowdata.monitornumber);
                $("#monitorFalg").val(rowdata.monitorfalg);
            }
        });
        $("#pageloading").hide();
    });

    //添加页面
    function add() {
        $("#model").val("");
        $("#id").val("");
        $("#no").val("");
        $("#fac").val("");
        $("#ere_address").val("");
        $("#ere_coverage").val("");
        $("#ere_remark").val("");
        $("#ere_meterAddress").val("");
        $("#ere_monitorNumber").val("");
        $("#monitorFalg").val("");
    }

    //编辑页面
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
        $(rows).each(function () {
            id = this.id;
            $("#wybtsintname").val(this.wybtsname);
            $("#id").val(id);
            cityName.setValue(this.cityId);
            $("#model").val(this.model);
            $("#fac").val(this.fac);
            $("#no").val(this.no);
            zfTypeCombox.setValue(this.type);
            $("#ere_address").val(this.address);
            $("#ere_coverage").val(this.coverage);
            $("#ere_remark").val(this.remark);
            $("#ere_meterAddress").val(this.meteraddress);
            $("#ere_monitorNumber").val(this.monitornumber);
            $("#monitorFalg").val(this.monitorfalg);
        });
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
                ids:str
            };
            $.getJSON('${ctx}/businessjson/businessjson/delete.action', params, function (json) {
                if (json.result) {
                    $.ligerDialog.alert('删除成功!');
                } else {
                    $.ligerDialog.alert('删除失败！');
                }
                gridObj.loadData();
            });
        });
    }

}
var gridObj2 = null;
var cityName2;
$(function () {
    $.post("${ctx}/schooljson/schooljson/initCityTree.action", function (ajaxData, status) {
        var treeData = ajaxData.cityJson;
        treeData = treeData.replace(/"children":\[\],/g, '');
        treeData = eval('(' + treeData + ')');
        cityName2 = $("#dry_cityName").ligerComboBox({
            width:200,
            selectBoxWidth:200,
            selectBoxHeight:200,
            textField:'text',
            valueField:'id',
            valueFieldID:'dryStation.cityId',
            treeLeafOnly:false,
            tree:{
                data:treeData
            }
        });
    });
    var v3 = $("#addDrytation").validate({
        debug:false,
        submitHandler:function (form) {
            jQuery(form).ajaxSubmit({
                success:function (responseText, statusText, xhr, form) {
                    var succe = responseText;
                    if (succe) {
                        $.ligerDialog.alert("操作成功!");
                        gridObj2.loadData();
                        $("#dry_id").val("");
                    } else {
                        $.ligerDialog.error("操作失败！");
                    }
                    setTimeout(function () {
                        $(".l-dialog-btn-inner").click();
                    }, 2000);
                }
            });
        }
    });
})
$("#tabs3").click(function () {
    if (dryNum == "") {
        $("#tabs1").click();
        $.ligerDialog.error("请先录入室分录入中干放设备数量！");
        setTimeout(function () {
            $(".l-dialog-btn-inner").click();
            $("#tabs1").click();
        }, 1000);
    } else {
        if (gridObj2 == null) {
            tabsGird2();
        }
        if ('${indoorManual.cityId}' != "") {
            cityName2.setValue('${indoorManual.cityId}');
            cityName2.setDisabled(true);
        }
    }
})
var rowDomElemen1;
function tabsGird2() {
    $(function () {
        //toptoolbar
        $("#toptoolbar2").ligerToolBar({
            items:[
                { text:'录入', click:add2, icon:'add'},
                { text:'编辑', click:edit2, icon:'modify'},
                { text:'删除', click:del2, icon:'delete'}
            ]
        });

        gridObj2 = $("#maingrid2").ligerGrid({
            columns:[
                {display:'室内分布站点名称', name:'wybtsname', width:120, align:'center'},
                {display:'本地网', name:'country', width:80, align:'center',
                    render:function (row) {
                        if (row.country) {
                            return row.country.cityName;
                        } else {
                            return "";
                        }
                    }
                },
                {display:'所带干放站编号', name:'no', width:110, align:'center'},
                {display:'干放上级设备（归属）', name:'parentdevice', width:130, align:'center'},
                {display:'干放站型号', name:'model', width:70, align:'center'} ,
                {display:'干放站厂家', name:'fac', width:70, align:'center'},
                {display:'干放站安装位置', name:'address', width:120, align:'center'},
                {display:'干放站下接室分系统覆盖范围', name:'coverage', width:180, align:'center'},
                {display:'有无监控', name:'monitorflag', width:60, align:'center'},
                {display:'监控号码', name:'monitornumber', width:60, align:'center'},
                {display:'电表安装位置', name:'meteraddress', width:90, align:'center'},
                {display:'备注', name:'remark', width:60, align:'center'},
                {display:'更新时间', name:'updatetimeToString', width:130, align:'center'}
            ],
            rownumbers:true,
            showTitle:false,
            pageSize:50,
            pageSizeOptions:[50, 100],
            url:'${ctx}/businessjson/businessjson/dryData.action?intId=${indoorManual.intId}',
            checkbox:true,
            width:'100%',
            height:300,
            onDblClickRow:function (rowdata, rowindex, rowDomElement) {
                rowDomElemen1 = rowDomElement;
                $("#dry_model").val(rowdata.model);
                $("#dry_no").val(rowdata.no);
                $("#dry_fac").val(rowdata.fac);
                $("#dry_id").val(rowdata.id);
                $("#parentDevice").val(rowdata.parentdevice);
                $("#dry_address").val(rowdata.address);
                $("#dry_coverage").val(rowdata.coverage);
                $("#dry_remark").val(rowdata.remark);
                $("#dry_meterAddress").val(rowdata.meteraddress);
                $("#dry_monitorNumber").val(rowdata.monitornumber);
                $("#dry_monitorFlag").val(rowdata.monitorflag);
            }
        });
        $("#pageloading").hide();
    });

    //添加页面
    function add2() {
        $("#dry_model").val("");
        $("#dry_id").val("");
        $("#dry_no").val("");
        $("#dry_fac").val("");
        $("#dry_id").val("");
        $("#dry_address").val("");
        $("#dry_coverage").val("");
        $("#dry_remark").val("");
        $("#dry_meterAddress").val("");
        $("#parentDevice").val("");
        $("#dry_monitorNumber").val("");
        $("#dry_monitorFlag").val("");
    }

    //编辑页面
    function edit2() {
        var rows = gridObj2.getCheckedRows();
        var j = rows.length;
        if (j == 0) {
            $.ligerDialog.alert('请选择要编辑的数据！');
            return;
        } else if (j > 1) {
            $.ligerDialog.alert('请选择一条编辑！');
            return;
        }
        var id;
        $(rows).each(function () {
            id = this.id;
            $("#dry_wybtsintname").val(this.wybtsname);
            cityName2.setValue(this.cityId);
            $("#dry_model").val(this.model);
            $("#dry_fac").val(this.fac);
            $("#dry_id").val(this.id);
            $("#dry_no").val(this.no);
            $("#dry_address").val(this.address);
            $("#dry_coverage").val(this.coverage);
            $("#dry_remark").val(this.remark);
            $("#parentDevice").val(this.parentdevice);
            $("#dry_meterAddress").val(this.meteraddress);
            $("#dry_monitorNumber").val(this.monitornumber);
            $("#dry_monitorFlag").val(this.monitorflag);
        });
    }

    //删除操作
    function del2() {
        var rows = gridObj2.getCheckedRows();
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
                ids:str
            };
            $.getJSON('${ctx}/businessjson/businessjson/deleteDry.action', params, function (json) {
                if (json.result) {
                    $.ligerDialog.alert('删除成功!');
                } else {
                    $.ligerDialog.alert('删除失败！');
                }
                gridObj2.loadData();
            });

        });
    }

}
</script>
</body>
</html>