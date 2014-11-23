<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>cons</title>
<%@ include file="/appviews/common/tag.jsp"%>
</head>
<body>
<div id="value" style="display:none"><%=request.getParameter("deleteResoncode")%></div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr class="tr_inquires">
		<td width="60px">
				废弃原因：
		</td>
		<td width="150px">
            <input name="cons" id="cons" type="text" class="required"/>
		</td>
	</tr>

</table>
</div>
</div>
<script type="text/javascript">
    var gridObj = null;
    $(function(){
        var typeURL1="${ctx}/schooljson/cons.action?groupCode=ABANDONEDTYPE";
        var sl1;
        $.getJSON(typeURL1,
                function(data){
                    sl1 = $("#cons").ligerComboBox({
                        data : data.Rows,
                        width : 170,
                        selectBoxWidth: 170,
                        textField : 'name',
                        valueField : 'code',
                        valueFieldID:'consValue'
                    });
                    sl1.selectValue($("#value").text());
                }
        );
        $("#cons").change(function(){
            $(window.parent.document).find("input[id='con']").val($("#consValue").val());
        })
    })
</script>
</body>
</html>