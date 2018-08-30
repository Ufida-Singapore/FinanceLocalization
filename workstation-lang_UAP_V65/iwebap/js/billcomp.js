var BillForm = function(app, billManageModel, ctrl) {
	this.app = app;
	this.ctrl = ctrl;
	this.model = billManageModel;
	this.handlingControlEvent = false;
	this.setEnable(false);
	this.billDataTables = {};
	this.isCopyOrAddEdit = false;
	this.gridInteractType = $.GridInteractType.NCNEW;
	this.registerEvent();
	// 注册表头的选中监听
	// var self = this;
	// var dataTable = this.getHeadDataTables()[0];
	// dataTable.on($.DataTable.ON_ROW_SELECT, function(event) {
	// self.handlingControlEvent = true;
	// var selectedIndex = event.indices[0];
	// self.model.setSelectedRow(selectedIndex);
	// self.handlingControlEvent = false;
	// })
//	this.registerRelationEvent();
}

BillForm.fn = BillForm.prototype;

$.GridInteractType = {
	NCCLASSIC: 'ncclassic',
	NCNEW: 'ncnew'
}

BillForm.fn.registerEvent = function() {
	var gridModels = this.app.getCompsByType("grid");
	for(var i = 0; i < gridModels.length; i++){
		var dt = gridModels[i].dataTable;
		(function(gridModel) {
			dt.on($.DataTable.ON_ROW_FOCUS, function(event){
				var index = gridModel.grid.getRowIndexByValue('$_#_@_id',event.rowId);
				if (index == gridModel.grid.focusRowIndex){
					if(gridModel.grid.eidtRowIndex != index){
						gridModel.editRowFun(index);
					}
					return
				}
				if(index > -1){
					if(gridModel.grid.eidtRowIndex != index){
						gridModel.editRowFun(index);
					}
				}
				
			})
		})(gridModels[i])
	}
}


BillForm.fn.setGridInteractType = function(type) {
	this.gridInteractType = type;
	if(type == $.GridInteractType.NCCLASSIC) {
		var bodyDataTables = this.getDataTablesByTabType("body");
		for(var i = 0, count = bodyDataTables.length; i < count; i++){
			var dataTable = bodyDataTables[i];
			$('#' + dataTable.id).closest('[u-meta]').addClass('u-grid-inter-classic')
		}
		
	}
	
}

BillForm.fn.registerRelationEvent = function(){
	var self = this;
	var dataTables = self.app.getDataTables();
	for(var dataTableId in dataTables){
		dataTables[dataTableId].on('valueChange', function(event){
			var dataTableIDs = [];
			dataTableIDs.push(event.dataTable);
			self.app.serverEvent().addDataTables(dataTableIDs).setEvent(event).fire({
				ctrl:'RelationItemHandleController',
				method:'proess',
				success: function(data){ 
					
				}
			})
		})
	}
}

BillForm.fn.handleEvent = function(event) {
	if ($.AppEventConst.SELECTION_CHANGED == event.type) {
		this.onSelectionChanged();
	} else if ($.AppEventConst.UISTATE_CHANGED == event.type) {
		if (this.model.getUIState() == $.UIState.ADD) {
			this.onAdd();
		} else if (this.model.getUIState() == $.UIState.EDIT) {
			this.onEdit();
		} else if(this.model.getUIState() == $.UIState.NOT_EDIT){
			this.onNotEdit();
		} else{
			this.onCancel();
		}
		
	} else if ($.AppEventConst.SELECTED_DATA_CHANGED == event.type) {
		this.onSelectedDataChanged();
	}
}

BillForm.fn.onSelectionChanged = function() {
	this.synchronizeDataFromModel();
}

BillForm.fn.synchronizeDataFromModel = function() {
	var data = this.model.getSelectedData();
	this.clearData();
	if (!data)
		return;
	this.loadHeadData(data.head, false);
	this.loadBodyData(data.body, false);
//	TODO 查询数据在后台转换的时候已经处理了IBillItem.UFREF的显示值，有必要再去后台加载关联项吗？ chenylh
//	this.loadLoadRelationItemValue(this.getDataTablesByTabType());
}

BillForm.fn.clearData = function() {
	var dataTables = this.app.getDataTables();
	for ( var key in dataTables) {
		dataTables[key].removeAllRows();
	}
}

BillForm.fn.loadHeadData = function(headData, isFireEvent) {
	var dataTables = this.getDataTablesByTabType("head");
	if(isFireEvent){
		for(var i = 0, count = dataTables.length; i < count; i++){
			var dataTable = dataTables[i];
			var row = dataTable.createEmptyRow();
			this.setRowValueAndFire(row, headData);
			dataTable.setRowSelect(0);
		}
	}else{
		for(var i = 0, count = dataTables.length; i < count; i++){
			var dataTable = dataTables[i];
			var row = new $.Row({parent:dataTable});
			this.setRowValue(row, headData);
			dataTable.addRow(row);
			row.status = $.Row.STATUS.NORMAL;
			dataTable.setRowSelect(0);
		}
	}
//	this.loadLoadRelationItemValue(dataTables);
}

BillForm.fn.setRowValue = function(row, data){
	for(var field in data){
		if(!row.data[field])
			continue;	
		if(data[field].scale != undefined){
    		row.setMeta(field,'precision',data[field].scale);
   		}
		row.data[field]['value'] = row.formatValue(field, data[field].pk);
		row.setMeta(field, "display", data[field].name);
	}
}

BillForm.fn.setRowValueAndFire = function(row, data){
	for(var field in data){
		if(!row.data[field])
			continue;
		if(data[field].scale){
    		row.setMeta(field,'precision',data[field].scale);
   		}
		row.setValue(field, data[field].pk);
		row.setMeta(field, "display", data[field].name);
	}
}

BillForm.fn.loadBodyData = function(body, isFireEvent){
	var bodyDatas = body.bodys;
	if(bodyDatas == undefined)
		return;
	var tabRows = {};
	for (var i=0, count = bodyDatas.length; i < count; i++){
		var bodyData = bodyDatas[i];
		var dataTable = this.getDataTableByCls(bodyData.cls);
		var row = new $.Row({parent:dataTable}); 
		this.setRowValue(row, bodyData);
		var curTabRows = tabRows[bodyData.cls];
		if(curTabRows == null){
			curTabRows = [];
			tabRows[bodyData.cls] = curTabRows;
		}
		row.status = $.Row.STATUS.NORMAL;
		curTabRows.push(row);
	}
	for(var key in tabRows){
		var dataTable = this.getDataTableByCls(key);
		var curTabRows = tabRows[key];
		dataTable.addRows(curTabRows);
	}
//	this.loadLoadRelationItemValue(this.getDataTablesByTabType("body"));
	var pageCount = bodyDatas.length/10 + 1;
}	

BillForm.fn.loadLoadRelationItemValue = function(dataTables){
	var dataTableIDs = [];
	var severEvent = this.app.serverEvent();
	for(var i = 0, count = dataTables.length; i < count; i++){
		severEvent.addDataTable(dataTables[i].id,"all");
	}
	severEvent.fire({
		ctrl:'RelationItemHandleController',
		method:'loadLoadRelationItemValue',
		success: function(data){ 
		}
	})
}

BillForm.fn.onSelectedDataChanged = function() {
//	this.synchronizeUpdateDataFromModel();
	this.synchronizeDataFromModel();
}

BillForm.fn.synchronizeUpdateDataFromModel = function() {
	var data = this.model.getSelectedData();
	if (!data)
		return;
	this.clearData();
	this.loadHeadData(data.head, true);
	this.loadBodyData(data.body, true);
}

/**
 * 根据class获取对应的表头dataTable
 * 
 * @param {} cls
 * @return {}
 */
BillForm.fn.getDataTableByCls = function(cls) {
	var dataTable = this.billDataTables[cls];
	if(typeof dataTable == 'undefined' || dataTable == null){
		var dataTables = this.app.getDataTables();
		for( var key in dataTables) {
			var dataTable = dataTables[key];
			if (dataTable.getParam("cls") === cls){
				this.billDataTables[cls] = dataTable;
				return dataTable;
			}
		}
	}
	return dataTable;
}

/**
 * 根据页签类型获取对应的dataTable
 * @param {} tabType
 * @return {}
 */
BillForm.fn.getDataTablesByTabType = function(tabType) {
	var dataTables = this.app.getDataTables();
	var result = [];
	for ( var key in dataTables) {
		if(!tabType){
			result.push(dataTables[key])
			continue;
		}
		if (dataTables[key].getParam("tabtype") === tabType)
			result.push(dataTables[key]);
	}
	return result;
}

BillForm.fn.onAdd = function() {
	this.setEnable(true);
	this.addNew();
}

BillForm.fn.addNew = function() {
	this.clearData();
	var headDataTables = this.getDataTablesByTabType("head");
	for(var i = 0, count = headDataTables.length; i < count; i++){
		var dataTable = headDataTables[i];
		var row = dataTable.createEmptyRow();
		dataTable.setRowSelect(0);
	}
	
	
	//进入新增状态，表体自动增加一行
	//TODO 确实设置表体新增的默认编辑
	var bodyDataTables = this.getDataTablesByTabType("body");
	for(var i = 0, count = bodyDataTables.length; i < count; i++){
		var dataTable = bodyDataTables[i];
		if(this.gridInteractType == $.GridInteractType.NCNEW) {
			this.adaptGridUE(dataTable)
		}
		
	}
}

// 适配ue交互效果, 增加态或者修改态
BillForm.fn.adaptGridUE = function(dataTable) {
	var $table = $('#' + dataTable.id + '_content_table'),
		$thead = $('#' + dataTable.id + '_header_thead'),
		$header = $('#' + dataTable.id + '_header'),
		me = this,
		menuBottom = 20
	
	if(dataTable.rows().length == 0) {
		$thead.hide();
		$header.hide()
	} else {
		$thead.show()
		$header.show()
	}
	
	var hasValidateFormula = this.hasBodyValidateFormula(dataTable);
	
	//一个dataTable可能关联多个grid，如共享页签的情况
	var registerGridRenderEditMenu = function(tmpGrid){
		tmpGrid.grid.options.renderEditMemu = function(ele,ridx,all){
			var htmlStr = '<div id="'+ dataTable.id+'_content_edit_menu" style="position:relative;float:left;width:100%;height:40px;"></div>';
			ele.insertAdjacentHTML('beforeEnd',htmlStr);		
			var $menu = $('#' + dataTable.id + '_content_edit_menu');
	
			if(ridx == all-1){
				var $addbtn = $('<button type="button" class="u-grid-content-edit-menu-button u-grid-content-edit-menu-button-add" id="'+ dataTable.id + '_content_edit_menu_add">Add</button>');
				$menu.append($addbtn);
				$addbtn.on('click', function() {
					if(validateBodyForm().length > 0) {
						$.showMessageDialog({type:"info",title:"Prompt",msg: 'Inspection does not pass',backdrop:true});
						return;
					}
					if(hasValidateFormula){
						app.serverEvent().addDataTable(dataTable.id).fire({
							ctrl:'FormularController',
							method:'execValidateFormulas',
							success: function(data){
								if(!me.processFormulaResults(data))
									return;
								dataTable.getRow(dataTable.rows().length-1).status = 'new'
								$thead.show();
								$header.show()
								dataTable.createEmptyRow()
								dataTable.setRowFocus(dataTable.rows().length-1);
								me.ajustScroll($('#' + dataTable.id + '_edit_form')[0]);
								if(typeof me.ctrl != 'undefined' && me.ctrl["afterAddSave"]){
									me.ctrl["afterAddSave"].call(this,dataTable);
								}
							}
						})
					}else{
						dataTable.getRow(dataTable.rows().length-1).status = 'new'
						$thead.show();
						$header.show()
						dataTable.createEmptyRow()
						dataTable.setRowFocus(dataTable.rows().length-1);
						me.ajustScroll($('#' + dataTable.id + '_edit_form')[0]);
						if(typeof me.ctrl != 'undefined' && me.ctrl["afterAddSave"]){
							me.ctrl["afterAddSave"].call(this,dataTable);
						}
					}
				})
				if(all == 1) {
					$thead.hide()
					$header.hide()
				} else {
					$thead.show()
					$header.show()
				}
				
			}else{
				var $cnlbtn = $('<button type="button" class="u-grid-content-edit-menu-button u-grid-content-edit-menu-button-cancel"  id="'+ dataTable.id +'_content_edit_menu_cancel">Cancel</button>'),
					$okbtn	= $('<button type="button" class="u-grid-content-edit-menu-button u-grid-content-edit-menu-button-save" id="'+ dataTable.id + '_content_edit_menu_save">Save</button>');
				$menu.append($cnlbtn).append($okbtn);
	
				$okbtn.on('click', function() {
					if(validateBodyForm().length > 0) {
						$.showMessageDialog({type:"info",title:"Prompt",msg: 'Inspection does not pass',backdrop:true});
						return;
					}
					if(hasValidateFormula){
						app.serverEvent().addDataTable(dataTable.id).fire({
							ctrl:'FormularController',
							method:'execValidateFormulas',
							success: function(data){
								if(!me.processFormulaResults(data))
									return;
								me.isCopyOrAddEdit = false;
								dataTable.setRowFocus(dataTable.rows().length-1);
								if(typeof me.ctrl != 'undefined' && me.ctrl["afterSave"]){
									me.ctrl["afterSave"].call(this,dataTable);
								}
							}
						})
					}else{
						me.isCopyOrAddEdit = false;
						dataTable.setRowFocus(dataTable.rows().length-1);
						if(typeof me.ctrl != 'undefined' && me.ctrl["afterSave"]){
							me.ctrl["afterSave"].call(this,dataTable);
						}
					}
				});		
				var cloneRow = $.extend(true,{}, dataTable.getRow(ridx))
				$cnlbtn.on('click',function() {
					dataTable.removeRow(ridx);
					if(!me.isCopyOrAddEdit)
						dataTable.insertRow(ridx, cloneRow);
					me.isCopyOrAddEdit = false;
					dataTable.setRowFocus(dataTable.rows().length-1);
					if(typeof me.ctrl != 'undefined' && me.ctrl["afterCancel"]){
						me.ctrl["afterCancel"].call(this,dataTable);
					}
				})
			}
			var validateBodyForm = function() {
				var a = app.compsValidate('#' + dataTable.id + '_edit_form')
				var comps = app.getComp(dataTable.id).editComponent
				var unpassed=[];
				for(var i in comps) {
					if(comps[i].doValidate && !comps[i].doValidate(true)) {
						unpassed.push(comps[i])
					}
				}
				return unpassed;
			}
		}
	}
	var app = me.app;
	var gridComps = app.getCompsByType("grid");
	for(var i = 0; i < gridComps.length; i++){
		if(gridComps[i].dataTable && gridComps[i].dataTable['id'] == dataTable.id){
			registerGridRenderEditMenu(gridComps[i]);
		}
	}
	var r = dataTable.createEmptyRow()
	dataTable.setRowSelect(r);
	dataTable.setRowFocus(r);
}

BillForm.fn.hasBodyValidateFormula = function(dataTable){
	var hasValidateFormula = null;
	var meta = dataTable.getMeta();
	for (var k in meta){
		hasValidateFormula = meta[k]['validateFormula'];
		if(hasValidateFormula)
			return true;
	}
	return false;
}

BillForm.fn.processFormulaResults = function(data) {
	var datas = data.split(";");
	for(var i = 0; i < datas.length; i++){
		if(datas[i].length == 0 || datas[i].split("=")[1].length == 0){
			continue;
		}
		if(datas[i].indexOf("$Message") === 0){
			$.showMessageDialog({type:"info",title:"Prompt",msg: datas[i].split("=")[1],backdrop:true});
		}else if(datas[i].indexOf("$Error") === 0){
			$.showMessageDialog({title:"Caveat",msg:datas[i].split("=")[1],backdrop:true});
			return false;
		}
	}
	return true;
}

BillForm.fn.ajustScroll = function(editfrm) {
	var frmtop = editfrm.getBoundingClientRect().top,
	frmheight = editfrm.getBoundingClientRect().height,
	docheight = document.documentElement.clientHeight,
	minus = frmtop+frmheight+50-docheight,
	scrolltop = $(document.body).scrollTop()
	if(minus > 0) {
		$(document.body).scrollTop(scrolltop+minus)
	}
	scrolltop = $(document.body).scrollTop()
	frmtop = editfrm.getBoundingClientRect().top
	if(frmtop < 0) {
		$(document.body).scrollTop(scrolltop+frmtop)
	}
}




BillForm.fn.onEdit = function() {
	this.setEnable(true);
	var bodyDataTables = this.getDataTablesByTabType("body");
	for(var i = 0, count = bodyDataTables.length; i < count; i++){
		var dataTable = bodyDataTables[i];
		if(this.gridInteractType == $.GridInteractType.NCNEW) {
			this.adaptGridUE(dataTable)
		}
	}
		
}

BillForm.fn.setEnable = function(enable){
	var dataTables = this.app.getDataTables();
	for(var key in dataTables){
		dataTables[key].setEnable(enable);
	}
}

BillForm.fn.onNotEdit = function() {
	this.setEnable(false);
}

BillForm.fn.onCancel = function() {
	this.synchronizeDataFromModel();
}

BillForm.fn.getValue = function(){
	if(!this.validate())
		return null;
	
	var data = {};
	var dataTables = this.app.getDataTables();
	var body = {};
	var bodys = [];
	for(var key in dataTables){
		var dataTable = dataTables[key];
		if(dataTable.getParam("tabtype") === "head"){
			var head = data.head || {};
//			head.cls = dataTable.getParam("cls");
			var rows = dataTable.getAllRows();
			for(var i = 0, count = rows.length; i < count; i++){
				var rowData = rows[i].data;
				for(var key in rowData){
					if(typeof rowData[key].value == 'undefined' || rowData[key].value == null)
						continue;
					head[key] = rowData[key].value;
				}
			}
			data.head = head;
		}else if(dataTable.getParam("tabtype") === "body"){
			var rows = dataTable.getAllRows(), rlen = rows.length;
			if(this.gridInteractType == $.GridInteractType.NCNEW) {
				if(this.model.getUIState() !== $.UIState.NOT_EDIT) {
					rlen = rows.length-1;
				}
			}
			
			for(var i = 0, count = rlen; i < count; i++){
				var rowData = rows[i].data;
				var bodyData = {};
				bodyData.cls = dataTable.getParam("cls");
				for(var key in rowData){
					if(typeof rowData[key].value == 'undefined' || rowData[key].value == null)
						continue;
					bodyData[key] = rowData[key].value;
				}
				bodys.push(bodyData);
			}
		}else{
			//表尾
		}
	}
	body.bodys = bodys;
	data.body = body;
	return data;
}

BillForm.fn.validate = function(){
	if(!this.app.compsValidate($('.pub-billtemplate .jbxx')[0]) ) {
		$.showMessageDialog({type:"info",title:"Prompt",msg: 'Inspection does not pass',backdrop:true});
		return false;
	}

	if(this.gridInteractType == $.GridInteractType.NCNEW && this.model.getUIState() !== $.UIState.NOT_EDIT) {
		//表体修改状态下禁止保存单据
		var dataTables = this.app.getDataTables();
		for(var key in dataTables){
			var dataTable = dataTables[key];
			if(dataTable.getParam("tabtype") === "body"){
				if(dataTable.getFocusIndex() >= 0 && dataTable.getFocusIndex() < dataTable.getAllRows().length - 1){
					$.showMessageDialog({type:"info",title:"Prompt",msg: 'Please handle the current modification first',backdrop:true});
					return false;
				}
			}
		}
	}
	
	var headDataTables = this.getDataTablesByTabType("head");
	var hasValidateFormula = false;
	for(var i = 0; i < headDataTables.length; i++){
		hasValidateFormula = this.hasBodyValidateFormula(headDataTables[i]);
		if(hasValidateFormula)
			break;
	}
	
	if(!hasValidateFormula)
		return true;
	var flag = false;
	var dataTableIDs = [];
	for(var i = 0; i < headDataTables.length; i++){
		dataTableIDs.push(headDataTables[i].id);
	}
	var self = this;
	this.app.serverEvent().addDataTables(dataTableIDs).fire({
		ctrl:'FormularController',
		method:'execValidateFormulas',
		async: false,
		success: function(data){
			if(!self.processFormulaResults(data)){
				flag = false;
			}else{
				flag = true;
			}
		}
	})
	return flag;
}

BillForm.fn.addLine = function(dataTableID){
	var dataTable = this.app.getDataTable(dataTableID);
	var row = dataTable.createEmptyRow();
	dataTable.setRowSelect(row);
	dataTable.setEnable(true);
}

BillForm.fn.delLine = function(dataTableID){
	var dataTable = this.app.getDataTable(dataTableID);
	var selectedIndex = dataTable.getSelectedIndex();
	dataTable.removeRow(selectedIndex);
	//删除表体选中行后，设置选中行的上一行为新的选中行
	if(!selectedIndex) {
		// 删除的是第一行
		dataTable.setRowSelect(0);
	} else {
		dataTable.setRowSelect(--selectedIndex);
	}
	
}
;
define("billformcomp", function(){});

	var AppEventConst = function(){
		
	}
	
	//事件类型
	//应用程序模型被初始化
	AppEventConst.MODEL_INITIALIZED = 'Model_Initialized'; 
	//模型的当前选择发生改变
	AppEventConst.SELECTION_CHANGED = 'Selection_Changed';
	//支持多行选择的模型多行选择发生了变化
	AppEventConst.MULTI_SELECTION_CHANGED = "Multi_Selection_Changed";
	//UI状态发生了改变 
	AppEventConst.UISTATE_CHANGED = "Uistate_Changed";
	//当前选中的数据发生了变化
	AppEventConst.SELECTED_DATA_CHANGED = "Selected_Data_Changed";
	//向数据库中插入了数据。
	AppEventConst.DATA_INSERTED = "Data_Inserted";
	/**
	 * 更新了数据库中的数据。
	 * 因此事件类型与SELECTED_DATA_CHANGED有部分重叠，因此在此处明确：
	 * 
	 * 修改一条数据：如果是选中数据，必须仅发送：SELECTED_DATA_CHANGED；如果不是选中数据，必须仅发送DATA_UPDATED事件。
	 * 修改多条数据：如果包含选中数据，则必须发送SELECTED_DATA_CHANGED和DATA_UPDATED两个事件，如果不包含选中数据，则仅发送DATA_UPDATED事件。
	 * 
	 */
	AppEventConst.DATA_UPDATED = "Data_Updated";
	//删除了数据库中的数据
	AppEventConst.DATA_DELETED = "Data_Deleted";
	//刷新了界面数据。
	AppEventConst.DATA_REFRESHED = "Data_Refresh";
	//显示List事件，模型不会判断调用时机，通过其他Editor或Action进行触发
	AppEventConst.VIEW_SHOWLIST = "View_ShowList";
	//显示Editor事件，模型不会判断调用时机，通过其他Editor或Action进行触发
	AppEventConst.VIEW_SHOWFORM = "View_ShowForm";
	
		
	var UIState = function(){
		
	}
	
	UIState.INIT = "init";
	UIState.ADD = "add";
	UIState.EDIT = "edit";
	UIState.NOT_EDIT = "not_edit";
	UIState.SAVE = "save";
	UIState.CANCEL = "cancel";
	UIState.DISABLE = "disable";
	UIState.BUSY = "busy";
	
	$.AppEventConst = AppEventConst;
	$.UIState = UIState;

define("eventconstcomp", function(){});

	/**===========================================================================================================
	 * 
	 * 业务单据模型   
	 * 
	 * ===========================================================================================================
	 */

	var BillManageModel = function(){
		this.uiState = $.UIState.NOT_EDIT;
		this.datas = [];     
		this.datapks = [];
		this.selectedOperaRows = [];	//当前多选框选中的行，比如用于删除、审批等操作
		this.selectedRow = -1;		//当前鼠标选中的行，比如用于上一行下一行浏览等操作
		this.listeners = [];
		
		//分页信息
		this.pageSize = 10; //每页记录条数
		this.pageIndex = 0;	//当前页码
		this.pageCount = {};//总页码
	}
	
	BillManageModel.fn = BillManageModel.prototype
	
	BillManageModel.fn.isArray = function(o){
//		return Object.prototype.toString.call(o) === '[object Array]';
		return o.constructor.toString().indexOf("Array") > -1;
	}
	
	BillManageModel.fn.initModel = function(datas){
		this.clearData();
		if(!datas){
			this.pageCount = 0;
			this.fireEvent(this.getAppEvent($.AppEventConst.MODEL_INITIALIZED, null));
			//设置表头第0行为被选中行
			this.setSelectedRow(-1);
		}else if(!this.isArray(datas)){
			this.datas.push(datas);
			this.datapks.push(datas.head.primarykey);
			this.pageCount = 1;
			this.fireEvent(this.getAppEvent($.AppEventConst.MODEL_INITIALIZED, null));
			//设置表头第0行为被选中行
			this.setSelectedRow(0);
		}else{
			this.datas = datas;
			for (var i=0, count = datas.length; i< count; i++){
				var data = datas[i];
				this.datapks.push(data.head.primarykey);
			}
			this.pageCount = Math.ceil(this.datas.length/this.pageSize);
			this.fireEvent(this.getAppEvent($.AppEventConst.MODEL_INITIALIZED, null));
			//设置表头第0行为被选中行
			this.setSelectedRow(this.datas.length > 0 ? 0 : -1);
		}
	}
	
	BillManageModel.fn.getAppEvent = function(type, data){
		var event = {};
		event.type = type;
		event.data = data;
		return event;
	}
	
	
	BillManageModel.fn.clearData = function(){
		this.datas.clear();
		this.datapks.clear();
		this.selectedRow = -1;
		this.selectedOperaRows.clear();
	}
	
	BillManageModel.fn.directlyAdd = function(data){
		this.datas.push(data);
		this.datapks.push(data.head.primarykey)
		var selectedIndex = this.datas.length - 1;
		var rowInfo = this.getRowOperationInfo(selectedIndex, data);
		this.fireEvent(this.getAppEvent($.AppEventConst.DATA_INSERTED, rowInfo));
		this.setSelectedRow(selectedIndex);
	}
	
	BillManageModel.fn.del = function(){
		// 优先删除复选框选择的纪录
		if (this.selectedOperaRows && this.selectedOperaRows.length > 0){
			this.deleteMultiRows();
		}
		else
		{
			this.deleteSeletedRow();
		}
	}
	
	BillManageModel.fn.deleteMultiRows = function(){
		var deletedObjects = [];
		
		for(var i = 0, count = this.selectedOperaRows.length; i < count; i++){
			deletedObjects.push(this.datas[this.selectedOperaRows[i]])
		}
		
		//数据库数据真实删除
//		dbDeleteMultiRows(deletedObjects);
		this.directlyDeletes(deletedObjects);
	}
	
	BillManageModel.fn.deleteSeletedRow = function(){
		// 其次优先删除鼠标选择的记录
		var deletedData = this.getSelectedData();
		if(deletedData != null){
//			dbDeleteMultiRows(deletedData);
			this.directlyDelete(deletedData);
		}
	}
	
	
	BillManageModel.fn.directlyDelete = function(data){
		if(!data)
			return;
		var index = this.findBusinessDataIndex(data);
		//从数据集中删除数据
		this.datas.splice(index,1);
		this.datapks.splice(index,1);
		
		var isDeleteSelectedData = false;
		if(index == this.selectedRow)
		{//避免删除选中行，在发送删除事件时，有监听器获取当前选中行出错。
			this.selectedRow = -1;
			isDeleteSelectedData = true;
		}
		var rowInfo = this.getRowOperationInfo(index, data);
		this.fireEvent(this.getAppEvent($.AppEventConst.DATA_DELETED, rowInfo));
		var selectedIndex = Math.min(index, this.datas.length - 1);
		if (isDeleteSelectedData || this.selectedRow >= this.datas.length)
			this.setSelectedRow(selectedIndex);		
	}
	
	Array.prototype.contains = function(item){
		return RegExp("\\b"+item+"\\b").test(this);
	};		
	
	/**
	 * 直接删除多行信息
	 */
	BillManageModel.fn.directlyDeletes = function(datas){
		if(!datas || datas.length == 0)
			return;
		
		var isDeleteSelectedData = false;
		var oldSelectedData = this.getSelectedData();
		var oldSelectedDataIndex = this.getSelectedRow();
		
		//防止因删除导致多选选中行错误
		var selectedOperaDataMap = {};
		if(this.selectedOperaRows.length > 0){
			for(var i=0, count = this.selectedOperaRows.length; i< count; i++){
				var index = this.selectedOperaRows[i];
				selectedOperaDataMap[index] = this.datas[index];
			}
		}
		
		var deleteIndexs = [];
		for (var i=0, count = datas.length; i< count; i++){
			var index = this.findBusinessDataIndex(datas[i]);
			deleteIndexs.push(index);
			if(index == this.selectedRow)
				isDeleteSelectedData = true;
		}
		
		deleteIndexs.sort();
		for (var i=deleteIndexs.length-1; i >= 0; i--){
			var deleteIndex = deleteIndexs[i];
			this.datas.remove(deleteIndex);
			this.datapks.remove(deleteIndex);
			if(this.selectedOperaRows.contains(deleteIndex))
				this.selectedOperaRows.remove($.inArray(deleteIndex, this.selectedOperaRows));
		}
		
		//两种情况需要清空选择行
		//1.选择行被删除
		//2.选择行的索引号在删除行之后，例如总共5条数据，选中第5行的时候，删除第3、4行，删除完只剩3条数据，还要选中第5行就会数组越界
		if(isDeleteSelectedData || this.selectedRow >= this.datas.length)
			this.selectedRow = -1;
		
		//将删除可能引起多选选中行号变化的情况处理掉
		if(!(this.selectedOperaRows.length === 0))
		{
			for (var i=0, count = this.selectedOperaRows.length; i< count; i++){
				var selectedOperaRow = this.selectedOperaRows[i];
				var data = selectedOperaDataMap[selectedOperaRow];
				var index = this.findBusinessDataIndex(data);
				if(index != selectedOperaRow)
					this.selectedOperaRows[i] = index;
			}
		}
		
		var rowInfo = this.getRowOperationInfo(deleteIndexs, datas);
		this.fireEvent(this.getAppEvent($.AppEventConst.DATA_DELETED, rowInfo));
		
		var nowSelectedDataIndex = this.findBusinessDataIndex(oldSelectedData);
		if(isDeleteSelectedData)
		{//如果删除了选中数据
			var selectedIndex = Math.min(oldSelectedDataIndex, this.datas.length - 1);
			this.setSelectedRow(selectedIndex);
		}else if(nowSelectedDataIndex != oldSelectedDataIndex)
		{//如果选中行的行数发生变化，比如删除了选中行前面的行，虽然选中数据没变化，但是选中行号发生了变化，也要发通知。
			this.setSelectedRow(nowSelectedDataIndex);
		}
	}
	
	BillManageModel.fn.update = function(data){
		this.directlyUpdate(data);
	}
	
	BillManageModel.fn.directlyUpdate = function(data){
		if(!data)
			return ;
		if(!this.isArray(data))
			this.directSingleUpdate(data);
		else
		{
			if(data.length == 1)
				this.directSingleUpdate(data[0]);
			else
				this.directMultiUpdate(data);
		}
	}
	
	/**
	 * 直接更新单个数据，如果不是选中对象，则设置其为选中对象。
	 * 发送SELECTED_DATE_CHANGED事件
	 */
	BillManageModel.fn.directSingleUpdate = function(data){
		var index = this.findBusinessDataIndex(data);
		
		if(index == -1)
			return;/*更新了一个不存在的数据*/
		this.datas[index] = data;
		if(index != this.getSelectedRow())
		{
			this.setSelectedRow(index);
		}
		var rowInfo = this.getRowOperationInfo(index, data);
		this.fireEvent(this.getAppEvent($.AppEventConst.SELECTED_DATA_CHANGED, rowInfo));
	}
	
	/**
	 * 直接更新多个数据，如果这些数据中有被选择数据，则首先发送SELECTED_DATE_CHANGED事件。
	 * 
	 * 总会发送 Data_Updated 事件
	 */
	BillManageModel.fn.directMultiUpdate = function(datas){
		var indexs = [];
		for(var key in datas){
			var data = datas[key];
			var index = this.findBusinessDataIndex(data);
			if(index === -1)
				return;/*更新了一个不存在的数据*/
			this.datas[index] = data;
			indexs.push(index);
			if(index == this.getSelectedRow())
			{
				var rowInfo = this.getRowOperationInfo(index, datas[key]);
				this.fireEvent(this.getAppEvent($.AppEventConst.SELECTED_DATA_CHANGED, rowInfo));
			}
		}
		var rowInfo = this.getRowOperationInfo(indexs, datas);
		this.fireEvent(this.getAppEvent($.AppEventConst.DATA_UPDATED, rowInfo));
	}
	
	BillManageModel.fn.getRowOperationInfo= function(index, data){
		var rowIndexes = [];
		if(this.isArray(index)){
			rowIndexes = index;
		}else{
			rowIndexes.push(index);
		}
		var rowDatas = [];
		if(this.isArray(data)){
			rowDatas = data;
		}else{
			rowDatas.push(data);
		}
		var rowOperationInfo = {};
		rowOperationInfo.rowIndexes = rowIndexes;
		rowOperationInfo.rowDatas = rowDatas;
		return rowOperationInfo;
	}
	
	BillManageModel.fn.getMultiRowOperationInfo= function(index, data, selectionState){
		var rowIndexes = [];
		rowIndexes.push(index);
		var rowDatas = [];
		rowDatas.push(data);
		var rowOperationInfo = {};
		rowOperationInfo.rowIndexes = rowIndexes;
		rowOperationInfo.rowDatas = rowDatas;
		rowOperationInfo.selectionState = selectionState;
		return rowOperationInfo;
	}
	
	BillManageModel.fn.findBusinessDataIndex = function(data){
		for(var i = 0, count = this.datapks.length; i < count; i++){
			var primarykey = data.head.primarykey;
			if(this.datapks[i] === primarykey)
				return i;
		}
		return -1;
	}
	
	/**
	 * 下一页
	 */
	BillManageModel.fn.nextRow = function(){
		if(this.selectedRow < -1)
			this.selectedRow = -1;
		if(!this.isLastRow())
			this.setSelectedRow(this.selectedRow + 1);
	}
	
	/**
	 * 上一页
	 */
	BillManageModel.fn.preRow = function(){
		if(this.selectedRow >= 1)
			this.setSelectedRow(this.selectedRow - 1);
	}
	
	/**
	 * 首页
	 */
	BillManageModel.fn.firstRow = function(){
		if(this.selectedRow > 0)
			this.setSelectedRow(0);
	}
	
	/**
	 * 末页
	 */
	BillManageModel.fn.lastRow = function(){
		if(!this.isLastRow())
			this.setSelectedRow(this.datas.length - 1);
	}
	
	/**
	 * 是否当前是最后一页
	 * 
	 * @return
	 */
	BillManageModel.fn.isLastRow = function(){
		return this.getSelectedRow() == this.datas.length-1;
	}
	
	BillManageModel.fn.setSelectedRow = function(index){
		if(this.selectedRow === index)
			return;
		this.selectedRow = index;
		this.fireEvent(this.getAppEvent($.AppEventConst.SELECTION_CHANGED));
	}
	
	BillManageModel.fn.setSelectedOperaRowsWithEvent = function(selectedRows){
		this.setSelectedOperaRows(selectedRows, true);
	}
	
	BillManageModel.fn.setSelectedOperaRowsWithoutEvent = function(selectedRows){
		this.setSelectedOperaRows(selectedRows, false);
	}
	
	BillManageModel.fn.setSelectedOperaRows = function(selectedRows, fireEvent){
		var self = this;
		self.selectedOperaRows.clear();
		if(selectedRows != null && selectedRows.length > 0){
			$.each(selectedRows, function(key, value){
				self.selectedOperaRows.push(value);
			});
//			this.selectedOperaRows.push(index)
		}
		if(fireEvent){
//			this.fireEvent(this.getAppEvent($.AppEventConst.MULTI_SELECTION_CHANGED));
		}
	}
	
	/**
	 * 复选框的多选形式下使用
	 * @param {} selectedRows
	 */
	BillManageModel.fn.addSelectedOperaRow = function(selectedRows){
		if(selectedRows == null || selectedRows.length === 0)
			return;
		var needAddIndexs = [];
		var selectedMap = {};//new HashMap<Integer, Integer>();
		var self = this;
		$.each(self.selectedOperaRows, function(i, value){
			selectedMap[value] = value;
		});
		$.each(selectedRows, function(i, value){
			if(selectedMap[value] == null){
				needAddIndexs.push(value);
				self.selectedOperaRows.push(value);
			}
			selectedMap[value] = value;
		});
		
		if(needAddIndexs.length == 0)
			return ;
		
		var rowIndexs = [];
		for(var i = 0, count = needAddIndexs.length;i < count; i++){
			rowIndexs.push(needAddIndexs[i]);
		}
//		this.fireEvent(this.getAppEvent($.AppEventConst.MULTI_SELECTION_CHANGED, getMultiRowOperationInfo()));
	}
	
	/**
	 * 获得鼠标选择行
	 */
	BillManageModel.fn.getSelectedRow = function(){
		return this.selectedRow;
	}
		 
	/**
	 * 获得复选框选择行
	 */
	BillManageModel.fn.getSelectedOperaRows = function(){
		return this.selectedOperaRows;
	}
	
	BillManageModel.fn.getData = function(){
		return this.datas;
	}
	
	BillManageModel.fn.getSelectedData = function(){
		if(this.getSelectedRow() == -1)
			return null;
		if (this.datas == null || this.datas.length == 0) {
			return null;
		} else {
			return this.datas[this.selectedRow];
		}
	}
	
	BillManageModel.fn.getSelectedOperaDatas = function(){
		if(this.selectedOperaRows.length == 0)
			return null;
		var selectedDatas = [];
		for(var i = 0, count = this.selectedOperaRows.length; i < count; i++){
			selectedDatas.push(this.datas[this.selectedOperaRows[i]])
		}
		return selectedDatas;
	}
	
	BillManageModel.fn.addListener = function(listener){
		this.listeners.push(listener);
	}
	
	BillManageModel.fn.fireEvent = function(event){
		for (var index in this.listeners) {
			var listener = this.listeners[index];
			if(listener["handleEvent"]){
				listener["handleEvent"].call(this,event)
			}
		}
	}	
	
	BillManageModel.fn.getUIState = function(){
		return this.uiState;
	}
	
	BillManageModel.fn.setUIState = function(uiState){
		var oldState = this.uiState;
		this.uiState = uiState;
		var uiStateEvent = {};
		uiStateEvent.type = $.AppEventConst.UISTATE_CHANGED;
		uiStateEvent.oldState = oldState;
		uiStateEvent.newState = uiState;
		this.fireEvent(uiStateEvent);
	}
	
	//分页模型  
	BillManageModel.fn.getPageCount = function(){
		return this.pageCount;
	}
	
	//当前一页中的记录数
	BillManageModel.fn.getPageSize = function(){
		return this.pageSize;
	}
	
	BillManageModel.fn.setPageSize = function(pageSize){
		this.pageSize = pageSize;
	}
	
	//当前页码
	BillManageModel.fn.getPageIndex = function(){
		return this.pageIndex;
	}
	
	BillManageModel.fn.setPageIndex = function(pageIndex){
		this.pageIndex = pageIndex;
	}
	
	;
define("billmanagemodelcomp", function(){});

define('formula',[],function(){

	var addFormulaEvent = function(app,callBack){
		var dts = app.getDataTables()
		for (var key in dts){
			var dt = dts[key]
			var meta = dt.getMeta()
			for (var k in meta){
				var hasEditFormula = meta[k]['editFormula'];
				var hasValidateFormula = meta[k]['validateFormula'];
				var hasRelationItems = meta[k]['associations'];
				
				var len=0;
			    if (hasRelationItems != undefined && hasRelationItems) {
			     for(var i in hasRelationItems){
			      if(hasRelationItems.hasOwnProperty){
			       len++;
			      }
			     }
			    }
			    if (hasEditFormula || hasValidateFormula || len>1){
					dt.on(k + '.valueChange', function(event){
//						if(event.ctx && event.ctx.isExe && event.ctx.isExe == true){
							app.serverEvent().addDataTable(event.dataTable).setEvent(event).fire({
							ctrl:'FormularController',
							method:'proess',
							async: false,
							success: function(data){ 
								if (callBack)
									callBack.call(this, event, data)
							}
						})
					})
				}
			}
		}
	}
	
	return {
		addFormulaEvent: addFormulaEvent
	}
	
});
define('gridRender',[],function(){
    /**
     * grid 自定义button render
     */
     
	var gridOperRenderType = function(params){
		var btngroup = gridOperRenderType.btns;
		var oThis = this;
		
		if(typeof btngroup != 'undefined' && btngroup != null && btngroup.length > 0){
			for(var i = 0;i<btngroup.length;i++){
				var btn = btngroup[i];
				var $ba;
				var bi = document.createElement('img');
				var iClass = btn.icon;
				if(!iClass)
					iClass = gridOperRenderType.btnIcons[btn.id];
				var aName = btn.id;
				var aTitle = btn.text;
				//$(bi).addClass(iClass);
				if(!btn.inmore){
					var htmlStr = '<a id="'+ aName +'" name="'+ aName +'" title="'+ aTitle +'"><img src="/iwebap/css/img/' + iClass + '"></img></a>';
					params.element.insertAdjacentHTML('beforeEnd', htmlStr);
					$ba = $(params.element).children('#'+aName);			
				}else{
					if(!$(params.element).children("#moreIcon").length){
						var more = '<a id="moreIcon" name="moreIcon"><img src="/iwebap/css/img/more.png"></a>' 										
						var more_detail = '<label class="hover_box"><ul class="more_detail"></ul></label>'
						params.element.insertAdjacentHTML('beforeEnd', more+more_detail);
						$(params.element).on("mouseleave",".hover_box",function(){
							$(this).removeClass("hoverOpen")
						})
						$(params.element).on("mouseenter",".hover_box",function(){
							
							$(this).addClass("hoverOpen")
						})
					 
					}
					var htmlStr = '<li><a id="'+ aName +'" name="'+ aName +'" title="'+ aTitle +'">'+btn.text+'</a></li>';
					$(params.element).children(".hover_box").children(".more_detail")[0].insertAdjacentHTML('beforeEnd', htmlStr);	
					$ba = $(params.element).children('.hover_box').children('.more_detail').children('li').children('#'+aName);		
				}
				
				$ba.on('click', (function(onclick) {
				
					return function(e) {
						if(onclick){
							onclick.call(oThis,e);
						}
						if (e.stopPropagation) {
							e.stopPropagation();
						} else {
							e.cancelBubble = true;
						}
					}
				})(btn.onclick))
				
			}
		}
		var trNode = $(params.element).parents('tr');
		var tdNode = $(params.element).parents('td');
		tdNode.addClass("oper_td");
	
		
	}
	
	gridOperRenderType.btns = []
	gridOperRenderType.btnIcons = {'copy':'fa fa-copy','edit':'fa fa-edit ','delete':'fa fa-remove'}
	
	return {
		gridOperRenderType: gridOperRenderType
	}
});
define('fullgridRender',[],function(){

	var fullGridOper = function(){
	}
	
	fullGridOper.registerActions = function(billForm, ctrl, jqDom){
		var btngroup = ctrl.fullGridOperRenderType;
		fullGridOper.ctrl = ctrl;
		fullGridOper.billForm = billForm;
		this.registerFormEvent();
		if(typeof btngroup == 'undefined' || btngroup == null || btngroup.length == 0)
			return;
		var bodyDataTables = billForm.getDataTablesByTabType("body");
		var oThis = this;
		var btnIcons = {'copy':'fa fa-copy','edit':'fa fa-edit ','delete':'fa fa-remove'}
		for(var i = 0, count = bodyDataTables.length; i < count; i++){	
			var dataTableID = bodyDataTables[i].id;
			for(var j=0;j<jqDom.length;j++) {
				if($(jqDom[j]).is("#detailtab_" + dataTableID)) {
					break;
				}
			}
			var trEle = $(jqDom[j]).find("tbody tr:first-child");
			var element = trEle.find(".oper_td span")[0];
			if(!element || $(element).children()[0])
				return;
			for(var i = 0;i<btngroup.length;i++){
				var btn = btngroup[i];
				var ba = document.createElement('a');
				var bi = document.createElement('img');
				var iClass = btn.icon;
				if(!iClass)
					iClass = btnIcons[btn.id];
				var aName = btn.id;
				var aTitle = btn.text;
				//$(bi).addClass(iClass);
				var data_action = btn.action;
				if(!btn.inmore){
					var htmlStr = '<a id="'+ aName +'" name="'+ aName +'" title="'+ aTitle +'" data-action="'+ data_action +'" dataTableID="'+ dataTableID +'"><img src="/iwebap/css/img/' + iClass + '"></img></a>';
					element.insertAdjacentHTML('beforeEnd', htmlStr);	
				}else{
					if(!$(element).find("#moreIcon").length){
						var more = '<a id="moreIcon" name="moreIcon"><img src="/iwebap/css/img/more.png"></a>' 										
						var more_detail = '<label class="hover_box"><ul class="more_detail"></ul></label>'
						element.insertAdjacentHTML('beforeEnd', more+more_detail);
					}
					var htmlStr = '<li><a id="'+ aName +'" name="'+ aName +'" title="'+ aTitle +'" data-action="'+ data_action +'" dataTableID="'+ dataTableID +'">'+btn.text+'</a></li>';
					$(element).children(".hover_box").children(".more_detail")[0].insertAdjacentHTML('beforeEnd', htmlStr);	
				}
			}
		}
		oThis.registerEditEvent();
	}
	
	/**
	 * 注册表体卡片form按钮事件
	 */
	fullGridOper.registerFormEvent = function(){
		var validateBodyForm = function(dataTableID) {
			var app = fullGridOper.billForm.app;
			var a = app.compsValidate('#detail_editor_' + dataTableID)
			var comps = app.getComp(dataTableID).editComponent;
			var unpassed=[];
			for(var i in comps) {
				if(comps[i].doValidate && !comps[i].doValidate(true)) {
					unpassed.push(comps[i])
				}
			}
			return unpassed;
		}
		var processFormulaResults = function(data) {
			var datas = data.split(";");
			for(var i = 0; i < datas.length; i++){
				if(datas[i].length == 0 || datas[i].split("=")[1].length == 0){
					continue;
				}
				if(datas[i].indexOf("$Message") === 0){
					$.showMessageDialog({type:"info",title:"Prompt",msg: datas[i].split("=")[1],backdrop:true});
				}else if(datas[i].indexOf("$Error") === 0){
					$.showMessageDialog({title:"Caveat",msg:datas[i].split("=")[1],backdrop:true});
					return false;
				}
			}
			return true;
		}
		fullGridOper.validateBodyForm = validateBodyForm;
		fullGridOper.processFormulaResults = processFormulaResults;
		
		$(document).on('click', '[data-action="handler_saveFullGrid"]', function(e) {
			handler_saveFullGrid($(this)[0])
		})
		
		$(document).on('click', '[data-action="handler_cancelFullGrid"]', function(e) {
			handler_cancelFullGrid($(this)[0])
		})

		$(document).on('click', '[data-action="handler_saveItem"]', function(e) {
			handler_saveItem($(this)[0])
		})
		
		function hasBodyValidateFormula(dataTable){
			var hasValidateFormula = null;
			var meta = dataTable.getMeta();
			for (var k in meta){
				hasValidateFormula = meta[k]['validateFormula'];
				if(hasValidateFormula)
					return true;
			}
			return false;
		}
		
		function handler_saveFullGrid(o){
			var dataTableID = (o.getAttribute("dataTableID"));
			if(fullGridOper.validateBodyForm(dataTableID).length > 0) {
				$.showMessageDialog({type:"info",title:"Prompt",msg: 'Inspection does not pass',backdrop:true});
				return;
			}
			
			var dataTable = fullGridOper.billForm.app.getDataTable(dataTableID);
			
			var hasValidateFormula = fullGridOper.hasValidateFormula;
			if(typeof hasValidateFormula == 'undefined'){
				hasValidateFormula = hasBodyValidateFormula(dataTable);
				fullGridOper.hasValidateFormula = hasValidateFormula;
			}
			
			if(hasValidateFormula){
				fullGridOper.billForm.app.serverEvent().addDataTable(dataTable.id).fire({
					ctrl:'FormularController',
					method:'execValidateFormulas',
					success: function(data){
						if(!fullGridOper.processFormulaResults(data))
							return;
						setLastRowFocus(dataTable);
						hideBodyEditor(o);
						if(typeof fullGridOper.ctrl != 'undefined' && fullGridOper.ctrl["afterSave"]){
							fullGridOper.ctrl["afterSave"].call(this,dataTable);
						}
					}
				})
			}else{
				setLastRowFocus(dataTable);
				hideBodyEditor(o);
				if(typeof fullGridOper.ctrl != 'undefined' && fullGridOper.ctrl["afterSave"]){
					fullGridOper.ctrl["afterSave"].call(this,dataTable);
				}
			}
		}
		
		function handler_cancelFullGrid(o){
			var dataTableID = (o.getAttribute("dataTableID"));
			var dataTable = fullGridOper.billForm.app.getDataTable(dataTableID);
			hideBodyEditor(o);
			dataTable.removeRow(fullGridOper.editIndex);
			//fullGridOper.editRow为空表明当前编辑是插入编辑或复制编辑，取消表示取消插入或取消复制
			if(fullGridOper.editRow)
				dataTable.insertRow(fullGridOper.editIndex, fullGridOper.editRow);
			fullGridOper.editIndex = -1, fullGridOper.editRow = null;
			setLastRowFocus(dataTable);
			if(typeof fullGridOper.ctrl != 'undefined' && fullGridOper.ctrl["afterCancel"]){
				fullGridOper.ctrl["afterCancel"].call(this,dataTable);
			}
		}
		
		function handler_saveItem(o){
			var dataTableID = (o.getAttribute("dataTableID"));
			if(fullGridOper.validateBodyForm(dataTableID).length > 0) {
				$.showMessageDialog({type:"info",title:"Prompt",msg: 'Inspection does not pass',backdrop:true});
				return;
			}
			
			var dataTable = fullGridOper.billForm.app.getDataTable(dataTableID);
			
			var hasValidateFormula = fullGridOper.hasValidateFormula;
			if(typeof hasValidateFormula == 'undefined'){
				hasValidateFormula = hasBodyValidateFormula(dataTable);
				fullGridOper.hasValidateFormula = hasValidateFormula;
			}
			
			if(hasValidateFormula){
				fullGridOper.billForm.app.serverEvent().addDataTable(dataTable.id).fire({
					ctrl:'FormularController',
					method:'execValidateFormulas',
					success: function(data){
						if(!fullGridOper.processFormulaResults(data))
							return;
						var row = dataTable.createEmptyRow();
						setLastRowFocus(dataTable);
						if(typeof fullGridOper.ctrl != 'undefined' && fullGridOper.ctrl["afterAddSave"]){
							fullGridOper.ctrl["afterAddSave"].call(this,dataTable);
						}
					}
				})
			}else{
				var row = dataTable.createEmptyRow();
				setLastRowFocus(dataTable);
				if(typeof fullGridOper.ctrl != 'undefined' && fullGridOper.ctrl["afterAddSave"]){
					fullGridOper.ctrl["afterAddSave"].call(this,dataTable);
				}
			}
		}
		
		//进入行增加状态，在表下面打开行增加编辑器
		function hideBodyEditor(o){
			var tb = $(o).closest("tbody");
			var currentTR = $(o).closest("tr")[0];
			var tf = $(o).closest("table").find("tfoot");
			//将表单所在行从tbody中转移到tfoot中
			tb[0].removeChild(currentTR);
			tf[0].appendChild(currentTR);
			//表体编辑表单中的按钮可见性控制
			$(currentTR).find("#body_add_action").show();
			$(currentTR).find("#body_edit_action").hide();
		}
		
		/**
		 * 选中最后一行操作，需要对缩略视图中的grid的UE调整，消除之前选中非最后一行的影响
		 */
		function setLastRowFocus(dataTable){
			dataTable.setRowSelect(dataTable.rows().length-1);
//			dataTable.setRowFocus(dataTable.rows().length-1);
		}
	}
	
	fullGridOper.registerEditEvent = function(){
		/**
	     * grid 自定义button render
	     */
		$(document).on('click', '[data-action="handler_edit"]', function(e) {
			handler_edit($(this)[0])
		})
		
		$(document).on('click', '[data-action="handler_remove"]', function(e) {
			handler_remove($(this)[0])
		})
		
		$(document).on('click', '[data-action="handler_copy"]', function(e) {
			handler_copy($(this)[0])
		})
		
		$(document).on('click', '[data-action="handler_add"]', function(e) {
			handler_add($(this)[0])
		})
	    
		//因为靠”隐藏被选中行”的功能，可能导致编辑缩略或详细图的行时，导致同时隐藏2种图中的行。
		//此处可能需要控制“缩略图”和“详细图”的切换可用性问题。
		function handler_edit(o){
			var currentTR = $(o).closest("tr");
			var index = parseInt(currentTR[0].getAttribute("index"));
			var dataTableID = (o.getAttribute("dataTableID"));
			var dataTable = fullGridOper.billForm.app.getDataTable(dataTableID);
			
			if(dataTable.getFocusIndex()>=0 && dataTable.getFocusIndex() < dataTable.rows().length-1) {
        		$.showMessageDialog({type:"info",title:"Prompt",msg: 'Please handle the current modification first',backdrop:true});
        		return;
        	}
			dataTable.setRowSelect(index);
			fullGridOper.editIndex = index;
			fullGridOper.editRow = $.extend(true,{}, dataTable.getRow(index));
			showBodyEditor(currentTR);
			fullGridOper.billForm.ajustScroll($('#detail_editor_' + dataTableID)[0]);
			if(typeof fullGridOper.ctrl != 'undefined' && fullGridOper.ctrl["afterEdit"]){
				fullGridOper.ctrl["afterEdit"].call(this,dataTable);
			}
		}
		
		//进入行编辑状态，在表中打开行编辑器
		function showBodyEditor(editTR){
			$(editTR).hide();
			var tf = $(editTR).closest("table").find("tfoot");
			var trInFoot = $(editTR).closest("table").find("tfoot").children("tr")[0];
			$(trInFoot).show();
			tf[0].removeChild(trInFoot);
			$(trInFoot).insertAfter($(editTR));
			$(trInFoot).find("#body_add_action").hide();
			$(trInFoot).find("#body_edit_action").show();
		}
		
		function handler_remove(o){
			var removeTR = $(o).closest("tr");
			var index = (removeTR[0].getAttribute("index"));
			var dataTableID = (o.getAttribute("dataTableID"));
			var dataTable = fullGridOper.billForm.app.getDataTable(dataTableID);
			if(dataTable.getFocusIndex()>=0 && dataTable.getFocusIndex() < dataTable.rows().length-1) {
        		$.showMessageDialog({type:"info",title:"Prompt",msg: 'Please handle the current modification first',backdrop:true});
        		return;
        	}
    		dataTable.removeRow(index);
    		var gridComps = fullGridOper.billForm.app.getCompsByType("grid");
    		for(var i = 0; i < gridComps.length; i++){
    			if(gridComps[i].dataTable && gridComps[i].dataTable['id'] == dataTableID){
    				gridComps[i].editRowFun(dataTable.rows().length-1);
    			}
    		}
    		if(typeof fullGridOper.ctrl != 'undefined' && fullGridOper.ctrl["afterDelete"]){
				fullGridOper.ctrl["afterDelete"].call(this,dataTable);
			}
		}
		
		function handler_copy(o){
			var originTR = $(o).closest("tr");
			var index = parseInt(originTR[0].getAttribute("index"));
			var dataTableID = (o.getAttribute("dataTableID"));
			var dataTable = fullGridOper.billForm.app.getDataTable(dataTableID);
			
			if(dataTable.getFocusIndex()>=0 && dataTable.getFocusIndex() < dataTable.rows().length-1) {
        		$.showMessageDialog({type:"info",title:"Prompt",msg: 'Please handle the current modification first',backdrop:true});
        		return;
        	}
			var currRow = dataTable.getRow(index);
			var insertIndex = index + 1;
			dataTable.copyRow(insertIndex, currRow);
			dataTable.setRowSelect(insertIndex);
			
			//复制后保存算成功复制，复制后取消算失败复制，所以fullGridOper.editRow为空
			fullGridOper.editIndex = insertIndex;
			var editTR = $(o).closest("tbody").find("tr[index='"+ insertIndex +"']")[0];
			showBodyEditor(editTR);
			fullGridOper.billForm.ajustScroll($('#detail_editor_' + dataTableID)[0]);
			if(typeof fullGridOper.ctrl != 'undefined' && fullGridOper.ctrl["afterCopy"]){
				fullGridOper.ctrl["afterCopy"].call(this,dataTable);
			}
		}
		
		function handler_add(o){
			var currentTR = $(o).closest("tr");
			var index = parseInt(currentTR[0].getAttribute("index"));
			var dataTableID = (o.getAttribute("dataTableID"));
			var dataTable = fullGridOper.billForm.app.getDataTable(dataTableID);
			if(dataTable.getFocusIndex()>=0 && dataTable.getFocusIndex() < dataTable.rows().length-1) {
        		$.showMessageDialog({type:"info",title:"Prompt",msg: 'Please handle the current modification first',backdrop:true});
        		return;
        	}
    		dataTable.insertRow(index);
    		dataTable.setRowSelect(index);
    		//插入后保存算成功插入，插入后取消算失败插入，所以fullGridOper.editRow为空
    		fullGridOper.editIndex = index;
    		var editTR = $(o).closest("tbody").find("tr[index='"+ index +"']")[0];
			showBodyEditor(editTR);
			if(typeof fullGridOper.ctrl != 'undefined' && fullGridOper.ctrl["afterAdd"]){
				fullGridOper.ctrl["afterAdd"].call(this,dataTable);
			}
		}
	}
	
	fullGridOper.billForm = {};
	
	return fullGridOper;
})
;
function substractStr(value){
	var v = value()
	if(!v) {
		return '';
	}
	v = v + ''
	if(v.lengthb() > 35) {
		return v.substrCH(35)+'...'
	}
	
	return v
}

function getRequest(url) {
        if(!url)
        	url = location.search; 
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
                 var str = url.substring(url.indexOf("?")+1);
                 strs = str.split("&");
                 for(var i = 0; i < strs.length; i ++) {
                        theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
                 }
        }
        return theRequest;
};
window.trimServerEventData = function(datasJson){
	return datasJson.replace(/,\"refmodel\":""/g,"").replace(/,\"label\":""/g,"").replace(/,\"refmodel\":.*?}"/g,"").replace(/,\"label\":\"[^"]*\"/g,"");
};
define("billutilcomp", function(){});

function formatNumber(precision, value){
	if(!value())
		return value();
	var maskerMeta = iweb.Core.getMaskerMeta('float') || {}
	maskerMeta.precision = precision()
	var formater = new $.NumberFormater(maskerMeta.precision);
	var masker = new NumberMasker(maskerMeta);
	return masker.format(formater.format(value())).value
}

function isEvenTR(index){
	if(index % 2 == 0){
		return 'true';
	}
	return 'false';
}

;
define("formattercomp", function(){});


define("billcomp", function(){});
