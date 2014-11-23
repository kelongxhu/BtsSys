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

        var menu;
        $(function(){
            menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
                    [
                        { id:'add',text: '增加', click:'' },
                        { id: 'modify', text: '修改', click: '', disable: true },
                        { line: true },
                        { id: 'view', text: '查看', click: '' },
                        { id: 'close', text: '关闭', click: '' }
                    ]
            });
        })


        var map,mapsEventListener,mapsRightEventListener;
        function initialize() {
            var myLatlng = new sogou.maps.LatLng(39.992792,116.326142);
            var myOptions = {
                zoom: 12,
                center: myLatlng,
                mapTypeId: sogou.maps.MapTypeId.ROADMAP
            }
            map = new sogou.maps.Map(document.getElementById("map_canvas"), myOptions);

            //添加侦听器，当缩放地图时触发，弹出对话框
            sogou.maps.event.addListener(map, 'zoom_changed', function() {
                moveToDarwin()
            });

            var marker = new sogou.maps.Marker({
                position: myLatlng,
                map: map,
                title:"Hello World!"
            });
            //添加侦听器，当点击标记时触发，将地图缩放到第8级
            mapsEventListener=sogou.maps.event.addListener(marker, 'click', function() {
                map.setZoom(8);
            });

            //添加侦听器，当点击标记时触发，将地图缩放到第8级
            mapsRightEventListener=sogou.maps.event.addListener(map, 'rightclick', function(e) {
//               alert("right click");
//                alert(e.pageY);
                menu.show({ top: e.pageY, left: e.pageX });
//               rightClick();
            });

        }

        function moveToDarwin() {
            alert(map.getZoom())
        }
        //删除侦听器
        function removeEvent()
        {
            //删除侦听器后再点击标记，将不再响应
            sogou.maps.event.removeListener(mapsEventListener)
        }


        function rightClick(){
            menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
                    [
                        { id:'add',text: '增加', click: onclick11 },
                        { id: 'modify', text: '修改', click: onclick11, disable: true },
                        { line: true },
                        { id: 'view', text: '查看', click: onclick11 },
                        { id: 'close', text: '关闭', click: onclick112 }
                    ]
            });

            alert(22222);

            menu.show({ top: e.pageY, left: e.pageX });
//            $(document).bind("contextmenu", function (e)
//            {
//                menu.show({ top: e.pageY, left: e.pageX });
//                return false;
//            });
        }


    </script>


</head>
<body onload="initialize()">
<div>

        <div id="map_canvas" position="center"></div>
</div>
</body>
</html>
