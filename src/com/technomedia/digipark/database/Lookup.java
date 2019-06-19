////////////////////////////////////////////////////////////////////////////

// FileName Lookup.java: Lookup Implementation file

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


public class Lookup  {

	//public static final String CREATE_LOOKUP_TABLE = "CREATE TABLE lookup (lookup_id INTEGER, lookup_group_id INTEGER, table_name TEXT, column_name TEXT, lookup_name TEXT, lookup_value INTEGER)";

	public final static String DATABASE_TABLE_NAME = "lookup";


//MySQL Script
	//CREATE TABLE lookup( lookup_id int, lookup_group_id int, table_name varchar(128), column_name varchar(128), lookup_name varchar(256), lookup_value int );
	public static final String KEY_LOOKUP_ID = "lookup_id";
	public static final String KEY_LOOKUP_GROUP_ID = "lookup_group_id";
	public static final String KEY_TABLE_NAME = "table_name";
	public static final String KEY_COLUMN_NAME = "column_name";
	public static final String KEY_LOOKUP_NAME = "lookup_name";
	public static final String KEY_LOOKUP_VALUE = "lookup_value";

	public static final String LABEL_LOOKUP_ID = "Lookup Id";
	public static final String LABEL_LOOKUP_GROUP_ID = "Lookup Group";
	public static final String LABEL_TABLE_NAME = "Table Name";
	public static final String LABEL_COLUMN_NAME = "Column Name";
	public static final String LABEL_LOOKUP_NAME = "Lookup Name";
	public static final String LABEL_LOOKUP_VALUE = "Lookup Value";

	public static final int INDEX_LOOKUP_ID = 0;
	public static final int INDEX_LOOKUP_GROUP_ID = 1;
	public static final int INDEX_TABLE_NAME = 2;
	public static final int INDEX_COLUMN_NAME = 3;
	public static final int INDEX_LOOKUP_NAME = 4;
	public static final int INDEX_LOOKUP_VALUE = 5;

	public final static int NUM_OF_COLUMNS = 6;

	public int mLookupId;		//Unique Id for the lookup
	public int mLookupGroupId;		//Group Id: Group to which this lookup belongs e.g. Division, Gender, Type of Employment
	public String mTableName;		//For a table name will be the same. 
	public String mColumnName;		//Type of lookup can be a key word like feature, operation etc.
	public String mLookupName;		//A name for the lookup value.
	public int mLookupValue;		//An integer value for lookup

	public Lookup() {
		mLookupId = 0;
		mLookupGroupId = 0;
		mTableName = null;
		mColumnName = null;
		mLookupName = null;
		mLookupValue = 0;
	}

/*
	public void printData() {

		MLogger.i( MLogger.MOD_DB, "lookup_id " + mLookupId );
		MLogger.i( MLogger.MOD_DB, "lookup_group_id " + mLookupGroupId );
		MLogger.i( MLogger.MOD_DB, "table_name " + mTableName );
		MLogger.i( MLogger.MOD_DB, "column_name " + mColumnName );
		MLogger.i( MLogger.MOD_DB, "lookup_name " + mLookupName );
		MLogger.i( MLogger.MOD_DB, "lookup_value " + mLookupValue );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_LOOKUP_ID);
		tableHeader.add(LABEL_LOOKUP_GROUP_ID);
		tableHeader.add(LABEL_TABLE_NAME);
		tableHeader.add(LABEL_COLUMN_NAME);
		tableHeader.add(LABEL_LOOKUP_NAME);
		tableHeader.add(LABEL_LOOKUP_VALUE);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<Lookup> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (Lookup obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mLookupId));
			strList.put(String.valueOf(obj.mLookupGroupId));
			strList.put(obj.mTableName == null?"":obj.mTableName);
			strList.put(obj.mColumnName == null?"":obj.mColumnName);
			strList.put(obj.mLookupName == null?"":obj.mLookupName);
			strList.put(String.valueOf(obj.mLookupValue));
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "LkpD", "LkpGrpD", "TblNm", "ClmnM", "LkpNm", "LkpVl"};
		String[] keys = { "mLookupId", "mLookupGroupId", "mTableName", "mColumnName", "mLookupName", "mLookupValue"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<Lookup> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (Lookup obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<Lookup> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(Lookup obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mLookupId));
			strList.add(String.valueOf(obj.mLookupGroupId));
			strList.add(obj.mTableName);
			strList.add(obj.mColumnName);
			strList.add(obj.mLookupName);
			strList.add(String.valueOf(obj.mLookupValue));

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(Lookup obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mLookupId));
		strList.add(String.valueOf(obj.mLookupGroupId));
		strList.add(obj.mTableName);
		strList.add(obj.mColumnName);
		strList.add(obj.mLookupName);
		strList.add(String.valueOf(obj.mLookupValue));

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< Lookup > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(Lookup obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mLookupId));
			strList.add(String.valueOf(obj.mLookupGroupId));
			strList.add(String.valueOf(obj.mTableName));
			strList.add(String.valueOf(obj.mColumnName));
			strList.add(String.valueOf(obj.mLookupName));
			strList.add(String.valueOf(obj.mLookupValue));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( Lookup obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mLookupId));
		strList.add(String.valueOf(obj.mLookupGroupId));
		strList.add(String.valueOf(obj.mTableName));
		strList.add(String.valueOf(obj.mColumnName));
		strList.add(String.valueOf(obj.mLookupName));
		strList.add(String.valueOf(obj.mLookupValue));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(Lookup lookup) {
		JSONObject jsonObj = new JSONObject();
		String[] lookupLabels = getmKeys();
		try {
			jsonObj.put(lookupLabels[ INDEX_LOOKUP_ID], lookup.mLookupId );
			jsonObj.put(lookupLabels[ INDEX_LOOKUP_GROUP_ID], lookup.mLookupGroupId );
			jsonObj.put(lookupLabels[ INDEX_TABLE_NAME], lookup.mTableName );
			jsonObj.put(lookupLabels[ INDEX_COLUMN_NAME], lookup.mColumnName );
			jsonObj.put(lookupLabels[ INDEX_LOOKUP_NAME], lookup.mLookupName );
			jsonObj.put(lookupLabels[ INDEX_LOOKUP_VALUE], lookup.mLookupValue );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static Lookup jsonObjectToObject( String jsonObjectString ) {
		Lookup lookup = new Lookup();
		JSONObject data;
		String[] lookupLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			lookup.mLookupId = data.getInt(lookupLabels[INDEX_LOOKUP_ID]);
			lookup.mLookupGroupId = data.getInt(lookupLabels[INDEX_LOOKUP_GROUP_ID]);
			lookup.mTableName = data.getString(lookupLabels[INDEX_TABLE_NAME]);
			lookup.mColumnName = data.getString(lookupLabels[INDEX_COLUMN_NAME]);
			lookup.mLookupName = data.getString(lookupLabels[INDEX_LOOKUP_NAME]);
			lookup.mLookupValue = data.getInt(lookupLabels[INDEX_LOOKUP_VALUE]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return lookup;

	}

	public static ArrayList<Lookup> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<Lookup> objList = new ArrayList<Lookup>();
		try {

			JSONArray lookupJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < lookupJSONArray.length(); i++) {

				JSONObject lookupJsonObject = lookupJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(lookupJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
