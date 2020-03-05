package manager;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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
	 * Time that the recovery verification id is valid
	 */
	private static int recoveryTimeout;
	/**
	 * A parameter that determines whether the system has been
	 */
	private static boolean init;

	private static void init() throws ConfigException {
		if (!init) {
			maxLoginAttempts = Integer.parseInt(ConfigService.fetchFromConfig("MAX_LOGIN_ATTEMPTS:"));
			loginAttemptTimelimit = Integer.parseInt(ConfigService.fetchFromConfig("LOGIN_ATTEMPT_TIMELIMIT:"));
			sessionTimeout = Integer.parseInt(ConfigService.fetchFromConfig("sessiontime:"));
			recoveryTimeout = Integer.parseInt(ConfigService.fetchFromConfig("recoverytime:"));
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
	public static HttpServletRequest getAccountInfo(HttpServletRequest request) throws ConfigException {
		init();
		String sessionID = request.getSession().getId();

		User u = UserDB.get(SessionDB.getEmail(sessionID));

		request.setAttribute("title", u.getTitle());
		request.setAttribute("fname", u.getFName());
		request.setAttribute("mname", u.getMName());
		request.setAttribute("lname", u.getLName());
		request.setAttribute("address1", u.getStreetAddress());
		request.setAttribute("address2", u.getStreetAddress2());
		request.setAttribute("addressCity", u.getCity());
		request.setAttribute("addressRegion", u.getProvince());
		request.setAttribute("addressCountry", u.getCountry());
		request.setAttribute("addressPostal", u.getPostalCode());
		request.setAttribute("contactPhone", u.getPhone());
		request.setAttribute("contactFax", u.getFax());
		request.setAttribute("language", u.getLanguage());

		return request;
	}

	public static HttpServletRequest setAccountInfo(HttpServletRequest request) throws ConfigException {
		init();
		String sessionID = request.getSession().getId();

		User u = UserDB.get(SessionDB.getEmail(sessionID));

		String title = request.getParameter("title");
		String fname = request.getParameter("fname");
		String mname = request.getParameter("mname");
		String lname = request.getParameter("lname");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String addressCity = request.getParameter("addressCity");
		String addressRegion = request.getParameter("addressRegion");
		String addressCountry = request.getParameter("addressCountry");
		String addressPostal = request.getParameter("addressPostal");
		String contactPhone = request.getParameter("contactPhone");
		String contactFax = request.getParameter("contactFax");
		String language = request.getParameter("language");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2 = request.getParameter("newPassword2");

		String errorMessageName = "";
		boolean nameError = false;
		String errorMessageAddress = "";
		boolean addressError = false;
		String errorMessageContact = "";
		boolean contactError = false;
		String errorMessageLanguage = "";
		boolean languageError = false;
		String errorMessagePassword = "";
		boolean passwordError = false;

		if (title == null || title.equals("")) {
			errorMessageName += "No title given.";
		}
		if (fname == null || fname.equals("")) {
			errorMessageName += errorMessageName.equals("") ? "" : "<br/>" + "No first name given.";
		}
		if (mname == null || mname.equals("")) {
			errorMessageName += errorMessageName.equals("") ? "" : "<br/>" + "No first name given.";
		}

		request.setAttribute("errorMessageName", errorMessageName);
		request.setAttribute("errorMessageAddress", errorMessageAddress);
		request.setAttribute("errorMessageContact", errorMessageContact);
		request.setAttribute("errorMessageLanguage", errorMessageLanguage);
		request.setAttribute("errorMessagePassword", errorMessagePassword);

		return request;
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
	 * Sends a password recovery email to a valid user.
	 * 
	 * @param email String The email address that the user wants us to recover.
	 * @param ip    String The ip of the request to recover the user's email.
	 * @return true if the user's email is already an existing user and the email
	 *         was successfully sent.
	 * @throws ConfigException If the config file cannot be found.
	 */
	public static boolean recover(String email, String ip) throws ConfigException {
		init();
		boolean output = false;
		User u = UserDB.get(email);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, recoveryTimeout);
		output = u != null && u.isActive()
				&& (u.getLastVerificationType() != User.VERIFY_TYPE_PASS_RESET || u.getLastVerificationAttempt() == null
						|| !u.getLastVerificationAttempt().before(new Date())
						|| !u.getLastVerificationAttempt().after(c.getTime()));
		if (output)
			try {
				UUID verificationID = UUID.randomUUID();
				u.setVerificationID(verificationID.toString());
				u.setLastVerificationAttempt(new Date());
				u.setLastVerificationType(User.VERIFY_TYPE_PASS_RESET);
				UserDB.update(u);
				EmailService.sendRecovery(email, verificationID);
			} catch (ConfigException | MessagingException | IllegalArgumentException e) {
				output = false;
				e.printStackTrace();
				LogEntryManager.logError(email, e, ip);
			}
		LogEntry l = new LogEntry(email, output ? "sent" : "not sent", LogEntry.RECOVER_PASSWORD, ip);
		LogEntryDB.insert(l);
		return output;
	}

	/**
	 * Verifies that the given user has been sent a verification email.
	 * 
	 * @param verificationID   String The UUID used to verify the email link.
	 * @param verificationType int The type of thing that the system is checking is
	 *                         verified.
	 * @return true if the user with the given verification id last used the
	 *         verification type within the configured timeout period.
	 * @throws ConfigException if the config file is missing.
	 */
	public static boolean verification(String verificationID, int verificationType) throws ConfigException {
		init();
		User u = UserDB.getByVerificationID(verificationID);
		boolean output = false;

		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -recoveryTimeout);

		output = u != null && u.isActive() && u.isVerified() && u.getLastVerificationType() == verificationType
				&& u.getLastVerificationAttempt() != null && u.getLastVerificationAttempt().after(c.getTime())
				&& u.getLastVerificationAttempt().before(new Date());

		return output;
	}

	/**
	 * Changes the password for a recovery attempt, but only if the verification id
	 * checks out and both passwords match.
	 * 
	 * @param newPass        String The new password to be assigned to the user.
	 * @param confirmPass    String The user's attempt to duplicate the new
	 *                       password.
	 * @param verificationID String The universally unique id that
	 * @param ip
	 * @return true if the given verification id matches, the two different
	 *         passwordds match, and the password was successfully updated.
	 * @throws ConfigException
	 */
	public static boolean recoveryChangePass(String newPass, String confirmPass, String verificationID, String ip)
			throws ConfigException {
		init();
		User u = UserDB.getByVerificationID(verificationID);

		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -recoveryTimeout);

		boolean output = u != null && u.isActive() && u.isVerified()
				&& u.getLastVerificationType() == User.VERIFY_TYPE_PASS_RESET && u.getLastVerificationAttempt() != null
				&& u.getLastVerificationAttempt().after(c.getTime())
				&& u.getLastVerificationAttempt().before(new Date());

		if (output) {
			if (newPass.equals(confirmPass)) {
				String salt = EncryptionService.getSalt();
				try {
					String passHash = EncryptionService.hash(newPass, salt);

					if (u.getPassHash() != passHash) {
						u.setPassHash(passHash);
						u.setPassSalt(salt);
						u.setLastVerificationType(User.VERIFY_TYPE_NONE);
						output = UserDB.update(u);
					} else {
						throw new IllegalArgumentException("You cannot use the same password twice in a row.");
					}
				} catch (NumberFormatException e) {
					output = false;
					LogEntryManager.logError(u.getEmail(), e, ip);
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					output = false;
					LogEntryManager.logError(u.getEmail(), e, ip);
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					output = false;
					LogEntryManager.logError(u.getEmail(), e, ip);
					e.printStackTrace();
				} catch (ConfigException e) {
					output = false;
					LogEntryManager.logError(u.getEmail(), e, ip);
					e.printStackTrace();
				}
			} else
				output = false;
		}

		return output;
	}

	/**
	 * Method to check if a user already exists in the database
	 * 
	 * @param email The user to check
	 * @return true if the user exists, false if not.
	 */
	public static boolean userExists(String email) {

		if (UserDB.get(email) == null) {
			return false;
		}

		return true;
	}

	
	public static boolean passwordConf(String password, String passwordConf) {
		if (password.equals(passwordConf)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean createUser(String email, String password, String passwordConf, String title, String fName,
			String mName, String lName, String phone, String fax, String language, String streetAddress,
			String streetAddress2, String city, String province, String country, String postalCode) throws Exception {
		User user = null;
		try {
			UUID registrationID = UUID.randomUUID();
			user = new User(email, fName, mName, lName, User.USER, phone, password, title, new Date(), fax,
					true, streetAddress, streetAddress2, city, province, country, postalCode, language, false,
					registrationID.toString(), new Date(), User.VERIFY_TYPE_CREATE_ACCOUNT);
			EmailService.sendRegisterEmail(email, registrationID);
		} catch (NumberFormatException | ConfigException | InvalidKeySpecException | NoSuchAlgorithmException e) {

			throw new Exception(
					"Something went wrong, please try again later. If problem persists, please contact us directly");
		} catch (MessagingException e) {
			throw new Exception();
		}

		// TODO set up email verification

		// write user to database
		if (user != null) {
			UserDB.insert(user);
		}

		return true;

	}
}