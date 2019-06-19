////////////////////////////////////////////////////////////////////////////

// FileName CurrencyDbAdaptor.java: CurrencyDbAdaptor Implementation file

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


public class CurrencyDbAdaptor extends AbstractDbAdaptor {

	public static final String KEY_CURRENCY_ID = "currency_id";
	public static final String KEY_CURRENCY_SYMBOL = "currency_symbol";
	public static final String KEY_CURRENCY_WORD = "currency_word";
	public static final String KEY_CURRENCY_DECIMAL = "currency_decimal";
	public static final String KEY_CONVERSION_RATE_WITH_USD = "conversion_rate_with_usd";


	public CurrencyDbAdaptor() {
		mTableName = Currency.DATABASE_TABLE_NAME;
	}

	public long insert( Currency currencyObj ) {

		if( currencyObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_CURRENCY_ID + ", " + KEY_CURRENCY_SYMBOL + ", " + KEY_CURRENCY_WORD + ", " + KEY_CURRENCY_DECIMAL + ", " + KEY_CONVERSION_RATE_WITH_USD;

		String values = "";
		for (int i = 1; i < Currency.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + Currency.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString, Statement.RETURN_GENERATED_KEYS );

			currencyObj.mCurrencyId = 0;
			insertObject.setInt( 1, currencyObj.mCurrencyId );
			insertObject.setString( 2, currencyObj.mCurrencySymbol );
			insertObject.setString( 3, currencyObj.mCurrencyWord );
			insertObject.setString( 4, currencyObj.mCurrencyDecimal );
			insertObject.setFloat( 5, currencyObj.mConversionRateWithUsd );

			MLogger.i( MLogger.MOD_DB, insertObject.toString() );

			int rowsInserted = insertObject.executeUpdate();

			if( rowsInserted > 0 ) { 
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			}
			ResultSet resultSet = insertObject.getGeneratedKeys();
			if( resultSet != null ) {
				resultSet.next();
				currencyObj.mCurrencyId = resultSet.getInt(1);
				resultSet.close();
			}

			if( insertObject != null )
				insertObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long update( Currency currencyObj, String whereClause ) {

		if( currencyObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + Currency.DATABASE_TABLE_NAME + " set "+ KEY_CURRENCY_ID + " =?, " + KEY_CURRENCY_SYMBOL + " =?, " + KEY_CURRENCY_WORD + " =?, " + KEY_CURRENCY_DECIMAL + " =?, " + KEY_CONVERSION_RATE_WITH_USD + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setInt( 1, currencyObj.mCurrencyId );
			updateObject.setString( 2, currencyObj.mCurrencySymbol );
			updateObject.setString( 3, currencyObj.mCurrencyWord );
			updateObject.setString( 4, currencyObj.mCurrencyDecimal );
			updateObject.setFloat( 5, currencyObj.mConversionRateWithUsd );

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
		sqlStmt = "DELETE from " + Currency.DATABASE_TABLE_NAME;
		
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

	public ArrayList<Currency> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + Currency.DATABASE_TABLE_NAME;
			
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
				ArrayList<Currency> list = new ArrayList<Currency>();

				do {
					Currency obj = new Currency();
					obj.mCurrencyId = rs.getInt( KEY_CURRENCY_ID );
					obj.mCurrencySymbol = rs.getString( KEY_CURRENCY_SYMBOL );
					obj.mCurrencyWord = rs.getString( KEY_CURRENCY_WORD );
					obj.mCurrencyDecimal = rs.getString( KEY_CURRENCY_DECIMAL );
					obj.mConversionRateWithUsd = rs.getFloat( KEY_CONVERSION_RATE_WITH_USD );
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

	public Currency getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + Currency.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				Currency obj = new Currency();
				obj.mCurrencyId = rs.getInt( KEY_CURRENCY_ID );
				obj.mCurrencySymbol = rs.getString( KEY_CURRENCY_SYMBOL );
				obj.mCurrencyWord = rs.getString( KEY_CURRENCY_WORD );
				obj.mCurrencyDecimal = rs.getString( KEY_CURRENCY_DECIMAL );
				obj.mConversionRateWithUsd = rs.getFloat( KEY_CONVERSION_RATE_WITH_USD );
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
		String columnsNames = KEY_CURRENCY_ID +  ", " +
				KEY_CURRENCY_SYMBOL +  ", " +
				KEY_CURRENCY_WORD +  ", " +
				KEY_CURRENCY_DECIMAL +  ", " +
				KEY_CONVERSION_RATE_WITH_USD;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getInt(KEY_CURRENCY_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_CURRENCY_SYMBOL) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_CURRENCY_WORD) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_CURRENCY_DECIMAL) ) +  ", " +
				resultSet.getFloat(KEY_CONVERSION_RATE_WITH_USD) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(Currency currency, String tableName) {
		String columnsNames = KEY_CURRENCY_ID +  ", " +
			KEY_CURRENCY_SYMBOL +  ", " +
			KEY_CURRENCY_WORD +  ", " +
			KEY_CURRENCY_DECIMAL +  ", " +
			KEY_CONVERSION_RATE_WITH_USD;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += currency.mCurrencyId +  ", " +
			GeneralUtils.getFormattedString( currency.mCurrencySymbol ) +  ", " +
			GeneralUtils.getFormattedString( currency.mCurrencyWord ) +  ", " +
			GeneralUtils.getFormattedString( currency.mCurrencyDecimal ) +  ", " +
			currency.mConversionRateWithUsd + " );";
		return sqlStatement;
	}
*/
}
