<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        $(function() {



            //下拉框

            var treeCombox;
            //初始化地市
            //初始化树控件
            $.post("${ctx}/adminjson/menuTreeData.action?roleId=${role.intId}", function(ajaxData, status) {
                var treeData = ajaxData.json;
                treeData = treeData.replace(/"children":\[\],/g, '');
                treeData = eval('(' + treeData + ')');
                treeCombox = $("#menuIdsVal").ligerComboBox({
                    width : 350,
                    selectBoxWidth : 350,
                    selectBoxHeight : 250,
                    textField : 'text',
                    valueField : 'id',
                    valueFieldID : 'menuIds',
                    treeLeafOnly : true,
                    tree : {
                        data : treeData
                    }
                });
                //treeCombox.selectValue('${menuIds}');
            });
        });



        $(function() {
            var v = $("#form1")
                    .validate(
                    {
                        submitHandler : function(form) {
                            //form.submit();
                            jQuery(form).ajaxSubmit(function(json) {
                                if (json.result == 1) {
                                    alert('操作成功!');
                                } else {
                                    alert('操作失败!');
                                }
                                window.location.href = "${ctx}/admin/role.action";
                            });
                        },
                        errorPlacement : function(lable, element) {
                            element.parent().ligerTip({ content: lable.html(), target: element[0] });
                            //lable.appendTo(element.parent());
                        },
                        success : function(lable) {
                            lable.ligerHideTip();
                            lable.remove();
                        }
                    });
        });


        function add() {
            $("#form1").submit();
        }

        //返回
        function back() {
            javascript: history.go(-1);
        }
    </script>
<body>


<div id="main_2">
    <!-- 标题 -->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif">权限分配</p>
    </div>
    <!-- 标题 end-->
    <div class="content">

        <form class="form-horizontal" method="post" name="form1" id="form1" action="${ctx}/adminjson/menuApply.action">
            <div class="control-group" style="margin-top: 50px">
                <label class="control-label">角色名称:</label>

                <div class="controls">
                    ${role.name}
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">选择菜单:</label>

                <div class="controls">
                    <input id="menuIdsVal" type="text"
                           style="border-radius: 0 0 0 0;height:11px;background-color:#FFFFFF;cursor:auto;" class="required" value="${menuText}"/>
                    <input name="menuIds" id="menuIds" type="hidden" value="${menuIds}"/>
                    <input name="roleId" type="hidden" value="${role.intId}"/>
                </div>
            </div>

        </form>
        <div class="form-actions_2" style="margin-top: 100px">
            <button id="demo-upload" class="btn btn-info" onclick="add();">
                <i class="icon-ok icon-white"></i>
                保存
            </button>
            <button id="a-save" class="btn" type="reset" onclick="back();">
                <i class="icon-repeat"></i>
                返回
            </button>
        </div>


    </div>
</div>

</body>
</html>