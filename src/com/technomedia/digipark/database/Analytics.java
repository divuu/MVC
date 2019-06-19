////////////////////////////////////////////////////////////////////////////

// FileName Analytics.java: Analytics Implementation file

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


public class Analytics  {

	//public static final String CREATE_ANALYTICS_TABLE = "CREATE TABLE analytics (analytics_id INTEGER, subscriber_id INTEGER, session_start_time INTEGER, session_end_time INTEGER, pages_per_session INTEGER, total_session INTEGER, total_visit INTEGER, new_user INTEGER, channels_of_traffic TEXT, time_spent REAL, bounce_rate REAL, source_of_traffic TEXT, click INTEGER, video_watched_duration REAL, browser TEXT, operating_system TEXT, device TEXT)";

	public final static String DATABASE_TABLE_NAME = "analytics";


//MySQL Script
	//CREATE TABLE analytics( analytics_id int, subscriber_id int, session_start_time datetime, session_end_time datetime, pages_per_session int, total_session int, total_visit int, new_user tinyint, channels_of_traffic varchar(128), time_spent float, bounce_rate float, source_of_traffic varchar(128), click int, video_watched_duration float, browser varchar(64), operating_system varchar(64), device varchar(128) );
	public static final String KEY_ANALYTICS_ID = "analytics_id";
	public static final String KEY_SUBSCRIBER_ID = "subscriber_id";
	public static final String KEY_SESSION_START_TIME = "session_start_time";
	public static final String KEY_SESSION_END_TIME = "session_end_time";
	public static final String KEY_PAGES_PER_SESSION = "pages_per_session";
	public static final String KEY_TOTAL_SESSION = "total_session";
	public static final String KEY_TOTAL_VISIT = "total_visit";
	public static final String KEY_NEW_USER = "new_user";
	public static final String KEY_CHANNELS_OF_TRAFFIC = "channels_of_traffic";
	public static final String KEY_TIME_SPENT = "time_spent";
	public static final String KEY_BOUNCE_RATE = "bounce_rate";
	public static final String KEY_SOURCE_OF_TRAFFIC = "source_of_traffic";
	public static final String KEY_CLICK = "click";
	public static final String KEY_VIDEO_WATCHED_DURATION = "video_watched_duration";
	public static final String KEY_BROWSER = "browser";
	public static final String KEY_OPERATING_SYSTEM = "operating_system";
	public static final String KEY_DEVICE = "device";

	public static final String LABEL_ANALYTICS_ID = "Analytics Id";
	public static final String LABEL_SUBSCRIBER_ID = "Subscriber";
	public static final String LABEL_SESSION_START_TIME = "Session Start Time";
	public static final String LABEL_SESSION_END_TIME = "Session End Time";
	public static final String LABEL_PAGES_PER_SESSION = "Pages Per Session";
	public static final String LABEL_TOTAL_SESSION = "Total Session";
	public static final String LABEL_TOTAL_VISIT = "Total Visit";
	public static final String LABEL_NEW_USER = "New User";
	public static final String LABEL_CHANNELS_OF_TRAFFIC = "Channels Of Traffic";
	public static final String LABEL_TIME_SPENT = "Time Spent";
	public static final String LABEL_BOUNCE_RATE = "Bounce Rate";
	public static final String LABEL_SOURCE_OF_TRAFFIC = "Source Of Traffic";
	public static final String LABEL_CLICK = "Click";
	public static final String LABEL_VIDEO_WATCHED_DURATION = "Video Watched Duration";
	public static final String LABEL_BROWSER = "Browser";
	public static final String LABEL_OPERATING_SYSTEM = "Operating System";
	public static final String LABEL_DEVICE = "Device";

	public static final int INDEX_ANALYTICS_ID = 0;
	public static final int INDEX_SUBSCRIBER_ID = 1;
	public static final int INDEX_SESSION_START_TIME = 2;
	public static final int INDEX_SESSION_END_TIME = 3;
	public static final int INDEX_PAGES_PER_SESSION = 4;
	public static final int INDEX_TOTAL_SESSION = 5;
	public static final int INDEX_TOTAL_VISIT = 6;
	public static final int INDEX_NEW_USER = 7;
	public static final int INDEX_CHANNELS_OF_TRAFFIC = 8;
	public static final int INDEX_TIME_SPENT = 9;
	public static final int INDEX_BOUNCE_RATE = 10;
	public static final int INDEX_SOURCE_OF_TRAFFIC = 11;
	public static final int INDEX_CLICK = 12;
	public static final int INDEX_VIDEO_WATCHED_DURATION = 13;
	public static final int INDEX_BROWSER = 14;
	public static final int INDEX_OPERATING_SYSTEM = 15;
	public static final int INDEX_DEVICE = 16;

	public final static int NUM_OF_COLUMNS = 17;

	public int mAnalyticsId;
	public int mSubscriberId;
	public long mSessionStartTime;
	public long mSessionEndTime;
	public int mPagesPerSession;
	public int mTotalSession;
	public int mTotalVisit;
	public byte mNewUser;
	public String mChannelsOfTraffic;
	public float mTimeSpent;
	public float mBounceRate;
	public String mSourceOfTraffic;
	public int mClick;
	public float mVideoWatchedDuration;
	public String mBrowser;
	public String mOperatingSystem;
	public String mDevice;

	public Analytics() {
		mAnalyticsId = 0;
		mSubscriberId = 0;
		mSessionStartTime = 0;
		mSessionEndTime = 0;
		mPagesPerSession = 0;
		mTotalSession = 0;
		mTotalVisit = 0;
		mNewUser = 0;
		mChannelsOfTraffic = null;
		mTimeSpent = 0;
		mBounceRate = 0;
		mSourceOfTraffic = null;
		mClick = 0;
		mVideoWatchedDuration = 0;
		mBrowser = null;
		mOperatingSystem = null;
		mDevice = null;
	}

/*
	public void printData() {

		String DateFormat = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
		Date parsedDate;

		MLogger.i( MLogger.MOD_DB, "analytics_id " + mAnalyticsId );
		MLogger.i( MLogger.MOD_DB, "subscriber_id " + mSubscriberId );
		parsedDate = new Date(mSessionStartTime );
		MLogger.i( MLogger.MOD_DB, "session_start_time " + sdf.format(parsedDate) );
		parsedDate = new Date(mSessionEndTime );
		MLogger.i( MLogger.MOD_DB, "session_end_time " + sdf.format(parsedDate) );
		MLogger.i( MLogger.MOD_DB, "pages_per_session " + mPagesPerSession );
		MLogger.i( MLogger.MOD_DB, "total_session " + mTotalSession );
		MLogger.i( MLogger.MOD_DB, "total_visit " + mTotalVisit );
		MLogger.i( MLogger.MOD_DB, "new_user " + mNewUser );
		MLogger.i( MLogger.MOD_DB, "channels_of_traffic " + mChannelsOfTraffic );
		MLogger.i( MLogger.MOD_DB, "time_spent " + mTimeSpent );
		MLogger.i( MLogger.MOD_DB, "bounce_rate " + mBounceRate );
		MLogger.i( MLogger.MOD_DB, "source_of_traffic " + mSourceOfTraffic );
		MLogger.i( MLogger.MOD_DB, "click " + mClick );
		MLogger.i( MLogger.MOD_DB, "video_watched_duration " + mVideoWatchedDuration );
		MLogger.i( MLogger.MOD_DB, "browser " + mBrowser );
		MLogger.i( MLogger.MOD_DB, "operating_system " + mOperatingSystem );
		MLogger.i( MLogger.MOD_DB, "device " + mDevice );
	}
*/

	public static List<String> getHeader() {

		List<String> tableHeader = new ArrayList<String>();

		tableHeader.add(LABEL_ANALYTICS_ID);
		tableHeader.add(LABEL_SUBSCRIBER_ID);
		tableHeader.add(LABEL_SESSION_START_TIME);
		tableHeader.add(LABEL_SESSION_END_TIME);
		tableHeader.add(LABEL_PAGES_PER_SESSION);
		tableHeader.add(LABEL_TOTAL_SESSION);
		tableHeader.add(LABEL_TOTAL_VISIT);
		tableHeader.add(LABEL_NEW_USER);
		tableHeader.add(LABEL_CHANNELS_OF_TRAFFIC);
		tableHeader.add(LABEL_TIME_SPENT);
		tableHeader.add(LABEL_BOUNCE_RATE);
		tableHeader.add(LABEL_SOURCE_OF_TRAFFIC);
		tableHeader.add(LABEL_CLICK);
		tableHeader.add(LABEL_VIDEO_WATCHED_DURATION);
		tableHeader.add(LABEL_BROWSER);
		tableHeader.add(LABEL_OPERATING_SYSTEM);
		tableHeader.add(LABEL_DEVICE);

		return tableHeader;
	}

	public static JSONArray objListToJSONArrayList(ArrayList<Analytics> objList) {

		if( objList == null )
			return null;

		JSONArray finalList = new JSONArray();
		for (Analytics obj : objList) {

			if (obj == null)
				continue;
			JSONArray strList = new JSONArray();
			strList.put(String.valueOf(obj.mAnalyticsId));
			strList.put(String.valueOf(obj.mSubscriberId));
			strList.put(String.valueOf(obj.mSessionStartTime));
			strList.put(String.valueOf(obj.mSessionEndTime));
			strList.put(String.valueOf(obj.mPagesPerSession));
			strList.put(String.valueOf(obj.mTotalSession));
			strList.put(String.valueOf(obj.mTotalVisit));
			strList.put(String.valueOf(obj.mNewUser));
			strList.put(obj.mChannelsOfTraffic == null?"":obj.mChannelsOfTraffic);
			strList.put(String.valueOf(obj.mTimeSpent));
			strList.put(String.valueOf(obj.mBounceRate));
			strList.put(obj.mSourceOfTraffic == null?"":obj.mSourceOfTraffic);
			strList.put(String.valueOf(obj.mClick));
			strList.put(String.valueOf(obj.mVideoWatchedDuration));
			strList.put(obj.mBrowser == null?"":obj.mBrowser);
			strList.put(obj.mOperatingSystem == null?"":obj.mOperatingSystem);
			strList.put(obj.mDevice == null?"":obj.mDevice);
			finalList.put(strList);
		}

		return finalList;
	}

	public static String[] getmKeys() {
		//String[] keys = { "AnlytcsD", "SbscrbrD", "SnStrtM", "SnDTm", "PgsPrSn", "TlSn", "TlVst", "NwSr", "ChnlsFTrfc", "TmSpnt", "BncRt", "SrcFTrfc", "Clck", "VdWtchdRtn", "Brwsr", "OprtngSystm", "Dvc"};
		String[] keys = { "mAnalyticsId", "mSubscriberId", "mSessionStartTime", "mSessionEndTime", "mPagesPerSession", "mTotalSession", "mTotalVisit", "mNewUser", "mChannelsOfTraffic", "mTimeSpent", "mBounceRate", "mSourceOfTraffic", "mClick", "mVideoWatchedDuration", "mBrowser", "mOperatingSystem", "mDevice"};
		return keys;
	}

	public static JSONArray objListToJSONObjectArray(ArrayList<Analytics> objList) {
		if (objList == null)
			return null;

		JSONArray finalList = new JSONArray();
		String[] keys = getmKeys();

		for (Analytics obj : objList) {

			if (obj == null)
			continue;
			//Object instance = obj;
			JSONObject jo = new JSONObject(obj, keys);
			finalList.put(jo);
		}
		return finalList;
	}

/*
	public static List<ArrayList<String>> objListToArrayList(ArrayList<Analytics> objList) {

		if( objList == null )
			return null;

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		for(Analytics obj : objList){

			if( obj == null )
				continue;
			ArrayList<String> strList = new ArrayList<String>();
			strList.add(String.valueOf(obj.mAnalyticsId));
			strList.add(String.valueOf(obj.mSubscriberId));
			strList.add(String.valueOf(obj.mSessionStartTime));
			strList.add(String.valueOf(obj.mSessionEndTime));
			strList.add(String.valueOf(obj.mPagesPerSession));
			strList.add(String.valueOf(obj.mTotalSession));
			strList.add(String.valueOf(obj.mTotalVisit));
			strList.add(String.valueOf(obj.mNewUser));
			strList.add(obj.mChannelsOfTraffic);
			strList.add(String.valueOf(obj.mTimeSpent));
			strList.add(String.valueOf(obj.mBounceRate));
			strList.add(obj.mSourceOfTraffic);
			strList.add(String.valueOf(obj.mClick));
			strList.add(String.valueOf(obj.mVideoWatchedDuration));
			strList.add(obj.mBrowser);
			strList.add(obj.mOperatingSystem);
			strList.add(obj.mDevice);

			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static List<String> objToArrayList(Analytics obj) {

		if( obj == null )
			return null;

		List<String> strList = new ArrayList<String>();
		strList.add(String.valueOf(obj.mAnalyticsId));
		strList.add(String.valueOf(obj.mSubscriberId));
		strList.add(String.valueOf(obj.mSessionStartTime));
		strList.add(String.valueOf(obj.mSessionEndTime));
		strList.add(String.valueOf(obj.mPagesPerSession));
		strList.add(String.valueOf(obj.mTotalSession));
		strList.add(String.valueOf(obj.mTotalVisit));
		strList.add(String.valueOf(obj.mNewUser));
		strList.add(obj.mChannelsOfTraffic);
		strList.add(String.valueOf(obj.mTimeSpent));
		strList.add(String.valueOf(obj.mBounceRate));
		strList.add(obj.mSourceOfTraffic);
		strList.add(String.valueOf(obj.mClick));
		strList.add(String.valueOf(obj.mVideoWatchedDuration));
		strList.add(obj.mBrowser);
		strList.add(obj.mOperatingSystem);
		strList.add(obj.mDevice);

		return strList;
	}
*/

/*
	public static Vector<Vector<String>> objListToVectorList( ArrayList< Analytics > objList ) {
		if( objList == null )
			return null;

		Vector<Vector<String>> finalList = new Vector<Vector<String>>();
		for(Analytics obj : objList){

			if( obj == null )
				continue;
			Vector<String> strList = new Vector<String>();
			strList.add(String.valueOf(obj.mAnalyticsId));
			strList.add(String.valueOf(obj.mSubscriberId));
			strList.add(String.valueOf(obj.mSessionStartTime));
			strList.add(String.valueOf(obj.mSessionEndTime));
			strList.add(String.valueOf(obj.mPagesPerSession));
			strList.add(String.valueOf(obj.mTotalSession));
			strList.add(String.valueOf(obj.mTotalVisit));
			strList.add(String.valueOf(obj.mNewUser));
			strList.add(String.valueOf(obj.mChannelsOfTraffic));
			strList.add(String.valueOf(obj.mTimeSpent));
			strList.add(String.valueOf(obj.mBounceRate));
			strList.add(String.valueOf(obj.mSourceOfTraffic));
			strList.add(String.valueOf(obj.mClick));
			strList.add(String.valueOf(obj.mVideoWatchedDuration));
			strList.add(String.valueOf(obj.mBrowser));
			strList.add(String.valueOf(obj.mOperatingSystem));
			strList.add(String.valueOf(obj.mDevice));
			finalList.add(strList);
		}
		return finalList;
	}
*/

/*
	public static Vector<String> objToVectorList( Analytics obj ) {
		if( obj == null )
			return null;

		Vector< String > strList = new Vector< String >();
		strList.add(String.valueOf(obj.mAnalyticsId));
		strList.add(String.valueOf(obj.mSubscriberId));
		strList.add(String.valueOf(obj.mSessionStartTime));
		strList.add(String.valueOf(obj.mSessionEndTime));
		strList.add(String.valueOf(obj.mPagesPerSession));
		strList.add(String.valueOf(obj.mTotalSession));
		strList.add(String.valueOf(obj.mTotalVisit));
		strList.add(String.valueOf(obj.mNewUser));
		strList.add(String.valueOf(obj.mChannelsOfTraffic));
		strList.add(String.valueOf(obj.mTimeSpent));
		strList.add(String.valueOf(obj.mBounceRate));
		strList.add(String.valueOf(obj.mSourceOfTraffic));
		strList.add(String.valueOf(obj.mClick));
		strList.add(String.valueOf(obj.mVideoWatchedDuration));
		strList.add(String.valueOf(obj.mBrowser));
		strList.add(String.valueOf(obj.mOperatingSystem));
		strList.add(String.valueOf(obj.mDevice));
		return strList;
	}
*/

	public static JSONObject objtoJsonObject(Analytics analytics) {
		JSONObject jsonObj = new JSONObject();
		String[] analyticsLabels = getmKeys();
		try {
			jsonObj.put(analyticsLabels[ INDEX_ANALYTICS_ID], analytics.mAnalyticsId );
			jsonObj.put(analyticsLabels[ INDEX_SUBSCRIBER_ID], analytics.mSubscriberId );
			jsonObj.put(analyticsLabels[ INDEX_SESSION_START_TIME], analytics.mSessionStartTime );
			jsonObj.put(analyticsLabels[ INDEX_SESSION_END_TIME], analytics.mSessionEndTime );
			jsonObj.put(analyticsLabels[ INDEX_PAGES_PER_SESSION], analytics.mPagesPerSession );
			jsonObj.put(analyticsLabels[ INDEX_TOTAL_SESSION], analytics.mTotalSession );
			jsonObj.put(analyticsLabels[ INDEX_TOTAL_VISIT], analytics.mTotalVisit );
			jsonObj.put(analyticsLabels[ INDEX_NEW_USER], analytics.mNewUser );
			jsonObj.put(analyticsLabels[ INDEX_CHANNELS_OF_TRAFFIC], analytics.mChannelsOfTraffic );
			jsonObj.put(analyticsLabels[ INDEX_TIME_SPENT], analytics.mTimeSpent );
			jsonObj.put(analyticsLabels[ INDEX_BOUNCE_RATE], analytics.mBounceRate );
			jsonObj.put(analyticsLabels[ INDEX_SOURCE_OF_TRAFFIC], analytics.mSourceOfTraffic );
			jsonObj.put(analyticsLabels[ INDEX_CLICK], analytics.mClick );
			jsonObj.put(analyticsLabels[ INDEX_VIDEO_WATCHED_DURATION], analytics.mVideoWatchedDuration );
			jsonObj.put(analyticsLabels[ INDEX_BROWSER], analytics.mBrowser );
			jsonObj.put(analyticsLabels[ INDEX_OPERATING_SYSTEM], analytics.mOperatingSystem );
			jsonObj.put(analyticsLabels[ INDEX_DEVICE], analytics.mDevice );
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return jsonObj;

	}

	public static Analytics jsonObjectToObject( String jsonObjectString ) {
		Analytics analytics = new Analytics();
		JSONObject data;
		String[] analyticsLabels = getmKeys();
		try {


			data = new JSONObject(jsonObjectString);
			analytics.mAnalyticsId = data.getInt(analyticsLabels[INDEX_ANALYTICS_ID]);
			analytics.mSubscriberId = data.getInt(analyticsLabels[INDEX_SUBSCRIBER_ID]);
			analytics.mSessionStartTime = data.getLong(analyticsLabels[INDEX_SESSION_START_TIME]);
			analytics.mSessionEndTime = data.getLong(analyticsLabels[INDEX_SESSION_END_TIME]);
			analytics.mPagesPerSession = data.getInt(analyticsLabels[INDEX_PAGES_PER_SESSION]);
			analytics.mTotalSession = data.getInt(analyticsLabels[INDEX_TOTAL_SESSION]);
			analytics.mTotalVisit = data.getInt(analyticsLabels[INDEX_TOTAL_VISIT]);
			analytics.mNewUser = (byte)data.getInt(analyticsLabels[INDEX_NEW_USER]);
			analytics.mChannelsOfTraffic = data.getString(analyticsLabels[INDEX_CHANNELS_OF_TRAFFIC]);
			analytics.mTimeSpent = (float)data.getDouble(analyticsLabels[INDEX_TIME_SPENT]);
			analytics.mBounceRate = (float)data.getDouble(analyticsLabels[INDEX_BOUNCE_RATE]);
			analytics.mSourceOfTraffic = data.getString(analyticsLabels[INDEX_SOURCE_OF_TRAFFIC]);
			analytics.mClick = data.getInt(analyticsLabels[INDEX_CLICK]);
			analytics.mVideoWatchedDuration = (float)data.getDouble(analyticsLabels[INDEX_VIDEO_WATCHED_DURATION]);
			analytics.mBrowser = data.getString(analyticsLabels[INDEX_BROWSER]);
			analytics.mOperatingSystem = data.getString(analyticsLabels[INDEX_OPERATING_SYSTEM]);
			analytics.mDevice = data.getString(analyticsLabels[INDEX_DEVICE]);
		}
		catch (JSONException e) {
			MLogger.dumpException(e);
		}

		return analytics;

	}

	public static ArrayList<Analytics> jsonObjArrayListToObjList( String jsonObjectArrayString ) {
		ArrayList<Analytics> objList = new ArrayList<Analytics>();
		try {

			JSONArray analyticsJSONArray = new JSONArray(jsonObjectArrayString);

			for(int i = 0; i < analyticsJSONArray.length(); i++) {

				JSONObject analyticsJsonObject = analyticsJSONArray.getJSONObject(i);
				objList.add(jsonObjectToObject(analyticsJsonObject.toString()));
			}
		}
		 catch (JSONException e) {
			MLogger.dumpException(e);
		}


		return objList;

	}
}
