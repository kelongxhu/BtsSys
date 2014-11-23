<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script type="text/javascript">
        var gridObj = null;
        $(function () {
            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display: '名称', name: 'cnName', width: 200, align: 'center'},
                    {display: '字段命名', name: 'enName', width: 200, align: 'center'}
                ],
                rownumbers: true,
                showTitle: false,
                url: '${ctx}/businessjson/columnsList.action?typeId=${typeId}',
                checkbox: true,
                width: 500,
                height: 300,
                usePager: false
            });
        });

    </script>
</head>
<body>
   <div>
       <div id="maingrid"></div>
       <div class="form-actions_2">
           <button class="btn btn-info" type="button" onclick="a();"><i class="icon-ok icon-white"></i>确定</button>
           <button class="btn btn-info" type="reset" onclick="back();"><i class="icon-repeat"></i>关闭</button>
       </div>
   </div>
</body>
</html>