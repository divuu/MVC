////////////////////////////////////////////////////////////////////////////

// FileName CountryDbAdaptor.java: CountryDbAdaptor Implementation file

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


public class CountryDbAdaptor extends AbstractDbAdaptor {

	public static final String KEY_COUNTRY_ID = "country_id";
	public static final String KEY_COUNTRY = "country";
	public static final String KEY_COUNTRY_CODE = "country_code";
	public static final String KEY_NO_OF_DIGITS = "no_of_digits";


	public CountryDbAdaptor() {
		mTableName = Country.DATABASE_TABLE_NAME;
	}

	public long insert( Country countryObj ) {

		if( countryObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_COUNTRY_ID + ", " + KEY_COUNTRY + ", " + KEY_COUNTRY_CODE + ", " + KEY_NO_OF_DIGITS;

		String values = "";
		for (int i = 1; i < Country.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + Country.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString, Statement.RETURN_GENERATED_KEYS );

			countryObj.mCountryId = 0;
			insertObject.setShort( 1, countryObj.mCountryId );
			insertObject.setString( 2, countryObj.mCountry );
			insertObject.setString( 3, countryObj.mCountryCode );
			insertObject.setInt( 4, countryObj.mNoOfDigits );

			MLogger.i( MLogger.MOD_DB, insertObject.toString() );

			int rowsInserted = insertObject.executeUpdate();

			if( rowsInserted > 0 ) { 
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			}
			ResultSet resultSet = insertObject.getGeneratedKeys();
			if( resultSet != null ) {
				resultSet.next();
				countryObj.mCountryId =(short) resultSet.getInt(1);
				resultSet.close();
			}

			if( insertObject != null )
				insertObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long update( Country countryObj, String whereClause ) {

		if( countryObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + Country.DATABASE_TABLE_NAME + " set "+ KEY_COUNTRY_ID + " =?, " + KEY_COUNTRY + " =?, " + KEY_COUNTRY_CODE + " =?, " + KEY_NO_OF_DIGITS + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setShort( 1, countryObj.mCountryId );
			updateObject.setString( 2, countryObj.mCountry );
			updateObject.setString( 3, countryObj.mCountryCode );
			updateObject.setInt( 4, countryObj.mNoOfDigits );

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
		sqlStmt = "DELETE from " + Country.DATABASE_TABLE_NAME;
		
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

	public ArrayList<Country> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + Country.DATABASE_TABLE_NAME;
			
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
				ArrayList<Country> list = new ArrayList<Country>();

				do {
					Country obj = new Country();
					obj.mCountryId = rs.getShort( KEY_COUNTRY_ID );
					obj.mCountry = rs.getString( KEY_COUNTRY );
					obj.mCountryCode = rs.getString( KEY_COUNTRY_CODE );
					obj.mNoOfDigits = rs.getInt( KEY_NO_OF_DIGITS );
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

	public Country getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + Country.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				Country obj = new Country();
				obj.mCountryId = rs.getShort( KEY_COUNTRY_ID );
				obj.mCountry = rs.getString( KEY_COUNTRY );
				obj.mCountryCode = rs.getString( KEY_COUNTRY_CODE );
				obj.mNoOfDigits = rs.getInt( KEY_NO_OF_DIGITS );
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
		String columnsNames = KEY_COUNTRY_ID +  ", " +
				KEY_COUNTRY +  ", " +
				KEY_COUNTRY_CODE +  ", " +
				KEY_NO_OF_DIGITS;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getShort(KEY_COUNTRY_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_COUNTRY) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_COUNTRY_CODE) ) +  ", " +
				resultSet.getInt(KEY_NO_OF_DIGITS) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(Country country, String tableName) {
		String columnsNames = KEY_COUNTRY_ID +  ", " +
			KEY_COUNTRY +  ", " +
			KEY_COUNTRY_CODE +  ", " +
			KEY_NO_OF_DIGITS;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += country.mCountryId +  ", " +
			GeneralUtils.getFormattedString( country.mCountry ) +  ", " +
			GeneralUtils.getFormattedString( country.mCountryCode ) +  ", " +
			country.mNoOfDigits + " );";
		return sqlStatement;
	}
*/
}
