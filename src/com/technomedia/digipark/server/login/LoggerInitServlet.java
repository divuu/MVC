package com.technomedia.digipark.server.login;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.technomedia.logger.MLogger;
import com.technomedia.digipark.database.base.DbManager;
import com.technomedia.digipark.server.common.DefaultServerSettings;
import com.technomedia.utils.BitUtils;

/**
 * Servlet implementation class LoggerInitServlet Initializae MLogger for the
 * server
 */
@WebServlet(description = "Initialize Logger Thread", urlPatterns = { "/LoggerInitServlet" }, loadOnStartup = 1)
public class LoggerInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static boolean mLoggerRunning = false;

	String mLoggerFilePath = null;

	private static final String DEFAULT_LOGGER_FILE_PATH = DefaultServerSettings.PATH_LOG_FILE
			+ DefaultServerSettings.DEFAULT_LOG_FILE;

	MLogger mLogger = null;
	Thread mLoggerThread = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoggerInitServlet() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// Thread to check Db connection

		System.out.println("Starting Logger Thread thread--->");

		if (mLoggerRunning == false) {

			initLogger();
			mLoggerRunning = true;
			// purge enabled
			MLogger.purgeFile(DEFAULT_LOGGER_FILE_PATH, 0);
		}
		DbManager.init();
	}

	// TODO : UI interface for reading logging options/modules, later.
	private void initLogger() {

		/*** File logFlie = new File(DEFAULT_LOGGER_FILE_PATH); logFlie. */

		mLogger = new MLogger();

		// Local
		if (mLoggerFilePath == null || mLoggerFilePath.length() <= 0)
			mLoggerFilePath = DEFAULT_LOGGER_FILE_PATH;
		// Cpanel Server
		if (mLoggerFilePath == null || mLoggerFilePath.length() <= 0)
			mLoggerFilePath = DEFAULT_LOGGER_FILE_PATH;

		mLogger.setFileName(mLoggerFilePath);

		mLogger.setLoggingOptions(getLoggingOptions());
		mLogger.setDebugModules(getLoggingModules());

		try {

			mLogger.start();

		} catch (Exception e) {

			System.err.println("Thread already instantiated");
			MLogger.dumpException(e);
		}
	}

	private int getLoggingModules() {

		int debugModules = 0;

		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_APP);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_DB);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_EXCEPTION);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_FILE_IO);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_FTP_CLIENT);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_GPRS);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_GPS);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_GSM);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_INSTALLER);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_NETWORK);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_OTHERS);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_SMS);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_UI);
		debugModules |= BitUtils.SET_BIT(debugModules, MLogger.MOD_UTILS);

		return debugModules;
	}

	private byte getLoggingOptions() {

		byte options = 0;

		options = (byte) BitUtils.SET_BIT(options,
				MLogger.LOG_OPTION_WRITE_TO_FILE);
		// options = (byte) BitUtils.SET_BIT(options,
		// MLogger.LOG_OPTION_WRITE_TO_SOCKET);

		options |= (byte) BitUtils.SET_BIT(options,
				MLogger.LOG_OPTION_WRITE_DEBUG_LOGGER);

		// options |= (byte) BitUtils.SET_BIT(options,
		// MLogger.LOG_OPTION_WRITE_RAW_GPS_DATA);

		mLogger.setServerAddress("192.168.1.20");

		return options;
	}
}
