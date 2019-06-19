////////////////////////////////////////////////////////////////////////////

// FileName OrganizationDbAdaptor.java: OrganizationDbAdaptor Implementation file

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


public class OrganizationDbAdaptor extends AbstractDbAdaptor {

	public static final String KEY_ORGANIZATION_ID = "organization_id";
	public static final String KEY_ORGANIZATION_NAME = "organization_name";
	public static final String KEY_SHORT_NAME = "short_name";
	public static final String KEY_REGISTERED_ADDRESS = "registered_address";
	public static final String KEY_BUSINESS_ADDRESS = "business_address";
	public static final String KEY_CITY_ID = "city_id";
	public static final String KEY_STATE_LOOKUP_ID = "state_lookup_id";
	public static final String KEY_COUNTRY_ID = "country_id";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_WEBSITE = "website";
	public static final String KEY_MOBILE_NUMBER = "mobile_number";
	public static final String KEY_LANDLINE_NUMBER = "landline_number";
	public static final String KEY_FAX_NUMBER = "fax_number";
	public static final String KEY_CONTACT_PERSON_NAME = "contact_person_name";
	public static final String KEY_CONTACT_PERSON_MOBILE_NUMBER = "contact_person_mobile_number";
	public static final String KEY_CONTACT_PERSON_WHATSAPP_NO = "contact_person_whatsApp_no";
	public static final String KEY_CONTACT_PERSON_EMAIL = "contact_person_email";
	public static final String KEY_LOGO_PATH = "logo_path";
	public static final String KEY_DOCUMENTS_PATH = "documents_path";
	public static final String KEY_BANK_ACCOUNT_ID = "bank_account_id";
	public static final String KEY_TYPE_OF_BUSINESS = "type_of_business";
	public static final String KEY_REMARKS = "remarks";
	public static final String KEY_PREFERENCES = "preferences";


	public OrganizationDbAdaptor() {
		mTableName = Organization.DATABASE_TABLE_NAME;
	}

	public long insert( Organization organizationObj ) {

		if( organizationObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_ORGANIZATION_ID + ", " + KEY_ORGANIZATION_NAME + ", " + KEY_SHORT_NAME + ", " + KEY_REGISTERED_ADDRESS + ", " + KEY_BUSINESS_ADDRESS + ", " + KEY_CITY_ID + ", " + KEY_STATE_LOOKUP_ID + ", " + KEY_COUNTRY_ID + ", " + KEY_EMAIL + ", " + KEY_WEBSITE + ", " + KEY_MOBILE_NUMBER + ", " + KEY_LANDLINE_NUMBER + ", " + KEY_FAX_NUMBER + ", " + KEY_CONTACT_PERSON_NAME + ", " + KEY_CONTACT_PERSON_MOBILE_NUMBER + ", " + KEY_CONTACT_PERSON_WHATSAPP_NO + ", " + KEY_CONTACT_PERSON_EMAIL + ", " + KEY_LOGO_PATH + ", " + KEY_DOCUMENTS_PATH + ", " + KEY_BANK_ACCOUNT_ID + ", " + KEY_TYPE_OF_BUSINESS + ", " + KEY_REMARKS + ", " + KEY_PREFERENCES;

		String values = "";
		for (int i = 1; i < Organization.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + Organization.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString, Statement.RETURN_GENERATED_KEYS );

			organizationObj.mOrganizationId = 0;
			insertObject.setInt( 1, organizationObj.mOrganizationId );
			insertObject.setString( 2, organizationObj.mOrganizationName );
			insertObject.setString( 3, organizationObj.mShortName );
			insertObject.setString( 4, organizationObj.mRegisteredAddress );
			insertObject.setString( 5, organizationObj.mBusinessAddress );
			insertObject.setInt( 6, organizationObj.mCityId );
			insertObject.setInt( 7, organizationObj.mStateLookupId );
			insertObject.setInt( 8, organizationObj.mCountryId );
			insertObject.setString( 9, organizationObj.mEmail );
			insertObject.setString( 10, organizationObj.mWebsite );
			insertObject.setString( 11, organizationObj.mMobileNumber );
			insertObject.setString( 12, organizationObj.mLandlineNumber );
			insertObject.setString( 13, organizationObj.mFaxNumber );
			insertObject.setString( 14, organizationObj.mContactPersonName );
			insertObject.setString( 15, organizationObj.mContactPersonMobileNumber );
			insertObject.setString( 16, organizationObj.mContactPersonWhatsappNo );
			insertObject.setString( 17, organizationObj.mContactPersonEmail );
			insertObject.setString( 18, organizationObj.mLogoPath );
			insertObject.setString( 19, organizationObj.mDocumentsPath );
			insertObject.setShort( 20, organizationObj.mBankAccountId );
			insertObject.setInt( 21, organizationObj.mTypeOfBusiness );
			insertObject.setString( 22, organizationObj.mRemarks );
			insertObject.setInt( 23, organizationObj.mPreferences );

			MLogger.i( MLogger.MOD_DB, insertObject.toString() );

			int rowsInserted = insertObject.executeUpdate();

			if( rowsInserted > 0 ) { 
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			}
			ResultSet resultSet = insertObject.getGeneratedKeys();
			if( resultSet != null ) {
				resultSet.next();
				organizationObj.mOrganizationId = resultSet.getInt(1);
				resultSet.close();
			}

			if( insertObject != null )
				insertObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long update( Organization organizationObj, String whereClause ) {

		if( organizationObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + Organization.DATABASE_TABLE_NAME + " set "+ KEY_ORGANIZATION_ID + " =?, " + KEY_ORGANIZATION_NAME + " =?, " + KEY_SHORT_NAME + " =?, " + KEY_REGISTERED_ADDRESS + " =?, " + KEY_BUSINESS_ADDRESS + " =?, " + KEY_CITY_ID + " =?, " + KEY_STATE_LOOKUP_ID + " =?, " + KEY_COUNTRY_ID + " =?, " + KEY_EMAIL + " =?, " + KEY_WEBSITE + " =?, " + KEY_MOBILE_NUMBER + " =?, " + KEY_LANDLINE_NUMBER + " =?, " + KEY_FAX_NUMBER + " =?, " + KEY_CONTACT_PERSON_NAME + " =?, " + KEY_CONTACT_PERSON_MOBILE_NUMBER + " =?, " + KEY_CONTACT_PERSON_WHATSAPP_NO + " =?, " + KEY_CONTACT_PERSON_EMAIL + " =?, " + KEY_LOGO_PATH + " =?, " + KEY_DOCUMENTS_PATH + " =?, " + KEY_BANK_ACCOUNT_ID + " =?, " + KEY_TYPE_OF_BUSINESS + " =?, " + KEY_REMARKS + " =?, " + KEY_PREFERENCES + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setInt( 1, organizationObj.mOrganizationId );
			updateObject.setString( 2, organizationObj.mOrganizationName );
			updateObject.setString( 3, organizationObj.mShortName );
			updateObject.setString( 4, organizationObj.mRegisteredAddress );
			updateObject.setString( 5, organizationObj.mBusinessAddress );
			updateObject.setInt( 6, organizationObj.mCityId );
			updateObject.setInt( 7, organizationObj.mStateLookupId );
			updateObject.setInt( 8, organizationObj.mCountryId );
			updateObject.setString( 9, organizationObj.mEmail );
			updateObject.setString( 10, organizationObj.mWebsite );
			updateObject.setString( 11, organizationObj.mMobileNumber );
			updateObject.setString( 12, organizationObj.mLandlineNumber );
			updateObject.setString( 13, organizationObj.mFaxNumber );
			updateObject.setString( 14, organizationObj.mContactPersonName );
			updateObject.setString( 15, organizationObj.mContactPersonMobileNumber );
			updateObject.setString( 16, organizationObj.mContactPersonWhatsappNo );
			updateObject.setString( 17, organizationObj.mContactPersonEmail );
			updateObject.setString( 18, organizationObj.mLogoPath );
			updateObject.setString( 19, organizationObj.mDocumentsPath );
			updateObject.setShort( 20, organizationObj.mBankAccountId );
			updateObject.setInt( 21, organizationObj.mTypeOfBusiness );
			updateObject.setString( 22, organizationObj.mRemarks );
			updateObject.setInt( 23, organizationObj.mPreferences );

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
		sqlStmt = "DELETE from " + Organization.DATABASE_TABLE_NAME;
		
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

	public ArrayList<Organization> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + Organization.DATABASE_TABLE_NAME;
			
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
				ArrayList<Organization> list = new ArrayList<Organization>();

				do {
					Organization obj = new Organization();
					obj.mOrganizationId = rs.getInt( KEY_ORGANIZATION_ID );
					obj.mOrganizationName = rs.getString( KEY_ORGANIZATION_NAME );
					obj.mShortName = rs.getString( KEY_SHORT_NAME );
					obj.mRegisteredAddress = rs.getString( KEY_REGISTERED_ADDRESS );
					obj.mBusinessAddress = rs.getString( KEY_BUSINESS_ADDRESS );
					obj.mCityId = rs.getInt( KEY_CITY_ID );
					obj.mStateLookupId = rs.getInt( KEY_STATE_LOOKUP_ID );
					obj.mCountryId = rs.getInt( KEY_COUNTRY_ID );
					obj.mEmail = rs.getString( KEY_EMAIL );
					obj.mWebsite = rs.getString( KEY_WEBSITE );
					obj.mMobileNumber = rs.getString( KEY_MOBILE_NUMBER );
					obj.mLandlineNumber = rs.getString( KEY_LANDLINE_NUMBER );
					obj.mFaxNumber = rs.getString( KEY_FAX_NUMBER );
					obj.mContactPersonName = rs.getString( KEY_CONTACT_PERSON_NAME );
					obj.mContactPersonMobileNumber = rs.getString( KEY_CONTACT_PERSON_MOBILE_NUMBER );
					obj.mContactPersonWhatsappNo = rs.getString( KEY_CONTACT_PERSON_WHATSAPP_NO );
					obj.mContactPersonEmail = rs.getString( KEY_CONTACT_PERSON_EMAIL );
					obj.mLogoPath = rs.getString( KEY_LOGO_PATH );
					obj.mDocumentsPath = rs.getString( KEY_DOCUMENTS_PATH );
					obj.mBankAccountId = rs.getShort( KEY_BANK_ACCOUNT_ID );
					obj.mTypeOfBusiness = rs.getInt( KEY_TYPE_OF_BUSINESS );
					obj.mRemarks = rs.getString( KEY_REMARKS );
					obj.mPreferences = rs.getInt( KEY_PREFERENCES );
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

	public Organization getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + Organization.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				Organization obj = new Organization();
				obj.mOrganizationId = rs.getInt( KEY_ORGANIZATION_ID );
				obj.mOrganizationName = rs.getString( KEY_ORGANIZATION_NAME );
				obj.mShortName = rs.getString( KEY_SHORT_NAME );
				obj.mRegisteredAddress = rs.getString( KEY_REGISTERED_ADDRESS );
				obj.mBusinessAddress = rs.getString( KEY_BUSINESS_ADDRESS );
				obj.mCityId = rs.getInt( KEY_CITY_ID );
				obj.mStateLookupId = rs.getInt( KEY_STATE_LOOKUP_ID );
				obj.mCountryId = rs.getInt( KEY_COUNTRY_ID );
				obj.mEmail = rs.getString( KEY_EMAIL );
				obj.mWebsite = rs.getString( KEY_WEBSITE );
				obj.mMobileNumber = rs.getString( KEY_MOBILE_NUMBER );
				obj.mLandlineNumber = rs.getString( KEY_LANDLINE_NUMBER );
				obj.mFaxNumber = rs.getString( KEY_FAX_NUMBER );
				obj.mContactPersonName = rs.getString( KEY_CONTACT_PERSON_NAME );
				obj.mContactPersonMobileNumber = rs.getString( KEY_CONTACT_PERSON_MOBILE_NUMBER );
				obj.mContactPersonWhatsappNo = rs.getString( KEY_CONTACT_PERSON_WHATSAPP_NO );
				obj.mContactPersonEmail = rs.getString( KEY_CONTACT_PERSON_EMAIL );
				obj.mLogoPath = rs.getString( KEY_LOGO_PATH );
				obj.mDocumentsPath = rs.getString( KEY_DOCUMENTS_PATH );
				obj.mBankAccountId = rs.getShort( KEY_BANK_ACCOUNT_ID );
				obj.mTypeOfBusiness = rs.getInt( KEY_TYPE_OF_BUSINESS );
				obj.mRemarks = rs.getString( KEY_REMARKS );
				obj.mPreferences = rs.getInt( KEY_PREFERENCES );
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
		String columnsNames = KEY_ORGANIZATION_ID +  ", " +
				KEY_ORGANIZATION_NAME +  ", " +
				KEY_SHORT_NAME +  ", " +
				KEY_REGISTERED_ADDRESS +  ", " +
				KEY_BUSINESS_ADDRESS +  ", " +
				KEY_CITY_ID +  ", " +
				KEY_STATE_LOOKUP_ID +  ", " +
				KEY_COUNTRY_ID +  ", " +
				KEY_EMAIL +  ", " +
				KEY_WEBSITE +  ", " +
				KEY_MOBILE_NUMBER +  ", " +
				KEY_LANDLINE_NUMBER +  ", " +
				KEY_FAX_NUMBER +  ", " +
				KEY_CONTACT_PERSON_NAME +  ", " +
				KEY_CONTACT_PERSON_MOBILE_NUMBER +  ", " +
				KEY_CONTACT_PERSON_WHATSAPP_NO +  ", " +
				KEY_CONTACT_PERSON_EMAIL +  ", " +
				KEY_LOGO_PATH +  ", " +
				KEY_DOCUMENTS_PATH +  ", " +
				KEY_BANK_ACCOUNT_ID +  ", " +
				KEY_TYPE_OF_BUSINESS +  ", " +
				KEY_REMARKS +  ", " +
				KEY_PREFERENCES;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getInt(KEY_ORGANIZATION_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_ORGANIZATION_NAME) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_SHORT_NAME) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_REGISTERED_ADDRESS) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_BUSINESS_ADDRESS) ) +  ", " +
				resultSet.getInt(KEY_CITY_ID) +  ", " +
				resultSet.getInt(KEY_STATE_LOOKUP_ID) +  ", " +
				resultSet.getInt(KEY_COUNTRY_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_EMAIL) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_WEBSITE) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_MOBILE_NUMBER) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_LANDLINE_NUMBER) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_FAX_NUMBER) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_CONTACT_PERSON_NAME) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_CONTACT_PERSON_MOBILE_NUMBER) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_CONTACT_PERSON_WHATSAPP_NO) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_CONTACT_PERSON_EMAIL) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_LOGO_PATH) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_DOCUMENTS_PATH) ) +  ", " +
				resultSet.getShort(KEY_BANK_ACCOUNT_ID) +  ", " +
				resultSet.getInt(KEY_TYPE_OF_BUSINESS) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_REMARKS) ) +  ", " +
				resultSet.getInt(KEY_PREFERENCES) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(Organization organization, String tableName) {
		String columnsNames = KEY_ORGANIZATION_ID +  ", " +
			KEY_ORGANIZATION_NAME +  ", " +
			KEY_SHORT_NAME +  ", " +
			KEY_REGISTERED_ADDRESS +  ", " +
			KEY_BUSINESS_ADDRESS +  ", " +
			KEY_CITY_ID +  ", " +
			KEY_STATE_LOOKUP_ID +  ", " +
			KEY_COUNTRY_ID +  ", " +
			KEY_EMAIL +  ", " +
			KEY_WEBSITE +  ", " +
			KEY_MOBILE_NUMBER +  ", " +
			KEY_LANDLINE_NUMBER +  ", " +
			KEY_FAX_NUMBER +  ", " +
			KEY_CONTACT_PERSON_NAME +  ", " +
			KEY_CONTACT_PERSON_MOBILE_NUMBER +  ", " +
			KEY_CONTACT_PERSON_WHATSAPP_NO +  ", " +
			KEY_CONTACT_PERSON_EMAIL +  ", " +
			KEY_LOGO_PATH +  ", " +
			KEY_DOCUMENTS_PATH +  ", " +
			KEY_BANK_ACCOUNT_ID +  ", " +
			KEY_TYPE_OF_BUSINESS +  ", " +
			KEY_REMARKS +  ", " +
			KEY_PREFERENCES;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += organization.mOrganizationId +  ", " +
			GeneralUtils.getFormattedString( organization.mOrganizationName ) +  ", " +
			GeneralUtils.getFormattedString( organization.mShortName ) +  ", " +
			GeneralUtils.getFormattedString( organization.mRegisteredAddress ) +  ", " +
			GeneralUtils.getFormattedString( organization.mBusinessAddress ) +  ", " +
			organization.mCityId +  ", " +
			organization.mStateLookupId +  ", " +
			organization.mCountryId +  ", " +
			GeneralUtils.getFormattedString( organization.mEmail ) +  ", " +
			GeneralUtils.getFormattedString( organization.mWebsite ) +  ", " +
			GeneralUtils.getFormattedString( organization.mMobileNumber ) +  ", " +
			GeneralUtils.getFormattedString( organization.mLandlineNumber ) +  ", " +
			GeneralUtils.getFormattedString( organization.mFaxNumber ) +  ", " +
			GeneralUtils.getFormattedString( organization.mContactPersonName ) +  ", " +
			GeneralUtils.getFormattedString( organization.mContactPersonMobileNumber ) +  ", " +
			GeneralUtils.getFormattedString( organization.mContactPersonWhatsappNo ) +  ", " +
			GeneralUtils.getFormattedString( organization.mContactPersonEmail ) +  ", " +
			GeneralUtils.getFormattedString( organization.mLogoPath ) +  ", " +
			GeneralUtils.getFormattedString( organization.mDocumentsPath ) +  ", " +
			organization.mBankAccountId +  ", " +
			organization.mTypeOfBusiness +  ", " +
			GeneralUtils.getFormattedString( organization.mRemarks ) +  ", " +
			organization.mPreferences + " );";
		return sqlStatement;
	}
*/
}
