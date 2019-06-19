////////////////////////////////////////////////////////////////////////////

// FileName Subscriber.java: Subscriber Implementation file

// Author : Vinu | Utkarsh | JRC
// Description : Slash Digital TVAS v1.0


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


public class Subscriber  {

	//public static final String CREATE_SUBSCRIBER_TABLE = "CREATE TABLE subscriber (subscriber_id INTEGER, name TEXT, mobile_number TEXT, email TEXT, gender TEXT, date_of_birth INTEGER, date_of_registration INTEGER, plan_id INTEGER, plan_start_date INTEGER, plan_end_date INTEGER, amount REAL, amount_paid REAL, discount_amount REAL, active_lookup_id INTEGER, status INTEGER, remarks TEXT, extra_data TEXT)";

	public final static String DATABASE_TABLE_NAME = "subscriber";


//MySQL Script
	//CREATE TABLE subscriber( subscriber_id int, name varchar(128), mobile_number varchar(16), email varchar(128), gender char(1), date_of_birth date, date_of_registration datetime, plan_id int, plan_start_date datetime, plan_end_date datetime, amount float, amount_paid float, discount_amount float, active_lookup_id int, status int, remarks text, extra_data text );
	public static final String KEY_SUBSCRIBER_ID = "subscriber_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_MOBILE_NUMBER = "mobile_number";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_DATE_OF_BIRTH = "date_of_birth";
	public static final String KEY_DATE_OF_REGISTRATION = "date_of_registration";
	public static final String KEY_PLAN_ID = "plan_id";
	public static final String KEY_PLAN_START_DATE = "plan_start_date";
	public static final String KEY_PLAN_END_DATE = "plan_end_date";
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_AMOUNT_PAID = "amount_paid";
	public static final String KEY_DISCOUNT_AMOUNT = "discount_amount";
	public static final String KEY_ACTIVE_LOOKUP_ID = "active_lookup_id";
	public static final String KEY_STATUS = "status";
	public static final String KEY_REMARKS = "remarks";
	public static final String KEY_EXTRA_DATA = "extra_data";

	public static final String LABEL_SUBSCRIBER_ID = "Subscriber Id";
	public static final String LABEL_NAME = "Name";
	public static final String LABEL_MOBILE_NUMBER = "Mobile Number";
	public static final String LABEL_EMAIL = "Email";
	public static final String LABEL_GENDER = "Gender";
	public static final String LABEL_DATE_OF_BIRTH = "Date Of Birth";
	public static final String LABEL_DATE_OF_REGISTRATION = "Date Of Registration";
	public static final String LABEL_PLAN_ID = "Plan";
	public static final String LABEL_PLAN_START_DATE = "Plan Start Date";
	public static final String LABEL_PLAN_END_DATE = "Plan End Date";
	public static final String LABEL_AMOUNT = "Amount";
	public static final String LABEL_AMOUNT_PAID = "Amount Paid";
	public static final String LABEL_DISCOUNT_AMOUNT = "Discount Amount";
	public static final String LABEL_ACTIVE_LOOKUP_ID = "Active";
	public static final String LABEL_STATUS = "Status";
	public static final String LABEL_REMARKS = "Remarks";
	public static final String LABEL_EXTRA_DATA = "Extra Data";

	public static final int INDEX_SUBSCRIBER_ID = 0;
	public static final int INDEX_NAME = 1;
	public static final int INDEX_MOBILE_NUMBER = 2;
	public static final int INDEX_EMAIL = 3;
	public static final int INDEX_GENDER = 4;
	public static final int INDEX_DATE_OF_BIRTH = 5;
	public static final int INDEX_DATE_OF_REGISTRATION = 6;
	public static final int INDEX_PLAN_ID = 7;
	public static final int INDEX_PLAN_START_DATE = 8;
	public static final int INDEX_PLAN_END_DATE = 9;
	public static final int INDEX_AMOUNT = 10;
	public static final int INDEX_AMOUNT_PAID = 11;
	public static final int INDEX_DISCOUNT_AMOUNT = 12;
	public static final int INDEX_ACTIVE_LOOKUP_ID = 13;
	public static final int INDEX_STATUS = 14;
	public static final int INDEX_REMARKS = 15;
	public static final int INDEX_EXTRA_DATA = 16;

	public final static int NUM_OF_COLUMNS = 17;

	public int mSubscriberId;
	public String mName;
	public String mMobileNumber;
	public String mEmail;
	public char mGender;
	public long mDateOfBirth;
	public long mDateOfRegistration;
	public int mPlanId;
	public long mPlanStartDate;
	public long mPlanEndDate;
	public float mAmount;
	public float mAmountPaid;
	public float mDiscountAmount;
	public int mActiveLookupId;
	public int mStatus;
	public String mRemarks;
	public String mExtraData;

	public Subscriber() {
		mSubscriberId = 0;
		mName = null;
		mMobileNumber = null;
		mEmail = null;
		mGender = 0;
		mDateOfBirth = 0;
		mDateOfRegistration = 0;
		mPlanId = 0;
		mPlanStartDate = 0;
		mPlanEndDate = 0;
		mAmount = 0;
		mAmountPaid = 0;
		mDiscountAmount = 0;
		mActiveLookupId = 0;
		mStatus = 0;
		mRemarks = null;
		mExtraData = null;
	}

/*
	public void printData() {

		String DateFormat = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
		Date parsedDate;

		MLogger.i( MLogger.MOD_DB, "subscriber_id " + mSubscriberId );
		MLogger.i( MLogger.MOD_DB, "name " + mName );
		MLogger.i( MLogger.MOD_DB, "mobile_number " + mMobileNumber );
		MLogger.i( MLogger.MOD_DB, "email " + mEmail );
		MLogger.i( MLogger.MOD_DB, "gender " + mGender );
		parsedDate = new Date(mDateOfBirth );
		MLogger.i( MLogger.MOD_DB, "date_of_birth " + sdf.format(parsedDate) );
		parsedDate = new Date(mDateOfRegistration );
		MLogger.i( MLogger.MOD_DB, "date_of_registration " + sdf.format(parsedDate) );
		MLogger.i( MLogger.MOD_DB, "plan_id " + mPlanId );
		parsedDate = new Date(mPlanStartDate );
		MLogger.i( MLogger.MOD_DB, "plan_start_date " + sdf.format(parsedDate) );
		parsedDate = new Date(mPlanEndDate );
		MLogger.i( MLogger.MOD_DB, "plan_end_date " + sdf.format(parsedDate) );
		MLogger.i( MLogger.MOD_DB, "amount " + mAmount );
		MLogger.i( MLogger.MOD_DB, "amount_paid " + mAmountPaid );
		MLogger.i( MLogger.MOD_DB, "discount_amount " + mDiscountAmount );
		MLogger.i( MLogger.MOD_DB, "active_lookup_id " + mActiveLookupId );
		MLogger.i( MLogger.MOD_DB, "status " + mStatus );
		MLogger.i( MLogger.MOD_DB, "remarks " + mRemarks );
		MLogger.i( MLogger.MOD_DB, "extra_data " + mExtraData );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_SUBSCRIBER_ID);
		tableHeader.add(LABEL_NAME);
		tableHeader.add(LABEL_MOBILE_NUMBER);
		tableHeader.add(LABEL_EMAIL);
		tableHeader.add(LABEL_GENDER);
		tableHeader.add(LABEL_DATE_OF_BIRTH);
		tableHeader.add(LABEL_DATE_OF_REGISTRATION);
		tableHeader.add(LABEL_PLAN_ID);
		tableHeader.add(LABEL_PLAN_START_DATE);
		tableHeader.add(LABEL_PLAN_END_DATE);
		tableHeader.add(LABEL_AMOUNT);
		tableHeader.add(LABEL_AMOUNT_PAID);
		tableHeader.add(LABEL_DISCOUNT_AMOUNT);
		tableHeader.add(LABEL_ACTIVE_LOOKUP_ID);
		tableHeader.add(LABEL_STATUS);
		tableHeader.add(LABEL_REMARKS);
		tableHeader.add(LABEL_EXTRA_DATA);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<Subscriber> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (Subscriber obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mSubscriberId));
			strList.put(obj.mName == null?"":obj.mName);
			strList.put(obj.mMobileNumber == null?"":obj.mMobileNumber);
			strList.put(obj.mEmail == null?"":obj.mEmail);
			strList.put(String.valueOf(obj.mGender));
			strList.put(String.valueOf(obj.mDateOfBirth));
			strList.put(String.valueOf(obj.mDateOfRegistration));
			strList.put(String.valueOf(obj.mPlanId));
			strList.put(String.valueOf(obj.mPlanStartDate));
			strList.put(String.valueOf(obj.mPlanEndDate));
			strList.put(String.valueOf(obj.mAmount));
			strList.put(String.valueOf(obj.mAmountPaid));
			strList.put(String.valueOf(obj.mDiscountAmount));
			strList.put(String.valueOf(obj.mActiveLookupId));
			strList.put(String.valueOf(obj.mStatus));
			strList.put(obj.mRemarks == null?"":obj.mRemarks);
			strList.put(obj.mExtraData == null?"":obj.mExtraData);
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "SbscrbrD", "Nm", "MblNmbr", "Eml", "Gndr", "DtFBrth", "DtFRgstrtn", "PlnD", "PlnStrtDt", "PlnDT", "Amnt", "AmntPd", "DscntMnt", "ActvLkpD", "Sts", "Rmrks", "ExtrDt"};
		String[] keys = { "mSubscriberId", "mName", "mMobileNumber", "mEmail", "mGender", "mDateOfBirth", "mDateOfRegistration", "mPlanId", "mPlanStartDate", "mPlanEndDate", "mAmount", "mAmountPaid", "mDiscountAmount", "mActiveLookupId", "mStatus", "mRemarks", "mExtraData"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<Subscriber> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (Subscriber obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<Subscriber> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(Subscriber obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mSubscriberId));
			strList.add(obj.mName);
			strList.add(obj.mMobileNumber);
			strList.add(obj.mEmail);
			strList.add(String.valueOf(obj.mGender));
			strList.add(String.valueOf(obj.mDateOfBirth));
			strList.add(String.valueOf(obj.mDateOfRegistration));
			strList.add(String.valueOf(obj.mPlanId));
			strList.add(String.valueOf(obj.mPlanStartDate));
			strList.add(String.valueOf(obj.mPlanEndDate));
			strList.add(String.valueOf(obj.mAmount));
			strList.add(String.valueOf(obj.mAmountPaid));
			strList.add(String.valueOf(obj.mDiscountAmount));
			strList.add(String.valueOf(obj.mActiveLookupId));
			strList.add(String.valueOf(obj.mStatus));
			strList.add(obj.mRemarks);
			strList.add(obj.mExtraData);

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(Subscriber obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mSubscriberId));
		strList.add(obj.mName);
		strList.add(obj.mMobileNumber);
		strList.add(obj.mEmail);
		strList.add(String.valueOf(obj.mGender));
		strList.add(String.valueOf(obj.mDateOfBirth));
		strList.add(String.valueOf(obj.mDateOfRegistration));
		strList.add(String.valueOf(obj.mPlanId));
		strList.add(String.valueOf(obj.mPlanStartDate));
		strList.add(String.valueOf(obj.mPlanEndDate));
		strList.add(String.valueOf(obj.mAmount));
		strList.add(String.valueOf(obj.mAmountPaid));
		strList.add(String.valueOf(obj.mDiscountAmount));
		strList.add(String.valueOf(obj.mActiveLookupId));
		strList.add(String.valueOf(obj.mStatus));
		strList.add(obj.mRemarks);
		strList.add(obj.mExtraData);

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< Subscriber > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(Subscriber obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mSubscriberId));
			strList.add(String.valueOf(obj.mName));
			strList.add(String.valueOf(obj.mMobileNumber));
			strList.add(String.valueOf(obj.mEmail));
			strList.add(String.valueOf(obj.mGender));
			strList.add(String.valueOf(obj.mDateOfBirth));
			strList.add(String.valueOf(obj.mDateOfRegistration));
			strList.add(String.valueOf(obj.mPlanId));
			strList.add(String.valueOf(obj.mPlanStartDate));
			strList.add(String.valueOf(obj.mPlanEndDate));
			strList.add(String.valueOf(obj.mAmount));
			strList.add(String.valueOf(obj.mAmountPaid));
			strList.add(String.valueOf(obj.mDiscountAmount));
			strList.add(String.valueOf(obj.mActiveLookupId));
			strList.add(String.valueOf(obj.mStatus));
			strList.add(String.valueOf(obj.mRemarks));
			strList.add(String.valueOf(obj.mExtraData));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( Subscriber obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mSubscriberId));
		strList.add(String.valueOf(obj.mName));
		strList.add(String.valueOf(obj.mMobileNumber));
		strList.add(String.valueOf(obj.mEmail));
		strList.add(String.valueOf(obj.mGender));
		strList.add(String.valueOf(obj.mDateOfBirth));
		strList.add(String.valueOf(obj.mDateOfRegistration));
		strList.add(String.valueOf(obj.mPlanId));
		strList.add(String.valueOf(obj.mPlanStartDate));
		strList.add(String.valueOf(obj.mPlanEndDate));
		strList.add(String.valueOf(obj.mAmount));
		strList.add(String.valueOf(obj.mAmountPaid));
		strList.add(String.valueOf(obj.mDiscountAmount));
		strList.add(String.valueOf(obj.mActiveLookupId));
		strList.add(String.valueOf(obj.mStatus));
		strList.add(String.valueOf(obj.mRemarks));
		strList.add(String.valueOf(obj.mExtraData));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(Subscriber subscriber) {
		JSONObject jsonObj = new JSONObject();
		String[] subscriberLabels = getmKeys();
		try {
			jsonObj.put(subscriberLabels[ INDEX_SUBSCRIBER_ID], subscriber.mSubscriberId );
			jsonObj.put(subscriberLabels[ INDEX_NAME], subscriber.mName );
			jsonObj.put(subscriberLabels[ INDEX_MOBILE_NUMBER], subscriber.mMobileNumber );
			jsonObj.put(subscriberLabels[ INDEX_EMAIL], subscriber.mEmail );
			jsonObj.put(subscriberLabels[ INDEX_GENDER], subscriber.mGender );
			jsonObj.put(subscriberLabels[ INDEX_DATE_OF_BIRTH], subscriber.mDateOfBirth );
			jsonObj.put(subscriberLabels[ INDEX_DATE_OF_REGISTRATION], subscriber.mDateOfRegistration );
			jsonObj.put(subscriberLabels[ INDEX_PLAN_ID], subscriber.mPlanId );
			jsonObj.put(subscriberLabels[ INDEX_PLAN_START_DATE], subscriber.mPlanStartDate );
			jsonObj.put(subscriberLabels[ INDEX_PLAN_END_DATE], subscriber.mPlanEndDate );
			jsonObj.put(subscriberLabels[ INDEX_AMOUNT], subscriber.mAmount );
			jsonObj.put(subscriberLabels[ INDEX_AMOUNT_PAID], subscriber.mAmountPaid );
			jsonObj.put(subscriberLabels[ INDEX_DISCOUNT_AMOUNT], subscriber.mDiscountAmount );
			jsonObj.put(subscriberLabels[ INDEX_ACTIVE_LOOKUP_ID], subscriber.mActiveLookupId );
			jsonObj.put(subscriberLabels[ INDEX_STATUS], subscriber.mStatus );
			jsonObj.put(subscriberLabels[ INDEX_REMARKS], subscriber.mRemarks );
			jsonObj.put(subscriberLabels[ INDEX_EXTRA_DATA], subscriber.mExtraData );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static Subscriber jsonObjectToObject( String jsonObjectString ) {
		Subscriber subscriber = new Subscriber();
		JSONObject data;
		String[] subscriberLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			subscriber.mSubscriberId = data.getInt(subscriberLabels[INDEX_SUBSCRIBER_ID]);
			subscriber.mName = data.getString(subscriberLabels[INDEX_NAME]);
			subscriber.mMobileNumber = data.getString(subscriberLabels[INDEX_MOBILE_NUMBER]);
			subscriber.mEmail = data.getString(subscriberLabels[INDEX_EMAIL]);
			//subscriber.mGender = data.getString(subscriberLabels[INDEX_GENDER]);
			subscriber.mDateOfBirth = data.getLong(subscriberLabels[INDEX_DATE_OF_BIRTH]);
			subscriber.mDateOfRegistration = data.getLong(subscriberLabels[INDEX_DATE_OF_REGISTRATION]);
			subscriber.mPlanId = data.getInt(subscriberLabels[INDEX_PLAN_ID]);
			subscriber.mPlanStartDate = data.getLong(subscriberLabels[INDEX_PLAN_START_DATE]);
			subscriber.mPlanEndDate = data.getLong(subscriberLabels[INDEX_PLAN_END_DATE]);
			subscriber.mAmount = (float)data.getDouble(subscriberLabels[INDEX_AMOUNT]);
			subscriber.mAmountPaid = (float)data.getDouble(subscriberLabels[INDEX_AMOUNT_PAID]);
			subscriber.mDiscountAmount = (float)data.getDouble(subscriberLabels[INDEX_DISCOUNT_AMOUNT]);
			subscriber.mActiveLookupId = data.getInt(subscriberLabels[INDEX_ACTIVE_LOOKUP_ID]);
			subscriber.mStatus = data.getInt(subscriberLabels[INDEX_STATUS]);
			subscriber.mRemarks = data.getString(subscriberLabels[INDEX_REMARKS]);
			subscriber.mExtraData = data.getString(subscriberLabels[INDEX_EXTRA_DATA]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return subscriber;

	}

	public static ArrayList<Subscriber> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<Subscriber> objList = new ArrayList<Subscriber>();
		try {

			JSONArray subscriberJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < subscriberJSONArray.length(); i++) {

				JSONObject subscriberJsonObject = subscriberJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(subscriberJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
