/*
 * Name: RA_SETTING.JS
 * DESCRIPTION: define all global constants and variables here.
 * 
 * */
 
//SET ORGANIZATION ID ON LOGIN.
	var ORGANIZATION_ID = 0;

//MODE : database operatios
	var INSERT_DATA=1,
		READ_DATA=2,
		UPDATE_DATA=3,
	    DELETE_DATA=4;

//url for db data handlers.	//handler operations
	var URL_INSERT="/insert"
	,	URL_UPDATE="/update"
	,	URL_DELETE="/delete"
	,	URL_SELECT="/select"
	,	URL_REGRESSION="/regression"
	,	URL_GET_DATA = "/getRecord"
	,	URL_QUERY_DATA = "/queryData"
	,	URL_GET_REPORT = "/getReport"
	,	URL_THEADER="/theaders"
	,   URL_COUNT="/count"
	,	URL_MULTIINSERT = "/multiinsert"
	,	URL_MULTIUPDATE = "/multiupdate";
	
//root Resource URL
	var ROOT_URL="rest";
	
	
//Mobile No. Country code
	
	var MOBILE_NO_COUNTRY_CODE = "+91";
	var DEFAULT_MOBILE_NO_LENGTH = 10;
	
//Regular expressions to be used
	var REG_REPLACE_CPMMA = /\~/g;	//reg for comma replace
	var REG_REPLACE_QUOTE = /\"\"/g;	
	var REPLACE_CPMMA_STR = '~';
	var REPLACE_QUOTE_STR = '""';

//Json Format values	
	var JSON_FORMAT_NORMAL = 0; 			// select returns normal JSON object
	var JSON_FORMAT_KEY_VALUE_PAIR = 1; 	// select returns key value pair JSON object ( to be used in detail forms )

