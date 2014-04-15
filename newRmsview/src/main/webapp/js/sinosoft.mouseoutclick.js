$(function() 
{
    $.fn.mouseoutclick = function( callback ) 
    {
    	var $this = $(this);
    	var flagMouseoverKey = 'isMouseover'; 
    	
    	//set flag to 0/false Ϊ0��ʱ���ʾ���û�н����Ԫ��
    	$this.mouseleave(function()
		{
    		$this.data(flagMouseoverKey,0);
		});
    	
    	//set flag to 1/true Ϊ1��ʱ���ʾ�������˸�Ԫ��
    	$this.mouseenter(function()
		{
    		$this.data(flagMouseoverKey,1);
		});	
    	//handle mouseup-event �������document��̧���Ǵ������¼�
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