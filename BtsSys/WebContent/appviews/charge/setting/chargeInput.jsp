<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<%@ include file="/appviews/common/tag.jsp"%>
		<script type="text/javascript"
			src="${ctx}/resources/js/ajaxfileupload.js"></script>
		<script src="${ctx}/resources/uploadify/jquery.uploadify.js"
			type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/resources/uploadify/uploadify.css">
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
	//注册表单验证
	$(function() {
		$("#contractStarttime").ligerDateEditor({ label: '', format: "yyyy-MM-dd", labelAlign: 'right',width : 200});
		$("#contractEndtime").ligerDateEditor({ label: '', format: "yyyy-MM-dd", labelAlign: 'right',width : 200});
		
		//$("#remindUser").ligerTextBox({width : 200 });
		
		


	});

	//初始化主设备安装位置
	var typeURL1 = "${ctx}/schooljson/cons.action?groupCode=BTSINSTALL";
	var sl1;
	$.getJSON(typeURL1, function(data) {
		sl1 = $("#installPosVal").ligerComboBox( {
			data : data.Rows,
			width : 200,
			selectBoxWidth : 200,
			textField : 'name',
			valueField : 'code',
			valueFieldID : 'installPos'
		});
		sl1.selectValue('${btsManual.installPos}');
	});

	//初始化塔桅类型
	var typeURL2 = "${ctx}/schooljson/cons.action?groupCode=TOWERTYPE";
	var sl2;
	$.getJSON(typeURL2, function(data) {
		sl2 = $("#towerTypeVal").ligerComboBox( {
			data : data.Rows,
			width : 200,
			selectBoxWidth : 200,
			textField : 'name',
			valueField : 'code',
			valueFieldID : 'towerType'
		});
		sl2.selectValue('${btsManual.towerType}');
	});

	//初始化机房结构
	var typeURL3 = "${ctx}/schooljson/cons.action?groupCode=MRSTRUT";
	var sl3;
	$.getJSON(typeURL3, function(data) {
		sl3 = $("#mrStrutVal").ligerComboBox( {
			data : data.Rows,
			width : 200,
			selectBoxWidth : 200,
			textField : 'name',
			valueField : 'code',
			valueFieldID : 'mrStrut'
		});
		sl3.selectValue('${btsManual.mrStrut}');
	});

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
				window.location.href = "${ctx}/business/bts.action";
			});
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

	var upDialog;

	function up() {
		upDialog = $.ligerDialog.open( {
			height : 150,
			width : 300,
			target : $("#forUploader"),
			title : '上传塔桅照片'
		});
	}

	function upImage() {
		var fileName = document.getElementById("file").value;
		if (fileName.length < 1) {
			alert("上传图片不能为空!");
			return;
		}
		var type = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!(type == "jpg")) {
			alert("请上传jpg类型图片!");
			return;
		}

		$.ajaxFileUpload( {
			url : '${ctx}/schooljson/upImage.action',
			secureuri : false,
			fileElementId : 'file',
			dataType : 'text',
			type : "post",
			cache : false,
			timeout : 5000,
			success : function(data, status) {
				if (data.indexOf("error") >= 0) {
					alert("文件上传失败");
				} else {
					var mmsDo = eval("(" + data + ")");
					var sc = mmsDo.result;
					if (sc == "1") {
						address = mmsDo.address;
						alert("上传成功");
						$("#towerPic").val(address);
						upDialog.close();
					} else {
						alert("上传失败");
					}
				}
			},
			error : function(data, status, e) {
				alert(e);
			}
		});
	}

	$(function() {
		//塔跪照片
		$("#uploadify").uploadify( {
			method : 'post',
			swf : '${ctx}/resources/uploadify/uploadify.swf',
			uploader : '${ctx}/businessjson/importFile.action',
			cancelImg : '${ctx}/resources/uploadify/uploadify-cancel.png',
			fileObjName : 'file',
			fileSizeLimit : 0,
			multi : false,
			removeCompleted : false,
			fileSizeLimit : '10MB',
			buttonText : '选择文件',
			buttonClass : 'some-class',
			height : 21,
			width : 75,
			auto : true,
			fileTypeDesc : 'jpg文件',//上传文件类型说明
			fileTypeExts : '*.jpg', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
			onInit : function() {
				$("#uploadify-queue").hide();
			},
			onSelect : function(file) {
				//$("#path").val(file.name);
		},
		onUploadSuccess : function(file, data, response) {
			data = eval('(' + data + ')');
			if (data.result == 1) {
				$("#towerPic").val(data.address);
				$("#msg").html("上传成功!");
			} else {
				$("#msg").html("上传失败!");
			}
		}
		});

		//机房照片
		$("#jf_uploadify").uploadify( {
			method : 'post',
			swf : '${ctx}/resources/uploadify/uploadify.swf',
			uploader : '${ctx}/businessjson/importFile.action',
			cancelImg : '${ctx}/resources/uploadify/uploadify-cancel.png',
			fileObjName : 'file',
			fileSizeLimit : 0,
			multi : false,
			removeCompleted : false,
			fileSizeLimit : '10MB',
			buttonText : '选择文件',
			buttonClass : 'some-class',
			height : 21,
			width : 75,
			auto : true,
			fileTypeDesc : 'jpg文件',//上传文件类型说明
			fileTypeExts : '*.jpg', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
			onInit : function() {
				$("#jf_uploadify-queue").hide();
			},
			onSelect : function(file) {
				//$("#path").val(file.name);
		},
		onUploadSuccess : function(file, data, response) {
			data = eval('(' + data + ')');
			if (data.result == 1) {
				$("#mrPic").val(data.address);
				$("#jf_msg").html("上传成功!");
			} else {
				$("#jf_msg").html("上传失败!");
			}
		}
		});

	});

	//选择上游节点对话框

	function showDilog() {
		$.ligerDialog.open( {
			height : 512,
			url : '${ctx}/appviews/business/bts/topTransferNode.jsp',
			width : 1100,
			name : 'columns',
			title : "选择传输拓扑上游站点",
			showMax : true,
			showToggle : true,
			showMin : true,
			isResize : true,
			isHidden : false
		});
	}

	function showDownDilog() {
		$.ligerDialog.open( {
			height : 512,
			url : '${ctx}/appviews/business/bts/downTransferNode.jsp',
			width : 1100,
			name : 'columns',
			title : "选择传输拓扑下游站点",
			showMax : true,
			showToggle : true,
			showMin : true,
			isResize : true,
			isHidden : false
		});
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
					action="${ctx}/businessjson/addBtsManual.action">
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
											<input name="wyBtsCharge.contractStarttime" class="required" type="text" id="contractStarttime" />
										</td>
										<td>
											<span style="color: red;">*</span>合同结束日期:
										</td>
										<td>
											<input name="wyBtsCharge.contractEndtime" class="required" type="text" id="contractEndtime" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color: red;">*</span>缴费周期：
										</td>
										<td>
											<input name="wyBtsCharge.payCycle" type="text" class="input150 required number" />
											<span style="color: red; font-size: 10px">(注：月)</span>
										</td>
										<td>
											<span style="color: red;">*</span>缴费日期:
										</td>
										<td>
											<input name="wyBtsCharge.payDay" type="text" class="input150 required number" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color: red;">*</span>提前提醒周期：
										</td>
										<td>
											<input name="wyBtsCharge.aheadDay" type="text" class="input150 required number" />
										</td>
										<td>
											<span style="color: red;">*</span>提醒人员:
										</td>
										<td>
											<input name="wyBtsCharge.remindUser" type="text" class="input150 required" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color: red;">*</span>提醒号码：
										</td>
										<td>
											<input name="wyBtsCharge.remindTel" type="text"
												class="input150 required"/>
										</td>
										<td>
											<span style="color: red;">*</span>金额：
										</td>
										<td>
											<input name="wyBtsCharge.money" type="text"
												class="input150 required number"/>
										</td>										
									</tr>
									<tr>
										<td>
											<span style="color: red;">*</span>合同附件：
										</td>
										<td>
											<input name="wyBtsCharge.contractFile" type="text"
												class="input150 required"/>
										</td>
										<td>
											<span style="color: red;">*</span>备注：
										</td>
										<td>
											<input name="wyBtsCharge.remark" type="text"
												class="input150 required"/>
										</td>										
									</tr>									
									<tr>
										<td>
											对方联系号码：
										</td>
										<td>
											<input name="wyBtsCharge.eachTel" type="text"
												class="input150"/>
										</td>
										<td>
											对方账号名称：
										</td>
										<td>
											<input name="wyBtsCharge.eachAccountname" type="text"
												class="input150"/>
										</td>										
									</tr>	
									<tr>
										<td>
											对方银行账号：
										</td>
										<td>
											<input name="wyBtsCharge.eachBanknum" type="text"
												class="input150"/>
										</td>
									</tr>
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
											<input name="wyBtsCharge.contractStarttime" class="required" type="text" id="contractStarttime" />
										</td>
										<td>
											<span style="color: red;">*</span>合同结束日期:
										</td>
										<td>
											<input name="wyBtsCharge.contractEndtime" class="required" type="text" id="contractEndtime" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color: red;">*</span>缴费周期：
										</td>
										<td>
											<input name="wyBtsCharge.payCycle" type="text" class="input150 required number" />
											<span style="color: red; font-size: 10px">(注：月)</span>
										</td>
										<td>
											<span style="color: red;">*</span>缴费日期:
										</td>
										<td>
											<input name="wyBtsCharge.payDay" type="text" class="input150 required number" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color: red;">*</span>提前提醒周期：
										</td>
										<td>
											<input name="wyBtsCharge.aheadDay" type="text" class="input150 required number" />
										</td>
										<td>
											<span style="color: red;">*</span>提醒人员:
										</td>
										<td>
											<input name="wyBtsCharge.remindUser" type="text" class="input150 required" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color: red;">*</span>提醒号码：
										</td>
										<td>
											<input name="wyBtsCharge.remindTel" type="text"
												class="input150 required"/>
										</td>
										<td>
											<span style="color: red;">*</span>金额：
										</td>
										<td>
											<input name="wyBtsCharge.money" type="text"
												class="input150 required number"/>
										</td>										
									</tr>
									<tr>
										<td>
											<span style="color: red;">*</span>合同附件：
										</td>
										<td>
											<input name="wyBtsCharge.contractFile" type="text"
												class="input150 required"/>
										</td>
										<td>
											<span style="color: red;">*</span>备注：
										</td>
										<td>
											<input name="wyBtsCharge.remark" type="text"
												class="input150 required"/>
										</td>										
									</tr>									
									<tr>
										<td>
											对方联系号码：
										</td>
										<td>
											<input name="wyBtsCharge.eachTel" type="text"
												class="input150"/>
										</td>
										<td>
											对方账号名称：
										</td>
										<td>
											<input name="wyBtsCharge.eachAccountname" type="text"
												class="input150"/>
										</td>										
									</tr>	
									<tr>
										<td>
											对方银行账号：
										</td>
										<td>
											<input name="wyBtsCharge.eachBanknum" type="text"
												class="input150"/>
										</td>
									</tr>
									<!-- 电费 -->										
									<tr>
										<th colspan="4">
											<span class="label label-success">电费费用设置</span>
										</th>
									</tr>
									<tr>
										<td>
											油机类型：
										</td>
										<td>
											<input name="btsManual.oeType" type="text" class="input150"
												value="${btsManual.oeType}" />
										</td>
										<td>
											油机型号:
										</td>
										<td>
											<input name="btsManual.oeModel" type="text" class="input150"
												value="${btsManual.oeModel}" />
										</td>
									</tr>
									<tr>
										<td>
											油机功率：
										</td>
										<td>
											<input name="btsManual.oePower" type="text" class="input150"
												value="${btsManual.oePower}" />
										</td>
										<td>
											厂家：
										</td>
										<td>
											<input name="btsManual.oeFac" type="text" class="input150"
												value="${btsManual.oeFac}" />
										</td>
									</tr>
									<tr>
										<td>
											备注：
										</td>
										<td colspan="3">
											<textarea name="btsManual.remark" id="remark" cols="55"
												rows="2">${btsManual.remark}</textarea>
										</td>
									</tr>
								</table>
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