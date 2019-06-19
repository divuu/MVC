/*     Data picker :drop menu        */
/**
 * use: element.dropmenu(options);
 * 
 * 
 */


(function($){
    $.fn.extend({ 
        //plugin name - dropmenu
        dropmenu: function(option) {
 
            var defaults = {
            	top: 'auto',
            	right: 'auto',
            	menuDivId: null,
            };
            
            var options = $.extend(defaults, option);
         	//var isVisible = false;
         
            return this.each(function() {
            	var o = options;
				var self = $(this);
				var menu = $("#" + o.menuDivId);
				
				self.on("click focus", function(event){
					event.stopPropagation();
					menu.css(
						{
							'position': 'absolute', 'top': o.top + 'px', 'right': o.right + 'px'
						}
					).show();
					//isVisible = true;
				});
				
				menu.on("click", function(event){
					event.stopPropagation();
				});
				// Close open dropdown slider by clicking elsewhwere on page
				$(document).bind('click', function (e) {
					var target = $(e.target);
					if (target.parents('div.dropmenu_datapick').length != 0 || target.is(self)){
						return;
					}else{
						menu.slideUp();
					} 
				});
				
		   });
        }	
    });
})(jQuery);