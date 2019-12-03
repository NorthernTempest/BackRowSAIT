package domain;

public class Log {

	private int logID;
	
	private String userEmail;
	
	private String logDescription;

	/**
	 * Returns the logID.
	 * @return the logID
	 */
	public int getLogID() {
		return logID;
	}

	/**
	 * Returns the userEmail.
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * Returns the logDescription.
	 * @return the logDescription
	 */
	public String getLogDescription() {
		return logDescription;
	}

	/**
	 * Sets the value of logID.
	 * @param logID the logID to set
	 */
	public void setLogID(int logID) {
		this.logID = logID;
	}

	/**
	 * Sets the value of userEmail.
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * Sets the value of logDescription.
	 * @param logDescription the logDescription to set
	 */
	public void setLogDescription(String logDescription) {
		this.logDescription = logDescription;
	}
}
