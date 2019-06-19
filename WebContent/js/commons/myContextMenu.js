/* Jquery plugin for context menu or right click menu.
 * jquery 1.8.3
 * by imdad
 * usage : $(selector).myContextMenu({menu: menu_id, callback: function(){ });
 * */

(function( $ ){
	  var menu_methods = {
				     init : function( options ) {
				    	 
				    	// Create some defaults, extending them with any options that were provided
				    	 var settings = $.extend( {
 							callback : function(){console.log("Clicked");},
 							menu : null,
				    	 }, options);
				    	 if( !settings.menu)
						    {
						    	alert("menu id not defined");
						    	return;
						    }
				    	 var revent = jQuery.Event("contextmenu");	
				    	 return this.each(function(){
				           	var $this = $(this),
				    	   	data = $this.data('myContextMenu'),
				    	   	myContextMenu = $("#"+options.menu),
				    	   	callback = options.callback? options.callback: null;
				    	     
				           	// If the plugin hasn't been initialized yet
					         if ( ! data ) 
					         {		
					        	 	/*Do more setup stuff here*/
					        	 	myContextMenu.css({display:'block', visibility:'hidden'}).appendTo(document.body); //need for getting menu size
					        	 	
					        	 	var menuDimension = { w:myContextMenu.outerWidth(), h:myContextMenu.outerHeight() };
					        	 	var docVisWidth=$(document).scrollLeft()+$(window).width()-40; //40 is to account for shadows in FF
					        		var docVisHeight=$(document).scrollTop()+$(window).height()-40;
					        		
					        		myContextMenu.css({display:'none', visibility:'visible'});
					        		//myContextMenu.toggleClass('ContextMenu', true);
					        		
					        		//Right click event
					        		$this.on('contextmenu.myContextMenu', function(e){
						    	   		e.preventDefault();	//disable default menu
						    	   		revent = e;
						        	 	var x=e.pageX+ 1;//additional offset
						    			var y=e.pageY-1;
						    			x = ( x + menuDimension.w > docVisWidth)? docVisWidth - menuDimension.w : x; //if not enough horizontal room to the ridge of the cursor
						    			y = ( y + menuDimension.h > docVisHeight)? docVisHeight - menuDimension.h : y;
						    			
						    			myContextMenu.css({left:x, top:y});
						    	   		myContextMenu.show();
						    	   		
						    	   });
					        		
					        		//left click evevt on menu
					    	   		myContextMenu.find('li').on('click', function(){
					    	   			if(callback){
					    	   				var id = $(this).find('a').attr('href');
					    	   				callback(id,revent);
					    	   			}
					    	   			myContextMenu.hide();
					    	   			return;
					    	   		});
					    	   		
					    	   		//left click event on outside menu //TODO need improvement( may use .one() )
					    	   		$(document).on('click', function (e) {
										var target = $(e.target);
										if (target.parents('ul.ContextMenu').length != 0 || target.is(myContextMenu)){
											return;
										}else{
											myContextMenu.hide();
										} 
									});
					    	   		
					    	   		//Set data for later use
							          $(this).data('myContextMenu', {
							               target : $this,
							               myContextMenu : myContextMenu,
							               menuDimension : menuDimension
							          });
					         }
				       });
				     },
				     destroy : function( ) {
			
				       return this.each(function(){
			
				         var $this = $(this),
				             data = $this.data('myContextMenu');
			
				         // Namespacing FTW
				         $this.off('.myContextMenu');
				         data.myContextMenu.remove();
				         $this.removeData('myContextMenu');
				       });
			
				     },
				     reposition : function( ) {  },
				     show : function( ) {  },
				     hide : function( ) {  },
				     update : function( content ) {  },
				   
	  };
//base/calling function
	  $.fn.myContextMenu = function( method ){
		   	//console.log(this, arguments);
		    if( menu_methods[method] ){
		    	return menu_methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
		    }
		    else if( typeof method === 'object' || ! method ){
		      return menu_methods.init.apply( this, arguments );
		    } 
		    else{
		      $.error( 'Method ' +  method + ' does not exist on jQuery.myContextMenu' );
		    } 
	  };

	})( jQuery );