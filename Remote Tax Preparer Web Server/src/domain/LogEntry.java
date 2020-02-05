package domain;

import java.util.Date;

public class LogEntry {

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
	
	public LogEntry(String email, String ip, char type) {
		
		setEmail(email);
		setIp(ip);
		setType(type);
		setDate(new Date());
	}
	
	public LogEntry(int logEntryID, String email, String ip, char type, Date date) {
		
		setLogEntryID(logEntryID);
		setEmail(email);
		setIp(ip);
		setType(type);
		setDate(date);
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
	 * Sets the value of logID.
	 * @param logID the logID to set
	 */
	public void setLogEntryID(int logEntryID) {
		this.logEntryID = logEntryID;
	}

	/**
	 * Sets the value of email.
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the value of message.
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Sets the value of type.
	 * @param type the type to set
	 */
	public void setType(char type) {
		
		switch(type) {
		
			case LOGIN_ATTEMPT:
			case ERROR:
			case DEACTIVATE_ACCOUNT:
			case UPDATE_ACCOUNT:
				this.type = type;
				break;
			
			default:
				System.out.println("Incorrect type character.");
				//TODO
				break;
		}
	}

	/**
	 * Sets the value of date.
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Sets the value of ip.
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isloggedOut(String email) {
		return false;
	}
}