/*
 * Layout控件
 *
 * Copyright (c) levone
 *
 * Date: 2011-12-01
 *
 */
(function ($) {
	 $.fn.extend({
		layout: function(opts){
			var defaults = {
				top:{
					topHeight : 60,       //高度
					topSwitch : true,    //拖动功能开关：true-开；false-关
					topMin : 30,         //拖动的最小高度
					topMax : 150,         //拖动的最大高度
					topHide : true ,      //显示隐藏开关：true-开；false-关
					topMaximize : false      //是否最大化：true/false
				},
				left:{
					leftWidth : 200,   //左侧宽度
					leftSwitch : true, //拖动功能开关：true-开；false-关
					leftMin : 100,  //拖动的最小宽度
					leftMax : 300, //拖动的最大宽度
					leftHide : true, //显示隐藏的开关：true-开；false-关
					leftMaximize : false //是否最大化：true:最大化；false：不最大化
				},
				right:{
					rightWidth : 150,
					rightSwitch : true,
					rightMin : 60,
					rightMax : 230,
					rightHide : true,
					rightMaximize : false
				},
				bottom:{
					bottomHeight : 30,
					bottomSwitch : true,
					bottomMin : 15,
					bottomMax : 100,
					bottomHide : true,
					bottomMaximize : false
				},
				center:{
					cneterMaximize : true
				}
			};
			$('html,body').width('100%').height('100%');
			var top;
			var left;
			var right;
			var bottom;
			var center;
			$.extend(true, defaults, opts);		
			top = defaults.top;
			left = defaults.left;
			right = defaults.right;
			bottom = defaults.bottom;
			center = defaults.center;
			var thisBox = $(this);
			thisBox.css({'overflow':'hidden','position':'relative'});
			//加入下边这句是为了防止IE6、7出现空白的滚动条
			$('html').css({'overflow':'hidden'});
			var header    = $("#layout_top");   //上
			var leftBar   = $("#layout_left");  //左
			var rightBar  = $("#layout_right"); //右
			var footer    = $("#layout_bottom");//下
			var contenter = $("#layout_center");//中（内容区）			
			
			var allWidth = thisBox.width();    //这个宽度是不是可变的？？这个宽度是可变的，根据窗口的大小而改变的
			var allHeight = thisBox.height();  //这个高度是不是可变的？？这个高度是可变的，根据窗口的大小而改变的
			var topHeight = top.topHeight;     //头部的高度(这个都有默认的)
			var leftWidth = left.leftWidth;    //左侧的宽度(这个都有默认的)
			var rightWidth = right.rightWidth; //右侧的宽度（这个都有默认的）
			var bottomHeight = bottom.bottomHeight; //底部的高度（这个都有默认的）
			var cenWidth = allWidth - leftWidth - rightWidth; //中间div的宽度，body的宽度-左侧宽度-右侧宽度
			var cenHeight = allHeight - topHeight - bottomHeight; //中间div的高度，body的高度-头部的高度-底部的高度
			var bottomTop = topHeight + cenHeight; //头部div的高度+中间的div的高度，是为了底部的top属性准备的
			
			var topReviseAll = $("<div class='layout_top_resizer'><a href='javascript:void(0)' class='top_up' id='top_resizer'></a></div>"); //头部进行拖拽的div
			var topRevise = $('#top_resizer'); //div中的a标签，那个小三角号
			var topReviseTop = topHeight - 6; //头部拖拽div的top属性的值
			
			var leftReviseAll = $("<div class='layout_left_resizer'><a href='javascript:void(0)' class='left_close' id='left_resizer'></a></div>"); //左侧进行拖拽的div
			var leftRevise = $('#left_resizer'); //左侧拖拽中的a标签，那个小三角号
			var leftReviseL = leftWidth - 4; //左侧拖拽的的left属性
			//这个是干什么？？？？这个东西是为了左右侧的那个三角号准备的，通过padding-top来对那个小三角进行定位
			var cenReviseP = cenHeight/2 - 25; 
			
			var rightReviseAll = $("<div class='layout_right_resizer'><a href='javascript:void(0)' class='right_close' id='right_resizer'></a></div>");//右侧进行拖拽的div
			var rightRevise = $('#right_resizer'); //右侧拖拽的小三角
			
			var bottomReviseAll = $("<div class='layout_bottom_resizer'><a href='javascript:void(0)' class='bottom_up' id='bottom_resizer'></a></div>");//底部进行拖拽的div             
			var bottomRevise = $('#bottom_resizer'); //底部拖拽的小三角
			
//问题：：：：：：下面这个东西的宽度和高度也得给他加上，否则他会比外部的div大，虽然表面上看不出来！！！但是现在还没有弄这个宽度和高度，它的作用应该是放内容的
			var wrapDiv = $('<div class="wrap_div"></div>'); 
			
			var displayDiv; //这个是干什么的？？控制.wrap_div的显示和隐藏
			
			var centerOldW;
			var centerOldH;
			var centerOldT;
			var centerOldL;
			var topOldW;
			var topOldH;
			var topOldT;
			var leftOldW;
			var leftOldH;
			var leftOldT;
			var rightOldW;
			var rightOldH;
			var rightOldT;
			var bottomOldW;
			var bottomOldH;
			var bottomOldT;
			
			
			//这个判断就是为了判断是否有需要该div
			if(footer.length=='0'){
				bottomHeight = 0;
			};
			if(header.length=='0'){
				topHeight = 0;
			};
			if(leftBar.length=='0'){
				leftWidth = 0;
			};
			if(rightBar.length=='0'){
				rightWidth = 0;
			};
			
			//初始化各个header、footer、right、left、center这些div用的，做layout的初始化装载用的
			forPane();
			function forPane(){
				//前边已经初始化了，为什么现在还要初始化？？因为在window的resize中也用到了他
				//这时body的大小已经改变了，所以要给下面的五个值重新赋值
				//问题：：：：：：不过感觉貌似不大需要了，等回头再看看吧！！！！！！！！！！！！
				cenWidth = allWidth - leftWidth - rightWidth;
				cenHeight = allHeight - topHeight - bottomHeight;
				bottomTop = topHeight + cenHeight;
				cenReviseP = cenHeight/2 - 25; 
				leftReviseL = leftWidth - 4;
				topReviseTop = topHeight - 6;
				
				if(header.length<=0){topHeight = 0};
				if(leftBar.length<=0){leftWidth = 0};
				if(rightBar.length<=0){rightWidth = 0};
				if(footer.length<=0){bottomHeight = 0};
				if(header.length > 0){
					header.css({'width':allWidth,'height':topHeight}); //头部的高度和宽度的初始化
					$('div.wrap_div',header).css({'width':allWidth,'height':topHeight});
					topReviseAll.css('top', topReviseTop); //头部的拖拽的滚动条top赋值
//（已解决）问题：：：：我觉的初始化的时候他应该是进不了if的，为什么现在进入if了，首先top.topSwitch是true，这个是没有错的，但是length应该是错的啊？？？
					//我懂了，因为header代表的div已经在页面初始化的时候写到html页面中了，
					//所以直接 向header中添加topReviseAll就可以了，但是在if前边还没有添加topReviseAll及topRevise
					//他是在if结束之后才进入的，我操，终于想明白了，刚才走神了~~~~~o(╯□╰)o
					//if只有在页面初始化的时候才起作用，其他的时候都不起作用。
					if(top.topSwitch == true && topRevise.length <= 0){
//问题：：：：：：：下边这个if和else有什么意思？？？？？？？ 初始化的时候走的是else,表示top.topHide为true
						//如果是false的时候，把向上的那个图标的class弄没了，就不能向上了
						if(top.topHide == false){
							$('.top_up', topReviseAll).remove();
						}else{
							header.wrapInner(wrapDiv);
							$('div.wrap_div',header).css({'width':allWidth,'height':topHeight});
						};					
						header.append(topReviseAll);
						topRevise = $('#top_resizer');
					};
					
				};
				if(leftBar.length>0){
					leftBar.css({'top':topHeight+1,'left':0,'width':leftWidth,'height':cenHeight});
					$('div.wrap_div',leftBar).css({'width':leftWidth ,'height':cenHeight});
					if(left.leftSwitch == true && leftRevise.length<=0){	
						if(left.leftHide == false){
							$('.left_close', leftReviseAll).remove();
						}else{
							leftBar.wrapInner(wrapDiv);							
							$('div.wrap_div',leftBar).css({'width':leftWidth,'height':cenHeight});
						};
						leftBar.append(leftReviseAll);
						leftRevise = $('#left_resizer');
					};
					//下边这个paddingTop是为了放置中间那个三角号
					leftReviseAll.css({'left':leftReviseL,'height':cenHeight-cenReviseP,'padding-top':cenReviseP,'position':"absolute", 'z-index':"800"});
				};
				if(rightBar.length>0){
					rightBar.css({'top':topHeight+1,'right':0,'width':rightWidth,'height':cenHeight});
					$('div.wrap_div',rightBar).css({'width':rightWidth,'height':cenHeight});
					if(right.rightSwitch==true && rightRevise.length<=0){
						if(right.rightHide == false){
							$('.right_close',rightReviseAll).remove();
						}else{
							rightBar.wrapInner(wrapDiv);
							$('div.wrap_div',rightBar).css({'width':rightWidth,'height':cenHeight,'margin-left':"5px"});
						};
						rightBar.append(rightReviseAll);
						rightRevise = $('#right_resizer');
					};
					
					rightReviseAll.css({'height':cenHeight-cenReviseP,'padding-top':cenReviseP, "position":"absolute", "z-index":"200"});
				};				
				if(footer.length>0){
					footer.css({'top':bottomTop,'left':0,'width':allWidth,'height':bottomHeight});
					$('div.wrap_div',footer).css({'width':allWidth,'height':bottomHeight});
					//问题：：：：：：：switch和revise的这两个开关的问题，其实就是这个if和else为什么在这里用，是为了初始化的确定是否有，箭头的方向么？
					if(bottom.bottomSwitch==true && bottomRevise.length<=0){
						if(bottom.bottomHide == false){
							$('.bottom_up',bottomReviseAll).remove();
						}else{
							footer.wrapInner(wrapDiv);
							$('div.wrap_div',footer).css({'width':allWidth,'height':bottomHeight});
						};
						footer.prepend(bottomReviseAll);
						bottomRevise = $('#bottom_resizer');
					};
					
				};
				if(contenter.length>0){
					contenter.css({'top':topHeight+1,'left':leftWidth+1,'width':cenWidth,'height':cenHeight});
				};
			};
			
			//鼠标拖拽移动标记
			var _move=false;
			var _x,_y;//鼠标离控件左上角的相对位置
			
			function draggable(e){
				var thisDiv = $(this);
				var parentBox = $(this).parent();
				var x,y;
				_move=true;
				_x=e.pageX-parseInt(thisDiv.css("left"));
				_y=e.pageY-parseInt(thisDiv.css("top"));
				thisDiv.css({'background':'#000'});
				parentBox.css({'overflow':'visible','z-index':99});//点击后开始拖动并透明显示

				$(document).mousemove(function(e){
					if(_move){
						x = e.pageX - _x;//移动时根据鼠标位置计算控件左上角的绝对位置
						y = e.pageY - _y;
						//拖拽位置
						if(thisDiv.attr('class')=='layout_top_resizer'){
							thisDiv.css({top:y});
						}else if(thisDiv.attr('class')=='layout_left_resizer'){
							thisDiv.css({left:x});
						}else if(thisDiv.attr('class')=='layout_right_resizer'){
							thisDiv.css({left:x});
						}else if(thisDiv.attr('class')=='layout_bottom_resizer'){
							thisDiv.css({top:y});
						};
					};
					
				}).mouseup(function(){										
					_move=false;
					//判断拖拽最大最小范围
					if(thisDiv.attr('class')=='layout_top_resizer'){
						if(y > top.topMax){
							topHeight = top.topMax;
						}else if(y < top.topMin){
							topHeight = top.topMin;
						}else{
							topHeight = y + 6;
						};
					}else if(thisDiv.attr('class')=='layout_bottom_resizer'){
						if(footer.height()-y > bottom.bottomMax){
							bottomHeight = bottom.bottomMax;
						}else if(footer.height()-y < bottom.bottomMin){
							bottomHeight = bottom.bottomMin;
						}else{
							bottomHeight = footer.height() - y;
						};
					}else if(thisDiv.attr('class')=='layout_left_resizer'){
						if(x > left.leftMax){
							leftWidth = left.leftMax;
							thisDiv.parent().find(".wrap_div").css({"width":leftWidth});
						}else if(x < left.leftMin){
							leftWidth = left.leftMin;
							thisDiv.parent().find(".wrap_div").css({"width":leftWidth});
						}else{
							leftWidth = x;
							thisDiv.parent().find(".wrap_div").css({"width":leftWidth});
						};
					}else if(thisDiv.attr('class')=='layout_right_resizer'){
						if(rightBar.width()-x > right.rightMax){
							rightWidth = right.rightMax;
						}else if(rightBar.width()-x < right.rightMin){
							rightWidth = right.rightMin;
						}else{
							rightWidth = rightBar.width()-x;
						};
					}
					$(document).unbind('mousemove mouseup');
					thisDiv.css('background','url(../images/layout_top_bg.gif) no-repeat -999px -999px');
					parentBox.fadeTo(20, 1).css({'overflow':'hidden','z-index':1});//松开鼠标后停止移动并恢复成不透明
					
					forPane();//控件新位置
					
					
					if(thisDiv.attr('class')=='layout_bottom_resizer'){
						thisDiv.css({top:0});
					}else if(thisDiv.attr('class')=='layout_right_resizer'){
						thisDiv.css({left:0});
					}else if(thisDiv.attr('class')=='layout_left_resizer'){
						thisDiv.css({left:leftWidth-4});
					}else if(thisDiv.attr('class')=='layout_top_resizer'){
						thisDiv.css({top:topHeight-6});
					};
				});				
				return false;
			};

			//在这里也用到了forPane这个函数
			$(window).resize(function(){
				allWidth = thisBox.width();
				allHeight = thisBox.height();				
				forPane();
				if(header.hasClass('max_start')){
					header.css({"width":allWidth, "height":allHeight});
				}
				if(leftBar.hasClass('max_start')){
					leftBar.css({"width":allWidth, "height":allHeight, "top":"0px"});
				}
				if(rightBar.hasClass('max_start')){
					rightBar.css({"width":allWidth, "height":allHeight, "top":"0px"});
				}
				if(footer.hasClass('max_start')){
					footer.css({"width":allWidth, "height":allHeight, "top":"0px"});
				}
				if(contenter.hasClass('max_start')) {
					contenter.css({"width":allWidth, "height":allHeight, "top":"0px", "left":"0px"});
				}
				
			});			
			
			
			//当鼠标移上去的时候，
			if(top.topMaximize){
				topMaximize('top',header);	
			};
			if(left.leftMaximize){
				topMaximize('left',leftBar);
			};
			if(right.rightMaximize){
				topMaximize('right',rightBar);
			};
			if(bottom.bottomMaximize){
				topMaximize('bottom',footer);
			};
			if(center.centerMaximize){
				topMaximize('center',contenter);
			};
			
			//插入最大化功能
//下面这个方法只是做了初始化工作的，只有在初始化的时候才会去执行
			function topMaximize(type,obj){
				//这个是右上角的最大化的图标的html标签
				var maxDiv = $('<a class="max_div" href="javascript:void(0)" onclick="javascript:void(0)"></a>');
				if(type == 'center') {
					obj.append(maxDiv);
				} else {
					obj.find(".wrap_div").append(maxDiv);
				}
				obj.hover(function(){
					maxDiv.show().animate({opacity:1},'fast');
				},function(){
					maxDiv.animate({opacity:0},'fast',function(){$(this).hide()});
				});
				if(type=='top'){
					maxDiv.bind('click',function(){maxStart(type)});
				};
				if(type=='left'){
					maxDiv.bind('click',function(){maxStart(type)});
				};
				if(type=='right'){
					maxDiv.bind('click',function(){maxStart(type)});
				};
				if(type=='bottom'){					
					maxDiv.bind('click',function(){maxStart(type)});
				};
				if(type=='center'){	
					maxDiv.bind('click',function(){maxStart(type)});
				};
				
			};
			//最大化动画（最大化实际上执行的是这个方法，上边的方法是给他绑定事件）
//问题：：：：：我终于发现了center中是没有.wrap_div这个div的，是直接向里边放东西的~~~~~~~~
			function maxStart(type){
				if(type == 'center'){
					var obj = contenter;
					if(obj.hasClass('max_start')){
						//还原
						//animate这个动画我如果设置了多个值并且还设置了hide这样的东西，那么他会怎么执行；如果只是有多个数值，那么他会按照那个值去执行，还是一段时间之内，每个值改变一次？？？？
						//下面这两行是我加的
						var centerOldW = $("body").width() - rightBar.width() - leftBar.width();
						var centerOldH = $("body").height() - header.height() - footer.height();
						obj.removeClass('max_start').animate({width:centerOldW,height:centerOldH,top:centerOldT,left:centerOldL},'fast',function(){$(this).css('z-index',1)});
					}else{
						//最大化
						//下边这四行是在最大化之前，把它以前的height，widht，top，left给保存起来，以让他还原的时候再来使用
						centerOldW = obj.width();
						centerOldH = obj.height();
						centerOldT = obj.css('top');
						centerOldL = obj.css('left');
						obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth,height:allHeight,top:0,left:0},'fast');
					};
				}else if(type == 'top'){
					var obj = header;
					if(obj.hasClass('max_start')){
						topOldW = $("body").width();
						//还原
						obj.removeClass('max_start').animate({width:topOldW,height:topOldH,top:topOldT},'fast',function(){$(this).css('z-index',1)});
						obj.find(".wrap_div").css({"height":topOldH, "width":topOldW});
						$('.wrap_div',obj).next().show();
					}else{
						//最大化
						topOldW = obj.width();
						topOldH = obj.height();
						topOldT = obj.css('top');
						obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth,height:allHeight,top:0},'fast');
						obj.find(".wrap_div").css({"height":allHeight, "width":allWidth});
						$('.wrap_div',obj).next().hide();
					};
				}else if(type == 'left'){
					var obj = leftBar;
					if(obj.hasClass('max_start')){
						var leftOldH = $("body").height() - header.height() - footer.height();
						obj.removeClass('max_start').animate({width:leftOldW,height:leftOldH,top:leftOldT},'fast',function(){$(this).css('z-index',1)});
						obj.find(".wrap_div").css({"height":leftOldH, "width":leftOldW});
						$('.wrap_div',obj).next().show();
					}else{
						leftOldW = obj.width();
						leftOldH = obj.height();
						leftOldT = obj.css('top');
						obj.find(".wrap_div").css({"height":allHeight, "width":allWidth});
						obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth,height:allHeight,top:0},'fast');
						$('.wrap_div',obj).next().hide();
					};
				}else if(type == 'right'){
					var obj = rightBar;
					if(obj.hasClass('max_start')){
						//还原
						var rightOldH = $("body").height() - header.height() - footer.height();
						obj.removeClass('max_start').animate({width:rightOldW,height:rightOldH,top:rightOldT},'fast',function(){$(this).css('z-index',1)});
						obj.find(".wrap_div").css({"height":rightOldH, "width":rightOldW});
						$('.wrap_div',obj).next().show();
					}else{
						//最大化
						rightOldW = obj.width();
						rightOldH = obj.height();
						rightOldT = obj.css('top');
						obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth,height:allHeight,top:0},'fast');
						obj.find(".wrap_div").css({"height":allHeight, "width":allWidth});
//这个是为了让拖拽的div隐藏，因为最大化之后，就不能进行拖拽了~~~~~下面这句话是为了做这个滴！！！
						$('.wrap_div',obj).next().hide();
					};
				}else if(type == 'bottom'){
					var obj = footer;
					if(obj.hasClass('max_start')){
						var bottomOldT = header.height() + contenter.height();
						var bottomOldW = $("body").width();
						obj.removeClass('max_start').animate({width:bottomOldW,height:bottomOldH,top:bottomOldT},'fast',function(){$(this).css('z-index',1)});
						obj.find(".wrap_div").css({"height":bottomOldH, "width":bottomOldW});
						$('.wrap_div',obj).next().show();
					}else{
						bottomOldW = obj.width();
						bottomOldH = obj.height();
						bottomOldT = obj.css('top');
						obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth,height:allHeight,top:0},'fast');
						obj.find(".wrap_div").css({"height":allHeight, "width":allWidth});
						$('.wrap_div',obj).next().hide();
					};
				};			
			}
			
			
			
			
			//给每一个拖拽中的a标签绑定click事件，执行的onOff方法
			topRevise.click(function(){
				//这里的$(this)代表的就是a标签，我分析的应该是没有错的！！！
				onOff($(this));
			});
			bottomRevise.click(function(){
				onOff($(this));
			});
			leftRevise.click(function(){
				onOff($(this));
			});
			rightRevise.click(function(){
				onOff($(this));
			});

			//显示隐藏总判断方法（通过判断a标签的id的值来确定执行哪个方法）
			function onOff(obj){
				if($(obj).attr('id')=='top_resizer'){//top开关
					topOnOff();
				}else if($(obj).attr('id')=='bottom_resizer'){//bottom开关
					bottomOnOff();
				}else if($(obj).attr('id')=='left_resizer'){//left开关
					leftOnOff();
				}else if($(obj).attr('id')=='right_resizer'){//right开关
					rightOnOff();
				};
			};
			
			//top隐藏/显示
			function topOnOff(){
				displayDiv = header.find('.wrap_div');
				//这个方法是用来隐藏.wrap_div中的内容
				displayDiv.toggle();
				//下面的if中的判断是判断a标签有无top_down这个class
				if(topRevise.hasClass('top_down')){
					topHeight = top.topHeight;
					forPane(); //使top回到初始化的状态
					topRevise.removeClass('top_down');
					topReviseAll.css({'top':topReviseTop}).removeClass('unbind').bind('mousedown',draggable);
				}else{
					topHeight = 6;
					forPane();
					topRevise.addClass('top_down');
					topReviseAll.css({'top':0}).addClass('unbind').unbind('mousedown');
				};
			};
			//bottom隐藏/显示
			function bottomOnOff(){
				displayDiv = footer.find('.wrap_div');
				//这个toggle()啥儿方法都没有，能干什么？？？？？				
				displayDiv.toggle();
				if(bottomRevise.hasClass('bottom_down')){
					bottomHeight = bottom.bottomHeight;
					forPane();
					bottomRevise.removeClass('bottom_down');
					bottomReviseAll.removeClass('unbind').bind('mousedown',draggable);
				}else{
					bottomHeight = 6;
					forPane();
					bottomRevise.addClass('bottom_down');
					bottomReviseAll.addClass('unbind').unbind('mousedown');
				};
			};
			//left隐藏/显示
			function leftOnOff(){
				displayDiv = leftBar.find('.wrap_div');
				displayDiv.toggle();
				if(thisBox.hasClass('left_open')){
					leftWidth = left.leftWidth;
					forPane();
					//dddddddddddddd
					thisBox.removeClass('left_open');
					leftReviseAll.css({'left':leftReviseL}).removeClass('unbind').bind('mousedown',draggable);
				}else{
					leftWidth = 6;
					forPane();
					thisBox.addClass('left_open');
					leftBar.wrapInner(wrapDiv);							
					$('div.wrap_div',leftBar).hide();
				};
			};
			//right隐藏/显示
			function rightOnOff(){
				displayDiv = rightBar.find('.wrap_div');
				displayDiv.toggle();
				if(rightRevise.hasClass('right_open')){
					rightWidth = right.rightWidth;
					forPane();
					rightRevise.removeClass('right_open');
					rightReviseAll.removeClass('unbind').bind('mousedown',draggable);
				}else{
					rightWidth = 6;
					forPane();
					rightRevise.addClass('right_open');
					rightReviseAll.addClass('unbind').unbind('mousedown');
				};
			};
			
			
			
			topReviseAll.bind('mousedown',draggable);
			leftReviseAll.bind('mousedown',draggable);
			rightReviseAll.bind('mousedown',draggable);
			bottomReviseAll.bind('mousedown',draggable);
			
			
			jQuery.extend({
				layout :{
					topOnOff : function(){//手动显示/隐藏top、bottom、left、right
						topOnOff();
					},
					bottomOnOff : function(){
						bottomOnOff();
					},
					leftOnOff : function(){
						leftOnOff();
					},
					rightOnOff : function(){
						rightOnOff();
					},
					allOnOff : function(){
						allOnOff();
					},
					setSize : function(opts){//手动改变top、bottom、left、right高宽
						var defaults = {
							left : 0,
							right : 0,
							top : 0,
							bottom : 0
						};
						$.extend(defaults,opts);
						var oldTopHeight = 0;
						var oldLeftWidth = 0;
						var oldRightWidth = 0;
						var oldBottomHeight = 0;
						if(defaults.left!=0 && defaults.right!=0 && defaults.top!=0 && defaults.bottom!=0){
							oldLeftWidth = leftWidth;
							oldTopHeight = topHeight;
							oldRightWidth = rightWidth;
							oldBottomHeight = bottomHeight;
							leftWidth = defaults.left;
							rightWidth = defaults.right;
							topHeight = defaults.top;
							bottomHeight = defaults.bottom;
							if(oldLeftWidth == leftWidth && oldRightWidth == rightWidth && oldTopHeight == topHeight && oldBottomHeight == bottomHeight){
								leftWidth = left.leftWidth;
								rightWidth = right.rightWidth;
								topHeight = top.topHeight;
								bottomHeight = bottom.bottomHeight;
							};
							forPane();
						}else if(defaults.left!=0){
							oldLeftWidth = leftWidth;
							leftWidth = defaults.left;
							if(oldLeftWidth == leftWidth){
								leftWidth = left.leftWidth;
							};
							forPane();
						}else if(defaults.right!=0){
							oldRightWidth = rightWidth;
							rightWidth = defaults.right;
							if(oldRightWidth == rightWidth){
								rightWidth = right.rightWidth;
							};
							forPane();
						}else if(defaults.top!=0){
							oldTopHeight = topHeight;
							topHeight = defaults.top;
							if(oldTopHeight == topHeight){
								topHeight = top.topHeight;
							};
							forPane();
						}else if(defaults.bottom!=0){
							oldBottomHeight = bottomHeight;
							bottomHeight = defaults.bottom;
							if(oldBottomHeight == bottomHeight){
								bottomHeight = bottom.bottomHeight;
							};
							forPane();
						};
					},

					dragSwitch : function(opts){//手动开关top、bottom、left、right拖拽
						var defaults = {
							allDrag : false,
							leftDrag : false,
							rightDrag : false,
							topDrag : false,
							bottomDrag : false
						};
						$.extend(defaults,opts);
						if(defaults.allDrag == true){
							if(topReviseAll.hasClass('unbind') || rightReviseAll.hasClass('unbind') || bottomReviseAll.hasClass('unbind') || leftReviseAll.hasClass('unbind')){
								topReviseAll.removeClass('unbind').bind('mousedown',draggable);
								bottomReviseAll.removeClass('unbind').bind('mousedown',draggable);
								leftReviseAll.removeClass('unbind').bind('mousedown',draggable);
								rightReviseAll.removeClass('unbind').bind('mousedown',draggable);
							}else{
								topReviseAll.addClass('unbind').unbind('mousedown');
								bottomReviseAll.addClass('unbind').unbind('mousedown');
								leftReviseAll.addClass('unbind').unbind('mousedown');
								rightReviseAll.addClass('unbind').unbind('mousedown');
							};
						}else if(defaults.topDrag == true){
							if(topReviseAll.hasClass('unbind')){
								topReviseAll.removeClass('unbind').bind('mousedown',draggable);
							}else{
								topReviseAll.addClass('unbind').unbind('mousedown');
							};
						}else if(defaults.bottomDrag == true){
							if(bottomReviseAll.hasClass('unbind')){
								bottomReviseAll.removeClass('unbind').bind('mousedown',draggable);
							}else{
								bottomReviseAll.addClass('unbind').unbind('mousedown');
							};
						}else if(defaults.leftDrag == true){
							if(leftReviseAll.hasClass('unbind')){
								leftReviseAll.removeClass('unbind').bind('mousedown',draggable);
							}else{
								leftReviseAll.addClass('unbind').unbind('mousedown');
							};
						}else if(defaults.rightDrag == true){
							if(rightReviseAll.hasClass('unbind')){
								rightReviseAll.removeClass('unbind').bind('mousedown',draggable);
							}else{
								rightReviseAll.addClass('unbind').unbind('mousedown');
							};
						}
					},
					windowMax : function(opts){//最大化窗口导出
						var defaults = {
							topMax : false,
							leftMax : false,
							rightMax : false,
							bottomMax : false,
							centerMax : false
						};
						$.extend(defaults,opts);
						if(defaults.topMax == true){
							maxStart('top');
						}else if(defaults.leftMax == true){
							maxStart('left');
						}else if(defaults.rightMax == true){
							maxStart('right');
						}else if(defaults.bottomMax == true){
							maxStart('bottom');
						}else if(defaults.centerMax == true){
							maxStart('center');
						}
					}
					//,,,,
				}
			});


		}
	})
})(jQuery);
