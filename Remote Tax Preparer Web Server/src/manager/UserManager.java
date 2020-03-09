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
import service.ConfigService;
import service.EmailService;
import service.EncryptionService;
import util.cesar.Debugger;

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
	private static int verificationTimeout;
	/**
	 * A parameter that determines whether the system has been
	 */
	private static boolean init;

	private static void init() throws ConfigException {
		if (!init) {
			maxLoginAttempts = Integer.parseInt(ConfigService.fetchFromConfig("MAX_LOGIN_ATTEMPTS:"));
			loginAttemptTimelimit = Integer.parseInt(ConfigService.fetchFromConfig("LOGIN_ATTEMPT_TIMELIMIT:"));
			sessionTimeout = Integer.parseInt(ConfigService.fetchFromConfig("sessiontime:"));
			verificationTimeout = Integer.parseInt(ConfigService.fetchFromConfig("verificationtime:"));
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
				return user.getPassHash().equals(pass_hash) && user.isActive();
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
	 * @param request HttpServletRequest The web request to fill with the user's
	 *                attributes.
	 * @return HttpServletRequest The filled in request.
	 * @throws ConfigException if the config file cannot be found.
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

	/**
	 * Updates the info of the user who sent the given request.
	 * 
	 * @param request HttpServletRequest The web request to get the user's
	 *                attributes from.
	 * @return HttpServletRequest The request updated with any errors.
	 * @throws ConfigException if the config file cannot be found.
	 */
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
		String errorMessagePassword = "";
		boolean passwordError = false;

		String successMessage = "";

		// Check name update.
		if (title == null || title.equals("")) {
			errorMessageName += "No title given.";
			nameError = true;
		}
		if (fname == null || fname.equals("")) {
			errorMessageName += nameError ? "<br/>" : "" + "No first name given.";
			nameError = true;
		}
		if (mname == null) {
			errorMessageName += nameError ? "<br/>" : "" + "No middle name given.";
			nameError = true;
		}
		if (lname == null || lname.equals("")) {
			errorMessageName += nameError ? "<br/>" : "" + "No last name given.";
			nameError = true;
		}
		if (nameError)
			request.setAttribute("errorMessageName", errorMessageName);
		else if (title.equals(u.getTitle()) && fname.equals(u.getFName()) && mname.equals(u.getMName())
				&& lname.equals(u.getLName())) {
		} else {
			try {
				u.setTitle(title);
				u.setFName(fname);
				u.setMName(mname);
				u.setLName(lname);

				if (UserDB.update(u))
					successMessage += "<br/> Name changes have been saved!";
				else
					request.setAttribute("errorMessageName", "Failed to save changes to name.");
			} catch (IllegalArgumentException e) {
				request.setAttribute("errorMessageName", e.getMessage());
				LogEntryManager.logError(u.getEmail(), e, request.getRemoteAddr());
				e.printStackTrace();
			}
		}

		// Check address update.
		if (address1 == null || address1.equals("")) {
			errorMessageAddress += "No street address given.";
			addressError = true;
		}
		if (address2 == null) {
			errorMessageAddress += addressError ? "<br/>" : "" + "No street address 2 given.";
			addressError = true;
		}
		if (addressCity == null || addressCity.equals("")) {
			errorMessageAddress += addressError ? "<br/>" : "" + "No city given.";
			addressError = true;
		}
		if (addressRegion == null || addressRegion.equals("")) {
			errorMessageAddress += addressError ? "<br/>" : "" + "No province given.";
			addressError = true;
		}
		if (addressCountry == null || addressCountry.equals("")) {
			errorMessageAddress += addressError ? "<br/>" : "" + "No country given.";
			addressError = true;
		}
		if (addressPostal == null || addressPostal.equals("")) {
			errorMessageAddress += addressError ? "<br/>" : "" + "No postal code given.";
			addressError = true;
		}
		if (addressError)
			request.setAttribute("errorMessageAddress", errorMessageAddress);
		else if (address1.equals(u.getStreetAddress()) && address2.equals(u.getStreetAddress2())
				&& addressCity.equals(u.getCity()) && addressRegion.equals(u.getProvince())
				&& addressCountry.equals(u.getCountry()) && addressPostal.equals(u.getPostalCode())) {
			/* Do nothing */
		} else {
			try {
				u.setStreetAddress(address1);
				u.setStreetAddress2(address2);
				u.setCity(addressCity);
				u.setProvince(addressRegion);
				u.setCountry(addressCountry);
				u.setPostalCode(addressPostal);

				if (UserDB.update(u))
					successMessage += "<br/> Address changes have been saved!";
				else
					request.setAttribute("errorMessageName", "Failed to save changes to address info.");
			} catch (IllegalArgumentException e) {
				request.setAttribute("errorMessageAddress", e.getMessage());
				LogEntryManager.logError(u.getEmail(), e, request.getRemoteAddr());
				e.printStackTrace();
			}
		}

		// Check contact update.
		if (contactPhone == null || contactPhone.equals("")) {
			errorMessageContact += contactError ? "<br/>" : "" + "No phone number given.";
			contactError = true;
		}
		if (contactFax == null) {
			errorMessageContact += contactError ? "<br/>" : "" + "No fax number given.";
			contactError = true;
		}
		if (contactError)
			request.setAttribute("errorMessageContact", errorMessageContact);
		else if (contactPhone.equals(u.getPhone()) && contactFax.equals(u.getFax())) {
			/* Do nothing */
		} else {
			try {
				u.setPhone(contactPhone);
				u.setFax(contactFax);

				if (UserDB.update(u))
					successMessage += "<br/> Contact changes have been saved!";
				else
					request.setAttribute("errorMessageContact", "Failed to save changes to contact info.");
			} catch (IllegalArgumentException e) {
				request.setAttribute("errorMessageContact", e.getMessage());
				LogEntryManager.logError(u.getEmail(), e, request.getRemoteAddr());
				e.printStackTrace();
			}
		}

		// Check language update.
		if (language == null || language.equals(""))
			request.setAttribute("errorMessageLanguage", "No language given.");
		else if (language.equals(u.getLanguage())) {
			/* Do nothing */
		} else {
			try {
				u.setLanguage(language);

				if (UserDB.update(u))
					successMessage += "<br/> Language changes have been saved!";
				else
					request.setAttribute("errorMessageLanguage", "Failed to save changes to language info.");
			} catch (IllegalArgumentException e) {
				request.setAttribute("errorMessageLanguage", e.getMessage());
				LogEntryManager.logError(u.getEmail(), e, request.getRemoteAddr());
				e.printStackTrace();
			}
		}

		// Check password update.
		if ((oldPassword == null || oldPassword.equals("")) && (newPassword1 == null || newPassword1.equals(""))
				&& (newPassword2 == null || newPassword2.equals(""))) {
			/* Do nothing */
		} else {
			if (oldPassword == null || oldPassword.equals("")) {
				errorMessageContact += contactError ? "<br/>" : "" + "Please enter your old password.";
				passwordError = true;
			}
			if (newPassword1 == null || newPassword1.equals("")) {
				errorMessageContact += contactError ? "<br/>" : "" + "Please enter your new password.";
				passwordError = true;
			}
			if (newPassword2 == null || newPassword2.equals("")) {
				errorMessageContact += contactError ? "<br/>" : "" + "Please enter your new password again.";
				passwordError = true;
			}

			String passHash = null;
			try {
				passHash = EncryptionService.hash(oldPassword, u.getPassSalt());
			} catch (NumberFormatException | NoSuchAlgorithmException | InvalidKeySpecException | ConfigException e) {
				errorMessageContact += contactError ? "<br/>" : "" + "There was an error setting your password.";
				passwordError = true;
				LogEntryManager.logError(u.getEmail(), e, request.getRemoteAddr());
				e.printStackTrace();
			}

			if (passwordError)
				request.setAttribute("errorMessagePassword", errorMessagePassword);
			else if (!u.getPassHash().equals(passHash))
				request.setAttribute("errorMessagePassword", "Your old password didn't match.");
			else if (!newPassword1.equals(newPassword2))
				request.setAttribute("errorMessagePassword", "Your new passwords didn't match.");
			else {
				try {
					u.setPassword(newPassword1);

					if (UserDB.update(u))
						successMessage += "<br/> Password changes have been saved.";
					else
						request.setAttribute("errorMessagePassword", "There was an error setting your password.");
				} catch (IllegalArgumentException e) {
					request.setAttribute("errorMessagePassword", e.getMessage());
					LogEntryManager.logError(u.getEmail(), e, request.getRemoteAddr());
					e.printStackTrace();
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					request.setAttribute("errorMessagePassword", "There was an error setting your password.");
					LogEntryManager.logError(u.getEmail(), e, request.getRemoteAddr());
					e.printStackTrace();
				}
			}
		}

		if (successMessage != null && !successMessage.equals(""))
			request.setAttribute("successMessage", successMessage);

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
		c.add(Calendar.MINUTE, verificationTimeout);
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
		c.add(Calendar.MINUTE, -verificationTimeout);

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
		c.add(Calendar.MINUTE, -verificationTimeout);

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
				} catch (NumberFormatException | NoSuchAlgorithmException | InvalidKeySpecException
						| ConfigException e) {
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
		return UserDB.get(email) != null;
	}

	/**
	 * A little silly.
	 * 
	 * @param password
	 * @param passwordConf
	 * @return
	 */
	public static boolean passwordConf(String password, String passwordConf) {
		return password.equals(passwordConf);
	}

	public static boolean createUser(String email, String password, String passwordConf, String title, String fName,
			String mName, String lName, String phone, String fax, String language, String streetAddress,
			String streetAddress2, String city, String province, String country, String postalCode) throws Exception {
		User user = null;
		UUID registrationID = UUID.randomUUID();
		try {
			user = new User(email, fName, mName, lName, User.USER, phone, password, title, new Date(), fax,
					true, streetAddress, streetAddress2, city, province, country, postalCode, language, false,
					registrationID.toString(), new Date(), User.VERIFY_TYPE_CREATE_ACCOUNT);
			UserDB.insert(user);
			EmailService.sendRegisterEmail(email, registrationID);
		} catch (NumberFormatException | ConfigException | InvalidKeySpecException | NoSuchAlgorithmException e) {

			throw new Exception(
					"Something went wrong, please try again later. If problem persists, please contact us directly");
		} catch (MessagingException e) {
			throw new MessagingException("Something went wrong with the verification email, please click <a href=\"/register?action=resend&email="+email+"\">here</a>. If problem persists, please contact us directly.");
		}
		return true;

	}

	public static void resendVerificationEmail(String email) throws MessagingException {
		User user = null;
		UUID registrationID = UUID.randomUUID();
		
		user = UserDB.get(email);
		user.setVerificationID(registrationID.toString());
		UserDB.update(user);
		
		try {
			EmailService.sendRegisterEmail(email, registrationID);
		} catch (ConfigException | MessagingException e) {
			throw new MessagingException("Something went wrong with the verification email, please click <a href=\"/register?action=resend&email="+email+"\">here</a>. If problem persists, please contact us directly.");
		}
	}

	public static void verifyEmail(String registrationID) throws Exception {
		init();
		User user = UserDB.getByVerificationID(registrationID);

		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -verificationTimeout);
		boolean output = user != null && user.isActive()
				&& user.getLastVerificationType() == User.VERIFY_TYPE_CREATE_ACCOUNT && user.getLastVerificationAttempt() != null
				&& user.getLastVerificationAttempt().after(c.getTime())
				&& user.getLastVerificationAttempt().before(new Date());
		
		Debugger.log(verificationTimeout);
		
		if (output) {
			user.setVerified(true);
			user.setLastVerificationType(User.VERIFY_TYPE_NONE);
			output = UserDB.update(user);
		} else {
			throw new Exception("Verification link has timed out");
		}
		if (!output) {
			throw new Exception("An error has occured, please try again. If problem persists, please contact us directly");
		}
		
		
	}
  
	public static int getRole(String sessionID) throws ConfigException {
		User user = getUser(SessionManager.getEmail(sessionID));
		if (user == null) {
			return -1;
		}
		return user.getPermissionLevel();
  }
  
  	/**
	 * Deletes the account of the user with the given session.
	 * 
	 * @param sessionID String The session ID number of the user whose account to
	 *                  delete.
	 * @param ip        String The ip address of the user whose account is being
	 *                  deleted.
	 * @return true if the account was successfully deleted.
	 */
	public static boolean deleteAccount(String sessionID, String ip) {
		User u = UserDB.get(SessionDB.getEmail(sessionID));

		if (u != null && u.isActive() && u.isVerified()) {
			u.setActive(false);
			boolean output = UserDB.update(u);
			LogEntryDB.insert(new LogEntry(u.getEmail(), "User requested.", LogEntry.DEACTIVATE_ACCOUNT, ip));
			return output;
		} else {
			return false;
		}
	}
}