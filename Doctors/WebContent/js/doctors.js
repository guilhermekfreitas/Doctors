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
         
         // Serializa objeto para ser enviado via post
         $.fn.serializeObject = function()
         {
             var o = {};
             var a = this.serializeArray();
             $.each(a, function() {
                 if (o[this.name] !== undefined) {
                     if (!o[this.name].push) {
                         o[this.name] = [o[this.name]];
                     }
                     o[this.name].push(this.value || '');
                 } else {
                     o[this.name] = this.value || '';
                 }
             });
             return o;
         };
         
         // funcoes: datepicker p/ dataNasc.
         
 })(jQuery);
