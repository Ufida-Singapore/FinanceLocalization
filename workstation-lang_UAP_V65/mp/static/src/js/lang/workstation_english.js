/********************************************
*
*	多语资源库(英文)--审批工作台多语(英文)
*
*********************************************/
function trans(key, obj){
	if(window.transMap == null){
		window.transMap = new Object;
		window.transMap.workstation_task = "Task";
		window.transMap.workstation_by_type = "By Type";
		window.transMap.workstation_by_time = "By Time";
		window.transMap.workstation_searchtask = "Enter the search task";
		window.transMap.workstation_search = "Search";
		window.transMap.workstation_more = "More...";
		window.transMap.workstation_documenttype = "Document Type:";
		window.transMap.workstation_other = "Other";
		window.transMap.workstation_documentcode = "Document Code";
		window.transMap.workstation_documentname = "Document Name";
		window.transMap.workstation_timelimit = "Time Limit:";
		window.transMap.workstation_all = "All";
		window.transMap.workstation_last_three_days = "Within three days";
		 
		window.transMap.workstation_recent_one_week = "Within one week";
		window.transMap.workstation_recent_one_month = "Within one month";
		window.transMap.workstation_earlier = "Earlier";
		window.transMap.workstation_processing_status = "Processing Status:";
		window.transMap.workstation_untreated = "Unprocessed";
		window.transMap.workstation_already_deal = "Porcessed";
		window.transMap.workstation_click_to_load_more = "Click to load more...";
		
		window.transMap.workstation_notice = "Notice";
		window.transMap.workstation_early_warning = "Alert";
		
		window.transMap.workstation_cancel_approval = "Cancel Approved";
		window.transMap.workstation_approval = "Approved";
		window.transMap.workstation_do_not_approval = "Not Approved";
		window.transMap.workstation_Process = "Process";
		window.transMap.workstation_Flow_Chart = "Flow Chart";
		window.transMap.workstation_more_operations = "More Operations&gt;&gt;";
		window.transMap.workstation_turn_down = "Reject";
		window.transMap.workstation_change = "Reassign";
		window.transMap.workstation_sign_up = "Countersign";
		window.transMap.workstation_enter_approval = "Please enter approval advice (default: approved) ...";
		window.transMap.workstation_enter_approv = "Please enter approval advice... ";
		window.transMap.workstation_assign_approval = "Please assign the approver:";
		window.transMap.workstation_choose_reject = "Choose to reject the step";
		window.transMap.workstation_choose_change = "Choose to reassign the processing staff";
		window.transMap.workstation_approval_advice_empty = "Approval comments can not be empty!";
		window.transMap.workstation_approval_cancel_sucess = "Cancel approval operation is completed!";
		window.transMap.workstation_approval_success = "Approval operation is completed!";
		
		window.transMap.workstation_dept_code = "Dept Code";
		window.transMap.workstation_dept_name = "Dept Name";
		window.transMap.workstation_dept = "Dept";
		
		window.transMap.workstation_flow_chart_andhistory = "Flow Chart And History";
		window.transMap.workstation_close = "Close";
		window.transMap.workstation_approve_link = "Approve step";
		window.transMap.workstation_sender = "Sender";
		window.transMap.workstation_send_date = "Send data";
		window.transMap.workstation_deal_people = "Processer";
		window.transMap.workstation_Processing_data = "Processing date";
		window.transMap.workstation_approve_status = "Approve status";
		window.transMap.workstation_approve_results = "Approve results";
		window.transMap.workstation_Cancel_approval_success = "Revoke approval success! ";
		window.transMap.workstation_Cancel_approval_failed = "Revoke approval failed!";
		
		window.transMap.workstation_enter_subject = "Please enter the subject";
		window.transMap.workstation_enter_sender = "Please enter the sender";
		window.transMap.workstation_theme = "Theme";
		window.transMap.workstation_themeandcomment = "Theme or comment";
		window.transMap.workstation_retrieve = "Retrieve";
		window.transMap.workstation_send_time = "Sending&nbsp;Time:";
		window.transMap.workstation_to = "To";
		window.transMap.workstation_please_integer = "Please enter the integer";
		window.transMap.workstation_pleanse_sendingtime = "Please enter the sending time";
		
		window.transMap.workstation_sponsor = "The Sender:";
		window.transMap.workstation_annex = "Annex&nbsp:";
		window.transMap.workstation_none = "None";
		window.transMap.workstation_no_attachments = "No attachments";
		window.transMap.workstation_last_approver = "Last&nbsp;Approver:";
		window.transMap.workstation_approval_comments = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Approval&nbsp;Comments:";
		window.transMap.workstation_processing_time = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Processing&nbsp;Time:";
		
		window.transMap.workstation_refresh_message = "Refresh&nbsp;Message";
		window.transMap.workstation_no_news_hoho = "No news ohoh~";
		
		window.transMap.workstation_sponsor_person = "Sender";
		
	}
	if(typeof(obj) != 'undefined'){
		return window.transMap[key].replace('{0}', obj);
	}
	return window.transMap[key];
}