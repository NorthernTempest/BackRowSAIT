package domain;

import java.util.Date;

/**
 * Class Description: A record of some event in the system.
 * @author Tristen Kreutz, Jesse Goerzen
 */
public final class LogEntry {
	/**
	 * The id number of the log entry.
	 */
	private int logEntryID;
	/**
	 * The email of the user who initiated the event.
	 */
	private String email;
	/**
	 * Any information that needs to be stored about the event.
	 */
	private String message;
	/**
	 * The type of event that necessitated the log entry.
	 */
	private char type;
	/**
	 * The time and date that the date occurred on.
	 */
	private Date date;
	/**
	 * The ip address of the user that caused the event.
	 */
	private String ip;
	
	/**
	 * Identifies a login attempt event.
	 */
	public static final char LOGIN_ATTEMPT = 'L';
	/**
	 * Identifies an error event.
	 */
	public static final char ERROR = 'E';
	/**
	 * Identifies the deactivation event of an account.
	 */
	public static final char DEACTIVATE_ACCOUNT = 'D';
	/**
	 * Identifies the update event of an account.
	 */
	public static final char UPDATE_ACCOUNT = 'U';
	/**
	 * Identifies the recovery event of a password.
	 */
	public static final char RECOVER_PASSWORD = 'P';
	/**
	 * Identifies the update of a session.
	 */
	public static final char SESSION_UPDATE = 'S';
	/**
	 * Identifies the backing up of the system.
	 */
	public static final char BACKUP = 'B';
	/**
	 * Identifies the restoration of the system to a previous state.
	 */
	public static final char RESTORE_SYSTEM = 'R';
	
	/**
	 * Constructs a complete record of a system event before being entered into the database.
	 * 
	 * @param email String The email of the user who initiated the event.
	 * @param message String Any information that needs to be stored about the event.
	 * @param type char The type of event that necessitated the log entry.
	 * @param ip String The ip address of the user that caused the event.
	 */
	public LogEntry(String email, String message, char type, String ip) {
		this.logEntryID = -1;
		this.email = email;
		this.message = message;
		setType(type);
		this.date = new Date();
		this.ip = ip;
	}
	
	/**
	 * Constructs a complete record of a system event that has been entered into the database.
	 * 
	 * @param logEntryID int The id number of the log entry.
	 * @param email String The email of the user who initiated the event.
	 * @param message String Any information that needs to be stored about the event.
	 * @param type char The type of event that necessitated the log entry.
	 * @param date Date The date and time that the event occurred at.
	 * @param ip String The ip address of the user that caused the event.
	 */
	public LogEntry(int logEntryID, String email, String message, char type, Date date, String ip) {
		
		this.logEntryID = logEntryID;
		this.email = email;
		this.message = message;
		setType(type);
		this.date = date;
		this.ip = ip;
	}
	
	/**
	 * Gets the id number of the log entry.
	 * @return int The id number of the log entry.
	 */
	public int getLogEntryID() {
		return logEntryID;
	}

	/**
	 * Gets the email of the user who initiated the event.
	 * @return String The email of the user who initiated the event.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets any information that needs to be stored about the event.
	 * @return String Any information that needs to be stored about the event.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the type of event that necessitated the log entry.
	 * @return char The type of event that necessitated the log entry.
	 */
	public char getType() {
		return type;
	}

	/**
	 * Gets the time and date that the date occurred on.
	 * @return Date The time and date that the date occurred on.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Gets the ip address of the user that caused the event.
	 * 
	 * @return String The ip address of the user that caused the event.
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the type of event that necessitated the log entry.
	 * Method checks to see if the value being set matches any of the constant values available for a log type.
	 * 
	 * @param char The type of event that necessitated the log entry.
	 */
	private void setType(char type) {
		
		switch(type) {
		
			case LOGIN_ATTEMPT:
			case ERROR:
			case DEACTIVATE_ACCOUNT:
			case RECOVER_PASSWORD:
			case UPDATE_ACCOUNT:
			case SESSION_UPDATE:
			case BACKUP:
			case RESTORE_SYSTEM:
				this.type = type;
				break;
			default:
				System.out.println("Incorrect type character.");
				throw new IllegalArgumentException("Illegal log entry type");
		}
	}
}