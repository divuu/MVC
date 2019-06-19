////////////////////////////////////////////////////////////////////////////

// FileName LookupDbAdaptor.java: LookupDbAdaptor Implementation file

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


public class LookupDbAdaptor extends AbstractDbAdaptor {

	public static final String KEY_LOOKUP_ID = "lookup_id";
	public static final String KEY_LOOKUP_GROUP_ID = "lookup_group_id";
	public static final String KEY_TABLE_NAME = "table_name";
	public static final String KEY_COLUMN_NAME = "column_name";
	public static final String KEY_LOOKUP_NAME = "lookup_name";
	public static final String KEY_LOOKUP_VALUE = "lookup_value";


	public LookupDbAdaptor() {
		mTableName = Lookup.DATABASE_TABLE_NAME;
	}

	public long insert( Lookup lookupObj ) {

		if( lookupObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_LOOKUP_ID + ", " + KEY_LOOKUP_GROUP_ID + ", " + KEY_TABLE_NAME + ", " + KEY_COLUMN_NAME + ", " + KEY_LOOKUP_NAME + ", " + KEY_LOOKUP_VALUE;

		String values = "";
		for (int i = 1; i < Lookup.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + Lookup.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString, Statement.RETURN_GENERATED_KEYS );

			lookupObj.mLookupId = 0;
			insertObject.setInt( 1, lookupObj.mLookupId );
			insertObject.setInt( 2, lookupObj.mLookupGroupId );
			insertObject.setString( 3, lookupObj.mTableName );
			insertObject.setString( 4, lookupObj.mColumnName );
			insertObject.setString( 5, lookupObj.mLookupName );
			insertObject.setInt( 6, lookupObj.mLookupValue );

			MLogger.i( MLogger.MOD_DB, insertObject.toString() );

			int rowsInserted = insertObject.executeUpdate();

			if( rowsInserted > 0 ) { 
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			}
			ResultSet resultSet = insertObject.getGeneratedKeys();
			if( resultSet != null ) {
				resultSet.next();
				lookupObj.mLookupId = resultSet.getInt(1);
				resultSet.close();
			}

			if( insertObject != null )
				insertObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long update( Lookup lookupObj, String whereClause ) {

		if( lookupObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + Lookup.DATABASE_TABLE_NAME + " set "+ KEY_LOOKUP_ID + " =?, " + KEY_LOOKUP_GROUP_ID + " =?, " + KEY_TABLE_NAME + " =?, " + KEY_COLUMN_NAME + " =?, " + KEY_LOOKUP_NAME + " =?, " + KEY_LOOKUP_VALUE + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setInt( 1, lookupObj.mLookupId );
			updateObject.setInt( 2, lookupObj.mLookupGroupId );
			updateObject.setString( 3, lookupObj.mTableName );
			updateObject.setString( 4, lookupObj.mColumnName );
			updateObject.setString( 5, lookupObj.mLookupName );
			updateObject.setInt( 6, lookupObj.mLookupValue );

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
		sqlStmt = "DELETE from " + Lookup.DATABASE_TABLE_NAME;
		
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

	public ArrayList<Lookup> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + Lookup.DATABASE_TABLE_NAME;
			
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
				ArrayList<Lookup> list = new ArrayList<Lookup>();

				do {
					Lookup obj = new Lookup();
					obj.mLookupId = rs.getInt( KEY_LOOKUP_ID );
					obj.mLookupGroupId = rs.getInt( KEY_LOOKUP_GROUP_ID );
					obj.mTableName = rs.getString( KEY_TABLE_NAME );
					obj.mColumnName = rs.getString( KEY_COLUMN_NAME );
					obj.mLookupName = rs.getString( KEY_LOOKUP_NAME );
					obj.mLookupValue = rs.getInt( KEY_LOOKUP_VALUE );
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

	public Lookup getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + Lookup.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				Lookup obj = new Lookup();
				obj.mLookupId = rs.getInt( KEY_LOOKUP_ID );
				obj.mLookupGroupId = rs.getInt( KEY_LOOKUP_GROUP_ID );
				obj.mTableName = rs.getString( KEY_TABLE_NAME );
				obj.mColumnName = rs.getString( KEY_COLUMN_NAME );
				obj.mLookupName = rs.getString( KEY_LOOKUP_NAME );
				obj.mLookupValue = rs.getInt( KEY_LOOKUP_VALUE );
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
		String columnsNames = KEY_LOOKUP_ID +  ", " +
				KEY_LOOKUP_GROUP_ID +  ", " +
				KEY_TABLE_NAME +  ", " +
				KEY_COLUMN_NAME +  ", " +
				KEY_LOOKUP_NAME +  ", " +
				KEY_LOOKUP_VALUE;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getInt(KEY_LOOKUP_ID) +  ", " +
				resultSet.getInt(KEY_LOOKUP_GROUP_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_TABLE_NAME) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_COLUMN_NAME) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_LOOKUP_NAME) ) +  ", " +
				resultSet.getInt(KEY_LOOKUP_VALUE) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(Lookup lookup, String tableName) {
		String columnsNames = KEY_LOOKUP_ID +  ", " +
			KEY_LOOKUP_GROUP_ID +  ", " +
			KEY_TABLE_NAME +  ", " +
			KEY_COLUMN_NAME +  ", " +
			KEY_LOOKUP_NAME +  ", " +
			KEY_LOOKUP_VALUE;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += lookup.mLookupId +  ", " +
			lookup.mLookupGroupId +  ", " +
			GeneralUtils.getFormattedString( lookup.mTableName ) +  ", " +
			GeneralUtils.getFormattedString( lookup.mColumnName ) +  ", " +
			GeneralUtils.getFormattedString( lookup.mLookupName ) +  ", " +
			lookup.mLookupValue + " );";
		return sqlStatement;
	}
*/
}
