package com.technomedia.digipark.database.base;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONObject;

import com.technomedia.logger.MLogger;
import com.technomedia.digipark.server.common.ErrorCodes;

public abstract class DataHandler {

	public final static byte JSON_FORMAT_NORMAL = 0; 			// select returns normal JSON object
	public final static byte JSON_FORMAT_KEY_VALUE_PAIR = 1; 	// select returns key value pair JSON object ( to be used in detail forms )

	@Context
	UriInfo ui;
	@Context
	HttpHeaders hh;

	public DataHandler() {
	}

	/** ---------- insert data into db -------------------------------- */
	/**
	 * POST method for creating an instance of UserResource
	 * 
	 * @param content
	 *            representation for the new resource
	 * @return an HTTP response with content of the created resource
	 */
	@POST
	@Consumes({ "text/html", "application/json", "application/text" })
	@Produces("application/json")
	public Response insertData(String content) {
		try {
			if (checkValidInput(content) == false) {
				JSONObject errorObj = new JSONObject();
				errorObj.put("status_code",
						ErrorCodes.ERROR_CODE_NO_DATA_RECEIVED);

				return Response.status(Response.Status.NO_CONTENT)
						.entity(errorObj).type(MediaType.APPLICATION_JSON)
						.build();
			}

			return saveData(true, content); // true -> indicates we want to insert data

		} catch (Exception e) {
			MLogger.dumpException(e);

			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("{\"status_code\": "
							+ ErrorCodes.ERROR_CODE_OPERATION_FAILED + ","
							+ "\"message\":" + e.getMessage() + "}")
					.type(MediaType.APPLICATION_JSON).build();
		}
	}

	/** ------------------Update data ----------------------------------- */
	/** PUT method for Updating Data. */
	@PUT
	@Consumes({ "text/html", "application/json", "application/text" })
	@Produces("application/json")
	public Response updateData(String content) {
		try {
			if (checkValidInput(content) == false) {
				JSONObject errorObj = new JSONObject();
				errorObj.put("status_code",
						ErrorCodes.ERROR_CODE_NO_DATA_RECEIVED);

				return Response.status(Response.Status.NO_CONTENT)
						.entity(errorObj).type(MediaType.APPLICATION_JSON)
						.build();
			}

			return saveData(false, content); // false -> indicates we want to
												// update data.

		} catch (Exception e) {
			MLogger.dumpException(e);

			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("{\"status_code\": "
							+ ErrorCodes.ERROR_CODE_NONE_OF_THE_ROWS_AFFECTED
							+ "," + "\"message\":" + e.getMessage() + "}")
					.type(MediaType.APPLICATION_JSON).build();
		}
	}

	/**
	 * -------------------- delete data from database
	 */
	@DELETE
	@Consumes({ "text/html", "application/json", "application/text" })
	@Produces("application/json")
	public Response delete(String delete_data) {

		long noOfRowsDeleted;
		try {
			if (checkValidInput(delete_data) == false) {
				JSONObject errorObj = new JSONObject();
				errorObj.put("status_code",
						ErrorCodes.ERROR_CODE_NO_DATA_RECEIVED);

				return Response.status(Response.Status.NO_CONTENT)
						.entity(errorObj).type(MediaType.APPLICATION_JSON)
						.build();
			}

			noOfRowsDeleted = deleteData(delete_data);

			if (noOfRowsDeleted <= 0) {
				// Handle Error condition
				MLogger.i(MLogger.MOD_DB, "Failed to delete data");
				JSONObject errorObj = new JSONObject();
				errorObj.put("status_code", 200);
				errorObj.put("rows_deleted", noOfRowsDeleted);

				MLogger.i( MLogger.MOD_DB, "Server sending:" + errorObj );
				return Response.status(Response.Status.OK).entity(errorObj).type(MediaType.APPLICATION_JSON).build();
			}

			JSONObject success = new JSONObject();
			
			success.put("rows_deleted", noOfRowsDeleted);
			success.put("status_code", ErrorCodes.ERROR_CODE_SUCCESS);
			
			MLogger.i( MLogger.MOD_DB, "Server sending:" + success );
			return Response.status(Response.Status.OK).entity(success).type(MediaType.APPLICATION_JSON).build();

			

		} catch (Exception e) {
			MLogger.dumpException(e);

			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("{\"status_code\": "
							+ ErrorCodes.ERROR_CODE_DELETE_OPERATION_FAILED
							+ "," + "\"message\":" + e.getMessage() + "}")
					.type(MediaType.APPLICATION_JSON).build();
		}
	}

	/** -------------check if input is valid----------------------------- */
	public boolean checkValidInput(String content) {

		if (content == null || content.length() == 0) {
			MLogger.i(MLogger.MOD_DB, "Content received: EMPTY STRING");
			return false;
		}

		String input = content.trim();
		if (input.length() == 0) {
			MLogger.i(MLogger.MOD_DB, "Content received: EMPTY STRING");
			return false;
		}
		MLogger.i(MLogger.MOD_DB, "Content received: " + content);
		return true;
	}

	/*** ------------------------- Abstract method Save data-------------------- */
	protected abstract Response saveData(boolean type, String data);

	/*** --------------------------Abstract method delete data------------------- */
	protected abstract long deleteData(String condition);

}