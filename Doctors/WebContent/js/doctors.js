(function($) {
        // $ é o jQuery no escopo do plugin
        // Poderia ser qualquer apelido
        $.fn.recarregaGrid= function(config){
            
            $(this).jqGrid('clearGridData');
            if (typeof config.postData !== 'undefined'){
            	$(this).jqGrid().setGridParam({postData: config.postData });
            }
    		if (typeof config.caption !== 'undefined'){
    			$(this).jqGrid('setCaption',config.caption);
    		}
    		$(this).trigger("reloadGrid");
            
            return this;
         };
         
         
         
 })(jQuery);