package com.technomedia.digipark.server.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

//import com.technomedia.digipark.server.login.*;
import com.technomedia.logger.MLogger;
import com.technomedia.digipark.database.Role;
import com.technomedia.digipark.database.RoleDbAdaptor;
//import com.technomedia.digipark.database.*;
import com.technomedia.digipark.database.SysUserDbAdaptor;
import com.technomedia.utils.BitUtils;

public class AuthenticationUtil {

	private static int iterationNumber = 5;

	/**
	 * Authenticates the user with a given password If password and/or login is
	 * null then always returns false. If the user does not exist in the
	 * database returns false.
	 * 
	 * @param userPassword
	 *            String The password of the user
	 * @param passwordFromDb
	 *            String The password from db
	 * @param salt
	 *            String The salt from db
	 * @return boolean Returns true if the user is authenticated, false
	 *         otherwise
	 * @throws NoSuchAlgorithmException
	 *             If the algorithm SHA-1 is not supported by the JVM
	 */
	public static boolean authenticate(String userPassword, String passwordFromDb,
			String salt) throws NoSuchAlgorithmException {

		try {
			boolean userExist = true;
			if (passwordFromDb == null || salt == null || userPassword == null) {
				// TIME RESISTANT ATTACK (Even if the user does not exist the
				// Computation time is equal to the time needed for a legitimate
				// user
				passwordFromDb = "000000000000000000000000000=";
				salt = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";
				userPassword = "";
				userExist = false;
			}

			String dbPwd = passwordFromDb;

			byte[] bSalt = base64ToByte(salt);
			byte[] bDigest = base64ToByte(dbPwd); 
			
	
			
			
			/*byte[] encodedBytes = Base64.encodeBase64("password".getBytes());
			System.out.println("encodedBytes " + new String(encodedBytes));
			byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
			System.out.println("decodedBytes " + new String(decodedBytes));*/
			
			// Compute the new DIGEST
			
			
			
			byte[] proposedDigest = getHash(iterationNumber, userPassword, bSalt);
			
			System.out.println("--------------"+byteToBase64(proposedDigest));
 			
			 String decodedData = new String(proposedDigest);  // Create new String Object and assign byte[] to it
			    System.out.println("\nText Decryted : " + decodedData);
			    String decodedDataUsingUTF8;
				try {
					decodedDataUsingUTF8 = new String(proposedDigest, "UTF-8");  // Best way to decode using "UTF-8"
				    System.out.println("Text Decryted using UTF-8 : " + decodedDataUsingUTF8);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			
				
				
				
				for(int index = 0; index < proposedDigest.length; index++){
				    System.out.print(String.format("%02X ", proposedDigest[index]));
				}
				System.out.println();
			
			//System.out.println("proposedDigest===="+Arrays.toString(proposedDigest));
			
			String decoded = new String(proposedDigest, "UTF-8");  
  
 			return Arrays.equals(proposedDigest, bDigest) && userExist;

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * Get encrypted password and salt
	 * 
	 * @param password
	 *            : plain password
	 * @return JsonObject{password, salt} or null
	 */
	public JSONObject getEncryptPasswordSalt(String password) {
		if (password == null) {
			return null;
		}

		// Uses a secure Random not a simple Random
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");

			// Salt generation 64 bits long
			byte[] bSalt = new byte[8];

			random.nextBytes(bSalt);

			// Digest computation
			byte[] bDigest = getHash(iterationNumber, password, bSalt);
			String sDigest = byteToBase64(bDigest);
			String sSalt = byteToBase64(bSalt);

			JSONObject result = new JSONObject();
			result.put(SysUserDbAdaptor.KEY_PASSWORD, sDigest);
			result.put(SysUserDbAdaptor.KEY_SALT, sSalt);

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			MLogger.dumpException(e);
		}
		return null;
	}

	/*
	 * public static void main(String [] arg){ AuthenticationUtil au = new
	 * AuthenticationUtil();
	 * System.out.println(au.getEncryptPasswordSalt("password")); }
	 */

	/**
	 * From a base 64 representation, returns the corresponding byte[]
	 * 
	 * @param data
	 *            String The base64 representation
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] base64ToByte(String data) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(data);
	}

	/**
	 * From a byte[] returns a base 64 representation
	 * 
	 * @param data
	 *            byte[]
	 * @return String
	 * @throws IOException
	 */
	public static String byteToBase64(byte[] data) {
		BASE64Encoder endecoder = new BASE64Encoder();
		return endecoder.encode(data);
	}

	/**
	 * From a password, a number of iterations and a salt, returns the
	 * corresponding digest
	 * 
	 * @param iterationNb
	 *            int The number of iterations of the algorithm
	 * @param password
	 *            String The password to encrypt
	 * @param salt
	 *            byte[] The salt
	 * @return byte[] The digested password
	 * @throws NoSuchAlgorithmException
	 *             If the algorithm doesn't exist
	 */
	private static byte[] getHash(int iterationNb, String password, byte[] salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(salt);
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		for (int i = 0; i < iterationNb; i++) {
			digest.reset();
			input = digest.digest(input);
		}
 		return input;
	}

	/**
	 * Create session objects
	 * 
	 * @param uo
	 *            : {id, uid, userType, role, name }
	 * @return
	 */
	public static JSONObject createUserSession(JSONObject uo) {

		try {
 			long time = System.currentTimeMillis();
			JSONObject sObj = new JSONObject();
			sObj.put("ID", uo.get("id"));
			sObj.put("S_UID", uo.get("uid"));
			sObj.put("S_UTYPE", uo.get("userType"));
			sObj.put("S_ROLE", uo.get("role"));
			sObj.put("S_NAME", uo.get("name"));
			sObj.put("S_Master_Menu", uo.get("userMastersMenu"));
			sObj.put("S_Transaction_Menu", uo.get("userTransactionMenu"));

			sObj.put("S_TIME", time);
			sObj.put("E_TIME", "-");
			sObj.put("Menu", "");

			System.out.println("Session created: " + sObj.toString());
			return new JSONObject(sObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean authenticateUserAccess(HttpServletRequest request, byte featureConst) {
		
		if (request == null) {
			return false;
		}

		int roleId = (int) request.getAttribute("S_ROLE_ID");
		// load Role
		RoleDbAdaptor rdba = new RoleDbAdaptor();
		String condition = RoleDbAdaptor.KEY_ROLE_ID + "=" + roleId;

		rdba.open();
		Role role = rdba.getData(condition);

		if (role == null) {
			MLogger.i(MLogger.MOD_DB, "Role with Id " + roleId + " not found");
			return false;
		}

		// check role permission
//		if (featureConst <= 31) {// if resource <= 31 check permission 1
//			return BitUtils.IS_BIT_SET(role.mRollAccessLevel1, featureConst);
//		} else if (featureConst <= 63) { // else check permission 2
//			return BitUtils.IS_BIT_SET(role.mRollAccessLevel2,
//					(byte) (featureConst - 32));
//		} else {
//			// add more access levels
//		}
		
		if( role.mRoleWebAppUiFeatures != null && role.mRoleWebAppUiFeatures.charAt( featureConst ) == '1' ) {


			return true;
		}
		MLogger.i(MLogger.MOD_DB, "Role role.mRoleWebAppUiFeature Failed");
		
		return false;
	}
}
