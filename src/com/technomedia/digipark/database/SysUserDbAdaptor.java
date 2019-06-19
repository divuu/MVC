////////////////////////////////////////////////////////////////////////////

// FileName SysUserDbAdaptor.java: SysUserDbAdaptor Implementation file

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


public class SysUserDbAdaptor extends AbstractDbAdaptor {

	public static final String KEY_SYS_USER_ID = "sys_user_id";
	public static final String KEY_ROLE_ID = "role_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_MOBILE_NUMBER = "mobile_number";
	public static final String KEY_MOBILE_NUMBER_2 = "mobile_number_2";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_CITY_ID = "city_id";
	public static final String KEY_COUNTRY_ID = "country_id";
	public static final String KEY_DESIGNATION_LOOKUP_ID = "designation_lookup_id";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_GENDER_LOOKUP_ID = "gender_lookup_id";
	public static final String KEY_DATE_OF_BIRTH = "date_of_birth";
	public static final String KEY_DRIVING_LICENSE_NUMBER = "driving_license_number";
	public static final String KEY_PASSPORT_NUMBER = "passport_number";
	public static final String KEY_REMARKS = "remarks";
	public static final String KEY_PHOTO = "photo";
	public static final String KEY_DATE_OF_REGISTRATION = "date_of_registration";
	public static final String KEY_LAST_LOGIN_DATE = "last_login_date";
	public static final String KEY_NO_OF_LOGINS = "no_of_logins";
	public static final String KEY_CREATE_BY_SYS_USER_ID = "create_by_sys_user_id";
	public static final String KEY_ACTIVE_LOOKUP_ID = "active_lookup_id";
	public static final String KEY_PARKING_AREA_ID = "parking_area_id";
	public static final String KEY_LOGGED_IN_PARKING_AREA_GATE_ID = "logged_in_parking_area_gate_id";
	public static final String KEY_STATUS = "status";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_SALT = "salt";
	public static final String KEY_EXTRA_DATA = "extra_data";


	public SysUserDbAdaptor() {
		mTableName = SysUser.DATABASE_TABLE_NAME;
	}

	public long insert( SysUser sysUserObj ) {

		if( sysUserObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_SYS_USER_ID + ", " + KEY_ROLE_ID + ", " + KEY_NAME + ", " + KEY_MOBILE_NUMBER + ", " + KEY_MOBILE_NUMBER_2 + ", " + KEY_EMAIL + ", " + KEY_ADDRESS + ", " + KEY_CITY_ID + ", " + KEY_COUNTRY_ID + ", " + KEY_DESIGNATION_LOOKUP_ID + ", " + KEY_LATITUDE + ", " + KEY_LONGITUDE + ", " + KEY_GENDER_LOOKUP_ID + ", " + KEY_DATE_OF_BIRTH + ", " + KEY_DRIVING_LICENSE_NUMBER + ", " + KEY_PASSPORT_NUMBER + ", " + KEY_REMARKS + ", " + KEY_PHOTO + ", " + KEY_DATE_OF_REGISTRATION + ", " + KEY_LAST_LOGIN_DATE + ", " + KEY_NO_OF_LOGINS + ", " + KEY_CREATE_BY_SYS_USER_ID + ", " + KEY_ACTIVE_LOOKUP_ID + ", " + KEY_PARKING_AREA_ID + ", " + KEY_LOGGED_IN_PARKING_AREA_GATE_ID + ", " + KEY_STATUS + ", " + KEY_PASSWORD + ", " + KEY_SALT + ", " + KEY_EXTRA_DATA;

		String values = "";
		for (int i = 1; i < SysUser.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + SysUser.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString, Statement.RETURN_GENERATED_KEYS );

			sysUserObj.mSysUserId = 0;
			insertObject.setInt( 1, sysUserObj.mSysUserId );
			insertObject.setShort( 2, sysUserObj.mRoleId );
			insertObject.setString( 3, sysUserObj.mName );
			insertObject.setString( 4, sysUserObj.mMobileNumber );
			insertObject.setString( 5, sysUserObj.mMobileNumber2 );
			insertObject.setString( 6, sysUserObj.mEmail );
			insertObject.setString( 7, sysUserObj.mAddress );
			insertObject.setInt( 8, sysUserObj.mCityId );
			insertObject.setInt( 9, sysUserObj.mCountryId );
			insertObject.setInt( 10, sysUserObj.mDesignationLookupId );
			insertObject.setFloat( 11, sysUserObj.mLatitude );
			insertObject.setFloat( 12, sysUserObj.mLongitude );
			insertObject.setInt( 13, sysUserObj.mGenderLookupId );
			insertObject.setDate( 14, GeneralUtils.getDate(sysUserObj.mDateOfBirth));
			insertObject.setString( 15, sysUserObj.mDrivingLicenseNumber );
			insertObject.setString( 16, sysUserObj.mPassportNumber );
			insertObject.setString( 17, sysUserObj.mRemarks );
			insertObject.setString( 18, sysUserObj.mPhoto );
			insertObject.setTimestamp( 19, GeneralUtils.getDateTime(sysUserObj.mDateOfRegistration));
			insertObject.setTimestamp( 20, GeneralUtils.getDateTime(sysUserObj.mLastLoginDate));
			insertObject.setInt( 21, sysUserObj.mNoOfLogins );
			insertObject.setInt( 22, sysUserObj.mCreateBySysUserId );
			insertObject.setInt( 23, sysUserObj.mActiveLookupId );
			insertObject.setInt( 24, sysUserObj.mParkingAreaId );
			insertObject.setInt( 25, sysUserObj.mLoggedInParkingAreaGateId );
			insertObject.setInt( 26, sysUserObj.mStatus );
			insertObject.setString( 27, sysUserObj.mPassword );
			insertObject.setString( 28, sysUserObj.mSalt );
			insertObject.setString( 29, sysUserObj.mExtraData );

			MLogger.i( MLogger.MOD_DB, insertObject.toString() );

			int rowsInserted = insertObject.executeUpdate();

			if( rowsInserted > 0 ) { 
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			}
			ResultSet resultSet = insertObject.getGeneratedKeys();
			if( resultSet != null ) {
				resultSet.next();
				sysUserObj.mSysUserId = resultSet.getInt(1);
				resultSet.close();
			}

			if( insertObject != null )
				insertObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long update( SysUser sysUserObj, String whereClause ) {

		if( sysUserObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + SysUser.DATABASE_TABLE_NAME + " set "+ KEY_SYS_USER_ID + " =?, " + KEY_ROLE_ID + " =?, " + KEY_NAME + " =?, " + KEY_MOBILE_NUMBER + " =?, " + KEY_MOBILE_NUMBER_2 + " =?, " + KEY_EMAIL + " =?, " + KEY_ADDRESS + " =?, " + KEY_CITY_ID + " =?, " + KEY_COUNTRY_ID + " =?, " + KEY_DESIGNATION_LOOKUP_ID + " =?, " + KEY_LATITUDE + " =?, " + KEY_LONGITUDE + " =?, " + KEY_GENDER_LOOKUP_ID + " =?, " + KEY_DATE_OF_BIRTH + " =?, " + KEY_DRIVING_LICENSE_NUMBER + " =?, " + KEY_PASSPORT_NUMBER + " =?, " + KEY_REMARKS + " =?, " + KEY_PHOTO + " =?, " + KEY_DATE_OF_REGISTRATION + " =?, " + KEY_LAST_LOGIN_DATE + " =?, " + KEY_NO_OF_LOGINS + " =?, " + KEY_CREATE_BY_SYS_USER_ID + " =?, " + KEY_ACTIVE_LOOKUP_ID + " =?, " + KEY_PARKING_AREA_ID + " =?, " + KEY_LOGGED_IN_PARKING_AREA_GATE_ID + " =?, " + KEY_STATUS + " =?, " + KEY_PASSWORD + " =?, " + KEY_SALT + " =?, " + KEY_EXTRA_DATA + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setInt( 1, sysUserObj.mSysUserId );
			updateObject.setShort( 2, sysUserObj.mRoleId );
			updateObject.setString( 3, sysUserObj.mName );
			updateObject.setString( 4, sysUserObj.mMobileNumber );
			updateObject.setString( 5, sysUserObj.mMobileNumber2 );
			updateObject.setString( 6, sysUserObj.mEmail );
			updateObject.setString( 7, sysUserObj.mAddress );
			updateObject.setInt( 8, sysUserObj.mCityId );
			updateObject.setInt( 9, sysUserObj.mCountryId );
			updateObject.setInt( 10, sysUserObj.mDesignationLookupId );
			updateObject.setFloat( 11, sysUserObj.mLatitude );
			updateObject.setFloat( 12, sysUserObj.mLongitude );
			updateObject.setInt( 13, sysUserObj.mGenderLookupId );
			updateObject.setDate( 14, GeneralUtils.getDate(sysUserObj.mDateOfBirth));
			updateObject.setString( 15, sysUserObj.mDrivingLicenseNumber );
			updateObject.setString( 16, sysUserObj.mPassportNumber );
			updateObject.setString( 17, sysUserObj.mRemarks );
			updateObject.setString( 18, sysUserObj.mPhoto );
			updateObject.setTimestamp( 19, GeneralUtils.getDateTime(sysUserObj.mDateOfRegistration));
			updateObject.setTimestamp( 20, GeneralUtils.getDateTime(sysUserObj.mLastLoginDate));
			updateObject.setInt( 21, sysUserObj.mNoOfLogins );
			updateObject.setInt( 22, sysUserObj.mCreateBySysUserId );
			updateObject.setInt( 23, sysUserObj.mActiveLookupId );
			updateObject.setInt( 24, sysUserObj.mParkingAreaId );
			updateObject.setInt( 25, sysUserObj.mLoggedInParkingAreaGateId );
			updateObject.setInt( 26, sysUserObj.mStatus );
			updateObject.setString( 27, sysUserObj.mPassword );
			updateObject.setString( 28, sysUserObj.mSalt );
			updateObject.setString( 29, sysUserObj.mExtraData );

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
		sqlStmt = "DELETE from " + SysUser.DATABASE_TABLE_NAME;
		
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

	public ArrayList<SysUser> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + SysUser.DATABASE_TABLE_NAME;
			
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
				ArrayList<SysUser> list = new ArrayList<SysUser>();

				do {
					SysUser obj = new SysUser();
					obj.mSysUserId = rs.getInt( KEY_SYS_USER_ID );
					obj.mRoleId = rs.getShort( KEY_ROLE_ID );
					obj.mName = rs.getString( KEY_NAME );
					obj.mMobileNumber = rs.getString( KEY_MOBILE_NUMBER );
					obj.mMobileNumber2 = rs.getString( KEY_MOBILE_NUMBER_2 );
					obj.mEmail = rs.getString( KEY_EMAIL );
					obj.mAddress = rs.getString( KEY_ADDRESS );
					obj.mCityId = rs.getInt( KEY_CITY_ID );
					obj.mCountryId = rs.getInt( KEY_COUNTRY_ID );
					obj.mDesignationLookupId = rs.getInt( KEY_DESIGNATION_LOOKUP_ID );
					obj.mLatitude = rs.getFloat( KEY_LATITUDE );
					obj.mLongitude = rs.getFloat( KEY_LONGITUDE );
					obj.mGenderLookupId = rs.getInt( KEY_GENDER_LOOKUP_ID );
					obj.mDateOfBirth = GeneralUtils.getDateTime( rs.getDate( KEY_DATE_OF_BIRTH ) );
					obj.mDrivingLicenseNumber = rs.getString( KEY_DRIVING_LICENSE_NUMBER );
					obj.mPassportNumber = rs.getString( KEY_PASSPORT_NUMBER );
					obj.mRemarks = rs.getString( KEY_REMARKS );
					obj.mPhoto = rs.getString( KEY_PHOTO );
					obj.mDateOfRegistration = GeneralUtils.getDateTime( rs.getTimestamp( KEY_DATE_OF_REGISTRATION ) );
					obj.mLastLoginDate = GeneralUtils.getDateTime( rs.getTimestamp( KEY_LAST_LOGIN_DATE ) );
					obj.mNoOfLogins = rs.getInt( KEY_NO_OF_LOGINS );
					obj.mCreateBySysUserId = rs.getInt( KEY_CREATE_BY_SYS_USER_ID );
					obj.mActiveLookupId = rs.getInt( KEY_ACTIVE_LOOKUP_ID );
					obj.mParkingAreaId = rs.getInt( KEY_PARKING_AREA_ID );
					obj.mLoggedInParkingAreaGateId = rs.getInt( KEY_LOGGED_IN_PARKING_AREA_GATE_ID );
					obj.mStatus = rs.getInt( KEY_STATUS );
					obj.mPassword = rs.getString( KEY_PASSWORD );
					obj.mSalt = rs.getString( KEY_SALT );
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

	public SysUser getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + SysUser.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				SysUser obj = new SysUser();
				obj.mSysUserId = rs.getInt( KEY_SYS_USER_ID );
				obj.mRoleId = rs.getShort( KEY_ROLE_ID );
				obj.mName = rs.getString( KEY_NAME );
				obj.mMobileNumber = rs.getString( KEY_MOBILE_NUMBER );
				obj.mMobileNumber2 = rs.getString( KEY_MOBILE_NUMBER_2 );
				obj.mEmail = rs.getString( KEY_EMAIL );
				obj.mAddress = rs.getString( KEY_ADDRESS );
				obj.mCityId = rs.getInt( KEY_CITY_ID );
				obj.mCountryId = rs.getInt( KEY_COUNTRY_ID );
				obj.mDesignationLookupId = rs.getInt( KEY_DESIGNATION_LOOKUP_ID );
				obj.mLatitude = rs.getFloat( KEY_LATITUDE );
				obj.mLongitude = rs.getFloat( KEY_LONGITUDE );
				obj.mGenderLookupId = rs.getInt( KEY_GENDER_LOOKUP_ID );
				obj.mDateOfBirth = GeneralUtils.getDateTime( rs.getDate( KEY_DATE_OF_BIRTH ) );
				obj.mDrivingLicenseNumber = rs.getString( KEY_DRIVING_LICENSE_NUMBER );
				obj.mPassportNumber = rs.getString( KEY_PASSPORT_NUMBER );
				obj.mRemarks = rs.getString( KEY_REMARKS );
				obj.mPhoto = rs.getString( KEY_PHOTO );
				obj.mDateOfRegistration = GeneralUtils.getDateTime( rs.getTimestamp( KEY_DATE_OF_REGISTRATION ) );
				obj.mLastLoginDate = GeneralUtils.getDateTime( rs.getTimestamp( KEY_LAST_LOGIN_DATE ) );
				obj.mNoOfLogins = rs.getInt( KEY_NO_OF_LOGINS );
				obj.mCreateBySysUserId = rs.getInt( KEY_CREATE_BY_SYS_USER_ID );
				obj.mActiveLookupId = rs.getInt( KEY_ACTIVE_LOOKUP_ID );
				obj.mParkingAreaId = rs.getInt( KEY_PARKING_AREA_ID );
				obj.mLoggedInParkingAreaGateId = rs.getInt( KEY_LOGGED_IN_PARKING_AREA_GATE_ID );
				obj.mStatus = rs.getInt( KEY_STATUS );
				obj.mPassword = rs.getString( KEY_PASSWORD );
				obj.mSalt = rs.getString( KEY_SALT );
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
		String columnsNames = KEY_SYS_USER_ID +  ", " +
				KEY_ROLE_ID +  ", " +
				KEY_NAME +  ", " +
				KEY_MOBILE_NUMBER +  ", " +
				KEY_MOBILE_NUMBER_2 +  ", " +
				KEY_EMAIL +  ", " +
				KEY_ADDRESS +  ", " +
				KEY_CITY_ID +  ", " +
				KEY_COUNTRY_ID +  ", " +
				KEY_DESIGNATION_LOOKUP_ID +  ", " +
				KEY_LATITUDE +  ", " +
				KEY_LONGITUDE +  ", " +
				KEY_GENDER_LOOKUP_ID +  ", " +
				KEY_DATE_OF_BIRTH +  ", " +
				KEY_DRIVING_LICENSE_NUMBER +  ", " +
				KEY_PASSPORT_NUMBER +  ", " +
				KEY_REMARKS +  ", " +
				KEY_PHOTO +  ", " +
				KEY_DATE_OF_REGISTRATION +  ", " +
				KEY_LAST_LOGIN_DATE +  ", " +
				KEY_NO_OF_LOGINS +  ", " +
				KEY_CREATE_BY_SYS_USER_ID +  ", " +
				KEY_ACTIVE_LOOKUP_ID +  ", " +
				KEY_PARKING_AREA_ID +  ", " +
				KEY_LOGGED_IN_PARKING_AREA_GATE_ID +  ", " +
				KEY_STATUS +  ", " +
				KEY_PASSWORD +  ", " +
				KEY_SALT +  ", " +
				KEY_EXTRA_DATA;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getInt(KEY_SYS_USER_ID) +  ", " +
				resultSet.getShort(KEY_ROLE_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_NAME) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_MOBILE_NUMBER) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_MOBILE_NUMBER_2) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_EMAIL) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_ADDRESS) ) +  ", " +
				resultSet.getInt(KEY_CITY_ID) +  ", " +
				resultSet.getInt(KEY_COUNTRY_ID) +  ", " +
				resultSet.getInt(KEY_DESIGNATION_LOOKUP_ID) +  ", " +
				resultSet.getFloat(KEY_LATITUDE) +  ", " +
				resultSet.getFloat(KEY_LONGITUDE) +  ", " +
				resultSet.getInt(KEY_GENDER_LOOKUP_ID) +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getDate(KEY_DATE_OF_BIRTH))  +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_DRIVING_LICENSE_NUMBER) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_PASSPORT_NUMBER) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_REMARKS) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_PHOTO) ) +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getTimestamp(KEY_DATE_OF_REGISTRATION))  +  ", " +
				GeneralUtils.getFormattedDateTimeString( resultSet.getTimestamp(KEY_LAST_LOGIN_DATE))  +  ", " +
				resultSet.getInt(KEY_NO_OF_LOGINS) +  ", " +
				resultSet.getInt(KEY_CREATE_BY_SYS_USER_ID) +  ", " +
				resultSet.getInt(KEY_ACTIVE_LOOKUP_ID) +  ", " +
				resultSet.getInt(KEY_PARKING_AREA_ID) +  ", " +
				resultSet.getInt(KEY_LOGGED_IN_PARKING_AREA_GATE_ID) +  ", " +
				resultSet.getInt(KEY_STATUS) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_PASSWORD) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_SALT) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_EXTRA_DATA) ) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(SysUser sysUser, String tableName) {
		String columnsNames = KEY_SYS_USER_ID +  ", " +
			KEY_ROLE_ID +  ", " +
			KEY_NAME +  ", " +
			KEY_MOBILE_NUMBER +  ", " +
			KEY_MOBILE_NUMBER_2 +  ", " +
			KEY_EMAIL +  ", " +
			KEY_ADDRESS +  ", " +
			KEY_CITY_ID +  ", " +
			KEY_COUNTRY_ID +  ", " +
			KEY_DESIGNATION_LOOKUP_ID +  ", " +
			KEY_LATITUDE +  ", " +
			KEY_LONGITUDE +  ", " +
			KEY_GENDER_LOOKUP_ID +  ", " +
			KEY_DATE_OF_BIRTH +  ", " +
			KEY_DRIVING_LICENSE_NUMBER +  ", " +
			KEY_PASSPORT_NUMBER +  ", " +
			KEY_REMARKS +  ", " +
			KEY_PHOTO +  ", " +
			KEY_DATE_OF_REGISTRATION +  ", " +
			KEY_LAST_LOGIN_DATE +  ", " +
			KEY_NO_OF_LOGINS +  ", " +
			KEY_CREATE_BY_SYS_USER_ID +  ", " +
			KEY_ACTIVE_LOOKUP_ID +  ", " +
			KEY_PARKING_AREA_ID +  ", " +
			KEY_LOGGED_IN_PARKING_AREA_GATE_ID +  ", " +
			KEY_STATUS +  ", " +
			KEY_PASSWORD +  ", " +
			KEY_SALT +  ", " +
			KEY_EXTRA_DATA;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += sysUser.mSysUserId +  ", " +
			sysUser.mRoleId +  ", " +
			GeneralUtils.getFormattedString( sysUser.mName ) +  ", " +
			GeneralUtils.getFormattedString( sysUser.mMobileNumber ) +  ", " +
			GeneralUtils.getFormattedString( sysUser.mMobileNumber2 ) +  ", " +
			GeneralUtils.getFormattedString( sysUser.mEmail ) +  ", " +
			GeneralUtils.getFormattedString( sysUser.mAddress ) +  ", " +
			sysUser.mCityId +  ", " +
			sysUser.mCountryId +  ", " +
			sysUser.mDesignationLookupId +  ", " +
			sysUser.mLatitude +  ", " +
			sysUser.mLongitude +  ", " +
			sysUser.mGenderLookupId +  ", " +
			sysUser.mDateOfBirth +  ", " +
			GeneralUtils.getFormattedString( sysUser.mDrivingLicenseNumber ) +  ", " +
			GeneralUtils.getFormattedString( sysUser.mPassportNumber ) +  ", " +
			GeneralUtils.getFormattedString( sysUser.mRemarks ) +  ", " +
			GeneralUtils.getFormattedString( sysUser.mPhoto ) +  ", " +
			GeneralUtils.getFormattedDateTimeString( sysUser.mDateOfRegistration ) +  ", " +
			GeneralUtils.getFormattedDateTimeString( sysUser.mLastLoginDate ) +  ", " +
			sysUser.mNoOfLogins +  ", " +
			sysUser.mCreateBySysUserId +  ", " +
			sysUser.mActiveLookupId +  ", " +
			sysUser.mParkingAreaId +  ", " +
			sysUser.mLoggedInParkingAreaGateId +  ", " +
			sysUser.mStatus +  ", " +
			GeneralUtils.getFormattedString( sysUser.mPassword ) +  ", " +
			GeneralUtils.getFormattedString( sysUser.mSalt ) +  ", " +
			GeneralUtils.getFormattedString( sysUser.mExtraData ) + " );";
		return sqlStatement;
	}
*/
}
