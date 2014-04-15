/*
 * Accordion控件
 *
 * Copyright (c) levone
 *
 * Date: 2011-12-19
 *
 */
(function ($) {
	 $.fn.extend({
		accordion: function(opts){
			var defaults = {
				collapsible: true, //折叠动画
				displayicon: true  //图标显示
			};
			var defaults = $.extend(defaults,opts);
			var accord = $(this);
			accord.addClass('accord');
			var accordTitle = accord.find('h3');
			var accordIcon = $('<span class="accord_icon"></span>');
			var accordCont = accordTitle.next();
			
			accordTitle.addClass('accord_title');
			var accordContHeight = accord.parent().height()-accordTitle.length*accordTitle.height()-2;
			
			accordCont.addClass('accord_cont');			
			
			if(defaults.displayicon==true){
				accordTitle.append(accordIcon);
				accordTitle.hover(function(){
					$(this).children('span').addClass('accord_hover')
				},function(){
					$(this).children('span').removeClass('accord_hover')
				});	
			};
			
			accordTitle.first().addClass('accord_select');
			accordCont.first().show();
			
			accordCont.height(accordContHeight);
			
			accordTitle.click(function(){
				if($(this).next().is(':hidden')){
					accordTitle.removeClass('accord_select');
					$(this).addClass('accord_select');
					if(defaults.collapsible){
						accordCont.not(':hidden').slideToggle('fast');
						$(this).next().slideToggle('fast');
					}else{
						accordCont.not(':hidden').hide();
						$(this).next().show();
					};					
				}else{
					if(defaults.collapsible){
						$(this).removeClass('accord_select').next().slideToggle('fast');
					}else{
						$(this).removeClass('accord_select').next().hide();
					};
				};
				
			});
			
			$(window).resize(function(){
				accordContHeight = accord.parent().height()-accordTitle.length*accordTitle.height();
				accordCont.height(accordContHeight);
			})
		}		
	})
})(jQuery);
