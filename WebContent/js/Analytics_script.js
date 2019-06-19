////////////////////////////////////////////////////////////////////////////

// FileName Analytics_script.js: Analytics Javascript file

// Author : Vinu | Utkarsh | JRC
// Description : Slash Digital TVAS v1.0


////////////////////////////////////////////////////////////////////////////


var AnalyticsScript = (function(){
	// Stores the field of the parent form
	var formField;

	// Is true if the form is launched from another form, as child form
	var isChildForm = false;

	// Instance stores a reference to the Singleton
	var instance;

	//Url
	var URL="/AnalyticsDataHandler";

	//table relation attributes
	var REL = "AnalyticsDetails";
	var FORM_ID = "edit_analytics_form";

	//Data Store : declare local data store objects.
	var LOCAL_DATA={};
	//Data table instance
	var DATA_TABLE;

	//Add/Edit Form Dimension
	var ADDFORM_HEIGHT = 'auto';	// $(window).height();
	var ADDFORM_WIDTH = 'auto';	// $(window).width();
	//--------------Table row index:-----------------

	var value = 0;

	var INDEX = {
		CHECKBOX : value++, 		// 0
		ANALYTICS_ID : value++,		// 1
		SUBSCRIBER_ID : value++,		// 2
		SESSION_START_TIME : value++,		// 3
		SESSION_END_TIME : value++,		// 4
		PAGES_PER_SESSION : value++,		// 5
		TOTAL_SESSION : value++,		// 6
		TOTAL_VISIT : value++,		// 7
		NEW_USER : value++,		// 8
		CHANNELS_OF_TRAFFIC : value++,		// 9
		TIME_SPENT : value++,		// 10
		BOUNCE_RATE : value++,		// 11
		SOURCE_OF_TRAFFIC : value++,		// 12
		CLICK : value++,		// 13
		VIDEO_WATCHED_DURATION : value++,		// 14
		BROWSER : value++,		// 15
		OPERATING_SYSTEM : value++,		// 16
		DEVICE : value++		// 17
	};

	//-------------Table Header Label----------------------

	var LABEL = {

		ANALYTICS_ID : "Analytics",
		SUBSCRIBER_ID : "Subscriber",
		SESSION_START_TIME_DATE : "Session Start Time Date",
		SESSION_START_TIME_TIME : "Session Start Time Time",
		SESSION_END_TIME_DATE : "Session End Time Date",
		SESSION_END_TIME_TIME : "Session End Time Time",
		PAGES_PER_SESSION : "Pages Per Session",
		TOTAL_SESSION : "Total Session",
		TOTAL_VISIT : "Total Visit",
		NEW_USER : "New User",
		CHANNELS_OF_TRAFFIC : "Channels Of Traffic",
		TIME_SPENT : "Time Spent",
		BOUNCE_RATE : "Bounce Rate",
		SOURCE_OF_TRAFFIC : "Source Of Traffic",
		CLICK : "Click",
		VIDEO_WATCHED_DURATION : "Video Watched Duration",
		BROWSER : "Browser",
		OPERATING_SYSTEM : "Operating System",
		DEVICE : "Device"
	};

	//-----------------------------Default values------------------------------------
	//// TODO: Assign group_lookup_id of Lookup forign keys
	var DEFAULT = {

		ANALYTICS_ID : 0,
		SUBSCRIBER_ID : 0,
		SESSION_START_TIME_DATE : "",
		SESSION_START_TIME_TIME : "12:00 PM",
		SESSION_END_TIME_DATE : "",
		SESSION_END_TIME_TIME : "12:00 PM",
		PAGES_PER_SESSION : 0,
		TOTAL_SESSION : 0,
		TOTAL_VISIT : 0,
		NEW_USER : 0,
		CHANNELS_OF_TRAFFIC : "",
		TIME_SPENT : 0.0,
		BOUNCE_RATE : 0.0,
		SOURCE_OF_TRAFFIC : "",
		CLICK : 0,
		VIDEO_WATCHED_DURATION : 0.0,
		BROWSER : "",
		OPERATING_SYSTEM : "",
		DEVICE : ""
	};

	//-----------------------------Form Elements------------------------------------
	var FORM_FIELD = {

		ANALYTICS_ID : "#"+FORM_ID+" input[name=analytics_id]",
		SUBSCRIBER_ID : "#"+FORM_ID+" select[name=subscriber_id]",
		SESSION_START_TIME_DATE : "#"+FORM_ID+" input[name=session_start_time_date]",
		SESSION_START_TIME_TIME : "#"+FORM_ID+" input[name=session_start_time_time]",
		SESSION_END_TIME_DATE : "#"+FORM_ID+" input[name=session_end_time_date]",
		SESSION_END_TIME_TIME : "#"+FORM_ID+" input[name=session_end_time_time]",
		PAGES_PER_SESSION : "#"+FORM_ID+" input[name=pages_per_session]",
		TOTAL_SESSION : "#"+FORM_ID+" input[name=total_session]",
		TOTAL_VISIT : "#"+FORM_ID+" input[name=total_visit]",
		NEW_USER : "#"+FORM_ID+" input[name=new_user]",
		CHANNELS_OF_TRAFFIC : "#"+FORM_ID+" input[name=channels_of_traffic]",
		TIME_SPENT : "#"+FORM_ID+" input[name=time_spent]",
		BOUNCE_RATE : "#"+FORM_ID+" input[name=bounce_rate]",
		SOURCE_OF_TRAFFIC : "#"+FORM_ID+" input[name=source_of_traffic]",
		CLICK : "#"+FORM_ID+" input[name=click]",
		VIDEO_WATCHED_DURATION : "#"+FORM_ID+" input[name=video_watched_duration]",
		BROWSER : "#"+FORM_ID+" input[name=browser]",
		OPERATING_SYSTEM : "#"+FORM_ID+" input[name=operating_system]",
		DEVICE : "#"+FORM_ID+" input[name=device]"
	};
	/*------------------------- Load Dependents---------------------------------------*/
	function loadDependents(option) {
		//updateCityData({callback : function(){
			if(undefined !== option)
				option.callback();
		//}});
	}

	//get Analytics data
	function getStorageData(){
		//get data from global storage
		if(typeof(Storage)!=="undefined"){
			// Code for localStorage/sessionStorage.
			if (sessionStorage.getItem(REL+'_data')){
				var jsonString = sessionStorage.getItem(REL+'_data');
				//return jQuery.parseJSON(jsonString);
				return  JSON.parse(jsonString);
			}else {
				return null;
			}
		}else {
			// Sorry! No Web Storage support..
			if(LOCAL_DATA.json){
				return LOCAL_DATA.json;
			}
			return null;
		}
	}
	//set Analytics data
	function setStorageData(json){
		//set data from global storage
		if(typeof(Storage)!=="undefined"){
			// Code for localStorage/sessionStorage.
			if (sessionStorage.getItem(REL+'_data')){
				sessionStorage.removeItem(REL+'_data');
				sessionStorage.setItem(REL+'_data', JSON.stringify(json)); 
			}
			else{
				sessionStorage.setItem(REL+'_data', JSON.stringify(json));
			}
		}
		else{
			// Sorry! No Web Storage support..
			LOCAL_DATA.json = json;
		}
	}
//		---------------HANDLERS SECTION --------------------


//	bind event handlers when form is opened
	function bindFormEventHandlers(){
		//$( FORM_FIELD.FIELDNAME ).change(onFieldNameChange);

	}
	function loadTabContent(){

		
		var subscriber = SubscriberScript.getInstance();
		subscriber.getData();



		fetchData({
			callback : function(status, response){

				setStorageData(response);
				createTable(response.data, 0);
				showCurrentTabContent();
			}
		});
		bindFormEventHandlers();
	}
	/*------------ create and Customize data table----------------*/
	function createTable(json, _row_index){
		var container_id = 'tab_content'; //dont change

		//Remove data table if already exists
		if( undefined !== DATA_TABLE && isDataTable(DATA_TABLE))
			DATA_TABLE.fnDestroy();

		//Remove tab container if already exist
		if($('#'+REL+'_content').length != 0){
			$('#'+REL+'_content').remove();
		}
		//check if data received
		if(!json){
			console.log('Data not available for table');
			return;
		}

		/*
		 * Create Container for datagrid
		 */
		var grid = $('<div></div>')
					.addClass('tblContainer')
						.attr('id', json.rel + "_content")
							.appendTo($("#"+container_id));

		var table_id = json.rel+'_table';

		/*
		 * Create TABLE
		 */
		var table_obj = $('<table id="'+table_id+'" class="_editable_table" width="100%"></table>').appendTo(grid);

		//attach data
		table_obj.data('table_data', JSON.stringify(json)); //to be use for retrieving rows

		//get a copy of data for rows
		var tableData = [];
		//create a copy
		if(json.values){

			tableData = json.values.slice(0);	
		}		

		//prepare data for rows
		$.each( tableData, function(i, v) {
			var inp = '<input type="checkbox" name="'+json.rel+"_"+(i+1)+'" class="'+v[0]+'" data_row_index="'+i+'"/>';
			v.splice(0,0,inp); //add input elemet to begining of array
		});

		//set display row start
		if(!(_row_index))
			_row_index = 0;

		var _num_row_per_page = 15; //number of records per page
		if( _row_index >= _num_row_per_page){
			_row_index -= (_row_index % _num_row_per_page);
		}else{
			_row_index = 0;
		}
		var subscriber = SubscriberScript.getInstance();
		//render column
		var aoColumnDefs_render = [

					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.ANALYTICS_ID ] ); },
								"aTargets": [ INDEX.ANALYTICS_ID ]
					},
					{
					"fnRender": function ( o, val ) {
						var subscriberName = getValueById( o.aData[ INDEX.SUBSCRIBER_ID ], subscriber );
						return subscriberName;
						},
						"aTargets": [ INDEX.SUBSCRIBER_ID ]
					},
					{
						"fnRender":	function ( o, val ) {
									return timeToDisplayDateTime( o.aData[ INDEX.SESSION_START_TIME ] ); },
								"aTargets": [ INDEX.SESSION_START_TIME ]
					},
					{
						"fnRender":	function ( o, val ) {
									return timeToDisplayDateTime( o.aData[ INDEX.SESSION_END_TIME ] ); },
								"aTargets": [ INDEX.SESSION_END_TIME ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.PAGES_PER_SESSION ] ); },
								"aTargets": [ INDEX.PAGES_PER_SESSION ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.TOTAL_SESSION ] ); },
								"aTargets": [ INDEX.TOTAL_SESSION ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.TOTAL_VISIT ] ); },
								"aTargets": [ INDEX.TOTAL_VISIT ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.NEW_USER ] ); },
								"aTargets": [ INDEX.NEW_USER ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.CHANNELS_OF_TRAFFIC ] ); },
								"aTargets": [ INDEX.CHANNELS_OF_TRAFFIC ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.TIME_SPENT ] ); },
								"aTargets": [ INDEX.TIME_SPENT ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.BOUNCE_RATE ] ); },
								"aTargets": [ INDEX.BOUNCE_RATE ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.SOURCE_OF_TRAFFIC ] ); },
								"aTargets": [ INDEX.SOURCE_OF_TRAFFIC ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.CLICK ] ); },
								"aTargets": [ INDEX.CLICK ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.VIDEO_WATCHED_DURATION ] ); },
								"aTargets": [ INDEX.VIDEO_WATCHED_DURATION ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.BROWSER ] ); },
								"aTargets": [ INDEX.BROWSER ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.OPERATING_SYSTEM ] ); },
								"aTargets": [ INDEX.OPERATING_SYSTEM ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.DEVICE ] ); },
								"aTargets": [ INDEX.DEVICE ]
					}
					];
		//Define Column Headings
		var aoColumns_headings = [

						{	"sTitle": '<input type="checkbox" name="select_all_rows" title="Select All/None">', "sClass": "center", "bSortable": false, "sWidth": "5%"  }

						,{	"sTitle": json.headings[INDEX.ANALYTICS_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.SUBSCRIBER_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.SESSION_START_TIME-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.SESSION_END_TIME-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.PAGES_PER_SESSION-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.TOTAL_SESSION-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.TOTAL_VISIT-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.NEW_USER-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.CHANNELS_OF_TRAFFIC-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.TIME_SPENT-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.BOUNCE_RATE-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.SOURCE_OF_TRAFFIC-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.CLICK-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.VIDEO_WATCHED_DURATION-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.BROWSER-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.OPERATING_SYSTEM-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.DEVICE-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
					];

		$('body').on("click", '#'+table_id+' tbody tr' ,function () {
			$(this).addClass('selected_row');
		});

		//Create data table
		var oTable = $("#"+table_id).dataTable({
							"aaData": tableData,
							"aoColumns":aoColumns_headings,
							"bJQueryUI": true,
							"sPaginationType": "full_numbers",
							"iDisplayLength":_num_row_per_page,
							"iDisplayStart": _row_index,
							"aLengthMenu": [[10, 15, 20, 25, 50, 100, -1], [10, 15, 20, 25, 50, 100, "All"]],
							"aoColumnDefs": aoColumnDefs_render 
							});

		DATA_TABLE=oTable;

		//show add/edit/delete buttons/and other events
		addButtonPanel(table_id);
		addTableEvents(table_id);
	}

	//Function for table events onClick, ondblClick
	function addTableEvents(table_id){
		//single click event
		addTableRowClickEnent(table_id);

		//dbl click event
		var userRole = getUserRoleFromCookie();
		if(userRole){
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.EDIT_ANALYTICS) == true ){ //edit enabled : permissions
				addTableRowDblClickEvent(table_id, function(index){
					var data = JSON.parse($('#'+table_id).data('table_data'));

					var value_array = data.values;

					popUpEditForm(value_array[index], index);
				});
			}
		}

		//select all/none check box
		addTableSelectAllRowChkBoxEvent(table_id);
	}
	//add button panel
	function addButtonPanel(table_id){
		//remove if exists
		removeButtonPanel();

		var tab_tool_id = '#tab_tool';

		var this_tool_id = REL+'_toolBtns';

		var panel = 	'<ul class="toolBtns" id="'+this_tool_id+'">'
				+'<li><button class="add_button tool_button_css t_button" type="button">Add</button></li>'
				+'<li><button class="edit_button tool_button_css t_button" type="button">Edit</button></li>'
				+'<li><button class="remove_button tool_button_css t_button" type="button">Delete</button></li>'
				+'<li><button class="refresh_button tool_button_css t_button" type="button">Refresh</button></li>'
				+'<li><button class="select_action tool_button_css_a t_button" type="button">Action</button>'
				+'<div class="select_action_list dropdown_menu hide_elem" style="min-width: 145px; min-height: auto;">'
				+'<ul>'
				+'</ul>'
				+'</div>'
				+'</li>'
				+'</ul>';
		$(tab_tool_id+'').append(panel);

		$('#'+this_tool_id+' .select_action').button({
			icons: {
				secondary: "ui-icon-triangle-1-s"
			}
		});

		$('#'+this_tool_id+' .t_button' ).button();

		$(tab_tool_id).show();
		$(tab_tool_id+' .toolBtns').hide();

		//attach click events
		attachToolButtonEvents(table_id, this_tool_id);
		//show buttons
		$('#'+this_tool_id ).show(); 				
	}

	//attach click events to the tool buttons
	function attachToolButtonEvents(table_id, button_panel_id){
		var userRole = getUserRoleFromCookie();
		if(userRole){

		//Add button
		var add_button_id = "#"+button_panel_id+' button.add_button';
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.ADD_ANALYTICS) == true ){
				$(add_button_id).on('click',function(){
					popUpAddForm();
				});
			}
			else{
				$(add_button_id).button({disabled: true});
			}

		//Edit button
		var edit_button_id = "#"+button_panel_id+' button.edit_button';
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.EDIT_ANALYTICS) == true ){
				$(edit_button_id).on('click',function(){
					if(checkIfSingleRowSelected(table_id)== true){
						var index_arr = getSelectedRowDataIndex(table_id);
						if( index_arr.length == 1){
							var data = JSON.parse($('#'+table_id).data('table_data'));
							var value_array = data.values;

							popUpEditForm(value_array[index_arr[0]], index_arr[0]);
						}
					}
				});
			}
			else{
				$(edit_button_id).button({ disabled: true });
			}

		//Delete/remove_button
		var remove_button_id = "#"+button_panel_id+' button.remove_button';
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.DELETE_ANALYTICS) == true ){
				$(remove_button_id).on('click',function(){

					if(checkIfRowSelected(table_id)== true){

						var index_arr = getSelectedRowDataIndex(table_id);
						var num_row = index_arr.length;

						if( num_row >= 1){
							var data = JSON.parse($('#'+table_id).data('table_data'));
							var value_array = data.values;
							var del_data_arr = [];

							$.each(index_arr, function(inx,val){
								del_data_arr.push(value_array[val]);
							});

							var table_name = "Analytics";

							dialogMessageDeleteRow( table_name, num_row, function(){
								deleteRows(del_data_arr);
							});
						}
					}
				});
			}else{//disable
				$(remove_button_id).button({ disabled: true });
			}
		}
		//refresh
		var refresh_button_id = "#"+button_panel_id+' button.refresh_button'; //
		$(refresh_button_id).on('click',function(e){
			$(e.currentTarget).button('disable');
			showTabContent(REL);
			window.setTimeout(function(){
				$(e.currentTarget).button('enable');
			}, 2000);
		});

		//actions : TODO check which actions to be enabled for which user
		var action_button_id = "#"+button_panel_id+' button.select_action'; //button id
		var action_menu_id = "#"+button_panel_id+' div.select_action_list'; //menu id
		var action_list = $(action_menu_id+' ul');

		populateActionMenu(action_list);

		addActionMenuEvent({
			button_id: action_button_id,
			menu_id: action_menu_id,
			callback: function(id){
				execMenuAction(id);
			}
		});
	}

	//remove botton panel
	function removeButtonPanel(){
		var tool_panel_id = REL+'_toolBtns';
		$('#'+tool_panel_id).remove();
	}

	//function to display popUp form: 'titleString' is title of popUp window, 'mode' is Add or Edit.
	function displayForm(titleString, mode, row_index) {

		//$("#"+FORM_ID+"").dialog("destroy");
		$("input, textarea", $("#"+FORM_ID+"")).removeClass( "ui-state-error" );

		addCommonFormEvents(FORM_ID);

		$("#"+FORM_ID+"").dialog({
			autoOpen : false,
			height : ADDFORM_HEIGHT,
			width : ADDFORM_WIDTH,
			modal : true,
			title : titleString,
			buttons : {
				" Save " : function(e) {

					$(e.currentTarget).button('disable');

					var bValid = true;

					$("#"+FORM_ID+"").replaceSymbolUtils();
					$("#"+FORM_ID+"").trimInputFields();

					$("form .name","#"+FORM_ID+"").capsFirstLetter();

					bValid = validateForm();

					if(true == bValid){
						saveFormData(mode, row_index);

						window.setTimeout(function(){
							$(e.currentTarget).button('enable');
						}, 1000);

					}else{
						$(e.currentTarget).button('enable');
					}
				},
				" Cancel " : function() {
					if(isChildForm ==  true){

						isChildForm == false;

					}

					$(this).dialog("close");
				}
			},
			close : function() {
				$.timepicker._hideTimepicker();
				$.datepicker._hideDatepicker();
				$("#"+FORM_ID+"").dialog("destroy");					
			}
		});
		$("#"+FORM_ID+"").dialog('open');
	}
	function validateForm(){
		var bValid = true;
		var country_code = "+91"; // PUT in the Country code or fetch from DB or server 
		var fv = FormValidation;

		console.log('Enable the validation for this form');
/* Enable as per requirement
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.ANALYTICS_ID), G_ERROR.MSG.empty_error+LABEL.ANALYTICS_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.SUBSCRIBER_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.SUBSCRIBER_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SESSION_START_TIME_DATE), G_ERROR.MSG.empty_error+LABEL.SESSION_START_TIME_DATE);
		bValid = bValid && fv.checkDate($(FORM_FIELD.SESSION_START_TIME_DATE), G_ERROR.MSG.invalid_date_error+LABEL.SESSION_START_TIME_DATE);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SESSION_START_TIME_TIME), G_ERROR.MSG.empty_error+LABEL.SESSION_START_TIME_TIME);
		bValid = bValid && fv.checkTime($(FORM_FIELD.SESSION_START_TIME_TIME), G_ERROR.MSG.invalid_time_error+LABEL.SESSION_START_TIME_TIME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SESSION_END_TIME_DATE), G_ERROR.MSG.empty_error+LABEL.SESSION_END_TIME_DATE);
		bValid = bValid && fv.checkDate($(FORM_FIELD.SESSION_END_TIME_DATE), G_ERROR.MSG.invalid_date_error+LABEL.SESSION_END_TIME_DATE);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SESSION_END_TIME_TIME), G_ERROR.MSG.empty_error+LABEL.SESSION_END_TIME_TIME);
		bValid = bValid && fv.checkTime($(FORM_FIELD.SESSION_END_TIME_TIME), G_ERROR.MSG.invalid_time_error+LABEL.SESSION_END_TIME_TIME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.PAGES_PER_SESSION), G_ERROR.MSG.empty_error+LABEL.PAGES_PER_SESSION);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.TOTAL_SESSION), G_ERROR.MSG.empty_error+LABEL.TOTAL_SESSION);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.TOTAL_VISIT), G_ERROR.MSG.empty_error+LABEL.TOTAL_VISIT);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.NEW_USER), G_ERROR.MSG.empty_error+LABEL.NEW_USER);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CHANNELS_OF_TRAFFIC), G_ERROR.MSG.empty_error+LABEL.CHANNELS_OF_TRAFFIC);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.TIME_SPENT), G_ERROR.MSG.empty_error+LABEL.TIME_SPENT);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.BOUNCE_RATE), G_ERROR.MSG.empty_error+LABEL.BOUNCE_RATE);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SOURCE_OF_TRAFFIC), G_ERROR.MSG.empty_error+LABEL.SOURCE_OF_TRAFFIC);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CLICK), G_ERROR.MSG.empty_error+LABEL.CLICK);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.VIDEO_WATCHED_DURATION), G_ERROR.MSG.empty_error+LABEL.VIDEO_WATCHED_DURATION);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.BROWSER), G_ERROR.MSG.empty_error+LABEL.BROWSER);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.OPERATING_SYSTEM), G_ERROR.MSG.empty_error+LABEL.OPERATING_SYSTEM);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.DEVICE), G_ERROR.MSG.empty_error+LABEL.DEVICE);
*/
		return bValid;
	}
	function setFormDefaults() {
		DEFAULT.ANALYTICS_ID = getNewId(getStorageData(), (INDEX.ANALYTICS_ID));
		$(FORM_FIELD.ANALYTICS_ID).val(DEFAULT.ANALYTICS_ID);
		var subscriber = SubscriberScript.getInstance();
		subscriber.populateInfo( $(FORM_FIELD.SUBSCRIBER_ID), DEFAULT.SUBSCRIBER_ID );
		$(FORM_FIELD.SESSION_START_TIME_DATE).val(DEFAULT.SESSION_START_TIME_DATE);
		$(FORM_FIELD.SESSION_START_TIME_TIME).val(DEFAULT.SESSION_START_TIME_TIME);
		$(FORM_FIELD.SESSION_END_TIME_DATE).val(DEFAULT.SESSION_END_TIME_DATE);
		$(FORM_FIELD.SESSION_END_TIME_TIME).val(DEFAULT.SESSION_END_TIME_TIME);
		$(FORM_FIELD.PAGES_PER_SESSION).val(DEFAULT.PAGES_PER_SESSION);
		$(FORM_FIELD.TOTAL_SESSION).val(DEFAULT.TOTAL_SESSION);
		$(FORM_FIELD.TOTAL_VISIT).val(DEFAULT.TOTAL_VISIT);
		$(FORM_FIELD.NEW_USER).val(DEFAULT.NEW_USER);
		$(FORM_FIELD.CHANNELS_OF_TRAFFIC).val(DEFAULT.CHANNELS_OF_TRAFFIC);
		$(FORM_FIELD.TIME_SPENT).val(DEFAULT.TIME_SPENT);
		$(FORM_FIELD.BOUNCE_RATE).val(DEFAULT.BOUNCE_RATE);
		$(FORM_FIELD.SOURCE_OF_TRAFFIC).val(DEFAULT.SOURCE_OF_TRAFFIC);
		$(FORM_FIELD.CLICK).val(DEFAULT.CLICK);
		$(FORM_FIELD.VIDEO_WATCHED_DURATION).val(DEFAULT.VIDEO_WATCHED_DURATION);
		$(FORM_FIELD.BROWSER).val(DEFAULT.BROWSER);
		$(FORM_FIELD.OPERATING_SYSTEM).val(DEFAULT.OPERATING_SYSTEM);
		$(FORM_FIELD.DEVICE).val(DEFAULT.DEVICE);
	}
	function popUpAddForm(field) {

		if(field){

			formField = field;

			isChildForm = true;

		}

		setFormDefaults();

		displayForm("Add New Analytics", INSERT_DATA, 0);
	}

	function popUpEditForm(data, row_index) {

		$(FORM_FIELD.ANALYTICS_ID).val(data[INDEX.ANALYTICS_ID-1]);
		var subscriber = SubscriberScript.getInstance();
		subscriber.populateInfo( $(FORM_FIELD.SUBSCRIBER_ID), data[INDEX.SUBSCRIBER_ID-1] );
		$(FORM_FIELD.SESSION_START_TIME_DATE).val(timeToDisplayDate(data[INDEX.SESSION_START_TIME-1]));
		$(FORM_FIELD.SESSION_START_TIME_TIME).val(timeToDisplayTime(data[INDEX.SESSION_START_TIME-1]));
		$(FORM_FIELD.SESSION_END_TIME_DATE).val(timeToDisplayDate(data[INDEX.SESSION_END_TIME-1]));
		$(FORM_FIELD.SESSION_END_TIME_TIME).val(timeToDisplayTime(data[INDEX.SESSION_END_TIME-1]));
		$(FORM_FIELD.PAGES_PER_SESSION).val(data[INDEX.PAGES_PER_SESSION-1]);
		$(FORM_FIELD.TOTAL_SESSION).val(data[INDEX.TOTAL_SESSION-1]);
		$(FORM_FIELD.TOTAL_VISIT).val(data[INDEX.TOTAL_VISIT-1]);
		$(FORM_FIELD.NEW_USER).val(data[INDEX.NEW_USER-1]);
		$(FORM_FIELD.CHANNELS_OF_TRAFFIC).val(data[INDEX.CHANNELS_OF_TRAFFIC-1]);
		$(FORM_FIELD.TIME_SPENT).val(data[INDEX.TIME_SPENT-1]);
		$(FORM_FIELD.BOUNCE_RATE).val(data[INDEX.BOUNCE_RATE-1]);
		$(FORM_FIELD.SOURCE_OF_TRAFFIC).val(data[INDEX.SOURCE_OF_TRAFFIC-1]);
		$(FORM_FIELD.CLICK).val(data[INDEX.CLICK-1]);
		$(FORM_FIELD.VIDEO_WATCHED_DURATION).val(data[INDEX.VIDEO_WATCHED_DURATION-1]);
		$(FORM_FIELD.BROWSER).val(data[INDEX.BROWSER-1]);
		$(FORM_FIELD.OPERATING_SYSTEM).val(data[INDEX.OPERATING_SYSTEM-1]);
		$(FORM_FIELD.DEVICE).val(data[INDEX.DEVICE-1]);
		displayForm("Edit Analytics", UPDATE_DATA, row_index);
	}

	function getFormDataAsJson(){
		var jsonData = {
			analytics_id : ($(FORM_FIELD.ANALYTICS_ID).val()),
			subscriber_id : ($(FORM_FIELD.SUBSCRIBER_ID).val()),
			session_start_time : DateTimeToSaveTime($(FORM_FIELD.SESSION_START_TIME_DATE).val(), $(FORM_FIELD.SESSION_START_TIME_TIME).val()),
			session_end_time : DateTimeToSaveTime($(FORM_FIELD.SESSION_END_TIME_DATE).val(), $(FORM_FIELD.SESSION_END_TIME_TIME).val()),
			pages_per_session : ($(FORM_FIELD.PAGES_PER_SESSION).val()),
			total_session : ($(FORM_FIELD.TOTAL_SESSION).val()),
			total_visit : ($(FORM_FIELD.TOTAL_VISIT).val()),
			new_user : ($(FORM_FIELD.NEW_USER).val()),
			channels_of_traffic : ($(FORM_FIELD.CHANNELS_OF_TRAFFIC).val()),
			time_spent : parseFloat($(FORM_FIELD.TIME_SPENT).val()),
			bounce_rate : parseFloat($(FORM_FIELD.BOUNCE_RATE).val()),
			source_of_traffic : ($(FORM_FIELD.SOURCE_OF_TRAFFIC).val()),
			click : ($(FORM_FIELD.CLICK).val()),
			video_watched_duration : parseFloat($(FORM_FIELD.VIDEO_WATCHED_DURATION).val()),
			browser : ($(FORM_FIELD.BROWSER).val()),
			operating_system : ($(FORM_FIELD.OPERATING_SYSTEM).val()),
			device : ($(FORM_FIELD.DEVICE).val())
		};

		console.log("JSON FormData: + jsonData");
		return jsonData;
	}

	function saveFormData(mode, row_index) {

		var jsonData = getFormDataAsJson();

		var inData = JSON.stringify(jsonData);

		var _url = ROOT_URL + URL, TYPE = '';

		switch (mode) {
		case INSERT_DATA:
			TYPE = "POST";
			break;
		case UPDATE_DATA:
			TYPE = "PUT";
			break;
		default:
			return false;
		}

		var com = new AjaxRequest();
		com.init({url:_url, type: TYPE, data: inData});
		com.send({ callback: function(status, response){

			var dialogMessage=" ERROR ";
			if( status && response.data.status_code >= 0 && mode==INSERT_DATA)
				dialogMessage="Analytics has been successfully added ("/*+jsonData.name*/ +" Id:"+response.data.id+")";
			else if( status && response.data.status_code >= 0 && mode==UPDATE_DATA)
				dialogMessage="Analytics has been successfully updated("/*+jsonData.name*/+" Id:"+response.data.id+")";
			else
				dialogMessage+=" : "+response.message+" ";

			if ( status && response.data.status_code >= 0 ){

				fetchData({callback : function(status, response){
					if(status && response.status_code >=0){

						if(mode==INSERT_DATA){
							row_index = response.data.values.length;
						}
						setStorageData(response);
						createTable(response.data, row_index );
					}
					else
						alert("   Please reload This Page.!   ");
				}});

				dialogMessageSuccess(dialogMessage, "Status");
				$("#"+FORM_ID).dialog("close");
			}
			else{
				dialogMessageFailed(dialogMessage, "Status");
			}
		}
		});
	}

	function deleteRows(row_data_arr){
		var row_id_arr = [];
		var key_column_index = [ INDEX.ANALYTICS_ID ];
		row_id_arr = getArrayFromArray({ index : key_column_index, data_arr : row_data_arr});

		var jsonData = {
				del_row_id_arr : row_id_arr
//			,analytics_id : getAnalyticsId()	// ADD ANY OTHER CONDITION (IF ANY)
		};
		var inData = JSON.stringify(jsonData);

		var _url = ROOT_URL + URL, TYPE = "DELETE";

		var com = new AjaxRequest();
		com.init({url:_url, type: TYPE, data: inData});
		com.send({callback:function(status, response){
			var dialogMessage=" ERROR";
			console.log("DELETE RESPONSE", response);
			if( true == status && response.status_code > 0 ){
//				check whether the delete operation was successful
				if( response.data.status_code == G_ERROR.CODE.SUCCESS ){

					dialogMessage="Analytics has been successfully deleted :"+response.data.rows_deleted+" row(s)";


					var table_id = REL+'_table';
					var data = JSON.parse($('#'+table_id).data('table_data'));
					var value_array = data.values;
					var index = getSelectedRowDataIndex(table_id);
					//on call back remove rows from table:put inside callback
					DATA_TABLE = $('#'+REL+"_table").dataTable();
					var aReturn = new Array();

					var aTrs = DATA_TABLE.fnGetNodes();

					for(var i=0; i<aTrs.length;i++) {
						if ( $(aTrs[i]).hasClass('selected_row') ) {
							aReturn.push( aTrs[i] );
						}
					}
					for(var i=0; i<aReturn.length;i++){
						DATA_TABLE.fnDeleteRow( aReturn[i] );
					}
					//update table data- remove deleted row
					index.sort();
					for(var j = (index.length - 1); j>=0; j--){
						value_array.splice(index[j], 1);
					}
					data.values = value_array;
					data.rows = value_array.length;
					$('#'+table_id).data('table_data', JSON.stringify(data));

					fetchData({
						callback : function(status, fetchResponse){
							if(status && fetchResponse.status_code >=0){
								setStorageData(fetchResponse);
							}
						}
					});
					dialogMessageSuccess(dialogMessage, "Status");
				}
				else {
					
					dialogMessage = "Unable to delete. Status Code is: " + response.data.status_code;
					dialogMessageFailed( dialogMessage, "Status" );
				}

			}
			else if( true == status &&  response.data.status_code == G_ERROR.CODE.DELETE_OPERATION_DEPENDENT_EXISTS){
				if( row_data_arr.length > 1)
					dialogMessage = "Cannot delete Analytics. There are some dependencies for this Analytics.";
				else
					dialogMessage = "Cannot delete Analytics. There are some dependencies for this Analytics.";

				dialogMessageWarning(dialogMessage, "Status");
			}
			else {
				dialogMessage += ":"+response.message +" ";
				dialogMessageFailed(dialogMessage, "Status");
			}
		}
		});
	}
	function fetchData(option){

		var _url = ROOT_URL + URL +"/select?organization_id="+ getOrganizationId();	//sys_user_id="+ getCurLogUserId();

		if(option && option.analytics_id){
			$.each(option.analytics_id, function(inx, val){
				_url += '&analytics_id='+val;
			});
		}

		console.log( _url );
		var com = new AjaxRequest();
		com.init({url:_url});
		com.send({callback: function(status, response){
			//if( status && response.data.status_code >= 0 ){
				if(option && option.callback)
					option.callback(status, response);
			//}
			//else {
				//if(option && option.callback)
					//option.callback(false, response);
			//}
		}});
	}
	/* populate combobox*/
	function populateInfo(elem, select_id, callback) {
		var  id_index = INDEX.ANALYTICS_ID, name_index = getNameIdx();
		if(select_id == undefined) select_id = 0;
		var values = [];

		if(null == getStorageData()){
			$("option:first",elem).nextAll().remove();
		}
		if(null !== getStorageData()){
			var storageData = getStorageData();
			values =  storageData.data.values;
			populateCombobox(elem, values, id_index, name_index);
			elem.val(select_id);
			if(callback)
				callback();
		}
		else {
			fetchData({
				callback: function(status, response){
					if( status && response.data.values){
						values =  response.data.values;

						populateCombobox(elem, values, id_index, name_index);
						elem.val(select_id);
					}
					else{
						console.log('ANALYTICS_DATA is empty : selected_id=' +select_id );
					}
					if(callback)
						callback();
				}
			});
		}
	}

	/**
	 *  Populate Action menu
	 */
	function populateActionMenu(list) {
		//empty list
		list.html('');
		//add items
		list.append( $('<li value="1">Send SMS</li>'));
		list.append( $('<li value="2">Send Email</li>'));
	}

	/**
	 *execute click function
	 * @param id{int}: li value in populateActionMenu()
	 */
	function execMenuAction(id){
		switch(id){
		case 1:
			AnalyticsSendSms();
			break;
		case 2:
			AnalyticsSendEmail();
			break;	
		default:
			log('Analytics Action', 'unknown parameter');
		}
	}
	function getData(){

		var data = getStorageData();
		if( data != null && data.values != null )
			return;

		fetchData({
			callback : function(status, response){

				if( status && response.status_code >= 0 ){

					console.log( response );
					setStorageData(response);
				}
			}
		});
	}

	function myFunction( value ) {
		return value;
	}

	function getIdIdx() {
		return INDEX.ANALYTICS_ID;
	}

	function getNameIdx() {
		console.log('Please Change the Name Idx');
		return INDEX.CHOOSE_NAME;
	}

	function init(){
		return {
			//expose all public instance methods
			createTable : createTable,
			loadTabContent: loadTabContent,
			populateInfo: populateInfo,
			setStorageData: setStorageData,
			getStorageData: getStorageData,
			fetchData: fetchData,
			//popUpAddForm: popUpAddForm,
			getIdIdx: getIdIdx,
			getNameIdx: getNameIdx,
			getData : getData
		};
	}

	return {
		// Get the Singleton instance if one exists
		// or create one if it doesn't
		getInstance: function () {
			if ( !instance ) {
				instance = init();
			}
			return instance;
		},
		//expose all public method and objects here
		INDEX : INDEX,
		LABEL : LABEL,
		FORM_FIELD : FORM_FIELD,
		URL : URL,
		REL : REL //,
		//loadDependents: loadDependents
	};
})();
