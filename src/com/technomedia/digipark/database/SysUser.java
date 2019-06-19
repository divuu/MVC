////////////////////////////////////////////////////////////////////////////

// FileName SysUser.java: SysUser Implementation file

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


public class SysUser  {

	//public static final String CREATE_SYSUSER_TABLE = "CREATE TABLE sys_user (sys_user_id INTEGER, role_id INTEGER, name TEXT, mobile_number TEXT, mobile_number_2 TEXT, email TEXT, address TEXT, city_id INTEGER, country_id INTEGER, designation_lookup_id INTEGER, latitude REAL, longitude REAL, gender_lookup_id INTEGER, date_of_birth INTEGER, driving_license_number TEXT, passport_number TEXT, remarks TEXT, photo TEXT, date_of_registration INTEGER, last_login_date INTEGER, no_of_logins INTEGER, create_by_sys_user_id INTEGER, active_lookup_id INTEGER, parking_area_gate_id INTEGER, status INTEGER, password TEXT, salt TEXT, extra_data TEXT)";

	public final static String DATABASE_TABLE_NAME = "sys_user";


//MySQL Script
	//CREATE TABLE sys_user( sys_user_id int, role_id smallint, name varchar(128), mobile_number varchar(16), mobile_number_2 varchar(16), email varchar(128), address text, city_id int, country_id int, designation_lookup_id int, latitude float, longitude float, gender_lookup_id int, date_of_birth date, driving_license_number varchar(32), passport_number varchar(32), remarks text, photo text, date_of_registration datetime, last_login_date datetime, no_of_logins int, create_by_sys_user_id int, active_lookup_id int, parking_area_date_id int, status int, password varchar(64), salt varchar(64), extra_data text );
	public static final String KEY_SYS_USER_ID = "sys_user_id";
	public static final String KEY_ROLE_ID = "role_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_MOBILE_NUMBER = "mobile_number";
	public static final String KEY_MOBILE_NUMBER_2 = "mobile_number_2";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_CITY_ID = "city_id";
	public static final String KEY_COUNTRY_ID = "country_id";
	public static final String KEY_DESIGNATION_LOOKUP_ID = "designation_lookup_id";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_GENDER_LOOKUP_ID = "gender_lookup_id";
	public static final String KEY_DATE_OF_BIRTH = "date_of_birth";
	public static final String KEY_DRIVING_LICENSE_NUMBER = "driving_license_number";
	public static final String KEY_PASSPORT_NUMBER = "passport_number";
	public static final String KEY_REMARKS = "remarks";
	public static final String KEY_PHOTO = "photo";
	public static final String KEY_DATE_OF_REGISTRATION = "date_of_registration";
	public static final String KEY_LAST_LOGIN_DATE = "last_login_date";
	public static final String KEY_NO_OF_LOGINS = "no_of_logins";
	public static final String KEY_CREATE_BY_SYS_USER_ID = "create_by_sys_user_id";
	public static final String KEY_ACTIVE_LOOKUP_ID = "active_lookup_id";
	public static final String KEY_PARKING_AREA_ID = "parking_area_id";
	public static final String KEY_LOGGED_IN_PARKING_AREA_GATE_ID = "logged_in_parking_area_gate_id";
	public static final String KEY_STATUS = "status";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_SALT = "salt";
	public static final String KEY_EXTRA_DATA = "extra_data";

	public static final String LABEL_SYS_USER_ID ="ID";// "Sys User Id";
	public static final String LABEL_ROLE_ID = "Role";
	public static final String LABEL_NAME = "Name";
	public static final String LABEL_MOBILE_NUMBER = "Mobile Number";
	public static final String LABEL_MOBILE_NUMBER_2 = "Mobile Number 2";
	public static final String LABEL_EMAIL = "Email";
	public static final String LABEL_ADDRESS = "Address";
	public static final String LABEL_CITY_ID = "City";
	public static final String LABEL_COUNTRY_ID = "Country";
	public static final String LABEL_DESIGNATION_LOOKUP_ID = "Designation";
	public static final String LABEL_LATITUDE = "Latitude";
	public static final String LABEL_LONGITUDE = "Longitude";
	public static final String LABEL_GENDER_LOOKUP_ID = "Gender";
	public static final String LABEL_DATE_OF_BIRTH = "Date Of Birth";
	public static final String LABEL_DRIVING_LICENSE_NUMBER = "Driving License Number";
	public static final String LABEL_PASSPORT_NUMBER = "Passport Number";
	public static final String LABEL_REMARKS = "Remarks";
	public static final String LABEL_PHOTO = "Photo";
	public static final String LABEL_DATE_OF_REGISTRATION = "Regis Date";//"Date Of Registration";
	public static final String LABEL_LAST_LOGIN_DATE = "Last Login Date";
	public static final String LABEL_NO_OF_LOGINS = "No Of Logins";
	public static final String LABEL_CREATE_BY_SYS_USER_ID = "Create By Sys User";
	public static final String LABEL_ACTIVE_LOOKUP_ID = "Active";
	public static final String LABEL_PARKING_AREA_ID = "Parking Area";
	public static final String LABEL_LOGGED_IN_PARKING_AREA_GATE_ID = "Logged In Parking Area Gate";
	public static final String LABEL_STATUS = "Status";
	public static final String LABEL_PASSWORD = "Password";
	public static final String LABEL_SALT = "Salt";
	public static final String LABEL_EXTRA_DATA = "Extra Data";

	public static final int INDEX_SYS_USER_ID = 0;
	public static final int INDEX_ROLE_ID = 1;
	public static final int INDEX_NAME = 2;
	public static final int INDEX_MOBILE_NUMBER = 3;
	public static final int INDEX_MOBILE_NUMBER_2 = 4;
	public static final int INDEX_EMAIL = 5;
	public static final int INDEX_ADDRESS = 6;
	public static final int INDEX_CITY_ID = 7;
	public static final int INDEX_COUNTRY_ID = 8;
	public static final int INDEX_DESIGNATION_LOOKUP_ID = 9;
	public static final int INDEX_LATITUDE = 10;
	public static final int INDEX_LONGITUDE = 11;
	public static final int INDEX_GENDER_LOOKUP_ID = 12;
	public static final int INDEX_DATE_OF_BIRTH = 13;
	public static final int INDEX_DRIVING_LICENSE_NUMBER = 14;
	public static final int INDEX_PASSPORT_NUMBER = 15;
	public static final int INDEX_REMARKS = 16;
	public static final int INDEX_PHOTO = 17;
	public static final int INDEX_DATE_OF_REGISTRATION = 18;
	public static final int INDEX_LAST_LOGIN_DATE = 19;
	public static final int INDEX_NO_OF_LOGINS = 20;
	public static final int INDEX_CREATE_BY_SYS_USER_ID = 21;
	public static final int INDEX_ACTIVE_LOOKUP_ID = 22;
	public static final int INDEX_PARKING_AREA_ID = 23;
	public static final int INDEX_LOGGED_IN_PARKING_AREA_GATE_ID = 24;
	public static final int INDEX_STATUS = 25;
	public static final int INDEX_PASSWORD = 26;
	public static final int INDEX_SALT = 27;
	public static final int INDEX_EXTRA_DATA = 28;

	public final static int NUM_OF_COLUMNS = 29;

	public int mSysUserId;
	public short mRoleId;
	public String mName;		//Name of the User
	public String mMobileNumber;
	public String mMobileNumber2;
	public String mEmail;
	public String mAddress;		//Address
	public int mCityId;
	public int mCountryId;
	public int mDesignationLookupId;		//Designation of the user
	public float mLatitude;
	public float mLongitude;
	public int mGenderLookupId;
	public long mDateOfBirth;		//Date of birth. Most likely the year will be correct. we can compute age from it
	public String mDrivingLicenseNumber;
	public String mPassportNumber;
	public String mRemarks;		//Any remarks to be stored by the backend or Field officer regarding this customer
	public String mPhoto;		//Location of photo
	public long mDateOfRegistration;
	public long mLastLoginDate;
	public int mNoOfLogins;		//The number of times that this user has logged into the system
	public int mCreateBySysUserId;		//The User that created this user
	public int mActiveLookupId;		//Only when the state is active, user can login
	public int mParkingAreaId;		//Fields officer has been alloted to this parking
	public int mLoggedInParkingAreaGateId;		//Field officer is currently at this gate. reset after logging out
	public int mStatus;		//Bit fields : Verified, Violator,
	public String mPassword;
	public String mSalt;		//salt string for password encryption
	public String mExtraData;

	public SysUser() {
		mSysUserId = 0;
		mRoleId = 0;
		mName = null;
		mMobileNumber = null;
		mMobileNumber2 = null;
		mEmail = null;
		mAddress = null;
		mCityId = 0;
		mCountryId = 0;
		mDesignationLookupId = 0;
		mLatitude = 0;
		mLongitude = 0;
		mGenderLookupId = 0;
		mDateOfBirth = 0;
		mDrivingLicenseNumber = null;
		mPassportNumber = null;
		mRemarks = null;
		mPhoto = null;
		mDateOfRegistration = 0;
		mLastLoginDate = 0;
		mNoOfLogins = 0;
		mCreateBySysUserId = 0;
		mActiveLookupId = 0;
		mParkingAreaId = 0;
		mLoggedInParkingAreaGateId = 0;
		mStatus = 0;
		mPassword = null;
		mSalt = null;
		mExtraData = null;
	}

/*
	public void printData() {

		String DateFormat = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
		Date parsedDate;

		MLogger.i( MLogger.MOD_DB, "sys_user_id " + mSysUserId );
		MLogger.i( MLogger.MOD_DB, "role_id " + mRoleId );
		MLogger.i( MLogger.MOD_DB, "name " + mName );
		MLogger.i( MLogger.MOD_DB, "mobile_number " + mMobileNumber );
		MLogger.i( MLogger.MOD_DB, "mobile_number_2 " + mMobileNumber2 );
		MLogger.i( MLogger.MOD_DB, "email " + mEmail );
		MLogger.i( MLogger.MOD_DB, "address " + mAddress );
		MLogger.i( MLogger.MOD_DB, "city_id " + mCityId );
		MLogger.i( MLogger.MOD_DB, "country_id " + mCountryId );
		MLogger.i( MLogger.MOD_DB, "designation_lookup_id " + mDesignationLookupId );
		MLogger.i( MLogger.MOD_DB, "latitude " + mLatitude );
		MLogger.i( MLogger.MOD_DB, "longitude " + mLongitude );
		MLogger.i( MLogger.MOD_DB, "gender_lookup_id " + mGenderLookupId );
		parsedDate = new Date(mDateOfBirth );
		MLogger.i( MLogger.MOD_DB, "date_of_birth " + sdf.format(parsedDate) );
		MLogger.i( MLogger.MOD_DB, "driving_license_number " + mDrivingLicenseNumber );
		MLogger.i( MLogger.MOD_DB, "passport_number " + mPassportNumber );
		MLogger.i( MLogger.MOD_DB, "remarks " + mRemarks );
		MLogger.i( MLogger.MOD_DB, "photo " + mPhoto );
		parsedDate = new Date(mDateOfRegistration );
		MLogger.i( MLogger.MOD_DB, "date_of_registration " + sdf.format(parsedDate) );
		parsedDate = new Date(mLastLoginDate );
		MLogger.i( MLogger.MOD_DB, "last_login_date " + sdf.format(parsedDate) );
		MLogger.i( MLogger.MOD_DB, "no_of_logins " + mNoOfLogins );
		MLogger.i( MLogger.MOD_DB, "create_by_sys_user_id " + mCreateBySysUserId );
		MLogger.i( MLogger.MOD_DB, "active_lookup_id " + mActiveLookupId );
		MLogger.i( MLogger.MOD_DB, "parking_area_id " + mParkingAreaId );
		MLogger.i( MLogger.MOD_DB, "logged_in_parking_area_gate_id " + mLoggedInParkingAreaGateId );
		MLogger.i( MLogger.MOD_DB, "status " + mStatus );
		MLogger.i( MLogger.MOD_DB, "password " + mPassword );
		MLogger.i( MLogger.MOD_DB, "salt " + mSalt );
		MLogger.i( MLogger.MOD_DB, "extra_data " + mExtraData );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_SYS_USER_ID);
		tableHeader.add(LABEL_ROLE_ID);
		tableHeader.add(LABEL_NAME);
		tableHeader.add(LABEL_MOBILE_NUMBER);
		tableHeader.add(LABEL_MOBILE_NUMBER_2);
		tableHeader.add(LABEL_EMAIL);
		tableHeader.add(LABEL_ADDRESS);
		tableHeader.add(LABEL_CITY_ID);
		tableHeader.add(LABEL_COUNTRY_ID);
		tableHeader.add(LABEL_DESIGNATION_LOOKUP_ID);
		tableHeader.add(LABEL_LATITUDE);
		tableHeader.add(LABEL_LONGITUDE);
		tableHeader.add(LABEL_GENDER_LOOKUP_ID);
		tableHeader.add(LABEL_DATE_OF_BIRTH);
		tableHeader.add(LABEL_DRIVING_LICENSE_NUMBER);
		tableHeader.add(LABEL_PASSPORT_NUMBER);
		tableHeader.add(LABEL_REMARKS);
		tableHeader.add(LABEL_PHOTO);
		tableHeader.add(LABEL_DATE_OF_REGISTRATION);
		tableHeader.add(LABEL_LAST_LOGIN_DATE);
		tableHeader.add(LABEL_NO_OF_LOGINS);
		tableHeader.add(LABEL_CREATE_BY_SYS_USER_ID);
		tableHeader.add(LABEL_ACTIVE_LOOKUP_ID);
		tableHeader.add(LABEL_PARKING_AREA_ID);
		tableHeader.add(LABEL_LOGGED_IN_PARKING_AREA_GATE_ID);
		tableHeader.add(LABEL_STATUS);
		tableHeader.add(LABEL_PASSWORD);
		tableHeader.add(LABEL_SALT);
		tableHeader.add(LABEL_EXTRA_DATA);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<SysUser> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (SysUser obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mSysUserId));
			strList.put(String.valueOf(obj.mRoleId));
			strList.put(obj.mName == null?"":obj.mName);
			strList.put(obj.mMobileNumber == null?"":obj.mMobileNumber);
			strList.put(obj.mMobileNumber2 == null?"":obj.mMobileNumber2);
			strList.put(obj.mEmail == null?"":obj.mEmail);
			strList.put(obj.mAddress == null?"":obj.mAddress);
			strList.put(String.valueOf(obj.mCityId));
			strList.put(String.valueOf(obj.mCountryId));
			strList.put(String.valueOf(obj.mDesignationLookupId));
			strList.put(String.valueOf(obj.mLatitude));
			strList.put(String.valueOf(obj.mLongitude));
			strList.put(String.valueOf(obj.mGenderLookupId));
			strList.put(String.valueOf(obj.mDateOfBirth));
			strList.put(obj.mDrivingLicenseNumber == null?"":obj.mDrivingLicenseNumber);
			strList.put(obj.mPassportNumber == null?"":obj.mPassportNumber);
			strList.put(obj.mRemarks == null?"":obj.mRemarks);
			strList.put(obj.mPhoto == null?"":obj.mPhoto);
			strList.put(String.valueOf(obj.mDateOfRegistration));
			strList.put(String.valueOf(obj.mLastLoginDate));
			strList.put(String.valueOf(obj.mNoOfLogins));
			strList.put(String.valueOf(obj.mCreateBySysUserId));
			strList.put(String.valueOf(obj.mActiveLookupId));
			strList.put(String.valueOf(obj.mParkingAreaId));
			strList.put(String.valueOf(obj.mLoggedInParkingAreaGateId));
			strList.put(String.valueOf(obj.mStatus));
			strList.put(obj.mPassword == null?"":obj.mPassword);
			strList.put(obj.mSalt == null?"":obj.mSalt);
			strList.put(obj.mExtraData == null?"":obj.mExtraData);
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "SysRD", "RlD", "Nm", "MblNmbr", "MblNmbr2", "Eml", "Adrs", "CtyD", "CntryD", "DsgntnLkpD", "Ltd", "Lngtd", "GndrLkpD", "DtFBrth", "DrvngLcnsNmbr", "PsprtNmbr", "Rmrks", "Pht", "DtFRgstrtn", "LstLgnDt", "NFLgns", "CrtBySysRD", "ActvLkpD", "PrkngRD", "LgdNPrkngRGtD", "Sts", "Pswrd", "Slt", "ExtrDt"};
		String[] keys = { "mSysUserId", "mRoleId", "mName", "mMobileNumber", "mMobileNumber2", "mEmail", "mAddress", "mCityId", "mCountryId", "mDesignationLookupId", "mLatitude", "mLongitude", "mGenderLookupId", "mDateOfBirth", "mDrivingLicenseNumber", "mPassportNumber", "mRemarks", "mPhoto", "mDateOfRegistration", "mLastLoginDate", "mNoOfLogins", "mCreateBySysUserId", "mActiveLookupId", "mParkingAreaId", "mLoggedInParkingAreaGateId", "mStatus", "mPassword", "mSalt", "mExtraData"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<SysUser> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (SysUser obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<SysUser> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(SysUser obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mSysUserId));
			strList.add(String.valueOf(obj.mRoleId));
			strList.add(obj.mName);
			strList.add(obj.mMobileNumber);
			strList.add(obj.mMobileNumber2);
			strList.add(obj.mEmail);
			strList.add(obj.mAddress);
			strList.add(String.valueOf(obj.mCityId));
			strList.add(String.valueOf(obj.mCountryId));
			strList.add(String.valueOf(obj.mDesignationLookupId));
			strList.add(String.valueOf(obj.mLatitude));
			strList.add(String.valueOf(obj.mLongitude));
			strList.add(String.valueOf(obj.mGenderLookupId));
			strList.add(String.valueOf(obj.mDateOfBirth));
			strList.add(obj.mDrivingLicenseNumber);
			strList.add(obj.mPassportNumber);
			strList.add(obj.mRemarks);
			strList.add(obj.mPhoto);
			strList.add(String.valueOf(obj.mDateOfRegistration));
			strList.add(String.valueOf(obj.mLastLoginDate));
			strList.add(String.valueOf(obj.mNoOfLogins));
			strList.add(String.valueOf(obj.mCreateBySysUserId));
			strList.add(String.valueOf(obj.mActiveLookupId));
			strList.add(String.valueOf(obj.mParkingAreaId));
			strList.add(String.valueOf(obj.mLoggedInParkingAreaGateId));
			strList.add(String.valueOf(obj.mStatus));
			strList.add(obj.mPassword);
			strList.add(obj.mSalt);
			strList.add(obj.mExtraData);

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(SysUser obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mSysUserId));
		strList.add(String.valueOf(obj.mRoleId));
		strList.add(obj.mName);
		strList.add(obj.mMobileNumber);
		strList.add(obj.mMobileNumber2);
		strList.add(obj.mEmail);
		strList.add(obj.mAddress);
		strList.add(String.valueOf(obj.mCityId));
		strList.add(String.valueOf(obj.mCountryId));
		strList.add(String.valueOf(obj.mDesignationLookupId));
		strList.add(String.valueOf(obj.mLatitude));
		strList.add(String.valueOf(obj.mLongitude));
		strList.add(String.valueOf(obj.mGenderLookupId));
		strList.add(String.valueOf(obj.mDateOfBirth));
		strList.add(obj.mDrivingLicenseNumber);
		strList.add(obj.mPassportNumber);
		strList.add(obj.mRemarks);
		strList.add(obj.mPhoto);
		strList.add(String.valueOf(obj.mDateOfRegistration));
		strList.add(String.valueOf(obj.mLastLoginDate));
		strList.add(String.valueOf(obj.mNoOfLogins));
		strList.add(String.valueOf(obj.mCreateBySysUserId));
		strList.add(String.valueOf(obj.mActiveLookupId));
		strList.add(String.valueOf(obj.mParkingAreaId));
		strList.add(String.valueOf(obj.mLoggedInParkingAreaGateId));
		strList.add(String.valueOf(obj.mStatus));
		strList.add(obj.mPassword);
		strList.add(obj.mSalt);
		strList.add(obj.mExtraData);

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< SysUser > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(SysUser obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mSysUserId));
			strList.add(String.valueOf(obj.mRoleId));
			strList.add(String.valueOf(obj.mName));
			strList.add(String.valueOf(obj.mMobileNumber));
			strList.add(String.valueOf(obj.mMobileNumber2));
			strList.add(String.valueOf(obj.mEmail));
			strList.add(String.valueOf(obj.mAddress));
			strList.add(String.valueOf(obj.mCityId));
			strList.add(String.valueOf(obj.mCountryId));
			strList.add(String.valueOf(obj.mDesignationLookupId));
			strList.add(String.valueOf(obj.mLatitude));
			strList.add(String.valueOf(obj.mLongitude));
			strList.add(String.valueOf(obj.mGenderLookupId));
			strList.add(String.valueOf(obj.mDateOfBirth));
			strList.add(String.valueOf(obj.mDrivingLicenseNumber));
			strList.add(String.valueOf(obj.mPassportNumber));
			strList.add(String.valueOf(obj.mRemarks));
			strList.add(String.valueOf(obj.mPhoto));
			strList.add(String.valueOf(obj.mDateOfRegistration));
			strList.add(String.valueOf(obj.mLastLoginDate));
			strList.add(String.valueOf(obj.mNoOfLogins));
			strList.add(String.valueOf(obj.mCreateBySysUserId));
			strList.add(String.valueOf(obj.mActiveLookupId));
			strList.add(String.valueOf(obj.mParkingAreaId));
			strList.add(String.valueOf(obj.mLoggedInParkingAreaGateId));
			strList.add(String.valueOf(obj.mStatus));
			strList.add(String.valueOf(obj.mPassword));
			strList.add(String.valueOf(obj.mSalt));
			strList.add(String.valueOf(obj.mExtraData));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( SysUser obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mSysUserId));
		strList.add(String.valueOf(obj.mRoleId));
		strList.add(String.valueOf(obj.mName));
		strList.add(String.valueOf(obj.mMobileNumber));
		strList.add(String.valueOf(obj.mMobileNumber2));
		strList.add(String.valueOf(obj.mEmail));
		strList.add(String.valueOf(obj.mAddress));
		strList.add(String.valueOf(obj.mCityId));
		strList.add(String.valueOf(obj.mCountryId));
		strList.add(String.valueOf(obj.mDesignationLookupId));
		strList.add(String.valueOf(obj.mLatitude));
		strList.add(String.valueOf(obj.mLongitude));
		strList.add(String.valueOf(obj.mGenderLookupId));
		strList.add(String.valueOf(obj.mDateOfBirth));
		strList.add(String.valueOf(obj.mDrivingLicenseNumber));
		strList.add(String.valueOf(obj.mPassportNumber));
		strList.add(String.valueOf(obj.mRemarks));
		strList.add(String.valueOf(obj.mPhoto));
		strList.add(String.valueOf(obj.mDateOfRegistration));
		strList.add(String.valueOf(obj.mLastLoginDate));
		strList.add(String.valueOf(obj.mNoOfLogins));
		strList.add(String.valueOf(obj.mCreateBySysUserId));
		strList.add(String.valueOf(obj.mActiveLookupId));
		strList.add(String.valueOf(obj.mParkingAreaId));
		strList.add(String.valueOf(obj.mLoggedInParkingAreaGateId));
		strList.add(String.valueOf(obj.mStatus));
		strList.add(String.valueOf(obj.mPassword));
		strList.add(String.valueOf(obj.mSalt));
		strList.add(String.valueOf(obj.mExtraData));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(SysUser sysUser) {
		JSONObject jsonObj = new JSONObject();
		String[] sysUserLabels = getmKeys();
		try {
			jsonObj.put(sysUserLabels[ INDEX_SYS_USER_ID], sysUser.mSysUserId );
			jsonObj.put(sysUserLabels[ INDEX_ROLE_ID], sysUser.mRoleId );
			jsonObj.put(sysUserLabels[ INDEX_NAME], sysUser.mName );
			jsonObj.put(sysUserLabels[ INDEX_MOBILE_NUMBER], sysUser.mMobileNumber );
			jsonObj.put(sysUserLabels[ INDEX_MOBILE_NUMBER_2], sysUser.mMobileNumber2 );
			jsonObj.put(sysUserLabels[ INDEX_EMAIL], sysUser.mEmail );
			jsonObj.put(sysUserLabels[ INDEX_ADDRESS], sysUser.mAddress );
			jsonObj.put(sysUserLabels[ INDEX_CITY_ID], sysUser.mCityId );
			jsonObj.put(sysUserLabels[ INDEX_COUNTRY_ID], sysUser.mCountryId );
			jsonObj.put(sysUserLabels[ INDEX_DESIGNATION_LOOKUP_ID], sysUser.mDesignationLookupId );
			jsonObj.put(sysUserLabels[ INDEX_LATITUDE], sysUser.mLatitude );
			jsonObj.put(sysUserLabels[ INDEX_LONGITUDE], sysUser.mLongitude );
			jsonObj.put(sysUserLabels[ INDEX_GENDER_LOOKUP_ID], sysUser.mGenderLookupId );
			jsonObj.put(sysUserLabels[ INDEX_DATE_OF_BIRTH], sysUser.mDateOfBirth );
			jsonObj.put(sysUserLabels[ INDEX_DRIVING_LICENSE_NUMBER], sysUser.mDrivingLicenseNumber );
			jsonObj.put(sysUserLabels[ INDEX_PASSPORT_NUMBER], sysUser.mPassportNumber );
			jsonObj.put(sysUserLabels[ INDEX_REMARKS], sysUser.mRemarks );
			jsonObj.put(sysUserLabels[ INDEX_PHOTO], sysUser.mPhoto );
			jsonObj.put(sysUserLabels[ INDEX_DATE_OF_REGISTRATION], sysUser.mDateOfRegistration );
			jsonObj.put(sysUserLabels[ INDEX_LAST_LOGIN_DATE], sysUser.mLastLoginDate );
			jsonObj.put(sysUserLabels[ INDEX_NO_OF_LOGINS], sysUser.mNoOfLogins );
			jsonObj.put(sysUserLabels[ INDEX_CREATE_BY_SYS_USER_ID], sysUser.mCreateBySysUserId );
			jsonObj.put(sysUserLabels[ INDEX_ACTIVE_LOOKUP_ID], sysUser.mActiveLookupId );
			jsonObj.put(sysUserLabels[ INDEX_PARKING_AREA_ID], sysUser.mParkingAreaId );
			jsonObj.put(sysUserLabels[ INDEX_LOGGED_IN_PARKING_AREA_GATE_ID], sysUser.mLoggedInParkingAreaGateId );
			jsonObj.put(sysUserLabels[ INDEX_STATUS], sysUser.mStatus );
			jsonObj.put(sysUserLabels[ INDEX_PASSWORD], sysUser.mPassword );
			jsonObj.put(sysUserLabels[ INDEX_SALT], sysUser.mSalt );
			jsonObj.put(sysUserLabels[ INDEX_EXTRA_DATA], sysUser.mExtraData );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static SysUser jsonObjectToObject( String jsonObjectString ) {
		SysUser sysUser = new SysUser();
		JSONObject data;
		String[] sysUserLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			sysUser.mSysUserId = data.getInt(sysUserLabels[INDEX_SYS_USER_ID]);
			sysUser.mRoleId = (short)data.getInt(sysUserLabels[INDEX_ROLE_ID]);
			sysUser.mName = data.getString(sysUserLabels[INDEX_NAME]);
			sysUser.mMobileNumber = data.getString(sysUserLabels[INDEX_MOBILE_NUMBER]);
			sysUser.mMobileNumber2 = data.getString(sysUserLabels[INDEX_MOBILE_NUMBER_2]);
			sysUser.mEmail = data.getString(sysUserLabels[INDEX_EMAIL]);
			sysUser.mAddress = data.getString(sysUserLabels[INDEX_ADDRESS]);
			sysUser.mCityId = data.getInt(sysUserLabels[INDEX_CITY_ID]);
			sysUser.mCountryId = data.getInt(sysUserLabels[INDEX_COUNTRY_ID]);
			sysUser.mDesignationLookupId = data.getInt(sysUserLabels[INDEX_DESIGNATION_LOOKUP_ID]);
			sysUser.mLatitude = (float)data.getDouble(sysUserLabels[INDEX_LATITUDE]);
			sysUser.mLongitude = (float)data.getDouble(sysUserLabels[INDEX_LONGITUDE]);
			sysUser.mGenderLookupId = data.getInt(sysUserLabels[INDEX_GENDER_LOOKUP_ID]);
			sysUser.mDateOfBirth = data.getLong(sysUserLabels[INDEX_DATE_OF_BIRTH]);
			sysUser.mDrivingLicenseNumber = data.getString(sysUserLabels[INDEX_DRIVING_LICENSE_NUMBER]);
			sysUser.mPassportNumber = data.getString(sysUserLabels[INDEX_PASSPORT_NUMBER]);
			sysUser.mRemarks = data.getString(sysUserLabels[INDEX_REMARKS]);
			sysUser.mPhoto = data.getString(sysUserLabels[INDEX_PHOTO]);
			sysUser.mDateOfRegistration = data.getLong(sysUserLabels[INDEX_DATE_OF_REGISTRATION]);
			sysUser.mLastLoginDate = data.getLong(sysUserLabels[INDEX_LAST_LOGIN_DATE]);
			sysUser.mNoOfLogins = data.getInt(sysUserLabels[INDEX_NO_OF_LOGINS]);
			sysUser.mCreateBySysUserId = data.getInt(sysUserLabels[INDEX_CREATE_BY_SYS_USER_ID]);
			sysUser.mActiveLookupId = data.getInt(sysUserLabels[INDEX_ACTIVE_LOOKUP_ID]);
			sysUser.mParkingAreaId = data.getInt(sysUserLabels[INDEX_PARKING_AREA_ID]);
			sysUser.mLoggedInParkingAreaGateId = data.getInt(sysUserLabels[INDEX_LOGGED_IN_PARKING_AREA_GATE_ID]);
			sysUser.mStatus = data.getInt(sysUserLabels[INDEX_STATUS]);
			sysUser.mPassword = data.getString(sysUserLabels[INDEX_PASSWORD]);
			sysUser.mSalt = data.getString(sysUserLabels[INDEX_SALT]);
			sysUser.mExtraData = data.getString(sysUserLabels[INDEX_EXTRA_DATA]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return sysUser;

	}

	public static ArrayList<SysUser> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<SysUser> objList = new ArrayList<SysUser>();
		try {

			JSONArray sysUserJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < sysUserJSONArray.length(); i++) {

				JSONObject sysUserJsonObject = sysUserJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(sysUserJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
