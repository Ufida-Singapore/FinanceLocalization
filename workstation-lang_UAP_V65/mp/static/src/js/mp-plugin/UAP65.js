!function ($) {

    'use strict';

    // TOOLS DEFINITION
    var hasAttr = function (obj, attr) {
        return (obj&&(typeof obj[attr] !== 'undefined'));
    };

    //CLASS DEFINITION
    var UAP65 = function (article, msgitemview,isMobielView) {
    	this.shownName="指派处理人员";
        this.$article = $(article);
        this.view=msgitemview;
        this.isMobielView=isMobielView;
        var that = this; 
        article.off('click', '.actionBtn').on('click', '.actionBtn', function (e) {
            var action = $(e.target).attr('actioncode');
            var articleid = $(e.target).attr('pk');
            window.billtype=$(e.target).attr('billtype');
            that.action=action;
            that.baseParam.actionCode=that.action;
            
            that.baseParam.tmpnote = $('#'+articleid).find('.noneinput').val();
            
            //本操作是否改变单据状态
            that.baseParam.isChangeState=true;
            that.title=$(e.target).html();
            var noteDefaultMap=that.noteDefaultMap;
            if(noteDefaultMap && typeof noteDefaultMap[that.action] !=='undefined'){
                that.baseParam.note=noteDefaultMap[that.action];
            }else{
                that.baseParam.note='';
            }
            var btn = $(e.target).closest(".ladda-button").get(0);
            if(!btn){
                var btn=$(e.target).closest(".btn-group").find(".ladda-button").get(0);
            }
            if($(btn).data('Ladda')){
                that.$busyIndicator = $(btn).data('Ladda');
            }else{
            	//适配IE8当btn为undefined时，不进行下面的操作
            	if(btn){
	                var busyIndicator = Ladda.create(btn);
	                $(btn).data('Ladda',busyIndicator);
	                that.$busyIndicator = busyIndicator;
            	}
            }
            if(action!="showFlowImg"){
            	that.trigger(action);
            }
        });

        article.off('click', '.content_more').on('click', '.content_more', function (e) {
        	//如果有特定url，则进行页面跳转，不显示页面详情 begin
        	var url = $(e.target).attr('ncurl');
        	//url="http://20.10.129.68:6588/portal/pt/home/view?pageModule=oapub&pageName=oaindex";
        	//url="http://20.10.129.68:6588/portal/pt/home/index?lrid=1";
        	if(url=="noportalcard"){
        		$.scojs_message('很报歉目前不能在审批工作台处理该工作任务，请您到NC系统中进行处理！', $.scojs_message.TYPE_ERROR);
        		return;
        	}
        	if(url!=null && url.indexOf('errormsg')>=0) {//以errormsg开始的点击后显示错误信息
                $.scojs_message(url.substr(9,url.length), $.scojs_message.TYPE_ERROR);
                return;
            }
        	if(typeof url !=='undefined' && url != "#"  && url!="" && url!=null){
        		//ajax调用action
        		//var pk = $(e.target).attr('pk');
/*       			var data = {actionCode: "AppendPortalLogin",actionType:"default",url:url,msgSystem:null};
    	        $.ajax({
    	        	url: "/mp/msg/appendPortalLogin",
    	            type:'GET',
    	            dataType: 'JSON',
    	            data:data,
    	            cache : false,
    	            success: function(ret) {
    	               var url = JSON.parse(ret.message).url;
    	            },
		          error:function(e){
		               $.scojs_message('错误', $.scojs_message.TYPE_ERROR);
		          }
    	        });*/
               that.baseParam.isChangeState=true
                that.baseParam.url=url;
                var cloneParam=$.extend({},that.baseParam);
                $.ajax({
                    url: "/mp/msg/action",
                    type: 'POST',
                    data: JSON.stringify($.extend(cloneParam, {actionCode: "AppendPortalLogin"})),
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
                    	if(typeof ret.isChangeState !=='undefined'){
                            var url = ret.msg;
                            //打开一个新页面
                            window.open(url);
                    	}else{
                    		var errormsg = ret.msg;
                    		$.scojs_message(errormsg, $.scojs_message.TYPE_ERROR);
                    	}
                   	//nc弹出页面，不打开任务详情
/*                   	$.fancybox.open({
                   		href :url,
                   		helpers: {
                              title : {
                   	                type : 'inside',
                                        position : 'top'					
                                      }
                           },
                           padding:20,
                           margin:0,
                           fitToView: true,
                           width: ($(window).width()*0.8),
                           height:$(window).height(),
                           autoSize: false,
                   		type:'iframe',
                           closeClick: true
                   	});*/
                    }
                });
        		return;
        	}
        	//end
 	       //影像扫描begin
/*       	    $('#img_scan').attr('pk',$(e.target).attr('pk'));
       	    $('#img_scan').attr('pluginType',that.baseParam.pluginType);*/
       	    //end
       	   //影像查看begin
       	    $('#img_show').attr('pk',$(e.target).attr('pk'));
       	    $('#img_show').attr('pluginType',that.baseParam.pluginType);
       	    //end
        	window.ifdealed = $(e.target).attr('ifdealed');
        	 window.billtype=$(e.target).attr('billtype');
	       
        	 $('#work_history').attr('pk',$(e.target).attr('pk'));
	 	        var action = 'showBill';
	 	        that.action=action;
	 	        that.baseParam.actionCode=that.action;
	 	        //本操作是否改变单据状态
	 	        that.baseParam.isChangeState=true;
	 	        that.title=$(e.target).html();
	 	        var noteDefaultMap=that.noteDefaultMap;
	 	        if(noteDefaultMap && typeof noteDefaultMap[that.action] !=='undefined'){
	 	            that.baseParam.note=noteDefaultMap[that.action];
	 	        }else{
	 	            that.baseParam.note='';
	 	        }
	 	        app.msgs.baseParam=that.baseParam;
	 	        var cloneParam=$.extend({},that.baseParam);
        	 
        	 
        	 if($('#isdealed').val() == 'dealed' || ($('#isdealed').val() == 'waitdeal' && window.ifdealed=='dealed')){
	        	that.forChildrenBody(that);
	         }else{
	        	
	 	        //初始化改派加签用户
	 	        that.initgpperson();
//	 	        初始化流程历史
	 	        var flage=that.initwfhistory();
	 	        //任务被收回
	 	        if(flage=="0"){
	 	        	return;
	 	        }
//	 	        初始化同意、不同意用户
	 	        that.assignusermore();
//	 	        初始化按钮事件
	 	        that.initactionbtnevent();	        
//	 		        初始化驳回环节
	 	        that.initrejectitem();
	 	        $.ajax({
	 	            url: "/mp/msg/action",
	 	            type: 'POST',
	 	            data: JSON.stringify($.extend(cloneParam, {actionCode: 'showBill',isChangeState:false, action: action})),
	 	            dataType: 'text',
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
	 	                //返回的数据是字符串，暂时这样区分
	 	                if(ret.indexOf('status')!=-1&&ret.indexOf('msg')!=-1){
	 	                	 ret= JSON.parse(ret);
	 	                	
	 	                     $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
	 	
	 	                } else {
	 	                	//初始化加签选择列表
	 	   	 	        that.showJqPersonTab('parent');
	 	                		var pageSize = 3;
	 	                		$('#userRefTablenew').bootstrapTable('destroy').bootstrapTable({
	 		                        url: '/mp/msg/action',
	 		                        cache: false,
	 		                        contentType: 'application/json',
	 		                        jsonToSend: true,
	 		                        method: 'POST',
	 		                        striped: true,
	 		                        pagination: true,
	 		                        pageSize: pageSize,
	 		                        minimumCountColumns: 2,
	 		                        queryParams: function (res) {
	 		                            res.search = res.search || '';
	 		                            res = $.extend(res, that.baseParam);
	 		                            return $.extend(res, {actionCode: 'getUserList', ref_type: 'user',isChangeState:false});
	 		                        },
	 		                        responseHandler: function (res) {
	 		                            var ret = {};
	 		                            if (res) {
	 		                                ret.total = res.ref_count;
	 		                                ret.rows = res.ref_result;
	 		                            }
	 		                            return ret;
	 		                        },
	 		                        clickToSelect: true
	 		                    }).on('check.bs.table',function(e,row){
	 			                   
	 		                    	
	 			                    if($("#joinperson span[data-code='"+row.code+"']")==undefined || $("#joinperson span[data-code='"+row.code+"']").length == 0){
	 			                    	var val = "<span class='ss-item' onclick='removeelt(this)' data-name='"+row.name+"' data-value='2' data-code='"+row.code+"'><b>"+row.name+"</b><i></i></span>";		
	 			                    	$('#joinperson').append(val);
	 			                    }
	 		                    	
	 		                    }).on('uncheck.bs.table',function(e,row){
	 			                    $('#joinperson span').each(function(){
	 			                    	if($(this).attr('data-code')==row.code){
	 			                    		$(this).remove();
	 			                    		return;
	 			                    	}
	 			                    });
	 		                    }).on('check-all.bs.table',function(e){
	 		                    	var tdata = $('#userRefTablenew').bootstrapTable('getSelections');
	 		                    	for(var i=0;i<tdata.length;i++){
	 		                    		var row = tdata[i];
	 		                    		if($("#joinperson span[data-code='"+row.code+"']")==undefined || $("#joinperson span[data-code='"+row.code+"']").length == 0){
	 					                    	var val = "<span class='ss-item' onclick='removeelt(this)' data-name='"+row.name+"' data-value='2' data-code='"+row.code+"'><b>"+row.name+"</b><i></i></span>";		
	 					                    	$('#joinperson').append(val);
	 					                   }
	 		                    	}
	 		                   
	 		                    }).on('uncheck-all.bs.table',function(e,name,args){
//	 		                    	$('#joinperson span').remove();
	 		                    	var tdata = $('#userRefTablenew').bootstrapTable('getData');
	 		                    	for(var i=0;i<tdata.length;i++){
	 		                    		var row = tdata[i];
	 		                    		$("#joinperson span[data-code='"+row.code+"']").remove();

	 		                    	}
	 		                    });
	 		                	
//	 		                	$('#body').css('overflow-y','hidden');
//	 		                	$('.MyDiv_right').css('overflow-y','auto');
	 	                		//加载单据正文
	 	                		$(".black_overlay").height("100%");
		 	               	    $(".MyDiv_left").height("100%");
		 	               	    $(".MyDiv_right").height("100%");
	 	                		//$('.preview_bill_content').html(ret);             			 		                	
	 		                	var msgid=$('#work_history').attr('pk');
	 		                    var msg=app.msgs.get(msgid);
	 		                  
	 		                    var curr_add_flowAttr = msg.curr_flowAttr;
	 		                    delete msg.curr_flowAttr;
	 		                    
	 		                    var attchstr='';
	 		                    var num = 0;
	 		                    if(curr_add_flowAttr){
	 		                    	for(var i = 0;i<curr_add_flowAttr.length;i++){
		 		                    	 
	      								if(curr_add_flowAttr[i].attrname==undefined) continue; //如果不加这句，ie8会出错
			 		                      var type = curr_add_flowAttr[i].attrname.substring(curr_add_flowAttr[i].attrname.lastIndexOf(".")+1);
			 		                   	  var imgstr="";
			 		       	             /* if(type.indexOf("xls")>=0){
			 		       	            	  imgstr="<img src='/mp/img/excel-01.svg'/>";
			 		       	              }else if(type.indexOf("doc")>=0){
			 		       	            	  imgstr="<img src='/mp/img/word-01.svg'/>";
			 		       	              }else if(type.indexOf("ppt")>=0){
			 		       	            	  imgstr="<img src='/mp/img/ppt-01.svg'/>";
			 		       	              }else if(type.indexOf("pdf")>=0){
			 		   	            	      imgstr="<img src='/mp/img/pdf-01.svg'/>";
			 		   	                  }else{
			 		       	            	  imgstr="<img src='/mp/img/normal-01.svg'/>";
			 		       	              }*/
			 		                   	var arr = CheckBrowser();
			 		 	              if(type.indexOf("xls")>=0){
			 		 	            	  if(arr && arr[0]=='IE'){
			 		 	            		  imgstr="<img src='/mp/img/excel-01.png'/>";
			 		 	            	  }else{
			 		 	            		  imgstr="<img src='/mp/img/excel-01.svg'/>";
			 		 	            	  }
			 		 	              }else if(type.indexOf("doc")>=0){
			 		 	            	  if(arr && arr[0]=='IE'){
			 		 	            		  imgstr="<img src='/mp/img/word-01.png'/>";
			 		 	            	  }else{
			 		 	            		  imgstr="<img src='/mp/img/word-01.svg'/>";
			 		 	            	  }
			 		 	            	  
			 		 	              }else if(type.indexOf("ppt")>=0){
			 		 	            	  if(arr && arr[0]=='IE'){
			 		 	            		  imgstr="<img src='/mp/img/ppt-01.png'/>";
			 		 	            	  }else{
			 		 	            		  imgstr="<img src='/mp/img/ppt-01.svg'/>";
			 		 	            	  }
			 		 	              }else if(type.indexOf("pdf")>=0){
			 		 	            	  if(arr && arr[0]=='IE'){
			 		 	            		  imgstr="<img src='/mp/img/pdf-01.png'/>";
			 		 	            	  }else{
			 		 	            		  imgstr="<img src='/mp/img/pdf-01.svg'/>";
			 		 	            	  }
			 		 	              }else{
			 		 	            	  if(arr && arr[0]=='IE'){
			 		 	            		  imgstr="<img src='/mp/img/normal-01.png'/>";
			 		 	            	  }else{
			 		 	            		  imgstr="<img src='/mp/img/normal-01.svg'/>";
			 		 	            	  }
			 		 	              }
				 		       	          var downloadurl = "msg/download/"+curr_add_flowAttr[i].attraddress;
			               	               downloadurl+="?sourceSystemCode="+msg.get('sourcesystem');
			               	               downloadurl+="&pk_sourcemsg="+msg.get('pk_sourcemsg');;
			               	               downloadurl+="&msgtype="+msg.get('msgtype');
			               	               downloadurl+="&fileType=flowAtt";
			               	               downloadurl+="&fileName="+curr_add_flowAttr[i].attrname;
			               	               downloadurl = encodeURI(encodeURI(downloadurl));
			 		       	               attchstr+='<li   class="fancy-view" style="display: list-item"><span class="fleft">'+imgstr+'<a  title='+"\""+curr_add_flowAttr[i].attrname+"\""+' href='+downloadurl+'>'+curr_add_flowAttr[i].attrname+'<a/></span><span class="fright">'+curr_add_flowAttr[i].uploader+'</span></li>';
		 		                       num++;
		 		                    }
	 		                    }
	 		                    
	 		                    var u = '<ol>' + ('' !== attchstr ? attchstr : '<li   class="fancy-view" style="display: list-item">没有附件</li>') + '<ol/>';
	 		                  	$('.psonslfoolter .popover-content').html(u);
	 		                  	
	 		                  	if(num==0){
	 		                  		$('.psonslfoolter .attch').html("附件 :&nbsp;无 &nbsp;");
	 		                  	}else{
	 		                  		var html = '附件&nbsp;：&nbsp;<div class="greennum_circle" style="display:block;">'+num+'</div>';
	 		                  		$('.psonslfoolter .attch').html(html);
	 		                  		//$('.psonslfoolter .greennum_circle').html(num);
	 		                  		
	 		                  	}
	 		                  	//上传附件功能
	 		                  	$('.psonslfoolter').find('.upload').css('color','#428bca');
	 		                  	$('.psonslfoolter').find('.file').removeAttr("disabled");
	 		                  	$('.psonslfoolter').find('.file').attr("pk",msgid);
	 		                  	var sourcesystem = that.baseParam.sourceSystemCode;
	 		                  	$('.psonslfoolter').find('.file').attr("sourcesystem",sourcesystem);
	 		                	
	 		                	var show_div = "MyDiv";
	 		                	var bg_div="fade";
	 		                	document.getElementById(show_div).style.display='block';
	 		                	document.getElementById(bg_div).style.display='block' ;
	 		                	var bgdiv = document.getElementById(bg_div);
	 		                	bgdiv.style.width = document.body.scrollWidth;
	 		                	// bgdiv.style.height = $(document).height();
	 		                	$("#"+bg_div).height($(document).height());
//	 		                	$(".MyDiv_right").height($(".white_content").height());
//	 		                	$(".MyDiv_middle").height($(".white_content").height());
//	 		                	$(".MyDiv_left").height($(".white_content").height());	 		                	
	 		                	//by lihn 单据附件正文的位置垂直居中	 		               
//	 		                	if($(".preview_right").height()>$('.preview_bill_content > table').height()){
//	 		                		$('.preview_bill_content').css('padding-top',$(".preview_right").height()/2-$('.preview_bill_content > table').height()/2);
//	 		                		
//	 		                	}else{
//	 		                		$('.preview_bill_content').css('padding-top',0);
//	 		                		
//	 		                	}
	 		                	
//	 		                	
//	 	                		var billContentHeight=$('.preview_bill_content > table').height();
//	 	                   	    if($(".black_overlay").height()<billContentHeight){
//	 	                   	      $(".black_overlay").height(billContentHeight);
//	 	                 	      $(".MyDiv_left").height(billContentHeight);
//	 	                 	      $(".MyDiv_right").height(billContentHeight);
//	 	                   	    }
	 		                	//好像没有必要设置宽度，因为table是自动居中
	 		                	/*if($(".preview_bill_content").width()>$('.preview_bill_content > table').width()){
	 		                		
	 		                		$('.preview_bill_content').css('padding-left',$(".preview_bill_content").width()/2-$('.preview_bill_content > table').width()/2);
	 		                	}else{
	 		                		$('.preview_bill_content').css('padding-left',0);
	 		                	}*/
	 		                	
	 		                	$('.tabhost').show();
	 		                	$('#page1 .laseone').show();
	 		                	$('#page1 .lasttwo').html('<button type="button" class="btn btn-success btn-xs actionBtn" pk="" actioncode="agree">确定</button>');
	 		                	$('#page1 .lasttwo').show();
	 		                	
	 		                	if(that.$article.find('.sub-tabcontent a').length<=1){
	 		                		$('.approve-todo-action').hide();
	 		                		$('#workflowiera').children(':last-child').find('.approve-vertical-line').hide();
	 		                	}else{
	 		                		$('.approve-todo-action').show();
	 		                		$('#workflowiera').children(':last-child').find('.approve-vertical-line').show();
	 		                	}
	 		                	
	 		                	
	 		                	$('.out_circletodo .approve-content').hide();
	 		                	
	 		                	//动态设置右侧审批记录竖线高度和内容区域高度一样
		 		       	        $('.row.approve-pass').each(function(){
		 		       	        	var ishidden = $(this).is(':hidden');
			 		       	        
		 		       	        	$(this).show();
		 		       	        	
		 		       	        	var nextheight = $(this).find('.approve-right').height();
			 		   	        	$(this).find('.approve-vertical-line').css('height',nextheight+'px');
			 		   	        	if(ishidden==true){
			 		   	        		$(this).hide();	
			 		   	        	}
			 		   	        	
			 		   	        });
	 	                }
	 	            }
	 	        });
	        }        
         	//初始化审批页面->左侧预览附件->左侧导航by lihn
 		 	that.initPreviewLeft(that.baseParam); 
	       
	    });
        
        
        
//        article.off('click', '.open-msg').on('click', '.open-msg', function (e) {
//			var t = app.msgs.get($(this).closest('.post').attr('id')).attributes.showBill;
//			var toOpen = app.msgs.get($(this).closest('.post').attr('id')).attributes.toOpen;
//			that.danjuDiv = $(this).closest('.p-summary').next();
//			if(!t || !toOpen) return;
//	        var action = 'showBill';
//	        that.action=action;
//	        that.baseParam.actionCode=that.action;
//	        //本操作是否改变单据状态
//	        that.baseParam.isChangeState=true;
//	        that.title=$(e.target).html();
//	        var noteDefaultMap=that.noteDefaultMap;
//	        if(noteDefaultMap && typeof noteDefaultMap[that.action] !=='undefined'){
//	            that.baseParam.note=noteDefaultMap[that.action];
//	        }else{
//	            that.baseParam.note='';
//	        }
//
//            $.ajax({
//                url: "/mp/msg/showBill",
//                type: 'POST',
//                data: JSON.stringify(that.baseParam),
//                dataType: 'text',
//                contentType: 'application/json',
//                cache: false,
//                success: function (ret) {
//                
//                    //返回的数据是字符串，暂时这样区分
//                    if(ret.indexOf('status')!=-1&&ret.indexOf('msg')!=-1){
//                    	 ret= JSON.parse(ret);
//                         $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
//
//                    } else {
//                    	if(ret.indexOf('table')!=-1){
//                    		if(that.danjuDiv.find("table")[0]==undefined){
//                    			that.danjuDiv.append(ret);
//                    		}
//                        	that.danjuDiv.css("overflow-x","auto");
//                    	}
//                    	
//                    }
//                }
//            })
//	        
//	    });
        //取消审批操作
        article.off('click', '.btn-unApprove').on('click', '.btn-unApprove', function (e) {
        	that.unapprovefun(e);
        });
/*        article.off('click', '.btn-unApprove').on('click', '.btn-unApprove', function (e) {

    	var action = "unApprove";
    	var task_userids_map = {};
    	 var articleid = $(e.target).attr('pk');
        that.baseParam.actionCode = action;
        var event = e.currentTarget;
        
        //批语
        var inputNote = $('#'+articleid).find('.noneinput').val();
        
        var note = (''!==inputNote)?inputNote: that.baseParam.note;
        that.baseParam.isChangeState=true
        
        that.baseParam.task_userids_map=task_userids_map;
        that.baseParam.note = note;
        var cloneParam=$.extend({},that.baseParam);
        $.ajax({
            url: "/mp/msg/action",
            type: 'POST',
            data: JSON.stringify($.extend(cloneParam, {actionCode: action})),
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
                if (ret.isChangeState) {
                	//取消审批操作的单据id变换了，因此下一次要执行重新查询
                	app.params.refreshFlag=true;
                	that.view.alertmsg(trans("workstation_approval_cancel_sucess"));
                	
                	//改变左上角任务数量
                	$("#task_tab_item").html(parseInt($("#task_tab_item").html())+1);
                	that.changeBilltypeNum('add');//重置单据类型数量
                	window.unclear = true;
                	that.view.clear();
                	window.unclear = false;
                    
                } else if(!ret.status){
                	that.view.alerterrormsg((undefined!=ret.msg && ""!=ret.msg)?ret.msg:'取消审批操作失败',$.scojs_message.TYPE_ERROR);
                }else{
                	that.view.alertmsg("取消审批操作失败！"); 
                }
            },
        
        error:function(e){        
        var errorStr = e.responseText.replace('\n','');
        errorStr=errorStr.replace('\t','');
        errorStr=errorStr.replace('\r\n','');
        var errorMsg=JSON.parse(errorStr);
        $.scojs_message(errorMsg.msg,  $.scojs_message.TYPE_ERROR);
        }
        }).always(function () {//暂时去掉
//                that.$busyIndicator.stop();
//                delete that.$busyIndicator;
            });
        });*/
	    
       
     
        this.init();
    };

    UAP65.prototype = {

        constructor: UAP65,
        forChildrenBody:function(obj){
        	var that = obj;
	        var action = 'showBill';
	        that.action=action;
	        that.baseParam.actionCode=that.action;
	        //本操作是否改变单据状态
	        that.baseParam.isChangeState=true;
	        that.title="";
	        app.msgs.baseParam=that.baseParam;
	        var cloneParam=$.extend({},that.baseParam);
        	if(window.ifdealed=="waitdeal"){
	 	        that.initgpperson();
//	 	        初始化同意、不同意用户
	 	        that.assignusermore();
//	 		        初始化驳回环节
	 	        that.initrejectitem();
        	}
//	        初始化流程历史
	        var flage=that.initwfhistory();
	        //任务被收回
 	        if(flage=="0"){
 	        	return;
 	        }
//	        初始化按钮事件
	        
//	        that.initactionbtnevent();
	       
	        $.ajax({
 	            url: "/mp/msg/action",
 	            type: 'POST',
 	            data: JSON.stringify($.extend(cloneParam, {actionCode: 'showBill',isChangeState:false, action: action,ifdealed:ifdealed})),
 	            dataType: 'text',
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
 	            var ifdealed = window.ifdealed;
 	           var parentbody = $('body');
 	           //集成到portal里面parentbody的值获取的是portal中的body，因此需要判断
 	           if(parentbody.find('#allcontent').length==0){
 	        	  parentbody= $('body',window.parent.document);
 	           }	           
 	          var msgid=parentbody.find('#work_history').attr('pk');
 	          var msg=app.msgs.get(msgid);
               
//            var attchs=msg.get("attrurllist");
 	            var attchs=JSON.parse(msg.get('extendattr')).workflowInfo.flowAttrs;
               var attchstr='';
               
               //fix ie8 (临时移除类方法indexOf)
               var indedxOf=Array.prototype.indexOf;
               delete Array.prototype.indexOf;
               var num = 0;
               for(var curr in attchs){
                   attchstr+='<li   class="fancy-view" style="display: list-item"><span class="fleft"><img src="/mp/img/excel-01.svg"/><a  href='+attchs[curr].downloadurl+'>'+attchs[curr].attrname+'<a/></span><span class="fright">'+attchs[curr].uploader+'</span></li>';
                  num++;
               }
               Array.prototype.indexOf=indedxOf;

               var u = '<ol>' + ('' !== attchstr ? attchstr : '<li   class="fancy-view" style="display: list-item">没有附件</li>') + '<ol/>';
               parentbody.find('.psonslfoolter .popover-content').html(u);
             	if(num==0){
             		parentbody.find('.psonslfoolter .attch').html("附件 :&nbsp;无 &nbsp;");
             	}else{
             		parentbody.find('.psonslfoolter.dealed .greennum_circle').html(num);
             		parentbody.find('.psonslfoolter .greennum_circle').html(num);
             	}
             	
             	//上传文件操作不可用
             	parentbody.find('.psonslfoolter').find('.upload').css('color','#9d9d9d');
             	parentbody.find('.psonslfoolter').find('.file').attr({"disabled":"disabled"});
             	
             	//从初始化加签人员
 	 	        var cloneParam=$.extend({},that.baseParam);
	             	$.ajax({
		 	            url: "/mp/msg/action",
		 	            type: 'POST',
		 	            data: JSON.stringify($.extend(cloneParam, {actionCode: 'showBill',isChangeState:false, action: action})),
		 	            dataType: 'text',
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
		 	                //返回的数据是字符串，暂时这样区分
		 	                if(ret.indexOf('status')!=-1&&ret.indexOf('msg')!=-1){
		 	                	 ret= JSON.parse(ret);
		 	                	
		 	                     $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
		 	
		 	                } else {
		 	                	$(".black_overlay").height("100%");
		 	               	    $(".MyDiv_left").height("100%");
		 	               	    $(".MyDiv_right").height("100%");
		 	                	//加载单据正文
	 	    		 	    	//parentbody.find('.preview_bill_content').html(ret);
		 	                	 that.showJqPersonTab('child');
		 	                		var pageSize = 3;
		 	                		parentbody.find('#userRefTablenew').bootstrapTable('destroy').bootstrapTable({
		 		                        url: '/mp/msg/action',
		 		                        cache: false,
		 		                        contentType: 'application/json',
		 		                        jsonToSend: true,
		 		                        method: 'POST',
		 		                        striped: true,
		 		                        pagination: true,
		 		                        pageSize: pageSize,
		 		                        minimumCountColumns: 2,
		 		                        queryParams: function (res) {
		 		                            res.search = res.search || '';
		 		                            res = $.extend(res, that.baseParam);
		 		                            return $.extend(res, {actionCode: 'getUserList', ref_type: 'user',isChangeState:false});
		 		                        },
		 		                        responseHandler: function (res) {
		 		                            var ret = {};
		 		                            if (res) {
		 		                                ret.total = res.ref_count;
		 		                                ret.rows = res.ref_result;
		 		                            }
		 		                            return ret;
		 		                        },
		 		                        clickToSelect: true
		 		                    }).on('check.bs.table',function(e,row){
		 			                   
		 		                    	
		 			                    if(parentbody.find("#joinperson span[data-code='"+row.code+"']")==undefined || parentbody.find("#joinperson span[data-code='"+row.code+"']").length == 0){
		 			                    	var val = "<span class='ss-item' onclick='removeelt(this)' data-name='"+row.name+"' data-value='2' data-code='"+row.code+"'><b>"+row.name+"</b><i></i></span>";		
		 			                    	parentbody.find('#joinperson').append(val);
		 			                    }
		 		                    	
		 		                    }).on('uncheck.bs.table',function(e,row){
		 		                    	parentbody.find('#joinperson span').each(function(){
		 			                    	if($(this).attr('data-code')==row.code){
		 			                    		$(this).remove();
		 			                    		return;
		 			                    	}
		 			                    });
		 		                    }).on('check-all.bs.table',function(e){
		 		                    	var tdata = parentbody.find('#userRefTablenew').bootstrapTable('getSelections');
		 		                    	for(var i=0;i<tdata.length;i++){
		 		                    		var row = tdata[i];
		 		                    		if(parentbody.find("#joinperson span[data-code='"+row.code+"']")==undefined || parentbody.find("#joinperson span[data-code='"+row.code+"']").length == 0){
		 					                    	var val = "<span class='ss-item' onclick='removeelt(this)' data-name='"+row.name+"' data-value='2' data-code='"+row.code+"'><b>"+row.name+"</b><i></i></span>";		
		 					                    	parentbody.find('#joinperson').append(val);
		 					                   }
		 		                    	}
		 		                   
		 		                    }).on('uncheck-all.bs.table',function(e,name,args){
	//	 		                    	$('#joinperson span').remove();
		 		                    	var tdata = parentbody.find('#userRefTablenew').bootstrapTable('getData');
		 		                    	for(var i=0;i<tdata.length;i++){
		 		                    		var row = tdata[i];
		 		                    		parentbody.find("#joinperson span[data-code='"+row.code+"']").remove();
	
		 		                    	}
		 		                    });		 	                		
		 	                		 if(ifdealed=="waitdeal"){
		 	    	 	            	parentbody.find('.fancybox-overlay').hide();
//		 	    	 	            	parentbody.find('.fancybox-overlay').remove();	 	    	 	            			 	    		 	    		
//		 	    		 	    		return;
		 	    		 	        	parentbody.find('#MyDiv').show();
		 	    		 	        	parentbody.find('#fade').show();
		 	    		 	        	var bgdiv = parentbody.find('#fade');
		 	    		 	        	bgdiv[0].style.width = $('body',window.parent.document)[0].scrollWidth;
		 	    		 	        	parentbody.find("#fade").height(parentbody.find('.fancybox-overlay').height());
//		 	    		 	        	parentbody.find(".MyDiv_right").height(parentbody.find(".white_content").height());
//		 	    		 	        	parentbody.find(".MyDiv_middle").height(parentbody.find(".white_content").height());
//		 	    		 	        	parentbody.find(".MyDiv_left").height(parentbody.find(".white_content").height());		 	    		 	       
		 	    		 	        	parentbody.find('.tabhost').show();
		 	    		 	        	parentbody.find('#page1').find('.laseone').html('<button type="button" class="btn btn-fail btn-xs actionBtn" pk="" actioncode="">取消</button>'); 
		 	    		 	        	parentbody.find('#page1').find('.lasttwo').html('<button type="button" class="btn btn-success btn-xs actionBtn okbtn" pk="" actioncode="agree">确定</button>');
		 	    		 	        	if(parentbody.find('#page1 .peoplecontent:hidden').length>0){
		 	    		 	        		parentbody.find('#page1').find('.psonslfoolter').attr('class','psonslfoolter dealed');
		 	    		 	        	}
		 	    		 	        	parentbody.css('overflow-y','auto');
		 	    		 	        	
		 	    		 	        	parentbody.find(".MyDiv_close").css('margin-right','20px');
		 	    		 	        //动态设置右侧审批记录竖线高度和内容区域高度一样
		 	    		 	        	parentbody.find('.row.approve-pass').each(function(){
				 		       	        	var ishidden = $(this).is(':hidden');
					 		       	        
				 		       	        	$(this).show();
				 		       	        	
				 		       	        	var nextheight = $(this).find('.approve-right').height();
					 		   	        	$(this).find('.approve-vertical-line').css('height',nextheight+'px');
					 		   	        	if(ishidden==true){
					 		   	        		$(this).hide();	
					 		   	        	}
					 		   	        	
					 		   	        });
		 	    	 	            }else{
		 	    		 	    		parentbody.find('.fancybox-overlay').hide();
//		 	    		 	    		parentbody.find('.fancybox-overlay').remove();		 	    		 
//		 	    		 	    		return;
		 	    		 	    		parentbody.find('.tabhost').hide();
		 	    		 	    		parentbody.find('.peoplecontent').hide();
		 	    		 	    		parentbody.find('#page1').find('.laseone').html('<button type="button" class="btn btn-fail btn-xs actionBtn" pk="" actioncode="">取消</button>');
		 	    		 	    		parentbody.find('#page1').find('.lasttwo').html('<button type="button" class="btn btn-success btn-xs actionBtn" pk="" actioncode="unApprovenew">取消审批</button>'); 
		 	    		 	    		
		 	    		 	        	parentbody.find('#MyDiv').show();
		 	    		 	        	parentbody.find('#fade').show();
		 	    		 	        	var bgdiv = parentbody.find('#fade');
		 	    		 	        	//bgdiv[0].style.width = $('body',window.parent.document)[0].scrollWidth;
		 	    		 	        	bgdiv[0].style.width = parentbody[0].scrollWidth;
//		 	    		 	        	parentbody.find("#fade").height(parentbody.find('.fancybox-overlay').height());
//		 	    		 	        	parentbody.find(".MyDiv_right").height(parentbody.find(".white_content").height());
//		 	    		 	        	parentbody.find(".MyDiv_middle").height(parentbody.find(".white_content").height());
//		 	    		 	        	parentbody.find(".MyDiv_left").height(parentbody.find(".white_content").height());
		 	    		 	        	
		 	    		 	        	if(parentbody.find('#page1 .peoplecontent:hidden').length>0){
		 	    		 	        		parentbody.find('#page1').find('.psonslfoolter').attr('class','psonslfoolter dealed');
		 	    		 	        	}
		 	    		 	        	parentbody.find('.approve-todo-action').show();
		 	    		 	        	parentbody.css('overflow-y','auto');
		 	    		 	        	parentbody.find(".MyDiv_close").css('margin-right','2.5%');
//		 	    		 	        	parentbody.find(".psonslfoolter.dealed").css('height','110px');
		 	    		 	        	parentbody.find('#lastright').hide();
		 	    		 	        	parentbody.find('.out_circletodo .approve-bage').html(parseInt(parentbody.find("#workflowiera").children().length)+1);
		 	    		 	        	parentbody.find('.out_circletodo .approve-bage').attr('class','approve-bage-dealed');
		 	    		 	        	parentbody.find('.out_circletodo').attr('class','out_circletodo-dealed');
		 	    		 	        	
		 	    		 	        	parentbody.find('#workflowiera').children(':last').find('.approve-vertical-line').css('border-right','1px solid gray');
//		 	    		 	        	$('#workflowiera').children(':last').find('.approve-right .approve-person').append('<p><a href="#" id="dealedfj">附件上传</a><button type="button" id="dealedqs" class="btn btn-success btn-xs actionBtn" pk="" actioncode="unApprovenew">取消审批</button></p>');
		 	    		 	        	$('.out_circletodo .approve-content').show();
		 	    		 	        	$('.out_circletodo-dealed .approve-content').show();
		 	    		 	        	
		 	    		 	        	//没有下一步活动时不显示活动环节号
		 	    		 	        	if($.trim($('.out_circletodo-dealed').find('.approve-content').html())==""){
		 	    				        	$('.approve-todo-action').parent().find('#workflowiera').children(':last').find('.approve-vertical-line').hide();
		 	    				        	$('.approve-todo-action').hide();
		 	    		 	        	}else{
		 	    				        	$('.approve-todo-action').parent().find('#workflowiera').children(':last').find('.approve-vertical-line').show();
		 	    				        	$('.approve-todo-action').show();
		 	    		 	        	}
		 	    		 	        //动态设置右侧审批记录竖线高度和内容区域高度一样
		 	    		 	        	parentbody.find('.row.approve-pass').each(function(){
				 		       	        	var ishidden = $(this).is(':hidden');
					 		       	        
				 		       	        	$(this).show();
				 		       	        	
				 		       	        	var nextheight = $(this).find('.approve-right').height();
					 		   	        	$(this).find('.approve-vertical-line').css('height',nextheight+'px');
					 		   	        	if(ishidden==true){
					 		   	        		$(this).hide();	
					 		   	        	}
					 		   	        	
					 		   	        });
		 	    	 	            }		 	     
		 	    		 	    //by lihn 单据附件正文的位置垂直居中
//		 	    		 	     	if($(".preview_right").height()>$('.preview_bill_content > table').height()){
//		 	    		 	     		$('.preview_bill_content').css('padding-top',$(".preview_right").height()/2-$('.preview_bill_content > table').height()/2);
//		 	    		 	     		
//		 	    		 	     	}else{
//		 	    		 	     		$('.preview_bill_content').css('padding-top',0);
//		 	    		 	     	
//		 	    		 	     	}
//		 	    		 	     	
//		 	    		 	     	
//		 	                		var billContentHeight=$('.preview_bill_content > table').height();
//		 	                   	   if($(".black_overlay").height()<billContentHeight){
//		 	                   	      $(".black_overlay").height(billContentHeight);
//		 	                 	      $(".MyDiv_left").height(billContentHeight);
//		 	                 	      $(".MyDiv_right").height(billContentHeight);
//		 	                   	   }
		 	    		 	    //好像没有必要设置宽度，因为table是自动居中
		 	    		 	     	/*if($(".preview_bill_content").width()>$('.preview_bill_content > table').width()){
		 	    		 	     		
		 	    		 	     		$('.preview_bill_content').css('padding-left',$(".preview_bill_content").width()/2-$('.preview_bill_content > table').width()/2);
		 	    		 	     	}else{
		 	    		 	     		$('.preview_bill_content').css('padding-left',0);
		 	    		 	     	}*/
		 	    		 	    	
		 	    	 	        	that.initactionbtneventforchild();		 	     
		 	                }
		 	            }
		 	        });
	             	
 	            }
	        });
        },
        showFlow: function () {
            var action = 'getHistory';
            var that = this;
            that.baseParam.isChangeState=false;
            var baseParam=that.baseParam;
            var cloneParam=$.extend({},that.baseParam);
            var param = function (params) {
                return $.extend(cloneParam, {actionCode: action});
            }


            bootbox.dialog({
                title:'流程图及审批历史',
                message: '<div class="showFlowInfo">\
                            <div class="panel-body">\
                                 <a href="#" class="flowImg"></a>\
                            </div>\
                            <table class="table"  data-height="150"></table>\
                            </div>',
                onEscape:function(){return true},
                backdrop:false,
                buttons: {
                    cancel:{
                        label:"关闭",
                        className: "btn-default  btn-sm"
                    }
                }
            });

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
                        field: 'sendman',
                        'class': 'hidden-xs',
                        title: '申请人'
                    },
                    {
                        field: 'senddate',
                        'class': 'hidden-xs',
                        align: 'center',
                        title: '申请日期'
                    },
                    {
                        field: 'dealman',
                        title: '处理人'
                    },
                    {
                        field: 'dealdate',
                        align: 'center',
                        title: '处理日期'
                    },
                    {
                        field: 'approvestatus',
                        title: '审批状态'
                    },
                    {
                        field: 'approveresult',
                        title: '审批结果'
                    },
                    {
                        field: 'checknote',
                        title: '批语',
                        align: 'center',
                        valign: 'middle',
                        clickToSelect: true
                    }
                ]
            });

            var img = new Image();
            $('.flowImg').append('<div class="loader" ></div>');
            $(img).load(function (ret) {
                $('.flowImg').empty().append($(this));
                $('.flowImg').attr('href', '/mp/msg/attachment?sourceSystemCode=' + baseParam.sourceSystemCode + '&pk_sourcemsg=' + baseParam.pk_sourcemsg+'&_timestamp='+new Date().getTime());

                $('.flowImg').data("jqzoom",null);
                $('.flowImg').jqzoom({
                    zoomType: 'innerzoom',
                    title: false,
                    lens: false,
                    showEffect: 'fadein',
                    hideEffect: 'fadeout'
                });
            }).attr({
                     src: '/mp/msg/attachment?type=min&sourceSystemCode=' + baseParam.sourceSystemCode + '&pk_sourcemsg=' + baseParam.pk_sourcemsg+'&_timestamp='+new Date().getTime()
                }).error(function (e) {
                    $('.flowImg').empty();
                });
            //IE8 暂时不处理
            if(!$.isIE()){
                $(img).attr( 'class','img-responsive');
            }

        },
        initgpperson:function(){
        var that = this;
        	var pageSize = 3;
        	var parentbody = "";
        	if($('#isdealed').val()=='dealed'){
        		parentbody = $('body',window.parent.document); 	           
        	}else{
        		parentbody = $('body');
        	}
        	
        	parentbody.find('#userRefTablegp').bootstrapTable('destroy').bootstrapTable({
                url: '/mp/msg/action',
                cache: false,
                contentType: 'application/json',
                jsonToSend: true,
                method: 'POST',
                striped: true,
                pagination: true,
                pageSize: pageSize,
                minimumCountColumns: 2,
                queryParams: function (res) {
                    res.search = res.search || '';
                    res = $.extend(res, that.baseParam);
                    return $.extend(res, {actionCode: 'getUserList', ref_type: 'user',isChangeState:false});
                },
                responseHandler: function (res) {
                    var ret = {};
                    if (res) {
                        ret.total = res.ref_count;
                        ret.rows = res.ref_result;
                    }
                    return ret;
                },
                clickToSelect: true
            }).on('check.bs.table',function(e,row){
            	that.baseParam.reassign = row.code;
            });
        },
        initwfhistory:function(){
        //标志任务消息是否存在，默认是存在1，不存在0
        var flage="1";
        var that = this;
        	var action = 'getHistorySimple';
    	    app.msgs.baseParam.isChangeState=false;
    	    var cloneParam=$.extend({},app.msgs.baseParam);
    	    $.ajax({
                url: "/mp/msg/action",
                type: 'POST',
                data: JSON.stringify($.extend(cloneParam, {actionCode: action})),
                dataType: 'json',
                contentType: 'application/json',
                async:false,
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
                	if (typeof ret.status !== "undefined") {
                     $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
//                      that.view.alerterrormsg(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
                      //消息不存在
                      flage="0";
                      return;
                	}
                var his_task = ret.history_task;
                var flow_pks = {};
            	for(var j=0;j<his_task.length; j++){
            		flow_pks[his_task[j].noteVO.pk_workflownote] = true;
            		var flowvo = his_task[j].flowAttVOs;
            		if(flowvo==undefined) continue;
            		for(var i=0; i<flowvo.length;i++){
            			var type = flowvo[i].attrname;
            			var imgstr = "";
            			var styleflag="";
						  if(type.indexOf("xls")>=0){
		       	            	  imgstr="../mp/img/excel-01.svg";
		       	              }else if(type.indexOf("doc")>=0){
		       	            	  imgstr="../mp/img/word-01.svg";
		       	              }else if(type.indexOf("ppt")>=0){
		       	            	  imgstr="../mp/img/ppt-01.svg";
		       	              }else if(type.indexOf("pdf")>=0){
            	            	  imgstr="../mp/img/pdf-01.svg";
            	              }else{
		       	            	  imgstr="../mp/img/normal-01.svg";
		       	            	  styleflag='1';
		       	              }
						  flowvo[i].pk_sourcemsg = app.msgs.baseParam.pk_sourcemsg;
						  flowvo[i].msgtype = app.msgs.baseParam.msgtype;
						  flowvo[i].sourceSystemCode = app.msgs.baseParam.sourceSystemCode;
						  flowvo[i].imgstr=imgstr;
						  flowvo[i].styleflag=styleflag;
						  flowvo[i].fileName=encodeURI(encodeURI(flowvo[i].attrname));
            		}
            	}
            		var msgid=$('#work_history').attr('pk');
            		var msg=app.msgs.get(msgid);
            		msg.curr_msg_flow_pks = flow_pks;
            		
             		if(ret.flowAtt){
             			msg.curr_flowAttr = ret.flowAtt;
             		}
             		
                	that.view.renderWorkFlowspjl(ret);
                	
                	if($('#isdealed').val() == 'dealed'){
                		var parentbody = $('body',window.parent.document);
                		 parentbody.find('#work_history').attr('pk',$('#work_history').attr('pk'));
                	}
                	
                }
    	    
            });
    	    return flage;
        },
        reject: function () {
            var that = this;
            var action=that.action;
            //查询驳回环节
            var validateCallBack=function(){
                var val =  $('.refUserMulti').val();
                if(!val){
                    $.scojs_message("没有选择驳回环节或者不支持驳回！",$.scojs_message.TYPE_WARN);
                    return false;
                }
                that.baseParam.pk_active = val;
                return true;
            };

//            this.showModalDialog(validateCallBack);
            $("#point .ext-content").empty().append('<div class="loader" ></div>');
            var cloneParam=$.extend({},that.baseParam);
            $.ajax({
                url: "/mp/msg/action",
                type: 'POST',
                data: JSON.stringify($.extend(cloneParam, {actionCode: 'getRejectActitity',isChangeState:false, action: action})),
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
                    if (typeof ret.status !== "undefined") {
                        $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
                    } else {
                    	//成功之后显示驳回对话框
                    	that.showModalDialog(validateCallBack);
                        that.view.nodeSelector(ret);
                    }
                }
            });
        },
        initrejectitem:function(){
        	var that = this;
            var action="reject";
            //查询驳回环节
            var cloneParam=$.extend({},that.baseParam);
            $.ajax({
                url: "/mp/msg/action",
                type: 'POST',
                data: JSON.stringify($.extend(cloneParam, {actionCode: 'getRejectActitity',isChangeState:false, action: action})),
                dataType: 'json',
                contentType: 'application/json',
                cache: false,
                async:false,
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
                

                var parentbody = "";
            	
            	if($('#isdealed').val()=='dealed'){
            		parentbody = $('body',window.parent.document);
            	}else{
            		parentbody = $('body');
            	}  
                	if($('#isdealed').val() == 'dealed'){
                		if (typeof ret.status !== "undefined") {
                			parentbody.find('#page3 .psonslttitle').hide();
                			parentbody.find('#page3 .bootstrap-tablediv').hide();
                        } else {
                        	that.view.workflowitem(ret);
                        	parentbody.find('.bootstrap-tablediv .moremiddlecontent input').unbind('click').bind('click',function(){
                    		parentbody.find('.bootstrap-tablediv .moremiddlecontent input').attr('checked',false);
                    		parentbody.find('.bootstrap-tablediv .moremiddlecontent input').parent().parent().find('.sub-tab-hr').hide();
                    		parentbody.find('.bootstrap-tablediv .moremiddlecontent input').parent().parent().find('.checkedname').hide();
                    		$(this).attr('checked',true);
                    		$(this).parent().parent().find('.checkedname').show();
                    		//IE8兼容  单独改变样式的颜色                  			                   		
                			/*var ua = navigator.userAgent;
                			ua = ua.toLocaleLowerCase();
                			if (ua.match(/msie/) != null || ua.match(/trident/) != null) {
                				var browserVersion = ua.match(/msie ([\d.]+)/) != null ? ua
                						.match(/msie ([\d.]+)/)[1] : ua.match(/rv:([\d.]+)/)[1];
                				if(browserVersion < 9.0){
                					//遍历所有的checkbox  使其的checkbox都变为uncheck
                					$('.checkboxFive > label').css("background","#fff");
                					//只有本按钮的背景色改变
                					$(this).parent().find('label').css("background","#0080cd");		
                				}
                			}*/
                        		
                        	});
                        }
                	}else{
                		if (typeof ret.status !== "undefined") {
                        	$('#page3 .psonslttitle').hide();
                        	$('#page3 .bootstrap-tablediv').hide();
                        } else {
                        	that.view.workflowitem(ret);
                        	$('.bootstrap-tablediv .moremiddlecontent input').unbind('click').bind('click',function(){                       	                                  		
                        		$('.bootstrap-tablediv .moremiddlecontent input').attr('checked',false);
                        		$('.bootstrap-tablediv .moremiddlecontent input').parent().parent().find('.sub-tab-hr').hide();
                        		$('.bootstrap-tablediv .moremiddlecontent input').parent().parent().find('.checkedname').hide();
                        		$(this).attr('checked',true);
                        		$(this).parent().parent().find('.checkedname').show();
                        		//IE8兼容  单独改变样式的颜色                  			                   		
                    			/*var ua = navigator.userAgent;
                    			ua = ua.toLocaleLowerCase();
                    			if (ua.match(/msie/) != null || ua.match(/trident/) != null) {
                    				var browserVersion = ua.match(/msie ([\d.]+)/) != null ? ua
                    						.match(/msie ([\d.]+)/)[1] : ua.match(/rv:([\d.]+)/)[1];
                    				if(browserVersion < 9.0){
                    					//遍历所有的checkbox  使其的checkbox都变为uncheck
                    					$('.checkboxFive > label').css("background","#fff");
                    					//只有本按钮的背景色改变
                    					$(this).parent().find('label').css("background","#0080cd");		
                    				}
                    			}*/
                        	});
                        	
                        }
                	}
                    
                }
            });
        },
        rejectnew:function(){
        	 var that = this;
             var action=that.action;
             //查询驳回环节
             var validateCallBack=function(){
                 var val =  $('.refUserMulti').val();
                 if(!val){
                     $.scojs_message("没有选择驳回环节或者不支持驳回！",$.scojs_message.TYPE_WARN);
                     return false;
                 }
                 that.baseParam.pk_active = val;
                 return true;
             };

             this.showModalDialog(validateCallBack);
             $("#point .ext-content").empty().append('<div class="loader" ></div>');
             var cloneParam=$.extend({},that.baseParam);
             $.ajax({
                 url: "/mp/msg/action",
                 type: 'POST',
                 data: JSON.stringify($.extend(cloneParam, {actionCode: 'getRejectActitity',isChangeState:false, action: action})),
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
                     if (typeof ret.status !== "undefined") {
                         $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
                     } else {
                         that.view.nodeSelector(ret);
                     }
                 }
             });
        },
        addApprove: function () {
            var that = this;
            var action=that.action;
            var validateCallBack=function(){
                if(!that.baseParam.reassign){
                    $.scojs_message("没有选择加签人员！",$.scojs_message.TYPE_WARN);
                    return false;
                }
                //加签的人员
                that.baseParam.addApproveUsers=that.baseParam.reassign;
                return true;
            };
            
            
            that.shownName="加签处理人员";
            var query=true;
            this.params ={
                	param:{
                		limit:5,
                		offset:0,
                		search:undefined,
                		sort:undefined,
                		order:'asc'
                	},
                	data:{},
                    queryParams: function (res) {
                        res.search = res.search || '';
                        res = $.extend(res, that.baseParam);
                        return $.extend(res, {actionCode: 'getUserList', ref_type: 'user',isChangeState:false});
                    },
                    responseHandler: function (res) {
                        var ret = {};
                        if (res) {
                            ret.total = res.ref_count;
                            ret.rows = res.ref_result;
                        }
                        return ret;
                    },
                    clickToSelect: true
                };
                var sdata = that.calculateFunctionValue(this.params.queryParams, [this.params.param], this.params.data);
                sdata=JSON.stringify(sdata);
                
                $.ajax({
        	      url: "/mp/msg/action",
        	      type: 'POST',
        	      data: sdata,
        	      dataType: 'json',
        	      contentType: 'application/json',
        	      cache: false,
        	      async:false,
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
        	        	  $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_WARN);
        	        	  query= false;
        	          } else {
        	          	
        	          }
        	      }
        	  });
                
                if(query==false) return;
            
            
            that.showModalDialog(validateCallBack);

            $('.ext-userRefModal').removeClass('hide');
            $('.refUserModal').bind('click',function(){
                that.showUserRefDialogSign();
                $('#userRefTable').bootstrapTable({
                    url: '/mp/msg/action',
                    cache: false,
                    contentType: 'application/json',
                    jsonToSend: true,
                    method: 'POST',
                    striped: true,
                    pagination: true,
                    pageSize: 5,
                    minimumCountColumns: 2,
                    queryParams: function (res) {
                        res.search = res.search || '';
                        res = $.extend(res, that.baseParam);
                        return $.extend(res, {actionCode: 'getUserList', ref_type: 'user',isChangeState:false, action: action});
                    },
                    responseHandler: function (res) {
                        var ret = {};
                        if (res) {
                            ret.total = res.ref_count;
                            ret.rows = res.ref_result;
                        }
                        return ret;
                    },
                    clickToSelect: true
                });
            });
        },
        agree: function () {
            var that=this;
            var action=that.action;
            //查询指定人getAssignUser
            //task_userids_map
            var validateCallBack=function(){
            	var back = true;
                //PARAM_ASSIGNINFO=task_userids_map
                var task_userids_map = {};
                $('.refUserMulti').each(function (index) {
                    var id = $(this).attr('id');
                    var nodeName = $(this).data('name');
                    var val = $(this).val();
                    if(!val){
                        that.view.alerterrormsg(nodeName+" 没有设置环节人员！");
                        back = false;
                    }
                    task_userids_map[id] = val;
                });
                that.baseParam.task_userids_map = task_userids_map;
                return back;
            };

            var cloneParam=$.extend({},that.baseParam);
            $.ajax({
                url: "/mp/msg/action",
                type: 'POST',
                data: JSON.stringify($.extend(cloneParam, {actionCode: 'getAssignUser',isChangeState:false, action: action})),
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
                    if (typeof ret.status !== "undefined") {
//                        $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
                        $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
                    } else {
                    	
                    	
                    	var ifempty = true;
                    	for(var p in ret){
                    		ifempty = false;
                    		break;
                    	}
                    	
                    	if(ret==null || ret==undefined || ret.length <=0){
                    		ifempty=true;
                    	}
                    	
                    	if(!ifempty){
	                    	that.showModalDialognew(validateCallBack);
	                        that.view.assingerSelectornew(ret);
                        }else{
                        	that.trigger("todeal");
                        }
                    	
                    }
                }
            });

        },
        showJqPersonTab:function(parent){
        	var parentbody = "";
        	
        	if($('#isdealed').val()=='dealed'){
        		parentbody = $('body',window.parent.document);
        	}else{
        		parentbody = $('body');
        	}
        	var tab = '<table id="userRefTablenew"'+
        				'data-show-refresh="false" data-search="true"'+
	                    'data-side-pagination="server"'+
	                    'data-pagination="true"'+
	                   	'data-page-list="[3, 6, 12, 24, 48]">'+
	                        '<thead>'+
	                            '<tr>'+
	                                '<th data-field="state" data-checkbox="true"></th>'+
	                                '<th data-field="code" data-sortable="true">编码</th>'+
	                                '<th data-field="name" data-align="center" data-sortable="true">名称</th>'+
	                                '<th data-field="dept" data-align="center" data-sortable="true">部门</th>'+
	                            '</tr>'+
	                        '</thead>'+
                    '</table>';
        	if(parent=='parent'){
        		$('#jqtable').empty().append($(tab));
        	}else{
        		parentbody.find('#jqtable').empty().append($(tab));
        	}
        },
        assignusermore:function(){
        	var that=this;
            var action="agree";
            var cloneParam=$.extend({},that.baseParam);
            $.ajax({
                url: "/mp/msg/action",
                type: 'POST',
                data: JSON.stringify($.extend(cloneParam, {actionCode: 'getAssignUser',isChangeState:false, action: action})),
                dataType: 'json',
                contentType: 'application/json',
                cache: false,
                async:false,
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
                
                
	            	var parentbody = "";
	
	            	if($('#isdealed').val()=='dealed'){
	            		parentbody = $('body',window.parent.document);
	            	}else{
	            		parentbody = $('body');
	            	}   
                
                
                    if (typeof ret.status !== "undefined" || ret.length == 0) {
                    	$('.peoplecontent').hide();
//                    	$('#page1').find('.psonslfoolter').css('height','110px');
                    	
                    	parentbody.find('.peoplecontent').hide();
//                    	parentbody.find('#page1').find('.psonslfoolter').css('height','110px');
                    } else {
                    	var ifempty = true;
                    	for(var p in ret){
                    		ifempty = false;
                    		break;
                    	}
                    	if(!ifempty){
                    		that.view.moreassignuserSelector(ret);
                    		$('.peoplecontent').show();
                    		$('#page1').find('.psonslfoolter').css('height','50px');
                    		
                    		parentbody.find('.peoplecontent').show();
                    		parentbody.find('#page1').find('.psonslfoolter').css('height','50px');
                    	}else{
                    		$('.peoplecontent').hide();
//                    		$('#page1').find('.psonslfoolter').css('height','110px');
                    		
                    		parentbody.find('.peoplecontent').hide();
//                    		parentbody.find('#page1').find('.psonslfoolter').css('height','110px');
                    	}
                    }
                }
            });
        },
        
        disAgree: function () {
            var that=this;
            var action=that.action;

            //查询指定人getAssignUser
            //task_userids_map
            var validateCallBack=function(){
            	var back = true;
                //PARAM_ASSIGNINFO=task_userids_map
                var task_userids_map = {};
                $('.refUserMulti').each(function (index) {
                    var id = $(this).attr('id');
                    var nodeName = $(this).data('name');
                    var val = $(this).val();
                    if(!val){
                        $.scojs_message(nodeName+" 没有设置环节人员！",$.scojs_message.TYPE_WARN);
                         back = false;
                    }
                    task_userids_map[id] = val;
                });
                that.baseParam.task_userids_map = task_userids_map;
                return back;
            };


           
            var cloneParam=$.extend({},that.baseParam);
            $.ajax({
                url: "/mp/msg/action",
                type: 'POST',
                data: JSON.stringify($.extend( cloneParam, {actionCode: 'getAssignUser',isChangeState:false, action: action})),
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
                    if (typeof ret.status !== "undefined") {
                    	if(!ret.status){
                    	//规避传过来的消息为null的情况。
                    	if(ret.msg==""||ret.msg==null){
                    		ret.msg="没有待办任务";
                    	}}
                        $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
                    } else {
                    	
                    	var ifempty = true;
                    	for(var p in ret){
                    		ifempty = false;
                    		break;
                    	}
                    	if(ret==null || ret==undefined || ret.length <=0){
                    		ifempty=true;
                    	}
                    	if(!ifempty){
	                    	that.showModalDialognew(validateCallBack);
	                        that.view.assingerSelectornew(ret);
                        }else{
                        	that.trigger("todeal");
                        }
                    }
                }
            });
        },
        initactionbtnevent:function(){
        	var that = this;
        	
        	$('.psonslfoolter .laseone button').unbind('click').bind('click',function(){
	            $('.page textarea').each(function(e){
                this.value="";
			    });
        		CloseDiv('MyDiv','fade');
        	});
        	
        	
        	$('.lasttwo').unbind('click').bind('click',function(){
        		var actionCode=$(this).find('button').attr('actioncode');
        		if(actionCode=='agree'){
        			var ifempty = $('.people-tablediv').find('input').length > 0?false:true;
        			var task_userids_map = {};
                     var ifselect = false;                    
                    if(!ifempty){
                    	
                        $('.people-tablediv>.moremiddlecontent').each(function(){
                        	//必须的指派人员
                        	if($(this).attr('mustassign')=="true"){
                        		//没有被选中的
                        		if($(this).find('input:checked').length==0){
                        			//直接提示
                        			that.view.alerterrormsg($(this).attr('keyname')+" 没有设置环节人员！");
                                	return;                      			
                        		}
                        		ifselect=true;
                        	}else{
                        	  //不是必须要选择 
                        		//没有选择人员
                        		if($(this).find('input:checked').length>0){                       			
                        			ifselect=true;	
                        		}
                        	  
                        	}
                        	//遍历选择的人员               
                        	var parray = new Array();
                        	$(this).find('input:checked').each(function(e){
                        		parray[e] = $(this).val();
                        	});
                        	if(parray.length>0){
                        	task_userids_map[$(this).attr('forkey')] = parray;}
                        });       			
                     if(!ifselect){
                    	 
                    	//$.scojs_message(alertmsg+" 没有设置环节人员！",$.scojs_message.TYPE_WARN);
                    	that.view.alerterrormsg("没有设置环节人员！");
                    	return;
                     }
                    }
                	that.baseParam.task_userids_map = task_userids_map;
        			that.baseParam.tmpnote = $('#agreemsg').val();
        			that.baseParam.actionCode = 'agree';
        			that.trigger("todeal");
        			
        		}else if(actionCode=='disAgree'){
        			
        			var ifempty = $('.people-tablediv2').find('input').length > 0?false:true;
        			var task_userids_map = {};
                     var ifselect = false;                    
                    if(!ifempty){ 
                        $('.people-tablediv2>.moremiddlecontent').each(function(){
                        	//必须的指派人员
                        	if($(this).attr('mustassign')=="true"){
                        		//没有被选中的
                        		if($(this).find('input:checked').length==0){
                        			//直接提示
                        			that.view.alerterrormsg($(this).attr('keyname')+" 没有设置环节人员！");
                                	return;                      			
                        		}
                        		ifselect=true;
                        	}else{
                        	  //不是必须要选择 
                        		//没有选择人员
                        		if($(this).find('input:checked').length>0){                       			
                        			ifselect=true;	
                        		}
                        	  
                        	}
                        	//遍历选择的人员  
                        	var parray = new Array();
                        	$(this).find('input:checked').each(function(e){
                        		parray[e] = $(this).val();
                        	});
                        	if(parray.length>0){
                        	task_userids_map[$(this).attr('forkey')] = parray;}
                        });
        			
                     if(!ifselect){
//    	                	$.scojs_message(alertmsg+" 没有设置环节人员！",$.scojs_message.TYPE_WARN);
                    	 that.view.alerterrormsg("没有设置环节人员！");
    	                	return;
    	                }}
    	            	that.baseParam.task_userids_map = task_userids_map;
    	    			that.baseParam.tmpnote = $('#disagreemsg').val();
    	    			//校验批语不能为空
	    				if(that.baseParam.tmpnote==""){
	    				  that.view.alerterrormsg(trans("workstation_approval_advice_empty"));
	    				  return;
	    				}
    	    			that.baseParam.actionCode = 'disAgree';
    	    			that.trigger("todeal");
    	    		}else if(actionCode=='reject'){//驳回
      			
    	    			var vval = $('.bootstrap-tablediv .moremiddlecontent input:checked');
    	    			if(vval!=undefined && vval.length > 0){
    	    				that.baseParam.tmpnote = $('#rejectmsg').val();
    	    				//校验批语不能为空
    	    				if(that.baseParam.tmpnote==""){
    	    				  that.view.alerterrormsg(trans("workstation_approval_advice_empty"));
    	    				  return;
    	    				}
    	    				that.baseParam.pk_active = vval.eq(0).val();    	    				
    	    				that.baseParam.actionCode = 'reject';
    		    			that.trigger("todeal");
    		    			
    	    			}else{
    	    				that.view.alerterrormsg("没有选择驳回环节！");
    	    			}
    	    			
    	    		}else if(actionCode=='reassign'){//改派
    	    			
    	    			if(!that.baseParam.reassign){
    	    				//没有选择改派人员
    	    				that.view.alerterrormsg(" 没有选择改派人员！");
    	    			}else{
    	    				that.baseParam.tmpnote = $('#reassigntmsg').val();
    	    				that.baseParam.actionCode = 'reassign';
    		    			that.trigger("todeal");
    	    			}
    	    			
    	    		}else if(actionCode=='addApprove'){//加签
    	    			var jqspan = $('#joinperson').find('span');
    	    			if(jqspan.length > 0){
    	    				var vcode = new Array();
    	    				jqspan.each(function(num){
    	    					vcode[num] = $(this).attr('data-code');
    	    				});
    	    				that.baseParam.addApproveUsers = vcode;
    	    				that.baseParam.tmpnote = $('#addApproventmsg').val();
    	    				that.baseParam.actionCode = 'addApprove';
    		    			that.trigger("todeal");
    	    			}else{
    	    				that.view.alerterrormsg(" 没有选择加签人员！");
    	    			}
    	    		}
        		
        	});
        	
        },
        initactionbtneventforchild:function(){
	    	var that = this;
	    	var parentbody = "";
	    	
        	if($('#isdealed').val()=='dealed'){
        		parentbody = $('body',window.parent.document);
        	}else{
        		parentbody = $('body');
        	}
	    	parentbody.find('.psonslfoolter .laseone button').unbind('click').bind('click',function(){
	    		CloseDiv('MyDiv','fade');
	    	});
	    	
	    	
	    	parentbody.find('.lasttwo').unbind('click').bind('click',function(){
	    		var actionCode=$(this).find('button').attr('actioncode');
	    		if(actionCode=='agree'){
	    			var ifempty = parentbody.find('.people-tablediv').find('input').length > 0?false:true;
	    			var task_userids_map = {};
	                 var ifselect = false;
	                if(!ifempty){ 
	                	parentbody.find('.people-tablediv>.moremiddlecontent').each(function(){
	                		//必须的指派人员
                        	if($(this).attr('mustassign')=="true"){
                        		//没有被选中的
                        		if($(this).find('input:checked').length==0){
                        			//直接提示
                        			that.view.alerterrormsg($(this).attr('keyname')+" 没有设置环节人员！");
                                	return;                      			
                        		}
                        		ifselect=true;
                        	}else{
                        	  //不是必须要选择 
                        		//没有选择人员
                        		if($(this).find('input:checked').length>0){                       			
                        			ifselect=true;	
                        		}
                        	  
                        	}
                        	//遍历选择的人员  
	                    	var parray = new Array();
	                    	$(this).find('input:checked').each(function(e){
	                    		parray[e] = $(this).val();
	                    	});
	                    	if(parray.length>0){
	                    	task_userids_map[$(this).attr('forkey')] = parray;}
	                    });	    			
	                 if(!ifselect){
	                	//$.scojs_message(alertmsg+" 没有设置环节人员！",$.scojs_message.TYPE_WARN);
	                	that.view.alerterrormsg(" 没有设置环节人员！");
	                	return;
	                }}
	            	that.baseParam.task_userids_map = task_userids_map;
	    			that.baseParam.tmpnote = parentbody.find('#agreemsg').val();
	    			that.baseParam.actionCode = 'agree';
	    			that.trigger("todeal");
	    			
	    		}else if(actionCode=='disAgree'){
	    			
	    			var ifempty = parentbody.find('.people-tablediv2').find('input').length > 0?false:true;
	    			var task_userids_map = {};
	                 var ifselect = false;
	                if(!ifempty){ 
	                	parentbody.find('.people-tablediv2>.moremiddlecontent').each(function(){
	                		//必须的指派人员
                        	if($(this).attr('mustassign')=="true"){
                        		//没有被选中的
                        		if($(this).find('input:checked').length==0){
                        			//直接提示
                        			that.view.alerterrormsg($(this).attr('keyname')+" 没有设置环节人员！");
                                	return;                      			
                        		}
                        		ifselect=true;
                        	}else{
                        	  //不是必须要选择 
                        		//没有选择人员
                        		if($(this).find('input:checked').length>0){                       			
                        			ifselect=true;	
                        		}
                        	  
                        	}
                        	//遍历选择的人员  
	                    	var parray = new Array();
	                    	$(this).find('input:checked').each(function(e){
	                    		parray[e] = $(this).val();
	                    	});
	                    	if(parray.length>0){
	                    	task_userids_map[$(this).attr('forkey')] = parray;}
	                    });
	    			
	                 if(!ifselect){
	//	                	$.scojs_message(alertmsg+" 没有设置环节人员！",$.scojs_message.TYPE_WARN);
	                	 that.view.alerterrormsg(" 没有设置环节人员！");
		                	return;
		                }}
		            	that.baseParam.task_userids_map = task_userids_map;
		    			that.baseParam.tmpnote = parentbody.find('#disagreemsg').val();
		    			//校验批语不能为空
	    				if(that.baseParam.tmpnote==""){
	    				  that.view.alerterrormsg(trans("workstation_approval_advice_empty"));
	    				  return;
	    				}
		    			that.baseParam.actionCode = 'disAgree';
		    			that.trigger("todeal");
		    		}else if(actionCode=='reject'){//驳回
		    			
		    			var vval = parentbody.find('.bootstrap-tablediv .moremiddlecontent input:checked');
		    			if(vval!=undefined && vval.length > 0){
		    				that.baseParam.tmpnote = parentbody.find('#rejectmsg').val();
    	    				//校验批语不能为空
    	    				if(that.baseParam.tmpnote==""){
    	    				  that.view.alerterrormsg(trans("workstation_approval_advice_empty"));
    	    				  return;
    	    				}
		    				that.baseParam.pk_active = vval.eq(0).val();
		    				that.baseParam.actionCode = 'reject';
		    				that.baseParam.tmpnote = parentbody.find('#rejectmsg').val();
			    			that.trigger("todeal");
		    			}else{
		    				that.view.alerterrormsg("没有选择驳回环节！");
		    			}
		    			
		    		}else if(actionCode=='reassign'){//改派
		    			
		    			if(!that.baseParam.reassign){
		    				//没有选择改派人员
		    				that.view.alerterrormsg(" 没有选择改派人员！");
		    			}else{
		    				that.baseParam.actionCode = 'reassign';
		    				that.baseParam.tmpnote = parentbody.find('#reassigntmsg').val();
			    			that.trigger("todeal");
		    			}
		    			
		    		}else if(actionCode=='addApprove'){//加签
		    			var jqspan = parentbody.find('#joinperson').find('span');
		    			if(jqspan.length > 0){
		    				var vcode = new Array();
		    				jqspan.each(function(num){
		    					vcode[num] = $(this).attr('data-code');
		    				});
		    				that.baseParam.addApproveUsers = vcode;
		    				that.baseParam.actionCode = 'addApprove';
		    				that.baseParam.tmpnote = parentbody.find('#addApproventmsg').val();
			    			that.trigger("todeal");
		    			}else{
		    				that.view.alerterrormsg(" 没有选择加签人员！");
		    			}
		    		}
	    		
	    	});
	    	
	    },
        unApprove: function () {
//            this.showModalDialog();
        var that = this;
	        //批语
	        if( that.baseParam.tmpnote){
	        	 that.baseParam.note =  that.baseParam.tmpnote;
	        }
	        var cloneParam=$.extend({},that.baseParam);
	        $.ajax({
	            url: "/mp/msg/action",
	            type: 'POST',
	            data: JSON.stringify($.extend(cloneParam, {actionCode: action})),
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
	                if (ret.isChangeState) {
	                    that.view.clear();
	                    that.view.alertmsg('操作完成！');
	                } else {
	                	 that.view.alertmsg('操作失败！');
	                }
	            }
	        }).always(function () {
	//                that.$busyIndicator.stop();
	//                delete that.$busyIndicator;
	        });
        },
        unApprovenew:function(){
        	that.baseParam.actionCode = action;
            //批语
            var inputNote=$('#agreemsg textarea').val();
            var note = (''!==inputNote)?inputNote: that.baseParam.note;
            that.baseParam.note = note;
            if( that.baseParam.tmpnote){
            	 that.baseParam.note =  that.baseParam.tmpnote;
            }
            var cloneParam=$.extend({},that.baseParam);
            $.ajax({
                url: "/mp/msg/action",
                type: 'POST',
                data: JSON.stringify($.extend(cloneParam, {actionCode: action})),
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
                    if (ret.isChangeState) {
                        that.view.clear();
                        that.view.alertmsg('取消审批操作完成！');
                    } else {
                    	 that.view.alertmsg('取消审批操作失败！');
                    }
                }
            }).always(function () {
//                    that.$busyIndicator.stop();
//                    delete that.$busyIndicator;
            });
            
        },
        //点击改派
       /* reassign: function () {
            var that = this;
            var action=that.action;

            var validateCallBack=function(){
                if(!that.baseParam.reassign){
                    $.scojs_message("没有改派人员！",$.scojs_message.TYPE_WARN);
                    return false;
                 }
                return true;
            };
            that.showModalDialog(validateCallBack);

            $('.ext-userRefModal').removeClass('hide');
            $('.refUserModal').bind('click',function(){
                that.showUserRefDialog();
               $('#userRefTable').bootstrapTable({
                url: '/msg/action',
                cache: false,
                contentType: 'application/json',
                jsonToSend: true,
                method: 'POST',
                striped: true,
                pagination: true,
                pageSize: 5,
                minimumCountColumns: 2,
                queryParams: function (res) {
                    res.search = res.search || '';
                    res = $.extend(res, that.baseParam);
                    return $.extend(res, {actionCode: 'getUserList', ref_type: 'user',isChangeState:false, action: action});
                },
                responseHandler: function (res) {
                    var ret = {};
                    if (res) {
                        ret.total = res.ref_count;
                        ret.rows = res.ref_result;
                    }
                    return ret;
                },
                clickToSelect: true
            });
            });
        },*/
       //修改改派点击事件开始
        
         calculateFunctionValue : function (func, args, defaultValue) {
            if (typeof func === 'string') {
                // support obj.func1.func2
                var fs = func.split('.');

                if (fs.length > 1) {
                    func = window;
                    $.each(fs, function (i, f) {
                        func = func[f];
                    });
                } else {
                    func = window[func];
                }
            }
            if (typeof func === 'function') {
                return func.apply(null, args);
            }
            return defaultValue;
        },
        
        
        reassign: function () {
        var that = this;
        var action=that.action;

        var validateCallBack=function(){
            if(!that.baseParam.reassign){
                $.scojs_message("没有改派人员！",$.scojs_message.TYPE_WARN);
                return false;
             }
            return true;
        };
        that.shownName="改派处理人员";
        var query=true;
        this.params ={
            	param:{
            		limit:5,
            		offset:0,
            		search:undefined,
            		sort:undefined,
            		order:'asc'
            	},
            	data:{},
                queryParams: function (res) {
                    res.search = res.search || '';
                    res = $.extend(res, that.baseParam);
                    return $.extend(res, {actionCode: 'getUserList', ref_type: 'user',isChangeState:false, action: action});
                },
                responseHandler: function (res) {
                    var ret = {};
                    if (res) {
                        ret.total = res.ref_count;
                        ret.rows = res.ref_result;
                    }
                    return ret;
                },
                clickToSelect: true
            };
            var sdata = that.calculateFunctionValue(this.params.queryParams, [this.params.param], this.params.data);
            sdata=JSON.stringify(sdata);
            
            $.ajax({
    	      url: "/mp/msg/action",
    	      type: 'POST',
    	      data: sdata,
    	      dataType: 'json',
    	      contentType: 'application/json',
    	      cache: false,
    	      async:false,
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
    	          if (!ret.ref_result) {
    	        	  $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_WARN);
    	        	  query= false;
    	          } else {
    	          	
    	          }
    	      }
    	  });
            
            if(query==false) return;
//        显示改派意见输入面板
        that.showModalDialog(validateCallBack);
//        显示改派指定人下拉选
        $('.ext-userRefModal').removeClass('hide');
       
//        点击“选择人员”触发人员查询事件
        $('.refUserModal').bind('click',function(){
        if(query==false) return;
            that.showUserRefDialog();
           $('#userRefTable').bootstrapTable({
            url: '/mp/msg/action',
            cache: false,
            contentType: 'application/json',
            jsonToSend: true,
            method: 'POST',
            striped: true,
            pagination: true,
            pageSize: 5,
            minimumCountColumns: 2,
            queryParams: function (res) {
                res.search = res.search || '';
                res = $.extend(res, that.baseParam);
                return $.extend(res, {actionCode: 'getUserList', ref_type: 'user',isChangeState:false, action: action});
            },
            responseHandler: function (res) {
                var ret = {};
                if (res) {
                    ret.total = res.ref_count;
                    ret.rows = res.ref_result;
                }
                return ret;
            },
            clickToSelect: true
        });
        });
    },
        //修改改派点击事件结束

        showBill: function () {
            var that = this;
            var action=that.action;
            that.$busyIndicator.start();
            that.baseParam.isChangeState=false;
            $.ajax({
                url: "/mp/msg/showBill",
                type: 'POST',
                data: JSON.stringify(that.baseParam),
                dataType: 'text',
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
                    //返回的数据是字符串，暂时这样区分
                    if(ret.indexOf('status')!=-1&&ret.indexOf('msg')!=-1){
                        ret= JSON.parse(ret);
                        $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_ERROR);
                    } else {
                         var bill=ret.replace(/border:0\.2mm/g,'border:0.325mm');
                        $.fancybox.open({
                            content: bill,
                            'type': 'html'
                        });
                    }
                }
            }).always(function () {
                    that.$busyIndicator.stop();
                });

        },

        trigger: function (name) {
            if (typeof this[name] !== 'undefined') {
                var args = Array.prototype.slice.call(arguments, 1);
                this[name].apply(this, args);
            } else {
                $.scojs_message('没有注册动作 ' + name, $.scojs_message.TYPE_WARN);
            }
        },

        init: function () {
            var that=this;
            var id = this.$article.attr('id');
            var msg = app.msgs.get(id);

            if(msg.get('extendattr')&&msg.get('extendattr').length>0){
                that.extendattr=JSON.parse(msg.get('extendattr'));
                var noteDefaultMap = that.extendattr.defaultNote;
                that.noteDefaultMap=noteDefaultMap;
            }

            var baseParam = {
                msgtype:msg.get("msgtype"),
                sourceSystemCode: msg.get("sourcesystem"),
                pk_sourcemsg: msg.get("pk_sourcemsg")
            };

            this.baseParam=baseParam;
            //动作类型
            if(hasAttr(that.extendattr,'pluginType')){
                that.baseParam.pluginType=that.extendattr['pluginType']
            }
            if(this.isMobielView){
                if(msg.get('showBill')){
                    this.initBill();
                }else{
                    that.view.$el.find('.fancybox-inner').addClass('hide');
                }

            }
        },

        initBill:function(){
            var that = this;
            this.action='showBill';
            that.baseParam.isChangeState=false;
            var baseParam=this.baseParam;
            baseParam.actionCode=this.action;
            $.ajax({
                url: "/mp/msg/showBill",
                type: 'POST',
                data: JSON.stringify(baseParam),
                dataType: 'text',
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
                    //返回的数据是字符串，暂时这样区分
                    $('.bill-content').empty()
                    if(ret.indexOf('status')!=-1&&ret.indexOf('msg')!=-1){
                        ret= JSON.parse(ret);
                        $.scojs_message(ret.msg, ret.status ? $.scojs_message.TYPE_OK : $.scojs_message.TYPE_WARN);
                    } else {
                        var bill=ret.replace(/border:0\.2mm/g,'border:0.325mm');
                         $('.bill-content').append(bill);
                    }
                }
            }).always(function () {

                });

        },
        initForm: function () {
            var that = this;
            var inputStyleA = '\
           <div class="input-group form-group  ">\
            <span class="input-group-addon">表单：</span>\
            <input   name="port" type="text"\
            placeholder=""\
            class="form-control input-md" required>\
            </div>';
            var inputStyleB = '\
            <div class="form-group row">\
                <label class="col-md-4 control-label" for="port">系统PORT</label>\
                <div class="col-md-8">\
                    <input id="port" name="port" type="text"\
                    placeholder=""\
                    class="form-control input-md" required>\
                    </div>\
                </div>';


            this.$okBtn = $('#handle .handleBtnGroup');
            this.$okBtn.find('.btn-submit')
                .off('click').on('click', function () {

                });
        },

        showUserRefDialog:function(){
            var that = this;
            var action=that.action;

            var okHandle= function () {
                var selects = $('#userRefTable').bootstrapTable('getSelections');
                if (selects.length > 0) {
                    $('button[data-target="userRefModal"]').html(selects[0].name);
                    that.baseParam.reassign = selects[0].code;//改派userpk
                }
                return true;
            };

            bootbox.dialog({
                title:'用户选择',
                message: '  \
                <div>\
                    <table id="userRefTable"\
                    data-show-refresh="true" data-search="true"\
                    data-side-pagination="server"\
                    data-pagination="true"\
                    data-page-list="[5, 10, 20, 50, 100, 200]">\
                        <thead>\
                            <tr>\
                                <th data-field="state" data-radio="true"></th>\
                                <th data-field="code" data-sortable="true">编码</th>\
                                <th data-field="name" data-align="center" data-sortable="true">名称</th>\
                            </tr>\
                        </thead>\
                    </table>\
            </div>' ,
                onEscape:function(){return true},
                backdrop:false,
                buttons: {
                    cancel:{
                        label:"取消",
                        className: "btn-default  btn-sm btn-submit"
                    },
                    main: {
                        label: "确定",
                        className: "btn-primary btn-sm btn-submit",
                        callback: okHandle
                    }
                }
            });
        },
        showUserRefDialogSign:function(){
        var that = this;
        var action=that.action;

        var okHandle= function () {
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
        };

        bootbox.dialog({
            title:'用户选择',
            message: '  \
            <div>\
                <table id="userRefTable"\
                data-show-refresh="true" data-search="true"\
                data-side-pagination="server"\
                data-pagination="true"\
                data-page-list="[5, 10, 20, 50, 100, 200]">\
                    <thead>\
                        <tr>\
                            <th data-field="state" data-checkbox="true"></th>\
                            <th data-field="code" data-sortable="true">编码</th>\
                            <th data-field="name" data-align="center" data-sortable="true">名称</th>\
                        </tr>\
                    </thead>\
                </table>\
        </div>' ,
            onEscape:function(){return true},
            backdrop:false,
            buttons: {
                cancel:{
                    label:"取消",
                    className: "btn-default  btn-sm btn-submit"
                },
                main: {
                    label: "确定",
                    className: "btn-primary btn-sm btn-submit",
                    callback: okHandle
                }
            }
        });
    },
    todeal:function(){
    var that = this;
    var flagmess = "";
	    if(!that.baseParam.task_userids_map){
	    	 that.baseParam.task_userids_map={};
	    }
//        that.baseParam.actionCode = action;
        //批语
        that.baseParam.isChangeState=true
        that.baseParam.note = that.baseParam.tmpnote;
        //校验批语
         if(that.baseParam.actionCode=='disAgree'||that.baseParam.actionCode=='reject'||that.baseParam.actionCode=='reassign'){
    		if(that.baseParam.note==""){
    			that.view.alerterrormsg(trans("workstation_approval_advice_empty"));
    			return;
    		}
    	}
        var cloneParam=$.extend({},that.baseParam);
        $.ajax({
            url: "/mp/msg/action",
            type: 'POST',
            data: JSON.stringify($.extend(cloneParam, {actionCode: that.baseParam.actionCode})),
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
            	//判断是否是柔性控制begin
/*            	if(ret.msg.indexOf("exceptionClass")>=0 && (that.baseParam.actionCode=="agree" || that.baseParam.actionCode=="disAgree" || that.baseParam.actionCode=="reject")){
            	//if (true) {	
	            		if(window.confirm(JSON.parse(ret.msg).message)){
	            	    	that.baseParam.forcePass='Y';
	            	    	that.baseParam.alertExceptionClass=JSON.parse(ret.msg).exceptionClass;
	            	    	that.todeal();
	            	    	return;
	            	    }else{
	            	    	that.baseParam.forcePass='N';
	            	    	return;
	            	    }
            	}
            	that.baseParam.forcePass='N';*/
            	if(ret.msg.indexOf("flag")>=0){
            		if(JSON.parse(ret.msg).flag.indexOf("fail")>=0 && (that.baseParam.actionCode=="agree" || that.baseParam.actionCode=="disAgree" || that.baseParam.actionCode=="reject")){
	            		if(window.confirm(JSON.parse(ret.msg).value.message)){
	            	    	that.baseParam.forcePass='Y';
	            	    	that.baseParam.alertExceptionClass=JSON.parse(ret.msg).value.exceptionClass;
	            	    	that.todeal();
	            	    	return;
	            	    }else{
	            	    	that.baseParam.forcePass='N';
	            	    	that.baseParam.alertExceptionClass="";
	            	    	return;
	            	    }
		            }else if(JSON.parse(ret.msg).flag.indexOf("success")>=0&& (that.baseParam.actionCode=="agree" || that.baseParam.actionCode=="disAgree" || that.baseParam.actionCode=="reject")){
		            	flagmess = JSON.parse(ret.msg).message;
		            	if (flagmess) {
	                		alert(flagmess);
						}
		            }
            	}
                	that.baseParam.forcePass='N';
                	that.baseParam.alertExceptionClass="";
            	//判断是否是柔性控制end
                //清空textarea
	            $('.page textarea').each(function(e){
	                 this.value="";
				});
	            $('.sub-tabcontent input').each(function(e){
	                 this.value="";
				});
            	var premsg = "";
            	var numflag = false;
            	
            	if(that.baseParam.actionCode=='agree'){
            		numflag = true;
            		premsg="批准";
            	}else if(that.baseParam.actionCode=='disAgree'){
            		numflag = true;
            		premsg="不批准";
            	}else if(that.baseParam.actionCode=='reject'){
            		numflag = true;
            		premsg="驳回";
            	}else if(that.baseParam.actionCode=='reassign'){
            		numflag = true;
            		premsg="改派";
            	}else if(that.baseParam.actionCode=='addApprove'){
            		premsg="加签";
            	}
            	var parentbody = "";
            	
            	if($('#isdealed').val()=='dealed'){
            		parentbody = $('body',window.parent.document);
            	}else{
            		parentbody = $('body');
            	}
            
                if (ret.isChangeState) {
                	$('.zzc').remove();//移除错误提示框
	                parentbody.find('.zzc').remove();
                	if($('#fade').css('display')!='none'||parentbody.find('#fade').css('display')!='none'){
                		//alert(flagmess);
                 		CloseDiv('MyDiv','fade');//关闭弹出层
                 	}
                	that.view.alertmsg(premsg+'操作完成！');
                	if(numflag){
                		that.changeBilltypeNum('mouse');//重置单据类型数量
                		var newcount = parseInt($("#task_tab_item").html())-1;
                    	if(newcount-1<0){
                    		$("#task_tab_item").html(0);
                    	 }else{
                    		 $("#task_tab_item").html(newcount);
                    	 }
                	}  
                	if(that.baseParam.actionCode=='reject' || that.baseParam.actionCode=='reassign'){
                		that.view.clear();
                	}
//                    that.view.clear();
                	if(that.baseParam.actionCode=='addApprove'){
                		that.$article.find('.context1').append('<div class="ellipse_jq" style="display:block;">加签中</div>');
                	}else{
                		that.$article.find('.status').attr('class','status status_did');
                        that.$article.find('h2 a').attr('ifdealed','dealed');
                        that.$article.find('.sub-tabcontent a').each(function(){
                        	if($(this).html()== trans("workstation_approval")){
                        		$(this).attr('ifdealed','dealed');
                        		$(this).attr('class','btn-unApprove');
                        		$(this).attr('actioncode','unApprove');
                        		$(this).html(trans("workstation_cancel_approval"));
                        	}else if($(this).html().indexOf(trans("workstation_more"))!=-1){
                        		$(this).attr('ifdealed','dealed');
                        		$(this).attr('class','content_more');
                        		$(this).attr('actioncode','getBill');
                        	}else if($(this).html()==trans("workstation_Process")){
                        		//增加流程动作
/*                        		$(this).attr('ifdealed','dealed');
                        		$(this).attr('class','actionBtn');
                        		$(this).attr('actioncode','showFlowImg');
                        		$(this).html('流程');*/
                        	}else{
                        		$(this).hide();
                        	}
                        	
                        });
                	}
                    

                    //$.scojs_message('操作完成',$.scojs_message.TYPE_OK);
                } else if(!ret.status){
                	
//                	 $.scojs_message((undefined!=ret.msg && ""!=ret.msg)?ret.msg:'操作失败',$.scojs_message.TYPE_ERROR);
	            	$('.zzc').remove();
	            	parentbody.find('.zzc').remove();
	            	that.view.alerterrormsg((undefined!=ret.msg && ""!=ret.msg)?ret.msg:premsg+'操作失败');
                }else{
                	$('.zzc').remove();
	                parentbody.find('.zzc').remove();
                	if($('#fade').css('display')!='none'||parentbody.find('#fade').css('display')!='none'){
 	                	
                 		CloseDiv('MyDiv','fade');
                 	}
                	that.view.alertmsg(premsg+'操作失败！');
                }
            },
            error:function (ret){
            	if(ret.responseText){
            		var num = ret.responseText.indexOf('msg')+6;
            		var errormsg = ret.responseText.substring(num).replace('"}','');
            		that.view.alerterrormsg((undefined!=errormsg && ""!=errormsg)?errormsg:'操作失败');
            	}
            }
        });
    },
    unapprovefun:function(e){
	    var that = this;
		var action = "unApprove";
		var flagmess = "";
		var task_userids_map = {};
		 var articleid = $(e.target).attr('pk');
	    that.baseParam.actionCode = action;
	    var event = e.currentTarget;
	    
	    //批语
	    var inputNote = $('#'+articleid).find('.noneinput').val();
	    
	    var note = (''!==inputNote)?inputNote: that.baseParam.note;
	    that.baseParam.isChangeState=true
	    
	    that.baseParam.task_userids_map=task_userids_map;
	    that.baseParam.note = note;
	    var cloneParam=$.extend({},that.baseParam);
	    $.ajax({
	        url: "/mp/msg/action",
	        type: 'POST',
	        data: JSON.stringify($.extend(cloneParam, {actionCode: action})),
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
	            if (ret.isChangeState) {
	            	//柔性判断begin
	            	if(ret.msg.indexOf("flag")>=0){
	            		if(JSON.parse(ret.msg).flag.indexOf("fail")>=0){
		            		if(window.confirm(JSON.parse(ret.msg).value.message)){
		            	    	that.baseParam.forcePass='Y';
		            	    	that.baseParam.alertExceptionClass=JSON.parse(ret.msg).value.exceptionClass;
		            	    	that.unapprovefun(e);
		            	    	return;
		            	    }else{
		            	    	that.baseParam.forcePass='N';
		            	    	that.baseParam.alertExceptionClass="";
		            	    	return;
		            	    }
			            }else if(JSON.parse(ret.msg).flag.indexOf("success")>=0){
			            	flagmess = JSON.parse(ret.msg).message;
			            	if (flagmess) {
		                		alert(flagmess);
							}
			            }
	            	}
	                	that.baseParam.forcePass='N';
	                	that.baseParam.alertExceptionClass="";
	               //柔性判断end
	            	//取消审批操作的单据id变换了，因此下一次要执行重新查询
	            	app.params.refreshFlag=true;
	            	that.view.alertmsg(trans("workstation_approval_cancel_sucess"));
	            	
	            	//改变左上角任务数量
	            	$("#task_tab_item").html(parseInt($("#task_tab_item").html())+1);
	            	that.changeBilltypeNum('add');//重置单据类型数量
	            	window.unclear = true;
	            	that.view.clear();
	            	window.unclear = false;
	                
	            } else if(!ret.status){
	            	//msg换行操作
	            	if(ret.msg){
	            		while(ret.msg.indexOf(';n;')>0){
	            			ret.msg = ret.msg.replace(/;n;/,'<br/>');
	            		}
	            	}
	            	that.view.alerterrormsg((undefined!=ret.msg && ""!=ret.msg)?ret.msg:'取消审批操作失败',$.scojs_message.TYPE_ERROR);
	            }else{
	            	that.view.alertmsg("取消审批操作失败！"); 
	            }
        },
    
    error:function(e){        
    var errorStr = e.responseText.replace('\n','');
    errorStr=errorStr.replace('\t','');
    errorStr=errorStr.replace('\r\n','');
    var errorMsg=JSON.parse(errorStr);
    $.scojs_message(errorMsg.msg,  $.scojs_message.TYPE_ERROR);
    }
    }).always(function () {//暂时去掉
//            that.$busyIndicator.stop();
//            delete that.$busyIndicator;
        });
    },
    //柔性控制
/*    taskSoftControl:function(msg){
	    var that = this;
	    if(window.confirm(msg)){
	    	that.baseParam.forcePass='Y';
	    }else{
	    	that.baseParam.forcePass='N';
	    }
		var cloneParam=$.extend({},that.baseParam);
		$.ajax({
            url: "/mp/msg/action",
            type: 'POST',
            data: JSON.stringify($.extend(cloneParam, {actionCode: that.baseParam.actionCode})),
            dataType: 'json',
            contentType: 'application/json',
            cache: false,
            success: function (ret) {
            	}
            });
    },*/
    changeBilltypeNum:function(staus){
    	var tmpbilltype = window.billtype;
    	var billtypenotes = $('.burright.billtype .buritem');
    	if(staus=='mouse'){
    		for(var i = 0;i<billtypenotes.length;i++){
        		var code = $.trim(billtypenotes.eq(i).attr('data-code'));
        		if(tmpbilltype==code){
        			var numhtml = billtypenotes.eq(i).next().html();
        			if(numhtml<=1){
        				billtypenotes.eq(i).next().html(0);
        				billtypenotes.eq(i).next().attr('class','graynum_circle');
        			}else{
        				billtypenotes.eq(i).next().html(parseInt(numhtml)-1);
        			}
        		}
        	}
    	}else{
    		for(var i = 0;i<billtypenotes.length;i++){
        		var code = $.trim(billtypenotes.eq(i).attr('data-code'));
        		if(tmpbilltype==code){
        			var numhtml = billtypenotes.eq(i).next().html();
        				billtypenotes.eq(i).next().html(parseInt(numhtml)+1);
        				billtypenotes.eq(i).next().attr('class','num_circle');
        		}
        	}
    	}
    	
    },
    showModalDialognew: function (callback) {
        var that = this;
	         var str = '<div class="people_container zzc">'+
				'<div class="black_overlaynew zzc">'+	
				'</div>'+
				'<div class="white_contentnew zzc">'+
					'<div class="assignpeo_top"><span>指派审批人</span></div>'+
					'<div class="assignpeo_middle">'+
					'</div>'+
					'<div class="assignpeo_bottom">'+
						'<div class="laseone"><button type="button" class="btn btn-fail btn-xs actionBtn dealcancle" pevent="cancle">取消</button></div>'+
			        	'<div class="lasttwo"><button type="button" style="background:#5cb85c;" class="btn btn-success btn-xs actionBtn dealok" >确定</button></div>'+
					'</div>'+
				'</div>'+
			'</div>';
	         
			$(str).appendTo('body');
			var show_div = "white_contentnew";
			var bg_div="black_overlaynew";
			$('.'+show_div).css('display','block');
			$('.'+bg_div).css('display','block');
			$('.'+bg_div).css('width',document.body.scrollWidth);
			$("."+bg_div).height($(document).height());

			$('.dealok').click(function(){
            
            var task_userids_map = {};
            var ifselect = false;
             $('.assignpeo_middle>.middlecontent').each(function(){
            	//必须的指派人员
             	if($(this).attr('mustassign')=="true"){
             		//没有被选中的
             		if($(this).find('input:checked').length==0){
             			//直接提示
             			$.scojs_message($(this).attr('keyname')+" 没有设置环节人员！",$.scojs_message.TYPE_WARN);           		
                     	return;                      			
             		}
             		ifselect=true;
             	}else{
             	  //不是必须要选择 
             		//没有选择人员
             		if($(this).find('input:checked').length>0){                       			
             			ifselect=true;	
             		}
             	  
             	}
             	//遍历选择的人员  
                	var parray = new Array();
                	$(this).find('input:checked').each(function(e){
                		parray[e] = $(this).val();
                	});
                	if(parray.length>0){
                	task_userids_map[$(this).attr('forkey')] = parray;
                	}
                });
            
                if(!ifselect){
                	$.scojs_message(" 没有设置环节人员！",$.scojs_message.TYPE_WARN);
//                	that.view.alerterrormsg(alertmsg+" 没有设置环节人员！");
                	
                	return;
                }else{
                	 that.baseParam.task_userids_map = task_userids_map;
                	 that.trigger("todeal");
                }
			});
			
            return true;
    },
    
        // PUBLIC FUNCTION DEFINITION
        showModalDialog: function (callback) {
            var that = this;
            var action=that.action;
            var notedefault='请输入处理意见,默认:'+that.baseParam.note||'';

            var submitBtnHandle= function () {
               //回调处理
                if(callback&&!callback())
                    return false;

//                that.$busyIndicator.start();
                that.baseParam.actionCode = action;
                //批语
                var inputNote=$('#point textarea').val();
                var note = (''!==inputNote)?inputNote: that.baseParam.note;
                that.baseParam.note = note;
                if( that.baseParam.tmpnote){
                	 that.baseParam.note =  that.baseParam.tmpnote;
                }
                var cloneParam=$.extend({},that.baseParam);
                $.ajax({
                    url: "/mp/msg/action",
                    type: 'POST',
                    data: JSON.stringify($.extend(cloneParam, {actionCode: action})),
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
                        if (ret.isChangeState) {
                            //跟新view
                            //ret.sourcesystem=that.baseParam.sourceSystemCode;
                            //that.view.model.set(ret);
                            that.view.clear();
                            $.scojs_message('操作完成',$.scojs_message.TYPE_OK);
                        } else {
                        	 $.scojs_message((undefined!=ret.msg && ""!=ret.msg)?ret.msg:'操作失败',$.scojs_message.TYPE_ERROR);
                        }
                    }
                }).always(function () {
                        that.$busyIndicator.stop();
                        delete that.$busyIndicator;
                    });

                return true;
            };
            bootbox.dialog({
                title: that.title,
                message: '<div>\
                <div>\
                    <div class="textarea" id="point">\
                        <div class="ext-content">\
                        </div>\
                        <div class="ext-userRefModal form-group hide">\
                            <div class="input-group">\
                                <span class="input-group-addon">'+that.shownName+'：</span>\
                                <button type="button" class="form-control dropdown-toggle btn btn-default refUserModal"\
                                data-toggle="modal"\
                                data-target="userRefModal"\
                                title="请选择" >请选择 <b class="caret"></b></button>\
                            </div>\
                        </div>\
                    </div>\
                 </div>\
                </div>\
            </div>' ,
                onEscape:function(){return true},
                backdrop:false,
                buttons: {
                    main:{
                    	label: "提交",
                        className: "btn-primary btn-sm btn-submit",
                        callback: submitBtnHandle
                       
                    },
                    cancel: {
                    	 label:"取消",
                         className: "btn-default  btn-sm btn-submit"
                    }
                }
            });
        },
        
      //初始化审批页面->左侧预览附件->左侧导航
        initPreviewLeft:function(previewParam){
        	//获取全局信息
          var msgid=$('#work_history').attr('pk');
          var msg=app.msgs.get(msgid);       	
        	//单据正文
            //获取下载单据正文附件的信息 
          var previewContent=JSON.parse(msg.get('extendattr')).workflowInfo.showBillAtt;       	
          var previewContentDownloadUrl="";        
          if(previewContent!=undefined){
             previewContentDownloadUrl="msg/download/"+previewContent.attraddress+"?sourceSystemCode="+previewParam.sourceSystemCode+"&pk_sourcemsg="+previewParam.pk_sourcemsg+"&msgtype="+previewParam.msgtype+"&fileType="+previewContent.attrtype+"&attraddress="+previewContent.attraddress+"&fileName="+previewContent.attrname;                         
          }
        //单据正文
        //获取下载单据正文附件的信息
    	var attchstr = '';
    	attchstr+='<div class="pleft-bill-select click-preview"><a class="select" href="javascript:void(0)"  downloadurl="'+previewContentDownloadUrl+'" onclick="preview(this);">单据正文</a></div>';
        /*attchstr+='<div class="attach-span" ><span style="color:white;">----</span><div class="pleft-attach"><span style="color: white;margin-left: 14px;font-size:11px;">附件----</span></div></div>	';*/
        attchstr+='<div class="attach-span" ><span style="color:white;">----</span><div class="pleft-attach"><img src="/mp/img/preview_attach.png" style="width:10px;height:14px;"/><span style="color: white;font-size:11px;margin-left: 2px;">附件----</span></div></div>	';
    	//附件        
    	var attchs=msg.get("attrurllist");
    	//var attchs=JSON.parse(msg.get('extendattr')).workflowInfo.flowAttrs;
    	var index = 0;
    	for(var curr in attchs){
    		if(attchs[curr].attrname==undefined||attchs[curr].attrname==''||attchs[curr].attrname.indexOf(".")<=0) continue;
             var type = attchs[curr].attrname.substring(attchs[curr].attrname.lastIndexOf(".")+1);
          	  var className="";
	              if(type.indexOf("xls")>=0){
	            	  className="pleft-excel";
	              }else if(type.indexOf("doc")>=0){
	            	  className="pleft-word";
	              }else if(type.indexOf("ppt")>=0){
	            	  className="pleft-ppt";
	              }else if(type.indexOf("pdf")>=0){
	            	  className="pleft-pdf";
	              }else{
	            	  className="pleft-other";
	              }	           
	              attchstr+='<div class="'+className+' click-preview"><a href="javascript:void(0)"  downloadurl='+attchs[curr].downloadurl+' onclick="preview(this);" title="'+attchs[curr].attrname+'" >'+attchs[curr].attrname+'</a></div>';  
	              index=index+1;
    	}
    	if(index==0){
    		attchstr+='<div style="text-align: center; font-size: 12px;text-align: center;color: #e5e5e5;">无附件</div>';
    	}
    	var parentbody = $('body');
         //集成到portal里面parentbody的值获取的是portal中的body，因此需要判断
         if(parentbody.find('#allcontent').length==0){
      	  parentbody= $('body',window.parent.document);
         }
         parentbody.find('.preview_left').html("");
         parentbody.find('.preview_left').html(attchstr);
    	//$('.preview_left').html("");
    	//$('.preview_left').html(attchstr); 
    	//单据正文初始化
         if($('body').find('#allcontent').length==0){
             window.parent.geturl(previewContentDownloadUrl);
         }else{
        	 geturl(previewContentDownloadUrl); 
         }   	
    }
    }

    //  PLUGIN DEFINITION
    /**
     *
     * @param article el
     * @param msgitemview backbone msg view
     * @param isMobielView
     * @returns {*}
     * @constructor
     */
    $.UAP65 = function (article, msgitemview,isMobielView) {
        var data = $(article).data('plugin');
        // Initialize the plugin.
        if (!data) {
            data = new UAP65(article, msgitemview,isMobielView);
            $(article).data('plugin', data);
        }
        return   data;
    };

}(jQuery);
