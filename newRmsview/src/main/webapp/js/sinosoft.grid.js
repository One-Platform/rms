/*!
 * grid
 * levone
 * Mail:zuoliwen@sinosoft.com.cn
 * Date: 2012-07-03
 */
(function ($) {
	 $.fn.extend({
		Grid: function(opts){
			var defaults = {
				url       : "grid.json" ,  
				dataType  : "json" ,        
				height    : "auto" ,     
				width     : "auto",
				loadText  : "数据加载中…",
				colums    : "", 
				rowNum    : "auto",  
				number    : true,     
				hasSpan   : false,     
				multiselect: true,    
				autoColWidth: true, 
				radioSelect: false,  
				sorts     : false,	  
				colDisplay: true,     
				draggable : true,		
				sequence  : true, 
				clickSelect :true,
				pages     : {
					pager    : true ,  
					renew    : false , 
					paging   : true ,  
					goPage   : true ,  
					info     : true    
				},
				callback : {
					beforeDelData : function(gridNode){},
					afterDelData: function(gridNode){return false;}
				}
			};			
			var defaults = $.extend(defaults,opts);		
			var g = $(this);  
			var $gBox = $('<div class="grid_box"></div>');
			
			var $gHeader = $('<div class="grid_head"></div>');
			var $gHeadBox = $('<div class="head_box"></div>');

			var $gHeadColumn = $('<div class="gird_head_column"></div>');

			var $needle = $('<div class="needle"></div>');

			var $gView = $('<div class="grid_view"></div>');
			var $load = $('<div class="loading"><span>' + defaults.loadText + '</span></div>');
			
			var colAttributes = {};
			colAttributes.widths = [];
			colAttributes.aligns = [];
			colAttributes.colors = [];
	
			var widths = [],aligns = [],colors = [],allCh = [],hids = [];
			var $checkBox = $('<input type="checkbox" value="" name="all_sec" class="chex" />');
			var $gNumber = '';

			var adaptive = 0,str = 0,end = defaults.rowNum,pB = 1;

			var onOff,
				pageUnclik,
				disb = true,
				trId,
				oW,
				nW,
				tI,
				rows,
				total,
				pFirst,
				pPrev,
				pNext,
				pLast,
				pNub,
				pB = 1,
				pSel,
				ps,
				_stop;
			var _move=false;
			var _x;

			var _data;

			g.append($gBox);

			var $gW = $gBox.width();

			autoWidth(); 
			
			createGridHead();

			function createGridHead(){

				var col = defaults.colums;

				if(defaults.multiselect) {
					$gHeadColumn.addClass("multiple").append($checkBox);
					$gHeader.append($gHeadColumn);
				};

				if(defaults.number){
					$gNumber = $('<div class="gird_head_column number"><span class="number">序号</span></div>');
					$gHeader.append($gNumber);
				};
				for(var i = 0; i < col.length; i++){

					if(!defaults.sorts && !defaults.colDisplay) {
						$gHeadColumn = $('<div class="gird_head_column cols col_' + i +'"></div>');
					}else{
						$gHeadColumn = $('<div class="gird_head_column cols col_' + i +'"><div class="grid_menu"></div></div>');
					};					
					$gHeadColumn.append("<span>"+ col[i].text +"</span>");
					if(col[i].id){
						$gHeadColumn.attr("id", col[i].id);
					}else{
						alert("id不能为空");
						return false;
					};
					if(col[i].name){
						$gHeadColumn.attr("name",col[i].name);				
					}else{
						alert("name不能为空");
						return false;
					};
					if(col[i].index){ 
						$gHeadColumn.attr("index",col[i].index);
					};

					if(col[i].width){
						$gHeadColumn.css({"width":col[i].width});

						colAttributes.widths.push(col[i].width);
					}else{
						$gHeadColumn.css({"width":adaptive});
						colAttributes.widths.push(adaptive);
					};
			
					if(col[i].align){
						colAttributes.aligns.push(col[i].align);
					}else{
						colAttributes.aligns.push("left");
					};
					
					if(col[i].color){
						colAttributes.colors.push(col[i].color);
					}else{
						colAttributes.colors.push("inherit");
					};

					if(defaults.pages.goPage || defaults.pages.paging){
						formatHids(i);
					};
					$gHeader.append($gHeadColumn);
				
					$gHeadColumn
						.bind({"mouseover":changeNeedle,"mouseout":unHover})
						.children("div").bind("click", colMenuOpt);
					if(defaults.sequence){
						$gHeadColumn.bind("click", colSequence);
					}
					if(defaults.draggable){
						$gHeadColumn.bind("mousedown",moveThead);
					};
				};
				$gHeader.append($needle);
				$gBox.append($gHeader);			
				if(defaults.sorts || defaults.colDisplay){
					greatMenu();
					$needle.bind("mousedown", dragCol);
				};
				$("div.gird_head_column",$gHeader).wrapAll($gHeadBox);
			};
			
			function formatHids(i){
				var obj = new HideInfo('col_' + i, false);
				hids.push(obj);
			};
			function HideInfo(name,isHide){
				this.name = name;
				this.isHide = isHide;
				this.getInfo = function(){
					return this.name + ": " + this.isHide;
				};
			};

			function colMenuOpt(e){			
				var $gM = $gBox.children('div.grid_head_menu');
			
				$('div.grid_head_menu', $gBox).hide();
			
				if($.browser.msie && ($.browser.version == "7.0")){
					$('div.grid_head_menu:last',$gBox).css('border-width','0')
				}

				var offset = $(this).offset();
				var nH = $(this).height();
				var pHead = $(this).parent();

				var sm = $gW + g.offset().left - offset.left < $gM.width();

				pHead
					.siblings().removeClass('select')
					.children('div.grid_menu').removeClass('clk');
				$(this).addClass('clk');
				pHead.addClass('select');
				
				if(sm){				
					$gM.css({'left':offset.left - g.offset().left - $gM.width() + $(this).width(),'top':nH}).show();
					
					onOff = false;
				}else{				
					$gM.css({'left':offset.left - g.offset().left,'top':nH}).show();
					onOff = true;
				};
				$(document).bind("click",bodyClick);
				e.stopPropagation();
				var part = $('div.grid_head_menu > ul > li.parting',$gBox);
				
				part.bind('mouseover',{part:part,onOff:onOff},subMopt);
			};
			
			
			function subMopt(e){
				if($.browser.msie&&($.browser.version == "7.0")){
					$('div.grid_head_menu:last',$gBox).css('border-width','1px')
				};
				var part = e.data.part;
				var onOff = e.data.onOff;
				var sM = $('div.grid_head_menu > div.grid_head_menu',$gBox);
					if(onOff){
						sM.css({'top':part.position().top,'left':part.position().left + 126}).show();
					}else{
						sM.css({'top':part.position().top,'left':-128}).show();
					};
					$('li',sM).unbind('click');
					$('li',sM).bind('click',subClick);
					return false;
			};
			
			function subClick(e){
				e.stopPropagation();
				var check = $('div > label',$(this));
				var index = $(this).index();
				var head = $("div.col_" + index,$gHeader);
				var hI = head.index();
				var bw = $("div.gird_head_column[col]:visible",$gHeader).length;
				var tb = $("table",$gView);
				var tr = $("table > tbody > tr",$gView);
				var ul = check.parents('ul');
				var oI = hI;				
				if(defaults.multiselect){
					oI = oI - 1;
				};
				if(defaults.number){
					oI = oI - 1;
				};
				var obj = hids[oI];
				if(check.hasClass('chected')){
					if(disb){
						var tw = tb.width() - head.width();
						check.removeClass('chected');
						head.hide();
						tb.width(tw - bw);
						tr.each(function(){
							$("td",$(this)).eq(hI).hide();
						});
						obj.isHide=true;			
					}
				}else{
					var tw = tb.width() + head.width();
					check.addClass('chected')
					$('label.disabled',ul).removeClass('disabled');
					head.show();
					tb.width(tw);
					tr.each(function(){
						$("td",$(this)).eq(hI).show();
					});
					disb = true;
					obj.isHide=false;
				};
				var cl = $("label.chected",ul).length;
				if(cl == 1){
					var last = $('label.chected',ul);
					last.addClass('disabled');
					disb = false;					
				};	
			}
			
			
			function bodyClick(e){
				var $gM = $('div.grid_head_menu',$gBox);
				var pHead = $('div.select',$gHeader);
				pHead
					.removeClass('select')
					.children('div.grid_menu').removeClass('clk');
				$gM.hide();
				if($.browser.msie&&($.browser.version == "7.0")){
					$gM.last().css('border-width','0')
				};
				$(document).unbind("click",bodyClick);
				return false
			};
			
			
			$gHeader.width($gW);

			if(defaults.height != 'auto'){
				$gView.height(defaults.height - $gHeader.height() - 1);
			};
			if(defaults.width == 'auto'){
				$gBox.width($gW);
			}else{
				$gBox.width(defaults.width);
			};
			
			$gBox.append($gView);
			if(_data){
				loading();
				initGrid(_data,str,end,colAttributes);
				_stop = _data.rows.length;
			}else{
				createData();
			}
			$checkBox.bind("click",allClick);
			$gView.scroll(function(){
				var sL = $gView.scrollLeft();
				$gHeader.css('margin-left',-sL);
			});
			if(defaults.pager){
				greadPage();
			}
			$gView.width($gW).height($gView.height());
			
			function createData(){
				var url = defaults.url;
				var dataType = defaults.dataType;
				var type = defaults.type;
				$.ajax({
					url: url,
					dataType : dataType,
					type : type,
					data:"pageNo=1" + "&rowNum=" + defaults.rowNum,
					async : false,
					processData : false,
					beforeSend : function(){loading()},
					error : function (XMLHttpRequest,errorThrown) {
						alert("数据加载出错！" + errorThrown);
					},
					success: function(data){
						initGrid(data, str, end, colAttributes);
						_data = data;
						_stop = _data.rows.length;
					}
				});
			};
			
			function initGrid(data, startParam, endParam, colParams) {
				readJson(data, startParam, endParam, colParams);
				$load.hide();
				$('tr',$gView).hover(function(){
					$(this).addClass('hover');
				},function(){
					$(this).removeClass('hover');
				});
				$('tr',$gView).bind('click',trClick);
			};
			
			
			function readJson(data, startParam, endParam, colParams){
				var gTable = '<table border="0" cellspacing="0" cellpadding="0">';		
				rows = data.rows; 			
				total = data.total; 
				var colLen = defaults.colums.length; 		
				var allW = 0; 
				gTable = gTable + '<tr class="th_rows">';
				if(defaults.multiselect) {
					gTable = gTable + '<td style="width:22px">&nbsp;</td>';
					allW += 22;
				};
				if(defaults.number) {
					gTable = gTable + '<td style="width:30px">&nbsp;</td>';
					allW += 30;
				};
				for(i = 0; i < colLen; i++){
					gTable  = gTable + '<td col="col_' + i + '" style="width:' + colParams.widths[i] + 'px">&nbsp;</td>';
					allW += Math.abs(colParams.widths[i]);
				};
				gTable  = gTable + '</tr>';
				
				if(rows.length < endParam){
					endParam = rows.length;
					pageUnclik = true;
				};
				for(j = startParam; j < endParam; j++){
					var cell = rows[j].cell;
					if(cell.length > colLen) {
						if(rows[j].cell[colLen] == "true") {
					//		alert("if");
							gTable = gTable + '<tr id="row_' + rows[j].id + '" class="select">';
						} else {
					//		alert("else");
							gTable = gTable + '<tr id="row_' + rows[j].id + '">';
						}
					} else {
						gTable = gTable + '<tr id="row_' + rows[j].id + '">'
					}
					allCh.push(false);
					if(defaults.multiselect){
						if(rows[j].cell[colLen] == "true") {
							gTable  = gTable + '<td class="multiple"><input name="g_check" type="checkbox" checked="checked" value="" /></td>';
						} else {
							gTable  = gTable + '<td class="multiple"><input name="g_check" type="checkbox" value="" /></td>';
							
						}
					} 
					if(defaults.number){
						var tempIndex = j + 1 + str;
						gTable = gTable + '<td class="number"><span title="' + tempIndex + '">' + tempIndex + '</span></td>' ;
					};
					for(i = 0; i < colLen; i++){
						var text = cell[i];
						if(defaults.hasSpan == true) {
							gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i] + ';color:' + colParams.colors[i] + '"><span title="' + text + '">' + text + '</span></td>';
						} else {
							gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i] + ';padding:0 3px;color:' + colParams.colors[i] + '">' + text + '</td>';
						}
					}
					gTable  = gTable + '</tr>';
				};
				gTable  = gTable + '</table>';
				$gView.append(gTable);
				$('tr:odd',$gView).addClass('odd');
				$('table',$gView).width(allW);
			};
			
			
			function isAllSelected(){
				for(var i = 0, len = allCh.length; i < len; i++){
					if(allCh[i] == false){
						return false
					};
				};
				return true;
			};
			
			
			function greadPage(){
				var $gPage = $('<div class="grid_page"><div class="grid_page_box"></div></div>');
				var $pBox = $('div.grid_page_box',$gPage);
				var $p = $('<div class="prick"></div>');
				ps = Math.ceil(_data.total/defaults.rowNum);
				if(defaults.pages.goPage) {
					var goTo = '<div class="grid_entry"><select name="grid_pages">';
					for(i = 1; i < ps + 1; i++){
						goTo = goTo + '<option>' + i + '</option>';
					};
					goTo = goTo + '</select></div>';
					$pBox.append(goTo,'<div class="prick"></div>');
					pSel = $('select', $pBox);
					pSel.bind('change', {b:'select'}, jampPage);
				};
				if(defaults.pages.paging){
					var $pagings = $('<span><b class="grid_page_fist unclick"></b></span><span><b class="grid_page_prev unclick"></b></span><div class="prick"></div><div class="grid_note"><input name="" type="text" class="page_nub" value="1" />页 共 '+ ps +' 页</div><div class="prick"></div><span><b class="grid_page_next"></b></span><span><b class="grid_page_last"></b></span><div class="prick"></div>');
					pFirst = $('b.grid_page_fist',$pagings);
					pPrev = $('b.grid_page_prev',$pagings); 
					pNub = $('input.page_nub',$pagings);  //页面输入框
					pNext = $('b.grid_page_next', $pagings); //跳转到表格的下一页
					pLast = $('b.grid_page_last', $pagings); //跳转到表格的最后一页
					
					
					if(pageUnclik){
						pNext.addClass('unclick');
						pLast.addClass('unclick');
					};
					$pBox.append($pagings);
					pFirst.bind("click",{b:'first'},pageFn);
					pPrev.bind("click",{b:'prev'},pageFn);
					pNext.bind("click",{b:'next'},pageFn);
					pLast.bind("click",{b:'last'},pageFn);
					
					pNub.bind('keyup',{b:'nub'},jampPage); 
				};
				if(defaults.pages.renew){
					var $refresh = $('<span title="刷新"><b class="grid_refresh"></b></span>');
					$pBox.append($refresh,$p);
					$refresh.bind('click',pageRefresh);
				};
				if(defaults.pages.info){
					var $info = $('<div class="grid_info">每页显示 '+ defaults.rowNum +' 条数据 - 共 '+ total +' 条数据</div>');
					$pBox.append($info);
				};
				$gBox.append($gPage);
			};
			
			function jampPage(e){
				var path = e.data.b,
					old = pB,
					on = 'unclick',
					un = !$(this).hasClass(on), 
					
					lHas = pLast.hasClass(on), 
					
					fHas = pFirst.hasClass(on);
				if(path == 'nub'){			
					if(e.keyCode == 13){
						var tempNum = pNub.val();
						var ok = tempNum >= 1 && tempNum <= ps;
						if(ok){
							pB = tempNum;
						};
						str = (pB - 1) * defaults.rowNum;
						end = pB * defaults.rowNum;
						if(ok){
							if(tempNum == 1){
								if(!fHas){
									pFirst.addClass(on);
									pPrev.addClass(on);
								};
								if(lHas){
									pNext.removeClass(on);
									pLast.removeClass(on);
								};
							}else if(tempNum == ps){
								if(end > _stop)
								end = _stop;
								if(!lHas){
									pNext.addClass(on);
									pLast.addClass(on);
								};
								if(fHas){
									pFirst.removeClass(on);
									pPrev.removeClass(on);
								};
							}else{
								 if(lHas || fHas){
									pNext.removeClass(on);
									pLast.removeClass(on);
									pFirst.removeClass(on);
									pPrev.removeClass(on);
								};
							};
						}
						$(this).blur();
					}else{
						return false;
					};					
				}else if(path == 'select'){
				//pB代表你选择的select的值				
					pB = $(this).val();
					if(pB == 1){
						if(!fHas){
							pFirst.addClass(on);
							pPrev.addClass(on);
						};
						if(lHas){
							pNext.removeClass(on);
							pLast.removeClass(on);
						};
					}else if(pB == ps){
						//进入这个if表示当前select选择的是最后一页
						if(end > _stop)
							end = _stop;
						if(!lHas){
							pNext.addClass(on);
							pLast.addClass(on);
						};
						if(fHas){
							pFirst.removeClass(on);
							pPrev.removeClass(on);
						};
					}else{
						 if(lHas || fHas){
							pNext.removeClass(on);
							pLast.removeClass(on);
							pFirst.removeClass(on);
							pPrev.removeClass(on);
						};
					};
					str = (pB - 1) * defaults.rowNum;
					end = pB * defaults.rowNum;
					if(end > _stop)
					end = _stop;
				};
				rePage();
			};
			function pageFn(e){
				var path = e.data.b,
					on = 'unclick',
					un = !$(this).hasClass(on),
					lHas = pLast.hasClass(on),
					fHas = pFirst.hasClass(on);
				if(path == 'first' && un){
					if(!fHas){
						pFirst.addClass(on);
						pPrev.addClass(on);
						pNext.removeClass(on);
						pLast.removeClass(on);
					};
					pB = 1;
					str = 0;
					end = defaults.rowNum;
				}else if(path == 'prev' && un){
					if(lHas){
						pNext.removeClass(on);
						pLast.removeClass(on);
					};
					pB --;
					if(pB <= 1){
						pB = 1;
						pFirst.addClass(on);
						pPrev.addClass(on);
					};
					str = (pB - 1) * defaults.rowNum;
					end = pB * defaults.rowNum;
				}else if(path == 'next' && un){
					if(!lHas){
						pFirst.removeClass(on);
						pPrev.removeClass(on);
					};
					pB ++;
					if(pB >= ps){
						pB = ps;
						pNext.addClass(on);
						pLast.addClass(on);
					};
					str = (pB - 1) * defaults.rowNum;					
					end = pB * defaults.rowNum;
					if(end > _stop)
						end = _stop;
				}else if(path == 'last' && un){
					if(!lHas){
						pNext.addClass(on);
						pLast.addClass(on);
						pFirst.removeClass(on);
						pPrev.removeClass(on);
					};
					pB = ps;
					str = (pB - 1) * defaults.rowNum;
					end = _stop;				
				}else{
					return false;
				};
				rePage();
			};
			function cancelCheck(){
				if($(".chex").attr("checked")) {
					$(".chex").attr("checked", false);
				}
			}
			
			function rePage(){
				$.ajax({
					url: defaults.url,
					dataType: "json",
					type: "POST",
					data:"pageNo="+pB + "&rowNum=" + defaults.rowNum,
					async:false,
					processData: false,
					beforeSend : function(){
						
					},
					error: function (XMLHttpRequest, errorThrown) {
						alert("数据加载出错！" + errorThrown);
					},
					success: function(myData){
						_data = myData;
						if(_data.total < pB * defaults.rowNum) {
							
							end = _data.total;
						} else {
							end = pB * defaults.rowNum;
						}
						cancelCheck();
					}
				});
				$('table', $gView).remove();
				loading();
				if(hids.length > 0){
					hideCol();
				}else{
					initGrid(_data, str, end, colAttributes);
				};				
				if(defaults.pages.paging)
				pNub.val(pB);
				if(defaults.pages.goPage)
				pSel.val(pB);
			};
			function hideCol(){
				var oldTabW = $gView.width();			
				var dataTemp = {};
				var dataRows = _data.rows;				
				dataTemp.rows = [];
				var colAttributesTemp = {};
				colAttributesTemp.widths = [];
				colAttributesTemp.aligns = [];
				colAttributesTemp.colors = [];
				for(var i=0, k=0; i < dataRows.length; i++, k++){
					var rowData = dataRows[i];
					var rowCells = rowData.cell;
					var rowCellsTemp = [];
					for(var j = 0, len = hids.length; j<len; j++){
						var ok = hids[j];
						var index = ok.name.substring(ok.name.indexOf("_") + 1);
						rowCellsTemp.push(rowCells[index]);
						if(k == 0){
							colAttributesTemp.widths[j] = colAttributes.widths[index];
							colAttributesTemp.aligns[j] = colAttributes.aligns[index];
							colAttributesTemp.colors[j] = colAttributes.colors[index];
						};
					};
					dataTemp.rows[k] = {};
       				dataTemp.rows[k].cell = rowCellsTemp;
				};
				initGrid(_data,0,end - str,colAttributesTemp);
				var tr = $("table > tbody > tr",$gView);
				var hideWidth = 0,tdW = 0;
				tr.each(function(j){
					for(i = 0; i < hids.length; i++){
						var the = i;
						if(defaults.multiselect){
							the = the + 1;
						};
						if(defaults.number){
							the = the + 1;
						};
						var hides = hids[i].isHide;
						var index = hids[i].index;
						var td;
						if(hides){
							td = $("td",$(this)).eq(the);
							tdW = td.width();
							td.hide();							
						};
						if(j == 0){
							hideWidth = hideWidth + tdW;
						};
					};				
				});
				var newTabW = $gHeader.width();
				$('table',$gView).width(newTabW);
			};
			function pageRefresh(){
				var sM = $('div.grid_head_menu > div.grid_head_menu',$gBox).find('label');
				formatHead();				
				$('table',$gView).remove();
				loading();
				if(defaults.pages.goPage || defaults.pages.paging){
					var col = defaults.colums;
					hids = [];
					for(var i = 0; i < col.length; i++) {						
						formatHids(i);
					};
				};			
				initGrid(_data, 0, end-str, colAttributes);
				sM.each(function(){
					if(!$(this).hasClass('chected')){
						$(this).addClass('chected');
					};
				});				
			};
			function formatHead(){
				var cols = $('div.cols',$gHeader);
				var hidTh = $('div.cols:hidden',$gHeader);
				for(i = 0; i < cols.length; i++){
					var wid = colAttributes.widths[i];
					cols.eq(i).width(wid);
				};
				if(hidTh.length > 0){
					hidTh.show();
				};
				if($checkBox.attr("checked")){
					$checkBox.attr("checked", false);
				};
			};
			function trClick(){
				if(defaults.multiselect){
					var cInpt = $("td:first > input",$(this));
					var theI = $(this).index() - 1;
					if(defaults.radioSelect){
						$(this).siblings().removeClass("select");
					};
					if($(this).hasClass('select')){
						$(this).removeClass('select');
						if(defaults.clickSelect){
							cInpt.attr("checked",false);
						}
						allCh[theI] = false;
					}else{
						$(this).addClass('select');
						if(defaults.clickSelect){
							cInpt.attr("checked",true);
						}
						allCh[theI] = true;
						trId = $(this).attr("id");
					};
					if(isAllSelected()){
						$checkBox.attr("checked",true);
					}else{
						$checkBox.attr("checked",false);
					};
				}else{
					if($(this).hasClass('select')){
						$(this).removeClass('select');
					}else{
						$(this).siblings().removeClass('select');
						$(this).addClass('select');
					};
				};
			};
			function allCheckedChange(isChecked){
				for(var i = 0, len = allCh.length; i < len; i++){
					allCh[i] = isChecked;
				};
			};
			function allClick(){
				var trs = $("table tr",$gView);
				var cInpt = $("table td input[name=g_check]",$gView);
				if($(this).attr("checked")=="checked"){
					cInpt.attr("checked",true);
					trs.addClass('select');
					allCheckedChange(true);
				}else{
					cInpt.attr("checked",false);
					trs.removeClass('select');
					allCheckedChange(false);
				}
			};

			//加载动画
			function loading(){
				if($('div.loading',$gView).length > 0){
					$load.show();
				}else{
					if(defaults.height){
						$load.css("padding-top",defaults.height/2 - 20)
					}
					$gView.append($load);
				};			
			}
			
			function colSequence(e){
				if(e.data != null) {
					var path = e.data.b;
					var tem = judgeMenu(path);
					if(tem == false) {
						return true;
					}
				}		
				var isAsc;
				var sorts = [];
				var way = $(this).children("span");
				var ways = $(this).siblings().find("span");
				var listData = $('table > tbody > tr:not(:first)',$gView);
				
				var tdIndex = $(this).index();			
				listData.each(function(i){
					var colsText = $(this).children('td').eq(tdIndex).text();
					var obj = new sortInfo(i, colsText);
					sorts.push(obj);
				});				
				ways.removeClass();
				
				if(way.parent().text() != "升序排序" && way.parent().text() != "降序排序") {
					if(way.hasClass('up')){			
						way.removeClass('up').addClass('down');
					}else{				
						way.removeClass('down').addClass('up');
					}
				}
				
				if(way.hasClass('down')){
					isAsc = true;
				}else{
					isAsc = false;
				};
				sortFn(sorts,isAsc);				
				var sortedTrs = "";
				$('table > tbody > tr:not(:first)',$gView).remove();
				listData.removeClass("odd");
				for(var i=0, len=sorts.length; i<len; i++){
					var j = i + 1 & 1;
					var index = sorts[i].index;
					if(defaults.number){
						$('td.number', listData.get(index))
							.attr("title",i + 1)
							.children('span').text(i + 1);
					};
					if(j == 0){
						listData.eq(index).addClass("odd");
					}
					sortedTrs = sortedTrs + listData.get(index).outerHTML;
				};			
				listData.remove();					
				$('table > tbody',$gView).append(sortedTrs);	
			};
			var upFlag = false;
			var downFlag = false;
			function judgeMenu(tem){
				if(tem == "up") {
					if(!upFlag) {
						upFlag = true;
						downFlag = false;
						return true;
					}
					return false;
				} else {
					if(!downFlag) {
						downFlag = true;
						upFlag = false;
						return true;
					}
					return false;
				}
			}
			
			//快速排序
			function sortFn(arr,isAsc){
				return quickSort(arr, 0, arr.length - 1);
				function quickSort(arr,l,r){           
					if(l<r){        
						var mid=arr[parseInt((l+r)/2)].text,
							i=l-1,
							j=r+1;        
						while(true){
							if(isAsc){
								while(arr[++i].text < mid);
								while(arr[--j].text > mid);
							}else{
								while(arr[++i].text > mid);
								while(arr[--j].text < mid);
							}
							if(i>=j)break;
							var temp=arr[i];
							arr[i]=arr[j];
							arr[j]=temp;
						}      
						quickSort(arr,l,i-1);
						quickSort(arr,j+1,r);
					}
					return arr;
				};
			};
			
			//siblings找的是同辈的元素，挺好用的，用到它好几次了。
			function changeNeedle(){
				$(this)
					.addClass('hover')
					.siblings().removeClass('th_change');
				var offset = $(this).offset();
				var mL = parseInt($gHeader.css("margin-left"));
				var x = offset.left - g.offset().left + $(this).width() + Math.abs(mL) - 5;
				var y = offset.top - g.offset().top - 1;
				$needle.css({'left':x,'top':y});
				$(this).addClass('th_change');
			};
			function unHover(){
				$(this).removeClass('hover')
			};
			//列排序
			function moveThead(e){
				var $T = $(this);
				$T.siblings(".cols").addClass("can");
				$T.removeClass("can next");
				$T.nextAll().addClass("next");
				var oldL = $(this).offset().left;
				var oldT = $(this).offset().top;
				var $Tt = $('<div class="g_indicator no">' + $T.text() + '</div>');
				var $P = $('<div class="g_pointer"></div>');
				$gBox.append($Tt,$P);
				_move = true;
				_x = $T.offset().left;
				$(document).mousemove(function(e){
					var $e = $(e.target);
					var has = $e.hasClass("can");
					var par = $e.parent().hasClass("can");
					var nex = $e.hasClass("next");
					var pne = $e.parent().hasClass("next");
					var l,t;
					if(_move){
						x = e.clientX;
						y = e.clientY + 20;
						$Tt.css({"left":x,"top":y});
						if(nex || pne){
							$Tt.removeClass("no");
							if(nex){
								l = $e.offset().left +  $e.width() - g.offset().left - 8;
								t = $e.height();
							}else{
								l = $e.parent().offset().left +  $e.parent().width() - g.offset().left - 8;
								t = $e.parent().height();
							};
							$P.css({"left":l,"top":t});
						}else if(has || par){
							$Tt.removeClass("no");
							if(has){
								l = $e.offset().left - g.offset().left - 8;
								t = $e.height();
							}else{
								l = $e.parent().offset().left - g.offset().left - 8;
								t = $e.parent().height();
							}							
							$P.css({"left":l,"top":t});
						}else{
							$Tt.addClass("no");
							$P.attr("style","")
						};
					};
					return false;
				}).mouseup(function(e){
					_move=false;
					$(document).unbind('mousemove mouseup');
					if($Tt.hasClass("no")){
						$Tt.animate({top:oldT,left:oldL,opacity:0},400).queue(function(){
							$Tt.remove();
							$P.remove();
						});
					}else{
						var point = $(e.target);
						var oCol = $T.index();
						var nex = point.hasClass("next") || point.parent().hasClass("next");
						var bOra = true;
						$Tt.remove();
						$P.remove();
						if(point.parent().is(".cols")){
							point = point.parent();
						};
						var nCol = point.index();
						if(nex){
							$T.insertAfter(point);
							bOra = false;
						}else{
							$T.insertBefore(point);
						};
						$T.siblings().removeClass("can next");
						formatCol(oCol,nCol,bOra);
						if(hids.length > 0){
							if(defaults.multiselect){
								oCol = oCol - 1;
								nCol = nCol - 1;
							};
							if(defaults.number){
								oCol = oCol - 1;
								nCol = nCol - 1;
							};
							var temp = hids[oCol];
							hids.splice(oCol,1);
							hids.splice(nCol,0,temp);			
						};
					}
				});
				return false;
			};
			//打印obj
			function testObj(arr){
				var result = "";
				for(var i=0;i<arr.length;i++){
					result += arr[i].getInfo() + ", ";
				}
				return result;
			};
			
			function sortInfo(index,text){
				this.index = index;
				this.text = text;
				this.getInfo = function(){
					return this.index + ": " + this.text;
				}
			}
			//排td位置
			function formatCol(oCol,nCol,bOra){
				var $t = $('table > tbody > tr',$gView);
				$t.each(function(){
					var oT = $(this).children("td");
					if(bOra){
						oT.eq(oCol).insertBefore(oT.eq(nCol));
					}else{
						oT.eq(oCol).insertAfter(oT.eq(nCol));
					};
				});
			}
			
			//生成menu
			function greatMenu(){
				var gMb = '<div class="grid_head_menu"><ul>';
				if(defaults.sorts){
					gMb += '<li><div><span class="up"></span>升序排序</div></li><li><div><span class="down"></span>降序排序</div></li>';
				};
				if(defaults.colDisplay){
					gMb += '<li class="parting"><div><b class="parent"></b><span class="veiw"></span>列显示</div></li></ul>';
					gMb += '<div class="grid_head_menu"><ul>'
					for(i = 0;i < defaults.colums.length; i++){					
						var text = defaults.colums[i].text ;
						gMb += '<li><div><label class="chected">' + text + '</label></div></li>'
					};
					gMb += '</div>'
				};
				gMb += '</div>';
				$gBox.prepend(gMb);
				$('div.grid_head_menu > ul > li > div:not(.grid_head_menu)',$gBox)
					.bind('mouseover',function(){$(this).addClass('hover')})
					.bind('mouseout',function(){$(this).removeClass('hover')});					
$('.grid_head_menu .up', $gBox).parent().bind("click", {b:'up'} ,colSequence);
$('.grid_head_menu .down', $gBox).parent().bind("click", {b:'down'} ,colSequence);	
			};
			
		
			function dragCol(e){
				$('div.grid_head_menu',$gBox).hide();
				var $c = $("div.th_change",$gHeader);
				var mL = parseInt($gHeader.css("margin-left"));
				var oldW = $c.width();
				var index = $c.index();
				var colL = $c.position().left;				
				var colW = $c.width();
				var $rL = $('<div class="ruler left"></div>');
				var $rR = $('<div class="ruler right"></div>');
				var x,y,p;
				if($('div.ruler',$gView).length > 0){				
					$rL = $('div.left',$gBox);
					$rR = $('div.right',$gBox);
					$rL.show().css("left",colL + mL + $gView.scrollLeft() - 1);
					$rR.show().css("left",colL + colW + $gView.scrollLeft() + mL);
				}else{
					$rL.css("left",colL - 1);
					$rR.css("left",colL + colW);
					$gView.append($rL,$rR);
				};
				_move = true;
				_x = $c.offset().left;
				p = _x - colL;				
				$(document).mousemove(function(e){
					if(_move){
						x = e.pageX - p + mL + $gView.scrollLeft();//移动时根据鼠标位置计算控件左上角的绝对位置
						if(x >= colL + mL + 20 + $gView.scrollLeft()){
							$rR.css("left", x);
						};
					};
					return false;
				}).mouseup(function(e){
					_move=false;
					$(document).unbind('mousemove mouseup');
					$rL.hide();
					$rR.hide();
					if(e.pageX - _x > 20){
						$c.width(e.pageX - _x);
					}else{
						$c.width(20);
					};
					oW = oldW;
					nW = $c.width();
					tI = index;
					formatWidth(oldW, nW, index);
					if(defaults.multiselect)
					tI = index - 1;
					if(defaults.number)
					tI = index - 1;
					colAttributes.widths.splice(tI - 1, 1, nW);
					var sL = $gView.scrollLeft();
					$gHeader.css('margin-left',-sL);
					if($("table", $gView).width() < $gBox.width()) {
						
						var cWidth = $c.width();
						var newWidth = cWidth + $gBox.width() - $("table", $gView).width();
						$c.width(newWidth);
						var $f = $('table > tbody > tr:first > td', $gView);
						$f.eq(index).width(newWidth);
					}
				});
				return false;
			};
			
			//改变td宽度
			function formatWidth(oldW,newW,i){
				var $f = $('table > tbody > tr:first > td', $gView);
				var tW = $('table',$gView).width();
				var z = tW + (newW - oldW);
				var n = tW - (oldW - newW) ;
				$f.eq(i).width(newW);
				if(newW > oldW){
					$('table',$gView).width(z);
					$gHeader.width(z);
				} else{
					$('table',$gView).width(n);
					if(n > $gBox.width())
						$gHeader.width(n);
				};
				if($checkBox.attr("checked")){
					$checkBox.attr("checked",false)
				};
				
			};
			//计算滚动条(一般是不会出现滚动条的)
			function scrollWidth(){			
				if(end == "auto"){
					var url = defaults.url;
					var dataType = defaults.dataType;
					$.ajax({
						url: url,
						dataType: dataType,
						async:false,
						success: function(data){
							end = data.rows.length;
							_data = data;
							_stop = end;
						}
					});
				};
				if(defaults.height < end * 21){
					return true;
				}else{
					return false;
				};
			}
			//算header中td宽度
			function autoWidth(){
				var col = defaults.colums; 
				var check = defaults.multiselect; //判断是否需要checkbox
				var num = defaults.number;  //是否需要序列号
				var allW = 0;
				if(defaults.width){
					if(defaults.width == 'auto'){
						allW = $gW;
					}else{
						allW = defaults.width;
					};					
				}else{
					allW = $gW;
				};		
				var j = 0,b = 0;//b的作用是记录共有表格共有多少列，包含序号列和checkbox，如果有的话
				var hasWidth = 0;
				for(i = 0; i < col.length; i++){
					if(col[i].width){
						hasWidth = parseInt(hasWidth) + parseInt(col[i].width);
					}else{						
						j++; //如果没有给列定义宽度，那么j++
					};
					b++; //每循环一次b加1
				};
				if(check){
					allW = allW - 22;
					b++;
				};
				if(num){
					allW = allW - 30;
					b++;
				};
				if(defaults.height == 'auto'){
					adaptive = Math.abs((allW - b - hasWidth) / j);
				}else{
					if(scrollWidth()){
						adaptive = Math.abs((allW - b - hasWidth - 17) / j);
					}else{
						adaptive = Math.abs((allW - b - hasWidth) / j);
					};				
				};
				
			}
			
			function reGreadPage() {
				var $gPage = $('<div class="grid_page"><div class="grid_page_box"></div></div>');
				var $pBox = $('div.grid_page_box',$gPage);
				var $p = $('<div class="prick"></div>');
				if(defaults.pages.goPage) {
					var goTo = '<div class="grid_entry"><select name="grid_pages">';
					for(i = 1; i < ps + 1; i++){
						goTo = goTo + '<option>' + i + '</option>';
					};
					goTo = goTo + '</select></div>';
					$pBox.append(goTo,'<div class="prick"></div>');
					pSel = $('select', $pBox);
					$($pBox).find("select").val(pB);
					pSel.bind('change', {b:'select'}, jampPage);
				};
				if(defaults.pages.paging){
					var $pagings = $('<span><b class="grid_page_fist"></b></span><span><b class="grid_page_prev"></b></span><div class="prick"></div><div class="grid_note"><input name="" type="text" class="page_nub" value="1" />页 共 '+ ps +' 页</div><div class="prick"></div><span><b class="grid_page_next"></b></span><span><b class="grid_page_last"></b></span><div class="prick"></div>');
					pFirst = $('b.grid_page_fist',$pagings); 
					pPrev = $('b.grid_page_prev',$pagings); 
					pNub = $('input.page_nub',$pagings);  
					pNext = $('b.grid_page_next', $pagings); 
					pLast = $('b.grid_page_last', $pagings); 				
					$pBox.append($pagings);
					pNub.val(pB);
					pFirst.bind("click",{b:'first'},pageFn);
					pPrev.bind("click",{b:'prev'},pageFn);
					pNext.bind("click",{b:'next'},pageFn);
					pLast.bind("click",{b:'last'},pageFn);					
					pNub.bind('keyup',{b:'nub'},jampPage); 
				};
				if(defaults.pages.renew){
					var $refresh = $('<span title="刷新"><b class="grid_refresh"></b></span>');
					$pBox.append($refresh,$p);
					$refresh.bind('click',pageRefresh);
				};
				if(defaults.pages.info){
					var $info = $('<div class="grid_info">每页显示 '+ defaults.rowNum +' 条数据 - 共 '+ total +' 条数据</div>');
					$pBox.append($info);
				};
				$gBox.append($gPage);
			};
			
			jQuery.extend({
				grid : {
					delGridData : function(delUrl, dataType, theThis) {
						defaults.callback.beforeDelData();
						var trId = $(theThis).parents("tr").attr("id");
						var idArray = trId.split("row_");
						$.ajax({
							url: delUrl,
							dataType: dataType,
							type: "GET",
							data:"dataId=" + idArray[1],
							beforeSend : function(){
							},
							error: function (XMLHttpRequest, errorThrown) {
								alert("删除失败！");
							},
							success: function(myData){
								if(myData == "success") {
									if(!defaults.callback.afterDelData()){
										$(theThis).parents('tr').remove();
										alert("删除成功！");
										if($(".grid_view").find("tr").length > 0) {
											$.ajax({
												type: "GET",
												url: defaults.url,
												dataType: "json",
												data:"pageNo=" + pB + "&rowNum=" + defaults.rowNum,
												success: function(loadData) {
													$('table',$gView).remove();
													var dataEnd = (pB-1)*defaults.rowNum + loadData.rows.length;
													initGrid(loadData, 0, dataEnd, colAttributes);
												},
												error:function(XMLHttpRequest, errorThrown) {
													alert("数据加载出错！" + errorThrown);
												}
											});
										} else {
											//传入的页码是上一页
											$.ajax({
												type: "GET",
												url: defaults.url,
												dataType: "json",
												data:"pageNo=" + pB + 2 + "&rowNum=" + defaults.rowNum,
												success: function(loadData) {
													var on = "unclick";
													if(pB != 1) {
														pB = pB - 1;
													}
													str = (pB - 1) * defaults.rowNum;
													ps = Math.ceil(loadData.total/defaults.rowNum);
													total = loadData.total;
													$(".grid_page").remove();
													reGreadPage();
													if((ps == pB && ps == 1) || ps == 0) {
														pNext.removeClass(on);
														pLast.removeClass(on);
														pFirst.removeClass(on);
														pPrev.removeClass(on);
														pNext.addClass(on);
														pLast.addClass(on);
														pFirst.addClass(on);
														pPrev.addClass(on);
													} else if(ps == pB) {
														pNext.removeClass(on);
														pLast.removeClass(on);
														pFirst.removeClass(on);
														pPrev.removeClass(on);
														pNext.addClass(on);
														pLast.addClass(on);
													} else if(pB == 1) {
														pNext.removeClass(on);
														pLast.removeClass(on);
														pFirst.removeClass(on);
														pPrev.removeClass(on);
														pFirst.addClass(on);
														pPrev.addClass(on);		
													}
													$('table',$gView).remove();
													var dataEnd = (pB-2)*defaults.rowNum + loadData.rows.length;
													initGrid(loadData, 0, dataEnd, colAttributes);
												},
												error:function(XMLHttpRequest, errorThrown) {
													alert("数据加载出错！" + errorThrown);
												}
											});
											
										}
										
									}
								}
							}
						});
					},
					clickId : function(){
						return trId;
					},
					selectTrs : function(obj){	
						var objs = obj.split(",");		
						for(var i=0;i < objs.length;i++){
							var id = objs[i];
							var trId = $('tr#'+id,g);
							if(trId.hasClass("select")){
								return;
							}else{
								trId.trigger('click');
							};							
						}
					},
					delMulGridData : function(gridId, delUrl){
						//首先得到所有checkbox的id，拼装成一个id的字符串
						//然后往后台传值，他给我返回新的数据
						var allId = "";
						var temGrid = $("#"+gridId);
						$(temGrid).find("table").eq(0).find("input[type='checkbox']").each(function(){
							if($(this).attr("checked")) {
								allId = allId + $(this).parents("tr").attr("id") + ",";	 	
							}	
						});
						$.ajax({
							url: delUrl,
							dataType: "text",
							type: "GET",
							data:"allId=" + allId,
							beforeSend : function(){
							},
							error: function (XMLHttpRequest, errorThrown) {
								alert("删除失败！");
							},
							success: function(myData){
								if(myData == "success") {
									if(!defaults.callback.afterDelData()){
										//$(theThis).parents('tr').remove();
										alert("删除成功！");
										if($(".grid_view").find("tr").length > 0) {
										$.ajax({
												type: "GET",
												url: defaults.url,
												dataType: "json",
												data:"pageNo=" + pB + "&rowNum=" + defaults.rowNum,
												success: function(loadData) {
													$('table',$gView).remove();
													var dataEnd = (pB-1)*defaults.rowNum + loadData.rows.length;
													initGrid(loadData, 0, dataEnd, colAttributes);
												},
												error:function(XMLHttpRequest, errorThrown) {
													alert("数据加载出错！" + errorThrown);
												}
											});
										} else {
											//传入的页码是上一页
										$.ajax({
												type: "GET",
												url: defaults.url,
												dataType: "json",
												data:"pageNo=" + pB + 2 + "&rowNum=" + defaults.rowNum,
												success: function(loadData) {
													var on = "unclick";
													if(pB != 1) {
														pB = pB - 1;
													}
													str = (pB - 1) * defaults.rowNum;
													ps = Math.ceil(loadData.total/defaults.rowNum);
													total = loadData.total;
													$(".grid_page").remove();
													reGreadPage();
													if((ps == pB && ps == 1) || ps == 0) {
														pNext.removeClass(on);
														pLast.removeClass(on);
														pFirst.removeClass(on);
														pPrev.removeClass(on);
														pNext.addClass(on);
														pLast.addClass(on);
														pFirst.addClass(on);
														pPrev.addClass(on);
													} else if(ps == pB) {
														pNext.removeClass(on);
														pLast.removeClass(on);
														pFirst.removeClass(on);
														pPrev.removeClass(on);
														pNext.addClass(on);
														pLast.addClass(on);
													} else if(pB == 1) {
														pNext.removeClass(on);
														pLast.removeClass(on);
														pFirst.removeClass(on);
														pPrev.removeClass(on);
														pFirst.addClass(on);
														pPrev.addClass(on);		
													}
													$('table',$gView).remove();
													var dataEnd = (pB-2)*defaults.rowNum + loadData.rows.length;
													initGrid(loadData, 0, dataEnd, colAttributes);
												},
												error:function(XMLHttpRequest, errorThrown) {
													alert("数据加载出错！" + errorThrown);
												}
											});
											
										}
										
									}
								}
							}
						});
					
					}
					
				}
			});
			
			
			
			
			
		}
	});
})(jQuery);