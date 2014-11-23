<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title></head>
<%@ include file="/appviews/common/tag.jsp" %>
<link rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript">
    var treeObj = null;
    $(function() {
        var setting = {
            async: {
                enable: true,
                url:"${ctx}/businessjson/buildDataTree.action",
                autoParam:["id","treeLevel","country"]
            },
            callback: {
                onClick:function(event, treeId, treeNode) {
                    var parms = {};
                }  ,
                onAsyncSuccess : function(event, treeId, treeNode, msg) {
                   // alert(msg);
                },
                onAsyncError : function(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
                    alert("异步加载失败!");
                }
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
    });
</script>
<body>
<ul id="tree1" class="ztree"></ul>
</body>
</html>