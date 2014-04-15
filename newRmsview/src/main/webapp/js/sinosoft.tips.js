/*!
 * tips
 * levone
 * Mail:zuoliwen@sinosoft.com.cn
 * Date: 2012-07-03
 */
(function ($) {
	 $.fn.extend({
		tips: function(opts){
			var defaults = {
				type      : "toolTip" , //toolTip浏览器自动提示/windowTip窗口提示/customTip用户自定义展示
				content   : "" ,        //提示内容
				title     : "" ,        //只有windowTip时显示
				width     : "auto",     //固定宽度，高度将自适应
				maxwidth  : 200,     //最大宽度
				maxHeight : "auto",     //最大高度
				pointer   : true,       //是否显示指针
				tipPostion: "top",   //top上、right右、bottom下、left左
				//aniType   : "toggle",   //动画展示方式:toggle基本、slideToggle滑动、fadeToggle淡入淡出
				hideTime  : 1000 ,      //动画执行速度，毫秒
				autoHide  : true,       //自动隐藏
				closeBtn  : false,      //关闭按钮
				eventType : "mouseover",//触发事件mouseover、mousedown、mouseenter、mousemove、mouseout、mouseover、mouseup
				//follow    : false,      //是否跟随
				winTipSite: "leftTop"          //windowTip坐标位置:leftTop左上角\rightTop右上角\leftBottom左下角\rightBottom右下角,自定义坐标:left数值\top数值
			}
			var defaults = $.extend(defaults,opts);
			
			//toolTip方法
			if(defaults.type == "toolTip"){
				var $tipCode = $('<div class="tips"><div class="skin skin_0"><div class="skin_line"><div class="tips_cont"><div class="tips_padding" id="tipsText">Undfind</div></div><div class="pointer"></div></div></div></div>');
				addTips($tipCode);
				/*
问题：：：下面这段是干什么的？？怎么看他也没作用？？当时第一遍看的时候怎么没有注意到？？
				$tipCode.hover(function(){
					$(this).show();
				},function(){
					$(this).hide();
				});
				*/
				//下面这段是把所有的title或者alt的属性的值拿出来，并且替换成tip这个属性，好让控件来识别
				$(this).each(function(){
					var tipText = "";
					if($(this).attr("title")){
						tipText = $(this).attr("title");
						$(this).removeAttr("title").attr("tip",tipText);
					}else if($(this).attr("alt")){
						tipText = $(this).attr("alt");
						$(this).removeAttr("alt").attr("tip",tipText);
					};
				});
				//绑定事件（他是先显示再调整位置）
				$(this).mouseover(function(e){
					//抓取属性
					var tipCrawl = $(this).attr("tip");	
					var tipOffset = $(this);
					if(tipCrawl){
						//替换提示内容
						$("#tipsText").text("").text(tipCrawl);	
						$tipCode.show();
						if(defaults.maxwidth != "auto"){							
							$("#tipsText").css({"width":"auto","white-space":"nowrap"})
							if($("#tipsText").width() > defaults.maxwidth){				
								$("#tipsText").css("white-space","normal").width(defaults.maxwidth);
							}
						};						
						tipLocate(tipOffset,$tipCode,e);						
						decideTip($tipCode,tipOffset,e)
					};
					if(defaults.width != "auto")
					$("#tipsText").width(defaults.width);					
				}).mouseout(function(){
					$tipCode.hide();
				})
			}else if(defaults.type == "customTip"){
				var content = defaults.content;
				var $customTip = $('<div class="tips"><div class="skin_1"><div class="skin_line bottom_left"><div class="tips_cont"><div class="tips_padding" id="customText">Undfind</div></div><div class="pointer"></div></div></div></div>');
				if(defaults.closeBtn){
					var $tipClose = $('<div class="close"></div>');
					var tipOffset = $(this);
					$("div.skin_line",$customTip).append($tipClose);
					$tipClose.click(function(){
						$customTip.hide();
					});
				}
				if(defaults.title){
					var $titleClose = $('<div class="tips_title">' + defaults.title + '</div>');
					$("div.skin_line",$customTip).prepend($titleClose)
				}
				addTips($customTip);								
				if(defaults.width)
				$customTip.width(defaults.width);
				$("div.tips_padding",$customTip).html(content);
				var eventType = defaults.eventType;
				tipOffset.bind(eventType,function (e){
					//判断是否为函数类型dddddddddddddd
					if(jQuery.isFunction(content))
					$("div.tips_padding",$customTip).html(content);
					$customTip.show();
					tipLocate(tipOffset,$customTip, e);						
					decideTip($customTip,tipOffset, e)
				})
			}else if(defaults.type == "windowTip"){
				var $winTip = $('<div class="tips"><div class="skin_1 tip_window"><div class="skin_line bottom_left"><div class="tips_cont"><div class="tips_padding">未定义</div></div></div></div></div>');
				var tipOffset = $(this);			
				if(defaults.title){
					var $titleClose = $('<div class="tips_title">' + defaults.title + '</div>');
					$("div.skin_line",$winTip).prepend($titleClose);
				}
				if(defaults.closeBtn){
					var $tipClose = $('<div class="close"></div>');
					$("div.tips_title",$winTip).prepend($tipClose);
					$tipClose.click(function(){
						$winTip.hide();
					});
				}
				if(defaults.wight){
					$winTip.width(defaults.wight);
				}
				addTips($winTip);
				$("div.tips_padding",$winTip).html(defaults.content)
				var onOff = function (event){
					tipOffset.unbind("click",onOff);
					winTipSite($winTip);
					$winTip
						.fadeIn()
						.delay(defaults.hideTime)
						.fadeOut(function(){tipOffset.bind("click",onOff)});
					event.stopPropagation();
				}
				tipOffset.bind("click",onOff);
			}
					
			//插入Tip
			function addTips($tipCode){
				$("body").append($tipCode);
			}
			//tip定位
			function tipLocate(origin,theTip,e){
				var top = origin.offset().top + origin.height() + 9;
				var left = e.pageX - 15;
				if(defaults.pointer == false){
					$("div.pointer",$tipCode).hide();
				}else{
					var pClass = $("div.skin_line",$tipCode);
					if(defaults.tipPostion == "bottom"){
						pClass.addClass("top_left");
					}else if(defaults.tipPostion == "top"){
						top = Math.abs(origin.offset().top - theTip.height() - 9);
						pClass.addClass("bottom_left");
					}else if(defaults.tipPostion == "right"){
						top = Math.abs(origin.offset().top + origin.height()/2 - 15);
						left = Math.abs(origin.offset().left + origin.width() + 10);
						pClass.addClass("left_top");
					}else if(defaults.tipPostion == "left"){
						top = Math.abs(origin.offset().top + origin.height()/2 - 16);
						left = Math.abs(origin.offset().left - theTip.width());
						pClass.addClass("right_top");
					};
				};				
				theTip.css({"left":left,"top":top});	
			};
			//winTip定位
			function winTipSite(tip){
				var left = $(window).scrollLeft();;
				var top = $(window).scrollTop();
				var bottom = top + $(window).height() - tip.height();
				var right = left + $(window).width() - 	tip.width();
				var y = top + $(window).height()/2 - tip.height()/2;
				var x = left + $(window).width()/2 - tip.width()/2;
				if(defaults.winTipSite == "leftBottom"){
					top = bottom;
				}else if(defaults.winTipSite == "rightTop"){
					left = right;
				}else if(defaults.winTipSite == "rightBottom"){
					left = right;
					top = bottom;
				}else if(defaults.winTipSite == "center"){
					left = x;
					top = y;
				};
				//跟随滚动条
				$(window).scroll(function(){
					left = $(window).scrollLeft();;
					top = $(window).scrollTop();
					bottom = top + $(window).height() - tip.height();
					right = left + $(window).width() - 	tip.width();
					y = top + $(window).height()/2 - tip.height()/2;
					x = left + $(window).width()/2 - tip.width()/2;
					if(!tip.is(":hidden")){
						if(defaults.winTipSite == "leftBottom"){
							top = bottom;
						}else if(defaults.winTipSite == "rightTop"){
							left = right;
						}else if(defaults.winTipSite == "rightBottom"){
							left = right;
							top = bottom;
						}else if(defaults.winTipSite == "center"){
							left = x;
							top = y;
						};
						tip.animate({top:top,left:left},{duration:600,queue:false})
					}
				})
				tip.css({"left":left,"top":top})
				
			}
			//判断改变tip位置
			function decideTip(tip,tipOffset,e){
				//指针
				var pDecide = $("div.skin_line",tip);
				//tip宽高
				var tipH = tip.height() + 10;
				var tipW = tip.width() + 10;
				//tip坐标
				var left = tip.css("left");
				var top = tip.css("top");
				//dom元素宽高
				var cW = tipOffset.width() + 12;
				var cH = tipOffset.height();
				//dom元素坐标
				var x = tipOffset.offset().left;
				var y = tipOffset.offset().top;
				//window宽高
				var wW = $(window).width();
				var wH = $(window).height();
				//scroll偏移
				var sW = $(window).scrollLeft();
				var sH = $(window).scrollTop();

				var b = wH + sH < y + cH + tipH;
				var t = y - sH < tipH;
				//var l = wW + sW > x + cW + tipW;
				var l = tipW < cW;
				var r = wW + sW < x + cW + tipW;
				
				var oldClass = pDecide.attr("class");
				if(!pDecide.attr("oldClass")){
					pDecide.attr("oldClass",oldClass)
				}
				
				if(b && r){
					pDecide.removeClass().addClass("skin_line bottom_right");
					top = y - tipH;
					left = x - tipW + cW ;
					tip.css({"left":left,"top":top});
				}else if(b && l){
					pDecide.removeClass().addClass("skin_line bottom_left");
					top = y - tipH;
					if(defaults.type == "toolTip"){
						left = x + cW/2;
					}else if(defaults.type == "customTip"){
						left = x + tipW/6 ;
					}				
					tip.css({"left":left,"top":top});
				}else if(t && l){
					pDecide.removeClass().addClass("skin_line top_left");										
					if(defaults.type == "toolTip"){
						top = y + cH + 8;
						left = x + cW/2 ;
					}else if(defaults.type == "customTip"){
						top = y + cH + 12;
						left = x + tipW/6 ;
					}
					tip.css({"left":left,"top":top});
				}else if(t && r){
					pDecide.removeClass().addClass("skin_line top_right");					
					if(defaults.type == "toolTip"){
						top = y + cH + 8;
						left = x - tipW + cW/2;
					}else if(defaults.type == "customTip"){
						top = y + cH + 8;
						left = x + cW - tipW;
					}
					tip.css({"left":left,"top":top});
				}else if(b){
					pDecide.removeClass().addClass("skin_line bottom_left");
					top = y - tipH;
					tip.css({"left":left,"top":top});
				}else if(t){
					pDecide.removeClass().addClass("skin_line top_left");
					top = y + cH + 8;
					tip.css({"left":left,"top":top});
				}else if(l){
					pDecide.removeClass().addClass("skin_line left_top");
					left = x + cW;					
					top = y + cH/6;
					tip.css({"left":left,"top":top});
				}else if(r){
					pDecide.removeClass().addClass("skin_line right_top");
					left = x - tipW;
					top = y + cH/6;
					tip.css({"left":left,"top":top});
				}else{
					pDecide.removeClass().addClass(pDecide.attr("oldClass"));
				}
			};
		}					
	});
})(jQuery);
