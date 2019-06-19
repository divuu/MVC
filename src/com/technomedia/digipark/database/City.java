

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


public class City  {

	//public static final String CREATE_CITY_TABLE = "CREATE TABLE city (city_id INTEGER, city TEXT, city_area_code TEXT, time_zone TEXT, state_lookup_id INTEGER, country_id INTEGER)";

	public final static String DATABASE_TABLE_NAME = "city";


//MySQL Script
	//CREATE TABLE city( city_id int, city varchar(64), city_area_code varchar(16), time_zone varchar(64), state_lookup_id int, country_id int );
	public static final String KEY_CITY_ID = "city_id";
	public static final String KEY_CITY = "city";
	public static final String KEY_CITY_AREA_CODE = "city_area_code";
	public static final String KEY_TIME_ZONE = "time_zone";
	public static final String KEY_STATE_LOOKUP_ID = "state_lookup_id";
	public static final String KEY_COUNTRY_ID = "country_id";

	public static final String LABEL_CITY_ID ="ID";//"City Id";
	public static final String LABEL_CITY = "City";
	public static final String LABEL_CITY_AREA_CODE = "City Area Code";
	public static final String LABEL_TIME_ZONE = "Time Zone";
	public static final String LABEL_STATE_LOOKUP_ID = "State";
	public static final String LABEL_COUNTRY_ID = "Country";

	public static final int INDEX_CITY_ID = 0;
	public static final int INDEX_CITY = 1;
	public static final int INDEX_CITY_AREA_CODE = 2;
	public static final int INDEX_TIME_ZONE = 3;
	public static final int INDEX_STATE_LOOKUP_ID = 4;
	public static final int INDEX_COUNTRY_ID = 5;

	public final static int NUM_OF_COLUMNS = 6;

	public int mCityId;		//Unique
	public String mCity;
	public String mCityAreaCode;		//Area / STD Code
	public String mTimeZone;		//Name of the time zone
	public int mStateLookupId;
	public int mCountryId;

	public City() {
		mCityId = 0;
		mCity = null;
		mCityAreaCode = null;
		mTimeZone = null;
		mStateLookupId = 0;
		mCountryId = 0;
	}

/*
	public void printData() {

		MLogger.i( MLogger.MOD_DB, "city_id " + mCityId );
		MLogger.i( MLogger.MOD_DB, "city " + mCity );
		MLogger.i( MLogger.MOD_DB, "city_area_code " + mCityAreaCode );
		MLogger.i( MLogger.MOD_DB, "time_zone " + mTimeZone );
		MLogger.i( MLogger.MOD_DB, "state_lookup_id " + mStateLookupId );
		MLogger.i( MLogger.MOD_DB, "country_id " + mCountryId );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_CITY_ID);
		tableHeader.add(LABEL_CITY);
		tableHeader.add(LABEL_CITY_AREA_CODE);
		tableHeader.add(LABEL_TIME_ZONE);
		tableHeader.add(LABEL_STATE_LOOKUP_ID);
		tableHeader.add(LABEL_COUNTRY_ID);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<City> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (City obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mCityId));
			strList.put(obj.mCity == null?"":obj.mCity);
			strList.put(obj.mCityAreaCode == null?"":obj.mCityAreaCode);
			strList.put(obj.mTimeZone == null?"":obj.mTimeZone);
			strList.put(String.valueOf(obj.mStateLookupId));
			strList.put(String.valueOf(obj.mCountryId));
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "CtyD", "Cty", "CtyRCd", "TmZn", "StLkpD", "CntryD"};
		String[] keys = { "mCityId", "mCity", "mCityAreaCode", "mTimeZone", "mStateLookupId", "mCountryId"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<City> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (City obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<City> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(City obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mCityId));
			strList.add(obj.mCity);
			strList.add(obj.mCityAreaCode);
			strList.add(obj.mTimeZone);
			strList.add(String.valueOf(obj.mStateLookupId));
			strList.add(String.valueOf(obj.mCountryId));

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(City obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mCityId));
		strList.add(obj.mCity);
		strList.add(obj.mCityAreaCode);
		strList.add(obj.mTimeZone);
		strList.add(String.valueOf(obj.mStateLookupId));
		strList.add(String.valueOf(obj.mCountryId));

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< City > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(City obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mCityId));
			strList.add(String.valueOf(obj.mCity));
			strList.add(String.valueOf(obj.mCityAreaCode));
			strList.add(String.valueOf(obj.mTimeZone));
			strList.add(String.valueOf(obj.mStateLookupId));
			strList.add(String.valueOf(obj.mCountryId));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( City obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mCityId));
		strList.add(String.valueOf(obj.mCity));
		strList.add(String.valueOf(obj.mCityAreaCode));
		strList.add(String.valueOf(obj.mTimeZone));
		strList.add(String.valueOf(obj.mStateLookupId));
		strList.add(String.valueOf(obj.mCountryId));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(City city) {
		JSONObject jsonObj = new JSONObject();
		String[] cityLabels = getmKeys();
		try {
			jsonObj.put(cityLabels[ INDEX_CITY_ID], city.mCityId );
			jsonObj.put(cityLabels[ INDEX_CITY], city.mCity );
			jsonObj.put(cityLabels[ INDEX_CITY_AREA_CODE], city.mCityAreaCode );
			jsonObj.put(cityLabels[ INDEX_TIME_ZONE], city.mTimeZone );
			jsonObj.put(cityLabels[ INDEX_STATE_LOOKUP_ID], city.mStateLookupId );
			jsonObj.put(cityLabels[ INDEX_COUNTRY_ID], city.mCountryId );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static City jsonObjectToObject( String jsonObjectString ) {
		City city = new City();
		JSONObject data;
		String[] cityLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			city.mCityId = data.getInt(cityLabels[INDEX_CITY_ID]);
			city.mCity = data.getString(cityLabels[INDEX_CITY]);
			city.mCityAreaCode = data.getString(cityLabels[INDEX_CITY_AREA_CODE]);
			city.mTimeZone = data.getString(cityLabels[INDEX_TIME_ZONE]);
			city.mStateLookupId = data.getInt(cityLabels[INDEX_STATE_LOOKUP_ID]);
			city.mCountryId = data.getInt(cityLabels[INDEX_COUNTRY_ID]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return city;

	}

	public static ArrayList<City> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<City> objList = new ArrayList<City>();
		try {

			JSONArray cityJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < cityJSONArray.length(); i++) {

				JSONObject cityJsonObject = cityJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(cityJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
