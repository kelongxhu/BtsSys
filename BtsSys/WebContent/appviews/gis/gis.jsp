<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>物理站点</title>
<%@ include file="/appviews/common/tag.jsp" %>
    <style type="text/css">
        html{height:100%}
        body{height:100%;margin:0px;padding:0px}
        #map_canvas{height:100%;}
        .mouseover{background-color: #E8F8A9;}
    </style>
    <script type="text/javascript" src="http://api.go2map.com/maps/js/api_v2.5.1.js"></script>
    <script type="text/javascript" src="http://api.go2map.com/maps/js/component/textIcon.js"></script>
    <script type="text/javascript" src="http://api.go2map.com/maps/js/component/markerCluster.js"></script>
    <script type="text/javascript" src="http://api.go2map.com/maps/js/component/markerManager.js"></script>
    <script type="text/javascript">
        var map;
        var sRender;
        var markerManager;//标记管理器
        var convertor;//转换器
        var infowindow;//信息窗口
        var markerObjs = new Array();//用于存放所有的标记对象
        var allMarkerData = new Array();//用于存放所有关于标记的数据
        var typeDatas = [{text: '物理站点', id: '1'}, {text: '室分站点', id: '2'}, {text: 'bbu', id: '3'}, {text: '传输节点', id: '4'}];
        var lineDatas = [{text: '拉远施主站点', id: '1'}, {text: '传输拓扑节点', id: '2'}];
        var NO_DATA_FOUND_MSG = '对不起,未搜索到相关数据!';
        var bounds;//定义一个坐标点的范围
        var circle;//定义一个圆
        var fbds;//定义一个视野
        $(function() {
            var treeCombox;
            //初始化地市-树控件
            $.post("${ctx}/schooljson/initCountryTree.action", function(ajaxData, status) {
                var treeData = ajaxData.cityJson;
                treeData = treeData.replace(/"children":\[\],/g, '');
                treeData = eval('(' + treeData + ')');
                treeCombox = $("#cityId").ligerComboBox({
                    width : 140,
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
            $("#searchTypes").ligerComboBox({
                width:120
            });
            $("#name").ligerTextBox({width: 150});
            $("#types").ligerComboBox({
                width: 120,
                selectBoxWidth: 140,
                isShowCheckBox: true,
                isMultiSelect: true,
                data: typeDatas,
                valueFieldID: 'typesVal'
            });
            $("#types").blur();//避免下拉默认显示光标
            $("#lines").ligerComboBox({
                width: 120,
                selectBoxWidth: 140,
                selectBoxHeight: 60,
                isShowCheckBox: true,
                isMultiSelect: true,
                data: lineDatas,
                valueFieldID: 'linesVal'
            });
            $("#lines").blur();//避免下拉默认显示光标
            /*$("#bscName").ligerTextBox({width: 150});
            $("#btsId").ligerTextBox({width: 150});*/

            $("#layout").ligerLayout({//左右布局
                leftWidth: 180,
                isLeftCollapse: true
            });
            //初始化地图及相关组件
            try{
                convertor = new sogou.maps.Convertor();
                infowindow = new sogou.maps.InfoWindow({
                    content: '',
                    title: '站点信息'
                });
                // 加载地图相关
                var myLatlng = new sogou.maps.LatLng(26.56667, 106.71667); // 默认为贵阳
                var myOptions = {
                    'zoom': 11,
                    'center': myLatlng,
                    'mapTypeId': sogou.maps.MapTypeId.ROADMAP,
                    'callback' : function() {
                        //markerToMap(null);
                    }
                };
                map = new sogou.maps.Map(document.getElementById("map_canvas"), myOptions);
            }catch (e){
                alert("不能访问，请检查是否访问外网");
            }
        });

        /**
         * 新构建一个标记管理器
         * @param markerObjs 需要被管理的标记对象集
         */
        function newMarkerManager(markerObjs){
            if(markerObjs == null || markerObjs == undefined){
                markerObjs = [];
            }
            var o={'map':map,markers:markerObjs,averageCenter:1, gridSize: 25, maxZoom: 15};
            markerManager = new MarkerManager(o);
        }


        function clearMark(makerObjs){
            if(markerManager!=null)markerManager.delMarkers(makerObjs);
            for(var i=0;i<makerObjs.length;i++)
            {
                makerObjs[i].setMap(null);
            }
        }

        /**
         * 将标记渲染到地图上
         * @param markers 加载的标记数据
         */
        function markerToMap(markers){
                if(markers){
                    for(var markerKey in markers){
                        var markerDatas = markers[markerKey];
                        if(markerDatas){
                            var markerType;
                            for(var name in markerDatas){
                                var markerData = markerDatas[name];
                                if(markerData){
                                    if(markerKey == 'bts'){
                                        var isIndoor = markerData['indoor'];
                                        markerType = isIndoor == '否' ? 1 : 2;//1：物理站点 2：室分
                                        // 关联相关的站点(拉远和施主站点/传输拓扑节点)
                                        var linesVal = $("#linesVal").val();
                                        if(linesVal){
                                            var lineArray = linesVal.split(",");
                                            for(var i in lineArray) associateRelatedMarker(markerData, lineArray[i]);
                                        }
                                    }else if(markerKey == 'bbu') markerType = 3;
                                    else if(markerKey == 'transfer') markerType = 4;
                                    //todo,是否在矩形框内的数据才渲染出来
                                    var marker = markerProcessor(markerData, markerType);
                                    if(marker) markerObjs.push(marker);
                                }
                            }
                        }
                    }
                }
                addMarkers();
        }



        /**
         * 将在范围的标记渲染到地图上
         * @param markers 加载的标记数据
         */
        function getCircleMarkerToMap(markers){
            if(markers){
                for(var markerKey in markers){
                    var markerDatas = markers[markerKey];
                    if(markerDatas){
                        var markerType;
                        var i=0;//定义抛弃数
                        var j=0;//定义显示数
                        for(var name in markerDatas){
                            var markerData = markerDatas[name];
                            //获取经度纬度
                            var longitude = markerData['longitude'];
                            var latitude = markerData['latitude'];
                            var sogouCoord=convertor.toSogou(new sogou.maps.LatLng(latitude,longitude));
                            if(!bounds.contains(sogouCoord)){
                                   i++;
                                  continue;
                            }
                           // fbds.extend(sogouCoord);//放到视野中
                            if(markerData){
                                if(markerKey == 'bts'){
                                    var isIndoor = markerData['indoor'];
                                    markerType = isIndoor == '否' ? 1 : 2;//1：物理站点 2：室分
                                    // 关联相关的站点(拉远和施主站点/传输拓扑节点)
                                    var linesVal = $("#linesVal").val();
                                    if(linesVal){
                                        var lineArray = linesVal.split(",");
                                        for(var i in lineArray) associateRelatedMarker(markerData, lineArray[i]);
                                    }
                                }else if(markerKey == 'bbu') markerType = 3;
                                else if(markerKey == 'transfer') markerType = 4;
                                //todo,是否在矩形框内的数据才渲染出来
                                j++;
                                var marker = markerProcessor(markerData, markerType);
                                if(marker) markerObjs.push(marker);
                            }
                        }
//                        alert(markerKey+",i="+i);
//                        alert(markerKey+",j="+j);
                    }
                }
            }
            addMarkers();
            $("#mainGis").unmask();
        }

        /**
         * 添加标记到标记管理器里
         */
        function addMarkers(){
            markerManager.addMarkers(markerObjs);
            //markerObjs.length = 0;
        }

        /**
         * 标记处理器
         * @param markerData 标记数据
         * @param markerType 标记类型 （1.物理站点 2.室分站点 3.bbu 4.传输节点）
         */
        function markerProcessor(markerData, markerType){
            var longitude = markerData['longitude'];
            var latitude = markerData['latitude'];
            var alarmName=markerData['alarmName'];
            var marker;
            if(longitude && longitude != null && latitude && latitude != null){
                var icon;
                switch(markerType){
                    case 1:
                        if(alarmName){
                            icon = '../resources/gisResources/img/baseSiteRed.gif';
                        }else{
                            icon = '../resources/gisResources/img/baseSiteBlue.png';
                        }
                        break;//物理基站
                    case 2:
                        if (alarmName) {
                            icon = '../resources/gisResources/img/house_red.png';//室分
                        } else {
                            icon = '../resources/gisResources/img/house_blue.gif';//室分
                        }
                        break;
                    case 3:
                        if(alarmName){
                            icon = '../resources/gisResources/img/bbu_red.gif';//bbu
                        }else{
                            icon = '../resources/gisResources/img/bbu.png';//bbu
                        }
                        break;
                    case 4: icon = null; break;//加图片 默认
                    default: icon = 'http://api.go2map.com/maps/images/v2.0/flag.png'; break;//加图片 旗子
                }
                marker = new sogou.maps.Marker({
                    position: convertor.toSogou(new sogou.maps.LatLng(latitude, longitude)),//注：必须要通过转换器将经纬度转换成相应的物理坐标点，标记管理器才能识别
                    map: null,
                    icon: icon ? getInitMarkerStyle(icon, markerType) : null,
                    title: markerData.name,
                    visible: true,
                    label: {visible:true,align:"BOTTOM"}//是否显示title
                });
                markerData['markerObj'] = marker;
                markerData['markerType'] = markerType;
                allMarkerData.push(markerData);
                addMarkerListener(marker, markerData, markerType);   //添加附属信息
            }
            return marker;
        }

        /**
         * 关联相关的标记（连线）
         * @param markerData 需要连线的标记数据
         * @param type 连线类型 1.施主与拉远 2.传输拓扑节点
         */
        function associateRelatedMarker(markerData, type){
            var latLng;
            var lineColor;
            if(type == '1') {
                latLng = markerData['szLatLng'];
                lineColor = '#FFA500';
            }
            else if(type == '2') {
                latLng = markerData['upLatLng'];
                lineColor = '#FF6EB4';
            }
            if(!latLng) return;//如果没有相应的站点,则不用关联
            var fromLat = latLng['latitude'];
            var fromLng = latLng['longitude'];
            var toLat  = markerData['latitude'];
            var toLng = markerData['longitude'];
            var title = latLng['name'] + '--->' + markerData['name'];
            if(fromLat && fromLng && toLat && toLng){
                var path = [
                    new sogou.maps.LatLng(fromLat, fromLng),//起点坐标
                    new sogou.maps.LatLng(toLat, toLng)//终点坐标
                ];
                var lineStyle1 = {
                    id: 'L01',
                    "v:stroke": {
                        color: lineColor,
                        weight:"3",
                        opacity:"75%"
                    }
                };
                var lineStyle2 = {
                    id: 'L02',
                    "v:stroke": {
                        color:"#FF0000",
                        weight:"6",
                        endcap:"Round",
                        opacity:"75%",
                        endArrow:"Classic",
                        endarrowlength:"long",
                        endarrowwidth:"wide"
                    }
                };
                var polyline = new sogou.maps.Polyline({
                  path: path,
                  title: title,
                  map: map,
                  styles: [lineStyle1, lineStyle2]
                });
            }
        }

        /**
         * 得到Marker样式
         * @param icon 图标地址
         */
        function getInitMarkerStyle(icon, markerType){
            var width = markerType == 2 ? 14 : 16;//室分图标宽度为14
            var height = markerType == 2 ? 18 : 24;//室分图标高度是18
            //定义标记样式
            var markerStyle=new sogou.maps.MarkerImage(icon,
        //    var markerStyle=new sogou.maps.MarkerImage(root + icon,
            // 蓝点图标宽16像素，高14像素
            new sogou.maps.Size(width, height),
            // 原点在图片的(34,88)
            new sogou.maps.Point(0,0),
            // 锚点在图标最下方中心
            new sogou.maps.Point(width/2,height),
            // 合并图片的大小
            new sogou.maps.Size(width, height));
            return markerStyle;
        }

        /**
         * 添加marker事件
         * @param marker 标记对象
         * @param resInfo 标记数据
         * @param markerType 标记类型
         */
        function addMarkerListener(marker, resInfo, markerType) {
            var alarmName=resInfo['alarmName'];
            var alarmMsg=	"<li><span style='width:60px;color:#ff0000;''>告警名称：</span>" +
                    resInfo['alarmName']+"</li>" +
                    "<li><span style='width:60px;color: #ff0000;''>告警级别：</span>" +
                    resInfo["alarmLevel"]+"</li>" +
                    "<li><span style='width:60px;color: #ff0000;''>告警时间：</span>" +
                    resInfo["alarmTime"] +"</li>";
            //////////////////////////////////
            var msg = "<div style='width:300px;height:170px;'>暂未设置额外属性</div>";
            var markerTypeName = getMarkerTypeName(markerType);
            if(markerType == 1 || markerType == 2){
                var cNameTmp = resInfo.cellName;
                var cNameShow = cNameTmp ? cNameTmp.replace(/；/g, '\n\t') : '';
                var szName = resInfo.szGisName == null ? '' : resInfo.szGisName;
                msg = "<div style='width:300px;height:200px;'><ul>" +
                            "<li><span style='width:60px; color: #0000ff;'>类型：</span>" +
                                    markerTypeName +"</li>" +
                            "<li><span style='width:60px; color: #0000ff;'>名称：</span>" +
                                    resInfo.name +"</li>" +
                            "<li><span style='width:60px; color: #0000ff;'>基 站 &nbsp;ID：</span>" +
                                   resInfo.btsId +"</li>" +
                           /* "<li><span style='width:60px; color: #0000ff;'>评&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分：</span>" +
                                   resInfo.totalGrade +"</li>" +*/
                            "<li><span style='width:60px; color: #0000ff;'>施主基站名称：</span>" +
                                   szName +"</li>" +
                            "<li algin='top'><span style='width:60px; color: #0000ff;'>下属小区：</span>" +
                                    "<pre>\t" +    cNameShow +  "</pre></li>" ;
                             if(alarmName){
                                 msg+=alarmMsg;
                             }
                            msg+="</ul></div>";
            }else if(markerType == 3){
                msg = "<div style='width:300px;height:200px;'><ul>" +
                            "<li><span style='width:60px; color: #0000ff;'>类型：</span>" +
                                    markerTypeName +"</li>" +
                            "<li><span style='width:60px; color: #0000ff;'>名称：</span>" +
                                    resInfo.name +"</li>" +
                            "<li><span style='width:60px; color: #0000ff;'>基 站 &nbsp;ID：</span>" +
                                   resInfo.btsId +"</li>" ;
                            if(alarmName){
                                 msg+=alarmMsg;
                                       }
                             msg+="</ul></div>";
            }else if(markerType == 4){
                var btsList = resInfo.btsList;
                var btsStr = '';
                if(btsList){
                    for(var i in btsList){
                        var bts = btsList[i];
                        var name = bts['name'];
                        btsStr += name + ',';
                    }
                }
                if(btsStr) btsStr = btsStr.substring(0, btsStr.length - 1);
                btsStr.replace(/,/g, '\n\t');
                msg = "<div style='width:300px;height:200px;'><ul>" +
                            "<li><span style='width:60px; color: #0000ff;'>类型：</span>" +
                                    markerTypeName +"</li>" +
                            "<li><span style='width:60px; color: #0000ff;'>名称：</span>" +
                                    resInfo.name +"</li>" +
                            "<li><span style='width:60px; color: #0000ff;'>所属类型：</span>" +
                                   resInfo.typeName +"</li>" +
                            "<li algin='top'><span style='width:60px; color: #0000ff;'>下挂基站：</span>" +
                                    "<pre>\t" +    btsStr +  "</pre></li>" +
            //				"<li><span style='width:60px;'>告警：</span>" +
            //                    "<input type='text' value='"+ resInfo.alarms +"' readonly='readonly' /></li>" +
                            "</ul></div>";
            }
            sogou.maps.event.addListener(marker, 'click', function() {
                infowindow.setContent(msg);
                infowindow.open(map,marker);
            });
        }

        /**
         * 查询操作
         */
        function toSearch() {
//            var name=$("#name").val();
           /* var bscName = $("#bscName").val();
            var btsId = $("#btsId").val();*/
            //处理地区
            <%--var cityIdsV = $("#cityIdVal").val().replace(/;/g, ',');--%>
            <%--//标记类型--%>
            <%--var types = $("#typesVal").val();--%>
            <%--var url = "${ctx}/gisjson/loadMarkerByCond";--%>
            <%--var cond = {countryIds: cityIdsV, name: name, types: types};--%>
            <%--loadMarkerByCond(url, cond);--%>


            var searchTypes = $("#searchTypes").val();
            if(searchTypes == '1'){
                seachByAddress();
            }else if(searchTypes == '2'){
                $("#result").css('overflow','hidden');
                var cityIdsV = $("#cityIdVal").val().replace(/;/g, ',');
                //标记类型
                var types = $("#typesVal").val();
                var url = "${ctx}/gisjson/loadMarkerByCond";
                var cond = {countryIds: cityIdsV, name: name, types: types};
                loadMarkerByCond(url, cond);
            }
        }

        function seachByAddress(){
            var name=$("#name").val();
            if(name == '')return;
            if(map!=null)map.clearAll();
            if(circle!=null){
                circle.remove();
            }
            if(sRender!=null)sRender.clearResultFeatures();
            var option={
                'map':map,    //当前使用的地图
                'panel':document.getElementById('result')
            };
            sRender=new sogou.maps.SearchRenderer(option);
            var request={
                'map':map,
                'what':{
                    'keyword':name
                }
            };
            var search=new sogou.maps.Search();//创建搜索实例
            search.search(request);
            search.setRenderer(sRender);
            var infowindow = new sogou.maps.InfoWindow();
            sogou.maps.event.addListener(sRender,'getMarker',function(a,b){
                if(circle!=null){
                    circle.remove();
                }
                var x= a.getPosition().x;
                var y= a.getPosition().y;
                //画圆
                var circleOptions = {
                    strokeColor: "#C8C8C8",
                    strokeOpacity: 0.6,
                    strokeWeight: 1,
                    fillColor: "#FF0000",//FFFFFF,
                    fillOpacity: 0.45,
                    map: map,
                    center: new sogou.maps.Point(x,y),
                    radius: 500
                };
                circle =new sogou.maps.Circle(circleOptions);
                //定义范围
                bounds=convertor.bounds(new sogou.maps.Point(x,y),500);
                fbds=new sogou.maps.Bounds();//定义一个视野
                var cond = {id: a.getFeature().id};
                loadMarkerByCounty(cond);
                infowindow.open(map,a);
//                map.setZoom(13);
               // map.fitBounds(fbds);//调整视野
                var div=document.createElement('div');
                div.innerHTML=b.innerHTML;
                div.style.width='300px';
                div.style.fontSize='12px';
                infowindow.setContent(div);
            });
            $("#result").html("");
            $("#result").css('overflow','auto');
            $.ligerui.get('layout').setLeftCollapse(false);
        }


        function loadMarkerByCounty(cond){
            var types = $("#typesVal").val();
            var url = "${ctx}/gisjson/loadMarkerByCounty";
            $.post(url, cond, function(data){
                if(data["countyId"]==-1){
                    alert("数据返回异常");
                    return;
                }
                var url = "${ctx}/gisjson/loadMarkerByCond";
                var cond = {countryIds: data.countyId};
                loadMarkerByCountyId(url, cond);
            });
        }

        function loadMarkerByCountyId(url, cond){
            $("#mainGis").mask("加载中...");
            //map.clearAll();
            //alert(markerObjs.length);
            clearMark(markerObjs);
            allMarkerData.length = 0;
            markerObjs.length = 0;
            newMarkerManager(null);
            $.post(url, cond, function(data){
                var errMsg = data['errMsg'];
                var centerLongit = data['centerLongit'];
                var centerLatit = data['centerLatit'];
                var markers = data['markers'];
                if(errMsg) alert(errMsg);
                else if(centerLongit && centerLatit){
                   // map.panTo(new sogou.maps.LatLng(centerLatit, centerLongit));
                    //============================todo
                    //判断数据是否在坐标矩形内

                    getCircleMarkerToMap(markers);
//                    markerToMap(markers);
                }
            });
        }

        /**
         * 搜索结果列表过滤
         */
        function toResultSearch(){
            var resultName = $("#resultName").val();
            setSearchResultData(1, resultName, null, true);
        }

        /**
         * 通过条件加载标记数据
         * @param url 加载地址
         * @param cond 条件
         */
        function loadMarkerByCond(url, cond){
            $("#mainGis").mask("加载中...");
            map.clearAll();
            allMarkerData.length = 0;
            markerObjs.length = 0;
            newMarkerManager(null);
            $.post(url, cond, function(data){
                var errMsg = data['errMsg'];
                var centerLongit = data['centerLongit'];
                var centerLatit = data['centerLatit'];
                var markers = data['markers'];
                if(errMsg) alert(errMsg);
                else if(centerLongit && centerLatit){
                    map.panTo(new sogou.maps.LatLng(centerLatit, centerLongit));
                    map.setZoom(9);
                    markerToMap(markers);
                }
                loadMarkerOver();
            });
        }

        /**
         * 加载标记结束
         */
        function loadMarkerOver(){
            $.ligerui.get('layout').setLeftCollapse(false);
            setSearchResultData(1, '', null, true);
            $("#mainGis").unmask();
        }

        /**
         * 设置查询结果数据到左边展示
         * @param page 展示页数
         */
        function setSearchResultData(page, resultName, markerDatas, isFirst){
            if(markerDatas == null) markerDatas = new Array();
            if(isFirst){
                if(resultName){
                    markerDatas = new Array();
                    for(var k in allMarkerData){
                        var tempMarkerData = allMarkerData[k];
                        if(tempMarkerData['name'].indexOf(resultName) > -1){
                            markerDatas.push(tempMarkerData);
                        }
                    }
                }else{
                    markerDatas = allMarkerData;
                }
            }else{
                if(!resultName){
                    markerDatas = allMarkerData;
                }
            }
            var length = markerDatas.length;
            var result = length > 0 ? $("<ul style='background-color: #f5f5f5;'></ul>") : NO_DATA_FOUND_MSG;
            var start = (page - 1) * 15;
            var end = Math.min(page * 15, length);
            for(var i = start; i < end; i++){
                var item = $("<li style='cursor: pointer;padding: 5px;border-bottom: solid white 1px' onclick='locateMarker(this)' onmouseover='mouseover(this)' onmouseout='mouseout(this);'></li>");
                var markerData = markerDatas[i];
                var markerType = markerData['markerType'];
                var marker = markerData['markerObj'];
                var name = markerData['name'];
                item.attr('title', getMarkerTypeName(markerType));
                item.html(i+1 + '.&nbsp;&nbsp;' + name);
                item.attr("latitude", markerData['latitude'] ? markerData['latitude'] : "");
                item.attr("longitude", markerData['longitude'] ? markerData['longitude'] : "");
                item.data("markerObj", marker);
                result.append(item);
            }
            if(page == 1) $("#result").html("");
            $("li[moreLink]").remove();
            if(end < length) result.append("<li moreLink style='color:blue;cursor: pointer;padding: 5px;border-bottom: solid white 1px' onclick='setSearchResultData(" + ++page + ")' onmouseover='mouseover(this)' onmouseout='mouseout(this);'>点击加载更多</li>");
            if(isFirst) $("#result").append("<div style='padding: 2px 5px 2px 5px;'><input id='resultName' style='margin-right: 5px;' type='text' size='16' value='" + resultName + "'/><input class='btn btn-info btn-mini' type='button' onclick='toResultSearch()' value='查询'/></div><div id='content' style='height:94.5%;overflow: auto;'></div>");
            $("#content").append(result);
        }

        /**
         * 获取标记类型名
         * @param markerType 标记类型
         */
        function getMarkerTypeName(markerType){
            var name;
            switch(markerType){
                case 1: name = '物理站点'; break;
                case 2: name = '室分站点'; break;
                case 3: name = 'bbu'; break;
                case 4: name = '传输节点'; break;
                default: name = ''; break;
            }
            return name;
        }

        /**
         * 定位标记
         * @param obj 需要定位的标记数据
         */
        function locateMarker(obj){
            var latitude = $(obj).attr('latitude');
            var longitude = $(obj).attr('longitude');
            var marker = $(obj).data("markerObj");
            if(latitude && longitude && marker){
                sogou.maps.event.trigger(marker, 'click');
                map.panTo(new sogou.maps.LatLng(latitude, longitude));
            }else alert('定位失败,该条数据信息不全!');
        }

        /**
         * 鼠标在指定对象中移动的操作
         * @param obj 事件对象
         */
        function mouseover(obj){
            $(obj).addClass("mouseover");
        }

        /**
         * 鼠标移除该对象后的操作
         * @param obj 事件对象
         */
        function mouseout(obj){
            $(obj).removeClass("mouseover");
        }


        function changeCond(obj){
            if(obj.value=='1'){
                $("#nameTag").html("位置");
                $('#nameTag').show();
                $('#nameText').show();

                $('#areaTag').hide();
                $('#areaSel').hide();

                $('#typeTag').hide();
                $('#typeSel').hide();

                $('#lineTag').hide();
                $('#lineSel').hide();
            }else if(obj.value=='2'){
                $("#nameTag").html("名称");
                $('#name').val("");
                $('#nameTag').show();
                $('#nameText').show();

                $('#areaTag').show();
                $('#areaSel').show();

                $('#typeTag').show();
                $('#typeSel').show();

                $('#lineTag').show();
                $('#lineSel').show();
            }
        }

    </script>
</head>
<body id="mainGis">
    <div>
        <table cellpadding="0" cellspacing="0" width="100%">
            <tr class="tr_inquires">
                <td width="40px">搜索：</td>
                <td width="120px">
                    <select id="searchTypes" onchange="changeCond(this);">
                        <option value="1">按位置搜索</option>
                        <option value="2" selected>按类型搜索</option>
                    </select>
                </td>
                <td width="40px" id="nameTag">名称：</td>
                <td width="150px" id="nameText">
                    <input type="text" id="name"/>
                </td>
                <td width="40px" id="areaTag">
                    地区：
                </td>
                <td width="140px" id="areaSel">
                    <input type="text" id="cityId"/>
                    <input type="hidden" id="cityIdVal"/>
                </td>
                <td width="40px" id="typeTag">类型：</td>
                <td width="12px" id="typeSel">
                    <input type="text" id="types"/>
                    <input type="hidden" id="typesVal"/>
                </td>
                <td width="40px" id="lineTag">连线：</td>
                <td width="120px" id="lineSel">
                    <input type="text" id="lines"/>
                    <input type="hidden" id="linesVal"/>
                </td>
                <%--<td width="60px">BSC名称：</td>
                <td width="150px">
                    <input type="text" id="bscName"/>
                </td>
                <td width="50px">btsID：</td>
                <td width="150px">
                    <input type="text" id="btsId"/>
                </td>--%>
                <td align="left">
                    <input class="btn btn-info btn-small" type="button" onclick="toSearch()" value="查询"/>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
    <div id="layout">
        <div position="left" id="result" style="height: 95%;" title="搜索结果列表">暂无相关数据!</div>
        <div id="map_canvas" position="center"></div>
    </div>
</body>
</html>