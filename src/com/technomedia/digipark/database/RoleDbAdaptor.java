////////////////////////////////////////////////////////////////////////////

// FileName RoleDbAdaptor.java: RoleDbAdaptor Implementation file

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


public class RoleDbAdaptor extends AbstractDbAdaptor {

	public static final String KEY_ROLE_ID = "role_id";
	public static final String KEY_ORGANIZATION_ID = "organization_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_ROLE_WEB_APP_UI_FEATURES = "role_web_app_ui_features";
	public static final String KEY_ROLE_MOBILE_APP_UI_FEATURES = "role_mobile_app_ui_features";
	public static final String KEY_ROLE_REPORT_FEATURES = "role_report_features";
	public static final String KEY_ROLE_MENU = "role_menu";
	public static final String KEY_ROLE_SCRIPT = "role_script";
	public static final String KEY_REMARKS = "remarks";


	public RoleDbAdaptor() {
		mTableName = Role.DATABASE_TABLE_NAME;
	}

	public long insert( Role roleObj ) {

		if( roleObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_ROLE_ID + ", " + KEY_ORGANIZATION_ID + ", " + KEY_NAME + ", " + KEY_ROLE_WEB_APP_UI_FEATURES + ", " + KEY_ROLE_MOBILE_APP_UI_FEATURES + ", " + KEY_ROLE_REPORT_FEATURES + ", " + KEY_ROLE_MENU + ", " + KEY_ROLE_SCRIPT + ", " + KEY_REMARKS;

		String values = "";
		for (int i = 1; i < Role.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + Role.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString, Statement.RETURN_GENERATED_KEYS );

			roleObj.mRoleId = 0;
			insertObject.setShort( 1, roleObj.mRoleId );
			insertObject.setInt( 2, roleObj.mOrganizationId );
			insertObject.setString( 3, roleObj.mName );
			insertObject.setString( 4, roleObj.mRoleWebAppUiFeatures );
			insertObject.setString( 5, roleObj.mRoleMobileAppUiFeatures );
			insertObject.setString( 6, roleObj.mRoleReportFeatures );
			insertObject.setString( 7, roleObj.mRoleMenu );
			insertObject.setString( 8, roleObj.mRoleScript );
			insertObject.setString( 9, roleObj.mRemarks );

			MLogger.i( MLogger.MOD_DB, insertObject.toString() );

			int rowsInserted = insertObject.executeUpdate();

			if( rowsInserted > 0 ) { 
				ret = DbErrorCodes.ERROR_CODE_SUCCESS;
			}
			ResultSet resultSet = insertObject.getGeneratedKeys();
			if( resultSet != null ) {
				resultSet.next();
				roleObj.mRoleId = (short) resultSet.getInt(1);
				resultSet.close();
			}

			if( insertObject != null )
				insertObject.close();
		} catch (SQLException se) {

			MLogger.dumpException( se );
		}
		return ret;
	}

	public long update( Role roleObj, String whereClause ) {

		if( roleObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + Role.DATABASE_TABLE_NAME + " set "+ KEY_ROLE_ID + " =?, " + KEY_ORGANIZATION_ID + " =?, " + KEY_NAME + " =?, " + KEY_ROLE_WEB_APP_UI_FEATURES + " =?, " + KEY_ROLE_MOBILE_APP_UI_FEATURES + " =?, " + KEY_ROLE_REPORT_FEATURES + " =?, " + KEY_ROLE_MENU + " =?, " + KEY_ROLE_SCRIPT + " =?, " + KEY_REMARKS + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setShort( 1, roleObj.mRoleId );
			updateObject.setInt( 2, roleObj.mOrganizationId );
			updateObject.setString( 3, roleObj.mName );
			updateObject.setString( 4, roleObj.mRoleWebAppUiFeatures );
			updateObject.setString( 5, roleObj.mRoleMobileAppUiFeatures );
			updateObject.setString( 6, roleObj.mRoleReportFeatures );
			updateObject.setString( 7, roleObj.mRoleMenu );
			updateObject.setString( 8, roleObj.mRoleScript );
			updateObject.setString( 9, roleObj.mRemarks );

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
		sqlStmt = "DELETE from " + Role.DATABASE_TABLE_NAME;
		
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

	public ArrayList<Role> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + Role.DATABASE_TABLE_NAME;
			
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
				ArrayList<Role> list = new ArrayList<Role>();

				do {
					Role obj = new Role();
					obj.mRoleId = rs.getShort( KEY_ROLE_ID );
					obj.mOrganizationId = rs.getInt( KEY_ORGANIZATION_ID );
					obj.mName = rs.getString( KEY_NAME );
					obj.mRoleWebAppUiFeatures = rs.getString( KEY_ROLE_WEB_APP_UI_FEATURES );
					obj.mRoleMobileAppUiFeatures = rs.getString( KEY_ROLE_MOBILE_APP_UI_FEATURES );
					obj.mRoleReportFeatures = rs.getString( KEY_ROLE_REPORT_FEATURES );
					obj.mRoleMenu = rs.getString( KEY_ROLE_MENU );
					obj.mRoleScript = rs.getString( KEY_ROLE_SCRIPT );
					obj.mRemarks = rs.getString( KEY_REMARKS );
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

	public Role getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + Role.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				Role obj = new Role();
				obj.mRoleId = rs.getShort( KEY_ROLE_ID );
				obj.mOrganizationId = rs.getInt( KEY_ORGANIZATION_ID );
				obj.mName = rs.getString( KEY_NAME );
				obj.mRoleWebAppUiFeatures = rs.getString( KEY_ROLE_WEB_APP_UI_FEATURES );
				obj.mRoleMobileAppUiFeatures = rs.getString( KEY_ROLE_MOBILE_APP_UI_FEATURES );
				obj.mRoleReportFeatures = rs.getString( KEY_ROLE_REPORT_FEATURES );
				obj.mRoleMenu = rs.getString( KEY_ROLE_MENU );
				obj.mRoleScript = rs.getString( KEY_ROLE_SCRIPT );
				obj.mRemarks = rs.getString( KEY_REMARKS );
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
		String columnsNames = KEY_ROLE_ID +  ", " +
				KEY_ORGANIZATION_ID +  ", " +
				KEY_NAME +  ", " +
				KEY_ROLE_WEB_APP_UI_FEATURES +  ", " +
				KEY_ROLE_MOBILE_APP_UI_FEATURES +  ", " +
				KEY_ROLE_REPORT_FEATURES +  ", " +
				KEY_ROLE_MENU +  ", " +
				KEY_ROLE_SCRIPT +  ", " +
				KEY_REMARKS;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getShort(KEY_ROLE_ID) +  ", " +
				resultSet.getInt(KEY_ORGANIZATION_ID) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_NAME) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_ROLE_WEB_APP_UI_FEATURES) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_ROLE_MOBILE_APP_UI_FEATURES) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_ROLE_REPORT_FEATURES) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_ROLE_MENU) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_ROLE_SCRIPT) ) +  ", " +
				GeneralUtils.getFormattedString( resultSet.getString(KEY_REMARKS) ) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(Role role, String tableName) {
		String columnsNames = KEY_ROLE_ID +  ", " +
			KEY_ORGANIZATION_ID +  ", " +
			KEY_NAME +  ", " +
			KEY_ROLE_WEB_APP_UI_FEATURES +  ", " +
			KEY_ROLE_MOBILE_APP_UI_FEATURES +  ", " +
			KEY_ROLE_REPORT_FEATURES +  ", " +
			KEY_ROLE_MENU +  ", " +
			KEY_ROLE_SCRIPT +  ", " +
			KEY_REMARKS;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += role.mRoleId +  ", " +
			role.mOrganizationId +  ", " +
			GeneralUtils.getFormattedString( role.mName ) +  ", " +
			GeneralUtils.getFormattedString( role.mRoleWebAppUiFeatures ) +  ", " +
			GeneralUtils.getFormattedString( role.mRoleMobileAppUiFeatures ) +  ", " +
			GeneralUtils.getFormattedString( role.mRoleReportFeatures ) +  ", " +
			GeneralUtils.getFormattedString( role.mRoleMenu ) +  ", " +
			GeneralUtils.getFormattedString( role.mRoleScript ) +  ", " +
			GeneralUtils.getFormattedString( role.mRemarks ) + " );";
		return sqlStatement;
	}
*/
}
