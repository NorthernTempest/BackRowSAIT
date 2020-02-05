package manager;

import java.util.ArrayList;
import java.util.Date;

import databaseAccess.LogEntryDB;
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
	public static LogEntry get(int logEntryID) {
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

	/** 
	 * Gets all LogEntries from database fulfilling criteria specified by parameters
	 * 
	 * @param email the email used to fetch logs, any if null
	 * @param type the log type used to fetch logs, any if null
	 * @param startDate the start Date to fetch logs from
	 * @param endDate the end Date to fetch logs to
	 * @param ip the ip used to fetch logs, any if null
	 * @return ArrayList<LogEntry> containing all the LogEntrys in the database that
	 *         match given parameters
	 */
	public static ArrayList<LogEntry> getLog(String email, char type, Date startDate, Date endDate, String ip) {
		LogEntryDB ldb = new LogEntryDB();
		ArrayList<LogEntry> log = ldb.getByParameters(email, type, startDate, endDate, ip);
		
		return log;
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
	 * Creates a LogEntry Object and inserts it into the database
	 * @param email
	 * @param message
	 * @param type
	 * @param ip
	 * @return
	 */
	public static boolean createLogEntry(String email, String message, char type, String ip) {
		return false;
	}

}
