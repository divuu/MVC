////////////////////////////////////////////////////////////////////////////

// FileName LookupGroup.java: LookupGroup Implementation file

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


public class LookupGroup  {

	//public static final String CREATE_LOOKUPGROUP_TABLE = "CREATE TABLE lookup_group (lookup_group_id INTEGER, lookup_group_name TEXT)";

	public final static String DATABASE_TABLE_NAME = "lookup_group";


//MySQL Script
	//CREATE TABLE lookup_group( lookup_group_id int, lookup_group_name varchar(256) );
	public static final String KEY_LOOKUP_GROUP_ID = "lookup_group_id";
	public static final String KEY_LOOKUP_GROUP_NAME = "lookup_group_name";

	public static final String LABEL_LOOKUP_GROUP_ID = "Lookup Group Id";
	public static final String LABEL_LOOKUP_GROUP_NAME = "Lookup Group Name";

	public static final int INDEX_LOOKUP_GROUP_ID = 0;
	public static final int INDEX_LOOKUP_GROUP_NAME = 1;

	public final static int NUM_OF_COLUMNS = 2;

	public int mLookupGroupId;
	public String mLookupGroupName;		//Group to which this lookup belongs e.g. Division, Gender, Type of Employment

	public LookupGroup() {
		mLookupGroupId = 0;
		mLookupGroupName = null;
	}

/*
	public void printData() {

		MLogger.i( MLogger.MOD_DB, "lookup_group_id " + mLookupGroupId );
		MLogger.i( MLogger.MOD_DB, "lookup_group_name " + mLookupGroupName );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_LOOKUP_GROUP_ID);
		tableHeader.add(LABEL_LOOKUP_GROUP_NAME);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<LookupGroup> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (LookupGroup obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mLookupGroupId));
			strList.put(obj.mLookupGroupName == null?"":obj.mLookupGroupName);
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "LkpGrpD", "LkpGrpNm"};
		String[] keys = { "mLookupGroupId", "mLookupGroupName"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<LookupGroup> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (LookupGroup obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<LookupGroup> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(LookupGroup obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mLookupGroupId));
			strList.add(obj.mLookupGroupName);

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(LookupGroup obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mLookupGroupId));
		strList.add(obj.mLookupGroupName);

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< LookupGroup > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(LookupGroup obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mLookupGroupId));
			strList.add(String.valueOf(obj.mLookupGroupName));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( LookupGroup obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mLookupGroupId));
		strList.add(String.valueOf(obj.mLookupGroupName));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(LookupGroup lookupGroup) {
		JSONObject jsonObj = new JSONObject();
		String[] lookupGroupLabels = getmKeys();
		try {
			jsonObj.put(lookupGroupLabels[ INDEX_LOOKUP_GROUP_ID], lookupGroup.mLookupGroupId );
			jsonObj.put(lookupGroupLabels[ INDEX_LOOKUP_GROUP_NAME], lookupGroup.mLookupGroupName );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static LookupGroup jsonObjectToObject( String jsonObjectString ) {
		LookupGroup lookupGroup = new LookupGroup();
		JSONObject data;
		String[] lookupGroupLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			lookupGroup.mLookupGroupId = data.getInt(lookupGroupLabels[INDEX_LOOKUP_GROUP_ID]);
			lookupGroup.mLookupGroupName = data.getString(lookupGroupLabels[INDEX_LOOKUP_GROUP_NAME]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return lookupGroup;

	}

	public static ArrayList<LookupGroup> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<LookupGroup> objList = new ArrayList<LookupGroup>();
		try {

			JSONArray lookupGroupJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < lookupGroupJSONArray.length(); i++) {

				JSONObject lookupGroupJsonObject = lookupGroupJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(lookupGroupJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
