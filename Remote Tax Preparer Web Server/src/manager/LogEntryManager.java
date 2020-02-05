package manager;

import java.util.ArrayList;
import java.util.Date;

import domain.LogEntry;

/**
 * 
 * Class Description: Class that communicates with the LogEntryDB class as a
 * proxy to pass information utilized in communicating with the database.
 *
 * @author Tristen Kreutz, Cesar Guzman, Taylor Hanlon
 *
 */
public final class LogEntryManager {

	/**
	 * Takes the LogEntry object passed into the method and calls the insert method
	 * of the LogEntryDB, passing the LogEntry.
	 * 
	 * @param logEntry LogEntry to insert
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean insert(LogEntry logEntry) {
		return false;
	}

	/**
	 * Takes the LogEntry object passed into the method and calls the update method
	 * of the LogEntryDB, passing the LogEntry.
	 * 
	 * @param logEntry LogEntry in the database to update
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean update(LogEntry logEntry) {
		return false;
	}

	/**
	 * Takes the logID passed into the method and calls the delete method of the
	 * LogEntryDB, passing the logID.
	 * 
	 * @param logID logID of the LogEntry in the database to delete
	 * @return boolean based on whether or not the operation was successful
	 */
	public static boolean delete(int logID) {
		return false;
	}

	/**
	 * Takes the logID passed into the method and calls the get method of the
	 * LogEntryDB, passing the logID.
	 * 
	 * @param logID logID of the LogEntry in the database to retrieve
	 * @return LogEntry containing the information of the requested LogEntry
	 */
	public static LogEntry get(int logID) {
		return null;
	}

	/**
	 * Calls the getAll method of the LogEntryDB.
	 * 
	 * @return ArrayList<LogEntry> containing all the LogEntrys in the database
	 */
	public static ArrayList<LogEntry> getAll() {
		return null;
	}

	//TODO
	/**
	 * @param logEntryID
	 * @param email
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param ip
	 * @return ArrayList<LogEntry> containing all the LogEntrys in the database that
	 *         match given parameters
	 */
	public static ArrayList<LogEntry> getLog(String logEntryID, String email, char type, Date startDate, Date endDate, String ip) {
		return null;

	}

	/**
	 * Counts the number of times a logEntry of type LOGIN_ATTEMPT has been created
	 * matching the passed email within the past 10 minutes.
	 * 
	 * @param email email of logs to count
	 * @return number of logs counted matching the above conditions
	 */
	public static int checkAttempts(String email) {
		return 0;
	}

	/**
	 * Inserts into the logEntry table of the database a specific type of log based
	 * on a user having successfully logged in.
	 * 
	 * @param email email of the user that successfully logged in
	 * @param ip    ip of the user that successfully logged in
	 */
	public static void insertLogin(String email, String ip) {

	}
}
