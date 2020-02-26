package manager;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;

import databaseAccess.LogEntryDB;
import databaseAccess.SessionDB;
import databaseAccess.UserDB;
import domain.LogEntry;
import domain.Session;
import domain.User;
import exception.ConfigException;
import exception.UserException;
import service.ConfigService;
import service.EmailService;
import service.EncryptionService;

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
	private static int maxLoginAttempts;
	/**
	 * Time in minutes to check for login attempts
	 */
	private static int loginAttemptTimelimit;
	/**
	 * Time that the session is valid
	 */
	private static int sessionTimeout;
	/**
	 * A parameter that determines whether the system has been
	 */
	private static boolean init;

	private static void init() throws ConfigException {
		if (!init) {
			maxLoginAttempts = Integer.parseInt(ConfigService.fetchFromConfig("MAX_LOGIN_ATTEMPTS:"));
			loginAttemptTimelimit = Integer.parseInt(ConfigService.fetchFromConfig("LOGIN_ATTEMPT_TIMELIMIT:"));
			sessionTimeout = Integer.parseInt(ConfigService.fetchFromConfig("sessiontime:"));
			init = true;
		}
	}

	/**
	 * Takes an email String and a password String to check against what exists in
	 * the user table to see if the information matches for authentication purposes
	 * 
	 * @param email    email to check
	 * @param password password
	 * @return boolean based on whether or not the information matches
	 * @throws ConfigException
	 */
	public static boolean authenticate(String email, String password) throws ConfigException {
		init();
		User user = getUser(email);

		if (user != null) {
			try {
				String pass_hash = EncryptionService.hash(password, user.getPassSalt());
				if (user.getPassHash().equals(pass_hash)) {
					return true;
				}
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
	 * @throws ConfigException
	 */
	public static boolean login(String email, String password, String sessionID, String ip) throws ConfigException {
		init();
		// set log message
		String logMessage = null;

		// Check logs for attempts from email, if too many attempts don't let them in.
		if (tooManyAttempts(email)) {
			return false;
		} else if (authenticate(email, password)) {
			logMessage = "Successful login attempt";
			// write to log
			LogEntryManager.createLogEntry(email, logMessage, LogEntry.LOGIN_ATTEMPT, ip);

			Calendar c = Calendar.getInstance();
			c.add(Calendar.MINUTE, sessionTimeout);

			Session newSession = new Session(email, sessionID, c.getTime());

			SessionDB.insert(newSession);
			return true;
		} else {
			logMessage = "Failed login attempt";
			// write to log
			LogEntryManager.createLogEntry(email, logMessage, LogEntry.LOGIN_ATTEMPT, ip);

			return false;
		}
	}

	/**
	 * Checks logs for login attempts from email, if too many attempts return true
	 * 
	 * @param email the email to check login attempts for
	 * @return true if too many log in attempts, false if not
	 * @throws ConfigException
	 */
	public static boolean tooManyAttempts(String email) throws ConfigException {
		init();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date endDate = cal.getTime();
		cal.add(Calendar.MINUTE, loginAttemptTimelimit);

		Date startDate = cal.getTime();
		ArrayList<LogEntry> log = LogEntryManager.getLog(email, LogEntry.LOGIN_ATTEMPT, startDate, endDate, null);

		if (log.size() > maxLoginAttempts) {
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
	 * @throws ConfigException
	 */
	public static Properties getAccountInfo(String email) throws ConfigException {
		init();
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
	 * @throws ConfigException
	 */
	public static boolean loggedIn(String email, String password, String ip) throws ConfigException {
		init();
		return false;
	}

	/**
	 * @param email
	 * @return the user with a matching email
	 * @throws ConfigException
	 */
	public static User getUser(String email) throws ConfigException {
		init();
		return UserDB.get(email);
	}

	/**
	 * Allows the user to recover their
	 * 
	 * @param email
	 * @return true if the user's email is already an existing user and the email
	 *         was successfully sent.
	 * @throws ConfigException
	 */
	public static boolean recover(String email, String ip) throws ConfigException {
		init();
		boolean output = false;
		User u = UserDB.get(email);
		output = u != null;
		if(output)
			try {
				String verificationID = UUID.randomUUID().toString();
				u.setVerificationID(verificationID);
				u.setLastVerificationAttempt(new Date());
				u.setLastVerificationType(User.VERIFY_TYPE_PASS_RESET);
				UserDB.update(u);
				EmailService.sendRecovery(email, verificationID);
			} catch (ConfigException e) {
				output = false;
				e.printStackTrace();
			} catch (MessagingException e) {
				output = false;
				e.printStackTrace();
			} catch (UserException e) {
				output = false;
				e.printStackTrace();
			}
		LogEntry l = new LogEntry(email, output ? "sent" : "not sent",LogEntry.RECOVER_PASSWORD, ip);
		LogEntryDB.insert(l);
		return output;
	}

	public static User verification(String verify) throws ConfigException {
		init();
		// TODO Auto-generated method stub
		return null;
	}
}