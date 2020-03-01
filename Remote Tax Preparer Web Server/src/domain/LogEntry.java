package domain;

import java.util.Date;

public final class LogEntry {

	private int logEntryID;
	
	private String email;
	
	private String message;
	
	private char type;
	
	private Date date;
	
	private String ip;
	
	public static final char LOGIN_ATTEMPT = 'L';
	public static final char ERROR = 'E';
	public static final char DEACTIVATE_ACCOUNT = 'D';
	public static final char UPDATE_ACCOUNT = 'U';
	public static final char RECOVER_PASSWORD = 'R';
	public static final char SESSION_UPDATE = 'S';
	
	public LogEntry(String email, String message, char type, String ip) {
		this.logEntryID = -1;
		this.email = email;
		this.message = message;
		setType(type);
		this.date = new Date();
		this.ip = ip;
	}
	
	public LogEntry(int logEntryID, String email, String message, char type, Date date, String ip) {
		
		this.logEntryID = logEntryID;
		this.email = email;
		this.message = message;
		setType(type);
		this.date = date;
		this.ip = ip;
	}
	
	/**
	 * Returns the logEntryID.
	 * @return the logEntryID
	 */
	public int getLogEntryID() {
		return logEntryID;
	}

	/**
	 * Returns the email.
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the message.
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the type.
	 * @return the type
	 */
	public char getType() {
		return type;
	}

	/**
	 * Returns the date.
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Returns the ip.
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the value of type.
	 * @param type the type to set
	 */
	private void setType(char type) {
		
		switch(type) {
		
			case LOGIN_ATTEMPT:
			case ERROR:
			case DEACTIVATE_ACCOUNT:
			case RECOVER_PASSWORD:
			case UPDATE_ACCOUNT:
			case SESSION_UPDATE:
				this.type = type;
				break;
			default:
				System.out.println("Incorrect type character.");
				throw new IllegalArgumentException("Illegal log entry type");
		}
	}

	public boolean isloggedOut(String email) {
		return false;
	}
}