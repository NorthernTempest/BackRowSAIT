package domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * Class Description: Class defining a parcel's attributes and methods to be used in sending messages and documents between clients.
 *
 * @author Tristen Kreutz, Cesar Guzman
 *
 */
public final class Parcel {

	private int parcelID;
	
	private ArrayList<Document> documents;
	
	private String subject;
	
	private String message;
	
	private String sender;
	
	private String receiver;
	
	private Date dateSent;
	
	private Date expirationDate;
	
	private int taxReturn;
	
	public Parcel (int parcelID, String subject, String message, String sender, String receiver, Date dateSent, Date expirationDate, int taxReturn) {
		
		setParcelID(parcelID);
		setSubject(subject);
		setMessage(message);
		setSender(sender);
		setReceiver(receiver);
		setDateSent(dateSent);
		setExpirationDate(expirationDate);
		setTaxReturn(taxReturn);
	}
	
	/**
	 * @return the taxReturn
	 */
	public int getTaxReturn() {
		return taxReturn;
	}

	/**
	 * @param taxReturn the taxReturn to set
	 */
	public void setTaxReturn(int taxReturn) {
		this.taxReturn = taxReturn;
	}

	/**
	 * Returns the parcelID.
	 * @return the parcelID
	 */
	public int getParcelID() {
		return parcelID;
	}

	/**
	 * Returns the documents.
	 * @return the documents
	 */
	public ArrayList<Document> getDocuments() {
		return documents;
	}

	/**
	 * Returns the message.
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the sender.
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Returns the receiver.
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * Returns the dateSent.
	 * @return the dateSent
	 */
	public Date getDateSent() {
		return dateSent;
	}

	/**
	 * Returns the expirationDate.
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Sets the value of parcelID.
	 * @param parcelID the parcelID to set
	 */
	public void setParcelID(int parcelID) {
		this.parcelID = parcelID;
	}

	/**
	 * Sets the value of documents.
	 * @param documents the documents to set
	 */
	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}

	/**
	 * Sets the value of message.
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Sets the value of sender.
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Sets the value of receiver.
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * Sets the value of dateSent.
	 * @param dateSent the dateSent to set
	 */
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	/**
	 * Sets the value of expirationDate.
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
