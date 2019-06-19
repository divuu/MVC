/**
 * 
 */
package com.technomedia.digipark.server.common;

/**
 * @author joydeep
 * 
 */
public class ErrorCodes {

	static byte value = 0;

	public static final int ERROR_CODE_SUCCESS = 0;
	public static final int ERROR_CODE_NO_DATA_RECEIVED = -101;
	public static final int ERROR_CODE_OPERATION_FAILED = -102;
	public static final int ERROR_CODE_NONE_OF_THE_ROWS_AFFECTED = -103;
	public static final int ERROR_CODE_EMPTYLIST = -104;
	public static final int ERROR_CODE_NULL_POINTER = -105;
	public static final int ERROR_CODE_DELETE_OPERATION_FAILED = -106;
	public static final int ERROR_CODE_DELETE_OPERATION_DEPENDENT_EXISTS = -107;
	public static final int ERROR_CODE_DELETE_OPERATION_NO_RECORD_EXISTS = -108;
	public static final int ERROR_CODE_INVALIDDATA = -109;
	public static final int ERROR_CODE_ACCESS_DENIED = -110;
	public static final int ERROR_CODE_INSERT_FAILED_ID_EXISTS = -111;

	public static final byte CODE_SUCCESS = value; // Value of SUCCESS is '0'
	public static final byte CODE_SUCCESS_BUT_TRY_ANOTHER = ++value;

	// Following are Bit fields and represent Bit positions
	public static final byte CODE_INVALID_PARAMETER = ++value;
	public static final byte CODE_NO_RECORDS_FOUND = ++value;
	public static final byte CODE_SOME_ORPHAN_PICKUPDROP_RECORDS_FOUND = ++value;
	public static final byte CODE_MULTIPLE_PM_RECORDS_FOUND = ++value;
	public static final byte CODE_CATEGORY_NOT_AVAILABLE = ++value;
	public static final byte CODE_ROUTESTOP_NOT_AVAILABLE = ++value;
	public static final byte CODE_ROUTE_STOP_NOT_SET = ++value; // Route stop
																// hasnt been
																// setup for
																// some
																// passengers
	public static final byte CODE_ROUTE_NOT_AVAILABLE = ++value;
	public static final byte CODE_ROUTE_DETAILS_NOT_AVAILABLE = ++value;
	public static final byte CODE_MAPDATA_NOT_AVAILABLE = ++value;
	public static final byte CODE_MEMORY_ALLOC_FAILED = ++value;
	public static final byte CODE_ROUTE_MAP_NOT_AVAILABLE = ++value;
	public static final byte CODE_WAITING_FOR_NEXT_FAILURE = ++value;
	public static final byte CODE_NULL_POINTER = ++value;
	public static final byte CODE_MOVING_IN_REVERSE_DIRECTION = ++value;
	public static final byte CODE_WAITING_FOR_NEXT_ITERATION = ++value;
	public static final byte CODE_NO_MORE_MAP_POINTS = ++value;
	public static final byte CODE_GENERAL_ERROR = ++value;

	public static final byte CODE_DEVIATED_FROM_ROUTE = ++value;
	public static final byte CODE_DEVIATING_FROM_ROUTE = ++value;
	public static final byte CODE_UNUSUAL_DISPLACEMENT = ++value;
	public static final byte CODE_NO_DISPLACEMENT = ++value;

	public static final byte CODE_FILE_INIT_ERROR = ++value;
	public static final byte CODE_ISSUED_ALERT = ++value;
	public static final byte CODE_NO_ALERTS_FOUND = ++value;
	public static final byte CODE_CANNOT_ALERT_NOW = ++value;
	public static final byte CODE_ISSUED_SMS_ALERT = ++value;
	public static final byte CODE_DEVICE_NOT_REGISTERED = ++value;
	public static final byte CODE_NOT_CHECKING_ALERTS_NOW = ++value;

	public static final byte CODE_SIM_CARD_REGISTRATION_FAILED = ++value;

	// //////////29 Bits have been used so far////////////////////////

	public static int setErrorCode(byte code) {

		return (1 << code);
	}

	public static boolean checkErrorCode(int errorCode, byte code) {

		return (errorCode == (1 << code));
	}

	// Values
	public static final int ERROR_INVALID_FILENAME = -1;
	public static final int ERROR_FILE_NOTFOUND = -2;

	public static final int SQL_EXECUTOR_SOME_QUERIES_FAILED = -3;
	public static final int SQL_EXECUTOR_ALL_QUERIES_FAILED = -4;
	public static final int SQL_EXECUTOR_INPUT_OUTPUT_ERROR = -5;

	public static final int SQL_EXECUTOR_NULL_POINTER = -999;
	public static final int SQL_EXECUTOR_FILENAME_INVALID = -1;
	public static final int SQL_EXECUTOR_FILE_NOTFOUND = -4;
	public static final int SQL_EXECUTOR_SUCCESS = 0;
	public static final int SQL_EXECUTOR_DB_CREATION_FAILED = -6;

	public static final int ERROR_CODE_FILE_NOT_FOUND = -7;

	/*
	 * public static final int SQL_EXECUTOR_SOME_QUERIES_FAILED = -2; public
	 * static final int SQL_EXECUTOR_ALL_QUERIES_FAILED = -3; public static
	 * final int SQL_EXECUTOR_INPUT_OUTPUT_ERROR = -5;
	 */
}