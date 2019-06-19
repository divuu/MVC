package com.technomedia.digipark.server.common;

public class SoftwareFeature { // SoftwareFeature

	// List 1 - 1 to 64
	// List all features in the application

//	public static final byte READ_TASK = 0;
//	public static final byte READ_ASSIGNED_ALARM = 0;
//	public static final byte ADD_ALARM = 0;
//	public static final byte EDIT_ALARM = 0;
//	public static final byte DELETE_ALARM = 0;
//	public static final byte READ_CATEGORY = 0;
//	public static final byte READ_ASSIGNED_CATEGORY = 0;
//	public static final byte ADD_CATEGORY = 0;
//	public static final byte EDIT_CATEGORY = 0;
//	public static final byte DELETE_CATEGORY = 0;
//	public static final byte READ_ROLE_LEVEL_FEATURES = 0;
//	public static final byte READ_ASSIGNED_ROLE_LEVEL_FEATURES = 0;
//	public static final byte ADD_ROLE_LEVEL_FEATURES = 0;
//	public static final byte EDIT_ROLE_LEVEL_FEATURES = 0;
//	public static final byte DELETE_ROLE_LEVEL_FEATURES = 0;
//	public static final byte READ_SECTION = 0;
//	public static final byte READ_ASSIGNED_SECTION = 0;
//	public static final byte ADD_SECTION = 0;
//	public static final byte EDIT_SECTION = 0;
//	public static final byte DELETE_SECTION = 0;
//	public static final byte READ_VALUE_LOOKUP_TABLE = 0;
//	public static final byte READ_ASSIGNED_VALUE_LOOKUP_TABLE = 0;
//	public static final byte ADD_VALUE_LOOKUP_TABLE = 0;
//	public static final byte EDIT_VALUE_LOOKUP_TABLE = 0;
//	public static final byte DELETE_VALUE_LOOKUP_TABLE = 0;
//	public static final byte READ_ALARM = 0;
//	// Group 1 - 1- 32
//	
//	public static final byte READ_ALL_ORGANIZATION = 1;      
//
//	public static final byte ADD_COMPANY = 3;
//	public static final byte EDIT_COMPANY = 2;
//	public static final byte READ_ASSIGNED_COMPANY = 1;
//	public static final byte READ_COMPANY = 1;
//	public static final byte DELETE_COMPANY = 4;
//
//
//
//	public static final byte ADD_USER_LOGIN = 3;
//	public static final byte EDIT_USER_LOGIN = 2;
//	public static final byte READ_ASSIGNED_USER_LOGIN = 1;
//	public static final byte READ_USER_LOGIN = 1;
//	public static final byte DELETE_USER_LOGIN = 4;
//
/////////////////////////////////////////////////////////////////////////////////////////////////	
//	public static final byte READ_ASSIGNED_BANK_ACCOUNT = 1;
//	public static final byte READ_BANK_ACCOUNT = 1;
//	public static final byte EDIT_BANK_ACCOUNT = 2;
//	public static final byte ADD_BANK_ACCOUNT = 3;
//	public static final byte DELETE_BANK_ACCOUNT = 4;
//	
//	
//	public static final byte READ_ASSIGNED_COMMERCIAL_INVOICE = 1;
//	public static final byte READ_COMMERCIAL_INVOICE = 1;
//	public static final byte EDIT_COMMERCIAL_INVOICE = 2;
//	public static final byte ADD_COMMERCIAL_INVOICE = 3;
//	public static final byte DELETE_COMMERCIAL_INVOICE = 4;
//
//
//	public static final byte READ_ASSIGNED_GODOWN = 1;
//	public static final byte READ_GODOWN = 1;
//	public static final byte EDIT_GODOWN = 2;
//	public static final byte ADD_GODOWN = 3;
//	public static final byte DELETE_GODOWN = 4;
//
//
//	public static final byte READ_ASSIGNED_GRN = 1;
//	public static final byte READ_GRN = 1;
//	public static final byte EDIT_GRN = 2;
//	public static final byte ADD_GRN = 3;
//	public static final byte DELETE_GRN = 4;
//
//
//	public static final byte READ_ASSIGNED_HSN_CODE = 1;
//	public static final byte READ_HSN_CODE = 1;
//	public static final byte EDIT_HSN_CODE = 2;
//	public static final byte ADD_HSN_CODE = 3;
//	public static final byte DELETE_HSN_CODE = 4;
//	
//
//	public static final byte READ_ASSIGNED_PRESCRIPTION = 1;
//	public static final byte READ_PRESCRIPTION = 1;
//	public static final byte EDIT_PRESCRIPTION = 2;
//	public static final byte ADD_PRESCRIPTION = 3;
//	public static final byte DELETE_PRESCRIPTION = 4;
//	
//
//	public static final byte READ_ASSIGNED_PRESCRIPTION_DETAILS = 1;
//	public static final byte READ_PRESCRIPTION_DETAILS = 1;
//	public static final byte EDIT_PRESCRIPTION_DETAILS = 2;
//	public static final byte ADD_PRESCRIPTION_DETAILS = 3;
//	public static final byte DELETE_PRESCRIPTION_DETAILS = 4;
//	
//	public static final byte READ_ASSIGNED_PATIENT = 1;
//	public static final byte READ_PATIENT = 1;
//	public static final byte EDIT_PATIENT = 2;
//	public static final byte ADD_PATIENT = 3;
//	public static final byte DELETE_PATIENT = 4;
//	
//	public static final byte READ_ASSIGNED_PHARMACY = 1;
//	public static final byte READ_PHARMACY = 1;
//	public static final byte EDIT_PHARMACY = 2;
//	public static final byte ADD_PHARMACY = 3;
//	public static final byte DELETE_PHARMACY = 4;
//	
//	public static final byte READ_ASSIGNED_TEMPLATE = 1;
//	public static final byte READ_TEMPLATE = 1;
//	public static final byte EDIT_TEMPLATE = 2;
//	public static final byte ADD_TEMPLATE = 3;
//	public static final byte DELETE_TEMPLATE = 4;
//
//
//
//	public static final byte READ_ASSIGNED_INVENTORY = 1;
//	public static final byte READ_INVENTORY = 1;
//	public static final byte EDIT_INVENTORY = 2;
//	public static final byte ADD_INVENTORY = 3;
//	public static final byte DELETE_INVENTORY = 4;
//
//	
//	public static final byte READ_ASSIGNED_LETTER_OF_CREDIT = 1;
//	public static final byte READ_LETTER_OF_CREDIT = 1;
//	public static final byte EDIT_LETTER_OF_CREDIT = 2;
//	public static final byte ADD_LETTER_OF_CREDIT = 3;
//	public static final byte DELETE_LETTER_OF_CREDIT = 4;
//
//
//	public static final byte READ_ASSIGNED_LOOKUP_GROUP_NEW = 1;
//	public static final byte READ_LOOKUP_GROUP_NEW = 1;
//	public static final byte EDIT_LOOKUP_GROUP_NEW = 2;
//	public static final byte ADD_LOOKUP_GROUP_NEW = 3;
//	public static final byte DELETE_LOOKUP_GROUP_NEW = 4;
//
//
//	public static final byte READ_ASSIGNED_OPEN_REQUESTS = 1;
//	public static final byte READ_OPEN_REQUESTS = 1;
//	public static final byte EDIT_OPEN_REQUESTS = 2;
//	public static final byte ADD_OPEN_REQUESTS = 3;
//	public static final byte DELETE_OPEN_REQUESTS = 4;
//
//
//	public static final byte READ_ASSIGNED_NEGOTIATION_DATA = 1;
//	public static final byte READ_NEGOTIATION_DATA = 1;
//	public static final byte EDIT_NEGOTIATION_DATA = 2;
//	public static final byte ADD_NEGOTIATION_DATA = 3;
//	public static final byte DELETE_NEGOTIATION_DATA = 4;
//
//	public static final byte READ_ASSIGNED_ORGANIZATION_PARAMETERS = 1;
//	public static final byte READ_ORGANIZATION_PARAMETERS = 1;
//	public static final byte EDIT_ORGANIZATION_PARAMETERS = 2;
//	public static final byte ADD_ORGANIZATION_PARAMETERS = 3;
//	public static final byte DELETE_ORGANIZATION_PARAMETERS = 4;
//
//	public static final byte READ_ASSIGNED_PAYMENT_RECEIVED = 1;
//	public static final byte READ_PAYMENT_RECEIVED = 1;
//	public static final byte EDIT_PAYMENT_RECEIVED = 2;
//	public static final byte ADD_PAYMENT_RECEIVED = 3;
//	public static final byte DELETE_PAYMENT_RECEIVED = 4;
//
//
//	public static final byte READ_ASSIGNED_PORT = 1;
//	public static final byte READ_PORT = 1;
//	public static final byte EDIT_PORT = 2;
//	public static final byte ADD_PORT = 3;
//	public static final byte DELETE_PORT = 4;
//
//
//	public static final byte READ_ASSIGNED_PRODUCT = 1;
//	public static final byte READ_PRODUCT = 1;
//	public static final byte EDIT_PRODUCT = 2;
//	public static final byte ADD_PRODUCT = 3;
//	public static final byte DELETE_PRODUCT = 4;
//
//
//	public static final byte READ_ASSIGNED_PRODUCT_CATEGORY = 1;
//	public static final byte READ_PRODUCT_CATEGORY = 1;
//	public static final byte EDIT_PRODUCT_CATEGORY = 2;
//	public static final byte ADD_PRODUCT_CATEGORY = 3;
//	public static final byte DELETE_PRODUCT_CATEGORY = 4;
//
//
//	public static final byte READ_ASSIGNED_PROFORMA_INVOICE = 1;
//	public static final byte READ_PROFORMA_INVOICE = 1;
//	public static final byte EDIT_PROFORMA_INVOICE = 2;
//	public static final byte ADD_PROFORMA_INVOICE = 3;
//	public static final byte DELETE_PROFORMA_INVOICE = 4;
//
//
//
//	public static final byte READ_ASSIGNED_PURCHASE_ORDER = 1;
//	public static final byte READ_PURCHASE_ORDER = 1;
//	public static final byte EDIT_PURCHASE_ORDER = 2;
//	public static final byte ADD_PURCHASE_ORDER = 3;
//	public static final byte DELETE_PURCHASE_ORDER = 4;
//
//
//
//	public static final byte READ_ASSIGNED_STOCK_IN_TRANSIT = 1;
//	public static final byte READ_STOCK_IN_TRANSIT = 1;
//	public static final byte EDIT_STOCK_IN_TRANSIT = 2;
//	public static final byte ADD_STOCK_IN_TRANSIT = 3;
//	public static final byte DELETE_STOCK_IN_TRANSIT = 4;
//	public static final byte READ_ASSIGNED_VENDOR = 1;
//	public static final byte READ_VENDOR = 1;
//	public static final byte EDIT_VENDOR = 2;
//	public static final byte ADD_VENDOR = 3;
//	public static final byte DELETE_VENDOR = 4;
//
//	
//	//////////////////////////////////////////////////////////////////////////////
//
//	public static final byte READ_ASSIGNED_DOSAGE = 1;
//	public static final byte READ_DOSAGE = 1;
//	public static final byte EDIT_DOSAGE = 2;
//	public static final byte ADD_DOSAGE = 3;
//	public static final byte DELETE_DOSAGE = 4;
	
	public static final byte READ_ASSIGNED_ANALYTICS = 1;
	public static final byte READ_ANALYTICS = 1;
	public static final byte EDIT_ANALYTICS = 2;
	public static final byte ADD_ANALYTICS = 3;
	public static final byte DELETE_ANALYTICS = 4;


	public static final byte READ_ASSIGNED_CITY = 1;
	public static final byte READ_CITY = 1;
	public static final byte EDIT_CITY = 2;
	public static final byte ADD_CITY = 3;
	public static final byte DELETE_CITY = 4;


	public static final byte READ_ASSIGNED_COUNTRY = 1;
	public static final byte READ_COUNTRY = 1;
	public static final byte EDIT_COUNTRY = 2;
	public static final byte ADD_COUNTRY = 3;
	public static final byte DELETE_COUNTRY = 4;


	public static final byte READ_ASSIGNED_CURRENCY = 1;
	public static final byte READ_CURRENCY = 1;
	public static final byte EDIT_CURRENCY = 2;
	public static final byte ADD_CURRENCY = 3;
	public static final byte DELETE_CURRENCY = 4;


	public static final byte READ_ASSIGNED_LOOKUP = 1;
	public static final byte READ_LOOKUP = 1;
	public static final byte EDIT_LOOKUP = 2;
	public static final byte ADD_LOOKUP = 3;
	public static final byte DELETE_LOOKUP = 4;


	public static final byte READ_ASSIGNED_LOOKUP_GROUP = 1;
	public static final byte READ_LOOKUP_GROUP = 1;
	public static final byte EDIT_LOOKUP_GROUP = 2;
	public static final byte ADD_LOOKUP_GROUP = 3;
	public static final byte DELETE_LOOKUP_GROUP = 4;


	public static final byte READ_ASSIGNED_ORGANIZATION = 1;
	public static final byte READ_ORGANIZATION = 1;
	public static final byte EDIT_ORGANIZATION = 2;
	public static final byte ADD_ORGANIZATION = 3;
	public static final byte DELETE_ORGANIZATION = 4;


	public static final byte READ_ASSIGNED_ORGANIZATION_SYS_USER_MAP = 1;
	public static final byte READ_ORGANIZATION_SYS_USER_MAP = 1;
	public static final byte EDIT_ORGANIZATION_SYS_USER_MAP = 2;
	public static final byte ADD_ORGANIZATION_SYS_USER_MAP = 3;
	public static final byte DELETE_ORGANIZATION_SYS_USER_MAP = 4;


	public static final byte READ_ASSIGNED_ROLE = 1;
	public static final byte READ_ROLE = 1;
	public static final byte EDIT_ROLE = 2;
	public static final byte ADD_ROLE = 3;
	public static final byte DELETE_ROLE = 4;


	public static final byte READ_ASSIGNED_SUBSCRIBER = 1;
	public static final byte READ_SUBSCRIBER = 1;
	public static final byte EDIT_SUBSCRIBER = 2;
	public static final byte ADD_SUBSCRIBER = 3;
	public static final byte DELETE_SUBSCRIBER = 4;


	public static final byte READ_ASSIGNED_SYSTEM_ISSUE = 1;
	public static final byte READ_SYSTEM_ISSUE = 1;
	public static final byte EDIT_SYSTEM_ISSUE = 2;
	public static final byte ADD_SYSTEM_ISSUE = 3;
	public static final byte DELETE_SYSTEM_ISSUE = 4;


	public static final byte READ_ASSIGNED_SYS_USER = 1;
	public static final byte READ_SYS_USER = 1;
	public static final byte EDIT_SYS_USER = 2;
	public static final byte ADD_SYS_USER = 3;
	public static final byte DELETE_SYS_USER = 4;


}



