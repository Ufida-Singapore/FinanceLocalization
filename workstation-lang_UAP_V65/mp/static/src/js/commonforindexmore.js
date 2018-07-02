/**
 * New node file
 */
$(function(){
	$('.refer_list.ul_list2.scrollbar-dynamic.scroll-content').unbind('click').bind('click',function(){
		alert($(this).attr('pk'));
	});
	
	
	$(document).on('click',function(e){
	e = e || window.event;
	var target = $(e.target) || $(e.srcElement); 
		if(target.attr('id')=="senderInput") return;
		if(target.attr('pevent')=='cancle'){
			$('.zzc').remove();
		}
		//改变用户选择的样式
		if($(e.target.parentNode).attr('class')=="people-tab" && target.attr('targetName')=="people"){
			$('.people-tab div').attr('class','');
			target.attr('class','checked');
		}
		if(target.attr('targetName')=='people'){
			var tid = target.attr("id"	);
			$('.middlecontent').css('display','none');
	        $('.middlecontent[forkey="'+tid+'"]').css('display','block');
	        $('.moremiddlecontent').css('display','none');
	        $('.moremiddlecontent[forkey="'+tid+'"]').css('display','block');
	        $('#page3 .bootstrap-tablediv').find('.moremiddlecontent').css('display','block');
		}
		if(target.attr('actioncode')=='moreUploadClass'){
			if(e.target.nextElementSibling.firstElementChild.className=="upload_forbid"){
				//$(e.target).off('onchange');
				$(e.target).attr({"disabled":"disabled"});
	
			}else{
				var pk = e.target.attributes[3].value;
				var id="fileToUpload"+pk;
				$(e.target).attr({"id":id});
			}
	    }
		//流程图按钮
		if(target.attr('actioncode')=='showFlowImg'){
			debugger;
			showFlowImgMore(target.attr('pk'));
		}
		//流程图tab切换
		if(target.attr('data-toggle')=="tab" || $(target).parent().attr('id')=="myTab"){
			changeTabClass(target,target.attr('href'));
		}
		
		
		$('#senderPopDiv').css('display','none');
		$('#senderPopDiv').on('click',function(e){ 
			stopPropagation(e); 
		});
		if(target.attr('pop')!='pop'){
			$('.billtype-tabledivmore').css('display','none');
		}
		
		$('.billtype-tabledivmore').on('click',function(e){ 
			stopPropagation(e); 
		});
	});
	
	
	
	
	$('#searchBtn').click(function(){
		if(window.morepending){
			return;
		}
		window.morepending = true;
		app.msgs.trigger('filter');
	});
	//发送人输入框为空时将data-code设置为空
	$('#senderInput').change(function(){
		if($(this).val()==""){
			$(this).attr('data-code','');
		}
	});

});//$fundtion 结束

//流程图tab样式修改
function changeTabClass(target,href){
    var hrefvar = href;
    $('#'+$(target).parent().parent().attr('id')+' li').each(function(){
		if($(this).find('a').attr('href')==hrefvar){
			$(this).find('a').attr('style','color: #006da6;');
		}else{
			$(this).find('a').attr('style','color: #4d4d4d;');
		}
    });
}
function showFlowImgMore(pk){
	var action = 'getHistory';
		app.msgs.baseParam = {
				actionCode: "showBill",
				msgtype: "TASK",
				isChangeState: false,
				pluginType: "default",
				sourceSystemCode: "UAP65",
				pk_sourcemsg:pk
		};
    app.msgs.baseParam.isChangeState=false;
    var baseParam=app.msgs.baseParam;
    var cloneParam=$.extend({},app.msgs.baseParam);
    var param = function (params) {
        return $.extend(cloneParam, {actionCode: action});
    };
     $.ajax({
         type: "GET",
         url: "/mp/msg/attachment",
         dataType: 'json',
         data: {
            	type:"flowImageWithSub",
            	sourceSystemCode:baseParam.sourceSystemCode,
            	pk_sourcemsg: baseParam.pk_sourcemsg,
            	_timestamp:new Date().getTime()
         },
         cache : false,
         success: function(data) {debugger;
          },
          error:function(e){debugger;
                         //href="/mp/staticResDir/UAP65waitdeal0001B110000000005DQLsub.png";
                         var arr = e.responseText.split(",");
                         //拼接样式以及展现
                         messageConstrct(arr);
                         //表格查询
                         tableQuery(param);
                         //生成图像
                         imgConstrct(arr,0);
          }
     }); 
};

function messageConstrct(arr){
    //拼接页面tab样式begin
    var count=arr.length;
    var messagemyTab='<ul id="myTab" class="nav nav-tabs" style="background-color:#f0f0f0;">'+
    '<li class="active"><a href="#mainflow" data-toggle="tab" style="color:#006da6;">'+arr[0].split("=")[0].replace(/\"/g,"").replace(/\{/g,"").replace(/\}/g,"").replace(/\[/g,"").replace(/\]/g,"").trim()+'</a></li>';
	var messagemyTabchild = '';
	for(var i=1; i<count;i++){
	   messagemyTabchild+='<li><a href="#branchflow'+i+'"data-toggle="tab" style="color:#4d4d4d;">'+arr[i].split("=")[0].replace(/\"/g,"").replace(/\{/g,"").replace(/\}/g,"").replace(/\[/g,"").replace(/\]/g,"").trim()+'</a></li>';
	}
	
	if(count>0){
	   messagemyTab+=messagemyTabchild+'</ul>';
	}else{
	   messagemyTab+='</ul>';
	}
			
	var messagemyTabContent='<div id="myTabContent" class="tab-content" style="border:1px solid #ddd;border-top: none;">'+
	    	    '<div class="tab-pane fade in active" id="mainflow">'+
	                '<div class="showFlowInfo">'+
	                 	'<div class="panel-body">'+
	                 		'<a href="#" class="branchflowImg0"></a>'+
	                 	'</div>'+
	                 	'<table class="table"  data-height="150"></table>'+
	                '</div>'+
	            '</div>';
			
	var messagemyTabContentchild = '';		
	for(var j=1; j<count;j++) {
	    messagemyTabContentchild+='<div class="tab-pane fade" id="branchflow'+j+'">'+
	    		        '<div class="showFlowInfo">'+
	    			 			'<div class="panel-body">'+
	    			 				'<a href="#" class="branchflowImg'+j+'"></a>'+
	    			 			'</div>'+
	    		 			    '<table class="table"  data-height="150"></table>'+
	    		 		'</div>'+
	        	    '</div>';
	}	
	if(count>0){
	    messagemyTabContent+=messagemyTabContentchild+'</div>';
	}else{
	    messagemyTabContent+='</div>';
	}
    var message=messagemyTab+messagemyTabContent;
    bootbox.dialog({
    	//流程图及审批历史
        title:trans("workstation_flow_chart_andhistory"),
        message: message,
        onEscape:function(){return true},
        backdrop:false,
        buttons: {
            cancel:{
        		//关闭
                label:trans("workstation_close"),
                className: "btn-default  btn-sm"
            }
        }
    });
}
//表数据查询
function tableQuery(param){
  //流程图数据查询
    $('.showFlowInfo table').bootstrapTable('destroy').bootstrapTable({
        method: 'POST',
        url: '/mp/msg/action',
        contentType: 'application/json',
        jsonToSend: true,
        cache: false,
        queryParams: param,
        height: 200,
        columns: [
			{
			    field: 'approvelink',
			    'class': 'hidden-xs',
			    //审批环节
			    title: trans("workstation_approve_link")
			},
            {
                field: 'sendman',
                'class': 'hidden-xs',
                //发送人
                title: trans("workstation_sender")
            },
            {
                field: 'senddate',
                'class': 'hidden-xs',
                align: 'center',
                //发送日期
                title: trans("workstation_send_date")
            },
            {
                field: 'dealman',
                //处理人
                title: trans("workstation_deal_people")
            },
            {
                field: 'dealdate',
                align: 'center',
                //处理日期
                title: trans("workstation_Processing_data")
            },
            {
                field: 'approvestatus',
                //审批状态
                title: trans("workstation_approve_status")
            },
            {
                field: 'approveresult',
                //审批结果
                title: trans("workstation_approve_results"),
                align: 'center',
                valign: 'middle',
                clickToSelect: true
            }
        ]
    });
}

function imgConstrct(arr,count){
		var href= arr[count].split("=")[1];
		href = href.substr(0,href.indexOf('.'))+".png";
		var src = href.substr(0,href.indexOf('.'))+".min.png";
		var img = new Image();
		var classid = '.branchflowImg' + count;
	    $(classid).append('<div class="loader" ></div>');
	    $(img).load(function (ret) {
	    	//结果设置
	        $(classid).empty().append($(this));
	        $(classid).attr('href', href+"?"+new Date().getTime());

	        $(classid).data("jqzoom",null);
	        $(classid).jqzoom({
	            zoomType: 'innerzoom',
	            title: false,
	            lens: false,
	            showEffect: 'fadein',
	            hideEffect: 'fadeout'
	        });
	        //开始下一次循环
	        if(count+1<arr.length){
	        	imgConstrct(arr,count+1);
	        }
	    }).attr({
	             src: src+"?"+new Date().getTime()
	        }).error(function (e) {
	            $(classid).empty();
	        });
}

function CheckBrowser() {
    ua = navigator.userAgent;
    ua = ua.toLocaleLowerCase();
    var browserType="";
    var browserVersion="";
    if (ua.match(/msie/) != null || ua.match(/trident/) != null) {
        browserType = "IE";
        //哈哈，现在可以检测ie11.0了！
        browserVersion = ua.match(/msie ([\d.]+)/) != null ? ua.match(/msie ([\d.]+)/)[1] : ua.match(/rv:([\d.]+)/)[1];
    } else if (ua.match(/firefox/) != null) {
        browserType = "火狐";
    } else if (ua.match(/opera/) != null) {
        browserType = "欧朋";
    } else if (ua.match(/chrome/) != null) {
        browserType = "谷歌";
    } else if (ua.match(/safari/) != null) {
        browserType = "Safari";
    }
    var arr = new Array(browserType, browserVersion);
    return arr;
}
function stopPropagation(e) { 
	if (e.stopPropagation) 
		e.stopPropagation(); 
	else 
		e.cancelBubble = true; 
	} 