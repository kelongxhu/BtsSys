$(document).ready(function() {
    try{
	// 加载地图相关
	var myLatlng = new sogou.maps.LatLng(26.56667,106.71667);  //贵阳
	var myOptions = {
		'zoom': 11,
		'center': myLatlng,
		'mapTypeId': sogou.maps.MapTypeId.ROADMAP,
		'callback' : function() {
//			changeLogo("mainMap");
		}
	};
	map = new sogou.maps.Map(document.getElementById("map_canvas"), myOptions);
    }catch (e){
        alert("不能访问，请检查是否访问外网");
    }
//
//	var hiddenMapDiv = document.createElement('DIV');
//	hiddenMapDiv.id="hiddenMapDIv";
//	hiddenMapDiv.style.position="absolute";
//	hiddenMapDiv.style.right="1%";
//	hiddenMapDiv.style.top="1%";
//	var loadControl =new Control(hiddenMapDiv, map,'点击隐藏底图','<input type="checkbox" id="hiddenMap">&nbsp;<label class="hiddenMap" for="hiddenMap">隐藏底图</label>',loadControlCallBack);
//	hiddenMapDiv.style.zIndex=99;
//	map.getContainer().appendChild(hiddenMapDiv);
});
//
//function loadControlCallBack(){
//
//	if($("#hiddenMap").attr("checked") == "checked") {
//		//隐藏地图底图方法：
//		hideMapImage(map, "mainMap");
//		//$("#hiddenMap").removeAttr("checked");
//	} else {
//		//$("#hiddenMap").attr("checked", "checked");
//		//显示地图底图方法：
//		showMapImage(map, "mainMap");
//	}
//}

function maskerMap(data){
    var errMsg = data['errMsg'];
    if(errMsg) {
        alert(errMsg);
    }else {
        if(data['centerLongit'] && data['centerLatit']) {
            // 将地图中心坐标设置为居中
            map.panTo(new sogou.maps.LatLng(data['centerLatit'], data['centerLongit']));
        }
        var markers = data['markers'];
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
                            }else if(markerKey == 'bbu') markerType = 3;
                            markerProcessor(markerData, markerType);
                        }
                    }
                }
            }
        }
    }
}

function markerProcessor(markerData, markerType){
    var longitude = markerData['longitude'];
    var latitude = markerData['latitude'];
    if(longitude && longitude != "null" && latitude && latitude != "null"){
        var icon;
        switch(markerType){
            case 1: icon = '../resources/gisResources/img/baseSiteYellow.png'; break;//加图片 黄色
            case 2: icon = '../resources/gisResources/img/baseSiteBlue.png'; break;//加图片 蓝色
            case 3: icon = '../resources/gisResources/img/baseSite.jpg'; break;//加图片 浅灰
            default: icon = 'http://api.go2map.com/maps/images/v2.0/flag.png'; //加图片 默认
        }
        var marker = new sogou.maps.Marker({
            position: new sogou.maps.LatLng(latitude, longitude),
            map: map,
            icon: getInitMarkerStyle(icon),
            title: markerData.name.toString(),
            visible: true,
            label: {visible:true,align:"BOTTOM"}//是否显示title
        });
        associateRelatedMarker(markerData);// 关联相关的站点(拉远和施主站点)
        addMarkerListener(marker, markerData, markerType);   //添加附属信息
    }
}

function associateRelatedMarker(markerData){
    var szLatLng = markerData['szLatLng'];
    if(!szLatLng) return;//如果没有相应的施主站点,则该站点不是拉远站点,不用关联
    var fromLat = markerData['latitude'];
    var fromLng = markerData['longitude'];
    var toLat = szLatLng['latitude'];
    var toLng = szLatLng['longitude'];
    if(fromLat && fromLng && toLat && toLng){
        var path = [
            new sogou.maps.LatLng(fromLat, fromLng),//拉远站点坐标
            new sogou.maps.LatLng(toLat, toLng)//施主站点坐标
        ];
        var lineStyle1 = {
            id: 'L01',
            "v:stroke": {
                color:"#0cf",
                weight:"3",
                opacity:"100%"
            }
        };
        var lineStyle2 = {
            id: 'L02',
            "v:stroke": {
                color:"#f00",
                weight:"6",
                endcap:"Round",
                opacity:"100%",
                endArrow:"Classic",
                endarrowlength:"long",
                endarrowwidth:"wide"
            }
        };
        var polyline = new sogou.maps.Polyline({
          path: path,
          styles: [lineStyle1, lineStyle2]
        });
        polyline.setMap(map);
    }
}

function loadMap(data) {
    if(data.centerLongit && data.centerLatit) {
        // 将地图中心坐标设置为居中
        map.panTo(new sogou.maps.LatLng(data.centerLatit,data.centerLongit));
    }

    if(data.Rows.length != 0) {
		// 清除地图上面所有元素
		map.clearAll();

        var datas = data.Rows;
		for(var key in datas) {
    		var latObj = datas[key];// 站点坐标信息
            if(latObj.longitude && latObj.longitude != "null" && latObj.latitude && latObj.latitude != "null") {
//					// 循环将每条坐标 标注到地图上面
                var marker = new sogou.maps.Marker({
                    position:  new sogou.maps.LatLng(latObj.latitude,latObj.longitude),
                    map: map,
//                    icon:getInitMarkerStyle('../resources/gisResources/img/baseSite.jpg'),     //加图片 浅灰
                    icon:getInitMarkerStyle('../resources/gisResources/img/baseSiteYellow.png'),     //加图片 黄色
//                    icon:getInitMarkerStyle('../resources/gisResources/img/baseSiteBlue.png'),     //加图片 蓝色
                    title:latObj.name.toString(),
                    visible:true,
                    label:{visible:true,align:"BOTTOM"}//是否显示title

                });
                addMarkerListener(marker, latObj);   //添加附属信息
            }
		}
	} else {
		// 清除地图上面所有元素
		map.clearAll();
	}
    $("#mainGis").unmask();
}
//得到Marker样式
function getInitMarkerStyle(icon){
	//定义标记样式
	var markerStyle=new sogou.maps.MarkerImage(icon,
//    var markerStyle=new sogou.maps.MarkerImage(root + icon,
	// 蓝点图标宽16像素，高14像素
	new sogou.maps.Size(15, 24),
	// 原点在图片的(34,88)
	new sogou.maps.Point(0,0),
	// 锚点在图标中心
	new sogou.maps.Point(7,12),
	// 合并图片的大小
	new sogou.maps.Size(15, 24));
	return markerStyle;
}

// 添加marker事件
function addMarkerListener(marker, resInfo, maskerType) {
    var msg = "<div style='width:300px;height:170px;'>暂未设置额外属性</div>";
    if(maskerType == 1 || maskerType == 2){
        var cNameTmp = resInfo.cellName.toString();
        var cNameShow = cNameTmp.replace(/；/g, '\n\t');
        msg = "<div style='width:300px;height:170px;'><ul>" +
                    "<li><span style='width:60px; color: #0000ff;'>基站名称：</span>" +
                            resInfo.name +"</li>" +
                    "<li><span style='width:60px; color: #0000ff;'>基 站 &nbsp;ID：</span>" +
                           resInfo.btsId +"</li>" +
                    "<li><span style='width:60px; color: #0000ff;'>评&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分：</span>" +
                           resInfo.totalGrade +"</li>" +
                    "<li algin='top'><span style='width:60px; color: #0000ff;'>下属小区：</span>" +
                            "<pre>\t" +    cNameShow +  "</pre></li>" +
    //				"<li><span style='width:60px;'>告警：</span>" +
    //                    "<input type='text' value='"+ resInfo.alarms +"' readonly='readonly' /></li>" +
                    "</ul></div>";
    }else if(maskerType == 3){

    }
	var infowindow = new sogou.maps.InfoWindow({
        content: msg,
        title: '站点信息'
    });
    sogou.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map,marker);
    });

}
