package com.technomedia.digipark.server.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;

import com.technomedia.digipark.database.SysUser;
import com.technomedia.digipark.database.SysUserDbAdaptor;
import com.technomedia.digipark.database.base.DbErrorCodes;
import com.technomedia.digipark.server.common.ErrorCodes;

/**
 * Servlet implementation class LoginFailure
 * 
 * @author imdad
 */

@WebServlet(name = "LoginFailure", displayName = "LoginFailure", urlPatterns = { "/forgotPassword" }, description = "recover Login info")
// @WebServlet("/forgotPassword")
public class LoginFailure extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginFailure() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get email and mobile no
		String mobileNo = request.getParameter("mobile_no");
		String emailId = request.getParameter("email_id");

		PrintWriter out = response.getWriter();
		JSONObject respObj = new JSONObject();

		String condition = null;
		try {
			// check if they are valid
			if (emailId != null) {
				condition = SysUserDbAdaptor.KEY_EMAIL + "='" + emailId + "'";
			} else if (mobileNo != null) {
				condition = SysUserDbAdaptor.KEY_MOBILE_NUMBER + "='" + mobileNo
						+ "'";
			} else {
				// error insufficient data
				respObj.put("message", ErrorCodes.ERROR_CODE_NO_DATA_RECEIVED);
				respObj.put("messageTxt", "Please Enter Email Id or Mobile No");
				out.println(respObj.toString());
				return;
			}

			// get the user data
			SysUserDbAdaptor dba = new SysUserDbAdaptor();
			SysUser su = dba.getData(condition);

			// TODO generate random passwd, encrypt and update db and finally
			// send to user
			if (su != null) {
				if (emailId != null && emailId.equals(su.mEmail)) {
					// TODO send email

					respObj.put("message", ErrorCodes.ERROR_CODE_SUCCESS);
					respObj.put("messageTxt",
							"An Email has been successfully sent to "
									+ su.mEmail);
				} else {
					// TODO send sms

					respObj.put("message", DbErrorCodes.ERROR_CODE_SUCCESS);
					respObj.put("messageTxt",
							"An SMS has been successfully sent to "
									+ su.mMobileNumber);
				}
				// response
				out.println(respObj.toString());
			} else {
				// user not found
				respObj.put("message", ErrorCodes.ERROR_CODE_EMPTYLIST);
				respObj.put("messageTxt", "User with given info not found");

				out.println(respObj.toString());
			}
		} catch (Exception e) {
			out.println("{\"message\": " + ErrorCodes.ERROR_CODE_EMPTYLIST
					+ ",\"messageTxt\": \"User with given info not found\"}");
		}

	}

}
