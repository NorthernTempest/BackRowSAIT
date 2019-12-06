package databaseAccess;

/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the log table in the database.
 *
 * @author Tristen Kreutz
 *
 */
import java.util.ArrayList;

import domain.Log;

public class LogDB {

	/**
	 * Establishes a connection with the database and inserts the Log object passed
	 * into this method into the log table.
	 * @param log Log to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(Log log) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and updates the Log in the log
	 * table that shares a Primary Key with the Log being passed into this method.
	 * All values of the Log in the database will be updated with the values of the
	 * object being passed assuming constraints are not violated.
	 * @param log Log to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(Log log) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and removes the Log from the
	 * log table that has a Primary Key matching the logID being passed
	 * into this method.
	 * @param logID logID of the Log to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(int logID) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and selects the Log in the
	 * log table that has a Primary Key matching the logID being passed
	 * into this method.
	 * @param logID logID of the Log to retrieve from the database
	 * @return Log that contains the information of the requested Log
	 */
	public Log get(int logID) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects all Logs in the
	 * log table that has an email matching the email being passed
	 * into this method.
	 * @param email email of the Logs to retrieve from the database
	 * @return ArrayList<Log> that contains information of the requested Logs
	 */
	public ArrayList<Log> getByEmail(String email) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Logs
	 * within the log table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<Log> containing all of the Logs from the database
	 */
	public ArrayList<Log> getAll() {
		return null;
	}
}
