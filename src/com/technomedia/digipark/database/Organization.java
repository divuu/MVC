////////////////////////////////////////////////////////////////////////////

// FileName Organization.java: Organization Implementation file

// Author : Vinu | Utkarsh | JRC
// Description : Digi Park v1.0


////////////////////////////////////////////////////////////////////////////


package com.technomedia.digipark.database;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import java.sql.Timestamp;
import com.technomedia.logger.MLogger;


public class Organization  {

	//public static final String CREATE_ORGANIZATION_TABLE = "CREATE TABLE organization (organization_id INTEGER, organization_name TEXT, short_name TEXT, registered_address TEXT, business_address TEXT, city_id INTEGER, state_lookup_id INTEGER, country_id INTEGER, email TEXT, website TEXT, mobile_number TEXT, landline_number TEXT, fax_number TEXT, contact_person_name TEXT, contact_person_mobile_number TEXT, contact_person_whatsApp_no TEXT, contact_person_email TEXT, logo_path TEXT, documents_path TEXT, bank_account_id INTEGER, type_of_business INTEGER, remarks TEXT, preferences INTEGER)";

	public final static String DATABASE_TABLE_NAME = "organization";


//MySQL Script
	//CREATE TABLE organization( organization_id int, organization_name varchar(64), short_name varchar(8), registered_address tinytext, business_address tinytext, city_id int, state_lookup_id int, country_id int, email varchar(128), website varchar(128), mobile_number varchar(24), landline_number varchar(24), fax_number varchar(24), contact_person_name varchar(128), contact_person_mobile_number varchar(24), contact_person_whatsApp_no varchar(24), contact_person_email varchar(128), logo_path text, documents_path text, bank_account_id smallint, type_of_business int, remarks text, preferences int );
	public static final String KEY_ORGANIZATION_ID = "organization_id";
	public static final String KEY_ORGANIZATION_NAME = "organization_name";
	public static final String KEY_SHORT_NAME = "short_name";
	public static final String KEY_REGISTERED_ADDRESS = "registered_address";
	public static final String KEY_BUSINESS_ADDRESS = "business_address";
	public static final String KEY_CITY_ID = "city_id";
	public static final String KEY_STATE_LOOKUP_ID = "state_lookup_id";
	public static final String KEY_COUNTRY_ID = "country_id";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_WEBSITE = "website";
	public static final String KEY_MOBILE_NUMBER = "mobile_number";
	public static final String KEY_LANDLINE_NUMBER = "landline_number";
	public static final String KEY_FAX_NUMBER = "fax_number";
	public static final String KEY_CONTACT_PERSON_NAME = "contact_person_name";
	public static final String KEY_CONTACT_PERSON_MOBILE_NUMBER = "contact_person_mobile_number";
	public static final String KEY_CONTACT_PERSON_WHATSAPP_NO = "contact_person_whatsApp_no";
	public static final String KEY_CONTACT_PERSON_EMAIL = "contact_person_email";
	public static final String KEY_LOGO_PATH = "logo_path";
	public static final String KEY_DOCUMENTS_PATH = "documents_path";
	public static final String KEY_BANK_ACCOUNT_ID = "bank_account_id";
	public static final String KEY_TYPE_OF_BUSINESS = "type_of_business";
	public static final String KEY_REMARKS = "remarks";
	public static final String KEY_PREFERENCES = "preferences";

	public static final String LABEL_ORGANIZATION_ID = "ID";//"Organization Id";
	public static final String LABEL_ORGANIZATION_NAME = "Organization Name";
	public static final String LABEL_SHORT_NAME = "Short Name";
	public static final String LABEL_REGISTERED_ADDRESS = "Registered Address";
	public static final String LABEL_BUSINESS_ADDRESS = "Business Address";
	public static final String LABEL_CITY_ID = "City";
	public static final String LABEL_STATE_LOOKUP_ID = "State";
	public static final String LABEL_COUNTRY_ID = "Country";
	public static final String LABEL_EMAIL = "Email";
	public static final String LABEL_WEBSITE = "Website";
	public static final String LABEL_MOBILE_NUMBER = "Mobile Number";
	public static final String LABEL_LANDLINE_NUMBER = "Landline Number";
	public static final String LABEL_FAX_NUMBER = "Fax Number";
	public static final String LABEL_CONTACT_PERSON_NAME = "Contact Person Name";
	public static final String LABEL_CONTACT_PERSON_MOBILE_NUMBER = "Contact Person Mobile Number";
	public static final String LABEL_CONTACT_PERSON_WHATSAPP_NO = "Contact Person Whatsapp No";
	public static final String LABEL_CONTACT_PERSON_EMAIL = "Contact Person Email";
	public static final String LABEL_LOGO_PATH = "Logo Path";
	public static final String LABEL_DOCUMENTS_PATH = "Documents Path";
	public static final String LABEL_BANK_ACCOUNT_ID = "Bank Account";
	public static final String LABEL_TYPE_OF_BUSINESS = "Type Of Business";
	public static final String LABEL_REMARKS = "Remarks";
	public static final String LABEL_PREFERENCES = "Preferences";

	public static final int INDEX_ORGANIZATION_ID = 0;
	public static final int INDEX_ORGANIZATION_NAME = 1;
	public static final int INDEX_SHORT_NAME = 2;
	public static final int INDEX_REGISTERED_ADDRESS = 3;
	public static final int INDEX_BUSINESS_ADDRESS = 4;
	public static final int INDEX_CITY_ID = 5;
	public static final int INDEX_STATE_LOOKUP_ID = 6;
	public static final int INDEX_COUNTRY_ID = 7;
	public static final int INDEX_EMAIL = 8;
	public static final int INDEX_WEBSITE = 9;
	public static final int INDEX_MOBILE_NUMBER = 10;
	public static final int INDEX_LANDLINE_NUMBER = 11;
	public static final int INDEX_FAX_NUMBER = 12;
	public static final int INDEX_CONTACT_PERSON_NAME = 13;
	public static final int INDEX_CONTACT_PERSON_MOBILE_NUMBER = 14;
	public static final int INDEX_CONTACT_PERSON_WHATSAPP_NO = 15;
	public static final int INDEX_CONTACT_PERSON_EMAIL = 16;
	public static final int INDEX_LOGO_PATH = 17;
	public static final int INDEX_DOCUMENTS_PATH = 18;
	public static final int INDEX_BANK_ACCOUNT_ID = 19;
	public static final int INDEX_TYPE_OF_BUSINESS = 20;
	public static final int INDEX_REMARKS = 21;
	public static final int INDEX_PREFERENCES = 22;

	public final static int NUM_OF_COLUMNS = 23;

	public int mOrganizationId;
	public String mOrganizationName;		//Name of the organization
	public String mShortName;		//Short Name of the organization used in invoice numbers etc e.g. TE or RE
	public String mRegisteredAddress;		//Registered Address
	public String mBusinessAddress;		//Business Address incase it is different from Registered Address
	public int mCityId;		//City in which this organization has been registered or is functioning from
	public int mStateLookupId;		//State to which this belongs to
	public int mCountryId;		//Country in which this organization has been registered or is functioning from
	public String mEmail;		//Company Email
	public String mWebsite;
	public String mMobileNumber;
	public String mLandlineNumber;
	public String mFaxNumber;
	public String mContactPersonName;		//Main contact person of the organization
	public String mContactPersonMobileNumber;		//Mobile No
	public String mContactPersonWhatsappNo;		//WhatsApp No
	public String mContactPersonEmail;
	public String mLogoPath;		//filename with Path of the file containing the logo
	public String mDocumentsPath;		//List of paths of the documents scanned and stored
	public short mBankAccountId;		//Preferred bank id
	public int mTypeOfBusiness;		//Bit field: export, manufacturing, trading
	public String mRemarks;		//Any other data that is not stored above
	public int mPreferences;		//Bit Field: can be used to indicate which fields need to be mandatory

	public Organization() {
		mOrganizationId = 0;
		mOrganizationName = null;
		mShortName = null;
		mRegisteredAddress = null;
		mBusinessAddress = null;
		mCityId = 0;
		mStateLookupId = 0;
		mCountryId = 0;
		mEmail = null;
		mWebsite = null;
		mMobileNumber = null;
		mLandlineNumber = null;
		mFaxNumber = null;
		mContactPersonName = null;
		mContactPersonMobileNumber = null;
		mContactPersonWhatsappNo = null;
		mContactPersonEmail = null;
		mLogoPath = null;
		mDocumentsPath = null;
		mBankAccountId = 0;
		mTypeOfBusiness = 0;
		mRemarks = null;
		mPreferences = 0;
	}

/*
	public void printData() {

		MLogger.i( MLogger.MOD_DB, "organization_id " + mOrganizationId );
		MLogger.i( MLogger.MOD_DB, "organization_name " + mOrganizationName );
		MLogger.i( MLogger.MOD_DB, "short_name " + mShortName );
		MLogger.i( MLogger.MOD_DB, "registered_address " + mRegisteredAddress );
		MLogger.i( MLogger.MOD_DB, "business_address " + mBusinessAddress );
		MLogger.i( MLogger.MOD_DB, "city_id " + mCityId );
		MLogger.i( MLogger.MOD_DB, "state_lookup_id " + mStateLookupId );
		MLogger.i( MLogger.MOD_DB, "country_id " + mCountryId );
		MLogger.i( MLogger.MOD_DB, "email " + mEmail );
		MLogger.i( MLogger.MOD_DB, "website " + mWebsite );
		MLogger.i( MLogger.MOD_DB, "mobile_number " + mMobileNumber );
		MLogger.i( MLogger.MOD_DB, "landline_number " + mLandlineNumber );
		MLogger.i( MLogger.MOD_DB, "fax_number " + mFaxNumber );
		MLogger.i( MLogger.MOD_DB, "contact_person_name " + mContactPersonName );
		MLogger.i( MLogger.MOD_DB, "contact_person_mobile_number " + mContactPersonMobileNumber );
		MLogger.i( MLogger.MOD_DB, "contact_person_whatsApp_no " + mContactPersonWhatsappNo );
		MLogger.i( MLogger.MOD_DB, "contact_person_email " + mContactPersonEmail );
		MLogger.i( MLogger.MOD_DB, "logo_path " + mLogoPath );
		MLogger.i( MLogger.MOD_DB, "documents_path " + mDocumentsPath );
		MLogger.i( MLogger.MOD_DB, "bank_account_id " + mBankAccountId );
		MLogger.i( MLogger.MOD_DB, "type_of_business " + mTypeOfBusiness );
		MLogger.i( MLogger.MOD_DB, "remarks " + mRemarks );
		MLogger.i( MLogger.MOD_DB, "preferences " + mPreferences );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_ORGANIZATION_ID);
		tableHeader.add(LABEL_ORGANIZATION_NAME);
		tableHeader.add(LABEL_SHORT_NAME);
		tableHeader.add(LABEL_REGISTERED_ADDRESS);
		tableHeader.add(LABEL_BUSINESS_ADDRESS);
		tableHeader.add(LABEL_CITY_ID);
		tableHeader.add(LABEL_STATE_LOOKUP_ID);
		tableHeader.add(LABEL_COUNTRY_ID);
		tableHeader.add(LABEL_EMAIL);
		tableHeader.add(LABEL_WEBSITE);
		tableHeader.add(LABEL_MOBILE_NUMBER);
		tableHeader.add(LABEL_LANDLINE_NUMBER);
		tableHeader.add(LABEL_FAX_NUMBER);
		tableHeader.add(LABEL_CONTACT_PERSON_NAME);
		tableHeader.add(LABEL_CONTACT_PERSON_MOBILE_NUMBER);
		tableHeader.add(LABEL_CONTACT_PERSON_WHATSAPP_NO);
		tableHeader.add(LABEL_CONTACT_PERSON_EMAIL);
		tableHeader.add(LABEL_LOGO_PATH);
		tableHeader.add(LABEL_DOCUMENTS_PATH);
		tableHeader.add(LABEL_BANK_ACCOUNT_ID);
		tableHeader.add(LABEL_TYPE_OF_BUSINESS);
		tableHeader.add(LABEL_REMARKS);
		tableHeader.add(LABEL_PREFERENCES);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<Organization> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (Organization obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mOrganizationId));
			strList.put(obj.mOrganizationName == null?"":obj.mOrganizationName);
			strList.put(obj.mShortName == null?"":obj.mShortName);
			strList.put(obj.mRegisteredAddress == null?"":obj.mRegisteredAddress);
			strList.put(obj.mBusinessAddress == null?"":obj.mBusinessAddress);
			strList.put(String.valueOf(obj.mCityId));
			strList.put(String.valueOf(obj.mStateLookupId));
			strList.put(String.valueOf(obj.mCountryId));
			strList.put(obj.mEmail == null?"":obj.mEmail);
			strList.put(obj.mWebsite == null?"":obj.mWebsite);
			strList.put(obj.mMobileNumber == null?"":obj.mMobileNumber);
			strList.put(obj.mLandlineNumber == null?"":obj.mLandlineNumber);
			strList.put(obj.mFaxNumber == null?"":obj.mFaxNumber);
			strList.put(obj.mContactPersonName == null?"":obj.mContactPersonName);
			strList.put(obj.mContactPersonMobileNumber == null?"":obj.mContactPersonMobileNumber);
			strList.put(obj.mContactPersonWhatsappNo == null?"":obj.mContactPersonWhatsappNo);
			strList.put(obj.mContactPersonEmail == null?"":obj.mContactPersonEmail);
			strList.put(obj.mLogoPath == null?"":obj.mLogoPath);
			strList.put(obj.mDocumentsPath == null?"":obj.mDocumentsPath);
			strList.put(String.valueOf(obj.mBankAccountId));
			strList.put(String.valueOf(obj.mTypeOfBusiness));
			strList.put(obj.mRemarks == null?"":obj.mRemarks);
			strList.put(String.valueOf(obj.mPreferences));
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "OrgnztnD", "OrgnztnM", "ShrtNm", "RgstrdRs", "BsnsDrs", "CtyD", "StLkpD", "CntryD", "Eml", "Wbst", "MblNmbr", "LndlnMbr", "FxNmbr", "CntctPrsnM", "CntctPrsnMblNmbr", "CntctPrsnWhtsapN", "CntctPrsnMl", "LgPth", "DcmntsPth", "BnkCntD", "TypFBsns", "Rmrks", "Prfrncs"};
		String[] keys = { "mOrganizationId", "mOrganizationName", "mShortName", "mRegisteredAddress", "mBusinessAddress", "mCityId", "mStateLookupId", "mCountryId", "mEmail", "mWebsite", "mMobileNumber", "mLandlineNumber", "mFaxNumber", "mContactPersonName", "mContactPersonMobileNumber", "mContactPersonWhatsappNo", "mContactPersonEmail", "mLogoPath", "mDocumentsPath", "mBankAccountId", "mTypeOfBusiness", "mRemarks", "mPreferences"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<Organization> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (Organization obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<Organization> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(Organization obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mOrganizationId));
			strList.add(obj.mOrganizationName);
			strList.add(obj.mShortName);
			strList.add(obj.mRegisteredAddress);
			strList.add(obj.mBusinessAddress);
			strList.add(String.valueOf(obj.mCityId));
			strList.add(String.valueOf(obj.mStateLookupId));
			strList.add(String.valueOf(obj.mCountryId));
			strList.add(obj.mEmail);
			strList.add(obj.mWebsite);
			strList.add(obj.mMobileNumber);
			strList.add(obj.mLandlineNumber);
			strList.add(obj.mFaxNumber);
			strList.add(obj.mContactPersonName);
			strList.add(obj.mContactPersonMobileNumber);
			strList.add(obj.mContactPersonWhatsappNo);
			strList.add(obj.mContactPersonEmail);
			strList.add(obj.mLogoPath);
			strList.add(obj.mDocumentsPath);
			strList.add(String.valueOf(obj.mBankAccountId));
			strList.add(String.valueOf(obj.mTypeOfBusiness));
			strList.add(obj.mRemarks);
			strList.add(String.valueOf(obj.mPreferences));

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(Organization obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mOrganizationId));
		strList.add(obj.mOrganizationName);
		strList.add(obj.mShortName);
		strList.add(obj.mRegisteredAddress);
		strList.add(obj.mBusinessAddress);
		strList.add(String.valueOf(obj.mCityId));
		strList.add(String.valueOf(obj.mStateLookupId));
		strList.add(String.valueOf(obj.mCountryId));
		strList.add(obj.mEmail);
		strList.add(obj.mWebsite);
		strList.add(obj.mMobileNumber);
		strList.add(obj.mLandlineNumber);
		strList.add(obj.mFaxNumber);
		strList.add(obj.mContactPersonName);
		strList.add(obj.mContactPersonMobileNumber);
		strList.add(obj.mContactPersonWhatsappNo);
		strList.add(obj.mContactPersonEmail);
		strList.add(obj.mLogoPath);
		strList.add(obj.mDocumentsPath);
		strList.add(String.valueOf(obj.mBankAccountId));
		strList.add(String.valueOf(obj.mTypeOfBusiness));
		strList.add(obj.mRemarks);
		strList.add(String.valueOf(obj.mPreferences));

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< Organization > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(Organization obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mOrganizationId));
			strList.add(String.valueOf(obj.mOrganizationName));
			strList.add(String.valueOf(obj.mShortName));
			strList.add(String.valueOf(obj.mRegisteredAddress));
			strList.add(String.valueOf(obj.mBusinessAddress));
			strList.add(String.valueOf(obj.mCityId));
			strList.add(String.valueOf(obj.mStateLookupId));
			strList.add(String.valueOf(obj.mCountryId));
			strList.add(String.valueOf(obj.mEmail));
			strList.add(String.valueOf(obj.mWebsite));
			strList.add(String.valueOf(obj.mMobileNumber));
			strList.add(String.valueOf(obj.mLandlineNumber));
			strList.add(String.valueOf(obj.mFaxNumber));
			strList.add(String.valueOf(obj.mContactPersonName));
			strList.add(String.valueOf(obj.mContactPersonMobileNumber));
			strList.add(String.valueOf(obj.mContactPersonWhatsappNo));
			strList.add(String.valueOf(obj.mContactPersonEmail));
			strList.add(String.valueOf(obj.mLogoPath));
			strList.add(String.valueOf(obj.mDocumentsPath));
			strList.add(String.valueOf(obj.mBankAccountId));
			strList.add(String.valueOf(obj.mTypeOfBusiness));
			strList.add(String.valueOf(obj.mRemarks));
			strList.add(String.valueOf(obj.mPreferences));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( Organization obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mOrganizationId));
		strList.add(String.valueOf(obj.mOrganizationName));
		strList.add(String.valueOf(obj.mShortName));
		strList.add(String.valueOf(obj.mRegisteredAddress));
		strList.add(String.valueOf(obj.mBusinessAddress));
		strList.add(String.valueOf(obj.mCityId));
		strList.add(String.valueOf(obj.mStateLookupId));
		strList.add(String.valueOf(obj.mCountryId));
		strList.add(String.valueOf(obj.mEmail));
		strList.add(String.valueOf(obj.mWebsite));
		strList.add(String.valueOf(obj.mMobileNumber));
		strList.add(String.valueOf(obj.mLandlineNumber));
		strList.add(String.valueOf(obj.mFaxNumber));
		strList.add(String.valueOf(obj.mContactPersonName));
		strList.add(String.valueOf(obj.mContactPersonMobileNumber));
		strList.add(String.valueOf(obj.mContactPersonWhatsappNo));
		strList.add(String.valueOf(obj.mContactPersonEmail));
		strList.add(String.valueOf(obj.mLogoPath));
		strList.add(String.valueOf(obj.mDocumentsPath));
		strList.add(String.valueOf(obj.mBankAccountId));
		strList.add(String.valueOf(obj.mTypeOfBusiness));
		strList.add(String.valueOf(obj.mRemarks));
		strList.add(String.valueOf(obj.mPreferences));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(Organization organization) {
		JSONObject jsonObj = new JSONObject();
		String[] organizationLabels = getmKeys();
		try {
			jsonObj.put(organizationLabels[ INDEX_ORGANIZATION_ID], organization.mOrganizationId );
			jsonObj.put(organizationLabels[ INDEX_ORGANIZATION_NAME], organization.mOrganizationName );
			jsonObj.put(organizationLabels[ INDEX_SHORT_NAME], organization.mShortName );
			jsonObj.put(organizationLabels[ INDEX_REGISTERED_ADDRESS], organization.mRegisteredAddress );
			jsonObj.put(organizationLabels[ INDEX_BUSINESS_ADDRESS], organization.mBusinessAddress );
			jsonObj.put(organizationLabels[ INDEX_CITY_ID], organization.mCityId );
			jsonObj.put(organizationLabels[ INDEX_STATE_LOOKUP_ID], organization.mStateLookupId );
			jsonObj.put(organizationLabels[ INDEX_COUNTRY_ID], organization.mCountryId );
			jsonObj.put(organizationLabels[ INDEX_EMAIL], organization.mEmail );
			jsonObj.put(organizationLabels[ INDEX_WEBSITE], organization.mWebsite );
			jsonObj.put(organizationLabels[ INDEX_MOBILE_NUMBER], organization.mMobileNumber );
			jsonObj.put(organizationLabels[ INDEX_LANDLINE_NUMBER], organization.mLandlineNumber );
			jsonObj.put(organizationLabels[ INDEX_FAX_NUMBER], organization.mFaxNumber );
			jsonObj.put(organizationLabels[ INDEX_CONTACT_PERSON_NAME], organization.mContactPersonName );
			jsonObj.put(organizationLabels[ INDEX_CONTACT_PERSON_MOBILE_NUMBER], organization.mContactPersonMobileNumber );
			jsonObj.put(organizationLabels[ INDEX_CONTACT_PERSON_WHATSAPP_NO], organization.mContactPersonWhatsappNo );
			jsonObj.put(organizationLabels[ INDEX_CONTACT_PERSON_EMAIL], organization.mContactPersonEmail );
			jsonObj.put(organizationLabels[ INDEX_LOGO_PATH], organization.mLogoPath );
			jsonObj.put(organizationLabels[ INDEX_DOCUMENTS_PATH], organization.mDocumentsPath );
			jsonObj.put(organizationLabels[ INDEX_BANK_ACCOUNT_ID], organization.mBankAccountId );
			jsonObj.put(organizationLabels[ INDEX_TYPE_OF_BUSINESS], organization.mTypeOfBusiness );
			jsonObj.put(organizationLabels[ INDEX_REMARKS], organization.mRemarks );
			jsonObj.put(organizationLabels[ INDEX_PREFERENCES], organization.mPreferences );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static Organization jsonObjectToObject( String jsonObjectString ) {
		Organization organization = new Organization();
		JSONObject data;
		String[] organizationLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			organization.mOrganizationId = data.getInt(organizationLabels[INDEX_ORGANIZATION_ID]);
			organization.mOrganizationName = data.getString(organizationLabels[INDEX_ORGANIZATION_NAME]);
			organization.mShortName = data.getString(organizationLabels[INDEX_SHORT_NAME]);
			organization.mRegisteredAddress = data.getString(organizationLabels[INDEX_REGISTERED_ADDRESS]);
			organization.mBusinessAddress = data.getString(organizationLabels[INDEX_BUSINESS_ADDRESS]);
			organization.mCityId = data.getInt(organizationLabels[INDEX_CITY_ID]);
			organization.mStateLookupId = data.getInt(organizationLabels[INDEX_STATE_LOOKUP_ID]);
			organization.mCountryId = data.getInt(organizationLabels[INDEX_COUNTRY_ID]);
			organization.mEmail = data.getString(organizationLabels[INDEX_EMAIL]);
			organization.mWebsite = data.getString(organizationLabels[INDEX_WEBSITE]);
			organization.mMobileNumber = data.getString(organizationLabels[INDEX_MOBILE_NUMBER]);
			organization.mLandlineNumber = data.getString(organizationLabels[INDEX_LANDLINE_NUMBER]);
			organization.mFaxNumber = data.getString(organizationLabels[INDEX_FAX_NUMBER]);
			organization.mContactPersonName = data.getString(organizationLabels[INDEX_CONTACT_PERSON_NAME]);
			organization.mContactPersonMobileNumber = data.getString(organizationLabels[INDEX_CONTACT_PERSON_MOBILE_NUMBER]);
			organization.mContactPersonWhatsappNo = data.getString(organizationLabels[INDEX_CONTACT_PERSON_WHATSAPP_NO]);
			organization.mContactPersonEmail = data.getString(organizationLabels[INDEX_CONTACT_PERSON_EMAIL]);
			organization.mLogoPath = data.getString(organizationLabels[INDEX_LOGO_PATH]);
			organization.mDocumentsPath = data.getString(organizationLabels[INDEX_DOCUMENTS_PATH]);
			organization.mBankAccountId = (short)data.getInt(organizationLabels[INDEX_BANK_ACCOUNT_ID]);
			organization.mTypeOfBusiness = data.getInt(organizationLabels[INDEX_TYPE_OF_BUSINESS]);
			organization.mRemarks = data.getString(organizationLabels[INDEX_REMARKS]);
			organization.mPreferences = data.getInt(organizationLabels[INDEX_PREFERENCES]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return organization;

	}

	public static ArrayList<Organization> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<Organization> objList = new ArrayList<Organization>();
		try {

			JSONArray organizationJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < organizationJSONArray.length(); i++) {

				JSONObject organizationJsonObject = organizationJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(organizationJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
