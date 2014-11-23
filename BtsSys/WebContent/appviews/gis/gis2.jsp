<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>物理站点</title>
    <%@ include file="/appviews/common/tag.jsp" %>

    <style type="text/css">
        html {
            height: 100%
        }

        body {
            height: 100%;
            margin: 0px;
            padding: 0px
        }

        #map_canvas {
            height:600px;
            position:absolute;
            width:500px;
        }

        .mouseover {
            background-color: #E8F8A9;
        }

    </style>
    <script type="text/javascript" src="http://api.go2map.com/maps/js/api_v2.5.1.js"></script>
    <script type="text/javascript" src="http://api.go2map.com/maps/js/component/textIcon.js"></script>
    <script type="text/javascript" src="http://api.go2map.com/maps/js/component/markerCluster.js"></script>
    <script type="text/javascript" src="http://api.go2map.com/maps/js/component/markerManager.js"></script>

    <script type="text/javascript">
        function seachKeyWord(keyWord) {
            var myLatLng = new sogou.maps.Point(12957062, 4827187);
            var myOptions = {
                zoom: 10,
                center: myLatLng
            };
            var map = new sogou.maps.Map(document.getElementById("map_canvas"), myOptions);//创建地图
            var sRender = new sogou.maps.SearchRenderer({'panel': document.getElementById('result')});
            var request = {
                'map': map,
                'what': {
                    'keyword': keyWord
                }
            };
            var search = new sogou.maps.Search();//创建搜索实例
            search.search(request,function(data){
                alert(data.message);
                alert(data.data);
            });
            search.setRenderer(sRender);
            var infowindow = new sogou.maps.InfoWindow();

            sogou.maps.event.addListener(sRender,'getMarker',function(a,b){
                infowindow.open(map,a);
                var div=document.createElement('div');
                div.innerHTML=b.innerHTML;
                alert(a.innerHTML);
                div.style.width='300px';
                div.style.fontSize='12px';
                infowindow.setContent(div);
            });

        }

        function search() {
            var address = $("#name").val();
            seachKeyWord(address);
        }
    </script>


</head>
<body>
<div>
    <div>
        名称： <input type="text" id="name"/> <input class="btn btn-info btn-small" type="button" onclick="search()"
                                                  value="查询"/>
    </div>
    <div id="layout">
        <div position="left" id="result" style="height: 95%;" title="搜索结果列表"></div>
        <div id="map_canvas" position="center"></div>
    </div>
</div>
</body>
</html>
