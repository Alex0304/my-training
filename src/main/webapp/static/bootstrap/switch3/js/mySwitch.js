/**
 * @author michael.du
 * @date 2014.12.27
 * 
 * 使用：
 *  html:
 *       <input id="a1" type="checkbox" class="switch" />
 *   说明：获取选中结果：$(obj).prop("checked");
 *   var clickA = function(obj){
 *   	alert("当前选中结果:" + data);
 *   }
 *   $("#a1").BootstrapSwitch();							// 显示结果
 *	 $("#a1").BootstrapSwitch('onSwitchChange', clickA);	// 切换开关时，回调自己的方法  clickA是自己定义的方法
 *   $("#a1").BootstrapSwitch('disable');					// 禁用
 *   $("#a1").BootstrapSwitch('enable');					// 启用
 *   $("#a1").BootstrapSwitch('state');						// 获取开关结果 true/false
 */
(function($){
	var bootStrapSwitch = function(element, options) {
		
		// 点击处理(点击时，做切换效果处理)
		var clickFun = function(e){
				e.stopPropagation();
				var callback = e.data;
	  			$(this).addClass("bootstrap-switch-animate bootstrap-switch-off");

	  			var isChecked = false;
	  			var marginLeft = $(this).css("margin-left");
	  			
	  			if(marginLeft=="0px"){
	  				$(this).css("margin-left", "-53px");
	  			}else{
	  				$(this).css("margin-left", "0px");
	  				isChecked = true;
	  			}
	  			
	  			var checkboxObj = $(this).find("input");
	  			checkboxObj.prop("checked", isChecked);
	  			
	  			// 如果有点击回调，就执行回调
	  			if(callback){
	  				callback(checkboxObj);
	  			}
		 };
	  	  
	  	  // 获取当前结果(true:选中，false:未选中)
		 if(element && element=='state'){
			var state = $(this).prop("checked");
			
			 return state;
		 };
		 
		 // 切换时，有回调的处理
		 if(element && element=='onSwitchChange'){
			 $(this).parent().unbind("click").on('click', options, clickFun);
			 				 
			 $(this).parent().data("click", options);		// 把点击回调事件备份起来, 在disable后，再enable时使用
			 
			 return;
		 }
		 
		 // 改变选中状态 (options：true时设启用， false时设为禁用)
		 if(element && element== 'changeState'){
			 $(this).each(function(){
				var container =  $(this).parent();
				if(options){
					$(container).css("margin-left", "0px");
				}else{
					$(container).css("margin-left", "-53px");
				}
				$(this).find("input").prop("checked", options);	// 改变checkbox的状态
			 });
			 return;
		 }
		 
		 // 禁用
		 if(element && element=='disable'){
			 $(this).parent().parent().removeClass("bootstrap-switch-id-switch-state").removeClass("bootstrap-switch-off");
			 $(this).parent().parent().addClass(" bootstrap-switch-on bootstrap-switch-readonly bootstrap-switch-id-switch-readonly bootstrap-switch-disabled");
			 
			 $(this).parent().unbind("click");
			 
			 return;
		 }
		 
		 // 重新启用           重新绑定点击事件  （注意：重新绑定后，原来的回调会丢失）
		 if(element && element=='enable'){
			 $(this).parent().parent().removeClass("bootstrap-switch-disabled bootstrap-switch-readonly bootstrap-switch-id-switch-readonly");
			 $(this).parent().parent().addClass("bootstrap-switch-id-switch-state bootstrap-switch-off");
			 
			 var clickCallback = $(this).parent().data("click");			// disable前绑定的点击回调事件，本次要重新绑定回去
			 
			 $(this).parent().on('click', clickCallback, clickFun);
			 return;
		 }
		 
    	 $(this).each(function(){
    			
    		var checkbox = $(this).prop("outerHTML");
    		var isChecked = $(this).prop('checked');

    		var marginLeft = "0px;";
			if(! isChecked){
				marginLeft = "-53px;";
			}
    			
			var str = '<div class="bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-id-switch-state bootstrap-switch-animate  bootstrap-switch-off" style="width:102px;">';
			str += '<div class="bootstrap-switch-container" style="width: 160px; margin-left: '+ marginLeft +'">';
			str += '<span class="bootstrap-switch-handle-on bootstrap-switch-primary" style="width: 50px;">ON</span>';
			str += '<span class="bootstrap-switch-label" style="width: 50px;">&nbsp;</span>';
			str += ' <span class="bootstrap-switch-handle-off bootstrap-switch-default" style="width: 54px;">OFF</span>';
			str += checkbox;
			str += '</div>';
			str += '</div>';
			
			$(this).replaceWith(str);
			$(this).find(".bootstrap-switch-container").on('click', clickFun);
		});
    	  
    	//$(".bootstrap-switch-container").on('click', clickFun);
     };
  
     $.fn.BootstrapSwitch = bootStrapSwitch;
		 
})(jQuery);
