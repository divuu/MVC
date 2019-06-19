////////////////////////////////////////////////////////////////////////////

// FileName Organization_script.js: Organization Javascript file

// Author : Vinu | Utkarsh | JRC
// Description : Digi Park v1.0


////////////////////////////////////////////////////////////////////////////


var OrganizationScript = (function(){
	// Stores the field of the parent form
	var formField;

	// Is true if the form is launched from another form, as child form
	var isChildForm = false;

	// Instance stores a reference to the Singleton
	var instance;

	//Url
	var URL="/OrganizationDataHandler";

	//table relation attributes
	var REL = "OrganizationDetails";
	var FORM_ID = "edit_organization_form";

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
		ORGANIZATION_ID : value++,		// 1
		ORGANIZATION_NAME : value++,		// 2
		SHORT_NAME : value++,		// 3
		REGISTERED_ADDRESS : value++,		// 4
		BUSINESS_ADDRESS : value++,		// 5
		CITY_ID : value++,		// 6
		STATE_LOOKUP_ID : value++,		// 7
		COUNTRY_ID : value++,		// 8
		EMAIL : value++,		// 9
		WEBSITE : value++,		// 10
		MOBILE_NUMBER : value++,		// 11
		LANDLINE_NUMBER : value++,		// 12
		FAX_NUMBER : value++,		// 13
		CONTACT_PERSON_NAME : value++,		// 14
		CONTACT_PERSON_MOBILE_NUMBER : value++,		// 15
		CONTACT_PERSON_WHATSAPP_NO : value++,		// 16
		CONTACT_PERSON_EMAIL : value++,		// 17
		LOGO_PATH : value++,		// 18
		DOCUMENTS_PATH : value++,		// 19
		BANK_ACCOUNT_ID : value++,		// 20
		TYPE_OF_BUSINESS : value++,		// 21
		REMARKS : value++,		// 22
		PREFERENCES : value++		// 23
	};

	//-------------Table Header Label----------------------

	var LABEL = {

		ORGANIZATION_ID : "Organization",
		ORGANIZATION_NAME : "Organization Name",
		SHORT_NAME : "Short Name",
		REGISTERED_ADDRESS : "Registered Address",
		BUSINESS_ADDRESS : "Business Address",
		CITY_ID : "City",
		STATE_LOOKUP_ID : "State",
		COUNTRY_ID : "Country",
		EMAIL : "Email",
		WEBSITE : "Website",
		MOBILE_NUMBER : "Mobile Number",
		LANDLINE_NUMBER : "Landline Number",
		FAX_NUMBER : "Fax Number",
		CONTACT_PERSON_NAME : "Contact Person Name",
		CONTACT_PERSON_MOBILE_NUMBER : "Contact Person Mobile Number",
		CONTACT_PERSON_WHATSAPP_NO : "Contact Person Whatsapp No",
		CONTACT_PERSON_EMAIL : "Contact Person Email",
		LOGO_PATH : "Logo Path",
		DOCUMENTS_PATH : "Documents Path",
		BANK_ACCOUNT_ID : "Bank Account",
		TYPE_OF_BUSINESS : "Type Of Business",
		REMARKS : "Remarks",
		PREFERENCES : "Preferences"
	};

	//-----------------------------Default values------------------------------------
	//// TODO: Assign group_lookup_id of Lookup forign keys
	var DEFAULT = {

		ORGANIZATION_ID : 0,
		ORGANIZATION_NAME : "",
		SHORT_NAME : "",
		REGISTERED_ADDRESS : "",
		BUSINESS_ADDRESS : "",
		CITY_ID : 0,
		STATE_LOOKUP_ID : 18,
		COUNTRY_ID : 0,
		EMAIL : "",
		WEBSITE : "",
		MOBILE_NUMBER : "",
		LANDLINE_NUMBER : "",
		FAX_NUMBER : "",
		CONTACT_PERSON_NAME : "",
		CONTACT_PERSON_MOBILE_NUMBER : "",
		CONTACT_PERSON_WHATSAPP_NO : "",
		CONTACT_PERSON_EMAIL : "",
		LOGO_PATH : "",
		DOCUMENTS_PATH : "",
		BANK_ACCOUNT_ID : 0,
		TYPE_OF_BUSINESS : 0,
		REMARKS : "",
		PREFERENCES : 0
	};

	//-----------------------------Form Elements------------------------------------
	var FORM_FIELD = {

		ORGANIZATION_ID : "#"+FORM_ID+" input[name=organization_id]",
		ORGANIZATION_NAME : "#"+FORM_ID+" input[name=organization_name]",
		SHORT_NAME : "#"+FORM_ID+" input[name=short_name]",
		REGISTERED_ADDRESS : "#"+FORM_ID+" textarea[name=registered_address]",
		BUSINESS_ADDRESS : "#"+FORM_ID+" textarea[name=business_address]",
		CITY_ID : "#"+FORM_ID+" select[name=city_id]",
		STATE_LOOKUP_ID : "#"+FORM_ID+" select[name=state_lookup_id]",
		COUNTRY_ID : "#"+FORM_ID+" select[name=country_id]",
		EMAIL : "#"+FORM_ID+" input[name=email]",
		WEBSITE : "#"+FORM_ID+" input[name=website]",
		MOBILE_NUMBER : "#"+FORM_ID+" input[name=mobile_number]",
		LANDLINE_NUMBER : "#"+FORM_ID+" input[name=landline_number]",
		FAX_NUMBER : "#"+FORM_ID+" input[name=fax_number]",
		CONTACT_PERSON_NAME : "#"+FORM_ID+" input[name=contact_person_name]",
		CONTACT_PERSON_MOBILE_NUMBER : "#"+FORM_ID+" input[name=contact_person_mobile_number]",
		CONTACT_PERSON_WHATSAPP_NO : "#"+FORM_ID+" input[name=contact_person_whatsapp_no]",
		CONTACT_PERSON_EMAIL : "#"+FORM_ID+" input[name=contact_person_email]",
		LOGO_PATH : "#"+FORM_ID+" textarea[name=logo_path]",
		DOCUMENTS_PATH : "#"+FORM_ID+" textarea[name=documents_path]",
		BANK_ACCOUNT_ID : "#"+FORM_ID+" select[name=bank_account_id]",
		TYPE_OF_BUSINESS : "#"+FORM_ID+" input[name=type_of_business]",
		REMARKS : "#"+FORM_ID+" textarea[name=remarks]",
		PREFERENCES : "#"+FORM_ID+" input[name=preferences]"
	};
	/*------------------------- Load Dependents---------------------------------------*/
	function loadDependents(option) {
		//updateCityData({callback : function(){
			if(undefined !== option)
				option.callback();
		//}});
	}

	//get Organization data
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
	//set Organization data
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
		//$( FORM_FIELD.FIELDNAME ).change(onFieldNameChange)
			$( FORM_FIELD.CITY_ID ).change(onCityChange);
	}

	//handle on change of city
	function onCityChange(){
		
		var city = CityScript.getInstance()
		var cityObject = getObjectById( $(this).val(), city)
		
		console.log(cityObject);
		if( cityObject != null && $(this).val() != 0 ){
			$( FORM_FIELD.COUNTRY_ID ).val( cityObject.values[ CityScript.INDEX.COUNTRY_ID - 1 ] )
			//console.log(cityObject.values[CityScript.INDEX.COUNTRY_ID - 1]);
			$( FORM_FIELD.STATE_LOOKUP_ID).val (cityObject.values[ CityScript.INDEX.STATE_LOOKUP_ID -1 ])
			//console.log(cityObject.values[CityScript.INDEX.STATE_LOOKUP_ID - 1]);
		}else{
			$( FORM_FIELD.COUNTRY_ID ).val( 0 )
		}
		
	}
	
	function loadTabContent(){

		
		var city = CityScript.getInstance();
		city.getData();

		var lookup = LookupScript.getInstance();
		lookup.getData();

		var country = CountryScript.getInstance();
		country.getData();

		// var bankAccount = BankAccountScript.getInstance();
		// bankAccount.getData();



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
		var city = CityScript.getInstance();
		var lookup = LookupScript.getInstance();
		var country = CountryScript.getInstance();
		//var bankAccount = BankAccountScript.getInstance();
		//render column
		var aoColumnDefs_render = [

					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.ORGANIZATION_ID ] ); },
								"aTargets": [ INDEX.ORGANIZATION_ID ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.ORGANIZATION_NAME ] ); },
								"aTargets": [ INDEX.ORGANIZATION_NAME ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.SHORT_NAME ] ); },
								"aTargets": [ INDEX.SHORT_NAME ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.REGISTERED_ADDRESS ] ); },
								"aTargets": [ INDEX.REGISTERED_ADDRESS ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.BUSINESS_ADDRESS ] ); },
								"aTargets": [ INDEX.BUSINESS_ADDRESS ]
					},
					{
					"fnRender": function ( o, val ) {
						var cityName = getValueById( o.aData[ INDEX.CITY_ID ], city );
						return cityName;
						},
						"aTargets": [ INDEX.CITY_ID ]
					},
					{
					"fnRender": function ( o, val ) {
						var lookupName = getValueById( o.aData[ INDEX.STATE_LOOKUP_ID ], lookup );
						return lookupName;
						},
						"aTargets": [ INDEX.STATE_LOOKUP_ID ]
					},
					{
					"fnRender": function ( o, val ) {
						var countryName = getValueById( o.aData[ INDEX.COUNTRY_ID ], country );
						return countryName;
						},
						"aTargets": [ INDEX.COUNTRY_ID ]
					},
					{
						"fnRender":	function ( o, val ) {
									return getEmailLink( o.aData[ INDEX.EMAIL ] ); },
								"aTargets": [ INDEX.EMAIL ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.WEBSITE ] ); },
								"aTargets": [ INDEX.WEBSITE ]
					},
					{
						"fnRender":	function ( o, val ) {
									return getSmsLink( o.aData[ INDEX.MOBILE_NUMBER ] ); },
								"aTargets": [ INDEX.MOBILE_NUMBER ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.LANDLINE_NUMBER ] ); },
								"aTargets": [ INDEX.LANDLINE_NUMBER ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.FAX_NUMBER ] ); },
								"aTargets": [ INDEX.FAX_NUMBER ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.CONTACT_PERSON_NAME ] ); },
								"aTargets": [ INDEX.CONTACT_PERSON_NAME ]
					},
					{
						"fnRender":	function ( o, val ) {
									return getSmsLink( o.aData[ INDEX.CONTACT_PERSON_MOBILE_NUMBER ] ); },
								"aTargets": [ INDEX.CONTACT_PERSON_MOBILE_NUMBER ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.CONTACT_PERSON_WHATSAPP_NO ] ); },
								"aTargets": [ INDEX.CONTACT_PERSON_WHATSAPP_NO ]
					},
					{
						"fnRender":	function ( o, val ) {
									return getEmailLink( o.aData[ INDEX.CONTACT_PERSON_EMAIL ] ); },
								"aTargets": [ INDEX.CONTACT_PERSON_EMAIL ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.LOGO_PATH ] ); },
								"aTargets": [ INDEX.LOGO_PATH ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.DOCUMENTS_PATH ] ); },
								"aTargets": [ INDEX.DOCUMENTS_PATH ]
					},
					// {
					// "fnRender": function ( o, val ) {
					// 	var bankAccountName = getValueById( o.aData[ INDEX.BANK_ACCOUNT_ID ], bankAccount );
					// 	return bankAccountName;
					// 	},
					// 	"aTargets": [ INDEX.BANK_ACCOUNT_ID ]
					// },
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.TYPE_OF_BUSINESS ] ); },
								"aTargets": [ INDEX.TYPE_OF_BUSINESS ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.REMARKS ] ); },
								"aTargets": [ INDEX.REMARKS ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.PREFERENCES ] ); },
								"aTargets": [ INDEX.PREFERENCES ]
					}
					];
		//Define Column Headings
		var aoColumns_headings = [

						{	"sTitle": '<input type="checkbox" name="select_all_rows" title="Select All/None">', "sClass": "center", "bSortable": false, "sWidth": "5%"  }

						,{	"sTitle": json.headings[INDEX.ORGANIZATION_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.ORGANIZATION_NAME-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.SHORT_NAME-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.REGISTERED_ADDRESS-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.BUSINESS_ADDRESS-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.CITY_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.STATE_LOOKUP_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.COUNTRY_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.EMAIL-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.WEBSITE-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.MOBILE_NUMBER-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.LANDLINE_NUMBER-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.FAX_NUMBER-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.CONTACT_PERSON_NAME-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.CONTACT_PERSON_MOBILE_NUMBER-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.CONTACT_PERSON_WHATSAPP_NO-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.CONTACT_PERSON_EMAIL-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.LOGO_PATH-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.DOCUMENTS_PATH-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.BANK_ACCOUNT_ID-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.TYPE_OF_BUSINESS-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.REMARKS-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.PREFERENCES-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.EDIT_ORGANIZATION) == true ){ //edit enabled : permissions
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.ADD_ORGANIZATION) == true ){
				$(add_button_id).on('click',function(){
					popUpAddForm();
				});
			}
			else{
				$(add_button_id).button({disabled: true});
			}

		//Edit button
		var edit_button_id = "#"+button_panel_id+' button.edit_button';
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.EDIT_ORGANIZATION) == true ){
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.DELETE_ORGANIZATION) == true ){
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

							var table_name = "Organization";

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
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.ORGANIZATION_ID), G_ERROR.MSG.empty_error+LABEL.ORGANIZATION_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.ORGANIZATION_NAME), G_ERROR.MSG.empty_error+LABEL.ORGANIZATION_NAME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SHORT_NAME), G_ERROR.MSG.empty_error+LABEL.SHORT_NAME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.REGISTERED_ADDRESS), G_ERROR.MSG.empty_error+LABEL.REGISTERED_ADDRESS);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.BUSINESS_ADDRESS), G_ERROR.MSG.empty_error+LABEL.BUSINESS_ADDRESS);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.CITY_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.CITY_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.STATE_LOOKUP_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.STATE_LOOKUP_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.COUNTRY_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.COUNTRY_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.EMAIL), G_ERROR.MSG.empty_error+LABEL.EMAIL);
		bValid = bValid && fv.checkEmail($(FORM_FIELD.EMAIL), LABEL.EMAIL+G_ERROR.MSG.invalid_emailid_error);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.WEBSITE), G_ERROR.MSG.empty_error+LABEL.WEBSITE);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.MOBILE_NUMBER), G_ERROR.MSG.empty_error+LABEL.MOBILE_NUMBER);
		bValid = bValid && fv.validateMobileNo( $(FORM_FIELD.MOBILE_NUMBER), country_code, 0 , LABEL.MOBILE_NUMBER+G_ERROR.MSG.invalid_mobile_no);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.LANDLINE_NUMBER), G_ERROR.MSG.empty_error+LABEL.LANDLINE_NUMBER);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.FAX_NUMBER), G_ERROR.MSG.empty_error+LABEL.FAX_NUMBER);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CONTACT_PERSON_NAME), G_ERROR.MSG.empty_error+LABEL.CONTACT_PERSON_NAME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CONTACT_PERSON_MOBILE_NUMBER), G_ERROR.MSG.empty_error+LABEL.CONTACT_PERSON_MOBILE_NUMBER);
		bValid = bValid && fv.validateMobileNo( $(FORM_FIELD.CONTACT_PERSON_MOBILE_NUMBER), country_code, 0 , LABEL.CONTACT_PERSON_MOBILE_NUMBER+G_ERROR.MSG.invalid_mobile_no);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CONTACT_PERSON_WHATSAPP_NO), G_ERROR.MSG.empty_error+LABEL.CONTACT_PERSON_WHATSAPP_NO);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CONTACT_PERSON_EMAIL), G_ERROR.MSG.empty_error+LABEL.CONTACT_PERSON_EMAIL);
		bValid = bValid && fv.checkEmail($(FORM_FIELD.CONTACT_PERSON_EMAIL), LABEL.CONTACT_PERSON_EMAIL+G_ERROR.MSG.invalid_emailid_error);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.LOGO_PATH), G_ERROR.MSG.empty_error+LABEL.LOGO_PATH);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.DOCUMENTS_PATH), G_ERROR.MSG.empty_error+LABEL.DOCUMENTS_PATH);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.BANK_ACCOUNT_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.BANK_ACCOUNT_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.TYPE_OF_BUSINESS), G_ERROR.MSG.empty_error+LABEL.TYPE_OF_BUSINESS);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.REMARKS), G_ERROR.MSG.empty_error+LABEL.REMARKS);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.PREFERENCES), G_ERROR.MSG.empty_error+LABEL.PREFERENCES);
*/
		return bValid;
	}
	function setFormDefaults() {
		DEFAULT.ORGANIZATION_ID = getNewId(getStorageData(), (INDEX.ORGANIZATION_ID));
		$(FORM_FIELD.ORGANIZATION_ID).val(DEFAULT.ORGANIZATION_ID);
		$(FORM_FIELD.ORGANIZATION_NAME).val(DEFAULT.ORGANIZATION_NAME);
		$(FORM_FIELD.SHORT_NAME).val(DEFAULT.SHORT_NAME);
		$(FORM_FIELD.REGISTERED_ADDRESS).val(DEFAULT.REGISTERED_ADDRESS);
		$(FORM_FIELD.BUSINESS_ADDRESS).val(DEFAULT.BUSINESS_ADDRESS);
		var city = CityScript.getInstance();
		city.populateInfo( $(FORM_FIELD.CITY_ID), DEFAULT.CITY_ID );
		var lookup = LookupScript.getInstance();
		lookup.populateInfo( $(FORM_FIELD.STATE_LOOKUP_ID), DEFAULT.STATE_LOOKUP_ID );
		var country = CountryScript.getInstance();
		country.populateInfo( $(FORM_FIELD.COUNTRY_ID), DEFAULT.COUNTRY_ID );
		$(FORM_FIELD.EMAIL).val(DEFAULT.EMAIL);
		$(FORM_FIELD.WEBSITE).val(DEFAULT.WEBSITE);
		$(FORM_FIELD.MOBILE_NUMBER).val(DEFAULT.MOBILE_NUMBER);
		$(FORM_FIELD.LANDLINE_NUMBER).val(DEFAULT.LANDLINE_NUMBER);
		$(FORM_FIELD.FAX_NUMBER).val(DEFAULT.FAX_NUMBER);
		$(FORM_FIELD.CONTACT_PERSON_NAME).val(DEFAULT.CONTACT_PERSON_NAME);
		$(FORM_FIELD.CONTACT_PERSON_MOBILE_NUMBER).val(DEFAULT.CONTACT_PERSON_MOBILE_NUMBER);
		$(FORM_FIELD.CONTACT_PERSON_WHATSAPP_NO).val(DEFAULT.CONTACT_PERSON_WHATSAPP_NO);
		$(FORM_FIELD.CONTACT_PERSON_EMAIL).val(DEFAULT.CONTACT_PERSON_EMAIL);
		$(FORM_FIELD.LOGO_PATH).val(DEFAULT.LOGO_PATH);
		$(FORM_FIELD.DOCUMENTS_PATH).val(DEFAULT.DOCUMENTS_PATH);
		// var bankAccount = BankAccountScript.getInstance();
		// bankAccount.populateInfo( $(FORM_FIELD.BANK_ACCOUNT_ID), DEFAULT.BANK_ACCOUNT_ID );
		$(FORM_FIELD.TYPE_OF_BUSINESS).val(DEFAULT.TYPE_OF_BUSINESS);
		$(FORM_FIELD.REMARKS).val(DEFAULT.REMARKS);
		$(FORM_FIELD.PREFERENCES).val(DEFAULT.PREFERENCES);
	}
	function popUpAddForm(field) {

		if(field){

			formField = field;

			isChildForm = true;

		}

		setFormDefaults();

		displayForm("Add New Organization", INSERT_DATA, 0);
	}

	function popUpEditForm(data, row_index) {

		$(FORM_FIELD.ORGANIZATION_ID).val(data[INDEX.ORGANIZATION_ID-1]);
		$(FORM_FIELD.ORGANIZATION_NAME).val(data[INDEX.ORGANIZATION_NAME-1]);
		$(FORM_FIELD.SHORT_NAME).val(data[INDEX.SHORT_NAME-1]);
		$(FORM_FIELD.REGISTERED_ADDRESS).val(data[INDEX.REGISTERED_ADDRESS-1]);
		$(FORM_FIELD.BUSINESS_ADDRESS).val(data[INDEX.BUSINESS_ADDRESS-1]);
		var city = CityScript.getInstance();
		city.populateInfo( $(FORM_FIELD.CITY_ID), data[INDEX.CITY_ID-1] );
		var lookup = LookupScript.getInstance();
	    lookup.populateInfo( $(FORM_FIELD.STATE_LOOKUP_ID), DEFAULT.STATE_LOOKUP_ID, data[INDEX.STATE_LOOKUP_ID-1] );
		var country = CountryScript.getInstance();
		country.populateInfo( $(FORM_FIELD.COUNTRY_ID), data[INDEX.COUNTRY_ID-1] );
		$(FORM_FIELD.EMAIL).val(data[INDEX.EMAIL-1]);
		$(FORM_FIELD.WEBSITE).val(data[INDEX.WEBSITE-1]);
		$(FORM_FIELD.MOBILE_NUMBER).val(data[INDEX.MOBILE_NUMBER-1]);
		$(FORM_FIELD.LANDLINE_NUMBER).val(data[INDEX.LANDLINE_NUMBER-1]);
		$(FORM_FIELD.FAX_NUMBER).val(data[INDEX.FAX_NUMBER-1]);
		$(FORM_FIELD.CONTACT_PERSON_NAME).val(data[INDEX.CONTACT_PERSON_NAME-1]);
		$(FORM_FIELD.CONTACT_PERSON_MOBILE_NUMBER).val(data[INDEX.CONTACT_PERSON_MOBILE_NUMBER-1]);
		$(FORM_FIELD.CONTACT_PERSON_WHATSAPP_NO).val(data[INDEX.CONTACT_PERSON_WHATSAPP_NO-1]);
		$(FORM_FIELD.CONTACT_PERSON_EMAIL).val(data[INDEX.CONTACT_PERSON_EMAIL-1]);
		$(FORM_FIELD.LOGO_PATH).val(data[INDEX.LOGO_PATH-1]);
		$(FORM_FIELD.DOCUMENTS_PATH).val(data[INDEX.DOCUMENTS_PATH-1]);
		// var bankAccount = BankAccountScript.getInstance();
		// bankAccount.populateInfo( $(FORM_FIELD.BANK_ACCOUNT_ID), data[INDEX.BANK_ACCOUNT_ID-1] );
		$(FORM_FIELD.TYPE_OF_BUSINESS).val(data[INDEX.TYPE_OF_BUSINESS-1]);
		$(FORM_FIELD.REMARKS).val(data[INDEX.REMARKS-1]);
		$(FORM_FIELD.PREFERENCES).val(data[INDEX.PREFERENCES-1]);
		displayForm("Edit Organization", UPDATE_DATA, row_index);
	}

	function getFormDataAsJson(){
		var jsonData = {
			organization_id : ($(FORM_FIELD.ORGANIZATION_ID).val()),
			organization_name : ($(FORM_FIELD.ORGANIZATION_NAME).val()),
			short_name : ($(FORM_FIELD.SHORT_NAME).val()),
			registered_address : ($(FORM_FIELD.REGISTERED_ADDRESS).val()),
			business_address : ($(FORM_FIELD.BUSINESS_ADDRESS).val()),
			city_id : ($(FORM_FIELD.CITY_ID).val()),
			state_lookup_id : ($(FORM_FIELD.STATE_LOOKUP_ID).val()),
			country_id : ($(FORM_FIELD.COUNTRY_ID).val()),
			email : ($(FORM_FIELD.EMAIL).val()),
			website : ($(FORM_FIELD.WEBSITE).val()),
			mobile_number : ($(FORM_FIELD.MOBILE_NUMBER).val()),
			landline_number : ($(FORM_FIELD.LANDLINE_NUMBER).val()),
			fax_number : ($(FORM_FIELD.FAX_NUMBER).val()),
			contact_person_name : ($(FORM_FIELD.CONTACT_PERSON_NAME).val()),
			contact_person_mobile_number : ($(FORM_FIELD.CONTACT_PERSON_MOBILE_NUMBER).val()),
			contact_person_whatsApp_no : ($(FORM_FIELD.CONTACT_PERSON_WHATSAPP_NO).val()),
			contact_person_email : ($(FORM_FIELD.CONTACT_PERSON_EMAIL).val()),
			logo_path : ($(FORM_FIELD.LOGO_PATH).val()),
			documents_path : ($(FORM_FIELD.DOCUMENTS_PATH).val()),
			bank_account_id : 0,//($(FORM_FIELD.BANK_ACCOUNT_ID).val()),
			type_of_business : 0,//($(FORM_FIELD.TYPE_OF_BUSINESS).val()),
			remarks : ($(FORM_FIELD.REMARKS).val()),
			preferences : ($(FORM_FIELD.PREFERENCES).val())
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
				dialogMessage="Organization has been successfully added ("/*+jsonData.name*/ +" Id:"+response.data.id+")";
			else if( status && response.data.status_code >= 0 && mode==UPDATE_DATA)
				dialogMessage="Organization has been successfully updated("/*+jsonData.name*/+" Id:"+response.data.id+")";
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
		var key_column_index = [ INDEX.ORGANIZATION_ID ];
		row_id_arr = getArrayFromArray({ index : key_column_index, data_arr : row_data_arr});

		var jsonData = {
				del_row_id_arr : row_id_arr
//			,organization_id : getOrganizationId()	// ADD ANY OTHER CONDITION (IF ANY)
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

					dialogMessage="Organization has been successfully deleted :"+response.data.rows_deleted+" row(s)";


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
					dialogMessage = "Cannot delete Organization. There are some dependencies for this Organization.";
				else
					dialogMessage = "Cannot delete Organization. There are some dependencies for this Organization.";

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

		//var _url = ROOT_URL + URL +"/select?organization_id="+ getOrganizationId();	//sys_user_id="+ getCurLogUserId();
		var _url = ROOT_URL + URL +"/select?organization_id="+ getCurLogUserId();
		if(option && option.organization_id){
			$.each(option.organization_id, function(inx, val){
				_url += '&organization_id='+val;
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
		var  id_index = INDEX.ORGANIZATION_ID, name_index = getNameIdx();
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
						console.log('ORGANIZATION_DATA is empty : selected_id=' +select_id );
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
			OrganizationSendSms();
			break;
		case 2:
			OrganizationSendEmail();
			break;	
		default:
			log('Organization Action', 'unknown parameter');
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
		return INDEX.ORGANIZATION_ID;
	}

	function getNameIdx() {
		console.log('Please Change the Name Idx');
		return INDEX.ORGANIZATION_NAME;
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
