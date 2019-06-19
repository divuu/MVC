
//---------------------Error messages--------------------------
	/**
	 * Global Error Object
	 */
	var G_ERROR = {
			
			MSG : {
				empty_error : " Please Enter ",
				empty_error_selectbox : " Please Select a ",
				nan_error : " should be a Number!",
				negative_num_error : " should be a Positive number!",
				length_error : " invalid length!",
				LT_zero_error : " should be a non zero number",
				invalid_name_error : " Invalid name. Use Only Alphabets or Alphabets followed by Numerics.",
				invalid_time_error : " : Invalid Time. Enter Time in HH:MM AM/PM format.(eg. 08:30 PM)",
				invalid_date_error : " : Invalid Date. Enter Date in DD/MM/YYYY format. (eg. 23/10/2012)",
				invalid_startenddate_error : " : Start Date should be an earlier date than End Date.",
				invalid_emailid_error : " : Please enter a valid email address.",
				invalid_url_error : " : Invalid Website Address(URL).",
				invalid_alphaneumeric_error : " : Invalid Data. It can contain only numbers and alphabets",	    
				invalid_mobile_no  :  " Please Enter a valid Mobile Number.",
				invalid_data: " has ivalid values"
			},
			CODE : {
				SUCCESS  :  0,
				NO_DATA_RECEIVED  :  -101,
				OPERATION_FAILED  :  -102,
				NONE_OF_THE_ROWS_AFFECTED  :  -103,
				EMPTYLIST  :  -104,
				NULL_POINTER  :  -105,
				DELETE_OPERATION_FAILED  :  -106,
				DELETE_OPERATION_DEPENDENT_EXISTS  :  -107,
				DELETE_OPERATION_NO_RECORD_EXISTS  :  -108,
				INVALID_DATA  :  -109,
				ACCESS_DENIED  :  -110,
				NETWORK_FAILURE : -10
			},
			/**
			 * @param ERROR_CODE{int} : error code 
			 * @return{String}  : error string for the given code
			 * */
			getMessage : function(ERROR_CODE){
				
				switch (ERROR_CODE)
				{
				case 0:
					return "Operation Successful!";
					break;
				case -101:
					return "Data sent to Server is incomplete!";
					break;
				case -102:
					return "Operation Failed!";
					break;
				case -103:
					return "Zero Rows Affected!";
					break;
				case -104:
					return "List is Empty!";
					break;
				case -105:
					return "No Data!";
					break;
				case -106:
					return "Failed to Delete!";
					break;
				case -107:
					return "Dependents exists."; //dependents
					break;
				case -108:
					return "No record found."; //dependents
					break;
				case -109:
					return "Invalid data sent."; //data sent are invalid
					break;
				case -110:
					return "Access Denied."; //data sent are invalid
					break;
				default:
					return "Error "+ERROR_CODE+" is Not Listed.!";
				}
			}
	};	
		
		