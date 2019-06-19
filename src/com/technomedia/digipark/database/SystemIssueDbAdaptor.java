////////////////////////////////////////////////////////////////////////////

// FileName SystemIssueDbAdaptor.java: SystemIssueDbAdaptor Implementation file

// Author : Vinu | Utkarsh | JRC
// Description : Digi Park v1.0


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


public class SystemIssueDbAdaptor extends AbstractDbAdaptor {

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


	public SystemIssueDbAdaptor() {
		mTableName = SystemIssue.DATABASE_TABLE_NAME;
	}

	public long insert( SystemIssue systemIssueObj ) {

		if( systemIssueObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_SYSTEM_ISSUE_ID + ", " + KEY_SYSTEM_ISSUE_DATE + ", " + KEY_SCREEN_NAME + ", " + KEY_DESCRIPTION + ", " + KEY_IMAGE_FILE_PATH + ", " + KEY_ISSUE_TYPE_LOOKUP_ID + ", " + KEY_ISSUE_PRIORITY_LOOKUP_ID + ", " + KEY_RESOLUTION + ", " + KEY_SYS_USER_ID + ", " + KEY_CLOSED_BY_SYS_USER_ID + ", " + KEY_CLOSED_DATE + ", " + KEY_STATUS;

		String values = "";
		for (int i = 1; i < SystemIssue.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + SystemIssue.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString, Statement.RETURN_GENERATED_KEYS );

			systemIssueObj.mSystemIssueId = 0;
			insertObject.setInt( 1, systemIssueObj.mSystemIssueId );
			insertObject.setTimestamp( 2, GeneralUtils.getDateTime(systemIssueObj.mSystemIssueDate));
			insertObject.setString( 3, systemIssueObj.mScreenName );
			insertObject.setString( 4, systemIssueObj.mDescription );
			insertObject.setString( 5, systemIssueObj.mImageFilePath );
			insertObject.setInt( 6, systemIssueObj.mIssueTypeLookupId );
			insertObject.setInt( 7, systemIssueObj.mIssuePriorityLookupId );
			insertObject.setString( 8, systemIssueObj.mResolution );
			insertObject.setInt( 9, systemIssueObj.mSysUserId );
			insertObject.setInt( 10, systemIssueObj.mClosedBySysUserId );
			insertObject.setTimestamp( 11, GeneralUtils.getDateTime(systemIssueObj.mClosedDate));
			insertObject.setInt( 12, systemIssueObj.mStatus );

			MLogger.i( MLogger.MOD_DB, insertObject.toString() );

			int rowsInserted = insertObject.executeUpdate();

			if( rowsInserted > 0 ) { 
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			}
			ResultSet resultSet = insertObject.getGeneratedKeys();
			if( resultSet != null ) {
				resultSet.next();
				systemIssueObj.mSystemIssueId = resultSet.getInt(1);
				resultSet.close();
			}

			if( insertObject != null )
				insertObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long update( SystemIssue systemIssueObj, String whereClause ) {

		if( systemIssueObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + SystemIssue.DATABASE_TABLE_NAME + " set "+ KEY_SYSTEM_ISSUE_ID + " =?, " + KEY_SYSTEM_ISSUE_DATE + " =?, " + KEY_SCREEN_NAME + " =?, " + KEY_DESCRIPTION + " =?, " + KEY_IMAGE_FILE_PATH + " =?, " + KEY_ISSUE_TYPE_LOOKUP_ID + " =?, " + KEY_ISSUE_PRIORITY_LOOKUP_ID + " =?, " + KEY_RESOLUTION + " =?, " + KEY_SYS_USER_ID + " =?, " + KEY_CLOSED_BY_SYS_USER_ID + " =?, " + KEY_CLOSED_DATE + " =?, " + KEY_STATUS + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setInt( 1, systemIssueObj.mSystemIssueId );
			updateObject.setTimestamp( 2, GeneralUtils.getDateTime(systemIssueObj.mSystemIssueDate));
			updateObject.setString( 3, systemIssueObj.mScreenName );
			updateObject.setString( 4, systemIssueObj.mDescription );
			updateObject.setString( 5, systemIssueObj.mImageFilePath );
			updateObject.setInt( 6, systemIssueObj.mIssueTypeLookupId );
			updateObject.setInt( 7, systemIssueObj.mIssuePriorityLookupId );
			updateObject.setString( 8, systemIssueObj.mResolution );
			updateObject.setInt( 9, systemIssueObj.mSysUserId );
			updateObject.setInt( 10, systemIssueObj.mClosedBySysUserId );
			updateObject.setTimestamp( 11, GeneralUtils.getDateTime(systemIssueObj.mClosedDate));
			updateObject.setInt( 12, systemIssueObj.mStatus );

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
		sqlStmt = "DELETE from " + SystemIssue.DATABASE_TABLE_NAME;
		
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

	public ArrayList<SystemIssue> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + SystemIssue.DATABASE_TABLE_NAME;
			
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
				ArrayList<SystemIssue> list = new ArrayList<SystemIssue>();

				do {
					SystemIssue obj = new SystemIssue();
					obj.mSystemIssueId = rs.getInt( KEY_SYSTEM_ISSUE_ID );
					obj.mSystemIssueDate = GeneralUtils.getDateTime( rs.getTimestamp( KEY_SYSTEM_ISSUE_DATE ) );
					obj.mScreenName = rs.getString( KEY_SCREEN_NAME );
					obj.mDescription = rs.getString( KEY_DESCRIPTION );
					obj.mImageFilePath = rs.getString( KEY_IMAGE_FILE_PATH );
					obj.mIssueTypeLookupId = rs.getInt( KEY_ISSUE_TYPE_LOOKUP_ID );
					obj.mIssuePriorityLookupId = rs.getInt( KEY_ISSUE_PRIORITY_LOOKUP_ID );
					obj.mResolution = rs.getString( KEY_RESOLUTION );
					obj.mSysUserId = rs.getInt( KEY_SYS_USER_ID );
					obj.mClosedBySysUserId = rs.getInt( KEY_CLOSED_BY_SYS_USER_ID );
					obj.mClosedDate = GeneralUtils.getDateTime( rs.getTimestamp( KEY_CLOSED_DATE ) );
					obj.mStatus = rs.getInt( KEY_STATUS );
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

	public SystemIssue getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + SystemIssue.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				SystemIssue obj = new SystemIssue();
				obj.mSystemIssueId = rs.getInt( KEY_SYSTEM_ISSUE_ID );
				obj.mSystemIssueDate = GeneralUtils.getDateTime( rs.getTimestamp( KEY_SYSTEM_ISSUE_DATE ) );
				obj.mScreenName = rs.getString( KEY_SCREEN_NAME );
				obj.mDescription = rs.getString( KEY_DESCRIPTION );
				obj.mImageFilePath = rs.getString( KEY_IMAGE_FILE_PATH );
				obj.mIssueTypeLookupId = rs.getInt( KEY_ISSUE_TYPE_LOOKUP_ID );
				obj.mIssuePriorityLookupId = rs.getInt( KEY_ISSUE_PRIORITY_LOOKUP_ID );
				obj.mResolution = rs.getString( KEY_RESOLUTION );
				obj.mSysUserId = rs.getInt( KEY_SYS_USER_ID );
				obj.mClosedBySysUserId = rs.getInt( KEY_CLOSED_BY_SYS_USER_ID );
				obj.mClosedDate = GeneralUtils.getDateTime( rs.getTimestamp( KEY_CLOSED_DATE ) );
				obj.mStatus = rs.getInt( KEY_STATUS );
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
		String columnsNames = KEY_SYSTEM_ISSUE_ID +  ", " +
				KEY_SYSTEM_ISSUE_DATE +  ", " +
				KEY_SCREEN_NAME +  ", " +
				KEY_DESCRIPTION +  ", " +
				KEY_IMAGE_FILE_PATH +  ", " +
				KEY_ISSUE_TYPE_LOOKUP_ID +  ", " +
				KEY_ISSUE_PRIORITY_LOOKUP_ID +  ", " +
				KEY_RESOLUTION +  ", " +
				KEY_SYS_USER_ID +  ", " +
				KEY_CLOSED_BY_SYS_USER_ID +  ", " +
				KEY_CLOSED_DATE +  ", " +
				KEY_STATUS;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getInt(KEY_SYSTEM_ISSUE_ID) +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getTimestamp(KEY_SYSTEM_ISSUE_DATE))  +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_SCREEN_NAME) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_DESCRIPTION) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_IMAGE_FILE_PATH) ) +  ", " +
				resultSet.getInt(KEY_ISSUE_TYPE_LOOKUP_ID) +  ", " +
				resultSet.getInt(KEY_ISSUE_PRIORITY_LOOKUP_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_RESOLUTION) ) +  ", " +
				resultSet.getInt(KEY_SYS_USER_ID) +  ", " +
				resultSet.getInt(KEY_CLOSED_BY_SYS_USER_ID) +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getTimestamp(KEY_CLOSED_DATE))  +  ", " +
				resultSet.getInt(KEY_STATUS) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(SystemIssue systemIssue, String tableName) {
		String columnsNames = KEY_SYSTEM_ISSUE_ID +  ", " +
			KEY_SYSTEM_ISSUE_DATE +  ", " +
			KEY_SCREEN_NAME +  ", " +
			KEY_DESCRIPTION +  ", " +
			KEY_IMAGE_FILE_PATH +  ", " +
			KEY_ISSUE_TYPE_LOOKUP_ID +  ", " +
			KEY_ISSUE_PRIORITY_LOOKUP_ID +  ", " +
			KEY_RESOLUTION +  ", " +
			KEY_SYS_USER_ID +  ", " +
			KEY_CLOSED_BY_SYS_USER_ID +  ", " +
			KEY_CLOSED_DATE +  ", " +
			KEY_STATUS;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += systemIssue.mSystemIssueId +  ", " +
			GeneralUtils.getFormattedDateTimeString( systemIssue.mSystemIssueDate ) +  ", " +
			GeneralUtils.getFormattedString( systemIssue.mScreenName ) +  ", " +
			GeneralUtils.getFormattedString( systemIssue.mDescription ) +  ", " +
			GeneralUtils.getFormattedString( systemIssue.mImageFilePath ) +  ", " +
			systemIssue.mIssueTypeLookupId +  ", " +
			systemIssue.mIssuePriorityLookupId +  ", " +
			GeneralUtils.getFormattedString( systemIssue.mResolution ) +  ", " +
			systemIssue.mSysUserId +  ", " +
			systemIssue.mClosedBySysUserId +  ", " +
			GeneralUtils.getFormattedDateTimeString( systemIssue.mClosedDate ) +  ", " +
			systemIssue.mStatus + " );";
		return sqlStatement;
	}
*/
}
