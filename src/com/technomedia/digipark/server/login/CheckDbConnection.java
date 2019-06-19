package com.technomedia.digipark.server.login;

/**
 * Thread to keep connection awake. Since connection expires after wait time completes.
 * 
 */

import com.technomedia.logger.MLogger;
import com.technomedia.digipark.database.SysUser;
import com.technomedia.digipark.database.SysUserDbAdaptor;
import com.technomedia.digipark.database.base.DbManager;

public class CheckDbConnection extends Thread {

	public CheckDbConnection() {
		new Thread(this, "CheckDbConnection").start();
	}

	private int default_timeout = 7200000; // sec (2hrs)

	@Override
	public void run() {
		while (true) {
			System.out.println("Checking My sql connection");
			MLogger.i(MLogger.MOD_DB, "->>> Checking MySql connection");

			/*
			 *  On 01 Nov 2015, tried to use City table to open the db 
			 *  But for some unknown reason, the login page initially results
			 *  in an exception in SysUser.getData while trying to login.
			 *  So use CategoryDbAdaptor
			 * 
			 * 
			 */
			
//			Category cat = new Category();
//			CategoryDbAdaptor catDb = new CategoryDbAdaptor();
//
//			cat = catDb.getData(CategoryDbAdaptor.KEY_CATEGORY_ID + "<>0");
			SysUser sysUser = new SysUser();
			SysUserDbAdaptor sysUserDbAdaptor = new SysUserDbAdaptor();
			
			sysUser = sysUserDbAdaptor.getData(SysUserDbAdaptor.KEY_SYS_USER_ID + "=1");
			
			if (sysUser != null) {
				MLogger.i(MLogger.MOD_DB, " Checking Connection Success.");
			} else {
				DbManager.destroy();
				DbManager.init();
				MLogger.i(MLogger.MOD_DB, " Checking Connection Failed.");
			}
			try {
				Thread.sleep(default_timeout);
			} catch (InterruptedException e) {
				MLogger.dumpException(e);
				e.printStackTrace();
			}
		}
	}

}
