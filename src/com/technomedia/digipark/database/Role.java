////////////////////////////////////////////////////////////////////////////

// FileName Role.java: Role Implementation file

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


public class Role  {

	//public static final String CREATE_ROLE_TABLE = "CREATE TABLE role (role_id INTEGER, organization_id INTEGER, name TEXT, role_web_app_ui_features TEXT, role_mobile_app_ui_features TEXT, role_report_features TEXT, role_menu TEXT, role_script TEXT, remarks TEXT)";

	public final static String DATABASE_TABLE_NAME = "role";


//MySQL Script
	//CREATE TABLE role( role_id smallint, organization_id int, name varchar(64), role_web_app_ui_features text, role_mobile_app_ui_features text, role_report_features text, role_menu text, role_script text, remarks text );
	public static final String KEY_ROLE_ID = "role_id";
	public static final String KEY_ORGANIZATION_ID = "organization_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_ROLE_WEB_APP_UI_FEATURES = "role_web_app_ui_features";
	public static final String KEY_ROLE_MOBILE_APP_UI_FEATURES = "role_mobile_app_ui_features";
	public static final String KEY_ROLE_REPORT_FEATURES = "role_report_features";
	public static final String KEY_ROLE_MENU = "role_menu";
	public static final String KEY_ROLE_SCRIPT = "role_script";
	public static final String KEY_REMARKS = "remarks";

	public static final String LABEL_ROLE_ID = "ID";//"Role Id";
	public static final String LABEL_ORGANIZATION_ID = "Organization";
	public static final String LABEL_NAME = "Name";
	public static final String LABEL_ROLE_WEB_APP_UI_FEATURES = "Role Web App Ui Features";
	public static final String LABEL_ROLE_MOBILE_APP_UI_FEATURES = "Role Mobile App Ui Features";
	public static final String LABEL_ROLE_REPORT_FEATURES = "Role Report Features";
	public static final String LABEL_ROLE_MENU = "Role Menu";
	public static final String LABEL_ROLE_SCRIPT = "Role Script";
	public static final String LABEL_REMARKS = "Remarks";

	public static final int INDEX_ROLE_ID = 0;
	public static final int INDEX_ORGANIZATION_ID = 1;
	public static final int INDEX_NAME = 2;
	public static final int INDEX_ROLE_WEB_APP_UI_FEATURES = 3;
	public static final int INDEX_ROLE_MOBILE_APP_UI_FEATURES = 4;
	public static final int INDEX_ROLE_REPORT_FEATURES = 5;
	public static final int INDEX_ROLE_MENU = 6;
	public static final int INDEX_ROLE_SCRIPT = 7;
	public static final int INDEX_REMARKS = 8;

	public final static int NUM_OF_COLUMNS = 9;

	public short mRoleId;
	public int mOrganizationId;
	public String mName;		//Role of the Role:
	public String mRoleWebAppUiFeatures;		//String of 1s and 0s, indicating which menu items will be visible or invisible
	public String mRoleMobileAppUiFeatures;		//1s and 0s
	public String mRoleReportFeatures;		//1s and 0s
	public String mRoleMenu;		//Menu item for this role. JSON Array of objects
	public String mRoleScript;		//Any role dependent script that we want to execute
	public String mRemarks;

	public Role() {
		mRoleId = 0;
		mOrganizationId = 0;
		mName = null;
		mRoleWebAppUiFeatures = null;
		mRoleMobileAppUiFeatures = null;
		mRoleReportFeatures = null;
		mRoleMenu = null;
		mRoleScript = null;
		mRemarks = null;
	}

/*
	public void printData() {

		MLogger.i( MLogger.MOD_DB, "role_id " + mRoleId );
		MLogger.i( MLogger.MOD_DB, "organization_id " + mOrganizationId );
		MLogger.i( MLogger.MOD_DB, "name " + mName );
		MLogger.i( MLogger.MOD_DB, "role_web_app_ui_features " + mRoleWebAppUiFeatures );
		MLogger.i( MLogger.MOD_DB, "role_mobile_app_ui_features " + mRoleMobileAppUiFeatures );
		MLogger.i( MLogger.MOD_DB, "role_report_features " + mRoleReportFeatures );
		MLogger.i( MLogger.MOD_DB, "role_menu " + mRoleMenu );
		MLogger.i( MLogger.MOD_DB, "role_script " + mRoleScript );
		MLogger.i( MLogger.MOD_DB, "remarks " + mRemarks );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_ROLE_ID);
		tableHeader.add(LABEL_ORGANIZATION_ID);
		tableHeader.add(LABEL_NAME);
		tableHeader.add(LABEL_ROLE_WEB_APP_UI_FEATURES);
		tableHeader.add(LABEL_ROLE_MOBILE_APP_UI_FEATURES);
		tableHeader.add(LABEL_ROLE_REPORT_FEATURES);
		tableHeader.add(LABEL_ROLE_MENU);
		tableHeader.add(LABEL_ROLE_SCRIPT);
		tableHeader.add(LABEL_REMARKS);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<Role> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (Role obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mRoleId));
			strList.put(String.valueOf(obj.mOrganizationId));
			strList.put(obj.mName == null?"":obj.mName);
			strList.put(obj.mRoleWebAppUiFeatures == null?"":obj.mRoleWebAppUiFeatures);
			strList.put(obj.mRoleMobileAppUiFeatures == null?"":obj.mRoleMobileAppUiFeatures);
			strList.put(obj.mRoleReportFeatures == null?"":obj.mRoleReportFeatures);
			strList.put(obj.mRoleMenu == null?"":obj.mRoleMenu);
			strList.put(obj.mRoleScript == null?"":obj.mRoleScript);
			strList.put(obj.mRemarks == null?"":obj.mRemarks);
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "RlD", "OrgnztnD", "Nm", "RlWbPFtrs", "RlMblPFtrs", "RlRprtFtrs", "RlMn", "RlScrpt", "Rmrks"};
		String[] keys = { "mRoleId", "mOrganizationId", "mName", "mRoleWebAppUiFeatures", "mRoleMobileAppUiFeatures", "mRoleReportFeatures", "mRoleMenu", "mRoleScript", "mRemarks"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<Role> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (Role obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<Role> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(Role obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mRoleId));
			strList.add(String.valueOf(obj.mOrganizationId));
			strList.add(obj.mName);
			strList.add(obj.mRoleWebAppUiFeatures);
			strList.add(obj.mRoleMobileAppUiFeatures);
			strList.add(obj.mRoleReportFeatures);
			strList.add(obj.mRoleMenu);
			strList.add(obj.mRoleScript);
			strList.add(obj.mRemarks);

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(Role obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mRoleId));
		strList.add(String.valueOf(obj.mOrganizationId));
		strList.add(obj.mName);
		strList.add(obj.mRoleWebAppUiFeatures);
		strList.add(obj.mRoleMobileAppUiFeatures);
		strList.add(obj.mRoleReportFeatures);
		strList.add(obj.mRoleMenu);
		strList.add(obj.mRoleScript);
		strList.add(obj.mRemarks);

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< Role > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(Role obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mRoleId));
			strList.add(String.valueOf(obj.mOrganizationId));
			strList.add(String.valueOf(obj.mName));
			strList.add(String.valueOf(obj.mRoleWebAppUiFeatures));
			strList.add(String.valueOf(obj.mRoleMobileAppUiFeatures));
			strList.add(String.valueOf(obj.mRoleReportFeatures));
			strList.add(String.valueOf(obj.mRoleMenu));
			strList.add(String.valueOf(obj.mRoleScript));
			strList.add(String.valueOf(obj.mRemarks));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( Role obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mRoleId));
		strList.add(String.valueOf(obj.mOrganizationId));
		strList.add(String.valueOf(obj.mName));
		strList.add(String.valueOf(obj.mRoleWebAppUiFeatures));
		strList.add(String.valueOf(obj.mRoleMobileAppUiFeatures));
		strList.add(String.valueOf(obj.mRoleReportFeatures));
		strList.add(String.valueOf(obj.mRoleMenu));
		strList.add(String.valueOf(obj.mRoleScript));
		strList.add(String.valueOf(obj.mRemarks));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(Role role) {
		JSONObject jsonObj = new JSONObject();
		String[] roleLabels = getmKeys();
		try {
			jsonObj.put(roleLabels[ INDEX_ROLE_ID], role.mRoleId );
			jsonObj.put(roleLabels[ INDEX_ORGANIZATION_ID], role.mOrganizationId );
			jsonObj.put(roleLabels[ INDEX_NAME], role.mName );
			jsonObj.put(roleLabels[ INDEX_ROLE_WEB_APP_UI_FEATURES], role.mRoleWebAppUiFeatures );
			jsonObj.put(roleLabels[ INDEX_ROLE_MOBILE_APP_UI_FEATURES], role.mRoleMobileAppUiFeatures );
			jsonObj.put(roleLabels[ INDEX_ROLE_REPORT_FEATURES], role.mRoleReportFeatures );
			jsonObj.put(roleLabels[ INDEX_ROLE_MENU], role.mRoleMenu );
			jsonObj.put(roleLabels[ INDEX_ROLE_SCRIPT], role.mRoleScript );
			jsonObj.put(roleLabels[ INDEX_REMARKS], role.mRemarks );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static Role jsonObjectToObject( String jsonObjectString ) {
		Role role = new Role();
		JSONObject data;
		String[] roleLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			role.mRoleId = (short)data.getInt(roleLabels[INDEX_ROLE_ID]);
			role.mOrganizationId = data.getInt(roleLabels[INDEX_ORGANIZATION_ID]);
			role.mName = data.getString(roleLabels[INDEX_NAME]);
			role.mRoleWebAppUiFeatures = data.getString(roleLabels[INDEX_ROLE_WEB_APP_UI_FEATURES]);
			role.mRoleMobileAppUiFeatures = data.getString(roleLabels[INDEX_ROLE_MOBILE_APP_UI_FEATURES]);
			role.mRoleReportFeatures = data.getString(roleLabels[INDEX_ROLE_REPORT_FEATURES]);
			role.mRoleMenu = data.getString(roleLabels[INDEX_ROLE_MENU]);
			role.mRoleScript = data.getString(roleLabels[INDEX_ROLE_SCRIPT]);
			role.mRemarks = data.getString(roleLabels[INDEX_REMARKS]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return role;

	}

	public static ArrayList<Role> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<Role> objList = new ArrayList<Role>();
		try {

			JSONArray roleJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < roleJSONArray.length(); i++) {

				JSONObject roleJsonObject = roleJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(roleJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
