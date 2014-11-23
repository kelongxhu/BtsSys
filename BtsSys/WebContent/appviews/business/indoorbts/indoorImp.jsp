<%@ page import="com.scttsc.admin.model.User" %>
<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>室分手工数据导入</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <script src="${ctx}/resources/uploadify/jquery.uploadify.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css">
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>

    <style type="text/css">

    </style>

    <script type="text/javascript">
        $(function () {
            $("#uploadify1").uploadify({
                method:'post',
                swf:'${ctx}/resources/uploadify/uploadify.swf',
                uploader:'${ctx}/businessjson/importIndoorInputData.action?userId=${user.intId}',
                fileObjName:'file',
                fileSizeLimit:0,
                successTimeout:600,
                multi:false,
                removeCompleted:false,
                fileSizeLimit:'10MB',
                buttonText:'选择室分文件',
                height:30,
                width:120,
                auto:true,
                fileTypeDesc:'Excel文件', //上传文件类型说明
                fileTypeExts:'*.xls', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
                onSelect:function (file) {
                    //$("#path").val(file.name);
                },
                onUploadSuccess:function (file, data, response) {
                    if(data)
                        data = eval('(' + data + ')');
                    if (data && data.result == 1) {
                        $("#msg1").html("文件：" + file.name + "上传成功!" + "上传成功条数:" + data.sucess);
                    } else {
                        $("#msg1").html("文件：" + file.name + "导入失败!");
                    }
                }
            });

            $("#uploadify2").uploadify({
                method:'post',
                swf:'${ctx}/resources/uploadify/uploadify.swf',
                uploader:'${ctx}/businessjson/importErctsInputData.action',
                fileObjName:'file',
                fileSizeLimit:0,
                successTimeout:600,
                multi:false,
                removeCompleted:false,
                fileSizeLimit:'10MB',
                buttonText:'选择直放站文件',
                height:30,
                width:120,
                auto:true,
                fileTypeDesc:'Excel文件', //上传文件类型说明
                fileTypeExts:'*.xls', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
                onSelect:function (file) {
                    //$("#path").val(file.name);
                },
                onUploadSuccess:function (file, data, response) {
                    if(data)
                        data = eval('(' + data + ')');
                    if (data && data.result == 1) {
                        $("#msg2").html("文件：" + file.name + "上传成功!" + "上传成功条数:" + data.sucess);
                    } else {
                        $("#msg2").html("文件：" + file.name + "导入失败!");
                    }
                }
            });

            $("#uploadify3").uploadify({
                method:'post',
                swf:'${ctx}/resources/uploadify/uploadify.swf',
                uploader:'${ctx}/businessjson/importDryInputData.action',
                fileObjName:'file',
                fileSizeLimit:0,
                successTimeout:600,
                multi:false,
                removeCompleted:false,
                fileSizeLimit:'10MB',
                buttonText:'选择干放站文件',
                height:30,
                width:120,
                auto:true,
                fileTypeDesc:'Excel文件', //上传文件类型说明
                fileTypeExts:'*.xls', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
                onSelect:function (file) {
                    //$("#path").val(file.name);
                },
                onUploadSuccess:function (file, data, response) {
                    if(data)
                        data = eval('(' + data + ')');
                    if (data && data.result == 1) {
                        $("#msg3").html("文件：" + file.name + "上传成功!" + "上传成功条数:" + data.sucess);
                    } else {
                        $("#msg3").html("文件：" + file.name + "导入失败!");
                    }
                }
            });
        });

        //上传
        function uploadifyUpload() {
            $('#uploadify').uploadify('upload', '*');
        }

        //返回
        function back() {
            javascript: history.go(-1);
        }

    </script>
</head>
<body>
<div id="main">
    <!-- 标题 end-->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">
            室分手工数据导入
        </p>
    </div>
    <div class="content_2">
        <div style="width: 450px;">
            <span style="color: red;">注意：请先导出待录入数据模板，按照录入模板填写对应数据，导入手工数据！</span>
             <%--<p>${user.intId}</p>--%>
            <form enctype="multipart/form-data">
                <br>
                <input type="hidden" name="userId" id="userId" value="${user.intId}">
                <div id="queue"></div>
                <input id="uploadify1" name="uploadify1" type="file" multiple="true" onclick="uploadifyUpload();"><span
                    id="msg1"></span>
                <input id="uploadify2" name="uploadify2" type="file" multiple="true" onclick="uploadifyUpload();"><span
                    id="msg2"></span>
                <input id="uploadify3" name="uploadify3" type="file" multiple="true" onclick="uploadifyUpload();"><span
                    id="msg3"></span>
                <button class="btn btn-info" type="reset" onclick="back();">返回</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>