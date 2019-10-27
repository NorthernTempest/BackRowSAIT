package parcel;

/**
 * 
 * Class Description: Class defining attributes and methods utilized in simple peer-to-peer messaging.
 *
 * @author Tristen Kreutz
 *
 */
public class Message {
	
	/**
	 * The message object's subject line.
	 */
	private String subject;
	
	/**
	 * The contents of the message object's message.
	 */
	private String message;
	
	/**
	 * Returns the subject of the message object.
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Sets the message object's subject attribute.
	 * @param subject subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * Returns the message contents of the message object.
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message object's message attribute.
	 * @param message message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
