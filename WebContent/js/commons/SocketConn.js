//Socket communication

//Singleton
var MySocketConnection = (function () {
	
	// Instance stores a reference to the Singleton
	var instance;
	var ws = null;
	 
	function init() {
	  // Singleton
		
		var msg_display_area = "#Socket_response_panel";
		var host = location.hostname;
		var ws_url = ':8080/RouteAlertServer/socketConn'; //TODO update url
	  // Private methods and variables
		function initConnection(){
			
			if(ws == null){
				ws = new WebSocket("ws://"+host+ws_url);
				ws.onopen = function(){
			    	console.log('socket connection established');
			    };
			    
			    ws.onmessage = function(message){
			         console.log('message received: '+message.data);
			         $(msg_display_area+" .message_area tbody").append('<tr><td>'+message.data+'</td></tr>');
			         $(msg_display_area).show();
			         $(msg_display_area+' .message_area').slideDown('slow');
			         $(msg_display_area+' .message_area').animate({"scrollTop": $(msg_display_area+' .message_area')[0].scrollHeight}, 400);
			    };
			    
			    ws.onerror = function(e){
			    	console.log('Error: '+e);
			    	instance = undefined;
			    	ws = null;
			    };
			    
			    ws.onclose = function(){
			    	console.log('Closing Conn');
			    	instance = undefined;
			    	ws = null;
			    };
			    //add click event on minimize button  
			    $(msg_display_area+' .minimize').on('click', function(){
			    	$(msg_display_area+' .message_area').slideToggle("slow");
			    });
			    $(msg_display_area+' .close').on('click', function(){
			    	$(msg_display_area+'').hide();
			    });
			}
		}
	
		return {
		    // Public methods and variables
			connect: function(msg){
		    	 console.log('Connecting');
		    	 initConnection();
		    },
			postToServer: function(msg){
		    	 console.log('Sending: '+msg);
		    	 if(ws != null){
		    		 ws.send(msg);
		    	 }else{
		    		 alert('Please connect to socket first.');
		    	 }
		    	 
		    },
		    closeConnect: function(){
		    	console.log('Closing Socket');
		        if(ws != null){
		        	ws.close();
		        	ws=null;
		    	 }else{
		    		 alert('Please connect to socket first.');
		    	 }
		    }
	  };
	};
	
	return {
	  // Get the Singleton instance if one exists
	  // or create one if it doesn't
	  getInstance: function () {
	    if ( !instance ) {
	      instance = init();
	    }
	    return instance;
	  }
	};
})();

