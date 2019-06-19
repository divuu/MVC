package com.technomedia.digipark.server.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DefaultServerSettings {
	// Local
	/* Default File Path */
	public final static String PATH_LOG_FILE = "C:/FTP Home/Server/logs";
	public static final String PATH_FILE_LOGO = "/images/logo";

	// Default database
	public static String DEFAULT_DB_NAME = "slash_digital_tvas";
	public static String DEFAULT_LOG_FILE = "/slashdigital.txt";

	//D:/TMSS/Project/Java/JavaSpace/.metadata/.plugins/org.eclipse.wst.server.core/tmp4/wtpwebapps/texERP/Teximco
//	public static final String DEFAULT_WEB_REPORT_FOLDER = "http://localhost:8080/texERP/";
//	public static final String DEFAULT_WEB_REPORT_FOLDER = "http://132.148.139.134:8080/texERP/";

	public static String DEFAULT_WEB_REPORT_FOLDER = "";
	
//	public static String DEFAULT_REPORT_CREATION_FOLDER = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\texData\\"; 
	public static String DEFAULT_REPORT_CREATION_FOLDER = "";
	
	private static DefaultServerSettings pThis = null;

	public static DefaultServerSettings getInstance() {
		if( pThis == null )
			pThis = new DefaultServerSettings();

		return pThis;
	}

	protected DefaultServerSettings() {
		pThis = this;
	}

	public static void main( String[] args ) {
		
		DefaultServerSettings.getInstance().loadFromPropertiesFile( "windows" );
	}

	public boolean loadFromPropertiesFile( String OS ) {

		boolean retval = false;
		InputStream input = null;
		try {

			if( OS.contains( "windows" ) )
				input = new FileInputStream( "C:/FTP Home/Server/slashdigital.properties" );
			else
				input = new FileInputStream( "/home/tmss/tomcat/conf/Teximco/Server/slashdigital.properties" );

			// load a properties file
			Properties prop = new Properties();

			prop.load( input );

			DEFAULT_DB_NAME = prop.getProperty( "DEFAULT_DB_NAME" );
			DEFAULT_LOG_FILE = prop.getProperty( "DEFAULT_LOG_FILE" );			
			DEFAULT_WEB_REPORT_FOLDER = prop.getProperty( "DEFAULT_WEB_REPORT_FOLDER" );
			DEFAULT_REPORT_CREATION_FOLDER = prop.getProperty( "DEFAULT_REPORT_CREATION_FOLDER" );
			
			retval = true;

		} catch( IOException ex ) {
			System.out.println( "If this exception is in the cloud server, then it is OK> Ignore it. Dont ignore if u see this exception in Local System" );
			ex.printStackTrace();
		} finally {
			if( input != null ) {
				try {
					input.close();
				} catch( IOException e ) {
					e.printStackTrace();
				}
			}
		}
		return retval;
	}

}
