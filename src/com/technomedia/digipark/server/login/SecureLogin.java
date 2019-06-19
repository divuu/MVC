package com.technomedia.digipark.server.login;

public class SecureLogin {
	private final static int ITERATION_NUMBER = 1325;

	/*
	 * Methods to use inside here:
	 * 
	 * public booleanauthenticate(Connection con, String UID, String password)
	 * 
	 * public boolean createUser(Connection con, String UID, String password,
	 * String first, String last, String type, int num_vehic) throws
	 * SQLException, NoSuchAlgorithmException
	 */

	public SecureLogin() {
	}

	/**
	 * Authenticates the user with a given login and password If password and/or
	 * login is null then always returns false. If the user does not exist in
	 * the database returns false.
	 * 
	 * @param con
	 *            Connection An open connection to a databse
	 * @param UID
	 *            String The login of the user
	 * @param password
	 *            String The password of the user
	 * @return boolean Returns true if the user is authenticated, false
	 *         otherwise
	 * @throws SQLException
	 *             If the database is inconsistent or unavailable ( (Two users
	 *             with the same login, salt or digested password altered etc.)
	 * @throws NoSuchAlgorithmException
	 *             If the algorithm SHA-256 is not supported by the JVM
	 */
	/*
	 * public boolean authenticate(Connection con, String UID, String password)
	 * throws SQLException, NoSuchAlgorithmException{ boolean
	 * authenticated=false; ///PreparedStatement ps = null; //ResultSet rs =
	 * null; try { boolean userExist = true; // INPUT VALIDATION if
	 * (UID==null||password==null){ // TIME RESISTANT ATTACK // Computation time
	 * is equal to the time needed by a legitimate user userExist = false;
	 * UID=""; password=""; } // ps = new Request //ps =
	 * con.prepareStatement("SELECT PASSWORD, SALT FROM USERS WHERE UID = ?");
	 * //choice = 4 //arg[0] = UID //ps.setString(1, UID); //rs =
	 * ps.executeQuery(); SysUserDbAdaptor sdba = new SysUserDbAdaptor();
	 * SysUser su = new SysUser(); String condition =
	 * SysUserDbAdaptor.KEY_SYS_USER_ID +"="+UID; su = sdba.getData(condition);
	 * 
	 * String digest, salt; if (su != null) { digest = su.mPassword; salt =
	 * "iuicuiwucnwiucniu";//su.mSalt; // DATABASE VALIDATION if (digest == null
	 * || salt == null) { throw new
	 * SQLException("Database inconsistant Salt or Digested Password altered");
	 * } if (rs.next()) { // Should not append, because UID is the primary key
	 * throw new
	 * SQLException("Database inconsistent two CREDENTIALS with the same UID");
	 * } } else { // TIME RESISTANT ATTACK (Even if the user does not exist the
	 * // Computation time is equal to the time needed for a legitimate user
	 * digest = "000000000000000000000000000="; salt = "00000000000="; userExist
	 * = false; }
	 * 
	 * byte[] bDigest = base64ToByte(digest); byte[] bSalt = base64ToByte(salt);
	 * 
	 * // Compute the new DIGEST byte[] proposedDigest =
	 * getHash(ITERATION_NUMBER, password, bSalt);
	 * 
	 * return Arrays.equals(proposedDigest, bDigest) && userExist; } catch
	 * (IOException ex){ throw new
	 * SQLException("Database inconsistant Salt or Digested Password altered");
	 * } finally{ close(rs); close(ps); } }
	 */

	/*
	 * public String getHashString(Connection con, String UID, String password)
	 * throws SQLException, NoSuchAlgorithmException{ boolean
	 * authenticated=false; PreparedStatement ps = null; ResultSet rs = null;
	 * try { boolean userExist = true; // INPUT VALIDATION if
	 * (UID==null||password==null){ // TIME RESISTANT ATTACK // Computation time
	 * is equal to the time needed by a legitimate user userExist = false;
	 * UID=""; password=""; }
	 * 
	 * ps = con.prepareStatement("SELECT SALT FROM USERS WHERE UID = ?");
	 * //choice = 4 ps.setString(1, UID); rs = ps.executeQuery(); String digest,
	 * salt; if (rs.next()) { digest = password; salt = rs.getString("SALT"); //
	 * DATABASE VALIDATION if (digest == null || salt == null) { throw new
	 * SQLException("Database inconsistant Salt or Digested Password altered");
	 * } if (rs.next()) { // Should not append, because UID is the primary key
	 * throw new
	 * SQLException("Database inconsistent two CREDENTIALS with the same UID");
	 * } } else { // TIME RESISTANT ATTACK (Even if the user does not exist the
	 * // Computation time is equal to the time needed for a legitimate user
	 * digest = "000000000000000000000000000="; salt = "00000000000="; userExist
	 * = false; }
	 * 
	 * byte[] bDigest = base64ToByte(digest); byte[] bSalt = base64ToByte(salt);
	 * 
	 * // Compute the new DIGEST byte[] proposedDigest =
	 * getHash(ITERATION_NUMBER, password, bSalt);
	 * 
	 * if (userExist){ return byteToBase64(proposedDigest); } else{ return null;
	 * }
	 * 
	 * 
	 * } catch (IOException ex){ throw new
	 * SQLException("Database inconsistent Salt or Digested Password altered");
	 * } finally{ close(rs); close(ps); } }
	 */

	/**
	 * Inserts a new user in the database
	 * 
	 * @param con
	 *            Connection An open connection to a databse
	 * @param first
	 *            String the user's first name
	 * @param last
	 *            String the user's last name
	 * @param UID
	 *            String The username of the user
	 * @param password
	 *            String The password of the user
	 * @param type
	 *            String The user's type
	 * @param num_vehic
	 *            The number of vehicles of the user
	 * @return boolean Returns true if the login and password are ok (not null
	 *         and length(login)<=100
	 * @throws SQLException
	 *             If the database is unavailable
	 * @throws NoSuchAlgorithmException
	 *             If the algorithm SHA-256 or the SecureRandom is not supported
	 *             by the JVM
	 */
	/*
	 * public boolean createUser(Connection con, String UID, String password,
	 * String first, String last, String type, int num_vehic, String chID,
	 * String eMail) throws SQLException, NoSuchAlgorithmException,
	 * java.io.UnsupportedEncodingException { PreparedStatement ps = null; try {
	 * if (UID!=null&&password!=null&&UID.length()<=100){ // Uses a secure
	 * Random not a simple Random SecureRandom random =
	 * SecureRandom.getInstance("SHA1PRNG"); // Salt generation 64 bits long
	 * byte[] bSalt = new byte[8]; random.nextBytes(bSalt); // Digest
	 * computation byte[] bDigest = getHash(ITERATION_NUMBER,password,bSalt);
	 * String sDigest = byteToBase64(bDigest); String sSalt =
	 * byteToBase64(bSalt);
	 * 
	 * System.out.println(sDigest.length()); ps = con.prepareStatement(
	 * "INSERT INTO USERS (FIRSTNAME, LASTNAME, UID, PASSWORD, SALT, USERTYPE, EMAIL, CH_ID, NUMVEHICLES) VALUES (?,?,?,?,?,?,?,?, ?)"
	 * ); //Choice 6 //args in order ps.setString(1, first); ps.setString(2,
	 * last); ps.setString(3,UID); ps.setString(4,sDigest);
	 * ps.setString(5,sSalt); ps.setString(6,type); ps.setString(7, eMail);
	 * ps.setString(8, chID); ps.setInt(9,num_vehic); ps.executeUpdate(); return
	 * true; } else { return false; } } finally { close(ps); } }
	 */

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
	 *             file://localhost/Users/evan/autobiography/User.java
	 **/
	/*
	 * public byte[] getHash(int iterationNb, String password, byte[] salt)
	 * throws NoSuchAlgorithmException, java.io.UnsupportedEncodingException {
	 * MessageDigest digest = MessageDigest.getInstance("SHA-256");
	 * digest.reset(); digest.update(salt); byte[] input =
	 * digest.digest(password.getBytes("UTF-8")); for (int i = 0; i <
	 * iterationNb; i++) { digest.reset(); input = digest.digest(input); }
	 * return input; }
	 */

	/*
	 * public void createTable(Connection con) throws SQLException{ Statement st
	 * = null; //Response answer = ClientModel.getstatResponse(new Request(1,
	 * null, null));
	 * 
	 * try { st = con.createStatement(); st.execute(
	 * "CREATE TABLE USERS (UID VARCHAR(100) PRIMARY KEY, PASSWORD VARCHAR(44) NOT NULL, SALT VARCHAR(32) NOT NULL, FIRSTNAME VARCHAR(15), LASTNAME VARCHAR(15), USERTYPE VARCHAR(15), EMAIL VARCHAR(44), CH_ID VARCHAR(10), NUMVEHICLES INT)"
	 * ); // Request choice = 1; } catch(Exception wacky){
	 * 
	 * }finally { close(st); } }
	 */
	/**
	 * Closes the current statement
	 * 
	 * @param ps
	 *            Statement
	 */
	/*
	 * public void close(Statement ps) { if (ps!=null){ try { ps.close(); }
	 * catch (SQLException ignore) { } } }
	 */

	/**
	 * Closes the current resultset
	 * 
	 * @param ps
	 *            Statement
	 */
	/*
	 * public void close(ResultSet rs) { if (rs!=null){ try { rs.close(); }
	 * catch (SQLException ignore) { } } }
	 */

	/**
	 * From a base 64 representation, returns the corresponding byte[]
	 * 
	 * @param data
	 *            String The base64 representation
	 * @return byte[]
	 * @throws IOException
	 */
	/*
	 * public static byte[] base64ToByte(String data) throws IOException {
	 * BASE64Decoder decoder = new BASE64Decoder(); return
	 * decoder.decodeBuffer(data); }
	 */

	/**
	 * From a byte[] returns a base 64 representation
	 * 
	 * @param data
	 *            byte[]
	 * @return String
	 * @throws IOException
	 */
	/*
	 * public static String byteToBase64(byte[] data){ BASE64Encoder endecoder =
	 * new BASE64Encoder(); return endecoder.encode(data); }
	 */

}