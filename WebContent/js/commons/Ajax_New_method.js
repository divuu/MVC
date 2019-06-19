
/**
 * Ajax call for Route Alert Server
 * Default customized for json request and response
 * Use var myajax  = new  AjaxFunction();
 * myajax.init({ contentType, data, dataType , type, url });
 * 								
 * default Options = {  cache : false,
 *						timeout : 10000,
 *						type : 'GET',
 *						contentType: 'application/json',
 *						dataType: 'json',
 *					};
 *	
 * myajax.send({progressCallback, callback});
 * 
 */

var AjaxRequest = /**
 * myajax.init({ contentType, data, dataType , type, url });	
 * default Options = {  cache : false,
 *						timeout : 10000,
 *						type : 'GET',
 *						contentType: 'application/json',
 *						dataType: 'json',
 *					};
 * myajax.send({progressCallback, callback});
 * @returns {AjaxRequest}
 */
function AjaxFunction(){
			
			//Private variables
			var status_code= 0,		//error code
				message = '',	//error text
				data= {},		//data received
				status= false,	//success status use as callback param
				isInitialised = false,	//construct status
				settings = {},	//ajax settings
				progressCallback = function(percentDone){}, //callback for progress
				callback = function(status, response){}; //callback method on complete
				
			
			//Default settings for ajax
			var defaultSettings = {
									cache : false,
									timeout : 10000,
									type : 'GET',
									contentType: 'application/json',
									dataType: 'json',
									progress: function(e){
										 //make sure we can compute the length
								        if(e.lengthComputable) {
								            //calculate the percentage loaded
								        	var pct = (e.loaded / e.total) * 100;
								        	
								            //log percentage loaded
								            console.log(pct);
								            progressCallback(pct);
								        }
								        //this usually happens when Content-Length isn't set
								        else {
								            console.warn('Content Length not reported!');
								        }
									},
									//custom xhr upload download progress listener
									xhr: function() {
										var oldXHR = $.ajaxSettings.xhr;
								        var xhr = oldXHR();
								        if(xhr instanceof window.XMLHttpRequest) {
								            xhr.addEventListener('progress', this.progress, false);
								        }
								        
								        if(xhr.upload) {
								            xhr.upload.addEventListener('progress', this.progress, false);
								        }
								        
								        return xhr;
								    }
				};
									
				//Private Callback function for fail
				var failCallback = function(jqXHR, textStatus, errorThrown){
												//check if network failure	//timeout
												if(jqXHR.responseText=="" && jqXHR.status==0){
													
													status_code = G_ERROR.CODE.NETWORK_FAILURE;
													message = G_ERROR.getMessage(status_code);
													
												}else{
													if( undefined != jqXHR.responseText){
														try{
															var object = jQuery.parseJSON(jqXHR.responseText);
															status_code = object.status_code;
															message = G_ERROR.getMessage(object.status_code);
															
															if(object.message){
																message +='. '+ object.message;
															}
														}catch(e){
															status_code = jqXHR.status;
															message = jqXHR.responseText;
														}
													}else{
														status_code = jqXHR.status;
														message = textStatus;
													}
												}
												status = false;
												data = {};
				};
				
				//Private callbak function for success
				var successCallback = function(data, textStatus, jqXHR){
												//on success set data received
												message = textStatus; 
												status_code = jqXHR.status;
												setData(data, true);
												
				};
				
				//private function set data for both success and failure
				var setData= function(_data, _status){
						data  = _data;
						if( data.status_code ){
							status_code = data.status_code;
							if(status_code < 0){
								message = G_ERROR.getMessage(status_code);
							}
						}
						status = _status;
				};
				
				//public get data
				var getData = function(){
						var respData = {
								status_code: status_code,
								data: data,
								status: status,
								message: message
							}; 
						
						return respData; 				
				};
				
				
				//private reset all data 
				var resetData = function(){
						status_code =0;
						data = {} ;
						status = false;
						message = '';
				};
				
				//public init
				/**
				 * Initialize with all ajax options to used for subsequent request
				 * @param option{ ajax option object } : { url, type(default 'GET'), data, dataType(default 'json'), contentType(default 'application/json') } */
				this.init = function(options){
					//reset data
					resetData();
					//init locals/ construct
					settings = $.extend( defaultSettings, options);
					isInitialised = true;
				};

				/**
				 * Finally Send request
				 * @param options{object} : { progressCallback, callback} */
				this.send = function(options){ 
						//check if initialsed
						if(!isInitialised){
							alert('Please initialised ajax function'); return;
						}
						
						//if option is empty
						if( !options || $.isEmptyObject(options) || !options.callback){
							alert('Please provide options: callback, progressCallback(optional)'); return;
						}
						if(options.progressCallback)
							progressCallback = options.progressCallback;
						
						callback = options.callback;
						
						//Send ajax request
						$.ajax(settings)			
									  .done(function(data, textStatus, jqXHR) {
										  			successCallback(data, textStatus, jqXHR);  			
									  })
									  .fail(function(jqXHR, textStatus, errorThrown) {
										  			failCallback(jqXHR, textStatus, errorThrown);
									  })
									  .always(function(){
										  			var data = getData();
													callback(status, data);
									  });
					};
				
	};
	

//Other useful info	
		//TODO 
		/*	file upload
		 * 	Progress
		 *	beforeSend 	
		 * 
		 * 
		 * */
		
		//.ajaxStart(), .ajaxStop(), .ajaxComplete(), .ajaxError(), .ajaxSuccess(), .ajaxSend()—rather than within the options object for $.ajaxSetup().	
				/*
				 * async 
				 * beforeSend
				 * complete
				 * contents
				 * contentType 
				 * data
				 * dataType
				 * cache 
				 * timeout
				 * type  - get, post 
				 * url 
				 * username
				 * password
				 * statusCode -
				 * error 
				 * success
				 * xhr
				 * 
				 * add progress listener
				 * 
				 * {contentType, data, dataType , type, url , callback}
				 */
				//TODO
				 /* $(document).ajaxStart(function() {
   						$( "#loading" ).show();
 					});
 					$(document).ajaxStop(function() {
      					$( "#loading" ).hide();
					});
				 * */	

	