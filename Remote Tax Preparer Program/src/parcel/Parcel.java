package parcel;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * Class Description: Class defining a parcel's attributes and methods to be used in sending messages and documents between clients.
 *
 * @author Tristen Kreutz
 *
 */
public class Parcel {
	
	/**
	 * A list containing documents that are attached to the parcel.
	 */
	private ArrayList<Document> documents;
	
	/**
	 * A message to provide an overview or summary of the parcel's contents.
	 */
	private String message;
	
	/**
	 * The information of the sending client.
	 */
	private String sender;
	
	/**
	 * The information of the receiving client.
	 */
	private String receiver;
	
	/**
	 * Boolean for whether or not a signature is still required.
	 */
	private boolean signatureRequired;
	
	/**
	 * Date for when the parcel was sent. This cannot be changed after the parcel is sent.
	 */
	private Date date;
	
	/**
	 * Date for when the parcel should be automatically deleted.
	 */
	private Date expirationDate;
	
	/**
	 * Returns the contents of the parcel's message.
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the contents of the parcel's message.
	 * @param message message contents
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Returns the information of the sending client.
	 * @return sender information
	 */
	public String getSender() {
		return sender;
	}
	
	/**
	 * Sets the information of the sending client.
	 * @param sender sender information
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	/**
	 * Returns the information of the receiving client.
	 * @return receiver information
	 */
	public String getReceiver() {
		return receiver;
	}
	
	/**
	 * Sets the information of the receiving client.
	 * @param receiver receiver information
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * Returns a boolean based on whether or not a signature required.
	 * @return signatureRequired
	 */
	public boolean requiresSignature() {
		return signatureRequired;
	}
	
	/**
	 * Sets the value of the signatureRequired boolean based on if the file has been signed.
	 * @param signatureRequired signatureRequired
	 */
	public void setSignatureRequired(boolean signatureRequired) {
		this.signatureRequired = signatureRequired;
	}
	
	/**
	 * Returns the date the parcel was sent.
	 * @return date sent
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Sets the date for when the parcel is sent. This cannot be changed after the parcel has been sent.
	 * @param date date sent
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Returns the date for when the parcel should be automatically deleted.
	 * @return expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	/**
	 * Sets the expiration date for when the parcel should be automatically deleted.
	 * @param expirationDate expirationDate
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * Adds a document to the parcel.
	 * @param d document to add
	 */
	public void addDocument(Document d) {
		
	}
	
	/**
	 * Removes a document from the parcel.
	 * @param i index to remove
	 */
	public void removeDocument(int i) {
		
	}
}
