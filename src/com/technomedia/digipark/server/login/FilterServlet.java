package com.technomedia.digipark.server.login;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.technomedia.logger.MLogger;

/**
 * Servlet Filter implementation class FilterServlet
 */
@WebFilter(urlPatterns = { "/*" }, description = "Session Checker Filter")
public class FilterServlet implements Filter {

	/**
	 * Default constructor.
	 */
	public FilterServlet() {

	}

	private FilterConfig config = null;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {

		this.config = fConfig;
		config.getServletContext().log("Initializing SessionCheckerFilter");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String resourcePath = "";
		ServletContext contxt = req.getServletContext();
		String servletPath = req.getServletPath(); // /servlet/MyServlet
		String pathInfo = req.getPathInfo(); // /a/b;c=123
		String queryString = req.getQueryString(); // d=789
		String rqUri = req.getRequestURI();
		StringBuffer rqUrl = req.getRequestURL();
		String method = req.getMethod();

		pathInfo = pathInfo != null ? pathInfo : "";

		// For test purpose
		/* MLogger.i(MLogger.MOD_DB, "Resource req "+rqUri); */
		// MLogger.i(MLogger.MOD_DB, "Resource URI: "+rqUri);
		// MLogger.i(MLogger.MOD_DB, "Resource URL: "+rqUrl.toString());

		if (queryString != null) {
			MLogger.i(MLogger.MOD_DB, "Resource Complete Path: " + servletPath
					+ pathInfo + "?" + queryString);
		} else {
			MLogger.i(MLogger.MOD_DB, "Resource Complete Path: " + servletPath
					+ pathInfo);
		}

		boolean isAuthenticated = false;
		HttpSession userSession = req.getSession(false);

		String s_id = null;// Session id

		// Is session info is null check for session id in url info
		if (userSession == null) {
			s_id = request.getParameter("s_id"); // search in url

			// Find s_id for non GET request check in header info
			if ((s_id == null || s_id == "") && method != "GET") {

				Enumeration<String> headerNames = req.getHeaderNames();

				while (headerNames.hasMoreElements()) {
					String headerName = (String) headerNames.nextElement();

					if (headerName.equalsIgnoreCase("s_id")) {
						s_id = req.getHeader(headerName);
						break;
					}
				}
				if (s_id != null && s_id != "") {
					MLogger.i(MLogger.MOD_DB, "Session id found in header");
				} else {
					MLogger.e(MLogger.MOD_DB, "Session id not found in header");
				}
			} else {
				MLogger.e(MLogger.MOD_DB, "Session id found in Url info");
			}
		}

		// Authenticated string
		String isAut = null;

		if (userSession != null) { // session available
			isAut = (String) req.getSession().getAttribute("isauthenticated");
			if (isAut != null) {
				isAuthenticated = true;
			}
		} else if (s_id != null && s_id != "") { // get session by id
			HashMap activeUsers = (HashMap) contxt.getAttribute("activeUsers");

			if (activeUsers != null) {
				HttpSession sessRef = (HttpSession) activeUsers.get(s_id);

				if (sessRef != null) {
					isAut = (String) sessRef.getAttribute("isauthenticated");

					if (isAut != null) {
						isAuthenticated = true;
						userSession = sessRef;
					}
				}
			} else {
				MLogger.e(MLogger.MOD_DB, "Active Users empty");
			}
		} else {
			isAuthenticated = false;
		}

		String redirectURL = servletPath;

		// Added for cpanel web server
		if (rqUri.endsWith("action.do")) {
			// get real url
			if (queryString != null) {
				rqUri = queryString.replaceAll(
						"(url_path=)([\\w\\/]*\\.*[\\w]*)([&]{0,1}.*)", "$2"); // read
																				// query
																				// value
																				// from
																				// string.
				redirectURL = rqUri;
			} else {
				rqUri = "/index.html";
			}
		}

		// control url access
		if (!rqUri.endsWith("login.html")
				&& !rqUri.endsWith("checkServerOnline.html")
				&& !rqUri.endsWith("login")
				&& !rqUri.endsWith("forgotPassword")
				&& !rqUri.endsWith("cookie.js")
				&& !rqUri.endsWith("jquery-1.8.3.js")
				&& !rqUri.endsWith("ra_icon.ico")
				&& !rqUri.endsWith("VehicleLocationDataHandler/update")
				&& !rqUri.endsWith("StopReachedHistoryDataHandler/insert")
				&& !rqUri.endsWith("VehicleLocationDataHandler/select")
				&& !rqUri.endsWith("StopReachedHistoryDataHandler/select")
				&& !isAuthenticated) {

			res.setHeader("Cache-Control",
					"private, no-store, no-cache, must-revalidate");// HTTP 1.1
			res.setHeader("Pragma", "no-cache");// HTTP 1.0
			res.setDateHeader("Expires", 0);
			res.sendRedirect(req.getContextPath() + "/login.html?redirectURL="
					+ redirectURL);

			return;
		}
		// MLogger.i(MLogger.MOD_DB,
		// "Is authenticated "+req.getSession().getAttribute("isauthenticated")
		// );
		// MLogger.i(MLogger.MOD_DB, "Uri "+req.getRequestURI());

		String[] uriArr = req.getRequestURI().split("/");
		String resPath = null;
		if (uriArr != null && uriArr.length > 0) {
			resPath = uriArr[uriArr.length - 1].trim();
		}
		// TODO
		if (resPath != null) {
			// MLogger.i(MLogger.MOD_DB, "Resource "+resPath);
			if (resPath != "login.html" && resPath != "checkServerOnline.html") {

			}
		}

		resourcePath = contxt.getContextPath();

		if (isAuthenticated == true) { // logged in
			// get user id from session
			String sessStr = (String) userSession.getAttribute("userInfo");

			if (sessStr != null) {
				JSONObject jObj;
				try {
					jObj = new JSONObject(sessStr);

					int userId = jObj.getInt("ID");
					int roleId = jObj.getInt("S_ROLE");

					// set these attribute to request
					request.setAttribute("S_ROLE_ID", roleId);
					request.setAttribute("S_USER_ID", userId);

				} catch (JSONException e) {
					// TODO catch
					e.printStackTrace();
				}
			}
		}

		// MLogger.i(MLogger.MOD_DB, "Context Path "+resourcePath);

		// set no cache for .html
		if (req.getRequestURI().endsWith(".html")) {
			res.setHeader("Cache-Control",
					"private, no-store, no-cache, must-revalidate");// HTTP 1.1
			res.setHeader("Pragma", "no-cache");// HTTP 1.0
			res.setDateHeader("Expires", -1);// Proxies. /TODO review
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		config.getServletContext().log("Destroying SessionCheckerFilter");
	}
}
