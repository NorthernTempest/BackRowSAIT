package manager;

import java.util.Calendar;
import java.util.Date;

import databaseAccess.SessionDB;
import domain.LogEntry;
import domain.Session;

/**
 * 
 * Class Description: 	Class that communicates with the SessionDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Cesar Guzman, Tristen Kreutz
 *
 */
public final class SessionManager {
	
	public static final int SESSION_TIMEOUT = 30;
	
	/**
	 * Takes the sessionID and checks the session table in the database
	 * to see whether or not the session is currently active.
	 * @param sessionID sessionID to check
	 * @return boolean based on whether or not the current Session is active
	 */
	public static boolean isSessionActive(String sessionID) {
		return false;
	}
	
	/**
	 * Creates a Session object and inserts it into the database.
	 * @param email email to create a Session object for
	 * @param sessionID the sessionID to set for the Session object
	 * @return true if session created successfully, false if not
	 */
	public static boolean createSession(String email, String sessionID, String ip) {
		//Create session with correct timeout date
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, SESSION_TIMEOUT);
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
}
