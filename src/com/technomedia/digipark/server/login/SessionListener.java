package com.technomedia.digipark.server.login;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.technomedia.logger.MLogger;

/**
 * Application Lifecycle Listener implementation class SessionListener
 * 
 */
@WebListener
public class SessionListener implements HttpSessionListener {

	/**
	 * Default constructor.
	 */
	public SessionListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 * 
	 *      Adds sessions to the context scoped HashMap when they begin.
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {

		HttpSession session = event.getSession();
		ServletContext context = session.getServletContext();
		HashMap activeUsers = (HashMap) context.getAttribute("activeUsers");

		activeUsers.put(session.getId(), session);
		context.setAttribute("activeUsers", activeUsers);
		MLogger.i(MLogger.MOD_DB, "Session listener Added new session");
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 * 
	 *      Removes sessions from the context scoped HashMap when they expire or
	 *      are invalidated.
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext context = session.getServletContext();
		HashMap activeUsers = (HashMap) context.getAttribute("activeUsers");
		activeUsers.remove(session.getId());
		MLogger.i(MLogger.MOD_DB, "Session listener Removed one session");
	}

}
