////////////////////////////////////////////////////////////////////////////

// FileName SystemIssue_script.js: SystemIssue Javascript file

// Author : Vinu | Utkarsh | JRC
// Description : Digi Park v1.0


////////////////////////////////////////////////////////////////////////////


var SystemIssueScript = (function(){
	// Stores the field of the parent form
	var formField;

	// Is true if the form is launched from another form, as child form
	var isChildForm = false;

	// Instance stores a reference to the Singleton
	var instance;

	//Url
	var URL="/SystemIssueDataHandler";

	//table relation attributes
	var REL = "SystemIssueDetails";
	var FORM_ID = "edit_system_issue_form";

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
		SYSTEM_ISSUE_ID : value++,		// 1
		SYSTEM_ISSUE_DATE : value++,		// 2
		SCREEN_NAME : value++,		// 3
		DESCRIPTION : value++,		// 4
		IMAGE_FILE_PATH : value++,		// 5
		ISSUE_TYPE_LOOKUP_ID : value++,		// 6
		ISSUE_PRIORITY_LOOKUP_ID : value++,		// 7
		RESOLUTION : value++,		// 8
		SYS_USER_ID : value++,		// 9
		CLOSED_BY_SYS_USER_ID : value++,		// 10
		CLOSED_DATE : value++,		// 11
		STATUS : value++		// 12
	};

	//-------------Table Header Label----------------------

	var LABEL = {

		SYSTEM_ISSUE_ID : "System Issue",
		SYSTEM_ISSUE_DATE_DATE : "System Issue Date Date",
		SYSTEM_ISSUE_DATE_TIME : "System Issue Date Time",
		SCREEN_NAME : "Screen Name",
		DESCRIPTION : "Description",
		IMAGE_FILE_PATH : "Image File Path",
		ISSUE_TYPE_LOOKUP_ID : "Issue Type",
		ISSUE_PRIORITY_LOOKUP_ID : "Issue Priority",
		RESOLUTION : "Resolution",
		SYS_USER_ID : "Sys User",
		CLOSED_BY_SYS_USER_ID : "Closed By Sys User",
		CLOSED_DATE_DATE : "Closed Date Date",
		CLOSED_DATE_TIME : "Closed Date Time",
		STATUS : "Status"
	};

	//-----------------------------Default values------------------------------------
	//// TODO: Assign group_lookup_id of Lookup forign keys
	var DEFAULT = {

		SYSTEM_ISSUE_ID : 0,
		SYSTEM_ISSUE_DATE_DATE : "",
		SYSTEM_ISSUE_DATE_TIME : "12:00 PM",
		SCREEN_NAME : "",
		DESCRIPTION : "",
		IMAGE_FILE_PATH : "",
		ISSUE_TYPE_LOOKUP_ID : 0,
		ISSUE_PRIORITY_LOOKUP_ID : 0,
		RESOLUTION : "",
		SYS_USER_ID : 0,
		CLOSED_BY_SYS_USER_ID : 0,
		CLOSED_DATE_DATE : "",
		CLOSED_DATE_TIME : "12:00 PM",
		STATUS : 0
	};

	//-----------------------------Form Elements------------------------------------
	var FORM_FIELD = {

		SYSTEM_ISSUE_ID : "#"+FORM_ID+" input[name=system_issue_id]",
		SYSTEM_ISSUE_DATE_DATE : "#"+FORM_ID+" input[name=system_issue_date_date]",
		SYSTEM_ISSUE_DATE_TIME : "#"+FORM_ID+" input[name=system_issue_date_time]",
		SCREEN_NAME : "#"+FORM_ID+" textarea[name=screen_name]",
		DESCRIPTION : "#"+FORM_ID+" textarea[name=description]",
		IMAGE_FILE_PATH : "#"+FORM_ID+" textarea[name=image_file_path]",
		ISSUE_TYPE_LOOKUP_ID : "#"+FORM_ID+" select[name=issue_type_lookup_id]",
		ISSUE_PRIORITY_LOOKUP_ID : "#"+FORM_ID+" select[name=issue_priority_lookup_id]",
		RESOLUTION : "#"+FORM_ID+" textarea[name=resolution]",
		SYS_USER_ID : "#"+FORM_ID+" select[name=sys_user_id]",
		CLOSED_BY_SYS_USER_ID : "#"+FORM_ID+" select[name=closed_by_sys_user_id]",
		CLOSED_DATE_DATE : "#"+FORM_ID+" input[name=closed_date_date]",
		CLOSED_DATE_TIME : "#"+FORM_ID+" input[name=closed_date_time]",
		STATUS : "#"+FORM_ID+" input[name=status]"
	};
	/*------------------------- Load Dependents---------------------------------------*/
	function loadDependents(option) {
		//updateCityData({callback : function(){
			if(undefined !== option)
				option.callback();
		//}});
	}

	//get SystemIssue data
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
	//set SystemIssue data
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

		
		var lookup = LookupScript.getInstance();
		lookup.getData();

		var sysUser = SysUserScript.getInstance();
		sysUser.getData();



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
		var lookup = LookupScript.getInstance();
		var sysUser = SysUserScript.getInstance();
		//render column
		var aoColumnDefs_render = [

					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.SYSTEM_ISSUE_ID ] ); },
								"aTargets": [ INDEX.SYSTEM_ISSUE_ID ]
					},
					{
						"fnRender":	function ( o, val ) {
									return timeToDisplayDateTime( o.aData[ INDEX.SYSTEM_ISSUE_DATE ] ); },
								"aTargets": [ INDEX.SYSTEM_ISSUE_DATE ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.SCREEN_NAME ] ); },
								"aTargets": [ INDEX.SCREEN_NAME ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.DESCRIPTION ] ); },
								"aTargets": [ INDEX.DESCRIPTION ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.IMAGE_FILE_PATH ] ); },
								"aTargets": [ INDEX.IMAGE_FILE_PATH ]
					},
					{
					"fnRender": function ( o, val ) {
						var lookupName = getValueById( o.aData[ INDEX.ISSUE_TYPE_LOOKUP_ID ], lookup );
						return lookupName;
						},
						"aTargets": [ INDEX.ISSUE_TYPE_LOOKUP_ID ]
					},
					{
					"fnRender": function ( o, val ) {
						var lookupName = getValueById( o.aData[ INDEX.ISSUE_PRIORITY_LOOKUP_ID ], lookup );
						return lookupName;
						},
						"aTargets": [ INDEX.ISSUE_PRIORITY_LOOKUP_ID ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.RESOLUTION ] ); },
								"aTargets": [ INDEX.RESOLUTION ]
					},
					{
					"fnRender": function ( o, val ) {
						var sysUserName = getValueById( o.aData[ INDEX.SYS_USER_ID ], sysUser );
						return sysUserName;
						},
						"aTargets": [ INDEX.SYS_USER_ID ]
					},
					{
					"fnRender": function ( o, val ) {
						var sysUserName = getValueById( o.aData[ INDEX.CLOSED_BY_SYS_USER_ID ], sysUser );
						return sysUserName;
						},
						"aTargets": [ INDEX.CLOSED_BY_SYS_USER_ID ]
					},
					{
						"fnRender":	function ( o, val ) {
									return timeToDisplayDateTime( o.aData[ INDEX.CLOSED_DATE ] ); },
								"aTargets": [ INDEX.CLOSED_DATE ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.STATUS ] ); },
								"aTargets": [ INDEX.STATUS ]
					}
					];
		//Define Column Headings
		var aoColumns_headings = [

						{	"sTitle": '<input type="checkbox" name="select_all_rows" title="Select All/None">', "sClass": "center", "bSortable": false, "sWidth": "5%"  }

						,{	"sTitle": json.headings[INDEX.SYSTEM_ISSUE_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.SYSTEM_ISSUE_DATE-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.SCREEN_NAME-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.DESCRIPTION-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.IMAGE_FILE_PATH-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.ISSUE_TYPE_LOOKUP_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.ISSUE_PRIORITY_LOOKUP_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.RESOLUTION-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.SYS_USER_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.CLOSED_BY_SYS_USER_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.CLOSED_DATE-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.STATUS-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.EDIT_SYSTEM_ISSUE) == true ){ //edit enabled : permissions
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.ADD_SYSTEM_ISSUE) == true ){
				$(add_button_id).on('click',function(){
					popUpAddForm();
				});
			}
			else{
				$(add_button_id).button({disabled: true});
			}

		//Edit button
		var edit_button_id = "#"+button_panel_id+' button.edit_button';
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.EDIT_SYSTEM_ISSUE) == true ){
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.DELETE_SYSTEM_ISSUE) == true ){
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

							var table_name = "System Issue";

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
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SYSTEM_ISSUE_ID), G_ERROR.MSG.empty_error+LABEL.SYSTEM_ISSUE_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SYSTEM_ISSUE_DATE_DATE), G_ERROR.MSG.empty_error+LABEL.SYSTEM_ISSUE_DATE_DATE);
		bValid = bValid && fv.checkDate($(FORM_FIELD.SYSTEM_ISSUE_DATE_DATE), G_ERROR.MSG.invalid_date_error+LABEL.SYSTEM_ISSUE_DATE_DATE);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SYSTEM_ISSUE_DATE_TIME), G_ERROR.MSG.empty_error+LABEL.SYSTEM_ISSUE_DATE_TIME);
		bValid = bValid && fv.checkTime($(FORM_FIELD.SYSTEM_ISSUE_DATE_TIME), G_ERROR.MSG.invalid_time_error+LABEL.SYSTEM_ISSUE_DATE_TIME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SCREEN_NAME), G_ERROR.MSG.empty_error+LABEL.SCREEN_NAME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.DESCRIPTION), G_ERROR.MSG.empty_error+LABEL.DESCRIPTION);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.IMAGE_FILE_PATH), G_ERROR.MSG.empty_error+LABEL.IMAGE_FILE_PATH);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.ISSUE_TYPE_LOOKUP_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.ISSUE_TYPE_LOOKUP_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.ISSUE_PRIORITY_LOOKUP_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.ISSUE_PRIORITY_LOOKUP_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.RESOLUTION), G_ERROR.MSG.empty_error+LABEL.RESOLUTION);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.SYS_USER_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.SYS_USER_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.CLOSED_BY_SYS_USER_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.CLOSED_BY_SYS_USER_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CLOSED_DATE_DATE), G_ERROR.MSG.empty_error+LABEL.CLOSED_DATE_DATE);
		bValid = bValid && fv.checkDate($(FORM_FIELD.CLOSED_DATE_DATE), G_ERROR.MSG.invalid_date_error+LABEL.CLOSED_DATE_DATE);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CLOSED_DATE_TIME), G_ERROR.MSG.empty_error+LABEL.CLOSED_DATE_TIME);
		bValid = bValid && fv.checkTime($(FORM_FIELD.CLOSED_DATE_TIME), G_ERROR.MSG.invalid_time_error+LABEL.CLOSED_DATE_TIME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.STATUS), G_ERROR.MSG.empty_error+LABEL.STATUS);
*/
		return bValid;
	}
	function setFormDefaults() {
		DEFAULT.SYSTEM_ISSUE_ID = getNewId(getStorageData(), (INDEX.SYSTEM_ISSUE_ID));
		$(FORM_FIELD.SYSTEM_ISSUE_ID).val(DEFAULT.SYSTEM_ISSUE_ID);
		$(FORM_FIELD.SYSTEM_ISSUE_DATE_DATE).val(DEFAULT.SYSTEM_ISSUE_DATE_DATE);
		$(FORM_FIELD.SYSTEM_ISSUE_DATE_TIME).val(DEFAULT.SYSTEM_ISSUE_DATE_TIME);
		$(FORM_FIELD.SCREEN_NAME).val(DEFAULT.SCREEN_NAME);
		$(FORM_FIELD.DESCRIPTION).val(DEFAULT.DESCRIPTION);
		$(FORM_FIELD.IMAGE_FILE_PATH).val(DEFAULT.IMAGE_FILE_PATH);
		var lookup = LookupScript.getInstance();
		lookup.populateInfo( $(FORM_FIELD.ISSUE_TYPE_LOOKUP_ID), DEFAULT.ISSUE_TYPE_LOOKUP_ID );
		lookup.populateInfo( $(FORM_FIELD.ISSUE_PRIORITY_LOOKUP_ID), DEFAULT.ISSUE_PRIORITY_LOOKUP_ID );
		$(FORM_FIELD.RESOLUTION).val(DEFAULT.RESOLUTION);
		var sysUser = SysUserScript.getInstance();
		sysUser.populateInfo( $(FORM_FIELD.SYS_USER_ID), DEFAULT.SYS_USER_ID );
		sysUser.populateInfo( $(FORM_FIELD.CLOSED_BY_SYS_USER_ID), DEFAULT.CLOSED_BY_SYS_USER_ID );
		$(FORM_FIELD.CLOSED_DATE_DATE).val(DEFAULT.CLOSED_DATE_DATE);
		$(FORM_FIELD.CLOSED_DATE_TIME).val(DEFAULT.CLOSED_DATE_TIME);
		$(FORM_FIELD.STATUS).val(DEFAULT.STATUS);
	}
	function popUpAddForm(field) {

		if(field){

			formField = field;

			isChildForm = true;

		}

		setFormDefaults();

		displayForm("Add New System Issue", INSERT_DATA, 0);
	}

	function popUpEditForm(data, row_index) {

		$(FORM_FIELD.SYSTEM_ISSUE_ID).val(data[INDEX.SYSTEM_ISSUE_ID-1]);
		$(FORM_FIELD.SYSTEM_ISSUE_DATE_DATE).val(timeToDisplayDate(data[INDEX.SYSTEM_ISSUE_DATE-1]));
		$(FORM_FIELD.SYSTEM_ISSUE_DATE_TIME).val(timeToDisplayTime(data[INDEX.SYSTEM_ISSUE_DATE-1]));
		$(FORM_FIELD.SCREEN_NAME).val(data[INDEX.SCREEN_NAME-1]);
		$(FORM_FIELD.DESCRIPTION).val(data[INDEX.DESCRIPTION-1]);
		$(FORM_FIELD.IMAGE_FILE_PATH).val(data[INDEX.IMAGE_FILE_PATH-1]);
		var lookup = LookupScript.getInstance();
		lookup.populateInfo( $(FORM_FIELD.ISSUE_TYPE_LOOKUP_ID), DEFAULT.ISSUE_TYPE_LOOKUP_ID, data[INDEX.ISSUE_TYPE_LOOKUP_ID-1] );
		lookup.populateInfo( $(FORM_FIELD.ISSUE_PRIORITY_LOOKUP_ID), DEFAULT.ISSUE_PRIORITY_LOOKUP_ID, data[INDEX.ISSUE_PRIORITY_LOOKUP_ID-1] );
		$(FORM_FIELD.RESOLUTION).val(data[INDEX.RESOLUTION-1]);
		var sysUser = SysUserScript.getInstance();
		sysUser.populateInfo( $(FORM_FIELD.SYS_USER_ID), data[INDEX.SYS_USER_ID-1] );
		sysUser.populateInfo( $(FORM_FIELD.CLOSED_BY_SYS_USER_ID), data[INDEX.CLOSED_BY_SYS_USER_ID-1] );
		$(FORM_FIELD.CLOSED_DATE_DATE).val(timeToDisplayDate(data[INDEX.CLOSED_DATE-1]));
		$(FORM_FIELD.CLOSED_DATE_TIME).val(timeToDisplayTime(data[INDEX.CLOSED_DATE-1]));
		$(FORM_FIELD.STATUS).val(data[INDEX.STATUS-1]);
		displayForm("Edit System Issue", UPDATE_DATA, row_index);
	}

	function getFormDataAsJson(){
		var jsonData = {
			system_issue_id : ($(FORM_FIELD.SYSTEM_ISSUE_ID).val()),
			system_issue_date : DateTimeToSaveTime($(FORM_FIELD.SYSTEM_ISSUE_DATE_DATE).val(), $(FORM_FIELD.SYSTEM_ISSUE_DATE_TIME).val()),
			screen_name : ($(FORM_FIELD.SCREEN_NAME).val()),
			description : ($(FORM_FIELD.DESCRIPTION).val()),
			image_file_path : ($(FORM_FIELD.IMAGE_FILE_PATH).val()),
			issue_type_lookup_id : ($(FORM_FIELD.ISSUE_TYPE_LOOKUP_ID).val()),
			issue_priority_lookup_id : ($(FORM_FIELD.ISSUE_PRIORITY_LOOKUP_ID).val()),
			resolution : ($(FORM_FIELD.RESOLUTION).val()),
			sys_user_id : ($(FORM_FIELD.SYS_USER_ID).val()),
			closed_by_sys_user_id : ($(FORM_FIELD.CLOSED_BY_SYS_USER_ID).val()),
			closed_date : DateTimeToSaveTime($(FORM_FIELD.CLOSED_DATE_DATE).val(), $(FORM_FIELD.CLOSED_DATE_TIME).val()),
			status : ($(FORM_FIELD.STATUS).val())
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
				dialogMessage="System Issue has been successfully added ("/*+jsonData.name*/ +" Id:"+response.data.id+")";
			else if( status && response.data.status_code >= 0 && mode==UPDATE_DATA)
				dialogMessage="System Issue has been successfully updated("/*+jsonData.name*/+" Id:"+response.data.id+")";
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
		var key_column_index = [ INDEX.SYSTEM_ISSUE_ID ];
		row_id_arr = getArrayFromArray({ index : key_column_index, data_arr : row_data_arr});

		var jsonData = {
				del_row_id_arr : row_id_arr
//			,system_issue_id : getSystemIssueId()	// ADD ANY OTHER CONDITION (IF ANY)
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

					dialogMessage="System Issue has been successfully deleted :"+response.data.rows_deleted+" row(s)";


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
					dialogMessage = "Cannot delete System Issue. There are some dependencies for this System Issue.";
				else
					dialogMessage = "Cannot delete System Issue. There are some dependencies for this System Issue.";

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

		if(option && option.system_issue_id){
			$.each(option.system_issue_id, function(inx, val){
				_url += '&system_issue_id='+val;
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
		var  id_index = INDEX.SYSTEM_ISSUE_ID, name_index = getNameIdx();
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
						console.log('SYSTEM_ISSUE_DATA is empty : selected_id=' +select_id );
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
			SystemIssueSendSms();
			break;
		case 2:
			SystemIssueSendEmail();
			break;	
		default:
			log('System Issue Action', 'unknown parameter');
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
		return INDEX.SYSTEM_ISSUE_ID;
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
