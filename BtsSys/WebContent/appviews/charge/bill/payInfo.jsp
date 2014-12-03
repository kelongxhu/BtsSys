<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>缴费信息</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/ligerUI/1.1.9/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        var gridObj=null;
        $(function(){
            gridObj = $("#maingrid").ligerGrid({
                columns: [
                    {display:'缴费金额',name:'money',width : 140,align:'center'},
                    {display:'缴费人员',name:'payUser',width : 80,align:'center'},
                    {display:'缴费时间',name:'payTimeStr',width : 80,align:'center'},
                    {display:'是否超时',name:'isTimeoutStr',width :80,align:'center'},
                    {display:'底度',name:'baseDegree',width :80,align:'center'},
                    {display:'当月度数',name:'monthDegree',width :80,align:'center'} ,
                    {display:'缴费方式',name:'payTypeStr',width :80,align:'center'} ,
                    {display:'缴费凭证',name:'proofFileName',width :300,align:'center',
                        render : function(row,rowIndex) {
                            var html = '<a href="#" onclick="downloadProof(' +rowIndex+ ')" title="点击下载">'+row.proofFileName+'</a>';
                            return html;
                        }
                    }
                ],
//                toolbar: {
//		            items: [
//		                {text: '下载',click: downloadProof, icon: 'save'}
//		            ]
//		        },
                usePager:false,
                rownumbers:true,
                showTitle : false,
                url:'${ctx}/chargejson/payDetailList.action?intId=${charge.intId}&costType=${charge.costType}',
                checkbox : false,
                width: '98%',
                height:'250'
            });

            var costType= "${charge.costType}";//费用类型
            if(costType=='3'){
                gridObj.toggleCol('baseDegree', true);
                gridObj.toggleCol('monthDegree', true);
                gridObj.toggleCol('payTypeStr', true);
            }else{
                gridObj.toggleCol('baseDegree', false);
                gridObj.toggleCol('monthDegree', false);
                gridObj.toggleCol('payTypeStr', false);
            }
        })
        //返回
        function back() {
            javascript: history.go(-1);
        }
        
        function downloadProof(rowid){
            var row = gridObj.getRow(rowid);
            var id=row.intId;
            var fileName=row.proofFileName;
            var url = encodeURI("${ctx}/charge/downloadAttachment.action?intId=" + id + "&fileType=proof&fileName=" + fileName);
			window.location.href = url;
        }
    </script>
</head>
<body>
<!-- 标题 -->
<div id="main_2">
    <!-- 标题 end-->
    <div class="main_title_2">
        <p class="main_title_p"><img src="${ctx}/layouts/image/ico_arrow.gif"></img>基站缴费信息</p>
    </div>
    <div class="content">
        <div>
        <table cellpadding="0" cellspacing="0" class="tab_1">
            <tr>
                <td colspan="4"><span class="label label-success">站点基本信息</span></td>
            </tr>
            <tr>
                <td width="150px">站点名称：</td>
                <td width="300px">${charge.btsName}
                </td>
                <td width="150px">所属BSC:</td>
                <td width="300px">${charge.bscName}</td>
            </tr>
            <tr>
                <td width="150px">本地网：</td>
                <td width="300px">${charge.cityName}</td>
                <td width="150px">区县:</td>
                <td width="300px">${charge.countryName}</td>
            </tr>
            <tr>
                <td>BTSID:</td>
                <td>${charge.btsId}</td>
                <td>费用类型:</td>
                <td>${charge.costTypeStr}</td>
            </tr>
        </table>
        </div>
        <div id="maingrid"></div>
        <div class="form-actions_2">
            <button class="btn" type="reset" onclick="back();">
                <i class="icon-repeat"></i>
                返回
            </button>
        </div>
    </div>
</div>

</body>
</html>