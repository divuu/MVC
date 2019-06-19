//package com.technomedia.digipark.server.login;
package com.technomedia.digipark.server.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.technomedia.digipark.database.SysUser;
import com.technomedia.digipark.database.SysUserDbAdaptor;

/**
 * Servlet implementation class LogoutServlet
 */

@WebServlet(name = "LogoutServlet", displayName = "LogoutServlet", urlPatterns = { "/logout" }, description = "Loout user")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in logout servlet...");

		PrintWriter out = response.getWriter();

		// empty cache
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", -1);
		response.setHeader("Pragma", "no-cache");

		HttpSession session = request.getSession();
		String sessionAttr = (String) session.getAttribute("userInfo"); // get
																		// user
																		// info
																		// from
																		// session

		Cookie[] cookies = request.getCookies();

		try {
			if (sessionAttr != null) {
				JSONObject jObj = new JSONObject(sessionAttr);
				if (jObj.getString("S_UTYPE") != null) {
					String utype = jObj.getString("S_UTYPE");

					if (utype == "sysuser") {
						long time = System.currentTimeMillis();

						String Id = jObj.getString("ID");
						String uid = jObj.getString("S_UID");

						SysUser su = new SysUser();
						SysUserDbAdaptor sudb = new SysUserDbAdaptor();
						String condition = SysUserDbAdaptor.KEY_EMAIL + "='"
								+ uid + "'";

						condition += " and " + SysUserDbAdaptor.KEY_SYS_USER_ID
								+ "=" + Id;

						su = sudb.getData(condition);

//						Long loginTime = su.mLastLoginDate;
//						int onlineMinutes = (int) (time - loginTime) / 60000; // Minutes
//
//						su.mOnlineMinutes = onlineMinutes;

						Long ststus = sudb.update(su, condition);

						if (ststus >= 0) {
							System.out.println("User online Minutes updated.");
						}
					}

				}
			} else {
				response.sendRedirect(request.getContextPath() + "/login.html");
				out.println("You are alraedy logged out.");
				return;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Updating Cookies: ");

		if (cookies != null)
			for (int i = 0; i < cookies.length; i++) {
				System.out.println("Cookie " + cookies[i].getName());
				System.out.println("Path " + cookies[i].getPath());
				System.out.println("Domain " + cookies[i].getDomain());

				cookies[i].setValue("-");
				// cookies[i].setPath("/"+request.getContextPath());
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}

		// update cookies
		/*
		 * Cookie cookie1 = new Cookie("JSESSIONID", ""); Cookie cookie2 = new
		 * Cookie("name", ""); Cookie cookie3 = new Cookie("userId", "");
		 * //Cookie cookie4 = new Cookie("fn", ""); cookie1.setMaxAge(0);
		 * cookie1.setPath("/" + request.getContextPath());
		 * cookie2.setMaxAge(0); cookie2.setPath("/" +
		 * request.getContextPath()); cookie3.setMaxAge(0); cookie3.setPath("/"
		 * + request.getContextPath()); //cookie4.setMaxAge(0);
		 * //cookie4.setPath("/" + request.getContextPath());
		 * 
		 * response.addCookie(cookie1); response.addCookie(cookie2);
		 * response.addCookie(cookie3); //response.addCookie(cookie4);
		 */
		if (request.getSession() != null) {
			request.getSession().invalidate();
		}

		try {
			response.sendRedirect(request.getContextPath() + "/login.html");
			out.println("You are successfully logged out.");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to redirect.");
		}

		// String redirectPage = request.getContextPath() + "/login.html";

		// response.sendRedirect(redirectPage);

		/*
		 * try { JSONObject obj = new JSONObject(); obj.put("rc", 0);
		 * obj.put("redirectURL", redirectPage); out.println(obj.toString()); }
		 * catch (Exception e) { e.printStackTrace();
		 * out.println("{\"redirectURL\" : \"" + redirectPage + "\"}"); }
		 */

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
