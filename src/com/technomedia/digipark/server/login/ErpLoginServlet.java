package com.technomedia.digipark.server.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.technomedia.logger.MLogger;
import com.technomedia.digipark.database.SysUser;
import com.technomedia.digipark.database.SysUserDbAdaptor;
import com.technomedia.digipark.database.utils.MiscUtils;

/**
 * 
 * @author Imdad
 */

@WebServlet(name = "ErpLoginServlet", displayName = "LoginServlet", urlPatterns = { "/login" }, description = "Login user")
public class ErpLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// Thread to check Db connection
		try {
			System.out.println("Starting mysql connection check thread--->");
			new CheckDbConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("In login servlet...");
		// get all parameters
		Enumeration<String> enum1 = request.getParameterNames();
		while (enum1.hasMoreElements()) {
			System.out.println("PARM: " + enum1.nextElement());
		}

		String source = (String) request.getParameter("source");
		System.out.println("Source: " + source);

		if (source.equals("auth")) { // authenticate to RR

			PrintWriter out = response.getWriter();
			String UserId = request.getParameter("j_username");

			if (UserId == "" || UserId == null) {
				JSONObject jo = new JSONObject();
				try {
					jo.put("message", "Please enter a valid User ID");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MLogger.dumpException(e);
				}
				out.println(jo.toString());
				return;
			}

			
//			if( MiscUtils.checkNumeric( UserId ) == true ) {
//				
//				if( UserId.startsWith("1")) {		// depots start with 1 -> has been decided by genius
//					authenticateDepots(request, response);
//					return;
//				}
//				else if( UserId.startsWith("7") ) {
//					authenticateGiEntity(request, response);
//					return;
//				}
//			}
			authenticateSysUser(request, response);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Login Servlet";
	}// </editor-fold>

	
	
	/*
	 * Authenticate Depots who are already logged in creates a session that is
	 * sent back to the client
	 */
	
/*	
	private void authenticateDepots(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String UserId = request.getParameter("j_username");
		
		DepotDbAdaptor depotDbAdaptor = new DepotDbAdaptor();
		depotDbAdaptor.open();
		String condition = DepotDbAdaptor.KEY_DEPOT_ID + "=" + UserId;
		
		Depot depot = depotDbAdaptor.getData(condition);
		String depoTrasnactionmenu="<ul class='tab_link' id=''>"+
  				"<li><a href='#' rel='InventoryDetails' title='Inventory'>Inventory</a></li>"+
  				"</ul>"	;
		
		String depoAstersnmenu="<ul class='tab_link' id=''>"+
 			"<li><a href='#'rel='DepotDetails' title='Depot'>Depot</a></li>"+
//			"<li><a href='#' rel='GodownDetails' title='Godown'>Godown</a></li>"+
 			"<li><a href='#' rel='ProductDetails' title='Product'>Product</a></li>"+
			"<li><a href='#' rel='ProductCategoryDetails' title='Product Category'>Product Category</a></li>"+
			"<li><a href='#' rel='ProductCategoryHeaderDetails' title='Product Category Header'>Product Category Header</a></li>"+
 			"<li><a href='#' rel='StateDetails' title='State'>State</a></li>"+
			"<li><a href='#' rel='DistrictDetails' title='District'>District</a></li>"+
			"<li><a href='#' rel='TalukDetails' title='Taluk'>Taluk</a></li>"+
 		    "<li><a href='#' rel='VendorDetails' title='VendorDetails'>Vendors</a></li>"+
			"</ul>";
		
		JSONObject userInfo = new JSONObject();
		try {
			if (depot != null) {// user exists
				MLogger.i(MLogger.MOD_APP, "Login: User Found for Id " + UserId);
				userInfo.put("id", depot.mDepotId);
				userInfo.put("uid", depot.mDepotId);
				userInfo.put("pwd", depot.mPassword);
				userInfo.put("salt", depot.mSalt);
				userInfo.put("role", depot.mDepotTypeLookupId);
				userInfo.put("name", depot.mName);
				userInfo.put("userType", "Depot");
				userInfo.put("userTransactionMenu",depoTrasnactionmenu);
				userInfo.put("userMastersMenu", depoAstersnmenu);
			} 
			else {

				MLogger.i(MLogger.MOD_APP, "Login: Depot User not Found for Id " + UserId);
				JSONObject jo = new JSONObject();
				jo.put("message", "User Id you have entered is not Registered.");
				out.println(jo.toString());
				return;
			}
		} catch (Exception e) {
			MLogger.dumpException(e);			
		}

		String password = request.getParameter("j_password");

		// System.out.println("password: " + password);

		if (password == null || password.length() == 0) {
			JSONObject jo = new JSONObject();
			MLogger.i(MLogger.MOD_APP, "User Password not provided.");
			try {
				jo.put("message",
						"Invalid password, login with your registered user name and password");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				MLogger.dumpException(e);
			}
			out.println(jo.toString());
			return;
		}

		try {

			System.out.println("userdetails: " + userInfo.toString(2));
			
			// Currently we will allow the user to login using
			
			if (AuthenticationUtil.authenticate(password, userInfo.getString("pwd"), userInfo.getString("salt"))) 			
			{
				MLogger.i(MLogger.MOD_APP,
						"Depot User Authenticated successfully with Id " + UserId);
				// user authenticated successfully
				// create new user session and pass response back to client with
				// cookie

				System.out.println("Depot user successfully authenticated");
				createSessionAndRespond(request, response, userInfo);

			} else {
				MLogger.i(MLogger.MOD_APP,
						"Authentication Failed for User with Id " + UserId);
				JSONObject jo = new JSONObject();
				jo.put("message",
						"Invalid user or password, login with your registered email and password");

				out.println(jo.toString());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MLogger.dumpException(e);
			out.println("{\"message\":\"Invalid user or password, login with your registered email and password\"}");
			return;
		}
	}
*/
	
	
	/*
	 * Authenticate GI Entities who are already logged in creates a session that is
	 * sent back to the client
	 */
	
/*	
	private void authenticateGiEntity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String UserId = request.getParameter("j_username");
		
		GiEntityDbAdaptor giEntityDbAdaptor = new GiEntityDbAdaptor();
		giEntityDbAdaptor.open();
		String condition = GiEntityDbAdaptor.KEY_GI_ENTITY_ID + "=" + UserId;
		
		GiEntity giEntity = giEntityDbAdaptor.getData(condition);
		
		try {
			if (giEntity == null) {	// user doesnt exist.

				MLogger.i(MLogger.MOD_APP, "Login: User not Found for Id " + UserId);
				JSONObject jo = new JSONObject();
				jo.put("message", "User Id you have entered is not Registered.");
				out.println(jo.toString());
				return;
			}
		} catch (Exception e) {

			MLogger.dumpException(e);
		}

		String password = request.getParameter("j_password");

		// System.out.println("password: " + password);

		if (password == null || password.length() == 0) {
			JSONObject jo = new JSONObject();
			MLogger.i(MLogger.MOD_APP, "User Password not provided.");
			try {
				jo.put("message", "Invalid password, login with your registered user name and password");
			} 
			catch (JSONException e) {

				MLogger.dumpException(e);
			}
			out.println(jo.toString());
			return;
		}

		try {

			String entityTrasnactionmenu="<ul class='tab_link123' id=''>"+ 
					"<li><a href='#' rel='AwardDetails' title='Awards'>NONE</a></li>"+
	 			 "</ul>";
			
			String entityMastersnmenu="<ul class='tab_link123' id=''>"+
		 			"<li><a href='#' rel='ViewGiEntityProfile' title='Genius Independent Entity'>View Profile</a></li>"+
		 			"<li><a href='#' rel='GiWelcomeLetter' title='Genius Independent Entity'>Welcome Letter</a></li>"+
					"<li><a href='#' rel='GiIncentive' title='Genius Independent Entity'>Incentive</a></li>"+
					"<li><a href='#' rel='GiCustomerDetails' title='Direct GICs'>Direct GICs</a></li>"+
					"<li><a href='#' rel='AwardRewards' title='Genius Independent Entity'>Award Rewards</a></li>"+
					"<li><a href='#' rel='BusinessDetails' title='Business'>Business</a></li>" +
					"<li><a href='#' rel='InteractionDetails' title='Interaction'>Interaction</a></li>" +
					"<li><a href='#' rel='AwardTrialDetails' title='Award Trial'>Award Trial</a></li>" +
		 			"</ul>";
			
					
//		 			"<li><a href='#' rel='ViewGiEntityProfile' title='View Profile'>View Profile</a></li>"+
//		 			"<li><a href='#' rel='GiWelcomeLetter' title='Welcome Letter'>Welcome Letter</a></li>"+
//		 			"<li><a href='#' rel='GiIdCard' title='ID Card'>ID Card</a></li>"+
//					"<li><a href='#' rel='GiIncentive' title='Incentive'>Incentive</a></li>"+
//					"<li><a href='#' rel='DirectGICs' title='Direct GICs'>Direct GICs</a></li>"+
//					"<li><a href='#' rel='AwardRewards' title='Award Rewards'>Award Rewards</a></li>"+
//		 			"</ul>";
			
			MLogger.i(MLogger.MOD_APP, "Login: User Found for Id " + UserId);
			JSONObject userInfo = new JSONObject();
			userInfo.put("id", giEntity.mGiEntityId);
			userInfo.put("uid", giEntity.mGiEntityId);
//			userInfo.put("pwd", ""//giEntity.mPassword);
//			userInfo.put("salt", ""//su.mSalt);
			userInfo.put("role", giEntity.mDesignationLookupId);
			userInfo.put("name", giEntity.mName);
			userInfo.put("userType", "GiEntity");
			userInfo.put("userTransactionMenu", entityTrasnactionmenu);
			userInfo.put("userMastersMenu", entityMastersnmenu);
			
			System.out.println("userdetails: " + userInfo.toString(2));
			
			// Currently we will allow the user to login using
			
//			if (AuthenticationUtil.authenticate(password,
//					userInfo.getString("pwd"), userInfo.getString("salt"))) 
			
			if( true //password == giEntity.mMobileNumber
			)
			{
				MLogger.i(MLogger.MOD_APP,
						"User Authenticated successfully with Id " + UserId);
				// user authenticated successfully
				// create new user session and pass response back to client with
				// cookie

				System.out.println("user successfully authenticated");
				createSessionAndRespond(request, response, userInfo);

			} else {
				MLogger.i(MLogger.MOD_APP,
						"Authentication Failed for User with Id " + UserId);
				JSONObject jo = new JSONObject();
				jo.put("message",
						"Invalid user or password, login with your registered email and password");

				out.println(jo.toString());
				return;
			}
		} catch (Exception e) {

			MLogger.dumpException(e);
			out.println("{\"message\":\"Invalid user or password, login with your registered email and password\"}");
			return;
		}

	}
*/	
	
	/*
	 * Authenticate Sys Users who are already logged in creates a session that is
	 * sent back to the client
	 */
	private void authenticateSysUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String UserId = request.getParameter("j_username");

		if (UserId == "" || UserId == null) {
			JSONObject jo = new JSONObject();
			try {
				jo.put("message", "Please enter a valid User ID");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MLogger.dumpException(e);
			}
			out.println(jo.toString());
			return;
		}
		
			

		// String userType = "";
		// TODO: //check if user exists
		// get user data: if null return false
		// check for sysuser
		SysUser su = new SysUser();
		SysUserDbAdaptor sudb = new SysUserDbAdaptor();
		sudb.open();
		String condition = SysUserDbAdaptor.KEY_EMAIL + "='" + UserId + "'";

		su = sudb.getData(condition);

		/*
		 * ParentInfo pri = new ParentInfo(); ParentInfoDbAdaptor pridb = new
		 * ParentInfoDbAdaptor();
		 */
		
		String sysuserTrasnactionmenu="<ul class='tab_link' id=''>"+
				"<li><a href='#' rel='PrescriptionDetails' title='Prescription'>Prescriptions</a></li>"+
				"<li><a href='#' rel='PurchaseOrderDetails' title='Purchase Order'>Purchase Order</a></li>"+
				"<li><a href='#' rel='GoodsReceivedNoteDetails' title='Goods Received Note'>Goods Received Note</a></li>"+
				"<li><a href='#' rel='CommercialInvoiceDetails' title='Commercial Invoice'>Commercial Invoice</a></li>"+
			    "<li><a href='#' rel='InventoryDetails' title='Inventory'>Inventory</a></li>"+
 				"</ul>"	;
		
		String sysuserMastersnmenu="<ul class='tab_link' id=''>"+
									"<li><a href='#' rel='OrganizationDetails' title='Organization'>Organization</a></li>"+
									"<li><a href='#' rel='OrganizationParametersDetails' title='Org Parameters'>Org Parameters</a></li>"+
 									"<li><a href='#' rel='SysUserDetails' title='Sys User'>Sys User</a></li>"+
 									"<li><a href='#' rel='RoleDetails' title='Role'>Role</a></li>"+
 									"<li><a href='#' rel='PatientDetails' title='Patient'>Patient</a></li>"+
  									"<li><a href='#' rel='BankAccountDetails' title='Bank Account'>Bank Account</a></li>"+
//  								    "<li><a href='#' rel='GodownDetails' title='Godown'>Godown</a></li>"+
									"<li><a href='#' rel='PharmacyDetails' title='Pharmacy'>Pharmacy</a></li>"+
									"<li><a href='#' rel='DosageDetails' title='Pharmacy'>Dosage</a></li>"+
  									"<li><a href='#' rel='ProductDetails' title='Product'>Product</a></li>"+
									"<li><a href='#' rel='ProductCategoryDetails' title='Product Category'>Product Category</a></li>"+
								    "<li><a href='#' rel='VendorDetails' title='Vendors'>Vendors</a></li>"+
								    
  									"</ul>";
	
		// Create another one for reports
		
		JSONObject userInfo = new JSONObject();
		try {
			if (su != null) {// user exists
				MLogger.i(MLogger.MOD_APP, "Login: User Found for Id " + UserId);
				// userType = "sysuser";
				userInfo.put("id", su.mSysUserId);
				userInfo.put("uid", su.mEmail);
				userInfo.put("pwd", su.mPassword);
				userInfo.put("salt", su.mSalt);
				userInfo.put("role", su.mRoleId);
				userInfo.put("name", su.mName);
				userInfo.put("userType", "sysuser");
				userInfo.put("userTransactionMenu", sysuserTrasnactionmenu);
				userInfo.put("userMastersMenu",sysuserMastersnmenu);


			} 
			else {

				MLogger.i(MLogger.MOD_APP, "Login: User not Found for Id "
						+ UserId);
				JSONObject jo = new JSONObject();
				jo.put("message",
						"User name or email you have entered is not Registered.");
				out.println(jo.toString());
				return;
				// }
			}
		} catch (Exception e) {
			// TODO
			MLogger.dumpException(e);
		}

		String password = request.getParameter("j_password");

		// System.out.println("password: " + password);

		if (password == null || password.length() == 0) {
			JSONObject jo = new JSONObject();
			MLogger.i(MLogger.MOD_APP, "User Password not provided.");
			try {
				jo.put("message",
						"Invalid password, login with your registered user name and password");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MLogger.dumpException(e);
			}
			out.println(jo.toString());
			return;
		}

		try {

			System.out.println("userdetails: " + userInfo.toString(2));

			if (AuthenticationUtil.authenticate(password,
					userInfo.getString("pwd"), userInfo.getString("salt"))) {
				MLogger.i(MLogger.MOD_APP,
						"User Authenticated successfully with Id " + UserId);
				// user authenticated successfully
				// create new user session and pass response back to client with
				// cookie

				System.out.println("user successfully authenticated");
				createSessionAndRespond(request, response, userInfo);

			} else {
				MLogger.i(MLogger.MOD_APP,
						"Authentication Failed for User with Id " + UserId);
				JSONObject jo = new JSONObject();
				jo.put("message",
						"Invalid user or password, login with your registered email and password");

				out.println(jo.toString());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MLogger.dumpException(e);
			out.println("{\"message\":\"Invalid user or password, login with your registered email and password\"}");
			return;
		}

	}

	/*
	 * Creates a user session object, persists it uploads the unique session id
	 * into the cookie and sets the cookie in the response
	 */
	private void createSessionAndRespond(HttpServletRequest request,
			HttpServletResponse response, JSONObject uo)
			throws ServletException, IOException {
		// get redirect page if available
		// create new session
		// add user information and session to cookie
		// send response back to client
		PrintWriter out = response.getWriter();

		String rdURL = request.getParameter("redirectURL");
		System.out.println("rdURL: " + rdURL);
		String redirectPage = null;

		try {
			Cookie[] cookies = request.getCookies();
			
			String userType = uo.getString("userType" );

//			if (userType == "GiEntity")
//				redirectPage = getURL(request) + "/giindex.html";
//			else if (userType == "Depot")
//				redirectPage = getURL(request) + "/depotindex.html";
//			else if (rdURL != null) {
//				redirectPage = getURL(request) + rdURL;
//			}
//			else 
			{
				redirectPage = getURL(request) + "/index.html";
			}
			if (cookies != null) {
				for (Cookie ck : cookies) {
					if ("redirectPage".equals(ck.getName())) {
						redirectPage = ck.getValue();
						break;
					}
				}
			}

			long time = System.currentTimeMillis();

			HttpSession session = request.getSession(true);

			if (session == null) {
				MLogger.i(MLogger.MOD_APP, "Unable to create session");

				JSONObject outjson = new JSONObject();
				outjson.put("message", "Could not create session...");
				out.println(outjson.toString(2));
				return;
			} else {
				MLogger.i(MLogger.MOD_APP, "created new session");
			}

			// String sid = session.getId();

			JSONObject success = new JSONObject();
			JSONObject sObj = new JSONObject();

			// update last login date
//			String utype = uo.getString("userType");
			if (userType == "sysuser") {
				String Id = uo.getString("id");// id
				String uid = uo.getString("uid");// email
				SysUser su = new SysUser();
				SysUserDbAdaptor sudb = new SysUserDbAdaptor();
				String condition = SysUserDbAdaptor.KEY_EMAIL + "='" + uid
						+ "'";// should be unique

				condition += " and " + SysUserDbAdaptor.KEY_SYS_USER_ID + "="
						+ Id;

				su = sudb.getData(condition);

				su.mLastLoginDate = time;
				Long ststus = sudb.update(su, condition);

				if (ststus > 0) {
					MLogger.i(MLogger.MOD_APP, "User Login time updated.");
				}
			}

			String _s_id = null;

			// Add session object
			sObj = AuthenticationUtil.createUserSession(uo);
			synchronized (session) {
				session.setAttribute("userInfo", sObj.toString());
				session.setAttribute("isauthenticated", "Authenticated User");

				// TODO: remove later
				_s_id = session.getId();

				// if remember me TODO
				Enumeration<String> enum1 = request.getParameterNames();
				while (enum1.hasMoreElements()) {
					String param = (String) enum1.nextElement();
					String paramVal = (String) request.getParameter(param);
					// System.out.println("Param "+param+" Val "+paramVal);
					if (param.equals("remember") && paramVal.equals("on")) {
						session.setMaxInactiveInterval(7 * 24 * 60 * 60); // 1
																			// week
						// System.out.println("Remember me on");
						break;
					}
				}
			}

			Cookie ck = new Cookie("name", uo.getString("name"));
			// ck.setPath("/"+request.getContextPath());
			response.addCookie(ck);
			response.setContentType("text/JSON");
			
			System.out.println("sObj==="+sObj.get("ID"));
			success.put("sObjusession", sObj.get("ID"));
 
			success.put("usession", sObj);

			if( userType == "GiEntity")
				redirectPage = getURL(request) + "/index.html";			
			else if( userType == "Depot")
				redirectPage = getURL(request) + "/index.html";			
			else if (rdURL != null) {
				redirectPage = getURL(request) + rdURL;
			} else {
				redirectPage = getURL(request) + "/index.html";
			}
			

			success.put("s_id", _s_id);
			success.put("rc", 0);
			success.put("redirectURL", redirectPage);
			MLogger.i(MLogger.MOD_APP, "Session created " + success.toString(2));
			out.println(success.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// This method needs to be used for getting the server path : print and see what is printed 
	private static String getURL(HttpServletRequest req) {

		String scheme = req.getScheme(); // http
		String serverName = req.getServerName(); // hostname.com
		int serverPort = req.getServerPort(); // 80
		String contextPath = req.getContextPath(); // /mywebapp
		String servletPath = req.getServletPath(); // /servlet/MyServlet
		String pathInfo = req.getPathInfo(); // /a/b;c=123
		String queryString = req.getQueryString(); // d=789

		// Reconstruct original requesting URL
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath);
		return url.toString();
	}

} // end of class
