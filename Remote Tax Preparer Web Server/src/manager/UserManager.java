package manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import databaseAccess.LogEntryDB;
import databaseAccess.UserDB;
import domain.LogEntry;
import domain.User;

/**
 * 
 * Class Description: Class that communicates with the UserDB class as a proxy
 * to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz, Cesar Guzman, Taylor Hanlon
 *
 */
public final class UserManager {

	/**
	 * Maximum number of login attempts within LOGIN_ATTEMPT_TIMELIMIT
	 */
	private static final int MAX_LOGIN_ATTEMPTS = Integer.parseInt(ConfigService.fetchFromConfig("MAX_LOGIN_ATTEMPTS:"));
	/**
	 * Time in minutes to check for login attempts
	 */
	private static final int LOGIN_ATTEMPT_TIMELIMIT = Integer.parseInt(ConfigService.fetchFromConfig("LOGIN_ATTEMPT_TIMELIMIT:"));
	
	/**
	 * Takes an email String and a password String to check against what exists in
	 * the user table to see if the information matches for authentication purposes
	 * 
	 * @param email    email to check
	 * @param password password
	 * @return boolean based on whether or not the information matches
	 */
	public static boolean authenticate(String email, String password) {
		
		return false;
	}

	/**
	 * Takes an email, password, and ip to be utilized in logging in to the website
	 * assuming the password passed matches the User tied to the email passed.
	 * 
	 * @param email    email of User to login
	 * @param password password
	 * @param ip       ip of login computer
	 * @return boolean based on whether or not the User can successfully login
	 */
	public static boolean login(String email, String password, String ip) {
		//set log message
		String logMessage = null;

		//Check logs for attempts from email, if too many attempts don't let them in.
		if (tooManyAttempts(email)) {
			return false;
		}

		if (authenticate(email, password)) {
			logMessage = "Successful login attempt";
			//write to log
			LogEntryManager.createLogEntry(email, logMessage, LogEntry.LOGIN_ATTEMPT, ip);
			return true;
		} else {
			logMessage = "Failed login attempt";
			//write to log
			LogEntryManager.createLogEntry(email, logMessage, LogEntry.LOGIN_ATTEMPT, ip);

			return false;
		}
	}

	/**
	 * Checks logs for login attempts from email, if too many attempts return true
	 * 
	 * @param email the email to check login attempts for
	 * @return true if too many log in attempts, false if not
	 */
	public static boolean tooManyAttempts(String email) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date endDate = cal.getTime();
		cal.add(Calendar.MINUTE, LOGIN_ATTEMPT_TIMELIMIT);

		Date startDate = cal.getTime();
		ArrayList<LogEntry> log = LogEntryManager.getLog(email, LogEntry.LOGIN_ATTEMPT, startDate, endDate, null);

		if (log.size() > MAX_LOGIN_ATTEMPTS) {
			return true;
		}
		return false;
	}

	/**
	 * Takes an email for a User and retrieves all account information for that User
	 * that is stored in the user table of the database.
	 * 
	 * @param email email of User to retrieve information for
	 * @return Properties objects containing the account information of the request
	 *         User
	 */
	public static Properties getAccountInfo(String email) {

		return null;
	}

	/**
	 * Utilizing the parameters that have been passed, this method verifies with the
	 * User database to check whether or not the user is currently logged in
	 * somewhere.
	 * 
	 * @param email    email to check
	 * @param password password to check
	 * @param ip       ip to check
	 * @return boolean
	 */
	public static boolean loggedIn(String email, String password, String ip) {

		return false;
	}

	/**
	 * Method to generate and return a String value containing a recovery key for
	 * the user that is utilized in identity verification for recovering an account.
	 * 
	 * @return recovery key
	 */
	public static String generateRecoveryKey() {

		return null;
	}

	/**
	 * Sends an email to the specified user containing the generated recovery key.
	 * 
	 * @return boolean
	 */
	public static boolean sendRecoveryEmail() {

		return false;
	}

	/**
	 * Takes the recovery key that was entered by the user and compares it to the
	 * key that was generated and sent to their email.
	 * 
	 * @return boolean
	 */
	public static boolean verifyRecoveryKey() {

		return false;
	}
	
	/**
	 * Allows the user to recover their
	 * 
	 * @param parameter
	 * @param parameter2
	 * @return true if the user's email is already an existing user and the email was successfully sent.
	 */
	public static boolean recover(String email) {
		User u = UserDB.get(email);
		return u != null;
	}
}