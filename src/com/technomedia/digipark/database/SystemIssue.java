////////////////////////////////////////////////////////////////////////////

// FileName SystemIssue.java: SystemIssue Implementation file

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


public class SystemIssue  {

	//public static final String CREATE_SYSTEMISSUE_TABLE = "CREATE TABLE system_issue (system_issue_id INTEGER, system_issue_date INTEGER, screen_name TEXT, description TEXT, image_file_path TEXT, issue_type_lookup_id INTEGER, issue_priority_lookup_id INTEGER, resolution TEXT, sys_user_id INTEGER, closed_by_sys_user_id INTEGER, closed_date INTEGER, status INTEGER)";

	public final static String DATABASE_TABLE_NAME = "system_issue";


//MySQL Script
	//CREATE TABLE system_issue( system_issue_id int, system_issue_date datetime, screen_name tinytext, description text, image_file_path tinytext, issue_type_lookup_id int, issue_priority_lookup_id int, resolution text, sys_user_id int, closed_by_sys_user_id int, closed_date datetime, status int );
	public static final String KEY_SYSTEM_ISSUE_ID = "system_issue_id";
	public static final String KEY_SYSTEM_ISSUE_DATE = "system_issue_date";
	public static final String KEY_SCREEN_NAME = "screen_name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_IMAGE_FILE_PATH = "image_file_path";
	public static final String KEY_ISSUE_TYPE_LOOKUP_ID = "issue_type_lookup_id";
	public static final String KEY_ISSUE_PRIORITY_LOOKUP_ID = "issue_priority_lookup_id";
	public static final String KEY_RESOLUTION = "resolution";
	public static final String KEY_SYS_USER_ID = "sys_user_id";
	public static final String KEY_CLOSED_BY_SYS_USER_ID = "closed_by_sys_user_id";
	public static final String KEY_CLOSED_DATE = "closed_date";
	public static final String KEY_STATUS = "status";

	public static final String LABEL_SYSTEM_ISSUE_ID = "System Issue Id";
	public static final String LABEL_SYSTEM_ISSUE_DATE = "System Issue Date";
	public static final String LABEL_SCREEN_NAME = "Screen Name";
	public static final String LABEL_DESCRIPTION = "Description";
	public static final String LABEL_IMAGE_FILE_PATH = "Image File Path";
	public static final String LABEL_ISSUE_TYPE_LOOKUP_ID = "Issue Type";
	public static final String LABEL_ISSUE_PRIORITY_LOOKUP_ID = "Issue Priority";
	public static final String LABEL_RESOLUTION = "Resolution";
	public static final String LABEL_SYS_USER_ID = "Sys User";
	public static final String LABEL_CLOSED_BY_SYS_USER_ID = "Closed By Sys User";
	public static final String LABEL_CLOSED_DATE = "Closed Date";
	public static final String LABEL_STATUS = "Status";

	public static final int INDEX_SYSTEM_ISSUE_ID = 0;
	public static final int INDEX_SYSTEM_ISSUE_DATE = 1;
	public static final int INDEX_SCREEN_NAME = 2;
	public static final int INDEX_DESCRIPTION = 3;
	public static final int INDEX_IMAGE_FILE_PATH = 4;
	public static final int INDEX_ISSUE_TYPE_LOOKUP_ID = 5;
	public static final int INDEX_ISSUE_PRIORITY_LOOKUP_ID = 6;
	public static final int INDEX_RESOLUTION = 7;
	public static final int INDEX_SYS_USER_ID = 8;
	public static final int INDEX_CLOSED_BY_SYS_USER_ID = 9;
	public static final int INDEX_CLOSED_DATE = 10;
	public static final int INDEX_STATUS = 11;

	public final static int NUM_OF_COLUMNS = 12;

	public int mSystemIssueId;
	public long mSystemIssueDate;
	public String mScreenName;
	public String mDescription;		//Description of issue
	public String mImageFilePath;
	public int mIssueTypeLookupId;
	public int mIssuePriorityLookupId;
	public String mResolution;
	public int mSysUserId;		//User reporting the issue
	public int mClosedBySysUserId;
	public long mClosedDate;
	public int mStatus;		//Closed. open, etc

	public SystemIssue() {
		mSystemIssueId = 0;
		mSystemIssueDate = 0;
		mScreenName = null;
		mDescription = null;
		mImageFilePath = null;
		mIssueTypeLookupId = 0;
		mIssuePriorityLookupId = 0;
		mResolution = null;
		mSysUserId = 0;
		mClosedBySysUserId = 0;
		mClosedDate = 0;
		mStatus = 0;
	}

/*
	public void printData() {

		String DateFormat = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
		Date parsedDate;

		MLogger.i( MLogger.MOD_DB, "system_issue_id " + mSystemIssueId );
		parsedDate = new Date(mSystemIssueDate );
		MLogger.i( MLogger.MOD_DB, "system_issue_date " + sdf.format(parsedDate) );
		MLogger.i( MLogger.MOD_DB, "screen_name " + mScreenName );
		MLogger.i( MLogger.MOD_DB, "description " + mDescription );
		MLogger.i( MLogger.MOD_DB, "image_file_path " + mImageFilePath );
		MLogger.i( MLogger.MOD_DB, "issue_type_lookup_id " + mIssueTypeLookupId );
		MLogger.i( MLogger.MOD_DB, "issue_priority_lookup_id " + mIssuePriorityLookupId );
		MLogger.i( MLogger.MOD_DB, "resolution " + mResolution );
		MLogger.i( MLogger.MOD_DB, "sys_user_id " + mSysUserId );
		MLogger.i( MLogger.MOD_DB, "closed_by_sys_user_id " + mClosedBySysUserId );
		parsedDate = new Date(mClosedDate );
		MLogger.i( MLogger.MOD_DB, "closed_date " + sdf.format(parsedDate) );
		MLogger.i( MLogger.MOD_DB, "status " + mStatus );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_SYSTEM_ISSUE_ID);
		tableHeader.add(LABEL_SYSTEM_ISSUE_DATE);
		tableHeader.add(LABEL_SCREEN_NAME);
		tableHeader.add(LABEL_DESCRIPTION);
		tableHeader.add(LABEL_IMAGE_FILE_PATH);
		tableHeader.add(LABEL_ISSUE_TYPE_LOOKUP_ID);
		tableHeader.add(LABEL_ISSUE_PRIORITY_LOOKUP_ID);
		tableHeader.add(LABEL_RESOLUTION);
		tableHeader.add(LABEL_SYS_USER_ID);
		tableHeader.add(LABEL_CLOSED_BY_SYS_USER_ID);
		tableHeader.add(LABEL_CLOSED_DATE);
		tableHeader.add(LABEL_STATUS);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<SystemIssue> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (SystemIssue obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mSystemIssueId));
			strList.put(String.valueOf(obj.mSystemIssueDate));
			strList.put(obj.mScreenName == null?"":obj.mScreenName);
			strList.put(obj.mDescription == null?"":obj.mDescription);
			strList.put(obj.mImageFilePath == null?"":obj.mImageFilePath);
			strList.put(String.valueOf(obj.mIssueTypeLookupId));
			strList.put(String.valueOf(obj.mIssuePriorityLookupId));
			strList.put(obj.mResolution == null?"":obj.mResolution);
			strList.put(String.valueOf(obj.mSysUserId));
			strList.put(String.valueOf(obj.mClosedBySysUserId));
			strList.put(String.valueOf(obj.mClosedDate));
			strList.put(String.valueOf(obj.mStatus));
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "SystmSD", "SystmSDt", "ScrnM", "Dscrptn", "ImgFlPth", "IsTypLkpD", "IsPrtyLkpD", "Rsltn", "SysRD", "ClsdBySysRD", "ClsdT", "Sts"};
		String[] keys = { "mSystemIssueId", "mSystemIssueDate", "mScreenName", "mDescription", "mImageFilePath", "mIssueTypeLookupId", "mIssuePriorityLookupId", "mResolution", "mSysUserId", "mClosedBySysUserId", "mClosedDate", "mStatus"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<SystemIssue> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (SystemIssue obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<SystemIssue> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(SystemIssue obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mSystemIssueId));
			strList.add(String.valueOf(obj.mSystemIssueDate));
			strList.add(obj.mScreenName);
			strList.add(obj.mDescription);
			strList.add(obj.mImageFilePath);
			strList.add(String.valueOf(obj.mIssueTypeLookupId));
			strList.add(String.valueOf(obj.mIssuePriorityLookupId));
			strList.add(obj.mResolution);
			strList.add(String.valueOf(obj.mSysUserId));
			strList.add(String.valueOf(obj.mClosedBySysUserId));
			strList.add(String.valueOf(obj.mClosedDate));
			strList.add(String.valueOf(obj.mStatus));

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(SystemIssue obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mSystemIssueId));
		strList.add(String.valueOf(obj.mSystemIssueDate));
		strList.add(obj.mScreenName);
		strList.add(obj.mDescription);
		strList.add(obj.mImageFilePath);
		strList.add(String.valueOf(obj.mIssueTypeLookupId));
		strList.add(String.valueOf(obj.mIssuePriorityLookupId));
		strList.add(obj.mResolution);
		strList.add(String.valueOf(obj.mSysUserId));
		strList.add(String.valueOf(obj.mClosedBySysUserId));
		strList.add(String.valueOf(obj.mClosedDate));
		strList.add(String.valueOf(obj.mStatus));

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< SystemIssue > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(SystemIssue obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mSystemIssueId));
			strList.add(String.valueOf(obj.mSystemIssueDate));
			strList.add(String.valueOf(obj.mScreenName));
			strList.add(String.valueOf(obj.mDescription));
			strList.add(String.valueOf(obj.mImageFilePath));
			strList.add(String.valueOf(obj.mIssueTypeLookupId));
			strList.add(String.valueOf(obj.mIssuePriorityLookupId));
			strList.add(String.valueOf(obj.mResolution));
			strList.add(String.valueOf(obj.mSysUserId));
			strList.add(String.valueOf(obj.mClosedBySysUserId));
			strList.add(String.valueOf(obj.mClosedDate));
			strList.add(String.valueOf(obj.mStatus));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( SystemIssue obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mSystemIssueId));
		strList.add(String.valueOf(obj.mSystemIssueDate));
		strList.add(String.valueOf(obj.mScreenName));
		strList.add(String.valueOf(obj.mDescription));
		strList.add(String.valueOf(obj.mImageFilePath));
		strList.add(String.valueOf(obj.mIssueTypeLookupId));
		strList.add(String.valueOf(obj.mIssuePriorityLookupId));
		strList.add(String.valueOf(obj.mResolution));
		strList.add(String.valueOf(obj.mSysUserId));
		strList.add(String.valueOf(obj.mClosedBySysUserId));
		strList.add(String.valueOf(obj.mClosedDate));
		strList.add(String.valueOf(obj.mStatus));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(SystemIssue systemIssue) {
		JSONObject jsonObj = new JSONObject();
		String[] systemIssueLabels = getmKeys();
		try {
			jsonObj.put(systemIssueLabels[ INDEX_SYSTEM_ISSUE_ID], systemIssue.mSystemIssueId );
			jsonObj.put(systemIssueLabels[ INDEX_SYSTEM_ISSUE_DATE], systemIssue.mSystemIssueDate );
			jsonObj.put(systemIssueLabels[ INDEX_SCREEN_NAME], systemIssue.mScreenName );
			jsonObj.put(systemIssueLabels[ INDEX_DESCRIPTION], systemIssue.mDescription );
			jsonObj.put(systemIssueLabels[ INDEX_IMAGE_FILE_PATH], systemIssue.mImageFilePath );
			jsonObj.put(systemIssueLabels[ INDEX_ISSUE_TYPE_LOOKUP_ID], systemIssue.mIssueTypeLookupId );
			jsonObj.put(systemIssueLabels[ INDEX_ISSUE_PRIORITY_LOOKUP_ID], systemIssue.mIssuePriorityLookupId );
			jsonObj.put(systemIssueLabels[ INDEX_RESOLUTION], systemIssue.mResolution );
			jsonObj.put(systemIssueLabels[ INDEX_SYS_USER_ID], systemIssue.mSysUserId );
			jsonObj.put(systemIssueLabels[ INDEX_CLOSED_BY_SYS_USER_ID], systemIssue.mClosedBySysUserId );
			jsonObj.put(systemIssueLabels[ INDEX_CLOSED_DATE], systemIssue.mClosedDate );
			jsonObj.put(systemIssueLabels[ INDEX_STATUS], systemIssue.mStatus );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static SystemIssue jsonObjectToObject( String jsonObjectString ) {
		SystemIssue systemIssue = new SystemIssue();
		JSONObject data;
		String[] systemIssueLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			systemIssue.mSystemIssueId = data.getInt(systemIssueLabels[INDEX_SYSTEM_ISSUE_ID]);
			systemIssue.mSystemIssueDate = data.getLong(systemIssueLabels[INDEX_SYSTEM_ISSUE_DATE]);
			systemIssue.mScreenName = data.getString(systemIssueLabels[INDEX_SCREEN_NAME]);
			systemIssue.mDescription = data.getString(systemIssueLabels[INDEX_DESCRIPTION]);
			systemIssue.mImageFilePath = data.getString(systemIssueLabels[INDEX_IMAGE_FILE_PATH]);
			systemIssue.mIssueTypeLookupId = data.getInt(systemIssueLabels[INDEX_ISSUE_TYPE_LOOKUP_ID]);
			systemIssue.mIssuePriorityLookupId = data.getInt(systemIssueLabels[INDEX_ISSUE_PRIORITY_LOOKUP_ID]);
			systemIssue.mResolution = data.getString(systemIssueLabels[INDEX_RESOLUTION]);
			systemIssue.mSysUserId = data.getInt(systemIssueLabels[INDEX_SYS_USER_ID]);
			systemIssue.mClosedBySysUserId = data.getInt(systemIssueLabels[INDEX_CLOSED_BY_SYS_USER_ID]);
			systemIssue.mClosedDate = data.getLong(systemIssueLabels[INDEX_CLOSED_DATE]);
			systemIssue.mStatus = data.getInt(systemIssueLabels[INDEX_STATUS]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return systemIssue;

	}

	public static ArrayList<SystemIssue> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<SystemIssue> objList = new ArrayList<SystemIssue>();
		try {

			JSONArray systemIssueJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < systemIssueJSONArray.length(); i++) {

				JSONObject systemIssueJsonObject = systemIssueJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(systemIssueJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
