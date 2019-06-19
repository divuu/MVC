package com.technomedia.digipark.database.base;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.Statement;
import com.technomedia.logger.MLogger;

public abstract class AbstractDbAdaptor {

	protected String mTableName = null;

	abstract protected String getInsertStatement(ResultSet resultSet);

	// public static Connection mConnection = null;

	public AbstractDbAdaptor() {

		// mConnection = (Connection) DbManager.getInstance().getConnection();
	}

	public static Connection getConnection() {
		return (Connection) DbManager.getInstance().getConnection();
	}

	public AbstractDbAdaptor open() {

		return this;
	}

	public void close() {

	}

	/**
	 * use only for integers USAGE Examples int max =
	 * mCategoryDbAdaptor.runAggregate("max",
	 * CategoryDbAdaptor.KEY_CATEGORY_ID); int min =
	 * mCategoryDbAdaptor.runAggregate("min",
	 * CategoryDbAdaptor.KEY_CATEGORY_ID); int avg =
	 * mCategoryDbAdaptor.runAggregate("avg",
	 * CategoryDbAdaptor.KEY_CATEGORY_ID);
	 */

	public long runAggregateLong(String functionType, String columnName,
			String condition) {

		String query = " select " + functionType + "(" + columnName + ") from "
				+ mTableName;

		if (condition != null && condition.length() > 0)
			query += " where " + condition;

		MLogger.i(MLogger.MOD_DB, "Query: " + query);

		long retValue = 0;
		try {
			try {

				Statement statement = (Statement) DbManager
						.getInstance()
						.getConnection()
						.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

				ResultSet resultSet = statement.executeQuery(query);

				if (resultSet != null) {

					resultSet.next();
					retValue = resultSet.getLong(1);
				}

			} catch (Exception se) {

				MLogger.dumpException(se);
			}

		} catch (Exception e) {
			MLogger.e(MLogger.MOD_DB, mTableName + " query execution failed "
					+ e);
			MLogger.dumpException(e);
		}

		MLogger.i(MLogger.MOD_DB, "\t->Query Returned = " + retValue);

		return retValue;
	}

	public int runAggregate(String functionType, String columnName,
			String condition) {

		String query = " select " + functionType + "(" + columnName + ") from "
				+ mTableName;

		if (condition != null && condition.length() > 0)
			query += " where " + condition;

		MLogger.i(MLogger.MOD_DB, "Query: " + query);

		int retValue = 0;
		try {
			try {

				Statement statement = (Statement) DbManager
						.getInstance()
						.getConnection()
						.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

				ResultSet resultSet = statement.executeQuery(query);

				if (resultSet != null) {

					resultSet.next();
					retValue = resultSet.getInt(1);
				}

			} catch (Exception se) {

				MLogger.dumpException(se);
			}

		} catch (Exception e) {
			MLogger.e(MLogger.MOD_DB, mTableName + " query execution failed "
					+ e);
			MLogger.dumpException(e);
		}

		MLogger.i(MLogger.MOD_DB, "\t->Query Returned = " + retValue);

		return retValue;
	}

	
	public boolean exportTable(OutputStreamWriter osw, String whereCondition)
			throws SQLException, IOException {

		MLogger.i(MLogger.MOD_DB, "Exporting -> " + mTableName);

		DatabaseMetaData databaseMetaData = (DatabaseMetaData) getConnection()
				.getMetaData();

		ResultSet metaDataResultSet = databaseMetaData.getTables(null, null,
				mTableName, null);

		if (metaDataResultSet == null || !metaDataResultSet.next()
				|| osw == null) {

			MLogger.i(MLogger.MOD_DB, "Export failed, table not found");

			return false;
		}

		String sqlStatement = "DELETE FROM " + mTableName + ";\n";
		osw.write(sqlStatement);

		ResultSet resultSet = null;
		Statement statement = null;

		try {

			statement = (Statement) getConnection().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			String query = "SELECT * FROM " + mTableName;

			if (whereCondition != null && whereCondition.length() > 0) {

				query += " where " + whereCondition;
			}

			resultSet = statement.executeQuery(query);

		} catch (Exception se) {

			MLogger.dumpException(se);
		}

		resultSet.first();

		if (resultSet != null && resultSet.first()) {

			do {

				sqlStatement = getInsertStatement(resultSet);
				osw.write(sqlStatement + "\n");

			} while (resultSet.next());
		}

		if (resultSet != null) {

			resultSet.close();
			resultSet = null;
		}

		return true;
	}

	// Ask Sir, whether it is required
	public static ResultSet executeSql(String query) {

		Statement statement;
		ResultSet resultSet = null;
		try {
			statement = (Statement) getConnection().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			MLogger.dumpException(e);
		}
		
		return resultSet;
	}

	// Imdad,Harsh Modified
	// Single sql statement executor.
	/**
	 * Use only for INSERT, UPDATE, or DELETE
	 * 
	 * @param qStr
	 *            : query string
	 * @return : either the number of rows affected or 0 value
	 */

	public static int executeQuery(String query) {

		MLogger.i(MLogger.MOD_DB, "\t Executing Query -> " + query);

		int ret = DbErrorCodes.ERROR_CODE_OPERATION_FAILED;

		try {
			Statement mStatement = (Statement) getConnection()
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			ret = mStatement.executeUpdate(query);

		} catch (SQLException se) {

			MLogger.e(MLogger.MOD_DB, "\t Query Execution Failed!");
			MLogger.i(MLogger.MOD_DB, "Query: " + query);
			MLogger.dumpException(se);
		}

		return ret;
	}

	//
	public static long generateId(String tableName, byte state_id,
			byte district_id, byte taluk_id) {

		int firstNumber = 0;
		Long generatedId = null;

		String initialId = "";
		String state_id_string = "";
		String district_id_string = "";
		String taluk_id_string = "";

		if (state_id < 9)
			state_id_string = String.format("%02d", state_id);
		else
			state_id_string = Byte.toString(state_id);

		if (district_id < 9)
			district_id_string = String.format("%02d", district_id);
		else
			district_id_string = Byte.toString(district_id);
		if (taluk_id < 9)
			taluk_id_string = String.format("%02d", taluk_id);
		else
			taluk_id_string = Byte.toString(taluk_id);

		if (tableName.contains("organizer"))
			firstNumber = 2;
		else if (tableName.contains("independent_retail_distributor"))
			firstNumber = 3;
		else if (tableName.contains("director"))
			firstNumber = 0;
		else
			firstNumber = 1;

		initialId = "" + firstNumber + state_id_string + district_id_string
				+ taluk_id_string;

		if (initialId != null) {
			String query = "select (max(" + tableName + "_id))+1 from "
					+ tableName + " where round(" + initialId + ")";
			query = query + " and " + tableName + "_id like \"%" + initialId
					+ "%\"";
			try {
				try {

					Statement statement = (Statement) DbManager
							.getInstance()
							.getConnection()
							.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_READ_ONLY);

					ResultSet resultSet = statement.executeQuery(query);

					if (resultSet != null) {
						resultSet.next();

						generatedId = resultSet.getLong("(max(" + tableName
								+ "_id" + "))+1");

						if (generatedId == 0) {
							generatedId = Long.parseLong(initialId + "000");
						}
						return generatedId;
					}
				} catch (Exception se) {
					MLogger.e(MLogger.MOD_DB, "\t Query Execution Failed!");
					MLogger.i(MLogger.MOD_DB, "Query: " + query);
					MLogger.dumpException(se);
				}

			} catch (Exception e) {
				MLogger.e(MLogger.MOD_DB, " query execution failed " + e);
				MLogger.dumpException(e);
			}
		}
		return generatedId;
	}
}
