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
	
	private int taxReturnYear;
	
	public Parcel (int parcelID, String subject, String message, String sender, String receiver, Date dateSent, Date expirationDate, int taxReturnYear, ArrayList<Document> documents) {
		
		this.parcelID = parcelID;
		this.subject = subject;
		this.message = message;
		this.sender = sender;
		this.receiver = receiver;
		this.dateSent = dateSent;
		this.expirationDate = expirationDate;
		this.taxReturnYear = taxReturnYear;
		this.documents = documents;
	}
	
	/**
	 * @return the taxReturn
	 */
	public int getTaxReturn() {
		return taxReturnYear;
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
	 * @return
	 */
	public String getSubject() {
		return subject;
	}
}
