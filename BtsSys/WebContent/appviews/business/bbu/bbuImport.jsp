<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>BBU基站手工数据导入</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script src="${ctx}/resources/uploadify/jquery.uploadify.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css">
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>

    <style type="text/css">

    </style>

    <script type="text/javascript">
        $(function() {
            $("#uploadify").uploadify({
                method    : 'post',
                swf           : '${ctx}/resources/uploadify/uploadify.swf',
                uploader      : '${ctx}/businessjson/importBbuInputData.action;jsessionid=${pageContext.session.id}',
                fileObjName     : 'file',
                fileSizeLimit   : 0,
                successTimeout:600,
                multi  : false,
                removeCompleted : false,
                fileSizeLimit : '10MB',
                buttonText      : '选择文件',
                height        : 30,
                width         : 120 ,
                auto :false,
                fileTypeDesc :'Excel文件',//上传文件类型说明
                fileTypeExts : '*.xls', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
                onSelect : function(file) {
                    //$("#path").val(file.name);
                },
                onUploadSuccess : function(file, data, response) {
                    data = eval('(' + data + ')');
                    if (data.result == 1) {
                        $("#msg").html("文件：" + file.name + "上传成功!" + "上传成功条数:" + data.sucess);
                        var error = data.errorList;
                        if (error && error.length > 0) {
                            for (var i = 0; i < error.length; i++) {
                                $("#error").append("<p>" + error[i] + "</p>");
                            }
                        }
                    } else {
                        $("#msg").html("文件：" + file.name + "导入失败!");
                    }
                }
            });
        });

        //上传
        function uploadifyUpload() {
            $('#uploadify').uploadify('upload', '*');
            $("#msg").html("请稍等,正在导入。。。");
            $("#msg").addClass("progressCss");
        }

        //返回
        function back() {
            javascript: history.go(-1);
        }
    </script>
</head>
<body>
<div id="main_2">
    <!-- 标题 end-->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>
            BBU站点手工数据导入
        </p>
    </div>
    <div class="content">
        <div style="height: 50px;"></div>
        <form class="form-horizontal" enctype="multipart/form-data">
            <div class="control-group error">
                <label class="control-label">注意：</label>

                <div class="controls">
                    <span style="font-weight: bold;color: #EA5200;">
                        请先导出待录入数据模板，按照录入模板填写对应数据，导入手工数据!
                    </span>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">选择文件:</label>

                <div class="controls">
                    <input id="uploadify" name="uploadify" type="file" multiple="true">
                    <div id="msg"></div>
                    <div id="error"></div>
                </div>
            </div>
        </form>
        <div class="form-actions">
            <button class="btn btn-info" type="button" onclick="uploadifyUpload();">    <i class="icon-ok icon-white"></i>上传</button>
            <button class="btn btn-info" type="reset" onclick="back();">  <i class="icon-repeat"></i>返回</button>
        </div>
    </div>
</div>
</body>
</html>