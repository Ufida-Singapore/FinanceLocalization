$(function(){
	$('.baritemTime').css("display","none");
	
	$('.baritemType > .sub-tabcontent a').click(function(){
		$('.baritemType > .sub-tabcontent a').attr('class','');
		$(this).attr('class','checked');
	});
	
	$('.baritemTime > .sub-tabcontent a').click(function(){
		$('.baritemTime > .sub-tabcontent a').attr('class','');
		$(this).attr('class','checked');
		
		app.msgs.trigger('filter');
	});
//	//显示附件
	$('.psonslfoolter').find('.attch').mouseover(
		function(){
			$(this).parent().find('.popover').css('display','block');
			
		}
	);

	
	$('#flow_img').mouseover(
			function(){return;
				$(this).parent().find('.popover').css('display','block');
			}
		);
	
	$('.psonslfoolter').find('.popover').hover(
			function(e){
				$(this).css('display','block');
			},
			function(e){
				$(this).css('display','none');
			}
	);
	
	$(document).on('click',function(e){
		 e = e || window.event;
		var target = $(e.target) || $(e.srcElement); 
		
		if(target.attr('another')=='another'){
			$('.billtype-tablediv').toggle();
		}
		if(target.attr('actioncode')=='unApprovenew'){
			unApprovenew("");
		}
		
		if(target.attr('actioncode')=='showFlowImg'){
			showFlowImg(target.attr('pk'));
		}
		
		if(target.closest('.popover-content').length==0){
			$('.popover').hide();
		}
		//改变用户选择的样式
		if($(e.target.parentNode).attr('class')=="people-tab" && target.attr('targetName')=="people"){
			$('.people-tab div').attr('class','');
			target.attr('class','checked');
		}
		if(target.attr('pevent')=='cancle'){
			$('.zzc').remove();
		}
		if(target.attr('targetName')=='people'){
			var tid = target.attr("id"	);
			$('.middlecontent').css('display','none');
	        $('.middlecontent[forkey="'+tid+'"]').css('display','block');
	        $('.moremiddlecontent').css('display','none');
	        $('.moremiddlecontent[forkey="'+tid+'"]').css('display','block');
	        $('#page3 .bootstrap-tablediv').find('.moremiddlecontent').css('display','block');
		}
		if(target.attr('href')=='#page1'){
			if($.trim($('.people-tablediv').html())==""){
				$('.people-tablediv').html($('.people-tablediv2').html());
				$('.people-tablediv2').html("");
				
				$('.people-tablediv').find('input').attr('checked',false);
				$('.people-tablediv').find('input').attr('checked',false);
				$('.people-tablediv').find('.checkedname').hide();
				//$('.people-tablediv').find('.sub-tab-hr').hide();
				
			}
			
		}else if(target.attr('href')=='#page2'){
			if($.trim($('.people-tablediv2').html())==""){
				$('.people-tablediv2').html($('.people-tablediv').html());
				$('.people-tablediv').html("");
				
				$('.people-tablediv2').find('input').attr('checked',false);
				$('.people-tablediv2').find('input').attr('checked',false);
				$('.people-tablediv2').find('.checkedname').hide();
				//$('.people-tablediv2').find('.sub-tab-hr').hide();
			}
		}
		//IE8兼容
		if(target.is('input') && target.attr('type')=='checkbox'){		
			//判断浏览器
			var ua = navigator.userAgent;
			ua = ua.toLocaleLowerCase();
			if (ua.match(/msie/) != null || ua.match(/trident/) != null) {
				var browserVersion = ua.match(/msie ([\d.]+)/) != null ? ua
						.match(/msie ([\d.]+)/)[1] : ua.match(/rv:([\d.]+)/)[1];
				if(browserVersion < 9.0){
					//遍历所有的checkbox  使其的checkbox都变为uncheck
					$('.checkboxFive > label').css("background","#fff");
					//只有本target的背景色改变
//					target.attr('checked',true);
//					target.parent().find('label').css("background","#0080cd");
					
					
					$('.checkboxFive').find('input:checked').each(function(){
						$(this).parent().find('label').css("background","#0080cd");
					});
						
							
				}
			}
			
		}
		if(target.attr('actioncode')=='moreChangeClass'){
			$(".tag_more").css("display", "block");
		}
		if(target.attr('actioncode')=='uploadClass'){
			if(e.target.nextElementSibling.firstElementChild.className=="upload_forbid"){
				//$(e.target).off('onchange');
				$(e.target).attr({"disabled":"disabled"});

			}
		}
		
		
		if(target.closest('.meta').length==0 && target.closest('.top_cusp').length==0){
			$('.top_cusp').hide();
		}
		
		if(target.attr('another')!='another'){
			if(!$('.billtype-tablediv').is(":hidden")){
				if(window.pending){
					window.pending = false;
				}
			}
			$('.billtype-tablediv').css('display','none');
		}
		//流程图tab切换
		if(target.attr('data-toggle')=="tab" || $(target).parent().attr('id')=="myTab"){
			changeTabClass(target,target.attr('href'));
		}
		$('.billtype-tablediv').on('click',function(e){
			stopPropagation(e); 
		}); 
	});
	
	$('[pevent="cancle"]').click(function(){
		$('.zzc').remove();
	});
	
	showorhide();
	$('#flow_img').click(function(e){
		showFlowImg($('#work_history').attr('pk'));
	});
	
/*	$('#flow_img').click(function(){
		var action = 'getHistory';
		if(!app.msgs.baseParam){
			app.msgs.baseParam = {
					actionCode: "showBill",
					msgtype: "TASK",
					isChangeState: false,
					pluginType: "default",
					sourceSystemCode: "UAP65",
					pk_sourcemsg:$('#work_history').attr('pk')
			};
		}
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
	            	pk_sourcemsg: baseParam.pk_sourcemsg
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
	});*/

	
	
	  var selects = $('#userRefTable').bootstrapTable('getSelections');
      if (selects.length > 0) {
      	var vname = "";
      	var vcode = new Array();
      	for(var i=0;i < selects.length;i++){
      		vname += selects[i].name+",";
      		vcode[i]=selects[i].code;
      	}
      	if(""!=vname){
      		vname = vname.substring(0,vname.length-1);
      	}
          $('button[data-target="userRefModal"]').html(vname);
          that.baseParam.reassign = vcode;//改派userpk
      }
      return true;
});//$function结束

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
function showFlowImg(pk){
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
         success: function(e) {debugger;
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
        title:trans("workstation_flow_chart_andhistory"),
        message: message,
        onEscape:function(){return true},
        backdrop:false,
        buttons: {
            cancel:{
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
			    title: trans("workstation_approve_link")
			},
            {
                field: 'sendman',
                'class': 'hidden-xs',
                title: trans("workstation_sender")
            },
            {
                field: 'senddate',
                'class': 'hidden-xs',
                align: 'center',
                title: trans("workstation_send_date")
            },
            {
                field: 'dealman',
                title: trans("workstation_deal_people")
            },
            {
                field: 'dealdate',
                align: 'center',
                title: trans("workstation_Processing_data")
            },
            {
                field: 'approvestatus',
                title: trans("workstation_approve_status")
            },
            {
                field: 'approveresult',
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




function changeClass(e,actionBtn){
	$('#'+e+' > .sub-tabcontent a').each(function(){
		if($(this).attr('actioncode')==actionBtn){
			$(this).attr('class',$(this).attr('class').replace('checked','')+' checked');
		}else{
			$(this).attr('class',$(this).attr('class').replace('checked',''));
		}
		
	});
}
function changeSlt(state){
	if(state==1){
		if( !window.titlehide){
			$('.barright').show();
		}
		
		if($('.barright2.typebarright2').is(':hidden')){
			$('.barright2.typebarright2 > .barcontentitem > .baritem.selected').attr('class','baritem');
			$('.barright2.typebarright2 > .barcontentitem > .baritem[all="all"]').attr('class','baritem selected');
			
			$('.barright2.timebarright2 > .barcontentitem > .baritem.selected').attr('class','baritem');
			$('.barright2.timebarright2 > .barcontentitem > .baritem[all="all"]').attr('class','baritem selected');
			
			app.msgs.trigger('filter');
		}
		
		$('.barright2.typebarright2').css("display","block");
		$('.barright2.timebarright2').css("display","none");
		$(".baritemType a[all='all']").attr('class','checked');
		
	}else{
		if($('.barright2.timebarright2').is(':hidden')){
			$('.barright2.timebarright2 > .barcontentitem > .baritem.selected').attr('class','baritem');
			$('.barright2.timebarright2 > .barcontentitem > .baritem[all="all"]').attr('class','baritem selected');
			
			$('.barright2.typebarright2 > .barcontentitem > .baritem.selected').attr('class','baritem');
			$('.barright2.typebarright2 > .barcontentitem > .baritem[all="all"]').attr('class','baritem selected');
	
			app.msgs.trigger('filter');
		}
		$('.barright').hide();
		$('.barright2.typebarright2').css("display","none");
		$('.barright2.timebarright2').css("display","block");
		$(".baritemTime a[all='all']").attr('class','checked');
	}
}
function removeelt(span){
	$(span).remove();
}

function showorhide(){
var len = $('#workflowiera').children('.approve-pass').length;
$('#workflowiera').children('.approve-pass').each(function(e){
	if(e!=0 && e<len-3){
		$(this).toggle();
	}
	
});
var target = $("[ifshow='approveopen']").find('.approve-bage');
if(target.html()=='1' && len>4){
//	target.html('<div class="whitecircle"></div><div class="whitecircle"></div><div class="whitecircle"></div>');
	target.css('background','url(/mp/img/approve-bage-whitecircle.png) no-repeat');
	$(target[0]).html('');
}else{
	target.css('background','url(/mp/img/approve-bage.png) no-repeat');
	$(target[0]).html('1');
}
$("[ifshow='approveopen']").css("cursor","pointer");
}

function unApprovenew(param){
    //批语
    var note=$('#agreemsg textarea').val();
    var flagmess = "";
    var baseParam = param;
    if(baseParam==""){
    	 baseParam = {
        		msgtype:"TASK",
        		sourceSystemCode:"UAP65",
        		pk_sourcemsg:$('#work_history').attr('pk'),
        		pluginType:"default",
        		actionCode:"unApprove",
        		isChangeState:true,
        		note:note
        		};
    }
  
    
    $.ajax({
        url: "/mp/msg/action",
        type: 'POST',
        data: JSON.stringify(baseParam),
        dataType: 'json',
        contentType: 'application/json',
        cache: false,
        beforeSend:function(XMLHttpRequest){
        	if(typeof checkLogin =='function'){
        		checkLogin(XMLHttpRequest);
        	}else if(typeof parent.checkLogin =='function'){
        		parent.checkLogin(XMLHttpRequest);
        	}else if(typeof top.checkLogin =='function'){
        		top.checkLogin(XMLHttpRequest);
        	}
        },
        success: function (ret) {
            if (ret.status==false) {
            	CloseDiv('MyDiv','fade');
            	$('.fancybox-overlay').show();
            	alerterrormsg(ret.msg?ret.msg:trans("workstation_Cancel_approval_failed"));
            } else {
            	//柔性判断begin
            	
            	if(ret.msg.indexOf("flag")>=0){
            		if(JSON.parse(ret.msg).flag.indexOf("fail")>=0){
	            		if(window.confirm(JSON.parse(ret.msg).value.message)){
	            	    	baseParam.forcePass='Y';
	            	    	baseParam.alertExceptionClass=JSON.parse(ret.msg).value.exceptionClass;
	            	    	unApprovenew(baseParam);
	            	    	return;
	            	    }else{
	            	    	return;
	            	    }
		            }else if(JSON.parse(ret.msg).flag.indexOf("success")>=0){
		            	flagmess = JSON.parse(ret.msg).message;
		            	if (flagmess) {
	                		alert(flagmess);
						}
		            }
            	}
            	//取消审批操作的单据id变换了，因此下一次要执行重新查询
            	app.params.refreshFlag=true;
            	CloseDiv('MyDiv','fade');
            	$('.fancybox-overlay').show();
            	var pk = $('#work_history').attr('pk');
            	$('.fancybox-overlay').find('iframe').contents().find('#'+pk).hide();
            	alertmsg(trans("workstation_Cancel_approval_success"));
            	var parentbody = "";
            	if($('#isdealed').val()=='dealed'){
            		parentbody = $('body',window.parent.document);
            	}else{
            		parentbody = $('body');
            	}
            	//修改首页左上角任务数量
            	var newcount = parseInt(parentbody.find("#task_tab_item").html())+1;
            	parentbody.find("#task_tab_item").html(newcount);
            	 
                	var tmpbilltype = window.billtype;
                	var billtypenotes = $('.burright.billtype .buritem');
                	
                		for(var i = 0;i<billtypenotes.length;i++){
                    		var code = $.trim(billtypenotes.eq(i).attr('data-code'));
                    		if(tmpbilltype==code){
                    			var numhtml = billtypenotes.eq(i).next().html();
                    				billtypenotes.eq(i).next().html(parseInt(numhtml)+1);
                    				billtypenotes.eq(i).next().attr('class','num_circle');
                    		}
                    	}
            	
            	if($('.fancybox-overlay').length <= 0){
            		var billtype = $('#'+pk).find('h2 a').attr('billtype');
            		
            		$('#'+pk).find('.status.status_did').attr('class','status');
            		$('#'+pk).find('h2 a').attr('ifdealed','waitdeal');
                     var aa =
                    	 	 '<input type="text" class="form-control input-txt noneinput" placeholder="请输入审批意见...">'+
                    	 	 '<a href="javascript:changeClass("'+pk+'","agree");" ifdealed="waitdeal" billtype="'+billtype+'" data="7" class="actionBtn" pk="'+pk+'" actioncode="agree">批准</a>'+
                     		 '<a href="javascript:changeClass("'+pk+'","disAgree");" ifdealed="waitdeal" billtype="'+billtype+'" data="7" class="actionBtn" pk="'+pk+'" actioncode="disAgree">不批准</a>'+
                     		 '<a href="javascript:changeClass("'+pk+'","getBill");" ifdealed="waitdeal" billtype="'+billtype+'" data="30" class="content_more" pk="'+pk+'" actioncode="getBill">更多操作&gt;&gt;</a>';
                     
                     $('#'+pk).find('.sub-tabcontent').empty().append($(aa));
            	}
            	
            	$('#'+pk).hide();
            }
        },
        error:function(e){        
        var errorStr = e.responseText.replace('\n','');
        errorStr=errorStr.replace('\t','');
        errorStr=errorStr.replace('\r\n','');
        var errorMsg=JSON.parse(errorStr);
        $.scojs_message(errorMsg.msg,  $.scojs_message.TYPE_ERROR);
        }
    });
}

function alertmsg(str){
	var childbody = $('.fancybox-overlay').find('iframe').contents().find('body');
	
	if(childbody.length <= 0){
		var layer=$('<div style="position: fixed;min-width: 150px;height: 50px;left: 50%;top: 50%;margin-left: -100px;background: #000;border-radius: 30px;opacity: 0.6;color:#fff;  text-align: center;  font-size: 14pxfont-family: 微软雅黑;  line-height: 50px;" id="layer">'+str+'</div>');
		if($('#layer').is(':hidden')==false){
			$('body').find('#layer').remove();
		}
		$('body').find('#layer').remove();
	    $('body').append(layer);
		$('#layer').fadeOut(5000); 
	}else{
		var layer=$('<div style="position: fixed;min-width: 150px;height: 50px;left: 50%;top: 50%;margin-left: -100px;background: #000;border-radius: 30px;opacity: 0.6;color:#fff;  text-align: center;  font-size: 14pxfont-family: 微软雅黑;  line-height: 50px;" id="layer">'+str+'</div>');

		if(childbody.find('#layer').is(':hidden')==false){
			childbody.find('#layer').remove();
		}
		childbody.find('#layer').remove();
		childbody.append(layer);
		childbody.find('#layer').fadeOut(5000); 
	}

}

function alerterrormsg(str){
	var str = '<div class="msg_container zzc">'+
		'<div class="black_overlaynew zzc">'+	
		'</div>'+
		'<div class="white_contentnew zzc">'+
			'<div class="assignpeo_top"><span>错误提示</span></div>'+
			'<div class="assignpeo_middle"><div class="errorleft"><div class="errorred_circle"><span class="heng"></span></div></div><div class="errorright"><p>'+str+'</p></div></div>'+
			'<div class="assignpeo_bottom">'+
	//			'<div class="laseone"><button type="button" class="btn btn-fail btn-xs actionBtn dealcancle" pevent="cancle">取消</button></div>'+
	        	'<div class="lasttwo"><button type="button" class="btn btn-success btn-xs actionBtn dealcancle"   pevent="cancle" style="margin-right:20px;">确定</button></div>'+
			'</div>'+
		'</div>'+
	'</div>';
	
	var parentbody = "";
	if($('#isdealed').val()=='dealed'){
		parentbody = $('body',window.parent.document);
	}else{
		parentbody = $('body');
	}
	
	if($('#isdealed').val() == 'dealed'){
	//	$(str).appendTo('body');
		parentbody.append($(str));
		
		var show_div = "white_contentnew";
		var bg_div="black_overlaynew";
		parentbody.find('.'+show_div).css('display','block');
		parentbody.find('.'+bg_div).css('display','block');
		parentbody.find('.'+bg_div).css('width',parentbody[0].scrollWidth);
		parentbody.find("."+bg_div).height($(document).height());
	}else{
		$(str).appendTo('body');
		var show_div = "white_contentnew";
		var bg_div="black_overlaynew";
		$('.'+show_div).css('display','block');
		$('.'+bg_div).css('display','block');
		$('.'+bg_div).css('width',document.body.scrollWidth);
		$("."+bg_div).height($(document).height());
	}

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