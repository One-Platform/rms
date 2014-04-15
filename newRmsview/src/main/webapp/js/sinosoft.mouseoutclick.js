$(function() 
{
    $.fn.mouseoutclick = function( callback ) 
    {
    	var $this = $(this);
    	var flagMouseoverKey = 'isMouseover'; 
    	
    	//set flag to 0/false 为0的时候表示鼠标没有进入该元素
    	$this.mouseleave(function()
		{
    		$this.data(flagMouseoverKey,0);
		});
    	
    	//set flag to 1/true 为1的时候表示鼠标进入了该元素
    	$this.mouseenter(function()
		{
    		$this.data(flagMouseoverKey,1);
		});	
    	//handle mouseup-event 当鼠标在document上抬起是触发的事件
		$(document).bind('mouseup', function()
		{		
			// if the mouserOverFlag is not set or set to 0
			if(!$this.data(flagMouseoverKey) || $this.data(flagMouseoverKey) == 0)
			{
				//Trigger callback (if set)
				if(typeof(callback) == 'function')
					callback();
				else //hide by default
					$this.hide();
			}
		});
	
	};
});