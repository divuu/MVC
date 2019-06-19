package com.technomedia.digipark.database.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.technomedia.digipark.server.common.DefaultServerSettings;

public class DbManager {
	private static DbManager mInstance = null;
	private static Connection mConnection = null;
	// Add a method called init() and read the params from properties file
	// Constants to be defined for the connection
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_HOST = "localhost";
	private static final String DB_USERNAME = "slashdigital";
	private static final String DB_PASSWORD = "password@123";
	
//	private static final String DB_USERNAME = "root";
//	private static final String DB_PASSWORD = "root";

	
	/*
	 * private static final String DB_USERNAME = "technome_root"; private static
	 * final String DB_PASSWORD = "tmss@123
	 */private static final String DB_NAME = DefaultServerSettings.DEFAULT_DB_NAME;
	private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":3306/"
			+ DB_NAME + "";

	static String mTableName = null;

	private DbManager() {

	}

	public static void init() {

		try {
			
			if (mConnection != null && mConnection.isClosed() == false)
				return;
			
	    	String OS = System.getProperty("os.name").toLowerCase();
	    	
			DefaultServerSettings.getInstance().loadFromPropertiesFile( OS );

			System.out.println( "Starting database: "  + DB_NAME );

			Class.forName(DB_DRIVER).newInstance();

			mConnection = DriverManager.getConnection(DB_URL, DB_USERNAME,
					DB_PASSWORD);// create connection

		} catch (SQLException err) {

			err.printStackTrace();

		} catch (InstantiationException e) {

			e.printStackTrace();

		} catch (IllegalAccessException e) {

			e.printStackTrace();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	static void setTableName(String tableName) {

		mTableName = tableName;
	}

	// method to be called to get DbManager object(singleton)
	public static DbManager getInstance() {

		if (mInstance == null) {
			mInstance = new DbManager();
		}
		init();// if connection closes reopen
		return mInstance;
	}

	// method to be called to get connection
	public Connection getConnection() {
		init(); // if connection closes reopen
		return mConnection;
	}

	// method to be called to close connection
	public static void disconnect() {
		if (mConnection == null)
			return;
		try {
			mConnection.close();
			mConnection = null;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// method to be called to destroy object
	public static void destroy() {
		disconnect();
	}
}