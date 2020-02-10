package manager;

import java.util.ArrayList;
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

	// Should not do this
	/**
	 * Takes the Session object passed into the method and calls the insert method
	 * of the SessionDB, passing the Session.
	 * @param session Session to insert
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(Session session) {
		return false;
	}

	// Should not do this
	/**
	 * Takes the sessionID passed into the method and calls the delete method
	 * of the SessionDB, passing the sessionID.
	 * @param sessionID sessionID of the Session in the database to delete
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(String sessionID) {
		return false;
	}

	// Should not do this
	/**
	 * Takes the sessionID passed into the method and calls the get method
	 * of the SessionDB, passing the sessionID.
	 * @param sessionID sessionID of the Session in the database to retrieve
	 * @return Session containing the information of the requested Session
	 */
	public static Session get(String sessionID) {
		return null;
	}

	// Should not do this
	/**
	 * Calls the getAll method of the SessionDB.
	 * @return ArrayList<Session> containing all the Sessions in the database
	 */
	public static ArrayList<Session> getAll() {
		return null;
	}
	
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
		SessionDB sdb = new SessionDB();
		sdb.insert(session);
		
		//write to log
		String logMessage = "Session created";
		LogEntryManager.createLogEntry(email, logMessage, LogEntry.SESSION_UPDATE, ip);
		
		return false;
	}
}
