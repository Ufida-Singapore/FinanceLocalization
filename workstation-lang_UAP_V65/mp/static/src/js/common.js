;(function ($) {


    Date.prototype.getLastDayOfMonth = function() {
        return new Date(new Date(this.getFullYear(),this.getMonth()+1,0)).getDate();
    };

    Date.prototype.format = function(fmt)
    {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }

    function error(msg,url,line){
    	   var REPORT_URL = "system/error?info="; // 收集上报数据的信息
    	   var m =[encodeURI(encodeURI(msg)), url, line, navigator.userAgent, +new Date];// 收集错误信息，发生错误的脚本文件网络地址，用户代理信息，时间
    	   var url = REPORT_URL + m.join('||');// 组装错误上报信息内容URL
    	   var img = new Image;
    	   img.onload = img.onerror = function(){
    	      img = null;
    	   };
    	   img.src = url;// 发送数据到后台cgi
    	}
    	// 监听错误上报
    	window.onerror = function(msg,url,line){
    	   error(msg,url,line);
    	}

    	
    $(document).ajaxComplete(function (q, s, p) {
        if (s.status == 0 || s.readyState == 0 || s.status == 400 || s.status == 404) {
        	//暂时注释掉 以后放开
//            $.scojs_message('服务连接故障, 请稍后重试', $.scojs_message.TYPE_ERROR);
        }
    });


    $.extend({
        isMobile:function(){
            return (/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(navigator.userAgent.toLowerCase()));
        },
        isIE: function () {
            var b = document.createElement('b')
            b.innerHTML = '<!--[if IE]><i></i><![endif]-->'
            return b.getElementsByTagName('i').length === 1
        },
        urlParam : function () {
            if (!!window.location.search) {
                var urlparams = decodeURI(window.location.search.substring(1));
                var kv = urlparams.split("&");
                var param = {}, par, paramkey;
                for (var index = 0, len = kv.length; index < len; index++) {
                    par = kv[index].split("=");
                    paramkey = par[0];
                    if (paramkey.indexOf("[]") == (paramkey.length - 2)) {
                        var q = paramkey.substring(0, paramkey.length - 2);
                        if (param[q] === undefined) {
                            param[q] = []
                        }
                        param[q].push(par[1])
                    } else {
                        param[paramkey] = par[1]
                    }
                }
               return param;
            } else {
               return {}
            }
    },
    attchDownload:function(fileUrl){
        var userAgent = (navigator.userAgent || navigator.vendor || window.opera).toLowerCase();
    var isIos;                  //has full support of features in iOS 4.0+, uses a new window to accomplish this.
    var isAndroid;              //has full support of GET features in 4.0+ by using a new window. Non-GET is completely unsupported by the browser. See above for specifying a message.
    var isOtherMobileBrowser;   //there is no way to reliably guess here so all other mobile devices will GET and POST to the current window.
    var $iframe, downloadWindow;

    if (/ip(ad|hone|od)/.test(userAgent)) {
        isIos = true;
    } else if (userAgent.indexOf('android') !== -1) {
        isAndroid = true;
    } else {
        isOtherMobileBrowser = true;
    }
    if (isIos || isAndroid) {
        downloadWindow = window.open(fileUrl);
        downloadWindow.document.title = "下载中";
        window.focus();
    } else {
        var showIFrame = function(fileUrl) {
            var iframe;
            $(body).remove("#DownLoadIFrame");
            $iframe = $("<iframe>")
                .hide()
                .prop("id", 'DownLoadIFrame')
                .prop("src", fileUrl);
//                .on('load', function(error) {
//                    //maybe 404 happen
//                    $.scojs_message('文件找不到啦', $.scojs_message.TYPE_WARN);
//                    return true;
//                });
            document.body.appendChild($iframe.get(0));
        };
        showIFrame(fileUrl);
      }
    },    
    detailAttchDownload:function(fileUrl){
        var userAgent = (navigator.userAgent || navigator.vendor || window.opera).toLowerCase();
        var isIos;                  //has full support of features in iOS 4.0+, uses a new window to accomplish this.
        var isAndroid;              //has full support of GET features in 4.0+ by using a new window. Non-GET is completely unsupported by the browser. See above for specifying a message.
        var isOtherMobileBrowser;   //there is no way to reliably guess here so all other mobile devices will GET and POST to the current window.
        var $iframe, downloadWindow;

        if (/ip(ad|hone|od)/.test(userAgent)) {
            isIos = true;
        } else if (userAgent.indexOf('android') !== -1) {
            isAndroid = true;
        } else {
            isOtherMobileBrowser = true;
        }
        if (isIos || isAndroid) {
            downloadWindow = window.open(fileUrl);
            downloadWindow.document.title = "下载中";
            window.focus();
        } else {
            var showIFrame = function(fileUrl) {
                var iframe;
                $(parent.document.body).remove("#DownLoadIFrame");
                $iframe = $("<iframe>")
                    .hide()
                    .prop("id", 'DownLoadIFrame')
                    .prop("src", fileUrl);
//                    .on('load', function(error) {
//                        //maybe 404 happen
//                        $.scojs_message('文件找不到啦', $.scojs_message.TYPE_WARN);
//                        return true;
//                    });
                //document.body.appendChild($iframe.get(0));
                (parent.document.body).appendChild($iframe.get(0));
            };
            showIFrame(fileUrl);
          }
        },
    moreAttchDownload:function(fileUrl){
        var userAgent = (navigator.userAgent || navigator.vendor || window.opera).toLowerCase();
    var isIos;                  //has full support of features in iOS 4.0+, uses a new window to accomplish this.
    var isAndroid;              //has full support of GET features in 4.0+ by using a new window. Non-GET is completely unsupported by the browser. See above for specifying a message.
    var isOtherMobileBrowser;   //there is no way to reliably guess here so all other mobile devices will GET and POST to the current window.
    var $iframe, downloadWindow;

    if (/ip(ad|hone|od)/.test(userAgent)) {
        isIos = true;
    } else if (userAgent.indexOf('android') !== -1) {
        isAndroid = true;
    } else {
        isOtherMobileBrowser = true;
    }
    if (isIos || isAndroid) {
        downloadWindow = window.open(fileUrl);
        downloadWindow.document.title = "下载中";
        window.focus();
    } else {
        var showIFrame = function(fileUrl) {
            var iframe;
          //$(parent.parent.document.body).remove("#DownLoadIFrame");
            var parentbody = $('body');
	           //集成到portal里面parentbody的值获取的是portal中的body，因此需要判断
	           if($(parent.document.body).find('#allcontent').length==1){
	        	   $(parent.document.body).remove("#DownLoadIFrame");
	           }else{
	        	   $(parent.parent.document.body).remove("#DownLoadIFrame"); 
	           }	          
            $iframe = $("<iframe>")
                .hide()
                .prop("id", 'DownLoadIFrame')
                .prop("src", fileUrl);
//                .on('load', function(error) {
//                    //maybe 404 happen
//                    $.scojs_message('文件找不到啦', $.scojs_message.TYPE_WARN);
//                    return true;
//                });
            //document.body.appendChild($iframe.get(0));
            //(parent.parent.document.body).appendChild($iframe.get(0));
            
	           //集成到portal里面parentbody的值获取的是portal中的body，因此需要判断
	           if($(parent.document.body).find('#allcontent').length==1){
	        	   (parent.document.body).appendChild($iframe.get(0));
	           }else{
	        	   (parent.parent.document.body).appendChild($iframe.get(0)); 
	           }
        };
        showIFrame(fileUrl);
      }
    }
    });

    $.fn.attchPopup = function (a) {
        var i = $(this);
        function show() {
            var msgid=i.closest("article").attr("id");

            var msg=app.msgs.get(msgid);
            var attchs=msg.get("attrurllist");
            var attchstr='';
            //fix ie8 (临时移除类方法indexOf)
            var indedxOf=Array.prototype.indexOf;
            delete Array.prototype.indexOf;
            for(var curr in attchs){
            	if(attchs[curr].attrname==undefined) continue; //如果不加这句，ie8会出错
            	  var type = attchs[curr].attrname.substring(attchs[curr].attrname.lastIndexOf(".")+1);
            	  var imgstr="";
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
	              //var title=(attchs[curr].attrname).replace(/\s/g,"");
            	
                /*attchstr+='<li   class="fancy-view" style="display: list-item"><span class="fleft"><img src="/mp/img/excel-01.svg"/><a target="_blank"  href='+attchs[curr].downloadurl+'>'+attchs[curr].attrname+'<a/></span><span class="fright">'+attchs[curr].uploader+'</span></li>';*/
	              attchstr+='<li   class="fancy-view" style="display: list-item"><span class="fleft">'+imgstr+'<a target="_blank" title='+"\""+attchs[curr].attrname+"\""+' href='+attchs[curr].downloadurl+'>'+attchs[curr].attrname+'<a/></span><span class="fright">'+attchs[curr].uploader+'</span></li>';
            }
           
            Array.prototype.indexOf=indedxOf;

            var u = '<ol>' + ('' !== attchstr ? attchstr : '<li   class="fancy-view" style="display: list-item">') + trans("workstation_no_attachments") + '</li>' + '<ol/>';

            var r = {placement: "bottom", title:'',trigger: "manual", html: true, content:u, template: ' <div class="pop-tag popover tag-popup"><div class="arrow"></div><h4 class="popover-title"></h4><div class="popover-content"></div></div>'};
            
            i.popover(r), i.popover("show");
            i.siblings(".tag-popup").css('left','-175px');
            //设置index页任务附件的小箭头位置
            i.siblings(".tag-popup").each(function(){
            	$(this).find(".arrow").css('left','80%');
            });
        }

        "show" === a ? 0 === i.siblings(".tag-popup").length ? show() : i.siblings(".tag-popup").show() : "hide" === a && i.siblings(".tag-popup").hide()

    }

    $.extend({
        loadScriptAsync : function(url, callback) {
        jQuery.ajax({
            'url': url,
            'dataType': 'script',
            'cache': true,
            'success': callback || jQuery.noop
        });
         }
    });


})(jQuery);

var myScroll;
/**
 * 下拉刷新 （自定义实现此方法）
 */
function pullDownAction () {
    app.msgs.trigger('pullpush',true,myScroll.refresh,myScroll);
}


/**
 * 初始化iScroll控件
 */
function loaded() {
	
	$("#pullDown").removeClass("hide");
	
    var pullDownEl, pullDownOffset;

    pullDownEl = document.getElementById('pullDown');
    pullDownOffset = pullDownEl.offsetHeight;
    myScroll = new iScroll('content', {
        scrollbarClass: 'myScrollbar',
        useTransition: false,
        handleClick: true,
        topOffset: pullDownOffset,
        onRefresh: function () {
            if (pullDownEl.className.match('loading')) {
                pullDownEl.className = '';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '更多...';
            }
        },
        onScrollMove: function () {
            if (this.y > 5 && !pullDownEl.className.match('flip')) {
                pullDownEl.className = 'flip';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手即更新...';
                this.minScrollY = 0;
            } else if (this.y < 5 && pullDownEl.className.match('flip')) {
                pullDownEl.className = '';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '更多...';
                this.minScrollY = -pullDownOffset;
            }
            //sync document
            $(document).scrollTop(-this.distY+$(document).scrollTop());
        },
        onScrollEnd: function () {
            if (pullDownEl.className.match('flip')) {
                pullDownEl.className = 'loading';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
                pullDownAction();	// Execute custom function (ajax call?)
            }
        }
    });
}
 
!function(){
	$('.meta-tags .popover').live('mouseout',function(){$(this).hide();}).live('mouseover',function(){$(this).show();});
}();
