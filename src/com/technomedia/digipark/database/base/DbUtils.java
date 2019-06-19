package com.technomedia.digipark.database.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.technomedia.logger.MLogger;

public class DbUtils {

	public DbUtils() {

	}

	// Get COUNT/SUM/MIN/MAX/AVG of a column:
	// int max = mCategoryDbAdaptor.runAggregate("max",
	// CategoryDbAdaptor.KEY_CATEGORY_ID);

	public static int runAggregate(Connection con, String table_name,
			String functionType, String columnName, String condition) {

		ResultSet rs = null;
		Statement stmt = null;
		int retValue = 0;

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT " + functionType + "(" + columnName
					+ ") from " + table_name;
			if (null != condition && condition.length() > 0)
				sql += " where " + condition;
			MLogger.i(MLogger.MOD_DB, sql);
			rs = stmt.executeQuery(sql);

		} catch (SQLException se) {

			MLogger.dumpException(se);
		}
		if (rs == null) {
			MLogger.i(MLogger.MOD_DB, "\t-> returned 0");
			return 0;
		}
		try {
			if (rs != null && rs.first()) {
				retValue = rs.getInt(1);
				rs.close();
				MLogger.i(MLogger.MOD_DB, "\t-> returned " + retValue);
				return retValue;
			}
			if (rs != null)
				rs.close();

		} catch (SQLException se) {
			MLogger.dumpException(se);
		}

		MLogger.i(MLogger.MOD_DB, "\t-> returned 0");
		return 0;
	}

	// Single sql statement executor.
	/**
	 * Use only for INSERT, UPDATE, or DELETE
	 * 
	 * @param con
	 *            :connection
	 * @param qStr
	 *            : query string
	 * @return : int( )
	 */
	public static int executeSingleUpdateStatement(Connection con, String qStr) {
		int ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;
		try {
			Statement mStatement = (Statement) con
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			ret = mStatement.executeUpdate(qStr);
		} catch (SQLException se) {
			// TODO: handle exception
			MLogger.i(MLogger.MOD_DB, "Failed to execute " + se.getMessage());
			MLogger.dumpException(se);
		}
		return ret;
	}

	/* *//**
	 * Use only for SELECT
	 * 
	 * @param con
	 *            :connection
	 * @param qStr
	 *            : query string
	 * @return : ArrayList<PassengerMaster>( )
	 */
	/*
	 * public static ArrayList<PassengerMaster>
	 * executeSingleStatement(Connection con, String qStr) { int ret =
	 * DbErrorCodes.ERROR_CODE_OPERATION_FAILED; try { Statement mStatement =
	 * (Statement) con .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	 * ResultSet.CONCUR_UPDATABLE);
	 * 
	 * ret = mStatement.executeUpdate(qStr); } catch (SQLException se) { //
	 * TODO: handle exception System.out.println("Failed to execute " +
	 * se.getMessage()); MLogger.dumpException(se); } return ret; }
	 */
}
