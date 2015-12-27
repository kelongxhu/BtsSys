<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>cons</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        var gridObj = null;
        $(function () {
            //废弃类型
            var typeURL1 = "${ctx}/schooljson/cons.action?groupCode=ABANDONEDTYPE";
            var sl1;
            $.getJSON(typeURL1,
                    function(data) {
                        sl1 = $("#deleteResoncodeVal").ligerComboBox({
                            data : data.Rows,
                            width : 170,
                            selectBoxWidth: 170,
                            textField : 'name',
                            valueField : 'code',
                            valueFieldID:'deleteResoncode'
                        });
                        sl1.selectValue('${deleteResoncode}');
                    }
            );

           var v = $("#form1")
			.validate(
					{
						submitHandler : function(form) {
							 jQuery(form).ajaxSubmit(function(json){
								 if (json.result == 1) {
									 alert('操作成功!');
								}else{
									 alert('操作失败!');
								}
                                 window.location.href="${ctx}/lte/lteAbandon.action?typeId="+${typeId}
							 });
						}
					});
        });

        function edit(){
           $("#form1").submit();
        }
    </script>
</head>
<body>

<div id="main_2">
    <!-- 标题 end-->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>
            填写废弃原因
        </p>
    </div>
    <div class="content">


        <form id="form1" method="post" style="padding-bottom: 15px;padding-top: 20px;" action="${ctx}/ltejson/lteAbandonEdit.action">
            <div class="controls row-fluid">
                    <span class="span12 form-horizontal">
                          <div class="control-group">
                              <label class="control-label">废弃原因：</label>

                              <div class="controls">
                                    <input type="text" id="deleteResoncodeVal" style="height:12px;" class="required"/>
                                    <input name="deleteResoncode" type="hidden" id="deleteResoncode"/>
                                    <input name="id" type="hidden" value="${id}"/>
                                    <input name="typeId" type="hidden" value="${typeId}">
                              </div>
                          </div>
                    </span>

            </div>
            <div class="controls row-fluid">
             <span class="span12 form-horizontal">
                          <div class="control-group">
                              <label class="control-label">废弃文本解释:</label>

                              <div class="controls">
                                  <textarea name="deleteText" class="input-xxlarge" id="deleteText"
                                            style="width:620px;height: 50px;">${deleteText}</textarea>
                              </div>
                          </div>
                    </span>
            </div>
            <div class="form-actions_2">
                <button class="btn btn-info" type="button" onclick="edit();"><i class="icon-ok icon-white"></i>保存</button>
                <button class="btn btn-info" type="reset" onclick="javascript:history.back(-1);"><i
                        class="icon-repeat"></i>返回
                </button>
            </div>
        </form>

    </div>
</div>
</body>
</html>