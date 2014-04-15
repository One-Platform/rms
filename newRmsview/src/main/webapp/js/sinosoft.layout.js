/*
 * Layout�ؼ�
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
					topHeight : 60,       //�߶�
					topSwitch : true,    //�϶����ܿ��أ�true-����false-��
					topMin : 30,         //�϶�����С�߶�
					topMax : 150,         //�϶������߶�
					topHide : true ,      //��ʾ���ؿ��أ�true-����false-��
					topMaximize : false      //�Ƿ���󻯣�true/false
				},
				left:{
					leftWidth : 200,   //�����
					leftSwitch : true, //�϶����ܿ��أ�true-����false-��
					leftMin : 100,  //�϶�����С���
					leftMax : 300, //�϶��������
					leftHide : true, //��ʾ���صĿ��أ�true-����false-��
					leftMaximize : false //�Ƿ���󻯣�true:��󻯣�false�������
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
			//�����±������Ϊ�˷�ֹIE6��7���ֿհ׵Ĺ�����
			$('html').css({'overflow':'hidden'});
			var header    = $("#layout_top");   //��
			var leftBar   = $("#layout_left");  //��
			var rightBar  = $("#layout_right"); //��
			var footer    = $("#layout_bottom");//��
			var contenter = $("#layout_center");//�У���������			
			
			var allWidth = thisBox.width();    //�������ǲ��ǿɱ�ģ����������ǿɱ�ģ����ݴ��ڵĴ�С���ı��
			var allHeight = thisBox.height();  //����߶��ǲ��ǿɱ�ģ�������߶��ǿɱ�ģ����ݴ��ڵĴ�С���ı��
			var topHeight = top.topHeight;     //ͷ���ĸ߶�(�������Ĭ�ϵ�)
			var leftWidth = left.leftWidth;    //���Ŀ��(�������Ĭ�ϵ�)
			var rightWidth = right.rightWidth; //�Ҳ�Ŀ�ȣ��������Ĭ�ϵģ�
			var bottomHeight = bottom.bottomHeight; //�ײ��ĸ߶ȣ��������Ĭ�ϵģ�
			var cenWidth = allWidth - leftWidth - rightWidth; //�м�div�Ŀ�ȣ�body�Ŀ��-�����-�Ҳ���
			var cenHeight = allHeight - topHeight - bottomHeight; //�м�div�ĸ߶ȣ�body�ĸ߶�-ͷ���ĸ߶�-�ײ��ĸ߶�
			var bottomTop = topHeight + cenHeight; //ͷ��div�ĸ߶�+�м��div�ĸ߶ȣ���Ϊ�˵ײ���top����׼����
			
			var topReviseAll = $("<div class='layout_top_resizer'><a href='javascript:void(0)' class='top_up' id='top_resizer'></a></div>"); //ͷ��������ק��div
			var topRevise = $('#top_resizer'); //div�е�a��ǩ���Ǹ�С���Ǻ�
			var topReviseTop = topHeight - 6; //ͷ����קdiv��top���Ե�ֵ
			
			var leftReviseAll = $("<div class='layout_left_resizer'><a href='javascript:void(0)' class='left_close' id='left_resizer'></a></div>"); //��������ק��div
			var leftRevise = $('#left_resizer'); //�����ק�е�a��ǩ���Ǹ�С���Ǻ�
			var leftReviseL = leftWidth - 4; //�����ק�ĵ�left����
			//����Ǹ�ʲô�����������������Ϊ�����Ҳ���Ǹ����Ǻ�׼���ģ�ͨ��padding-top�����Ǹ�С���ǽ��ж�λ
			var cenReviseP = cenHeight/2 - 25; 
			
			var rightReviseAll = $("<div class='layout_right_resizer'><a href='javascript:void(0)' class='right_close' id='right_resizer'></a></div>");//�Ҳ������ק��div
			var rightRevise = $('#right_resizer'); //�Ҳ���ק��С����
			
			var bottomReviseAll = $("<div class='layout_bottom_resizer'><a href='javascript:void(0)' class='bottom_up' id='bottom_resizer'></a></div>");//�ײ�������ק��div             
			var bottomRevise = $('#bottom_resizer'); //�ײ���ק��С����
			
//���⣺����������������������Ŀ�Ⱥ͸߶�Ҳ�ø������ϣ�����������ⲿ��div����Ȼ�����Ͽ��������������������ڻ�û��Ū�����Ⱥ͸߶ȣ���������Ӧ���Ƿ����ݵ�
			var wrapDiv = $('<div class="wrap_div"></div>'); 
			
			var displayDiv; //����Ǹ�ʲô�ģ�������.wrap_div����ʾ������
			
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
			
			
			//����жϾ���Ϊ���ж��Ƿ�����Ҫ��div
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
			
			//��ʼ������header��footer��right��left��center��Щdiv�õģ���layout�ĳ�ʼ��װ���õ�
			forPane();
			function forPane(){
				//ǰ���Ѿ���ʼ���ˣ�Ϊʲô���ڻ�Ҫ��ʼ��������Ϊ��window��resize��Ҳ�õ�����
				//��ʱbody�Ĵ�С�Ѿ��ı��ˣ�����Ҫ����������ֵ���¸�ֵ
				//���⣺���������������о�ò�Ʋ�����Ҫ�ˣ��Ȼ�ͷ�ٿ����ɣ�����������������������
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
					header.css({'width':allWidth,'height':topHeight}); //ͷ���ĸ߶ȺͿ�ȵĳ�ʼ��
					$('div.wrap_div',header).css({'width':allWidth,'height':topHeight});
					topReviseAll.css('top', topReviseTop); //ͷ������ק�Ĺ�����top��ֵ
//���ѽ�������⣺�������Ҿ��ĳ�ʼ����ʱ����Ӧ���ǽ�����if�ģ�Ϊʲô���ڽ���if�ˣ�����top.topSwitch��true�������û�д�ģ�����lengthӦ���Ǵ�İ�������
					//�Ҷ��ˣ���Ϊheader�����div�Ѿ���ҳ���ʼ����ʱ��д��htmlҳ�����ˣ�
					//����ֱ�� ��header�����topReviseAll�Ϳ����ˣ�������ifǰ�߻�û�����topReviseAll��topRevise
					//������if����֮��Ž���ģ��Ҳ٣������������ˣ��ղ�������~~~~~o(�s���t)o
					//ifֻ����ҳ���ʼ����ʱ��������ã�������ʱ�򶼲������á�
					if(top.topSwitch == true && topRevise.length <= 0){
//���⣺�������������±����if��else��ʲô��˼�������������� ��ʼ����ʱ���ߵ���else,��ʾtop.topHideΪtrue
						//�����false��ʱ�򣬰����ϵ��Ǹ�ͼ���classŪû�ˣ��Ͳ���������
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
					//�±����paddingTop��Ϊ�˷����м��Ǹ����Ǻ�
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
					//���⣺������������switch��revise�����������ص����⣬��ʵ�������if��elseΪʲô�������ã���Ϊ�˳�ʼ����ȷ���Ƿ��У���ͷ�ķ���ô��
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
			
			//�����ק�ƶ����
			var _move=false;
			var _x,_y;//�����ؼ����Ͻǵ����λ��
			
			function draggable(e){
				var thisDiv = $(this);
				var parentBox = $(this).parent();
				var x,y;
				_move=true;
				_x=e.pageX-parseInt(thisDiv.css("left"));
				_y=e.pageY-parseInt(thisDiv.css("top"));
				thisDiv.css({'background':'#000'});
				parentBox.css({'overflow':'visible','z-index':99});//�����ʼ�϶���͸����ʾ

				$(document).mousemove(function(e){
					if(_move){
						x = e.pageX - _x;//�ƶ�ʱ�������λ�ü���ؼ����Ͻǵľ���λ��
						y = e.pageY - _y;
						//��קλ��
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
					//�ж���ק�����С��Χ
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
					parentBox.fadeTo(20, 1).css({'overflow':'hidden','z-index':1});//�ɿ�����ֹͣ�ƶ����ָ��ɲ�͸��
					
					forPane();//�ؼ���λ��
					
					
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

			//������Ҳ�õ���forPane�������
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
			
			
			//���������ȥ��ʱ��
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
			
			//������󻯹���
//�����������ֻ�����˳�ʼ�������ģ�ֻ���ڳ�ʼ����ʱ��Ż�ȥִ��
			function topMaximize(type,obj){
				//��������Ͻǵ���󻯵�ͼ���html��ǩ
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
			//��󻯶��������ʵ����ִ�е�������������ϱߵķ����Ǹ������¼���
//���⣺�������������ڷ�����center����û��.wrap_div���div�ģ���ֱ������߷Ŷ�����~~~~~~~~
			function maxStart(type){
				if(type == 'center'){
					var obj = contenter;
					if(obj.hasClass('max_start')){
						//��ԭ
						//animate�����������������˶��ֵ���һ�������hide�����Ķ�������ô������ôִ�У����ֻ���ж����ֵ����ô���ᰴ���Ǹ�ֵȥִ�У�����һ��ʱ��֮�ڣ�ÿ��ֵ�ı�һ�Σ�������
						//�������������Ҽӵ�
						var centerOldW = $("body").width() - rightBar.width() - leftBar.width();
						var centerOldH = $("body").height() - header.height() - footer.height();
						obj.removeClass('max_start').animate({width:centerOldW,height:centerOldH,top:centerOldT,left:centerOldL},'fast',function(){$(this).css('z-index',1)});
					}else{
						//���
						//�±��������������֮ǰ��������ǰ��height��widht��top��left��������������������ԭ��ʱ������ʹ��
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
						//��ԭ
						obj.removeClass('max_start').animate({width:topOldW,height:topOldH,top:topOldT},'fast',function(){$(this).css('z-index',1)});
						obj.find(".wrap_div").css({"height":topOldH, "width":topOldW});
						$('.wrap_div',obj).next().show();
					}else{
						//���
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
						//��ԭ
						var rightOldH = $("body").height() - header.height() - footer.height();
						obj.removeClass('max_start').animate({width:rightOldW,height:rightOldH,top:rightOldT},'fast',function(){$(this).css('z-index',1)});
						obj.find(".wrap_div").css({"height":rightOldH, "width":rightOldW});
						$('.wrap_div',obj).next().show();
					}else{
						//���
						rightOldW = obj.width();
						rightOldH = obj.height();
						rightOldT = obj.css('top');
						obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth,height:allHeight,top:0},'fast');
						obj.find(".wrap_div").css({"height":allHeight, "width":allWidth});
//�����Ϊ������ק��div���أ���Ϊ���֮�󣬾Ͳ��ܽ�����ק��~~~~~������仰��Ϊ��������Σ�����
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
			
			
			
			
			//��ÿһ����ק�е�a��ǩ��click�¼���ִ�е�onOff����
			topRevise.click(function(){
				//�����$(this)����ľ���a��ǩ���ҷ�����Ӧ����û�д�ģ�����
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

			//��ʾ�������жϷ�����ͨ���ж�a��ǩ��id��ֵ��ȷ��ִ���ĸ�������
			function onOff(obj){
				if($(obj).attr('id')=='top_resizer'){//top����
					topOnOff();
				}else if($(obj).attr('id')=='bottom_resizer'){//bottom����
					bottomOnOff();
				}else if($(obj).attr('id')=='left_resizer'){//left����
					leftOnOff();
				}else if($(obj).attr('id')=='right_resizer'){//right����
					rightOnOff();
				};
			};
			
			//top����/��ʾ
			function topOnOff(){
				displayDiv = header.find('.wrap_div');
				//�����������������.wrap_div�е�����
				displayDiv.toggle();
				//�����if�е��ж����ж�a��ǩ����top_down���class
				if(topRevise.hasClass('top_down')){
					topHeight = top.topHeight;
					forPane(); //ʹtop�ص���ʼ����״̬
					topRevise.removeClass('top_down');
					topReviseAll.css({'top':topReviseTop}).removeClass('unbind').bind('mousedown',draggable);
				}else{
					topHeight = 6;
					forPane();
					topRevise.addClass('top_down');
					topReviseAll.css({'top':0}).addClass('unbind').unbind('mousedown');
				};
			};
			//bottom����/��ʾ
			function bottomOnOff(){
				displayDiv = footer.find('.wrap_div');
				//���toggle()ɶ��������û�У��ܸ�ʲô����������				
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
			//left����/��ʾ
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
			//right����/��ʾ
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
					topOnOff : function(){//�ֶ���ʾ/����top��bottom��left��right
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
					setSize : function(opts){//�ֶ��ı�top��bottom��left��right�߿�
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

					dragSwitch : function(opts){//�ֶ�����top��bottom��left��right��ק
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
					windowMax : function(opts){//��󻯴��ڵ���
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
