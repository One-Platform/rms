$.fn.extend({ 
	selectWidth : {
		defaultWidth:150
	},
	
	forms:function(opts){
		
	//text
	var inputText = $(":text");
	inputText.inputStyle();
	
	
	//textareas
	var textAreas = $("textarea");
	textAreas.textAreaStyle();
	
	//select
	$.extend(this.selectWidth,opts);
	var selects = $("select");
	selects.selectStyle();

	//checkbox
	var checkBox = $(":checkbox");
	checkBox.checkBoxStyle();
	
	//radio
	var radios = $(":radio");	
	radios.radioStyle();
	
	//button
	/*
	var buttons = $(":button");
	buttons.buttonStyle();
	
	var submits = $(":submit")
	submits.submitStyle();
	*/
},

	
	
	checkBoxStyle : function(){
//alert($(this).length); 通过这个可以发现，$(this)代表的不一定是一个，有可能是好几个，
//因为html页面中有3个checkbox，所以打印出来的是3
//checkbox这里是把原来的隐藏了，然后用的图片替换的，2种状态，2张图片
		$(this).hide();
		function clickCase(obj){
			if(obj.attr("src") == '../images/form/checkbox.gif'){
				obj.attr("src","../images/form/checkbox_ed.gif");
				obj.prev("input").attr("checked",true);
			}else{
				obj.attr("src","../images/form/checkbox.gif");
				obj.prev("input").attr("checked",false);
			};	
		}
		$(this).each(function(){
			//下面这个if else是根据checkbox的状态替换成图片
			if($(this).attr("checked") == true){
				$(this).after("<img src='../images/form/checkbox_ed.gif' class='checkbox' />");
			}else{
				$(this).after("<img src='../images/form/checkbox.gif' class='checkbox' />");
			};
			var obj = $(this).next();
			//为什么要有这个if/else呢？？？
			if($(this).parent().attr("tagName") == "LABEL") { 
				$(this).parent().click(function(){
					clickCase(obj);
					return false;
				});
			} else {
				obj.click(function(){
				   clickCase($(this));
				   return false;
				});
			}
		});
	},
	
	selectStyle : function() {
		var defaultWidth = this.selectWidth.defaultWidth
		$(this).css("display","none");
		$(this).each(function(i){
			//如果他没有设宽度，那么就用默认宽度
			if($(this).css("width") == "0px"){
				$(this).width(defaultWidth);
			}
			var id = $(this).attr("id");			
			if(!id || id == "") {
				$(this).attr("id","select" + "_" + i);
			}
//获取的是select展现出来的值,会把每个select中的选择出来的值展示出来
			var selected = $(this).find("option:selected").val();
			$(this).after("<div class='selectArea'></div>");
			var selectArea = $(this).next();
			var currentSelect = $(this);
			selectArea.css({"width":currentSelect.width() + 21 + 'px'}).prepend("<div class='selectLeft'></div>");
			if($(this).attr("disabled") == "disabled") {
				selectArea.find("div.selectLeft").before("<div class='selectRight disabled'></div>").after("<div class='selectMain'></div>");
			} else {
				selectArea.find("div.selectLeft").before("<div class='selectRight'></div>").after("<div class='selectMain'></div>");
			}
			selectArea.find("div.selectMain").css({"width":currentSelect.width() - 8 + 'px'}).prepend(selected);
			selectArea.prepend("<ul class='selectList'></ul>");
			selectArea.find("ul.selectList").css({"width":currentSelect.width() - 3 + 'px'});
			$(".selectArea").wrap("<div style='display: inline;'></div>")
			var opts = $(this).find("option");
			var selectList = selectArea.find("ul.selectList");
			for(var i=0, len=opts.length; i<len; i++){ 		
				selectList.append("<li>" + "<span>" + opts.eq(i).text() + "</span>" + "</li>");
			};
			var selectSpan = $("ul.selectList li span");
			selectSpan.hover(function(){
				selectSpan.removeClass();
				$(this).addClass("selectList_hover");
			})
			selectArea.find("div.selectRight").click(function(){
				if($(this).hasClass("disabled")) {
				}else{
					if(selectList.css("display")=='none'){
						$("li",selectList).show();
						selectArea.css("position","relative");
						selectList.slideDown(260);
					}else{
						selectList.toggle();
						selectArea.css("position","static");
					}	
				}
			});
			selectList.find("li span").click(function(){
				selectList.toggle();			
				selectArea.find("div.selectMain").replaceWith("<div class='selectMain'>" + $(this).text() + "</div>");				
				currentSelect.val($(this).text());
				selectArea.css("position", "static");
			});
			selectArea.mouseoutclick(function(){
				selectArea.css("position","static");
				selectList.hide();				  
			})
		});		
	},
	
	inputStyle : function() {
		$(this).before("<img src='../images/form/input_left.gif' class='inputMiddle'/>");
		$(this).after("<img src='../images/form/input_right.gif' class='inputMiddle'/>");
		$(this).addClass("inputText");
		$(this).focus(function(){
			$(this).prev().attr('src', "../images/form/input_left_xon.gif");
			$(this).next().attr('src', "../images/form/input_right_xon.gif");
			$(this).addClass("inputTextHover");
		});
		$(this).blur(function(){
			$(this).prev().attr('src',"../images/form/input_left.gif");
			$(this).next().attr('src',"../images/form/input_right.gif");
			$(this).removeClass();
			$(this).addClass("inputText");
		});	
			
	},
	
	textAreaStyle : function() {
		$(this).addClass("textareas");
		$(this).css({ "width": $(this).attr("cols") * 10 + 'px', "height": $(this).attr("rows") * 10 + 'px' });
		$(this).before("<div class='textareaTop'></div>");
		$(this).after("<div class='textareaBottom'></div>");
		$(".textareaTop").prepend("<img src='../images/form/txtarea_tl.gif' />");
		$(".textareaBottom").prepend("<img src='../images/form/txtarea_bl.gif' />");
		//通过设置class：textareaCon的背景颜色，出现在左右边框。
		$(this).wrap("<div class='textareaCon'></div>");
		$(".textareaCon").prepend("<div class='conLeft'></div>");
		
		$(".conLeft").css({"height": $(this).attr("rows") * 10  +  'px'});
		$(".textareaTop,.textareaCon,.textareaBottom").wrapAll("<div class='txtareaBox'></div>");
		$(".txtareaBox").css({"width":$(this).attr("cols") * 10 + 6 + 'px'});
		$(this).focus(function(){
			$(".textareaTop").addClass("topXon");
			$(".textareaBottom").addClass("bottomXon");
			$(".textareaCon").addClass("conXon");
			$(".conLeft").addClass("leftXon");
			$(".textareaTop img").attr('src','../images/form/txtarea_tl_xon.gif');
			$(".textareaBottom img").attr('src','../images/form/txtarea_bl_xon.gif');
		});
		$(this).blur(function(){
			$(".textareaTop").removeClass().addClass("textareaTop");
			$(".textareaBottom").removeClass().addClass("textareaBottom");
			$(".textareaCon").removeClass().addClass("textareaCon");
			$(".conLeft").removeClass().addClass("conLeft");
			$(".textareaTop img").attr('src','../images/form/txtarea_tl.gif');
			$(".textareaBottom img").attr('src','../images/form/txtarea_bl.gif');
		});
	},
	
	radioStyle : function() {
		$(this).hide();
		var radios = $(this);
		function clickRadis(obj){		
			var tagName = obj.attr("tagName");
			var radioObj= obj.prev();
			var name = radioObj.attr("name");
			var otherRadioes = radios.filter("[name='" + name + "']");
			otherRadioes.each(function(){
				$(this).next().attr("src","../images/form/radio.gif");
			});
			if(obj.attr("src")=='../images/form/radio.gif'){
				obj.attr("src","../images/form/radio_ed.gif");
				obj.prev("input").attr("checked",true);
			}else{
				obj.attr("src","../images/form/radio.gif");
				obj.prev("input").attr("checked",false);
			};
		}
		$(this).each(function(){
			if($(this).attr("checked")==true){
				$(this).after("<img src='../images/form/radio_ed.gif' class='radio' />");
			}else{
				$(this).after("<img src='../images/form/radio.gif' class='radio' />");
			};
			var obj = $(this).next();
			if($(this).parent().attr("tagName") == "LABEL") { 
				$(this).parent().click(function(){clickRadis(obj)});
			} else {
				obj.click(function(){clickRadis($(this))});
			};
		});	
	},
	
	buttonStyle : function() {
		$(this).addClass("btn");
		$(this).before("<img src='../images/form/btn_left.gif' class='btn_bor' />");
		$(this).after("<img src='../images/form/btn_right.gif' class='btn_bor' />");
	},
	
	submitStyle : function() {
		$(this).addClass("submit_btn");
		$(this).before("<img src='../images/form/submit_btn_left.gif' class='btn_bor' />");
		$(this).after("<img src='../images/form/submit_btn_right.gif' class='btn_bor' />");
	}
	
});