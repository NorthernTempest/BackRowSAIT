package manager;

import java.io.PrintWriter;
import java.io.StringWriter;
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
		return LogEntryDB.getByParameters(email, type, startDate, endDate, ip);
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
	
	/**
	 * Creates a log entry for the given error.
	 * 
	 * @param email String The email address of the user who triggered the error.
	 * @param error Exception The exception that was thrown.
	 * @param ip String The ip address of the user who triggered the error.
	 * @return true if the error log was successfully added to the database.
	 */
	public static boolean logError(String email, Exception error, String ip) {
		StringWriter sw = new StringWriter();
		error.printStackTrace(new PrintWriter(sw));
		return LogEntryDB.insert(new LogEntry(email, sw.toString(), LogEntry.ERROR, ip));
	}

}
