/********************************************
*
*	多语资源库(中文)--审批工作台多语(中文)
*
*********************************************/
function trans(key, obj){
	if(window.transMap == null){
		window.transMap = new Object;
		window.transMap.workstation_task = "任务";
		window.transMap.workstation_by_type = "按类型";
		window.transMap.workstation_by_time = "按时间";
		window.transMap.workstation_searchtask = "输入搜索任务";
		window.transMap.workstation_search = "搜索";
		
		window.transMap.workstation_loadingdata = "正在加载数据中，请稍候……";
		window.transMap.workstation_no_searchresult = "没有找到匹配的记录";
		
		window.transMap.workstation_more = "更多...";
		window.transMap.workstation_documenttype = "单据类型:";
		window.transMap.workstation_other = "其它";
		window.transMap.workstation_documentcode = "编码";
		window.transMap.workstation_documentname = "单据名称";
		window.transMap.workstation_timelimit = "时间范围:";
		window.transMap.workstation_all = "全部";
		window.transMap.workstation_last_three_days = "最近三天";
		 
		window.transMap.workstation_recent_one_week = "最近一周";
		window.transMap.workstation_recent_one_month = "最近一月";
		window.transMap.workstation_earlier = "更早";
		window.transMap.workstation_processing_status = "处理状态:";
		window.transMap.workstation_untreated = "未处理";
		window.transMap.workstation_already_deal = "已处理";
		window.transMap.workstation_click_to_load_more = "点击加载更多...";
		
		window.transMap.workstation_notice = "通知";
		window.transMap.workstation_early_warning = "预警";
		
		window.transMap.workstation_cancel_approval = "取消审批";
		window.transMap.workstation_approval = "批准";
		window.transMap.workstation_do_not_approval = "不批准";
		window.transMap.workstation_Process = "流程";
		window.transMap.workstation_Flow_Chart = "流程图";
		window.transMap.workstation_more_operations = "更多操作&gt;&gt;";
		window.transMap.workstation_turn_down = "驳回";
		window.transMap.workstation_change = "改派";
		window.transMap.workstation_sign_up = "加签";
		window.transMap.workstation_enter_approval = "请输入审批意见(默认为 ：批准)...";
		window.transMap.workstation_enter_approv = "请输入审批意见...";
		window.transMap.workstation_assign_approval = "请指派审批人:";
		window.transMap.workstation_choose_reject = "选择驳回环节";
		window.transMap.workstation_choose_change = "选择改派处理人员";
		window.transMap.workstation_approval_advice_empty = "审批意见不能为空!";
		window.transMap.workstation_approval_cancel_sucess = "取消审批操作完成!";
		window.transMap.workstation_approval_success = "批准操作完成!";
		
		window.transMap.workstation_dept_code = "编码";
		window.transMap.workstation_dept_name = "名称";
		window.transMap.workstation_dept = "部门";
		
		window.transMap.workstation_flow_chart_andhistory = "流程图及审批历史";
		window.transMap.workstation_close = "关闭";
		window.transMap.workstation_approve_link = "审批环节";
		window.transMap.workstation_sender = "发送人:";
		window.transMap.workstation_send_date = "发送日期";
		window.transMap.workstation_deal_people = "处理人";
		window.transMap.workstation_Processing_data = "处理日期";
		window.transMap.workstation_approve_status = "审批状态";
		window.transMap.workstation_approve_results = "审批结果";
		window.transMap.workstation_Cancel_approval_success = "取消审批操作成功！";
		window.transMap.workstation_Cancel_approval_failed = "取消审批操作失败！";
		
		window.transMap.workstation_enter_subject = "请输入主题";
		window.transMap.workstation_enter_sender = "请输入发送人";
		window.transMap.workstation_theme = "主       题";
		window.transMap.workstation_themeandcomment = "主题/内容";
		window.transMap.workstation_retrieve = "检索";
		window.transMap.workstation_send_time = "发送时间:";
		window.transMap.workstation_to = "至";
		window.transMap.workstation_please_integer = "请输入整数";
		window.transMap.workstation_pleanse_sendingtime = "请输入发送时间";
						
		window.transMap.workstation_sponsor = "发件人";
		window.transMap.workstation_annex = "附件&nbsp;:";
		window.transMap.workstation_none = "无";
		window.transMap.workstation_no_attachments = "没有附件";
		window.transMap.workstation_last_approver = "上一审批人:";
		window.transMap.workstation_approval_comments = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批意见:";
		window.transMap.workstation_processing_time = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批时间:";
		
		window.transMap.workstation_refresh_message = "刷新消息";
		window.transMap.workstation_no_news_hoho = "没有消息哦";
		
		window.transMap.workstation_sponsor_person = "发起人:";
		
		//提示信息
		window.transMap.message_approval_none = "审批意见不能为空！";
		window.transMap.message_error_message = "错误提示";
		window.transMap.message_approval_ok = "确定";		
		window.transMap.message_not_setup_link = "没有设置环节人员！";	
		window.transMap.message_not_reject_links = "没有选择驳回环节！";	
		
	}
	if(typeof(obj) != 'undefined'){
		return window.transMap[key].replace('{0}', obj);
	}
	return window.transMap[key];
}