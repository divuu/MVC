////////////////////////////////////////////////////////////////////////////

// FileName City_script.js: City Javascript file

// Author : Vinu | Utkarsh | JRC
// Description : Digi Park v1.0


////////////////////////////////////////////////////////////////////////////


var CityScript = (function(){
	// Stores the field of the parent form
	var formField;

	// Is true if the form is launched from another form, as child form
	var isChildForm = false;

	// Instance stores a reference to the Singleton
	var instance;

	//Url
	var URL="/CityDataHandler";

	//table relation attributes
	var REL = "CityDetails";
	var FORM_ID = "edit_city_form";

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
		CITY_ID : value++,		// 1
		CITY : value++,		// 2
		CITY_AREA_CODE : value++,		// 3
		TIME_ZONE : value++,		// 4
		STATE_LOOKUP_ID : value++,		// 5
		COUNTRY_ID : value++		// 6
	};

	//-------------Table Header Label----------------------

	var LABEL = {

		CITY_ID : "City",
		CITY : "City",
		CITY_AREA_CODE : "City Area Code",
		TIME_ZONE : "Time Zone",
		STATE_LOOKUP_ID : "State",
		COUNTRY_ID : "Country"
	};

	//-----------------------------Default values------------------------------------
	//// TODO: Assign group_lookup_id of Lookup forign keys
	var DEFAULT = {

		CITY_ID : 0,
		CITY : "",
		CITY_AREA_CODE : "",
		TIME_ZONE : "",
		STATE_LOOKUP_ID : 18,
		COUNTRY_ID : 0
	};

	//-----------------------------Form Elements------------------------------------
	var FORM_FIELD = {

		CITY_ID : "#"+FORM_ID+" input[name=city_id]",
		CITY : "#"+FORM_ID+" input[name=city]",
		CITY_AREA_CODE : "#"+FORM_ID+" input[name=city_area_code]",
		TIME_ZONE : "#"+FORM_ID+" input[name=time_zone]",
		STATE_LOOKUP_ID : "#"+FORM_ID+" select[name=state_lookup_id]",
		COUNTRY_ID : "#"+FORM_ID+" select[name=country_id]"
	};
	/*------------------------- Load Dependents---------------------------------------*/
	function loadDependents(option) {
		//updateCityData({callback : function(){
			if(undefined !== option)
				option.callback();
		//}});
	}

	//get City data
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
	//set City data
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

		var country = CountryScript.getInstance();
		country.getData();



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
		var country = CountryScript.getInstance();
		//render column
		var aoColumnDefs_render = [

					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.CITY_ID ] ); },
								"aTargets": [ INDEX.CITY_ID ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.CITY ] ); },
								"aTargets": [ INDEX.CITY ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.CITY_AREA_CODE ] ); },
								"aTargets": [ INDEX.CITY_AREA_CODE ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.TIME_ZONE ] ); },
								"aTargets": [ INDEX.TIME_ZONE ]
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
					}
					];
		//Define Column Headings
		var aoColumns_headings = [

						{	"sTitle": '<input type="checkbox" name="select_all_rows" title="Select All/None">', "sClass": "center", "bSortable": false, "sWidth": "5%"  }

						,{	"sTitle": json.headings[INDEX.CITY_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.CITY-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.CITY_AREA_CODE-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.TIME_ZONE-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.STATE_LOOKUP_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.COUNTRY_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.EDIT_CITY) == true ){ //edit enabled : permissions
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.ADD_CITY) == true ){
				$(add_button_id).on('click',function(){
					popUpAddForm();
				});
			}
			else{
				$(add_button_id).button({disabled: true});
			}

		//Edit button
		var edit_button_id = "#"+button_panel_id+' button.edit_button';
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.EDIT_CITY) == true ){
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.DELETE_CITY) == true ){
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

							var table_name = "City";

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
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CITY_ID), G_ERROR.MSG.empty_error+LABEL.CITY_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CITY), G_ERROR.MSG.empty_error+LABEL.CITY);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.CITY_AREA_CODE), G_ERROR.MSG.empty_error+LABEL.CITY_AREA_CODE);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.TIME_ZONE), G_ERROR.MSG.empty_error+LABEL.TIME_ZONE);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.STATE_LOOKUP_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.STATE_LOOKUP_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.COUNTRY_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.COUNTRY_ID);
*/
		return bValid;
	}
	function setFormDefaults() {
		DEFAULT.CITY_ID = getNewId(getStorageData(), (INDEX.CITY_ID));
		$(FORM_FIELD.CITY_ID).val(DEFAULT.CITY_ID);
		$(FORM_FIELD.CITY).val(DEFAULT.CITY);
		$(FORM_FIELD.CITY_AREA_CODE).val(DEFAULT.CITY_AREA_CODE);
		$(FORM_FIELD.TIME_ZONE).val(DEFAULT.TIME_ZONE);
		var lookup = LookupScript.getInstance();
		lookup.populateInfo( $(FORM_FIELD.STATE_LOOKUP_ID), DEFAULT.STATE_LOOKUP_ID );
		var country = CountryScript.getInstance();
		country.populateInfo( $(FORM_FIELD.COUNTRY_ID), DEFAULT.COUNTRY_ID );
	}
	function popUpAddForm(field) {

		if(field){

			formField = field;

			isChildForm = true;

		}

		setFormDefaults();

		displayForm("Add New City", INSERT_DATA, 0);
	}

	function popUpEditForm(data, row_index) {

		$(FORM_FIELD.CITY_ID).val(data[INDEX.CITY_ID-1]);
		$(FORM_FIELD.CITY).val(data[INDEX.CITY-1]);
		$(FORM_FIELD.CITY_AREA_CODE).val(data[INDEX.CITY_AREA_CODE-1]);
		$(FORM_FIELD.TIME_ZONE).val(data[INDEX.TIME_ZONE-1]);
		var lookup = LookupScript.getInstance();
		lookup.populateInfo( $(FORM_FIELD.STATE_LOOKUP_ID), DEFAULT.STATE_LOOKUP_ID, data[INDEX.STATE_LOOKUP_ID-1] );
		var country = CountryScript.getInstance();
		country.populateInfo( $(FORM_FIELD.COUNTRY_ID), data[INDEX.COUNTRY_ID-1] );
		displayForm("Edit City", UPDATE_DATA, row_index);
	}

	function getFormDataAsJson(){
		var jsonData = {
			city_id : ($(FORM_FIELD.CITY_ID).val()),
			city : ($(FORM_FIELD.CITY).val()),
			city_area_code : ($(FORM_FIELD.CITY_AREA_CODE).val()),
			time_zone : ($(FORM_FIELD.TIME_ZONE).val()),
			state_lookup_id : ($(FORM_FIELD.STATE_LOOKUP_ID).val()),
			country_id : ($(FORM_FIELD.COUNTRY_ID).val())
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
				dialogMessage="City has been successfully added ("/*+jsonData.name*/ +" Id:"+response.data.id+")";
			else if( status && response.data.status_code >= 0 && mode==UPDATE_DATA)
				dialogMessage="City has been successfully updated("/*+jsonData.name*/+" Id:"+response.data.id+")";
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
		var key_column_index = [ INDEX.CITY_ID ];
		row_id_arr = getArrayFromArray({ index : key_column_index, data_arr : row_data_arr});

		var jsonData = {
				del_row_id_arr : row_id_arr
//			,city_id : getCityId()	// ADD ANY OTHER CONDITION (IF ANY)
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

					dialogMessage="City has been successfully deleted :"+response.data.rows_deleted+" row(s)";


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
					dialogMessage = "Cannot delete City. There are some dependencies for this City.";
				else
					dialogMessage = "Cannot delete City. There are some dependencies for this City.";

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
		
		
		if(option && option.city_id){
			$.each(option.city_id, function(inx, val){
				_url += '&city_id='+val;
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
		var  id_index = INDEX.CITY_ID, name_index = getNameIdx();
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
						console.log('CITY_DATA is empty : selected_id=' +select_id );
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
			CitySendSms();
			break;
		case 2:
			CitySendEmail();
			break;	
		default:
			log('City Action', 'unknown parameter');
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
		return INDEX.CITY_ID;
	}

	function getNameIdx() {
		console.log('Please Change the Name Idx');
		return INDEX.CITY;
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
