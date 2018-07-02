/********************************************
*
*	多语资源库(英文)--审批工作台多语-英文
*
*********************************************/
function getCookieForLang() {
	var strcookie = document.cookie;//获取cookie字符串
	var arr = strcookie.split(';'); //分割cookie
	var lang = 'zh-cn';
	for(var i = 0;i<arr.length;i++){
        var arrStr = arr[i].split('='); //对各个cookie进行分割
        if(arrStr[0].trim() == 'LA_K1' && arrStr[1] == 'english') 
			return "en"; //判断是否存在cookie名称为LA_K1并返回
	};
	return lang;
};
function onloadLangSource() {
	//输入搜索任务
	if(document.getElementById("tasksearch")) {
		document.getElementById("tasksearch").setAttribute("placeholder",trans("workstation_searchtask"));
	}
	//刷新消息
	if(document.getElementById("refreshBtn")) {
		document.getElementById("refreshBtn").innerHTML = trans("workstation_refresh_message");
	}
	//全部
	if(document.getElementById("spanall")) {
		document.getElementById("spanall").innerHTML= trans("workstation_all");
	}
	//最近三天
	if(document.getElementById("lastthreedays")) {
		document.getElementById("lastthreedays").innerHTML= trans("workstation_last_three_days");
	}
	//最近一周
	if(document.getElementById("lastaweek")) {
		document.getElementById("lastaweek").innerHTML= trans("workstation_recent_one_week");
	}
	//最近一月
	if(document.getElementById("lastamouth")) {
		document.getElementById("lastamouth").innerHTML= trans("workstation_recent_one_month");
	}
	//更早
	if(document.getElementById("earlier")) {
		document.getElementById("earlier").innerHTML= trans("workstation_earlier");
	}
	//处理状态
	if(document.getElementById("processing_status")) {
		document.getElementById("processing_status").innerHTML= trans("workstation_processing_status");
	}
	//全部
	if(document.getElementById("allinfo")) {
		document.getElementById("allinfo").innerHTML= trans("workstation_all");
	}
	//未处理
	if(document.getElementById("waitdealtask")) {
		document.getElementById("waitdealtask").innerHTML= trans("workstation_untreated");
	}
	//已处理
	if(document.getElementById("alreadydealtask")) {
		document.getElementById("alreadydealtask").innerHTML= trans("workstation_already_deal");
	}
	//预警
	if(document.getElementById("early_warning")) {
		document.getElementById("early_warning").innerHTML= trans("workstation_early_warning");
	}
	//通知
	if(document.getElementById("index_notice")) {
		document.getElementById("index_notice").innerHTML= trans("workstation_notice");
	}
	//任务
	if(document.getElementById("index_task")) {
		document.getElementById("index_task").innerHTML= trans("workstation_task");
	}
	//更多
	if(document.getElementById("noticemsgselectmore")) {
		document.getElementById("noticemsgselectmore").innerHTML= trans("workstation_more");
	}
	if(document.getElementById("alertmsgselectmore")) {
		document.getElementById("alertmsgselectmore").innerHTML= trans("workstation_more");
	}
	if(document.getElementById("morecontent")) {
		document.getElementById("morecontent").innerHTML= trans("workstation_more");
	}
	//主题
	if(document.getElementById("spantheme")) {
		document.getElementById("spantheme").innerHTML = trans("workstation_theme");
	}
	//主题/内容
	if(document.getElementById("alertthemeInput")) {
		document.getElementById("alertthemeInput").setAttribute("placeholder",trans("workstation_themeandcomment"));
	}
	//至
	if(document.getElementById("workstation_to")) {
		document.getElementById("workstation_to").innerHTML = trans("workstation_to");
	}
	//发送时间
	if(document.getElementById("send_time")) {
		document.getElementById("send_time").innerHTML = trans("workstation_send_time");
	}
	//全部
	if(document.getElementById("alertAllInfo")) {
		document.getElementById("alertAllInfo").innerHTML= trans("workstation_all");
	}
	//未处理
	if(document.getElementById("alertWaitDeal")) {
		document.getElementById("alertWaitDeal").innerHTML= trans("workstation_untreated");
	}
	//已处理
	if(document.getElementById("alertDealed")) {
		document.getElementById("alertDealed").innerHTML= trans("workstation_already_deal");
	}
	//检索
	if(document.getElementById("alertSearchBtn")) {
		document.getElementById("alertSearchBtn").innerHTML= trans("workstation_retrieve");
	}
	//点击加载更多
	if(document.getElementById("click_to_load_more")) {
		document.getElementById("click_to_load_more").innerHTML = trans("workstation_click_to_load_more");
	}
	//发送人
	if(document.getElementById("send_person")) {
		document.getElementById("send_person").innerHTML = trans("workstation_sender");
	}
	//请输入发送人
	if(document.getElementById("alertSenderInput")) {
		document.getElementById("alertSenderInput").setAttribute("placeholder",trans("workstation_enter_sender"));
	}
	//部门编码
	if(document.getElementById("dept_code")) {
		document.getElementById("dept_code").innerHTML= trans("workstation_dept_code");
	}
	//部门名称
	if(document.getElementById("dept_name")) {
		document.getElementById("dept_name").innerHTML= trans("workstation_dept_name");
	}
	//部门
	if(document.getElementById("dept")) {
		document.getElementById("dept").innerHTML= trans("workstation_dept");
	}
	//单据类型
	if(document.getElementById("documenttype")) {
		document.getElementById("documenttype").innerHTML= trans("workstation_documenttype");
	}
	//时间范围
	if(document.getElementById("timelimit")) {
		document.getElementById("timelimit").innerHTML= trans("workstation_timelimit");
	}
	//请输入主题
	if(document.getElementById("themename")) {
		document.getElementById("themename").setAttribute("placeholder",trans("workstation_enter_subject"));
	}
	//请输入发送人
	if(document.getElementById("senderInput")) {
		document.getElementById("senderInput").setAttribute("placeholder",trans("workstation_enter_sender"));
	}
	//全部
	if(document.getElementById("all")) {
		document.getElementById("all").innerHTML= trans("workstation_all");
	}
	//未处理
	if(document.getElementById("untreated")) {
		document.getElementById("untreated").innerHTML= trans("workstation_untreated");
	}
	//已处理
	if(document.getElementById("already_deal")) {
		document.getElementById("already_deal").innerHTML= trans("workstation_already_deal");
	}
	//检索
	if(document.getElementById("searchBtn")) {
		document.getElementById("searchBtn").innerHTML= trans("workstation_retrieve");
	}
	//主题/内容
	if(document.getElementById("themeInput")) {
		document.getElementById("themeInput").setAttribute("placeholder",trans("workstation_theme"));
	}
	//请输入发送人
	if(document.getElementById("senderInput")) {
		document.getElementById("senderInput").setAttribute("placeholder",trans("workstation_enter_sender"));
	}
	//请输入整数
	if(document.getElementById("endtime")) {
		document.getElementById("endtime").setAttribute("errormsg",trans("workstation_please_integer"));
	}
	//全部
	if(document.getElementById("noticeAllInfo")) {
		document.getElementById("noticeAllInfo").innerHTML= trans("workstation_all");
	}
	//未处理
	if(document.getElementById("noticeWaitDeal")) {
		document.getElementById("noticeWaitDeal").innerHTML= trans("workstation_untreated");
	}
	//已处理
	if(document.getElementById("noticeDealed")) {
		document.getElementById("noticeDealed").innerHTML= trans("workstation_already_deal");
	}
	//发送人
	if(document.getElementById("spansender")) {
		document.getElementById("spansender").innerHTML = trans("workstation_sender");
	}
	window.onload=onloadLangSource;
}
