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
			//返回
			function back() {
				javascript: history.go(-1);
			}
			
			function downloadAttachment(intId, costType, fileName){
				window.location.href = "${ctx}/charge/downloadAttachment.action?intId=" + intId + "&costType=" + costType + "&fileName=" + fileName;
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
					费用信息
				</p>
			</div>
			<div class="content">
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
											${bts.btsName}
										</td>
										<td width="150px">
											所属BSC:
										</td>
										<td width="300px">
											${bts.bscName}
										</td>
									</tr>
									<tr>
										<td>
											本地网:
										</td>
										<td>
											${bts.cityName}
										</td>
										<td>
											区县:
										</td>
										<td>
											${bts.countryName}
										</td>
									</tr>
									<tr>
										<td>
											BTSID:
										</td>
										<td>
											${bts.btsId}
										</td>
									</tr>

									<c:forEach var="charge" items="${btsChargeList}">
										<c:choose>
											<c:when test="${charge.costType=='1'}">
												<!-- 房租  -->
												<tr>
													<th colspan="4">
														<span class="label label-success">房租费用设置</span>
													</th>
												</tr>
												<tr>
													<td>
														<span style="color: red;">*</span>合同开始日期：
													</td>
													<td>
														<fmt:formatDate value='${charge.contractEndtime }' pattern='yyyy-MM-dd' />
													</td>
													<td>
														<span style="color: red;">*</span>合同结束日期:
													</td>
													<td>
														<fmt:formatDate value='${charge.contractEndtime }' pattern='yyyy-MM-dd' />
													</td>
												</tr>	
												<tr>
													<td>
														<span style="color: red;">*</span>缴费周期：
													</td>
													<td>
														${charge.payCycle}
														<span style="color: red; font-size: 10px">(注：月)</span>
													</td>
													<td>
														<span style="color: red;">*</span>上次缴费月份:
													</td>
													<td>
														<fmt:formatDate value='${charge.lastPayTime }' pattern='yyyy-MM'/>
													</td>										
												</tr>		
												<tr>
													<td>
														<span style="color: red;">*</span><span id="payDay">缴费日期:</span> 
													</td>
													<td>
														${charge.payDay }
													</td>	
													<td>
														<span style="color: red;">*</span>
														金额：
													</td>
													<td>
														${charge.money }
													</td>																						
												</tr>	
												<tr>
													<td>
														<span style="color: red;">*</span>提醒号码：
													</td>
													<td>
														${charge.remindTel }
													</td>										
													<td>
														<span style="color: red;">*</span>提醒人员:
													</td>
													<td>
														${charge.remindUser }
													</td>
												</tr>				
												<tr>
													<td>
														<span style="color: red;">*</span>提前提醒周期：
													</td>
													<td>
														${charge.aheadDay }
													</td>
													<td>
														对方联系号码：
													</td>
													<td>
														${charge.eachTel }
													</td>											
												</tr>		
												<tr>
													<td>
														对方账号名称：
													</td>
													<td>
														${charge.eachAccountname }
													</td>													
													<td>
														<span id="eachBanknum">对方银行账号：</span>
													</td>
													<td>
														${charge.eachBanknum }
													</td>	
												</tr>	
												<tr>
													<td>
														合同附件：
													</td>
													<td>
														${charge.contractFile }
													</td>	
											        <td colspan="2">
											            <button class="btn btn-success" onclick="downloadAttachment('${charge.intId }', '${charge.costType }', '${charge.contractFile }');" title="Upload File"><i class="icon-ok icon-white"></i>下载</button>
											        </td>																			
												</tr>
												<tr>
													<td>
														备注：
													</td>
													<td colspan="3">
			                							${charge.remark}
													</td>		
												</tr>																																																														
											</c:when>
											<c:when test="${charge.costType=='2'}">		
												<!-- 物业 -->									
												<tr>
													<th colspan="4">
														<span class="label label-success">物业费用设置</span>
													</th>
												</tr>	
												<tr>
													<td>
														<span style="color: red;">*</span>合同开始日期：
													</td>
													<td>
														<fmt:formatDate value='${charge.contractEndtime }' pattern='yyyy-MM-dd' />
													</td>
													<td>
														<span style="color: red;">*</span>合同结束日期:
													</td>
													<td>
														<fmt:formatDate value='${charge.contractEndtime }' pattern='yyyy-MM-dd' />
													</td>
												</tr>	
												<tr>
													<td>
														<span style="color: red;">*</span>缴费周期：
													</td>
													<td>
														${charge.payCycle}
														<span style="color: red; font-size: 10px">(注：月)</span>
													</td>
													<td>
														<span style="color: red;">*</span>上次缴费月份:
													</td>
													<td>
														<fmt:formatDate value='${charge.lastPayTime }' pattern='yyyy-MM'/>
													</td>										
												</tr>		
												<tr>
													<td>
														<span style="color: red;">*</span><span id="payDay">缴费日期:</span> 
													</td>
													<td>
														${charge.payDay }
													</td>		
													<td>
														<span style="color: red;">*</span>
														金额：
													</td>
													<td>
														${charge.money }
													</td>																					
												</tr>			
												<tr>
													<td>
														<span style="color: red;">*</span>提醒号码：
													</td>
													<td>
														${charge.remindTel }
													</td>												
													<td>
														<span style="color: red;">*</span>提醒人员:
													</td>
													<td>
														${charge.remindUser }
													</td>												
												</tr>	
												<tr>
													<td>
														<span style="color: red;">*</span>提前提醒周期：
													</td>
													<td>
														${charge.aheadDay }
													</td>
													<td>
														对方联系号码：
													</td>
													<td>
														${charge.eachTel }
													</td>											
												</tr>		
												<tr>
													<td>
														对方账号名称：
													</td>
													<td>
														${charge.eachAccountname }
													</td>													
													<td>
														<span id="eachBanknum">对方银行账号：</span>
													</td>
													<td>
														${charge.eachBanknum }
													</td>	
												</tr>		
												<tr>
													<td>
														合同附件：
													</td>
													<td>
														${charge.contractFile }
													</td>	
											        <td colspan="2">
											            <button class="btn btn-success" onclick="downloadAttachment('${charge.intId }', '${charge.costType }', '${charge.contractFile }');" title="Upload File"><i class="icon-ok icon-white"></i>下载</button>
											        </td>																			
												</tr>
												<tr>
													<td>
														备注：
													</td>
													<td colspan="3">
			                							${charge.remark}
													</td>		
												</tr>																																																																												
											</c:when>
											<c:when test="${charge.costType=='3'}">		
												<!-- 电费 -->										
												<tr>
													<th colspan="4">
														<span class="label label-success">电费费用设置</span>
													</th>
												</tr>	
												<tr>
													<td>
														<span style="color: red;">*</span>缴费方式:
													</td>
													<td>
														<c:choose>
															<c:when test="${charge.payType == '1'}">人工缴费</c:when>
															<c:when test="${charge.payType == '2'}">自动代扣</c:when>
															<c:otherwise/>
														</c:choose>
													</td>
												</tr>	
												<tr>
													<td>
														<span style="color: red;">*</span>
														<c:choose>
															<c:when test="${charge.payType == '1'}">缴费周期：</c:when>
															<c:when test="${charge.payType == '2'}">代扣周期：</c:when>
															<c:otherwise/>
														</c:choose>
													</td>
													<td>
														${charge.payCycle}
														<span style="color: red; font-size: 10px">(注：月)</span>
													</td>
													<td>
														<span style="color: red;">*</span>上次缴费月份:
													</td>
													<td>
														<fmt:formatDate value='${charge.lastPayTime }' pattern='yyyy-MM'/>
													</td>										
												</tr>
												<tr>
													<td>
														<span style="color: red;">*</span><span id="payDay">
														<c:choose>
															<c:when test="${charge.payType == '1'}">缴费日期:</c:when>
															<c:when test="${charge.payType == '2'}">代扣日期：</c:when>
															<c:otherwise/>
														</c:choose>
														</span> 
													</td>
													<td>
														${charge.payDay }
													</td>	
													<td>
														<span style="color: red;">*</span>
														单价：
													</td>
													<td>
														${charge.money }
													</td>																					
												</tr>		
												<tr>
													<td>
														<span style="color: red;">*</span>提醒号码：
													</td>
													<td>
														${charge.remindTel }
													</td>										
													<td>
														<span style="color: red;">*</span>提醒人员:
													</td>
													<td>
														${charge.remindUser }
													</td>
												</tr>	
												<c:if test="${charge.payType == '2'}">
													<tr>
														<td>
															<span>代扣银行账号：</span>
														</td>
														<td>
															${charge.bankAccount }
														</td>													
														<td>
															<span id="balance">当前账户余额：</span>
														</td>
														<td>
															${charge.balance }
														</td>											
													</tr>																																																									
												</c:if>
												<tr>
													<td>
														<span style="color: red;">*</span>提前提醒周期：
													</td>
													<td>
														${charge.aheadDay }
													</td>
												</tr>												
											</c:when>
											<c:otherwise/>
										</c:choose>											
									</c:forEach>
								</table>
							</div>
						</div>

						<div class="form-actions_2">
							<button class="btn" type="reset" onclick='back();'>
								<i class="icon-repeat"></i> 返回
							</button>
						</div>
					</div>

			</div>
		</div>

	</body>
</html>