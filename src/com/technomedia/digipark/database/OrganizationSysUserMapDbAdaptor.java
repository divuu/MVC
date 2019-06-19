////////////////////////////////////////////////////////////////////////////

// FileName OrganizationSysUserMapDbAdaptor.java: OrganizationSysUserMapDbAdaptor Implementation file

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


public class OrganizationSysUserMapDbAdaptor extends AbstractDbAdaptor {

	public static final String KEY_SYS_USER_ID = "sys_user_id";
	public static final String KEY_ORGANIZATION_ID = "organization_id";
	public static final String KEY_ROLE_ID = "role_id";


	public OrganizationSysUserMapDbAdaptor() {
		mTableName = OrganizationSysUserMap.DATABASE_TABLE_NAME;
	}

	public long insert( OrganizationSysUserMap organizationSysUserMapObj ) {

		if( organizationSysUserMapObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String columnsNames = KEY_SYS_USER_ID + ", " + KEY_ORGANIZATION_ID + ", " + KEY_ROLE_ID;

		String values = "";
		for (int i = 1; i < OrganizationSysUserMap.NUM_OF_COLUMNS; i++)
			values = values + "?,";
		values = values + "?";

		String insertString = "insert into " + OrganizationSysUserMap.DATABASE_TABLE_NAME
				+ "( " + columnsNames + ") values( " + values + " )";

		try {
			PreparedStatement insertObject = null;
			insertObject = getConnection().prepareStatement( insertString);
			insertObject.setShort( 1, organizationSysUserMapObj.mSysUserId );
			insertObject.setInt( 2, organizationSysUserMapObj.mOrganizationId );
			insertObject.setShort( 3, organizationSysUserMapObj.mRoleId );

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

	public long update( OrganizationSysUserMap organizationSysUserMapObj, String whereClause ) {

		if( organizationSysUserMapObj == null )
			return DbErrorCodes.ERROR_CODE_NULL_POINTER;

		long ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		String updateString = "update " + OrganizationSysUserMap.DATABASE_TABLE_NAME + " set "+ KEY_SYS_USER_ID + " =?, " + KEY_ORGANIZATION_ID + " =?, " + KEY_ROLE_ID + " =? " + " where " + whereClause + ";";
		try {
			PreparedStatement updateObject = null;
			updateObject = getConnection().prepareStatement(updateString);
			updateObject.setShort( 1, organizationSysUserMapObj.mSysUserId );
			updateObject.setInt( 2, organizationSysUserMapObj.mOrganizationId );
			updateObject.setShort( 3, organizationSysUserMapObj.mRoleId );

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
		sqlStmt = "DELETE from " + OrganizationSysUserMap.DATABASE_TABLE_NAME;
		
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

	public ArrayList<OrganizationSysUserMap> getList(String whereStr, String orderBy) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr) {
			whereStr = " where " + whereStr;
		}
		try {
			stmt = getConnection().createStatement();
			String sqlStmt;
			sqlStmt = "SELECT * from " + OrganizationSysUserMap.DATABASE_TABLE_NAME;
			
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
				ArrayList<OrganizationSysUserMap> list = new ArrayList<OrganizationSysUserMap>();

				do {
					OrganizationSysUserMap obj = new OrganizationSysUserMap();
					obj.mSysUserId = rs.getShort( KEY_SYS_USER_ID );
					obj.mOrganizationId = rs.getInt( KEY_ORGANIZATION_ID );
					obj.mRoleId = rs.getShort( KEY_ROLE_ID );
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

	public OrganizationSysUserMap getData(String whereStr) {
		ResultSet rs = null;
		Statement stmt = null;

		if (null != whereStr)
			whereStr = " where " + whereStr;
		try {
			stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * from " + OrganizationSysUserMap.DATABASE_TABLE_NAME + " " + whereStr);

		} catch (SQLException se) {
			MLogger.dumpException( se );
		}
		try {
			if( rs == null ) {
				stmt.close();
				return null;
			}
			if (rs != null && rs.first()) {
				OrganizationSysUserMap obj = new OrganizationSysUserMap();
				obj.mSysUserId = rs.getShort( KEY_SYS_USER_ID );
				obj.mOrganizationId = rs.getInt( KEY_ORGANIZATION_ID );
				obj.mRoleId = rs.getShort( KEY_ROLE_ID );
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
				KEY_ORGANIZATION_ID +  ", " +
				KEY_ROLE_ID;

		String sqlStatement = "INSERT INTO " + mTableName + " ( " + columnsNames + " ) VALUES ( ";

		try {
			sqlStatement += resultSet.getShort(KEY_SYS_USER_ID) +  ", " +
				resultSet.getInt(KEY_ORGANIZATION_ID) +  ", " +
				resultSet.getShort(KEY_ROLE_ID) + " );";
		} catch (SQLException e) {

			MLogger.dumpException(e);
		}

		return sqlStatement;
	}

/*
	public static String getInsertStatementFromObject(OrganizationSysUserMap organizationSysUserMap, String tableName) {
		String columnsNames = KEY_SYS_USER_ID +  ", " +
			KEY_ORGANIZATION_ID +  ", " +
			KEY_ROLE_ID;

		String sqlStatement = "INSERT INTO " + tableName + " ( " + columnsNames + " ) VALUES ( ";

		sqlStatement += organizationSysUserMap.mSysUserId +  ", " +
			organizationSysUserMap.mOrganizationId +  ", " +
			organizationSysUserMap.mRoleId + " );";
		return sqlStatement;
	}
*/
}
