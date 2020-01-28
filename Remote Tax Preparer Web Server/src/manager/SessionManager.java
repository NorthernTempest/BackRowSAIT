package manager;

import java.util.ArrayList;

import domain.Session;

/**
 * 
 * Class Description: 	Class that communicates with the SessionDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz
 *
 */
public class SessionManager {

	// Should not do this
	/**
	 * Takes the Session object passed into the method and calls the insert method
	 * of the SessionDB, passing the Session.
	 * @param session Session to insert
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(Session session) {
		return false;
	}

	// Should not do this
	/**
	 * Takes the sessionID passed into the method and calls the delete method
	 * of the SessionDB, passing the sessionID.
	 * @param sessionID sessionID of the Session in the database to delete
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(String sessionID) {
		return false;
	}

	// Should not do this
	/**
	 * Takes the sessionID passed into the method and calls the get method
	 * of the SessionDB, passing the sessionID.
	 * @param sessionID sessionID of the Session in the database to retrieve
	 * @return Session containing the information of the requested Session
	 */
	public Session get(String sessionID) {
		return null;
	}

	// Should not do this
	/**
	 * Calls the getAll method of the SessionDB.
	 * @return ArrayList<Session> containing all the Sessions in the database
	 */
	public ArrayList<Session> getAll() {
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
	 * Takes the email and creates a new Session object passing the email
	 * into the constructor.
	 * @param email email to create a Session object for
	 * @return String
	 */
	public static String createSession(String email) {
		return null;
	}
}
