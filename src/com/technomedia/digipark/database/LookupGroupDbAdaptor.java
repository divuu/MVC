////////////////////////////////////////////////////////////////////////////

// FileName LookupGroupDbAdaptor.java: LookupGroupDbAdaptor Implementation file

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


public class LookupGroupDbAdaptor extends AbstractDbAdaptor {

	public static final String KEY_LOOKUP_GROUP_ID = "lookup_group_id";
	public static final String KEY_LOOKUP_GROUP_NAME = "lookup_group_name";


	public LookupGroupDbAdaptor() {
		mTableName = LookupGroup.DATABASE_TABLE_NAME;
	}

	public long insert( LookupGroup lookupGroupObj ) {

		if( lookupGroupObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_LOOKUP_GROUP_ID + ", " + KEY_LOOKUP_GROUP_NAME;

		String values = "";
		for (int i = 1; i < LookupGroup.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + LookupGroup.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString);
			insertObject.setInt( 1, lookupGroupObj.mLookupGroupId );
			insertObject.setString( 2, lookupGroupObj.mLookupGroupName );

			MLogger.i( MLogger.MOD_DB, insertObject.toString() );

			int rowsInserted = insertObject.executeUpdate();

			if( rowsInserted > 0 ) { 
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			}
			if( insertObject != null )
				insertObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long update( LookupGroup lookupGroupObj, String whereClause ) {

		if( lookupGroupObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + LookupGroup.DATABASE_TABLE_NAME + " set "+ KEY_LOOKUP_GROUP_ID + " =?, " + KEY_LOOKUP_GROUP_NAME + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setInt( 1, lookupGroupObj.mLookupGroupId );
			updateObject.setString( 2, lookupGroupObj.mLookupGroupName );

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
		sqlStmt = "DELETE from " + LookupGroup.DATABASE_TABLE_NAME;
		
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

	public ArrayList<LookupGroup> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + LookupGroup.DATABASE_TABLE_NAME;
			
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
				ArrayList<LookupGroup> list = new ArrayList<LookupGroup>();

				do {
					LookupGroup obj = new LookupGroup();
					obj.mLookupGroupId = rs.getInt( KEY_LOOKUP_GROUP_ID );
					obj.mLookupGroupName = rs.getString( KEY_LOOKUP_GROUP_NAME );
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

	public LookupGroup getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + LookupGroup.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				LookupGroup obj = new LookupGroup();
				obj.mLookupGroupId = rs.getInt( KEY_LOOKUP_GROUP_ID );
				obj.mLookupGroupName = rs.getString( KEY_LOOKUP_GROUP_NAME );
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
		String columnsNames = KEY_LOOKUP_GROUP_ID +  ", " +
				KEY_LOOKUP_GROUP_NAME;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getInt(KEY_LOOKUP_GROUP_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_LOOKUP_GROUP_NAME) ) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(LookupGroup lookupGroup, String tableName) {
		String columnsNames = KEY_LOOKUP_GROUP_ID +  ", " +
			KEY_LOOKUP_GROUP_NAME;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += lookupGroup.mLookupGroupId +  ", " +
			GeneralUtils.getFormattedString( lookupGroup.mLookupGroupName ) + " );";
		return sqlStatement;
	}
*/
}
