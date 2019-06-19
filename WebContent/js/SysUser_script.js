////////////////////////////////////////////////////////////////////////////

// FileName SysUser_script.js: SysUser Javascript file

// Author : Vinu | Utkarsh | JRC
// Description : Slash Digital TVAS v1.0


////////////////////////////////////////////////////////////////////////////


var SysUserScript = (function(){
	// Stores the field of the parent form
	var formField;

	// Is true if the form is launched from another form, as child form
	var isChildForm = false;

	// Instance stores a reference to the Singleton
	var instance;

	//Url
	var URL="/SysUserDataHandler";

	//table relation attributes
	var REL = "SysUserDetails";
	var FORM_ID = "edit_sys_user_form";

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
		SYS_USER_ID : value++,		// 1
		ROLE_ID : value++,		// 2
		NAME : value++,		// 3
		MOBILE_NUMBER : value++,		// 4
		MOBILE_NUMBER_2 : value++,		// 5
		EMAIL : value++,		// 6
		ADDRESS : value++,		// 7
		CITY_ID : value++,		// 8
		COUNTRY_ID : value++,		// 9
		DESIGNATION_LOOKUP_ID : value++,		// 10
		LATITUDE : value++,		// 11
		LONGITUDE : value++,		// 12
		GENDER_LOOKUP_ID : value++,		// 13
		DATE_OF_BIRTH : value++,		// 14
		DRIVING_LICENSE_NUMBER : value++,		// 15
		PASSPORT_NUMBER : value++,		// 16
		REMARKS : value++,		// 17
		PHOTO : value++,		// 18
		DATE_OF_REGISTRATION : value++,		// 19
		LAST_LOGIN_DATE : value++,		// 20
		NO_OF_LOGINS : value++,		// 21
		CREATE_BY_SYS_USER_ID : value++,		// 22
		ACTIVE_LOOKUP_ID : value++,		// 23
		PARKING_AREA_ID : value++,		// 24
		LOGGED_IN_PARKING_AREA_GATE_ID : value++,		// 25
		STATUS : value++,		// 26
		PASSWORD : value++,		// 27
		SALT : value++,		// 28
		EXTRA_DATA : value++		// 29
	};

	//-------------Table Header Label----------------------

	var LABEL = {

		SYS_USER_ID : "Sys User",
		ROLE_ID : "Role",
		NAME : "Name",
		MOBILE_NUMBER : "Mobile Number",
		MOBILE_NUMBER_2 : "Mobile Number 2",
		EMAIL : "Email",
		ADDRESS : "Address",
		CITY_ID : "City",
		COUNTRY_ID : "Country",
		DESIGNATION_LOOKUP_ID : "Designation",
		LATITUDE : "Latitude",
		LONGITUDE : "Longitude",
		GENDER_LOOKUP_ID : "Gender",
		DATE_OF_BIRTH : "Date Of Birth",
		DRIVING_LICENSE_NUMBER : "Driving License Number",
		PASSPORT_NUMBER : "Passport Number",
		REMARKS : "Remarks",
		PHOTO : "Photo",
		DATE_OF_REGISTRATION_DATE : "Date Of Registration Date",
		DATE_OF_REGISTRATION_TIME : "Date Of Registration Time",
		LAST_LOGIN_DATE_DATE : "Last Login Date Date",
		LAST_LOGIN_DATE_TIME : "Last Login Date Time",
		NO_OF_LOGINS : "No Of Logins",
		CREATE_BY_SYS_USER_ID : "Create By Sys User",
		ACTIVE_LOOKUP_ID : "Active",
		PARKING_AREA_ID : "Parking Area",
		LOGGED_IN_PARKING_AREA_GATE_ID : "Logged In Parking Area Gate",
		STATUS : "Status",
		PASSWORD : "Password",
		SALT : "Salt",
		EXTRA_DATA : "Extra Data"
	};

	//-----------------------------Default values------------------------------------
	//// TODO: Assign group_lookup_id of Lookup forign keys
	var DEFAULT = {

		SYS_USER_ID : 0,
		ROLE_ID : 0,
		NAME : "",
		MOBILE_NUMBER : "",
		MOBILE_NUMBER_2 : "",
		EMAIL : "",
		ADDRESS : "",
		CITY_ID : 0,
		COUNTRY_ID : 0,
		DESIGNATION_LOOKUP_ID : 21,
		LATITUDE : 0.0,
		LONGITUDE : 0.0,
		GENDER_LOOKUP_ID : 1,
		DATE_OF_BIRTH : "",
		DRIVING_LICENSE_NUMBER : "",
		PASSPORT_NUMBER : "",
		REMARKS : "",
		PHOTO : "",
		DATE_OF_REGISTRATION_DATE : "",
		DATE_OF_REGISTRATION_TIME : "12:00 PM",
		LAST_LOGIN_DATE_DATE : 0,
		LAST_LOGIN_DATE_TIME : "12:00 PM",
		NO_OF_LOGINS : 0,
		CREATE_BY_SYS_USER_ID : 0,
		ACTIVE_LOOKUP_ID : 12,
		PARKING_AREA_ID : 0,
		LOGGED_IN_PARKING_AREA_GATE_ID : 0,
		STATUS : 0,
		PASSWORD : "",
		SALT : "",
		EXTRA_DATA : ""
	};

	//-----------------------------Form Elements------------------------------------
	var FORM_FIELD = {

		SYS_USER_ID : "#"+FORM_ID+" input[name=sys_user_id]",
		ROLE_ID : "#"+FORM_ID+" select[name=role_id]",
		NAME : "#"+FORM_ID+" input[name=name]",
		MOBILE_NUMBER : "#"+FORM_ID+" input[name=mobile_number]",
		MOBILE_NUMBER_2 : "#"+FORM_ID+" input[name=mobile_number_2]",
		EMAIL : "#"+FORM_ID+" input[name=email]",
		ADDRESS : "#"+FORM_ID+" textarea[name=address]",
		CITY_ID : "#"+FORM_ID+" select[name=city_id]",
		COUNTRY_ID : "#"+FORM_ID+" select[name=country_id]",
		DESIGNATION_LOOKUP_ID : "#"+FORM_ID+" select[name=designation_lookup_id]",
		LATITUDE : "#"+FORM_ID+" input[name=latitude]",
		LONGITUDE : "#"+FORM_ID+" input[name=longitude]",
		GENDER_LOOKUP_ID : "#"+FORM_ID+" select[name=gender_lookup_id]",
		DATE_OF_BIRTH : "#"+FORM_ID+" input[name=date_of_birth]",
		DRIVING_LICENSE_NUMBER : "#"+FORM_ID+" input[name=driving_license_number]",
		PASSPORT_NUMBER : "#"+FORM_ID+" input[name=passport_number]",
		REMARKS : "#"+FORM_ID+" textarea[name=remarks]",
		PHOTO : "#"+FORM_ID+" textarea[name=photo]",
		DATE_OF_REGISTRATION_DATE : "#"+FORM_ID+" input[name=date_of_registration_date]",
		DATE_OF_REGISTRATION_TIME : "#"+FORM_ID+" input[name=date_of_registration_time]",
		LAST_LOGIN_DATE_DATE : "#"+FORM_ID+" input[name=last_login_date_date]",
		LAST_LOGIN_DATE_TIME : "#"+FORM_ID+" input[name=last_login_date_time]",
		NO_OF_LOGINS : "#"+FORM_ID+" input[name=no_of_logins]",
		CREATE_BY_SYS_USER_ID : "#"+FORM_ID+" select[name=create_by_sys_user_id]",
		ACTIVE_LOOKUP_ID : "#"+FORM_ID+" select[name=active_lookup_id]",
		PARKING_AREA_ID : "#"+FORM_ID+" select[name=parking_area_id]",
		LOGGED_IN_PARKING_AREA_GATE_ID : "#"+FORM_ID+" select[name=logged_in_parking_area_gate_id]",
		STATUS : "#"+FORM_ID+" input[name=status]",
		PASSWORD : "#"+FORM_ID+" input[name=password]",
		SALT : "#"+FORM_ID+" input[name=salt]",
		EXTRA_DATA : "#"+FORM_ID+" textarea[name=extra_data]"
	};
	/*------------------------- Load Dependents---------------------------------------*/
	function loadDependents(option) {
		//updateCityData({callback : function(){
			if(undefined !== option)
				option.callback();
		//}});
	}

	//get SysUser data
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
	//set SysUser data
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
		$( FORM_FIELD.CITY_ID ).change(onCityChange);
		$(FORM_FIELD.PARKING_AREA_ID).change(onParkingAreaChange);

	}

	function showtheparkingareaintab(sysuserid){
		if(sysuserid > 0)
		{
		   fetchData({
			   //parking_slot_id: parking_area_id,
			   callback:  function(status, response){
				   var correctArray = response.data.values.filter(function(value, index){
					   return value[INDEX.ROLE_ID - 1] == sysuserid
				   });
   
				   response.data.values = correctArray;
   
				   createTable(response.data, 0);
				   showCurrentTabContent();
   
				   var role = RoleScript.getInstance();
				   role.populateInfo( $("#Sysuserid1"), sysuserid);	
				  // console.log(response.data,"KHIUHIJOI");	
			   }
		   })
		}
		else
		{
			if(sysuserid == 0)
			{
			   fetchData({
				   callback : function (status , response){
					   var wholearray = response.data;
					   //response.data.values = wholearray;
				   createTable(wholearray, 0);
				   showCurrentTabContent();
				   }
			   })
			}
		   //alert("fail");
		}
   }	

	//current date for registration date field. 
	function todaysdate(){
		var now = new Date();
		var day = ("0" + now.getDate()).slice(-2);
		//alert(day);
		var month = ("0" + (now.getMonth() + 1)).slice(-2);
		//alert(month);
		var today = now.getFullYear()+"-"+(month)+"-"+(day);
		//alert(today);
		$(FORM_FIELD.DATE_OF_REGISTRATION_DATE).val(today).prop('readonly', true);
		DEFAULT.SYS_USER_ID = getNewId(getStorageData(), (INDEX.SYS_USER_ID));
		$(FORM_FIELD.CREATE_BY_SYS_USER_ID).val(DEFAULT.SYS_USER_ID).attr("disabled", true);;

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

	function onParkingAreaChange(){ 
		var parkingArea = $(FORM_FIELD.PARKING_AREA_ID).val();
		ParkingAreaGateScript.getInstance().fetchData({
			format: JSON_FORMAT_KEY_VALUE_PAIR,
			parking_area_gate_id: parkingArea,
			callback: function(status, response){
				if(status && response.status_code >=0){	 
				var parkingGate = response.data.values.filter(parkingGate => (parkingGate[ParkingAreaGateScript.INDEX.PARKING_AREA_ID - 1] == parkingArea))
				//console.log(parkingGate);
				var select = document.getElementById("logged_in_parking_area_gate_id");
				var i;
				for(i = select.options.length - 1 ; i >= 0 ; i--)
				{
					select.remove(i);
				}
				for(var i = 0; i < parkingGate.length; i++){
					var opt = parkingGate[i][2];
					var val = parkingGate[i][0];
					var el = document.createElement("option");
					el.textContent = opt;
					el.value = val;
					select.appendChild(el);
				}
			}
			}
		})
	}

	function loadTabContent(){

		
		var role = RoleScript.getInstance();
		role.getData();

		var city = CityScript.getInstance();
		city.getData();

		var country = CountryScript.getInstance();
		country.getData();

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
		var role = RoleScript.getInstance();
		var city = CityScript.getInstance();
		var country = CountryScript.getInstance();
		var lookup = LookupScript.getInstance();
		var sysUser = SysUserScript.getInstance();
		//render column
		var aoColumnDefs_render = [

					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.SYS_USER_ID ] ); },
								"aTargets": [ INDEX.SYS_USER_ID ]
					},
					{
					"fnRender": function ( o, val ) {
						var roleName = getValueById( o.aData[ INDEX.ROLE_ID ], role );
						return roleName;
						},
						"aTargets": [ INDEX.ROLE_ID ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.NAME ] ); },
								"aTargets": [ INDEX.NAME ]
					},
					{
						"fnRender":	function ( o, val ) {
									return getSmsLink( o.aData[ INDEX.MOBILE_NUMBER ] ); },
								"aTargets": [ INDEX.MOBILE_NUMBER ]
					},
					{
						"fnRender":	function ( o, val ) {
									return getSmsLink( o.aData[ INDEX.MOBILE_NUMBER_2 ] ); },
								"aTargets": [ INDEX.MOBILE_NUMBER_2 ]
					},
					{
						"fnRender":	function ( o, val ) {
									return getEmailLink( o.aData[ INDEX.EMAIL ] ); },
								"aTargets": [ INDEX.EMAIL ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.ADDRESS ] ); },
								"aTargets": [ INDEX.ADDRESS ]
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
						var countryName = getValueById( o.aData[ INDEX.COUNTRY_ID ], country );
						return countryName;
						},
						"aTargets": [ INDEX.COUNTRY_ID ]
					},
					{
					"fnRender": function ( o, val ) {
						var lookupName = getValueById( o.aData[ INDEX.DESIGNATION_LOOKUP_ID ], lookup );
						return lookupName;
						},
						"aTargets": [ INDEX.DESIGNATION_LOOKUP_ID ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.LATITUDE ] ); },
								"aTargets": [ INDEX.LATITUDE ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.LONGITUDE ] ); },
								"aTargets": [ INDEX.LONGITUDE ]
					},
					{
					"fnRender": function ( o, val ) {
						var lookupName = getValueById( o.aData[ INDEX.GENDER_LOOKUP_ID ], lookup );
						return lookupName;
						},
						"aTargets": [ INDEX.GENDER_LOOKUP_ID ]
					},
					{
						"fnRender":	function ( o, val ) {
									return timeToDisplayDate( o.aData[ INDEX.DATE_OF_BIRTH ] ); },
								"aTargets": [ INDEX.DATE_OF_BIRTH ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.DRIVING_LICENSE_NUMBER ] ); },
								"aTargets": [ INDEX.DRIVING_LICENSE_NUMBER ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.PASSPORT_NUMBER ] ); },
								"aTargets": [ INDEX.PASSPORT_NUMBER ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.REMARKS ] ); },
								"aTargets": [ INDEX.REMARKS ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.PHOTO ] ); },
								"aTargets": [ INDEX.PHOTO ]
					},
					{
						"fnRender":	function ( o, val ) {
									return timeToDisplayDate( o.aData[ INDEX.DATE_OF_REGISTRATION ] ); },
								"aTargets": [ INDEX.DATE_OF_REGISTRATION ]
					},
					{
						"fnRender":	function ( o, val ) {
									return timeToDisplayDate( o.aData[ INDEX.LAST_LOGIN_DATE ] ); },
								"aTargets": [ INDEX.LAST_LOGIN_DATE ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.NO_OF_LOGINS ] ); },
								"aTargets": [ INDEX.NO_OF_LOGINS ]
					},
					{
					"fnRender": function ( o, val ) {
						var sysUserName = getValueById( o.aData[ INDEX.CREATE_BY_SYS_USER_ID ], sysUser );
						return sysUserName;
						},
						"aTargets": [ INDEX.CREATE_BY_SYS_USER_ID ]
					},
					{
					"fnRender": function ( o, val ) {
						var lookupName = getValueById( o.aData[ INDEX.ACTIVE_LOOKUP_ID ], lookup );
						return lookupName;
						},
						"aTargets": [ INDEX.ACTIVE_LOOKUP_ID ]
					},
					// {
					// "fnRender": function ( o, val ) {
					// 	var parkingAreaName = getValueById( o.aData[ INDEX.PARKING_AREA_ID ], parkingArea );
					// 	return parkingAreaName;
					// 	},
					// 	"aTargets": [ INDEX.PARKING_AREA_ID ]
					// },
					// {
					// "fnRender": function ( o, val ) {
					// 	var parkingAreaName = getValueById( o.aData[ INDEX.LOGGED_IN_PARKING_AREA_GATE_ID ], parkingArea );
					// 	return parkingAreaName;
					// 	},
					// 	"aTargets": [ INDEX.LOGGED_IN_PARKING_AREA_GATE_ID ]
					// },
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.STATUS ] ); },
								"aTargets": [ INDEX.STATUS ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.PASSWORD ] ); },
								"aTargets": [ INDEX.PASSWORD ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.SALT ] ); },
								"aTargets": [ INDEX.SALT ]
					},
					{
						"fnRender":	function ( o, val ) {
									return myFunction( o.aData[ INDEX.EXTRA_DATA ] ); },
								"aTargets": [ INDEX.EXTRA_DATA ]
					}
					];
		//Define Column Headings
		var aoColumns_headings = [

						{	"sTitle": '<input type="checkbox" name="select_all_rows" title="Select All/None">', "sClass": "center", "bSortable": false, "sWidth": "5%"  }

						,{	"sTitle": json.headings[INDEX.SYS_USER_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.ROLE_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.NAME-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.MOBILE_NUMBER-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.MOBILE_NUMBER_2-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.EMAIL-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.ADDRESS-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.CITY_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.COUNTRY_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.DESIGNATION_LOOKUP_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.LATITUDE-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.LONGITUDE-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.GENDER_LOOKUP_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.DATE_OF_BIRTH-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.DRIVING_LICENSE_NUMBER-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.PASSPORT_NUMBER-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.REMARKS-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.PHOTO-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.DATE_OF_REGISTRATION-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.LAST_LOGIN_DATE-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.NO_OF_LOGINS-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.CREATE_BY_SYS_USER_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.ACTIVE_LOOKUP_ID-1] , "sClass": "center", "sType": "string", "bSearchable": true, "bVisible": true }
						,{	"sTitle": json.headings[INDEX.PARKING_AREA_ID-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.LOGGED_IN_PARKING_AREA_GATE_ID-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.STATUS-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.PASSWORD-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.SALT-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
						,{	"sTitle": json.headings[INDEX.EXTRA_DATA-1] , "sClass": "center", "sType": "string", "bSearchable": false, "bVisible": false }
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
							"aoColumnDefs": aoColumnDefs_render,
							"sDom": '<"H"l<"toolbar_passenger_details dataTables_filter">fr>t<"F"ip>'
						});

			var toolBar = $("#"+table_id+"_wrapper")
							.find('.toolbar_passenger_details')
								.css({'right': '242px', 'position': 'absolute'});
						
			toolBar.append('<label> Sys User Role List </label>'
					+'<select id="Sysuserid1" name="Sysuserid1">'
						+'<option value="0">Show All</option>'
					+'</select>');

		var role = RoleScript.getInstance();
		role.populateInfo( $("#Sysuserid1"), DEFAULT.ROLE_ID );
		
		toolBar.find('select[name=Sysuserid1]').on('change', function(){
			//var route = $(this).val();
			var sysuserid = $(this).val();
			//alert("changed" + sysuserid);
			DEFAULT_ORGANIZATION.current_parking_area_id = sysuserid;
			showtheparkingareaintab(sysuserid);
			//DEFAULT_ORGANIZATION.current_route_id = route;
			//showTabContent(PASSENGER_DETAILS_REL);
			
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.EDIT_SYS_USER) == true ){ //edit enabled : permissions
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.ADD_SYS_USER) == true ){
				$(add_button_id).on('click',function(){
					popUpAddForm();
					todaysdate();
				});
			}
			else{
				$(add_button_id).button({disabled: true});
			}

		//Edit button
		var edit_button_id = "#"+button_panel_id+' button.edit_button';
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.EDIT_SYS_USER) == true ){
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
			if( checkRolePermission(userRole, SOFTWARE_FEATURE_CONST.DELETE_SYS_USER) == true ){
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

							var table_name = "Sys User";

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
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SYS_USER_ID), G_ERROR.MSG.empty_error+LABEL.SYS_USER_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.ROLE_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.ROLE_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.NAME), G_ERROR.MSG.empty_error+LABEL.NAME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.MOBILE_NUMBER), G_ERROR.MSG.empty_error+LABEL.MOBILE_NUMBER);
		bValid = bValid && fv.validateMobileNo( $(FORM_FIELD.MOBILE_NUMBER), country_code, 0 , LABEL.MOBILE_NUMBER+G_ERROR.MSG.invalid_mobile_no);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.MOBILE_NUMBER_2), G_ERROR.MSG.empty_error+LABEL.MOBILE_NUMBER_2);
		bValid = bValid && fv.validateMobileNo( $(FORM_FIELD.MOBILE_NUMBER_2), country_code, 0 , LABEL.MOBILE_NUMBER_2+G_ERROR.MSG.invalid_mobile_no);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.EMAIL), G_ERROR.MSG.empty_error+LABEL.EMAIL);
		bValid = bValid && fv.checkEmail($(FORM_FIELD.EMAIL), LABEL.EMAIL+G_ERROR.MSG.invalid_emailid_error);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.ADDRESS), G_ERROR.MSG.empty_error+LABEL.ADDRESS);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.CITY_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.CITY_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.COUNTRY_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.COUNTRY_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.DESIGNATION_LOOKUP_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.DESIGNATION_LOOKUP_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.LATITUDE), G_ERROR.MSG.empty_error+LABEL.LATITUDE);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.LONGITUDE), G_ERROR.MSG.empty_error+LABEL.LONGITUDE);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.GENDER_LOOKUP_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.GENDER_LOOKUP_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.DATE_OF_BIRTH), G_ERROR.MSG.empty_error+LABEL.DATE_OF_BIRTH);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.DRIVING_LICENSE_NUMBER), G_ERROR.MSG.empty_error+LABEL.DRIVING_LICENSE_NUMBER);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.PASSPORT_NUMBER), G_ERROR.MSG.empty_error+LABEL.PASSPORT_NUMBER);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.REMARKS), G_ERROR.MSG.empty_error+LABEL.REMARKS);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.PHOTO), G_ERROR.MSG.empty_error+LABEL.PHOTO);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.DATE_OF_REGISTRATION_DATE), G_ERROR.MSG.empty_error+LABEL.DATE_OF_REGISTRATION_DATE);
		bValid = bValid && fv.checkDate($(FORM_FIELD.DATE_OF_REGISTRATION_DATE), G_ERROR.MSG.invalid_date_error+LABEL.DATE_OF_REGISTRATION_DATE);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.DATE_OF_REGISTRATION_TIME), G_ERROR.MSG.empty_error+LABEL.DATE_OF_REGISTRATION_TIME);
		bValid = bValid && fv.checkTime($(FORM_FIELD.DATE_OF_REGISTRATION_TIME), G_ERROR.MSG.invalid_time_error+LABEL.DATE_OF_REGISTRATION_TIME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.LAST_LOGIN_DATE_DATE), G_ERROR.MSG.empty_error+LABEL.LAST_LOGIN_DATE_DATE);
		bValid = bValid && fv.checkDate($(FORM_FIELD.LAST_LOGIN_DATE_DATE), G_ERROR.MSG.invalid_date_error+LABEL.LAST_LOGIN_DATE_DATE);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.LAST_LOGIN_DATE_TIME), G_ERROR.MSG.empty_error+LABEL.LAST_LOGIN_DATE_TIME);
		bValid = bValid && fv.checkTime($(FORM_FIELD.LAST_LOGIN_DATE_TIME), G_ERROR.MSG.invalid_time_error+LABEL.LAST_LOGIN_DATE_TIME);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.NO_OF_LOGINS), G_ERROR.MSG.empty_error+LABEL.NO_OF_LOGINS);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.CREATE_BY_SYS_USER_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.CREATE_BY_SYS_USER_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.ACTIVE_LOOKUP_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.ACTIVE_LOOKUP_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.PARKING_AREA_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.PARKING_AREA_ID);
		bValid = bValid && fv.checkEmptySelect($(FORM_FIELD.LOGGED_IN_PARKING_AREA_GATE_ID), G_ERROR.MSG.empty_error_selectbox+LABEL.LOGGED_IN_PARKING_AREA_GATE_ID);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.STATUS), G_ERROR.MSG.empty_error+LABEL.STATUS);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.PASSWORD), G_ERROR.MSG.empty_error+LABEL.PASSWORD);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.SALT), G_ERROR.MSG.empty_error+LABEL.SALT);
		bValid = bValid && fv.checkEmpty($(FORM_FIELD.EXTRA_DATA), G_ERROR.MSG.empty_error+LABEL.EXTRA_DATA);
*/
		return bValid;
	}
	function setFormDefaults() {
		DEFAULT.SYS_USER_ID = getNewId(getStorageData(), (INDEX.SYS_USER_ID));
		$(FORM_FIELD.SYS_USER_ID).val(DEFAULT.SYS_USER_ID);
		var role = RoleScript.getInstance();
		role.populateInfo( $(FORM_FIELD.ROLE_ID), DEFAULT.ROLE_ID );
		$(FORM_FIELD.NAME).val(DEFAULT.NAME);
		$(FORM_FIELD.MOBILE_NUMBER).val(DEFAULT.MOBILE_NUMBER);
		$(FORM_FIELD.MOBILE_NUMBER_2).val(DEFAULT.MOBILE_NUMBER_2);
		$(FORM_FIELD.EMAIL).val(DEFAULT.EMAIL);
		$(FORM_FIELD.ADDRESS).val(DEFAULT.ADDRESS);
		var city = CityScript.getInstance();
		city.populateInfo( $(FORM_FIELD.CITY_ID), DEFAULT.CITY_ID );
		var country = CountryScript.getInstance();
		country.populateInfo( $(FORM_FIELD.COUNTRY_ID), DEFAULT.COUNTRY_ID );
		var lookup = LookupScript.getInstance();
		lookup.populateInfo( $(FORM_FIELD.DESIGNATION_LOOKUP_ID), DEFAULT.DESIGNATION_LOOKUP_ID );
		$(FORM_FIELD.LATITUDE).val(DEFAULT.LATITUDE);
		$(FORM_FIELD.LONGITUDE).val(DEFAULT.LONGITUDE);
		lookup.populateInfo( $(FORM_FIELD.GENDER_LOOKUP_ID),DEFAULT.GENDER_LOOKUP_ID );
		$(FORM_FIELD.DATE_OF_BIRTH).val(DEFAULT.DATE_OF_BIRTH);
		$(FORM_FIELD.DRIVING_LICENSE_NUMBER).val(DEFAULT.DRIVING_LICENSE_NUMBER);
		$(FORM_FIELD.PASSPORT_NUMBER).val(DEFAULT.PASSPORT_NUMBER);
		$(FORM_FIELD.REMARKS).val(DEFAULT.REMARKS);
		$(FORM_FIELD.PHOTO).val(DEFAULT.PHOTO);
		$(FORM_FIELD.DATE_OF_REGISTRATION_DATE).val(DEFAULT.DATE_OF_REGISTRATION_DATE);
		$(FORM_FIELD.DATE_OF_REGISTRATION_TIME).val(DEFAULT.DATE_OF_REGISTRATION_TIME);
		$(FORM_FIELD.LAST_LOGIN_DATE_DATE).val(DEFAULT.LAST_LOGIN_DATE_DATE);
		$(FORM_FIELD.LAST_LOGIN_DATE_TIME).val(DEFAULT.LAST_LOGIN_DATE_TIME);
		$(FORM_FIELD.NO_OF_LOGINS).val(DEFAULT.NO_OF_LOGINS);
		var sysUser = SysUserScript.getInstance();
		sysUser.populateInfo( $(FORM_FIELD.CREATE_BY_SYS_USER_ID), DEFAULT.CREATE_BY_SYS_USER_ID );
		lookup.populateInfo( $(FORM_FIELD.ACTIVE_LOOKUP_ID), DEFAULT.ACTIVE_LOOKUP_ID );
		//var parkingArea = ParkingAreaScript.getInstance();
		//parkingArea.populateInfo( $(FORM_FIELD.PARKING_AREA_ID), DEFAULT.PARKING_AREA_ID );
		//parkingArea.populateInfo( $(FORM_FIELD.LOGGED_IN_PARKING_AREA_GATE_ID), DEFAULT.LOGGED_IN_PARKING_AREA_GATE_ID );
		$(FORM_FIELD.STATUS).val(DEFAULT.STATUS);
		$(FORM_FIELD.PASSWORD).val(DEFAULT.PASSWORD);
		$(FORM_FIELD.SALT).val(DEFAULT.SALT);
		$(FORM_FIELD.EXTRA_DATA).val(DEFAULT.EXTRA_DATA);
	}
	function popUpAddForm(field) {

		if(field){

			formField = field;

			isChildForm = true;

		}

		setFormDefaults();

		displayForm("Add New Sys User", INSERT_DATA, 0);
	}

	function popUpEditForm(data, row_index) {

		$(FORM_FIELD.SYS_USER_ID).val(data[INDEX.SYS_USER_ID-1]);
		var role = RoleScript.getInstance();
		role.populateInfo( $(FORM_FIELD.ROLE_ID), data[INDEX.ROLE_ID-1] );
		$(FORM_FIELD.NAME).val(data[INDEX.NAME-1]);
		$(FORM_FIELD.MOBILE_NUMBER).val(data[INDEX.MOBILE_NUMBER-1]);
		$(FORM_FIELD.MOBILE_NUMBER_2).val(data[INDEX.MOBILE_NUMBER_2-1]);
		$(FORM_FIELD.EMAIL).val(data[INDEX.EMAIL-1]);
		$(FORM_FIELD.ADDRESS).val(data[INDEX.ADDRESS-1]);
		var city = CityScript.getInstance();
		city.populateInfo( $(FORM_FIELD.CITY_ID), data[INDEX.CITY_ID-1] );
		var country = CountryScript.getInstance();
		country.populateInfo( $(FORM_FIELD.COUNTRY_ID), data[INDEX.COUNTRY_ID-1] );
		var lookup = LookupScript.getInstance();
		lookup.populateInfo( $(FORM_FIELD.DESIGNATION_LOOKUP_ID), DEFAULT.DESIGNATION_LOOKUP_ID, data[INDEX.DESIGNATION_LOOKUP_ID-1] );
		$(FORM_FIELD.LATITUDE).val(data[INDEX.LATITUDE-1]);
		$(FORM_FIELD.LONGITUDE).val(data[INDEX.LONGITUDE-1]);
		lookup.populateInfo( $(FORM_FIELD.GENDER_LOOKUP_ID), DEFAULT.GENDER_LOOKUP_ID, data[INDEX.GENDER_LOOKUP_ID-1] );
		$(FORM_FIELD.DATE_OF_BIRTH).val(timeToDisplayDate(data[INDEX.DATE_OF_BIRTH-1]));
		$(FORM_FIELD.DRIVING_LICENSE_NUMBER).val(data[INDEX.DRIVING_LICENSE_NUMBER-1]);
		$(FORM_FIELD.PASSPORT_NUMBER).val(data[INDEX.PASSPORT_NUMBER-1]);
		$(FORM_FIELD.REMARKS).val(data[INDEX.REMARKS-1]);
		$(FORM_FIELD.PHOTO).val(data[INDEX.PHOTO-1]);
		$(FORM_FIELD.DATE_OF_REGISTRATION_DATE).val(timeToDisplayDate(data[INDEX.DATE_OF_REGISTRATION-1]));
		$(FORM_FIELD.DATE_OF_REGISTRATION_TIME).val(timeToDisplayTime(data[INDEX.DATE_OF_REGISTRATION-1]));
		$(FORM_FIELD.LAST_LOGIN_DATE_DATE).val(timeToDisplayDate(data[INDEX.LAST_LOGIN_DATE-1]));
		$(FORM_FIELD.LAST_LOGIN_DATE_TIME).val(timeToDisplayTime(data[INDEX.LAST_LOGIN_DATE-1]));
		$(FORM_FIELD.NO_OF_LOGINS).val(data[INDEX.NO_OF_LOGINS-1]);
		var sysUser = SysUserScript.getInstance();
		sysUser.populateInfo( $(FORM_FIELD.CREATE_BY_SYS_USER_ID), data[INDEX.CREATE_BY_SYS_USER_ID-1] );
		lookup.populateInfo( $(FORM_FIELD.ACTIVE_LOOKUP_ID), DEFAULT.ACTIVE_LOOKUP_ID, data[INDEX.ACTIVE_LOOKUP_ID-1] );
		//var parkingArea = ParkingAreaScript.getInstance();
		//parkingArea.populateInfo( $(FORM_FIELD.PARKING_AREA_ID), data[INDEX.PARKING_AREA_ID-1] );
		//var parkingAreaGate = ParkingAreaGateScript.getInstance();
		//parkingAreaGate.populateInfo( $(FORM_FIELD.LOGGED_IN_PARKING_AREA_GATE_ID), data[INDEX.PARKING_AREA_GATE_ID-1] );
		$(FORM_FIELD.STATUS).val(data[INDEX.STATUS-1]);
		$(FORM_FIELD.PASSWORD).val(data[INDEX.PASSWORD-1]);
		$(FORM_FIELD.SALT).val(data[INDEX.SALT-1]);
		$(FORM_FIELD.EXTRA_DATA).val(data[INDEX.EXTRA_DATA-1]);
		displayForm("Edit Sys User", UPDATE_DATA, row_index);
	}

	function getFormDataAsJson(){
		var jsonData = {
			sys_user_id : ($(FORM_FIELD.SYS_USER_ID).val()),
			role_id : ($(FORM_FIELD.ROLE_ID).val()),
			name : ($(FORM_FIELD.NAME).val()),
			mobile_number : ($(FORM_FIELD.MOBILE_NUMBER).val()),
			mobile_number_2 : ($(FORM_FIELD.MOBILE_NUMBER_2).val()),
			email : ($(FORM_FIELD.EMAIL).val()),
			address : ($(FORM_FIELD.ADDRESS).val()),
			city_id : ($(FORM_FIELD.CITY_ID).val()),
			country_id : ($(FORM_FIELD.COUNTRY_ID).val()),
			designation_lookup_id : ($(FORM_FIELD.DESIGNATION_LOOKUP_ID).val()),
			latitude : parseFloat(0.0),//$(FORM_FIELD.LATITUDE).val()),
			longitude :  parseFloat(0.0),//$(FORM_FIELD.LONGITUDE).val()),
			gender_lookup_id : ($(FORM_FIELD.GENDER_LOOKUP_ID).val()),
			date_of_birth : DateToSaveTime($(FORM_FIELD.DATE_OF_BIRTH).val()),
			driving_license_number : ($(FORM_FIELD.DRIVING_LICENSE_NUMBER).val()),
			passport_number : ($(FORM_FIELD.PASSPORT_NUMBER).val()),
			remarks : ($(FORM_FIELD.REMARKS).val()),
			photo : (" "),//($(FORM_FIELD.PHOTO).val()),
			date_of_registration : DateTimeToSaveTime($(FORM_FIELD.DATE_OF_REGISTRATION_DATE).val(), $(FORM_FIELD.DATE_OF_REGISTRATION_TIME).val()),
			last_login_date : DateTimeToSaveTime($(FORM_FIELD.DATE_OF_REGISTRATION_DATE).val(),$(FORM_FIELD.LAST_LOGIN_DATE_TIME).val()),
			no_of_logins : DEFAULT.NO_OF_LOGINS,//($(FORM_FIELD.NO_OF_LOGINS).val(DEFAULT.NO_OF_LOGINS)),
			create_by_sys_user_id : ($(FORM_FIELD.CREATE_BY_SYS_USER_ID).val()),
			active_lookup_id : ($(FORM_FIELD.ACTIVE_LOOKUP_ID).val()),
			parking_area_id : ($(FORM_FIELD.PARKING_AREA_ID).val()),
			logged_in_parking_area_gate_id : ($(FORM_FIELD.LOGGED_IN_PARKING_AREA_GATE_ID).val()),
			status : ($(FORM_FIELD.STATUS).val()),
			password : ($(FORM_FIELD.PASSWORD).val()),
			salt : ($(FORM_FIELD.SALT).val()),
			extra_data : ($(FORM_FIELD.EXTRA_DATA).val())
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
				dialogMessage="Sys User has been successfully added ("/*+jsonData.name*/ +" Id:"+response.data.id+")";
			else if( status && response.data.status_code >= 0 && mode==UPDATE_DATA)
				dialogMessage="Sys User has been successfully updated("/*+jsonData.name*/+" Id:"+response.data.id+")";
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
		var key_column_index = [ INDEX.SYS_USER_ID ];
		row_id_arr = getArrayFromArray({ index : key_column_index, data_arr : row_data_arr});

		var jsonData = {
				del_row_id_arr : row_id_arr
//			,sys_user_id : getSysUserId()	// ADD ANY OTHER CONDITION (IF ANY)
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

					dialogMessage="Sys User has been successfully deleted :"+response.data.rows_deleted+" row(s)";


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
					dialogMessage = "Cannot delete Sys User. There are some dependencies for this Sys User.";
				else
					dialogMessage = "Cannot delete Sys User. There are some dependencies for this Sys User.";

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
		//var _url = ROOT_URL + URL +"/select?sys_user_id="+ getCurLogUserId();
		//var zero =0;
		var _url = ROOT_URL + URL +"/select?sys_user_id=0";
		
		if(option && option.sys_user_id){
			$.each(option.sys_user_id, function(inx, val){
				_url += '&sys_user_id='+val;
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
		var  id_index = INDEX.SYS_USER_ID, name_index = getNameIdx();
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
						console.log('SYS_USER_DATA is empty : selected_id=' +select_id );
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
			SysUserSendSms();
			break;
		case 2:
			SysUserSendEmail();
			break;	
		default:
			log('Sys User Action', 'unknown parameter');
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
		return INDEX.SYS_USER_ID;
	}

	function getNameIdx() {
		console.log('Please Change the Name Idx');
		return INDEX.NAME;
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
