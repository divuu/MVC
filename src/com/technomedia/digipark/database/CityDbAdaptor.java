////////////////////////////////////////////////////////////////////////////

// FileName CityDbAdaptor.java: CityDbAdaptor Implementation file

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


public class CityDbAdaptor extends AbstractDbAdaptor {

	public static final String KEY_CITY_ID = "city_id";
	public static final String KEY_CITY = "city";
	public static final String KEY_CITY_AREA_CODE = "city_area_code";
	public static final String KEY_TIME_ZONE = "time_zone";
	public static final String KEY_STATE_LOOKUP_ID = "state_lookup_id";
	public static final String KEY_COUNTRY_ID = "country_id";


	public CityDbAdaptor() {
		mTableName = City.DATABASE_TABLE_NAME;
	}

	public long insert( City cityObj ) {

		if( cityObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_CITY_ID + ", " + KEY_CITY + ", " + KEY_CITY_AREA_CODE + ", " + KEY_TIME_ZONE + ", " + KEY_STATE_LOOKUP_ID + ", " + KEY_COUNTRY_ID;

		String values = "";
		for (int i = 1; i < City.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + City.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString);
			insertObject.setInt( 1, cityObj.mCityId );
			insertObject.setString( 2, cityObj.mCity );
			insertObject.setString( 3, cityObj.mCityAreaCode );
			insertObject.setString( 4, cityObj.mTimeZone );
			insertObject.setInt( 5, cityObj.mStateLookupId );
			insertObject.setInt( 6, cityObj.mCountryId );

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

	public long update( City cityObj, String whereClause ) {

		if( cityObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + City.DATABASE_TABLE_NAME + " set "+ KEY_CITY_ID + " =?, " + KEY_CITY + " =?, " + KEY_CITY_AREA_CODE + " =?, " + KEY_TIME_ZONE + " =?, " + KEY_STATE_LOOKUP_ID + " =?, " + KEY_COUNTRY_ID + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setInt( 1, cityObj.mCityId );
			updateObject.setString( 2, cityObj.mCity );
			updateObject.setString( 3, cityObj.mCityAreaCode );
			updateObject.setString( 4, cityObj.mTimeZone );
			updateObject.setInt( 5, cityObj.mStateLookupId );
			updateObject.setInt( 6, cityObj.mCountryId );

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
		sqlStmt = "DELETE from " + City.DATABASE_TABLE_NAME;
		
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

	public ArrayList<City> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + City.DATABASE_TABLE_NAME;
			
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
				ArrayList<City> list = new ArrayList<City>();

				do {
					City obj = new City();
					obj.mCityId = rs.getInt( KEY_CITY_ID );
					obj.mCity = rs.getString( KEY_CITY );
					obj.mCityAreaCode = rs.getString( KEY_CITY_AREA_CODE );
					obj.mTimeZone = rs.getString( KEY_TIME_ZONE );
					obj.mStateLookupId = rs.getInt( KEY_STATE_LOOKUP_ID );
					obj.mCountryId = rs.getInt( KEY_COUNTRY_ID );
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

	public City getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + City.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				City obj = new City();
				obj.mCityId = rs.getInt( KEY_CITY_ID );
				obj.mCity = rs.getString( KEY_CITY );
				obj.mCityAreaCode = rs.getString( KEY_CITY_AREA_CODE );
				obj.mTimeZone = rs.getString( KEY_TIME_ZONE );
				obj.mStateLookupId = rs.getInt( KEY_STATE_LOOKUP_ID );
				obj.mCountryId = rs.getInt( KEY_COUNTRY_ID );
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
		String columnsNames = KEY_CITY_ID +  ", " +
				KEY_CITY +  ", " +
				KEY_CITY_AREA_CODE +  ", " +
				KEY_TIME_ZONE +  ", " +
				KEY_STATE_LOOKUP_ID +  ", " +
				KEY_COUNTRY_ID;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getInt(KEY_CITY_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_CITY) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_CITY_AREA_CODE) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_TIME_ZONE) ) +  ", " +
				resultSet.getInt(KEY_STATE_LOOKUP_ID) +  ", " +
				resultSet.getInt(KEY_COUNTRY_ID) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(City city, String tableName) {
		String columnsNames = KEY_CITY_ID +  ", " +
			KEY_CITY +  ", " +
			KEY_CITY_AREA_CODE +  ", " +
			KEY_TIME_ZONE +  ", " +
			KEY_STATE_LOOKUP_ID +  ", " +
			KEY_COUNTRY_ID;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += city.mCityId +  ", " +
			GeneralUtils.getFormattedString( city.mCity ) +  ", " +
			GeneralUtils.getFormattedString( city.mCityAreaCode ) +  ", " +
			GeneralUtils.getFormattedString( city.mTimeZone ) +  ", " +
			city.mStateLookupId +  ", " +
			city.mCountryId + " );";
		return sqlStatement;
	}
*/
}
