////////////////////////////////////////////////////////////////////////////

// FileName Country.java: Country Implementation file

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


public class Country  {

	//public static final String CREATE_COUNTRY_TABLE = "CREATE TABLE country (country_id INTEGER, country TEXT, country_code TEXT, no_of_digits INTEGER)";

	public final static String DATABASE_TABLE_NAME = "country";


//MySQL Script
	//CREATE TABLE country( country_id smallint, country varchar(64), country_code varchar(16), no_of_digits int );
	public static final String KEY_COUNTRY_ID = "country_id";
	public static final String KEY_COUNTRY = "country";
	public static final String KEY_COUNTRY_CODE = "country_code";
	public static final String KEY_NO_OF_DIGITS = "no_of_digits";

	public static final String LABEL_COUNTRY_ID = "ID";//"Country Id";
	public static final String LABEL_COUNTRY = "Country";
	public static final String LABEL_COUNTRY_CODE = "Country Code";
	public static final String LABEL_NO_OF_DIGITS = "No Of Digits";

	public static final int INDEX_COUNTRY_ID = 0;
	public static final int INDEX_COUNTRY = 1;
	public static final int INDEX_COUNTRY_CODE = 2;
	public static final int INDEX_NO_OF_DIGITS = 3;

	public final static int NUM_OF_COLUMNS = 4;

	public short mCountryId;
	public String mCountry;
	public String mCountryCode;		//Telco Code
	public int mNoOfDigits;		//No of digits allowed for the mobile nos for this country

	public Country() {
		mCountryId = 0;
		mCountry = null;
		mCountryCode = null;
		mNoOfDigits = 0;
	}

/*
	public void printData() {

		MLogger.i( MLogger.MOD_DB, "country_id " + mCountryId );
		MLogger.i( MLogger.MOD_DB, "country " + mCountry );
		MLogger.i( MLogger.MOD_DB, "country_code " + mCountryCode );
		MLogger.i( MLogger.MOD_DB, "no_of_digits " + mNoOfDigits );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_COUNTRY_ID);
		tableHeader.add(LABEL_COUNTRY);
		tableHeader.add(LABEL_COUNTRY_CODE);
		tableHeader.add(LABEL_NO_OF_DIGITS);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<Country> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (Country obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mCountryId));
			strList.put(obj.mCountry == null?"":obj.mCountry);
			strList.put(obj.mCountryCode == null?"":obj.mCountryCode);
			strList.put(String.valueOf(obj.mNoOfDigits));
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "CntryD", "Cntry", "CntryCd", "NFDgts"};
		String[] keys = { "mCountryId", "mCountry", "mCountryCode", "mNoOfDigits"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<Country> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (Country obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<Country> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(Country obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mCountryId));
			strList.add(obj.mCountry);
			strList.add(obj.mCountryCode);
			strList.add(String.valueOf(obj.mNoOfDigits));

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(Country obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mCountryId));
		strList.add(obj.mCountry);
		strList.add(obj.mCountryCode);
		strList.add(String.valueOf(obj.mNoOfDigits));

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< Country > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(Country obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mCountryId));
			strList.add(String.valueOf(obj.mCountry));
			strList.add(String.valueOf(obj.mCountryCode));
			strList.add(String.valueOf(obj.mNoOfDigits));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( Country obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mCountryId));
		strList.add(String.valueOf(obj.mCountry));
		strList.add(String.valueOf(obj.mCountryCode));
		strList.add(String.valueOf(obj.mNoOfDigits));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(Country country) {
		JSONObject jsonObj = new JSONObject();
		String[] countryLabels = getmKeys();
		try {
			jsonObj.put(countryLabels[ INDEX_COUNTRY_ID], country.mCountryId );
			jsonObj.put(countryLabels[ INDEX_COUNTRY], country.mCountry );
			jsonObj.put(countryLabels[ INDEX_COUNTRY_CODE], country.mCountryCode );
			jsonObj.put(countryLabels[ INDEX_NO_OF_DIGITS], country.mNoOfDigits );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static Country jsonObjectToObject( String jsonObjectString ) {
		Country country = new Country();
		JSONObject data;
		String[] countryLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			country.mCountryId = (short)data.getInt(countryLabels[INDEX_COUNTRY_ID]);
			country.mCountry = data.getString(countryLabels[INDEX_COUNTRY]);
			country.mCountryCode = data.getString(countryLabels[INDEX_COUNTRY_CODE]);
			country.mNoOfDigits = data.getInt(countryLabels[INDEX_NO_OF_DIGITS]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return country;

	}

	public static ArrayList<Country> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<Country> objList = new ArrayList<Country>();
		try {

			JSONArray countryJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < countryJSONArray.length(); i++) {

				JSONObject countryJsonObject = countryJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(countryJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
