define(['jquery', 'knockout','compevent','bignumber','/iwebap/js/BillFormActionUtils.js','director','/iwebap/js/ImageBrowser.js','i18next'],function($, ko, compevent,bignumber) {
	var ctrl = {};
	ctrl.bodyButtongroups = [];//表体操作按钮注册
	ctrl.mainOrg = {};
	ctrl.pkFieldName = "";
	ctrl.funcnode = "";
	ctrl.bignumber = bignumber;
	ctrl.isLoadingData = false;
	ctrl.org_local_currtype = "";
	ctrl.router = Router();

	ctrl.router.on('/add', function(){
		appModel.setUIState($.UIState.ADD);
		$("#approvalinfos").hide();	
		ctrl.mainOrg.setEnable(true);
	});
	ctrl.router.on('/view/:id', function(id){
		ctrl.openbillid = id
		appModel.setUIState($.UIState.NOT_EDIT);
		$("#approvalinfos").show();			
	});
	
	
	window.onbeforeunload = function(){ 
		if(isUrlChanged()==0 && appModel.getUIState() != $.UIState.NOT_EDIT){
			return ""; 
		}
	};
	
	ctrl.extendViewModel = function(viewModel, app, preProcessInfo){
		//preProcessInfo包含有模板对外提供的用于帮助设置预处理信息的参数，节点开发可以从中获取当前模板组件中使用的dataTableID，
		//而不是通过写死的方式设置dataTableID
		
		//options 模板中u-meta内的控件属性
		ctrl.viewModel  = viewModel;
		ctrl.funcnode = getRequest().nodecode;
		app.setAdjustMetaFunc(function(options){
			var dataTableIDs = preProcessInfo.dataTableIDs;
			ctrl.dataTableIDs = dataTableIDs;
			if(dataTableIDs.length > 0){
				for(var x in dataTableIDs){
					if(typeof dataTableIDs[x] == 'string') {
						if(dataTableIDs[x].indexOf('head') >= 0) {
							ctrl.dataTableIDs.head = dataTableIDs[x];
						}else if(dataTableIDs[x].indexOf('body') >= 0) {
							ctrl.dataTableIDs.body = dataTableIDs[x]; 
						}
					}
					if (options['type']  === 'grid'  &&  options['id'] && options['id'] === dataTableIDs[x]){
//						options['multiSelect'] = true
						options['editable'] = true
					}
					
				}
			}
			if(options.type =='gridColumn' && options.columns) {
				// 隐藏列表视图上不显示字段
				for(var i=0;i < options.columns.length;i++) {
					if(i > 8 && i != options.columns.length -1){
						options.columns[i].visible = false;
					}
				}
			}
		})
		if(ctrl.funcnode == '20080EBR'){
			var combodata_obj = viewModel.combodata_objtype;
			
			for(var i = 0;i<combodata_obj.length;i++){
				if(combodata_obj[i].name == '客户'){
					viewModel.combodata_objtype.remove(i);
				}
			}
		}
		if(ctrl.funcnode == '20060GBR'){
			var combodata_obj = viewModel.combodata_objtype;
			
			for(var i = 0;i<combodata_obj.length;i++){
				if(combodata_obj[i].name == '供应商'){
					viewModel.combodata_objtype.remove(i);
				}
			}
		}
		//设置参照初始化参数
		setPageInitRefCfg(viewModel, app);
	}
	
	ctrl.init = function(viewModel,app, billForm, appModel){
		ctrl.app =app;
		ctrl.billForm = billForm;
		ctrl.headTable = app.getDataTable(ctrl.dataTableIDs.head);
		ctrl.bodyTable = app.getDataTable(ctrl.dataTableIDs.body);
		ctrl.pkFieldName = getPkFieldName();
		ctrl.pkBodyRow = getPkBodyRow();
		var openbillid = ctrl.openbillid;
		appModel.addListener(ctrl);
		
		ctrl.router.init();
		ctrl.bodyTotal();
	}
	
	ctrl.beforeInit = function(app, appModel, billForm){
		//注册表体行编辑按钮
		var actionutil = new ActionUtils(billForm, ctrl);
		ctrl.gridOperRenderType = actionutil.getActions();
		ctrl.fullGridOperRenderType = actionutil.getDetailActions();
	}
	
	ctrl.afterInit = function(viewModel, app){
		
		viewModel[ctrl.dataTableIDs.body].on('delete',function(event){
			headToltal();
		}).on('insert',function(event){
			if(appModel.uiState != $.UIState.NOT_EDIT && event.index != ctrl.bodyTable.getAllRows().length-1){
				ctrl.bodyTable.getAllRows()[event.index].setValue(ctrl.pkBodyRow,null);
			} 
			headToltal();
		}).on('money_de.valueChange',function(event){
			headToltal();
		}).on('money_cr.valueChange',function(event){
			headToltal();
		}).on('focus',function(){
				var rowdata = ctrl.bodyTable.getCurrentRow().data;
				var recRefparam = {};
				var payRefparam = {};
				var taxcodeidparam = {};
				var costcenterparam = {}; 
				var cashaccountRefparam = {};
				var checknoRefparam = {};
				
				
				if(ctrl.funcnode == '20080PBR' || ctrl.funcnode == '20080EBR'){
					if(ctrl.bodyTable.meta['recaccount'].refparam){
						recRefparam = JSON.parse(ctrl.bodyTable.meta['recaccount'].refparam);
					}
					if(ctrl.bodyTable.meta['payaccount'].refparam){
						payRefparam = JSON.parse(ctrl.bodyTable.meta['payaccount'].refparam);
					}
					if(rowdata.supplier.value){
						recRefparam.pk_cust = rowdata.supplier.value;					
					}
				}else{
					if(ctrl.bodyTable.meta['payaccount'].refparam){
						recRefparam = JSON.parse(ctrl.bodyTable.meta['payaccount'].refparam);
					}
					if(ctrl.bodyTable.meta['recaccount'].refparam){
						payRefparam = JSON.parse(ctrl.bodyTable.meta['recaccount'].refparam);
					}
					if(rowdata.customer.value){
						recRefparam.pk_cust = rowdata.customer.value;					
					}
				}
				if(ctrl.bodyTable.meta['cashaccount'] && ctrl.bodyTable.meta['cashaccount'].refparam){
					cashaccountRefparam = JSON.parse(ctrl.bodyTable.meta['cashaccount'].refparam);
				}
				if(ctrl.bodyTable.meta['taxcodeid']){
					if(ctrl.bodyTable.meta['taxcodeid'].refparam){
						taxcodeidparam = JSON.parse(ctrl.bodyTable.meta['taxcodeid'].refparam);
					}
				}

				if(ctrl.headTable.meta['costcenter']){
					if(ctrl.headTable.meta['costcenter'].refparam){
						costcenterparam = JSON.parse(ctrl.bodyTable.meta['costcenter'].refparam);
					}
				}

				if(rowdata.objtype.value){
					recRefparam.objtype = rowdata.objtype.value;					
				}
				if(rowdata.pk_currtype.value){
					recRefparam.pk_currtype = rowdata.pk_currtype.value;					
				}
				if(rowdata.pk_currtype.value){
					payRefparam.pk_currtype = rowdata.pk_currtype.value;					
				}
				if (ctrl.headTable.getCurrentRow().data.pk_pcorg_v.value) {
					costcenterparam.pk_pcorg_v = ctrl.headTable.getCurrentRow().data.pk_pcorg_v.value;
					ctrl.bodyTable.setMeta('costcenter','refparam',JSON.stringify(costcenterparam));
				};
				if(ctrl.funcnode == '20080PBR' || ctrl.funcnode == '20080EBR'){
					ctrl.bodyTable.setMeta('recaccount','refparam',JSON.stringify(recRefparam));
					ctrl.bodyTable.setMeta('payaccount','refparam',JSON.stringify(payRefparam));
					if(ctrl.funcnode == '20080PBR'){
						taxcodeidparam.supplier = rowdata.supplier.value;
						taxcodeidparam.taxcountryid = ctrl.headTable.getCurrentRow().data.taxcountryid.value;
						var buysell = rowdata.buysellflag.value;
						var buysellStr = "";
						if (typeof buysell == 'number') {
							buysellStr = buysell.toString();
						}else{
							buysellStr = buysell;
						};
						taxcodeidparam.buysellflag = buysellStr;
						ctrl.bodyTable.setMeta('taxcodeid','refparam',JSON.stringify(taxcodeidparam));
					}else{
						if(rowdata.pk_currtype.value){
							cashaccountRefparam.pk_moneytype = rowdata.pk_currtype.value;
							ctrl.bodyTable.setMeta('cashaccount','refparam',JSON.stringify(cashaccountRefparam));
						}
					}
				}else{
					ctrl.bodyTable.setMeta('payaccount','refparam',JSON.stringify(recRefparam));
					ctrl.bodyTable.setMeta('recaccount','refparam',JSON.stringify(payRefparam));
					if(ctrl.funcnode == '20060RBR'){
						taxcodeidparam.supplier = rowdata.customer.value;
						taxcodeidparam.taxcountryid = ctrl.headTable.getCurrentRow().data.taxcountryid.value;
						var buysell = rowdata.buysellflag.value;
						var buysellStr = "";
						if (typeof buysell == 'number') {
							buysellStr = buysell.toString();
						}else{
							buysellStr = buysell;
						};
						taxcodeidparam.buysellflag = buysellStr;	
						ctrl.bodyTable.setMeta('taxcodeid','refparam',JSON.stringify(taxcodeidparam));
					}else{
						if(rowdata.pk_currtype.value){
							cashaccountRefparam.pk_moneytype = rowdata.pk_currtype.value;
							ctrl.bodyTable.setMeta('cashaccount','refparam',JSON.stringify(cashaccountRefparam));
						}
					}
				}
				if((ctrl.funcnode == '20080EBR' || ctrl.funcnode == '20060GBR')){
					if(rowdata.checktype.value){
						ctrl.bodyTable.getCurrentRow().setMeta("checkno","enable",true);
					}else{
						ctrl.bodyTable.getCurrentRow().setMeta("checkno","enable",false);
					}
					
					if(ctrl.bodyTable.meta['checkno'].refparam){
						checknoRefparam = JSON.parse(ctrl.bodyTable.meta['checkno'].refparam);
					}
                    if(rowdata.pk_currtype.value){
						checknoRefparam.pk_currtype = rowdata.pk_currtype.value;
					}else{
						checknoRefparam.pk_currtype = "";
					}
					if(rowdata.checktype.value){
						checknoRefparam.fbmbilltype = rowdata.checktype.value;
					}else{
						checknoRefparam.fbmbilltype = "";
					}
					if(rowdata.checkno.value){
						checknoRefparam.pk_register = rowdata.checkno.value;
					}else{
						checknoRefparam.pk_register = "";
					}

					ctrl.bodyTable.setMeta('checkno','refparam',JSON.stringify(checknoRefparam));
				}
		});

		var param = {};
		param.tradetype = getRequest().tradetype;
		param.pk_billtemplet = ctrl.app.getEnvironment().clientAttributes["pk_billtemplet"];
		param.openbillid = ctrl.openbillid;
		var source = getRequest().from;
		$.showLoading();
		if(param.openbillid != null && param.openbillid !="" && param.openbillid !=undefined){
			$.ajax({ 
					type: "GET", 
					url: "/iwebap/viewbill_ctr/viewbill?time="+ new Date().getTime(),  
					data:param, 
					dataType: "json" ,
					success: function(result) {
						$.hideLoading();
						if(result["success"] == "true"){
							openbillid = result.head.primarykey;
							var pk_org = result.head.pk_org;
							ctrl.isLoadingData = true
							ctrl.mainOrg.setRowSelect(0);
							ctrl.mainOrg.getCurrentRow().setValue("pk_org",pk_org.pk);
							ctrl.mainOrg.getCurrentRow().setMeta("pk_org",'display',pk_org.name);
							setRefOrg(pk_org.pk,ctrl.app.getDataTables());
							ctrl.isLoadingData = false
							if(appModel.uiState == $.UIState.NOT_EDIT){
								ctrl.mainOrg.setEnable(false);
								if(result.head.approvestatus.pk == '1'){
									$("#bfEdit").hide();
									$("#bfDelete").hide();
								}
								if(result.head.billstatus.pk == '1'){
									$("#imageupload").hide();
								}
								if(source == 'ssc'){
									$("#bfAdd").hide();
									$("#imageupload").hide();
									var $buttons = $("#bfAdd").siblings();
									for(var i = 0;i<$buttons.length-1;i++){
										if($buttons[i].style.display != 'none'){
											$('#'+$buttons[i].id).removeClass('preline');
											break;
										}
									}
								}
							}else{
								ctrl.mainOrg.setEnable(true);
							}
							appModel.directlyAdd(result);
							var brows = ctrl.app.dataTables.body_1bodys.rows();
							for(var i = 0 ;i < brows.length ;i++){
								if(brows[i].data.top_billtype){
									var top_billtype =  brows[i].data.top_billtype.value;
									var pk_billtype = brows[i].data.pk_billtype.value;
									if(("F0" == top_billtype || "F1" == top_billtype || "F2" == top_billtype || "F3" == top_billtype)
										&& pk_billtype != top_billtype){
										var settlecurr = brows[i].data.settlecurr.value;
										var currtype = brows[i].data.pk_currtype.value;
										if(currtype != settlecurr){
											brows[i].setMeta("settlemoney","enable",true);
										}else{
											brows[i].setMeta("settlemoney","enable",false);
										}
									}
								}
							}
							getApproveInfosNew(openbillid,param.tradetype,$("#approvalinfos"),pk_org.pk,{filtToDoAction:hideUseLessButtons});
							translateLang();
							

						}else{
							$.showMessageDialog({type:"info",title:"未查找到相应单据！",msg:result["message"],backdrop:true});
						}
		        	}
				});
		}
	}
	
	ctrl.processOrgChanged = function(event){
		if(appModel.uiState == $.UIState.NOT_EDIT){
			return;
		}
		var dataTables = ctrl.app.getDataTables();
		if(event.newValue != event.oldValue){
			setRefOrg(event.newValue,dataTables);
			if(ctrl.bodyTable.getAllRows().length > 1){
				ctrl.bodyTable.removeAllRows();
				ctrl.bodyTable.createEmptyRow();
				ctrl.bodyTable.setRowSelect(0);
			}
			if(event.newValue == null || event.newValue == ""){
				ctrl.headTable.setEnable(false);
				ctrl.bodyTable.setEnable(false);
				$("#body_1bodys_content_edit_menu_add").attr('disabled', 'disabled');
				return;
			}
			ctrl.headTable.setEnable(true);
			ctrl.bodyTable.setEnable(true);
			$("#body_1bodys_content_edit_menu_add").removeAttr("disabled");
			ctrl.app.serverEvent().addDataTables(ctrl.dataTableIDs).setEvent(event).fire({
				ctrl:'ArapLinkAttrController',
				method:'valueChanged',
				async:false,
				success: function(data){
					ctrl.bodyTable.removeAllRows();
					ctrl.bodyTable.createEmptyRow();
					ctrl.bodyTable.setRowSelect(0);
				},
				error: function(data){
					ctrl.headTable.setEnable(false);
				    ctrl.bodyTable.setEnable(false);
					$.showMessageDialog({type:"info",title:"提示",msg:data["message"],backdrop:true});
				}
			});
			dealInitNotEdit();
		}
	}
	
	function registerEvent(){
		var dataTables = ctrl.app.getDataTables();
		for(var tid in dataTables){
			dataTables[tid].on('valueChange',function(event){
				if(checkLinkAttr(event)){
					if(event.dataTable == ctrl.dataTableIDs.head){
						ctrl.app.serverEvent().addDataTables(ctrl.dataTableIDs,'all').setEvent(event).fire({
							ctrl:'ArapLinkAttrController',
							method:'valueChanged',
							success: function(data){
							}
						});		
					}else{
						ctrl.app.serverEvent().addDataTables(ctrl.dataTableIDs).setEvent(event).fire({
							ctrl:'ArapLinkAttrController',
							method:'valueChanged',
							async : false,
							success: function(data){
								if(event.field == 'checkno' && ctrl.funcnode=='20060GBR'){
									var checknoRefparam = {};
									if(ctrl.bodyTable.meta['checkno'].refparam){
										checknoRefparam = JSON.parse(ctrl.bodyTable.meta['checkno'].refparam);
									}
									checknoRefparam.pk_register = event.newValue;
									ctrl.bodyTable.setMeta('checkno','refparam',JSON.stringify(checknoRefparam));
								}
								//处理拉单数据编辑币种控制收、付款金额可编辑性
								if(event.field == 'pk_currtype'){
									var currrow = ctrl.app.dataTables.body_1bodys.getCurrentRow();
									if(currrow.data.top_billtype){
										var top_billtype =  currrow.data.top_billtype.value;
										var pk_billtype = currrow.data.pk_billtype.value;
										if(("F0" == top_billtype || "F1" == top_billtype || "F2" == top_billtype || "F3" == top_billtype)
											&& pk_billtype != top_billtype){
											var settlecurr = currrow.data.settlecurr.value;
											var currtype = currrow.data.pk_currtype.value;
											if(currtype != settlecurr){
												currrow.setMeta("settlemoney","enable",true);
											}else{
												currrow.setMeta("settlemoney","enable",false);
											}
										}
									}
								}
							}
						});					
					}
				}
			});
		}
	}
	
	ctrl.getValue = function(){
		return ctrl.billForm.getValue();
	}
	
	function checkLinkAttr(event){
		var headLinkAttrs = ["pk_psndoc"/*业务员*/,"pk_deptid_v"/*部门版本*/,"sett_org_v"/*结算财务组织版本主键*/,"pu_org_v"/*业务单元版本*/,
							 "pu_deptid_v"/*部门版本*/,"pu_psndoc"/*人员*/,"bankrollprojet"/*资金计划项目*/,"pk_deptid"/*部门*/,"pk_currtype"/*币种*/,
							 "customer"/*客户*/,"objtype"/*往来对象*/,"cashitem"/*现金流量项目*/,"payaccount"/*付款银行账户*/,"recaccount"/*收款银行账户*/,
							 "checkelement"/*责任核算要素*/,"cashaccount"/*现金账户*/,"subjcode"/*科目*/,"pk_balatype"/*结算方式*/,"supplier"/*供应商*/,
							 "checktype"/*票据类型*/,"billdate"/*单据日期*/,"material"/*物料*/,"rate"/*本币汇率*/,"busidate"/*起算日期*/,"taxcountryid"/*报税国*/,"pk_pcorg_v"/*利润中心*/,"pk_subjcode"/*收支项目*/];
		var bodyLinkAttrs = ["pk_psndoc"/*业务员*/,"pk_currtype"/*币种*/,"customer"/*客户*/,"material"/*物料*/,"objtype"/*往来对象*/,"pk_deptid_v"/*部门版本*/,
							 "billdate"/*单据日期*/,"taxcodeid"/*税码*/,"pay"/**/,"paycurr"/**/,"payrate"/**/,"quantity_cr"/*贷方数量*/,"local_tax_cr"/*贷方本币税金*/,
							 "money_cr"/*贷方原币金额*/,"local_notax_cr"/*贷方本币无税金额*/,"notax_cr"/*贷方原币无税金额*/,"local_money_cr"/*贷方本币金额*/,
							 "quantity_de"/*借方数量*/,"local_tax_de"/*借方本币税金*/,"money_de"/*借方原币金额*/,"local_notax_de"/*借方本币无税金额*/,
							 "notax_de"/*借方原币无税金额*/,"local_money_de"/*借方本币金额*/,"rate"/*本币汇率*/,"taxrate"/*税率*/,"price"/*单价*/,"taxprice"/*含税单价*/,
							 "taxtype"/*扣税类别*/,"nosubtax"/*不可抵扣税额*/,"nosubtaxrate"/*不可抵扣税率*/,"groupcrebit"/*集团本币金额(贷方)*/,
							 "groupdebit"/*集团本币金额(借方)*/,"globalcrebit"/*全局本币金额(贷方)*/,"globaldebit"/*全局本币金额(借方)*/,"supplier"/*供应商*/,
							 "checktype"/*票据类型*/,"settlecurr"/*收款币种*/,"rececountryid"/*收货国*/,"sendcountryid"/*发货国*/,"checkno"/*票据号*/];
		var type = event.dataTable.split("_")[0];
		if(type == "headform"){
			return headLinkAttrs.indexOf(event.field) > -1;
		}
		if(type == "body"){
			return bodyLinkAttrs.indexOf(event.field) > -1;
		}
		return false;
	}
	
	function setPageInitRefCfg(){
	var orgcfg = {};
	var accountcfg = {};
	var subjcodecfg = {};
	var scommentcfg = {};
	var freecustcfg = {};
	var cashcfg = {};
	var taxcodecfg = {};
	var costcentercfg = {};
	var psndoccfg = {};
	var deptvcfg = {};
	var deptcfg = {};
	var checknocfg = {};
	var cashAccountcfg = {};
	var payableBillExtendModel = "nc.web.arap.bill.payable.PayableBillWebRefModel";
    var payBillExtendModel = "nc.web.arap.bill.pay.PayBillWebRefModel";
    var recBillExtendModel = "nc.web.arap.bill.rec.RecBillWebRefModel";
    var gatherBillExtendModel = "nc.web.arap.bill.gather.GatherBillWebRefModel";

	orgcfg.isMultiSelectedEnabled = false;
	accountcfg.refUIType = 'RefGrid';
	subjcodecfg.isClassDoc = true;
	scommentcfg.autoCheck = false;
	freecustcfg.refUIType = 'RefGrid';
	var extendModel = "";
	if(ctrl.funcnode=='20080PBR'){
		extendModel = payableBillExtendModel;
		taxcodecfg.refModelHandlerClass = extendModel;
		ctrl.viewModel.body_1bodys.setMeta("taxcodeid","refcfg",JSON.stringify(taxcodecfg));
	}else if(ctrl.funcnode=='20080EBR'){
		extendModel = payBillExtendModel;
		checknocfg.refModelHandlerClass = extendModel;
		ctrl.viewModel.body_1bodys.setMeta("checkno","refcfg",JSON.stringify(checknocfg));
		cashAccountcfg.refModelHandlerClass = extendModel;
		ctrl.viewModel.headform.setMeta("cashaccount","refcfg",JSON.stringify(cashAccountcfg));
		ctrl.viewModel.body_1bodys.setMeta("cashaccount","refcfg",JSON.stringify(cashAccountcfg));
	}else if(ctrl.funcnode=='20060RBR'){
		extendModel = recBillExtendModel;
		taxcodecfg.refModelHandlerClass = extendModel;
		ctrl.viewModel.body_1bodys.setMeta("taxcodeid","refcfg",JSON.stringify(taxcodecfg));
	}else if(ctrl.funcnode=='20060GBR'){
		extendModel = gatherBillExtendModel;
		checknocfg.refModelHandlerClass = extendModel;
		ctrl.viewModel.body_1bodys.setMeta("checkno","refcfg",JSON.stringify(checknocfg));
		cashAccountcfg.refModelHandlerClass = extendModel;
		ctrl.viewModel.headform.setMeta("cashaccount","refcfg",JSON.stringify(cashAccountcfg));
		ctrl.viewModel.body_1bodys.setMeta("cashaccount","refcfg",JSON.stringify(cashAccountcfg));
	}
	accountcfg.refModelHandlerClass = extendModel; 
	cashcfg.refModelHandlerClass = extendModel; 
	
	psndoccfg.refModelHandlerClass = extendModel;
	deptvcfg.refModelHandlerClass = extendModel;
	deptcfg.refModelHandlerClass = extendModel;
	
	costcentercfg.refModelHandlerClass = extendModel;
	ctrl.viewModel.headform.setMeta("costcenter","refcfg",JSON.stringify(costcentercfg));
	
	ctrl.mainOrg.setMeta("pk_org","refcfg",JSON.stringify(orgcfg));	
	ctrl.viewModel.headform.setMeta("payaccount","refcfg", JSON.stringify(accountcfg));
	ctrl.viewModel.headform.setMeta("recaccount","refcfg",JSON.stringify(accountcfg));
	ctrl.viewModel.headform.setMeta("subjcode","refcfg",JSON.stringify(subjcodecfg));
	ctrl.viewModel.headform.setMeta("scomment","refcfg",JSON.stringify(scommentcfg));
	ctrl.viewModel.headform.setMeta("pk_psndoc","refcfg",JSON.stringify(psndoccfg));
	ctrl.viewModel.headform.setMeta("pk_deptid_v","refcfg",JSON.stringify(deptvcfg));
	ctrl.viewModel.headform.setMeta("pk_deptid","refcfg",JSON.stringify(deptcfg));
	
	ctrl.viewModel.body_1bodys.setMeta("payaccount","refcfg",JSON.stringify(accountcfg));
	ctrl.viewModel.body_1bodys.setMeta("recaccount","refcfg",JSON.stringify(accountcfg));
	ctrl.viewModel.body_1bodys.setMeta("subjcode","refcfg",JSON.stringify(subjcodecfg));
	ctrl.viewModel.body_1bodys.setMeta("scomment","refcfg",JSON.stringify(scommentcfg));
	ctrl.viewModel.body_1bodys.setMeta("pk_psndoc","refcfg",JSON.stringify(psndoccfg));
	ctrl.viewModel.body_1bodys.setMeta("pk_deptid_v","refcfg",JSON.stringify(deptvcfg));
	ctrl.viewModel.body_1bodys.setMeta("pk_deptid","refcfg",JSON.stringify(deptcfg));
	
	if(ctrl.funcnode == "20060RBR" || ctrl.funcnode == "20080EBR"){
		ctrl.viewModel.body_1bodys.setMeta('money_de','regExp',"^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$|^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$");
	}else{
		ctrl.viewModel.body_1bodys.setMeta('money_cr','regExp',"^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$|^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$");
	}
	
	}
	
	function loadData(){
		ctrl.dataTableIDs.push("mainOrg");
		ctrl.app.dataTables.mainOrg = ctrl.mainOrg;
		$.showLoading();
		ctrl.app.serverEvent().addDataTables(ctrl.dataTableIDs).addParameter("uistate",appModel.getUIState()).fire({
			ctrl:'ArapDefDataController',
			method:'loadData',
			success: function(data){
				$.hideLoading();
				var defOrg = ctrl.headTable.getCurrentRow().data.pk_org.value;
				if(defOrg){
					ctrl.mainOrg.getCurrentRow().setValue("pk_org",defOrg);
					ctrl.mainOrg.getCurrentRow().setMeta("pk_org",'display',ctrl.headTable.getCurrentRow().data.pk_org.meta.display);
					setRefOrg(defOrg,ctrl.app.dataTables);
				}else{
					ctrl.headTable.setEnable(false);
					ctrl.bodyTable.setEnable(false);					
					$("#body_1bodys_content_edit_menu_add").attr('disabled', 'disabled');
				}
				//if(appModel.uiState != $.UIState.ADD){
				ctrl.bodyTable.removeRow(ctrl.bodyTable.getAllRows().length-1);
				ctrl.bodyTable.createEmptyRow();
				ctrl.bodyTable.setRowSelect(ctrl.bodyTable.getAllRows().length-1);
				//}
				dealInitNotEdit();
				ctrl.org_local_currtype = data;
				translateLang();
			 },
			error: function(data){
				$.hideLoading();
				ctrl.headTable.setEnable(false);
			    ctrl.bodyTable.setEnable(false);
				$.showMessageDialog({type:"info",title:"提示",msg:data["message"],backdrop:true});
			 }
		})
	}
	
	function setRefOrg(orgValue,dataTables){
		for(var dt in dataTables){
			if(dt != 'mainOrg'){
				var meta = dataTables[dt].meta;
				for(var key in meta){
					if(meta[key].refmodel){
						var param = {};
						if(meta[key].refparam){
							param = JSON.parse(meta[key].refparam);	
						}
						param.pk_org = orgValue;
						dataTables[dt].setMeta(key,'refparam',JSON.stringify(param));
					}
				}
			}
		}
	}
	
	function dealInitNotEdit(){
		if((ctrl.funcnode == '20080EBR' || ctrl.funcnode == '20060GBR')){
			if(ctrl.bodyTable.getCurrentRow().data.checktype){
				var ctValue = ctrl.bodyTable.getCurrentRow().data.checktype.value;
				if(ctValue == null){
					ctrl.bodyTable.getCurrentRow().setMeta("checkno","enable",false);
				}
			}
		}
	}
	
	function headToltal(){
		var nodecode = getRequest().nodecode;
		var len = ctrl.bodyTable.getAllRows().length; //表体行个数
		var total = new ctrl.bignumber(0); //原币金额
		var local_money_total = new ctrl.bignumber(0); //组织本地金额
		var grouplocal_total = new ctrl.bignumber(0); //集团本币金额
		var globallocal_total = new ctrl.bignumber(0); //全局本币金额
		var field = "money_cr";
		/**
		 * 20060GBR 收款单
		 * 20060RBR 应收单
		 * 20080PBR 应付单
		 * 20080EBR 付款单
		 */
		var local_money_bodyfield = "local_money_cr"; //组织本币金额
		var groupcrebit_bodyfield = "groupcrebit"; //集团本币金额
		var globalcrebit_bodyfield = "globalcrebit"; //全局本币金额
		if(nodecode == "20060RBR" || nodecode == "20080EBR"){
			field = "money_de";
			local_money_bodyfield = "local_money_de";
			groupcrebit_bodyfield = "groupdebit";
			globalcrebit_bodyfield = "globaldebit";
		}
		if(appModel.uiState == $.UIState.NOT_EDIT){
			return;
		}
		for(var i=0; i < len; i++){
			if(i == len-1) continue;
			var row = ctrl.bodyTable.getAllRows()[i];
			if(!row.data[field]) continue;
			var money_cr = new ctrl.bignumber(row.data[field].value);
			var local_money_cr = new ctrl.bignumber(row.data[local_money_bodyfield].value);
			var groupcrebit = new ctrl.bignumber(row.data[groupcrebit_bodyfield].value);
			var globalcrebit = new ctrl.bignumber(row.data[globalcrebit_bodyfield].value);
			total = new ctrl.bignumber(total).plus(new ctrl.bignumber(money_cr));
			local_money_total = new ctrl.bignumber(local_money_total).plus(new ctrl.bignumber(local_money_cr));
			grouplocal_total = new ctrl.bignumber(grouplocal_total).plus(new ctrl.bignumber(groupcrebit));
			globallocal_total = new ctrl.bignumber(globallocal_total).plus(new ctrl.bignumber(globalcrebit));
		}
		//修改表头金额信息
		ctrl.headTable.getCurrentRow().setValue("money",total.toString());//原币金额
		ctrl.headTable.getCurrentRow().setValue("local_money",local_money_total.toString());//组织本币金额
		ctrl.headTable.getCurrentRow().setValue("grouplocal",grouplocal_total.toString());//集团本币金额
		ctrl.headTable.getCurrentRow().setValue("globallocal",globallocal_total.toString());//全局本币金额
	}
	
	//增加表体合计
	ctrl.bodyTotal = function(){
		var dataTableID = ctrl.dataTableIDs.body;
		var rowTotal = 0;
		var dataTable = ctrl.bodyTable;
		
		ctrl.viewModel.totalBodyNum = function(dataTable){
			return ko.pureComputed({
			
				read: function(){
					if(appModel.uiState == 'not_edit'){
						rowTotal = dataTable.rows().length;
					}else{
						rowTotal = dataTable.rows().length-1
					}
					return rowTotal;
				},
				write: function(value){
					
				},
				owner: this
			});
		}
		
		ctrl.viewModel.totalBody = ko.observable(0)
		ctrl.totalMoneyChange = function() {
			var rows = dataTable.getAllRows();
			var totalNum = new ctrl.bignumber(0.00);
			var maxPrecision = 0;
			for(var i=0; i < rows.length; i++){
				var field = 'money_cr';
				if(ctrl.funcnode == "20060RBR" || ctrl.funcnode == "20080EBR"){
					field = "money_de";
				}
				var rowPrecision = 0;
				if(rows[i].data[field].meta && rows[i].data[field].meta.precision != undefined){
					rowPrecision = rows[i].data[field].meta.precision;
				}else if(dataTable.meta[field] && dataTable.meta[field].precision != undefined){
					rowPrecision = dataTable.meta[field].precision;
				}
				maxPrecision = rowPrecision > maxPrecision?rowPrecision:maxPrecision;
				totalNum = new ctrl.bignumber(totalNum).plus(new ctrl.bignumber(rows[i].getValue(field)));
			}
			totalNum = totalNum.toFormat(maxPrecision);
			ctrl.viewModel.totalBody(totalNum);
		}
		
		dataTable.on('delete',ctrl.totalMoneyChange).on('insert',ctrl.totalMoneyChange).on('money_de.valueChange',ctrl.totalMoneyChange).on('money_cr.valueChange',ctrl.totalMoneyChange);		
			
		ctrl.afterAddSave = ctrl.totalMoneyChange;
		ctrl.afterSave = ctrl.totalMoneyChange;	
		$("#"+ dataTableID + "_Desc").html('共&nbsp;&nbsp;<span data-bind="text:totalBodyNum('+ dataTableID + ')"></span>&nbsp;&nbsp;条,&nbsp;&nbsp;合计：&nbsp;&nbsp;<span class="test" data-bind="text:totalBody"></span>');
		translateLang();
	}	
	
	function getPkFieldName(){
		var pkFieldName;
		if(ctrl.funcnode=='20080PBR'){
			pkFieldName = 'pk_payablebill'; 
		}else if(ctrl.funcnode=='20080EBR'){
			pkFieldName = 'pk_paybill'; 
		}else if(ctrl.funcnode=='20060RBR'){
			pkFieldName = 'pk_recbill'; 
		}else if(ctrl.funcnode=='20060GBR'){
			pkFieldName = 'pk_gatherbill'; 
		}
		return pkFieldName;
	}
	
	function getPkBodyRow(){
		var pkFieldName;
		if(ctrl.funcnode=='20080PBR'){
			pkFieldName = 'pk_payableitem'; 
		}else if(ctrl.funcnode=='20080EBR'){
			pkFieldName = 'pk_payitem'; 
		}else if(ctrl.funcnode=='20060RBR'){
			pkFieldName = 'pk_recitem'; 
		}else if(ctrl.funcnode=='20060GBR'){
			pkFieldName = 'pk_gatheritem'; 
		}
		return pkFieldName;
	}
	
	var hideUseLessButtons = function (action_info){
		var acts = action_info.actions;
		var ret  = [];
		$.each(acts, function(i, e){
		 	if(e.action_code != 'recall') {
		  		ret.push(e);
	  		}
		});
		action_info.actions = ret;
		return action_info;
	}
	
	ctrl.handleEvent = function(event){
		if ($.AppEventConst.UISTATE_CHANGED == event.type) {
			if (appModel.getUIState() == $.UIState.ADD || appModel.getUIState() == $.UIState.EDIT) {
				$("#xingFlag").css("display","inline");
				$("#inputListFlag").css("display","table-cell");
				$("#mainorg_pk_org").removeClass('not_edit_state');
				//加载初始化数据
				loadData();
				// 添加联动事件监听
				registerEvent();
			}else{
				$("#xingFlag").css("display","none");
				$("#inputListFlag").css("display","none");
				$("#mainorg_pk_org").addClass('not_edit_state');
				translateLang();
			} 
		}
	}
	
	
	ctrl.add = function(){
		location.hash = '/add';
	}
	
	ctrl.edit = function(e){
		var tradetype = ctrl.app.getEnvironment().clientAttributes["tradetype"];
		var openbillid = ctrl.viewModel.headform.getCurrentRow().data[ctrl.pkFieldName].value;
		$.ajax({
			type: 'GET',
			url:'/iwebap/billsave_ctr/editright?tradetype='+tradetype+'&openbillid='+openbillid + "&time=" +(new Date()).getTime(),
			dataType: 'json',
			success: function (result) {	
                if(result["success"] == "true"){	
		            appModel.setUIState($.UIState.EDIT);
		            $("#approvalinfos").hide();
		            ctrl.mainOrg.setEnable(false);
				}else{
					$.showMessageDialog({type:"info",title:"失败",msg:result["message"],backdrop:true});
				}
			}
		});	
	}
	
	
	
	
	ctrl.del = function(e){
			$.showMessageDialog({type:"warning",title:"Tips",msg:"The deletion is unrecoverable. Do you really want to delete the document?",backdrop:true,
				okfn:function(){
					var data = ctrl.getValue();
					var param = {};
					param.openbillid = data.head[ctrl.pkFieldName];
					param.tradetype = data.head.pk_tradetype;
					param.ts = data.head.ts;
					// 删除操作
					$.showLoading();
					$.ajax({ 
						type: "GET", 
						url: "/iwebap/viewbill_ctr/delbill?time="+ new Date().getTime(), 
						data:param, 
						dataType: "json" ,
						success: function(result) {
							$.hideLoading();
							if(result["success"] == "true"){
								 window.close();
							}else{
								$.showMessageDialog({type:"info",title:"Failed to delete",msg:result["message"],backdrop:true});
							}
	        			},
					});
				}}
			);
		}
	ctrl.print =function(e){
		var data = ctrl.getValue();
		var param = {};
		param.openbillid = data.head[ctrl.pkFieldName];
		param.tradetype = ctrl.app.getEnvironment().clientAttributes["tradetype"];
		param.funcode = ctrl.app.getEnvironment().clientAttributes["nodecode"];
		param.nodekey = ctrl.app.getEnvironment().clientAttributes["tradetype"];
		$.showLoading();
		$.ajax({
	            type: 'GET',
	            url: '/iwebap/billprint/print?time='+ new Date().getTime(), 
	            data: param,
	            dataType: "json" ,
	            success: function (result) {
	                $.hideLoading();
					if(result["success"] == "true"){
						var filePath = result.filepath;
						window.open('/iwebap/printFileService/download?filePath='+filePath,"_blank","resizable=yes,titlebar=no,location=no,toolbar=no,menubar=no;top=100"); 
					}else{
						$.showMessageDialog({type:"info",title:"提示",msg: result["message"],backdrop:true});
					}
	            }
	        });
	}
	ctrl.imageupload =function(e){
				var param = {};
				param.openbillid = ctrl.viewModel.headform.getCurrentRow().data[ctrl.pkFieldName].value;
				var isCreating = false;
				if(param.openbillid==undefined){
					isCreating= true;
				}
				param.billtype = ctrl.app.getEnvironment().clientAttributes["tradetype"];
				param.isCreating = isCreating;
				param.pk_org = ctrl.viewModel.headform.getCurrentRow().data["pk_org"].value;
				$.showLoading();
			 	$.ajax({
						type: 'GET',
						url: '/iwebap/billshow_ctr/imageUpload?time='+ new Date().getTime(), 
						data: param,
						dataType: "json" ,
		                success: function (data) {
	                		$.hideLoading();
		                	if(data["success"] == "true"){
								ctrl.viewModel.headform.getCurrentRow().setValue(data["pkfieldName"],data[data["pkfieldName"]]);
		                		var tradetypeName = ctrl.viewModel.headform.getCurrentRow().data.pk_tradetypeid.meta.display;
								var imagurl =  data["imagurl"] + "&modulename=应收应付&tradtypeName="+tradetypeName+"&billNo= ";
								var ipaddr =  data["ipaddr"];
								imageuploadBrowserByUrl(ipaddr,imagurl); 
							}else{
								$.showMessageDialog({type:"info",title:"提示",msg: data["Msg"],backdrop:true});
							} 
				        	
		                }
		            });
	}
	ctrl.imageshow=function(e){
	    // 影像查看   
		$.ajax({
			type: 'GET',
            url: '/iwebap/billshow_ctr/getImagePara?time='+ new Date().getTime(),
            dataType: 'json',
            success: function (data) {
            	if(data["success"] == "true"){
					var imagurl =  "modulename=应收应付";
					imagurl = imagurl + "&pk_jkbx="+ctrl.openbillid;
					var bodylength = ctrl.viewModel.body_1bodys.getAllRows().length;
				 	for(var i=0;i<bodylength;i++){
	 					if(ctrl.viewModel.body_1bodys.getAllRows()[i].data["top_billid"].value){
							imagurl = imagurl + "+" + ctrl.viewModel.body_1bodys.getAllRows()[i].data["top_billid"].value
						}
					}
					imagurl = imagurl + "&userName=" + data["userName"];
					var ipaddr =  data["ipaddr"];
					imageshowBrowserByUrl(ipaddr,imagurl);
				}else{
					$.showMessageDialog({type:"info",title:"提示",msg: data["Msg"],backdrop:true});
				} 
			}
		}); 
	}
	
	ctrl.save =function(e){
		var data = ctrl.getValue();
		if(!data)
			return;
		var param = {};
		param.tradetype = ctrl.app.getEnvironment().clientAttributes["tradetype"];
		param.pk_billtemplet = ctrl.app.getEnvironment().clientAttributes["pk_billtemplet"];
		param.state = appModel.uiState;
		if(ctrl.isAlarmPassed == true){
			param.isAlarmPassed = ctrl.isAlarmPassed;
		}
		var dataJson = ko.utils.stringifyJson(data);
	    var compressType = '';
   		var compression = true;
   		if(compression){
			if(!iweb.browser.isIE8 && window.pako){
				dataJson = encodeBase64(window.pako.gzip(dataJson));
				compressType = 'gzip';
			}else{
				compression = false;
			}
   		}
		param.bill = dataJson;
		param.compressType = compressType;
		param.compression = compression;
		$.showLoading();
		var openbillid = "";
		$.ajax({ 
			type: "POST", 
			url: "/iwebap/billsave_ctr/savedata?time="+ new Date().getTime(), 
			data:param, 
			dataType: "json" ,
			success: function(result) {
				ctrl.isAlarmPassed = false;
				$.hideLoading();
				if(result["success"] == "true"){
					openbillid = result.head.primarykey;
					var pk_org = result.head.pk_org;
					if(appModel.getUIState() == $.UIState.ADD){
						appModel.setUIState($.UIState.NOT_EDIT);
						appModel.directlyAdd(result);
						location.hash = '/view/' + openbillid;
					}else if(appModel.getUIState() == $.UIState.EDIT){
						appModel.setUIState($.UIState.NOT_EDIT);
						appModel.update(result);
						ctrl.mainOrg.setEnable(false);
						$("#approvalinfos").show();
					} 
					if(result.head.approvestatus.pk == '1'){
						$("#bfEdit").hide();
						$("#bfDelete").hide();
						$("#imageupload").hide();
					}else{
						$("#bfEdit").show();
						$("#bfDelete").show();
						$("#imageupload").show();
					}
					getApproveInfosNew(openbillid,param.tradetype,$("#approvalinfos"),pk_org.pk,{filtToDoAction:hideUseLessButtons});
					translateLang();
					//getBillFlowInfos(openbillid,param.tradetype,result.head.billno.pk,$("#linkinfos"));
				}else{
					var type = result["type"]?result["type"]:"info";
					if(type == 'warning'){
						$.showMessageDialog({type:type,title:"录单失败",okfn:function(){
							ctrl.isAlarmPassed = true;
							ctrl.save();
						},msg:result["message"],backdrop:true});
					}else{
						$.showMessageDialog({type:type,title:"录单失败",msg:result["message"],backdrop:true});
					}						
				}
        	}
		});
	}
	ctrl.cancel =function(e){
		if(appModel.getUIState() == $.UIState.ADD){
			$.showMessageDialog({type:"warning",title:"Tips",msg:"Please confirm if you want to cancel?",backdrop:true,okfn:function(){			
				//关闭当前窗口
				window.close();
			}});
		}else if(appModel.getUIState() == $.UIState.EDIT){
			$.showMessageDialog({type:"warning",title:"Tips",msg:"Please confirm if you want to cancel?",backdrop:true,okfn:function(){
				//回到保存成功的浏览态
				appModel.setUIState($.UIState.NOT_EDIT);
				var event = {};
				event.type = $.AppEventConst.SELECTION_CHANGED;
				appModel.fireEvent(event);
				$("#approvalinfos").show();
			}});
		}
	}
		
	ctrl.afterEdit = function(){
		if(appModel.getUIState() == $.UIState.EDIT){
			if (ctrl.bodyTable.getCurrentRow().data.pk_currtype != undefined && ctrl.bodyTable.getCurrentRow().data.pk_currtype.value==ctrl.org_local_currtype) {
				ctrl.bodyTable.getCurrentRow().setMeta("rate","enable",false);
			}else{
				ctrl.bodyTable.getCurrentRow().setMeta("rate","enable",true);
			}
		}
	}

	
	
	ctrl.afterCopy = ctrl.afterEdit;
	
	ctrl.setupapp = function(funcViewModel,v_pub_billform){
		
		//注册节点需要的单据模板卡片界面
		var appModel = new BillManageModel();
		window.appModel = appModel;
		// 监听页面状态改变
		appModel.addListener(this);
		this.handleEvent = function(event){
			if ($.AppEventConst.UISTATE_CHANGED == event.type) {
				if (appModel.getUIState() == $.UIState.ADD || appModel.getUIState() == $.UIState.EDIT) {
					funcViewModel.isEditable(true);
				} else if(appModel.getUIState() == $.UIState.NOT_EDIT){
					funcViewModel.isEditable(false);
				}
			}
		}
		
		var app = $.createApp();
		try{
			app.init(funcViewModel,[document.getElementById("panel-heading"),document.getElementById("mainorgpanel"), document.getElementById("editBtn")]);
		}catch(e){
			console.log(e);
		}
		
		var dataTables = app.getDataTables();
		ctrl.mainOrg = dataTables["mainOrg"];
		
		for(var dtid in dataTables){
			dataTables[dtid].createEmptyRow();
			dataTables[dtid].setRowSelect(0);
			dataTables[dtid].on('valueChange', function(event){
				//主组织变动后，到卡片组件控制器中去发起远程处理联动
				ctrl.processOrgChanged(event);
			})
		}
			
	//		var templetUtils = new TempletUtils(app);
	//		var tradetype = getRequest().tradetype;
	//		var nodecode = getRequest().nodecode;
	//		var billCardView = templetUtils.queryBillForm(nodecode,tradetype);
	//	    var btid = billCardView.id;
	//		var btname="bf-"+ btid;
	//		var btURL = window.ctx + billCardView.url;
	//		window.tangram.register(btname, btURL);
	//		$(".cardpanel").empty();
	//		$(".cardpanel").html("<div class='"+btname+"'><"+btname+" params=''></"+btname+"></div>");
	//		ko.applyBindings({},$("."+btname)[0]);
	
		var pubBillform = new v_pub_billform()
		ko.applyBindings(pubBillform,$(".cardpanel")[0]);
		
		//注册卡片界面左下角的按钮
		var btns = [];
		funcViewModel.headButtongroups(btns);

		$('#back-to-top').backtop();
		$(document).scroll(function() {
			var $container = $('.submit-container')
			var $submit = $('.submit-container>.row-submit')
			if($container.length > 0 && $submit.length > 0) {
				var top = $container[0].getBoundingClientRect().top //元素顶端到可见区域顶端的距离
				var se = document.documentElement.clientHeight //浏览器可见区域高度。
				if(top <= se ) {
					if($submit.hasClass('row-submit-fixed')) {
						$submit.removeClass('row-submit-fixed').css('width', '100%');
					}
				} else {
					if(!$submit.hasClass('row-submit-fixed')) {
						$submit.addClass('row-submit-fixed').css('width', $('body > .container').width());
					}
				}
			}
		}).scroll()	
		

//		var btns =[actionutils.addBtn, actionutils.editBtn, actionutils.deleteBtn,actionutils.wfBtn];
		
//		var openbillid = getRequest()["openbillid"];
//        var billtype = getRequest()["tradetype"];
//        getApproveInfosNew("0001TY100000000031T8", "D3", $("#approvalinfos"));//流程信息
//      getBillFlowInfos(openbillid,billtype);//上下游信息联查信息
			}

	

   function translateLang() {
		var lang = getLanguagesByCookie("LA_K1");
    if(lang == 'simpchn'){
       lang = 'cn';
      }
    if(lang == 'english'){
      lang = 'en';
      }
   var option = {
   lng:lang,
   ns:{namespaces:['app'],defaultNs:'app'}
   };
   option.resGetPath = '/iwebap/arap/locales/'+option.lng+'/'+option.ns.defaultNs+'.json';
   option.postAsync = false;
   option.getAsync = false;
   option.fallbackLng = false; 
   ctrl.i18n = $.extend(true, {}, $.i18n || window.i18n)
   ctrl.i18n.init(option,function(t){
   	  $('.jbxx h4').text(t('title.basic_info'));
   	  $('.xxxx h4').text(t('title.details_info'));
   	  $('.mainorgpanel label').text(t('title.pk_orginfo'));
      $('#bfSave').text(t('btn.save'));
      $('#bfNoEdit').text(t('btn.cancel'));
      $('#bfAdd').text(t('btn.add'));
      $('#bfEdit').text(t('btn.edit'));
      $('#bfDelete').text(t('delete'));
      $('#bfPrint').text(t('btn.print'));
      $('#imageupload').text(t('btn.imageupload'));
      $('#imageshow').text(t('btn.imageshow')); 
      $('#approvalSituation').text(t('approvalmsg.approvalSituation'));
      $('#inputinfomsg').attr('placeholder',t('approvalmsg.inputinfo'));
	  $('.approve-btn.approve-dopass-btn').text(t('approvalmsg.approval'));
	  $('.btn.btn-default-alt.approve-btn.dropdown-toggle').text(t('approvalmsg.other'));
	  $('#turndown').text(t('approvalmsg.turndown'));
	  $('#notapproval').text(t('approvalmsg.notapproval'));
	  $('#cancelapproval').text(t('approvalmsg.cancelapproval'));
	  $('#submit').text(t('approvalmsg.iwebsumbit'));
	  $('#body_1bodys_content_edit_menu_add').text(t('btn.add'));
	  $('.alert-content').text(t('approvalmsg.rejectadvice'));
      ctrl.i18n.t("app:title.bill_no_find");
      ctrl.i18n.t("app:title.tip");
      ctrl.i18n.t("app:title.tip_warm");
      ctrl.i18n.t("app:title.wrong");
      ctrl.i18n.t("app:title.failed");
      ctrl.i18n.t("app:title.failed_delete");
      ctrl.i18n.t("app:title.failed_write");
      ctrl.i18n.t("app:msg.confim_cancel");
      ctrl.i18n.t("app:msg.confim_delete");
	  //imageupload
	  if($('.btn-toolbar.btn-left a')) {
		var langimageupload = $('.btn-toolbar.btn-left a');
		for(var i=0; i<langimageupload.length;i++){
			if(langimageupload[i].innerHTML == '影像扫描'){
				langimageupload[i].innerHTML = t('btn.imageupload');
			}
		}
	  }
	  //topapprovedialog
      if($('.approve-right em')) {
     	 var langapprove = $('.approve-right em');
 		 var langapanapprove = $('.approve-right span');
 		 for(var i=0; i<langapprove.length; i++) {
 			 if(langapprove[i].innerHTML=="提交"){
 				langapprove[i].innerHTML = t('approvalmsg.iwebsumbit');
 			 }
 			 else if(langapprove[i].innerHTML=="批准") {
 				langapprove[i].innerHTML=t('approvalmsg.approval');
 			 }
 			 else if(langapprove[i].innerHTML=="不批准") {
 				langapprove[i].innerHTML=t('approvalmsg.notapproval');
 			 }
 			 else if(langapprove[i].innerHTML=="驳回") {
				 langapprove[i].innerHTML=t('approvalmsg.turndown');
			 }
 		 }
		 //topapprovedialog
 		 for(var i=0; i<langapanapprove.length; i++) {
 			if(langapanapprove[i].innerHTML=="提交"){
 				langapanapprove[i].innerHTML = t('approvalmsg.iwebsumbit');
 			 }
 			 else if(langapanapprove[i].innerHTML=="批准") {
 				langapanapprove[i].innerHTML =t('approvalmsg.approval');
 			 }
 			 else if(langapanapprove[i].innerHTML=="不批准") {
 				langapanapprove[i].innerHTML=t('approvalmsg.notapproval');
 			 }
 		 }
		 //billstatus
 		 if($('.form-readonly-content')) {
 			var langbillstatus = $('.form-readonly-content');
 			for(var i=0; i<langbillstatus.length; i++) {
 				if(langbillstatus[i].innerHTML == "已保存") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billsaved');
 				}
 				else if(langbillstatus[i].innerHTML == "自由态") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billfree');
 				}
 				else if(langbillstatus[i].innerHTML == "未生效") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billnotactive');
 				}
 				else if(langbillstatus[i].innerHTML == "提交态") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billsubmit');
 				}
 				else if(langbillstatus[i].innerHTML == "已生效") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billactive');
 				}
 				else if(langbillstatus[i].innerHTML == "进行中态") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billdoing');
 				}
 				else if(langbillstatus[i].innerHTML == "未确认") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billnotsubmit');
 				}
 				else if(langbillstatus[i].innerHTML == "审批通过") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billapproved');
 				}
 				else if(langbillstatus[i].innerHTML == "审批中") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billapproving');
 				}
 				else if(langbillstatus[i].innerHTML == "已暂存") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billstored');
 				}
 				else if(langbillstatus[i].innerHTML == "已签字") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billsigned');
 				}
 				else if(langbillstatus[i].innerHTML == "通过态") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billpassing');
 				}
 				else if(langbillstatus[i].innerHTML == "未通过态") {
 					langbillstatus[i].innerHTML = t('approvalmsg.billfailed');
 				}
 			}
 		 }
		 //newbillstatus
 		 if($('.input-group.date.form_date.disablecover div')) {
 			 var langeditstatus = $('.input-group.date.form_date.disablecover div');
 			 for(var i=0; i<langeditstatus.length; i++) {
 				if(langeditstatus[i].innerHTML == "自由态") {
 					langeditstatus[i].innerHTML = t('approvalmsg.billfree');
 				}
 				else if(langeditstatus[i].innerHTML == "已保存") {
 					langeditstatus[i].innerHTML = t('approvalmsg.billsaved');
 				}
 				else if(langeditstatus[i].innerHTML == "未生效") {
 					langeditstatus[i].innerHTML = t('approvalmsg.billnotactive');
 				}
 			 }
 		 }
       }
    })
   }
   function getLanguagesByCookie(name){
                  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
                  if(arr=document.cookie.match(reg))
                   return unescape(arr[2]);
                  else
                    return null;
    }
	translateLang();
	return ctrl;
})
