package manager;

import java.util.Calendar;
import java.util.Date;

import databaseAccess.SessionDB;
import databaseAccess.UserDB;
import domain.LogEntry;
import domain.Session;
import domain.User;
import exception.ConfigException;
import service.ConfigService;

/**
 * 
 * Class Description: 	Class that communicates with the SessionDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Cesar Guzman, Tristen Kreutz, Jesse Goerzen
 *
 */
public final class SessionManager {

	/**
	 * Time a session can remain active/idle.
	 */
	private static int sessionTimeout;
	
	/**
	 * Specifies whether or not the class has been initialized with values from the config.
	 */
	private static boolean init;

	private static void init() throws ConfigException {
		if (!init) {
			sessionTimeout = Integer.parseInt(ConfigService.fetchFromConfig("sessiontime:"));
			init = true;
		}
	}
	
	/**
	 * Takes the sessionID and checks the session table in the database
	 * to see whether or not the session is currently active.
	 * @param sessionID sessionID to check
	 * @return boolean based on whether or not the current Session is active
	 */
	public static boolean isSessionActive(String sessionID) {
		Session s = SessionDB.get(sessionID);
		boolean output = s != null;
		
		if(s != null && !s.getTimeout().after(new Date())) {
			output = false;
			SessionDB.delete(sessionID);
		}
		
		return output;
	}
	
	/**
	 * Creates a Session object and inserts it into the database.
	 * @param email email to create a Session object for
	 * @param sessionID the sessionID to set for the Session object
	 * @return true if session created successfully, false if not
	 * @throws ConfigException 
	 */
	public static boolean createSession(String email, String sessionID, String ip) throws ConfigException {
		init();
		//Create session with correct timeout date
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, sessionTimeout);
		Date timeoutDate = cal.getTime();
		Session session = new Session(email, sessionID, timeoutDate);
		
		//insert session to database
		SessionDB.insert(session);
		
		//write to log
		String logMessage = "Session created";
		LogEntryManager.createLogEntry(email, logMessage, LogEntry.SESSION_UPDATE, ip);
		
		return false;
	}

	public static void invalidate(String sessionID) {
		SessionDB.delete(sessionID);
	}

	/**
	 * Gets the email associated with a session ID
	 * @param id the sessionID
	 * @return the email associated with a session ID
	 */
	public static String getEmail(String id) {
		return SessionDB.getEmail(id);
	}

	public static int getPermissionLevel(String sessionID) {
		int output = User.DEFAULT;
		
		String email = SessionDB.getEmail(sessionID);
		
		User u = null;
		
		if(email != null && !email.equals(""))
			u = UserDB.get(email);
		
		if(u != null)
			output = u.getPermissionLevel();
		
		return output;
	}
}
