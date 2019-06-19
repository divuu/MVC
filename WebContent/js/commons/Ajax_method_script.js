/* All ajax methods are defined below*/

$(document).ready( function(){
	/*Check server is online*/
	var pingUrl = 'checkServerOnline.html'; //Url to ping at
	var _check_server  = checkServerOnline(pingUrl);
	_check_server.check();

	$('#try_connect_server').live('click', function(){ 
		_check_server.check();
	});
});	
//TODO put css & html here


/*
 * jqXHR.then(function(data, textStatus, jqXHR) {}, function(jqXHR, textStatus, errorThrown) {});
 */

/*  
 * function to check server online : jquery 1.8
 */
function checkServerOnline(pingUrl){
	var timer_interval=0,
	check_con_timeout=0,
	_URL = pingUrl,
	_TYPE = 'HEAD',
	_TIMEOUT = 10000,
	_CACHE = false;

	var elem_timer = $('#reconnect_count_down'),
	elem_sts_msg = $('#serverConnection_status'),
	elem_srv_dlg = $("#serverConnection_dialog");

	return {
		check: function(){
			var _this = this;
			if(timer_interval)
				clearInterval( timer_interval);

			if(check_con_timeout)
				clearTimeout( check_con_timeout);

			var con_success = true;
			var set_time = 10;

			elem_timer.text(".!");
			elem_sts_msg.text("Connecting");
			elem_srv_dlg.text("Please Wait.!");

			if ($.support.cors) 
			{	
				var jqxhr = $.ajax({
					url: _URL,
					type: _TYPE,
					timeout : _TIMEOUT,
					cache:_CACHE
				});
				jqxhr.done(function() {

					clearInterval( timer_interval);
					con_success = true;
					$("#serverConnection").toggleClass('hide_elem', con_success);

					elem_timer.text(".!");
					elem_sts_msg.text("Connecting");
					elem_srv_dlg.text("Please Wait.!");

				});
				jqxhr.fail(function (jqXHR, textStatus, errorThrown) {

					con_success = false;
					$("#serverConnection").toggleClass('hide_elem', con_success);
					clearInterval( timer_interval);
					setTimeout( function(){

						timer_time = set_time;
						timer_interval = setInterval( function(){

							if(timer_time > -1){

								elem_timer.text(timer_time);
								timer_time -= 1;
								elem_sts_msg.text("Trying in");
								elem_srv_dlg.text("Unable to connect Server.!");
							}else
							{	
								clearInterval( timer_interval);
								elem_timer.text(".!");
								elem_sts_msg.text("Connecting");
								elem_srv_dlg.text("Please Wait.!");
							}
						}, 1000);
					}, 1000);	
				});
				jqxhr.always(function(){ 

					$("#serverConnection").toggleClass('hide_elem', con_success);
					//restart timer
					check_con_timeout = setTimeout( function(){ 
						_this.check();
					}, 14000);
				});

			}

		}
	};
}
