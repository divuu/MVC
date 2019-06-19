////////////////////////////////////////////////////////////////////////////

// FileName AnalyticsDataHandler.java: AnalyticsDataHandler Implementation file

// Author : Vinu | Utkarsh | JRC
// Description : Slash Digital TVAS v1.0


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


@Path("/AnalyticsDataHandler")

public class AnalyticsDataHandler extends DataHandler {

	@Context
	private UriInfo context;
	@Context
	private ServletContext servletContext;

	@Context
	private HttpServletRequest request;


	public AnalyticsDataHandler() {

	// Add your code here
	}

	@Path("/select")
	@GET
	@Consumes( "application/json")
	@Produces({ "text/html", "application/json", "application/text" })
	public Response getAnalyticsList( @QueryParam("organization_id") int whrCondition ) {
		try {

			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.READ_ANALYTICS) == false) {
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
			AnalyticsDbAdaptor dbAdaptor = new AnalyticsDbAdaptor();

			String condition = null; //AnalyticsDbAdaptor.KEY_ORGANIZATION_ID +"="+ whrCondition;
			String orderBy = AnalyticsDbAdaptor.KEY_ANALYTICS_ID;
			MLogger.i( MLogger.MOD_DB, "Condition:" + condition);
			ArrayList<Analytics> finalList = dbAdaptor.getList( condition, orderBy );

			if( finalList == null || finalList.size() == 0 ) {

				MLogger.i( MLogger.MOD_DB, "Analytics List is empty: No data to send");
				JSONObject errorObj = new JSONObject();
				errorObj.put("rel", "AnalyticsDetails");
				errorObj.put("columns", Analytics.NUM_OF_COLUMNS);
				errorObj.put("headings", Analytics.getHeader());
				errorObj.put("status_code", ErrorCodes.ERROR_CODE_EMPTYLIST);
				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj)
						.type(MediaType.APPLICATION_JSON).build();
			}

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rel", "AnalyticsDetails");
			jsonObj.put("rows", finalList.size());
			jsonObj.put("columns", Analytics.NUM_OF_COLUMNS);
			jsonObj.put("headings", Analytics.getHeader());
			jsonObj.put("values", Analytics.objListToJSONArrayList(finalList));

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
			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.READ_ASSIGNED_ANALYTICS) == false) {
				JSONObject errorObj = new JSONObject();
				errorObj.put("status_code", ErrorCodes.ERROR_CODE_ACCESS_DENIED);
				return Response.status( Response.Status.NO_CONTENT )
						.entity(errorObj)
						.type(MediaType.APPLICATION_JSON).build();
			}

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rel", "AnalyticsDetails");
			jsonObj.put("columns", Analytics.NUM_OF_COLUMNS); // number of column in each row
			jsonObj.put("headings",	Analytics.getHeader());   // each column name

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
			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.ADD_ANALYTICS) == false) {
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
			if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.EDIT_ANALYTICS) == false) {
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
		Analytics obj = new Analytics();
		try {

			AbstractDbAdaptor.getConnection().setAutoCommit( false );
			JSONObject data = new JSONObject(inputString);
			AnalyticsDbAdaptor dbAdaptor = new AnalyticsDbAdaptor();
			//obj.mOrganizationId = Integer.valueOf(data.getInt(AnalyticsDbAdaptor.KEY_ORGANIZATION_ID));
			String condition = null;
			if( insertData == true ) {
				condition = null; // AnalyticsDbAdaptor.KEY_ORGANIZATION_ID+"="+obj.mOrganizationId;
				String fieldName = AnalyticsDbAdaptor.KEY_ANALYTICS_ID;
// auto_increment
//				obj.mAnalyticsId =  ( 1 + dbAdaptor.runAggregate( "max", fieldName, condition ) );
			}
			else {
				obj.mAnalyticsId =  data.getInt( AnalyticsDbAdaptor.KEY_ANALYTICS_ID );
				condition = AnalyticsDbAdaptor.KEY_ANALYTICS_ID+"="+obj.mAnalyticsId;
			}

			obj.mSubscriberId = data.getInt(AnalyticsDbAdaptor.KEY_SUBSCRIBER_ID);
			obj.mSessionStartTime = data.getLong(AnalyticsDbAdaptor.KEY_SESSION_START_TIME);
			obj.mSessionEndTime = data.getLong(AnalyticsDbAdaptor.KEY_SESSION_END_TIME);
			obj.mPagesPerSession = data.getInt(AnalyticsDbAdaptor.KEY_PAGES_PER_SESSION);
			obj.mTotalSession = data.getInt(AnalyticsDbAdaptor.KEY_TOTAL_SESSION);
			obj.mTotalVisit = data.getInt(AnalyticsDbAdaptor.KEY_TOTAL_VISIT);
			obj.mNewUser = (byte) data.getInt(AnalyticsDbAdaptor.KEY_NEW_USER);
			obj.mChannelsOfTraffic = data.getString(AnalyticsDbAdaptor.KEY_CHANNELS_OF_TRAFFIC).trim();
			obj.mTimeSpent = (float) data.getDouble(AnalyticsDbAdaptor.KEY_TIME_SPENT);
			obj.mBounceRate = (float) data.getDouble(AnalyticsDbAdaptor.KEY_BOUNCE_RATE);
			obj.mSourceOfTraffic = data.getString(AnalyticsDbAdaptor.KEY_SOURCE_OF_TRAFFIC).trim();
			obj.mClick = data.getInt(AnalyticsDbAdaptor.KEY_CLICK);
			obj.mVideoWatchedDuration = (float) data.getDouble(AnalyticsDbAdaptor.KEY_VIDEO_WATCHED_DURATION);
			obj.mBrowser = data.getString(AnalyticsDbAdaptor.KEY_BROWSER).trim();
			obj.mOperatingSystem = data.getString(AnalyticsDbAdaptor.KEY_OPERATING_SYSTEM).trim();
			obj.mDevice = data.getString(AnalyticsDbAdaptor.KEY_DEVICE).trim();
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
			success.put("id", obj.mAnalyticsId);
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

		if( AuthenticationUtil.authenticateUserAccess(request, SoftwareFeature.DELETE_ANALYTICS) == false) {
			return ErrorCodes.ERROR_CODE_ACCESS_DENIED;
		}

		try{
			AbstractDbAdaptor.getConnection().setAutoCommit(false);
			JSONObject data = new JSONObject(delete_data);
			JSONArray object_id_arr = (JSONArray) data.get("del_row_id_arr");

			int length = object_id_arr.length();
			//int organization_id = data.getInt(AnalyticsDbAdaptor.KEY_ORGANIZATION_ID);

			String condition = "(";
			for(int i = 0; i<(length-1);i++){
				int j = 0;
				JSONArray id_arr = object_id_arr.getJSONArray(i);
				condition += "(";
				condition += AnalyticsDbAdaptor.KEY_ANALYTICS_ID + " = " + id_arr.getString(j) + " ) or ";
			}
			int j = 0;
			JSONArray id_arr = object_id_arr.getJSONArray(length -1);
			condition += "(";

			condition += AnalyticsDbAdaptor.KEY_ANALYTICS_ID + " = " + id_arr.getString(j++) + " ) ) ";
			//condition += " and " + AnalyticsDbAdaptor.KEY_ORGANIZATION_ID + " = " + id_arr.getString(j++) + " ";;

			MLogger.i( MLogger.MOD_DB, "Deleting Analytics: where clause " + condition );
			AnalyticsDbAdaptor dbAdaptor = new AnalyticsDbAdaptor();
			long noOfRowsDeleted = dbAdaptor.delete(condition);

			if( noOfRowsDeleted < 0 ) {
				MLogger.i( MLogger.MOD_DB, "Deleting Analytics: FAILED, status= " + noOfRowsDeleted + ". Rolling back" );

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
