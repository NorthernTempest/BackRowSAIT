package databaseAccess;

import java.util.ArrayList;

import domain.Session;

/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the session table in the database.
 *
 * @author Tristen Kreutz
 *
 */
public final class SessionDB {

	/**
	 * Establishes a connection with the database and inserts the Session object passed
	 * into this method into the session table.
	 * @param session Session to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(Session session) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and removes the Session from the
	 * session table that has a Primary Key matching the sessionID being passed
	 * into this method.
	 * @param sessionID sessionID of the Session to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(String sessionID) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and selects the Session in the
	 * session table that has a Primary Key matching the sessionID being passed
	 * into this method.
	 * @param sessionID sessionID of the Session to retrieve from the database
	 * @return Session that contains the information of the requested Session
	 */
	public Session get(String sessionID) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Sessions
	 * within the session table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<Session> containing all of the Sessions from the database
	 */
	public ArrayList<Session> getAll() {
		return null;
	}
}
