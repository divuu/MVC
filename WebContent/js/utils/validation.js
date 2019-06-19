//functions for data validation of different fields.
//Author: Imdad Bahar Laskar and Bharat Jyoti Hazarika @TechnoMedia Software Solution pvt. Ltd
//Date: 25 aug, 2012
var FormValidation = {
		/**  
		 * @param id{object}:element
		 * @param message:error message
		 * @returns {Boolean}
		 */
		checkEmpty : function (id,message){
			var value = $.trim((id).val());
			
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			
			if(value=="")
			{
				dialogMessageInvalid(message, "Empty field", id);
				id.addClass( "ui-state-error" );
				return false;
			}
			id.removeClass( "ui-state-error" );
			return true;
		},
		/**
		 * check <li> elem for <ul>
		 * @param id
		 * @param message
		 * @returns {Boolean}
		 */
		checkEmptyList : function (id,message){	//for move passsenger list
			
			var value=$('li,option',id).length;
			if(value < 1)
			{
				dialogMessageInvalid(message, "Empty List", id);
				return false;
			}
			return true;
		},
		checkNumeric : function(id, message){	
			
			var val = $.trim((id).val());
			if( val == "" || val == null ){
				(id).val(0);
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			if(isNaN( (id).val() )){
				dialogMessageInvalid(message, "Invalid Input", id);
				id.addClass( "ui-state-error" );
				return false;
			}
			id.removeClass( "ui-state-error" );
			return true;
		},
		
		checkEmptySelect : function(id, message){
			
			var val = $.trim((id).val());
			if( val == "" || val == null ){
				(id).val(0);
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			if(isNaN((id).val()) || parseInt((id).val(),10)<1){
				dialogMessageInvalid(message, "Select One", id);
				id.addClass( "ui-state-error" );
				return false;
			}
			id.removeClass( "ui-state-error" );
			return true;
		},
		
		checkNonZeroNumeric : function(id, message){
			
			var val = $.trim((id).val());
			if( val == "" || val == null ){
				(id).val(0);
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			if(isNaN((id).val()) || parseInt((id).val(),10)<1){
				dialogMessageInvalid(message, "Invalid Input", id);
				id.addClass( "ui-state-error" );
				return false;
			}
			id.removeClass( "ui-state-error" );
			return true;
		},
		checkPositiveNumeric : function(id, message){
			
			var val = $.trim((id).val());
			if( val == "" || val == null ){
				(id).val(0);
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			if(isNaN((id).val()) || parseInt((id).val(),10)<0){
				dialogMessageInvalid(message, "Invalid Input", id);
				id.addClass( "ui-state-error" );
				return false;
			}
			id.removeClass( "ui-state-error" );
			return true;
		},
		checkLength : function (id , min, max, msg){
			
			var val = $.trim((id).val());
			if( val == "" || val == null ){
				(id).val("");
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			val=val.replace(/\s/g,"");
			var len=val.length;
			if ( len < min || len > max ) {
				if(min != max)
					msg += ".Length must be between " +min + " and " + max + ".";
				else
					msg += ".Length must be " +min + ".";
				
				dialogMessageInvalid(msg, "Invalid Input", id);
				id.addClass( "ui-state-error" );
				return false;
			}
			id.removeClass( "ui-state-error" );
			return true;
		},
		checkStartwithone : function (id ,msg){
			
			var val = $.trim((id).val());
			if( val == "" || val == null ){
				(id).val("");
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
  			if ( val.charAt(0) !="1") {
  				msg ="ID must start with 1"
				dialogMessageInvalid(msg, "Invalid Input", id);

				id.addClass( "ui-state-error" );
				return false;
			}
			id.removeClass( "ui-state-error" );
			return true;
		},

		/**  
		 * @param id{object}:element
		 * @param min{number}:minimum value
		 * @param max{number}:maximum value
		 * @param msg{string}:element name
		 * @returns {Boolean}
		 */
		checkInterval: function(id, min, max, msg){		//add regular exp for validn
			var val = $.trim((id).val()).replace(/\s/g,"");
			
			if( val == "" ){
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			
			val = parseInt(val, 10);
			
			if ( val < min || val > max ) {
				 dialogMessageInvalid(msg+" should be greater than "+min+" and less than "+max, "Invalid Input", id);
				 id.addClass( "ui-state-error" );
				 return false;
				}
				id.removeClass( "ui-state-error" ); 
				return true;
		},

		/**  
		 * validate alphanumeric characters and '-'
		 * @param id{object}:element
		 * @param msg{string}:error message
		 * @returns {Boolean}
		 */
		checkAlphaNumeric : function(id, msg){		//add regular exp for validn
			var word = $.trim((id).val());
			if( word == "" || word == null ){
				(id).val("");
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			var wordPattern = /^[a-zA-Z0-9][a-zA-Z0-9\s\-]*$/i;
			if ( !( wordPattern.test( word ) ) ) {
				 dialogMessageInvalid(msg, "Invalid Input",id);
				 id.addClass( "ui-state-error" );
				 return false;
			 }
			 id.removeClass( "ui-state-error" );
			 return true;
		},

		/**  
		 * @param id{object}:element
		 * @param msg{string}:error message
		 * @returns {Boolean}
		 */
		checkName : function(id, msg){		//add regular exp for validn
			var name = $.trim((id).val());
			if( name == "" || name == null ){
				(id).val("");
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			var namePattern = /^[a-zA-Z]([a-zA-Z0-9_\s])*$/i;
			 if ( !( namePattern.test(name ) ) ) {
				 dialogMessageInvalid(msg, "Invalid Input", id);
				 id.addClass( "ui-state-error" );
				 return false;
			 }
			 id.removeClass( "ui-state-error" );
			 return true;
		},

		checkEmail : function(id, msg){		//add regular exp for validn
			var email=$.trim((id).val());
			if( email == "" || email == null ){
				(id).val("");
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			var emailPattern =  /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
			 if ( !( emailPattern.test(email) ) ) {
				 dialogMessageInvalid(msg, "Invalid Input", id);
				 id.addClass( "ui-state-error" );
				 return false;
			 }
			 id.removeClass( "ui-state-error" );
			 return true;
		},

		checkDate : function(id, msg){		//add regular exp for validn
			var date = $.trim((id).val());
			if( date == "" || date == null ){
				(id).val("");
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			var datePattern =  /(\d{2})\/(\d{2})\/(\d{4})/;
			 if ( !( datePattern.test(date) ) ) {
				 dialogMessageInvalid(msg, "Invalid Input", id);
				 id.addClass( "ui-state-error" );
				 return false;
			 }
			 id.removeClass( "ui-state-error" );
			 return true;
		},

		checkTime : function(id, msg){		//add regular exp for validn
			var time = $.trim((id).val()).replace(/\s/g,"");
			if( time == "" || time == null ){
				(id).val("");
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			var timePattern =  /\d{2}\:\d{2}[aApP][mM]/;
			 if ( !( timePattern.test(time) ) ) {
				 dialogMessageInvalid(msg, "Invalid Input", id);
				 id.addClass( "ui-state-error" );
				 return false;
			 }
			 id.removeClass( "ui-state-error" );
			 return true;
		},

		checkUrl : function(id, msg){		//add regular exp for validn
			var url=$.trim((id).val()).replace(/\s/g,"");
			if( url == "" || url == null ){
				(id).val("");
				return true;
			}
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			var urlPattern =  /^(([^:\/?#]+):)?(\/\/([^\/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?/;
			/*verify and update jq ui regex:  /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i */
			 if ( !( urlPattern.test(url) ) ) {
				 dialogMessageInvalid(msg, "Invalid Input", id);
				 id.addClass( "ui-state-error" );
				 return false;
			 }
			 id.removeClass( "ui-state-error" );
			 return true;
		},

		checkStartEndDate : function(s_id,e_id, msg){			
					//add regular exp for validn
					var startDateStr = $.trim((s_id).val()).replace(/\s/g,"");
					var endDateStr = $.trim((e_id).val()).replace(/\s/g,"");
					if( (startDateStr == "" || startDateStr == null) && (endDateStr == "" || endDateStr == null) ){
						/*(s_id).val("");
						(e_id).val("");*/
						return true;
					}
					e_id.on("keyup click",function(){
						e_id.removeClass( "ui-state-error" );
					});
					var startDate = new Date(startDateStr.replace( /(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3"));
					var endDate = new Date(endDateStr.replace( /(\d{2})\/(\d{2})\/(\d{4})/, "$2/$1/$3"));
					
					if ( startDate.getTime() > endDate.getTime() ) 
					{
						 dialogMessageInvalid(msg, "Invalid Input", e_id);
						 e_id.addClass( "ui-state-error" );
						 return false;
					 }
					e_id.removeClass( "ui-state-error" ); 
					return true;
		},

		/**  
		 * @param m_id{object}:mobile no. input field
		 * @param c_code{string}:country code (default: null)
		 * @param r_len{number}:required length of mobile no.(default: 0)
		 * @param msg: display error message.
		 * @returns {Boolean}
		 */
		validateMobileNo : function(m_id, c_code, r_len ,msg){
			
				var m_no = m_id.val().replace(/\s/g,"");
				
				if( m_no == '' || m_no == null )
					return true;
				m_id.on("keyup click",function(){
					m_id.removeClass( "ui-state-error" );
				});
				//var wordPattern = /^[\+]{0,1}([0-9])*$/i;
				var wordPattern = /([0-9])*$/i;
				if ( !( wordPattern.test( m_no ) ) ) 
				{	 
					dialogMessageInvalid(msg, "Invalid Input",m_id);
					m_id.addClass( "ui-state-error" );
					return false;
				}
/*				
				if( !c_code )
					c_code = MOBILE_NO_COUNTRY_CODE;
				
				//check if country code add at begin
				if ( m_no.slice(0,c_code.length) != c_code ) 
				{	 
					dialogMessageInvalid(msg, "Invalid Input",m_id);
					m_id.addClass( "ui-state-error" );
					return false;
				}
				
				var m_len = m_no.length - c_code.length;
*/				
				var m_len = m_no.length;
				if( !r_len)				
					r_len = DEFAULT_MOBILE_NO_LENGTH;
				
				if(m_len != r_len)
				{
					 dialogMessageInvalid(msg, "Invalid Input", m_id);
					 m_id.addClass( "ui-state-error" );
					 return false;
				}
				m_id.removeClass( "ui-state-error" ); 
				return true;
		},

		/**  
		 *Validate latitude and longitude.
		 * 
		 * @param id: element object
		 * @param type{1,2}: 1- lat, 2- lon.
		 * @param msg
		 * @returns {Boolean}
		 * 
		 */
		validateLatLon : function(id,type, msg){
			var _val = id.val().replace(/\s/g,"");
			
			if( _val == '' || _val == null )
				return true;
			id.on("keyup click",function(){
				id.removeClass( "ui-state-error" );
			});
			if( isNaN(_val) ){
				 dialogMessageInvalid(msg, "Invalid Input", id);
				 id.addClass( "ui-state-error" );
				 return false;
			}
			
			if( type==1){
				var maxLat = 90.0,
					minLat = -90.0;
				var lat  = parseFloat(_val);
				if( lat < minLat || lat > maxLat){
						dialogMessageInvalid(msg, "Invalid Input", id);
						id.addClass( "ui-state-error" );
						return false;
				}
				
			}
			if( type==2){
				var maxLon = 180.0,
					minLon = -180.0;
				var lon  = parseFloat(_val);
				if( lon < minLon || lon > maxLon){
						dialogMessageInvalid(msg, "Invalid Input", id);
						id.addClass( "ui-state-error" );
						return false;
				}
				
			}
			id.removeClass( "ui-state-error" );
			
			return true;
				
		}

};


