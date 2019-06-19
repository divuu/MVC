/**
 * Name: routeAlert_main.js Description: Contains all operations to be performed
 * on document ready. Also contains methods common to all members.
 * 
 * 
 */


// table row index:
var INDEX_OF_CHECKBOX = 0;
var DEFAULT_ORGANIZATION = {id:0}; // TODO:set to 0.

var delay_event = false;

// create tab on click event
$(document).ready(function(){
		console.log('Ready Block');
		
		
		/* Set main body layout size on resize window */
		$( window ).resize(function(){
			// console.log('resize window fired');
			$('td.rNavgator div.navPanel').css({
				height: ($('body').height() - $('table.wrapper>thead').height() - $('table.wrapper>tbody>tr:first').height() - $('table.wrapper>tfoot').height())+ "px" // get
																																										// hight
																																										// of
																																										// pane
																																										// td
			});
			
			$("td.rContainer div.contents").css({
				width: ($("html").width() - 231 )+ "px",
				height: $('td.rNavgator div.navPanel').height() + "px", // get
																		// hight
																		// of
																		// content
																		// pane
																		// td
			});
		});
		$(window).trigger("resize");
		/* End Main window resize */
		
		giLogin = false;
		
		/* Show User Account info */		
		var ac_user_name = $.cookie('name');
		 
		
		$("#account_options span.user_name").text(ac_user_name.replace(/\"/g,'')); // remove
		var user_session_str_frommenu = $.cookie( "u_session");

		// //load role Access level
		// var mainmenu = RoleScript.getInstance();
		// 	mainmenu.fetchData({
		// 	callback : function(status, response){
		// 		if(status && response.data.values){
		// 			var _role = response.data.values[0];
		// 			var role = {
		// 				role_id : _role[RoleScript.INDEX.ROLE_ID - 1],
		// 				//roll : _role[RoleScript.INDEX.ROLE_MENU - 1],
		// 				roll_access_level_1 : _role[RoleScript.INDEX.ROLE_MENU - 1],
		// 				roll_access_level_2 : _role[RoleScript.INDEX.ROLE_SCRIPT - 1]  
		// 			};
		// 			var transtr = role.roll_access_level_1;
		// 			var masstr =role.roll_access_level_2;
		// 			$('#Transactions1').html(transtr);
		// 			$('#Masters1').html(masstr);			
		// 		}		
		// 	}	
		// });

		var user_session = JSON.parse(user_session_str_frommenu);

		//var  str = user_session.S_Transaction_Menu;
		//console.log(str);
		var str1 ="<ul class='tab_link' id=''><li><a href='#' rel='OrganizationDetails' title='Organization'>Organization</a></li><li><a href='#' rel='SysUserDetails' title='SysUser'>Sys User</a></li><li><a href='#' rel='RoleDetails' title='Role'>Role</a></li><li><a href='#' rel='CityDetails' title='city'>City</a></li><li><a href='#' rel='CountryDetails' title='Country'>Country</a></li><li><a href='#' rel='CurrencyDetails' title='currency'>Currency</a></li><li><a href='#' rel='SubscriberDetails' title='Subscriber'>Subscriber</a></li></ul>"
		//<li><a href='#' rel='ParkingAreaDetaillsDetails' title='parking area details'>Parking Area Details</a></li>
		
		var str ="<ul class='tab_link' id=''><li><a href='#' rel='AnalyticsDetails' title='Analytics'>Analytics</a></li>"
		//<li><a href='#' rel='CustomerDetails' title='Customer'>Customer</a></li></ul>"

		$('#Transactions1').html(str);

		//var  str1 = user_session.S_Master_Menu;
		//console.log(str1);

		$('#Masters1').html(str1);
/*
 * $("#account_options span.user_name").text(ac_user_name.replace(/\"/g,'')); //
 * remove //if( ac_user_name =="\"Joydeep RC\"") { var str ="<ul class='tab_link' id=''>"+ "<li><a
 * href='#' rel='InvoiceDetails' title='Invoice'>Invoice</a></li>"+ "<li><a
 * href='#' rel='IncentiveDetails' title='Incentive'>Incentive</a></li>"+ "<li><a
 * href='#' rel='InventoryDetails' title='Inventory'>Inventory</a></li>"+ "<li><a
 * href='#' rel='PaymentHistoryDetails' title='Payment'>Payment</a></li>"+ "<li><a
 * href='#' rel='PromotionDetails' title='Promotion'>Promotion</a></li>"+ "<li><a
 * href='#' rel='AwardDetails' title='Awards'>Awards</a></li>"+ "</ul>"
 * $('#Transactions1').html(str);
 *  }
 */		
		/* Dashboard accordion */
		$("#dash_board_accordion").accordion({'active':0});  // Transaction
																// menu
		$("#dash_board_accordion").css({
			height: '99%' 
		});
		/* populate organization combo */
		showOrgDetailsinHome(-1);
		
		/* Set organization change event in home */
		$("#home_tab_content a._change_organization").on('click', function(){
			
				var _select_combo = "#home_tab_content select[name=organization_id]";
				var org_id = parseInt( $(_select_combo).val(),10 );
				var org_name = $(_select_combo+" option:selected").text();
				
				var elem_org = $(_select_combo);
				var message = "Please Select one organization in Home Tab!",
					title = "Organization Not Selected";
				
				if( org_id == 0)
				{
					dialogMessageInvalid(message, title, elem_org);
					return;
				}
				else if( DEFAULT_ORGANIZATION.id != 0 && org_id != DEFAULT_ORGANIZATION.id )
				{	
					message = "All open tabs will be closed after changing organization. Do you want to proceed?";
					
					$("#messageid" ).html('<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span></p>');	// can
																																			// be a
																																			// method
					$("#messageid" ).append(message);
					
					$( "#messageid" ).dialog({
						title : title,
						height : 'auto',
						width : 'auto',
						modal: true,
						buttons:{
							"Ok" : function(){
									/* remove other tabs */
									CloseAllTabs();
									setOrganizationId( org_id );
									$(this).dialog("close");
									
							},
							"Cancel" : function(){
									elem_org.val(DEFAULT_ORGANIZATION.id);
									$(this).dialog("close");
							}
						},
						close: function(){
							if(elem_org != undefined)
								elem_org.focus();
							$("#messageid").dialog("destroy");
						}
				
					});
				}else{
					// set the organization id
					setOrganizationId( org_id );
					
					var message = "You have selected "+org_name,
					title = "Organization Selected";
					dialogMessage(message, title);
										
				}
		});
		
		// context menu for tab
		$("#tab_bar").myContextMenu({ 
			menu:'TabContextMenu',
			callback: function(id, event){
				// console.log(event);
				switch(id){
						case "#close":
							CloseAllTabs();
							break;
						default : 
							alert("I\'m sure it was great");
				}
			}
		});

		
		/* Open tab */
// var delay_event = false;
		
// $("#dash_board .tab_link123 a").on('click', function(){
//
// handleGiEntity( $(this) );
// });
		
		$("#dash_board .tab_link a").on('click', function(){
			
			if( $(this).is('a[rel='+OrganizationScript.REL+']') )
			{
				console.log('select org not needed');
			}
			else if( DEFAULT_ORGANIZATION.id < 1)
			{
				var message = "Please Select one organization in Home Tab!",
					title = "Organization Not Selected";
				// hide all other tab content
				$(".tblContainer").hide();
				// hide all other tabs
				$("#tab_bar .tabs li").removeClass("current");
				// find the first tab
		    	var firsttab = $('#tab_bar .tabs :first-child').addClass("current");
		    	// get its link name and show related content
		    	var firsttabid = $('#tab_bar .tabs :first-child a.tab').attr("id");
		    	$("#" + firsttabid + "_content").show();
		    	// hide tool bar
		    	$("#tab_tool .toolBtns").hide();
		    	
		    	dialogMessageInvalid(message, title, $('#home_tab_content select[name=organization_id]'));
				
		    	return;
			}
			else{
				console.log('current organization id='+getOrganizationId());
			}
				
			// show tools
			
			if(!delay_event){
				delay_event = true;
				// create tab
			    addTab( $(this), true );
			}
			window.setTimeout(function(){
				delay_event = false;
			}, 1000);
		});
				
	    
/*-----------------------tip-tip--------------------- */
		/* $(".show_tooltip").tipTip(); */		
		
/*-------------------------tab events---------------------------*/
			$('#home_tab').on('click', function() { // TODO remove extra code
		    
				$(".tblContainer").hide();
			   
				// Get the tab name
			    var contentname = $(this).attr("rel") + "_content";
			    
			    // hide all other tabs
			    $("#tab_bar .tabs li").removeClass("current");
			    
			    $("#tab_tool .toolBtns").hide();
			    
			    // show current tab
			    $("#" + contentname).show();
			    $(this).parent().addClass("current");
			});
		
		/* Left - right movements of tab */
		$("#tabs-header .tabs-scroller-right").on('click', function(){
			var header_width = $("#tabs-header").width();		console.log(header_width); 
			var tab_width = $("#tab_bar .tabs").outerWidth(true);
			var tab_hid_width = $("#tab_bar div:first").position().left;
			var tab_vis_width = tab_width + tab_hid_width;		
			var tab_pos_right = header_width - tab_vis_width - 5;	// 5px
																	// margin
																	// console.log(tab_pos_right);
			if( tab_pos_right == 18)
				 return;
			if(tab_pos_right <= -120 ){
					$("#tab_bar div:first",$("#tabs-header")).animate({"left": "-=100px"}, 10);
			}else if(tab_pos_right <= -60 ){
					$("#tab_bar div:first",$("#tabs-header")).animate({"left": "-=60px"}, 10);
			}else{
					var move_left = tab_pos_right;
					if( move_left < 0 ){
						move_left = -(move_left)+18;
						$("#tab_bar div:first",$("#tabs-header")).animate({"left": "-="+(move_left)+"px"}, 10);
					}else if(move_left >= 0 && move_left < 18){
						move_left = 18 - (move_left);
						$("#tab_bar div:first",$("#tabs-header")).animate({"left": "-="+(move_left)+"px"}, 10);
					}else
						return;
				}
			
		});
		
		$("#tabs-header .tabs-scroller-left").on('click', function(){// continue
																		// from
			var tab_pos = $("#tab_bar div:first").position(); 
			if(tab_pos.left == 18)
				return;
			if(tab_pos.left <= -120 ){
				$("#tab_bar div:first",$("#tabs-header")).animate({"left": "+=100px"}, 10);
			}else if(tab_pos.left <= -60 ){
				$("#tab_bar div:first",$("#tabs-header")).animate({"left": "+=60px"}, 10);
			}else{
				var move_right = tab_pos.left;
				if( move_right < 0 ){
					move_right = -(move_right)+18;
					$("#tab_bar div:first",$("#tabs-header")).animate({"left": "+="+(move_right)+"px"}, 10);
				}else if(move_right >= 0 && move_right < 18){
					move_right = 18 - (move_right);
					$("#tab_bar div:first",$("#tabs-header")).animate({"left": "+="+(move_right)+"px"}, 10);
				}else
					return;
			}
		});
/*---------------------------end of tab events---------------------------------*/
	

	// Single click :on single click on table row :checkbox selects row
	$('#tab_content').on('click', "._editable_table tbody td", function(e){
		
		// toggle default checkbox prop
		if( $(e.target).is(':checkbox' )){
			$(e.target).prop('checked',!$(e.target).is(':checked'));
		}
		// check-uncheck checkbox
		var elem = $(this).parent("tr:first");
		var tab_rel = $("#tab_bar li.current a").attr('id');
		var elem_checkbox = $(elem).find(":checkbox"); 
		$(elem_checkbox).prop('checked',!(elem_checkbox.is(':checked')));
		elem.toggleClass('selected_row', elem_checkbox.is(':checked'));
		// check-uncheck select all checkbox
		if(!elem.find("input:first").is(':checked')){
			$( "#"+tab_rel+"_table > thead th:first input[type=checkbox]" ).prop('checked', false);
		}
		
	});
// ------Style tool buttons
		$("button").button();
		
		/* Account info menu */
		$("#account_options .dropdown a.button").on('click', function(){
			$("#account_options .dropdown_menu").toggleClass('hide_elem');
			
			if( !$("#account_options .dropdown_menu").hasClass('hide_elem')){
				$("#account_options .dropdown_menu").find('a.change_password').one('click', function(){
					showChangePasswordDialog();
					$("#account_options .dropdown_menu").toggleClass('hide_elem', true);
				});
				$("#account_options .dropdown a.button .toggle").toggleClass('active', true);
			}else{
				$("#account_options .dropdown a.button .toggle").toggleClass('active', false);
			}
		});
		// hide account info menu
		$(document).on('click.accountdropdown_menu', function (e) {
			var target = $(e.target);
			var action_button = $("#account_options .dropdown a.button");
			if( target.parents('div.dropdown_menu').length != 0 || target.is( action_button) || target.parents("#account_options .dropdown a.button").length != 0  ){
				return;
			}else{
				$("#account_options .dropdown_menu").toggleClass('hide_elem', true);
				$("#account_options .dropdown a.button .toggle").toggleClass('active', false);
			}
		});
		
		// ******end account info menu*******
		
		
		// bind globally ajax loader
		$(document).ajaxStart(function() {
			$( "#tab_tool .ajax_loader_img" ).show();
			});
		$(document).ajaxStop(function() {
			$( "#tab_tool .ajax_loader_img" ).hide();
		});
		$(document).ajaxComplete(function() {
			$( "#tab_tool .ajax_loader_img" ).hide();
		});
		
		// Hide all components that are not eligible for the role
		var roleId = getUserRoleIDFromCookie();
		
		// load role Access level
		var role_obj = RoleScript.getInstance();
		
		role_obj.fetchData({
			organization_id : getOrganizationId(),
			role_id : [roleId],
			callback : function(status, response){
				if(status && response.data.values){
					
					console.log("NEED to change this part of ROLE Code");
					var _role = response.data.values[0];
					// set permission to a variable for later use
					var role = {
						role_id : _role[RoleScript.INDEX.ROLE_ID - 1],
						roll : _role[RoleScript.INDEX.ROLL - 1],
						roll_access_level_1 : _role[RoleScript.INDEX.REMARKS - 1],  
						//roll_access_level_2 : _role[RoleScript.INDEX.ROLL_ACCESS_LEVEL_2 - 1]
					};
					setUserRoleInCookie(role);
					
					// update UI Dashboard
					// hide organization link
					if(checkRolePermission(role, SOFTWARE_FEATURE_CONST.READ_ASSIGNED_ORGANIZATION)== false){
						$("#dash_board .tab_link a[rel="+OrganizationScript.REL+"]").hide();
					}
					// add more
					
					
				}else{
					// role not found
				}
				
			}
		}); 
		
		// attaching event listener to the text editor dialog box
		attachTextEditorEvent();
		latlong_enter();
		latlong_btn();
	});// document.ready end here

	/** *************************************************************************************************** */    
	/**
	 * @param permission1
	 * @param permission2
	 * @param featureConst
	 * @returns
	 */
	function checkRolePermission(role, featureConst){
		// check role permission
// if( featureConst <= 31){// if resource <= 31 check permission 1
// return BitUtils.IS_BIT_SET( role.roll_access_level_1, featureConst );
// }else if(featureConst <= 63 ){ // else check permission 2
// return BitUtils.IS_BIT_SET( role.roll_access_level_2,(byte)
// (featureConst-32));
// }else{
// // add more access levels
// }
// return false;
		
		console.log("checkRolePermission -> has to be implemented");
		return true;
	}

	function addCommonFormEvents(form_id){
		// Enable time picker in form:
		
		$("#"+form_id+" .timepicker").timepicker({
		       showPeriod: true,
		       showLeadingZero: true
		   });

		// Enable date picker
		$("#"+form_id+" .datepicker").datepicker({
					changeMonth: true,
		            changeYear: true
		            });
		
		$("#"+form_id+" .datepicker").datepicker("option", "dateFormat", "dd/mm/yy" );
		
		/* Get Location popup */
// $("#"+form_id+" ._getlocation").off('click._getlocation');
// $("#"+form_id+" ._getlocation").on('click._getlocation', function(){
//			
// var elem_lat = $('input._latitude',$('#'+form_id));
// var elem_lon = $('input._longitude',$('#'+form_id));
//			
// dialogGetLocation(elem_lat, elem_lon);
// });
		
	}


	function addTableSelectAllRowChkBoxEvent(table_id){
		// Select All check box to check/uncheck all row check box ---
		$( "#"+table_id+" > thead th:first input[name=select_all_rows]" ).on('click', function(e){
			
			var chk_box = $(this);
			
			var row_vis = $( "#"+table_id+" > tbody tr");
			
			row_vis.each(function(){
				var elem=$(this);
				elem.find("td:first input[type=checkbox]").prop("checked", chk_box.is(':checked'));
				elem.toggleClass('selected_row', chk_box.is(':checked'));
			});
		});
		// end select all
	}
	
	
	function addTableRowDblClickEvent(table_id, callback){
		
		$('#'+table_id+' tbody td').on('dblclick.edit_row', function(){
			
			var elem_row= $(this).parents("tr:first");
			var index = elem_row.find('td:first input:checkbox').attr('data_row_index');
			
			if(index && !isNaN(index)){
				callback(index);
			}else{
				callback(-1); // index not found
			}        
		});
	}
	
	
	/**
	 * single click on table row
	 * 
	 * @param table_id
	 */
	function addTableRowClickEnent(table_id){

		// // Single click
		// $('#'+table_id+ ' tbody td' ).on('click', function(e){
		// // toggle default checkbox prop
		// if( $(e.target).is(':checkbox' )){
		// $(e.target).prop('checked',!$(e.target).is(':checked'));
		// }
		// // check-uncheck checkbox
		// var _tr = $(this).parent("tr:first");
		// var elem_checkbox = $(_tr).find("td:first :checkbox");
			
		// $(elem_checkbox).prop('checked',!(elem_checkbox.is(':checked')));
			
		// _tr.toggleClass('selected_row', !elem_checkbox.is(':checked'));
			
		// // check-uncheck select all checkbox
		// if(!elem_checkbox.is(':checked')){
		// $( "#"+table_id+" > thead th:first input[name=select_all_rows]"
		// ).prop('checked', false);
		// }
		// });
		
		
		/* Send sms */
		$('#'+table_id+ ' tbody ._sendsms' ).on('click', function(){
			var mobile_no = $.trim( $(this).text());
			if(mobile_no == "")	return;
			dialogSendSms(mobile_no);
		});
		
		/* Send email */  
		$('#'+table_id+ ' tbody ._sendemail').on('click', function(){
			var email_id = $.trim( $(this).text());
			if(email_id == "")	return;
			dialogSendEmail(email_id);
		});
	}


	/**
	 * @param option{object} : {
	 *            button_id: id, menu_id: id, callback: function() }
	 */
	function addActionMenuEvent(option){
		if(option && option.button_id && option.menu_id && option.callback){
			
			$(option.button_id).on('click', function(){
				
				var list = $(option.menu_id+' ul');
				
				if( $('li', list).length !=0 ){
						$(option.menu_id).toggleClass('hide_elem');
				}
				
				// Action on click menu event
				$(option.menu_id +' ul li').off();
				$(option.menu_id +' ul li').on('click', function(){
					
					var id = $(this).attr('value');
					$(option.menu_id).toggleClass('hide_elem', true);
					
					if( id && !(isNaN(id))){
						id = parseInt(id,10);
					}
					option.callback(id);
				});
			});
			
			// Close menu on click outside menu
			$(document).on('click.dropdown_menu', function (e) {
				var target = $(e.target);
				var action_button = $(option.button_id);
				if( target.parents('div.dropdown_menu').length != 0 || target.is( action_button) || target.parents(option.button_id).length != 0  ){
					return;
				}else{
					$(option.menu_id).toggleClass('hide_elem', true);
				} 
			});
		}
	}




	/**
	 * get index of all selected row as array
	 * 
	 * @param table_id
	 * @returns{Array}[] array on data index
	 */
	function getSelectedRowDataIndex(table_id){
		if(!table_id || table_id=='' || table_id == null){
			console.log('table id is null');
			return -1;
		}
		
		var selected_row = $('#'+table_id+' tbody tr.selected_row');
		
		var index_arr = [];
		if(selected_row){
			selected_row.each(function(){
				var inx = $(this).find('td:first input:checked').attr('data_row_index');
				index_arr.push(parseInt(inx));
			});
		}
		return index_arr;
	}
	
	/**
	 * Check if only one row is selected
	 * 
	 * @param table_id
	 * @returns {Boolean}
	 */
	function checkIfSingleRowSelected(table_id){
		var selected_row = $('#'+table_id+' tbody tr.selected_row');
		var num_row = selected_row.length;
		
		if(num_row < 1){
			$( "#selected_null_edit" ).dialog({
				height: 'auto',
				modal: true,
				buttons:{
					" OK " : function() {
						$(this).dialog("close");
					}
				}
			});
		
	    }else if(num_row>1){
			$( "#selected_more" ).dialog({
				height: 'auto',
				modal: true,
				buttons:{
					" OK " : function() {
						$(this).dialog("close");
					}
				}
			});
	    }else{
	    	return true;
	    }
		return false;
	}
	
	/**
	 * check if any row selected
	 * 
	 * @param table_id
	 */
	function checkIfRowSelected(table_id){
		
		var selected_row = $('#'+table_id+' tbody tr.selected_row');
	    
	    var num_row = selected_row.length;
	    
	    // check if any row selected
	    if( num_row<1 ){
	    	$( "#selected_null_del" ).dialog({
	    		height: 'auto',
				modal: true,
				buttons:{
					" OK " : function() {
						$(this).dialog("close");
					}
				}
	    	});
	    	return false;
	    }
	    return true;
	}
	
	
	/**
	 * @param table_name
	 * @param num_row
	 * @param callback
	 */
	function dialogMessageDeleteRow( table_name, num_row, callback){
		
		var msg = null;
		
		msg = " You have selected "+num_row+" "+table_name+" row(s).";
		
		if(1 == num_row)
			msg += "This item will be permanently deleted. Confirm?";
		else
			msg += "These items will be permanently deleted. Confirm?";
		
		$("#delete_row_confirm .message").html(msg);
		
		$("#delete_row_confirm").dialog({
			title : "Delete selected "+table_name+" Row(s)?",
			height: 'auto',
			width: 'auto',
			modal: true,
			buttons: {
				" Delete ": function() {

					if(callback){
						callback();
					}
					$( this ).dialog( "close" );
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			},
			close: function(){
				$("#delete_row_confirm").dialog("destroy");
			}
		});
	}
	
	/** ** Show organization Details *** */
	function showOrgDetailsinHome(id){
		if(id==undefined || id==0) return;
		
		if(id==-1){
			id=0;
		}
		
		var elem_select = "#home_tab_content select[name=organization_id]";
		
		/* populate & select organization */
		var organization_obj = OrganizationScript.getInstance();
		var orga = organization_obj.fetchData
		organization_obj.populateInfo( $(elem_select), id, function(){
			if( $(elem_select+' option').length > 1 ){
				$(elem_select+' option:first').next().prop('selected',true);
			}else{
				console.log('Organization not found.');
			}
			// set organization
			setOrganizationId( $(elem_select).val() );
		});
	}


	
	/* Change password */
	function showChangePasswordDialog(){
		var formId = '#account_change_password';
		
		// reset form
		$( formId+" input" ).val('');
		
		// show dialog
		$( formId ).dialog({
			title : 'Change Password',
			height : 'auto',
			width : 'auto',
			buttons:{
				"Change" : function(e){
					
								$(e.currentTarget).button('disable');
								
								// validate
								var valid = true;
								var fv  = FormValidation;
								
								valid = valid && fv.checkEmpty( $(formId+" input[name=password]"), "Please Enter Your Current Password");
								valid = valid && fv.checkEmpty( $(formId+" input[name=new_password]"), "Please Enter a New Password");
								valid = valid && fv.checkEmpty( $(formId+" input[name=confirm_new_password]"), "Please Confirm New Password");
								
								var passEl = $(formId+" input[name=password]");
								var newPassEl = $(formId+" input[name=new_password]");
								var cnfPassEl = $(formId+" input[name=confirm_new_password]");
								
								if(valid){
									if( passEl.val() == newPassEl.val()){
										newPassEl.val('');
										cnfPassEl.val('');
										
										dialogMessageInvalid( " Please Enter a different password ", "Invalid Input", newPassEl );
										newPassEl.addClass( "ui-state-error" );
										valid = false;
									}
								}
								
								// check new pass and conf pass match
								if(valid){
									if( newPassEl.val() != cnfPassEl.val()){
										newPassEl.val('');
										cnfPassEl.val('');
										
										dialogMessageInvalid( " Passwords do not match ", "Invalid Input", newPassEl );
										newPassEl.addClass( "ui-state-error" );
										valid = false;
									}
								}
								
								if(!valid){
									$(e.currentTarget).button('enable');
									return;
								}
								
								// get user session cookie
								var user_session_str = $.cookie( "u_session");
								
								if(!user_session_str){
									dialogMessage('User session cookie not found', 'Change Password');
									return;
								}
								var user_session = JSON.parse(user_session_str);
								
								var jsonData = {
										"user_id" : user_session.ID, 
										"email": user_session.S_UID,
										"type": user_session.S_UTYPE,
										"password": passEl.val(),
										"new_password": newPassEl.val()
								};
								
								var inData = JSON.stringify(jsonData);
 								// var _url = ROOT_URL +
								// SysUserScript.URL+"/changePassword"; //TODO
								// enable later
								// var _url = ROOT_URL +
								// SYS_USER_URL+"/changePassword";
								var user_type = "";
								if(user_session.S_UTYPE=="sysuser")
									user_type = "SysUser";
								
								
								if(user_session.S_UTYPE=="Depot")
									user_type = "Depot";
								
								if(user_session.S_UTYPE=="GiEntity")
									user_type = "GiEntity";
								
								
								
								var _url = ROOT_URL+"/"+user_type+"DataHandler/changePassword";
								
								var com = new AjaxRequest();
								com.init({
									url: _url,
									data: inData,
									dataType: "json",
									type: "POST",
									contentType: "application/json"
								});
								com.send({ callback: function(status, response){
									if( status && response.message == "success"){
										dialogMessageSuccess("Password Successfully Changed", "Change Password");
										
										$( formId ).dialog("close");
									}else if( response.data && response.data.message_txt){
										dialogMessageFailed("Unable To Change Password."+response.data.message_txt, "Change Password");
									}
									else{
										dialogMessage("Unable To Change Password."+G_ERROR.getMessage(response.data.message), "Change Password");
									}
								}});
								
								window.setTimeout(function(){
									$(e.currentTarget).button('enable');
								}, 500);
								
				}, 
				"Cancel" : function(){
					$(this).dialog("close");
				}
			},
			close: function(){
				$( formId ).dialog("destroy");
			}
		});
	}
		
		
	/*------------------- function to trim input field ----------------------------*/    
		jQuery.fn.trimInputFields = function() {
		    var o = $(this); // It's your element
		    /*-------------remove spaces from both end--------------*/
			$("input[type=text],textarea",o).each(function(){
				var value=$.trim($(this).val());
				$(this).val(value);
			});
		};
	    
	/*---------------------------------- capitalize first letter of word ---------------------------------------------*/
		jQuery.fn.capsFirstLetter = function() {
		   
			var o = $(this);
			o.each(function(){
			    var str = $(this).val()+''.toLowerCase().trim();
			    var strVal = '';
		        str = str.split(' ');
		        length = str.length;
		        for (var chr = 0; chr < (length-1); chr++) {
		            strVal += str[chr].slice(0,1).toUpperCase() + str[chr].slice(1) + ' ';
		        }
		        strVal += str[length-1].slice(0,1).toUpperCase() + str[length-1].slice(1);
		        $(this).val(strVal);
    		 });
		};
		
		/**
		 * caps first letter of each word
		 * 
		 * @param words{name
		 *            string} eg: some name
		 * @returns {String}
		 */
		function capsFirstLetter(words){
			
			var str = words.toLowerCase().trim();
			if(str == '') return '';
			
		    var strVal = '';
	        str = str.split(' ');
	        length = str.length;
	        
	        for (var chr = 0; chr < (length-1); chr++) {
	            strVal += str[chr].slice(0,1).toUpperCase() + str[chr].slice(1) + ' ';
	        }
	        
	        strVal += str[length-1].slice(0,1).toUpperCase() + str[length-1].slice(1);
	        return strVal;
		}
		
		/**
		 * Add country code to mobile //TODO improve for other country
		 * 
		 * @param elem
		 *            {object}: target input element.
		 * @param c_code
		 *            {String}: country code.
		 * @param _length{number}:mobile
		 *            number length.
		 */
		function addCcToMobile( elem, c_code, _length ) {
			
			if( !_length){
				_length = DEFAULT_MOBILE_NO_LENGTH;
			}
			if(!c_code){
				c_code = MOBILE_NO_COUNTRY_CODE;
			}
			var o = elem;
			o.each(function(){
					var mobile_no = $(this).val();
					var mob_no = "";
					if( mobile_no.length )
						mob_no = mobile_no;
					
					mob_no = mob_no.replace(/\s/g,"");
					
					if(  mob_no.length && mob_no.length >= _length && mob_no.length <= ( _length + c_code.length ) )
					{
							if( mob_no.length == _length)
							{
								mob_no = c_code+mob_no;
							}
							else if( c_code.replace(/\+/g,'') == mob_no.replace(/\+/g,'').slice(0,c_code.length) && mob_no.length <= ( _length + c_code.length -1))
							{
								mob_no = '+'+c_code+mob_no.replace(/\+/g,'');
							}
							else if( 0 == mob_no.slice(0,1) && mob_no.length == ( _length + 1 ))
							{
								mob_no = mob_no.replace("0","");
								mob_no = c_code+mob_no;
							}else
							{
								$(this).focus();							// need
																			// to
																			// review
							}
							
					}else
					{
						$(this).focus();							// need to
																	// review
					}
					$(this).val(mob_no);
			});
			
		};
	   
		/**
		 * Tab Function: function to add new tab or to show current tab
		 * 
		 * @param link{id}:
		 *            element that triggered the action
		 * @param showContent{boolean}:
		 *            show the tab content or not
		 */
	    function addTab(link, showContent) {
		console.log('TCL: addTab -> addTab CALLED ADD TAB')
		
			// If tab already exist in the list, return
			if ( $("#" + $(link).attr("rel")).length != 0){
			    $(".tblContainer").hide();
			    // Get the tab name
			    var contentname = $(link).attr("rel") + "_content";
			    // hide all other tabs
			    $("#tab_bar .tabs li").removeClass("current");
			    // show current tab
			    $("#" + contentname).show();
			    
			    $("#tab_bar .tabs li").removeClass("current");
			    // $('li #'+$(this).attr('rel')).addClass("current");
			    $('li #'+$(link).attr('rel')).parent().addClass("current");
			    
			    // show tool buttons
			    $("#tab_tool .toolBtns").hide();
			    $('#'+$(link).attr("rel")+'_toolBtns').show();
			    
			    if($("#tabs-header .tabs-scroller-right").is(":visible")){
			    	var tab_pos = $('#tab_bar .tabs li.current').position();
			    	var tab_holder_pos = $('#tab_bar div:first').position();
			    	var actual_pos = (tab_pos.left + tab_holder_pos.left);
			    	var tab_pos_right = actual_pos + $('#tab_bar .tabs li.current').width() + 2;
			    	
			    	if( actual_pos >= 18 && tab_pos_right <= $('#tab_bar').width() ){
			    		var last_tab_pos = $('#tab_bar .tabs li:last').position().left;
			    		var act_pos = $('#tab_bar div:first').position().left + last_tab_pos;
			    		var act_pos_rt = act_pos + $('#tab_bar .tabs li:last').width()+2;
			    		var move_rt = $('#tab_bar').width() - act_pos_rt;
			    		if( act_pos_rt < $('#tab_bar').width() ){// move
																	// right
			    			$("#tab_bar div:first").animate({"left": "+="+(move_rt)+"px"}, 10);
			    			return;
			    		}
			    		var tab_cur_pos = $('#tab_bar div:first').position().left;
			    		if( tab_cur_pos  > 18){// move left
			    			var move_left = tab_cur_pos - 18 ;
			    			$("#tab_bar div:first").animate({"left": "-="+(move_left)+"px"}, 10);
			    			return;
			    		}
			    		return;
			    	}else if( tab_pos_right > $('#tab_bar').width() ){	// move
																		// left
			    		if( tab_pos_right > $('#tab_bar').width() ){	// if
																		// not
																		// visible
			    			var move_left = tab_pos_right - $('#tab_bar').width();
			    			$("#tab_bar div:first").animate({"left": "-="+(move_left)+"px"}, 10);
			    			return;
			    		}
			    	}else{	// move right
			    		var tab_pos_left = actual_pos;
			    		var move_right = -(tab_pos_left)+20;
			    		$("#tab_bar div:first").animate({"left": "+="+(move_right)+"px"}, 10);
			    		return;
			    	}
			    }
			    
			    return;
			}

			// hide other tabs
			$("#tab_bar .tabs li").removeClass("current");
			$(".tblContainer").hide();
			// hide tool buttons
		    $("#tab_tool .toolBtns").hide();
		    
			// add new tab and related content
			var newTab = $("<li class='current'><a class='tab' id='" +
				    $(link).attr("rel") + "' rel='" + $(link).attr("rel") + "' href='#'>" + $(link).html() +
				    "</a><a href='#' class='remove'><div class='x'>x</div></a></li>");
			$("#tab_bar .tabs").append(newTab);
			
			/* *Tab enents* */
			// click event on tab function
			newTab.find('a.tab').on('click', function() {
			    
			   $(".tblContainer").hide();
			   
			   // Get the tab name
			    var contentname = $(this).attr("rel") + "_content";
			    var rel = $(this).attr("rel");
			    // hide all other tabs
			    $("#tab_bar .tabs li").removeClass("current");
			    
			    // show current tab
			    $("#" + contentname).show();
			    $(this).parent().addClass("current");
			    
		    	// show tool buttons
			    $("#tab_tool .toolBtns").hide();
			    $('#'+rel+'_toolBtns').show();
			});
			
			// click event tab remove button
			newTab.find('a.remove').on('click',function() {
			    var tab_length = $(this).parent().outerWidth(true);
			    var prev_tab = $(this).parent().prev();
			    // Get the tab name
			    var tabid = $(this).parent().find(".tab").attr("id");
			    // remove tab and related content
			    var contentname = tabid + "_content";
			    $("#"+contentname).remove();
			    $(this).parent().remove();
			    
			    // if there is no current tab and if there are still tabs left,
				// show prev tab
			    if ($("#tab_bar .tabs li.current").length == 0 && $("#tab_bar .tabs li").length > 0) {
			    	// find the first tab
			    	$(prev_tab).addClass("current");
			    	
			    	// get its link name and show related content
			    	var prev_tab_id = $('a.tab', prev_tab).attr('id');
			    	$("#" + prev_tab_id + "_content").show();
			    	
			    	// hide tool bar
			    	// show tool buttons
				    $("#tab_tool .toolBtns").hide();
				    
				    $('#'+prev_tab.find('a.tab').attr('rel')+'_toolBtns').show();
			    }
			    
			    if($("#tab_bar .tabs li").length == 1){
			    	$("#tab_tool .toolBtns").hide();
				    $('#'+$('#tab_bar .tabs li a.tab').attr('rel')+'_toolBtns').show();
			    }
			    
			    if( $("#tabs-header .tabs-scroller-right").is(":visible") ){
			    	
					if( $("#tab_bar .tabs").outerWidth(true) < $("#tab_bar").innerWidth() ){
						var move_length = 0;
						$(".tab_scroller").hide();
						
						var tab_bar_width = $("#tab_bar").width();
						tab_bar_width += 36;
						$("#tab_bar",$("#tabs-header")).css({"margin-left":"0px", "margin-right":"0px", "width": tab_bar_width+"px"});
						
						move_length = $("#tab_bar div:first",$("#tabs-header")).position().left;
						move_length = -(move_length);
						$("#tab_bar div:first",$("#tabs-header")).animate({"left": "+="+(move_length)+"px"}, 10);
						
					}else{
						var move_length = tab_length;
						var pos_left = $("#tab_bar div:first",$("#tabs-header")).position().left;
						if( ( pos_left + tab_length) > 18){
							move_length =  18 - pos_left;
						}
						$("#tab_bar div:first",$("#tabs-header")).animate({"left": "+="+(move_length)+"px"}, 10);
					}
				}
			});
			
			
			if( newTab.parent().outerWidth(true) > $("#tab_bar").innerWidth() ){
				var move_length = 0;
				if($("#tabs-header .tabs-scroller-right").is(":visible")){ 
					/* move left */
					move_length = $('#tab_bar .tabs').width() - ( $('#tab_bar').width() -  $('#tab_bar div:first').position().left ) -18 + 5;
					var new_pos = $('#tab_bar div:first').position().left - move_length;
					var new_move_length = $('#tab_bar .tabs').width() - ( $('#tab_bar').width() -  new_pos ) -18 + 5;
					if( new_move_length < 0){
						move_length = move_length + new_move_length;
					}
					$("#tab_bar div:first",$("#tabs-header")).animate({"left": "-="+(move_length)+"px"}, 10);
				}else{
					var tab_bar_width = $("#tab_bar").width();
					tab_bar_width -= 36; 
					$("#tab_bar",$("#tabs-header")).css({"margin-left":"18px", "margin-right":"18px", "width": tab_bar_width+"px"});
					move_length = newTab.parent().width(); 
					move_length = move_length - $("#tabs-header").width() + $("#tabs-header .tabs-scroller-right").outerWidth(true) + 5;
					$("#tab_bar div:first",$("#tabs-header")).animate({"left": "-="+(move_length)+"px"}, 10);
					$(".tab_scroller",$("#tabs-header")).show();
				}
			}
			
			// $("#tab_content").append("<p id='" + $(link).attr("rel") +
			// "_content'></p>");
			var rel = $.trim($(link).attr("rel"));
			if(showContent){
				showTabContent(rel);
			}
		} // End tab
	    
	    
	    /**
		 * Show the Tab Content: remove old data and reload the tab data
		 * 
		 * @param rel
		 */
	    function showTabContent(rel){
	    	if( $('#'+rel+'_content').length != 0)
	    		$('#'+rel+'_content').remove();
	    	
	    	// if tab is not open
	    	if( !($("#tab_bar .tabs").has('#'+rel)) ){
	    		console.log("tabs not created : call create tab to create a new tab");
	    		return;
	    	}
	    	
	    	$('#table_ajax_loader').show();
	    	
			console.log('TCL: showTabContent -> rel', rel)
			// Add a case for different tab.
			switch(rel){
						
					// Organization
					case OrganizationScript.REL : // TODO send sys_user id for
													// filtering org on user
						/* create data table */
						var organization_obj = OrganizationScript.getInstance();
						organization_obj.loadTabContent();
						break;
																	
					case RoleScript.REL :
						var role_obj = RoleScript.getInstance();
						role_obj.loadTabContent();
						break;
					
					case SysUserScript.REL :
						var sysuser_obj = SysUserScript.getInstance();
						sysuser_obj.loadTabContent();
						break;
						
					case CurrencyScript.REL :
					 	var currency_obj = CurrencyScript.getInstance();
					 	currency_obj.loadTabContent();
					 	break;	

					case CountryScript.REL :
						var country_obj = CountryScript.getInstance();
						country_obj.loadTabContent();
						break;

					case CityScript.REL :
						var city_obj = CityScript.getInstance();
						city_obj.loadTabContent();
						break;

					case AnalyticsScript.REL :
						var analytics_obj = AnalyticsScript.getInstance();
						analytics_obj.loadTabContent();
						break;
					
					case SubscriberScript.REL :
						var subscriber_obj = SubscriberScript.getInstance();
						subscriber_obj.loadTabContent();
						break;	
						// --add option for other tables
					default:
			 			// showToolbuttons(0);
			 			showCurrentTabContent();
			 			alert("Sorry, This feature hasnt been implemented at the moment..!");
			 			return;
					 			
				 }  
				console.log('showing content: '+ rel + "_content");			
		}
	    // --------End Tab Content creation--------------------
	    
	    function showCurrentTabContent(){
	    	$('#table_ajax_loader').hide();
	    	
	    	// get rel attribute of selected tab
	    	var rel = $('#tab_bar .tabs li.current a:first').attr('rel');
	    	$("#tab_content .tblContainer").hide();
	    	
	    	try{
	    		// show content of selected tab
	    		$("#" + rel + "_content").show();
	    	}catch(e){
	    		console.log('There is no tab content');
	    	}
	    }

	    // Close all tab
	    function CloseAllTabs(){
	    	// remove tabs
	    	$('#tab_bar .tabs > li:first').nextAll().remove();
	    	$('#tab_bar .tabs > li:first').toggleClass('current', true);
			
	    	// remove body
	    	$('#home_tab_content').nextAll().remove();
			$('#home_tab_content').show();
			
			// reposition home tab
			var move_length = 0;
			var tab_bar_width = $("#tab_bar").width();
			
			if($('#tabs-header .tab_scroller').is(':visible')){
				$('#tabs-header .tab_scroller').hide();
				tab_bar_width += 36;
			}
						
			$("#tab_bar",$("#tabs-header")).css({"margin-left":"0px", "margin-right":"0px", "width": tab_bar_width+"px"});
			
			move_length = $("#tab_bar div:first",$("#tabs-header")).position().left;
			move_length = -(move_length);
			$("#tab_bar div:first",$("#tabs-header")).animate({"left": "+="+(move_length)+"px"}, 10);
			
			// hide tool bar
			$("#tab_tool .toolBtns").hide();
	    }
		
/*-----------------------------ERROR MESSAGE------------------------------*/
		function LoadErrordialog(json){
			var message = 'Please add some data. No data available to Display.'+getErrorMessage(json.message);
			var title = "Error in Loading Data";
			dialogMessage( message, title );
			return;
		}
		
/*------------------------Dialog message----------------------------------*/

		/**
		 * @param message :
		 *            Message to be displayed.(text)
		 * @param title:
		 *            Title Header of the dialog.
		 */
		function dialogMessage(message, title){
				
				$("#messageid" ).html('<p><span class="ui-icon ui-icon-info" style="float: left; margin: 0 7px 20px 0;"></span>'+message+'</p>');	// can
																																					// be a
																																					// method
				
				$( "#messageid" ).dialog({
					title : title,
					height : 'auto',
					width : 'auto',
					modal: true,
					buttons:{
							" OK " : function() {
								$(this).dialog("close");
							}
					},
					close: function(){
						$(this).dialog('destroy');
					}
				
				});
		
		}
		
		function dialogMessageWarning( message, title ){
			
			$("#messageWarningId span.message" ).html( message);
			
			$( "#messageWarningId" ).dialog({
				title : title,
				height : 'auto',
				width : 'auto',
				modal: true ,
				buttons:{
					"OK" : function(){
						$(this).dialog("close");
					}
				},
				close: function(){
					$(this).dialog("destroy");
				}
		
			});

		}
		
		function dialogMessageSuccess(message, title){
			
			$("#messageSuccessId" ).html('<p><span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 20px 0;"></span></p>');	// can
																																					// be a
																																					// method
			$("#messageSuccessId p" ).append(message);
			
			$( "#messageSuccessId" ).dialog({
				title : title,
				height : 'auto',
				width : 'auto',
				modal: true,
				buttons:{
					" Close " : function() {
						$(this).dialog("close");
					}
				}
				,close : function() {
					$(this).dialog("destroy");
				}
			});
	
		}
		
		
		function dialogAddSuccess( dialogObject ){
	

			
			var date = new Date( dialogObject.date ); // create date object
														// according to PI date
			var day = ("0" + date.getDate()).slice(-2);
			var month = ("0" + (date.getMonth() + 1)).slice(-2);
			var today = (day)+"-"+(month)+"-"+date.getFullYear();
			
			$("#messageAddSuccessId .label-main" ).text( dialogObject.labelMain );
			$("#messageAddSuccessId .label-number" ).text( dialogObject.labelNumber);
			$("#messageAddSuccessId .label-date" ).text( dialogObject.labelDate );
			$("#messageAddSuccessId .label-name" ).text( dialogObject.labelName );
	
			$("#messageAddSuccessId .number" ).text( dialogObject.number );
			$("#messageAddSuccessId .date" ).text( today );
			$("#messageAddSuccessId .name" ).text( dialogObject.name );
			$("#messageAddSuccessId .quantity" ).text( dialogObject.quantity );
			$("#messageAddSuccessId .currency-word" ).text( "In " + dialogObject.currencyWord );
			$("#messageAddSuccessId .amount" ).text( dialogObject.currencySymbol + " " + dialogObject.amount );
	
		}
		
		
		function dialogMessageFailed(message, title){
			
			$("#messageFailedId" ).html('<p><span class="ui-icon ui-icon-cancel" style="float: left; margin: 0 7px 20px 0;"></span></p>');	// can
																																			// be a
																																			// method
			$("#messageFailedId p" ).append(message);
			
			$( "#messageFailedId" ).dialog({
				title : title,
				height : 'auto',
				width : 'auto',
				modal: true,
				buttons:{
					" Close " : function() {
						$(this).dialog("close");
					}
				}
				,close : function() {
					$(this).dialog("destroy");
				}
			});
	
		}
		
		/**
		 * Dialog for form validation msg
		 * 
		 * @param message :
		 *            error message
		 * @param title :
		 *            tile
		 * @param elem:
		 *            form element to be focused
		 */
		function dialogMessageInvalid( message, title, elem ){
			
			$("#messageInvalidId" ).html('<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span></p>');	// can
																																			// be a
																																			// method
			$("#messageInvalidId p" ).append( message);
			
			$( "#messageInvalidId" ).dialog({
				title : title,
				height : 'auto',
				width : 'auto',
				modal: true ,
				buttons:{
					"OK" : function(){
						$(this).dialog("close");
					}
				},
				close: function(){
					if(elem != undefined)
						elem.focus();
					$(this).dialog("destroy");
				}
		
			});
			/*
			 * var widget = $( ".messageInvalidId" ).dialog( "widget" );
			 * widget.find('div.ui-dialog-buttonset');
			 */
		}
	
	
		/**
		 * Dialog box used oly for Passenger Details form
		 * 
		 * @param message
		 * @param title
		 * @param callback:
		 *            function
		 */
		function dialogMessageDelete( message, title, callback ){
		
			$("#delete_confirm span.message" ).html(message);	
		
			$( "#delete_confirm" ).dialog({
				title : title,
				height : 'auto',
				width : 400,
				modal: true ,
				buttons:{
					"Delete" : function(){
						if(callback) callback();
						$(this).dialog("close");
					},
					"Cancel" : function(){
						$(this).dialog("close");
					}
				},
				close: function(){
					$(this).dialog("destroy");
				}
		
			});

		}
	
	
		
	/*
	 * Send Email Dialog Form
	 * 
	 * @param email_id : an Email id / array of Email id.
	 */
	function dialogSendEmail(email_id){
		var email = '';
		
		if( typeof email_id == 'object'){
			email_id = uniqueArray(email_id);
			$.each( email_id, function(index,value){
				email += value;
				if(email_id.length >1 && index+1 != email_id.length)
					email +=';';
			});
		}else{
			email = email_id;
		}
		
		$('input[name=email]', $( "#sendEmailForm" )).val(email); 
		$( "#sendEmailForm" ).dialog({
			title : 'Send Email',
			height : 'auto',
			width : 'auto',
			buttons:{
				"Send" : function(e){
					
								$(e.currentTarget).button('disable');
								
								// validate
								var valid = true;
								
								valid = valid && checkEmpty($("#sendEmailForm input[name=email]"), "Please Enter Email Id");
								valid = valid && checkEmpty($("#sendEmailForm input[name=subject]"), "Please Enter Email Subject");
								valid = valid && checkEmpty($("#sendEmailForm textarea[name=message_body]"), "Please Enter Email Body");
								
								if(!valid){
									$(e.currentTarget).button('enable');
									return;
								}
								
								$( "#sendEmailForm .ajax_loader_img" ).show();
								// email id
								var emails = $("#sendEmailForm input[name=email]").val();
								$("#sendEmailForm input[name=email]").attr('disabled', true);
								// subject
								var sub = $("#sendEmailForm input[name=subject]").val();
								$("#sendEmailForm input[name=subject]").attr('disabled', true);
								// body
								var body = $("#sendEmailForm textarea[name=message_body]").val();
								$("#sendEmailForm textarea[name=message_body]").attr('disabled', true);
								
								var jsonData = {
										"email": emails,
										"subject": sub,
										"body": body
								};
								
								var inData = JSON.stringify(jsonData);
								
								var _url = ROOT_URL + "/EmailHandler/send", TYPE = "POST";
								
								var com = new AjaxRequest();
								com.init({url:_url, type: TYPE, data: inData});
								com.send({ callback: function(status, response){
									
									if(status && response.data.message == 0){
										dialogMessage("Email has been sent successfully.", "Email");
										// enable form inputs
										$("#sendEmailForm input[name=email]").attr('disabled', false);
										$("#sendEmailForm input[name=subject]").attr('disabled', false);
										$("#sendEmailForm textarea[name=message_body]").attr('disabled', false);
										
										$( "#sendEmailForm .ajax_loader_img" ).hide();
										$( "#sendEmailForm" ).dialog("close");
									}else{
										dialogMessage("Unable To send Email.", "Email");
										$( "#sendEmailForm .ajax_loader_img" ).hide();
										
										// enable form inputs
										$("#sendEmailForm input[name=email]").attr('disabled', false);
										$("#sendEmailForm input[name=subject]").attr('disabled', false);
										$("#sendEmailForm textarea[name=message_body]").attr('disabled', false);
									}
								}});
								
								window.setTimeout(function(){
									$(e.currentTarget).button('enable');
								}, 500);
								
				}, 
				"Cancel" : function(){
					$(this).dialog("close");
				}
			},
			close: function(){
				
			}
		});
	}
	
	
	/*
	 * Send Sms Dialog Form
	 * 
	 * @param mobile_no || mobile_no[]
	 */
	function dialogSendSms(mobile_no){
		var mobile='';
		
		if( typeof mobile_no == 'object'){
			mobile_no = uniqueArray(mobile_no);
			$.each(mobile_no, function(index,value){
				mobile += value;
				if(mobile_no.length >1 && index+1 != mobile_no.length)
					mobile +=';';
			});
			
		}else{
			mobile = mobile_no;
		}
			
		$('input[name=mobile_no]', $( "#sendSmsForm" )).val(mobile);
		
		$( "#sendSmsForm" ).dialog({
			title : 'Send Sms',
			height : 'auto',
			width : 'auto',
			buttons:{
				"Send" : function(){
					$(this).dialog("close"); // TODO remove later
					return;
					
					// validate
					var valid = true;
					
					valid = valid && checkEmpty($("#sendSmsForm input[name=mobile_no]"), "Please Enter Mobile Number");
					valid = valid && checkEmpty($("#sendSmsForm textarea[name=message_body]"), "Please Enter Mobile Number");
					
					if(!valid){
						return;
					}
					// mobile no
					var mobile = $("#sendSmsForm input[name=mobile_no]").val();
					// subject
					var msg = $("#sendSmsForm textarea[name=message_body]").val();
					
					var jsonData = {
							"mobile_no": mobile,
							"message": msg
					};
					
					var inData = JSON.stringify(jsonData);
					
					var _url = ROOT_URL + "/GatewaySms/send", TYPE = "POST";
					var com = new AjaxRequest();
					com.init({url:_url, type: TYPE, data: inData});
					com.send({ callback: function(status, response){
						
						if(status && response.data.message == 0){
							alert("Sms Successfully Sent");
							$( "#sendEmailForm" ).dialog("close");
						}else{
							alert("Unable To send Sms.");
						}
					}});
				},
				"Cancel" : function(){
					$(this).dialog("close");
				}
			},
			close: function(){
			}
		});
	}

	function latlong_btn(){
		$('.latlongbtn').on("click", function(e){

			if($("#lat").val() != 0 && $("#long").val() != 0)
			{
				var lat = $("#lat").val();
				var long = $("#long").val();
				//alert("lat");
				window.open('https://www.google.com/search?q='+ lat +','+ long +'&z=16','_blank');
			}
		})

		$('.latlongbtn2').on("click", function(e){

		if($("#long2").val() != 0 && $("#lat2").val() != 0)
			{
				var long2 = $("#long2").val();
				var lat2 = $("#lat2").val();
				//alert("lat2");
				window.open('https://www.google.com/search?q='+ lat2 +','+ long2+'&z=16','_blank');
			}
			
		})

		$('.latlongbtn3').on("click", function(e){
			if($("#long3").val() != 0 && $("#lat3").val() != 0)
			{
				var long3 = $("#long3").val();
				var lat3 = $("#lat3").val();
				//alert("lat3")
				window.open('https://www.google.com/search?q='+ lat3 +','+ long3+'&z=16','_blank');
			}
		})
	} 
	
	function latlong_enter(){
		$('.latlong').on("click", function(){
			var box_title = "Enter/Paste Latitude,Longitide Here";
			latlongtext($(this),box_title);
		})
	}

	function attachTextEditorEvent(){
		$('.enableTextEditor').on("click", function(){
			var box_title = this.name.toUpperCase();
			box_title = box_title.replace(/[_-]/g, " ");
			//alert(box_title);
			editText($(this),box_title);
		})
	}

	// 
	// Method is used to edit the text of an textArea ( change to input also ) : used in organization_parameters 
	function editText(element, box_title){
		$("#textEditor textarea.content").val(element.val());
		//console.log(box_title);
		$("#textEditor").dialog({
			title : box_title,
			height : 'auto',
			width : 'auto',
			modal: 'true',
			buttons:{
				"Save" : function(){
					element.val( $("#textEditor textarea.content").val());
					$(this).dialog("close");
					return;	
					
				},
				"Cancel" : function(){
					$(this).dialog("close");
				}
			},
			close: function(){
				$("#textEditor").dialog("destroy");
				
			}
		});
	}

// Method is used to edit the text of an textArea ( change to input also ) : used in organization_parameters 
function latlongtext(element, box_title){
	var maindata= $("#textEditorlatlong textarea.content").val(element.val());
	//var maindata1 = element.val();
	//console.log(maindata1);
	//var lat = maindata.val().split(',')[0]; 
	//console.log(lat);
	$("#textEditorlatlong").dialog({
		title : box_title,
		height : 'auto',
		width : 'auto',
		modal: 'true',
		buttons:{
			"Save" : function(){
				//$("#lat").val(lat);
				var latu = element.val( $("#textEditorlatlong textarea.content").val());
				var latuvalue = latu[0].value;
				
				var lat = latuvalue.split(",")[0];//latuvalue.substring(0,latuvalue.indexOf(","));
				var long = latuvalue.split(",")[1]; //latuvalue.split[","][0];
				console.log(lat);
				console.log(long);
				$("#long").val(long);
				$("#lat").val(lat);

				$("#long2").val(long);
				$("#lat2").val(lat);

				$("#long3").val(long);
				$("#lat3").val(lat);

				$(this).dialog("close");
				return;	
				
			},
			"Cancel" : function(){
				$(this).dialog("close");
			}
		},
		close: function(){
			$("#textEditorlatlong").dialog("destroy");
			
		}
	});
}

	
	
	function dialogGetLocation(elem_lat, elem_lon){
		
		var old_latlon = elem_lat.val()+','+elem_lon.val();
		$('input[name=lat_long]', $("#getLocationForm" )).val(old_latlon);
		
		$(document).off('click', '#getLocationForm a._show_on_Map');
		$(document).on( 'click', '#getLocationForm a._show_on_Map', function(e){
			e.preventDefault();
			var zoom = 16;
			var link = 'https://maps.google.co.in',
				latlon = $.trim( $('input[name=lat_long]', $("#getLocationForm" )).val());
			if(latlon != ''){
				latlon = latlon.replace(/\s/g,"");
				var latlonarr = latlon.split(/\,|\;/);
				if( latlonarr.length == 2 && ( parseInt(latlonarr[0],10)!=0 || parseInt(latlonarr[1])!=0 ) )
					link += '?q='+latlon+'&z='+zoom;
			}
			var newtab = window.open();
				newtab.location = link;
		});
		
		$( "#getLocationForm" ).dialog({
			title : 'Mark Location',
			height : 'auto',
			width : 'auto',
			buttons:{
				"Set" : function(){
								var location = $('input[name=lat_long]', $("#getLocationForm" )).val();
								location = location.replace(/\s/g,"");
								
								if( location == '' || location == ',' || location == ';')
									return;
								
								var location_arr = location.split(/\,|\;/);
								if( location_arr.length != 2){
									alert('Please enter location like- 12.1233,77.2345');
									
								}else{
										var lat = location_arr[0].split('.');
										
										if( lat.length==2 && lat[1].length >8){
												lat[1] = lat[1].slice(0,8);
										}
										
										var lon = location_arr[1].split('.');
										
										if( lon.length==2 && lon[1].length >8){
											lon[1] = lon[1].slice(0,8);
										}
									
										elem_lat.val( lat.join('.'));
										elem_lon.val( lon.join('.'));
										
										$(this).dialog("close");
								}
								
				},
				"Cancel" : function(){
								$(this).dialog("close");
				}
			},
			close: function(){
				$(this).dialog("destroy");
			}
		});
	}
	
// convert string to array and filter--- only for received table data
	  /*
		 * function convertStringToArray(json) {
		 * 
		 * $.each(json.values, function(index, value){
		 * value=value.replace("[",""); //only for dataTable Data
		 * value=value.replace("]",""); json.values[index]=value.split(",");
		 * json.values[index] = placeSymbolBackUtil(json.values[index]);
		 * 
		 * }); }
		 */

	    
		// place comma(,) in place of(~) // 2d array array[][];
		function placeSymbolBackUtil(values) {
		 	
			 /*
				 * $.each(values, function(index, value){ if(value != null &&
				 * value != ''){ value = value.replace(REG_REPLACE_CPMMA,",");
				 * //only for dataTable Data value =
				 * value.replace(REG_REPLACE_QUOTE,"\""); //only for dataTable
				 * Data value = $.trim(value); values[index] = value =="null"?
				 * "":value; } });
				 */
			
		 	return values;
		}
		
		/*-------------------- replace comma(,) with a space (" ") from input fields ------------------------*/		
		jQuery.fn.replaceSymbolUtils = function() {
			/*
			 * var o = $(this);
			 * 
			 * $("input[type=text], textarea",o).each(function(){ var str =
			 * $(this).val(); str = str.replace(/,/g,REPLACE_CPMMA_STR); str =
			 * str.replace(/"/g,REPLACE_QUOTE_STR); $(this).val(str);
			 * 
			 * });
			 */
			
		}

			    
	    

/*---------------------other methods-----------------------*/
	
	// get organisation id from session.
	function getOrganizationId(){         // TODO get to cookies
	     // get organisation via global var info
	     return DEFAULT_ORGANIZATION.id;
	}
	
	// get current user id from cookies
	function getCurLogUserId(){
		$.cookie.json = false;
		
		var _session = $.cookie('u_session');
		
		if(_session){
			var sessionObj = JSON.parse(_session);
			var userId = sessionObj.ID;
			
			if(userId){
				return parseInt(userId);
			}
		}
		console.error("Current user id not found");
		return 0;
	}
	
	// attach the autoComplete event to the input fields
	function attachProductAutocomplete(formId, callback){
		let autoComplete;
		let dialog =  $("#" + formId);
		let input = $("input[name=product_code]", dialog);

		ProductScript.getInstance().fetchData({
			format: JSON_FORMAT_KEY_VALUE_PAIR,
			callback: function(status, response){
				if(status == true && response.status_code > 0 ){
					let productCodeArray = response.data.values.map(product => product.mProductCode);
					let productbilling_name = response.data.values.map(product => product.mBillingProductName);
					let displayNameArray = response.data.values.map(product => product.mDisplayName);
					 
					var finalArray = [];
					
/*					
					 productCodeArray.forEach(function(){
						 for(var i=0; i < productCodeArray.length; i++)
							 {
							 var product_name = productbilling_name[i];
							 //var product_codearray = productCodeArray[i] + " :- " + product_name ;
							 var productDetails = productCodeArray[i].concat(" :- " + product_name);
							 console.log(productDetails);
							 finalArray.push(productDetails);
							 //new array = product_codearray;
							 }
					 });
*/
					finalArray = displayNameArray;
					// initialize autocomplete
					input.autocomplete({
						// source: ["HELLO", "WORLD"],
						source: finalArray,
						minLength: 3,
						open: function () {
							autoComplete.zIndex(dialog.zIndex()+1);
						},
						select: callback
					});
					
			
					// get reference to autoComplete widget
					autoComplete = input.autocomplete("widget");
					// move the autocomplete element after the dialog in the DOM
					autoComplete.insertAfter(dialog.parent());
				}
					 else{
					dialogMessage("Autocomplete Failed", "Auto Complete Error")
				}
				
			}
		})
		
	}

	// attach the autoComplete event to the input fields
	function attachProformaAutocomplete(formId, callback){
		// console.log('TCL: attachProductAutocomplete -> itemsArray',
		// itemsArray)
		// get reference to autocomplete element
		let autoComplete;
		let dialog =  $("#" + formId);
		let input = $(`input[name=${ formId == "edit_proforma_invoice_form"? "old_":"" }proforma_invoice_number]`, dialog);

		let proformaInvoiceNumberArray = ProformaInvoiceScript.getInstance().getStorageData().data.values;
		proformaInvoiceNumberArray = proformaInvoiceNumberArray.map(pi => pi[ProformaInvoiceScript.INDEX.PROFORMA_INVOICE_NUMBER - 1])
		console.log(proformaInvoiceNumberArray)
		input.autocomplete({
			source: proformaInvoiceNumberArray,
			// source: productCodeArray,
			minLength: 4,
			open: function () {
				autoComplete.zIndex(dialog.zIndex()+1);
			},
			select: callback
		});
		// get reference to autoComplete widget
		autoComplete = input.autocomplete("widget");
		// move the autocomplete element after the dialog in the DOM
		autoComplete.insertAfter(dialog.parent());
	
	}

	/**
	 * Get user role ID from cookie
	 */
	function getUserRoleIDFromCookie(){
		$.cookie.json = false;
		var user_session_str = $.cookie( "u_session");
		
		if(!user_session_str){
			dialogMessage('User session cookie not found', 'User Role');
			return;
		}
		var user_session = JSON.parse(user_session_str);
		
		var role = user_session.S_ROLE;
		
		if(role){
			return parseInt(role);
		}
		return 0;
	}
	
	/**
	 * set user role object in cookie for future reference
	 * 
	 * @param role
	 *            {object}: user role object
	 */
	function setUserRoleInCookie(role){
		if(role){
			var cOptions = {
					path : '/',
					expires : 1,
					secure : false
			};
			
			$.cookie( "U_USER_ROLE", JSON.stringify(role), cOptions );
		}else{
			// alert pass role
		}
	}
	
	/**
	 * @returns user role object from cookie
	 */
	function getUserRoleFromCookie(){
		$.cookie.json = false;
		var user_role_str = $.cookie( "U_USER_ROLE");
		
		if(!user_role_str){
			dialogMessage('User Role cookie not found', 'User Role');
			return;
		}
		var user_role = JSON.parse(user_role_str);
		
		if(user_role){
			return user_role;
		}
		return null;
	}
	
	// Set organization
	function setOrganizationId(organization_id){  // TODO set to cookies base
													// on user id
		
		if(organization_id==undefined ||organization_id==0){
			// reset
			DEFAULT_ORGANIZATION = {};
			DEFAULT_ORGANIZATION.id = 0;
			$("#organization_details_header").text('Organization not selected');
		}
		var organization_obj = OrganizationScript.getInstance();
		
		organization_obj.fetchData({
			organization_id : organization_id,
			callback: function(status, response){
				if(status){
					// console.log("ORGANIZATION RESPONSE",response)
					if( response.data && response.data.values){
						
						var org_data = response.data.values.find((org) => org[0] == organization_id)
	
					
						if(org_data != undefined && org_data.length>0){
							 // set organisation global info
						     DEFAULT_ORGANIZATION.id = organization_id;
						     DEFAULT_ORGANIZATION.name = org_data[OrganizationScript.INDEX.ORGANIZATION_NAME-1];
						     DEFAULT_ORGANIZATION.logo_file = org_data[OrganizationScript.INDEX.LOGO_LOCATION-1];
						     
						    // console.log( DEFAULT_ORGANIZATION.name + " ID=" +
							// DEFAULT_ORGANIZATION.id );
						    // console.log( org_data );
						     
						     
						     DEFAULT_ORGANIZATION.orgObj = org_data;		// save
																			// the
																			// organization
																			// selected
																			// for
																			// later
																			// use
						     
						     // set org name to header
						     $("#organization_details_header").text(DEFAULT_ORGANIZATION.name);
						     
						     // $("#organization_wellcome_text").text(DEFAULT_ORGANIZATION.name);
						     
						     // TODO may add a path in setting
						     if( DEFAULT_ORGANIZATION.logo_file != null && DEFAULT_ORGANIZATION.logo_file!= ''){
						    	 var logo_file = 'images/logo/'+ DEFAULT_ORGANIZATION.logo_file;
							     $('table.wrapper th div.rBanner .organization_logo').css({'background-image' : 'url('+logo_file+')'});
						     }
						     
						     // set country code
						     var country_obj = CountryScript.getInstance();
						     country_obj.fetchData({
						    	 country_id:
						    		 org_data[OrganizationScript.INDEX.COUNTRY_ID-1],
						    		 callback: function(status, response){
						    			 if(status && response.data.values){
						    				 var country_code = response.data.values[0][CountryScript.INDEX.COUNTRY_CODE-1];
						    				 if(country_code != '' && country_code != null ){
						    					 DEFAULT_ORGANIZATION.country_code = country_code;
						    					 DEFAULT_ORGANIZATION.country_name = response.data.values[0][CountryScript.INDEX.COUNTRY-1];
												 DEFAULT_ORGANIZATION.no_of_digits = response.data.values[0][CountryScript.INDEX.NO_OF_DIGITS-1];
						    					 MOBILE_NO_COUNTRY_CODE = country_code;
						    				 }else{
						    					 console.log('Country code not set');
						    				 }
						    			 }else{
						    				 console.log('Country code not set');
						    			 }
						    		 }
						     });
						     // city
						     var city_obj = CityScript.getInstance();
						     city_obj.fetchData({// TODO update
						    	 city_id: org_data[OrganizationScript.INDEX.CITY_ID-1],
						    	 callback: function(status, response){ 
						    		 if(status && response.data.values){
						    			 DEFAULT_ORGANIZATION.city_name = response.data.values[0][CityScript.INDEX.CITY-1];
						    		 }else{
						    			 console.log('City not set');
						    		 }
						    	 }
						     });
						     
						}else{
							dialogMessageWarning('Organization Data not found for id '+organization_id, 'Default Organization Selection');
							// reset
							DEFAULT_ORGANIZATION = {};
							DEFAULT_ORGANIZATION.id = 0;
							$("#organization_details_header").text('Organization not selected');
						}
					}
				}
			}
		});
	}
	
	/*------ Change first lettet of words to uppercase*/
	function capitalize(str) {
		    var strVal = '';
		    str = str.split(' ');
		    length = str.length;
		    for (var chr = 0; chr < length; chr++) {
		        strVal += str[chr].slice(0,1).toUpperCase() + str[chr].slice(1) + ' ';
		    }
		    return strVal;
	}

	/**
	 * Get New id for adding new value. Returns max+1 of specified row.
	 * 
	 * @param data{local
	 *            data object} data {data.json.values[] used}
	 * @param id_index{int}
	 *            index of column( starts with 1)
	 * @returns {Number} 1+max val
	 */
	function getNewId(data, id_index ){
		var NewId=0;
		var maxVal=0;
		id_index = (id_index - 1);
		if ( data!= null && undefined !== data.json && id_index >= 0){
			 $.each(data.json.values, function(index, value){
				 id = $.trim(value[id_index]);
				 nxtVal=parseInt(id,10);	// value[0]- fist value of the
											// array.
				 if(maxVal<nxtVal) maxVal=nxtVal;
			 });
		}else maxVal=0;
		NewId = (maxVal + 1);
		
		return NewId;
		
	}
	
	/**
	 * Get Names by Id (2d data_arr )
	 * 
	 * @param id_index :
	 *            index of id column (starts with 1)
	 * @param name_index:
	 *            index of name column (starts with 1)
	 * @param id:
	 *            the id for which name is required
	 * @param data_arr:
	 *            array of data(2d)
	 * @returns {String}
	 */
	function getNameById( id_index, name_index, id, data_arr ) { // index -
																	// array
																	// index
		
		id = id+'';
		var temp_id=0, name="";
		
		if (data_arr && data_arr.length > 0){
			$.each( data_arr, function(indx, val){
				temp_id =  $.trim(val[id_index -1]);
				if(id==temp_id){
					name = $.trim(val[name_index -1]);
					return false;
				}
			} );
			
		}else{
				// console.log("error : getNameByID: Local data store
				// empty..!");
		}
		// console.log(name);
		return name;
	}
	
	
	
/*--------------------- Datable related functions------------------------*/
	/**
	 * Check if data table initialised
	 * 
	 * @param nTable:
	 *            Data table object
	 * @returns {Boolean}: true / false
	 */
	function isDataTable ( nTable )
	{
	    var settings = $.fn.dataTableSettings;
	    for ( var i=0, iLen=settings.length ; i<iLen ; i++ )
	    {
	        if ( settings[i].nTable == nTable )
	        {
	            return true;
	        }
	    }
	    return false;
	}	


	/**
	 * create map link for lat lon
	 * 
	 * @param lat :
	 *            latitude
	 * @param lon :
	 *            longitude
	 * @returns {html} a link for send email popup dialog
	 */
	function getLocationLink(lat, lon){
		if( parseFloat( lat ) == 0.0 && parseFloat( lon ) == 0.0)
			return "UNKNOWN";
		
		var location = '<a href="https://maps.google.com/maps?q='+ trimRHSDecimalPoint(lat,6) +','+ trimRHSDecimalPoint(lon,6) +'&z=16" target="_blank" >'+ lat +','+lon +'</a>';
		return location;
	}
	// TODO on click show stop
	function getLocationLinkByName(name){
		if( name == '' || name == null)
			return "";
		name = name.trim();
		var nameArr = name.split(' ');
		var qName = '';
		$.each(nameArr, function(inx, val){
			qName += val+'+';
		});
		qName += ","+DEFAULT_ORGANIZATION.city_name;
		
		var location = '<a href="https://maps.google.com/maps?q='+qName+'" target="_blank" >'+ name +'</a>';
		return location;
	}

	/**
	 * create email link for email
	 * 
	 * @param email:
	 *            email id
	 * @returns {html} a link for send email popup dialog
	 */
	function getEmailLink(email){
		if( $.trim( email ) == '' || email=='null' || email==null)
			return "";
		
		var email_link = '<a href="#" class="_sendemail">'+email+'</a>';
		return email_link;
	}		
		

	/**
	 * create sms link for sms
	 * 
	 * @param mobile:
	 *            Mobile No.
	 * @returns {html} a link for send sms popup dialog
	 */
	function getSmsLink(mobile){
		if( $.trim( mobile ) == '' || mobile == 'null' || mobile == null )
			return "";
		
		var sms_link = '<a href="#" class="_sendsms">'+mobile+'</a>';
		return sms_link;
	}
	
	function getMapLinkByName(name){
		if( $.trim( name ) == '' || name == 'null' || name == null )
			return "";
		
		return '<a href="#" class="show_map">'+name+'</a>';
	}

	// Function to get the name by id for any json list of Id and name
	function getNameById(fieldId, values){
		var column = 0, name = "", id;
		for(var row = 0 ; row<values.length; row++){
			if (null != values[row][column]) {
				id = $.trim(values[row][0]);
				if(id == fieldId)
					name = $.trim(values[row][1]);
			}
		}
		return name;
	}
	
// function handleGiEntity( link ) {
//		
// var rel = $("#" + $(link).attr("rel")).selector;
// console.log( rel );
//		
// switch(rel){
//
// case "#GiCustomerDetails" :
//			
// if(!delay_event){
// delay_event = true;
// // create tab
// addTab( $(this), true );
// }
// window.setTimeout(function(){
// delay_event = false;
// }, 1000);
//			
// var giCustomer_obj = GiCustomerScript.getInstance();
// giCustomer_obj.loadTabContent();
// break;
//	
// case "#GiWelcomeLetter" :
// getwelcomecard( "Joydeep RC", "791212", new Date());
// break;
//
// case "#GiIdCard" :
// getwelcomecard( "Kakoli RC", "1291212", new Date());
// break;
//			
//		
// default:
// alert("Sorry, This feature hasnt been implemented..! \n" + rel );
//		
// }
		
// }
	
// -----------------------End -----------------------------------//
