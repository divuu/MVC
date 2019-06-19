////////////////////////////////////////////////////////////////////////////

// FileName OrganizationSysUserMap.java: OrganizationSysUserMap Implementation file

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


public class OrganizationSysUserMap  {

	//public static final String CREATE_ORGANIZATIONSYSUSERMAP_TABLE = "CREATE TABLE organization_sys_user_map (sys_user_id INTEGER, organization_id INTEGER, role_id INTEGER)";

	public final static String DATABASE_TABLE_NAME = "organization_sys_user_map";


//MySQL Script
	//CREATE TABLE organization_sys_user_map( sys_user_id smallint, organization_id int, role_id smallint );
	public static final String KEY_SYS_USER_ID = "sys_user_id";
	public static final String KEY_ORGANIZATION_ID = "organization_id";
	public static final String KEY_ROLE_ID = "role_id";

	public static final String LABEL_SYS_USER_ID = "Sys User Id";
	public static final String LABEL_ORGANIZATION_ID = "Organization";
	public static final String LABEL_ROLE_ID = "Role";

	public static final int INDEX_SYS_USER_ID = 0;
	public static final int INDEX_ORGANIZATION_ID = 1;
	public static final int INDEX_ROLE_ID = 2;

	public final static int NUM_OF_COLUMNS = 3;

	public short mSysUserId;
	public int mOrganizationId;		//Incase entity applies for all, this field will be -1
	public short mRoleId;

	public OrganizationSysUserMap() {
		mSysUserId = 0;
		mOrganizationId = 0;
		mRoleId = 0;
	}

/*
	public void printData() {

		MLogger.i( MLogger.MOD_DB, "sys_user_id " + mSysUserId );
		MLogger.i( MLogger.MOD_DB, "organization_id " + mOrganizationId );
		MLogger.i( MLogger.MOD_DB, "role_id " + mRoleId );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_SYS_USER_ID);
		tableHeader.add(LABEL_ORGANIZATION_ID);
		tableHeader.add(LABEL_ROLE_ID);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<OrganizationSysUserMap> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (OrganizationSysUserMap obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mSysUserId));
			strList.put(String.valueOf(obj.mOrganizationId));
			strList.put(String.valueOf(obj.mRoleId));
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "SysRD", "OrgnztnD", "RlD"};
		String[] keys = { "mSysUserId", "mOrganizationId", "mRoleId"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<OrganizationSysUserMap> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (OrganizationSysUserMap obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<OrganizationSysUserMap> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(OrganizationSysUserMap obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mSysUserId));
			strList.add(String.valueOf(obj.mOrganizationId));
			strList.add(String.valueOf(obj.mRoleId));

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(OrganizationSysUserMap obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mSysUserId));
		strList.add(String.valueOf(obj.mOrganizationId));
		strList.add(String.valueOf(obj.mRoleId));

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< OrganizationSysUserMap > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(OrganizationSysUserMap obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mSysUserId));
			strList.add(String.valueOf(obj.mOrganizationId));
			strList.add(String.valueOf(obj.mRoleId));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( OrganizationSysUserMap obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mSysUserId));
		strList.add(String.valueOf(obj.mOrganizationId));
		strList.add(String.valueOf(obj.mRoleId));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(OrganizationSysUserMap organizationSysUserMap) {
		JSONObject jsonObj = new JSONObject();
		String[] organizationSysUserMapLabels = getmKeys();
		try {
			jsonObj.put(organizationSysUserMapLabels[ INDEX_SYS_USER_ID], organizationSysUserMap.mSysUserId );
			jsonObj.put(organizationSysUserMapLabels[ INDEX_ORGANIZATION_ID], organizationSysUserMap.mOrganizationId );
			jsonObj.put(organizationSysUserMapLabels[ INDEX_ROLE_ID], organizationSysUserMap.mRoleId );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static OrganizationSysUserMap jsonObjectToObject( String jsonObjectString ) {
		OrganizationSysUserMap organizationSysUserMap = new OrganizationSysUserMap();
		JSONObject data;
		String[] organizationSysUserMapLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			organizationSysUserMap.mSysUserId = (short)data.getInt(organizationSysUserMapLabels[INDEX_SYS_USER_ID]);
			organizationSysUserMap.mOrganizationId = data.getInt(organizationSysUserMapLabels[INDEX_ORGANIZATION_ID]);
			organizationSysUserMap.mRoleId = (short)data.getInt(organizationSysUserMapLabels[INDEX_ROLE_ID]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return organizationSysUserMap;

	}

	public static ArrayList<OrganizationSysUserMap> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<OrganizationSysUserMap> objList = new ArrayList<OrganizationSysUserMap>();
		try {

			JSONArray organizationSysUserMapJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < organizationSysUserMapJSONArray.length(); i++) {

				JSONObject organizationSysUserMapJsonObject = organizationSysUserMapJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(organizationSysUserMapJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
