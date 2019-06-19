////////////////////////////////////////////////////////////////////////////

// FileName AnalyticsDbAdaptor.java: AnalyticsDbAdaptor Implementation file

// Author : Vinu | Utkarsh | JRC
// Description : Slash Digital TVAS v1.0


////////////////////////////////////////////////////////////////////////////


package com.technomedia.digipark.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.technomedia.logger.MLogger;
import com.technomedia.utils.GeneralUtils;

import com.technomedia.digipark.database.base.AbstractDbAdaptor;
import com.technomedia.digipark.database.base.DbErrorCodes;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class AnalyticsDbAdaptor extends AbstractDbAdaptor {

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


	public AnalyticsDbAdaptor() {
		mTableName = Analytics.DATABASE_TABLE_NAME;
	}

	public long insert( Analytics analyticsObj ) {

		if( analyticsObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_ANALYTICS_ID + ", " + KEY_SUBSCRIBER_ID + ", " + KEY_SESSION_START_TIME + ", " + KEY_SESSION_END_TIME + ", " + KEY_PAGES_PER_SESSION + ", " + KEY_TOTAL_SESSION + ", " + KEY_TOTAL_VISIT + ", " + KEY_NEW_USER + ", " + KEY_CHANNELS_OF_TRAFFIC + ", " + KEY_TIME_SPENT + ", " + KEY_BOUNCE_RATE + ", " + KEY_SOURCE_OF_TRAFFIC + ", " + KEY_CLICK + ", " + KEY_VIDEO_WATCHED_DURATION + ", " + KEY_BROWSER + ", " + KEY_OPERATING_SYSTEM + ", " + KEY_DEVICE;

		String values = "";
		for (int i = 1; i < Analytics.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + Analytics.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString, Statement.RETURN_GENERATED_KEYS );

			analyticsObj.mAnalyticsId = 0;
			insertObject.setInt( 1, analyticsObj.mAnalyticsId );
			insertObject.setInt( 2, analyticsObj.mSubscriberId );
			insertObject.setTimestamp( 3, GeneralUtils.getDateTime(analyticsObj.mSessionStartTime));
			insertObject.setTimestamp( 4, GeneralUtils.getDateTime(analyticsObj.mSessionEndTime));
			insertObject.setInt( 5, analyticsObj.mPagesPerSession );
			insertObject.setInt( 6, analyticsObj.mTotalSession );
			insertObject.setInt( 7, analyticsObj.mTotalVisit );
			insertObject.setByte( 8, analyticsObj.mNewUser );
			insertObject.setString( 9, analyticsObj.mChannelsOfTraffic );
			insertObject.setFloat( 10, analyticsObj.mTimeSpent );
			insertObject.setFloat( 11, analyticsObj.mBounceRate );
			insertObject.setString( 12, analyticsObj.mSourceOfTraffic );
			insertObject.setInt( 13, analyticsObj.mClick );
			insertObject.setFloat( 14, analyticsObj.mVideoWatchedDuration );
			insertObject.setString( 15, analyticsObj.mBrowser );
			insertObject.setString( 16, analyticsObj.mOperatingSystem );
			insertObject.setString( 17, analyticsObj.mDevice );

			MLogger.i( MLogger.MOD_DB, insertObject.toString() );

			int rowsInserted = insertObject.executeUpdate();

			if( rowsInserted > 0 ) { 
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			}
			ResultSet resultSet = insertObject.getGeneratedKeys();
			if( resultSet != null ) {
				resultSet.next();
				analyticsObj.mAnalyticsId = resultSet.getInt(1);
				resultSet.close();
			}

			if( insertObject != null )
				insertObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long update( Analytics analyticsObj, String whereClause ) {

		if( analyticsObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + Analytics.DATABASE_TABLE_NAME + " set "+ KEY_ANALYTICS_ID + " =?, " + KEY_SUBSCRIBER_ID + " =?, " + KEY_SESSION_START_TIME + " =?, " + KEY_SESSION_END_TIME + " =?, " + KEY_PAGES_PER_SESSION + " =?, " + KEY_TOTAL_SESSION + " =?, " + KEY_TOTAL_VISIT + " =?, " + KEY_NEW_USER + " =?, " + KEY_CHANNELS_OF_TRAFFIC + " =?, " + KEY_TIME_SPENT + " =?, " + KEY_BOUNCE_RATE + " =?, " + KEY_SOURCE_OF_TRAFFIC + " =?, " + KEY_CLICK + " =?, " + KEY_VIDEO_WATCHED_DURATION + " =?, " + KEY_BROWSER + " =?, " + KEY_OPERATING_SYSTEM + " =?, " + KEY_DEVICE + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setInt( 1, analyticsObj.mAnalyticsId );
			updateObject.setInt( 2, analyticsObj.mSubscriberId );
			updateObject.setTimestamp( 3, GeneralUtils.getDateTime(analyticsObj.mSessionStartTime));
			updateObject.setTimestamp( 4, GeneralUtils.getDateTime(analyticsObj.mSessionEndTime));
			updateObject.setInt( 5, analyticsObj.mPagesPerSession );
			updateObject.setInt( 6, analyticsObj.mTotalSession );
			updateObject.setInt( 7, analyticsObj.mTotalVisit );
			updateObject.setByte( 8, analyticsObj.mNewUser );
			updateObject.setString( 9, analyticsObj.mChannelsOfTraffic );
			updateObject.setFloat( 10, analyticsObj.mTimeSpent );
			updateObject.setFloat( 11, analyticsObj.mBounceRate );
			updateObject.setString( 12, analyticsObj.mSourceOfTraffic );
			updateObject.setInt( 13, analyticsObj.mClick );
			updateObject.setFloat( 14, analyticsObj.mVideoWatchedDuration );
			updateObject.setString( 15, analyticsObj.mBrowser );
			updateObject.setString( 16, analyticsObj.mOperatingSystem );
			updateObject.setString( 17, analyticsObj.mDevice );

			MLogger.i( MLogger.MOD_DB, updateObject.toString() );

			int rowsUpdated = updateObject.executeUpdate();

			if( rowsUpdated >0 )
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			if (updateObject != null)
				updateObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long delete( String aWhereStr ) {
		int ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;
		Statement stmt = null;

		String sqlStmt;
		sqlStmt = "DELETE from " + Analytics.DATABASE_TABLE_NAME;
		
		if( aWhereStr != null && aWhereStr.length() > 0 )
			sqlStmt += " where " + aWhereStr;

		try {
			stmt = getConnection().createStatement();
			int noOfRowsDeleted = stmt.executeUpdate( sqlStmt );
			stmt.close();
			if ( noOfRowsDeleted > 0 )

				 return (long)noOfRowsDeleted;

			ret = DbErrorCodes.ERROR_CODE_NO_RECORDS_FOUND;

		} catch (Exception se) {
			MLogger.dumpException( se );
		}

		return ret;
	}

	public ArrayList<Analytics> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + Analytics.DATABASE_TABLE_NAME;
			
			if( whereStr != null && whereStr.length() > 0 )
				sqlStmt += whereStr;
			
			if (null != orderBy) {
				sqlStmt = sqlStmt + " order by " + orderBy;
			}
			
			MLogger.i( MLogger.MOD_DB, sqlStmt );
			
			rs = stmt.executeQuery( sqlStmt );

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}


		try {
			if (rs == null) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				ArrayList<Analytics> list = new ArrayList<Analytics>();

				do {
					Analytics obj = new Analytics();
					obj.mAnalyticsId = rs.getInt( KEY_ANALYTICS_ID );
					obj.mSubscriberId = rs.getInt( KEY_SUBSCRIBER_ID );
					obj.mSessionStartTime = GeneralUtils.getDateTime( rs.getTimestamp( KEY_SESSION_START_TIME ) );
					obj.mSessionEndTime = GeneralUtils.getDateTime( rs.getTimestamp( KEY_SESSION_END_TIME ) );
					obj.mPagesPerSession = rs.getInt( KEY_PAGES_PER_SESSION );
					obj.mTotalSession = rs.getInt( KEY_TOTAL_SESSION );
					obj.mTotalVisit = rs.getInt( KEY_TOTAL_VISIT );
					obj.mNewUser = rs.getByte( KEY_NEW_USER );
					obj.mChannelsOfTraffic = rs.getString( KEY_CHANNELS_OF_TRAFFIC );
					obj.mTimeSpent = rs.getFloat( KEY_TIME_SPENT );
					obj.mBounceRate = rs.getFloat( KEY_BOUNCE_RATE );
					obj.mSourceOfTraffic = rs.getString( KEY_SOURCE_OF_TRAFFIC );
					obj.mClick = rs.getInt( KEY_CLICK );
					obj.mVideoWatchedDuration = rs.getFloat( KEY_VIDEO_WATCHED_DURATION );
					obj.mBrowser = rs.getString( KEY_BROWSER );
					obj.mOperatingSystem = rs.getString( KEY_OPERATING_SYSTEM );
					obj.mDevice = rs.getString( KEY_DEVICE );
					list.add(obj);
				} while (rs.next());
				rs.close();
				stmt.close();
				return list;
			}
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			return null;

		} catch (SQLException se) {

			MLogger.dumpException( se );
		}

		return null;
	}

	public Analytics getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + Analytics.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				Analytics obj = new Analytics();
				obj.mAnalyticsId = rs.getInt( KEY_ANALYTICS_ID );
				obj.mSubscriberId = rs.getInt( KEY_SUBSCRIBER_ID );
				obj.mSessionStartTime = GeneralUtils.getDateTime( rs.getTimestamp( KEY_SESSION_START_TIME ) );
				obj.mSessionEndTime = GeneralUtils.getDateTime( rs.getTimestamp( KEY_SESSION_END_TIME ) );
				obj.mPagesPerSession = rs.getInt( KEY_PAGES_PER_SESSION );
				obj.mTotalSession = rs.getInt( KEY_TOTAL_SESSION );
				obj.mTotalVisit = rs.getInt( KEY_TOTAL_VISIT );
				obj.mNewUser = rs.getByte( KEY_NEW_USER );
				obj.mChannelsOfTraffic = rs.getString( KEY_CHANNELS_OF_TRAFFIC );
				obj.mTimeSpent = rs.getFloat( KEY_TIME_SPENT );
				obj.mBounceRate = rs.getFloat( KEY_BOUNCE_RATE );
				obj.mSourceOfTraffic = rs.getString( KEY_SOURCE_OF_TRAFFIC );
				obj.mClick = rs.getInt( KEY_CLICK );
				obj.mVideoWatchedDuration = rs.getFloat( KEY_VIDEO_WATCHED_DURATION );
				obj.mBrowser = rs.getString( KEY_BROWSER );
				obj.mOperatingSystem = rs.getString( KEY_OPERATING_SYSTEM );
				obj.mDevice = rs.getString( KEY_DEVICE );
				rs.close();
				stmt.close();
				return obj;
			}
			if( rs != null )
				rs.close();
			if( stmt != null )
				stmt.close();
			return null;

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}

		return null;
	}

	@Override
	protected String getInsertStatement(ResultSet resultSet) {
		String columnsNames = KEY_ANALYTICS_ID +  ", " +
				KEY_SUBSCRIBER_ID +  ", " +
				KEY_SESSION_START_TIME +  ", " +
				KEY_SESSION_END_TIME +  ", " +
				KEY_PAGES_PER_SESSION +  ", " +
				KEY_TOTAL_SESSION +  ", " +
				KEY_TOTAL_VISIT +  ", " +
				KEY_NEW_USER +  ", " +
				KEY_CHANNELS_OF_TRAFFIC +  ", " +
				KEY_TIME_SPENT +  ", " +
				KEY_BOUNCE_RATE +  ", " +
				KEY_SOURCE_OF_TRAFFIC +  ", " +
				KEY_CLICK +  ", " +
				KEY_VIDEO_WATCHED_DURATION +  ", " +
				KEY_BROWSER +  ", " +
				KEY_OPERATING_SYSTEM +  ", " +
				KEY_DEVICE;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getInt(KEY_ANALYTICS_ID) +  ", " +
				resultSet.getInt(KEY_SUBSCRIBER_ID) +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getTimestamp(KEY_SESSION_START_TIME))  +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getTimestamp(KEY_SESSION_END_TIME))  +  ", " +
				resultSet.getInt(KEY_PAGES_PER_SESSION) +  ", " +
				resultSet.getInt(KEY_TOTAL_SESSION) +  ", " +
				resultSet.getInt(KEY_TOTAL_VISIT) +  ", " +
				resultSet.getByte(KEY_NEW_USER) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_CHANNELS_OF_TRAFFIC) ) +  ", " +
				resultSet.getFloat(KEY_TIME_SPENT) +  ", " +
				resultSet.getFloat(KEY_BOUNCE_RATE) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_SOURCE_OF_TRAFFIC) ) +  ", " +
				resultSet.getInt(KEY_CLICK) +  ", " +
				resultSet.getFloat(KEY_VIDEO_WATCHED_DURATION) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_BROWSER) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_OPERATING_SYSTEM) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_DEVICE) ) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(Analytics analytics, String tableName) {
		String columnsNames = KEY_ANALYTICS_ID +  ", " +
			KEY_SUBSCRIBER_ID +  ", " +
			KEY_SESSION_START_TIME +  ", " +
			KEY_SESSION_END_TIME +  ", " +
			KEY_PAGES_PER_SESSION +  ", " +
			KEY_TOTAL_SESSION +  ", " +
			KEY_TOTAL_VISIT +  ", " +
			KEY_NEW_USER +  ", " +
			KEY_CHANNELS_OF_TRAFFIC +  ", " +
			KEY_TIME_SPENT +  ", " +
			KEY_BOUNCE_RATE +  ", " +
			KEY_SOURCE_OF_TRAFFIC +  ", " +
			KEY_CLICK +  ", " +
			KEY_VIDEO_WATCHED_DURATION +  ", " +
			KEY_BROWSER +  ", " +
			KEY_OPERATING_SYSTEM +  ", " +
			KEY_DEVICE;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += analytics.mAnalyticsId +  ", " +
			analytics.mSubscriberId +  ", " +
			GeneralUtils.getFormattedDateTimeString( analytics.mSessionStartTime ) +  ", " +
			GeneralUtils.getFormattedDateTimeString( analytics.mSessionEndTime ) +  ", " +
			analytics.mPagesPerSession +  ", " +
			analytics.mTotalSession +  ", " +
			analytics.mTotalVisit +  ", " +
			analytics.mNewUser +  ", " +
			GeneralUtils.getFormattedString( analytics.mChannelsOfTraffic ) +  ", " +
			analytics.mTimeSpent +  ", " +
			analytics.mBounceRate +  ", " +
			GeneralUtils.getFormattedString( analytics.mSourceOfTraffic ) +  ", " +
			analytics.mClick +  ", " +
			analytics.mVideoWatchedDuration +  ", " +
			GeneralUtils.getFormattedString( analytics.mBrowser ) +  ", " +
			GeneralUtils.getFormattedString( analytics.mOperatingSystem ) +  ", " +
			GeneralUtils.getFormattedString( analytics.mDevice ) + " );";
		return sqlStatement;
	}
*/
}
