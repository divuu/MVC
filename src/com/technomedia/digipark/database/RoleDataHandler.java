////////////////////////////////////////////////////////////////////////////

// FileName RoleDataHandler.java: RoleDataHandler Implementation file

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


@Path("/RoleDataHandler")

public class RoleDataHandler extends DataHandler {

	@Context
	private UriInfo context;
	@Context
	private ServletContext servletContext;

	@Context
	private HttpServletRequest request;


	public RoleDataHandler() {

	// Add your code here
	}

	@Path("/select")
	@GET
	@Consumes( "application/json")
	@Produces({ "text/html", "application/json", "application/text" })
	public Response getRoleList( @QueryParam("organization_id") int whrCondition ) {
		try {

			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.READ_ROLE) == false) {
				JSONObject errorObj = new JSONObject();
				errorObj.put("status_code", ErrorCodes.ERROR_CODE_ACCESS_DENIED);
				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj).type(MediaType.APPLICATION_JSON).build();
			}

			if( whrCondition == 0 ) {
				JSONObject errorObj = new JSONObject();
				errorObj.put("status_code", ErrorCodes.ERROR_CODE_NO_DATA_RECEIVED);
				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj)
						.type(MediaType.APPLICATION_JSON).build();
			}
				
			MLogger.i( MLogger.MOD_DB, "getList Condition:" + whrCondition );
			RoleDbAdaptor dbAdaptor = new RoleDbAdaptor();

			String condition = null; //RoleDbAdaptor.KEY_ORGANIZATION_ID +"="+ whrCondition;
			String orderBy = RoleDbAdaptor.KEY_ROLE_ID;
			MLogger.i( MLogger.MOD_DB, "Condition:" + condition);
			ArrayList<Role> finalList = dbAdaptor.getList( condition, orderBy );

			if( finalList == null || finalList.size() == 0 ) {

				MLogger.i( MLogger.MOD_DB, "Role List is empty: No data to send");
				JSONObject errorObj = new JSONObject();
				errorObj.put("rel", "RoleDetails");
				errorObj.put("columns", Role.NUM_OF_COLUMNS);
				errorObj.put("headings", Role.getHeader());
				errorObj.put("status_code", ErrorCodes.ERROR_CODE_EMPTYLIST);
				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj)
						.type(MediaType.APPLICATION_JSON).build();
			}

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rel", "RoleDetails");
			jsonObj.put("rows", finalList.size());
			jsonObj.put("columns", Role.NUM_OF_COLUMNS);
			jsonObj.put("headings", Role.getHeader());
			jsonObj.put("values", Role.objListToJSONArrayList(finalList));

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
			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.READ_ASSIGNED_ROLE) == false) {
				JSONObject errorObj = new JSONObject();
				errorObj.put("status_code", ErrorCodes.ERROR_CODE_ACCESS_DENIED);
				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj)
						.type(MediaType.APPLICATION_JSON).build();
			}

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rel", "RoleDetails");
			jsonObj.put("columns", Role.NUM_OF_COLUMNS); // number of column in each row
			jsonObj.put("headings",	Role.getHeader());   // each column name

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
			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.ADD_ROLE) == false) {
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
			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.EDIT_ROLE) == false) {
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
		Role obj = new Role();
		try {

			AbstractDbAdaptor.getConnection().setAutoCommit( false );
			JSONObject data = new JSONObject(inputString);
			RoleDbAdaptor dbAdaptor = new RoleDbAdaptor();
			//obj.mOrganizationId = Integer.valueOf(data.getInt(RoleDbAdaptor.KEY_ORGANIZATION_ID));
			String condition = null;
			if( insertData == true ) {
				condition = null; // RoleDbAdaptor.KEY_ORGANIZATION_ID+"="+obj.mOrganizationId;
				String fieldName = RoleDbAdaptor.KEY_ROLE_ID;
// auto_increment
//				obj.mRoleId = (short) ( 1 + dbAdaptor.runAggregate( "max", fieldName, condition ) );
			}
			else {
				obj.mRoleId = (short) data.getInt( RoleDbAdaptor.KEY_ROLE_ID );
				condition = RoleDbAdaptor.KEY_ROLE_ID+"="+obj.mRoleId;
			}

			obj.mOrganizationId = data.getInt(RoleDbAdaptor.KEY_ORGANIZATION_ID);
			obj.mName = data.getString(RoleDbAdaptor.KEY_NAME).trim();
			obj.mRoleWebAppUiFeatures = data.getString(RoleDbAdaptor.KEY_ROLE_WEB_APP_UI_FEATURES).trim();
			obj.mRoleMobileAppUiFeatures = data.getString(RoleDbAdaptor.KEY_ROLE_MOBILE_APP_UI_FEATURES).trim();
			obj.mRoleReportFeatures = data.getString(RoleDbAdaptor.KEY_ROLE_REPORT_FEATURES).trim();
			obj.mRoleMenu = data.getString(RoleDbAdaptor.KEY_ROLE_MENU).trim();
			obj.mRoleScript = data.getString(RoleDbAdaptor.KEY_ROLE_SCRIPT).trim();
			obj.mRemarks = data.getString(RoleDbAdaptor.KEY_REMARKS).trim();
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
			success.put("id", obj.mRoleId);
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

		if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.DELETE_ROLE) == false) {
			return ErrorCodes.ERROR_CODE_ACCESS_DENIED;
		}

		try{
			AbstractDbAdaptor.getConnection().setAutoCommit(false);
			JSONObject data = new JSONObject(delete_data);
			JSONArray object_id_arr = (JSONArray) data.get("del_row_id_arr");

			int length = object_id_arr.length();
			//int organization_id = data.getInt(RoleDbAdaptor.KEY_ORGANIZATION_ID);

			String condition = "(";
			for(int i = 0; i<(length-1);i++){
				int j = 0;
				JSONArray id_arr = object_id_arr.getJSONArray(i);
				condition += "(";
				condition += RoleDbAdaptor.KEY_ROLE_ID + " = " + id_arr.getString(j) + " ) or ";
			}
			int j = 0;
			JSONArray id_arr = object_id_arr.getJSONArray(length -1);
			condition += "(";

			condition += RoleDbAdaptor.KEY_ROLE_ID + " = " + id_arr.getString(j++) + " ) ) ";
			//condition += " and " + RoleDbAdaptor.KEY_ORGANIZATION_ID + " = " + id_arr.getString(j++) + " ";;

			MLogger.i( MLogger.MOD_DB, "Deleting Role: where clause " + condition );
			RoleDbAdaptor dbAdaptor = new RoleDbAdaptor();
			long noOfRowsDeleted = dbAdaptor.delete(condition);

			if( noOfRowsDeleted < 0 ) {
				MLogger.i( MLogger.MOD_DB, "Deleting Role: FAILED, status= " + noOfRowsDeleted + ". Rolling back" );

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
