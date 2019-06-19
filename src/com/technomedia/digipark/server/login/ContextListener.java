package com.technomedia.digipark.server.login;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.technomedia.logger.MLogger;

/**
 * Application Lifecycle Listener implementation class ContextListener
 * 
 * Instanciates a HashMap for holding references to session objects, and binds
 * it to context scope.
 */
@WebListener
public class ContextListener implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public ContextListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {

		ServletContext context = event.getServletContext();
		//
		// instanciate a map to store references to all the active
		// sessions and bind it to context scope.
		//
		HashMap activeUsers = new HashMap();
		context.setAttribute("activeUsers", activeUsers);
		MLogger.i(MLogger.MOD_DB, "Context listener added active users");
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// To overcome the problem with losing the session references
		// during server restarts, put code here to serialize the
		// activeUsers HashMap. Then put code in the contextInitialized
		// method that reads and reloads it if it exists.
	}

}
