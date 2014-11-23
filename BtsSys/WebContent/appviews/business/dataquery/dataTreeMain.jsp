<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title></head>
<%@ include file="/appviews/common/tag.jsp" %>
<link rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.core-3.5.js"></script>
<style type="text/css">
    body {
        padding: 0;
        margin: 0;
    }

    #leftContent {
        width: 200px;
        overflow: auto;
        position: absolute;
        top: 0px;
        _height: expression(document.getElementById("con").offsetHeight-2 + "px");
        bottom: 0;
        _zoom: 1;
        z-index: 100;
        border: 1px solid #84A0C4;
    }

    #rightContent {
        position: relative;
        margin-left: 205px;
        overflow:auto;
    }

    div#rMenu {
        position: absolute;
        visibility: hidden;
        top: 0;
        background-color: #555;
        text-align: left;
        padding: 2px;
        z-index:999
    }

    div#rMenu ul li {
        margin: 1px 0;
        padding: 0 5px;
        cursor: pointer;
        list-style: none outside none;
        background-color: #DFDFDF;
    }
</style>
<script type="text/javascript">
    var treeObj = null;
    var rMenu = null;
    $(function() {
        var setting = {
            async: {
                enable: true,
                url:"${ctx}/businessjson/buildDataTree.action",
                autoParam:["id","treeLevel","country","type"]
            },
            view: {
                fontCss: getFont,
                nameIsHTML: true
            },
            callback: {
                onClick:function(event, treeId, treeNode) {
                    var treeLevel = treeNode.treeLevel;
                    var type = treeNode.type;
                    var intId = treeNode.id;
                    if (treeLevel == 5) {
                        if (type == 'bts') {
                            $("#infoFrame").attr("src", "${ctx}/business/btsInfo.action?intId=" + intId);
                        } else if (type == 'bbu') {
                            $("#infoFrame").attr("src", "${ctx}/business/bbuInfo.action?intId=" + intId);
                        }else if(type=='indoor'){
                            $("#infoFrame").attr("src", "${ctx}/business/business/indoorDetail.action?intId=" + intId);
                        }
                    } else if (treeLevel == 6) {
                        if (type == 'cell') {
                            $("#infoFrame").attr("src", "${ctx}/business/cellInfo.action?intId=" + intId);
                        }
                    }
                }  ,
                onAsyncSuccess : function(event, treeId, treeNode, msg) {
                    // alert(msg);
                },
                onAsyncError : function(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
                    alert("异步加载失败!");
                },
                onRightClick: OnRightClick
            } ,
            data: {
                simpleData: {
                    enable: true
                },
                key: {
                    name: "text"
                }
            }
        };
        var nodes = [
            {id:10001,text: "贵州省",isParent:true,treeLevel:1,country:10001}
        ];
        treeObj = $.fn.zTree.init($("#tree1"), setting, nodes);
        treeObj.expandNode(treeObj.getNodeByParam("id", 10001), true);
        rMenu = $("#rMenu");
    });
    function getFont(treeId, node) {
        return node.font ? node.font : {};
    }

    function OnRightClick(event, treeId, treeNode) {
        var treeLevel=treeNode.treeLevel;
        var country=treeNode.country;
        var countrys='${user.countryIds}';
        var flag=false;
        if(treeLevel>4){
            var array=countrys.split(",");
            for(var i=0;i<array.length;i++){
                if(array[i]==country){
                    flag=true;
                    break;
                }
            }
        }
        //显示右键功能
        if(flag){
            var x = event.clientX;
            var y = event.clientY;
            $("#rMenu ul").show();
            $("#m_add").show();
            rMenu.css({"top":y + "px", "left":x + "px", "visibility":"visible"});
            $("body").bind("mousedown", onBodyMouseDown);
        }
    }

    function onBodyMouseDown(event) {
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
            rMenu.css({"visibility" : "hidden"});
        }
    }

    function add(){
        var node = treeObj.getSelectedNodes()[0];
        var type=node.type;
        var country= node.country;
        var countrys='${user.cityIds}';
        alert(countrys);
        if(type=='bts'){
             addTab('录入物理站点手工信息','${ctx}/business/btsInput.action?intId='+node.id);
        }else if(type=='bbu'){
             addTab('录入BBU站点手工信息','${ctx}/business/bbuInput.action?intId='+node.id);
        }else if(type=='cell'){
             addTab('录入小区手工信息','${ctx}/business/cellInput.action?intId='+node.id);
        }else if(type=='indoor'){

        }
    }


     function addTab(title, href) {
            var tt = parent.$('#main-center');
            if (tt.tabs('exists', title)) {
                tt.tabs('select', title);
            } else {
                if (href) {
                    var content = '<iframe scrolling="no" frameborder="0"  src="' + href + '" style="width:100%;height:100%;"></iframe>';
                } else {
                    var content = '未实现';
                }
                tt.tabs('add', {
                    title:title,
                    closable:true,
                    content:content
                });
            }
        }

</script>
<body>
<div id="con" style="position:relative;z-index:99;height: 100%">
    <div id="leftContent">
        <ul id="tree1" class="ztree">
        </ul>
    </div>
    <div></div>
    <div id="rightContent">
        <div id="resultGrid">
            <iframe id="infoFrame" width="100%" height="100%" frameborder="0"></iframe>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
<div id="rMenu">
    <ul>
        <li id="m_add" onclick="add();">录入/编辑</li>
    </ul>
</div>
</body>
</html>