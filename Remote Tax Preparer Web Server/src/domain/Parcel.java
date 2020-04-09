package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Class Description: Record of a message between users.
 *
 * @author Tristen Kreutz, Cesar Guzman
 */
public final class Parcel {
	/**
	 * The id of this parcel.
	 */
	private String parcelID;
	/**
	 * The list of documents attached to the parcel.
	 */
	private ArrayList<Document> documents;
	/**
	 * The subject of the parcel.
	 */
	private String subject;
	/**
	 * The message of the parcel.
	 */
	private String message;
	/**
	 * The email of the user who sent the parcel.
	 */
	private String sender;
	/**
	 * The email of the recipient of the parcel.
	 */
	private String receiver;
	/**
	 * The date that the parcel was sent.
	 */
	private Date dateSent;
	/**
	 * The date when the parcel must be deleted from the system.
	 */
	private Date expirationDate;
	/**
	 * The tax year of the tax return the parcel discusses.
	 */
	private int taxReturnYear;
	/**
	 * Whether the first document of this parcel requires a signature.
	 */
	private boolean requiresSignature;

	/**
	 * Constructs a new parcel.
	 * 
	 * @param subject String The subject of the parcel's message.
	 * @param message String the parcel's message.
	 * @param sender String the email of the user who sent the parcel.
	 * @param receiver String the recipient of the parcel.
	 * @param dateSent Date the date that the parcel was sent.
	 * @param expirationDate Date the date that the parcel must be destroyed by.
	 * @param taxReturnYear int The year that the return was created on.
	 * @param documents ArrayList The list of documents attached to this parcel.
	 * @param requiresSignature boolean Whether the first document of the parcel is a form that requires signature.
	 */
	public Parcel(String subject, String message, String sender, String receiver, Date dateSent, Date expirationDate,
			int taxReturnYear, ArrayList<Document> documents, boolean requiresSignature) {

		this.parcelID = UUID.randomUUID().toString();
		this.subject = subject.replaceAll("'", "’");
		this.message = message.replaceAll("'", "’");
		this.sender = sender;
		this.receiver = receiver;
		this.dateSent = dateSent;
		this.expirationDate = expirationDate;
		this.taxReturnYear = taxReturnYear;
		this.documents = documents;
		this.requiresSignature = requiresSignature;
	}

	/**
	 * Constructs a parcel from the database.
	 * 
	 * @param parcelID
	 * @param subject
	 * @param message
	 * @param sender
	 * @param receiver
	 * @param dateSent
	 * @param expirationDate
	 * @param taxReturnYear
	 * @param documents
	 * @param requiresSignature
	 */
	public Parcel(String parcelID, String subject, String message, String sender, String receiver, Date dateSent,
			Date expirationDate, int taxReturnYear, ArrayList<Document> documents, boolean requiresSignature) {

		this.parcelID = parcelID;
		this.subject = subject.replaceAll("'", "’");
		this.message = message.replaceAll("'", "’");
		this.sender = sender;
		this.receiver = receiver;
		this.dateSent = dateSent;
		this.expirationDate = expirationDate;
		this.taxReturnYear = taxReturnYear;
		this.documents = documents;
		this.requiresSignature = requiresSignature;
	}

	/**
	 * Gets the tax year of the tax return the parcel discusses.
	 * 
	 * @return the tax year of the tax return the parcel discusses.
	 */
	public int getTaxReturnYear() {
		return taxReturnYear;
	}

	/**
	 * Gets the id of this parcel.
	 * 
	 * @return the id of this parcel.
	 */
	public String getParcelID() {
		return parcelID;
	}

	/**
	 * Gets the list of documents attached to the parcel.
	 * 
	 * @return the list of documents attached to the parcel.
	 */
	public ArrayList<Document> getDocuments() {
		return documents;
	}

	/**
	 * Gets the message of the parcel.
	 * 
	 * @return the message of the parcel.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the email of the user who sent the parcel.
	 * 
	 * @return the email of the user who sent the parcel.
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Gets the email of the recipient of the parcel.
	 * 
	 * @return the email of the recipient of the parcel.
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * Gets the date that the parcel was sent.
	 * 
	 * @return the date that the parcel was sent.
	 */
	public Date getDateSent() {
		return dateSent;
	}

	/**
	 * Gets the date when the parcel must be deleted from the system.
	 * 
	 * @return the date when the parcel must be deleted from the system.
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Gets the subject of the parcel.
	 * @return the subject of the parcel.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Returns true if the first document of this parcel requires a signature.
	 * @return true if the first document of this parcel requires a signature.
	 */
	public boolean isRequiresSignature() {
		return requiresSignature;
	}
}
