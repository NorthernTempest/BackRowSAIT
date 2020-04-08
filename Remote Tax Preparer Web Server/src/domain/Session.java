package domain;

import java.util.Date;

/**
 * Class Description:	Class defining the attributes and methods for a Session object.
 *
 * @author Tristen Kreutz
 *
 */
public final class Session {

	private String email;
	
	private String sessionID;
	
	private Date timeout;
	
	public Session(String email, String sessionID, Date timeout) {
		this.email = email;
		this.sessionID = sessionID;
		this.timeout = timeout;
	}

	/**
	 * Returns the sessionID.
	 * @return the sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * Returns the email.
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the timeout.
	 * @return the timeout
	 */
	public Date getTimeout() {
		return timeout;
	}

	/**
	 * Sets the value of sessionID.
	 * @param sessionID the sessionID to set
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * Sets the value of email.
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the value of timeout.
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Date timeout) {
		this.timeout = timeout;
	}
}
