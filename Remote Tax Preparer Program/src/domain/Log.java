package domain;

import java.util.Date;

public class Log {

	private int logID;
	
	private String email;
	
	private String message;
	
	private char type;
	
	private Date date;
	
	private String ip;
	
	public Log(String email, String ip, char type) {
		
		setEmail(email);
		setIp(ip);
		setType(type);
		setDate(new Date());
	}
	
	public Log(int logID, String email, String ip, char type, Date date) {
		
		setLogID(logID);
		setEmail(email);
		setIp(ip);
		setType(type);
		setDate(date);
	}

	/**
	 * Returns the logID.
	 * @return the logID
	 */
	public int getLogID() {
		return logID;
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
	public void setLogID(int logID) {
		this.logID = logID;
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
		
			case 'L':
			case 'E':
			case 'X':
			case 'S':
				this.type = type;
				break;
			
			default:
				System.out.println("Incorrect type character.");
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
