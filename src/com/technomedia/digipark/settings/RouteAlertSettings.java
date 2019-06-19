package com.technomedia.digipark.settings;

public class RouteAlertSettings {

	static byte value = 0;
	// Bit Positions
	public static final byte IS_RELEASE_MODE = value++; // This bit is enabled
														// when the app is in
														// release mode
	public static final byte SEND_SMS_USING_SIMCARD = value++; // Send SMS :
																// using SIM
																// Card. it can
																// be blocked by
																// setting it to
																// 0; on days
																// when there is
																// higher charge
																// this could be
																// disabled
	public static final byte SEND_SMS_USING_SMS_GATEWAY = value++; // Send SMS :
																	// using SMS
																	// Gateway
	// NOTE if both the above modes are not set then the mode is considered to
	// be SMS SIMULATION:
	// In SMS Simulation, The SMS will not be sent out. it will just assume that
	// the SMS has been sent
	// and call the callbacks
	public static final byte ALLOW_GPS_FAILURE_SMS = value++; // SMS : On GPS
																// Failure send
																// out MSGs to
																// parents with
																// 'Dont wait
																// for alert
	public static final byte SEND_RA_STARTED_SMS_AT_STARTUP = value++; // SMS :
																		// RouteAlert
																		// Started
																		// SMS
																		// will
																		// be
																		// sent
																		// to
																		// the
																		// IA
	public static final byte SEND_DEVICE_READY_SMS = value++; // SMS : on Route
																// Play Start
	public static final byte SEND_STATUS_SMS_TO_IA_ON_ROUTE_START = value++; // SMS
																				// :
																				// Send
																				// out
																				// STATUS1
																				// sms
																				// to
																				// IA
																				// at
																				// START
																				// of
																				// route
	public static final byte SEND_SMS_ON_MARKING_INCOMING_REQUEST = value++; // SMS
																				// :
																				// Send
																				// out
																				// SMS
																				// to
																				// parents
																				// /
																				// IA/IE
																				// when
																				// they
																				// send
																				// MyLocation
																				// or
																				// Missed
																				// call
																				// to
																				// mark
																				// a
																				// location
	public static final byte SEND_STATUS_SMS_TO_IA_ON_ROUTE_END = value++; // SMS
																			// :
																			// Send
																			// out
																			// STATUS3
																			// sms
																			// to
																			// IA
																			// at
																			// END
																			// of
																			// route
	public static final byte ATTACH_GOOGLE_MAP_WITH_WRU_FOR_ADMIN = value++; // SMS
																				// Modifier:
																				// Send
																				// Google
																				// map
																				// URL
																				// with
																				// WRU
																				// to
																				// Admin
	public static final byte PurgeDbgOnRouteStartup = value++; // Purge the
																// debug files
																// on Route
																// Start: Save
																// space
	public static final byte USE_AT_COMMAND_TO_TERMINATE_CALL = value++; // Use
																			// AT+CHUP
																			// to
																			// terminate
																			// call.
																			// else
																			// it
																			// will
																			// use
																			// GetTelephoneServer.endCall(which
																			// doesnt
																			// work
																			// in
																			// some
																			// phones
																			// )

}
