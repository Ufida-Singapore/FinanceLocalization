/*global Backbone, jQuery, _, ENTER_KEY */
var app = app || {};

(function ($) {
    'use strict';

    // The Application
    // ---------------
    var alerttabflag =0;
    var viewHelper = {
        	timeStamp2String : function(time){
        		if(!time){
        			return'';
        		}
    		    var datetime = new Date();
    		    datetime.setTime(time);
    		    var year = datetime.getFullYear();
    		    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    		    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    		    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    		    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    		    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    		    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
        	}
    };
    
    app.AlertMsgMoreView = Backbone.View.extend({
        //  present in the HTML.
        el: '#alert_msg_more_info1',
        
        alert_detail_template:_.template($('#alert_detail_template').html()),
        
        alert_msg_more_template:_.template($('#alert_msg_more_template').html()),

        events: {
        	'click .msg_title': 'showAlertDetailMsg',
            'click .alert_waitdeal_img_wrap div': "alertMoreImgChangeClass"
        },

        initialize: function () {
            this.renderMsg("alert");
            alerttabflag=0;
            this.listenTo(app.msgs, 'alertMoreSelectInfo', this.loadAlertMoreSelectInfo);
            this.listenTo(app.msgs, 'alertSelectMore', this.loadAlertMore);
            this.listenTo(app.msgs, 'alertSenderInit', this.loadAlertSenderInfo);
        },
        
        renderMsg:function(e){
        	var that=this;
            $.ajax({
                url: "/mp/msg/msgs",
                type:'GET',
                dataType: 'json',
                data:{
                	 msgType:"alert",
                	 msgStatus:"all",
                	per_page:"10",
                	    page:"1"
                },
                cache:false,
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
                success:function(ret){
                	 if(!ret.status){
                         $.scojs_message(ret.msg, ret.status?$.scojs_message.TYPE_OK:$.scojs_message.TYPE_ERROR);
                     }else{
                    	 if((ret.total/10)<1){
                        		$(".toselectmore").hide();
                        	}else{
                        		$(".toselectmore").show();
                        	}
                    	 var data = _.extend({msg: ret.data},viewHelper);
                    	 if(0==ret.total){
                    		 $("#alert_msg_more_info").html('<div class="nodata">\
                                     <span class="glyphicon glyphicon-info-sign"></span>\
                                     <div class="txt">' + trans("workstation_no_news_hoho") + '</div>\
                                    </div>');  
                       		$("#alertMoreBody").addClass("overflow_y_hidden");
                	    }else{
                	    	$("#alertMoreBody").removeClass("overflow_y_hidden");
                	    	for(var i=0;i<data.msg.length;i++){
                   			 var str = that.HTMLEncode1(data.msg[i].content);
                   			 data.msg[i].content=str;
                   		    }
                            $("#alert_msg_more_info").append(that.alert_msg_more_template(data));
                	    }
                    }
                }
            });
        },
        HTMLEncode1: function( input ) 
		{ 
        	if(!input){
        		return null;
        	}
		    var converter = document.createElement("DIV"); 
			var arr = CheckBrowser();
			var output 
			if(arr && arr[0]=='IE'){
				converter.innerHTML = input; 
				output = converter.innerText; 
				converter = null; 
			}else{
				converter.innerHTML = input; 
				output = converter.textContent; 
				converter = null; 
			}
			return output;
		}, 
        HTMLEncode: function( input ) 
		{ 
        	if(!input){
        		return null;
        	}
//		var converter = document.createElement("DIV"); 
//		
//			converter.innerText = input; 
//			var output = converter.innerHTML;
//			converter = null;
//			
//			var converter2 = document.createElement("DIV");
//			converter2.innerHTML = output; 
//			var output2 = converter2.innerText;
//			return output2; 
        	var converter = document.createElement("DIV"); 
			var arr = CheckBrowser();
			var output 
			if(arr && arr[0]=='IE'){
				converter.innerHTML = input; 
				output = converter.innerText; 
				converter = null; 
			}else{
				converter.innerHTML = input; 
				output = converter.textContent; 
				converter = null; 
			}
			
			var converter2 = document.createElement("DIV"); 
			var output2 
			if(arr && arr[0]=='IE'){
				converter2.innerHTML = output; 
				output2 = converter2.innerText; 
				converter2 = null; 
			}else{
				converter2.innerHTML = output; 
				output2 = converter2.textContent; 
				converter2 = null; 
			}
			return output2;
		},
        //查询动作
        loadAlertMoreSelectInfo:function(theme,starttime,endtime,sender,msgstatus){
        	var that=this;
        	if(starttime){
        		var str = starttime.replace(/-/g,'/'); 
        		starttime = new Date(str).getTime()+'';
        	}
        	if(endtime){
        		var str = endtime.replace(/-/g,'/'); 
        		endtime = new Date(str).getTime()+'';
        	}
        	var fresh = false;
        	if(typeof window._refreshFlag == 'boolean' && window._refreshFlag){
        		fresh = true;
        	} 
        	
        	//alert(theme+starttime+endtime+sender+msgstatus);       	
        	app.msgs.state.currentPage=1;
            $.ajax({
                url: "/mp/msg/msgs",
                type:'GET',
                dataType: 'json',
                data:{
                	 msgType:"alert",
                	 msgStatus:msgstatus,
                	 msgDate:starttime,
                	 endtime:endtime,
                   	 msgSearch:theme?encodeURI(encodeURI(theme)):'',
                	 seachSender:sender,
                	 refreshFlag:fresh,
                	per_page:"10",
                	    page:"1"
                },
                cache:false,
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
                success:function(ret){
                	 if(!ret.status){
                         $.scojs_message(ret.msg, ret.status?$.scojs_message.TYPE_OK:$.scojs_message.TYPE_ERROR);
                     }else{
                    	 var data = _.extend({msg: ret.data},viewHelper);
                    	 if((ret.total/10)<1){
                       		$(".toselectmore").hide();
                       	}else{
                       		$(".toselectmore").show();
                       	}                    	
                    	 if(0==ret.total){
                    		 $("#alert_msg_more_info").html('<div class="nodata">\
                                     <span class="glyphicon glyphicon-info-sign"></span>\
                                     <div class="txt">' + trans("workstation_no_news_hoho") + '</div>\
                                    </div>');
                     		$("#alertMoreBody").addClass("overflow_y_hidden");
	              	    }else{
	              	    	$("#alertMoreBody").removeClass("overflow_y_hidden");
	              	    	for(var i=0;i<data.msg.length;i++){
                   			 var str = that.HTMLEncode1(data.msg[i].content);
                   			 data.msg[i].content=str;
                   		 }
	                         $("#alert_msg_more_info").empty().append(that.alert_msg_more_template(data));
	              	    }
	               }
                }
            });
        },
        //点击加载更多
        loadAlertMore:function(theme,starttime,endtime,sender,msgstatus){
        	var that=this;
        	if(starttime){
        		var str = starttime.replace(/-/g,'/'); 
        		starttime = new Date(str).getTime()+'';
        	}
        	if(endtime){
        		var str = endtime.replace(/-/g,'/'); 
        		endtime = new Date(str).getTime()+'';
        	}
        	
        	//alert(theme+sendtime+sender+msgstatus);
        	app.msgs.state.currentPage+=1;
        	//alert(app.msgs.state.currentPage);
            $.ajax({
                url: "/mp/msg/msgs",
                type:'GET',
                dataType: 'json',
                data:{
                	 msgType:"alert",
                	 msgStatus:msgstatus,
                	 msgDate:starttime,
                	 endtime:endtime,
                   	 msgSearch:theme?encodeURI(encodeURI(theme)):'',
                	 seachSender:sender,
                	per_page:"10",
                	    page:app.msgs.state.currentPage
                },
                cache:false,
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
                success:function(ret){
                	 if(!ret.status){
                         $.scojs_message(ret.msg, ret.status?$.scojs_message.TYPE_OK:$.scojs_message.TYPE_ERROR);
                     }else{
                    	 var data = _.extend({msg: ret.data},viewHelper);
                    	 if((ret.total/(app.msgs.state.currentPage*10))<1){                    	
                      		$(".toselectmore").hide();
                      	}else{
                      		$(".toselectmore").show();
                      	}
                    	 for(var i=0;i<data.msg.length;i++){
                			 var str = that.HTMLEncode1(data.msg[i].content);
                			 data.msg[i].content=str;
                		 }
                    	 $("#alert_msg_more_info").append(that.alert_msg_more_template(data));
                    }
                }
            });
        },
        alertMoreImgChangeClass:function(e){
	    	var that=this;
	    	if(e.currentTarget.className=="alert_more_waitdeal_img"){
	    		var selectedItem = $(e.currentTarget);
		    	selectedItem.removeClass('alert_more_waitdeal_img').addClass('alert_more_dealed_img');
		    	//首页tab数量更新
		    	var alerttab = $(parent.document.getElementById('alert_tab_item'));
	     		var newcount;
	     		var arr = CheckBrowser();
        		if(arr && arr[0]=='火狐'){
           		 newcount = alerttab[0].textContent;
                }else{
               	 newcount = alerttab[0].innerText;
                }
	     		if(newcount>0){
	         		if(0==newcount-1){
	         			$(parent.document.getElementById('alert_tab_red_circle')).removeClass("red_circle");
	         		}
	         		if(arr && arr[0]=='火狐'){
	         			alerttab[0].textContent=newcount-1;
                       }else{
                    	alerttab[0].innerText=newcount-1;
                    }
	     		}
		    	//状态post
	        	var baseParam = {
		                msgtype:"alert",
		                sourceSystemCode: e.target.attributes[2].value,
		                pk_sourcemsg: e.target.attributes[1].value
	            };
	            this.statusChangeAjaxPost(baseParam);
	    	}
    },
    statusChangeAjaxPost:function(baseParam){
    	var that=this;
	        var cloneParam=$.extend({},baseParam);
	        var action = "readMessage";
	       $.ajax({
	    	    url: "/mp/msg/action",
	            type: 'POST',
	            data: JSON.stringify($.extend(cloneParam, {isChangeState:true, actionCode: action})),
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
                //$.scojs_message('操作完成',$.scojs_message.TYPE_OK);
            } else {
            	//$.scojs_message((undefined!=ret.msg && ""!=ret.msg)?ret.msg:'操作失败',$.scojs_message.TYPE_ERROR);
            }
        }
    })
    },
    //发件人初始化
    loadAlertSenderInfo:function(){
    //$("#senderPopDiv").addClass('sender-div');
    $("#alertSenderPopDiv").attr( 'class','sender-div');
        var that = this;
    	var pageSize = 3;
    	var baseParam = {        			
    			//limit:3,
    			//offset:0,
    			//search:"",
    			//order:"asc",
        		pluginType: "default",
        		sourceSystemCode: "UAP65",  //必须 
        		isChangeState:false       			
    		};
    	$('#alertSenderRefTablegp').bootstrapTable('destroy').bootstrapTable({
            url: '/mp/msg/action',
            cache: false,
            contentType: 'application/json',
            jsonToSend: true,
            method: 'POST',
            striped: true,
            pagination: true,
            pageSize: pageSize,
            minimumCountColumns: 2,
            //search:false,
            queryParams: function (res) {
                res.search = res.search || '';
                res = $.extend(res, baseParam);
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
                $("#alertSenderInput").val(row.name);
                $("#alertSenderInput").attr('data-code',row.code);
                $("#alertSenderPopDiv").attr( 'class','hidden');
        })
        if($("#alertSenderRefTablegp").find("thead").find("tr").children('th').length==5){
    		$("#alertSenderRefTablegp").find("tr").children('th:last-child').remove();
    	}
    },
    //预警详情
    showAlertDetailMsg:function(e){
    	var _this=this;
    	var html="";
    	var title="";
    	/*var sourceSystemCode=e.target.attributes[4].value;
    	var pk=e.target.attributes[2].value;
    	var msgtype=e.target.attributes[3].value;*/
    	var sourceSystemCode=e.target.attributes.id.value;
    	var pk=e.target.attributes.pk.value;
    	var msgtype=e.target.attributes.msgtype.value;
            $.ajax({
                url: "/mp/msg/findOneMsg",
                type:'GET',
                dataType: 'json',
                data:{
                	    pk_sourcemsg:pk,
                	sourceSystemCode:sourceSystemCode,
                			 msgType:msgtype
                },
                cache:false,
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
                success:function(ret){
                	 if(!ret){
                         $.scojs_message(ret.msg, ret.status?$.scojs_message.TYPE_OK:$.scojs_message.TYPE_ERROR);
                     }else{
                    	 var data = _.extend({msg: ret},viewHelper);
                    	 /*var str = _this.HTMLEncode(data.msg.msgVo.content);*/
                    	 var str = data.msg.msgVo.content;
                    	 html=_this.alert_detail_template(data);
                    	 title="预警-详情";
                    	 if(!str){
                    		 str="";
                    	 }
                    	 $(e.currentTarget.parentNode.parentElement.parentElement.parentElement).empty().append(_this.alert_detail_template(data)).find('.msg_detail_content').html("").append($('<div>'+str+'</div>'));
                    	 //post改变处理状态
                    	 //未读
                    	 window._refreshFlag = true;
                         if(data.msg.msgVo.msgstatus == "waitdeal"){
                        	 //修改tab中数量
                        	 var alerttab ,newcount;
                        	 var arr = CheckBrowser();
                        	 alerttab = $(parent.document.getElementById('alert_tab_item'));
                        	 if(arr && arr[0]=='火狐'){
                        		 newcount = alerttab[0].textContent;
                             }else{
                            	 newcount = alerttab[0].innerText;
                             }
                            if(newcount>0){
                                 if(0==newcount-1){
                                 	$(parent.document.getElementById('alert_tab_red_circle')).removeClass("red_circle");
                                 }
                                 if(arr && arr[0]=='火狐'){
                                	 alerttab[0].textContent=newcount-1;
                                 }else{
                                	 alerttab[0].innerText=newcount-1;
                                 }
                                
                           }
             	            var baseParam = {
             	            		pluginType:JSON.parse(ret.msgVo.extendattr).pluginType,/*添加actionType参数*/
             		                msgtype:msgtype,
             		                sourceSystemCode: sourceSystemCode,
             		                pk_sourcemsg: pk
             	            };
             	            _this.statusChangeAjaxPost(baseParam);
                         }
                         
/*                         $.fancybox.open({
                        	 title:title,
                     		titleShow:true,
                     		helpers: {
                                title : {
                     	                type : 'inside',
                                          position : 'top'			
                                        }
                             },
                             content: html,
                        	 scrolling: 'auto',
                             width:743,
                        	 padding:0,
                             'type' : 'iframe'
                         });*/
                    }
                }
            });
           
    }
    });
    $(document).bind('click',function(e){
	var target = $(e.target);
	if(target.attr('id')=="alertSenderInput"){
		$('#alertSenderPopDiv').show();
		return;
		}
	
	$('#alertSenderPopDiv').css('display','none');
	$('#alertSenderPopDiv').on('click',function(e){ 
		if (e.stopPropagation) 
			e.stopPropagation(); 
		else 
			e.cancelBubble = true; 
	});
  });
})(jQuery);