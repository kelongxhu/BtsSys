<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<%@ include file="/appviews/common/tag.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ajaxfileupload.js"></script>
		<script src="${ctx}/resources/uploadify/jquery.uploadify.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/uploadify/uploadify.css">
		<style type="text/css">
			#msg {
				padding-left: 16px;
				padding-bottom: 2px;
				font-weight: bold;
				display: inline;
				color: #EA5200;
			}
		</style>
		<script type="text/javascript">
			var select = false;
			var upload = false;
			//注册表单验证
			$(function() {
				/**
				var combox = $("#payTypeVal").ligerComboBox({  
			         data: [
			             { text: '人工缴费', id: '1' },
			             { text: '自动代扣', id: '2' }
			         ], 
			         width : 200,
					 selectBoxWidth: 200,
			         valueFieldID: 'payType',
			         onSelected:function(data){
			         	if(data == '1'){
			         		$("#accountInfo").hide();
			         		$("#payCycle").html("缴费周期");
			         		$("#payDay").html("缴费天");
			         	}else if(data == '2'){
			         		$("#accountInfo").show();
			         		$("#payCycle").html("代扣周期");
			         		$("#payDay").html("代扣天");
			         	}
					 }
			     });*/

				if('${WyBtsCharge.payType}' == '1'){
					$("#accountInfo").hide();
				}else if('${WyBtsCharge.payType}' == '2'){
					$("#accountInfo").show();
				}
							     
			});
		
			function chgType(obj){
				if(obj.value == '1'){
	         		$("#accountInfo").hide();
	         		$("#payCycle").html("缴费周期");
	         		$("#payDay").html("缴费天");
	         		//$("#_bankAccount").val('');
	         		//$("#_balance").val('');
	         	}else if(obj.value == '2'){
	         		$("#accountInfo").show();
	         		$("#payCycle").html("代扣周期");
	         		$("#payDay").html("代扣天");
	         		//$("#_bankAccount").val('${WyBtsCharge.bankAccount }');
	         		//$("#_balance").val('${WyBtsCharge.balance }');
	         	}
			}
		
			$(function() {
				var v = $("#form1").validate( {
					ignore : "",
					submitHandler : function(form) {
					//form.submit();
					jQuery(form).ajaxSubmit(function(json) {
						if (json.result == 1) {
							alert('操作成功!');
						} else {
							alert('操作失败!');
						}
						window.location.href = "${ctx}/charge/chargeSetting.action";
					});
				}
				});
			});
		
			function add() {
				if(select == true && upload == false){
					alert('请上传附件');
				}else if($("#charge_payDay").val()>28||$("#charge_payDay").val()<1){
					alert('缴费天只能是1-28间的数');
				}
				else{
					$("#form1").submit();
				}
			}
		
			//返回
			function back() {
				javascript: history.go(-1);
			}
			
			$(function() {
			    $("#pz_uploadify").uploadify({
			        method    : 'post',
			        swf           : '${ctx}/resources/uploadify/uploadify.swf',
			        uploader      : '${ctx}/chargejson/uploadFile.action;jsessionid=${pageContext.session.id}',
			        cancelImg :  '${ctx}/resources/uploadify-cancel.png',
			        fileObjName     : 'file',
			        successTimeout:600,
			        multi  : false,
			        removeCompleted : false,
			        fileSizeLimit : '10MB',
			        buttonText      : '选择文件',
			        height        : 25,
			        width         : 70 ,
			        auto :false,
			        fileTypeDesc: 'All Files',//上传文件类型说明
			        fileTypeExts: '*.*', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
			        onInit   : function() {
			            $("#pz_uploadify-queue").hide();
			        },
			        onSelect : function(file) {
			            $("#proofFile").val(file.name);
			            select = true;
			        },
			        onUploadProgress: function(file,bytesUploaded,bytesTotal,totalBytesUploaded,totalBytesTotal){
			            $('#msg').html('已上传:'+(totalBytesTotal/bytesTotal)*100);
			        },
			        onUploadSuccess : function(file, data, response) {
			            data = eval('(' + data + ')');
			            var status=data["fileDTO"].status;
			            if(status==1){
			            	upload = true;
			                $("#proofFile").val(data["fileDTO"].uuid);
			                $("#msg").html("上传成功!");
			            }else{
			                $("#msg").html("上传失败!");
			            }
			        }
			    });
			});
			
			//上传
			function uploadifyUpload() {
			    $('#pz_uploadify').uploadify('upload', '*');
				//    $("#msg").html("请稍等,正在上传。。");
				//    $("#msg").addClass("tipMsg");
			    return false;
			}
			
		</script>
	</head>
	<body>
		<!-- 标题 -->
		<div id="main_2">
			<!-- 标题 end-->
			<div class="main_title_2">
				<p class="main_title_p">
					<img src="${ctx}/layouts/image/ico_arrow.gif"></img>
					费用设置
				</p>
			</div>
			<div class="content">
				<form method="post" name="form1" id="form1"
					action="${ctx}/chargejson/doChargeSetting.action">
					<div class="tabbable tabs-left">
						<ul class="nav nav-tabs nav-stacked" id="mytab"></ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab1">
								<table cellpadding="0" cellspacing="0" class="tab_1">
									<tr>
										<td colspan="4">
											<span class="label label-success">站点基本信息</span>
										</td>
									</tr>
									<tr>
										<td width="150px">
											名称：
										</td>
										<td width="300px">
											${WyBtsCharge.btsName}
										</td>
										<td width="150px">
											所属BSC:
										</td>
										<td width="300px">
											${WyBtsCharge.bscName}
										</td>
									</tr>
									<tr>
										<td>
											本地网:
										</td>
										<td>
											${WyBtsCharge.cityName}
										</td>
										<td>
											区县:
										</td>
										<td>
											${WyBtsCharge.countryName}
										</td>
									</tr>
									<tr>
										<td>
											BTSID:
										</td>
										<td>
											${WyBtsCharge.btsId}
										</td>
									</tr>

									<c:choose>
										<c:when test="${param.costType=='1'}">
											<!-- 房租  -->
											<tr>
												<th colspan="4">
													<span class="label label-success">房租费用设置</span>
												</th>
											</tr>
										</c:when>
										<c:when test="${param.costType=='2'}">		
											<!-- 物业 -->									
											<tr>
												<th colspan="4">
													<span class="label label-success">物业费用设置</span>
												</th>
											</tr>										
										</c:when>
										<c:when test="${param.costType=='3'}">		
											<!-- 电费 -->										
											<tr>
												<th colspan="4">
													<span class="label label-success">电费费用设置</span>
												</th>
											</tr>										
										</c:when>
										<c:otherwise/>
									</c:choose>			
												
									<c:if test="${param.costType=='1'||param.costType=='2'}">
									<tr>
										<td>
											<span style="color: red;">*</span>合同开始日期：
										</td>
										<td>
											<input name="wyBtsCharge.contractStarttime" type="text" id="contractStarttime" 
												class="Wdate input150 required" onFocus="WdatePicker({dateFmt: 'yyyy-MM-dd'})" 
												value="<fmt:formatDate value='${WyBtsCharge.contractEndtime }' pattern='yyyy-MM-dd' />"/>
										</td>
										<td>
											<span style="color: red;">*</span>合同结束日期:
										</td>
										<td>
											<input name="wyBtsCharge.contractEndtime" type="text" id="contractEndtime" 
												class="Wdate input150 required" onFocus="WdatePicker({dateFmt: 'yyyy-MM-dd'})" 
												value="<fmt:formatDate value='${WyBtsCharge.contractEndtime }' pattern='yyyy-MM-dd' />"/>
										</td>
									</tr>
									</c:if>											
									
									<c:if test="${param.costType=='3'}">
									<tr>
										<td>
											<span style="color: red;">*</span>缴费方式:
										</td>
										<td>
											<select name="wyBtsCharge.payType" class="input150 required" onchange="chgType(this);">
							                    <option value="1" <c:if test="${WyBtsCharge.payType == '1'}">selected="selected"</c:if>>人工缴费</option>
							                    <option value="2" <c:if test="${WyBtsCharge.payType == '2'}">selected="selected"</c:if>>自动代扣</option>
							                    
							                </select>
<!--											<input name="payTypeVal" id="payTypeVal" type="text" class="required"/>-->
<!--											<input name="wyBtsCharge.payType" id="payType" type="hidden" value="${WyBtsCharge.payType }"/>-->
										</td>
									</tr>									
									</c:if>	
									<tr>
										<td>
											<span style="color: red;">*</span><span id="payCycle">缴费周期：</span>
										</td>
										<td>
											<input name="wyBtsCharge.payCycle" type="text" class="input150 required number" 
												   <c:if test="${!empty WyBtsCharge.payCycle }">value="${WyBtsCharge.payCycle }"</c:if>
												   <c:if test="${empty WyBtsCharge.payCycle }">value="1"</c:if>
												   />
											<span style="color: red; font-size: 10px">(注：月)</span>
										</td>
										<td>
											<span style="color: red;">*</span>上次缴费月份:
										</td>
										<td>
											<input name="wyBtsCharge.lastPayTime" type="text" 
												class="Wdate input150 required" onFocus="WdatePicker({dateFmt: 'yyyy-MM'})" 
												value="<fmt:formatDate value='${WyBtsCharge.lastPayTime }' pattern='yyyy-MM'/>"/>
										</td>										
									</tr>
									<tr>
										<td>
											<span style="color: red;">*</span><span id="payDay">缴费天数:</span> 
										</td>
										<td>
											<input id="charge_payDay" name="wyBtsCharge.payDay" type="text" class="input150 required number" value="${WyBtsCharge.payDay }"/>
										</td>									
										<td>
											<span style="color: red;">*</span>提醒人员:
										</td>
										<td>
											<input name="wyBtsCharge.remindUser" type="text" class="input150 required" value="${WyBtsCharge.remindUser }"/>
										</td>
									</tr>
									<tr>
										<td>
											<span style="color: red;">*</span>提醒号码：
										</td>
										<td>
											<input name="wyBtsCharge.remindTel" type="text"
												class="input150 required" value="${WyBtsCharge.remindTel }"/>
										</td>										
										<td>
											<span style="color: red;">*</span>
											<c:if test="${param.costType=='3'}">单价:</c:if>
											<c:if test="${param.costType=='1'||param.costType=='2'}">金额：</c:if>
										</td>
										<td>
											<input name="wyBtsCharge.money" type="text"
												class="input150 required number" value="${WyBtsCharge.money }"/>
										</td>		
									</tr>
									<c:if test="${param.costType=='1'||param.costType=='2'}">
									<tr>
										<td>
											对方联系号码：
										</td>
										<td>
											<input name="wyBtsCharge.eachTel" type="text"
												class="input150" value="${WyBtsCharge.eachTel }"/>
										</td>											
										<td>
											对方账号名称：
										</td>
										<td>
											<input name="wyBtsCharge.eachAccountname" type="text"
												class="input150" value="${WyBtsCharge.eachAccountname }"/>
										</td>		
									</tr>
									<tr>
										<td>
											<span>对方银行账号：</span>
										</td>
										<td>
											<input name="wyBtsCharge.eachBanknum" type="text"
												class="input150" value="${WyBtsCharge.eachBanknum }"/>
										</td>	
									</tr>									
									</c:if>
									<c:if test="${param.costType=='3'}">
									<tr id="accountInfo">
										<td>
											代扣银行账号：
										</td>
										<td>
											<input name="wyBtsCharge.bankAccount" type="text"
												class="input150" value="${WyBtsCharge.bankAccount }" id="_bankAccount"/>
										</td>	
										<td>
											当前账户余额：
										</td>
										<td>
											<input name="wyBtsCharge.balance" type="text"
												class="input150 required number" value="${WyBtsCharge.balance }" id="_balance"/>
										</td>											
									</tr>	
									</c:if>
									<c:if test="${param.costType=='1'||param.costType=='2'}">
									<tr>
										<td>
											合同附件：
										</td>
										<td>
											<div style="float:left">
									            <input name="wyBtsCharge.contractFile" id="proofFile" type="text" readonly="readonly" class="input150" value="${WyBtsCharge.contractFile }"/>
									        </div>
									        <div style="float:left">
									            <input id="pz_uploadify" name="pz_uploadify" type="file" multiple="true">
									        </div>
										</td>	
								        <td colspan="2">
								            <button class="btn btn-success" onclick="$('#pz_uploadify').uploadify('upload', '*'); return false;" title="Upload File"><i class="icon-ok icon-white"></i>上传</button>
								            <span id="msg" class="tipMsg"></span>
								        </td>																			
									</tr>
									<tr>
										<td>
											备注：
										</td>
										<td colspan="3">
                							<textarea name="wyBtsCharge.remark" class="area150">${WyBtsCharge.remark}</textarea>
										</td>		
									</tr>		
									</c:if>									
								</table>
								<input type="hidden" name="wyBtsCharge.intId" value="${param.intId }">
								<input type="hidden" name="wyBtsCharge.btsType" value="${param.btsType }">
								<input type="hidden" name="wyBtsCharge.costType" value="${param.costType }">
							</div>
						</div>

						<div class="form-actions_2">
							<button class="btn btn-info" type="button" onclick='add();'>
								<i class="icon-ok icon-white"></i> 保存
							</button>
							<button class="btn" type="reset" onclick='back();'>
								<i class="icon-repeat"></i> 返回
							</button>
						</div>
					</div>
				</form>

			</div>
		</div>

	</body>
</html>