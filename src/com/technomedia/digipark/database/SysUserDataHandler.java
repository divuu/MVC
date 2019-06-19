////////////////////////////////////////////////////////////////////////////

// FileName SysUserDataHandler.java: SysUserDataHandler Implementation file

// Author : Vinu | Utkarsh | JRC
// Description : Digi Park v1.0


////////////////////////////////////////////////////////////////////////////


package com.technomedia.digipark.database;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.technomedia.logger.MLogger;
import com.technomedia.digipark.database.base.DataHandler;
import com.technomedia.digipark.server.common.ErrorCodes;
import com.technomedia.digipark.server.common.SoftwareFeature;
import com.technomedia.digipark.server.login.AuthenticationUtil;
import com.technomedia.digipark.database.base.AbstractDbAdaptor;


@Path("/SysUserDataHandler")

public class SysUserDataHandler extends DataHandler {

	@Context
	private UriInfo context;
	@Context
	private ServletContext servletContext;

	@Context
	private HttpServletRequest request;


	public SysUserDataHandler() {

	// Add your code here
	}

	@Path("/select")
	@GET
	@Consumes( "application/json")
	@Produces({ "text/html", "application/json", "application/text" })
	public Response getSysUserList( @QueryParam("sys_user_id") int whrCondition, @QueryParam("parking_area_id") int parkingAreaId ) {
		try {

			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.READ_SYS_USER) == false) {
				JSONObject errorObj = new JSONObject();
				errorObj.put("status_code", ErrorCodes.ERROR_CODE_ACCESS_DENIED);
				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj).type(MediaType.APPLICATION_JSON).build();
			}

//			if( whrCondition == 0 ) {
//				JSONObject errorObj = new JSONObject();
//				errorObj.put("status_code", ErrorCodes.ERROR_CODE_NO_DATA_RECEIVED);
//				return Response.status( Response.Status.NO_CONTENT )
//						.entity(errorObj)
//						.type(MediaType.APPLICATION_JSON).build();
//			}
				
			MLogger.i( MLogger.MOD_DB, "getList Condition:" + whrCondition );
			SysUserDbAdaptor dbAdaptor = new SysUserDbAdaptor();

			String condition = null; //SysUserDbAdaptor.KEY_ORGANIZATION_ID +"="+ whrCondition;
			if(whrCondition != 0){
			
				condition = SysUserDbAdaptor.KEY_SYS_USER_ID +"="+ whrCondition;
			}
			else if(parkingAreaId != 0){
				
				condition = SysUserDbAdaptor.KEY_PARKING_AREA_ID + "=" + parkingAreaId;
			}
			
			
			String orderBy = SysUserDbAdaptor.KEY_SYS_USER_ID;
			MLogger.i( MLogger.MOD_DB, "Condition:" + condition);
			ArrayList<SysUser> finalList = dbAdaptor.getList( condition, orderBy );

			if( finalList == null || finalList.size() == 0 ) {

				MLogger.i( MLogger.MOD_DB, "Sys User List is empty: No data to send");
				JSONObject errorObj = new JSONObject();
				errorObj.put("rel", "SysUserDetails");
				errorObj.put("columns", SysUser.NUM_OF_COLUMNS);
				errorObj.put("headings", SysUser.getHeader());
				errorObj.put("status_code", ErrorCodes.ERROR_CODE_EMPTYLIST);
				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj)
						.type(MediaType.APPLICATION_JSON).build();
			}

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rel", "SysUserDetails");
			jsonObj.put("rows", finalList.size());
			jsonObj.put("columns", SysUser.NUM_OF_COLUMNS);
			jsonObj.put("headings", SysUser.getHeader());
			jsonObj.put("values", SysUser.objListToJSONArrayList(finalList));

			jsonObj.put("status_code", ErrorCodes.ERROR_CODE_SUCCESS);
			MLogger.i( MLogger.MOD_DB, "Server sending:" + jsonObj );
			return Response.status(Response.Status.OK).entity(jsonObj).type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			MLogger.dumpException( e );
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"status_code\": "+ErrorCodes.ERROR_CODE_OPERATION_FAILED +"," +
							"\"message\":"+e.getMessage()+"}")
					.type(MediaType.APPLICATION_JSON).build();
		}

	}

	@Path("/theaders")
	@GET
	@Produces({ "application/json", "application/text" })
	public Response getTableHeaders() {
		try {
			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.READ_ASSIGNED_SYS_USER) == false) {
				JSONObject errorObj = new JSONObject();
				errorObj.put("status_code", ErrorCodes.ERROR_CODE_ACCESS_DENIED);
				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj)
						.type(MediaType.APPLICATION_JSON).build();
			}

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rel", "SysUserDetails");
			jsonObj.put("columns", SysUser.NUM_OF_COLUMNS); // number of column in each row
			jsonObj.put("headings",	SysUser.getHeader());   // each column name

			jsonObj.put("status_code", ErrorCodes.ERROR_CODE_SUCCESS); // each column name
			MLogger.i(MLogger.MOD_DB, "Server sending:" + jsonObj);

			return Response.status(Response.Status.OK).entity(jsonObj)
			.type(MediaType.APPLICATION_JSON).build();

		} catch	(Exception e) {
			MLogger.dumpException(e);
			return Response.status(Response.Status.BAD_REQUEST)
				.entity("{\"status_code\": "+ ErrorCodes.ERROR_CODE_OPERATION_FAILED +"," +
						"\"message\":"+e.getMessage()+"}")
				.type(MediaType.APPLICATION_JSON).build();
		}

	}

	@Override
	protected Response saveData( boolean insertData, String inputString ) {
		if(insertData == true){
			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.ADD_SYS_USER) == false) {
				JSONObject errorObj = new JSONObject();
				try {
					errorObj.put("status_code", ErrorCodes.ERROR_CODE_ACCESS_DENIED);
				} catch (JSONException e) {
					MLogger.dumpException( e );
				}

				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj)
						.type(MediaType.APPLICATION_JSON).build();
			}
		}else{
			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.EDIT_SYS_USER) == false) {
				JSONObject errorObj = new JSONObject();
				try {
					errorObj.put("status_code", ErrorCodes.ERROR_CODE_ACCESS_DENIED);
				} catch (JSONException e) {
					MLogger.dumpException( e );
				}
				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj)
						.type(MediaType.APPLICATION_JSON).build();
			}
		}
		SysUser obj = new SysUser();
		try {

			AbstractDbAdaptor.getConnection().setAutoCommit( false );
			JSONObject data = new JSONObject(inputString);
			SysUserDbAdaptor dbAdaptor = new SysUserDbAdaptor();
			//obj.mOrganizationId = Integer.valueOf(data.getInt(SysUserDbAdaptor.KEY_ORGANIZATION_ID));
			String condition = null;
			if( insertData == true ) {
				condition = null; // SysUserDbAdaptor.KEY_ORGANIZATION_ID+"="+obj.mOrganizationId;
				String fieldName = SysUserDbAdaptor.KEY_SYS_USER_ID;
// auto_increment
//				obj.mSysUserId =  ( 1 + dbAdaptor.runAggregate( "max", fieldName, condition ) );
			}
			else {
				obj.mSysUserId =  data.getInt( SysUserDbAdaptor.KEY_SYS_USER_ID );
				condition = SysUserDbAdaptor.KEY_SYS_USER_ID+"="+obj.mSysUserId;
			}

			obj.mRoleId = (short) data.getInt(SysUserDbAdaptor.KEY_ROLE_ID);
			obj.mName = data.getString(SysUserDbAdaptor.KEY_NAME).trim();
			obj.mMobileNumber = data.getString(SysUserDbAdaptor.KEY_MOBILE_NUMBER).trim();
			obj.mMobileNumber2 = data.getString(SysUserDbAdaptor.KEY_MOBILE_NUMBER_2).trim();
			obj.mEmail = data.getString(SysUserDbAdaptor.KEY_EMAIL).trim();
			obj.mAddress = data.getString(SysUserDbAdaptor.KEY_ADDRESS).trim();
			obj.mCityId = data.getInt(SysUserDbAdaptor.KEY_CITY_ID);
			obj.mCountryId = data.getInt(SysUserDbAdaptor.KEY_COUNTRY_ID);
			obj.mDesignationLookupId = data.getInt(SysUserDbAdaptor.KEY_DESIGNATION_LOOKUP_ID);
			obj.mLatitude = (float) data.getDouble(SysUserDbAdaptor.KEY_LATITUDE);
			obj.mLongitude = (float) data.getDouble(SysUserDbAdaptor.KEY_LONGITUDE);
			obj.mGenderLookupId = data.getInt(SysUserDbAdaptor.KEY_GENDER_LOOKUP_ID);
			obj.mDateOfBirth = data.getLong(SysUserDbAdaptor.KEY_DATE_OF_BIRTH);
			obj.mDrivingLicenseNumber = data.getString(SysUserDbAdaptor.KEY_DRIVING_LICENSE_NUMBER).trim();
			obj.mPassportNumber = data.getString(SysUserDbAdaptor.KEY_PASSPORT_NUMBER).trim();
			obj.mRemarks = data.getString(SysUserDbAdaptor.KEY_REMARKS).trim();
			obj.mPhoto = data.getString(SysUserDbAdaptor.KEY_PHOTO).trim();
			obj.mDateOfRegistration = data.getLong(SysUserDbAdaptor.KEY_DATE_OF_REGISTRATION);
			obj.mLastLoginDate = data.getLong(SysUserDbAdaptor.KEY_LAST_LOGIN_DATE);
			obj.mNoOfLogins = data.getInt(SysUserDbAdaptor.KEY_NO_OF_LOGINS);
			obj.mCreateBySysUserId = data.getInt(SysUserDbAdaptor.KEY_CREATE_BY_SYS_USER_ID);
			obj.mActiveLookupId = data.getInt(SysUserDbAdaptor.KEY_ACTIVE_LOOKUP_ID);
			obj.mParkingAreaId = data.getInt(SysUserDbAdaptor.KEY_PARKING_AREA_ID);
			obj.mLoggedInParkingAreaGateId = data.getInt(SysUserDbAdaptor.KEY_LOGGED_IN_PARKING_AREA_GATE_ID);
			obj.mStatus = data.getInt(SysUserDbAdaptor.KEY_STATUS);
			obj.mPassword = data.getString(SysUserDbAdaptor.KEY_PASSWORD).trim();
			obj.mSalt = data.getString(SysUserDbAdaptor.KEY_SALT).trim();
			obj.mExtraData = data.getString(SysUserDbAdaptor.KEY_EXTRA_DATA).trim();
			long status = 0;

			if( insertData == true ){
				status = dbAdaptor.insert(obj);
			}
			else
				status = dbAdaptor.update(obj, condition);

			if( status < 0 ) {

				AbstractDbAdaptor.getConnection().rollback();
				AbstractDbAdaptor.getConnection().setAutoCommit(true);
				MLogger.i(MLogger.MOD_DB, "Failed to save/update");
				return Response.status(Response.Status.NO_CONTENT)
						.entity("{\"status_code\": "+ErrorCodes.ERROR_CODE_OPERATION_FAILED+" }")
						.type(MediaType.APPLICATION_JSON).build();
			}
			AbstractDbAdaptor.getConnection().commit();
			AbstractDbAdaptor.getConnection().setAutoCommit(true);

			JSONObject success = new JSONObject();
			success.put("status_code", ErrorCodes.ERROR_CODE_SUCCESS);
			success.put("id", obj.mSysUserId);
			MLogger.i(MLogger.MOD_DB, "->Saved successfully : " + success.toString() );
			return Response.status(Response.Status.OK).entity(success).type(MediaType.APPLICATION_JSON).build();		
		}catch (Exception e) {
			MLogger.dumpException( e );
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"status_code\": "+ErrorCodes.ERROR_CODE_NONE_OF_THE_ROWS_AFFECTED +"," +
							"\"message\":"+e.getMessage()+"}")
					.type(MediaType.APPLICATION_JSON).build();
		}


	}

	@Override
	protected long deleteData( String delete_data ) {
		long status = ErrorCodes.ERROR_CODE_OPERATION_FAILED;

		if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.DELETE_SYS_USER) == false) {
			return ErrorCodes.ERROR_CODE_ACCESS_DENIED;
		}

		try{
			AbstractDbAdaptor.getConnection().setAutoCommit(false);
			JSONObject data = new JSONObject(delete_data);
			JSONArray object_id_arr = (JSONArray) data.get("del_row_id_arr");

			int length = object_id_arr.length();
			//int organization_id = data.getInt(SysUserDbAdaptor.KEY_ORGANIZATION_ID);

			String condition = "(";
			for(int i = 0; i<(length-1);i++){
				int j = 0;
				JSONArray id_arr = object_id_arr.getJSONArray(i);
				condition += "(";
				condition += SysUserDbAdaptor.KEY_SYS_USER_ID + " = " + id_arr.getString(j) + " ) or ";
			}
			int j = 0;
			JSONArray id_arr = object_id_arr.getJSONArray(length -1);
			condition += "(";

			condition += SysUserDbAdaptor.KEY_SYS_USER_ID + " = " + id_arr.getString(j++) + " ) ) ";
			//condition += " and " + SysUserDbAdaptor.KEY_ORGANIZATION_ID + " = " + id_arr.getString(j++) + " ";;

			MLogger.i( MLogger.MOD_DB, "Deleting Sys User: where clause " + condition );
			SysUserDbAdaptor dbAdaptor = new SysUserDbAdaptor();
			long noOfRowsDeleted = dbAdaptor.delete(condition);

			if( noOfRowsDeleted < 0 ) {
				MLogger.i( MLogger.MOD_DB, "Deleting Sys User: FAILED, status= " + noOfRowsDeleted + ". Rolling back" );

				AbstractDbAdaptor.getConnection().rollback();
				AbstractDbAdaptor.getConnection().setAutoCommit(true);
				return noOfRowsDeleted;
			}

			AbstractDbAdaptor.getConnection().commit();
			AbstractDbAdaptor.getConnection().setAutoCommit(true);

			return noOfRowsDeleted;
		}catch (Exception e) {
			MLogger.dumpException( e);
			return status;
		}

	}
}
