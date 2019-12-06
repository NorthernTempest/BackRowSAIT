package managers;

import java.util.ArrayList;

import domain.Log;

/**
 * 
 * Class Description: 	Class that communicates with the LogDB class as a proxy
 * 						to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz
 *
 */
public class LogManager {

	/**
	 * Takes the Log object passed into the method and calls the insert method
	 * of the LogDB, passing the Log.
	 * @param log Log to insert
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(Log log) {
		return false;
	}
	
	/**
	 * Takes the Log object passed into the method and calls the update method
	 * of the LogDB, passing the Log.
	 * @param log Log in the database to update
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(Log log) {
		return false;
	}
	
	/**
	 * Takes the logID passed into the method and calls the delete method
	 * of the LogDB, passing the logID.
	 * @param logID logID of the Log in the database to delete
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(int logID) {
		return false;
	}
	
	/**
	 * Takes the logID passed into the method and calls the get method
	 * of the LogDB, passing the logID.
	 * @param logID logID of the Log in the database to retrieve
	 * @return Log containing the information of the requested Log
	 */
	public Log get(int logID) {
		return null;
	}
	
	/**
	 * Calls the getAll method of the LogDB.
	 * @return ArrayList<Log> containing all the Logs in the database
	 */
	public ArrayList<Log> getAll() {
		return null;
	}
	
	/**
	 * Counts the number of times a log of type LOGIN_ATTEMPT has been
	 * created matching the passed email within the past 10 minutes.
	 * @param email email of logs to count
	 * @return number of logs counted matching the above conditions
	 */
	public int checkAttempts(String email) {
		return 0;
	}
	
	/**
	 * Inserts into the log table of the database a specific type of log
	 * based on a user having successfully logged in.
	 * @param email email of the user that successfully logged in
	 * @param ip ip of the user that successfully logged in
	 */
	public void insertLogin(String email, String ip) {
		
	}
}
