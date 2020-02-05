package databaseAccess;

/**
 * 
 * Class Description: 	Class that establishes a connection and communicates directly
 * 						with the logEntry table in the database.
 *
 * @author Tristen Kreutz
 *
 */
import java.util.ArrayList;

import domain.LogEntry;

public class LogEntryDB {

	/**
	 * Establishes a connection with the database and inserts the LogEntry object passed
	 * into this method into the logEntry table.
	 * @param logEntry LogEntry to insert into the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean insert(LogEntry logEntry) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and updates the LogEntry in the logEntry
	 * table that shares a Primary Key with the LogEntry being passed into this method.
	 * All values of the LogEntry in the database will be updated with the values of the
	 * object being passed assuming constraints are not violated.
	 * @param logEntry LogEntry to update in the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean update(LogEntry logEntry) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and removes the LogEntry from the
	 * logEntry table that has a Primary Key matching the logEntryID being passed
	 * into this method.
	 * @param logEntryID logEntryID of the LogEntry to remove from the database
	 * @return boolean based on whether or not the operation was successful
	 */
	public boolean delete(int logEntryID) {
		return false;
	}
	
	/**
	 * Establishes a connection with the database and selects the LogEntry in the
	 * logEntry table that has a Primary Key matching the logEntryID being passed
	 * into this method.
	 * @param logEntryID logEntryID of the LogEntry to retrieve from the database
	 * @return LogEntry that contains the information of the requested Log
	 */
	public LogEntry get(int logEntryID) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects all Logs in the
	 * logEntry table that has an email matching the email being passed
	 * into this method.
	 * @param email email of the Logs to retrieve from the database
	 * @return ArrayList<Log> that contains information of the requested Logs
	 */
	public ArrayList<LogEntry> getByEmail(String email) {
		return null;
	}
	
	/**
	 * Establishes a connection with the database and selects all the Logs
	 * within the logEntry table. It then adds them to a newly created ArrayList
	 * and returns that to the calling object.
	 * @return ArrayList<LogEntry> containing all of the Logs from the database
	 */
	public ArrayList<LogEntry> getAll() {
		return null;
	}
}
