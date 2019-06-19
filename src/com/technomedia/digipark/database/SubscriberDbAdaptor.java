////////////////////////////////////////////////////////////////////////////

// FileName SubscriberDbAdaptor.java: SubscriberDbAdaptor Implementation file

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


public class SubscriberDbAdaptor extends AbstractDbAdaptor {

	public static final String KEY_SUBSCRIBER_ID = "subscriber_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_MOBILE_NUMBER = "mobile_number";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_DATE_OF_BIRTH = "date_of_birth";
	public static final String KEY_DATE_OF_REGISTRATION = "date_of_registration";
	public static final String KEY_PLAN_ID = "plan_id";
	public static final String KEY_PLAN_START_DATE = "plan_start_date";
	public static final String KEY_PLAN_END_DATE = "plan_end_date";
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_AMOUNT_PAID = "amount_paid";
	public static final String KEY_DISCOUNT_AMOUNT = "discount_amount";
	public static final String KEY_ACTIVE_LOOKUP_ID = "active_lookup_id";
	public static final String KEY_STATUS = "status";
	public static final String KEY_REMARKS = "remarks";
	public static final String KEY_EXTRA_DATA = "extra_data";


	public SubscriberDbAdaptor() {
		mTableName = Subscriber.DATABASE_TABLE_NAME;
	}

	public long insert( Subscriber subscriberObj ) {

		if( subscriberObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_SUBSCRIBER_ID + ", " + KEY_NAME + ", " + KEY_MOBILE_NUMBER + ", " + KEY_EMAIL + ", " + KEY_GENDER + ", " + KEY_DATE_OF_BIRTH + ", " + KEY_DATE_OF_REGISTRATION + ", " + KEY_PLAN_ID + ", " + KEY_PLAN_START_DATE + ", " + KEY_PLAN_END_DATE + ", " + KEY_AMOUNT + ", " + KEY_AMOUNT_PAID + ", " + KEY_DISCOUNT_AMOUNT + ", " + KEY_ACTIVE_LOOKUP_ID + ", " + KEY_STATUS + ", " + KEY_REMARKS + ", " + KEY_EXTRA_DATA;

		String values = "";
		for (int i = 1; i < Subscriber.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + Subscriber.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString, Statement.RETURN_GENERATED_KEYS );

			subscriberObj.mSubscriberId = 0;
			insertObject.setInt( 1, subscriberObj.mSubscriberId );
			insertObject.setString( 2, subscriberObj.mName );
			insertObject.setString( 3, subscriberObj.mMobileNumber );
			insertObject.setString( 4, subscriberObj.mEmail );
			insertObject.setString( 5, String.valueOf( subscriberObj.mGender ) );
			insertObject.setDate( 6, GeneralUtils.getDate(subscriberObj.mDateOfBirth));
			insertObject.setTimestamp( 7, GeneralUtils.getDateTime(subscriberObj.mDateOfRegistration));
			insertObject.setInt( 8, subscriberObj.mPlanId );
			insertObject.setTimestamp( 9, GeneralUtils.getDateTime(subscriberObj.mPlanStartDate));
			insertObject.setTimestamp( 10, GeneralUtils.getDateTime(subscriberObj.mPlanEndDate));
			insertObject.setFloat( 11, subscriberObj.mAmount );
			insertObject.setFloat( 12, subscriberObj.mAmountPaid );
			insertObject.setFloat( 13, subscriberObj.mDiscountAmount );
			insertObject.setInt( 14, subscriberObj.mActiveLookupId );
			insertObject.setInt( 15, subscriberObj.mStatus );
			insertObject.setString( 16, subscriberObj.mRemarks );
			insertObject.setString( 17, subscriberObj.mExtraData );

			MLogger.i( MLogger.MOD_DB, insertObject.toString() );

			int rowsInserted = insertObject.executeUpdate();

			if( rowsInserted > 0 ) { 
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			}
			ResultSet resultSet = insertObject.getGeneratedKeys();
			if( resultSet != null ) {
				resultSet.next();
				subscriberObj.mSubscriberId = resultSet.getInt(1);
				resultSet.close();
			}

			if( insertObject != null )
				insertObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long update( Subscriber subscriberObj, String whereClause ) {

		if( subscriberObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + Subscriber.DATABASE_TABLE_NAME + " set "+ KEY_SUBSCRIBER_ID + " =?, " + KEY_NAME + " =?, " + KEY_MOBILE_NUMBER + " =?, " + KEY_EMAIL + " =?, " + KEY_GENDER + " =?, " + KEY_DATE_OF_BIRTH + " =?, " + KEY_DATE_OF_REGISTRATION + " =?, " + KEY_PLAN_ID + " =?, " + KEY_PLAN_START_DATE + " =?, " + KEY_PLAN_END_DATE + " =?, " + KEY_AMOUNT + " =?, " + KEY_AMOUNT_PAID + " =?, " + KEY_DISCOUNT_AMOUNT + " =?, " + KEY_ACTIVE_LOOKUP_ID + " =?, " + KEY_STATUS + " =?, " + KEY_REMARKS + " =?, " + KEY_EXTRA_DATA + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setInt( 1, subscriberObj.mSubscriberId );
			updateObject.setString( 2, subscriberObj.mName );
			updateObject.setString( 3, subscriberObj.mMobileNumber );
			updateObject.setString( 4, subscriberObj.mEmail );
			updateObject.setString( 5, String.valueOf( subscriberObj.mGender ) );
			updateObject.setDate( 6, GeneralUtils.getDate(subscriberObj.mDateOfBirth));
			updateObject.setTimestamp( 7, GeneralUtils.getDateTime(subscriberObj.mDateOfRegistration));
			updateObject.setInt( 8, subscriberObj.mPlanId );
			updateObject.setTimestamp( 9, GeneralUtils.getDateTime(subscriberObj.mPlanStartDate));
			updateObject.setTimestamp( 10, GeneralUtils.getDateTime(subscriberObj.mPlanEndDate));
			updateObject.setFloat( 11, subscriberObj.mAmount );
			updateObject.setFloat( 12, subscriberObj.mAmountPaid );
			updateObject.setFloat( 13, subscriberObj.mDiscountAmount );
			updateObject.setInt( 14, subscriberObj.mActiveLookupId );
			updateObject.setInt( 15, subscriberObj.mStatus );
			updateObject.setString( 16, subscriberObj.mRemarks );
			updateObject.setString( 17, subscriberObj.mExtraData );

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
		sqlStmt = "DELETE from " + Subscriber.DATABASE_TABLE_NAME;
		
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

	public ArrayList<Subscriber> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + Subscriber.DATABASE_TABLE_NAME;
			
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
				ArrayList<Subscriber> list = new ArrayList<Subscriber>();

				do {
					Subscriber obj = new Subscriber();
					obj.mSubscriberId = rs.getInt( KEY_SUBSCRIBER_ID );
					obj.mName = rs.getString( KEY_NAME );
					obj.mMobileNumber = rs.getString( KEY_MOBILE_NUMBER );
					obj.mEmail = rs.getString( KEY_EMAIL );
					obj.mGender = rs.getString( KEY_GENDER ).charAt(0);
					obj.mDateOfBirth = GeneralUtils.getDateTime( rs.getDate( KEY_DATE_OF_BIRTH ) );
					obj.mDateOfRegistration = GeneralUtils.getDateTime( rs.getTimestamp( KEY_DATE_OF_REGISTRATION ) );
					obj.mPlanId = rs.getInt( KEY_PLAN_ID );
					obj.mPlanStartDate = GeneralUtils.getDateTime( rs.getTimestamp( KEY_PLAN_START_DATE ) );
					obj.mPlanEndDate = GeneralUtils.getDateTime( rs.getTimestamp( KEY_PLAN_END_DATE ) );
					obj.mAmount = rs.getFloat( KEY_AMOUNT );
					obj.mAmountPaid = rs.getFloat( KEY_AMOUNT_PAID );
					obj.mDiscountAmount = rs.getFloat( KEY_DISCOUNT_AMOUNT );
					obj.mActiveLookupId = rs.getInt( KEY_ACTIVE_LOOKUP_ID );
					obj.mStatus = rs.getInt( KEY_STATUS );
					obj.mRemarks = rs.getString( KEY_REMARKS );
					obj.mExtraData = rs.getString( KEY_EXTRA_DATA );
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

	public Subscriber getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + Subscriber.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				Subscriber obj = new Subscriber();
				obj.mSubscriberId = rs.getInt( KEY_SUBSCRIBER_ID );
				obj.mName = rs.getString( KEY_NAME );
				obj.mMobileNumber = rs.getString( KEY_MOBILE_NUMBER );
				obj.mEmail = rs.getString( KEY_EMAIL );
				obj.mGender = rs.getString( KEY_GENDER ).charAt(0);
				obj.mDateOfBirth = GeneralUtils.getDateTime( rs.getDate( KEY_DATE_OF_BIRTH ) );
				obj.mDateOfRegistration = GeneralUtils.getDateTime( rs.getTimestamp( KEY_DATE_OF_REGISTRATION ) );
				obj.mPlanId = rs.getInt( KEY_PLAN_ID );
				obj.mPlanStartDate = GeneralUtils.getDateTime( rs.getTimestamp( KEY_PLAN_START_DATE ) );
				obj.mPlanEndDate = GeneralUtils.getDateTime( rs.getTimestamp( KEY_PLAN_END_DATE ) );
				obj.mAmount = rs.getFloat( KEY_AMOUNT );
				obj.mAmountPaid = rs.getFloat( KEY_AMOUNT_PAID );
				obj.mDiscountAmount = rs.getFloat( KEY_DISCOUNT_AMOUNT );
				obj.mActiveLookupId = rs.getInt( KEY_ACTIVE_LOOKUP_ID );
				obj.mStatus = rs.getInt( KEY_STATUS );
				obj.mRemarks = rs.getString( KEY_REMARKS );
				obj.mExtraData = rs.getString( KEY_EXTRA_DATA );
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
		String columnsNames = KEY_SUBSCRIBER_ID +  ", " +
				KEY_NAME +  ", " +
				KEY_MOBILE_NUMBER +  ", " +
				KEY_EMAIL +  ", " +
				KEY_GENDER +  ", " +
				KEY_DATE_OF_BIRTH +  ", " +
				KEY_DATE_OF_REGISTRATION +  ", " +
				KEY_PLAN_ID +  ", " +
				KEY_PLAN_START_DATE +  ", " +
				KEY_PLAN_END_DATE +  ", " +
				KEY_AMOUNT +  ", " +
				KEY_AMOUNT_PAID +  ", " +
				KEY_DISCOUNT_AMOUNT +  ", " +
				KEY_ACTIVE_LOOKUP_ID +  ", " +
				KEY_STATUS +  ", " +
				KEY_REMARKS +  ", " +
				KEY_EXTRA_DATA;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getInt(KEY_SUBSCRIBER_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_NAME) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_MOBILE_NUMBER) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_EMAIL) ) +  ", " +
				"'" + resultSet.getString(KEY_GENDER).charAt(0) + "'"  +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getDate(KEY_DATE_OF_BIRTH))  +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getTimestamp(KEY_DATE_OF_REGISTRATION))  +  ", " +
				resultSet.getInt(KEY_PLAN_ID) +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getTimestamp(KEY_PLAN_START_DATE))  +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getTimestamp(KEY_PLAN_END_DATE))  +  ", " +
				resultSet.getFloat(KEY_AMOUNT) +  ", " +
				resultSet.getFloat(KEY_AMOUNT_PAID) +  ", " +
				resultSet.getFloat(KEY_DISCOUNT_AMOUNT) +  ", " +
				resultSet.getInt(KEY_ACTIVE_LOOKUP_ID) +  ", " +
				resultSet.getInt(KEY_STATUS) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_REMARKS) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_EXTRA_DATA) ) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(Subscriber subscriber, String tableName) {
		String columnsNames = KEY_SUBSCRIBER_ID +  ", " +
			KEY_NAME +  ", " +
			KEY_MOBILE_NUMBER +  ", " +
			KEY_EMAIL +  ", " +
			KEY_GENDER +  ", " +
			KEY_DATE_OF_BIRTH +  ", " +
			KEY_DATE_OF_REGISTRATION +  ", " +
			KEY_PLAN_ID +  ", " +
			KEY_PLAN_START_DATE +  ", " +
			KEY_PLAN_END_DATE +  ", " +
			KEY_AMOUNT +  ", " +
			KEY_AMOUNT_PAID +  ", " +
			KEY_DISCOUNT_AMOUNT +  ", " +
			KEY_ACTIVE_LOOKUP_ID +  ", " +
			KEY_STATUS +  ", " +
			KEY_REMARKS +  ", " +
			KEY_EXTRA_DATA;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += subscriber.mSubscriberId +  ", " +
			GeneralUtils.getFormattedString( subscriber.mName ) +  ", " +
			GeneralUtils.getFormattedString( subscriber.mMobileNumber ) +  ", " +
			GeneralUtils.getFormattedString( subscriber.mEmail ) +  ", " +
			subscriber.mGender +  ", " +
			subscriber.mDateOfBirth +  ", " +
			GeneralUtils.getFormattedDateTimeString( subscriber.mDateOfRegistration ) +  ", " +
			subscriber.mPlanId +  ", " +
			GeneralUtils.getFormattedDateTimeString( subscriber.mPlanStartDate ) +  ", " +
			GeneralUtils.getFormattedDateTimeString( subscriber.mPlanEndDate ) +  ", " +
			subscriber.mAmount +  ", " +
			subscriber.mAmountPaid +  ", " +
			subscriber.mDiscountAmount +  ", " +
			subscriber.mActiveLookupId +  ", " +
			subscriber.mStatus +  ", " +
			GeneralUtils.getFormattedString( subscriber.mRemarks ) +  ", " +
			GeneralUtils.getFormattedString( subscriber.mExtraData ) + " );";
		return sqlStatement;
	}
*/
}
