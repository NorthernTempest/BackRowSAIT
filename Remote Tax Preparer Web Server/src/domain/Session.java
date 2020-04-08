package domain;

import java.util.Date;

/**
 * Class Description: A record of a user's session after having logged into the system.
 *
 * @author Tristen Kreutz
 */
public final class Session {
	/**
	 * The email of the logged in user.
	 */
	private String email;
	/**
	 * The ID of the user's JSession cookie.
	 */
	private String sessionID;
	/**
	 * The time and date that the session times out at.
	 */
	private Date timeout;
	
	/**
	 * Constructs a complete session record.
	 * 
	 * @param email The email of the logged in user.
	 * @param sessionID The ID of the user's JSession cookie.
	 * @param timeout The time and date that the session times out at.
	 */
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
